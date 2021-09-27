package com.hyl.op.log.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author huayuanlin
 * @date 2021/08/05 20:51
 * @desc Set the Bean of the data Source of your default data source to Primary
 */
public class DefaultDatasourcePrimarySetterBean implements BeanDefinitionRegistryPostProcessor {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinition beanDefinition;
        try {
            beanDefinition = beanDefinitionRegistry.getBeanDefinition("spring.datasource-org.springframework.boot.autoconfigure.jdbc.DataSourceProperties");
        } catch (NoSuchBeanDefinitionException e) {
            return;
        }
        beanDefinition.setPrimary(true);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
