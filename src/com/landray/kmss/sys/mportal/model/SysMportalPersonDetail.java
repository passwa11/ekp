package com.landray.kmss.sys.mportal.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.mportal.forms.SysMportalPersonDetailForm;

public class SysMportalPersonDetail extends BaseModel {
	
	private static final long serialVersionUID = -5343771399684196830L;
	
	/**
	 * 个人门户部件记录
	 */
	SysMportalPerson fdMportalPerson;
	
	/**
	 * 卡片id
	 */
	String fdCardId;
	
	Integer fdOrder;
	

	public SysMportalPerson getFdMportalPerson() {
		return fdMportalPerson;
	}

	public void setFdMportalPerson(SysMportalPerson fdMportalPerson) {
		this.fdMportalPerson = fdMportalPerson;
	}

	public String getFdCardId() {
		return fdCardId;
	}

	public void setFdCardId(String fdCardId) {
		this.fdCardId = fdCardId;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
	public Class<SysMportalPersonDetailForm> getFormClass() {
		return SysMportalPersonDetailForm.class;
	} 
	
}
