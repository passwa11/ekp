package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.forms.EopBasedataCurrencyForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.DateUtil;

/**
  * 货币
  */
public class EopBasedataCurrency extends ExtendAuthTmpModel {
	
	//EOP币种有效
	public static final Integer EOP_CURRENCY_IS_ENABLED = 0;

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdStatus;

    private String fdCountry;

    private String fdEnglishName;

    private String fdAbbreviation;

    private Integer fdOrder;

    private String fdCode;
    
    private String fdSymbol;

    @Override
    public Class<EopBasedataCurrencyForm> getFormClass() {
        return EopBasedataCurrencyForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 货币中文名称
     */
    @Override
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 货币中文名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 国家/地区
     */
    public String getFdCountry() {
        return this.fdCountry;
    }

    /**
     * 国家/地区
     */
    public void setFdCountry(String fdCountry) {
        this.fdCountry = fdCountry;
    }

    /**
     * 货币英文名称
     */
    public String getFdEnglishName() {
        return this.fdEnglishName;
    }

    /**
     * 货币英文名称
     */
    public void setFdEnglishName(String fdEnglishName) {
        this.fdEnglishName = fdEnglishName;
    }

    /**
     * 货币简称
     */
    public String getFdAbbreviation() {
        return this.fdAbbreviation;
    }

    /**
     * 货币简称
     */
    public void setFdAbbreviation(String fdAbbreviation) {
        this.fdAbbreviation = fdAbbreviation;
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 货币编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 货币编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }
    /**
     * 货币符号
     */
    public String getFdSymbol() {
        return this.fdSymbol;
    }

    /**
     * 货币符号
     */
    public void setFdSymbol(String fdSymbol) {
        this.fdSymbol = fdSymbol;
    }
}
