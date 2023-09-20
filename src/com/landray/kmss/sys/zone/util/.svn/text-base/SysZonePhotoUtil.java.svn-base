package com.landray.kmss.sys.zone.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.person.service.plugin.PersonZoneHelp;
import com.landray.kmss.sys.zone.dict.SysZonePhotoSource;
import com.landray.kmss.sys.zone.service.plugin.ISysZonePhotoSourceService;
import com.landray.kmss.util.StringUtil;


public class SysZonePhotoUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZonePhotoUtil.class);

	
	private static final String PHOTO_MAP_EXTENSION_POINT_ID = "com.landray.kmss.sys.zone.photomap";
	private static final String SOURCE_ITEM = "source";
	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_SERVICE = "service";
	private static final String PARAM_IMG_BEAN = "imageBeanName";
	private static List<Map<String, Object>> sources 
					=  new ArrayList<Map<String, Object>>();
	

	/**
	 * 获取扩展数据源配置
	 * @return
	 */
	public  static List<Map<String, Object>> getAllSource() {
		if (sources.isEmpty()) {
			synchronized(sources) {
				if (sources.isEmpty()) {
					IExtensionPoint point = Plugin
							.getExtensionPoint(PHOTO_MAP_EXTENSION_POINT_ID);
					IExtension[] extensions = point.getExtensions();
					for (IExtension extension : extensions) {
						if (SOURCE_ITEM.equals(extension
								.getAttribute("name"))) {
							Map<String, Object> params = new HashMap<String, Object>();
							params.put(PARAM_ID, Plugin
									.getParamValueString(extension,
											PARAM_ID));
							params.put(PARAM_NAME, Plugin
									.getParamValueString(extension,
											PARAM_NAME));
							params.put(PARAM_SERVICE, Plugin
									.getParamValue(extension,
											PARAM_SERVICE));
							params.put(PARAM_IMG_BEAN, Plugin
									.getParamValue(extension,
											PARAM_IMG_BEAN));
							sources.add(params);
						}
					}
				}
			}
		}
		return sources;
	}
	
	
	/**
	 * 获取所有数据源
	 * @return
	 */
	public static  Map<String, SysZonePhotoSource> getImgSources(ServletContext servletContext) {
		HashMap<String, SysZonePhotoSource> rtnMap = new HashMap<String, SysZonePhotoSource>();
		ArrayList<Map<String, Object>> sourceDefines 
				= (ArrayList<Map<String, Object>>)getAllSource();
		//获取头像的bean
		String imgBeanName = PersonZoneHelp.IMG_SELECTED_EXT;
		//imgBeanName = "sysZonePersonImageBean";
		if(StringUtil.isNull(imgBeanName)) {
			imgBeanName = "sysZonePersonImageBean";
		}
		for(Map<String, Object> source : sourceDefines) {
			ISysZonePhotoSourceService service = (ISysZonePhotoSourceService)source.get("service");
			String sourceBeanName = (String)source.get("imageBeanName");
			try {
				if(service != null && sourceBeanName.equals(imgBeanName)) {
					rtnMap.put((String)source.get("id"), service.getSource(servletContext));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取照片墙数据异常，beanName:" + service.getClass().getName(), e);
			}
		} 
		return rtnMap;
	}
	
}
