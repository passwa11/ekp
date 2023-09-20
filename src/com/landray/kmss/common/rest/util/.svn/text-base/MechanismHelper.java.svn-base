package com.landray.kmss.common.rest.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;

/**
 * @Author 严明镜
 * @create 2021年01月14日
 */
public class MechanismHelper {

	private static Log logger = LogFactory.getLog(MechanismHelper.class);


	private static final String MECHANISM_KEY = "mechanism";

	/**
	 * form转json
	 *
	 * @param extendForm
	 * @return
	 */
	public static Map<String, Object> formToJson(IExtendForm extendForm) throws Exception{
		if (extendForm == null) {
			return null;
		}
		//所有实现的机制
		List<MechanismConfig.OneMech> mechList = filterMechanisms(extendForm.getClass());
		if (mechList.isEmpty()) {
			return null;
		}
		//排除字段
		List<String> ignoreList = calcIgnoreProperties(mechList);


		//将里面的值添加到机制体部的字段
		Map<String, String> oneToOneList = calcOneToOneProperties(mechList, false);
		//直接添加到机制体部的字段
		Map<String, String> propertyNames = calcPropertyNames(mechList);
		// form
        Map<String, Object> form = new HashMap<>();
		// 所有机制的数据
		Map<String, Object> mechanisms = new HashMap<>();
		for (PropertyDescriptor propDesciptor : PropertyUtils.getPropertyDescriptors(extendForm)) {
			String propName = propDesciptor.getName();
			if (!PropertyUtils.isReadable(extendForm, propName)) {
				continue;
			}
			Object propVal;
			try {
				propVal = PropertyUtils.getProperty(extendForm, propName);
			} catch (Exception e) {
				logger.error("无法读取property: " + propName, e);
				continue;
			}
			if (propertyNames.containsKey(propName)) {
				// 直接添加到机制体部的字段
				String mechanismKey = propertyNames.get(propName);
				Map<String, Object> oneMechValue;
                if (mechanisms.containsKey(mechanismKey)) {
                    oneMechValue = (Map<String, Object>) mechanisms.get(mechanismKey);
                } else {
                    oneMechValue = new HashMap<>();
                    mechanisms.put(mechanismKey, oneMechValue);
                }
                oneMechValue.put(propName, propVal);
			} else if (oneToOneList.containsKey(propName)) {
				// 将里面的值添加到机制体部的字段
				String mechanismKey = oneToOneList.get(propName);
                ObjectMapper mapper = new ObjectMapper();
                String formTojson = mapper.writeValueAsString(propVal);
                Map<String, Object> formatVal = mapper.readValue(formTojson, HashMap.class);
                filterIgnoreProperties(formatVal, ignoreList);
				mechanisms.put(mechanismKey, formatVal);
			} else {
			    //主文档字段
                if (!ignoreList.contains(propName)) {
                    form.put(propName, propVal);
                }
            }
		}
		form.put(MECHANISM_KEY, mechanisms);
        if (logger.isDebugEnabled()) {
			logger.debug(form);
		}
		return form;
	}

	private static void filterIgnoreProperties(Object value, List<String> ignoreProperties) throws Exception{
        if (value instanceof Map) {
            Map<String, Object> formatVal = (Map<String, Object>) value;
            if (!ArrayUtil.isEmpty(ignoreProperties)) {
                for (String ignoreProp : ignoreProperties) {
                    if (formatVal.containsKey(ignoreProp)) {
                        formatVal.remove(ignoreProp);
                    }
                }
            }
            for (Map.Entry<String, Object> entry : formatVal.entrySet()) {
                Object entryVal = entry.getValue();
                filterIgnoreProperties(entryVal, ignoreProperties);
            }
        } else if (value instanceof List) {
            List<?> listVals = (List<?>) value;
            for (Object item : listVals) {
                filterIgnoreProperties(item, ignoreProperties);
            }
        }
    }

	private static void addGlobalIgnoreProperties(List<String> ignoreList) {
        ignoreList.add("customPropMap");
        ignoreList.add("dynamicMap");
        ignoreList.add("toModelPropertyMap");
        ignoreList.add("fieldMessageKeyMap");
        ignoreList.add("modelClass");
        ignoreList.add("formClass");
        ignoreList.add("toFormPropertyMap");
    }

