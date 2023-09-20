package com.landray.kmss.third.weixin.work.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

/**
 * 企业微信集成配置
 */
public class WeixinWorkConfig extends BaseAppConfig {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinWorkConfig.class);

	public static WeixinWorkConfig newInstance() {
		WeixinWorkConfig config = null;
		try {
			config = new WeixinWorkConfig();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return config;
	}

	public WeixinWorkConfig() throws Exception {
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

		// 是否开启消息推送
		if (StringUtil.isNull(getWxTodoEnabled())) {
			setWxTodoEnabled("false");
		}

		// 是否开启待阅推送
		if (StringUtil.isNull(getWxTodoType2Enabled())) {
			setWxTodoType2Enabled("false");
		}

		// 是否同步根机构到企业微信
		if (StringUtil.isNull(getWxOmsRootFlag())) {
			setWxOmsRootFlag("false");
		}

		// 是否开启扫码登陆
		if (StringUtil.isNull(getWxPcScanLoginEnabled())) {
			setWxPcScanLoginEnabled("false");
		}

		// 是否开启组织架构接出
		// if (StringUtil.isNull(getWxOmsOutEnabled())) {
		// setWxOmsOutEnabled("false");
		// }
		// 同步方式
		if (StringUtil.isNull(getSyncSelection())) {
			// 判断旧开关wxOmsOutEnabled
			String out = getWxOmsOutEnabled();
			if (StringUtil.isNotNull(out)) {
				if ("true".equalsIgnoreCase(out)) {
					setSyncSelection("toWx");
				} else {
					setSyncSelection("fromWx");
				}
			} else {
				setSyncSelection("notSyn");
			}

		}

		if (StringUtil.isNull(getWx2ekpEkpOrgHandle())) {
			setWx2ekpEkpOrgHandle("noHandle");
		}
		// 微信集成组件未开启状态下,单点、消息推送、组织架构接出强制不开启
		if ("false".equals(getWxEnabled())) {
			setWxOauth2Enabled("false");
			setWxOmsOutEnabled("false");
			setWxTodoEnabled("false");
			setWxTodoType2Enabled("false");
			setWxPcScanLoginEnabled("false");
		}

		// ---------------同步字段默认选择--------------
		if (StringUtil.isNull(getWx2ekpAlias())) {
			setWx2ekpAlias("alias"); // 别名
		}

		if (StringUtil.isNull(getWx2ekpEmail())) {
			setWx2ekpEmail("email");
		}

		if (StringUtil.isNull(getWx2ekpSex())) {
			setWx2ekpSex("gender");
		}

		if (StringUtil.isNull(getWx2ekpTel())) {
			setWx2ekpTel("telephone");
		}

		if (StringUtil.isNull(getWx2ekpTel())) {
			setWx2ekpTel("telephone");
		}

		// ekp到企业微信---同步字段默认选择
		if (StringUtil.isNull(getOrg2wxWorkAlias())) {
			setOrg2wxWorkAlias("fdNickName");
		}

		if (StringUtil.isNull(getOrg2wxWorkSex())) {
			setOrg2wxWorkSex("fdSex");
		}

		if (StringUtil.isNull(getOrg2wxWorkTel())) {
			setOrg2wxWorkTel("fdWorkPhone");
		}
		if (StringUtil.isNull(getOrg2wxWorkEmail())) {
			setOrg2wxWorkEmail("fdEmail");
		}

		if (StringUtil.isNull(getOrg2wxWorkBizEmail())) {
			setOrg2wxWorkBizEmail("fdEmail");
		}
		if (StringUtil.isNull(getOrg2wxWorkPosition())) {
			setOrg2wxWorkPosition("hbmPosts");
		}

	}

	// 自定义明细查询
	public static List<ThirdWeixinConfigCustom> getCustomData() {

		List<ThirdWeixinConfigCustom> list = new ArrayList<ThirdWeixinConfigCustom>();
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("fdField like :fdField and fdKey = :fdKey");

		hql.setParameter("fdKey",
				"com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
		hql.setParameter("fdField", "org2wxWork.custom.%");

		ISysAppConfigService service = (ISysAppConfigService) SpringBeanUtil
				.getBean("sysAppConfigService");

		HashMap<Integer, ThirdWeixinConfigCustom> map = new HashMap<Integer, ThirdWeixinConfigCustom>();

		try {
			List<SysAppConfig> allCustom = service.findList(hql);
			for (SysAppConfig item : allCustom) {
				// 提取index
				String field = item.getFdField();
				// System.out.println("-----field:" + field);
				String _index = field.substring(field.indexOf("[") + 1,
						field.indexOf("]"));
				// System.out.println("-----index:" + _index);
				Integer index = Integer.valueOf(_index);
				ThirdWeixinConfigCustom custom = null;
				if (map.containsKey(index)) {
					custom = map.get(index);
				} else {
					custom = new ThirdWeixinConfigCustom();
				}
				if (field.contains("title")) {
					custom.setTitle(item.getFdValue());
				} else if (field.contains("synWay")) {
					custom.setSynWay(item.getFdValue());
				} else if (field.contains("target")) {
					custom.setTarget(item.getFdValue());
				}
				map.put(index, custom);
			}
			for (Integer key : map.keySet()) {
				list.add(map.get(key));
			}
			// System.out.println(allCustom.size());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public static void deleteCustomData() {
		List<ThirdWeixinConfigCustom> list = new ArrayList<ThirdWeixinConfigCustom>();
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("fdField like :fdField and fdKey = :fdKey");

		hql.setParameter("fdKey",
				"com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
		hql.setParameter("fdField", "org2wxWork.custom.%");

		ISysAppConfigService service = (ISysAppConfigService) SpringBeanUtil
				.getBean("sysAppConfigService");
		try {
			List<SysAppConfig> allCustom = service.findList(hql);
			TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				for (SysAppConfig item : allCustom) {
					// service.delete(item);
					// service.delete(item.getFdId());

					item.setFdValue(null);
					service.update(item);
				}
				TransactionUtils.commit(status);
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
				if (status != null) {
					TransactionUtils.rollback(status);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void save() throws Exception {
		if("true".equals(getWxEnabled())){
			WeixinConfig config = WeixinConfig.newInstance();
			config.setWxEnabled("false");
			config.setWxOauth2Enabled("false");
			config.setWxOmsOutEnabled("false");
			config.setWxTodoEnabled("false");
			config.setWxTodoType2Enabled("false");
			config.setWxPcScanLoginEnabled("false");
			config.save();
		}
		// 兼容旧数据
		if (StringUtil.isNotNull(getWxPersonOrder())) {
			setWxPersonOrder("");
		}
		if (StringUtil.isNotNull(getWxDeptOrder())) {
			setWxDeptOrder("");
		}
		if (StringUtil.isNotNull(getWxPostEnabled())) {
			setWxPostEnabled("");
		}
		if (StringUtil.isNotNull(getWxOfficePhone())) {
			setWxOfficePhone("");
		}
		if (StringUtil.isNotNull(getWxOmsOutEnabled())) {
			setWxOmsOutEnabled("");
		}

		if (UserOperHelper.allowLogOper("sysAppConfigUpdate", "*")) {
			UserOperHelper.setModelNameAndModelDesc(this.getClass().getName(),
					this.getModelDesc());
		}
		WxworkUtils.getWxworkApiService().expireAllToken();
		WxworkUtils.getWxworkApiService().expireAllJsapiTicket();
		super.save();
	}

	public void superSave() throws Exception {
		super.save();
	}

	// 是否开启微信直播
	public String getWxLivingEnabled() {
		return getValue("wxLivingEnabled");
	}
	public void setWxLivingEnabled(String wxLivingEnabled) {
		setValue("wxLivingEnabled", wxLivingEnabled);
	}

	//是否开启微信集成
	public String getWxEnabled() {
		return getValue("wxEnabled");
	}

	public void setWxEnabled(String wxworkEnabled) {
		setValue("wxEnabled", wxworkEnabled);
	}

	// 人员置为无效处理方式 0 删除 1禁用
	public String getWxOmsPersonDisableHandle() {
		return getValue("wxOmsPersonDisableHandle");
	}

	public void setWxOmsPersonDisableHandle(String wxworkEnabled) {
		setValue("wxOmsPersonDisableHandle", wxworkEnabled);
	}

	// 企业微信CorpID
	public String getWxCorpid() {
		return getValue("wxCorpid");
	}

	public void setWxCorpid(String wxworkCorpid) {
		setValue("wxCorpid", wxworkCorpid);
	}

	// 云端企业微信的接口地址
	public String getWxApiUrl() {
		return StringUtil.isNotNull(getValue("wx.api.url"))
				? getValue("wx.api.url").trim() : "";
	}

	public void setWxApiUrl(String wxApiUrl) {
		setValue("wx.api.url", wxApiUrl);
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
	/*public String getWxNotifyType() {
		return getValue("wxNotifyType");
	}

	public void setWxNotifyType(String wxworkNotifyType) {
		setValue("wxNotifyType", wxworkNotifyType);
	}*/

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
	/*public void setWxToReadNotifyType(String wxToReadNotifyType) {
		setValue("wxToReadNotifyType", wxToReadNotifyType);
	}

	public String getWxToReadNotifyType() {
		if (StringUtil.isNull(getValue("wxToReadNotifyType"))) {
			return getWxNotifyType();
		}
		return getValue("wxToReadNotifyType");
	}*/

	//企业微信待阅微应用ID
	public String getWxToReadAgentid() {
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
		if ("toWx".equals(getSyncSelection())) {
			return "true";
		}
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

	@Override
	public String getJSPUrl() {
		return "/third/weixin/work/weixin_config.jsp";
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

	/*
	 * 同步方式 接入：fromWx；接出：toWx； 不同步：notSyn
	 */
	public String getSyncSelection() {
		return getValue("syncSelection");
	}

	public void setSyncSelection(String syncSelection) {
		setValue("syncSelection", syncSelection);
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
	
	public String getWxSSOAttendSecret() {
		return getValue("wxSSOAttendSecret");
	}

	public void setWxSSOAttendSecret(String wxSSOAttendSecret) {
		setValue("wxSSOAttendSecret", wxSSOAttendSecret);
	}
	
	public String getWxOfficePhone() {
		return getValue("wxOfficePhone");
	}

	public void setWxOfficePhone(String wxOfficePhone) {
		setValue("wxOfficePhone",wxOfficePhone);
	}

	public String getWxPostEnabled() {
		return getValue("wxPostEnabled");
	}

	public void setWxPostEnabled(String wxPostEnabled) {
		setValue("wxPostEnabled", wxPostEnabled);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString(
				"third-weixin-work:third.weixin.work.config.setting");
	}
	
	public String getWxCalendarEnabled() {
		return getValue("wxCalendarEnabled");
	}

	public void setWxCalendarEnabled(String wxCalendarEnabled) {
		setValue("wxCalendarEnabled", wxCalendarEnabled);
	}

	public String getWxNotifySendType() {
		String wxNotifySendType = getValue("wxNotifySendType");
		if (StringUtil.isNull(wxNotifySendType)) {
			return "news";
		}
		return wxNotifySendType;
	}

	public void setWxNotifySendType(String wxNotifySendType) {
		setValue("wxNotifySendType", wxNotifySendType);
	}

	@Override
    protected String getValue(String name) {
		String value = getDataMap().get(name);
		if ("wxNotifySendType".equals(name) && StringUtil.isNull(value)) {
			value = "news";
		}
		return value;
	}

	public String getWxworkSize() {
		return getValue("wxWorkSize");
	}
	
	public String getWxAuthCheckEnabled() {
		return getValue("wxAuthCheckEnabled");
	}

	public String getWxAuchCheckToken() {
		return getValue("wxAuchCheckToken");
	}

	public String getWxAuchCheckAESKey() {
		return getValue("wxAuchCheckAESKey");
	}

	public String getWxUpdatePassToken() {
		return getValue("wxUpdatePassToken");
	}

	public String getWxUpdatePassAESKey() {
		return getValue("wxUpdatePassAESKey");
	}

	// ----------------页面通讯录同步字段 start------------

	public String getOrg2wxWorkNameSynWay() {
		return getValue("org2wxWork.name.synWay");
	}

	public void setOrg2wxWorkNameSynWay(String org2wxWorkNameSynWay) {
		setValue("org2wxWork.name.synWay", org2wxWorkNameSynWay);
	}

	public String getOrg2wxWorkName() {
		return getValue("org2wxWork.name");
	}

	public void setOrg2wxWorkName(String Org2wxWorkName) {
		setValue("org2wxWork.name", Org2wxWorkName);
	}

	public String getOrg2wxWorkMobileSynWay() {
		return getValue("org2wxWork.mobile.synWay");
	}

	public void setOrg2wxWorkMobileSynWay(String org2wxWorkMobileSynWay) {
		setValue("org2wxWork.mobile.synWay", org2wxWorkMobileSynWay);
	}

	public String getOrg2wxWorkMobile() {
		return getValue("org2wxWork.mobile");
	}

	public void setOrg2wxWorkMobile(String Org2wxWorkMobile) {
		setValue("org2wxWork.mobile", Org2wxWorkMobile);
	}

	public String getOrg2wxWorkUseridSynWay() {
		return getValue("org2wxWork.userid.synWay");
	}

	public void setOrg2wxWorkUseridSynWay(String org2wxWorkUseridSynWay) {
		setValue("org2wxWork.userid.synWay", org2wxWorkUseridSynWay);
	}

	public String getOrg2wxWorkUserid() {
		return getValue("org2wxWork.userid");
	}

	public void setOrg2wxWorkUserid(String Org2wxWorkUserid) {
		setValue("org2wxWork.userid", Org2wxWorkUserid);
	}

	public String getOrg2wxWorkDepartmentSynWay() {
		return getValue("org2wxWork.department.synWay");
	}

	public void
			setOrg2wxWorkDepartmentSynWay(String org2wxWorkDepartmentSynWay) {
		setValue("org2wxWork.department.synWay", org2wxWorkDepartmentSynWay);
	}

	public String getOrg2wxWorkDepartment() {
		return getValue("org2wxWork.department");
	}

	public void setOrg2wxWorkDepartment(String Org2wxWorkDepartment) {
		setValue("org2wxWork.department", Org2wxWorkDepartment);
	}

	public String getOrg2wxWorkOrderInDeptsSynWay() {
		return getValue("org2wxWork.orderInDepts.synWay");
	}

	public void setOrg2wxWorkOrderInDeptsSynWay(
			String org2wxWorkOrderInDeptsSynWay) {
		setValue("org2wxWork.orderInDepts.synWay",
				org2wxWorkOrderInDeptsSynWay);
	}

	public String getOrg2wxWorkOrderInDepts() {
		return getValue("org2wxWork.orderInDepts");
	}

	public void setOrg2wxWorkOrderInDepts(String Org2wxWorkOrderInDepts) {
		setValue("org2wxWork.orderInDepts", Org2wxWorkOrderInDepts);
	}

	public String getOrg2wxWorkAliasSynWay() {
		return getValue("org2wxWork.alias.synWay");
	}

	public void setOrg2wxWorkAliasSynWay(String org2wxWorkAliasSynWay) {
		setValue("org2wxWork.alias.synWay", org2wxWorkAliasSynWay);
	}

	public String getOrg2wxWorkAlias() {
		return getValue("org2wxWork.alias");
	}

	public void setOrg2wxWorkAlias(String Org2wxWorkAlias) {
		setValue("org2wxWork.alias", Org2wxWorkAlias);
	}

	public String getOrg2wxWorkSexSynWay() {
		return getValue("org2wxWork.sex.synWay");
	}

	public void setOrg2wxWorkSexSynWay(String org2wxWorkSexSynWay) {
		setValue("org2wxWork.sex.synWay", org2wxWorkSexSynWay);
	}

	public String getOrg2wxWorkSex() {
		return getValue("org2wxWork.sex");
	}

	public void setOrg2wxWorkSex(String Org2wxWorkSex) {
		setValue("org2wxWork.sex", Org2wxWorkSex);
	}

	public String getOrg2wxWorkTelSynWay() {
		return getValue("org2wxWork.tel.synWay");
	}

	public void setOrg2wxWorkTelSynWay(String org2wxWorkTelSynWay) {
		setValue("org2wxWork.tel.synWay", org2wxWorkTelSynWay);
	}

	public String getOrg2wxWorkTel() {
		return getValue("org2wxWork.tel");
	}

	public void setOrg2wxWorkTel(String Org2wxWorkTel) {
		setValue("org2wxWork.tel", Org2wxWorkTel);
	}

	public String getOrg2wxWorkEmailSynWay() {
		return getValue("org2wxWork.email.synWay");
	}

	public void setOrg2wxWorkEmailSynWay(String org2wxWorkEmailSynWay) {
		setValue("org2wxWork.email.synWay", org2wxWorkEmailSynWay);
	}

	public String getOrg2wxWorkEmail() {
		return getValue("org2wxWork.email");
	}

	public void setOrg2wxWorkEmail(String Org2wxWorkEmail) {
		setValue("org2wxWork.email", Org2wxWorkEmail);
	}

	public String getOrg2wxWorkBizEmailSynWay() {
		return getValue("org2wxWork.bizEmail.synWay");
	}

	public void setOrg2wxWorkBizEmailSynWay(String org2wxWorkBizEmailSynWay) {
		setValue("org2wxWork.bizEmail.synWay", org2wxWorkBizEmailSynWay);
	}

	public String getOrg2wxWorkBizEmail() {
		return getValue("org2wxWork.bizEmail");
	}

	public void setOrg2wxWorkBizEmail(String Org2wxWorkBizEmail) {
		setValue("org2wxWork.bizEmail", Org2wxWorkBizEmail);
	}

	public String getOrg2wxWorkWorkPlaceSynWay() {
		return getValue("org2wxWork.workPlace.synWay");
	}

	public void setOrg2wxWorkWorkPlaceSynWay(String org2wxWorkWorkPlaceSynWay) {
		setValue("org2wxWork.workPlace.synWay", org2wxWorkWorkPlaceSynWay);
	}

	public String getOrg2wxWorkWorkPlace() {
		return getValue("org2wxWork.workPlace");
	}

	public void setOrg2wxWorkWorkPlace(String Org2wxWorkWorkPlace) {
		setValue("org2wxWork.workPlace", Org2wxWorkWorkPlace);
	}

	public String getOrg2wxWorkUsNameSynWay() {
		return getValue("org2wxWork.usName.synWay");
	}

	public void setOrg2wxWorkUsNameSynWay(String org2wxWorkUsNameSynWay) {
		setValue("org2wxWork.usName.synWay", org2wxWorkUsNameSynWay);
	}

	public String getOrg2wxWorkUsName() {
		return getValue("org2wxWork.usName");
	}

	public void setOrg2wxWorkUsName(String Org2wxWorkUsName) {
		setValue("org2wxWork.usName", Org2wxWorkUsName);
	}

	public String getOrg2wxWorkPositionSynWay() {
		return getValue("org2wxWork.position.synWay");
	}

	public void setOrg2wxWorkPositionSynWay(String org2wxWorkPositionSynWay) {
		setValue("org2wxWork.position.synWay", org2wxWorkPositionSynWay);
	}

	public String getOrg2wxWorkPosition() {
		return getValue("org2wxWork.position");
	}

	public void setOrg2wxWorkPosition(String Org2wxWorkPosition) {
		setValue("org2wxWork.position", Org2wxWorkPosition);
	}

	public String getOrg2wxWorkIsLeaderInDeptSynWay() {
		return getValue("org2wxWork.isLeaderInDept.synWay");
	}

	public void setOrg2wxWorkIsLeaderInDeptSynWay(
			String org2wxWorkIsLeaderInDeptSynWay) {
		setValue("org2wxWork.isLeaderInDept.synWay",
				org2wxWorkIsLeaderInDeptSynWay);
	}

	public String getOrg2wxWorkIsLeaderInDept() {
		return getValue("org2wxWork.isLeaderInDept");
	}

	public void setOrg2wxWorkIsLeaderInDept(String Org2wxWorkIsLeaderInDept) {
		setValue("org2wxWork.isLeaderInDept", Org2wxWorkIsLeaderInDept);
	}

	public String getOrg2wxWorkDeptNameSynWay() {
		return getValue("org2wxWork.dept.name.synWay");
	}

	public void setOrg2wxWorkDeptNameSynWay(String org2wxWorkDeptNameSynWay) {
		setValue("org2wxWork.dept.name.synWay", org2wxWorkDeptNameSynWay);
	}

	public String getOrg2wxWorkDeptName() {
		return getValue("org2wxWork.dept.name");
	}

	public void setOrg2wxWorkDeptName(String Org2wxWorkDeptName) {
		setValue("org2wxWork.dept.name", Org2wxWorkDeptName);
	}

	public String getOrg2wxWorkDeptParentDeptSynWay() {
		return getValue("org2wxWork.dept.parentDept.synWay");
	}

	public void setOrg2wxWorkDeptParentDeptSynWay(
			String org2wxWorkDeptParentDeptSynWay) {
		setValue("org2wxWork.dept.parentDept.synWay",
				org2wxWorkDeptParentDeptSynWay);
	}

	public String getOrg2wxWorkDeptParentDept() {
		return getValue("org2wxWork.dept.parentDept");
	}

	public void setOrg2wxWorkDeptParentDept(String Org2wxWorkDeptParentDept) {
		setValue("org2wxWork.dept.parentDept", Org2wxWorkDeptParentDept);
	}

	public String getOrg2wxWorkDeptOrderSynWay() {
		return getValue("org2wxWork.dept.order.synWay");
	}

	public void setOrg2wxWorkDeptOrderSynWay(String org2wxWorkDeptOrderSynWay) {
		setValue("org2wxWork.dept.order.synWay", org2wxWorkDeptOrderSynWay);
	}

	public String getOrg2wxWorkDeptOrder() {
		return getValue("org2wxWork.dept.order");
	}

	public void setOrg2wxWorkDeptOrder(String Org2wxWorkDeptOrder) {
		setValue("org2wxWork.dept.order", Org2wxWorkDeptOrder);
	}

	public String getOrg2wxWorkDeptUSNameSynWay() {
		return getValue("org2wxWork.dept.USName.synWay");
	}

	public void
			setOrg2wxWorkDeptUSNameSynWay(String org2wxWorkDeptUSNameSynWay) {
		setValue("org2wxWork.dept.USName.synWay", org2wxWorkDeptUSNameSynWay);
	}

	public String getOrg2wxWorkDeptUSName() {
		return getValue("org2wxWork.dept.USName");
	}

	public void setOrg2wxWorkDeptUSName(String Org2wxWorkDeptUSName) {
		setValue("org2wxWork.dept.USName", Org2wxWorkDeptUSName);
	}

	// ----------------页面通讯录同步字段 end--------------

	// ----------------页面通讯录企业微信到ekp 同步字段 start--------------

	public String getWx2ekpEkpOrgHandle() {
		return getValue("wx2ekp.ekpOrgHandle");
	}

	public void setWx2ekpEkpOrgHandle(String wx2ekpEkpOrgHandle) {
		setValue("wx2ekp.ekpOrgHandle", wx2ekpEkpOrgHandle);
	}

	public String getWx2ekpWxRootId() {
		return getValue("wx2ekp.wxRootId");
	}

	public void setWx2ekpWxRootId(String wxRootId) {
		setValue("wx2ekp.wxRootId", wxRootId);
	}

	public String getWx2ekpEkpOrgId() {
		return getValue("wx2ekp.ekpOrgId");
	}

	public void setWx2ekpEkpOrgId(String ekpOrgId) {
		setValue("wx2ekp.ekpOrgId", ekpOrgId);
	}

	public String getWx2ekpName() {
		return getValue("wx2ekp.name");
	}

	public void setWx2ekpName(String wx2ekpName) {
		setValue("wx2ekp.name", wx2ekpName);
	}

	public String getWx2ekpNameSynWay() {
		return getValue("wx2ekp.name.synWay");
	}

	public void setWx2ekpNameSynWay(String nameSynWay) {
		setValue("wx2ekp.name.synWay", nameSynWay);
	}

	public String getWx2ekpLoginName() {
		return getValue("wx2ekp.loginName");
	}

	public void setWx2ekpLoginName(String wx2ekpLoginName) {
		setValue("wx2ekp.loginName", wx2ekpLoginName);
	}

	public String getWx2ekpLoginNameSynWay() {
		return getValue("wx2ekp.loginName.synWay");
	}

	public void setWx2ekpLoginNameSynWay(String loginNameSynWay) {
		setValue("wx2ekp.loginName.synWay", loginNameSynWay);
	}

	public String getWx2ekpMobile() {
		return getValue("wx2ekp.mobile");
	}

	public void setWx2ekpMobile(String wx2ekpMobile) {
		setValue("wx2ekp.mobile", wx2ekpMobile);
	}

	public String getWx2ekpMobileSynWay() {
		return getValue("wx2ekp.mobile.synWay");
	}

	public void setWx2ekpMobileSynWay(String mobileSynWay) {
		setValue("wx2ekp.mobile.synWay", mobileSynWay);
	}

	public String getWx2ekpDepartment() {
		return getValue("wx2ekp.department");
	}

	public void setWx2ekpDepartment(String wx2ekpDepartment) {
		setValue("wx2ekp.department", wx2ekpDepartment);
	}

	public String getWx2ekpDepartmentSynWay() {
		return getValue("wx2ekp.department.synWay");
	}

	public void setWx2ekpDepartmentSynWay(String departmentSynWay) {
		setValue("wx2ekp.department.synWay", departmentSynWay);
	}

	public String getWx2ekpOrderInMainDept() {
		return getValue("wx2ekp.orderInMainDept");
	}

	public void setWx2ekpOrderInMainDept(String wx2ekpOrderInMainDept) {
		setValue("wx2ekp.orderInMainDept", wx2ekpOrderInMainDept);
	}

	public String getWx2ekpOrderInMainDeptSynWay() {
		return getValue("wx2ekp.orderInMainDept.synWay");
	}

	public void setWx2ekpOrderInMainDeptSynWay(String orderInMainDeptSynWay) {
		setValue("wx2ekp.orderInMainDept.synWay", orderInMainDeptSynWay);
	}

	public String getWx2ekpAlias() {
		return getValue("wx2ekp.alias");
	}

	public void setWx2ekpAlias(String wx2ekpAlias) {
		setValue("wx2ekp.alias", wx2ekpAlias);
	}

	public String getWx2ekpAliasSynWay() {
		return getValue("wx2ekp.alias.synWay");
	}

	public void setWx2ekpAliasSynWay(String aliasSynWay) {
		setValue("wx2ekp.alias.synWay", aliasSynWay);
	}

	public String getWx2ekpSex() {
		return getValue("wx2ekp.sex");
	}

	public void setWx2ekpSex(String wx2ekpSex) {
		setValue("wx2ekp.sex", wx2ekpSex);
	}

	public String getWx2ekpSexSynWay() {
		return getValue("wx2ekp.sex.synWay");
	}

	public void setWx2ekpSexSynWay(String sexSynWay) {
		setValue("wx2ekp.sex.synWay", sexSynWay);
	}

	public String getWx2ekpTel() {
		return getValue("wx2ekp.tel");
	}

	public void setWx2ekpTel(String wx2ekpTel) {
		setValue("wx2ekp.tel", wx2ekpTel);
	}

	public String getWx2ekpTelSynWay() {
		return getValue("wx2ekp.tel.synWay");
	}

	public void setWx2ekpTelSynWay(String telSynWay) {
		setValue("wx2ekp.tel.synWay", telSynWay);
	}

	public String getWx2ekpEmail() {
		return getValue("wx2ekp.email");
	}

	public void setWx2ekpEmail(String wx2ekpEmail) {
		setValue("wx2ekp.email", wx2ekpEmail);
	}

	public String getWx2ekpEmailSynWay() {
		return getValue("wx2ekp.email.synWay");
	}

	public void setWx2ekpEmailSynWay(String emailSynWay) {
		setValue("wx2ekp.email.synWay", emailSynWay);
	}

	public String getWx2ekpWorkPlace() {
		return getValue("wx2ekp.workPlace");
	}

	public void setWx2ekpWorkPlace(String wx2ekpWorkPlace) {
		setValue("wx2ekp.workPlace", wx2ekpWorkPlace);
	}

	public String getWx2ekpWorkPlaceSynWay() {
		return getValue("wx2ekp.workPlace.synWay");
	}

	public void setWx2ekpWorkPlaceSynWay(String workPlaceSynWay) {
		setValue("wx2ekp.workPlace.synWay", workPlaceSynWay);
	}

	public String getWx2ekpDefaultLang() {
		return getValue("wx2ekp.defaultLang");
	}

	public void setWx2ekpDefaultLang(String wx2ekpDefaultLang) {
		setValue("wx2ekp.defaultLang", wx2ekpDefaultLang);
	}

	public String getWx2ekpDefaultLangSynWay() {
		return getValue("wx2ekp.defaultLang.synWay");
	}

	public void setWx2ekpDefaultLangSynWay(String defaultLangSynWay) {
		setValue("wx2ekp.defaultLang.synWay", defaultLangSynWay);
	}

	public String getWx2ekpKeyword() {
		return getValue("wx2ekp.keyword");
	}

	public void setWx2ekpKeyword(String wx2ekpKeyword) {
		setValue("wx2ekp.keyword", wx2ekpKeyword);
	}

	public String getWx2ekpKeywordSynWay() {
		return getValue("wx2ekp.keyword.synWay");
	}

	public void setWx2ekpKeywordSynWay(String keywordSynWay) {
		setValue("wx2ekp.keyword.synWay", keywordSynWay);
	}

	public String getWx2ekpShort() {
		return getValue("wx2ekp.short");
	}

	public void setWx2ekpShort(String wx2ekpShort) {
		setValue("wx2ekp.short", wx2ekpShort);
	}

	public String getWx2ekpShortSynWay() {
		return getValue("wx2ekp.short.synWay");
	}

	public void setWx2ekpShortSynWay(String shortSynWay) {
		setValue("wx2ekp.short.synWay", shortSynWay);
	}

	public String getWx2ekpIsBussiness() {
		return getValue("wx2ekp.isBussiness");
	}

	public void setWx2ekpIsBussiness(String wx2ekpIsBussiness) {
		setValue("wx2ekp.isBussiness", wx2ekpIsBussiness);
	}

	public String getWx2ekpIsBussinessSynWay() {
		return getValue("wx2ekp.isBussiness.synWay");
	}

	public void setWx2ekpIsBussinessSynWay(String isBussinessSynWay) {
		setValue("wx2ekp.isBussiness.synWay", isBussinessSynWay);
	}

	public String getWx2ekpremark() {
		return getValue("wx2ekp.remark");
	}

	public void setWx2ekpremark(String wx2ekpremark) {
		setValue("wx2ekp.remark", wx2ekpremark);
	}

	public String getWx2ekpremarkSynWay() {
		return getValue("wx2ekp.remark.synWay");
	}

	public void setWx2ekpremarkSynWay(String remarkSynWay) {
		setValue("wx2ekp.remark.synWay", remarkSynWay);
	}

	public String getWx2ekpDeptName() {
		return getValue("wx2ekp.dept.name");
	}

	public void setWx2ekpDeptName(String deptWx2ekpName) {
		setValue("wx2ekp.dept.name", deptWx2ekpName);
	}

	public String getWx2ekpDeptNameSynWay() {
		return getValue("wx2ekp.dept.name.synWay");
	}

	public void setWx2ekpDeptNameSynWay(String deptNameSynWay) {
		setValue("wx2ekp.dept.name.synWay", deptNameSynWay);
	}

	public String getWx2ekpDeptParentDept() {
		return getValue("wx2ekp.dept.parentDept");
	}

	public void setWx2ekpDeptParentDept(String deptWx2ekpParentDept) {
		setValue("wx2ekp.dept.parentDept", deptWx2ekpParentDept);
	}

	public String getWx2ekpDeptParentDeptSynWay() {
		return getValue("wx2ekp.dept.parentDept.synWay");
	}

	public void setWx2ekpDeptParentDeptSynWay(String deptParentDeptSynWay) {
		setValue("wx2ekp.dept.parentDept.synWay", deptParentDeptSynWay);
	}

	public String getWx2ekpDeptOrder() {
		return getValue("wx2ekp.dept.order");
	}

	public void setWx2ekpDeptOrder(String deptWx2ekpOrder) {
		setValue("wx2ekp.dept.order", deptWx2ekpOrder);
	}

	public String getWx2ekpDeptOrderSynWay() {
		return getValue("wx2ekp.dept.order.synWay");
	}

	public void setWx2ekpDeptOrderSynWay(String deptOrderSynWay) {
		setValue("wx2ekp.dept.order.synWay", deptOrderSynWay);
	}

	// ----------------页面通讯录企业微信到ekp 同步字段 end--------------
	public String getWx2ekpDeptLeader() {
		return getValue("wx2ekp.dept.leader");
	}

	public void setWx2ekpDeptLeader(String deptWx2ekpLeader) {
		setValue("wx2ekp.dept.leader", deptWx2ekpLeader);
	}

	public String getWx2ekpDeptLeaderSynWay() {
		return getValue("wx2ekp.dept.leader.synWay");
	}

	public void setWx2ekpDeptLeaderSynWay(String deptLeaderSynWay) {
		setValue("wx2ekp.dept.leader.synWay", deptLeaderSynWay);
	}

	public String getEkp2wxNoSynPersonIds() {
		return getValue("ekp2wx.noSyn.person.ids");
	}

	private String wxPayEnable;
	private String wxPayMchID;
	private String wxPayKey;
	private String wxPayCertFilePath;

	public String getWxPayEnable(){
		return getValue("wxPayEnable");
	}

	public void setWxPayEnable(String wxPayEnable){
		setValue("wxPayEnable",wxPayEnable);
	}

	public String getWxPayMchID(){
		return getValue("wxPayMchID");
	}

	public void setWxPayMchID(String wxPayMchID){
		setValue("wxPayMchID",wxPayMchID);
	}

	public String getWxPayKey(){
		return getValue("wxPayKey");
	}

	public void setWxPayKey(String wxPayKey){
		setValue("wxPayKey",wxPayKey);
	}

	public String getWxPayCertFilePath(){
		return getValue("wxPayCertFilePath");
	}

	public void setWxPayCertFilePath(String wxPayCertFilePath){
		setValue("wxPayCertFilePath",wxPayCertFilePath);
	}

	public String getSyncContactSecret() {
		return getValue("syncContact.secret");
	}

	public String getSyncContactOrgTypeSetting() {
		return getValue("syncContact.orgType.setting");
	}

	public String getSyncContactFieldSetting() {
		return getValue("syncContact.field.setting");
	}


	public void setSyncContactSecret(String syncContactSecret) {
		setValue("syncContact.secret",syncContactSecret);
	}

	public void setSyncContactOrgTypeSetting(String syncContactOrgTypeSetting) {
		setValue("syncContact.orgType.setting",syncContactOrgTypeSetting);
	}

	public void setSyncContactFieldSetting(String syncContactFieldSetting) {
		setValue("syncContact.field.setting",syncContactFieldSetting);
	}

	public String getSyncContactToEkp(){
		return getValue("syncContact.toEkp");
	}

	public void setSyncContactToEkp(String syncContactToEkp){
		setValue("syncContact.toEkp",syncContactToEkp);
	}

	public String getChatdataSyncEnable() {
		return getValue("chatdataSyncEnable");
	}

	public void setChatdataSyncEnable(String chatdataSyncEnable) {
		setValue("chatdataSyncEnable",chatdataSyncEnable);
	}

	public String getChatdataSyncPrivateKey() {
		return getValue("chatdataSyncPrivateKey");
	}

	public void setChatdataSyncPrivateKey(String chatdataSyncPrivateKey) {
		setValue("chatdataSyncPrivateKey",chatdataSyncPrivateKey);
	}

	public String getChatdataSyncPublicKey() {
		return getValue("chatdataSyncPublicKey");
	}

	public void setChatdataSyncPublicKey(String chatdataSyncPublicKey) {
		setValue("chatdataSyncPublicKey",chatdataSyncPublicKey);
	}

	public String getChatdataEncryEnable() {
		return getValue("chatdataEncryEnable");
	}

	public void setChatdataEncryEnable(String chatdataEncryEnable) {
		setValue("chatdataEncryEnable",chatdataEncryEnable);
	}

	public String getChatdataBakMonth() {
		String month = getValue("chatdataBakMonth");
		if(StringUtil.isNull(month)){
			month = "3";
		}
		return month;
	}

	public void setChatdataBakMonth(String chatdataBakMonth) {
		setValue("chatdataBakMonth",chatdataBakMonth);
	}

	public String getChatdataAppSecret(){
		return getValue("chatdataAppSecret");
	}
	public void setChatdataAppSecret(String chatdataAppSecret) {
		setValue("chatdataAppSecret", chatdataAppSecret);
	}

	public String getContactIntegrateEnable() {
		return getValue("contactIntegrateEnable");
	}

	public void setContactIntegrateEnable(String contactIntegrateEnable) {
		setValue("contactIntegrateEnable",contactIntegrateEnable);
	}

	public String getCorpGroupIntegrateEnable() {
		return getValue("corpGroupIntegrateEnable");
	}

	public void setCorpGroupIntegrateEnable(String corpGroupIntegrateEnable) {
		setValue("corpGroupIntegrateEnable",corpGroupIntegrateEnable);
	}

	public String getCorpGroupAgentIds() {
		return getValue("corpGroupAgentIds");
	}

	public void setCorpGroupAgentIds(String corpGroupAgentIds) {
		setValue("corpGroupAgentIds",corpGroupAgentIds);
	}

	public String getSyncCorpGroupOrgEnable() {
		return getValue("syncCorpGroupOrgEnable");
	}

	public void setSyncCorpGroupOrgEnable(String syncCorpGroupOrgEnable) {
		setValue("syncCorpGroupOrgEnable",syncCorpGroupOrgEnable);
	}

	public String getSyncCorpGroupOrgType() {
		return getValue("syncCorpGroupOrgType");
	}

	public void setSyncCorpGroupOrgType(String syncCorpGroupOrgType) {
		setValue("syncCorpGroupOrgType",syncCorpGroupOrgType);
	}

	public String getWxSSOHashHandle() {
		return getValue("wxSSOHashHandle");
	}
	public void setWxSSOHashHandle(String wxSSOHashHandle) {
		setValue("wxSSOHashHandle",wxSSOHashHandle);
	}



	public String getWxWorkTodoPcOpenType() {
		String val = getValue("wxWorkTodoPcOpenType");
		if(StringUtil.isNull(val)){
			val = "out";
		}
		return getValue("wxWorkTodoPcOpenType");
	}

	public void setWxWorkTodoPcOpenType(String wxworkTodoPcOpenType) {
		setValue("wxWorkTodoPcOpenType",wxworkTodoPcOpenType);
	}
}