package com.hyl.op.log.core.mapping;

import com.hyl.op.log.core.OpLogContent;
import com.hyl.op.log.enums.LogMappingType;

import java.util.List;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/06/26 00:11
 * @desc the class desc
 */
public class InsertMappingProcessor implements MappingProcessor {

    @Override
    public LogMappingType typeEnum() {
        return LogMappingType.CREATE;
    }

    @Override
    public MappingLogDO doMapping(OpLogContent opLogContent, MappingLogDO source) {
        source.setType(opLogContent.getType());
        List<Map<String, Object>> after = opLogContent.getAfter();

        return null;
    }
}
