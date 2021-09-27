package com.hyl.test.single.demo.log;

import com.hyl.op.log.core.support.BizTraceSupport;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author huayuanlin
 * @date 2021/09/27 00:31
 * @desc the class desc
 */
@Component
public class BizLogSupport implements BizTraceSupport {

    /**
     * 操作人员id，这里一般都是从你的userToken中可以拿到
     */
    @Override
    public String opId() {
        return String.valueOf(new Random().nextInt(100000000));
    }

    /**
     * 业务编码，可区分开业务
     */
    @Override
    public String bizCode() {
        return "user_mgmt";
    }

    /**
     * 业务Id，可选，一般不会在此统一设置
     */
    @Override
    public String bizId() {
        return String.valueOf(new Random().nextInt(100000000));
    }

    /**
     * 链路Id，可选，一般微服务中才有
     */
    @Override
    public String traceId() {
        return null;
    }
}
