package com.hyl.op.log.core.mybatis.processor;

import com.hyl.op.log.core.mybatis.MybatisParseDataProcessor;
import com.hyl.op.log.core.mybatis.ParseContext;

import java.util.List;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/06/19 13:39
 * @desc the class desc
 */
public class SoftDeleteMybatisOpLogInterceptorProcessor extends MybatisParseDataProcessor {


    @Override
    public List<Map<String, Object>> parseBeforeData(ParseContext context) {
        return DeleteMybatisOpLogInterceptorProcessor.getInstance().parseBeforeData(context);
    }

    public static SoftDeleteMybatisOpLogInterceptorProcessor getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SoftDeleteMybatisOpLogInterceptorProcessor
                INSTANCE = new SoftDeleteMybatisOpLogInterceptorProcessor();
    }
}
