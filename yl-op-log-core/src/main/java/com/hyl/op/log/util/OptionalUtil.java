package com.hyl.op.log.util;

import com.hyl.op.log.common.SimpleCallBack;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author huayuanlin
 * @date 2021/08/04 10:11
 * @desc the class desc
 */
public class OptionalUtil {


    public static <T> void ifPresentOrElse(Supplier<T> supplier, Consumer<T> consumer, SimpleCallBack callBack) {
        T t = supplier.get();
        if (t != null) {
            consumer.accept(t);
        } else {
            callBack.callBack();
        }
    }

    public static <T> void ifPredicateOrElse(Predicate<T> predicate, Supplier<T> supplier, Consumer<T> consumer, SimpleCallBack callBack) {
        T t = supplier.get();
        if (predicate.test(t)) {
            consumer.accept(t);
        } else {
            callBack.callBack();
        }
    }

}
