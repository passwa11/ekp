package com.landray.kmss.common.rest.convertor;

import com.landray.kmss.util.EnumerationTypeUtil;
import com.sunbor.web.tag.enums.ValueLabel;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @ClassName: EnumsPropetyConvertor
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-12-15 19:05
 * @Version: 1.0
 */
public class EnumsPropetyConvertor extends PropertyConvertorSupport {

    private static final Log logger = LogFactory
            .getLog(EnumsPropetyConvertor.class);

    @Override
    public Object convert(Object value, PropertyConvertorContext context) {
        String property = context.getColumnInfo().getProperty();
        String enumsType = getProps(context).get("enumsType");
        try {
            Object enumVal = PropertyUtils.getProperty(value, property);
            List columnEnums = EnumerationTypeUtil.getColumnEnumsByType(enumsType);
            for(int i = 0; i < columnEnums.size(); ++i) {
                ValueLabel valueLabel = (ValueLabel) columnEnums.get(i);
                if (valueLabel.getValue().equals(enumVal.toString())) {
                    String label = valueLabel.getLabel();
                    return label;
                }
            }
        } catch (Exception e) {
            logger.error("无法获取enumsType: " + enumsType + "显示值");
        }
        return "";
    }
}
