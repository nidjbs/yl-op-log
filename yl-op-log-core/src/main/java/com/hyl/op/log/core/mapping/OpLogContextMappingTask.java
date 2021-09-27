package com.hyl.op.log.core.mapping;

import com.hyl.op.log.common.ObjBuilder;
import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.core.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huayuanlin
 * @date 2021/06/21 00:10
 * @desc the class desc
 */
public class OpLogContextMappingTask extends AbstractOpLogMappingTask<OpLogContext> {

    /*** convert fun */
    private static final Function<SqlMetaData, OpLogContent> CONVERT_FUN = sqlMetaData -> {
        OpLogContent opLogContent = new OpLogContent();
        opLogContent.setTableName(sqlMetaData.getTableName());
        opLogContent.setType(OpLogConstant.SQL_TYPE_CONVERT_FUN.apply(sqlMetaData.getSqlType()).getId());
        opLogContent.setAfter(sqlMetaData.getAfterData());
        opLogContent.setBefore(sqlMetaData.getBeforeData());
        return opLogContent;
    };

    public OpLogContextMappingTask(OpLogContext opLogContext) {
        super(opLogContext);
    }

    @Override
    public OpLogMetaDataWrapper convert() {
        OpLogContext opLogContext = getT();
        OpLogGlobalContext opLogGlobalContext = opLogContext.getOpLogGlobalContext();
        Long opLogId = opLogContext.getOpLogId();
        List<OpLogContent> opLogContents = opLogContext.getSqlMetaDataList()
                .stream().map(CONVERT_FUN).collect(Collectors.toList());
        return ObjBuilder.create(OpLogMetaDataWrapper::new)
                .of(OpLogMetaDataWrapper::setOpId, opLogGlobalContext.getOpId())
                .of(OpLogMetaDataWrapper::setTraceId, opLogGlobalContext.getTraceId())
                .of(OpLogMetaDataWrapper::setOpType, opLogGlobalContext.getOpType())
                .of(OpLogMetaDataWrapper::setId, opLogId)
                .of(OpLogMetaDataWrapper::setOpLogContents, opLogContents)
                .build();
    }
}
