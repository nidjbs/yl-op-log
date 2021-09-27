package com.hyl.op.log.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * @author huayuanlin
 * @date 2021/08/10 17:42
 * @desc the class desc
 */
public class OpLogDataSourceProperties extends DataSourceProperties {

    /**
     * Whether to enable multiple data sources to support saving logs to a unified database
     */
    private Boolean enable = false;

}
