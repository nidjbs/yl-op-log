package com.hyl.op.log.core.mapping;

import com.hyl.op.log.annotations.OpLogField;
import com.hyl.op.log.annotations.OpLogTable;
import com.hyl.op.log.util.ConverterUtil;
import com.hyl.op.log.util.MapsUtil;
import com.hyl.op.log.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author huayuanlin
 * @date 2021/06/25 23:17
 * @desc the class desc
 */
public class ProcessRawMappingBean extends InstantiationAwareBeanPostProcessorAdapter {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        OpLogTable annotation = beanClass.getAnnotation(OpLogTable.class);
        if (annotation == null) {
            return super.postProcessBeforeInstantiation(beanClass, beanName);
        }
        MappingBean mappingBean = new MappingBean();
        Map<String, String> convertValue = convertMapping(beanClass);
        mappingBean.setColumnDescMap(convertValue);
        return mappingBean;
    }

    /**
     * convert mapping,get the filed with biz desc map
     *
     * @param beanClass beanClass
     * @return filed with biz desc map
     */
    private Map<String, String> convertMapping(Class<?> beanClass) {
        Field[] declaredFields = beanClass.getDeclaredFields();
        Map<String, String> map = MapsUtil.hashMap();
        for (Field field : declaredFields) {
            OpLogField annotation = field.getAnnotation(OpLogField.class);
            if (annotation != null) {
                String str;
                if (StringUtil.isEmpty(annotation.fieldName())) {
                    str = ConverterUtil.camelToUnderline(field.getName());
                } else {
                    str = annotation.fieldName();
                }
                map.put(str, annotation.value());
            }
        }
        return map;
    }
}
