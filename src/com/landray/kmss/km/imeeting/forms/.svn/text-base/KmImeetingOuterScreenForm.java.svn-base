package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingOuterScreen;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.web.action.ActionMapping;

public class KmImeetingOuterScreenForm extends ExtendForm {
	private String fdName;// 设备名称

	private String fdCode;// 设备编码

	private String fdResId;

	private String fdResName;

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

	public String getFdResId() {
		return fdResId;
	}

	public void setFdResId(String fdResId) {
		this.fdResId = fdResId;
	}

	public String getFdResName() {
		return fdResName;
	}

	public void setFdResName(String fdResName) {
		this.fdResName = fdResName;
	}

	@Override
	public Class<KmImeetingOuterScreen> getModelClass() {
		return KmImeetingOuterScreen.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdCode = null;
		fdResId = null;
		fdResName = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap formToModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			formToModelPropertyMap.put("fdResId",
					new FormConvertor_IDToModel(
							"fdRes", KmImeetingRes.class));
		}

		return formToModelPropertyMap;
	}
}
