package com.hyl.op.log.core.mapping;

import com.hyl.op.log.core.ISqlLogMetaDataService;
import com.hyl.op.log.core.OpLogMetaDataWrapper;
import com.hyl.op.log.util.SpringBeanUtil;

/**
 * @author huayuanlin
 * @date 2021/06/21 00:32
 * @desc the class desc
 */
public class StandardOpLogMapping implements IOpLogMapping {

    @Override
    public void process(OpLogMetaDataWrapper opLogMetaDataWrapper) {
        ISqlLogMetaDataService iSqlLogMetaDataService = SpringBeanUtil.getBeanByType(ISqlLogMetaDataService.class);
        boolean isNeedProcess = iSqlLogMetaDataService.updateStatusToProcessing(opLogMetaDataWrapper.getId());
        if (!isNeedProcess) {
            return;
        }
        // todo process

        // process complete,insert mapping log

    }

    public static StandardOpLogMapping getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final StandardOpLogMapping INSTANCE = new StandardOpLogMapping();
    }

}
