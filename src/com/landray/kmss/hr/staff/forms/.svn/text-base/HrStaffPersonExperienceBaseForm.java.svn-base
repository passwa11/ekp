package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;

import java.util.Date;

/**
 * 个人经历基类
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public abstract class HrStaffPersonExperienceBaseForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;
	// 相关流程
	private String fdRelatedProcess;
	// 开始日期
	private String fdBeginDate;
	// 结束日期
	private String fdEndDate;
	// 备注
	private String fdMemo;

	private String fdContractPeriod;

	private String fdContractUnit;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdBeginDate = null;
		this.fdEndDate = null;
		this.fdMemo = null;
		fdContractUnit= null;
		fdContractPeriod = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}

	public String getFdContractPeriod() {
		return fdContractPeriod;
	}

	public void setFdContractPeriod(String fdContractPeriod) {
		this.fdContractPeriod = fdContractPeriod;
	}

	public String getFdContractUnit() {
		return fdContractUnit;
	}

	public void setFdContractUnit(String fdContractUnit) {
		this.fdContractUnit = fdContractUnit;
	}

	public String getFdBeginDate() {
		return fdBeginDate;
	}

	public void setFdBeginDate(String fdBeginDate) {
		this.fdBeginDate = fdBeginDate;
	}

	public String getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}
	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}
}
