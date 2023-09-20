package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCostType;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 成本中心
  */
public class EopBasedataCostCenterForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIsGroup;

    private String fdCode;

    private String fdIsAvailable;
    
    private String fdJoinSystem;

  	private String fdSystemParam;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String fdTypeId;

    private String fdTypeName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

	protected String fdParentId;

	protected String fdParentName;

    private String fdFirstChargerIds;

    private String fdFirstChargerNames;

    private String fdSecondChargerIds;

    private String fdSecondChargerNames;

    private String fdManagerIds;

    private String fdManagerNames;

    private String fdBudgetManagerIds;

    private String fdBudgetManagerNames;

    private String fdEkpOrgIds;

    private String fdEkpOrgNames;

    private String fdOrder;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdIsGroup = null;
        fdCode = null;
        fdIsAvailable = null;
        fdJoinSystem = null;
		fdSystemParam = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        fdTypeId = null;
        fdTypeName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
		fdParentId = null;
		fdParentName = null;
        fdFirstChargerIds = null;
        fdFirstChargerNames = null;
        fdSecondChargerIds = null;
        fdSecondChargerNames = null;
        fdManagerIds = null;
        fdManagerNames = null;
        fdBudgetManagerIds = null;
        fdBudgetManagerNames = null;
        fdEkpOrgIds = null;
        fdEkpOrgNames = null;
        fdOrder=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataCostCenter> getModelClass() {
        return EopBasedataCostCenter.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("fdTypeId", new FormConvertor_IDToModel("fdType", EopBasedataCostType.class));
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdFirstChargerIds", new FormConvertor_IDsToModelList("fdFirstCharger", SysOrgPerson.class));
            toModelPropertyMap.put("fdSecondChargerIds", new FormConvertor_IDsToModelList("fdSecondCharger", SysOrgPerson.class));
            toModelPropertyMap.put("fdManagerIds", new FormConvertor_IDsToModelList("fdManager", SysOrgPerson.class));
            toModelPropertyMap.put("fdBudgetManagerIds", new FormConvertor_IDsToModelList("fdBudgetManager", SysOrgPerson.class));
            toModelPropertyMap.put("fdEkpOrgIds", new FormConvertor_IDsToModelList("fdEkpOrg", SysOrgElement.class));
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
	 * 对接财务系统参数
	 */

	public String getFdSystemParam() {
		return fdSystemParam;
	}

	/**
	 * 对接财务系统参数
	 */
	public void setFdSystemParam(String fdSystemParam) {
		this.fdSystemParam = fdSystemParam;
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
     * 成本中心类型
     */
    public String getFdTypeId() {
        return this.fdTypeId;
    }

    /**
     * 成本中心类型
     */
    public void setFdTypeId(String fdTypeId) {
        this.fdTypeId = fdTypeId;
    }

    /**
     * 成本中心类型
     */
    public String getFdTypeName() {
        return this.fdTypeName;
    }

    /**
     * 成本中心类型
     */
    public void setFdTypeName(String fdTypeName) {
        this.fdTypeName = fdTypeName;
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
	 * @return 所属成本中心组的ID
	 */
	public String getFdParentId() {
		return fdParentId;
	}

	/**
	 * @param fdParentId
	 *            所属成本中心组的ID
	 */
	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
	 * 所属成本中心组的名称
	 */

	/**
	 * @return 所属成本中心组的名称
	 */
	public String getFdParentName() {
		return fdParentName;
	}

	/**
	 * @param fdParentName
	 *            所属成本中心组的名称
	 */
	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

    /**
     * 第一负责人
     */
    public String getFdFirstChargerIds() {
        return this.fdFirstChargerIds;
    }

    /**
     * 第一负责人
     */
    public void setFdFirstChargerIds(String fdFirstChargerIds) {
        this.fdFirstChargerIds = fdFirstChargerIds;
    }

    /**
     * 第一负责人
     */
    public String getFdFirstChargerNames() {
        return this.fdFirstChargerNames;
    }

    /**
     * 第一负责人
     */
    public void setFdFirstChargerNames(String fdFirstChargerNames) {
        this.fdFirstChargerNames = fdFirstChargerNames;
    }

    /**
     * 第二负责人
     */
    public String getFdSecondChargerIds() {
        return this.fdSecondChargerIds;
    }

    /**
     * 第二负责人
     */
    public void setFdSecondChargerIds(String fdSecondChargerIds) {
        this.fdSecondChargerIds = fdSecondChargerIds;
    }

    /**
     * 第二负责人
     */
    public String getFdSecondChargerNames() {
        return this.fdSecondChargerNames;
    }

    /**
     * 第二负责人
     */
    public void setFdSecondChargerNames(String fdSecondChargerNames) {
        this.fdSecondChargerNames = fdSecondChargerNames;
    }

    /**
     * 业务财务经理
     */
    public String getFdManagerIds() {
        return this.fdManagerIds;
    }

    /**
     * 业务财务经理
     */
    public void setFdManagerIds(String fdManagerIds) {
        this.fdManagerIds = fdManagerIds;
    }

    /**
     * 业务财务经理
     */
    public String getFdManagerNames() {
        return this.fdManagerNames;
    }

    /**
     * 业务财务经理
     */
    public void setFdManagerNames(String fdManagerNames) {
        this.fdManagerNames = fdManagerNames;
    }

    /**
     * 预算管理员
     */
    public String getFdBudgetManagerIds() {
        return this.fdBudgetManagerIds;
    }

    /**
     * 预算管理员
     */
    public void setFdBudgetManagerIds(String fdBudgetManagerIds) {
        this.fdBudgetManagerIds = fdBudgetManagerIds;
    }

    /**
     * 预算管理员
     */
    public String getFdBudgetManagerNames() {
        return this.fdBudgetManagerNames;
    }

    /**
     * 预算管理员
     */
    public void setFdBudgetManagerNames(String fdBudgetManagerNames) {
        this.fdBudgetManagerNames = fdBudgetManagerNames;
    }

    /**
     * 对应行政组织
     */
    public String getFdEkpOrgIds() {
        return this.fdEkpOrgIds;
    }

    /**
     * 对应行政组织
     */
    public void setFdEkpOrgIds(String fdEkpOrgIds) {
        this.fdEkpOrgIds = fdEkpOrgIds;
    }

    /**
     * 对应行政组织
     */
    public String getFdEkpOrgNames() {
        return this.fdEkpOrgNames;
    }

    /**
     * 对应行政组织
     */
    public void setFdEkpOrgNames(String fdEkpOrgNames) {
        this.fdEkpOrgNames = fdEkpOrgNames;
    }
    /**
     * 排序号
     */
    public String getFdOrder() {
        return fdOrder;
    }
    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
    }
}
