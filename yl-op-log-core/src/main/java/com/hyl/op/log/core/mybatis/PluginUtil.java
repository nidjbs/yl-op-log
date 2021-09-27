package com.hyl.op.log.core.mybatis;


import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;

/**
 * @author huayuanlin
 * @date 2021/06/13 00:12
 * @desc the class desc
 */
public class PluginUtil {

    private PluginUtil() {
        throw new UnsupportedOperationException();
    }

    private static final String H_TARGET = "h.target";

    /**
     * get meta target obj
     *
     * @param target target
     * @return meta target obj
     */
    @SuppressWarnings("unchecked")
    public static <T> T getTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return getTarget(metaObject.getValue(H_TARGET));
        }
        return (T) target;
    }

}
