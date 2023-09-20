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
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustDetail;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算调整明细
  */
public class FsscBudgetAdjustDetailForm extends ExtendForm {

	private static final long serialVersionUID = -6952466990098373404L;

	private static FormToModelPropertyMap toModelPropertyMap;

    private String fdMoney;

    private String fdBorrowCanUseMoney;

    private String fdLendCanUseMoney;
    
    private String fdBudgetInfo;

    private String fdBorrowCostCenterGroupId;

    private String fdBorrowCostCenterGroupName;

    private String fdBorrowCostCenterId;

    private String fdBorrowCostCenterName;

    private String fdBorrowBudgetItemId;

    private String fdBorrowBudgetItemName;
    
    private String fdBorrowParentName;

    private String fdBorrowProjectId;

    private String fdBorrowProjectName;

    private String fdBorrowInnerOrderId;

    private String fdBorrowInnerOrderName;

    private String fdBorrowWbsId;

    private String fdBorrowWbsName;

    private String fdBorrowPersonId;

    private String fdBorrowPersonName;
    
    private String fdBorrowDeptId;
    
    private String fdBorrowDeptName;
    
    private String fdBorrowPeriod;

    private String fdLendCostCenterGroupId;

    private String fdLendCostCenterGroupName;

    private String fdLendCostCenterId;

    private String fdLendCostCenterName;

    private String fdLendBudgetItemId;

    private String fdLendBudgetItemName;
    
    private String fdLendParentName;

    private String fdLendProjectId;

    private String fdLendProjectName;

    private String fdLendInnerOrderId;

    private String fdLendInnerOrderName;

    private String fdLendWbsId;

    private String fdLendWbsName;

    private String fdLendPersonId;

    private String fdLendPersonName;
    
    private String fdLendDeptId;
    
    private String fdLendDeptName;
    
    private String fdLendPeriod;
    
    private String docMainId;
    
