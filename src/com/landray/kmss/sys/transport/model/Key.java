package com.landray.kmss.sys.transport.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelToForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.transport.form.KeyForm;

/**
 * @author 苏轶
 *	外键属性
 */
public class Key extends BaseModel
{
	private String fdName;
	private Property property;
	
	public Key(String fdName) {
		setFdName(fdName);
	}
	public Key() {}
	public String getFdName() {
		return fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	@Override
    public Class getFormClass() {
		return KeyForm.class;
	}
	private static ModelToFormPropertyMap toFormPropertyMap;
	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("property", new ModelConvertor_ModelToForm(
					"propertyForm"));
		}
		return toFormPropertyMap;
	}
}
