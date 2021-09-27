package com.hyl.op.log.core;

import com.hyl.op.log.common.ObjBuilder;
import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.enums.OpLogStatus;
import com.hyl.op.log.util.JsonUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author huayuanlin
 * @date 2021/06/20 20:52
 * @desc the class desc
 */
public abstract class BaseSqlLogMetaDataService implements ISqlLogMetaDataService, IDbInsertService<OpLogMetaDataDO> {

    @Override
    public OpLogMetaDataDO doConvert() {
        OpLogContext opLogContext = OpLogContextProvider.opLogContext();
        OpLogGlobalContext bizTrace = opLogContext.getOpLogGlobalContext();
        List<OpLogContent> opLogContents = opLogContext.getSqlMetaDataList()
                .stream().filter(Objects::nonNull).map(this::logContentConvert).collect(Collectors.toList());
        return ObjBuilder.create(OpLogMetaDataDO::new)
                .of(OpLogMetaDataDO::setBizId, bizTrace.getBizId())
                .of(OpLogMetaDataDO::setOpId, bizTrace.getOpId())
                .of(OpLogMetaDataDO::setStatus, OpLogStatus.INIT.getId())
                .of(OpLogMetaDataDO::setTraceId, bizTrace.getTraceId())
                .of(OpLogMetaDataDO::setMetaData, JsonUtil.toJsonString(opLogContents))
                .of(OpLogMetaDataDO::setBizCode, bizTrace.getBizCode())
                .of(OpLogMetaDataDO::setOpType,bizTrace.getOpType())
                .build();
    }

    /**
     * logContentConvert
     *
     * @param sqlMetaData sqlMetaData
     * @return content
     */
    private OpLogContent logContentConvert(SqlMetaData sqlMetaData) {
        return ObjBuilder.create(OpLogContent::new)
                .of(OpLogContent::setTableName, sqlMetaData.getTableName())
                .of(OpLogContent::setType, OpLogConstant.SQL_TYPE_CONVERT_FUN.apply(sqlMetaData.getSqlType()).getId())
                .of(OpLogContent::setBefore, sqlMetaData.getBeforeData())
                .of(OpLogContent::setAfter, sqlMetaData.getAfterData())
                .build();
    }

    @Override
    public void insertBatch(List<OpLogMetaDataDO> list) {

    }
}
