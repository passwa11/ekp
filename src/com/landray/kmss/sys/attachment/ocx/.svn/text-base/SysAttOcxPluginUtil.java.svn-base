package com.landray.kmss.sys.attachment.ocx;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.sys.profile.util.ProfileMenuUtil;
import org.apache.commons.beanutils.PropertyUtils;
import com.landray.kmss.util.ClassUtils;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.StringUtil;

public class SysAttOcxPluginUtil {
	
	private static final String POINT_ID = "com.landray.kmss.sys.attachment.ocx";

	private static Map<String,JSONObject> extensionMapInfo = null;

	/**
	 * 根据扩展点获取业务模块所选的控件
	 * @param fdKey
	 * @param fdModelName
	 * @return
	 */
	public static String getConfigOcxName(String fdKey,String fdModelName) {
		String ocxName = "";
		try {
			//获取扩展点信息
			if (extensionMapInfo == null) {
				getExtensionInfo();
			}
			JSONObject ocxInfoJson = extensionMapInfo.get(fdKey+fdModelName);
			if (ocxInfoJson != null) {
				String configClass = ocxInfoJson.getString("configClass");
				String configProperty = ocxInfoJson.getString("configProperty");
				//是否使用与合同模块相同的配置
				boolean useAgreement = false;
				if (fdModelName.startsWith("com.landray.kmss.km.sample")) {
					//范本模块使用的正文编辑器与合同模块配置相同
					if (ProfileMenuUtil.moduleExist("/km/agreement")) {
						useAgreement = true;
					}
				} else {
					useAgreement = true;
				}
				
				if (useAgreement) {
					//默认配置类是继承BaseAppConfig
					Object configObj = ClassUtils.forName(configClass).newInstance();
					if (configObj instanceof BaseAppConfig) {
						BaseAppConfig info = (BaseAppConfig)configObj;
						String configValue = info.getDataMap().get(configProperty);
						if (StringUtil.isNotNull(configValue)) {
							ocxName = configValue;
						}
					} else {
						//通过反射获取配置类的属性值
						Object obj = PropertyUtils.getSimpleProperty(configObj, configProperty);
						if (obj != null && obj instanceof String) {
							ocxName = obj.toString();
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ocxName;
	}
	

	/**
	 * 获取扩展点信息
	 */
	private synchronized static void getExtensionInfo() {
		if (extensionMapInfo == null) {
			extensionMapInfo = new HashMap<String,JSONObject>();
			IExtension[] extensions = Plugin.getExtensions(POINT_ID, "*", "type");
			if (extensions == null || extensions.length == 0) {
				//没有获取到任何扩展点信息
			} else {
				for (IExtension extension : extensions) {
					String fdKeyStr = Plugin.getParamValueString(extension, "fdKey");
					String fdModelNameStr = Plugin.getParamValueString(extension, "fdModelName");
					String configClass = Plugin.getParamValueString(extension, "configClass");
					String configProperty = Plugin.getParamValueString(extension, "configProperty");
					//使用json对象方便扩展
					JSONObject ocxInfoJson = new JSONObject();
					ocxInfoJson.put("configClass", configClass);
					ocxInfoJson.put("configProperty", configProperty);
					String[] fdKeyArr = fdKeyStr.split(",");
					String[] fdModelNameArr = fdModelNameStr.split(",");
					for (String fdKey : fdKeyArr) {
						for (String fdModelName : fdModelNameArr) {
							extensionMapInfo.put(fdKey+fdModelName, ocxInfoJson);
						}
					}
				}
			}
		}
	}

}
