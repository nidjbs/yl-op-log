package com.hyl.op.log.util;

import java.util.HashMap;

/**
 * @author huayuanlin
 * @date 2021/06/15 23:01
 * @desc the class desc
 */
public class MapsUtil {


    private MapsUtil() {
        throw new UnsupportedOperationException();
    }

    public static <K, V> HashMap<K, V> hashMap() {
        return new HashMap<>(16);
    }

}
