package com.landray.kmss.common.rest.util;

import com.landray.kmss.common.rest.convertor.IPropertyConvertor;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * 列表字段扩展点工具类
 * 
 * @author lwc
 * @version 1.0 2020-12-15
 */
public class PluginUtil {

    private static final Log logger = LogFactory.getLog(PluginUtil.class);

	/**
	 * 扩展点
	 */
	private static final String EXTENSION_POINT_ID = "com.landray.kmss.sys.list.field.config";

    public static final String ITEM_NAME = "field";

	public static final String PARAM_UNID = "unid";

	public static final String PARAM_PROPERTY = "property";

	public static final String PARAM_COL = "col";

	public static final String PARAM_TITLE = "title";

	public static final String PARAM_CONVERTOR = "convertor";

	public static final String PARAM_CONVERTOR_PROPS = "convertorProps";

	public static final String PARAM_PROPS = "props";

	public static final String PARAM_SHOW = "show";

	public static final String PARAM_ORDER = "order";

	/**
	 * @Description 根据类名和接口名, 获取列表字段信息
	 * @param modelName 列表对应的实体类
     * @param method 列表方法名
	 * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @Exception
	 * @Date 2020-12-15 10:40
	 */
    public static Map<String, Map<String,Object>> getFields(String modelName, String method) {
        IExtension[] extensions = Plugin
                .getExtensions(EXTENSION_POINT_ID, modelName, ITEM_NAME);
        Map<String, Map<String,Object>> fields = new HashMap<>(extensions.length);
        List<IExtension> canApplyExtensions = new ArrayList<>(extensions.length);
        for (IExtension extension : extensions) {
            String unid = Plugin.getParamValueString(
                    extension, PARAM_UNID);
            if (unid.equals(method)) {
                canApplyExtensions.add(extension);
            }
        }
        canApplyExtensions.forEach((extension) -> {
            Map<String, Object> field = new HashMap<>();
            String propertyName = Plugin.getParamValueString(
                    extension, PARAM_PROPERTY);
            String propsStr = Plugin.getParamValueString(extension, PARAM_PROPS);
            if (StringUtil.isNotNull(propsStr)) {
                Map<String, String> props = getProps(propsStr);
                field.put(PARAM_PROPS,props);
            }
            field.put(PARAM_PROPERTY, propertyName);
            field.put(PARAM_COL, Plugin.getParamValueString(
                    extension, PARAM_PROPERTY));
            field.put(PARAM_TITLE, Plugin.getParamValueString(
                    extension, PARAM_TITLE));
            field.put(PARAM_CONVERTOR, Plugin.getParamValue(
                    extension, PARAM_CONVERTOR));
            field.put(PARAM_CONVERTOR_PROPS, Plugin.getParamValueString(
                    extension, PARAM_CONVERTOR_PROPS));
            field.put(PARAM_SHOW, Plugin.getParamValueString(
                    extension, PARAM_SHOW));
            fields.put(propertyName, field);
        });
        return fields;
    }
    
    /**
     * @Description 将props转成map
     * @Param 
     * @param: propsStr
     * @return java.util.Map<java.lang.String,java.lang.String> 
     * @throws 
     */
    public static Map<String,String> getProps(String propsStr) {
        HashMap<String, String> props = new HashMap<>();
        if(StringUtil.isNull(propsStr)) {
            return props;
        }
        String[] propItems = propsStr.split(IPropertyConvertor.PROPERTY_DELEMITER);
        for (int i = 0; i < propItems.length; i++) {
            String propItem = propItems[i];
            String[] item = propItem.split(IPropertyConvertor.KEY_VALUE_DELEMITER);
            props.put(item[0],item[1]);
        }
        return props;
    }

    /**
     * @Version  1.0
     * @Description 按order排序
     * @param extensions
     * @Return void
     * @Exception
     * @Date 2020-12-15 16:19
     */
    private static void sortByOrder(List<IExtension> extensions) {
        if (!ArrayUtil.isEmpty(extensions)) {
            Collections.sort(extensions, new Comparator<IExtension>() {
                @Override
                public int compare(IExtension o1, IExtension o2) {
                    int order1, order2;
                    Object obj1 = Plugin.getParamValue(o1, PARAM_ORDER);
                    Object obj2 = Plugin.getParamValue(o2, PARAM_ORDER);
                    if (obj1 instanceof Integer) {
                        order1 = (Integer) obj1;
                        order2 = (Integer) obj2;
                    } else {
                        order1 = Integer.valueOf((String) obj1);
                        order2 = Integer.valueOf((String) obj2);
                    }
                    return order1 - order2;
                }
            });
        }
    }

    /**
     * @Description 根据类名和接口名, 获取列表字段信息
     * @param modelName 列表对应的实体类
     * @param method 列表方法名
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Exception
     * @Date 2020-12-15 10:40
     */
    public static String getProps(String modelName, String method) {
        Map<String, Map<String, Object>> fields = getFields(modelName, method);
        final StringBuffer sb = new StringBuffer();
        fields.forEach((k,v) -> {
            sb.append(k).append(";");
        });
        return sb.toString();
    }

}
