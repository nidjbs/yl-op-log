package com.hyl.op.log.core.support;

import com.hyl.op.log.core.OpLogContext;
import com.hyl.op.log.core.OpLogContextProvider;
import com.hyl.op.log.util.AssertUtil;
import com.hyl.op.log.util.StringUtil;

/**
 * @author huayuanlin
 * @date 2021/08/02 14:07
 * @desc the class desc
 */
public class ManualBizTraceSupport {

    private ManualBizTraceSupport() {
        throw new UnsupportedOperationException();
    }

    /**
     * set biz id for log op log
     *
     * @param bizId bizId, support Integer Long String
     */
    public static void setBizId(Object bizId) {
        AssertUtil.notNull(bizId, "bizId param");
        OpLogContext opLogContext = OpLogContextProvider.opLogContext();
        AssertUtil.notNull(opLogContext, "op log context");
        opLogContext.getOpLogGlobalContext().setBizId(StringUtil.parseStr(bizId));
    }

    /**
     * set op user id for log op log
     *
     * @param opId opId, support:Integer Long String
     */
    public static void setOpId(Object opId) {
        AssertUtil.notNull(opId, "opId param");
        OpLogContext opLogContext = OpLogContextProvider.opLogContext();
        AssertUtil.notNull(opLogContext, "op log context");
        opLogContext.getOpLogGlobalContext().setOpId(StringUtil.parseStr(opId));
    }


    /**
     * set traceId for log op log
     *
     * @param traceId traceId, support:Integer Long String
     */
    public static void setTraceId(Object traceId) {
        AssertUtil.notNull(traceId, "traceId param");
        OpLogContext opLogContext = OpLogContextProvider.opLogContext();
        AssertUtil.notNull(opLogContext, "op log context");
        opLogContext.getOpLogGlobalContext().setTraceId(StringUtil.parseStr(traceId));
    }

}
