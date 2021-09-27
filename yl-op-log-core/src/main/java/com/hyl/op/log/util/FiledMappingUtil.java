package com.hyl.op.log.util;

import com.hyl.op.log.common.OpLogConstant;
import com.hyl.op.log.core.mapping.MappingBean;

/**
 * @author huayuanlin
 * @date 2021/06/25 23:39
 * @desc the class desc
 */
public class FiledMappingUtil {

    private FiledMappingUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * get filed biz desc by table name with db column name
     *
     * @param tableName tableName
     * @param column    column name
     * @return filed biz desc
     */
    public static String getFiledDescByTableWithColumn(String tableName, String column) {
        AssertUtil.notNullStr(tableName, "table name");
        AssertUtil.notNullStr(column, "column name");
        MappingBean mappingBean = SpringBeanUtil.getBeanByName(
                OpLogConstant.PREFIX + tableName, MappingBean.class);
        return mappingBean.getColumnDescMap().get(column);
    }
}
