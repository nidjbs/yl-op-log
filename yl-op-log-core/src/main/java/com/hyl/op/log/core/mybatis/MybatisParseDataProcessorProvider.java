package com.hyl.op.log.core.mybatis;

import com.hyl.op.log.core.mybatis.processor.DeleteMybatisOpLogInterceptorProcessor;
import com.hyl.op.log.core.mybatis.processor.InsertMybatisOpLogInterceptorProcessor;
import com.hyl.op.log.core.mybatis.processor.SoftDeleteMybatisOpLogInterceptorProcessor;
import com.hyl.op.log.core.mybatis.processor.UpdateMybatisOpLogInterceptorProcessor;
import com.hyl.op.log.enums.SqlType;
import com.hyl.op.log.exception.FrameworkException;

/**
 * @author huayuanlin
 * @date 2021/06/16 11:28
 * @desc the class desc
 */
public class MybatisParseDataProcessorProvider {

    private MybatisParseDataProcessorProvider() {
        throw new UnsupportedOperationException();
    }

    /**
     * get mybatis op log interceptor processor
     *
     * @param sqlType sql type
     * @return interceptor processor
     */
    public static MybatisParseDataProcessor mybatisParseDataProcessor(SqlType sqlType) {
        switch (sqlType) {
            case INSERT:
                return InsertMybatisOpLogInterceptorProcessor.getInstance();
            case DELETE:
                return DeleteMybatisOpLogInterceptorProcessor.getInstance();
            case SOFT_DELETE:
                return SoftDeleteMybatisOpLogInterceptorProcessor.getInstance();
            case UPDATE:
                return UpdateMybatisOpLogInterceptorProcessor.getInstance();
            default:
                throw new FrameworkException("not found op log interceptor processor");
        }
    }

}
