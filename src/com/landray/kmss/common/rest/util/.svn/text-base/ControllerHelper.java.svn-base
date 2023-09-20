package com.landray.kmss.common.rest.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.discovery.tools.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.common.dto.QueryRequest;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;

/**
 * 前后端分离的Controller的工具类
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
public class ControllerHelper {
    private static final Log log = LogFactory.getLog(ControllerHelper.class);

    /**
     * 封装request请求
     *
     * @param request 请求对象
     * @return 封装后的请求体
     */
    public static HttpRequestParameterWrapper buildRequestParameterWrapper(HttpServletRequest request) {
        return new HttpRequestParameterWrapper(request);
    }

    /**
     * 分页请求对象的封装与转换
     *
     * @param request 请求对象
     * @param query   请求体
     * @return 请求参数经过包装的请求体
     */
    public static HttpRequestParameterWrapper buildRequestParameterWrapper(HttpServletRequest request, QueryRequest query) {
        HttpRequestParameterWrapper wrapper = new HttpRequestParameterWrapper(request);
        //查询条件
        for (Map.Entry<String, Object> entry : query.getConditions().entrySet()) {
            Object value = entry.getValue();
            String[] mapValue = objectToStringArgs(value, entry.getKey());
            if (mapValue == null) {
                continue;
            }
            wrapper.putParameter(entry.getKey(), mapValue);
        }
        //排序
        wrapper.putParameter("orderby", query.getSorts().getOrderby());
        wrapper.putParameter("ordertype", query.getSorts().getOrdertype());
        //分页
        wrapper.putParameter("pageno", query.getPageno());
        wrapper.putParameter("rowsize", query.getRowsize());
        return wrapper;
    }

    /**
     * map请求对象的封装与转换
     *
     * @param request    请求对象
     * @param requestMap map格式的请求体
     * @return 请求参数经过包装的请求体
     */
    public static HttpRequestParameterWrapper buildRequestParameterWrapper(HttpServletRequest request, Map<String, Object> requestMap) {
        HttpRequestParameterWrapper wrapper = new HttpRequestParameterWrapper(request);
        //请求体置入请求参数中
        for (Map.Entry<String, Object> param : requestMap.entrySet()) {
            String[] mapValue = objectToStringArgs(param.getValue(), param.getKey());
            wrapper.putParameter(param.getKey(), mapValue);
        }
        return wrapper;
    }

    /**
	 * map请求对象的封装与转换
	 *
	 * @param request
	 *            请求对象
	 * @param requestMap
	 *            map格式的请求体
	 * @return 请求参数经过包装的请求体
	 */
	public static HttpRequestParameterWrapper buildRequestParameterWrapper(
			HttpServletRequest request, Map<String, Object> requestMap,
			boolean extractCondition) {
		HttpRequestParameterWrapper wrapper = new HttpRequestParameterWrapper(
				request);
		// 请求体置入请求参数中
		for (Map.Entry<String, Object> param : requestMap.entrySet()) {
			String[] mapValue = objectToStringArgs(param.getValue(),
					param.getKey());
			wrapper.putParameter(param.getKey(), mapValue);
		}
		if (extractCondition && requestMap.containsKey("conditions")) {
			Object obj = requestMap.get("conditions");
			if (obj instanceof Map) {
				Map<String, Object> conditions = (Map) obj;
				for (Map.Entry<String, Object> condition : conditions
						.entrySet()) {
					String[] conditionVal = objectToStringArgs(
							condition.getValue(), condition.getKey());
					wrapper.putParameter(condition.getKey(), conditionVal);
				}
			}
		}
		return wrapper;
	}

	/**
	 * VO转参数的请求VO的分装与转换
	 *
	 * @param request
	 *            请求对象
	 * @param requestBody
	 *            请求体
	 * @return 请求参数经过包装的请求体
	 */
    public static HttpRequestParameterWrapper buildRequestParameterWrapper(HttpServletRequest request, Object requestBody) {
        HttpRequestParameterWrapper wrapper = new HttpRequestParameterWrapper(request);
        //请求体置入请求参数中
        wrapper.getParameterMap().putAll(extractRequesrBody(requestBody));
        return wrapper;
    }


    /**
     * 对象类型转为参数所需的String数组
     *
     * @param value         需要转换的值
     * @param parameterName 用于日志记录定位到转换有问题的参数
     * @return String数组
     */
    @SuppressWarnings("rawtypes")
    private static String[] objectToStringArgs(Object value, String parameterName) {
        //JSON的值转换为request参数所需的类型为String[]
        String[] mapValue;
        if (value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
            mapValue = new String[]{String.valueOf(value)};
        } else if (value instanceof String[]) {
            mapValue = (String[]) value;
        } else if (value instanceof List) {
            List valueList = (List) value;
            if (valueList.size() < 1) {
                return null;
            }
            if (valueList.get(0) instanceof String) {
                mapValue = new String[valueList.size()];
                for (int i = 0; i < valueList.size(); i++) {
                    mapValue[i] = (String) valueList.get(i);
                }
            } else {
                log.warn("无法转换的参数：key=" + parameterName + " value=" + value + " .[0]Class=" + valueList.get(0).getClass());
                return null;
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("不进行转换的参数类型：key=" + parameterName + " value=" + value + " Class=" + value.getClass());
            }
            return null;
        }
        return mapValue;
    }

    /**
     * 将请求体VO转为可put到parameter中的map
     *
     * @param requestBody 请求体的VO
     * @return map
     */
    public static Map<String, String[]> extractRequesrBody(Object requestBody) {
        Map<String, String[]> extracted = new HashMap<>();
        //bean对应的字段与方法
        Field[] fields = requestBody.getClass().getDeclaredFields();
        Method[] methods = requestBody.getClass().getDeclaredMethods();
        Map<String, Method> methodsMap = new HashMap<>();
        for (Method method : methods) {
            methodsMap.put(method.getName(), method);
        }
        //字段置入抽取的map中
        for (Field field : fields) {
            String fieldName = field.getName();
            //字段存在对应的get方法
            String getMethodName = buildGetMethodName(fieldName);
            if (methodsMap.containsKey(getMethodName)) {
                Method method = methodsMap.get(getMethodName);
                try {
                    Object result = method.invoke(requestBody);
                    String[] mapValue = objectToStringArgs(result, fieldName);
                    extracted.put(fieldName, mapValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return extracted;
    }

    private static String buildGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 处理VO中未正确转换的AutoHashMap
     *
     * @param vo
     */
    public static void VOConvertPostHandler(Object vo) {
        try {
            System.out.println(1111);
            String modelClassName = ModelUtil.getModelClassName(vo);
            if (StringUtil.isNotNull(modelClassName) && modelClassName.indexOf("com.landray.kmss") > -1) {
                //将现有的map替换为有正确itemClass的map
                PropertyDescriptor[] sProperties = PropertyUtils
                        .getPropertyDescriptors(vo);
                for (int i = 0; i < sProperties.length; i++) {
                    PropertyDescriptor sProperty = sProperties[i];
                    String propertyName = sProperty.getName();
                    Class<?> propertyType = sProperty.getPropertyType();
                    if (AutoHashMap.class.isAssignableFrom(propertyType)
                            && PropertyUtils.isReadable(vo, propertyName)) {
                        AutoHashMap propVal = (AutoHashMap) PropertyUtils
                                .getProperty(vo, propertyName);
                        doConvertAutoHashMap(vo, propertyName, propVal);
                    }
                }
            }
        } catch (Exception e) {
            log.error("autoHashMap类型转换异常", e);
        }
    }

    public static void doConvertAutoHashMap(Object vo, String propertyName, Map propVal) throws Exception {
        AutoHashMap autoHashMap = (AutoHashMap) PropertyUtils
                .getProperty(ClassUtils.newInstance(vo.getClass(),
                        null, null), propertyName);
        Map formProperty = (Map) PropertyUtils.getProperty(vo, propertyName);
        if (propVal != null
                && propVal.size() > 0) {
            Class autoHashMapItemClass = autoHashMap.get("newInstance").getClass();
            ObjectMapper mapper = new ObjectMapper();
            for (Object key : propVal.keySet()) {
                Object value = propVal.get(key);
                if (value instanceof LinkedHashMap) {
                    Object convertedValue = mapper.convertValue(value, autoHashMapItemClass);
                    VOConvertPostHandler(convertedValue);
                    convertAutoArrayList(convertedValue);
                    propVal.put(key, convertedValue);
                    formProperty.put(key, convertedValue);
                }
            }
        }
    }

    public static void convertAutoArrayList(Object vo) throws Exception {
        try {
            String modelClassName = ModelUtil.getModelClassName(vo);
            if (StringUtil.isNotNull(modelClassName) && modelClassName.indexOf("com.landray.kmss") > -1) {
                PropertyDescriptor[] sProperties = PropertyUtils
                        .getPropertyDescriptors(vo);
                for (int i = 0; i < sProperties.length; i++) {
                    PropertyDescriptor sProperty = sProperties[i];
                    String propertyName = sProperty.getName();
                    Class<?> propertyType = sProperty.getPropertyType();
                    if (AutoArrayList.class.isAssignableFrom(propertyType)
                            && PropertyUtils.isReadable(vo, propertyName)
                            && PropertyUtils.isWriteable(vo,
                            propertyName)) {
                        List autoArrayList = (List) PropertyUtils
                                .getProperty(vo, propertyName);
                        doConvertAutoArrayList(vo, propertyName, autoArrayList);
                    }
                }
            }
        } catch (Exception e) {
            log.error("转化autoArrayList属性异常", e);
        }
    }

    public static void doConvertAutoArrayList(Object vo, String propertyName, List autoArrayList) throws Exception {
        if (!ArrayUtil.isEmpty(autoArrayList)) {
            AutoArrayList targetAutoArrayList = (AutoArrayList) PropertyUtils
                    .getProperty(ClassUtils
                                    .newInstance(vo.getClass(), null, null),
                            propertyName);
            for (int index = 0; index < autoArrayList
                    .size(); index++) {
                Object srcItem = autoArrayList.get(index);
                Object targetItem = targetAutoArrayList.get(index);
                copyProperties(targetItem, srcItem);
            }
            PropertyUtils.setProperty(vo, propertyName,
                    targetAutoArrayList);
        }
    }

    private static void copyProperties(Object target, Object src)
            throws Exception {
        if (src instanceof Map && target instanceof IExtendForm) {
            PropertyDescriptor[] sProperties = PropertyUtils
                    .getPropertyDescriptors(target);
            Map srcMap = (Map) src;
            for (int i = 0; i < sProperties.length; i++) {
                PropertyDescriptor sProperty = sProperties[i];
                String propertyName = sProperty.getName();
                Class<?> propertyType = sProperty.getPropertyType();
                if (AutoArrayList.class.isAssignableFrom(propertyType)
                        && PropertyUtils.isReadable(target, propertyName)
                        && PropertyUtils.isWriteable(target,propertyName)) {
                    List srcVal = (List) srcMap.get(propertyName);
                    doConvertAutoArrayList(target, propertyName, srcVal);
                } else if (AutoHashMap.class.isAssignableFrom(propertyType)
                        && PropertyUtils.isReadable(target, propertyName)) {
                    Map srcVal = (Map) srcMap.get(propertyName);
                    doConvertAutoHashMap(target, propertyName, srcVal);
                } else {
                    Object propertyVal = srcMap.get(propertyName);
                    if (propertyVal != null && PropertyUtils.isWriteable(target,propertyName)) {
                        ObjectMapper mapper = new ObjectMapper();
                        Object convertValue = mapper.convertValue(propertyVal, propertyType);
                        PropertyUtils.setProperty(target, propertyName,convertValue);
                    }
                }
            }
        }
    }

    /**
     * 使后端接口返回值标准化，处理以下两种信息：
     * 1.外层JSONArray套内层JSONArray（只用于某些有问题的接口，所以该方法应只在需要时调用）
     * 2.返回值为String格式的JSON对象
     *
     * @param returnObject
     * @return
     */
    public static Object standardizeResult(Object returnObject) {
        if (returnObject instanceof JSONArray) {
            //多了一层JSONArray嵌套的对象，需要去掉一层
            JSONArray outerArray = (JSONArray) returnObject;
            if (!outerArray.isEmpty() && outerArray.get(0) instanceof JSONArray) {
                returnObject = outerArray.get(0);
            }
        } else if (returnObject instanceof String) {
            String oldString = (String) returnObject;
            //返回的信息是字符串，根据需要处理为JSON对象
            Class<?> clazz = null;
            if (oldString.startsWith("{") && oldString.endsWith("}")) {
                clazz = HashMap.class;
            } else if (oldString.startsWith("[") && oldString.endsWith("]")) {
                clazz = List.class;
            }
            try {
                if (clazz != null) {
                    returnObject = new ObjectMapper().readValue(String.valueOf(returnObject), clazz);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnObject;
    }

    /**
     * writer不写入outputStream，而是存为字符串，后续手动拿出
     *
     * @param response
     * @return
     */
    public static HttpResponseWriterWrapper buildResponseWriterWrapper(HttpServletResponse response) {
        return new HttpResponseWriterWrapper(response);
    }

}