package com.landray.kmss.fssc.budget.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算数据
  */
public class FsscBudgetDataForm extends ExtendAuthForm {

	private static final long serialVersionUID = -8165181655524479616L;

	private static FormToModelPropertyMap toModelPropertyMap;

    private String fdYear;

    private String fdPeriodType;

    private String fdPeriod;

    private String fdMoney;

    private String fdBudgetStatus;

    private String fdRule;
    
    private String fdApply;

    private String fdElasticPercent;

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

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCompanyGroupId;

    private String fdCompanyGroupName;

    private String fdCostCenterId;

    private String fdCostCenterName;
    
    private String fdCostCenterParentName;//成本中心所属组

    private String fdBudgetItemId;

    private String fdBudgetItemName;

    private String fdProjectId;

    private String fdProjectName;

    private String fdInnerOrderId;

    private String fdInnerOrderName;

    private String fdWbsId;

    private String fdWbsName;

    private String fdPersonId;

    private String fdPersonName;

    private String fdBudgetSchemeId;

    private String fdBudgetSchemeName;

    private String fdCostCenterGroupId;

    private String fdCostCenterGroupName;

    private String fdCurrencyId;

    private String fdCurrencyName;
    
    private String fdIsKnots;
    
    private String fdTransferAcount;
    
    private String fdDeptId;
    
    private String fdDeptName;
    
    private String docMainId;
    
    private String docMainName;
    
    private String docCreatorId;
    
    private String docCreatorName;
    
