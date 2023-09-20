package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendSignPatchDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 补签详情
 *
 * @author cuiwj
 * @version 1.0 2018-10-18
 */
public class SysAttendSignPatchDetail extends BaseModel {

	private SysAttendSignPatch fdPatch;

	private SysOrgPerson fdSignPerson;

	private Date fdSignTime;

	public SysAttendSignPatch getFdPatch() {
		return fdPatch;
	}

	public void setFdPatch(SysAttendSignPatch fdPatch) {
		this.fdPatch = fdPatch;
	}

	public SysOrgPerson getFdSignPerson() {
		return fdSignPerson;
	}

	public void setFdSignPerson(SysOrgPerson fdSignPerson) {
		this.fdSignPerson = fdSignPerson;
	}

	public Date getFdSignTime() {
		return fdSignTime;
	}

	public void setFdSignTime(Date fdSignTime) {
		this.fdSignTime = fdSignTime;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdSignPerson.fdId", "fdSignPersonId");
			toFormPropertyMap.put("fdSignPerson.fdName", "fdSignPersonName");
			toFormPropertyMap.put("fdPatch.fdId", "fdPatchId");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysAttendSignPatchDetailForm.class;
	}

}
