package com.hyl.op.log.core;

import com.hyl.op.log.annotations.OpLogGlobal;
import com.hyl.op.log.common.ObjBuilder;
import com.hyl.op.log.config.OpLogGlobalConfig;
import com.hyl.op.log.core.support.BizTraceSupport;
import com.hyl.op.log.util.AssertUtil;
import com.hyl.op.log.util.SpringBeanUtil;
import com.hyl.op.log.util.SpringUtil;
import com.hyl.op.log.util.StringUtil;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

/**
 * @author huayuanlin
 * @date 2021/08/07 22:03
 * @desc the class desc
 */
public class OpLogGlobalAopMethodInterceptor extends OpLogMethodInterceptor {


    @Override
    protected Class<? extends Annotation> annotation() {
        return OpLogGlobal.class ;
    }

    @Override
    protected Object doIntercept(MethodInvocation invocation) throws Throwable {
        try {
            // try init global context
            initGlobalContext(invocation);
            return invocation.proceed();
        }finally {
            // clean context
            OpLogGlobalContextHolder.clean();
        }
    }

    /**
     * Try to initialize the global context.
     * prioritized: BizTraceSupport < OpLogGlobalConfig < the OpLogGlobal annotation
     *
     * @see BizTraceSupport
     * @see OpLogGlobalConfig
     * @see OpLogGlobal
     * @param invocation method invocation
     */
    private void initGlobalContext(MethodInvocation invocation) {
        String bizCode = null;
        String bizId = null;
        String traceId = null;
        String opId = null;
        BizTraceSupport bizTraceSupport = SpringBeanUtil.safeGetBeanByType(BizTraceSupport.class);
        if (bizTraceSupport != null) {
            bizCode = bizTraceSupport.bizCode();
            bizId = bizTraceSupport.bizId();
            traceId = bizTraceSupport.traceId();
            opId = bizTraceSupport.opId();
        }
        OpLogGlobalConfig logGlobalConfig = SpringBeanUtil.getBeanByType(OpLogGlobalConfig.class);
        if (StringUtil.isNotEmpty(logGlobalConfig.getBizCode())) {
            bizCode = logGlobalConfig.getBizCode();
        }
        OpLogGlobal opLogGlobal = invocation.getMethod().getAnnotation(OpLogGlobal.class);
        AssertUtil.notNull(opLogGlobal, "opLogGlobal annotation");
        String bizIdEl = opLogGlobal.bizIdEl();
        Object elResult = SpringUtil.doExecuteEl(bizIdEl, invocation.getMethod(), invocation.getArguments());
        String parseBizId = StringUtil.parseStr(elResult);
        if (StringUtil.isNotEmpty(parseBizId)) {
            bizId = parseBizId;
        }
        String opType = opLogGlobal.opType();
        OpLogGlobalContext opLogGlobalContext = ObjBuilder.create(OpLogGlobalContext::new)
                .of(OpLogGlobalContext::setBizCode, bizCode)
                .of(OpLogGlobalContext::setOpType, opType)
                .of(OpLogGlobalContext::setBizId, bizId)
                .of(OpLogGlobalContext::setTraceId,traceId)
                .of(OpLogGlobalContext::setOpId,opId)
                .build();
        OpLogGlobalContextHolder.init(opLogGlobalContext);
    }
}
