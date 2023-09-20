package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingOrmDe;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 流程模板字段映射
  */
public class ThirdDingOrmDeForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdDingField;

    private String fdEkpField;

	private String fdEkpFieldName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdDingField = null;
        fdEkpField = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingOrmDe> getModelClass() {
        return ThirdDingOrmDe.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }

    /**
     * 钉钉字段名称
     */
    public String getFdDingField() {
        return this.fdDingField;
    }

    /**
     * 钉钉字段名称
     */
    public void setFdDingField(String fdDingField) {
        this.fdDingField = fdDingField;
    }

    /**
     * EKP字段名称
     */
    public String getFdEkpField() {
        return this.fdEkpField;
    }

    /**
     * EKP字段名称
     */
    public void setFdEkpField(String fdEkpField) {
        this.fdEkpField = fdEkpField;
    }

	public String getFdEkpFieldName() {
		return fdEkpFieldName;
	}

	public void setFdEkpFieldName(String fdEkpFieldName) {
		this.fdEkpFieldName = fdEkpFieldName;
	}

}
