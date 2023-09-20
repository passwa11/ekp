package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBrief;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 个人简介
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPersonExperienceBriefForm extends ExtendForm {
	private static final long serialVersionUID = 1L;
	// 个人信息
	private String fdPersonInfoId;
	private String fdPersonInfoName;
	
	// 内容
	private String fdContent;

	// 创建者
	private String fdCreatorId;
	private String fdCreatorName;
	// 创建时间
	private String fdCreatorTime;
	
	@Override
	public Class getModelClass() {
		return HrStaffPersonExperienceBrief.class;
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

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdPersonInfoId = null;
		this.fdPersonInfoName = null;
		this.fdContent = null;
		this.fdCreatorId = null;
		this.fdCreatorName = null;
		this.fdCreatorTime = null;
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

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
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

	public String getFdCreatorTime() {
		return fdCreatorTime;
	}

	public void setFdCreatorTime(String fdCreatorTime) {
		this.fdCreatorTime = fdCreatorTime;
	}

}
