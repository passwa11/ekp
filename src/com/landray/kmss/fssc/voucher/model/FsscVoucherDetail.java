package com.landray.kmss.fssc.voucher.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
  * 凭证明细
  */
public class FsscVoucherDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdType;

    private EopBasedataCostCenter fdBaseCostCenter;

    private EopBasedataErpPerson fdBaseErpPerson;

    private EopBasedataCashFlow fdBaseCashFlow;

    private Double fdMoney;

    private String fdVoucherText;

    private Integer fdOrder;

    private EopBasedataWbs fdBaseWbs;

    private EopBasedataInnerOrder fdBaseInnerOrder;

    private EopBasedataAccounts fdBaseAccounts;

    private EopBasedataSupplier fdBaseSupplier;

    private EopBasedataCustomer fdBaseCustomer;

    private EopBasedataProject fdBaseProject;

    private EopBasedataPayBank fdBasePayBank;
    
    private String fdContractCode;	//新增:合同编号
    
    private SysOrgElement fdDept;	//新增：部门

    private FsscVoucherMain docMain;

    @Override
    public Class<FsscVoucherDetailForm> getFormClass() {
        return FsscVoucherDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdBaseCostCenter.fdName", "fdBaseCostCenterName");
            toFormPropertyMap.put("fdBaseCostCenter.fdId", "fdBaseCostCenterId");
            toFormPropertyMap.put("fdBaseErpPerson.fdName", "fdBaseErpPersonName");
            toFormPropertyMap.put("fdBaseErpPerson.fdId", "fdBaseErpPersonId");
            toFormPropertyMap.put("fdBaseCashFlow.fdName", "fdBaseCashFlowName");
            toFormPropertyMap.put("fdBaseCashFlow.fdId", "fdBaseCashFlowId");
            toFormPropertyMap.put("fdBaseWbs.fdName", "fdBaseWbsName");
            toFormPropertyMap.put("fdBaseWbs.fdId", "fdBaseWbsId");
            toFormPropertyMap.put("fdBaseInnerOrder.fdName", "fdBaseInnerOrderName");
            toFormPropertyMap.put("fdBaseInnerOrder.fdId", "fdBaseInnerOrderId");
            toFormPropertyMap.put("fdBaseAccounts.fdCode", "fdBaseAccountsCode");
            toFormPropertyMap.put("fdBaseAccounts.fdName", "fdBaseAccountsName");
            toFormPropertyMap.put("fdBaseAccounts.fdId", "fdBaseAccountsId");
            toFormPropertyMap.put("fdBaseSupplier.fdName", "fdBaseSupplierName");
            toFormPropertyMap.put("fdBaseSupplier.fdId", "fdBaseSupplierId");
            toFormPropertyMap.put("fdBaseCustomer.fdName", "fdBaseCustomerName");
            toFormPropertyMap.put("fdBaseCustomer.fdId", "fdBaseCustomerId");
            toFormPropertyMap.put("fdBaseProject.fdName", "fdBaseProjectName");
            toFormPropertyMap.put("fdBaseProject.fdId", "fdBaseProjectId");
            toFormPropertyMap.put("fdBasePayBank.fdAccountName", "fdBasePayBankName");
            toFormPropertyMap.put("fdBasePayBank.fdId", "fdBasePayBankId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
            toFormPropertyMap.put("docMain.docNumber", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
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
     * 成本中心
     */
    public EopBasedataCostCenter getFdBaseCostCenter() {
        return this.fdBaseCostCenter;
    }

    /**
     * 成本中心
     */
    public void setFdBaseCostCenter(EopBasedataCostCenter fdBaseCostCenter) {
        this.fdBaseCostCenter = fdBaseCostCenter;
    }

    /**
     * 个人
     */
    public EopBasedataErpPerson getFdBaseErpPerson() {
        return fdBaseErpPerson;
    }
    /**
     * 个人
     */
    public void setFdBaseErpPerson(EopBasedataErpPerson fdBaseErpPerson) {
        this.fdBaseErpPerson = fdBaseErpPerson;
    }

    /**
     * 现金流量项目
     */
    public EopBasedataCashFlow getFdBaseCashFlow() {
        return this.fdBaseCashFlow;
    }

    /**
     * 现金流量项目
     */
    public void setFdBaseCashFlow(EopBasedataCashFlow fdBaseCashFlow) {
        this.fdBaseCashFlow = fdBaseCashFlow;
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
     * 摘要文本
     */
    public String getFdVoucherText() {
        return this.fdVoucherText;
    }

    /**
     * 摘要文本
     */
    public void setFdVoucherText(String fdVoucherText) {
        this.fdVoucherText = fdVoucherText;
    }

    /**
     * WBS号
     */
    public EopBasedataWbs getFdBaseWbs() {
        return this.fdBaseWbs;
    }

    /**
     * WBS号
     */
    public void setFdBaseWbs(EopBasedataWbs fdBaseWbs) {
        this.fdBaseWbs = fdBaseWbs;
    }

    /**
     * 内部订单
     */
    public EopBasedataInnerOrder getFdBaseInnerOrder() {
        return this.fdBaseInnerOrder;
    }

    /**
     * 内部订单
     */
    public void setFdBaseInnerOrder(EopBasedataInnerOrder fdBaseInnerOrder) {
        this.fdBaseInnerOrder = fdBaseInnerOrder;
    }

    /**
     * 会计科目
     */
    public EopBasedataAccounts getFdBaseAccounts() {
        return this.fdBaseAccounts;
    }

    /**
     * 会计科目
     */
    public void setFdBaseAccounts(EopBasedataAccounts fdBaseAccounts) {
        this.fdBaseAccounts = fdBaseAccounts;
    }

    /**
     * 供应商
     */
    public EopBasedataSupplier getFdBaseSupplier() {
        return this.fdBaseSupplier;
    }

    /**
     * 供应商
     */
    public void setFdBaseSupplier(EopBasedataSupplier fdBaseSupplier) {
        this.fdBaseSupplier = fdBaseSupplier;
    }

    /**
     * 客户
     */
    public EopBasedataCustomer getFdBaseCustomer() {
        return this.fdBaseCustomer;
    }

    /**
     * 客户
     */
    public void setFdBaseCustomer(EopBasedataCustomer fdBaseCustomer) {
        this.fdBaseCustomer = fdBaseCustomer;
    }

    /**
     * 核算项目
     */
    public EopBasedataProject getFdBaseProject() {
        return this.fdBaseProject;
    }

    /**
     * 核算项目
     */
    public void setFdBaseProject(EopBasedataProject fdBaseProject) {
        this.fdBaseProject = fdBaseProject;
    }

    /**
     * 主表单
     */
    public FsscVoucherMain getDocMain() {
        return this.docMain;
    }

    /**
     * 主表单
     */
    public void setDocMain(FsscVoucherMain docMain) {
        this.docMain = docMain;
    }
    /**
     * @return 项
     */
    public Integer getFdOrder() {
        return fdOrder;
    }

    /**
     * @param fdOrder
     *            项
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 银行
     */
    public EopBasedataPayBank getFdBasePayBank() {
        return fdBasePayBank;
    }

    /**
     * 银行
     */
    public void setFdBasePayBank(EopBasedataPayBank fdBasePayBank) {
        this.fdBasePayBank = fdBasePayBank;
    }
    
    /**
     * 合同编号
     */
    public String getFdContractCode() {
        return fdContractCode;
    }

    /**
     * 合同编号
     */
    public void setFdContractCode(String fdContractCode) {
        this.fdContractCode = fdContractCode;
    }
    
    /**
     * 部门
     */
    public SysOrgElement getFdDept() {
        return this.fdDept;
    }

    /**
     * 部门
     */
    public void setFdDept(SysOrgElement fdDept) {
        this.fdDept = fdDept;
    }
}
