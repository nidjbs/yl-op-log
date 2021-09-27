package com.hyl.op.log.enums;

/**
 * @author huayuanlin
 * @date 2021/06/20 21:12
 * @desc the enum desc
 */
public enum OpLogStatus {
    /**
     * init
     */
    INIT(1),
    /**
     * processing
     */
    PROCESSING(2),
    /**
     * fail
     */
    FAIL(3),
    /**
     * complete
     */
    COMPLETE(4)
    ;

    private final int id;

    OpLogStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
