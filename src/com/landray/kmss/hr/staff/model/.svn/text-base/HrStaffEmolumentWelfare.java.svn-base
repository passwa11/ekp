package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffEmolumentWelfareForm;

/**
 * 薪酬福利
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffEmolumentWelfare extends HrStaffBaseModel {
	private static final long serialVersionUID = 1L;

	// 工资账户名
	private String fdPayrollName;
	// 工资银行
	private String fdPayrollBank;
	// 工资账号
	private String fdPayrollAccount;
	// 公积金账户
	private String fdSurplusAccount;
	// 社保号码
	private String fdSocialSecurityNumber;
	// 主文档
	private HrStaffEntry docMain;

	@Override
	public Class getFormClass() {
		return HrStaffEmolumentWelfareForm.class;
	}

	public HrStaffEntry getDocMain() {
		return docMain;
	}

	public void setDocMain(HrStaffEntry docMain) {
		this.docMain = docMain;
	}

	public String getFdPayrollName() {
		return fdPayrollName;
	}

	public void setFdPayrollName(String fdPayrollName) {
		this.fdPayrollName = fdPayrollName;
	}

	public String getFdPayrollBank() {
		return fdPayrollBank;
	}

	public void setFdPayrollBank(String fdPayrollBank) {
		this.fdPayrollBank = fdPayrollBank;
	}

	public String getFdPayrollAccount() {
		return fdPayrollAccount;
	}

	public void setFdPayrollAccount(String fdPayrollAccount) {
		this.fdPayrollAccount = fdPayrollAccount;
	}

	public String getFdSurplusAccount() {
		return fdSurplusAccount;
	}

	public void setFdSurplusAccount(String fdSurplusAccount) {
		this.fdSurplusAccount = fdSurplusAccount;
	}

	public String getFdSocialSecurityNumber() {
		return fdSocialSecurityNumber;
	}

	public void setFdSocialSecurityNumber(String fdSocialSecurityNumber) {
		this.fdSocialSecurityNumber = fdSocialSecurityNumber;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docMain.fdId", "docMainId");
			toFormPropertyMap.put("docMain.fdName", "docMainName");
		}
		return toFormPropertyMap;
	}
}
