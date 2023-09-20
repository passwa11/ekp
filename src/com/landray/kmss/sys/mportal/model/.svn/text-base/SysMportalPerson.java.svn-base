package com.landray.kmss.sys.mportal.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.mportal.forms.SysMportalPersonForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public class SysMportalPerson extends BaseModel {

	private static final long serialVersionUID = 7115375263657842682L;

	private SysOrgPerson fdPerson;

	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}

	
	private List<SysMportalPersonDetail> fdDetails;
	
	public List<SysMportalPersonDetail> getFdDetails() {
		return fdDetails;
	}

	public void setFdDetails(List<SysMportalPersonDetail> fdDetails) {
		this.fdDetails = fdDetails;
	}

	@Override
	public Class<SysMportalPersonForm> getFormClass() {
		return SysMportalPersonForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
			toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
			
			toFormPropertyMap.put("fdDetails",
					new ModelConvertor_ModelListToFormList(
							"fdDetailForms"));
		}
		return toFormPropertyMap;
	}
	
	@Override
	public void recalculateFields() {
		super.recalculateFields();
		int order = 0;
		for (SysMportalPersonDetail item : this.getFdDetails()) {
			order ++;
			item.setFdOrder(order);
		}
	}
	
}
