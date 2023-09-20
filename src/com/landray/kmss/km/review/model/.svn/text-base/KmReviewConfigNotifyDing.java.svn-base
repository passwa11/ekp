package com.landray.kmss.km.review.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class KmReviewConfigNotifyDing extends BaseAppConfig {

	@Override
    public String getJSPUrl() {
		return "/km/review/km_review_notify_config/km_review_notify_config_ding.jsp";
	}

	public KmReviewConfigNotifyDing() throws Exception {
		super();
		String str = "";
		str = super.getValue("fdNotifyType");
		if (StringUtil.isNull(str)) {
			str = "todo";
		}
		super.setValue("fdNotifyType", str);
		
		str = super.getValue("email");
		if (StringUtil.isNull(str)) {
			str = "email";
		}
		super.setValue("email", str);
		
		str = super.getValue("msg");
		if (StringUtil.isNull(str)) {
			str = "msg";
		}
		super.setValue("msg", str);
		
		str = super.getValue("subject");
		if (str == null) {
			str = "subject";
		}
		super.setValue("subject", str);
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

	public void setEmail(String email) {
		setValue("email", email);
	}
	public void setMsg(String msg) {
		setValue("msg", msg);
	}
	
	public String getMsg() {
		String str = "";
		str = super.getValue("msg");
		if (StringUtil.isNull(str)) {
			str = "msg";
			return str;
		}
		return getValue("msg");
	}
	
	public String getEmail() {
		String str = "";
		str = super.getValue("email");
		if (StringUtil.isNull(str)) {
			str = "email";
			return str;
		}
		return getValue("email");
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
		return "";
	}

}
