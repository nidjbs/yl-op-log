package com.hyl.op.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author huayuanlin
 * @date 2021/06/10 21:43
 * @desc the class desc
 */
@ConfigurationProperties(prefix = "op.log")
public class OpLogConfig {

    /*** need log table names，lower case */
    private List<String> logTables;
    /*** record the operation log in the same transaction as your business */
    private Boolean logInTx;
    /*** manualTxTimeOut unit：s */
    private Integer manualTxTimeOut;
    /*** softDeleteColumn ex：del */
    private String softDeleteColumn;
    /*** softDeleteColumn delete status value  ex：1,-1,0,true,false */
    private String softDeleteColumnDeleteValue;

    public String getSoftDeleteColumnDeleteValue() {
        return softDeleteColumnDeleteValue;
    }

    public void setSoftDeleteColumnDeleteValue(String softDeleteColumnDeleteValue) {
        this.softDeleteColumnDeleteValue = softDeleteColumnDeleteValue;
    }

    public String getSoftDeleteColumn() {
        return softDeleteColumn;
    }

    public void setSoftDeleteColumn(String softDeleteColumn) {
        this.softDeleteColumn = softDeleteColumn;
    }

    public List<String> getLogTables() {
        return logTables;
    }

    public void setLogTables(List<String> logTables) {
        this.logTables = logTables;
    }


    public Integer getManualTxTimeOut() {
        return manualTxTimeOut;
    }

    public void setManualTxTimeOut(Integer manualTxTimeOut) {
        this.manualTxTimeOut = manualTxTimeOut;
    }


    public Boolean getLogInTx() {
        return logInTx;
    }

    public void setLogInTx(Boolean logInTx) {
        this.logInTx = logInTx;
    }
}
