package com.landray.kmss.sys.restservice.server.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysRestserviceServerLogConfig extends BaseAppConfig {
	
	public static final String DATA_TYPE_BASE = "0";//记录基础数据
	public static final String DATA_TYPE_DETAIL = "1";//记录详细数据(包括基础数据 + 请求报文 + 响应报文)

	public SysRestserviceServerLogConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "/sys/restservice/server/sys_restservice_server_log_config/sysRestserviceServerLogConfig_edit.jsp";
	}

	public String getModelName() {
		return getValue("modelName");
	}

	public void setModelName(String modelName) {
		setValue("modelName", modelName);
	}

	/**
	 * 记录日志数据类型
	 * 
	 * @return
	 */
	public String getDataType() {
		String dataType = getValue("dataType");
		if (StringUtil.isNull(dataType)) {
			dataType = DATA_TYPE_BASE;
		}
		return dataType;
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-restservice-server:sys.restservice.config");
	}
}
