package com.landray.kmss.sys.attachment.watermark.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.watermark.service.IYuFeiWatermarkService;
import com.landray.kmss.sys.attachment.watermark.service.IWatermarkService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * 获取水印接口实现类的工具类，业务模块通过它来获取相关服务 添加水印接口 {@link IWatermarkService}
 * 
 * @author 梅瑞鹏
 *
 */
public class WatermarkPlugin {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WatermarkPlugin.class);

	private WatermarkPlugin() {

	}

	public static final String EX_ITEM_NAME_SERVICE = "service";
	
	public static final String YF_ITEM_NAME_SERVICE="yfService";

	public static final String P_PN = "providerName";
	public static final String P_BEAN = "bean";
	public static final String P_CJSP = "configJsp";
	public static final String P_ORDER = "order";

	/**
	 * 添加水印服务扩展点
	 */
	public static final String EXTENSION_WATERMARK_ADDWATERMARK_SERVICE = IWatermarkService.class.getPackage().getName();

	public static List<IWatermarkService> getWatermarkServices() {
		return getService(IWatermarkService.class, null, null, EX_ITEM_NAME_SERVICE);
	}

	public static IWatermarkService getWatermarkService(String providerName) {
		List<IWatermarkService> services = getService(IWatermarkService.class, null, providerName, EX_ITEM_NAME_SERVICE);
		if (!services.isEmpty()) {
			return services.get(0);
		}
		return null;
	}
	
	public static List<IYuFeiWatermarkService> getYuFeiWatermarkServices() {
		return getService(IYuFeiWatermarkService.class, null, null, YF_ITEM_NAME_SERVICE);
	}
	
	public static IYuFeiWatermarkService getYuFeiWatermarkService(String providerName) {
		List<IYuFeiWatermarkService> services = getService(IYuFeiWatermarkService.class, null, providerName, YF_ITEM_NAME_SERVICE);
		if (!services.isEmpty()) {
			return services.get(0);
		}
		return null;
	}

	/* 根据order参数排序，小的排前面 */
	private static void sortByOrder(List<IExtension> enabledExtensions) {
		Collections.sort(enabledExtensions, new Comparator<IExtension>() {
			@Override
			public int compare(IExtension o1, IExtension o2) {
				int order1, order2;
				Object obj1 = Plugin.getParamValue(o1, P_ORDER);
				Object obj2 = Plugin.getParamValue(o2, P_ORDER);
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

	private static <T> List<T> getService(Class<T> type, String model, String providerName, String itemName) {
		 if(StringUtil.isNull(model)){
	            model="*";
	        }
		 List<T> rtn = new ArrayList<T>();
		 IExtension[] extensions = Plugin.getExtensions(type.getPackage().getName(),model,itemName);
	        if(extensions==null||extensions.length<1){
	            return rtn;
	        }
	        List<IExtension> enabledExtensions = Arrays.asList(extensions);
	        Object bean = null;
	      //如果没有指定供应商，就返回第一个，否则遍历
		if (StringUtil.isNull(providerName)) {
			sortByOrder(enabledExtensions);
            /* 根据bean名称获取对应的bean，添加到临时列表中 */
            Map<String,String> cache = new HashMap<>();
            for(IExtension extesion : enabledExtensions){
                String beanName = Plugin.getParamValueString(extesion, P_BEAN);
                String pn = Plugin.getParamValueString(extesion, P_PN);
                if(cache.containsKey(pn)){
					if (logger.isDebugEnabled()) {
                        logger.warn("There is a extension with provider {name:"+pn+", bean: "+cache.get(pn)+
                                "}, bean: "+beanName+" will be ignored.");
                    }
                    continue;
                }
                cache.put(pn,beanName);
                bean = SpringBeanUtil.getBean(beanName);
                if (!(type.isInstance(bean))){
                    throw new RuntimeException("the bean:" + beanName+ " must implements the interface:"+type.getName());
                }
                rtn.add(type.cast(bean));
			}
		} else {
			for (IExtension extesion : enabledExtensions) {
				String pn = Plugin.getParamValueString(extesion, P_PN);
				if (providerName.equalsIgnoreCase(pn)) {
					String beanName = Plugin.getParamValueString(extesion, P_BEAN);
					bean = SpringBeanUtil.getBean(beanName);
					if (!(type.isInstance(bean))) {
						throw new RuntimeException("the bean:" + beanName
								+ " must implements the interface:" + type.getName());
					}
					rtn.add(type.cast(bean));
					break;
				}
			}
	        }
		return rtn;
	}

}
