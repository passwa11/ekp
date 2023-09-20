package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 人事管理基类
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public abstract class HrStaffBaseForm extends HrStaffImportForm {
	private static final long serialVersionUID = 1L;

	// 个人信息
	private String fdPersonInfoId;
	private String fdPersonInfoName;
	// 创建者
	private String fdCreatorId;
	private String fdCreatorName;
	// 创建时间
	private String fdCreateTime;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdPersonInfoId = null;
		this.fdPersonInfoName = null;
		this.fdCreatorId = null;
		this.fdCreatorName = null;
		this.fdCreateTime = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonInfoId",
					new FormConvertor_IDToModel("fdPersonInfo",
							HrStaffPersonInfo.class));
			toModelPropertyMap.put("fdCreatorId", new FormConvertor_IDToModel(
					"fdCreator", SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	public String getFdPersonInfoId() {
		return fdPersonInfoId;
	}

	public void setFdPersonInfoId(String fdPersonInfoId) {
		this.fdPersonInfoId = fdPersonInfoId;
	}

	public String getFdPersonInfoName() {
		return fdPersonInfoName;
	}

	public void setFdPersonInfoName(String fdPersonInfoName) {
		this.fdPersonInfoName = fdPersonInfoName;
	}

	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	public String getFdCreatorName() {
		return fdCreatorName;
	}

	public void setFdCreatorName(String fdCreatorName) {
		this.fdCreatorName = fdCreatorName;
	}

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

}
