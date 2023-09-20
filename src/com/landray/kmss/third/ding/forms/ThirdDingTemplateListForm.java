package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingSendDing;
import com.landray.kmss.web.action.ActionMapping;

/**
  * DING日志
  */
public class ThirdDingTemplateListForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private String processCode;
	private String name;
	private String description;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		processCode = null;
		name = null;
		description = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingSendDing> getModelClass() {
        return ThirdDingSendDing.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
