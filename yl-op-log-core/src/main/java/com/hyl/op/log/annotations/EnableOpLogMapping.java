package com.hyl.op.log.annotations;

import com.hyl.op.log.core.mapping.MappingBeansRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author huayuanlin
 * @date 2021/06/24 16:23
 * @desc the annotation desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MappingBeansRegistrar.class)
public @interface EnableOpLogMapping {

    String[] basePackages();

}