    private String docMainName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdMoney = null;
        fdBorrowCanUseMoney = null;
        fdLendCanUseMoney = null;
        fdBudgetInfo=null;
        fdBorrowCostCenterGroupId = null;
        fdBorrowCostCenterGroupName = null;
        fdBorrowCostCenterId = null;
        fdBorrowCostCenterName = null;
        fdBorrowBudgetItemId = null;
        fdBorrowBudgetItemName = null;
        fdBorrowParentName=null;
        fdBorrowProjectId = null;
        fdBorrowProjectName = null;
        fdBorrowInnerOrderId = null;
        fdBorrowInnerOrderName = null;
        fdBorrowWbsId = null;
        fdBorrowWbsName = null;
        fdBorrowPersonId = null;
        fdBorrowPersonName = null;
        fdBorrowDeptId = null;
        fdBorrowDeptName = null;
        fdBorrowPeriod=null;
        fdLendCostCenterGroupId = null;
        fdLendCostCenterGroupName = null;
        fdLendCostCenterId = null;
        fdLendCostCenterName = null;
        fdLendBudgetItemId = null;
        fdLendBudgetItemName = null;
        fdLendParentName=null;
        fdLendProjectId = null;
        fdLendProjectName = null;
        fdLendInnerOrderId = null;
        fdLendInnerOrderName = null;
        fdLendWbsId = null;
        fdLendWbsName = null;
        fdLendPersonId = null;
        fdLendPersonName = null;
        fdLendDeptId = null;
        fdLendDeptName = null;
        fdLendPeriod=null;
        docMainId=null;
        docMainName=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetAdjustDetail> getModelClass() {
        return FsscBudgetAdjustDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdBorrowCostCenterGroupId", new FormConvertor_IDToModel("fdBorrowCostCenterGroup", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdBorrowCostCenterId", new FormConvertor_IDToModel("fdBorrowCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdBorrowBudgetItemId", new FormConvertor_IDToModel("fdBorrowBudgetItem", EopBasedataBudgetItem.class));
            toModelPropertyMap.put("fdBorrowProjectId", new FormConvertor_IDToModel("fdBorrowProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdBorrowInnerOrderId", new FormConvertor_IDToModel("fdBorrowInnerOrder", EopBasedataInnerOrder.class));
            toModelPropertyMap.put("fdBorrowWbsId", new FormConvertor_IDToModel("fdBorrowWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdBorrowPersonId", new FormConvertor_IDToModel("fdBorrowPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdBorrowDeptId", new FormConvertor_IDToModel("fdBorrowDept", SysOrgElement.class));
            toModelPropertyMap.put("fdLendCostCenterGroupId", new FormConvertor_IDToModel("fdLendCostCenterGroup", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdLendCostCenterId", new FormConvertor_IDToModel("fdLendCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdLendBudgetItemId", new FormConvertor_IDToModel("fdLendBudgetItem", EopBasedataBudgetItem.class));
            toModelPropertyMap.put("fdLendProjectId", new FormConvertor_IDToModel("fdLendProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdLendInnerOrderId", new FormConvertor_IDToModel("fdLendInnerOrder", EopBasedataInnerOrder.class));
            toModelPropertyMap.put("fdLendWbsId", new FormConvertor_IDToModel("fdLendWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdLendPersonId", new FormConvertor_IDToModel("fdLendPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdLendDeptId", new FormConvertor_IDToModel("fdLendDept", SysOrgElement.class));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscBudgetAdjustMain.class));
        }
        return toModelPropertyMap;
    }
    /**
     * 借入可用预算
     */
    public String getFdBorrowCanUseMoney() {
        return fdBorrowCanUseMoney;
    }
    /**
     * 借入可用预算
     */
    public void setFdBorrowCanUseMoney(String fdBorrowCanUseMoney) {
        this.fdBorrowCanUseMoney = fdBorrowCanUseMoney;
    }
    /**
     * 借出可用预算
     */
    public String getFdLendCanUseMoney() {
        return fdLendCanUseMoney;
    }

    /**
     * 借出可用预算
     */
    public void setFdLendCanUseMoney(String fdLendCanUseMoney) {
        this.fdLendCanUseMoney = fdLendCanUseMoney;
    }
    /**
     * 调整金额
     */
    public String getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 调整金额
     */
    public void setFdMoney(String fdMoney) {
        this.fdMoney = fdMoney;
    }
    
    /**
     * 借出预算信息
     */
    public String getFdBudgetInfo() {
		return fdBudgetInfo;
	}
    /**
     * 借出预算信息
     */
	public void setFdBudgetInfo(String fdBudgetInfo) {
		this.fdBudgetInfo = fdBudgetInfo;
	}

    /**
     * 借入成本中心组
     */
    public String getFdBorrowCostCenterGroupId() {
        return this.fdBorrowCostCenterGroupId;
    }

    /**
     * 借入成本中心组
     */
    public void setFdBorrowCostCenterGroupId(String fdBorrowCostCenterGroupId) {
        this.fdBorrowCostCenterGroupId = fdBorrowCostCenterGroupId;
    }

    /**
     * 借入成本中心组
     */
    public String getFdBorrowCostCenterGroupName() {
        return this.fdBorrowCostCenterGroupName;
    }

    /**
     * 借入成本中心组
     */
    public void setFdBorrowCostCenterGroupName(String fdBorrowCostCenterGroupName) {
        this.fdBorrowCostCenterGroupName = fdBorrowCostCenterGroupName;
    }

    /**
     * 借入成本中心
     */
    public String getFdBorrowCostCenterId() {
        return this.fdBorrowCostCenterId;
    }

    /**
     * 借入成本中心
     */
    public void setFdBorrowCostCenterId(String fdBorrowCostCenterId) {
        this.fdBorrowCostCenterId = fdBorrowCostCenterId;
    }

    /**
     * 借入成本中心
     */
    public String getFdBorrowCostCenterName() {
        return this.fdBorrowCostCenterName;
    }

    /**
     * 借入成本中心
     */
    public void setFdBorrowCostCenterName(String fdBorrowCostCenterName) {
        this.fdBorrowCostCenterName = fdBorrowCostCenterName;
    }

    /**
     * 借入预算科目
     */
    public String getFdBorrowBudgetItemId() {
        return this.fdBorrowBudgetItemId;
    }

    /**
     * 借入预算科目
     */
    public void setFdBorrowBudgetItemId(String fdBorrowBudgetItemId) {
        this.fdBorrowBudgetItemId = fdBorrowBudgetItemId;
    }

    /**
     * 借入预算科目
     */
    public String getFdBorrowBudgetItemName() {
        return this.fdBorrowBudgetItemName;
    }

    /**
     * 借入预算科目
     */
    public void setFdBorrowBudgetItemName(String fdBorrowBudgetItemName) {
        this.fdBorrowBudgetItemName = fdBorrowBudgetItemName;
    }

    /**
     * 借入项目
     */
    public String getFdBorrowProjectId() {
        return this.fdBorrowProjectId;
    }

    /**
     * 借入项目
     */
    public void setFdBorrowProjectId(String fdBorrowProjectId) {
        this.fdBorrowProjectId = fdBorrowProjectId;
    }

    /**
     * 借入项目
     */
    public String getFdBorrowProjectName() {
        return this.fdBorrowProjectName;
    }

    /**
     * 借入项目
     */
    public void setFdBorrowProjectName(String fdBorrowProjectName) {
        this.fdBorrowProjectName = fdBorrowProjectName;
    }

    /**
     * 借入内部订单
     */
    public String getFdBorrowInnerOrderId() {
        return this.fdBorrowInnerOrderId;
    }

    /**
     * 借入内部订单
     */
    public void setFdBorrowInnerOrderId(String fdBorrowInnerOrderId) {
        this.fdBorrowInnerOrderId = fdBorrowInnerOrderId;
    }

    /**
     * 借入内部订单
     */
    public String getFdBorrowInnerOrderName() {
        return this.fdBorrowInnerOrderName;
    }

    /**
     * 借入内部订单
     */
    public void setFdBorrowInnerOrderName(String fdBorrowInnerOrderName) {
        this.fdBorrowInnerOrderName = fdBorrowInnerOrderName;
    }

    /**
     * 借入WBS
     */
    public String getFdBorrowWbsId() {
        return this.fdBorrowWbsId;
    }

    /**
     * 借入WBS
     */
    public void setFdBorrowWbsId(String fdBorrowWbsId) {
        this.fdBorrowWbsId = fdBorrowWbsId;
    }

    /**
     * 借入WBS
     */
    public String getFdBorrowWbsName() {
        return this.fdBorrowWbsName;
    }

    /**
     * 借入WBS
     */
    public void setFdBorrowWbsName(String fdBorrowWbsName) {
        this.fdBorrowWbsName = fdBorrowWbsName;
    }

    /**
     * 借入员工
     */
    public String getFdBorrowPersonId() {
        return this.fdBorrowPersonId;
    }

    /**
     * 借入员工
     */
    public void setFdBorrowPersonId(String fdBorrowPersonId) {
        this.fdBorrowPersonId = fdBorrowPersonId;
    }

	/**
     * 借入员工
     */
    public void setFdBorrowPersonName(String fdBorrowPersonName) {
    	this.fdBorrowPersonName = fdBorrowPersonName;
    }
    

    /**
     * 借入员工
     */
    public String getFdBorrowPersonName() {
        return this.fdBorrowPersonName;
    }

    /**
     * 借入部门
     */
    
    public String getFdBorrowDeptId() {
		return fdBorrowDeptId;
	}
    
    /**
     * 借入部门
     */

	public void setFdBorrowDeptId(String fdBorrowDeptId) {
		this.fdBorrowDeptId = fdBorrowDeptId;
	}
	 /**
     * 借入部门
     */
	public String getFdBorrowDeptName() {
		return fdBorrowDeptName;
	}
	 /**
     * 借入部门
     */
	public void setFdBorrowDeptName(String fdBorrowDeptName) {
		this.fdBorrowDeptName = fdBorrowDeptName;
	}
	
	/**
     * 借入期间
     */
    public String getFdBorrowPeriod() {
		return fdBorrowPeriod;
	}
    /**
     * 借入期间
     */
	public void setFdBorrowPeriod(String fdBorrowPeriod) {
		this.fdBorrowPeriod = fdBorrowPeriod;
	}

	/**
     * 借出成本中心组
     */
    public String getFdLendCostCenterGroupId() {
        return this.fdLendCostCenterGroupId;
    }

    /**
     * 借出成本中心组
     */
    public void setFdLendCostCenterGroupId(String fdLendCostCenterGroupId) {
        this.fdLendCostCenterGroupId = fdLendCostCenterGroupId;
    }

    /**
     * 借出成本中心组
     */
    public String getFdLendCostCenterGroupName() {
        return this.fdLendCostCenterGroupName;
    }

    /**
     * 借出成本中心组
     */
    public void setFdLendCostCenterGroupName(String fdLendCostCenterGroupName) {
        this.fdLendCostCenterGroupName = fdLendCostCenterGroupName;
    }

    /**
     * 借出成本中心
     */
    public String getFdLendCostCenterId() {
        return this.fdLendCostCenterId;
    }

    /**
     * 借出成本中心
     */
    public void setFdLendCostCenterId(String fdLendCostCenterId) {
        this.fdLendCostCenterId = fdLendCostCenterId;
    }

    /**
     * 借出成本中心
     */
    public String getFdLendCostCenterName() {
        return this.fdLendCostCenterName;
    }

    /**
     * 借出成本中心
     */
    public void setFdLendCostCenterName(String fdLendCostCenterName) {
        this.fdLendCostCenterName = fdLendCostCenterName;
    }

    /**
     * 借出预算科目
     */
    public String getFdLendBudgetItemId() {
        return this.fdLendBudgetItemId;
    }

    /**
     * 借出预算科目
     */
    public void setFdLendBudgetItemId(String fdLendBudgetItemId) {
        this.fdLendBudgetItemId = fdLendBudgetItemId;
    }

    /**
     * 借出预算科目
     */
    public String getFdLendBudgetItemName() {
        return this.fdLendBudgetItemName;
    }

    /**
     * 借出预算科目
     */
    public void setFdLendBudgetItemName(String fdLendBudgetItemName) {
        this.fdLendBudgetItemName = fdLendBudgetItemName;
    }

    /**
     * 借出项目
     */
    public String getFdLendProjectId() {
        return this.fdLendProjectId;
    }

    /**
     * 借出项目
     */
    public void setFdLendProjectId(String fdLendProjectId) {
        this.fdLendProjectId = fdLendProjectId;
    }

    /**
     * 借出项目
     */
    public String getFdLendProjectName() {
        return this.fdLendProjectName;
    }

    /**
     * 借出项目
     */
    public void setFdLendProjectName(String fdLendProjectName) {
        this.fdLendProjectName = fdLendProjectName;
    }

    /**
     * 借出内部订单
     */
    public String getFdLendInnerOrderId() {
        return this.fdLendInnerOrderId;
    }

    /**
     * 借出内部订单
     */
    public void setFdLendInnerOrderId(String fdLendInnerOrderId) {
        this.fdLendInnerOrderId = fdLendInnerOrderId;
    }

    /**
     * 借出内部订单
     */
    public String getFdLendInnerOrderName() {
        return this.fdLendInnerOrderName;
    }

    /**
     * 借出内部订单
     */
    public void setFdLendInnerOrderName(String fdLendInnerOrderName) {
        this.fdLendInnerOrderName = fdLendInnerOrderName;
    }

    /**
     * 借出WBS
     */
    public String getFdLendWbsId() {
        return this.fdLendWbsId;
    }

    /**
     * 借出WBS
     */
    public void setFdLendWbsId(String fdLendWbsId) {
        this.fdLendWbsId = fdLendWbsId;
    }

    /**
     * 借出WBS
     */
    public String getFdLendWbsName() {
        return this.fdLendWbsName;
    }

    /**
     * 借出WBS
     */
    public void setFdLendWbsName(String fdLendWbsName) {
        this.fdLendWbsName = fdLendWbsName;
    }

    /**
     * 借出员工
     */
    public String getFdLendPersonId() {
        return this.fdLendPersonId;
    }

    /**
     * 借出员工
     */
    public void setFdLendPersonId(String fdLendPersonId) {
        this.fdLendPersonId = fdLendPersonId;
    }

    /**
     * 借出员工
     */
    public String getFdLendPersonName() {
        return this.fdLendPersonName;
    }

    /**
     * 借出员工
     */
    public void setFdLendPersonName(String fdLendPersonName) {
        this.fdLendPersonName = fdLendPersonName;
    }
    /**
     * 借出部门
     */
	public String getFdLendDeptId() {
		return fdLendDeptId;
	}
	/**
     * 借出部门
     */
	public void setFdLendDeptId(String fdLendDeptId) {
		this.fdLendDeptId = fdLendDeptId;
	}
	/**
     * 借出部门
     */
	public String getFdLendDeptName() {
		return fdLendDeptName;
	}
	/**
     * 借出部门
     */
	public void setFdLendDeptName(String fdLendDeptName) {
		this.fdLendDeptName = fdLendDeptName;
	}
	/**
     * 借出期间
     */
	public String getFdLendPeriod() {
		return fdLendPeriod;
	}
	/**
     * 借出期间
     */
	public void setFdLendPeriod(String fdLendPeriod) {
		this.fdLendPeriod = fdLendPeriod;
	}

	/**
     * 调整主表
     */
	public String getDocMainId() {
		return docMainId;
	}
	
	/**
     * 调整主表
     */
	public void setDocMainId(String docMainId) {
		this.docMainId = docMainId;
	}
	
	/**
     * 调整主表
     */
	public String getDocMainName() {
		return docMainName;
	}
	
	/**
     * 调整主表
     */
	public void setDocMainName(String docMainName) {
		this.docMainName = docMainName;
	}

	public String getFdBorrowParentName() {
		return fdBorrowParentName;
	}

	public void setFdBorrowParentName(String fdBorrowParentName) {
		this.fdBorrowParentName = fdBorrowParentName;
	}

	public String getFdLendParentName() {
		return fdLendParentName;
	}

	public void setFdLendParentName(String fdLendParentName) {
		this.fdLendParentName = fdLendParentName;
	}
    
    
}
