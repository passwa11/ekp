package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.expense.forms.FsscExpenseDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 费用明细
  */
public class FsscExpenseDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private String fdProvisionInfo;
    
    private Double fdProvisionMoney;
    
    private EopBasedataProject fdProject;
    
    private String fdProappStatus;
    
    private String fdProappInfo;
    
    private String fdStartPlace;
    
    private String fdStartPlaceId;
    
    private String fdArrivalPlace;
    
    private String fdArrivalPlaceId;
    
    private EopBasedataBerth fdBerth;
    
    private Integer fdTravelDays;
    
    private Date fdStartDate;
    
    private Boolean fdIsDeduct;
    
    private Double fdBudgetRate;
    
    private Double fdBudgetMoney;

    private Double fdInputTaxMoney;//进项税额
    
    private Double fdInputTaxRate;
    
    private Double fdNoTaxMoney;
    
    private Double fdNonDeductMoney;
    
    private Double fdInvoiceMoney;
    
    private String fdFeeStatus;
    
    private String fdFeeInfo;

    private EopBasedataCompany fdCompany;

    private EopBasedataCostCenter fdCostCenter;
    
    private EopBasedataExpenseItem fdExpenseItem;
    
    private String fdReason;

    private SysOrgPerson fdRealUser;
    
    private SysOrgElement fdDept;

    private Double fdApplyMoney;

    private Double fdStandardMoney;

    private EopBasedataCurrency fdCurrency;

    private Double fdExchangeRate;

    private Double fdApprovedApplyMoney;

    private Double fdApprovedStandardMoney;

    private String fdUse;

    private Date fdHappenDate;

    private String fdBudgetStatus;

    private String fdBudgetInfo;
    
    private String fdTravel;
    
    private EopBasedataWbs fdWbs;
    
    private EopBasedataInnerOrder fdInnerOrder;
    
    private String fdStandardStatus;
    
    private String fdStandardInfo;
    
    public String getFdStandardInfo() {
		return fdStandardInfo;
	}
	
	public void setFdStandardInfo(String fdStandardInfo) {
		this.fdStandardInfo = fdStandardInfo;
	}
    
    private Integer fdPersonNumber;
    
    private String fdExpenseTempId;
    
    private String fdExpenseTempDetailIds;
    
    private String fdNoteId ;
    
    private FsscExpenseMain docMain;

    private String fdTranDataId;	//交易数据id
    
    @Override
    public Class<FsscExpenseDetailForm> getFormClass() {
        return FsscExpenseDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdProject.fdName", "fdProjectName");
            toFormPropertyMap.put("fdProject.fdId", "fdProjectId");
            toFormPropertyMap.put("fdCostCenter.fdName", "fdCostCenterName");
            toFormPropertyMap.put("fdCostCenter.fdId", "fdCostCenterId");
            toFormPropertyMap.put("fdRealUser.fdName", "fdRealUserName");
            toFormPropertyMap.put("fdRealUser.fdId", "fdRealUserId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
            toFormPropertyMap.put("fdExpenseItem.fdName", "fdExpenseItemName");
            toFormPropertyMap.put("fdExpenseItem.fdId", "fdExpenseItemId");
			toFormPropertyMap.put("fdExpenseItem.fdDayCalType", "fdDayCalType");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("fdWbs.fdName", "fdWbsName");
            toFormPropertyMap.put("fdWbs.fdId", "fdWbsId");
            toFormPropertyMap.put("fdInnerOrder.fdName", "fdInnerOrderName");
            toFormPropertyMap.put("fdInnerOrder.fdId", "fdInnerOrderId");
            toFormPropertyMap.put("fdBerth.fdName", "fdBerthName");
            toFormPropertyMap.put("fdBerth.fdId", "fdBerthId");
            toFormPropertyMap.put("fdHappenDate", new ModelConvertor_Common("fdHappenDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdStartDate", new ModelConvertor_Common("fdStartDate").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toFormPropertyMap;
    }
    
    public String getFdProvisionInfo() {
    	return (String) readLazyField("fdProvisionInfo", fdProvisionInfo);
	}

	public void setFdProvisionInfo(String fdProvisionInfo) {
		this.fdProvisionInfo = (String) writeLazyField("fdProvisionInfo",
				this.fdProvisionInfo, fdProvisionInfo);
	}

	public Double getFdProvisionMoney() {
		return fdProvisionMoney;
	}

	public void setFdProvisionMoney(Double fdProvisionMoney) {
		this.fdProvisionMoney = fdProvisionMoney;
	}

	public EopBasedataProject getFdProject() {
		return fdProject;
	}

	public void setFdProject(EopBasedataProject fdProject) {
		this.fdProject = fdProject;
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

	//未报费用id
    public String getFdNoteId() {
		return fdNoteId;
	}

	public void setFdNoteId(String fdNoteId) {
		this.fdNoteId = fdNoteId;
	}

	public FsscExpenseMain getDocMain() {
		return docMain;
	}

	public void setDocMain(FsscExpenseMain docMain) {
		this.docMain = docMain;
	}

	public Integer getFdTravelDays() {
		return fdTravelDays;
	}

	public void setFdTravelDays(Integer fdTravelDays) {
		this.fdTravelDays = fdTravelDays;
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

	public EopBasedataBerth getFdBerth() {
		return fdBerth;
	}

	public void setFdBerth(EopBasedataBerth fdBerth) {
		this.fdBerth = fdBerth;
	}

	public Date getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(Date fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
     * 费用承担方
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 费用承担方
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 费用承担部门
     */
    public EopBasedataCostCenter getFdCostCenter() {
        return this.fdCostCenter;
    }

    /**
     * 费用承担部门
     */
    public void setFdCostCenter(EopBasedataCostCenter fdCostCenter) {
        this.fdCostCenter = fdCostCenter;
    }

    /**
     * 报销人
     */
    public SysOrgPerson getFdRealUser() {
        return this.fdRealUser;
    }

    /**
     * 报销人
     */
    public void setFdRealUser(SysOrgPerson fdRealUser) {
        this.fdRealUser = fdRealUser;
    }

    /**
     * 原币申报金额
     */
    public Double getFdApplyMoney() {
        return this.fdApplyMoney;
    }

    /**
     * 原币申报金额
     */
    public void setFdApplyMoney(Double fdApplyMoney) {
        this.fdApplyMoney = fdApplyMoney;
    }

    /**
     * 本币金额
     */
    public Double getFdStandardMoney() {
        return this.fdStandardMoney;
    }

    /**
     * 本币金额
     */
    public void setFdStandardMoney(Double fdStandardMoney) {
        this.fdStandardMoney = fdStandardMoney;
    }

    /**
     * 币种
     */
    public EopBasedataCurrency getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 币种
     */
    public void setFdCurrency(EopBasedataCurrency fdCurrency) {
        this.fdCurrency = fdCurrency;
    }

    /**
     * 汇率
     */
    public Double getFdExchangeRate() {
        return this.fdExchangeRate;
    }

    /**
     * 汇率
     */
    public void setFdExchangeRate(Double fdExchangeRate) {
        this.fdExchangeRate = fdExchangeRate;
    }

    /**
     * 核准金额(原币)
     */
    public Double getFdApprovedApplyMoney() {
        return this.fdApprovedApplyMoney;
    }

    /**
     * 核准金额(原币)
     */
    public void setFdApprovedApplyMoney(Double fdApprovedApplyMoney) {
        this.fdApprovedApplyMoney = fdApprovedApplyMoney;
    }

    /**
     * 核准金额(本币)
     */
    public Double getFdApprovedStandardMoney() {
        return this.fdApprovedStandardMoney;
    }

    /**
     * 核准金额(本币)
     */
    public void setFdApprovedStandardMoney(Double fdApprovedStandardMoney) {
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
    public Date getFdHappenDate() {
        return this.fdHappenDate;
    }

    /**
     * 发生日期
     */
    public void setFdHappenDate(Date fdHappenDate) {
        this.fdHappenDate = fdHappenDate;
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
     * 预算信息
     */
    public String getFdBudgetInfo() {
        return this.fdBudgetInfo;
    }

    /**
     * 预算信息
     */
    public void setFdBudgetInfo(String fdBudgetInfo) {
        this.fdBudgetInfo = fdBudgetInfo;
    }

	public EopBasedataExpenseItem getFdExpenseItem() {
		return fdExpenseItem;
	}

	public void setFdExpenseItem(EopBasedataExpenseItem fdExpenseItem) {
		this.fdExpenseItem = fdExpenseItem;
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

	public EopBasedataWbs getFdWbs() {
		return fdWbs;
	}

	public void setFdWbs(EopBasedataWbs fdWbs) {
		this.fdWbs = fdWbs;
	}

	public EopBasedataInnerOrder getFdInnerOrder() {
		return fdInnerOrder;
	}

	public void setFdInnerOrder(EopBasedataInnerOrder fdInnerOrder) {
		this.fdInnerOrder = fdInnerOrder;
	}
	/**
	 * 预算汇率
	 * @return
	 */
	public Double getFdBudgetRate() {
		return fdBudgetRate;
	}

	/**
	 * 预算汇率
	 * @return
	 */
	public void setFdBudgetRate(Double fdBudgetRate) {
		this.fdBudgetRate = fdBudgetRate;
	}

	/**
	 * 预算金额
	 * @return
	 */
	public Double getFdBudgetMoney() {
		return fdBudgetMoney;
	}
	/**
	 * 预算金额
	 * @return
	 */
	public void setFdBudgetMoney(Double fdBudgetMoney) {
		this.fdBudgetMoney = fdBudgetMoney;
	}
    /**
     * 进项税额
     */
    public Double getFdInputTaxMoney() {
        return fdInputTaxMoney;
    }
    /**
     * 进项税额
     */
    public void setFdInputTaxMoney(Double fdInputTaxMoney) {
        this.fdInputTaxMoney = fdInputTaxMoney;
    }
    /**
     * 进项税率
     */
	public Double getFdInputTaxRate() {
		return fdInputTaxRate;
	}
	 /**
     * 进项税率
     */
	public void setFdInputTaxRate(Double fdInputTaxRate) {
		this.fdInputTaxRate = fdInputTaxRate;
	}

	public String getFdStandardStatus() {
		return fdStandardStatus;
	}

	public void setFdStandardStatus(String fdStandardStatus) {
		this.fdStandardStatus = fdStandardStatus;
	}

	public Integer getFdPersonNumber() {
		return fdPersonNumber;
	}

	public void setFdPersonNumber(Integer fdPersonNumber) {
		this.fdPersonNumber = fdPersonNumber;
	}

	public String getFdFeeStatus() {
		return fdFeeStatus;
	}

	public void setFdFeeStatus(String fdFeeStatus) {
		this.fdFeeStatus = fdFeeStatus;
	}

	public String getFdFeeInfo() {
		return (String) readLazyField("fdFeeInfo", fdFeeInfo);
	}

	public void setFdFeeInfo(String fdFeeInfo) {
		this.fdFeeInfo = (String) writeLazyField("fdFeeInfo",
				this.fdFeeInfo, fdFeeInfo);
	}

	public SysOrgElement getFdDept() {
		return fdDept;
	}

	public void setFdDept(SysOrgElement fdDept) {
		this.fdDept = fdDept;
	}

	public Boolean getFdIsDeduct() {
		return fdIsDeduct;
	}

	public void setFdIsDeduct(Boolean fdIsDeduct) {
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
	public Double getFdNoTaxMoney() {
		return fdNoTaxMoney;
	}

	/**
	 * 不含税金额
	 * @param fdNoTaxMoney
	 */
	public void setFdNoTaxMoney(Double fdNoTaxMoney) {
		this.fdNoTaxMoney = fdNoTaxMoney;
	}
	/**
	 * 不可抵扣金额
	 * @return
	 */
	public Double getFdNonDeductMoney() {
		return fdNonDeductMoney;
	}
	/**
	 * 不可抵扣金额
	 * @param fdNoTaxMoney
	 */
	public void setFdNonDeductMoney(Double fdNonDeductMoney) {
		this.fdNonDeductMoney = fdNonDeductMoney;
	}
	/**
	 * 发票金额
	 * @return
	 */
	public Double getFdInvoiceMoney() {
		return fdInvoiceMoney;
	}
	/**
	 * 发票金额
	 * @param fdNoTaxMoney
	 */
	public void setFdInvoiceMoney(Double fdInvoiceMoney) {
		this.fdInvoiceMoney = fdInvoiceMoney;
	}

	public String getFdTranDataId() {
		return fdTranDataId;
	}

	public void setFdTranDataId(String fdTranDataId) {
		this.fdTranDataId = fdTranDataId;
	}

}
