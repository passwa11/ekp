package com.landray.kmss.third.weixin.work.spi.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.weixin.work.spi.forms.WxworkOmsRelationModelForm;

public class WxworkOmsRelationModel extends BaseModel {
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
		return WxworkOmsRelationModelForm.class;
	}

	private String fdOpenId;

	public String getFdOpenId(){
		return this.fdOpenId;
	}

	public void setFdOpenId(String fdOpenId){
		this.fdOpenId = fdOpenId;
	}
}
