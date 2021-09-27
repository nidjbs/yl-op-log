package com.hyl.op.log.core;

/**
 * @author huayuanlin
 * @date 2021/08/06 18:14
 * @desc the class desc
 */
public class OpLogGlobalContext {

    /**
     * op type（description）
     */
    private String opType;

    /**
     * biz code,unique in a system
     */
    private String bizCode;

    /**
     * traceId
     */
    private String traceId;

    /**
     * bizId
     */
    private String bizId;

    /**
     * opUserId
     */
    private String opId;

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }
}
