package com.landray.kmss.sys.portal.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 个人默认门户
 *
 */
public class SysPortalPersonDefaultForm extends ExtendForm {
	
	private String fdPortalId;

	private Date fdPortalName;


	private String fdPersonId;

	private String fdPersonName;
	
	private String docCreateTime;


	public String getFdPortalId() {
		return fdPortalId;
	}

	public void setFdPortalId(String fdPortalId) {
		this.fdPortalId = fdPortalId;
	}

	public Date getFdPortalName() {
		return fdPortalName;
	}

	public void setFdPortalName(Date fdPortalName) {
		this.fdPortalName = fdPortalName;
	}

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	@Override
	public Class getModelClass() {
		return SysPortalPersonDefaultForm.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPortalId= null;
		fdPortalName= null;
		fdPersonId = null;
		fdPersonName = null;
		docCreateTime = null;
		super.reset(mapping, request);
	}

	FormToModelPropertyMap formToModelPropertyMap = null;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			formToModelPropertyMap.put("fdPersonId",
					new FormConvertor_IDToModel("fdPerson",
							SysOrgPerson.class));
		}
		return formToModelPropertyMap;
	}
}
