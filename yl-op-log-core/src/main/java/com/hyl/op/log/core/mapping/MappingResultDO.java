package com.hyl.op.log.core.mapping;

/**
 * @author huayuanlin
 * @date 2021/06/29 01:22
 * @desc the class desc
 */
public class MappingResultDO {

    private String before;

    private String after;

    private Integer filedType;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public Integer getFiledType() {
        return filedType;
    }

    public void setFiledType(Integer filedType) {
        this.filedType = filedType;
    }

    @Override
    public String toString() {
        return "MappingResultDO{" +
                "before='" + before + '\'' +
                ", after='" + after + '\'' +
                ", filedType=" + filedType +
                '}';
    }
}
