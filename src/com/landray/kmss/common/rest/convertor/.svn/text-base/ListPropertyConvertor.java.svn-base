package com.landray.kmss.common.rest.convertor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;

/**
 * @ClassName: ListPropertyConvertor
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-18 14:57
 * @Version: 1.0
 */
public class ListPropertyConvertor extends PropertyConvertorSupport {

    private final Log log = LogFactory.getLog(ListPropertyConvertor.class);

    private final String DELEMITER = "delemiter";

    private final String PROPERTIES = "prop";

    private final String DEFAULT_DELEMITER = ";";

    @Override
    public Object convert(Object value, PropertyConvertorContext context) {
        String str = "";
        try {
            if (value == null) {
                return null;
            }
            String property = context.getColumnInfo().getProperty();
            Map<String, String> props = getProps(context);
            String split = props.get(DELEMITER);
            if (StringUtil.isNull(split)) {
                split = DEFAULT_DELEMITER;
            }
            String properties = props.get(PROPERTIES);
            if (StringUtil.isNull(properties)) {
                log.error(property + " 没有配置prop属性,无法转换");
            }
            int index = properties.lastIndexOf(".");
            String listPropertyName = properties.substring(0,index);
			String propertyName = properties.substring(index + 1);
            if (PropertyUtils.isReadable(value, listPropertyName)) {
                try {
                    value = PropertyUtils.getProperty(value, listPropertyName);
                } catch (Exception e) {
                    log.error("获取属性值：" + listPropertyName + "异常", e);
                }
            }
            if (!(value instanceof List)) {
                return null;
            }
            List val = (List) value;
            String[] result = ArrayUtil.joinProperty(val,
                    propertyName, split);
            for (int i = 0; i < result.length; i++) {
                if (i > 0) {
                    str += ":";
                }
                str += result[i];
            }
        } catch (Exception e) {
            throw new KmssRuntimeException(e);
        }
        return str;
    }
}
