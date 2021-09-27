package com.hyl.op.log.core.mybatis;

import com.hyl.op.log.common.ObjBuilder;
import com.hyl.op.log.config.OpLogConfig;
import com.hyl.op.log.core.OpLogInterceptorProcessor;
import com.hyl.op.log.core.SqlMetaData;
import com.hyl.op.log.enums.SqlType;
import com.hyl.op.log.exception.FrameworkException;
import com.hyl.op.log.util.SpringBeanUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;

/**
 * @author huayuanlin
 * @date 2021/08/04 14:01
 * @desc the class desc
 */
public abstract class BaseOpLogInterceptorProcessor implements OpLogInterceptorProcessor {

    private static final String DELEGATE_MAPPED_STATEMENT = "delegate.mappedStatement";
    private static final String DELEGATE_BOUND_SQL = "delegate.boundSql";

    @Override
    public final SqlMetaData parseMetaData(Object invocation) {
        try {
            ParseContext parseContext = initContext((Invocation)invocation);
            SqlType sqlType = this.getSqlType(parseContext);
            if (sqlType == SqlType.UNKNOWN) {
                return null;
            }
            SqlMetaData sqlMetaData = new MybatisSqlMetaData();
            sqlMetaData.setSqlType(sqlType);
            parseContext.setSqlMetaData(sqlMetaData);
            if (!this.needLog(parseContext)) {
                return null;
            }
            return doParseData(parseContext);
        } catch (Exception exx) {
            // This exception will be caught by ourselves
            throw new FrameworkException("parse meta data error",exx);
        }
    }

    private ParseContext initContext(Invocation invocation) {
        // get the resource target obj（multiple interceptions will generate a proxy chain）
        StatementHandler statementHandler = PluginUtil.getTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(DELEGATE_MAPPED_STATEMENT);
        BoundSql boundSql = (BoundSql) metaObject.getValue(DELEGATE_BOUND_SQL);
        OpLogConfig opLogConfig = SpringBeanUtil.getBeanByType(OpLogConfig.class);
        Object[] args = invocation.getArgs();
        // get connection
        Connection connection = (Connection) args[0];
        return ObjBuilder.create(ParseContext::new).of(ParseContext::setBoundSql, boundSql)
                .of(ParseContext::setConnection, connection)
                .of(ParseContext::setMappedStatement, mappedStatement)
                .of(ParseContext::setOpLogConfig, opLogConfig)
                .preCheck(e -> e.getBoundSql() != null && e.getConnection() != null
                        && e.getMappedStatement() != null && e.getOpLogConfig() != null).build();
    }

    /**
     * get sql type
     * @param context context
     * @return sql type
     */
    protected abstract SqlType getSqlType(ParseContext context);


    /**
     * need Log ?
     *
     * @param context context
     * @return need Log ?
     */
    protected abstract boolean needLog(ParseContext context);

    /**
     * do parse data
     *
     * @param context context
     * @return data
     */
    protected abstract SqlMetaData doParseData(ParseContext context);



}
