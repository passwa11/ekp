package com.landray.kmss.km.smissive.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.smissive.model.KmSmissiveNumber;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2014-11-29
 * 
 * @author 朱湖强
 */
public class KmSmissiveNumberForm extends ExtendForm {
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

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdNumberId = null;
		fdNumberValue = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmSmissiveNumber.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}



}
