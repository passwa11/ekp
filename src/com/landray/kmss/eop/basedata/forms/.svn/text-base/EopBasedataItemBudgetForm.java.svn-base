package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemBudget;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 费用类型-预算控制维护
  */
public class EopBasedataItemBudgetForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docAlterTime;

    private String fdName;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdCategoryId;

    private String fdCategoryName;

    private String fdItemIds;

    private String fdItemNames;

    private String fdOrgIds;

    private String fdOrgNames;
    
    private String fdIsAvailable;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
        docAlterTime = null;
        fdName = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdCategoryId = null;
        fdCategoryName = null;
        fdItemIds = null;
        fdItemNames = null;
        fdOrgIds = null;
        fdOrgNames = null;
        fdIsAvailable = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataItemBudget> getModelClass() {
        return EopBasedataItemBudget.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgPerson.class));
            toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel("docAlteror", SysOrgPerson.class));
            toModelPropertyMap.put("fdCategoryId", new FormConvertor_IDToModel("fdCategory", EopBasedataBudgetScheme.class));
            toModelPropertyMap.put("fdItemIds", new FormConvertor_IDsToModelList("fdItems", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdOrgIds", new FormConvertor_IDsToModelList("fdOrgs", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
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
    public String getFdCompanyListIds() {
        return this.fdCompanyListIds;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyListIds(String fdCompanyListIds) {
        this.fdCompanyListIds = fdCompanyListIds;
    }

    /**
     * 启用公司
     */
    public String getFdCompanyListNames() {
        return this.fdCompanyListNames;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyListNames(String fdCompanyListNames) {
        this.fdCompanyListNames = fdCompanyListNames;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 修改人
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * 修改人
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * 修改人
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }

    /**
     * 预算方案
     */
    public String getFdCategoryId() {
        return this.fdCategoryId;
    }

    /**
     * 预算方案
     */
    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    /**
     * 预算方案
     */
    public String getFdCategoryName() {
        return this.fdCategoryName;
    }

    /**
     * 预算方案
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
    }

    /**
     * 费用类型
     */
    public String getFdItemIds() {
        return this.fdItemIds;
    }

    /**
     * 费用类型
     */
    public void setFdItemIds(String fdItemIds) {
        this.fdItemIds = fdItemIds;
    }

    /**
     * 费用类型
     */
    public String getFdItemNames() {
        return this.fdItemNames;
    }

    /**
     * 费用类型
     */
    public void setFdItemNames(String fdItemNames) {
        this.fdItemNames = fdItemNames;
    }

    /**
     * 行政组织架构范围
     */
    public String getFdOrgIds() {
        return this.fdOrgIds;
    }

    /**
     * 行政组织架构范围
     */
    public void setFdOrgIds(String fdOrgIds) {
        this.fdOrgIds = fdOrgIds;
    }

    /**
     * 行政组织架构范围
     */
    public String getFdOrgNames() {
        return this.fdOrgNames;
    }

    /**
     * 行政组织架构范围
     */
    public void setFdOrgNames(String fdOrgNames) {
        this.fdOrgNames = fdOrgNames;
    }

	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}
}
