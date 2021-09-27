package com.hyl.op.log.core;

import java.util.Date;

/**
 * @author huayuanlin
 * @date 2021/06/20 15:46
 * @desc the class desc
 */
public class OpLogMetaDataDO {

    private Long id;

    private String traceId;

    private String opId;

    private String opType;

    private String bizCode;

    private String bizId;

    /**
     * log status: 0-init，1-processing，2-fail，3-complete
     */
    private Integer status;

    /**
     * metaData json
     */
    private String metaData;

    /**
     * createdTime
     */
    private Date createdTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }


    @Override
    public String toString() {
        return "OpLogMetaDataDO{" +
                "id=" + id +
                ", traceId='" + traceId + '\'' +
                ", opId='" + opId + '\'' +
                ", opType='" + opType + '\'' +
                ", bizCode='" + bizCode + '\'' +
                ", bizId='" + bizId + '\'' +
                ", status=" + status +
                ", metaData='" + metaData + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
