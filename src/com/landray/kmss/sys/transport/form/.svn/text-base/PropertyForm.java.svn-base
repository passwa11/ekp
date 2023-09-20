package com.landray.kmss.sys.transport.form;

import java.util.List;

import com.landray.kmss.common.convertor.FormConvertor_FormToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.transport.model.Property;

public class PropertyForm extends ExtendForm
{
	private String fdName;
	private Integer fdOrder;
	private ConfigForm configForm;
	private List keyList; // 外键属性列表
	
	public List getKeyList() {
		return keyList;
	}
	public void setKeyList(List keyList) {
		this.keyList = keyList;
	}
	public ConfigForm getConfigForm() {
		return configForm;
	}
	public void setConfigForm(ConfigForm configForm) {
		this.configForm = configForm;
	}
	public String getFdName() {
		return fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	public Integer getFdOrder() {
		return fdOrder;
	}
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	@Override
    public Class getModelClass() {
		return Property.class;
	}
	private static FormToModelPropertyMap toModelPropertyMap;
	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("configForm",
					new FormConvertor_FormToModel("config"));
		}
		return toModelPropertyMap;
	}
}
