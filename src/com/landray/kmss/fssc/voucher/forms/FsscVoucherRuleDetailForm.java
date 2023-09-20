package com.landray.kmss.fssc.voucher.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
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
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 凭证规则明细
  */
public class FsscVoucherRuleDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docMainId;

    private String docMainName;

    private String fdRuleFormula;

    private String fdRuleText;

    private String fdTypeFormula;

    private String fdTypeText;

    private String fdBaseAccountsFlag;

    private String fdBaseAccountsId;

    private String fdBaseAccountsName;

    private String fdBaseAccountsFormula;

    private String fdBaseAccountsText;

    private String fdBaseCostCenterFlag;

    private String fdBaseCostCenterId;

    private String fdBaseCostCenterName;

    private String fdBaseCostCenterFormula;

    private String fdBaseCostCenterText;

    private String fdBaseErpPersonFlag;

    private String fdBaseErpPersonId;

    private String fdBaseErpPersonName;

    private String fdBaseErpPersonFormula;

    private String fdBaseErpPersonText;

    private String fdBaseCashFlowFlag;

    private String fdBaseCashFlowId;

    private String fdBaseCashFlowName;

    private String fdBaseCashFlowFormula;

    private String fdBaseCashFlowText;

    private String fdBaseCustomerFlag;

    private String fdBaseCustomerId;

    private String fdBaseCustomerName;

    private String fdBaseCustomerFormula;

    private String fdBaseCustomerText;

    private String fdBaseSupplierFlag;

    private String fdBaseSupplierId;

    private String fdBaseSupplierName;

    private String fdBaseSupplierFormula;

    private String fdBaseSupplierText;

    private String fdBaseProjectFlag;

    private String fdBaseProjectId;

    private String fdBaseProjectName;

    private String fdBaseProjectFormula;

    private String fdBaseProjectText;

    private String fdBasePayBankFlag;

    private String fdBasePayBankId;

    private String fdBasePayBankName;

    private String fdBasePayBankFormula;

    private String fdBasePayBankText;
    
    private String fdDeptFlag;	//新增:部门

    private String fdDeptId;

    private String fdDeptName;

    private String fdDeptFormula;

    private String fdDeptText;

    private String fdBaseWbsFlag;

    private String fdBaseWbsId;

    private String fdBaseWbsName;

    private String fdBaseWbsFormula;

    private String fdBaseWbsText;

    private String fdBaseInnerOrderFlag;

    private String fdBaseInnerOrderId;

    private String fdBaseInnerOrderName;

    private String fdBaseInnerOrderFormula;

    private String fdBaseInnerOrderText;

    private String fdMoneyFormula;

    private String fdMoneyText;

    private String fdVoucherTextText;

    private String fdVoucherTextFormula;
    
    private String fdContractCodeText;	//新增:合同编号

    private String fdContractCodeFormula;	//新增:合同编号

    private String fdIsPayment;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docMainId = null;
        docMainName = null;
        fdRuleFormula = null;
        fdRuleText = null;
        fdTypeFormula = null;
        fdTypeText = null;
        fdBaseAccountsFlag = null;
        fdBaseAccountsId = null;
        fdBaseAccountsName = null;
        fdBaseAccountsFormula = null;
        fdBaseAccountsText = null;
        fdBaseCostCenterFlag = null;
        fdBaseCostCenterId = null;
        fdBaseCostCenterName = null;
        fdBaseCostCenterFormula = null;
        fdBaseCostCenterText = null;
        fdBaseErpPersonFlag = null;
        fdBaseErpPersonId = null;
        fdBaseErpPersonName = null;
        fdBaseErpPersonFormula = null;
        fdBaseErpPersonText = null;
        fdBaseCashFlowFlag = null;
        fdBaseCashFlowId = null;
        fdBaseCashFlowName = null;
        fdBaseCashFlowFormula = null;
        fdBaseCashFlowText = null;
        fdBaseCustomerFlag = null;
        fdBaseCustomerId = null;
        fdBaseCustomerName = null;
        fdBaseCustomerFormula = null;
        fdBaseCustomerText = null;
        fdBaseSupplierFlag = null;
        fdBaseSupplierId = null;
        fdBaseSupplierName = null;
        fdBaseSupplierFormula = null;
        fdBaseSupplierText = null;
        fdBaseProjectFlag = null;
        fdBaseProjectId = null;
        fdBaseProjectName = null;
        fdBaseProjectFormula = null;
        fdBaseProjectText = null;
        fdBasePayBankFlag = null;
        fdBasePayBankId = null;
        fdBasePayBankName = null;
        fdBasePayBankFormula = null;
        fdBasePayBankText = null;
        fdDeptFlag = null;
        fdDeptId = null;
        fdDeptName = null;
        fdDeptFormula = null;
        fdDeptText = null;
        fdBaseWbsFlag = null;
        fdBaseWbsId = null;
        fdBaseWbsName = null;
        fdBaseWbsFormula = null;
        fdBaseWbsText = null;
        fdBaseInnerOrderFlag = null;
        fdBaseInnerOrderId = null;
        fdBaseInnerOrderName = null;
        fdBaseInnerOrderFormula = null;
        fdBaseInnerOrderText = null;
        fdMoneyFormula = null;
        fdMoneyText = null;
        fdVoucherTextText = null;
        fdVoucherTextFormula = null;
        fdContractCodeText = null;
        fdContractCodeFormula = null;
        fdIsPayment = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscVoucherRuleDetail> getModelClass() {
        return FsscVoucherRuleDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscVoucherRuleConfig.class));
            toModelPropertyMap.put("fdBaseAccountsId", new FormConvertor_IDToModel("fdBaseAccounts", EopBasedataAccounts.class));
            toModelPropertyMap.put("fdBaseCostCenterId", new FormConvertor_IDToModel("fdBaseCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdBaseErpPersonId", new FormConvertor_IDToModel("fdBaseErpPerson", EopBasedataErpPerson.class));
            toModelPropertyMap.put("fdBaseCashFlowId", new FormConvertor_IDToModel("fdBaseCashFlow", EopBasedataCashFlow.class));
            toModelPropertyMap.put("fdBaseCustomerId", new FormConvertor_IDToModel("fdBaseCustomer", EopBasedataSupplier.class));
            toModelPropertyMap.put("fdBaseSupplierId", new FormConvertor_IDToModel("fdBaseSupplier", EopBasedataCustomer.class));
            toModelPropertyMap.put("fdBaseProjectId", new FormConvertor_IDToModel("fdBaseProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdBasePayBankId", new FormConvertor_IDToModel("fdBasePayBank", EopBasedataPayBank.class));
            toModelPropertyMap.put("fdDeptId", new FormConvertor_IDToModel("fdDept", SysOrgElement.class));
            toModelPropertyMap.put("fdBaseWbsId", new FormConvertor_IDToModel("fdBaseWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdBaseInnerOrderId", new FormConvertor_IDToModel("fdBaseInnerOrder", EopBasedataInnerOrder.class));
        }
        return toModelPropertyMap;
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
    public String getFdBaseAccountsId() {
        return this.fdBaseAccountsId;
    }

    /**
     * 会计科目
     */
    public void setFdBaseAccountsId(String fdBaseAccountsId) {
        this.fdBaseAccountsId = fdBaseAccountsId;
    }

    /**
     * 会计科目
     */
    public String getFdBaseAccountsName() {
        return this.fdBaseAccountsName;
    }

    /**
     * 会计科目
     */
    public void setFdBaseAccountsName(String fdBaseAccountsName) {
        this.fdBaseAccountsName = fdBaseAccountsName;
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
    public String getFdBaseCostCenterId() {
        return this.fdBaseCostCenterId;
    }

    /**
     * 成本中心
     */
    public void setFdBaseCostCenterId(String fdBaseCostCenterId) {
        this.fdBaseCostCenterId = fdBaseCostCenterId;
    }

    /**
     * 成本中心
     */
    public String getFdBaseCostCenterName() {
        return this.fdBaseCostCenterName;
    }

    /**
     * 成本中心
     */
    public void setFdBaseCostCenterName(String fdBaseCostCenterName) {
        this.fdBaseCostCenterName = fdBaseCostCenterName;
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
    public String getFdBaseErpPersonId() {
        return this.fdBaseErpPersonId;
    }

    /**
     * 个人
     */
    public void setFdBaseErpPersonId(String fdBaseErpPersonId) {
        this.fdBaseErpPersonId = fdBaseErpPersonId;
    }

    /**
     * 个人
     */
    public String getFdBaseErpPersonName() {
        return this.fdBaseErpPersonName;
    }

    /**
     * 个人
     */
    public void setFdBaseErpPersonName(String fdBaseErpPersonName) {
        this.fdBaseErpPersonName = fdBaseErpPersonName;
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
    public String getFdBaseCashFlowId() {
        return this.fdBaseCashFlowId;
    }

    /**
     * 现金流量项目
     */
    public void setFdBaseCashFlowId(String fdBaseCashFlowId) {
        this.fdBaseCashFlowId = fdBaseCashFlowId;
    }

    /**
     * 现金流量项目
     */
    public String getFdBaseCashFlowName() {
        return this.fdBaseCashFlowName;
    }

    /**
     * 现金流量项目
     */
    public void setFdBaseCashFlowName(String fdBaseCashFlowName) {
        this.fdBaseCashFlowName = fdBaseCashFlowName;
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
    public String getFdBaseCustomerId() {
        return this.fdBaseCustomerId;
    }

    /**
     * 客户
     */
    public void setFdBaseCustomerId(String fdBaseCustomerId) {
        this.fdBaseCustomerId = fdBaseCustomerId;
    }

    /**
     * 客户
     */
    public String getFdBaseCustomerName() {
        return this.fdBaseCustomerName;
    }

    /**
     * 客户
     */
    public void setFdBaseCustomerName(String fdBaseCustomerName) {
        this.fdBaseCustomerName = fdBaseCustomerName;
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
    public String getFdBaseSupplierId() {
        return this.fdBaseSupplierId;
    }

    /**
     * 供应商
     */
    public void setFdBaseSupplierId(String fdBaseSupplierId) {
        this.fdBaseSupplierId = fdBaseSupplierId;
    }

    /**
     * 供应商
     */
    public String getFdBaseSupplierName() {
        return this.fdBaseSupplierName;
    }

    /**
     * 供应商
     */
    public void setFdBaseSupplierName(String fdBaseSupplierName) {
        this.fdBaseSupplierName = fdBaseSupplierName;
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
    public String getFdBaseProjectId() {
        return this.fdBaseProjectId;
    }

    /**
     * 核算项目
     */
    public void setFdBaseProjectId(String fdBaseProjectId) {
        this.fdBaseProjectId = fdBaseProjectId;
    }

    /**
     * 核算项目
     */
    public String getFdBaseProjectName() {
        return this.fdBaseProjectName;
    }

    /**
     * 核算项目
     */
    public void setFdBaseProjectName(String fdBaseProjectName) {
        this.fdBaseProjectName = fdBaseProjectName;
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
    public String getFdBasePayBankId() {
        return this.fdBasePayBankId;
    }

    /**
     * 银行
     */
    public void setFdBasePayBankId(String fdBasePayBankId) {
        this.fdBasePayBankId = fdBasePayBankId;
    }

    /**
     * 银行
     */
    public String getFdBasePayBankName() {
        return this.fdBasePayBankName;
    }

    /**
     * 银行
     */
    public void setFdBasePayBankName(String fdBasePayBankName) {
        this.fdBasePayBankName = fdBasePayBankName;
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
     * 部门
     * @return
     */
    public String getFdDeptFlag() {
        return this.fdDeptFlag;
    }

    /**
     * 部门
     * @param fdDeptFlag
     */
    public void setFdDeptFlag(String fdDeptFlag) {
        this.fdDeptFlag = fdDeptFlag;
    }

    /**
     * 部门
     * @return
     */
    public String getFdDeptId() {
        return this.fdDeptId;
    }

    /**
     * 部门
     * @param fdDeptId
     */
    public void setFdDeptId(String fdDeptId) {
        this.fdDeptId = fdDeptId;
    }

    /**
     * 部门
     * @return
     */
    public String getFdDeptName() {
        return this.fdDeptName;
    }

    /**
     * 部门
     * @param fdDeptName
     */
    public void setFdDeptName(String fdDeptName) {
        this.fdDeptName = fdDeptName;
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
    public String getFdBaseWbsId() {
        return this.fdBaseWbsId;
    }

    /**
     * WBS号
     */
    public void setFdBaseWbsId(String fdBaseWbsId) {
        this.fdBaseWbsId = fdBaseWbsId;
    }

    /**
     * WBS号
     */
    public String getFdBaseWbsName() {
        return this.fdBaseWbsName;
    }

    /**
     * WBS号
     */
    public void setFdBaseWbsName(String fdBaseWbsName) {
        this.fdBaseWbsName = fdBaseWbsName;
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
    public String getFdBaseInnerOrderId() {
        return this.fdBaseInnerOrderId;
    }

    /**
     * 内部订单
     */
    public void setFdBaseInnerOrderId(String fdBaseInnerOrderId) {
        this.fdBaseInnerOrderId = fdBaseInnerOrderId;
    }

    /**
     * 内部订单
     */
    public String getFdBaseInnerOrderName() {
        return this.fdBaseInnerOrderName;
    }

    /**
     * 内部订单
     */
    public void setFdBaseInnerOrderName(String fdBaseInnerOrderName) {
        this.fdBaseInnerOrderName = fdBaseInnerOrderName;
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
    public String getFdIsPayment() {
        return this.fdIsPayment;
    }

    /**
     * 是否与付款有关
     */
    public void setFdIsPayment(String fdIsPayment) {
        this.fdIsPayment = fdIsPayment;
    }

    public String getDocMainId() {
        return docMainId;
    }

    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    public String getDocMainName() {
        return docMainName;
    }

    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }
}
