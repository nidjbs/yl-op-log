package com.hyl.op.log.core.mybatis.processor;

import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.core.mybatis.MybatisParseDataProcessor;
import com.hyl.op.log.core.mybatis.MybatisSqlMetaData;
import com.hyl.op.log.core.mybatis.ParseContext;
import com.hyl.op.log.util.ObjUtil;
import com.hyl.op.log.util.SqlParseUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huayuanlin
 * @date 2021/06/17 14:18
 * @desc the delete MybatisOpLogInterceptorProcessor
 */
public class DeleteMybatisOpLogInterceptorProcessor extends MybatisParseDataProcessor {

    private DeleteMybatisOpLogInterceptorProcessor() {

    }


    @Override
    public List<Map<String, Object>> parseBeforeData(ParseContext context) {
        MybatisSqlMetaData sqlMetaDataByContext = (MybatisSqlMetaData) context.getSqlMetaData();
        String formatToSelectSqlParam = SqlParseUtil.formatToSelectSqlParam(sqlMetaDataByContext.getSql());
        // get connection
        Connection connection = context.getConnection();
        String selectSql = String.format(OpLogConstant.SELECT_SQL_FORMAT, "*",
                sqlMetaDataByContext.getTableName(), formatToSelectSqlParam);
        BoundSql boundSql = context.getBoundSql();
        Configuration configuration = context.getMappedStatement().getConfiguration();
        List<Object> params = getAllSqlParams(boundSql, configuration);
        return doSelect(connection, selectSql, params);
    }

    /**
     * get sql params value
     *
     * @param boundSql      boundSql
     * @param configuration configuration
     * @return params value list
     */
    protected List<Object> getAllSqlParams(BoundSql boundSql, Configuration configuration) {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        return parameterMappings.stream().map(parameterMapping -> {
            Object value;
            String propertyName = parameterMapping.getProperty();
            if (boundSql.hasAdditionalParameter(propertyName)) {
                value = boundSql.getAdditionalParameter(propertyName);
            } else if (parameterObject == null) {
                value = null;
            } else {
                if (ObjUtil.isBaseType(parameterObject)) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
            }
            return value;
        }).collect(Collectors.toList());
    }


    public static DeleteMybatisOpLogInterceptorProcessor getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final DeleteMybatisOpLogInterceptorProcessor
                INSTANCE = new DeleteMybatisOpLogInterceptorProcessor();
    }
}
