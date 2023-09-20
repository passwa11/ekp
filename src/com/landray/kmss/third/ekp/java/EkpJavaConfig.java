package com.landray.kmss.third.ekp.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.StringUtil;


public class EkpJavaConfig extends BaseAppConfig {
	
	public EkpJavaConfig() throws Exception {
		super();
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpJavaConfig.class);

	@Override
	public String getJSPUrl() {
		// TODO 自动生成的方法存根
		return "/third/ekp/java/ekpJavaConfig_edit.jsp";
	}

	@Override
    public String getValue(String name) {
		String value = (String) getDataMap().get(name);
		if ("kmss.oms.in.java.synchro.business.no".equals(name)
				&& StringUtil.isNull(value)) {
			value = "false";
		}
		return value;
	}

	@Override
    public void setValue(String name, String value) {
		getDataMap().put(name, value);
	}
	
	public synchronized String getWebServicePassword() {
		String tnsPassword = getValue("kmss.java.webservice.tnsPassword");
		if (StringUtil.isNotNull(tnsPassword)) {
			return tnsPassword;
		}
		String value = getValue("kmss.java.webservice.password");
		logger.debug("password ori :" + value);
		if (StringUtil.isNull(value)) {
			return "";
		}
		value = EkpJavaUtil.desDecrypt(value);
		tnsPassword = MD5Util.getMD5String(value);
		logger.debug("password md5 :" + tnsPassword);
		return tnsPassword;
	}

	@Override
    public void save() throws Exception {
		super.save();
	}

	


}
