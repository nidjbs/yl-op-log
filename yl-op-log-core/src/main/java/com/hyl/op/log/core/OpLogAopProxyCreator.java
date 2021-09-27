package com.hyl.op.log.core;

import com.hyl.op.log.annotations.OpLog;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;

/**
 * @author huayuanlin
 * @date 2021/08/07 21:19
 * @desc the class desc
 */
public class OpLogAopProxyCreator extends AbstractOpLogAopProxyCreator {


    public OpLogAopProxyCreator(MethodInterceptor interceptor) {
        super(interceptor);
    }

    @Override
    protected Class<? extends Annotation> aopOpLogAnnotation() {
        return OpLog.class;
    }
}
