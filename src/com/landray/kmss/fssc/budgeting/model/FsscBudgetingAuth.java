package com.landray.kmss.fssc.budgeting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingAuthForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 预算编制权限
  */
public class FsscBudgetingAuth extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdDesc;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<SysOrgPerson> fdPersonList = new ArrayList<SysOrgPerson>();
    
    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private List<SysOrgElement> fdOrgList = new ArrayList<SysOrgElement>();

    private List<EopBasedataCostCenter> fdCostCenterList = new ArrayList<EopBasedataCostCenter>();

    private List<EopBasedataBudgetItem> fdBudgetItemList = new ArrayList<EopBasedataBudgetItem>();

    private List<EopBasedataProject> fdProjectList = new ArrayList<EopBasedataProject>();

    @Override
    public Class<FsscBudgetingAuthForm> getFormClass() {
        return FsscBudgetingAuthForm.class;
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
            toFormPropertyMap.put("fdPersonList", new ModelConvertor_ModelListToString("fdPersonListIds:fdPersonListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdOrgList", new ModelConvertor_ModelListToString("fdOrgListIds:fdOrgListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdCostCenterList", new ModelConvertor_ModelListToString("fdCostCenterListIds:fdCostCenterListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdBudgetItemList", new ModelConvertor_ModelListToString("fdBudgetItemListIds:fdBudgetItemListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdProjectList", new ModelConvertor_ModelListToString("fdProjectListIds:fdProjectListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 公司
     */
    public List<EopBasedataCompany> getFdCompanyList() {
		return fdCompanyList;
	}
    /**
     * 公司
     */
	public void setFdCompanyList(List<EopBasedataCompany> fdCompanyList) {
		this.fdCompanyList = fdCompanyList;
	}

	/**
     * 人员
     */
    public List<SysOrgPerson> getFdPersonList() {
        return this.fdPersonList;
    }

    /**
     * 人员
     */
    public void setFdPersonList(List<SysOrgPerson> fdPersonList) {
        this.fdPersonList = fdPersonList;
    }

    /**
     * 组织架构
     */
    public List<SysOrgElement> getFdOrgList() {
        return this.fdOrgList;
    }

    /**
     * 组织架构
     */
    public void setFdOrgList(List<SysOrgElement> fdOrgList) {
        this.fdOrgList = fdOrgList;
    }

    /**
     * 成本中心
     */
    public List<EopBasedataCostCenter> getFdCostCenterList() {
        return this.fdCostCenterList;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenterList(List<EopBasedataCostCenter> fdCostCenterList) {
        this.fdCostCenterList = fdCostCenterList;
    }

    /**
     * 预算科目
     */
    public List<EopBasedataBudgetItem> getFdBudgetItemList() {
        return this.fdBudgetItemList;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItemList(List<EopBasedataBudgetItem> fdBudgetItemList) {
        this.fdBudgetItemList = fdBudgetItemList;
    }

    /**
     * 项目
     */
    public List<EopBasedataProject> getFdProjectList() {
        return this.fdProjectList;
    }

    /**
     * 项目
     */
    public void setFdProjectList(List<EopBasedataProject> fdProjectList) {
        this.fdProjectList = fdProjectList;
    }
}
