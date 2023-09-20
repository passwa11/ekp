package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 人事管理基类
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public abstract class HrStaffBaseModel extends HrStaffImport {
	private static final long serialVersionUID = 1L;
	// 个人信息
	private HrStaffPersonInfo fdPersonInfo;
	// 创建者
	private SysOrgPerson fdCreator;
	// 创建时间
	private Date fdCreateTime;

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
