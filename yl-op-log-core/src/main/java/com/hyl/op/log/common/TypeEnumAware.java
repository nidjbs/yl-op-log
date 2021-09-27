package com.hyl.op.log.common;

/**
 * @author huayuanlin
 * @date 2021/06/25 23:55
 * @desc the interface desc
 */
public interface TypeEnumAware<T extends TypeEnum> {

    /**
     * type enum
     *
     * @return type
     */
    T typeEnum();

}
