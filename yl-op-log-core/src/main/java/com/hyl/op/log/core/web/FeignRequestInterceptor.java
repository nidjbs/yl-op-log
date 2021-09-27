package com.hyl.op.log.core.web;

import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.core.OpLogGlobalContext;
import com.hyl.op.log.core.OpLogGlobalContextHolder;
import com.hyl.op.log.exception.FrameworkException;
import com.hyl.op.log.util.JsonUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author huayuanlin
 * @date 2021/08/06 18:40
 * @desc Used to transfer context between services
 */
public class FeignRequestInterceptor implements RequestInterceptor, RpcRequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        OpLogGlobalContext opLogGlobalContext = OpLogGlobalContextHolder.getContext();
        if (opLogGlobalContext != null) {
            try {
                String encode = URLEncoder.encode(JsonUtil.toJsonString(opLogGlobalContext), "UTF-8");
                requestTemplate.header(OpLogConstant.CONTEXT_HANDLER_KEY, encode);
            } catch (UnsupportedEncodingException e) {
                // ignore
                throw new FrameworkException("unsupportedEncodingException");
            }
        }
    }
}
