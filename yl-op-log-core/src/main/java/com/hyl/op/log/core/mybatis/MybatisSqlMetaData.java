package com.hyl.op.log.core.mybatis;

import com.hyl.op.log.common.SimpleCallBack;
import com.hyl.op.log.core.SqlMetaData;

/**
 * @author huayuanlin
 * @date 2021/06/14 21:13
 * @desc the mybatis sql meta data desc
 */
public class MybatisSqlMetaData extends SqlMetaData {

    private SimpleCallBack afterDataGetCallBack;

    public void setAfterDataGetCallBack(SimpleCallBack afterDataGetCallBack) {
        this.afterDataGetCallBack = afterDataGetCallBack;
    }

    /**
     * do call back ,get the after data
     */
    public void doCallBack() {
        if (this.afterDataGetCallBack != null) {
            afterDataGetCallBack.callBack();
        }
    }
}
