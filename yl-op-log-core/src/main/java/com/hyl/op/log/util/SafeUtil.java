package com.hyl.op.log.util;

import com.hyl.op.log.common.SimpleCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huayuanlin
 * @date 2021/08/04 10:58
 * @desc the class desc
 */
public class SafeUtil {

    private SafeUtil() {
        throw new UnsupportedOperationException();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SafeUtil.class);

    /**
     * Safe execution logic that catches specified exceptions (including subclass exceptions)
     *
     * @param logicCallBack logic
     * @param catchExe      specified exceptions(including subclass exceptions)
     */
    public static void safeExecute(SimpleCallBack logicCallBack, Class<? extends Throwable> catchExe) {
        safeExecute(logicCallBack, null, catchExe);
    }

    /**
     * Safe execution logic that catches specified exceptions (including subclass exceptions)
     *
     * @param logicCallBack logic
     * @param failCallBack when execute fail,execute the failCallBack logic
     * @param catchExe      specified exceptions(including subclass exceptions)
     */
    public static void safeExecute(SimpleCallBack logicCallBack, SimpleCallBack failCallBack,
                                   Class<? extends Throwable> catchExe) {
        AssertUtil.notNull(logicCallBack, "call back logic");
        AssertUtil.notNull(catchExe, "catchExe class");
        try {
            logicCallBack.callBack();
        } catch (Throwable e) {
            if (failCallBack != null) {
                failCallBack.callBack();
            }
            boolean assignable = catchExe.isAssignableFrom(e.getClass());
            if (assignable) {
                LOGGER.warn("safe logic error:", e);
            } else {
                throw e;
            }
        }
    }

}
