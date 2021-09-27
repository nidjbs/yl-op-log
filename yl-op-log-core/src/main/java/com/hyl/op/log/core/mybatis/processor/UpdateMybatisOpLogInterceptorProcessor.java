package com.hyl.op.log.core.mybatis.processor;

import com.hyl.op.log.core.mybatis.MybatisParseDataProcessor;
import com.hyl.op.log.core.mybatis.MybatisSqlMetaData;
import com.hyl.op.log.core.mybatis.ParseContext;
import com.hyl.op.log.util.CollectionsUtil;
import com.hyl.op.log.util.SqlParseUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/06/17 14:21
 * @desc the update MybatisOpLogInterceptorProcessor
 */
public class UpdateMybatisOpLogInterceptorProcessor extends MybatisParseDataProcessor {

    private UpdateMybatisOpLogInterceptorProcessor() {

    }

    @Override
    public List<Map<String, Object>> parseBeforeData(ParseContext context) {
        // separate update column and condition
        MybatisSqlMetaData sqlMetaDataByContext = (MybatisSqlMetaData) context.getSqlMetaData();
        String sql = sqlMetaDataByContext.getSql();
        String selectSqlParam = SqlParseUtil.formatToSelectSqlParam(sql);
        BoundSql boundSql = context.getBoundSql();
        // get connection
        Connection connection = context.getConnection();
        String selectSql = SqlParseUtil.formatUpdateToSelectSql(sql, sqlMetaDataByContext.getTableName());
        Configuration configuration = context.getMappedStatement().getConfiguration();
        List<Object> sqlParams = getSqlParams(selectSqlParam, boundSql, configuration);
        // cache for after query
        CacheParam cacheParam = new CacheParam();
        cacheParam.setSelectSql(selectSql);
        cacheParam.setSelectParams(sqlParams);
        context.setCache(cacheParam);
        return doSelect(connection, selectSql, sqlParams);
    }

    @Override
    public List<Map<String, Object>> parseAfterData(ParseContext context) {
        CacheParam cache = (CacheParam) context.getCache();
        List<Object> selectParams = cache.getSelectParams();
        String selectSql = cache.getSelectSql();
        // get connection
        Connection connection = context.getConnection();
        return doSelect(connection, selectSql, selectParams);
    }

    /**
     * get sql prams
     *
     * @param selectSqlParam sql
     * @param boundSql       boundSql
     * @param configuration  configuration
     * @return sql prams
     */
    private List<Object> getSqlParams(String selectSqlParam, BoundSql boundSql, Configuration configuration) {
        // count contain ? param
        int count = (int) Arrays.stream(selectSqlParam.split("")).filter("?"::equals).count();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        MetaObject metaObject = configuration.newMetaObject(parameterObject);
        int size = parameterMappings.size();
        List<Object> params = CollectionsUtil.arrayList();
        // get the after where's condition param value
        for (int i = size - count; i < size; i++) {
            params.add(metaObject.getValue(parameterMappings.get(i).getProperty()));
        }
        return params;
    }

    protected static class CacheParam {

        private String selectSql;

        private List<Object> selectParams;

        public String getSelectSql() {
            return selectSql;
        }

        public void setSelectSql(String selectSql) {
            this.selectSql = selectSql;
        }

        public List<Object> getSelectParams() {
            return selectParams;
        }

        public void setSelectParams(List<Object> selectParams) {
            this.selectParams = selectParams;
        }
    }

    public static UpdateMybatisOpLogInterceptorProcessor getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final UpdateMybatisOpLogInterceptorProcessor
                INSTANCE = new UpdateMybatisOpLogInterceptorProcessor();
    }
}
