package com.landray.kmss.fssc.expense.forms;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareDetail;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 分摊明细
  */
public class FsscExpenseShareDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdProjectId;
    
    private String fdProjectName;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCostCenterId;

    private String fdCostCenterName;

    private String fdCostDeptId;

    private String fdCostDeptName;

    private String fdExpenseItemId;

    private String fdExpenseItemName;

    private String fdHappenDate;

    private String fdMoney;

    private String fdRate;

    private String fdCurrencyId;

    private String fdCurrencyName;

    private String fdStandardMoney;

    private String fdRemark;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdProjectId = null;
    	fdProjectName = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCostCenterId = null;
        fdCostCenterName = null;
        fdCostDeptId = null;
        fdCostDeptName = null;
        fdExpenseItemId = null;
        fdExpenseItemName = null;
        fdHappenDate = null;
        fdMoney = null;
        fdRate = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        fdStandardMoney = null;
        fdRemark = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseShareDetail> getModelClass() {
        return FsscExpenseShareDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdProjectId", new FormConvertor_IDToModel("fdProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdCostCenterId", new FormConvertor_IDToModel("fdCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdCostDeptId", new FormConvertor_IDToModel("fdCostDept", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdExpenseItemId", new FormConvertor_IDToModel("fdExpenseItem", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdHappenDate", new FormConvertor_Common("fdHappenDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
        }
        return toModelPropertyMap;
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
     * 核算部门
     */
    public String getFdCostDeptId() {
        return this.fdCostDeptId;
    }

    /**
     * 核算部门
     */
    public void setFdCostDeptId(String fdCostDeptId) {
        this.fdCostDeptId = fdCostDeptId;
    }

    /**
     * 核算部门
     */
    public String getFdCostDeptName() {
        return this.fdCostDeptName;
    }

    /**
     * 核算部门
     */
    public void setFdCostDeptName(String fdCostDeptName) {
        this.fdCostDeptName = fdCostDeptName;
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
     * 汇率
     */
    public String getFdRate() {
    	if(StringUtil.isNull(this.fdRate)){
    		return this.fdRate;
    	}
    	return new DecimalFormat("0.0#####").format(Double.valueOf(this.fdRate));
    }

    /**
     * 汇率
     */
    public void setFdRate(String fdRate) {
        this.fdRate = fdRate;
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
        this.fdStandardMoney =fdStandardMoney;
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
}
