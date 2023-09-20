package com.landray.kmss.third.weixin.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 微信企业号集成配置
 */
public class WeixinConfig extends BaseAppConfig {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WeixinConfig.class);

	public static WeixinConfig newInstance() {
		WeixinConfig config = null;
		try {
			config = new WeixinConfig();
		} catch (Exception e) {
			logger.error("", e);
		}
		return config;
	}

	public WeixinConfig() throws Exception {
		super();
		// ==================以下为默认值===================

		// 是否开启微信集成
		if (StringUtil.isNull(getWxEnabled())) {
			setWxEnabled("false");
		}
		
		//是否开启EKP免登陆
		if (StringUtil.isNull(getWxOauth2Enabled())) {
			setWxOauth2Enabled("false");
		}

		// 是否开启组织架构接出
		if (StringUtil.isNull(getWxOmsOutEnabled())) {
			setWxOmsOutEnabled("false");
		}

		// 是否开启消息推送
		if (StringUtil.isNull(getWxTodoEnabled())) {
			setWxTodoEnabled("false");
		}

		// 是否开启待阅推送
		if (StringUtil.isNull(getWxTodoType2Enabled())) {
			setWxTodoType2Enabled("false");
		}

		// 是否开启扫码登陆
		if (StringUtil.isNull(getWxPcScanLoginEnabled())) {
			setWxPcScanLoginEnabled("false");
		}

		// 是否同步根机构到微信企业号
		if (StringUtil.isNull(getWxOmsRootFlag())) {
			setWxOmsRootFlag("false");
		}

		// 微信集成组件未开启状态下,单点、消息推送、组织架构接出强制不开启
		if ("false".equals(getWxEnabled())) {
			setWxOauth2Enabled("false");
			setWxOmsOutEnabled("false");
			setWxTodoEnabled("false");
			setWxTodoType2Enabled("false");
			setWxPcScanLoginEnabled("false");
		}

	}

	@Override
	public void save() throws Exception {
		super.save();
		if("true".equals(getWxEnabled())){
			WeixinWorkConfig config = WeixinWorkConfig.newInstance();
			config.setWxEnabled("false");
			config.setWxOauth2Enabled("false");
			config.setWxOmsOutEnabled("false");
			config.setWxTodoEnabled("false");
			config.setWxTodoType2Enabled("false");
			config.setWxPcScanLoginEnabled("false");
			config.save();
		}
		// 日志记录
		if (UserOperHelper.allowLogOper("sysAppConfigUpdate", "*")) {
			UserOperHelper.setModelNameAndModelDesc(this.getClass().getName(),
					this.getModelDesc());
		}
	}

	// 是否开启微信集成
	public String getWxEnabled() {
		return getValue("wxEnabled");
	}

	public void setWxEnabled(String wxEnabled) {
		setValue("wxEnabled", wxEnabled);
	}

	// 微信企业号CorpID
	public String getWxCorpid() {
		return getValue("wxCorpid");
	}

	public void setWxCorpid(String wxCorpid) {
		setValue("wxCorpid", wxCorpid);
	}

	// 微信企业号Secret
	public String getWxCorpsecret() {
		return getValue("wxCorpsecret");
	}

	public void setWxCorpsecret(String wxCorpsecret) {
		setValue("wxCorpsecret", wxCorpsecret);
	}

	// 回调URL
	public String getWxCallbackurl() {
		return getValue("wxCallbackurl");
	}

	public void setWxCallbackurl(String wxCallbackurl) {
		setValue("wxCallbackurl", wxCallbackurl);
	}

	// 回调Token
	public String getWxToken() {
		return getValue("wxToken");
	}

	public void setWxToken(String wxToken) {
		setValue("wxToken", wxToken);
	}

	// 回调EncodingAESKey
	public String getWxAeskey() {
		return getValue("wxAeskey");
	}

	public void setWxAeskey(String wxAeskey) {
		setValue("wxAeskey", wxAeskey);
	}

	// 微信企业号微应用ID
	public String getWxAgentid() {
		return getValue("wxAgentid");
	}

	public void setWxAgentid(String wxAgentid) {
		setValue("wxAgentid", wxAgentid);
	}

	// 是否推送消息到微信
	public String getWxTodoEnabled() {
		return getValue("wxTodoEnabled");
	}

	public void setWxTodoEnabled(String wxTodoEnabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxTodoEnabled = "false";
		}
		setValue("wxTodoEnabled", wxTodoEnabled);
	}

	// 通知消息类型
	/*public String getWxNotifyType() {
		return getValue("wxNotifyType");
	}

	public void setWxNotifyType(String wxNotifyType) {
		setValue("wxNotifyType", wxNotifyType);
	}*/

	// EKP免登陆
	public String getWxOauth2Enabled() {
		return getValue("wxOauth2Enabled");
	}

	public void setWxOauth2Enabled(String wxOauth2Enabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxOauth2Enabled = "false";
		}
		setValue("wxOauth2Enabled", wxOauth2Enabled);
	}

	// PC扫码登陆
	public String getWxPcScanLoginEnabled() {
		return getValue("wxPcScanLoginEnabled");
	}

	public void setWxPcScanLoginEnabled(String wxPcScanLoginEnabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxPcScanLoginEnabled = "false";
		}
		setValue("wxPcScanLoginEnabled", wxPcScanLoginEnabled);
	}

	// 微信企业号待阅推送
	public String getWxTodoType2Enabled() {
		return getValue("wxTodoType2Enabled");
	}

	public void setWxTodoType2Enabled(String wxTodoType2Enabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxTodoType2Enabled = "false";
		}
		setValue("wxTodoType2Enabled", wxTodoType2Enabled);
	}

	// 微信企业号微应用首页地址中设置的域名
	public String getWxDomain() {
		return getValue("wxDomain");
	}

	public void setWxDomain(String wxDomain) {
		setValue("wxDomain", wxDomain);
	}

	// 组织架构接出到微信企业号
	public String getWxOmsOutEnabled() {
		return getValue("wxOmsOutEnabled");
	}

	public void setWxOmsOutEnabled(String wxOmsOutEnabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxOmsOutEnabled = "false";
		}
		setValue("wxOmsOutEnabled", wxOmsOutEnabled);
	}

	// EKP中根机构ID
	public String getWxOrgId() {
		return getValue("wxOrgId");
	}

	public void setWxOrgId(String wxOrgId) {
		setValue("wxOrgId", wxOrgId);
	}

	// 同步根机构到微信企业号
	public String getWxOmsRootFlag() {
		return getValue("wxOmsRootFlag");
	}

	public void setWxOmsRootFlag(String wxOmsRootFlag) {
		setValue("wxOmsRootFlag", wxOmsRootFlag);
	}

	public String getWxProxy() {
		return getValue("wxProxy");
	}

	public void setWxProxy(String wxProxy) {
		setValue("wxProxy", wxProxy);
	}
	
	/**
	 * @return
	 * 可选值为mobile|id|loginname,默认是loginname
	 */
	public String getWxLoginName() {
		return getValue("wxLoginName");
	}

	public void setWxLoginName(String wxLoginName) {
		setValue("wxLoginName", wxLoginName);
	}

	public String getWxRootId() {
		return getValue("wxRootId");
	}
	
	public void setWxRootId(String wxRootId) {
		setValue("wxRootId", wxRootId);
	}
	
	public String getWxOmsOrgPersonHandle() {
		return getValue("wxOmsOrgPersonHandle");
	}

	public void setWxOmsOrgPersonHandle(String wxOmsOrgPersonHandle) {
		setValue("wxOmsOrgPersonHandle", wxOmsOrgPersonHandle);
	}
	
	@Override
	public String getJSPUrl() {
		return "/third/weixin/weixin_config.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("third-weixin:third.wx.config.setting");
	}

}
