package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataInputTaxForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
 * 进项税率
 */
public class EopBasedataInputTax extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private Double fdTaxRate;

    private Boolean fdIsInputTax;

    private String fdDesc;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private EopBasedataExpenseItem fdItem;

    private EopBasedataAccounts fdAccount;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    @Override
    public Class<EopBasedataInputTaxForm> getFormClass() {
        return EopBasedataInputTaxForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdItem.fdName", "fdItemName");
            toFormPropertyMap.put("fdItem.fdId", "fdItemId");
            toFormPropertyMap.put("fdAccount.fdName", "fdAccountName");
            toFormPropertyMap.put("fdAccount.fdId", "fdAccountId");
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 税率
     */
    public Double getFdTaxRate() {
        return this.fdTaxRate;
    }

    /**
     * 税率
     */
    public void setFdTaxRate(Double fdTaxRate) {
        this.fdTaxRate = fdTaxRate;
    }

    /**
     * 是否进项税抵扣
     */
    public Boolean getFdIsInputTax() {
        return this.fdIsInputTax;
    }

    /**
     * 是否进项税抵扣
     */
    public void setFdIsInputTax(Boolean fdIsInputTax) {
        this.fdIsInputTax = fdIsInputTax;
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
     * 费用类型
     */
    public EopBasedataExpenseItem getFdItem() {
        return this.fdItem;
    }

    /**
     * 费用类型
     */
    public void setFdItem(EopBasedataExpenseItem fdItem) {
        this.fdItem = fdItem;
    }

    /**
     * 进项税科目
     */
    public EopBasedataAccounts getFdAccount() {
        return this.fdAccount;
    }

    /**
     * 进项税科目
     */
    public void setFdAccount(EopBasedataAccounts fdAccount) {
        this.fdAccount = fdAccount;
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
