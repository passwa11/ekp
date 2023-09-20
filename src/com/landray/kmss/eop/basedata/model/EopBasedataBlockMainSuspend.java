package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.model.BaseModel;

public class EopBasedataBlockMainSuspend extends BaseModel {
	
	private String fdKey;
	
	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}
	
	private String fdModelId;
	
	private String fdFeeMainId;
	
	public String getFdFeeMainId() {
		return fdFeeMainId;
	}

	public void setFdFeeMainId(String fdFeeMainId) {
		this.fdFeeMainId = fdFeeMainId;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	private String fdModelName;
	
	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	@Override
	public Class getFormClass() {
		// TODO 自动生成的方法存根
		return null;
	}
}
