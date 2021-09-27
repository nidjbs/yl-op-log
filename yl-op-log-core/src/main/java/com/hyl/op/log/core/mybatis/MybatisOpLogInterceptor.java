package com.hyl.op.log.core.mybatis;

import com.hyl.op.log.common.SmartOptional;
import com.hyl.op.log.core.*;
import com.hyl.op.log.exception.FrameworkException;
import com.hyl.op.log.util.SafeUtil;
import com.hyl.op.log.util.SpringBeanUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * @author huayuanlin
 * @date 2021/06/10 20:18
 * @desc the class desc
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisOpLogInterceptor implements Interceptor, OrmOpLogInterceptor {


    @Override
    public Object intercept(Invocation invocation) throws Exception {
        boolean curOpenRecordOpLog = OpLogContextProvider.curOpenRecordOpLog();
        // Currently not belonging to the environment where operation records need to be recorded
        if (!curOpenRecordOpLog) {
            return invocation.proceed();
        }
        Object obj = PluginUtil.getTarget(invocation.getTarget());
        OpLogContext opLogContext = OpLogContextProvider.opLogContext();
        if (obj instanceof Executor) {
            Object result = invocation.proceed();
            SafeUtil.safeExecute(() -> {
                if (!opLogContext.isRunAbleTag()) {
                    opLogContext.reset();
                    return;
                }
                // sql is execute,do the call back and get after data
                List<SqlMetaData> sqlMetaDataList = opLogContext.getSqlMetaDataList();
                MybatisSqlMetaData curSqlMetaData = (MybatisSqlMetaData) opLogContext.getCurSqlMetaData();
                SmartOptional.ofNullable(curSqlMetaData).ifPresent(MybatisSqlMetaData::doCallBack);
                sqlMetaDataList.add(curSqlMetaData);
                opLogContext.reset();
            }, Throwable.class);
            return result;
        } else if (obj instanceof StatementHandler) {
            SafeUtil.safeExecute(() -> {
                // parse meta data
                OpLogInterceptorProcessor processor =
                        SpringBeanUtil.getBeanByType(OpLogInterceptorProcessor.class);
                SmartOptional.ofNullable(processor.parseMetaData(invocation)).ifPresent(opLogContext::setCurSqlMetaData);
            }, () -> opLogContext.setRunAbleTag(false), FrameworkException.class);
            return invocation.proceed();
        }
        throw new FrameworkException("unknown exception");
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
