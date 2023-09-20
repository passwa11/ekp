package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.expense.forms.FsscExpenseBalanceDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 调账明细
  */
public class FsscExpenseBalanceDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdType;

    private EopBasedataExpenseItem fdExpenseItem;

    private EopBasedataAccounts fdAccount;

    private EopBasedataCostCenter fdCostCenter;

    private SysOrgPerson fdPerson;
    
    private SysOrgElement fdDept;

    private EopBasedataCashFlow fdCashFlow;

    private EopBasedataProject fdProject;

    private Double fdMoney;

    private String fdRemark;
    
    private String fdBudgetInfo;
    
    private Double fdBudgetMoney;
    
    @Override
    public Class<FsscExpenseBalanceDetailForm> getFormClass() {
        return FsscExpenseBalanceDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdExpenseItem.fdName", "fdExpenseItemName");
            toFormPropertyMap.put("fdExpenseItem.fdId", "fdExpenseItemId");
            toFormPropertyMap.put("fdAccount.fdName", "fdAccountName");
            toFormPropertyMap.put("fdAccount.fdId", "fdAccountId");
            toFormPropertyMap.put("fdCostCenter.fdName", "fdCostCenterName");
            toFormPropertyMap.put("fdCostCenter.fdId", "fdCostCenterId");
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("fdCashFlow.fdName", "fdCashFlowName");
            toFormPropertyMap.put("fdCashFlow.fdId", "fdCashFlowId");
            toFormPropertyMap.put("fdProject.fdName", "fdProjectName");
            toFormPropertyMap.put("fdProject.fdId", "fdProjectId");
        }
        return toFormPropertyMap;
    }

    /**
     * 借/贷
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 借/贷
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 费用类型
     */
    public EopBasedataExpenseItem getFdExpenseItem() {
        return this.fdExpenseItem;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseItem(EopBasedataExpenseItem fdExpenseItem) {
        this.fdExpenseItem = fdExpenseItem;
    }

    /**
     * 会计科目
     */
    public EopBasedataAccounts getFdAccount() {
        return this.fdAccount;
    }

    /**
     * 会计科目
     */
    public void setFdAccount(EopBasedataAccounts fdAccount) {
        this.fdAccount = fdAccount;
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
     * 个人
     */
    public SysOrgPerson getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 个人
     */
    public void setFdPerson(SysOrgPerson fdPerson) {
        this.fdPerson = fdPerson;
    }

    /**
     * 现金流量项目
     */
    public EopBasedataCashFlow getFdCashFlow() {
        return this.fdCashFlow;
    }

    /**
     * 现金流量项目
     */
    public void setFdCashFlow(EopBasedataCashFlow fdCashFlow) {
        this.fdCashFlow = fdCashFlow;
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
     * 备注
     */
    public String getFdRemark() {
        return this.fdRemark;
    }

    /**
     * 备注
     */
    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

	public String getFdBudgetInfo() {
		return fdBudgetInfo;
	}

	public void setFdBudgetInfo(String fdBudgetInfo) {
		this.fdBudgetInfo = fdBudgetInfo;
	}

	public SysOrgElement getFdDept() {
		return fdDept;
	}

	public void setFdDept(SysOrgElement fdDept) {
		this.fdDept = fdDept;
	}

	public Double getFdBudgetMoney() {
		return fdBudgetMoney;
	}

	public void setFdBudgetMoney(Double fdBudgetMoney) {
		this.fdBudgetMoney = fdBudgetMoney;
	}
}
