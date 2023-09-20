package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.OmsRelationModelForm;

public class OmsRelationModel extends BaseModel {

	public OmsRelationModel() {
	}

	public OmsRelationModel(String fdEkpId, String fdAppKey, String fdAppPkId, String fdType, String fdAvatar, String fdUnionId,String fdAccountType) {
		this.fdEkpId = fdEkpId;
		this.fdAppKey = fdAppKey;
		this.fdAppPkId = fdAppPkId;
		this.fdType = fdType;
		this.fdAvatar = fdAvatar;
		this.fdUnionId = fdUnionId;
		this.fdAccountType = fdAccountType;
	}

	protected String fdEkpId;

	public String getFdEkpId() {
		return fdEkpId;
	}

	public void setFdEkpId(String fdEkpId) {
		this.fdEkpId = fdEkpId;
	}

	protected String fdAppKey;

	public String getFdAppKey() {
		return fdAppKey;
	}

	public void setFdAppKey(String fdAppKey) {
		this.fdAppKey = fdAppKey;
	}

	protected String fdAppPkId;

	public String getFdAppPkId() {
		return fdAppPkId;
	}

	public void setFdAppPkId(String fdAppPkId) {
		this.fdAppPkId = fdAppPkId;
	}

	@Override
    public Class getFormClass() {
		return OmsRelationModelForm.class;
	}
	
	private String fdType;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
	
	protected String fdAvatar;

	public String getFdAvatar() {
		return fdAvatar;
	}

	public void setFdAvatar(String fdAvatar) {
		this.fdAvatar = fdAvatar;
	}

	private String fdUnionId;

	public String getFdUnionId() {
		return fdUnionId;
	}

	public void setFdUnionId(String fdUnionId) {
		this.fdUnionId = fdUnionId;
	}

	private String fdAccountType;

	public String getFdAccountType() {
		return fdAccountType;
	}

	public void setFdAccountType(String fdAccountType) {
		this.fdAccountType = fdAccountType;
	}
}
