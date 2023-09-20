package com.landray.kmss.common.rest.convertor;


import com.landray.kmss.util.StringUtil;

/**
 * @ClassName: ArrayIndexConvertor
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-18 17:36
 * @Version: 1.0
 */
public class ArrayIndexConvertor extends PropertyConvertorSupport{

    private final String INDEX = "index";

    @Override
    public Object convert(Object value, PropertyConvertorContext context) {
        String indexStr = getProps(context).get(INDEX);
        if (StringUtil.isNotNull(indexStr)) {
            int index = Integer.parseInt(indexStr);
            if (value instanceof Object[]) {
                Object[] valueArr = (Object[]) value;
                if (valueArr.length > index) {
                    Object rtnVal = valueArr[index];
                    return rtnVal;
                }
            }
        }
        return null;
    }
}
