package com.hyl.op.log.annotations;

import java.lang.annotation.*;

/**
 * @author huayuanlin
 * @date 2021/06/24 16:06
 * @desc the annotation desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface OpLogTable {

    /**
     * table name,default is the DO Camel-Case name, ex : OrderInfoDO => order_info
     */
    String tableName() default "";

}