    private static Map<String,String> calcPropertyNames(List<MechanismConfig.OneMech> mechList) {
        Map<String, String> propertyNames = new HashMap<>();
        for (MechanismConfig.OneMech oneMech : mechList) {
            String mechKey = oneMech.getMechKey();
            List<String> oneMechPropertyNames = oneMech.getPropertyNames();
            if (!ArrayUtil.isEmpty(oneMechPropertyNames)) {
                for (String property : oneMechPropertyNames) {
                    propertyNames.put(property, mechKey);
                }
            }
        }
        return propertyNames;
    }

    /**
     * @Description mechKey->props 映射
     * @Param 
     * @param: mechList
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>> 
     * @throws 
     */
    private static Map<String, List<String>> calcOneMechProperties(List<MechanismConfig.OneMech> mechList) {
	    Map<String, List<String>> oneMechProperties = new HashMap<>();
        for (MechanismConfig.OneMech oneMech : mechList) {
            String mechKey = oneMech.getMechKey();
            List<String> oneMechPropertyNames = oneMech.getPropertyNames();
            if (!ArrayUtil.isEmpty(oneMechPropertyNames)) {
                List<String> properties = oneMechProperties.get(mechKey);
                if (ArrayUtil.isEmpty(properties)) {
                    properties = new ArrayList<>(oneMechPropertyNames);
                    oneMechProperties.put(mechKey, properties);
                } else {
                    properties.addAll(oneMechPropertyNames);
                }
            }
        }
        return oneMechProperties;
    }

    private static Map<String,String> calcOneToOneProperties(List<MechanismConfig.OneMech> mechList, boolean mechToProperty) {
        Map<String, String> oneToOneProperties = new HashMap<>();
        for (MechanismConfig.OneMech oneMech : mechList) {
            String mechKey = oneMech.getMechKey();
            String oneToOneProperty = oneMech.getOneToOneProperty();
            if (StringUtil.isNotNull(oneToOneProperty)) {
                if (mechToProperty) {
                    oneToOneProperties.put(mechKey, oneToOneProperty);
                } else {
                    oneToOneProperties.put(oneToOneProperty, mechKey);
                }
            }
        }
        return oneToOneProperties;
    }

    private static List<String> calcIgnoreProperties(List<MechanismConfig.OneMech> mechList) {
        List<String> ignoreProperties = new ArrayList<>();
        for (MechanismConfig.OneMech oneMech : mechList) {
            List<String> ignores = oneMech.getIgnores();
            if (!ArrayUtil.isEmpty(ignores)) {
                ignoreProperties.addAll(ignores);
            }
        }
        addGlobalIgnoreProperties(ignoreProperties);
        return ignoreProperties;
    }

    /**
	 * 分析该类实现的机制，返回所有对应的机制配置
	 *
	 * @param formClass
	 * @return
	 */
	private static List<MechanismConfig.OneMech> filterMechanisms(Class<? extends IExtendForm> formClass) {
		List<MechanismConfig.OneMech> filtedConfigs = new ArrayList<>();
		Map<Class<?>, MechanismConfig.OneMech> configs = MechanismConfig.getInstance().getConfigs();
		Class<?>[] interfaces = org.springframework.util.ClassUtils.getAllInterfacesForClass(formClass);
		for (Class<?> inte : interfaces) {
			if (configs.containsKey(inte)) {
				filtedConfigs.add(configs.get(inte));
			}
		}
		return filtedConfigs;
	}

