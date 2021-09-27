package com.hyl.op.log.common;

import com.hyl.op.log.util.AssertUtil;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author huayuanlin
 * @date 2021/08/04 10:26
 * @desc the class desc
 */
public class SmartOptional<T> {

    private final T value;
    private Predicate<T> predicate;

    private SmartOptional<T> optional;

    public SmartOptional(T value) {
        this.value = value;
    }

    public static <T> SmartOptional<T> ofNullable(T t) {
        return new SmartOptional<>(t);
    }

    public void ofPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public void ifPresent(Consumer<T> consumer) {
        AssertUtil.notNull(consumer, "consumer logic");
        if (value != null) {
            consumer.accept(value);
        }
    }


    public void ifPresentOrElse(Consumer<T> consumer, SimpleCallBack callBack) {
        AssertUtil.notNull(consumer, "consumer logic");
        AssertUtil.notNull(callBack, "callBack logic");
        if (value != null) {
            consumer.accept(value);
        } else {
            callBack.callBack();
        }
    }

    /**
     * Before using, call the ofPredicate method to set the conditions.
     * It should be noted that the parameters in the consumer's param value may be empty
     *
     * @param consumer Predicate Succeed Logic
     * @param callBack Predicate Fail Logic
     */
    public void ifPredicateOrElse(Consumer<T> consumer, SimpleCallBack callBack) {
        if (predicate == null) {
            throw new UnsupportedOperationException("Please set up Predicate before using this method!");
        }
        AssertUtil.notNull(consumer, "consumer logic");
        AssertUtil.notNull(callBack, "callBack logic");
        if (predicate.test(value)) {
            consumer.accept(value);
        } else {
            callBack.callBack();
        }
    }

    /**
     * Before using, call the ofPredicate method to set the conditions.
     * It should be noted that the parameters in the consumer's param value may be empty
     *
     * @param consumer     Predicate Succeed Logic
     * @param elseConsumer Predicate Fail Logic
     */
    public void ifPredicateOrElse(Consumer<T> consumer, Consumer<T> elseConsumer) {
        if (predicate == null) {
            throw new UnsupportedOperationException("Please set up Predicate before using this method!");
        }
        AssertUtil.notNull(consumer, "consumer logic");
        AssertUtil.notNull(elseConsumer, "elseConsumer logic");
        if (predicate.test(value)) {
            consumer.accept(value);
        } else {
            elseConsumer.accept(value);
        }
    }


}
