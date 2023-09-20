package com.landray.kmss.sys.transport.form;

import com.landray.kmss.common.convertor.FormConvertor_FormToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.transport.model.Key;

/**
 * @author 苏轶
 *	外键属性
 */
public class KeyForm extends ExtendForm
{
	private String fdName;
	private PropertyForm propertyForm;
	public String getFdName() {
		return fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	public PropertyForm getPropertyForm() {
		return propertyForm;
	}
	public void setPropertyForm(PropertyForm propertyForm) {
		this.propertyForm = propertyForm;
	}
	@Override
    public Class getModelClass() {
		return Key.class;
	}
	private static FormToModelPropertyMap toModelPropertyMap;
	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("propertyForm",
					new FormConvertor_FormToModel("property"));
		}
		return toModelPropertyMap;
	}
}
