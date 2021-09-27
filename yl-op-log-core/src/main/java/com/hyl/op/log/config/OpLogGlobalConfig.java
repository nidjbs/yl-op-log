package com.hyl.op.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huayuanlin
 * @date 2021/08/07 22:09
 * @desc the class desc
 */
@ConfigurationProperties(prefix = "op.log.global")
public class OpLogGlobalConfig {

    private Boolean enable = false;

    private String bizCode;


    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
