package com.hyl.op.log.annotations;

import java.lang.annotation.*;

/**
 * @author huayuanlin
 * @date 2021/06/15 22:32
 * @desc the annotation desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface OpLogDbField {

    /**
     * mapping db filed name
     */
    String value();

    
}
