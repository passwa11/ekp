package com.landray.kmss.fssc.fee.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 事前台账
  */
public class FsscFeeLedgerForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdIsUseBudget;
    
    private String fdDetailId;
    
    private String fdLedgerId;
    
    private String fdModelId ;
    
    private String fdModelName;

    private String fdCompanyId;

    private String fdCompanyCode;
    
    private String fdCompanyName;
    
    private String fdCompanyGroupId;
    
    private String fdCompanyGroupName;
    
    private String fdCompanyGroupCode;

    private String fdCostCenterId;

    private String fdCostCenterCode;
    
    private String fdCostCenterName;

    private String fdExpenseItemId;

    private String fdExpenseItemCode;
    
    private String fdExpenseItemName;

    private String fdCostCenterGroupId;

    private String fdCostCenterGroupCode;
    
    private String fdCostCenterGroupName;

    private String fdProjectId;

    private String fdProjectCode;
    
    private String fdProjectName;

    private String fdInnerOrderId;

    private String fdInnerOrderCode;
    
    private String fdInnerOrderName;

    private String fdWbsId;

    private String fdWbsCode;
    
    private String fdWbsName;

    private String fdPersonId;

    private String fdPersonCode;
    
    private String fdPersonName;

    private String fdApplyMoney;
    
    private String fdStandardMoney;
    
    private String fdBudgetMoney;

    private String fdDeptId;

    private String fdDeptCode;
    
    private String fdDeptName;
    
    private String docCreateTime;
    
    private String fdType;
    
    private String fdForbid;
    
    private String fdBudgetItemId;
    
    private String fdStartDate;
    
    private String fdEndDate;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdIsUseBudget = null;
    	fdStartDate = null;
    	fdEndDate = null;
    	fdDetailId = null;
    	fdLedgerId = null;
    	fdType = null;
    	fdForbid = null;
    	fdModelId = null;
    	fdModelName = null;
        fdCompanyId = null;
        fdCompanyCode = null;
        fdCompanyName = null;
        fdCompanyGroupId = null;
        fdCompanyGroupCode = null;
        fdCompanyGroupName = null;
        fdCostCenterId = null;
        fdCostCenterCode = null;
        fdCostCenterName = null;
        fdExpenseItemId = null;
        fdExpenseItemCode = null;
        fdExpenseItemName = null;
        fdCostCenterGroupId = null;
        fdCostCenterGroupCode = null;
        fdCostCenterGroupName = null;
        fdProjectId = null;
        fdProjectCode = null;
        fdProjectName = null;
        fdInnerOrderId = null;
        fdInnerOrderCode = null;
        fdInnerOrderName = null;
        fdWbsId = null;
        fdWbsCode = null;
        fdWbsName = null;
        fdPersonId = null;
        fdPersonCode = null;
        fdPersonName = null;
        fdStandardMoney = null;
        fdBudgetMoney = null;
        fdApplyMoney = null;
        fdDeptId = null;
        fdDeptCode = null;
        fdDeptName = null;
        docCreateTime = null;
        fdBudgetItemId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscFeeLedger> getModelClass() {
        return FsscFeeLedger.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }

    /**
     * 记账公司ID
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 记账公司ID
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
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
     * 成本中心ID
     */
    public String getFdCostCenterId() {
        return this.fdCostCenterId;
    }

    /**
     * 成本中心ID
     */
    public void setFdCostCenterId(String fdCostCenterId) {
        this.fdCostCenterId = fdCostCenterId;
    }

    /**
     * 成本中心编号
     */
    public String getFdCostCenterCode() {
        return this.fdCostCenterCode;
    }

    /**
     * 成本中心编号
     */
    public void setFdCostCenterCode(String fdCostCenterCode) {
        this.fdCostCenterCode = fdCostCenterCode;
    }

    /**
     * 费用类型ID
     */
    public String getFdExpenseItemId() {
        return this.fdExpenseItemId;
    }

    /**
     * 费用类型ID
     */
    public void setFdExpenseItemId(String fdExpenseItemId) {
        this.fdExpenseItemId = fdExpenseItemId;
    }

    /**
     * 费用类型编码
     */
    public String getFdExpenseItemCode() {
        return this.fdExpenseItemCode;
    }

    /**
     * 费用类型编码
     */
    public void setFdExpenseItemCode(String fdExpenseItemCode) {
        this.fdExpenseItemCode = fdExpenseItemCode;
    }

    /**
     * 项目ID
     */
    public String getFdProjectId() {
        return this.fdProjectId;
    }

    /**
     * 项目ID
     */
    public void setFdProjectId(String fdProjectId) {
        this.fdProjectId = fdProjectId;
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
     * 内部订单ID
     */
    public String getFdInnerOrderId() {
        return this.fdInnerOrderId;
    }

    /**
     * 内部订单ID
     */
    public void setFdInnerOrderId(String fdInnerOrderId) {
        this.fdInnerOrderId = fdInnerOrderId;
    }

    /**
     * 内部订单编码
     */
    public String getFdInnerOrderCode() {
        return this.fdInnerOrderCode;
    }

    /**
     * 内部订单编码
     */
    public void setFdInnerOrderCode(String fdInnerOrderCode) {
        this.fdInnerOrderCode = fdInnerOrderCode;
    }

    /**
     * WBSID
     */
    public String getFdWbsId() {
        return this.fdWbsId;
    }

    /**
     * WBSID
     */
    public void setFdWbsId(String fdWbsId) {
        this.fdWbsId = fdWbsId;
    }

    /**
     * WBS编码
     */
    public String getFdWbsCode() {
        return this.fdWbsCode;
    }

    /**
     * WBS编码
     */
    public void setFdWbsCode(String fdWbsCode) {
        this.fdWbsCode = fdWbsCode;
    }

    /**
     * 人员ID
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 人员ID
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 人员编码
     */
    public String getFdPersonCode() {
        return this.fdPersonCode;
    }

    /**
     * 人员编码
     */
    public void setFdPersonCode(String fdPersonCode) {
        this.fdPersonCode = fdPersonCode;
    }


    /**
     * 部门ID
     */
    public String getFdDeptId() {
        return this.fdDeptId;
    }

    /**
     * 部门ID
     */
    public void setFdDeptId(String fdDeptId) {
        this.fdDeptId = fdDeptId;
    }

    /**
     * 部门编码
     */
    public String getFdDeptCode() {
        return this.fdDeptCode;
    }

    /**
     * 部门编码
     */
    public void setFdDeptCode(String fdDeptCode) {
        this.fdDeptCode = fdDeptCode;
    }

	public String getFdCompanyName() {
		return fdCompanyName;
	}

	public void setFdCompanyName(String fdCompanyName) {
		this.fdCompanyName = fdCompanyName;
	}

	public String getFdCostCenterName() {
		return fdCostCenterName;
	}

	public void setFdCostCenterName(String fdCostCenterName) {
		this.fdCostCenterName = fdCostCenterName;
	}

	public String getFdExpenseItemName() {
		return fdExpenseItemName;
	}

	public void setFdExpenseItemName(String fdExpenseItemName) {
		this.fdExpenseItemName = fdExpenseItemName;
	}


	public String getFdProjectName() {
		return fdProjectName;
	}

	public void setFdProjectName(String fdProjectName) {
		this.fdProjectName = fdProjectName;
	}

	public String getFdInnerOrderName() {
		return fdInnerOrderName;
	}

	public void setFdInnerOrderName(String fdInnerOrderName) {
		this.fdInnerOrderName = fdInnerOrderName;
	}

	public String getFdWbsName() {
		return fdWbsName;
	}

	public void setFdWbsName(String fdWbsName) {
		this.fdWbsName = fdWbsName;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public String getFdDeptName() {
		return fdDeptName;
	}

	public void setFdDeptName(String fdDeptName) {
		this.fdDeptName = fdDeptName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdCompanyGroupId() {
		return fdCompanyGroupId;
	}

	public void setFdCompanyGroupId(String fdCompanyGroupId) {
		this.fdCompanyGroupId = fdCompanyGroupId;
	}

	public String getFdCompanyGroupName() {
		return fdCompanyGroupName;
	}

	public void setFdCompanyGroupName(String fdCompanyGroupName) {
		this.fdCompanyGroupName = fdCompanyGroupName;
	}

	public String getFdCompanyGroupCode() {
		return fdCompanyGroupCode;
	}

	public void setFdCompanyGroupCode(String fdCompanyGroupCode) {
		this.fdCompanyGroupCode = fdCompanyGroupCode;
	}

	public String getFdCostCenterGroupId() {
		return fdCostCenterGroupId;
	}

	public void setFdCostCenterGroupId(String fdCostCenterGroupId) {
		this.fdCostCenterGroupId = fdCostCenterGroupId;
	}

	public String getFdCostCenterGroupCode() {
		return fdCostCenterGroupCode;
	}

	public void setFdCostCenterGroupCode(String fdCostCenterGroupCode) {
		this.fdCostCenterGroupCode = fdCostCenterGroupCode;
	}

	public String getFdCostCenterGroupName() {
		return fdCostCenterGroupName;
	}

	public void setFdCostCenterGroupName(String fdCostCenterGroupName) {
		this.fdCostCenterGroupName = fdCostCenterGroupName;
	}

	public String getFdApplyMoney() {
		return fdApplyMoney;
	}

	public void setFdApplyMoney(String fdApplyMoney) {
		this.fdApplyMoney = fdApplyMoney;
	}

	public String getFdStandardMoney() {
		return fdStandardMoney;
	}

	public void setFdStandardMoney(String fdStandardMoney) {
		this.fdStandardMoney = fdStandardMoney;
	}

	public String getFdBudgetMoney() {
		return fdBudgetMoney;
	}

	public void setFdBudgetMoney(String fdBudgetMoney) {
		this.fdBudgetMoney = fdBudgetMoney;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdForbid() {
		return fdForbid;
	}

	public void setFdForbid(String fdForbid) {
		this.fdForbid = fdForbid;
	}

	public String getFdBudgetItemId() {
		return fdBudgetItemId;
	}

	public void setFdBudgetItemId(String fdBudgetItemId) {
		this.fdBudgetItemId = fdBudgetItemId;
	}

	public String getFdLedgerId() {
		return fdLedgerId;
	}

	public void setFdLedgerId(String fdLedgerId) {
		this.fdLedgerId = fdLedgerId;
	}

	public String getFdDetailId() {
		return fdDetailId;
	}

	public void setFdDetailId(String fdDetailId) {
		this.fdDetailId = fdDetailId;
	}

	public String getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	public String getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public String getFdIsUseBudget() {
		return fdIsUseBudget;
	}

	public void setFdIsUseBudget(String fdIsUseBudget) {
		this.fdIsUseBudget = fdIsUseBudget;
	}
}
