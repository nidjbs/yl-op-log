package com.hyl.op.log.core;

import com.hyl.op.log.enums.OpLogStatus;

import java.util.List;

/**
 * @author huayuanlin
 * @date 2021/06/20 19:48
 * @desc the interface desc
 */
public interface ISqlLogMetaDataService {

    /**
     * covert to do log
     *
     * @return log do
     */
    OpLogMetaDataDO doConvert();

    /**
     * insert
     *
     * @param opLogMetaDataDO log Do
     * @return update status
     */
    Long insert(OpLogMetaDataDO opLogMetaDataDO);

    /**
     * list not processor log
     *
     * @return list log
     */
    List<OpLogMetaDataDO> listNotProcessor();

    /**
     * update opLogStatus
     *
     * @param opLogId  log id
     * @param opLogStatus  opLogStatus
     * @return update status
     */
    boolean updateStatus(Long opLogId, OpLogStatus opLogStatus);

    /**
     * update opLogStatus to processing
     *
     * @see OpLogStatus#PROCESSING
     *
     * @param opLogId  log id
     * @return update status
     */
    boolean updateStatusToProcessing(Long opLogId);

}
