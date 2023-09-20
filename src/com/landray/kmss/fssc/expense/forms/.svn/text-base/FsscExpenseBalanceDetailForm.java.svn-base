package com.landray.kmss.fssc.expense.forms;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 调账明细
  */
public class FsscExpenseBalanceDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdType;

    private String fdExpenseItemId;

    private String fdExpenseItemName;

    private String fdAccountId;

    private String fdAccountName;

    private String fdCostCenterId;

    private String fdCostCenterName;

    private String fdPersonId;

    private String fdPersonName;
    
    private String fdDeptId;

    private String fdDeptName;

    private String fdCashFlowId;

    private String fdCashFlowName;

    private String fdProjectId;

    private String fdProjectName;

    private String fdMoney;

    private String fdRemark;
    
    private String fdBudgetInfo;
    
    private String fdBudgetMoney;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdBudgetMoney = null;
    	fdDeptId = null;
    	fdDeptName = null;
        fdType = null;
        fdExpenseItemId = null;
        fdExpenseItemName = null;
        fdAccountId = null;
        fdAccountName = null;
        fdCostCenterId = null;
        fdCostCenterName = null;
        fdPersonId = null;
        fdPersonName = null;
        fdCashFlowId = null;
        fdCashFlowName = null;
        fdProjectId = null;
        fdProjectName = null;
        fdMoney = null;
        fdRemark = null;
        fdBudgetInfo = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseBalanceDetail> getModelClass() {
        return FsscExpenseBalanceDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdExpenseItemId", new FormConvertor_IDToModel("fdExpenseItem", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdCostCenterId", new FormConvertor_IDToModel("fdCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdDeptId", new FormConvertor_IDToModel("fdDept", SysOrgElement.class));
            toModelPropertyMap.put("fdCashFlowId", new FormConvertor_IDToModel("fdCashFlow", EopBasedataCashFlow.class));
            toModelPropertyMap.put("fdProjectId", new FormConvertor_IDToModel("fdProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdAccountId", new FormConvertor_IDToModel("fdAccount", EopBasedataAccounts.class));
        }
        return toModelPropertyMap;
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
    public String getFdExpenseItemId() {
        return this.fdExpenseItemId;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseItemId(String fdExpenseItemId) {
        this.fdExpenseItemId = fdExpenseItemId;
    }

    /**
     * 费用类型
     */
    public String getFdExpenseItemName() {
        return this.fdExpenseItemName;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseItemName(String fdExpenseItemName) {
        this.fdExpenseItemName = fdExpenseItemName;
    }

    /**
     * 会计科目
     */
    public String getFdAccountId() {
        return this.fdAccountId;
    }

    /**
     * 会计科目
     */
    public void setFdAccountId(String fdAccountId) {
        this.fdAccountId = fdAccountId;
    }

    /**
     * 会计科目
     */
    public String getFdAccountName() {
        return this.fdAccountName;
    }

    /**
     * 会计科目
     */
    public void setFdAccountName(String fdAccountName) {
        this.fdAccountName = fdAccountName;
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
     * 个人
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 个人
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 个人
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 个人
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
    }

    /**
     * 现金流量项目
     */
    public String getFdCashFlowId() {
        return this.fdCashFlowId;
    }

    /**
     * 现金流量项目
     */
    public void setFdCashFlowId(String fdCashFlowId) {
        this.fdCashFlowId = fdCashFlowId;
    }

    /**
     * 现金流量项目
     */
    public String getFdCashFlowName() {
        return this.fdCashFlowName;
    }

    /**
     * 现金流量项目
     */
    public void setFdCashFlowName(String fdCashFlowName) {
        this.fdCashFlowName = fdCashFlowName;
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
     * 金额
     */
    public String getFdMoney() {
    	if(StringUtil.isNull(this.fdMoney)){
    		return this.fdMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdMoney));
    }

    /**
     * 金额
     */
    public void setFdMoney(String fdMoney) {
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

	public String getFdDeptId() {
		return fdDeptId;
	}

	public void setFdDeptId(String fdDeptId) {
		this.fdDeptId = fdDeptId;
	}

	public String getFdDeptName() {
		return fdDeptName;
	}

	public void setFdDeptName(String fdDeptName) {
		this.fdDeptName = fdDeptName;
	}

	public String getFdBudgetMoney() {
		if(StringUtil.isNull(this.fdBudgetMoney)){
    		return this.fdBudgetMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdBudgetMoney));
	}

	public void setFdBudgetMoney(String fdBudgetMoney) {
		this.fdBudgetMoney = fdBudgetMoney;
	}
}
