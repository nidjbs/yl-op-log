package com.hyl.op.log.core.mapping;

import com.hyl.op.log.core.OpLogMetaDataWrapper;

/**
 * @author huayuanlin
 * @date 2021/06/20 23:25
 * @desc the interface desc
 */
public interface IOpLogMapping {

    /**
     * process
     *
     * @param opLogMetaDataWrapper wrapper
     */
    void process(OpLogMetaDataWrapper opLogMetaDataWrapper);

}
