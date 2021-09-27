package com.hyl.op.log.core.mapping;

import com.google.common.collect.Sets;
import com.hyl.op.log.annotations.EnableOpLogMapping;
import com.hyl.op.log.annotations.OpLogTable;
import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.util.AssertUtil;
import com.hyl.op.log.util.ConverterUtil;
import com.hyl.op.log.util.StringUtil;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

/**
 * @author huayuanlin
 * @date 2021/06/24 15:31
 * @desc the class desc
 */
public class MappingBeansRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {


    private ResourceLoader resourceLoader;

    private Environment environment;

    /**
     * register temp bean, the bean will eventually be converted to a mapping bean
     *
     * @see ProcessRawMappingBean
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry, false);
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(OpLogTable.class));
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableOpLogMapping.class.getName());
        assert attrs != null;
        Assert.notNull(attrs, "attrs is not null");
        String[] basePackages = (String[]) attrs.get("basePackages");
        Set<String> basePackageSet = Sets.newHashSet(basePackages);
        basePackageSet.forEach(path -> {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(path);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    AssertUtil.isTrue(!annotationMetadata.isInterface(), "mapping DO must not interface!");
                    Map<String, Object> attributes = annotationMetadata
                            .getAnnotationAttributes(OpLogTable.class.getCanonicalName());
                    String tableName = (String) attributes.get("tableName");
                    String beanClassName = beanDefinition.getBeanClassName();
                    tableName = formatOrDefaultTableName(tableName, beanClassName);
                    String beanName = OpLogConstant.PREFIX + tableName;
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClassName);
                    // singleton
                    builder.setScope(BeanDefinition.SCOPE_SINGLETON);
                    // will eventually be converted to a mapping bean,see com.yqn.op.log.core.mapping.ProcessRawMappingBean
                    beanDefinitionRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());
                }
            }
        });
    }

    /**
     * format table name by class name or user specified name
     *
     * @param tableName     the table name
     * @param beanClassName the class name
     * @return format table name
     */
    private String formatOrDefaultTableName(String tableName, String beanClassName) {
        if (StringUtil.isEmpty(tableName)) {
            // todo 支持配置
            beanClassName = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
            beanClassName = beanClassName.substring(0, beanClassName.indexOf("DO"));
            return ConverterUtil.camelToUnderline(beanClassName);
        } else {
            return tableName;
        }
    }


    private String getFromConfig(String key) {
        AssertUtil.notNull(key, "config key");
        return this.environment.resolvePlaceholders(key);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
