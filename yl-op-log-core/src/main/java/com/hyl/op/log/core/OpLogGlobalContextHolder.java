package com.hyl.op.log.core;

import com.hyl.op.log.annotations.OpLogGlobal;
import com.hyl.op.log.common.SimpleCallBack;
import com.hyl.op.log.util.AssertUtil;

import java.util.function.Supplier;

/**
 * @author huayuanlin
 * @date 2021/08/06 18:43
 * @desc the class desc
 */
public class OpLogGlobalContextHolder {

    private static final ThreadLocal<OpLogGlobalContext> GLOBAL_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static OpLogGlobalContext getContext() {
        return GLOBAL_CONTEXT_THREAD_LOCAL.get();
    }


    public static void init(OpLogGlobalContext context) {
        GLOBAL_CONTEXT_THREAD_LOCAL.set(context);
    }

    public static void clean() {
        GLOBAL_CONTEXT_THREAD_LOCAL.remove();
    }

    /**
     * When you want to call asynchronously in the method annotated by
     * com.yqn.op.log.annotations.OpLogGlobal,you need to call the method to pass the context
     *
     * @see OpLogGlobal
     * @param contextSupplier context supplier,you can use OpLogGlobalContextHolder#getContext to get
     * @param callBack your async logic
     */
    public static void smartAsyncContext(Supplier<OpLogGlobalContext> contextSupplier, SimpleCallBack callBack) {
        AssertUtil.notNull(contextSupplier, "context supplier");
        try {
            init(contextSupplier.get());
            callBack.callBack();
        } finally {
            clean();
        }
    }

}
