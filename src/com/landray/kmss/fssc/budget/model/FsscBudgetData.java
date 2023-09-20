package com.landray.kmss.fssc.budget.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budget.forms.FsscBudgetDataForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

/**
  * 预算数据
  */
public class FsscBudgetData extends ExtendAuthModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdYear;

    private String fdPeriodType;

    private String fdPeriod;

    private Double fdMoney;

    private String fdBudgetStatus;

    private String fdRule;
    
    private String fdApply;

    private Double fdElasticPercent;

    private String fdCompanyGroupCode;

    private String fdCompanyCode;

    private String fdCostCenterGroupCode;

    private String fdCostCenterCode;

    private String fdBudgetItemCode;

    private String fdProjectCode;

    private String fdInnerOrderCode;

    private String fdWbsCode;

    private String fdPersonCode;
    
    private String fdDeptCode;

    private String fdBudgetSchemeCode;

    private EopBasedataCompany fdCompany;

    private EopBasedataCompanyGroup fdCompanyGroup;

    private EopBasedataCostCenter fdCostCenter;

    private EopBasedataBudgetItem fdBudgetItem;
    private EopBasedataBudgetItem fdBudgetItemParent;

    private EopBasedataProject fdProject;

    private EopBasedataInnerOrder fdInnerOrder;

    private EopBasedataWbs fdWbs;

    private SysOrgPerson fdPerson;

    private EopBasedataBudgetScheme fdBudgetScheme;

    private EopBasedataCostCenter fdCostCenterGroup;

    private EopBasedataCurrency fdCurrency;  
    
    private String fdIsKnots;
    
    private Double fdTransferAcount;
    
    private SysOrgElement fdDept;
    
    private SysOrgPerson docCreator;
    
    private Date docCreateTime;

    @Override
    public Class<FsscBudgetDataForm> getFormClass() {
        return FsscBudgetDataForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdCompanyGroup.fdName", "fdCompanyGroupName");
            toFormPropertyMap.put("fdCompanyGroup.fdId", "fdCompanyGroupId");
            toFormPropertyMap.put("fdCostCenter.fdName", "fdCostCenterName");
            toFormPropertyMap.put("fdCostCenter.fdId", "fdCostCenterId");
            toFormPropertyMap.put("fdCostCenter.fdParent.fdName", "fdCostCenterParentName");

            toFormPropertyMap.put("fdBudgetItem.fdName", "fdBudgetItemName");
            toFormPropertyMap.put("fdBudgetItem.fdId", "fdBudgetItemId");
            toFormPropertyMap.put("fdProject.fdName", "fdProjectName");
            toFormPropertyMap.put("fdProject.fdId", "fdProjectId");
            toFormPropertyMap.put("fdInnerOrder.fdName", "fdInnerOrderName");
            toFormPropertyMap.put("fdInnerOrder.fdId", "fdInnerOrderId");
            toFormPropertyMap.put("fdWbs.fdName", "fdWbsName");
            toFormPropertyMap.put("fdWbs.fdId", "fdWbsId");
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("fdBudgetScheme.fdName", "fdBudgetSchemeName");
            toFormPropertyMap.put("fdBudgetScheme.fdId", "fdBudgetSchemeId");
            toFormPropertyMap.put("fdCostCenterGroup.fdName", "fdCostCenterGroupName");
            toFormPropertyMap.put("fdCostCenterGroup.fdId", "fdCostCenterGroupId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    public EopBasedataBudgetItem getFdBudgetItemParent() {
		return fdBudgetItemParent;
	}

	public void setFdBudgetItemParent(EopBasedataBudgetItem fdBudgetItemParent) {
		this.fdBudgetItemParent = fdBudgetItemParent;
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
     * 区间类型
     */
    public String getFdPeriodType() {
        return this.fdPeriodType;
    }

    /**
     * 区间类型
     */
    public void setFdPeriodType(String fdPeriodType) {
        this.fdPeriodType = fdPeriodType;
    }

    /**
     * 期间值
     */
    public String getFdPeriod() {
        return this.fdPeriod;
    }

    /**
     * 期间值
     */
    public void setFdPeriod(String fdPeriod) {
        this.fdPeriod = fdPeriod;
    }

    /**
     * 金额
     */
    public Double getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 金额
     */
    public void setFdMoney(Double fdMoney) {
        this.fdMoney = fdMoney;
    }

    /**
     * 预算状态
     */
    public String getFdBudgetStatus() {
        return this.fdBudgetStatus;
    }

    /**
     * 预算状态
     */
    public void setFdBudgetStatus(String fdBudgetStatus) {
        this.fdBudgetStatus = fdBudgetStatus;
    }

    /**
     * 控制规则
     */
    public String getFdRule() {
        return this.fdRule;
    }

    /**
     * 控制规则
     */
    public void setFdRule(String fdRule) {
        this.fdRule = fdRule;
    }
    
    /**
     * 运用规则
     */
    public String getFdApply() {
		return fdApply;
	}
    /**
     * 运用规则
     */
	public void setFdApply(String fdApply) {
		this.fdApply = fdApply;
	}

    /**
     * 弹性比例
     */
    public Double getFdElasticPercent() {
        return this.fdElasticPercent;
    }

    /**
     * 弹性比例
     */
    public void setFdElasticPercent(Double fdElasticPercent) {
        this.fdElasticPercent = fdElasticPercent;
    }

    /**
     * 公司组编号
     */
    public String getFdCompanyGroupCode() {
        return this.fdCompanyGroupCode;
    }

    /**
     * 公司组编号
     */
    public void setFdCompanyGroupCode(String fdCompanyGroupCode) {
        this.fdCompanyGroupCode = fdCompanyGroupCode;
    }

    /**
     * 公司编号
     */
    public String getFdCompanyCode() {
        return this.fdCompanyCode;
    }

    /**
     * 公司编号
     */
    public void setFdCompanyCode(String fdCompanyCode) {
        this.fdCompanyCode = fdCompanyCode;
    }

    /**
     * 成本中心组编号
     */
    public String getFdCostCenterGroupCode() {
        return this.fdCostCenterGroupCode;
    }

    /**
     * 成本中心组编号
     */
    public void setFdCostCenterGroupCode(String fdCostCenterGroupCode) {
        this.fdCostCenterGroupCode = fdCostCenterGroupCode;
    }

    /**
     * 成本中心编码
     */
    public String getFdCostCenterCode() {
        return this.fdCostCenterCode;
    }

    /**
     * 成本中心编码
     */
    public void setFdCostCenterCode(String fdCostCenterCode) {
        this.fdCostCenterCode = fdCostCenterCode;
    }

    /**
     * 预算科目编码
     */
    public String getFdBudgetItemCode() {
        return this.fdBudgetItemCode;
    }

    /**
     * 预算科目编码
     */
    public void setFdBudgetItemCode(String fdBudgetItemCode) {
        this.fdBudgetItemCode = fdBudgetItemCode;
    }

    /**
     * 项目编码
     */
    public String getFdProjectCode() {
        return this.fdProjectCode;
    }

    /**
     * 项目编码
     */
    public void setFdProjectCode(String fdProjectCode) {
        this.fdProjectCode = fdProjectCode;
    }

    /**
     * 内部订单
     */
    public String getFdInnerOrderCode() {
        return this.fdInnerOrderCode;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrderCode(String fdInnerOrderCode) {
        this.fdInnerOrderCode = fdInnerOrderCode;
    }

    /**
     * WBS号
     */
    public String getFdWbsCode() {
        return this.fdWbsCode;
    }

    /**
     * WBS号
     */
    public void setFdWbsCode(String fdWbsCode) {
        this.fdWbsCode = fdWbsCode;
    }

    /**
     * 员工编码
     */
    public String getFdPersonCode() {
        return this.fdPersonCode;
    }

    /**
     * 员工编码
     */
    public void setFdPersonCode(String fdPersonCode) {
        this.fdPersonCode = fdPersonCode;
    }
    
    
    /**
     * 部门编码
     */
    public String getFdDeptCode() {
		return fdDeptCode;
	}
    /**
     * 部门编码
     */
	public void setFdDeptCode(String fdDeptCode) {
		this.fdDeptCode = fdDeptCode;
	}

	/**
     * 预算方案编码
     */
    public String getFdBudgetSchemeCode() {
        return this.fdBudgetSchemeCode;
    }

    /**
     * 预算方案编码
     */
    public void setFdBudgetSchemeCode(String fdBudgetSchemeCode) {
        this.fdBudgetSchemeCode = fdBudgetSchemeCode;
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
     * 公司组
     */
    public EopBasedataCompanyGroup getFdCompanyGroup() {
        return this.fdCompanyGroup;
    }

    /**
     * 公司组
     */
    public void setFdCompanyGroup(EopBasedataCompanyGroup fdCompanyGroup) {
        this.fdCompanyGroup = fdCompanyGroup;
    }

    /**
     * 成本中心
     */
    public EopBasedataCostCenter getFdCostCenter() {
        return this.fdCostCenter;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenter(EopBasedataCostCenter fdCostCenter) {
        this.fdCostCenter = fdCostCenter;
    }

    /**
     * 预算科目
     */
    public EopBasedataBudgetItem getFdBudgetItem() {
        return this.fdBudgetItem;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItem(EopBasedataBudgetItem fdBudgetItem) {
        this.fdBudgetItem = fdBudgetItem;
    }

    /**
     * 项目
     */
    public EopBasedataProject getFdProject() {
        return this.fdProject;
    }

    /**
     * 项目
     */
    public void setFdProject(EopBasedataProject fdProject) {
        this.fdProject = fdProject;
    }

    /**
     * 内部订单
     */
    public EopBasedataInnerOrder getFdInnerOrder() {
        return this.fdInnerOrder;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrder(EopBasedataInnerOrder fdInnerOrder) {
        this.fdInnerOrder = fdInnerOrder;
    }

    /**
     * WBS
     */
    public EopBasedataWbs getFdWbs() {
        return this.fdWbs;
    }

    /**
     * WBS
     */
    public void setFdWbs(EopBasedataWbs fdWbs) {
        this.fdWbs = fdWbs;
    }

    /**
     * 员工
     */
    public SysOrgPerson getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 员工
     */
    public void setFdPerson(SysOrgPerson fdPerson) {
        this.fdPerson = fdPerson;
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
     * 成本中心组
     */
    public EopBasedataCostCenter getFdCostCenterGroup() {
        return this.fdCostCenterGroup;
    }

    /**
     * 成本中心组
     */
    public void setFdCostCenterGroup(EopBasedataCostCenter fdCostCenterGroup) {
        this.fdCostCenterGroup = fdCostCenterGroup;
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
     * 是否结转，0或者nul未结转，1：已结转
     */
	  public String getFdIsKnots() {
			return fdIsKnots;
		}
    
	  /**
	     * 是否结转，0或者nul未结转，1：已结转
	     */
	  public void setFdIsKnots(String fdIsKnots) {
			this.fdIsKnots = fdIsKnots;
		}

	  /**
	   * 结转金额
	   */

	  public Double getFdTransferAcount() {
		return fdTransferAcount;
	  }
	  
	  /**
	   * 结转金额
	   */
	  public void setFdTransferAcount(Double fdTransferAcount) {
			this.fdTransferAcount = fdTransferAcount;
	  }
	  
	  /**
	     * 部门
	     */
		public SysOrgElement getFdDept() {
			return fdDept;
		}
		
		 /**
	     * 部门
	     */
		public void setFdDept(SysOrgElement fdDept) {
			this.fdDept = fdDept;
		}
		 /**
	     * 创建人
	     */
	    @Override
        public SysOrgPerson getDocCreator() {
	        return this.docCreator;
	    }

	    /**
	     * 创建人
	     */
	    @Override
        public void setDocCreator(SysOrgPerson docCreator) {
	        this.docCreator = docCreator;
	    }
	    /**
	     * 创建时间
	     */
		@Override
        public Date getDocCreateTime() {
			return docCreateTime;
		}
		 /**
	     * 创建时间
	     */
		@Override
        public void setDocCreateTime(Date docCreateTime) {
			this.docCreateTime = docCreateTime;
		}
		
		@Override
		public String getDocSubject() {
			return null;
		}

		/**
		 * @return 返回 所有人可阅读标记
		 */
		@Override
        public java.lang.Boolean getAuthReaderFlag() {
			if (authReaderFlag == null) {
				return new Boolean(false);
			}
			return authReaderFlag;
		}

		/**
		 * @param authReaderFlag
		 *            要设置的 所有人可阅读标记
		 */
		@Override
        public void setAuthReaderFlag(java.lang.Boolean authReaderFlag) {
			this.authReaderFlag = authReaderFlag;
		}

		@Override
        protected void recalculateReaderField() {
			if (this.authAllReaders == null) {
                this.authAllReaders = new ArrayList();
            } else {
				this.authAllReaders.clear();
			}
			if (getAuthReaderFlag().booleanValue()) {
				this.authAllReaders.add(UserUtil.getEveryoneUser());
				return;
			}
	
			List tmpList = getAuthOtherReaders();
			if (tmpList != null) {
				ArrayUtil.concatTwoList(tmpList, this.authAllReaders);
			}
			tmpList = getAuthReaders();
			if (tmpList != null) {
				ArrayUtil.concatTwoList(tmpList, this.authAllReaders);
			}
		}
	    
}
