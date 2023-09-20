package com.landray.kmss.third.weixin.mutil.constant;

import com.landray.kmss.util.ResourceUtil;

public interface WxmutilConstant {
	// 企业ID，必选
	public static final String WXWORK_CORPID = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.corpid");

	// 企业secret，必选
	public static final String WXWORK_CORPSECRET = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.corpsecret");

	public static final String WXWORK_TOKEN = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.token");

	public static final String WXWORK_AESKEY = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.aeskey");

	public static final String WXWORK_TODO_ENABLED = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.todo.enabled");

	public static final String WXWORK_TODO_TYPE2_ENABLED = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.todo.type2.enabled");

	// 待办类型
	public static final String WXWORK_TODO_TYPE = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.notify.type");

	// 微应用首页地址中设置的域名，可选，不设置为EKP应用所在域名
	public static final String WXWORK_NOTIFY_DOMAIN = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.domain");

	// 微应用ID，消息发送时必选，在此用于EKP中待办发送时所在的应用ID
	public static final String WXWORK_AGENTID = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.agentid");

	// 将EKP中的组织机构同步到钉钉时，指定只同步EKP中某个机构或部门下的所有成员
	public static final String WXWORK_OMS_ROOT_ORG_ID = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.org.id");

	// 指定是否同步根机构，需要和ding.oms.root.org.id中指定的值一同使用，可选，默认不同步
	public static final String WXWORK_OMS_ROOT_FLAG = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.oms.root.flag");

	public static final String WXWORK_OMS_APP_KEY = ResourceUtil
			.getKmssConfigString("kmss.third.wxwork.oms.app.key");
	
	public static final String OAUTH_EKP_FLAG="wx2ekp";

	// 企业微信api请求前缀
	public static final String WXWORK_PREFIX = "https://qyapi.weixin.qq.com/cgi-bin";

	public static final String WXWORK_PROXY = ResourceUtil
			.getKmssConfigString("third.phone.proxy.server");

	public static final String CUSTOM_MSG_NEWS = "news";
	public static final String CUSTOM_MSG_TEXT = "text";
	public static final String CUSTOM_MSG_TEXTCARD = "textcard";
	public static final String BUTTON_VIEW = "view";
}
