package com.landray.kmss.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 排序公用方法
 * @author 王京
 *
 */
public class ListSortUtil {

    private final static String methodType = "get";

    public static int compare(final String orderName,final Boolean desc ,Object arg0,Object arg1){
        Object objVal1 = null;
        Object objVal2 = null;
        if(arg0 instanceof java.util.Map) {
            objVal1 =((java.util.Map) arg0).get(orderName);
            objVal2 = ((java.util.Map) arg1).get(orderName);
        }else if(arg0 instanceof net.sf.json.JSONObject) {
            objVal1 =((java.util.Map) arg0).get(orderName);
            objVal2 = ((java.util.Map) arg1).get(orderName);
        } else {
            // TODO Auto-generated method stub
            Field order1 = getFieldByClasss(orderName, arg0);
            Field order2 = getFieldByClasss(orderName, arg1);
            objVal1 = getFileValue(arg0, order1.getName());
            objVal2 = getFileValue(arg1, order2.getName());
        }
        if (objVal1 == null && objVal2 != null) {
            return 1;
        } else if (objVal1 != null && objVal2 == null) {
            return -1;
        } else if (objVal1 == null && objVal2 == null) {
            return 0;
        }
        // 同一个对象的同一个字段类型是一样的，这里拿order1的类型来比较
        if (objVal1 instanceof java.lang.String) {
            return desc ? Collator.getInstance(Locale.CHINA).compare(objVal2,objVal1):Collator.getInstance(Locale.CHINA).compare(objVal1,objVal2);
        } else if (objVal1 instanceof java.lang.Integer) {
            // int 类型或者封装类型
            return desc ? Integer.valueOf(objVal2.toString()).compareTo(Integer.valueOf(objVal1.toString())):
                    Integer.valueOf(objVal1.toString()).compareTo(Integer.valueOf(objVal2.toString()));
        }else if (objVal1 instanceof java.util.Date) {
            // date 类型或者封装类型
            return desc ? ((Date)objVal2).compareTo((Date)(objVal1)):
                    ((Date)objVal1).compareTo(((Date)objVal2));
        }else{
            // 比较两个类型
            return desc ? String.valueOf(objVal2).compareTo(String.valueOf(objVal1)):
                    String.valueOf(objVal1).compareTo(String.valueOf(objVal2));
        }
    }

    /**
     *
     * @param list
     * @param orderName 字段名称
     * @param isDesc 是否倒序
     */
    public static void sort(List list, final String orderName,final boolean isDesc) {
        Collections.sort(list, new Comparator<Object>(){
            @Override
            public int compare(Object arg0, Object arg1) {
                return ListSortUtil.compare(orderName,isDesc,arg0,arg1);
            }
        });
    }

    /**
     * 获取属性对应的值
     *
     * @param object
     * @param name
     * @return
     */
    private static Object getFileValue(Object object, String name) {
        try {
            Method m = (Method) object.getClass().getMethod(String.format("%s%s", methodType, getMethodName(name)));
            if (m != null) {
                return m.invoke(object);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 转换get方法名称第一个字母骆驼命名规则。第一个字母大写
     *
     * @param fildeName
     * @return
     * @throws Exception
     */
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 根据属性名获取属性元素，包括各种安全范围和所有父类
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static Field getFieldByClasss(String fieldName,  Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {

            }
        }
        return field;

    }
}
