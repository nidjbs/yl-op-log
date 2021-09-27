package com.hyl.op.log.core.mybatis;

import com.hyl.op.log.exception.FrameworkException;
import com.hyl.op.log.util.CollectionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/08/04 15:11
 * @desc the interface desc
 */
public abstract class MybatisParseDataProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MybatisParseDataProcessor.class);

    public List<Map<String, Object>> parseBeforeData(ParseContext context){
        return CollectionsUtil.arrayList();
    }

    public List<Map<String, Object>> parseAfterData(ParseContext context){
        return CollectionsUtil.arrayList();
    }


    /**
     * do select
     *
     * @param connection jdbc connection
     * @param sql        prepare sql
     * @param params     param values list
     * @return do select result
     */
    protected List<Map<String, Object>> doSelect(Connection connection, String sql, List<Object> params) {
        LOGGER.debug(String.format("do select before or after data,sql: %s,params:%s", sql, Arrays.toString(params.toArray())));
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            List<Map<String, Object>> result = CollectionsUtil.arrayList();
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                while (resultSet.next()) {
                    Map<String, Object> data = new LinkedHashMap<>(16);
                    for (int i = 1; i <= columnCount; i++) {
                        Object columnValue = this.getColumnValue(resultSet, resultSetMetaData, i);
                        data.put(resultSetMetaData.getColumnLabel(i).toLowerCase(), columnValue);
                    }
                    result.add(data);
                }
                return result;
            }
        } catch (Exception e) {
            throw new FrameworkException("do select before or after log sql error", e);
        }
    }


    /**
     * get column value
     *
     * @param resultSet  resultSet
     * @param i          result index
     * @return column value
     * @throws SQLException exception
     */
    private Object getColumnValue(ResultSet resultSet, ResultSetMetaData rs, int i)
            throws SQLException {
        Object value;
        // process time type
        int columnType = rs.getColumnType(i);
        if (Types.DATE == columnType) {
            Date date = resultSet.getDate(rs.getColumnLabel(i));
            value = date == null ? null : new java.util.Date(date.getTime());
        } else if (Types.TIME == columnType || Types.TIME_WITH_TIMEZONE == columnType) {
            Time time = resultSet.getTime(i);
            value = time == null ? null : new java.util.Date(time.getTime());
        } else if (Types.TIMESTAMP == columnType || Types.TIMESTAMP_WITH_TIMEZONE == columnType) {
            Timestamp timestamp = resultSet.getTimestamp(i);
            value = timestamp == null ? null : new java.util.Date(timestamp.getTime());
        } else {
            // todo Add supported types, not all types need to be supported.
            value = resultSet.getObject(i);
        }
        return value;
    }



}
