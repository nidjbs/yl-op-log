package com.hyl.op.log.util;

import org.springframework.cglib.beans.BeanMap;

import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/07/04 15:24
 * @desc the class desc
 */
public class BeanUtil {

    private BeanUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * bean to map
     *
     * @param bean bean not null
     * @return map
     */
    public static Map<String, Object> toMap(Object bean) {
        if (bean == null) {
            return MapsUtil.hashMap();
        }
        Map<String, Object> result = MapsUtil.hashMap();
        BeanMap beanMap = BeanMap.create(bean);
        for (Object key : beanMap.keySet()) {
            Object value = beanMap.get(key);
            if (value != null) {
                result.put((String) key, value);
            }
        }
        return result;
    }
}
