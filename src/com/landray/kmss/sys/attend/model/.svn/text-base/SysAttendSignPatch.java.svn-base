package com.landray.kmss.sys.attend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendSignPatchForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 补签
 *
 * @author cuiwj
 * @version 1.0 2018-10-18
 */
public class SysAttendSignPatch extends BaseModel {

	private String fdCateName;

	private SysOrgPerson fdPatchPerson;

	private Date fdPatchTime;

	List<SysAttendSignPatchDetail> fdPatchDetail = new ArrayList<SysAttendSignPatchDetail>();

	public String getFdCateName() {
		return fdCateName;
	}

	public void setFdCateName(String fdCateName) {
		this.fdCateName = fdCateName;
	}

	public SysOrgPerson getFdPatchPerson() {
		return fdPatchPerson;
	}

	public void setFdPatchPerson(SysOrgPerson fdPatchPerson) {
		this.fdPatchPerson = fdPatchPerson;
	}

	public Date getFdPatchTime() {
		return fdPatchTime;
	}

	public void setFdPatchTime(Date fdPatchTime) {
		this.fdPatchTime = fdPatchTime;
	}

	public List<SysAttendSignPatchDetail> getFdPatchDetail() {
		return fdPatchDetail;
	}

	public void setFdPatchDetail(List<SysAttendSignPatchDetail> fdPatchDetail) {
		this.fdPatchDetail = fdPatchDetail;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPatchPerson.fdId", "fdPatchPersonId");
			toFormPropertyMap.put("fdPatchPerson.fdName", "fdPatchPersonName");
			toFormPropertyMap.put("fdPatchDetail",
					new ModelConvertor_ModelListToFormList("fdPatchDetail"));
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysAttendSignPatchForm.class;
	}

}