    private String docCreateTime;
    

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdYear = null;
        fdPeriodType = null;
        fdPeriod = null;
        fdMoney = null;
        fdBudgetStatus = null;
        fdRule = null;
        fdApply=null;
        fdElasticPercent = null;
        fdCompanyGroupCode = null;
        fdCompanyCode = null;
        fdCostCenterGroupCode = null;
        fdCostCenterCode = null;
        fdBudgetItemCode = null;
        fdProjectCode = null;
        fdInnerOrderCode = null;
        fdWbsCode = null;
        fdPersonCode = null;
        fdDeptCode=null;
        fdBudgetSchemeCode = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCompanyGroupId = null;
        fdCompanyGroupName = null;
        fdCostCenterId = null;
        fdCostCenterName = null;
        fdCostCenterParentName=null;
        fdBudgetItemId = null;
        fdBudgetItemName = null;
        fdProjectId = null;
        fdProjectName = null;
        fdInnerOrderId = null;
        fdInnerOrderName = null;
        fdWbsId = null;
        fdWbsName = null;
        fdPersonId = null;
        fdPersonName = null;
        fdBudgetSchemeId = null;
        fdBudgetSchemeName = null;
        fdCostCenterGroupId = null;
        fdCostCenterGroupName = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        fdIsKnots=null;
        fdTransferAcount=null;
        fdDeptId=null;
        fdDeptName=null;
        docMainId=null;
        docMainName=null;
        docCreatorId=null;
        docCreatorName=null;
        docCreateTime=null;
        
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetData> getModelClass() {
        return FsscBudgetData.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdCompanyGroupId", new FormConvertor_IDToModel("fdCompanyGroup", EopBasedataCompanyGroup.class));
            toModelPropertyMap.put("fdCostCenterId", new FormConvertor_IDToModel("fdCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdBudgetItemId", new FormConvertor_IDToModel("fdBudgetItem", EopBasedataBudgetItem.class));
            toModelPropertyMap.put("fdProjectId", new FormConvertor_IDToModel("fdProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdInnerOrderId", new FormConvertor_IDToModel("fdInnerOrder", EopBasedataInnerOrder.class));
            toModelPropertyMap.put("fdWbsId", new FormConvertor_IDToModel("fdWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdBudgetSchemeId", new FormConvertor_IDToModel("fdBudgetScheme", EopBasedataBudgetScheme.class));
            toModelPropertyMap.put("fdCostCenterGroupId", new FormConvertor_IDToModel("fdCostCenterGroup", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscBudgetMain.class));
            toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgPerson.class));
        }
        return toModelPropertyMap;
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
    public String getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 金额
     */
    public void setFdMoney(String fdMoney) {
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
    public String getFdElasticPercent() {
        return this.fdElasticPercent;
    }

    /**
     * 弹性比例
     */
    public void setFdElasticPercent(String fdElasticPercent) {
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
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 公司组
     */
    public String getFdCompanyGroupId() {
        return this.fdCompanyGroupId;
    }

    /**
     * 公司组
     */
    public void setFdCompanyGroupId(String fdCompanyGroupId) {
        this.fdCompanyGroupId = fdCompanyGroupId;
    }

    /**
     * 公司组
     */
    public String getFdCompanyGroupName() {
        return this.fdCompanyGroupName;
    }

    /**
     * 公司组
     */
    public void setFdCompanyGroupName(String fdCompanyGroupName) {
        this.fdCompanyGroupName = fdCompanyGroupName;
    }

    /**
     * 成本中心
     */
    public String getFdCostCenterId() {
        return this.fdCostCenterId;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenterId(String fdCostCenterId) {
        this.fdCostCenterId = fdCostCenterId;
    }

    /**
     * 成本中心
     */
    public String getFdCostCenterName() {
        return this.fdCostCenterName;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenterName(String fdCostCenterName) {
        this.fdCostCenterName = fdCostCenterName;
    }

    /**
     * 预算科目
     */
    public String getFdBudgetItemId() {
        return this.fdBudgetItemId;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItemId(String fdBudgetItemId) {
        this.fdBudgetItemId = fdBudgetItemId;
    }

    /**
     * 预算科目
     */
    public String getFdBudgetItemName() {
        return this.fdBudgetItemName;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItemName(String fdBudgetItemName) {
        this.fdBudgetItemName = fdBudgetItemName;
    }

    /**
     * 项目
     */
    public String getFdProjectId() {
        return this.fdProjectId;
    }

    /**
     * 项目
     */
    public void setFdProjectId(String fdProjectId) {
        this.fdProjectId = fdProjectId;
    }

    /**
     * 项目
     */
    public String getFdProjectName() {
        return this.fdProjectName;
    }

    /**
     * 项目
     */
    public void setFdProjectName(String fdProjectName) {
        this.fdProjectName = fdProjectName;
    }

    /**
     * 内部订单
     */
    public String getFdInnerOrderId() {
        return this.fdInnerOrderId;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrderId(String fdInnerOrderId) {
        this.fdInnerOrderId = fdInnerOrderId;
    }

    /**
     * 内部订单
     */
    public String getFdInnerOrderName() {
        return this.fdInnerOrderName;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrderName(String fdInnerOrderName) {
        this.fdInnerOrderName = fdInnerOrderName;
    }

    /**
     * WBS
     */
    public String getFdWbsId() {
        return this.fdWbsId;
    }

    /**
     * WBS
     */
    public void setFdWbsId(String fdWbsId) {
        this.fdWbsId = fdWbsId;
    }

    /**
     * WBS
     */
    public String getFdWbsName() {
        return this.fdWbsName;
    }

    /**
     * WBS
     */
    public void setFdWbsName(String fdWbsName) {
        this.fdWbsName = fdWbsName;
    }

    /**
     * 员工
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 员工
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 员工
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 员工
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
    }

    /**
     * 预算方案
     */
    public String getFdBudgetSchemeId() {
        return this.fdBudgetSchemeId;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetSchemeId(String fdBudgetSchemeId) {
        this.fdBudgetSchemeId = fdBudgetSchemeId;
    }

    /**
     * 预算方案
     */
    public String getFdBudgetSchemeName() {
        return this.fdBudgetSchemeName;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetSchemeName(String fdBudgetSchemeName) {
        this.fdBudgetSchemeName = fdBudgetSchemeName;
    }

    /**
     * 成本中心组
     */
    public String getFdCostCenterGroupId() {
        return this.fdCostCenterGroupId;
    }

    /**
     * 成本中心组
     */
    public void setFdCostCenterGroupId(String fdCostCenterGroupId) {
        this.fdCostCenterGroupId = fdCostCenterGroupId;
    }

    /**
     * 成本中心组
     */
    public String getFdCostCenterGroupName() {
        return this.fdCostCenterGroupName;
    }

    /**
     * 成本中心组
     */
    public void setFdCostCenterGroupName(String fdCostCenterGroupName) {
        this.fdCostCenterGroupName = fdCostCenterGroupName;
    }

    /**
     * 币种汇率
     */
    public String getFdCurrencyId() {
        return this.fdCurrencyId;
    }

    /**
     * 币种汇率
     */
    public void setFdCurrencyId(String fdCurrencyId) {
        this.fdCurrencyId = fdCurrencyId;
    }

    /**
     * 币种汇率
     */
    public String getFdCurrencyName() {
        return this.fdCurrencyName;
    }

    /**
     * 币种汇率
     */
    public void setFdCurrencyName(String fdCurrencyName) {
        this.fdCurrencyName = fdCurrencyName;
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

	  public String getFdTransferAcount() {
		return fdTransferAcount;
	}
	  
	  /**
	   * 结转金额
	   */
	  public void setFdTransferAcount(String fdTransferAcount) {
			this.fdTransferAcount = fdTransferAcount;
		}
	  
	  /**
	     * 部门ID
	     */
		public String getFdDeptId() {
			return fdDeptId;
		}
		
		/**
	     * 部门ID
	     */
		public void setFdDeptId(String fdDeptId) {
			this.fdDeptId = fdDeptId;
		}
		
		/**
	     * 部门名称
	     */
		public String getFdDeptName() {
			return fdDeptName;
		}
		
		/**
	     * 部门名称
	     */
		public void setFdDeptName(String fdDeptName) {
			this.fdDeptName = fdDeptName;
		}
		
		/**
	     * 主表
	     */
	    public String getDocMainId() {
	        return this.docMainId;
	    }

	    /**
	     * 主表
	     */
	    public void setDocMainId(String docMainId) {
	        this.docMainId = docMainId;
	    }

	    /**
	     * 主表
	     */
	    public String getDocMainName() {
	        return this.docMainName;
	    }

	    /**
	     * 主表
	     */
	    public void setDocMainName(String docMainName) {
	        this.docMainName = docMainName;
	    }
	    /**
	     * 创建者ID
	     */
		public String getDocCreatorId() {
			return docCreatorId;
		}
		/**
	     * 创建者ID
	     */
		public void setDocCreatorId(String docCreatorId) {
			this.docCreatorId = docCreatorId;
		}
		/**
	     * 创建者Name
	     */
		public String getDocCreatorName() {
			return docCreatorName;
		}
		/**
	     * 创建者Name
	     */
		public void setDocCreatorName(String docCreatorName) {
			this.docCreatorName = docCreatorName;
		}
		/**
	     * 创建者时间
	     */
		public String getDocCreateTime() {
			return docCreateTime;
		}
		/**
	     * 创建者时间
	     */
		public void setDocCreateTime(String docCreateTime) {
			this.docCreateTime = docCreateTime;
		}
		@Override
        public String getAuthReaderNoteFlag() {
            return "2";
        }
        /**
         * 所有人可阅读标记
         */
        private String authReaderFlag = null;

        /**
         * @return 返回 所有人可阅读标记
         */
        public String getAuthReaderFlag() {
            return authReaderFlag;
        }

        /**
         * @param authReaderFlag
         *            要设置的 所有人可阅读标记
         */
        public void setAuthReaderFlag(String authReaderFlag) {
            this.authReaderFlag = authReaderFlag;
        }

		public String getFdCostCenterParentName() {
			return fdCostCenterParentName;
		}

		public void setFdCostCenterParentName(String fdCostCenterParentName) {
			this.fdCostCenterParentName = fdCostCenterParentName;
		}
	    
}
