package com.landray.kmss.fssc.budgeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalAuth;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 预算审批权限
  */
public class FsscBudgetingApprovalAuthForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdDesc;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;
    
    private String fdCompanyListIds;
    
    private String fdCompanyListNames;

    private String fdPersonListIds;

    private String fdPersonListNames;

    private String fdOrgListIds;

    private String fdOrgListNames;

    private String fdCostCenterListIds;

    private String fdCostCenterListNames;

    private String fdBudgetItemListIds;

    private String fdBudgetItemListNames;

    private String fdProjectListIds;

    private String fdProjectListNames;
    
    private FormFile fdFile;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdDesc = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdCompanyListIds = null;
        fdCompanyListNames = null;
        fdPersonListIds = null;
        fdPersonListNames = null;
        fdOrgListIds = null;
        fdOrgListNames = null;
        fdCostCenterListIds = null;
        fdCostCenterListNames = null;
        fdBudgetItemListIds = null;
        fdBudgetItemListNames = null;
        fdProjectListIds = null;
        fdProjectListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetingApprovalAuth> getModelClass() {
        return FsscBudgetingApprovalAuth.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("fdPersonListIds", new FormConvertor_IDsToModelList("fdPersonList", SysOrgPerson.class));
            toModelPropertyMap.put("fdOrgListIds", new FormConvertor_IDsToModelList("fdOrgList", SysOrgElement.class));
            toModelPropertyMap.put("fdCostCenterListIds", new FormConvertor_IDsToModelList("fdCostCenterList", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdBudgetItemListIds", new FormConvertor_IDsToModelList("fdBudgetItemList", EopBasedataBudgetItem.class));
            toModelPropertyMap.put("fdProjectListIds", new FormConvertor_IDsToModelList("fdProjectList", EopBasedataProject.class));
        }
        return toModelPropertyMap;
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
     * 说明
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 说明
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 是否有效
     */
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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
     * 公司
     */

    public String getFdCompanyListIds() {
		return fdCompanyListIds;
	}
    /**
     * 公司
     */
	public void setFdCompanyListIds(String fdCompanyListIds) {
		this.fdCompanyListIds = fdCompanyListIds;
	}
	/**
     * 公司
     */
	public String getFdCompanyListNames() {
		return fdCompanyListNames;
	}
	/**
     * 公司
     */
	public void setFdCompanyListNames(String fdCompanyListNames) {
		this.fdCompanyListNames = fdCompanyListNames;
	}

    /**
     * 人员
     */
    public String getFdPersonListIds() {
        return this.fdPersonListIds;
    }

    /**
     * 人员
     */
    public void setFdPersonListIds(String fdPersonListIds) {
        this.fdPersonListIds = fdPersonListIds;
    }

    /**
     * 人员
     */
    public String getFdPersonListNames() {
        return this.fdPersonListNames;
    }

    /**
     * 人员
     */
    public void setFdPersonListNames(String fdPersonListNames) {
        this.fdPersonListNames = fdPersonListNames;
    }

    /**
     * 组织架构
     */
    public String getFdOrgListIds() {
        return this.fdOrgListIds;
    }

    /**
     * 组织架构
     */
    public void setFdOrgListIds(String fdOrgListIds) {
        this.fdOrgListIds = fdOrgListIds;
    }

    /**
     * 组织架构
     */
    public String getFdOrgListNames() {
        return this.fdOrgListNames;
    }

    /**
     * 组织架构
     */
    public void setFdOrgListNames(String fdOrgListNames) {
        this.fdOrgListNames = fdOrgListNames;
    }

    /**
     * 成本中心
     */
    public String getFdCostCenterListIds() {
        return this.fdCostCenterListIds;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenterListIds(String fdCostCenterListIds) {
        this.fdCostCenterListIds = fdCostCenterListIds;
    }

    /**
     * 成本中心
     */
    public String getFdCostCenterListNames() {
        return this.fdCostCenterListNames;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenterListNames(String fdCostCenterListNames) {
        this.fdCostCenterListNames = fdCostCenterListNames;
    }

    /**
     * 预算科目
     */
    public String getFdBudgetItemListIds() {
        return this.fdBudgetItemListIds;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItemListIds(String fdBudgetItemListIds) {
        this.fdBudgetItemListIds = fdBudgetItemListIds;
    }

    /**
     * 预算科目
     */
    public String getFdBudgetItemListNames() {
        return this.fdBudgetItemListNames;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItemListNames(String fdBudgetItemListNames) {
        this.fdBudgetItemListNames = fdBudgetItemListNames;
    }

    /**
     * 项目
     */
    public String getFdProjectListIds() {
        return this.fdProjectListIds;
    }

    /**
     * 项目
     */
    public void setFdProjectListIds(String fdProjectListIds) {
        this.fdProjectListIds = fdProjectListIds;
    }

    /**
     * 项目
     */
    public String getFdProjectListNames() {
        return this.fdProjectListNames;
    }

    /**
     * 项目
     */
    public void setFdProjectListNames(String fdProjectListNames) {
        this.fdProjectListNames = fdProjectListNames;
    }

	public FormFile getFdFile() {
		return fdFile;
	}

	public void setFdFile(FormFile fdFile) {
		this.fdFile = fdFile;
	}
    
}
