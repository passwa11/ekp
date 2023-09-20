package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;


public class KmImeetingMapping extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

	/*
	 * fdAppKey
	 */
	private String fdAppKey;

	public String getFdAppKey() {
		return fdAppKey;
	}

	public void setFdAppKey(String fdAppKey) {
		this.fdAppKey = fdAppKey;
	}

	/*
	 * 主表ID
	 */
	private String fdModelId;

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	/*
	 * 主表域模型
	 */
	private String fdModelName;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/*
	 * 第三方业务系统
	 */
	private String fdThirdSysId;

	public String getFdThirdSysId() {
		return fdThirdSysId;
	}

	public void setFdThirdSysId(String fdThirdSysId) {
		this.fdThirdSysId = fdThirdSysId;
	}

	private String fdExtData;

	public String getFdExtData() {
		return fdExtData;
	}

	public void setFdExtData(String fdExtData) {
		this.fdExtData = fdExtData;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());

        }
        return toFormPropertyMap;
    }
}
