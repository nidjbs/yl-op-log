package com.hyl.op.log.config;

import com.hyl.op.log.core.web.FeignRequestInterceptor;
import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huayuanlin
 * @date 2021/08/10 22:43
 * @desc the class desc
 */
@Configuration
@ConditionalOnClass(Feign.class)
@ConditionalOnProperty(prefix = "op.log.global",name = "enable",havingValue = "true")
public class FeignRequestInterceptorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
