package com.landray.kmss.third.weixin.mutil.model;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.StringUtil;

/**
 * 企业微信集成配置
 */
public class WeixinWorkConfig extends ThirdWxBaseConfig {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WeixinWorkConfig.class);


	public static WeixinWorkConfig newInstance(String key) {
		WeixinWorkConfig config = null;
		try {
			config = new WeixinWorkConfig(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return config;
	}

	public static WeixinWorkConfig newInstance() {
		WeixinWorkConfig config = null;
		try {
			config = new WeixinWorkConfig(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return config;
	}

	public WeixinWorkConfig(String key) throws Exception {
		super(key);
		

	}

	public void save() throws Exception {
		super.save(key, getDataMap().get(key));
	}

	// 是否开启微信集成
	public String getWxEnabled() {
		return getValue("wxEnabled");
	}

	public void setWxEnabled(String wxworkEnabled) {
		setValue("wxEnabled", wxworkEnabled);
	}

	// 企业微信CorpID
	public String getWxCorpid() {
		return getValue("wxCorpid");
	}

	public void setWxCorpid(String wxworkCorpid) {
		setValue("wxCorpid", wxworkCorpid);
	}

	// 企业微信Secret
	public String getWxCorpsecret() {
		return getValue("wxCorpsecret");
	}

	public void setWxCorpsecret(String wxworkCorpsecret) {
		setValue("wxCorpsecret", wxworkCorpsecret);
	}

	// 回调URL
	public String getWxCallbackurl() {
		return getValue("wxCallbackurl");
	}

	public void setWxCallbackurl(String wxworkCallbackurl) {
		setValue("wxCallbackurl", wxworkCallbackurl);
	}

	// 回调Token
	public String getWxToken() {
		return getValue("wxToken");
	}

	public void setWxToken(String wxworkToken) {
		setValue("wxToken", wxworkToken);
	}

	// 回调EncodingAESKey
	public String getWxAeskey() {
		return getValue("wxAeskey");
	}

	public void setWxAeskey(String wxworkAeskey) {
		setValue("wxAeskey", wxworkAeskey);
	}


	// 是否推送待办消息到微信
	public String getWxTodoEnabled() {
		return getValue("wxTodoEnabled");
	}

	public void setWxTodoEnabled(String wxworkTodoEnabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxworkTodoEnabled = "false";
		}
		setValue("wxTodoEnabled", wxworkTodoEnabled);
	}

	// 待办通知消息类型
	public String getWxNotifyType() {
		return getValue("wxNotifyType");
	}

	public void setWxNotifyType(String wxworkNotifyType) {
		setValue("wxNotifyType", wxworkNotifyType);
	}

	//企业微信待办微应用ID
	public String getWxAgentid() {
		return getValue("wxAgentid");
	}

	public void setWxAgentid(String wxworkAgentid) {
		setValue("wxAgentid", wxworkAgentid);
	}

	// EKP免登陆
	public String getWxOauth2Enabled() {
		return getValue("wxOauth2Enabled");
	}

	public void setWxOauth2Enabled(String wxworkOauth2Enabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxworkOauth2Enabled = "false";
		}
		setValue("wxOauth2Enabled", wxworkOauth2Enabled);
	}

	// 企业微信待阅推送
	public String getWxTodoType2Enabled() {
		return getValue("wxTodoType2Enabled");
	}

	public void setWxTodoType2Enabled(String wxworkTodoType2Enabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxworkTodoType2Enabled = "false";
		}
		setValue("wxTodoType2Enabled", wxworkTodoType2Enabled);
	}

	//待阅消息类型
	public void setWxToReadNotifyType(String wxToReadNotifyType) {
		setValue("wxToReadNotifyType", wxToReadNotifyType);
	}

	public String getWxToReadNotifyType() {
		if (StringUtil.isNull(getValue("wxToReadNotifyType"))) {
			return getWxNotifyType();
		}
		return getValue("wxToReadNotifyType");
	}

	//企业微信待阅微应用ID
	public String getWxToReadAgentid() {
		if (StringUtil.isNull(getValue("wxToReadAgentid"))) {
			return getWxAgentid();
		}
		return getValue("wxToReadAgentid");
	}

	public void setWxToReadAgentid(String wxworkAgentid) {
		setValue("wxToReadAgentid", wxworkAgentid);
	}

	//使用按模块推送待阅
	public void setWxToReadPre(String wxToReadPre) {
		setValue("wxToReadPre", wxToReadPre);
	}

	public String getWxToReadPre() {
		return getValue("wxToReadPre");
	}

	// 企业微信微应用首页地址中设置的域名
	public String getWxDomain() {
		return getValue("wxDomain");
	}

	public void setWxDomain(String wxworkDomain) {
		setValue("wxDomain", wxworkDomain);
	}

	// 组织架构接出到企业微信
	public String getWxOmsOutEnabled() {
		return getValue("wxOmsOutEnabled");
	}

	public void setWxOmsOutEnabled(String wxworkOmsOutEnabled) {
		if (StringUtil.isNull(getWxEnabled()) || "false".equals(getWxEnabled())) {
			wxworkOmsOutEnabled = "false";
		}
		setValue("wxOmsOutEnabled", wxworkOmsOutEnabled);
	}

	// EKP中根机构ID
	public String getWxOrgId() {
		return getValue("wxOrgId");
	}

	public void setWxOrgId(String wxworkOrgId) {
		setValue("wxOrgId", wxworkOrgId);
	}

	// 同步根机构到企业微信
	public String getWxOmsRootFlag() {
		return getValue("wxOmsRootFlag");
	}

	public void setWxOmsRootFlag(String wxworkOmsRootFlag) {
		setValue("wxOmsRootFlag", wxworkOmsRootFlag);
	}

	public String getWxProxy() {
		return getValue("wxProxy");
	}

	public void setWxProxy(String wxworkProxy) {
		setValue("wxProxy", wxworkProxy);
	}
	
	// 云端企业微信的接口地址
	public String getWxApiUrl() {
		return StringUtil.isNotNull(getValue("wx.api.url"))
				? getValue("wx.api.url").trim() : "";
	}

	public void setWxApiUrl(String wxApiUrl) {
		setValue("wx.api.url", wxApiUrl);
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
	
	public String getWxPersonOrder() {
		return getValue("wxPersonOrder");
	}

	public String getWxPostEnabled() {
		return getValue("wxPostEnabled");
	}

	public void setWxPersonOrder(String wxPersonOrder) {
		setValue("wxPersonOrder", wxPersonOrder);
	}

	public String getWxDeptOrder(){
		return getValue("wxDeptOrder");
	}
	
	public void setWxDeptOrder(String wxDeptOrder){
		setValue("wxDeptOrder",wxDeptOrder);
	}
	
	public String getWxSSOAgentId() {
		return getValue("wxSSOAgentId");
	}

	public void setWxSSOAgentId(String wxSSOAgentId) {
		setValue("wxSSOAgentId",wxSSOAgentId);
	}
	
	public String getWxOfficePhone() {
		return getValue("wxOfficePhone");
	}

	public void setWxOfficePhone(String wxOfficePhone) {
		setValue("wxOfficePhone",wxOfficePhone);
	}

	public String getWxName() {
		return getValue("wxName");
	}


	/**
	 * 判断是否启用企业微信集成
	 * @throws Exception 
	 */
	public static boolean isWxWorkEnabled() throws Exception {
		Map<String, Map<String, String>> dataMap = newInstance().getDataMap();
		if (null == dataMap || dataMap.isEmpty()) {
			return false;
		}
		for (Map.Entry<String, Map<String, String>> map : dataMap.entrySet()) {
			if ("true".equals(map.getValue().get("wxEnabled"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>获取所有企业微信配置</p>
	 * @return
	 * @author 孙佳
	 */
	public static Map<String, Map<String, String>> getWxConfigDataMap() {
		Map<String, Map<String, String>> dataMap = null;
		try {
			dataMap = newInstance().getDataMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	/**
	 * <p>获取所有企业微信配置 key</p>
	 * @return
	 * @author 孙佳
	 */
	public static Set<String> getWxConfigDataKey() {
		Set<String> dataKey = null;
		try {
			dataKey = newInstance().getDataKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataKey;
	}
}
