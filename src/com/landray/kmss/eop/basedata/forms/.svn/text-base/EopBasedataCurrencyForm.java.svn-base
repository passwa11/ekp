package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 货币
  */
public class EopBasedataCurrencyForm extends ExtendAuthTmpForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdStatus;

    private String fdCountry;

    private String fdEnglishName;

    private String fdAbbreviation;

    private String fdOrder;

    private String fdCode;
    
    private String fdSymbol;

    private String docCreatorId;

    private String docCreatorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        docCreateTime = null;
        fdStatus = null;
        fdCountry = null;
        fdEnglishName = null;
        fdAbbreviation = null;
        fdOrder = null;
        fdCode = null;
        fdSymbol = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataCurrency> getModelClass() {
        return EopBasedataCurrency.class;
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

    /**
     * 货币中文名称
     */
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
     * 最近更新时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 最近更新时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
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
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
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
     * 货币符号  ￥
     */
    public String getFdSymbol() {
        return this.fdSymbol;
    }

    /**
     * 货币符号  ￥
     */
    public void setFdSymbol(String fdSymbol) {
        this.fdSymbol = fdSymbol;
    }
    
    /**
     * 最近更新人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }
}
