package com.hyl.op.log.util;

/**
 * @author huayuanlin
 * @date 2021/09/27 23:55
 * @desc the class desc
 */
public class ObjUtil {

    private ObjUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean isBaseType(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof Integer
                || obj instanceof String
                || obj instanceof Long
                || obj instanceof Boolean
                || obj instanceof Character;
    }

}
