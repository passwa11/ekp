package com.landray.kmss.fssc.budget.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.fssc.budget.forms.FsscBudgetMainForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 预算主表
  */
public class FsscBudgetMain extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdYear;

    private Date docCreateTime;

    private String fdDesc;

    private Date fdEnableDate;

    private EopBasedataCompany fdCompany;

    private EopBasedataBudgetScheme fdBudgetScheme;

    private SysOrgPerson docCreator;

    private EopBasedataCompanyGroup fdCompanyGroup;

    private EopBasedataCurrency fdCurrency;

    private List<FsscBudgetDetail> fdDetailList;

    @Override
    public Class<FsscBudgetMainForm> getFormClass() {
        return FsscBudgetMainForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdEnableDate", new ModelConvertor_Common("fdEnableDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdBudgetScheme.fdName", "fdBudgetSchemeName");
            toFormPropertyMap.put("fdBudgetScheme.fdId", "fdBudgetSchemeId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdCompanyGroup.fdName", "fdCompanyGroupName");
            toFormPropertyMap.put("fdCompanyGroup.fdId", "fdCompanyGroupId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("fdDetailList", new ModelConvertor_ModelListToFormList("fdDetailList_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 年度
     */
    public String getFdYear() {
        return this.fdYear;
    }

    /**
     * 年度
     */
    public void setFdYear(String fdYear) {
        this.fdYear = fdYear;
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
     * 描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 预算启用时间
     */
    public Date getFdEnableDate() {
        return this.fdEnableDate;
    }

    /**
     * 预算启用时间
     */
    public void setFdEnableDate(Date fdEnableDate) {
        this.fdEnableDate = fdEnableDate;
    }

    /**
     * 所属公司
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 所属公司
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 预算方案
     */
    public EopBasedataBudgetScheme getFdBudgetScheme() {
        return this.fdBudgetScheme;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetScheme(EopBasedataBudgetScheme fdBudgetScheme) {
        this.fdBudgetScheme = fdBudgetScheme;
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
     * 所属公司组
     */
    public EopBasedataCompanyGroup getFdCompanyGroup() {
        return this.fdCompanyGroup;
    }

    /**
     * 所属公司组
     */
    public void setFdCompanyGroup(EopBasedataCompanyGroup fdCompanyGroup) {
        this.fdCompanyGroup = fdCompanyGroup;
    }

    /**
     * 币种汇率
     */
    public EopBasedataCurrency getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 币种汇率
     */
    public void setFdCurrency(EopBasedataCurrency fdCurrency) {
        this.fdCurrency = fdCurrency;
    }

    /**
     * 预算明细
     */
    public List<FsscBudgetDetail> getFdDetailList() {
        return this.fdDetailList;
    }

    /**
     * 预算明细
     */
    public void setFdDetailList(List<FsscBudgetDetail> fdDetailList) {
        this.fdDetailList = fdDetailList;
    }
}
