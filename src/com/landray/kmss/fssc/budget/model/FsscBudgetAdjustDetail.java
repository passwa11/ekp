package com.landray.kmss.fssc.budget.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 预算调整明细
  */
public class FsscBudgetAdjustDetail extends BaseModel {

	private static final long serialVersionUID = 4637241791806988731L;

	private static ModelToFormPropertyMap toFormPropertyMap;

    private Double fdMoney;

    private Double fdBorrowCanUseMoney;

    private Double fdLendCanUseMoney;

    private String fdBudgetInfo;

    private EopBasedataCostCenter fdBorrowCostCenterGroup;

    private EopBasedataCostCenter fdBorrowCostCenter;

    private EopBasedataBudgetItem fdBorrowBudgetItem;

    private EopBasedataProject fdBorrowProject;

    private EopBasedataInnerOrder fdBorrowInnerOrder;

    private EopBasedataWbs fdBorrowWbs;

    private SysOrgPerson fdBorrowPerson;
    
    private SysOrgElement fdBorrowDept;
    
    private String fdBorrowPeriod;

    private EopBasedataCostCenter fdLendCostCenterGroup;

    private EopBasedataCostCenter fdLendCostCenter;

    private EopBasedataBudgetItem fdLendBudgetItem;

    private EopBasedataProject fdLendProject;

    private EopBasedataInnerOrder fdLendInnerOrder;

    private EopBasedataWbs fdLendWbs;

    private SysOrgPerson fdLendPerson;
    
    private SysOrgElement fdLendDept;
    
    private String fdLendPeriod;
    
    private FsscBudgetAdjustMain docMain;
    
    private String fdBorrowParentName;
    
    private String fdLendParentName;

    

