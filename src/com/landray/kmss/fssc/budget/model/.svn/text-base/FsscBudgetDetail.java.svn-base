package com.landray.kmss.fssc.budget.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budget.forms.FsscBudgetDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 预算明细
  */
public class FsscBudgetDetail extends BaseModel {

	private static final long serialVersionUID = -6023994207668643935L;

	private static ModelToFormPropertyMap toFormPropertyMap;

    private Double fdYearMoney;

    private Double fdFirstQuarterMoney;

    private Double fdJanMoney;

    private Double fdFebMoney;

    private Double fdMarMoney;

    private Double fdSecondQuarterMoney;

    private Double fdAprMoney;

    private Double fdMayMoney;

    private Double fdJunMoney;

    private Double fdThirdQuarterMoney;

    private Double fdJulMoney;

    private Double fdAugMoney;

    private Double fdSeptMoney;

    private Double fdFourthQuarterMoney;

    private Double fdOctMoney;

    private Double fdNovMoney;

    private Double fdDecMoney;
    
    private Double fdMoney;

    private String fdYearRule;

    private String fdQuarterRule;

    private String fdMonthRule;
    
    private String fdRule;
    
    private String fdYearApply;
    
    private String fdQuarterApply;
    
    private String fdMonthApply;
    
    private String fdApply;


    private Double fdElasticPercent;
    
    private String fdIsEnable;

    private EopBasedataCostCenter fdCostCenterGroup;

    private EopBasedataCostCenter fdCostCenter;

    private EopBasedataBudgetItem fdBudgetItem;

    private EopBasedataProject fdProject;

    private EopBasedataInnerOrder fdInnerOrder;

    private SysOrgPerson fdPerson;

    private EopBasedataWbs fdWbs;
    
    private SysOrgElement fdDept;
    
    private FsscBudgetMain docMain;
    
