package com.hyl.op.log.core;

import com.hyl.op.log.util.AssertUtil;

/**
 * @author huayuanlin
 * @date 2021/06/16 10:52
 * @desc the class desc
 */
public class OpLogContextProvider {

    private OpLogContextProvider() {
        throw new UnsupportedOperationException();
    }

    public static OpLogContext opLogContext() {
        OpLogContext opLogContext = OpLogAopMethodInterceptor.OP_LOG_CONTEXT.get();
        AssertUtil.notNull(opLogContext, "opLogContext");
        return opLogContext;
    }

    public static boolean curOpenRecordOpLog() {
        return OpLogAopMethodInterceptor.CUR_OPEN_OP_LOG.get();
    }

}
