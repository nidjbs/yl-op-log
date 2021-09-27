package com.hyl.op.log.enums;

/**
 * @author huayuanlin
 * @date 2021/06/20 21:29
 * @desc the enum desc
 */
public enum OpLogType {
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
    DELETE(3)

    ;


    private final int id;

    OpLogType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
