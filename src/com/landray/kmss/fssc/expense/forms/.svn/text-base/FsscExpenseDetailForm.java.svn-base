package com.landray.kmss.fssc.expense.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

/**
  * 费用明细
  */
public class FsscExpenseDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdProvisionInfo;
    
    private String fdProvisionMoney;
    
    private String fdProjectId;
    
    private String fdProjectName;
    
    private String fdProappStatus;
    
    private String fdProappInfo;
    
    private String fdVehicleName;
    
    private String fdStartPlace;
    
    private String fdStartPlaceId;
    
    private String fdArrivalPlace;
    
    private String fdArrivalPlaceId;
    
    private String fdTravelDays;
    
    private String fdBerthId;
    
    private String fdBerthName;
    
    private String fdStartDate;
    
    private String fdIsDeduct;
    
    private String fdFeeStatus;
    
    private String fdFeeInfo;
    
    private String fdBudgetRate;
    
    private String fdBudgetMoney;
    private String fdBudgetStatus;

    private String fdInputTaxMoney;
    
    private String fdInputTaxRate;
    
    private String fdNoTaxMoney;
    
    private String fdNonDeductMoney;
    
    private String fdInvoiceMoney;

    private String fdBudgetInfo;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCostCenterId;

    private String fdCostCenterName;
    
    private String fdExpenseItemId;
    
    private String fdExpenseItemName;
    
    private String fdReason;

    private String fdRealUserId;

    private String fdRealUserName;
    
    private String fdDeptId;
    
    private String fdDeptName;

    private String fdApplyMoney;

    private String fdStandardMoney;

    private String fdCurrencyId;

    private String fdCurrencyName;

    private String fdExchangeRate;

    private String fdApprovedApplyMoney;

    private String fdApprovedStandardMoney;

    private String fdUse;

    private String fdHappenDate;
    
    private String fdTravel;
    
    private String fdWbsId;
    
    private String fdWbsName;
    
    private String fdInnerOrderId;
    
    private String fdInnerOrderName;
    
	private String fdStandardStatus;
    
    private String fdStandardInfo;
    
    public String getFdStandardInfo() {
		return fdStandardInfo;
	}
	
	public void setFdStandardInfo(String fdStandardInfo) {
		this.fdStandardInfo = fdStandardInfo;
	}
    
    private String fdPersonNumber;
    
    private String fdExpenseTempId;
    
    private String fdExpenseTempDetailIds;
    
    private String fdNoteId;
    
    private String fdTranDataId;    //交易数据id

    private String fdDayCalType;//天数计算规则

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdProvisionInfo = null;
    	fdProvisionMoney = null;
    	fdProjectId = null;
    	fdProjectName = null;
		fdProappStatus = null;
		fdProappInfo = null;
    	fdVehicleName = null;
    	fdNoteId= null;
    	fdStartPlace = null;
        fdStartPlaceId= null;
        fdArrivalPlace = null;
        fdArrivalPlaceId = null;
        fdTravelDays = null;
        fdBerthId = null;
        fdBerthName = null;
    	fdStartDate = null;
    	fdIsDeduct = null;
    	fdInputTaxMoney = null;
    	fdInputTaxRate=null;
    	fdNoTaxMoney= null;
        fdNonDeductMoney= null;
        fdInvoiceMoney= null;
    	fdFeeInfo = null;
    	fdFeeStatus = null;
    	fdBudgetRate = null;
    	fdBudgetMoney = null;
    	fdBudgetInfo = null;
    	fdBudgetStatus = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCostCenterId = null;
        fdCostCenterName = null;
        fdExpenseItemId = null;
        fdExpenseItemName = null;
        fdReason = null;
        fdRealUserId = null;
        fdDeptId = null;
        fdDeptName = null;
        fdRealUserName = null;
        fdApplyMoney = null;
        fdStandardMoney = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        fdExchangeRate = null;
        fdApprovedApplyMoney = null;
        fdApprovedStandardMoney = null;
        fdUse = null;
        fdHappenDate = null;
        fdTravel = null;
        fdWbsId = null;
        fdWbsName = null;
        fdInnerOrderId = null;
        fdInnerOrderName = null;
        fdStandardStatus = null;
        fdStandardInfo = null;
        fdPersonNumber = null;
        fdExpenseTempId=null;
        fdExpenseTempDetailIds=null;
        fdTranDataId = null;
        fdDayCalType = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseDetail> getModelClass() {
        return FsscExpenseDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdCostCenterId", new FormConvertor_IDToModel("fdCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdProjectId", new FormConvertor_IDToModel("fdProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdRealUserId", new FormConvertor_IDToModel("fdRealUser", SysOrgPerson.class));
            toModelPropertyMap.put("fdDeptId", new FormConvertor_IDToModel("fdDept", SysOrgElement.class));
            toModelPropertyMap.put("fdExpenseItemId", new FormConvertor_IDToModel("fdExpenseItem", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdHappenDate", new FormConvertor_Common("fdHappenDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdStartDate", new FormConvertor_Common("fdStartDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdWbsId", new FormConvertor_IDToModel("fdWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdInnerOrderId", new FormConvertor_IDToModel("fdInnerOrder", EopBasedataInnerOrder.class));
            toModelPropertyMap.put("fdBerthId", new FormConvertor_IDToModel("fdBerth", EopBasedataBerth.class));
        }
        return toModelPropertyMap;
    }
    
    public String getFdProvisionInfo() {
		return fdProvisionInfo;
	}

	public void setFdProvisionInfo(String fdProvisionInfo) {
		this.fdProvisionInfo = fdProvisionInfo;
	}

	public String getFdProvisionMoney() {
		return fdProvisionMoney;
	}

	public void setFdProvisionMoney(String fdProvisionMoney) {
		this.fdProvisionMoney = fdProvisionMoney;
	}

	public String getFdProjectId() {
		return fdProjectId;
	}

	public void setFdProjectId(String fdProjectId) {
		this.fdProjectId = fdProjectId;
	}

	public String getFdProjectName() {
		return fdProjectName;
	}

	public void setFdProjectName(String fdProjectName) {
		this.fdProjectName = fdProjectName;
	}

	public String getFdProappStatus() {
		return fdProappStatus;
	}

	public void setFdProappStatus(String fdProappStatus) {
		this.fdProappStatus = fdProappStatus;
	}

	public String getFdProappInfo() {
		return fdProappInfo;
	}

	public void setFdProappInfo(String fdProappInfo) {
		this.fdProappInfo = fdProappInfo;
	}

	public String getFdVehicleName() {
		return fdVehicleName;
	}

	public void setFdVehicleName(String fdVehicleName) {
		this.fdVehicleName = fdVehicleName;
	}

	//未报费用id
    public String getFdNoteId() {
		return fdNoteId;
	}

	public void setFdNoteId(String fdNoteId) {
		this.fdNoteId = fdNoteId;
	}

    public String getFdStartPlace() {
		return fdStartPlace;
	}

	public void setFdStartPlace(String fdStartPlace) {
		this.fdStartPlace = fdStartPlace;
	}

	public String getFdStartPlaceId() {
		return fdStartPlaceId;
	}

	public void setFdStartPlaceId(String fdStartPlaceId) {
		this.fdStartPlaceId = fdStartPlaceId;
	}

	public String getFdArrivalPlace() {
		return fdArrivalPlace;
	}

	public void setFdArrivalPlace(String fdArrivalPlace) {
		this.fdArrivalPlace = fdArrivalPlace;
	}

	public String getFdArrivalPlaceId() {
		return fdArrivalPlaceId;
	}

	public void setFdArrivalPlaceId(String fdArrivalPlaceId) {
		this.fdArrivalPlaceId = fdArrivalPlaceId;
	}

	public String getFdTravelDays() {
		return fdTravelDays;
	}

	public void setFdTravelDays(String fdTravelDays) {
		this.fdTravelDays = fdTravelDays;
	}

	public String getFdBerthId() {
		return fdBerthId;
	}

	public void setFdBerthId(String fdBerthId) {
		this.fdBerthId = fdBerthId;
	}

	public String getFdBerthName() {
		return fdBerthName;
	}

	public void setFdBerthName(String fdBerthName) {
		this.fdBerthName = fdBerthName;
	}

	public String getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
     * 进项税额
     */
    public String getFdInputTaxMoney() {
        return fdInputTaxMoney;
    }
    /**
     * 进项税额
     */
    public void setFdInputTaxMoney(String fdInputTaxMoney) {
        this.fdInputTaxMoney = fdInputTaxMoney;
    }
    
    /**
     * 进项税率
     */
    public String getFdInputTaxRate() {
		return fdInputTaxRate;
	}
    /**
     * 进项税率
     */
	public void setFdInputTaxRate(String fdInputTaxRate) {
		this.fdInputTaxRate = fdInputTaxRate;
	}

	/**
     * 费用承担方
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 费用承担方
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 费用承担方
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 费用承担方
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 费用承担部门
     */
    public String getFdCostCenterId() {
        return this.fdCostCenterId;
    }

    /**
     * 费用承担部门
     */
    public void setFdCostCenterId(String fdCostCenterId) {
        this.fdCostCenterId = fdCostCenterId;
    }

    /**
     * 费用承担部门
     */
    public String getFdCostCenterName() {
        return this.fdCostCenterName;
    }

    /**
     * 费用承担部门
     */
    public void setFdCostCenterName(String fdCostCenterName) {
        this.fdCostCenterName = fdCostCenterName;
    }

    /**
     * 报销人
     */
    public String getFdRealUserId() {
        return this.fdRealUserId;
    }

    /**
     * 报销人
     */
    public void setFdRealUserId(String fdRealUserId) {
        this.fdRealUserId = fdRealUserId;
    }

    /**
     * 报销人
     */
    public String getFdRealUserName() {
        return this.fdRealUserName;
    }

    /**
     * 报销人
     */
    public void setFdRealUserName(String fdRealUserName) {
        this.fdRealUserName = fdRealUserName;
    }

    /**
     * 原币申报金额
     */
    public String getFdApplyMoney() {
    	if(StringUtil.isNull(this.fdApplyMoney)){
    		return this.fdApplyMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdApplyMoney));
    }

    /**
     * 原币申报金额
     */
    public void setFdApplyMoney(String fdApplyMoney) {
        this.fdApplyMoney = fdApplyMoney;
    }

    /**
     * 本币金额
     */
    public String getFdStandardMoney() {
    	if(StringUtil.isNull(this.fdStandardMoney)){
    		return this.fdStandardMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdStandardMoney));
    }

    /**
     * 本币金额
     */
    public void setFdStandardMoney(String fdStandardMoney) {
        this.fdStandardMoney = fdStandardMoney;
    }

    /**
     * 币种
     */
    public String getFdCurrencyId() {
        return this.fdCurrencyId;
    }

    /**
     * 币种
     */
    public void setFdCurrencyId(String fdCurrencyId) {
        this.fdCurrencyId = fdCurrencyId;
    }

    /**
     * 币种
     */
    public String getFdCurrencyName() {
        return this.fdCurrencyName;
    }

    /**
     * 币种
     */
    public void setFdCurrencyName(String fdCurrencyName) {
        this.fdCurrencyName = fdCurrencyName;
    }

    /**
     * 汇率
     */
    public String getFdExchangeRate() {
    	return this.fdExchangeRate;
    }

    /**
     * 汇率
     */
    public void setFdExchangeRate(String fdExchangeRate) {
        this.fdExchangeRate = fdExchangeRate;
    }

    /**
     * 核准金额(原币)
     */
    public String getFdApprovedApplyMoney() {
    	if(StringUtil.isNull(this.fdApprovedApplyMoney)){
    		return this.fdApprovedApplyMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdApprovedApplyMoney));
    }

    /**
     * 核准金额(原币)
     */
    public void setFdApprovedApplyMoney(String fdApprovedApplyMoney) {
        this.fdApprovedApplyMoney = fdApprovedApplyMoney;
    }

    /**
     * 核准金额(本币)
     */
    public String getFdApprovedStandardMoney() {
    	if(StringUtil.isNull(this.fdApprovedStandardMoney)){
    		return this.fdApprovedStandardMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdApprovedStandardMoney));
    }

    /**
     * 核准金额(本币)
     */
    public void setFdApprovedStandardMoney(String fdApprovedStandardMoney) {
        this.fdApprovedStandardMoney = fdApprovedStandardMoney;
    }

    /**
     * 用途(摘要)
     */
    public String getFdUse() {
        return this.fdUse;
    }

    /**
     * 用途(摘要)
     */
    public void setFdUse(String fdUse) {
        this.fdUse = fdUse;
    }

    /**
     * 发生日期
     */
    public String getFdHappenDate() {
        return this.fdHappenDate;
    }

    /**
     * 发生日期
     */
    public void setFdHappenDate(String fdHappenDate) {
        this.fdHappenDate = fdHappenDate;
    }

	public String getFdExpenseItemId() {
		return fdExpenseItemId;
	}

	public void setFdExpenseItemId(String fdExpenseItemId) {
		this.fdExpenseItemId = fdExpenseItemId;
	}

	public String getFdExpenseItemName() {
		return fdExpenseItemName;
	}

	public void setFdExpenseItemName(String fdExpenseItemName) {
		this.fdExpenseItemName = fdExpenseItemName;
	}

	public String getFdReason() {
		return fdReason;
	}

	public void setFdReason(String fdReason) {
		this.fdReason = fdReason;
	}

	public String getFdTravel() {
		return fdTravel;
	}

	public void setFdTravel(String fdTravel) {
		this.fdTravel = fdTravel;
	}

	public String getFdWbsId() {
		return fdWbsId;
	}

	public void setFdWbsId(String fdWbsId) {
		this.fdWbsId = fdWbsId;
	}

	public String getFdWbsName() {
		return fdWbsName;
	}

	public void setFdWbsName(String fdWbsName) {
		this.fdWbsName = fdWbsName;
	}

	public String getFdInnerOrderId() {
		return fdInnerOrderId;
	}

	public void setFdInnerOrderId(String fdInnerOrderId) {
		this.fdInnerOrderId = fdInnerOrderId;
	}

	public String getFdInnerOrderName() {
		return fdInnerOrderName;
	}

	public void setFdInnerOrderName(String fdInnerOrderName) {
		this.fdInnerOrderName = fdInnerOrderName;
	}

	public String getFdBudgetStatus() {
		return fdBudgetStatus;
	}

	public void setFdBudgetStatus(String fdBudgetStatus) {
		this.fdBudgetStatus = fdBudgetStatus;
	}

	public String getFdBudgetInfo() {
		return fdBudgetInfo;
	}

	public void setFdBudgetInfo(String fdBudgetInfo) {
		this.fdBudgetInfo = fdBudgetInfo;
	}
	public String getFdBudgetRate() {
		return fdBudgetRate;
	}

	public void setFdBudgetRate(String fdBudgetRate) {
		this.fdBudgetRate = fdBudgetRate;
	}

	public String getFdBudgetMoney() {
		return fdBudgetMoney;
	}

	public void setFdBudgetMoney(String fdBudgetMoney) {
		this.fdBudgetMoney = fdBudgetMoney;
	}

	public String getFdStandardStatus() {
		return fdStandardStatus;
	}

	public void setFdStandardStatus(String fdStandardStatus) {
		this.fdStandardStatus = fdStandardStatus;
	}

	public String getFdPersonNumber() {
		return fdPersonNumber;
	}

	public void setFdPersonNumber(String fdPersonNumber) {
		this.fdPersonNumber = fdPersonNumber;
	}

	public String getFdFeeStatus() {
		return fdFeeStatus;
	}

	public void setFdFeeStatus(String fdFeeStatus) {
		this.fdFeeStatus = fdFeeStatus;
	}

	public String getFdFeeInfo() {
		return fdFeeInfo;
	}

	public void setFdFeeInfo(String fdFeeInfo) {
		this.fdFeeInfo = fdFeeInfo;
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

	public String getFdIsDeduct() {
		return fdIsDeduct;
	}

	public void setFdIsDeduct(String fdIsDeduct) {
		this.fdIsDeduct = fdIsDeduct;
	}
	/**
	 * 对应发票查看页面ID
	 * @return
	 */
	public String getFdExpenseTempId() {
		return fdExpenseTempId;
	}
	
	/**
	 * 对应发票查看页面ID
	 * @return
	 */
	public void setFdExpenseTempId(String fdExpenseTempId) {
		this.fdExpenseTempId = fdExpenseTempId;
	}

	/**
	 * 对应发票明细ID
	 * @return
	 */
	public String getFdExpenseTempDetailIds() {
		return fdExpenseTempDetailIds;
	}

	/**
	 * 对应发票明细ID
	 * @return
	 */
	public void setFdExpenseTempDetailIds(String fdExpenseTempDetailIds) {
		this.fdExpenseTempDetailIds = fdExpenseTempDetailIds;
	}
	/**
	 * 不含税金额
	 * @return
	 */
	public String getFdNoTaxMoney() {
		return fdNoTaxMoney;
	}

	/**
	 * 不含税金额
	 * @param fdNoTaxMoney
	 */
	public void setFdNoTaxMoney(String fdNoTaxMoney) {
		this.fdNoTaxMoney = fdNoTaxMoney;
	}
	/**
	 * 不可抵扣金额
	 * @return
	 */
	public String getFdNonDeductMoney() {
		return fdNonDeductMoney;
	}
	/**
	 * 不可抵扣金额
	 * @param fdNoTaxMoney
	 */
	public void setFdNonDeductMoney(String fdNonDeductMoney) {
		this.fdNonDeductMoney = fdNonDeductMoney;
	}
	/**
	 * 发票金额
	 * @return
	 */
	public String getFdInvoiceMoney() {
		return fdInvoiceMoney;
	}
	/**
	 * 发票金额
	 * @param fdNoTaxMoney
	 */
	public void setFdInvoiceMoney(String fdInvoiceMoney) {
		this.fdInvoiceMoney = fdInvoiceMoney;
	}

	public String getFdTranDataId() {
		return fdTranDataId;
	}

	public void setFdTranDataId(String fdTranDataId) {
		this.fdTranDataId = fdTranDataId;
	}

    public String getFdDayCalType() {
        return fdDayCalType;
    }

    public void setFdDayCalType(String fdDayCalType) {
        this.fdDayCalType = fdDayCalType;
    }
}
