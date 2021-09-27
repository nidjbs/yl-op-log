package com.hyl.op.log.core.mybatis.processor;

import com.hyl.op.log.annotations.OpLogDbField;
import com.hyl.op.log.core.mybatis.MybatisParseDataProcessor;
import com.hyl.op.log.core.mybatis.ParseContext;
import com.hyl.op.log.util.CollectionsUtil;
import com.hyl.op.log.util.ConverterUtil;
import com.hyl.op.log.util.MapsUtil;
import org.apache.ibatis.mapping.BoundSql;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author huayuanlin
 * @date 2021/06/15 22:49
 * @desc the insert MybatisOpLogInterceptorProcessor
 */
public class InsertMybatisOpLogInterceptorProcessor extends MybatisParseDataProcessor {

    private InsertMybatisOpLogInterceptorProcessor() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> parseAfterData(ParseContext context) {
        BoundSql boundSql = context.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();
        List<Map<String, Object>> resultList = CollectionsUtil.arrayList();
        // batch insert
        if (parameterObject instanceof Map) {
            Set<List<Object>> temp = CollectionsUtil.hashSet();
            ((Map<String, Object>) parameterObject).forEach((k, v) -> {
                if (v instanceof List) {
                    temp.add((List<Object>) v);
                } else {
                    LOGGER.warn("op log batch insert only support list params");
                }
            });
            temp.stream().filter(Objects::nonNull).forEach(e -> resultList.add(convertToMap(e)));
        } else {
            // single insert
            resultList.add(convertToMap(parameterObject));
        }
        return resultList;
    }


    /**
     * convert to map（process the annotation @OpLogDbField）
     *
     * @param obj obj
     * @return result
     * @see OpLogDbField
     */
    private Map<String, Object> convertToMap(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        Map<String, Object> result = MapsUtil.hashMap();
        for (Field field : declaredFields) {
            OpLogDbField opLogDbField = field.getAnnotation(OpLogDbField.class);
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                LOGGER.error("reflection invoke fail", e);
            }
            Optional.ofNullable(value).ifPresent(v -> {
                if (opLogDbField != null) {
                    result.put(opLogDbField.value(), v);
                } else {
                    result.put(ConverterUtil.camelToUnderline(field.getName()), v);
                }
            });
        }
        return result;
    }


    public static InsertMybatisOpLogInterceptorProcessor getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final InsertMybatisOpLogInterceptorProcessor
                INSTANCE = new InsertMybatisOpLogInterceptorProcessor();
    }
}
