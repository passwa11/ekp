package com.landray.kmss.hr.staff.util;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.*;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExportUtil {
    /**
     * 根据属性名获取属性值
     */
    public static String getFieldValueByName(Object o, String key) {
        String values = "";
        try {
            if(o == null){
                return "";
            }
            int indexD = key.indexOf(".");
            if (indexD > -1) {
                Object value = getValue(key.substring(0, indexD),o);
                String keysD = key.substring(indexD + 1);
                if (value instanceof List) {
                    StringBuffer s = new StringBuffer();
                    List valueList = (List) value;
                    if (!ArrayUtil.isEmpty(valueList)) {
                        for (Object v : valueList) {
                            s.append(getFieldValueByName(v, keysD)).append(";");
                        }
                        values = s.substring(0, s.length() - 1);
                    }
                } else {
                    values = getFieldValueByName(value, keysD);
                }
            }else{
                Object value = getValue(key,o);
                values = formatObject(key,value,o);
            }
            return values;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据object属性获取对应的value值
     * @param key
     * @param o
     * @return
     * @throws Exception
     */
    public static Object getValue(String key , Object o) throws Exception {
        if(null == o){
            return "";
        }
        //生成get方法名
        String getMethodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
        Method m = o.getClass().getMethod(getMethodName, new Class[]{});
        Object value = m.invoke(o, new Object[] {});
        return value;
    }

    /**
     * 获取导出字段对应的标题内容
     * @param field
     * @param dataDict
     * @return
     * @throws Exception
     */
    public static String getMessage(String field, SysDictModel dataDict){
        String message = "";
        Map<String, SysDictCommonProperty> map = dataDict.getPropertyMap();
        int index = field.indexOf(".");
        SysDictCommonProperty property;
        if (index > -1) {
            String prefix = field.substring(0, index);
            property = map.get(prefix);
            message = ResourceUtil.getString(property.getMessageKey());
        }else{
            property = map.get(field);
            message = ResourceUtil.getString(property.getMessageKey());
        }
        return message;
    }

    /**
     * 对象转字符串类型
     * @param
     * @return
     */
    private static String formatObject(String key,Object value,Object model) throws Exception {
        String s = "";
        if(null == value){
            return s;
        }
        String modelName = ModelUtil.getModelClassName(model);
        SysDictModel dataDict = SysDataDict.getInstance().getModel(modelName);
        Map<String, SysDictCommonProperty> map = dataDict.getPropertyMap();
        if(map.containsKey(key)){
            SysDictCommonProperty property = map.get(key);
            if (value instanceof Date) {
                Date date = (Date) value;
                s = DateUtil.convertDateToString(date, "yyyy-MM-dd");
            } else if (StringUtil.isNotNull(property.getEnumType())) {
                s = EnumerationTypeUtil.getColumnEnumsLabel(property.getEnumType(), value.toString());
            } else {
                s = value.toString();
            }
        }else{
            throw new Exception("未找到该属性信息:" + key);
        }
        return s;
    }
}
