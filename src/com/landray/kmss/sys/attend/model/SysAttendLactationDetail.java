package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 
 * @author sunny
 * @version 创建时间：2022年11月21日下午12:48:54
 */
public class SysAttendLactationDetail extends BaseModel {
	private Date docCreateTime;
	private Integer fdCountHour;
	private SysOrgPerson docCreator;
	private Date fdStartTime;
	private Date fdDate;
	private Date fdEndTime;
	private Integer fdType;

	public Integer getFdType() {
		return fdType;
	}

	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	
	public Date getFdDate() {
		return fdDate;
	}

	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
	}


	public Date getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public Integer getFdCountHour() {
		return fdCountHour;
	}

	public void setFdCountHour(Integer fdCountHour) {
		this.fdCountHour = fdCountHour;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;


	public Date getDocCreateTime() {
		return this.docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
