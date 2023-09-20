package com.landray.kmss.km.review.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class KmReviewConfigNotify extends BaseAppConfig {

	@Override
    public String getJSPUrl() {
		return "/km/review/km_review_notify_config/km_review_config.jsp";
	}

	public KmReviewConfigNotify() throws Exception {
		super();
		String str = "";
		str = super.getValue("fdNotifyType");
		if (StringUtil.isNull(str)) {
			str = "todo";
		}
		super.setValue("fdNotifyType", str);
		str = super.getValue("subject");
		if (str == null) {
			str = "subject";
		}
		super.setValue("subject", str);
		/*#139365-打印时能够打印出来文档的状态标记-开始*/
		str = super.getValue("statusFlag");
		if (str == null) {
			str = "statusFlag";
		}
		super.setValue("statusFlag", str);
		/*#139365-打印时能够打印出来文档的状态标记-结束*/
		str = super.getValue("base");
		if(str==null){
			str = "base";
		}
		super.setValue("base", str);
		str = super.getValue("info");
		if(str==null){
			str = "info";
		}
		super.setValue("info", str);
		str = super.getValue("note");
		if(str==null){
			str = "note";
		}
		super.setValue("note", str);
		str = super.getValue("qrcode");
		if (str == null) {
			str = "qrcode";
		}
		super.setValue("qrcode", str);
	}

	public void setFdNotifyType(String fdNotifyType) {
		setValue("fdNotifyType", fdNotifyType);
	}

	public String getFdNotifyType() {
		String str = "";
		str = super.getValue("fdNotifyType");
		if (StringUtil.isNull(str)) {
			str = "todo";
			return str;
		}
		return getValue("fdNotifyType");
	}
	
	public String getSubject() {
		return getValue("subject");
	}

	public void setSubject(String subject) {
		setValue("subject", subject);
	}

	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	public String getStatusFlag() {
		return getValue("statusFlag");
	}

	public void setStatusFlag(String statusFlag) {
		setValue("statusFlag", statusFlag);
	}
	/*#139365-打印时能够打印出来文档的状态标记-结束*/

	public String getBase() {
		return getValue("base");
	}

	public void setBase(String base) {
		setValue("base", base);
	}
	public String getInfo() {
		return getValue("info");
	}

	public void setInfo(String info) {
		setValue("info", info);
	}
	public String getNote() {
		return getValue("note");
	}

	public void setNote(String note) {
		setValue("note", note);
	}

	public String getQrcode() {
		return getValue("qrcode");
	}

	public void setQrcode(String qrcode) {
		setValue("qrcode", qrcode);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("km-review:kmReview.config.notify.default");
	}

	/**
	 * 关联信息
	 */
	public String getEnableSysRelation() {
		String value = getValue("enableSysRelation");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysRelation(String isDefault) {
		setValue("enableSysRelation", isDefault);
	}

	/**
	 * 日程同步
	 */
	public String getEnableSysAgenda() {
		String value = getValue("enableSysAgenda");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysAgenda(String isDefault) {
		setValue("enableSysAgenda", isDefault);
	}

	/**
	 * 打印模板
	 */
	public String getEnableSysPrint() {
		String value = getValue("enableSysPrint");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysPrint(String isDefault) {
		setValue("enableSysPrint", isDefault);
	}

	/**
	 * 流程归档
	 */
	public String getEnableKmArchives() {
		String value = getValue("enableKmArchives");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableKmArchives(String isDefault) {
		setValue("enableKmArchives", isDefault);
	}

	/**
	 * 流程沉淀
	 */
	public String getEnableKmsMultidoc() {
		String value = getValue("enableKmsMultidoc");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableKmsMultidoc(String isDefault) {
		setValue("enableKmsMultidoc", isDefault);
	}

	/**
	 * 规则设置
	 */
	public String getEnableSysRule() {
		String value = getValue("enableSysRule");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysRule(String isDefault) {
		setValue("enableSysRule", isDefault);
	}

	/**
	 * 提醒中心
	 */
	public String getEnableSysRemind() {
		String value = getValue("enableSysRemind");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysRemind(String isDefault) {
		setValue("enableSysRemind", isDefault);
	}

	/**
	 * 智能检查
	 */
	public String getEnableSysIassister() {
		String value = getValue("enableSysIassister");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysIassister(String isDefault) {
		setValue("enableSysIassister", isDefault);
	}

	/**
	 * 相关沟通
	 */
	public String getEnableKmCollaborate() {
		String value = getValue("enableKmCollaborate");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableKmCollaborate(String isDefault) {
		setValue("enableKmCollaborate", isDefault);
	}

	/**
	 * 相关督办
	 */
	public String getEnableKmSupervise() {
		String value = getValue("enableKmSupervise");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableKmSupervise(String isDefault) {
		setValue("enableKmSupervise", isDefault);
	}

	/**
	 * 流程传阅
	 */
	public String getEnableSysCirculation() {
		String value = getValue("enableSysCirculation");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysCirculation(String isDefault) {
		setValue("enableSysCirculation", isDefault);
	}

	/**
	 * 流程收藏
	 */
	public String getEnableSysBookmark() {
		String value = getValue("enableSysBookmark");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysBookmark(String isDefault) {
		setValue("enableSysBookmark", isDefault);
	}

	/**
	 * 访问统计
	 */
	public String getEnableSysReadlog() {
		String value = getValue("enableSysReadlog");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableSysReadlog(String isDefault) {
		setValue("enableSysReadlog", isDefault);
	}

	/**
	 * 数据图表
	 */
	public String getEnableDbcenterEcharts() {
		String value = getValue("enableDbcenterEcharts");
		if ("true".equals(value) || "false".equals(value)) {
			return value;
		}
		return "true";
	}

	public void setEnableDbcenterEcharts(String isDefault) {
		setValue("enableDbcenterEcharts", isDefault);
	}
}
