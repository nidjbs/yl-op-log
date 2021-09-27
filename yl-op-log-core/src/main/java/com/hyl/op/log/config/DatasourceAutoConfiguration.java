package com.hyl.op.log.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author huayuanlin
 * @date 2021/08/05 17:42
 * @desc the class desc
 */
@Configuration
@ConditionalOnClass(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = "op.log.datasource",name = "enable",havingValue = "true")
public class DatasourceAutoConfiguration {


    @Bean
    public DefaultDatasourcePrimarySetterBean defaultDatasourcePrimarySetterBean() {
        return new DefaultDatasourcePrimarySetterBean();
    }


    @Bean("opLogDatasourceProperties")
    @ConfigurationProperties(prefix = "op.log.datasource")
    public OpLogDataSourceProperties opLogDataSourceProperties () {
        return new OpLogDataSourceProperties();
    }

    @Bean("opLogJdbcTemplate")
    public JdbcTemplate opLogJdbcTemplate(@Qualifier("opLogDatasourceProperties") DataSourceProperties properties) {
        return new JdbcTemplate(properties.initializeDataSourceBuilder().build());
    }

}
