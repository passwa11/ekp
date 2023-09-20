package com.landray.kmss.km.smissive.model;

import com.landray.kmss.common.model.BaseCreateInfoModel;

public class KmSmissiveNumber extends BaseCreateInfoModel {

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 编号规则的id
	 */
	protected String fdNumberId;
	/**
	 * 编号规则的值
	 */
	protected String fdNumberValue;

	public String getFdNumberId() {
		return fdNumberId;
	}

	public void setFdNumberId(String fdNumberId) {
		this.fdNumberId = fdNumberId;
	}

	public String getFdNumberValue() {
		return fdNumberValue;
	}

	public void setFdNumberValue(String fdNumberValue) {
		this.fdNumberValue = fdNumberValue;
	}

}