	/**
	 * 前端提交的json数据转fom
	 *
	 * @param vo
	 */
	@SuppressWarnings("rawtypes")
	public static ActionForm jsonToForm(Map<String, Object> vo, Class formClass) throws Exception{
        ActionForm extendForm = (ActionForm)formClass.newInstance();
        if (vo != null && !vo.isEmpty()) {
            //所有实现的机制
            List<MechanismConfig.OneMech> mechList = filterMechanisms(formClass);
            Map<String, List<String>> properties = calcOneMechProperties(mechList);
            Map<String, String> oneToOneProperties = calcOneToOneProperties(mechList, true);
            for (Map.Entry<String, Object> entry : vo.entrySet()) {
                String propName = entry.getKey();
                Object propValue = entry.getValue();
                if (MECHANISM_KEY.equals(propName) && propValue instanceof Map) {
                    Map<String, Object> mechanismVals = (Map<String, Object>) propValue;
                    for (Map.Entry<String, Object> oneMech : mechanismVals.entrySet()) {
                        String mechKey = oneMech.getKey();
                        Object mechValue = oneMech.getValue();
                        if (oneToOneProperties.containsKey(mechKey)) {
                            String mechPropName = oneToOneProperties.get(mechKey);
                            if (PropertyUtils.isReadable(extendForm, mechPropName)) {
                                convertJsonToProperties(mechValue, mechPropName, extendForm);
                            }
                        } else if (properties.containsKey(mechKey)) {
                            convertJsonToProperties(mechValue, null, extendForm);
                        }
                    }
                } else {
                    convertValToProperty(propName, propValue, extendForm);
                }
            }
		}
		return extendForm;
	}
    
	/**
	 * @Description //TODO
	 * @Param 
	 * @param: mechValue
	 * @param: mechPropName
	 * @param: extendForm
	 * @return void 
	 * @throws 
	 */
    private static void convertJsonToProperties(Object mechValue, String mechPropName, Object extendForm) throws Exception{
        Object mechForm = null;
        if (StringUtil.isNotNull(mechPropName)) {
            mechForm = PropertyUtils.getProperty(extendForm, mechPropName);
        }
        if(mechForm instanceof AutoHashMap && mechValue instanceof Map) {
            ControllerHelper.doConvertAutoHashMap(extendForm, mechPropName, (Map) mechValue);
        } else if (mechForm instanceof AutoArrayList && mechValue instanceof List) {
            ControllerHelper.doConvertAutoArrayList(extendForm, mechPropName, (List) mechValue);
        } else if (mechValue instanceof Map){
            Map<String, Object> mapMechVal = (Map<String, Object>) mechValue;
            for (Map.Entry<String, Object> propEntry : mapMechVal.entrySet()) {
                String propName = propEntry.getKey();
                Object propValue = propEntry.getValue();
                if (mechForm != null) {
                    convertValToProperty(propName, propValue, mechForm);
                } else {
                    convertValToProperty(propName, propValue, extendForm);
                }
            }
        }

    }

    private static void convertValToProperty(String propName, Object propValue, Object extendForm) throws Exception{
        //System.out.println("propName: " + propName + ", propVal: " + propValue + ", extendForm: " + extendForm);
        PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(extendForm, propName);
		if (propertyDescriptor != null) {
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			if (AutoHashMap.class.isAssignableFrom(propertyType)) {
				Map mapVal = (Map) propValue;
				ControllerHelper.doConvertAutoHashMap(extendForm, propName,
						mapVal);
			} else if (AutoArrayList.class.isAssignableFrom(propertyType)) {
				List listVal = (List) propValue;
				ControllerHelper.doConvertAutoArrayList(extendForm, propName,
						listVal);
			} else {
				if (PropertyUtils.isWriteable(extendForm, propName)) {
					if (IExtendForm.class.isAssignableFrom(propertyType)) {
						Object property = PropertyUtils.getProperty(extendForm,
								propName);
						convertJsonToProperties(propValue, propName,
								extendForm);
						PropertyUtils.setProperty(extendForm, propName,
								property);
					} else {
						ObjectMapper mapper = new ObjectMapper();
						Object convertValue = mapper.convertValue(propValue,
								propertyType);
						PropertyUtils.setProperty(extendForm, propName,
								convertValue);
					}
				} else if (Map.class.isAssignableFrom(propertyType)) {
                    ObjectMapper mapper = new ObjectMapper();
					Map propertyVal = (Map) PropertyUtils
							.getProperty(extendForm, propName);
					Map convertValue = (Map) mapper.convertValue(propValue,
							propertyType);
					propertyVal.putAll(convertValue);
                }
            }
        }
    }
}