package com.landray.kmss.sys.attachment.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.attachment.cluster.HistoryVersionConfigMessage;
import com.landray.kmss.sys.attachment.plugin.HistoryVersionPlugin;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysAttMainHistoryConfig extends BaseAppConfig {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttMainHistoryConfig.class);
	
	public SysAttMainHistoryConfig() throws Exception {
		super();
		initDefaultValue("attClearEnable","false");
		initDefaultValue("attClearCount","500");
		initDefaultValue("attKeepDays","90");
		initDefaultValue("rootType","0");
	}

	private void initDefaultValue(String key,String value) {
		if (StringUtils.isEmpty(getValue(key))) {
			setValue(key,value);
		}
	}
	
	@Override
	public void save() throws Exception {
		super.save();
		HistoryVersionPlugin.resetEnabledModelsMap(getAttHistoryConfigEnableModules());
		MessageCenter.getInstance().sendToOther(
				new HistoryVersionConfigMessage(
						getAttHistoryConfigEnableModules()));
	}
	
	/**
	 * 调用处可以直接调用此方法（统一处理异常），也可以使用new SysAttMainHistoryConfig()（异常需要自行处理）
	 */
	public static SysAttMainHistoryConfig newInstance() {
		SysAttMainHistoryConfig config = null;
		try {
			config = new SysAttMainHistoryConfig();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return config;
	}
	/**
	 * 获取已经开启附件版本的模块
	 */
	public String getAttHistoryEnable() {
		String attHistoryEnable = getValue(
				"attHistoryEnable");
		if (StringUtil.isNull(attHistoryEnable)) {
			attHistoryEnable = "";
		}
		return attHistoryEnable;
	}

	/**
	 * 定时清理附件开关
	 * @return
	 */
	public String getAttClearEnable() {
		String attClearEnable = getValue(
				"attClearEnable");
		if (StringUtil.isNull(attClearEnable)) {
			attClearEnable = "false";
		}
		return attClearEnable;
	}

	/**
	 * 定时清理获取文件数量
	 * @return
	 */
	public String getAttClearCount() {
		String attClearCount = getValue(
				"attClearCount");
		if (StringUtil.isNull(attClearCount)) {
			attClearCount = "500";
		}
		return attClearCount;
	}

	/**
	 * 附件历史版本保留天数，默认90天
	 * @return
	 */
	public String getAttKeepDays() {
		String attKeepDays = getValue(
				"attKeepDays");
		if (StringUtil.isNull(attKeepDays)) {
			attKeepDays = "90";
		}
		return attKeepDays;
	}

	/**
	 * 附件移出目录配置类型
	 * @return
	 */
	public String getRootType() {
		String rootType = getValue("rootType");
		if (StringUtil.isNull(rootType)) {
			rootType = "0";
		}
		return rootType;
	}

	/**
	 * 附件移出目录配置路径
	 * @return
	 */
	public String getRootPath() {
		String rootPath = getValue("rootPath");
		if (StringUtil.isNull(rootPath)) {
			rootPath = "";
		}
		return rootPath;
	}
	
	/**
	 * 获取已经开启附件版本的模块
	 */
	public String getAttHistoryConfigEnableModules() {
		String attHistoryConfigEnableModules = getValue(
				"attHistoryConfigEnableModules");
		if (StringUtil.isNull(attHistoryConfigEnableModules)) {
			attHistoryConfigEnableModules = "";
		}
		return attHistoryConfigEnableModules;
	}
	
	@Override
	public String getJSPUrl() {
		// TODO Auto-generated method stub
		return "/sys/attachment/sys_att_main/sysAttMainHistory_config.jsp";
	}
	
	@Override
    public String getModelDesc() {
		return ResourceUtil.getString("sys-attachment:sysAttMain.view.history.config");
	}

}
