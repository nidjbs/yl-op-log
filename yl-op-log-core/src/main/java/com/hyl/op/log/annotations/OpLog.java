package com.hyl.op.log.annotations;

import java.lang.annotation.*;

/**
 * @author huayuanlin
 * @date 2021/06/07 23:27
 * @desc the annotation desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface OpLog {

    /**
     * biz op type,if the upper-level service is maintained, it is recommended not to maintain it
     * @see OpLogGlobal
     */
    String bizType() default "";

    /**
     * biz id spring el expression
     *
     * @return biz id spring el expression
     */
    String bizIdEl() default "";
}
