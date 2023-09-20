package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 薪酬福利
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffEmolumentWelfareForm extends HrStaffBaseForm {
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
	private String docMainId;
	private String docMainName;

	@Override
	public Class getModelClass() {
		return HrStaffEmolumentWelfare.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdPayrollName = null;
		this.fdPayrollBank = null;
		this.fdPayrollAccount = null;
		this.fdSurplusAccount = null;
		this.fdSocialSecurityNumber = null;
		this.docMainId = null;
		this.docMainName = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", SysOrgElement.class));

		}
		return toModelPropertyMap;
	}

	public String getDocMainId() {
		return docMainId;
	}

	public void setDocMainId(String docMainId) {
		this.docMainId = docMainId;
	}

	public String getDocMainName() {
		return docMainName;
	}

	public void setDocMainName(String docMainName) {
		this.docMainName = docMainName;
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

}
