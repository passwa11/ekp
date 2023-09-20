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
import com.landray.kmss.fssc.voucher.forms.FsscVoucherRuleDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
  * 凭证规则明细
  */
public class FsscVoucherRuleDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private FsscVoucherRuleConfig docMain;

    private String fdRuleFormula;

    private String fdRuleText;

    private String fdTypeFormula;

    private String fdTypeText;

    private String fdBaseAccountsFlag;

    private EopBasedataAccounts fdBaseAccounts;

    private String fdBaseAccountsFormula;

    private String fdBaseAccountsText;

    private String fdBaseCostCenterFlag;

    private EopBasedataCostCenter fdBaseCostCenter;

    private String fdBaseCostCenterFormula;

    private String fdBaseCostCenterText;

    private String fdBaseErpPersonFlag;

    private EopBasedataErpPerson fdBaseErpPerson;

    private String fdBaseErpPersonFormula;

    private String fdBaseErpPersonText;

    private String fdBaseCashFlowFlag;

    private EopBasedataCashFlow fdBaseCashFlow;

    private String fdBaseCashFlowFormula;

    private String fdBaseCashFlowText;

    private String fdBaseCustomerFlag;

    private EopBasedataCustomer fdBaseCustomer;

    private String fdBaseCustomerFormula;

    private String fdBaseCustomerText;

    private String fdBaseSupplierFlag;

    private EopBasedataSupplier fdBaseSupplier;

    private String fdBaseSupplierFormula;

    private String fdBaseSupplierText;

    private String fdBaseProjectFlag;

    private EopBasedataProject fdBaseProject;

    private String fdBaseProjectFormula;

    private String fdBaseProjectText;

    private String fdBasePayBankFlag;

    private EopBasedataPayBank fdBasePayBank;

    private String fdBasePayBankFormula;

    private String fdBasePayBankText;
    
    private String fdDeptFlag;	//新增:部门

    private SysOrgElement fdDept;

    private String fdDeptFormula;

    private String fdDeptText;

    private String fdBaseWbsFlag;

    private EopBasedataWbs fdBaseWbs;

    private String fdBaseWbsFormula;

    private String fdBaseWbsText;

    private String fdBaseInnerOrderFlag;

    private EopBasedataInnerOrder fdBaseInnerOrder;

    private String fdBaseInnerOrderFormula;

    private String fdBaseInnerOrderText;

    private String fdMoneyFormula;

    private String fdMoneyText;

    private String fdVoucherTextText;

    private String fdVoucherTextFormula;
    
    private String fdContractCodeText;	//新增:合同编号

    private String fdContractCodeFormula;	//新增:合同编号

    private Boolean fdIsPayment;

    @Override
    public Class<FsscVoucherRuleDetailForm> getFormClass() {
        return FsscVoucherRuleDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docMain.fdName", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
            toFormPropertyMap.put("fdBaseAccounts.fdName", "fdBaseAccountsName");
            toFormPropertyMap.put("fdBaseAccounts.fdId", "fdBaseAccountsId");
            toFormPropertyMap.put("fdBaseCostCenter.fdName", "fdBaseCostCenterName");
            toFormPropertyMap.put("fdBaseCostCenter.fdId", "fdBaseCostCenterId");
            toFormPropertyMap.put("fdBaseErpPerson.fdName", "fdBaseErpPersonName");
            toFormPropertyMap.put("fdBaseErpPerson.fdId", "fdBaseErpPersonId");
            toFormPropertyMap.put("fdBaseCashFlow.fdName", "fdBaseCashFlowName");
            toFormPropertyMap.put("fdBaseCashFlow.fdId", "fdBaseCashFlowId");
            toFormPropertyMap.put("fdBaseCustomer.fdName", "fdBaseCustomerName");
            toFormPropertyMap.put("fdBaseCustomer.fdId", "fdBaseCustomerId");
            toFormPropertyMap.put("fdBaseSupplier.fdName", "fdBaseSupplierName");
            toFormPropertyMap.put("fdBaseSupplier.fdId", "fdBaseSupplierId");
            toFormPropertyMap.put("fdBaseProject.fdName", "fdBaseProjectName");
            toFormPropertyMap.put("fdBaseProject.fdId", "fdBaseProjectId");
            toFormPropertyMap.put("fdBasePayBank.fdAccountName", "fdBasePayBankName");
            toFormPropertyMap.put("fdBasePayBank.fdId", "fdBasePayBankId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
            toFormPropertyMap.put("fdBaseWbs.fdName", "fdBaseWbsName");
            toFormPropertyMap.put("fdBaseWbs.fdId", "fdBaseWbsId");
            toFormPropertyMap.put("fdBaseInnerOrder.fdName", "fdBaseInnerOrderName");
            toFormPropertyMap.put("fdBaseInnerOrder.fdId", "fdBaseInnerOrderId");
        }
        return toFormPropertyMap;
    }

    /**
     * 生成规则
     */
    public String getFdRuleFormula() {
        return this.fdRuleFormula;
    }

    /**
     * 生成规则
     */
    public void setFdRuleFormula(String fdRuleFormula) {
        this.fdRuleFormula = fdRuleFormula;
    }

    /**
     * 生成规则text
     */
    public String getFdRuleText() {
        return this.fdRuleText;
    }

    /**
     * 生成规则text
     */
    public void setFdRuleText(String fdRuleText) {
        this.fdRuleText = fdRuleText;
    }

    /**
     * 借/贷
     */
    public String getFdTypeFormula() {
        return this.fdTypeFormula;
    }

    /**
     * 借/贷
     */
    public void setFdTypeFormula(String fdTypeFormula) {
        this.fdTypeFormula = fdTypeFormula;
    }

    /**
     * 借/贷
     */
    public String getFdTypeText() {
        return this.fdTypeText;
    }

    /**
     * 借/贷
     */
    public void setFdTypeText(String fdTypeText) {
        this.fdTypeText = fdTypeText;
    }

    /**
     * 会计科目flag
     */
    public String getFdBaseAccountsFlag() {
        return this.fdBaseAccountsFlag;
    }

    /**
     * 会计科目flag
     */
    public void setFdBaseAccountsFlag(String fdBaseAccountsFlag) {
        this.fdBaseAccountsFlag = fdBaseAccountsFlag;
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
     * 会计科目
     */
    public String getFdBaseAccountsFormula() {
        return this.fdBaseAccountsFormula;
    }

    /**
     * 会计科目
     */
    public void setFdBaseAccountsFormula(String fdBaseAccountsFormula) {
        this.fdBaseAccountsFormula = fdBaseAccountsFormula;
    }

    /**
     * 会计科目text
     */
    public String getFdBaseAccountsText() {
        return this.fdBaseAccountsText;
    }

    /**
     * 会计科目text
     */
    public void setFdBaseAccountsText(String fdBaseAccountsText) {
        this.fdBaseAccountsText = fdBaseAccountsText;
    }

    /**
     * 成本中心flag
     */
    public String getFdBaseCostCenterFlag() {
        return this.fdBaseCostCenterFlag;
    }

    /**
     * 成本中心flag
     */
    public void setFdBaseCostCenterFlag(String fdBaseCostCenterFlag) {
        this.fdBaseCostCenterFlag = fdBaseCostCenterFlag;
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
     * 成本中心
     */
    public String getFdBaseCostCenterFormula() {
        return this.fdBaseCostCenterFormula;
    }

    /**
     * 成本中心
     */
    public void setFdBaseCostCenterFormula(String fdBaseCostCenterFormula) {
        this.fdBaseCostCenterFormula = fdBaseCostCenterFormula;
    }

    /**
     * 成本中心text
     */
    public String getFdBaseCostCenterText() {
        return this.fdBaseCostCenterText;
    }

    /**
     * 成本中心text
     */
    public void setFdBaseCostCenterText(String fdBaseCostCenterText) {
        this.fdBaseCostCenterText = fdBaseCostCenterText;
    }

    /**
     * 个人flag
     */
    public String getFdBaseErpPersonFlag() {
        return this.fdBaseErpPersonFlag;
    }

    /**
     * 个人flag
     */
    public void setFdBaseErpPersonFlag(String fdBaseErpPersonFlag) {
        this.fdBaseErpPersonFlag = fdBaseErpPersonFlag;
    }

    /**
     * 个人
     */
    public EopBasedataErpPerson getFdBaseErpPerson() {
        return this.fdBaseErpPerson;
    }

    /**
     * 个人
     */
    public void setFdBaseErpPerson(EopBasedataErpPerson fdBaseErpPerson) {
        this.fdBaseErpPerson = fdBaseErpPerson;
    }

    /**
     * 个人
     */
    public String getFdBaseErpPersonFormula() {
        return this.fdBaseErpPersonFormula;
    }

    /**
     * 个人
     */
    public void setFdBaseErpPersonFormula(String fdBaseErpPersonFormula) {
        this.fdBaseErpPersonFormula = fdBaseErpPersonFormula;
    }

    /**
     * 个人text
     */
    public String getFdBaseErpPersonText() {
        return this.fdBaseErpPersonText;
    }

    /**
     * 个人text
     */
    public void setFdBaseErpPersonText(String fdBaseErpPersonText) {
        this.fdBaseErpPersonText = fdBaseErpPersonText;
    }

    /**
     * 现金流量项目flag
     */
    public String getFdBaseCashFlowFlag() {
        return this.fdBaseCashFlowFlag;
    }

    /**
     * 现金流量项目flag
     */
    public void setFdBaseCashFlowFlag(String fdBaseCashFlowFlag) {
        this.fdBaseCashFlowFlag = fdBaseCashFlowFlag;
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
     * 现金流量项目
     */
    public String getFdBaseCashFlowFormula() {
        return this.fdBaseCashFlowFormula;
    }

    /**
     * 现金流量项目
     */
    public void setFdBaseCashFlowFormula(String fdBaseCashFlowFormula) {
        this.fdBaseCashFlowFormula = fdBaseCashFlowFormula;
    }

    /**
     * 现金流量项目text
     */
    public String getFdBaseCashFlowText() {
        return this.fdBaseCashFlowText;
    }

    /**
     * 现金流量项目text
     */
    public void setFdBaseCashFlowText(String fdBaseCashFlowText) {
        this.fdBaseCashFlowText = fdBaseCashFlowText;
    }

    /**
     * 客户flag
     */
    public String getFdBaseCustomerFlag() {
        return this.fdBaseCustomerFlag;
    }

    /**
     * 客户flag
     */
    public void setFdBaseCustomerFlag(String fdBaseCustomerFlag) {
        this.fdBaseCustomerFlag = fdBaseCustomerFlag;
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
     * 客户
     */
    public String getFdBaseCustomerFormula() {
        return this.fdBaseCustomerFormula;
    }

    /**
     * 客户
     */
    public void setFdBaseCustomerFormula(String fdBaseCustomerFormula) {
        this.fdBaseCustomerFormula = fdBaseCustomerFormula;
    }

    /**
     * 客户text
     */
    public String getFdBaseCustomerText() {
        return this.fdBaseCustomerText;
    }

    /**
     * 客户text
     */
    public void setFdBaseCustomerText(String fdBaseCustomerText) {
        this.fdBaseCustomerText = fdBaseCustomerText;
    }

    /**
     * 供应商flag
     */
    public String getFdBaseSupplierFlag() {
        return this.fdBaseSupplierFlag;
    }

    /**
     * 供应商flag
     */
    public void setFdBaseSupplierFlag(String fdBaseSupplierFlag) {
        this.fdBaseSupplierFlag = fdBaseSupplierFlag;
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
     * 供应商
     */
    public String getFdBaseSupplierFormula() {
        return this.fdBaseSupplierFormula;
    }

    /**
     * 供应商
     */
    public void setFdBaseSupplierFormula(String fdBaseSupplierFormula) {
        this.fdBaseSupplierFormula = fdBaseSupplierFormula;
    }

    /**
     * 供应商text
     */
    public String getFdBaseSupplierText() {
        return this.fdBaseSupplierText;
    }

    /**
     * 供应商text
     */
    public void setFdBaseSupplierText(String fdBaseSupplierText) {
        this.fdBaseSupplierText = fdBaseSupplierText;
    }

    /**
     * 核算项目flag
     */
    public String getFdBaseProjectFlag() {
        return this.fdBaseProjectFlag;
    }

    /**
     * 核算项目flag
     */
    public void setFdBaseProjectFlag(String fdBaseProjectFlag) {
        this.fdBaseProjectFlag = fdBaseProjectFlag;
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
     * 核算项目
     */
    public String getFdBaseProjectFormula() {
        return this.fdBaseProjectFormula;
    }

    /**
     * 核算项目
     */
    public void setFdBaseProjectFormula(String fdBaseProjectFormula) {
        this.fdBaseProjectFormula = fdBaseProjectFormula;
    }

    /**
     * 核算项目text
     */
    public String getFdBaseProjectText() {
        return this.fdBaseProjectText;
    }

    /**
     * 核算项目text
     */
    public void setFdBaseProjectText(String fdBaseProjectText) {
        this.fdBaseProjectText = fdBaseProjectText;
    }

    /**
     * 银行flag
     */
    public String getFdBasePayBankFlag() {
        return this.fdBasePayBankFlag;
    }

    /**
     * 银行flag
     */
    public void setFdBasePayBankFlag(String fdBasePayBankFlag) {
        this.fdBasePayBankFlag = fdBasePayBankFlag;
    }

    /**
     * 银行
     */
    public EopBasedataPayBank getFdBasePayBank() {
        return this.fdBasePayBank;
    }

    /**
     * 银行
     */
    public void setFdBasePayBank(EopBasedataPayBank fdBasePayBank) {
        this.fdBasePayBank = fdBasePayBank;
    }

    /**
     * 银行
     */
    public String getFdBasePayBankFormula() {
        return this.fdBasePayBankFormula;
    }

    /**
     * 银行
     */
    public void setFdBasePayBankFormula(String fdBasePayBankFormula) {
        this.fdBasePayBankFormula = fdBasePayBankFormula;
    }

    /**
     * 银行text
     */
    public String getFdBasePayBankText() {
        return this.fdBasePayBankText;
    }

    /**
     * 银行text
     */
    public void setFdBasePayBankText(String fdBasePayBankText) {
        this.fdBasePayBankText = fdBasePayBankText;
    }
    
    /**
     * 部门flag
     * @return
     */
    public String getFdDeptFlag() {
        return this.fdDeptFlag;
    }

    /**
     * 部门flag
     * @param fdDeptFlag
     */
    public void setFdDeptFlag(String fdDeptFlag) {
        this.fdDeptFlag = fdDeptFlag;
    }

    /**
     * 部门
     * @return
     */
    public SysOrgElement getFdDept() {
        return this.fdDept;
    }

    /**
     * 部门
     * @param fdDept
     */
    public void setFdDept(SysOrgElement fdDept) {
        this.fdDept = fdDept;
    }

    /**
     * 部门
     * @return
     */
    public String getFdDeptFormula() {
        return this.fdDeptFormula;
    }

    /**
     * 部门
     * @param fdDeptFormula
     */
    public void setFdDeptFormula(String fdDeptFormula) {
        this.fdDeptFormula = fdDeptFormula;
    }

    /**
     * 部门text
     * @return
     */
    public String getFdDeptText() {
        return this.fdDeptText;
    }

    /**
     * 部门text
     * @param fdDeptText
     */
    public void setFdDeptText(String fdDeptText) {
        this.fdDeptText = fdDeptText;
    }

    /**
     * WBS号flag
     */
    public String getFdBaseWbsFlag() {
        return this.fdBaseWbsFlag;
    }

    /**
     * WBS号flag
     */
    public void setFdBaseWbsFlag(String fdBaseWbsFlag) {
        this.fdBaseWbsFlag = fdBaseWbsFlag;
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
     * WBS号
     */
    public String getFdBaseWbsFormula() {
        return this.fdBaseWbsFormula;
    }

    /**
     * WBS号
     */
    public void setFdBaseWbsFormula(String fdBaseWbsFormula) {
        this.fdBaseWbsFormula = fdBaseWbsFormula;
    }

    /**
     * WBS号text
     */
    public String getFdBaseWbsText() {
        return this.fdBaseWbsText;
    }

    /**
     * WBS号text
     */
    public void setFdBaseWbsText(String fdBaseWbsText) {
        this.fdBaseWbsText = fdBaseWbsText;
    }

    /**
     * 内部订单flag
     */
    public String getFdBaseInnerOrderFlag() {
        return this.fdBaseInnerOrderFlag;
    }

    /**
     * 内部订单flag
     */
    public void setFdBaseInnerOrderFlag(String fdBaseInnerOrderFlag) {
        this.fdBaseInnerOrderFlag = fdBaseInnerOrderFlag;
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
     * 内部订单
     */
    public String getFdBaseInnerOrderFormula() {
        return this.fdBaseInnerOrderFormula;
    }

    /**
     * 内部订单
     */
    public void setFdBaseInnerOrderFormula(String fdBaseInnerOrderFormula) {
        this.fdBaseInnerOrderFormula = fdBaseInnerOrderFormula;
    }

    /**
     * 内部订单text
     */
    public String getFdBaseInnerOrderText() {
        return this.fdBaseInnerOrderText;
    }

    /**
     * 内部订单text
     */
    public void setFdBaseInnerOrderText(String fdBaseInnerOrderText) {
        this.fdBaseInnerOrderText = fdBaseInnerOrderText;
    }

    /**
     * 金额
     */
    public String getFdMoneyFormula() {
        return this.fdMoneyFormula;
    }

    /**
     * 金额
     */
    public void setFdMoneyFormula(String fdMoneyFormula) {
        this.fdMoneyFormula = fdMoneyFormula;
    }

    /**
     * 金额text
     */
    public String getFdMoneyText() {
        return this.fdMoneyText;
    }

    /**
     * 金额text
     */
    public void setFdMoneyText(String fdMoneyText) {
        this.fdMoneyText = fdMoneyText;
    }

    /**
     * 摘要文本text
     */
    public String getFdVoucherTextText() {
        return this.fdVoucherTextText;
    }

    /**
     * 摘要文本text
     */
    public void setFdVoucherTextText(String fdVoucherTextText) {
        this.fdVoucherTextText = fdVoucherTextText;
    }

    /**
     * 摘要文本
     */
    public String getFdVoucherTextFormula() {
        return this.fdVoucherTextFormula;
    }

    /**
     * 摘要文本
     */
    public void setFdVoucherTextFormula(String fdVoucherTextFormula) {
        this.fdVoucherTextFormula = fdVoucherTextFormula;
    }
    
    /**
     * 合同编号text
     */
    public String getFdContractCodeText() {
        return this.fdContractCodeText;
    }

    /**
     * 合同编号text
     */
    public void setFdContractCodeText(String fdContractCodeText) {
        this.fdContractCodeText = fdContractCodeText;
    }

    /**
     * 合同编号
     */
    public String getFdContractCodeFormula() {
        return this.fdContractCodeFormula;
    }

    /**
     * 合同编号
     */
    public void setFdContractCodeFormula(String fdContractCodeFormula) {
        this.fdContractCodeFormula = fdContractCodeFormula;
    }

    /**
     * 是否与付款有关
     */
    public Boolean getFdIsPayment() {
        return this.fdIsPayment;
    }

    /**
     * 是否与付款有关
     */
    public void setFdIsPayment(Boolean fdIsPayment) {
        this.fdIsPayment = fdIsPayment;
    }

    public FsscVoucherRuleConfig getDocMain() {
        return docMain;
    }

    public void setDocMain(FsscVoucherRuleConfig docMain) {
        this.docMain = docMain;
    }
}
