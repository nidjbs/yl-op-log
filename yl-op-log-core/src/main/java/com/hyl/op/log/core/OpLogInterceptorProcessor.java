package com.hyl.op.log.core;


/**
 * @author huayuanlin
 * @date 2021/06/14 20:23
 * @desc the interface desc
 */
public interface OpLogInterceptorProcessor {

    /**
     * parse meta data
     *
     * @param invocation interceptor param
     * @return meta data
     */
    SqlMetaData parseMetaData(Object invocation);

}
