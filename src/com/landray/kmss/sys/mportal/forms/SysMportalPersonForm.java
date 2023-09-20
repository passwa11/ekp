package com.landray.kmss.sys.mportal.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalPerson;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoArrayList;

public class SysMportalPersonForm extends ExtendForm {

	private static final long serialVersionUID = 2924841890134044612L;
	
	protected String fdPersonId;

	public String getFdPersonId() {
		return fdPersonId;
	}


	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}


	protected  String fdPersonName;
	
	public String getFdPersonName() {
		return fdPersonName;
	}


	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}



	protected AutoArrayList fdDetailForms = new AutoArrayList(
			SysMportalPersonDetailForm.class);
	
	
	public AutoArrayList getFdDetailForms() {
		return fdDetailForms;
	}

	public void setFdDetailForms(AutoArrayList fdDetailForms) {
		this.fdDetailForms = fdDetailForms;
	}


	@Override
	public Class<SysMportalPerson> getModelClass() {
		return SysMportalPerson.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonId",
					new FormConvertor_IDToModel("fdPerson",
							SysOrgPerson.class));
			
			toModelPropertyMap.put("fdDetailForms",
					new FormConvertor_FormListToModelList(
							"fdDetails",
							"fdMportalPerson"));
		}
		return toModelPropertyMap;
	}
}