    @Override
    public Class<FsscBudgetDetailForm> getFormClass() {
        return FsscBudgetDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCostCenterGroup.fdName", "fdCostCenterGroupName");
            toFormPropertyMap.put("fdCostCenterGroup.fdId", "fdCostCenterGroupId");
            toFormPropertyMap.put("fdCostCenter.fdName", "fdCostCenterName");
            toFormPropertyMap.put("fdCostCenter.fdId", "fdCostCenterId");
            toFormPropertyMap.put("fdBudgetItem.fdName", "fdBudgetItemName");
            toFormPropertyMap.put("fdBudgetItem.fdId", "fdBudgetItemId");
            toFormPropertyMap.put("fdProject.fdName", "fdProjectName");
            toFormPropertyMap.put("fdProject.fdId", "fdProjectId");
            toFormPropertyMap.put("fdInnerOrder.fdName", "fdInnerOrderName");
            toFormPropertyMap.put("fdInnerOrder.fdId", "fdInnerOrderId");
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("fdWbs.fdName", "fdWbsName");
            toFormPropertyMap.put("fdWbs.fdId", "fdWbsId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    /**
     * 年
     */
    public Double getFdYearMoney() {
        return this.fdYearMoney;
    }

    /**
     * 年
     */
    public void setFdYearMoney(Double fdYearMoney) {
        this.fdYearMoney = fdYearMoney;
    }

    /**
     * 第一季度
     */
    public Double getFdFirstQuarterMoney() {
        return this.fdFirstQuarterMoney;
    }

    /**
     * 第一季度
     */
    public void setFdFirstQuarterMoney(Double fdFirstQuarterMoney) {
        this.fdFirstQuarterMoney = fdFirstQuarterMoney;
    }

    /**
     * 1月
     */
    public Double getFdJanMoney() {
        return this.fdJanMoney;
    }

    /**
     * 1月
     */
    public void setFdJanMoney(Double fdJanMoney) {
        this.fdJanMoney = fdJanMoney;
    }

    /**
     * 2月
     */
    public Double getFdFebMoney() {
        return this.fdFebMoney;
    }

    /**
     * 2月
     */
    public void setFdFebMoney(Double fdFebMoney) {
        this.fdFebMoney = fdFebMoney;
    }

    /**
     * 3月
     */
    public Double getFdMarMoney() {
        return this.fdMarMoney;
    }

    /**
     * 3月
     */
    public void setFdMarMoney(Double fdMarMoney) {
        this.fdMarMoney = fdMarMoney;
    }

    /**
     * 第二季度
     */
    public Double getFdSecondQuarterMoney() {
        return this.fdSecondQuarterMoney;
    }

    /**
     * 第二季度
     */
    public void setFdSecondQuarterMoney(Double fdSecondQuarterMoney) {
        this.fdSecondQuarterMoney = fdSecondQuarterMoney;
    }

    /**
     * 4月
     */
    public Double getFdAprMoney() {
        return this.fdAprMoney;
    }

    /**
     * 4月
     */
    public void setFdAprMoney(Double fdAprMoney) {
        this.fdAprMoney = fdAprMoney;
    }

    /**
     * 5月
     */
    public Double getFdMayMoney() {
        return this.fdMayMoney;
    }

    /**
     * 5月
     */
    public void setFdMayMoney(Double fdMayMoney) {
        this.fdMayMoney = fdMayMoney;
    }

    /**
     * 6月
     */
    public Double getFdJunMoney() {
        return this.fdJunMoney;
    }

    /**
     * 6月
     */
    public void setFdJunMoney(Double fdJunMoney) {
        this.fdJunMoney = fdJunMoney;
    }

    /**
     * 第三季度
     */
    public Double getFdThirdQuarterMoney() {
        return this.fdThirdQuarterMoney;
    }

    /**
     * 第三季度
     */
    public void setFdThirdQuarterMoney(Double fdThirdQuarterMoney) {
        this.fdThirdQuarterMoney = fdThirdQuarterMoney;
    }

    /**
     * 7月
     */
    public Double getFdJulMoney() {
        return this.fdJulMoney;
    }

    /**
     * 7月
     */
    public void setFdJulMoney(Double fdJulMoney) {
        this.fdJulMoney = fdJulMoney;
    }

    /**
     * 8月
     */
    public Double getFdAugMoney() {
        return this.fdAugMoney;
    }

    /**
     * 8月
     */
    public void setFdAugMoney(Double fdAugMoney) {
        this.fdAugMoney = fdAugMoney;
    }

    /**
     * 9月
     */
    public Double getFdSeptMoney() {
        return this.fdSeptMoney;
    }

    /**
     * 9月
     */
    public void setFdSeptMoney(Double fdSeptMoney) {
        this.fdSeptMoney = fdSeptMoney;
    }

    /**
     * 第四季度
     */
    public Double getFdFourthQuarterMoney() {
        return this.fdFourthQuarterMoney;
    }

    /**
     * 第四季度
     */
    public void setFdFourthQuarterMoney(Double fdFourthQuarterMoney) {
        this.fdFourthQuarterMoney = fdFourthQuarterMoney;
    }

    /**
     * 10月
     */
    public Double getFdOctMoney() {
        return this.fdOctMoney;
    }

    /**
     * 10月
     */
    public void setFdOctMoney(Double fdOctMoney) {
        this.fdOctMoney = fdOctMoney;
    }

    /**
     * 11月
     */
    public Double getFdNovMoney() {
        return this.fdNovMoney;
    }

    /**
     * 11月
     */
    public void setFdNovMoney(Double fdNovMoney) {
        this.fdNovMoney = fdNovMoney;
    }

    /**
     * 12月
     */
    public Double getFdDecMoney() {
        return this.fdDecMoney;
    }

    /**
     * 12月
     */
    public void setFdDecMoney(Double fdDecMoney) {
        this.fdDecMoney = fdDecMoney;
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
     * 主表
     */
	public FsscBudgetMain getDocMain() {
		return docMain;
	}
	/**
     * 主表
     */
	public void setDocMain(FsscBudgetMain docMain) {
		this.docMain = docMain;
	}
    
}
