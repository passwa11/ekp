package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingOrmDeForm;

/**
  * 流程模板字段映射
  */
public class ThirdDingOrmDe extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdDingField;

    private String fdEkpField;

	private String fdEkpFieldName;

    @Override
    public Class<ThirdDingOrmDeForm> getFormClass() {
        return ThirdDingOrmDeForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
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
