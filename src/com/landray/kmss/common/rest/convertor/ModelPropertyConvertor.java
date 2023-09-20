package com.landray.kmss.common.rest.convertor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: ModelPropertyConvertor
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-18 17:23
 * @Version: 1.0
 */
public class ModelPropertyConvertor extends PropertyConvertorSupport {

    private final Log log = LogFactory.getLog(ModelPropertyConvertor.class);

    @Override
    public Object convert(Object value, PropertyConvertorContext context) {
        String property = context.getColumnInfo().getProperty();
        String field = property;
        String[] fdFieldProperties = null;
        //字段为对象类型
        if (field.contains(".")) {
            field = field.split("\\.")[0];
            fdFieldProperties = property.split("\\.");
        }
        //获取字段的实际值
        Object rtnVal = null;
        if (PropertyUtils.isReadable(value, field)) {
            rtnVal = getProperty(value, field);
            //字段为对象类型
            if (rtnVal != null && fdFieldProperties != null && fdFieldProperties.length > 0) {
                //对象字段在后台配置的字段名都是X.fdY，如fdDepartment.fdName，此处增加fdId信息
                Object fdId = getProperty(rtnVal, "fdId");
                //显示的字段名
                String displayProperty = null;
                //显示的字段值
                Object displayValue = rtnVal;
                for (int i = 0; i < fdFieldProperties.length; i++) {
                    if (i == 0) {
                        continue;
                    }
                    displayProperty = fdFieldProperties[i];
                    displayValue = getProperty(displayValue, displayProperty);
                }
                Map<String, Object> modelValue = new HashMap<>();
                modelValue.put("fdId", fdId);
                if (displayProperty != null) {
                    modelValue.put(displayProperty, displayValue);
                }
                //对象类型的字段结果转为对象格式
                rtnVal = modelValue;
            }
        }
        return rtnVal;
    }

    /**
     * 获取实体对象的字段值
     *
     * @param bean  实体对象
     * @param field 字段名
     * @return 字段值
     */
    private Object getProperty(Object bean, String field) {
        try {
            return PropertyUtils.getProperty(bean, field);
        } catch (Exception e) {
            log.error("获取字段值失败", e);
        }
        return null;
    }
}
