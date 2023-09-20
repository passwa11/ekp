package com.landray.kmss.fssc.budget.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budget.model.FsscBudgetDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算明细
  */
public class FsscBudgetDetailForm extends ExtendForm {

	private static final long serialVersionUID = 3627904831854176553L;

	private static FormToModelPropertyMap toModelPropertyMap;

    private String fdYearMoney;

    private String fdFirstQuarterMoney;

    private String fdJanMoney;

    private String fdFebMoney;

    private String fdMarMoney;

    private String fdSecondQuarterMoney;

    private String fdAprMoney;

    private String fdMayMoney;

    private String fdJunMoney;

    private String fdThirdQuarterMoney;

    private String fdJulMoney;

    private String fdAugMoney;

    private String fdSeptMoney;

    private String fdFourthQuarterMoney;

    private String fdOctMoney;

    private String fdNovMoney;

    private String fdDecMoney;
    
    private String fdMoney;

    private String fdYearRule;

    private String fdQuarterRule;

    private String fdMonthRule;
    
    private String fdRule;
    
    private String fdYearApply;
    
    private String fdQuarterApply;
    
    private String fdMonthApply;
    
    private String fdApply;

    private String fdElasticPercent;
    
    private String fdIsEnable;

    private String fdCostCenterGroupId;

    private String fdCostCenterGroupName;

    private String fdCostCenterId;

    private String fdCostCenterName;

    private String fdBudgetItemId;

    private String fdBudgetItemName;

    private String fdProjectId;

    private String fdProjectName;

    private String fdInnerOrderId;

    private String fdInnerOrderName;

    private String fdPersonId;

    private String fdPersonName;

    private String fdWbsId;

    private String fdWbsName;
    
    private String fdDeptId;
    
