package com.hyl.op.log.core.mapping;

import com.hyl.op.log.core.OpLogMetaDataWrapper;

/**
 * @author huayuanlin
 * @date 2021/06/21 00:03
 * @desc the interface desc
 */
public abstract class AbstractOpLogMappingTask<T> {

    private final T t;

    protected AbstractOpLogMappingTask(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    /**
     * convert
     *
     * @return meta data wrapper
     */
    protected abstract OpLogMetaDataWrapper convert();

}
