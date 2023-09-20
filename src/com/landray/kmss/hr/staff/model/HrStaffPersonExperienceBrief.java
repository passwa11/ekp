package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceBriefForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 个人简介
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPersonExperienceBrief extends BaseModel {
	private static final long serialVersionUID = 1L;

	// 个人信息
	private HrStaffPersonInfo fdPersonInfo;
	
	// 内容
	private String fdContent;
	
	// 创建者
	private SysOrgPerson fdCreator;
	// 创建时间
	private Date fdCreateTime;

	@Override
	public Class getFormClass() {
		return HrStaffPersonExperienceBriefForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPersonInfo.fdId", "fdPersonInfoId");
			toFormPropertyMap.put("fdPersonInfo.fdName", "fdPersonInfoName");
			toFormPropertyMap.put("fdCreator.fdId", "fdCreatorId");
			toFormPropertyMap.put("fdCreator.fdParentsName", "fdCreatorName");
		}
		return toFormPropertyMap;
	}

	public HrStaffPersonInfo getFdPersonInfo() {
		return fdPersonInfo;
	}

	public void setFdPersonInfo(HrStaffPersonInfo fdPersonInfo) {
		this.fdPersonInfo = fdPersonInfo;
	}

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	public SysOrgPerson getFdCreator() {
		return fdCreator;
	}

	public void setFdCreator(SysOrgPerson fdCreator) {
		this.fdCreator = fdCreator;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

}
