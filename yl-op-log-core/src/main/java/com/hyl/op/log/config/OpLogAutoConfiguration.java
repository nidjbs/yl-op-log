package com.hyl.op.log.config;

import com.hyl.op.log.core.ISqlLogMetaDataService;
import com.hyl.op.log.core.JdbcTemplateSqlLogMetaDataServiceImpl;
import com.hyl.op.log.core.OpLogAopMethodInterceptor;
import com.hyl.op.log.core.OpLogAopProxyCreator;
import com.hyl.op.log.core.web.OpLogContextFilter;
import com.hyl.op.log.util.SpringBeanUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huayuanlin
 * @date 2021/06/20 14:59
 * @desc the class desc
 */
@Configuration
@EnableConfigurationProperties(OpLogConfig.class)
public class OpLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OpLogAopProxyCreator opLogAopScanner() {
        return new OpLogAopProxyCreator(new OpLogAopMethodInterceptor());
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringBeanUtil opLogSpringBeanUtil() {
        return new SpringBeanUtil();
    }

    @Bean
    @ConditionalOnMissingBean
    public ISqlLogMetaDataService iSqlLogMetaDataService() {
        return new JdbcTemplateSqlLogMetaDataServiceImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<OpLogContextFilter> opLogContextFilter() {
        FilterRegistrationBean<OpLogContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new OpLogContextFilter());
        registration.addUrlPatterns("/*");
        registration.setName("op_log_context");
        registration.setEnabled(true);
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}
