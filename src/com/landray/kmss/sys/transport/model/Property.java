package com.landray.kmss.sys.transport.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelToForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;

public class Property extends BaseModel
{
	private String fdName;
	private Integer fdOrder;
	private Config config;  // 所属配置表
	private List keyList; // 外键属性列表
	
	@Override
    public Class getFormClass() {
		return null;
	}
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
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
	private static ModelToFormPropertyMap toFormPropertyMap;
	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("config", new ModelConvertor_ModelToForm(
					"configForm"));
		}
		return toFormPropertyMap;
	}
	public List getKeyList() {
		return keyList;
	}
	public void setKeyList(List keyList) {
		this.keyList = keyList;
	}
}
