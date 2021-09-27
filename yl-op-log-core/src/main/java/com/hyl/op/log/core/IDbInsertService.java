package com.hyl.op.log.core;

import java.util.List;

/**
 * @author huayuanlin
 * @date 2021/06/28 00:12
 * @desc the interface desc
 */
public interface IDbInsertService<T> {

    /**
     * insert
     *
     * @param t log Do
     * @return id
     */
    Long insert(T t);

    /**
     * insert batch
     *
     * @param list list DO
     */
    void insertBatch(List<T> list);
}
