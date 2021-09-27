package com.hyl.op.log.enums;

import com.hyl.op.log.common.TypeEnum;

/**
 * @author huayuanlin
 * @date 2021/06/25 23:58
 * @desc the enum desc
 */
public enum LogMappingType implements TypeEnum {
    /**
     * create
     */
    CREATE(1),
    /**
     * update
     */
    UPDATE(2),
    /**
     * delete
     */
    DELETE(3);


    private final int id;

    LogMappingType(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
