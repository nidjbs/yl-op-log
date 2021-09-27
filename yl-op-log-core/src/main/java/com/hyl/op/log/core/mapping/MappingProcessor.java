package com.hyl.op.log.core.mapping;

import com.hyl.op.log.common.TypeEnumAware;
import com.hyl.op.log.core.OpLogContent;
import com.hyl.op.log.enums.LogMappingType;

/**
 * @author huayuanlin
 * @date 2021/06/26 00:09
 * @desc the class desc
 */
public interface MappingProcessor extends TypeEnumAware<LogMappingType> {

    /**
     * do mapping
     *
     * @param opLogContent op log meta data detail
     * @param source source DO
     * @return mapping result
     */
    MappingLogDO doMapping(OpLogContent opLogContent, MappingLogDO source);

}
