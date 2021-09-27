package com.hyl.op.log.common;

/**
 * @author huayuanlin
 * @date 2021/06/17 15:19
 * @desc the interface desc
 */
@FunctionalInterface
public interface CallBack<P, R> {

    /**
     * call back method
     *
     * @param p prams
     * @return result
     */
    R call(P p);

}
