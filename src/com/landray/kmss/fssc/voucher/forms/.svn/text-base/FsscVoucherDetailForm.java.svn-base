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
import com.landray.kmss.fssc.voucher.model.FsscVoucherDetail;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 凭证明细
  */
public class FsscVoucherDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdType;

    private String fdBaseCostCenterId;

    private String fdBaseCostCenterName;

    private String fdBaseErpPersonId;

    private String fdBaseErpPersonName;

    private String fdBaseCashFlowId;

    private String fdBaseCashFlowName;

    private String fdMoney;

    private String fdVoucherText;

    private String fdOrder;

    private String fdBaseWbsId;

    private String fdBaseWbsName;

    private String fdBaseInnerOrderId;

    private String fdBaseInnerOrderName;

    private String fdBaseAccountsId;

    private String fdBaseAccountsName;

    private String fdBaseAccountsCode;

    private String fdBaseSupplierId;

    private String fdBaseSupplierName;

    private String fdBaseCustomerId;

    private String fdBaseCustomerName;

    private String fdBaseProjectId;

    private String fdBaseProjectName;

    private String fdBasePayBankId;

    private String fdBasePayBankName;
    
    private String fdContractCode;	//新增:合同编号
    
    private String fdDeptId;	//新增:部门
    
    private String fdDeptName;

    private String docMainId;

    private String docMainName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdType = null;
        fdBaseCostCenterId = null;
        fdBaseCostCenterName = null;
        fdBaseErpPersonId = null;
        fdBaseErpPersonName = null;
        fdBaseCashFlowId = null;
        fdBaseCashFlowName = null;
        fdMoney = null;
        fdVoucherText = null;
        fdOrder = null;
        fdBaseWbsId = null;
        fdBaseWbsName = null;
        fdBaseInnerOrderId = null;
        fdBaseInnerOrderName = null;
        fdBaseAccountsId = null;
        fdBaseAccountsName = null;
        fdBaseAccountsCode = null;
        fdBaseSupplierId = null;
        fdBaseSupplierName = null;
        fdBaseCustomerId = null;
        fdBaseCustomerName = null;
        fdBaseProjectId = null;
        fdBaseProjectName = null;
        fdBasePayBankId = null;
        fdBasePayBankName = null;
        fdContractCode = null;
        fdDeptId = null;
        fdDeptName = null;
        docMainId = null;
        docMainName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscVoucherDetail> getModelClass() {
        return FsscVoucherDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdBaseCostCenterId", new FormConvertor_IDToModel("fdBaseCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdBaseErpPersonId", new FormConvertor_IDToModel("fdBaseErpPerson", EopBasedataErpPerson.class));
            toModelPropertyMap.put("fdBaseCashFlowId", new FormConvertor_IDToModel("fdBaseCashFlow", EopBasedataCashFlow.class));
            toModelPropertyMap.put("fdBaseWbsId", new FormConvertor_IDToModel("fdBaseWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdBaseInnerOrderId", new FormConvertor_IDToModel("fdBaseInnerOrder", EopBasedataInnerOrder.class));
            toModelPropertyMap.put("fdBaseAccountsId", new FormConvertor_IDToModel("fdBaseAccounts", EopBasedataAccounts.class));
            toModelPropertyMap.put("fdBaseSupplierId", new FormConvertor_IDToModel("fdBaseSupplier", EopBasedataSupplier.class));
            toModelPropertyMap.put("fdBaseCustomerId", new FormConvertor_IDToModel("fdBaseCustomer", EopBasedataCustomer.class));
            toModelPropertyMap.put("fdBaseProjectId", new FormConvertor_IDToModel("fdBaseProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdBasePayBankId", new FormConvertor_IDToModel("fdBasePayBank", EopBasedataPayBank.class));
            toModelPropertyMap.put("fdDeptId", new FormConvertor_IDToModel("fdDept", SysOrgElement.class));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscVoucherMain.class));
        }
        return toModelPropertyMap;
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
     * 个人
     */
    public String getFdBaseErpPersonId() {
        return fdBaseErpPersonId;
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
        return fdBaseErpPersonName;
    }
    /**
     * 个人
     */
    public void setFdBaseErpPersonName(String fdBaseErpPersonName) {
        this.fdBaseErpPersonName = fdBaseErpPersonName;
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
    public String getFdBaseAccountsCode() {
        return this.fdBaseAccountsCode;
    }

    /**
     * 会计科目
     */
    public void setFdBaseAccountsCode(String fdBaseAccountsCode) {
        this.fdBaseAccountsCode = fdBaseAccountsCode;
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
     * 主表单
     */
    public String getDocMainId() {
        return this.docMainId;
    }

    /**
     * 主表单
     */
    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    /**
     * 主表单
     */
    public String getDocMainName() {
        return this.docMainName;
    }

    /**
     * 主表单
     */
    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }
    /**
     * @return 项
     */
    public String getFdOrder() {
        return fdOrder;
    }

    /**
     * @param fdOrder
     *            项
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
    }
    /**
     * 银行
     */
    public String getFdBasePayBankId() {
        return fdBasePayBankId;
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
        return fdBasePayBankName;
    }
    /**
     * 银行
     */
    public void setFdBasePayBankName(String fdBasePayBankName) {
        this.fdBasePayBankName = fdBasePayBankName;
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
     * @return
     */
    public String getFdDeptId() {
        return fdDeptId;
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
        return fdDeptName;
    }
    
    /**
     * 部门
     * @param fdDeptName
     */
    public void setFdDeptName(String fdDeptName) {
        this.fdDeptName = fdDeptName;
    }
}
