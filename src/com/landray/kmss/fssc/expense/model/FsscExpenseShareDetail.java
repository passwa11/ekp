package com.landray.kmss.fssc.expense.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.expense.forms.FsscExpenseShareDetailForm;
import com.landray.kmss.util.DateUtil;

/**
  * 分摊明细
  */
public class FsscExpenseShareDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private EopBasedataProject fdProject;

    private EopBasedataCompany fdCompany;

    private EopBasedataCostCenter fdCostCenter;

    private EopBasedataCostCenter fdCostDept;

    private EopBasedataExpenseItem fdExpenseItem;

    private Date fdHappenDate;

    private Double fdMoney;

    private Double fdRate;

    private EopBasedataCurrency fdCurrency;

    private Double fdStandardMoney;

    private String fdRemark;

    @Override
    public Class<FsscExpenseShareDetailForm> getFormClass() {
        return FsscExpenseShareDetailForm.class;
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
            toFormPropertyMap.put("fdCostDept.fdName", "fdCostDeptName");
            toFormPropertyMap.put("fdCostDept.fdId", "fdCostDeptId");
            toFormPropertyMap.put("fdExpenseItem.fdName", "fdExpenseItemName");
            toFormPropertyMap.put("fdExpenseItem.fdId", "fdExpenseItemId");
            toFormPropertyMap.put("fdHappenDate", new ModelConvertor_Common("fdHappenDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
        }
        return toFormPropertyMap;
    }

    public EopBasedataProject getFdProject() {
		return fdProject;
	}

	public void setFdProject(EopBasedataProject fdProject) {
		this.fdProject = fdProject;
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
     * 核算部门
     */
    public EopBasedataCostCenter getFdCostDept() {
        return this.fdCostDept;
    }

    /**
     * 核算部门
     */
    public void setFdCostDept(EopBasedataCostCenter fdCostDept) {
        this.fdCostDept = fdCostDept;
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
     * 汇率
     */
    public Double getFdRate() {
        return this.fdRate;
    }

    /**
     * 汇率
     */
    public void setFdRate(Double fdRate) {
        this.fdRate = fdRate;
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
