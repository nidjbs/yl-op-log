package com.hyl.op.log.common;

import com.hyl.op.log.enums.OpLogType;
import com.hyl.op.log.enums.SqlType;

import java.util.function.Function;

/**
 * @author huayuanlin
 * @date 2021/06/13 00:14
 * @desc the class desc
 */
public class OpLogConstant {

    public static final String SELECT_SQL_FORMAT = "SELECT %s FROM %s WHERE %s";
    public static final String META_DATA_INSERT_SQL = "INSERT INTO `op_log_meta_data` (`trace_id`,`op_id`,`biz_id`,`biz_desc`,`status`,`meta_data`,`biz_code`) VALUES (?,?,?,?,?,?,?)";
    public static final String META_DATA_SELECT_SQL = "SELECT `trace_id` AS traceId,`op_id` AS opId,`biz_id` AS bizId,`biz_desc` AS bizDesc,`status`,`meta_data` AS metaData FROM `op_log_meta_data` WHERE `status` = 0";
    public static final String META_DATA_SMART_UPDATE_SQL = "UPDATE `op_log_meta_data` SET `status` = 1 WHERE `id` = ? AND `status` = 0";
    public static final String META_DATA_UPDATE_STATUS_SQL = "UPDATE `op_log_meta_data` SET `status` = ? WHERE `id` = ?";
    public static final String SET = "SET";
    public static final String SET_SPACE = "SET ";
    public static final String UPDATE = "UPDATE";
    public static final String UPDATE_SPACE = "UPDATE ";
    public static final String WHERE = "WHERE";
    public static final String WHERE_WITH_SPACE = " WHERE ";
    public static final String CONTEXT_HANDLER_KEY = "OP_LOG_CONTEXT_KEY";


    /*** the mapping bean name prefix */
    public static final String PREFIX = "mappingDo.";

    /*** convert sql type to op log type */
    public static final Function<SqlType, OpLogType> SQL_TYPE_CONVERT_FUN = sqlType -> {
        switch (sqlType) {
            case INSERT:
                return OpLogType.CREATE;
            case DELETE:
            case SOFT_DELETE:
                return OpLogType.DELETE;
            default:
                return OpLogType.UPDATE;
        }
    };


}
