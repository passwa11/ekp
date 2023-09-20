<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<style type="text/css">
    
    	.lui_paragraph_title{
    		font-size: 15px;
    		color: #15a4fa;
        	padding: 15px 0px 5px 0px;
    	}
    	.lui_paragraph_title span{
    		display: inline-block;
    		margin: -2px 5px 0px 0px;
    	}
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
        #TABLE_DocList_fdDetail_Form table td{
            "overflow":"hidden"
        }
    
</style>
<script type="text/javascript">
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/voucher/fssc_voucher_rule_config/fsscVoucherRuleConfig.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscVoucherRuleConfig.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/voucher/fssc_voucher_rule_config/fsscVoucherRuleConfig.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscVoucherRuleConfig.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-voucher:table.fsscVoucherRuleConfig') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdName')}
                </td>
                <td width="35%">
                    <%-- 凭证规则名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig')}
                </td>
                <td width="35%">
                    <%-- 凭证模板设置--%>
                    <div id="_xform_fdVoucherModelConfigId" _xform_type="dialog">
                        <xform:dialog propertyId="fdVoucherModelConfigId" propertyName="fdVoucherModelConfigName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'fssc_voucher_model_config_fdModel','fdVoucherModelConfigId','fdVoucherModelConfigName');
                        </xform:dialog>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCategoryName')}
                </td>
                <td width="35%">
                    <%-- 对应分类名称--%>
                    <div id="_xform_fdCategoryName" _xform_type="text">
                        <xform:text property="fdCategoryName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdRuleFormula')}
                </td>
                <td width="35%">
                    <%-- 生成规则--%>
                    <div id="_xform_fdRuleText" _xform_type="text">
                        <xform:text property="fdRuleText" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdModelNumberFormula')}
                </td>
                <td width="35%">
                    <%-- 来源单据编号--%>
                    <div id="_xform_fdModelNumberText" _xform_type="text">
                        <xform:text property="fdModelNumberText" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCompany')}
                </td>
                <td width="35%">
                    <%-- 公司--%>
                    <c:if test="${fsscVoucherRuleConfigForm.fdCompanyFlag == '1'}">
                        <div id="_xform_fdCompanyId"  _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                            </xform:dialog>
                        </div>
                    </c:if>
                    <c:if test="${fsscVoucherRuleConfigForm.fdCompanyFlag == '2'}">
                        <div id="_xform_fdCompanyFormula" _xform_type="dialog">
                            ${fsscVoucherRuleConfigForm.fdCompanyText }
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherType')}
                </td>
                <td width="35%">
                    <%-- 凭证类型--%>
                    <c:if test="${fsscVoucherRuleConfigForm.fdVoucherTypeFlag == '1'}">
                        <div id="_xform_fdVoucherTypeId" _xform_type="dialog">
                            <xform:dialog propertyId="fdVoucherTypeId" propertyName="fdVoucherTypeName" showStatus="view" style="width:95%;">
                                <xform:address propertyId="fdOrderMakingPersonId" propertyName="fdOrderMakingPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" subject="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdOrderMakingPerson')}" style="width:95%;" />
                            </xform:dialog>
                        </div>
                    </c:if>
                    <c:if test="${fsscVoucherRuleConfigForm.fdVoucherTypeFlag == '2'}">
                        <div id="_xform_fdVoucherTypeFormula" _xform_type="dialog">
                            ${fsscVoucherRuleConfigForm.fdVoucherTypeText }
                        </div>
                    </c:if>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCurrency')}
                </td>
                <td width="35%">
                    <%-- 凭证货币--%>
                    <c:if test="${fsscVoucherRuleConfigForm.fdCurrencyFlag == '1'}">
                        <div id="_xform_fdCurrencyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                            </xform:dialog>
                        </div>
                    </c:if>
                    <c:if test="${fsscVoucherRuleConfigForm.fdCurrencyFlag == '2'}">
                        <div id="_xform_fdCurrencyFormula" _xform_type="dialog">
                            ${fsscVoucherRuleConfigForm.fdCurrencyText }
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherDateFormula')}
                </td>
                <td width="35%">
                    <%-- 凭证日期--%>
                    <div id="_xform_fdVoucherDateText" _xform_type="text">
                        ${fsscVoucherRuleConfigForm.fdVoucherDateText }
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdNumberFormula')}
                </td>
                <td width="35%">
                    <%-- 单据数--%>
                    <div id="_xform_fdNumberText" _xform_type="text">
                        ${fsscVoucherRuleConfigForm.fdNumberText }
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherTextFormula')}
                </td>
                <td width="35.0%">
                    <%-- 凭证抬头文本--%>
                    <div id="_xform_fdVoucherTextText" _xform_type="text">
                        ${fsscVoucherRuleConfigForm.fdVoucherTextText }
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdPushType')}
                </td>
                <td width="35.0%">
                    <%-- 推送方式--%>
                    <div id="_xform_fdPushType" _xform_type="radio">
                        <xform:radio property="fdPushType" htmlElementProperties="id='fdPushType'" required="true" showStatus="view">
                            <xform:enumsDataSource enumsType="fssc_voucher_fd_push_type" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdOrderMakingPerson')}
                </td>
                <td width="35%">
                    <%-- 制单人--%>
                    <c:if test="${fsscVoucherRuleConfigForm.fdOrderMakingPersonFlag == '1'}">
                        <div id="_xform_fdCurrencyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdOrderMakingPersonId" propertyName="fdOrderMakingPersonName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdOrderMakingPersonId','fdOrderMakingPersonName');
                            </xform:dialog>
                        </div>
                    </c:if>
                    <c:if test="${fsscVoucherRuleConfigForm.fdOrderMakingPersonFlag == '2'}">
                        <div id="_xform_fdCurrencyFormula" _xform_type="dialog">
                            ${fsscVoucherRuleConfigForm.fdOrderMakingPersonText }
                        </div>
                    </c:if>
                </td>
                <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdMergeEntry')}
                    </td>
                    <td width="35%">
                        <%-- 合并分录--%>
                        <div id="_xform_fdMergeEntry" _xform_type="radio">
                            <xform:radio property="fdMergeEntry" htmlElementProperties="id='fdMergeEntry'" required="true" showStatus="view">
                                <xform:enumsDataSource enumsType="fssc_voucher_fd_merge_entry" />
                            </xform:radio>
                        </div>
                    </td>
            </tr>
            <tr>
                <td colspan="4" width="100%">
                    <table class="tb_normal" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true" style="table-layout:fixed;white-space: normal;word-break:break-all; width: 100%;">
                        <tr align="center" class="tr_normal_title">
                            <td style="width:40px;">
                                ${lfn:message('page.serial')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdRuleFormula')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdIsPayment')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdTypeFormula')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseAccounts')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCostCenter')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseErpPerson')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCashFlow')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCustomer')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseSupplier')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseProject')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBasePayBank')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdContractCodeFormula')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdDept')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseWbs')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseInnerOrder')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdMoneyFormula')}
                            </td>
                            <td style="width:6%;">
                                ${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdVoucherTextFormula')}
                            </td>
                        </tr>
                        <c:forEach items="${fsscVoucherRuleConfigForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                            <tr KMSS_IsContentRow="1">
                                <td align="center">
                                    ${vstatus.index+1}
                                </td>
                                <td align="center">
                                    <%-- 生成规则--%>
                                    <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdRuleText" _xform_type="text">
                                        ${fdDetail_FormItem.fdRuleText }
                                    </div>
                                </td>
                                <td align="center">
                                        <%-- 是否与付款有关--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdIsPayment" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[${vstatus.index}].fdIsPayment" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdIsPayment'" showStatus="view">
                                            <xform:enumsDataSource enumsType="common_yesno" />
                                        </xform:radio>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 借/贷--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdTypeText" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdTypeText" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 会计科目--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseAccountsFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseAccountsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseAccountsName" showStatus="view" style="width:95%;">
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseAccountsFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseAccountsText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 成本中心--%>
                                        <c:if test="${fdDetail_FormItem.fdBaseCostCenterFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCostCenterName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetail_Form[*].fdBaseCostCenterId','fdDetail_Form[*].fdBaseCostCenterName');
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseCostCenterFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCostCenterFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseCostCenterText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 个人--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseErpPersonFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseErpPersonName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_erp_person_fdERPPerson','fdDetail_Form[*].fdBaseErpPersonId','fdDetail_Form[*].fdBaseErpPersonName');
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseErpPersonFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseErpPersonFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseErpPersonText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 现金流量项目--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseCashFlowFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCashFlowName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetail_Form[*].fdBaseCashFlowId','fdDetail_Form[*].fdBaseCashFlowName');
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseCashFlowFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCashFlowFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseCashFlowText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 客户--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseCustomerFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCustomerId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCustomerId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCustomerName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_customer_getCustomer','fdDetail_Form[*].fdBaseCustomerId','fdDetail_Form[*].fdBaseCustomerName',{'fdType':'1'});
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseCustomerFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCustomerFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseCustomerText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 供应商--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseSupplierFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseSupplierId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseSupplierId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseSupplierName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_supplier_getSupplier','fdDetail_Form[*].fdBaseSupplierId','fdDetail_Form[*].fdBaseSupplierName',{'fdType':'2'});
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseSupplierFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseSupplierFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseSupplierText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 核算项目--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseProjectFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseProjectId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseProjectId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseProjectName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_project_project','fdDetail_Form[*].fdBaseProjectId','fdDetail_Form[*].fdBaseProjectName');
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseProjectFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseProjectFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseProjectText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 银行--%>
                                    <c:if test="${fdDetail_FormItem.fdBasePayBankFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form[*].fdBasePayBankId','fdDetail_Form[*].fdBasePayBankName');
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBasePayBankFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBasePayBankText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 合同编号--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdContractCodeText" _xform_type="text">
                                        ${fdDetail_FormItem.fdContractCodeText }
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 部门--%>
                                    <c:if test="${fdDetail_FormItem.fdDeptFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdDeptId" _xform_type="address">
                                            <xform:address propertyId="fdDetail_Form[${vstatus.index}].fdDeptId" propertyName="fdDetail_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="view" style="width:90%;" />
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdDeptFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdDeptFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdDeptText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- WBS号--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseWbsFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseWbsId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseWbsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseWbsName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_wbs_fdWbs','fdDetail_Form[*].fdBaseWbsId','fdDetail_Form[*].fdBaseWbsName');
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseWbsFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseWbsFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseWbsText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 内部订单--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseInnerOrderFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderName" showStatus="view" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdDetail_Form[*].fdBaseInnerOrderId','fdDetail_Form[*].fdBaseInnerOrderName');
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseInnerOrderFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseInnerOrderFormula" _xform_type="dialog">
                                            ${fdDetail_FormItem.fdBaseInnerOrderText }
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 金额--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdMoneyText" _xform_type="text">
                                        ${fdDetail_FormItem.fdMoneyText }
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 摘要文本--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdVoucherTextText" _xform_type="text">
                                        ${fdDetail_FormItem.fdVoucherTextText }
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdIsAvailable')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 是否有效--%>
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/fssc/voucher/fssc_voucher_rule_config/fsscVoucherRuleConfig.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
