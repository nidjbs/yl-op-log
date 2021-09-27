package com.hyl.op.log.core.support;

import com.hyl.op.log.config.OpLogGlobalConfig;

/**
 * @author huayuanlin
 * @date 2021/06/17 10:29
 * @desc the interface desc
 */
public interface BizTraceSupport {

    /**
     * optional
     *
     * @return traceId str
     */
    default String traceId() {
        return "";
    }

    /**
     * optional
     *
     * @return opId str
     */
    default String opId()  {return "";}

    /**
     * optional
     *
     * @return bizId str
     */
    default String bizId() {return "";}

    /**
     * optional
     * but, the OpLogGlobalConfig.bizCode higher priority
     *
     * @return bizCode str
     * @see OpLogGlobalConfig
     */
    default String bizCode() {return "";}

}
