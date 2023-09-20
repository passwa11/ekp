package com.landray.kmss.third.ding.constant;

import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public interface DingConstant {
	
	public static final String DING_ENABLE = ResourceUtil
			.getKmssConfigString("kmss.third.ding.enabled");
	
	// 钉钉微应用中指定的企业ID，必选
	public static final String DING_CORPID = ResourceUtil
			.getKmssConfigString("kmss.third.ding.corpid");

	// 钉钉微应用中指定的企业secret，必选
	public static final String DING_CORPSECRET = ResourceUtil
			.getKmssConfigString("kmss.third.ding.corpsecret");
	
	public static final String DING_CALLBACK_URL = ResourceUtil
			.getKmssConfigString("kmss.third.ding.callbackurl");
	
	public static final String DING_TOKEN = ResourceUtil
			.getKmssConfigString("kmss.third.ding.token");

	public static final String DING_AESKEY = ResourceUtil
			.getKmssConfigString("kmss.third.ding.aeskey");

	public static final String DING_TODO_ENABLED = ResourceUtil
			.getKmssConfigString("kmss.third.ding.todo.enabled");
	public static final String DING_TODO_TYPE2_ENABLED = ResourceUtil
			.getKmssConfigString("kmss.third.ding.todo.type2.enabled");

	public static final String DING_PORXY = ResourceUtil.getKmssConfigString("third.phone.proxy.server");

	// 钉钉微应用首页地址中设置的域名，可选，不设置为EKP应用所在域名
	public static final String DING_NOTIFY_DOMAIN = ResourceUtil
			.getKmssConfigString("kmss.third.ding.domain");
	// 钉钉微应用ID，钉钉消息发送时必选，在此用于EKP中待办发送到钉钉时所在的应用ID
	public static final String DING_AGENTID = ResourceUtil
			.getKmssConfigString("kmss.third.ding.agentid");
	// 在此用于EKP中待办发送到钉钉时，用于设置消息标题的颜色，可选
	public static final String DING_TITLE_COLOR = ResourceUtil
			.getKmssConfigString("kmss.third.ding.title.color");

	// 将EKP中的组织机构同步到钉钉时，指定只同步EKP中某个机构或部门下的所有成员到钉钉
	public static final String DING_OMS_ROOT_ORG_ID = ResourceUtil
			.getKmssConfigString("kmss.third.ding.org.id");
	// 指定是否同步根机构到钉钉，需要和ding.oms.root.org.id中指定的值一同使用，可选，默认不同步
	public static final String DING_OMS_ROOT_FLAG = ResourceUtil
			.getKmssConfigString("kmss.third.ding.oms.root.flag");

	public static final String DING_OMS_APP_KEY = ResourceUtil
			.getKmssConfigString("kmss.third.ding.oms.app.key");

	// 将EKP中的组织机构同步到钉钉时，指定将EKP中的所有部门放在钉钉中根部门下
	public static final String DING_OMS_ROOT_DING_DEPTID = ResourceUtil
			.getKmssConfigString("kmss.third.ding.ding.deptid");
	
	public static final String DING_OMS_OUT_CREATEDEPTGROUP =  ResourceUtil
			.getKmssConfigString("kmss.third.ding.oms.createDeptGroup");
	
	//是否开启钉钉同步（钉钉到EKP）
	public static final String DING_OMS_IN_ENABLED = ResourceUtil
			.getKmssConfigString("kmss.third.ding.oms.in.enabled");
	
	//是否同步部门
	public static final String DING_OMS_IN_DEPT_ENABLED = ResourceUtil
			.getKmssConfigString("kmss.third.ding.oms.in.dept.enabled");
	
	// EKP中根机构ID，将钉钉部门同步到EKP时，指定钉钉根部门同步到EKP哪个根机构之下，为空则同步到最顶层
	public static final String DING_OMS_IN_ROOT_ORG_ID = ResourceUtil
				.getKmssConfigString("kmss.third.ding.in.org.id");
		
	// 钉钉访问地址前缀拼接（admin.do中无法或者则默认前缀是https://oapi.dingtalk.com）
	public static final String DING_PREFIX = "true"
			.equals(DingConfig.newInstance().getAttendanceDebug())
					? "https://pre-oapi.dingtalk.com"
					: (StringUtil.isNull(
							ResourceUtil.getKmssConfigString("kmss.ding.proxy"))
									? "https://oapi.dingtalk.com"
									: ResourceUtil.getKmssConfigString(
											"kmss.ding.proxy"));
	public static final String DING_API_PREFIX = StringUtil.isNull(ResourceUtil.getKmssConfigString("kmss.ding.proxy"))
			? "https://api.dingtalk.com": ResourceUtil.getKmssConfigString("kmss.ding.proxy");

}
