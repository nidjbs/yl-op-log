package com.hyl.op.log.core.web;

import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.core.OpLogGlobalContext;
import com.hyl.op.log.core.OpLogGlobalContextHolder;
import com.hyl.op.log.util.JsonUtil;
import com.hyl.op.log.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author huayuanlin
 * @date 2021/08/06 17:53
 * @desc the class desc
 */
public class OpLogContextFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contextJson = ((HttpServletRequest) request).getHeader(OpLogConstant.CONTEXT_HANDLER_KEY);
        if (StringUtil.isNotEmpty(contextJson)) {
            String decode = URLDecoder.decode(contextJson, "UTF-8");
            OpLogGlobalContext opLogGlobalContext = JsonUtil.fromJson(decode, OpLogGlobalContext.class);
            try {
                OpLogGlobalContextHolder.init(opLogGlobalContext);
                chain.doFilter(request, response);
            } finally {
                OpLogGlobalContextHolder.clean();
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
