package com.hyl.op.log.core.mybatis;

import com.hyl.op.log.config.OpLogConfig;
import com.hyl.op.log.core.SqlMetaData;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.sql.Connection;

/**
 * @author huayuanlin
 * @date 2021/08/04 15:25
 * @desc the class desc
 */
public class ParseContext {

    private MappedStatement mappedStatement;
    private BoundSql boundSql;
    private Connection connection;
    private OpLogConfig opLogConfig;
    private SqlMetaData sqlMetaData;
    private Object cache;

    public Object getCache() {
        return cache;
    }

    public void setCache(Object cache) {
        this.cache = cache;
    }

    public MappedStatement getMappedStatement() {
        return mappedStatement;
    }

    public void setMappedStatement(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }

    public void setBoundSql(BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public OpLogConfig getOpLogConfig() {
        return opLogConfig;
    }

    public void setOpLogConfig(OpLogConfig opLogConfig) {
        this.opLogConfig = opLogConfig;
    }

    public SqlMetaData getSqlMetaData() {
        return sqlMetaData;
    }

    public void setSqlMetaData(SqlMetaData sqlMetaData) {
        this.sqlMetaData = sqlMetaData;
    }
}
