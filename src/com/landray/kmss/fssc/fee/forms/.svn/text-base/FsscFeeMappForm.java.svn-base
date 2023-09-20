package com.landray.kmss.fssc.fee.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 台账映射
  */
public class FsscFeeMappForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdTravelDays;
    
    private String fdTravelDaysId;
    
    private String fdArea;
    
    private String fdAreaId;
    
    private String fdLevel;
    
    private String fdLevelId;
    
    private String fdPersonNum;
    
    private String fdPersonNumId;
    
    private String fdVehicle;
    
    private String fdVehicleId;
    
    private String fdStandard;
    
    private String fdStandardId;
    
    private String fdCurrency;
    
    private String fdCurrencyId;
    
    private String fdTable;
    
    private String fdTableId;  
    
    private String fdRule;
    
    private String fdRuleId;

    private String fdCompanyId;

    private String fdCostCenterId;

    private String fdCostGroupId;
    
    private String fdCompany;

    private String fdCostCenter;

    private String fdCostGroup;

    private String fdExpenseItem;

    private String fdProject;

    private String fdInnerOrder;

    private String fdWbs;

    private String fdPerson;

    private String fdMoney;

    private String fdName;

    private String fdDept;

    private String fdExpenseItemId;

    private String fdProjectId;

    private String fdInnerOrderId;

    private String fdWbsId;

    private String fdDeptId;

    private String fdPersonId;

    private String fdMoneyId;

    private String fdTemplateId;

    private String fdTemplateName;
    
    private String fdStartDate;
    
    private String fdStartDateId;
    
    private String fdEndDate;
    
    private String fdEndDateId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdTravelDays = null;
    	fdTravelDaysId = null;
    	fdLevelId = null;
    	fdLevel = null;
    	fdArea = null;
    	fdAreaId = null;
    	fdPersonNum = null;
    	fdPersonNumId=null;
    	fdVehicle = null;
    	fdVehicleId = null;
    	fdStandard = null;
    	fdStandardId = null;
    	fdStartDate = null;
    	fdStartDateId = null;
    	fdEndDate = null;
    	fdEndDateId = null;
    	fdCurrency = null;
        fdCurrencyId = null;
    	fdTable = null;
    	fdTableId = null;
    	fdRule = null;
    	fdRuleId = null;
        fdCompanyId = null;
        fdCostCenterId = null;
        fdCostGroupId = null;
        fdCompany = null;
        fdCostCenter = null;
        fdCostGroup = null;
        fdExpenseItem = null;
        fdProject = null;
        fdInnerOrder = null;
        fdWbs = null;
        fdPerson = null;
        fdMoney = null;
        fdName = null;
        fdDept = null;
        fdExpenseItemId = null;
        fdProjectId = null;
        fdInnerOrderId = null;
        fdWbsId = null;
        fdDeptId = null;
        fdPersonId = null;
        fdMoneyId = null;
        fdTemplateId = null;
        fdTemplateName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscFeeMapp> getModelClass() {
        return FsscFeeMapp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel("fdTemplate", FsscFeeTemplate.class));
        }
        return toModelPropertyMap;
    }

    public String getFdTravelDays() {
		return fdTravelDays;
	}

	public void setFdTravelDays(String fdTravelDays) {
		this.fdTravelDays = fdTravelDays;
	}

	public String getFdTravelDaysId() {
		return fdTravelDaysId;
	}

	public void setFdTravelDaysId(String fdTravelDaysId) {
		this.fdTravelDaysId = fdTravelDaysId;
	}

	/**
     * 公司
     */
    public String getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 公司
     */
    public void setFdCompany(String fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 成本中心
     */
    public String getFdCostCenter() {
        return this.fdCostCenter;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenter(String fdCostCenter) {
        this.fdCostCenter = fdCostCenter;
    }

    /**
     * 成本中心组
     */
    public String getFdCostGroup() {
        return this.fdCostGroup;
    }

    /**
     * 成本中心组
     */
    public void setFdCostGroup(String fdCostGroup) {
        this.fdCostGroup = fdCostGroup;
    }
    
    /**
     * 公司
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
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
     * 成本中心组
     */
    public String getFdCostGroupId() {
        return this.fdCostGroupId;
    }

    /**
     * 成本中心组
     */
    public void setFdCostGroupId(String fdCostGroupId) {
        this.fdCostGroupId = fdCostGroupId;
    }

    /**
     * 费用类型
     */
    public String getFdExpenseItem() {
        return this.fdExpenseItem;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseItem(String fdExpenseItem) {
        this.fdExpenseItem = fdExpenseItem;
    }

    /**
     * 项目
     */
    public String getFdProject() {
        return this.fdProject;
    }

    /**
     * 项目
     */
    public void setFdProject(String fdProject) {
        this.fdProject = fdProject;
    }

    /**
     * 内部订单
     */
    public String getFdInnerOrder() {
        return this.fdInnerOrder;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrder(String fdInnerOrder) {
        this.fdInnerOrder = fdInnerOrder;
    }

    /**
     * WBS
     */
    public String getFdWbs() {
        return this.fdWbs;
    }

    /**
     * WBS
     */
    public void setFdWbs(String fdWbs) {
        this.fdWbs = fdWbs;
    }

    /**
     * 人员
     */
    public String getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 人员
     */
    public void setFdPerson(String fdPerson) {
        this.fdPerson = fdPerson;
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
     * 部门
     */
    public String getFdDept() {
        return this.fdDept;
    }

    /**
     * 部门
     */
    public void setFdDept(String fdDept) {
        this.fdDept = fdDept;
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
     * 金额ID
     */
    public String getFdMoneyId() {
        return this.fdMoneyId;
    }

    /**
     * 金额ID
     */
    public void setFdMoneyId(String fdMoneyId) {
        this.fdMoneyId = fdMoneyId;
    }

    /**
     * 模板
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * 模板
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 模板
     */
    public String getFdTemplateName() {
        return this.fdTemplateName;
    }

    /**
     * 模板
     */
    public void setFdTemplateName(String fdTemplateName) {
        this.fdTemplateName = fdTemplateName;
    }

	public String getFdTable() {
		return fdTable;
	}

	public void setFdTable(String fdTable) {
		this.fdTable = fdTable;
	}

	public String getFdTableId() {
		return fdTableId;
	}

	public void setFdTableId(String fdTableId) {
		this.fdTableId = fdTableId;
	}

	public String getFdRule() {
		return fdRule;
	}

	public void setFdRule(String fdRule) {
		this.fdRule = fdRule;
	}

	public String getFdRuleId() {
		return fdRuleId;
	}

	public void setFdRuleId(String fdRuleId) {
		this.fdRuleId = fdRuleId;
	}

	public String getFdCurrency() {
		return fdCurrency;
	}

	public void setFdCurrency(String fdCurrency) {
		this.fdCurrency = fdCurrency;
	}

	public String getFdCurrencyId() {
		return fdCurrencyId;
	}

	public void setFdCurrencyId(String fdCurrencyId) {
		this.fdCurrencyId = fdCurrencyId;
	}

	public String getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	public String getFdStartDateId() {
		return fdStartDateId;
	}

	public void setFdStartDateId(String fdStartDateId) {
		this.fdStartDateId = fdStartDateId;
	}

	public String getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public String getFdEndDateId() {
		return fdEndDateId;
	}

	public void setFdEndDateId(String fdEndDateId) {
		this.fdEndDateId = fdEndDateId;
	}

	public String getFdArea() {
		return fdArea;
	}

	public void setFdArea(String fdArea) {
		this.fdArea = fdArea;
	}

	public String getFdAreaId() {
		return fdAreaId;
	}

	public void setFdAreaId(String fdAreaId) {
		this.fdAreaId = fdAreaId;
	}

	public String getFdLevel() {
		return fdLevel;
	}

	public void setFdLevel(String fdLevel) {
		this.fdLevel = fdLevel;
	}

	public String getFdLevelId() {
		return fdLevelId;
	}

	public void setFdLevelId(String fdLevelId) {
		this.fdLevelId = fdLevelId;
	}

	public String getFdPersonNum() {
		return fdPersonNum;
	}

	public void setFdPersonNum(String fdPersonNum) {
		this.fdPersonNum = fdPersonNum;
	}

	public String getFdPersonNumId() {
		return fdPersonNumId;
	}

	public void setFdPersonNumId(String fdPersonNumId) {
		this.fdPersonNumId = fdPersonNumId;
	}

	public String getFdVehicle() {
		return fdVehicle;
	}

	public void setFdVehicle(String fdVehicle) {
		this.fdVehicle = fdVehicle;
	}

	public String getFdVehicleId() {
		return fdVehicleId;
	}

	public void setFdVehicleId(String fdVehicleId) {
		this.fdVehicleId = fdVehicleId;
	}

	public String getFdStandard() {
		return fdStandard;
	}

	public void setFdStandard(String fdStandard) {
		this.fdStandard = fdStandard;
	}

	public String getFdStandardId() {
		return fdStandardId;
	}

	public void setFdStandardId(String fdStandardId) {
		this.fdStandardId = fdStandardId;
	}

}
