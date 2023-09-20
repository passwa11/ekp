package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingInnerScreenForm;

/**
 * 铂恩集成外屏model
 * 
 * @author ADai
 *
 */
public class KmImeetingInnerScreen extends BaseModel {

	private String fdName;// 设备名称

	private String fdCode;// 设备编码

	private KmImeetingRes fdRes;// 会议室

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdCode() {
		return fdCode;
	}

	public void setFdCode(String fdCode) {
		this.fdCode = fdCode;
	}

	public KmImeetingRes getFdRes() {
		return fdRes;
	}

	public void setFdRes(KmImeetingRes fdRes) {
		this.fdRes = fdRes;
	}

	@Override
	public Class<KmImeetingInnerScreenForm> getFormClass() {
		return KmImeetingInnerScreenForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdRes.fdId", "fdResId");
		}
		return toFormPropertyMap;
	}

}
