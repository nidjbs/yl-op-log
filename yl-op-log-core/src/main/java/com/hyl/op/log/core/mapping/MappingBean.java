package com.hyl.op.log.core.mapping;

import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/06/25 11:55
 * @desc the class desc
 */
public class MappingBean {

    private Map<String, String> columnDescMap;

    public Map<String, String> getColumnDescMap() {
        return columnDescMap;
    }

    public void setColumnDescMap(Map<String, String> columnDescMap) {
        this.columnDescMap = columnDescMap;
    }
}
