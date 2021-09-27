package com.hyl.op.log.core;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author huayuanlin
 * @date 2021/09/12 23:32
 * @desc the class desc
 */
public abstract class OpLogMethodInterceptor implements MethodInterceptor {

    @Override
    public final Object invoke(MethodInvocation invocation) throws Throwable {
        if (!containAnnotation(invocation)) {
            return invocation.proceed();
        } else {
            return doIntercept(invocation);
        }
    }

    private boolean containAnnotation(MethodInvocation invocation) {
        return obtainCurMethod(invocation).isPresent();
    }

    protected Optional<Method> obtainCurMethod(MethodInvocation invocation) {
        // may be is a abstract method
        Method method = invocation.getMethod();
        if (method.getAnnotation(annotation()) != null) {
            return Optional.of(method);
        }
        Object targetObj = invocation.getThis();
        Method targetMethod = ReflectionUtils.findMethod(targetObj.getClass(),
                method.getName(), method.getParameterTypes());
        if (targetMethod != null && targetMethod.getAnnotation(annotation()) != null) {
            return Optional.of(targetMethod);
        }
        return Optional.empty();
    }


    /**
     * annotation class on interception method
     *
     * @return annotation
     */
    protected abstract Class<? extends Annotation> annotation();

    /**
     * do intercept
     *
     * @return result
     * @param invocation invocation
     * @throws Throwable exx
     */
    protected abstract Object doIntercept(MethodInvocation invocation) throws Throwable;
}
