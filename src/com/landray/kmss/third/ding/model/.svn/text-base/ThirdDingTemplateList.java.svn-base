package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingTemplateListForm;

/**
  * DING日志
  */
public class ThirdDingTemplateList extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

	private String processCode;
	private String name;
	private String description;

	@Override
    public Class<ThirdDingTemplateListForm> getFormClass() {
		return ThirdDingTemplateListForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public void recalculateFields() {
        super.recalculateFields();
    }

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
