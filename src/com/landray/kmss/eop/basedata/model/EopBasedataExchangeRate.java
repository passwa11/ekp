package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataExchangeRateForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 汇率
  */
public class EopBasedataExchangeRate extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Double fdRate;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private Integer fdOrder;

    private Date fdStartDate;

    private String fdType;

    private EopBasedataCurrency fdSourceCurrency;

    private EopBasedataCurrency fdTargetCurrency;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    @Override
    public Class<EopBasedataExchangeRateForm> getFormClass() {
        return EopBasedataExchangeRateForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdStartDate", new ModelConvertor_Common("fdStartDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdSourceCurrency.fdName", "fdSourceCurrencyName");
            toFormPropertyMap.put("fdSourceCurrency.fdId", "fdSourceCurrencyId");
            toFormPropertyMap.put("fdTargetCurrency.fdName", "fdTargetCurrencyName");
            toFormPropertyMap.put("fdTargetCurrency.fdId", "fdTargetCurrencyId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 汇率
     */
    public Double getFdRate() {
        return this.fdRate;
    }

    /**
     * 汇率
     */
    public void setFdRate(Double fdRate) {
        this.fdRate = fdRate;
    }

    /**
     * 是否有效
     */
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
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
     * 有效开始日期
     */
    public Date getFdStartDate() {
        return this.fdStartDate;
    }

    /**
     * 有效开始日期
     */
    public void setFdStartDate(Date fdStartDate) {
        this.fdStartDate = fdStartDate;
    }

    /**
     * 汇率类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 汇率类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 货币(源)
     */
    public EopBasedataCurrency getFdSourceCurrency() {
        return this.fdSourceCurrency;
    }

    /**
     * 货币(源)
     */
    public void setFdSourceCurrency(EopBasedataCurrency fdSourceCurrency) {
        this.fdSourceCurrency = fdSourceCurrency;
    }

    /**
     * 货币(目标)
     */
    public EopBasedataCurrency getFdTargetCurrency() {
        return this.fdTargetCurrency;
    }

    /**
     * 货币(目标)
     */
    public void setFdTargetCurrency(EopBasedataCurrency fdTargetCurrency) {
        this.fdTargetCurrency = fdTargetCurrency;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

    /**
     * 启用公司
     */
    public List<EopBasedataCompany> getFdCompanyList() {
        return this.fdCompanyList;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyList(List<EopBasedataCompany> fdCompanyList) {
        this.fdCompanyList = fdCompanyList;
    }
}
