package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseTreeModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataCostCenterForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 成本中心
  */
public class EopBasedataCostCenter extends BaseTreeModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdIsGroup;

    private String fdCode;

    private Boolean fdIsAvailable;
    
    private String fdJoinSystem;

  	private String fdSystemParam;

    private Date docCreateTime;

    private Date docAlterTime;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private EopBasedataCostType fdType;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private Integer fdOrder;

    private List<SysOrgPerson> fdFirstCharger = new ArrayList<SysOrgPerson>();

    private List<SysOrgPerson> fdSecondCharger = new ArrayList<SysOrgPerson>();

    private List<SysOrgPerson> fdManager = new ArrayList<SysOrgPerson>();

    private List<SysOrgPerson> fdBudgetManager = new ArrayList<SysOrgPerson>();

    private List<SysOrgElement> fdEkpOrg = new ArrayList<SysOrgElement>();

    @Override
    public Class<EopBasedataCostCenterForm> getFormClass() {
        return EopBasedataCostCenterForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdType.fdName", "fdTypeName");
            toFormPropertyMap.put("fdType.fdId", "fdTypeId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
            toFormPropertyMap.put("fdFirstCharger", new ModelConvertor_ModelListToString("fdFirstChargerIds:fdFirstChargerNames", "fdId:fdName"));
            toFormPropertyMap.put("fdSecondCharger", new ModelConvertor_ModelListToString("fdSecondChargerIds:fdSecondChargerNames", "fdId:fdName"));
            toFormPropertyMap.put("fdManager", new ModelConvertor_ModelListToString("fdManagerIds:fdManagerNames", "fdId:fdName"));
            toFormPropertyMap.put("fdBudgetManager", new ModelConvertor_ModelListToString("fdBudgetManagerIds:fdBudgetManagerNames", "fdId:fdName"));
            toFormPropertyMap.put("fdEkpOrg", new ModelConvertor_ModelListToString("fdEkpOrgIds:fdEkpOrgNames", "fdId:fdName"));
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
     * 成本中心/组
     */
    public String getFdIsGroup() {
        return this.fdIsGroup;
    }

    /**
     * 成本中心/组
     */
    public void setFdIsGroup(String fdIsGroup) {
        this.fdIsGroup = fdIsGroup;
    }

    /**
     * 编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
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
     * 对接财务系统
     */
    public String getFdJoinSystem() {
        return this.fdJoinSystem;
    }

    /**
     * 对接财务系统
     */
    public void setFdJoinSystem(String fdJoinSystem) {
        this.fdJoinSystem = fdJoinSystem;
    }

	/**
	 * 对接财务系统
	 */

	public String getFdSystemParam() {
		return fdSystemParam;
	}

	/**
	 * 对接财务系统
	 */
	public void setFdSystemParam(String fdSystemParam) {
		this.fdSystemParam = fdSystemParam;
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
        return fdOrder;
    }
    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
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
     * 成本中心类型
     */
    public EopBasedataCostType getFdType() {
        return this.fdType;
    }

    /**
     * 成本中心类型
     */
    public void setFdType(EopBasedataCostType fdType) {
        this.fdType = fdType;
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
     * 第一负责人
     */
    public List<SysOrgPerson> getFdFirstCharger() {
        return this.fdFirstCharger;
    }

    /**
     * 第一负责人
     */
    public void setFdFirstCharger(List<SysOrgPerson> fdFirstCharger) {
        this.fdFirstCharger = fdFirstCharger;
    }

    /**
     * 第二负责人
     */
    public List<SysOrgPerson> getFdSecondCharger() {
        return this.fdSecondCharger;
    }

    /**
     * 第二负责人
     */
    public void setFdSecondCharger(List<SysOrgPerson> fdSecondCharger) {
        this.fdSecondCharger = fdSecondCharger;
    }

    /**
     * 业务财务经理
     */
    public List<SysOrgPerson> getFdManager() {
        return this.fdManager;
    }

    /**
     * 业务财务经理
     */
    public void setFdManager(List<SysOrgPerson> fdManager) {
        this.fdManager = fdManager;
    }

    /**
     * 预算管理员
     */
    public List<SysOrgPerson> getFdBudgetManager() {
        return this.fdBudgetManager;
    }

    /**
     * 预算管理员
     */
    public void setFdBudgetManager(List<SysOrgPerson> fdBudgetManager) {
        this.fdBudgetManager = fdBudgetManager;
    }

    /**
     * 对应行政组织
     */
    public List<SysOrgElement> getFdEkpOrg() {
        return this.fdEkpOrg;
    }

    /**
     * 对应行政组织
     */
    public void setFdEkpOrg(List<SysOrgElement> fdEkpOrg) {
        this.fdEkpOrg = fdEkpOrg;
    }
}
