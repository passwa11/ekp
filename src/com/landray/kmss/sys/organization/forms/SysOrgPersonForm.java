package com.landray.kmss.sys.organization.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * 
 * @author 叶中奇
 */
public class SysOrgPersonForm extends SysOrgElementForm {
	public boolean isAnonymous() {
		if (fdLoginName == null) {
            return false;
        }
		return "anonymous".equals(fdLoginName);
	}

	/*
	 * 手机号码
	 */
	private String fdMobileNo;

	public String getFdMobileNo() {
		return fdMobileNo;
	}

	public void setFdMobileNo(String fdMobileNo) {
		this.fdMobileNo = fdMobileNo;
	}

	/*
	 * 邮件地址
	 */
	private String fdEmail;

	public String getFdEmail() {
		return fdEmail;
	}

	public void setFdEmail(String fdEmail) {
		this.fdEmail = fdEmail;
	}

	/*
	 * 登录名
	 */
	private String fdLoginName;

	public String getFdLoginName() {
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	/*
	 * 密码，仅页面展现用
	 */
	private String fdNewPassword;

	public String getFdNewPassword() {
		return fdNewPassword;
	}

	public void setFdNewPassword(String fdNewPassword) {
		this.fdNewPassword = fdNewPassword;
	}

	/*
	 * RTX帐号
	 */
	private String fdRtxNo;

	public String getFdRtxNo() {
		return fdRtxNo;
	}

	public void setFdRtxNo(String fdRtxNo) {
		this.fdRtxNo = fdRtxNo;
	}

	/*
	 * 微信号
	 */
	private String fdWechatNo;

	public String getFdWechatNo() {
		return fdWechatNo;
	}

	public void setFdWechatNo(String fdWechatNo) {
		this.fdWechatNo = fdWechatNo;
	}

	/*
	 * 动态密码卡号
	 */
	private String fdCardNo;

	public String getFdCardNo() {
		return fdCardNo;
	}

	public void setFdCardNo(String fdCardNo) {
		this.fdCardNo = fdCardNo;
	}

	/*
	 * 办公电话
	 */
	protected String fdWorkPhone;

	public String getFdWorkPhone() {
		return fdWorkPhone;
	}

	public void setFdWorkPhone(String fdWorkPhone) {
		this.fdWorkPhone = fdWorkPhone;
	}

	/*
	 * 默认语言
	 */
	private String fdDefaultLang;

	public String getFdDefaultLang() {
		return fdDefaultLang;
	}

	public void setFdDefaultLang(String fdDefaultLang) {
		this.fdDefaultLang = fdDefaultLang;
	}

	/*
	 * 最后一次修改密码时间
	 */
	protected String fdLastChangePwd;

	public String getFdLastChangePwd() {
		return fdLastChangePwd;
	}

	public void setFdLastChangePwd(String fdLastChangePwd) {
		this.fdLastChangePwd = fdLastChangePwd;
	}

	/*
	 * 岗位列表
	 */
	private String fdPostIds = null;

	private String fdPostNames = null;

	public String getFdPostIds() {
		return fdPostIds;
	}

	public void setFdPostIds(String fdPostIds) {
		this.fdPostIds = fdPostIds;
	}

	public String getFdPostNames() {
		return fdPostNames;
	}

	public void setFdPostNames(String fdPostNames) {
		this.fdPostNames = fdPostNames;
	}

	/*
	 * 个人地址本
	 */
	private List addressTypeList = new AutoArrayList(
			SysOrgPersonAddressTypeForm.class);

	public List getAddressTypeList() {
		return addressTypeList;
	}

	public void setAddressTypeList(List addressTypeList) {
		this.addressTypeList = addressTypeList;
	}

	/**
	 * 性别
	 */
	private String fdSex;

	public String getFdSex() {
		return fdSex;
	}

	public void setFdSex(String fdSex) {
		this.fdSex = fdSex;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMobileNo = null;
		fdEmail = null;
		fdRtxNo = null;
		fdCardNo = null;
		fdDefaultLang = null;
		fdLoginName = null;
		fdPostIds = null;
		fdPostNames = null;
		fdNewPassword = null;
		fdSex = null;
		fdStaffingLevelId = null;
		fdStaffingLevelName = null;
		fdLastChangePwd = null;
		fdShortNo = null;
		addressTypeList.clear();
		fdWorkPhone = null;
		fdWechatNo = null;
		fdNickName = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return SysOrgPerson.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPostIds",
					new FormConvertor_IDsToModelList("fdPosts",
							SysOrgPost.class));
			// toModelPropertyMap.put("addressTypeList",
			// new FormConvertor_FormListToModelList("addressTypeList",
			// "sysOrgPerson"));
			toModelPropertyMap.put("fdStaffingLevelId",
					new FormConvertor_IDToModel("fdStaffingLevel",
							SysOrganizationStaffingLevel.class));
		}
		return toModelPropertyMap;
	}

	public static String getLangDisplayName(HttpServletRequest request,
			String value) {
		if (StringUtil.isNotNull(value)) {
			String langStr = ResourceUtil
					.getKmssConfigString("kmss.lang.support");
			if (StringUtil.isNotNull(langStr)) {
				String v = "|" + value;
				String[] langArr = langStr.split(";");
				for (int i = 0; i < langArr.length; i++) {
					if (langArr[i].endsWith(v)) {
						return langArr[i].substring(0, langArr[i].length()
								- v.length());
					}
				}
			}
		}
		return ResourceUtil.getString("message.defaultLang", request
				.getLocale());
	}

	public static String getLangSelectHtml(HttpServletRequest request,
			String propertyName, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\"").append(propertyName).append("\">");
		sb.append("<option value=\"\">").append(
				ResourceUtil.getString("message.defaultLang", request
						.getLocale())).append("</option>");
		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		if (StringUtil.isNotNull(langStr)) {
			String[] langArr = langStr.trim().split(";");
			for (int i = 0; i < langArr.length; i++) {
				String[] langInfo = langArr[i].split("\\|");
				sb.append("<option value=\"").append(langInfo[1]).append("\"");
				if (langInfo[1].equals(value)) {
					sb.append(" selected");
				}
				sb.append(">").append(langInfo[0]).append("</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	public void setFdStaffingLevelId(String fdStaffingLevelId) {
		this.fdStaffingLevelId = fdStaffingLevelId;
	}

	public String getFdStaffingLevelId() {
		return fdStaffingLevelId;
	}

	public void setFdStaffingLevelName(String fdStaffingLevelName) {
		this.fdStaffingLevelName = fdStaffingLevelName;
	}

	public String getFdStaffingLevelName() {
		return fdStaffingLevelName;
	}

	private String fdStaffingLevelId;

	private String fdStaffingLevelName;

	/*
	 * 短号
	 */
	protected String fdShortNo;

	public String getFdShortNo() {
		return fdShortNo;
	}

	public void setFdShortNo(String fdShortNo) {
		this.fdShortNo = fdShortNo;
	}

	/*
	 * 双因子验证：启用，禁用，网段策略
	 */
	protected String fdDoubleValidation;

	public String getFdDoubleValidation() {
		return fdDoubleValidation;
	}

	public void setFdDoubleValidation(String fdDoubleValidation) {
		this.fdDoubleValidation = fdDoubleValidation;
	}

	public String getFdNickName() {
		return fdNickName;
	}

	public void setFdNickName(String fdNickName) {
		this.fdNickName = fdNickName;
	}

	private String fdNickName;

	/**
	 * 是否激活（三员管理中使用）
	 */
	private String fdIsActivated;

	public String getFdIsActivated() {
		return fdIsActivated;
	}

	public void setFdIsActivated(String fdIsActivated) {
		this.fdIsActivated = fdIsActivated;
	}

	/**
	 * 是否登录系统
	 */
	private Boolean fdCanLogin;

	public Boolean getFdCanLogin() {
		return fdCanLogin;
	}

	public void setFdCanLogin(Boolean fdCanLogin) {
		this.fdCanLogin = fdCanLogin;
	}

}
