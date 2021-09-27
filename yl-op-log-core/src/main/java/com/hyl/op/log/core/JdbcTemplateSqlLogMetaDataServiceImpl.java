package com.hyl.op.log.core;

import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.enums.OpLogStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

/**
 * @author huayuanlin
 * @date 2021/06/20 20:50
 * @desc the class desc
 */
public class JdbcTemplateSqlLogMetaDataServiceImpl extends BaseSqlLogMetaDataService {

    @Resource
    @Qualifier("opLogJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(OpLogMetaDataDO opLogMetaDataDO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    OpLogConstant.META_DATA_INSERT_SQL, new String[]{"id"});
            preparedStatement.setString(1, opLogMetaDataDO.getTraceId());
            preparedStatement.setString(2, opLogMetaDataDO.getOpId());
            preparedStatement.setString(3, opLogMetaDataDO.getBizId());
            preparedStatement.setString(4, opLogMetaDataDO.getOpType());
            preparedStatement.setInt(5, opLogMetaDataDO.getStatus());
            preparedStatement.setString(6, opLogMetaDataDO.getMetaData());
            preparedStatement.setString(7, opLogMetaDataDO.getBizCode());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<OpLogMetaDataDO> listNotProcessor() {
        return jdbcTemplate.queryForList(OpLogConstant.META_DATA_SELECT_SQL, OpLogMetaDataDO.class);
    }

    @Override
    public boolean updateStatus(Long opLogId, OpLogStatus opLogStatus) {
        return jdbcTemplate.update(OpLogConstant.META_DATA_UPDATE_STATUS_SQL, opLogStatus.getId(), opLogId) > 0;
    }

    @Override
    public boolean updateStatusToProcessing(Long opLogId) {
        return jdbcTemplate.update(OpLogConstant.META_DATA_SMART_UPDATE_SQL, opLogId) > 0;
    }


}
