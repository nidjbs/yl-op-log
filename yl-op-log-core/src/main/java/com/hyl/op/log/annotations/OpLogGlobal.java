package com.hyl.op.log.annotations;

import com.hyl.op.log.core.support.ManualBizTraceSupport;

import java.lang.annotation.*;

/**
 * @author huayuanlin
 * @date 2021/08/06 17:15
 * @desc the annotation for the starting point for the upper call side,ex: facade interface
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface OpLogGlobal {
    /**
     * biz op type(description)
     * you can define enumerations to maintain the uniqueness of operation types in the system
     */
    String opType() default "";

    /**
     * biz id spring el expression
     *
     * @see ManualBizTraceSupport#setBizId(Object)  It can be set in this annotation method as well
     * @return biz id spring el expression
     */
    String bizIdEl() default "";
}
