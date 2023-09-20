package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataItemBudgetForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 费用类型-预算控制维护
  */
public class EopBasedataItemBudget extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdName;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private EopBasedataBudgetScheme fdCategory;
    
    private Boolean fdIsAvailable;

    private List<EopBasedataExpenseItem> fdItems = new ArrayList<EopBasedataExpenseItem>();

    private List<SysOrgElement> fdOrgs = new ArrayList<SysOrgElement>();

    @Override
    public Class<EopBasedataItemBudgetForm> getFormClass() {
        return EopBasedataItemBudgetForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdCategory.fdName", "fdCategoryName");
            toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
            toFormPropertyMap.put("fdItems", new ModelConvertor_ModelListToString("fdItemIds:fdItemNames", "fdId:fdName"));
            toFormPropertyMap.put("fdOrgs", new ModelConvertor_ModelListToString("fdOrgIds:fdOrgNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * 预算方案
     */
    public EopBasedataBudgetScheme getFdCategory() {
        return this.fdCategory;
    }

    /**
     * 预算方案
     */
    public void setFdCategory(EopBasedataBudgetScheme fdCategory) {
        this.fdCategory = fdCategory;
    }

    /**
     * 费用类型
     */
    public List<EopBasedataExpenseItem> getFdItems() {
        return this.fdItems;
    }

    /**
     * 费用类型
     */
    public void setFdItems(List<EopBasedataExpenseItem> fdItems) {
        this.fdItems = fdItems;
    }

    /**
     * 行政组织架构范围
     */
    public List<SysOrgElement> getFdOrgs() {
        return this.fdOrgs;
    }

    /**
     * 行政组织架构范围
     */
    public void setFdOrgs(List<SysOrgElement> fdOrgs) {
        this.fdOrgs = fdOrgs;
    }

	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}
}
