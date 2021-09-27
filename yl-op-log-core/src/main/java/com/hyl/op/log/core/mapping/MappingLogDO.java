package com.hyl.op.log.core.mapping;

/**
 * @author huayuanlin
 * @date 2021/06/26 00:24
 * @desc the class desc
 */
public class MappingLogDO {

    private Long id;

    private String tableName;

    /**
     * 1-create 2-update 3-delete
     */
    private Integer type;

    private String opId;

    private String traceId;

    private String bizId;

    /**
     * update json,map list
     *
     */
    private String content;

    @Override
    public String toString() {
        return "MappingLogDO{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", type=" + type +
                ", opId='" + opId + '\'' +
                ", traceId='" + traceId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
