package com.hyl.op.log.util;

import java.util.*;

/**
 * @author ： huayuanlin
 * @date ： 2021/5/23 16:59
 * @desc ： Collections util
 */
public class CollectionsUtil {

    private CollectionsUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <E> ArrayList<E> arrayList() {
        return new ArrayList<>();
    }

    public static <E> Set<E> hashSet() {
        return new HashSet<>();
    }


}
