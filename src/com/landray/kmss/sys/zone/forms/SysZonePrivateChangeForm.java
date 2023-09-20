package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;

public class SysZonePrivateChangeForm extends ExtendForm {
	
	
	String fdIds = null;
	
	String fdNames = null;
	

	String isContactPrivate = null;
	
	String isDepInfoPrivate = null;
	
	String isRelationshipPrivate = null;
	
	String isWorkmatePrivate = null;
	
	public String getFdIds() {
		return fdIds;
	}

	public void setFdIds(String fdIds) {
		this.fdIds = fdIds;
	}
	
	public String getFdNames() {
		return fdNames;
	}

	public void setFdNames(String fdNames) {
		this.fdNames = fdNames;
	}
	
	public String getIsContactPrivate() {
		return isContactPrivate;
	}

	public void setIsContactPrivate(String isContactPrivate) {
		this.isContactPrivate = isContactPrivate;
	}

	public String getIsDepInfoPrivate() {
		return isDepInfoPrivate;
	}

	public void setIsDepInfoPrivate(String isDepInfoPrivate) {
		this.isDepInfoPrivate = isDepInfoPrivate;
	}

	public String getIsRelationshipPrivate() {
		return isRelationshipPrivate;
	}

	public void setIsRelationshipPrivate(String isRelationshipPrivate) {
		this.isRelationshipPrivate = isRelationshipPrivate;
	}

	public String getIsWorkmatePrivate() {
		return isWorkmatePrivate;
	}

	public void setIsWorkmatePrivate(String isWorkmatePrivate) {
		this.isWorkmatePrivate = isWorkmatePrivate;
	}

	
	
	@Override
	public Class getModelClass() {
		return null;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdIds = null;
		fdNames = null;
		this.isContactPrivate = null;
		this.isDepInfoPrivate = null;
		this.isRelationshipPrivate = null;
		this.isWorkmatePrivate = null;
		super.reset(mapping, request);
	}
}
