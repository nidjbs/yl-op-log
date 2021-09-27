package com.hyl.op.log.core;

import com.hyl.op.log.enums.SqlType;

import java.util.List;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/06/13 00:42
 * @desc the class desc
 */
public class SqlMetaData {

    private SqlType sqlType;

    private boolean needLog = false;

    private String tableName;

    private String sql;

    private List<Map<String, Object>> beforeData;

    private List<Map<String, Object>> afterData;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public boolean isNeedLog() {
        return needLog;
    }

    public void setNeedLog(boolean needLog) {
        this.needLog = needLog;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Map<String, Object>> getBeforeData() {
        return beforeData;
    }

    public void setBeforeData(List<Map<String, Object>> beforeData) {
        this.beforeData = beforeData;
    }

    public List<Map<String, Object>> getAfterData() {
        return afterData;
    }

    public void setAfterData(List<Map<String, Object>> afterData) {
        this.afterData = afterData;
    }
}