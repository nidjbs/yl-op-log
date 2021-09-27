package com.hyl.op.log.core;

import com.hyl.op.log.annotations.OpLogGlobal;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;

/**
 * @author huayuanlin
 * @date 2021/08/07 21:38
 * @desc the class desc
 */
public class OpLogGlobalAopProxyCreator extends AbstractOpLogAopProxyCreator {


    public OpLogGlobalAopProxyCreator(MethodInterceptor interceptor) {
        super(interceptor);
    }

    @Override
    protected Class<? extends Annotation> aopOpLogAnnotation() {
        return OpLogGlobal.class;
    }
}
