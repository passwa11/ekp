package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * Form bean for a Struts application.
 * 
 * @author 叶中奇
 */
public class SysOrgPersonInfoForm extends ExtendForm {
	/*
	 * 显示名
	 */
	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
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
	 * 办公电话
	 */
	private String fdWorkPhone;

	public String getFdWorkPhone() {
		return fdWorkPhone;
	}

	public void setFdWorkPhone(String fdWorkPhone) {
		this.fdWorkPhone = fdWorkPhone;
	}

	/*
	 * 默认语言
	 */
	protected String fdDefaultLang;

	public String getFdDefaultLang() {
		return fdDefaultLang;
	}

	public void setFdDefaultLang(String fdDefaultLang) {
		this.fdDefaultLang = fdDefaultLang;
	}

	/*
	 * 备注
	 */
	private String fdMemo;

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
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
	 * 我的地址本
	 */
	private String fdMyAddressIds;

	public String getFdMyAddressIds() {
		return fdMyAddressIds;
	}

	public void setFdMyAddressIds(String fdMyAddressIds) {
		this.fdMyAddressIds = fdMyAddressIds;
	}

	private String fdMyAddressNames;

	public String getFdMyAddressNames() {
		return fdMyAddressNames;
	}

	public void setFdMyAddressNames(String fdMyAddressNames) {
		this.fdMyAddressNames = fdMyAddressNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMobileNo = null;
		fdMemo = null;
		fdEmail = null;
		fdMyAddressIds = null;
		fdMyAddressNames = null;
		fdDefaultLang = null;
		fdWorkPhone = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysOrgPerson.class;
	}

}