    @Override
    public Class<FsscBudgetAdjustDetailForm> getFormClass() {
        return FsscBudgetAdjustDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdBorrowCostCenterGroup.fdName", "fdBorrowCostCenterGroupName");
            toFormPropertyMap.put("fdBorrowCostCenterGroup.fdId", "fdBorrowCostCenterGroupId");
            toFormPropertyMap.put("fdBorrowCostCenter.fdName", "fdBorrowCostCenterName");
            toFormPropertyMap.put("fdBorrowCostCenter.fdId", "fdBorrowCostCenterId");
            toFormPropertyMap.put("fdBorrowBudgetItem.fdName", "fdBorrowBudgetItemName");
            toFormPropertyMap.put("fdBorrowBudgetItem.fdId", "fdBorrowBudgetItemId");

            toFormPropertyMap.put("fdBorrowProject.fdName", "fdBorrowProjectName");
            toFormPropertyMap.put("fdBorrowProject.fdId", "fdBorrowProjectId");
            toFormPropertyMap.put("fdBorrowInnerOrder.fdName", "fdBorrowInnerOrderName");
            toFormPropertyMap.put("fdBorrowInnerOrder.fdId", "fdBorrowInnerOrderId");
            toFormPropertyMap.put("fdBorrowWbs.fdName", "fdBorrowWbsName");
            toFormPropertyMap.put("fdBorrowWbs.fdId", "fdBorrowWbsId");
            toFormPropertyMap.put("fdBorrowPerson.fdName", "fdBorrowPersonName");
            toFormPropertyMap.put("fdBorrowPerson.fdId", "fdBorrowPersonId");
            toFormPropertyMap.put("fdBorrowDept.fdName", "fdBorrowDeptName");
            toFormPropertyMap.put("fdBorrowDept.fdId", "fdBorrowDeptId");
            toFormPropertyMap.put("fdLendCostCenterGroup.fdName", "fdLendCostCenterGroupName");
            toFormPropertyMap.put("fdLendCostCenterGroup.fdId", "fdLendCostCenterGroupId");
            toFormPropertyMap.put("fdLendCostCenter.fdName", "fdLendCostCenterName");
            toFormPropertyMap.put("fdLendCostCenter.fdId", "fdLendCostCenterId");
            toFormPropertyMap.put("fdLendBudgetItem.fdName", "fdLendBudgetItemName");
            toFormPropertyMap.put("fdLendBudgetItem.fdId", "fdLendBudgetItemId");

            toFormPropertyMap.put("fdLendProject.fdName", "fdLendProjectName");
            toFormPropertyMap.put("fdLendProject.fdId", "fdLendProjectId");
            toFormPropertyMap.put("fdLendInnerOrder.fdName", "fdLendInnerOrderName");
            toFormPropertyMap.put("fdLendInnerOrder.fdId", "fdLendInnerOrderId");
            toFormPropertyMap.put("fdLendWbs.fdName", "fdLendWbsName");
            toFormPropertyMap.put("fdLendWbs.fdId", "fdLendWbsId");
            toFormPropertyMap.put("fdLendPerson.fdName", "fdLendPersonName");
            toFormPropertyMap.put("fdLendPerson.fdId", "fdLendPersonId");
            toFormPropertyMap.put("fdLendDept.fdName", "fdLendDeptName");
            toFormPropertyMap.put("fdLendDept.fdId", "fdLendDeptId");
            toFormPropertyMap.put("docMain.docSubject", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }
    /**
     * 借入可用预算
     */
    public Double getFdBorrowCanUseMoney() {
        return fdBorrowCanUseMoney;
    }
    /**
     * 借入可用预算
     */
    public void setFdBorrowCanUseMoney(Double fdBorrowCanUseMoney) {
        this.fdBorrowCanUseMoney = fdBorrowCanUseMoney;
    }
    /**
     * 借出可用预算
     */
    public Double getFdLendCanUseMoney() {
        return fdLendCanUseMoney;
    }
    /**
     * 借出可用预算
     */
    public void setFdLendCanUseMoney(Double fdLendCanUseMoney) {
        this.fdLendCanUseMoney = fdLendCanUseMoney;
    }

    /**
     * 调整金额
     */
    public Double getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 调整金额
     */
    public void setFdMoney(Double fdMoney) {
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
    public EopBasedataCostCenter getFdBorrowCostCenterGroup() {
        return this.fdBorrowCostCenterGroup;
    }

    /**
     * 借入成本中心组
     */
    public void setFdBorrowCostCenterGroup(EopBasedataCostCenter fdBorrowCostCenterGroup) {
        this.fdBorrowCostCenterGroup = fdBorrowCostCenterGroup;
    }

    /**
     * 借入成本中心
     */
    public EopBasedataCostCenter getFdBorrowCostCenter() {
        return this.fdBorrowCostCenter;
    }

    /**
     * 借入成本中心
     */
    public void setFdBorrowCostCenter(EopBasedataCostCenter fdBorrowCostCenter) {
        this.fdBorrowCostCenter = fdBorrowCostCenter;
    }

    /**
     * 借入预算科目
     */
    public EopBasedataBudgetItem getFdBorrowBudgetItem() {
        return this.fdBorrowBudgetItem;
    }

    /**
     * 借入预算科目
     */
    public void setFdBorrowBudgetItem(EopBasedataBudgetItem fdBorrowBudgetItem) {
        this.fdBorrowBudgetItem = fdBorrowBudgetItem;
    }

    /**
     * 借入项目
     */
    public EopBasedataProject getFdBorrowProject() {
        return this.fdBorrowProject;
    }

    /**
     * 借入项目
     */
    public void setFdBorrowProject(EopBasedataProject fdBorrowProject) {
        this.fdBorrowProject = fdBorrowProject;
    }

    /**
     * 借入内部订单
     */
    public EopBasedataInnerOrder getFdBorrowInnerOrder() {
        return this.fdBorrowInnerOrder;
    }

    /**
     * 借入内部订单
     */
    public void setFdBorrowInnerOrder(EopBasedataInnerOrder fdBorrowInnerOrder) {
        this.fdBorrowInnerOrder = fdBorrowInnerOrder;
    }

    /**
     * 借入WBS
     */
    public EopBasedataWbs getFdBorrowWbs() {
        return this.fdBorrowWbs;
    }

    /**
     * 借入WBS
     */
    public void setFdBorrowWbs(EopBasedataWbs fdBorrowWbs) {
        this.fdBorrowWbs = fdBorrowWbs;
    }

    /**
     * 借入员工
     */
    public SysOrgPerson getFdBorrowPerson() {
        return this.fdBorrowPerson;
    }

    /**
     * 借入员工
     */
    public void setFdBorrowPerson(SysOrgPerson fdBorrowPerson) {
        this.fdBorrowPerson = fdBorrowPerson;
    }
    
    /**
     * 借入部门
     */
    public SysOrgElement getFdBorrowDept() {
		return fdBorrowDept;
	}

    /**
     * 借入部门
     */
	public void setFdBorrowDept(SysOrgElement fdBorrowDept) {
		this.fdBorrowDept = fdBorrowDept;
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
    public EopBasedataCostCenter getFdLendCostCenterGroup() {
        return this.fdLendCostCenterGroup;
    }

    /**
     * 借出成本中心组
     */
    public void setFdLendCostCenterGroup(EopBasedataCostCenter fdLendCostCenterGroup) {
        this.fdLendCostCenterGroup = fdLendCostCenterGroup;
    }

    /**
     * 借出成本中心
     */
    public EopBasedataCostCenter getFdLendCostCenter() {
        return this.fdLendCostCenter;
    }

    /**
     * 借出成本中心
     */
    public void setFdLendCostCenter(EopBasedataCostCenter fdLendCostCenter) {
        this.fdLendCostCenter = fdLendCostCenter;
    }

    /**
     * 借出预算科目
     */
    public EopBasedataBudgetItem getFdLendBudgetItem() {
        return this.fdLendBudgetItem;
    }

    /**
     * 借出预算科目
     */
    public void setFdLendBudgetItem(EopBasedataBudgetItem fdLendBudgetItem) {
        this.fdLendBudgetItem = fdLendBudgetItem;
    }

    /**
     * 借出项目
     */
    public EopBasedataProject getFdLendProject() {
        return this.fdLendProject;
    }

    /**
     * 借出项目
     */
    public void setFdLendProject(EopBasedataProject fdLendProject) {
        this.fdLendProject = fdLendProject;
    }

    /**
     * 借出内部订单
     */
    public EopBasedataInnerOrder getFdLendInnerOrder() {
        return this.fdLendInnerOrder;
    }

    /**
     * 借出内部订单
     */
    public void setFdLendInnerOrder(EopBasedataInnerOrder fdLendInnerOrder) {
        this.fdLendInnerOrder = fdLendInnerOrder;
    }

    /**
     * 借出WBS
     */
    public EopBasedataWbs getFdLendWbs() {
        return this.fdLendWbs;
    }

    /**
     * 借出WBS
     */
    public void setFdLendWbs(EopBasedataWbs fdLendWbs) {
        this.fdLendWbs = fdLendWbs;
    }

    /**
     * 借出员工
     */
    public SysOrgPerson getFdLendPerson() {
        return this.fdLendPerson;
    }

    /**
     * 借出员工
     */
    public void setFdLendPerson(SysOrgPerson fdLendPerson) {
        this.fdLendPerson = fdLendPerson;
    }

    /**
     * 借出部门
     */
	public SysOrgElement getFdLendDept() {
		return fdLendDept;
	}
	/**
     * 借出部门
     */
	public void setFdLendDept(SysOrgElement fdLendDept) {
		this.fdLendDept = fdLendDept;
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
	public FsscBudgetAdjustMain getDocMain() {
		return docMain;
	}
	/**
     * 调整主表
     */
	public void setDocMain(FsscBudgetAdjustMain docMain) {
		this.docMain = docMain;
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
