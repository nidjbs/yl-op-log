package com.hyl.op.log.core.mybatis;

import com.hyl.op.log.config.OpLogConfig;
import com.hyl.op.log.core.SqlMetaData;
import com.hyl.op.log.enums.SqlType;
import com.hyl.op.log.util.CollectionsUtil;
import com.hyl.op.log.util.ConverterUtil;
import com.hyl.op.log.util.SqlParseUtil;
import com.hyl.op.log.util.StringUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;

import java.util.List;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/08/04 14:34
 * @desc the class desc
 */
public class StandardOpLogInterceptorProcessor extends BaseOpLogInterceptorProcessor {

    @Override
    protected SqlType getSqlType(ParseContext context) {
        SqlCommandType sqlCommandType = context.getMappedStatement().getSqlCommandType();
        SqlType sqlType = sqlTypeConvert(sqlCommandType);
        if (sqlType == SqlType.UNKNOWN) {
            return SqlType.UNKNOWN;
        }
        // check is soft delete
        if (sqlType == SqlType.UPDATE && isSoftDelete(context)) {
            sqlType = SqlType.SOFT_DELETE;
        }
        return sqlType;
    }


    @Override
    protected boolean needLog(ParseContext context) {
        // If the configuration contains the current table name, need to record the log
        List<String> logTables = context.getOpLogConfig().getLogTables();
        String sql = context.getBoundSql().getSql();
        SqlMetaData sqlMetaData = context.getSqlMetaData();
        String tableName = SqlParseUtil.parseTableName(sql, sqlMetaData.getSqlType());
        sqlMetaData.setSql(sql);
        sqlMetaData.setTableName(tableName);
        return logTables.stream().anyMatch(e -> e.toUpperCase().equals(tableName));
    }

    @Override
    protected SqlMetaData doParseData(ParseContext context) {
        MybatisSqlMetaData sqlMetaData = (MybatisSqlMetaData) context.getSqlMetaData();
        SqlType sqlType = sqlMetaData.getSqlType();
        MybatisParseDataProcessor mybatisParseDataProcessor =
                MybatisParseDataProcessorProvider.mybatisParseDataProcessor(sqlType);
        List<Map<String, Object>> beforeData = mybatisParseDataProcessor.parseBeforeData(context);
        sqlMetaData.setBeforeData(beforeData);
        sqlMetaData.setAfterDataGetCallBack(() -> {
            List<Map<String, Object>> afterData = mybatisParseDataProcessor.parseAfterData(context);
            sqlMetaData.setAfterData(afterData);
        });
        return sqlMetaData;
    }


    /**
     * Sql type convert
     */
    private SqlType sqlTypeConvert(SqlCommandType sqlCommandType) {
        // insert into ..  update ...  delete
        switch (sqlCommandType) {
            case INSERT:
                return SqlType.INSERT;
            case DELETE:
                return SqlType.DELETE;
            case UPDATE:
                return SqlType.UPDATE;
            default:
                return SqlType.UNKNOWN;
        }
    }

    /**
     * check is soft delete by config
     *
     * @param context the context
     * @return result
     */
    private boolean isSoftDelete(ParseContext context) {
        String sql = context.getBoundSql().getSql();
        OpLogConfig opLogConfig = context.getOpLogConfig();
        String softDeleteColumn = opLogConfig.getSoftDeleteColumn();
        String softDeleteColumnDeleteValue = opLogConfig.getSoftDeleteColumnDeleteValue();
        boolean notConfig = StringUtil.isEmpty(softDeleteColumn) || StringUtil.isEmpty(softDeleteColumnDeleteValue);
        if (notConfig) {
            return false;
        }
        String formatSql = sql.replaceAll("[\\s\\t\\n\\r]", "").replaceAll(" ", "").toUpperCase();
        List<String> checkSoftDeleteStrList = getCheckSoftDeleteStrList(softDeleteColumn, softDeleteColumnDeleteValue);
        boolean anyMatch = checkSoftDeleteStrList.stream().anyMatch(formatSql::contains);
        // when the sql contains the config deleteColumn=deleteColumnValue str,means is soft delete
        if (anyMatch) {
            return true;
        }
        return doCheckSoftDeleteBySqlParams(context, softDeleteColumn, softDeleteColumnDeleteValue);
    }

    /**
     * get check soft delete str list
     *
     * @param column      column
     * @param columnValue columnValue
     * @return list result
     */
    private List<String> getCheckSoftDeleteStrList(String column, String columnValue) {
        column = ConverterUtil.camelToUnderline(column);
        column = column.replaceAll(" ", "").toUpperCase();
        columnValue = columnValue.replaceAll(" ", "").toUpperCase();
        List<String> result = CollectionsUtil.arrayList();
        if ("TRUE".equals(columnValue) || "FALSE".equals(columnValue)) {
            result.add((column + "=" + columnValue).toUpperCase());
            result.add((column + "=" + ("TRUE".equals(columnValue) ? 1 : 0)).toUpperCase());
        } else {
            result.add((column + "=" + columnValue).toUpperCase());
        }
        return result;
    }

    /**
     * do check is soft delete
     * when the sql params values contains the config deleteColumn and is deleteValue means is soft delete
     *
     * @see OpLogConfig
     */
    private boolean doCheckSoftDeleteBySqlParams(ParseContext context, String column, String columnValue) {
        BoundSql boundSql = context.getBoundSql();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        MetaObject metaObject = context.getMappedStatement().getConfiguration().newMetaObject(parameterObject);
        return parameterMappings.stream().anyMatch(parameterMapping -> {
            String propertyName = parameterMapping.getProperty();
            String helperStr = propertyName;
            if (propertyName.contains(".")) {
                helperStr = propertyName.substring(propertyName.lastIndexOf('.') + 1);
            }
            if (helperStr.equals(column)) {
                Object value = metaObject.getValue(propertyName);
                if (value instanceof String) {
                    return columnValue.equals(value);
                } else if (value instanceof Integer) {
                    return value.equals(Integer.parseInt(columnValue));
                } else if (value instanceof Boolean) {
                    return Boolean.parseBoolean(columnValue) == (Boolean) value;
                } else {
                    return false;
                }
            }
            return false;
        });
    }
}