    private String fdDeptName;
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdYearMoney = null;
        fdFirstQuarterMoney = null;
        fdJanMoney = null;
        fdFebMoney = null;
        fdMarMoney = null;
        fdSecondQuarterMoney = null;
        fdAprMoney = null;
        fdMayMoney = null;
        fdJunMoney = null;
        fdThirdQuarterMoney = null;
        fdJulMoney = null;
        fdAugMoney = null;
        fdSeptMoney = null;
        fdFourthQuarterMoney = null;
        fdOctMoney = null;
        fdNovMoney = null;
        fdDecMoney = null;
        fdYearRule = null;
        fdQuarterRule = null;
        fdMonthRule = null;
        fdYearApply=null;
        fdQuarterApply=null;
        fdMonthApply=null;
        fdApply=null;
        fdElasticPercent = null;
        fdIsEnable=null;
        fdCostCenterGroupId = null;
        fdCostCenterGroupName = null;
        fdCostCenterId = null;
        fdCostCenterName = null;
        fdBudgetItemId = null;
        fdBudgetItemName = null;
        fdProjectId = null;
        fdProjectName = null;
        fdInnerOrderId = null;
        fdInnerOrderName = null;
        fdPersonId = null;
        fdPersonName = null;
        fdWbsId = null;
        fdWbsName = null;
        fdDeptId=null;
        fdDeptName=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetDetail> getModelClass() {
        return FsscBudgetDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCostCenterGroupId", new FormConvertor_IDToModel("fdCostCenterGroup", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdCostCenterId", new FormConvertor_IDToModel("fdCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdBudgetItemId", new FormConvertor_IDToModel("fdBudgetItem", EopBasedataBudgetItem.class));
            toModelPropertyMap.put("fdProjectId", new FormConvertor_IDToModel("fdProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdInnerOrderId", new FormConvertor_IDToModel("fdInnerOrder", EopBasedataInnerOrder.class));
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdWbsId", new FormConvertor_IDToModel("fdWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdDeptId", new FormConvertor_IDToModel("fdDept", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 年
     */
    public String getFdYearMoney() {
        return this.fdYearMoney;
    }

    /**
     * 年
     */
    public void setFdYearMoney(String fdYearMoney) {
        this.fdYearMoney = fdYearMoney;
    }

    /**
     * 第一季度
     */
    public String getFdFirstQuarterMoney() {
        return this.fdFirstQuarterMoney;
    }

    /**
     * 第一季度
     */
    public void setFdFirstQuarterMoney(String fdFirstQuarterMoney) {
        this.fdFirstQuarterMoney = fdFirstQuarterMoney;
    }

    /**
     * 1月
     */
    public String getFdJanMoney() {
        return this.fdJanMoney;
    }

    /**
     * 1月
     */
    public void setFdJanMoney(String fdJanMoney) {
        this.fdJanMoney = fdJanMoney;
    }

    /**
     * 2月
     */
    public String getFdFebMoney() {
        return this.fdFebMoney;
    }

    /**
     * 2月
     */
    public void setFdFebMoney(String fdFebMoney) {
        this.fdFebMoney = fdFebMoney;
    }

    /**
     * 3月
     */
    public String getFdMarMoney() {
        return this.fdMarMoney;
    }

    /**
     * 3月
     */
    public void setFdMarMoney(String fdMarMoney) {
        this.fdMarMoney = fdMarMoney;
    }

    /**
     * 第二季度
     */
    public String getFdSecondQuarterMoney() {
        return this.fdSecondQuarterMoney;
    }

    /**
     * 第二季度
     */
    public void setFdSecondQuarterMoney(String fdSecondQuarterMoney) {
        this.fdSecondQuarterMoney = fdSecondQuarterMoney;
    }

    /**
     * 4月
     */
    public String getFdAprMoney() {
        return this.fdAprMoney;
    }

    /**
     * 4月
     */
    public void setFdAprMoney(String fdAprMoney) {
        this.fdAprMoney = fdAprMoney;
    }

    /**
     * 5月
     */
    public String getFdMayMoney() {
        return this.fdMayMoney;
    }

    /**
     * 5月
     */
    public void setFdMayMoney(String fdMayMoney) {
        this.fdMayMoney = fdMayMoney;
    }

    /**
     * 6月
     */
    public String getFdJunMoney() {
        return this.fdJunMoney;
    }

    /**
     * 6月
     */
    public void setFdJunMoney(String fdJunMoney) {
        this.fdJunMoney = fdJunMoney;
    }

    /**
     * 第三季度
     */
    public String getFdThirdQuarterMoney() {
        return this.fdThirdQuarterMoney;
    }

    /**
     * 第三季度
     */
    public void setFdThirdQuarterMoney(String fdThirdQuarterMoney) {
        this.fdThirdQuarterMoney = fdThirdQuarterMoney;
    }

    /**
     * 7月
     */
    public String getFdJulMoney() {
        return this.fdJulMoney;
    }

    /**
     * 7月
     */
    public void setFdJulMoney(String fdJulMoney) {
        this.fdJulMoney = fdJulMoney;
    }

    /**
     * 8月
     */
    public String getFdAugMoney() {
        return this.fdAugMoney;
    }

    /**
     * 8月
     */
    public void setFdAugMoney(String fdAugMoney) {
        this.fdAugMoney = fdAugMoney;
    }

    /**
     * 9月
     */
    public String getFdSeptMoney() {
        return this.fdSeptMoney;
    }

    /**
     * 9月
     */
    public void setFdSeptMoney(String fdSeptMoney) {
        this.fdSeptMoney = fdSeptMoney;
    }

    /**
     * 第四季度
     */
    public String getFdFourthQuarterMoney() {
        return this.fdFourthQuarterMoney;
    }

    /**
     * 第四季度
     */
    public void setFdFourthQuarterMoney(String fdFourthQuarterMoney) {
        this.fdFourthQuarterMoney = fdFourthQuarterMoney;
    }

    /**
     * 10月
     */
    public String getFdOctMoney() {
        return this.fdOctMoney;
    }

    /**
     * 10月
     */
    public void setFdOctMoney(String fdOctMoney) {
        this.fdOctMoney = fdOctMoney;
    }

    /**
     * 11月
     */
    public String getFdNovMoney() {
        return this.fdNovMoney;
    }

    /**
     * 11月
     */
    public void setFdNovMoney(String fdNovMoney) {
        this.fdNovMoney = fdNovMoney;
    }

    /**
     * 12月
     */
    public String getFdDecMoney() {
        return this.fdDecMoney;
    }

    /**
     * 12月
     */
    public void setFdDecMoney(String fdDecMoney) {
        this.fdDecMoney = fdDecMoney;
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
     * 年度控制规则
     */
    public String getFdYearRule() {
        return this.fdYearRule;
    }

    /**
     * 年度控制规则
     */
    public void setFdYearRule(String fdYearRule) {
        this.fdYearRule = fdYearRule;
    }

    /**
     * 季度控制规则
     */
    public String getFdQuarterRule() {
        return this.fdQuarterRule;
    }

    /**
     * 季度控制规则
     */
    public void setFdQuarterRule(String fdQuarterRule) {
        this.fdQuarterRule = fdQuarterRule;
    }

    /**
     * 月度控制规则
     */
    public String getFdMonthRule() {
        return this.fdMonthRule;
    }

    /**
     * 月度控制规则
     */
    public void setFdMonthRule(String fdMonthRule) {
        this.fdMonthRule = fdMonthRule;
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
     * 年度运用规则
     */
    public String getFdYearApply() {
		return fdYearApply;
	}

    /**
     * 年度运用规则
     */
	public void setFdYearApply(String fdYearApply) {
		this.fdYearApply = fdYearApply;
	}

	 /**
     * 季度运用规则
     */
	public String getFdQuarterApply() {
		return fdQuarterApply;
	}
	 /**
     * 季度运用规则
     */
	public void setFdQuarterApply(String fdQuarterApply) {
		this.fdQuarterApply = fdQuarterApply;
	}

	 /**
     * 月度运用规则
     */
	public String getFdMonthApply() {
		return fdMonthApply;
	}

	/**
     * 月度运用规则
     */
	public void setFdMonthApply(String fdMonthApply) {
		this.fdMonthApply = fdMonthApply;
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
     * 是否启用  0/null：未生成预算，1：已生成预算
     */
    public String getFdIsEnable() {
		return fdIsEnable;
	}
    
    /**
     * 是否启用  0/null：未生成预算，1：已生成预算
     */
    public void setFdIsEnable(String fdIsEnable) {
		this.fdIsEnable = fdIsEnable;
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
    
    
}
