package com.landray.kmss.third.wechat.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 初始参数
 * 
 * @author 陈园园
 * @version 1.0 2015-05-04
 */

public class WechatMainConfig extends BaseAppConfig {
	
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WechatMainConfig.class);

	public static WechatMainConfig newInstance() {
		WechatMainConfig config = null;
		try {
			config = new WechatMainConfig();
		} catch (Exception e) {
			logger.error("", e);
		}
		return config;
	}

	public WechatMainConfig() throws Exception {
		super();
		
		// ==================以下为默认值===================
		
		// 微云系统地址
		if(StringUtil.isNull(getLwechat_wyUrl())) {
			setLwechat_wyUrl("http://www.mobiportal.cn");
		}
		
		// 是否微云推送待办
		if(StringUtil.isNull(getLwechat_wyEnable())) {
			setLwechat_wyEnable("false");
		}
		
		// 不推送待办时，代办类型不开启
		if("false".equals(getLwechat_wyEnable())) {
			setLwechat_wyisSendTodo("false");
			setLwechat_wyisSendView("false");
		}
	}

	@Override
    public String getJSPUrl() {
		return "/third/wechat/wechatMainConfig_edit.jsp";
	}

	/**微云系统地址*/
	public String getLwechat_wyUrl() {
		return getValue("lwechat_wyUrl");
	}

	public void setLwechat_wyUrl(String lwechat_wyUrl) {
		setValue("lwechat_wyUrl", lwechat_wyUrl);
	}
	
	/**微云待办是否启用*/
	public String getLwechat_wyEnable() {
		return getValue("lwechat_wyEnable");
	}

	public void setLwechat_wyEnable(String lwechat_wyEnable) {
		setValue("lwechat_wyEnable", lwechat_wyEnable);
	}
	
	/**微云通知地址*/
	public String getLwechat_wyNotifyUrl() {
		return getValue("lwechat_wyNotifyUrl");
	}

	public void setLwechat_wyNotifyUrl(String lwechat_wyNotifyUrl) {
		setValue("lwechat_wyNotifyUrl", lwechat_wyNotifyUrl);
	}
	
	/**企业号系统地址*/
	public String getLwechat_qyUrl() {
		return getValue("lwechat_qyUrl");
	}

	public void setLwechat_qyUrl(String lwechat_qyUrl) {
		setValue("lwechat_qyUrl", lwechat_qyUrl);
	}
	
	/**企业号待办是否启用*/
	public String getLwechat_qyEnable() {
		return getValue("lwechat_qyEnable");
	}

	public void setLwechat_qyEnable(String lwechat_qyEnable) {
		setValue("lwechat_qyEnable", lwechat_qyEnable);
	}
	
	/**企业微信通知地址*/
	public String getLwechat_qyNotifyUrl() {
		return getValue("lwechat_qyNotifyUrl");
	}

	public void setLwechat_qyNotifyUrl(String lwechat_qyNotifyUrl) {
		setValue("lwechat_qyNotifyUrl", lwechat_qyNotifyUrl);
	}
	
	/**企业号文件下载地址*/
	public String getLwechat_qyDownloadUrl() {
		return getValue("lwechat_qyDownloadUrl");
	}

	public void setLwechat_qyDownloadUrl(String lwechat_qyDownloadUrl) {
		setValue("lwechat_qyDownloadUrl", lwechat_qyDownloadUrl);
	}
	
	/**License*/
	public String getLwechat_license() {
		return getValue("lwechat_license");
	}

	public void setLwechat_license(String lwechat_license) {
		setValue("lwechat_license", lwechat_license);
	}
	
	/**lwechat_wyisSendTodo*/
	public String getLwechat_wyisSendTodo() {
		return getValue("lwechat_wyisSendTodo");
	}

	public void setLwechat_wyisSendTodo(String lwechat_wyisSendTodo) {
		setValue("lwechat_wyisSendTodo",lwechat_wyisSendTodo);
	}

	/**lwechat_wyisSendView*/
	public String getLwechat_wyisSendView() {
		return getValue("lwechat_wyisSendView");
		
	}

	public void setLwechat_wyisSendView(String lwechat_wyisSendView) {
		setValue("lwechat_wyisSendView",lwechat_wyisSendView);
	}

	/**lwechat_qyisSendTodo*/
	public String getLwechat_qyisSendTodo() {
		return getValue("lwechat_qyisSendTodo");
	}

	public void setLwechat_qyisSendTodo(String lwechat_qyisSendTodo) {
		setValue("lwechat_qyisSendTodo",lwechat_qyisSendTodo);
	}

	/**lwechat_qyisSendView*/
	public String getLwechat_qyisSendView() {
		return getValue("lwechat_qyisSendView");
	}

	public void setLwechat_qyisSendView(String lwechat_qyisSendView) {
		setValue("lwechat_qyisSendView",lwechat_qyisSendView);
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("third-wechat:module.third.wechat");
	}

}
