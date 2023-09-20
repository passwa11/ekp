package com.landray.kmss.common.rest.convertor;

import com.landray.kmss.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PropertyConvertorSupport
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-12-15 17:51
 * @Version: 1.0
 */
public abstract class PropertyConvertorSupport implements IPropertyConvertor {

    public Map<String,String> getProps(PropertyConvertorContext context) {
        String convertorProps = context.getConvertorProps();
        HashMap<String, String> props = new HashMap<>();
        if(StringUtil.isNull(convertorProps)) {
            return props;
        }

        String[] propItems = convertorProps.split(IPropertyConvertor.PROPERTY_DELEMITER);
        for (int i = 0; i < propItems.length; i++) {
            String propItem = propItems[i];
            String[] item = propItem.split(IPropertyConvertor.KEY_VALUE_DELEMITER);
            props.put(item[0],item[1]);
        }
        return props;
    }

}
