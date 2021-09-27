package com.hyl.op.log.core;

import java.util.List;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/06/20 21:27
 * @desc the class desc
 */
public class OpLogContent {

    private String tableName;

    private Integer type;

    private List<Map<String,Object>> before;

    private List<Map<String,Object>> after;


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

    public List<Map<String, Object>> getBefore() {
        return before;
    }

    public void setBefore(List<Map<String, Object>> before) {
        this.before = before;
    }

    public List<Map<String, Object>> getAfter() {
        return after;
    }

    public void setAfter(List<Map<String, Object>> after) {
        this.after = after;
    }
}
