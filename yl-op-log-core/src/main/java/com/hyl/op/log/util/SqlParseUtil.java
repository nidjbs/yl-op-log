package com.hyl.op.log.util;

import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.enums.SqlType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/08/04 14:49
 * @desc the class desc
 */
public class SqlParseUtil {


    private static final Map<SqlType, String> PARSE_TABLE_NAME_SEPARATOR = new HashMap<>(16);

    static {
        PARSE_TABLE_NAME_SEPARATOR.put(SqlType.INSERT, "INSERT INTO ");
        PARSE_TABLE_NAME_SEPARATOR.put(SqlType.UPDATE, "UPDATE ");
        PARSE_TABLE_NAME_SEPARATOR.put(SqlType.DELETE, "DELETE FROM ");
        PARSE_TABLE_NAME_SEPARATOR.put(SqlType.SOFT_DELETE, "UPDATE ");
        PARSE_TABLE_NAME_SEPARATOR.put(SqlType.UNKNOWN, "");
    }

    private SqlParseUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * parse table name from sql
     *
     * @param sql     sql
     * @param sqlType sqlType
     * @return table name
     */
    public static String parseTableName(String sql, SqlType sqlType) {
        AssertUtil.notNull(sql, "sql");
        AssertUtil.notNull(sqlType, "sql type enum");
        String separatorKey = PARSE_TABLE_NAME_SEPARATOR.get(sqlType);
        String toUpperCase = sql.toUpperCase();
        String format = toUpperCase.replaceAll("[\\s\\t\\n\\r]", " ")
                .replaceAll("\\s+", " ");
        format = format.substring(toUpperCase.indexOf(separatorKey) + separatorKey.length());
        return format.substring(0, format.indexOf(" ")).replaceAll("`", "")
                .replace("(", "").replace(")", "");
    }

    /**
     * update sql to select
     * ex: update A set a = ?, b = ? where id = ?  => select a,b from A where id = ?
     *
     * @param updateSql src sql
     * @param tableName tableName
     * @return select sql
     */
    public static String formatUpdateToSelectSql(String updateSql, String tableName) {
        // get param sql : where id = ?
        String selectSqlParam = formatToSelectSqlParam(updateSql);
        // Remove update
        String var1 = removeStr(formatSql(updateSql), OpLogConstant.UPDATE_SPACE);
        // remove table name
        String tableNameUpperCase = tableName.toUpperCase();
        String var2 = removeStr(var1, tableNameUpperCase);
        // remove set
        String var3 = removeStr(var2, OpLogConstant.SET_SPACE);
        // => a = ? ,
        String var4 = var3.substring(0, var3.lastIndexOf(OpLogConstant.WHERE));
        String var5 = var4.replaceAll(" ", "")
                .replaceAll("\\n", "").replaceAll("=\\?", " ");
        return String.format(OpLogConstant.SELECT_SQL_FORMAT, var5, tableNameUpperCase, selectSqlParam);
    }


    /**
     * format to select sql params sql
     * ex: update A set a = ?, b = ? where id = ?  => id = ?
     * applies to conditional SQL
     *
     * @param sql sql
     * @return select sql params sql
     */
    public static String formatToSelectSqlParam(String sql) {
        String formatSql = formatSql(sql);
        if (formatSql.contains(OpLogConstant.WHERE_WITH_SPACE)) {
            return formatSql.substring(formatSql.lastIndexOf(OpLogConstant.WHERE_WITH_SPACE) + OpLogConstant.WHERE_WITH_SPACE.length());
        } else {
            return " ";
        }
    }

    private static String formatSql(String sql) {
        return sql.toUpperCase().replaceAll("[\\s\\t\\n\\r]", " ")
                .replaceAll("\\s+", " ");
    }

    private static String removeStr(String src, String target) {
        return src.substring(src.indexOf(target) + target.length());
    }

}
