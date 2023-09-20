package com.landray.kmss.km.cogroup.util;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import org.apache.commons.beanutils.PropertyUtils;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

public class CogroupUtil {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(CogroupUtil.class);

	public static String getModelDocCreateTimeProperyValue(IBaseModel baseModel, String property, Locale local)
			throws Exception {
		String rtnStr = "";
		String tempString = ModelUtil.getModelPropertyString(baseModel, property, "", local);
		if (StringUtil.isNotNull(tempString)) {
			rtnStr = PropertyUtils.getProperty(baseModel, property).toString();
			if (StringUtil.isNotNull(rtnStr)) {
				rtnStr = rtnStr.substring(0, 16);
			}
		}
		return rtnStr;
	}

	public static String getModelDocCreatorProperyValue(IBaseModel baseModel, String property, Locale local)
			throws Exception {
		String rtnStr = "";
		String tempString = ModelUtil.getModelPropertyString(baseModel, property, "", local);
		if (StringUtil.isNotNull(tempString)) {
			SysOrgPerson sysOrgPerson = (SysOrgPerson) PropertyUtils.getProperty(baseModel, property);
			if (sysOrgPerson != null) {
				rtnStr = sysOrgPerson.getFdLoginName();
			}
		}
		return rtnStr;
	}

	public static String getModelSimpleClassName(String modelName, String fdId) {
		try {
			SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
			IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
			IBaseModel baseModel;

			baseModel = baseService.findByPrimaryKey(fdId);

			// 如果传入的是一个String类型的全类名，则不需要再解析了
			String rtnVal = baseModel.getClass().getSimpleName();
			int i = rtnVal.indexOf('$');
			if (i > -1) {
                rtnVal = rtnVal.substring(0, i);
            }
			return rtnVal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean moduleExist(String path) {
		boolean exist = new File(PluginConfigLocationsUtil.getKmssConfigPath() + path).exists();
		return exist;
	}

	public static Map<String, String> getWeixinConfig() throws Exception {
		Class<?> cls;
		try {
			cls = Class.forName("com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
			BaseAppConfig config = (BaseAppConfig) cls.newInstance();
			return config.getDataMap();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static boolean isWeixinChatDataEnable() throws Exception {
		boolean moduleExist = moduleExist("/third/weixin");
		if(moduleExist==false){
			return false;
		}
		Map<String,String> configMap = getWeixinConfig();
		String wxEnabled = configMap.get("wxEnabled");
		String chatdataSyncEnable = configMap.get("chatdataSyncEnable");
		if("true".equals(wxEnabled) && "true".equals(chatdataSyncEnable)){
			return true;
		}
		return false;
	}
}
