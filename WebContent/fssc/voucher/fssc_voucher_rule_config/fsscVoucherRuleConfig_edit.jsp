<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    
</style>
<script type="text/javascript">
    var formInitData = {

    };
    var messageInfo = {
        "fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig.null" : "${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig.null')}",
        "fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig.change" : "${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig.change')}"
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("formula.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/voucher/fssc_voucher_rule_config/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/voucher/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_IncludeFile("fsscVoucherRuleConfig_edit.js", "${LUI_ContextPath}/fssc/voucher/fssc_voucher_rule_config/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/voucher/fssc_voucher_rule_config/fsscVoucherRuleConfig.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscVoucherRuleConfigForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscVoucherRuleConfigForm, 'update');">
            </c:when>
            <c:when test="${fsscVoucherRuleConfigForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscVoucherRuleConfigForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
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
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig')}
                    </td>
                    <td width="35%">
                        <%-- 凭证模板设置--%>
                        <div id="_xform_fdVoucherModelConfigId" _xform_type="dialog">
                            <input type="hidden" name="fdVoucherModelConfigModelName" value="${fsscVoucherRuleConfigForm.fdVoucherModelConfigModelName}"/>
                            <xform:dialog propertyId="fdVoucherModelConfigId" propertyName="fdVoucherModelConfigName" required="true" subject="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig') }" showStatus="edit" style="width:95%;">
                                oldFdVoucherModelConfigId = $('[name=fdVoucherModelConfigId]').val();
                                oldFdVoucherModelConfigName = $('[name=fdVoucherModelConfigName]').val();
                                dialogSelect(false,'fssc_voucher_model_config_fdModel','fdVoucherModelConfigId','fdVoucherModelConfigName', null, selectFdVoucherModelConfigNameCallback);
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
                        <div id="_xform_fdCategoryId" style="display: none;" _xform_type="dialog">
                            <input type="hidden" name="fdCategoryModelName" value="${fsscVoucherRuleConfigForm.fdCategoryModelName}"/>
                            <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCategoryName') }" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'fssc_voucher_model_config_fdCategory','fdCategoryId','fdCategoryName',{'fdCategoryModelName':$('[name=fdCategoryModelName]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdRuleFormula')}
                    </td>
                    <td width="35%">
                        <%-- 生成规则--%>
                        <div id="_xform_fdRuleFormula" _xform_type="text">
                            <xform:dialog propertyId="fdRuleFormula" propertyName="fdRuleText" idValue="${fsscVoucherRuleConfigForm.fdRuleFormula}" nameValue="${fsscVoucherRuleConfigForm.fdRuleText}" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('fdRuleFormula','fdRuleText');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdModelNumberFormula')}
                    </td>
                    <td width="35%">
                        <%-- 来源单据编号--%>
                        <div id="_xform_fdModelNumber" _xform_type="text">
                            <xform:dialog propertyId="fdModelNumberFormula" propertyName="fdModelNumberText" idValue="${fsscVoucherRuleConfigForm.fdModelNumberFormula}" nameValue="${fsscVoucherRuleConfigForm.fdModelNumberText}" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('fdModelNumberFormula','fdModelNumberText');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCompany')}
                    </td>
                    <td width="35%">
                        <%-- 公司--%>
                        <div id="_xform_fdCompanyFlag" _xform_type="radio">
                            <xform:radio property="fdCompanyFlag" required="true" onValueChange="onChangFlag" htmlElementProperties="id='fdCompanyFlag'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                            </xform:radio>
                        </div>
                        <div id="_xform_fdCompanyId" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCompany')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                            </xform:dialog>
                            <span class="txtstrong">*</span>
                        </div>
                        <div id="_xform_fdCompanyFormula" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyFormula" propertyName="fdCompanyText" idValue="${fsscVoucherRuleConfigForm.fdCompanyFormula}" nameValue="${fsscVoucherRuleConfigForm.fdCompanyText}" showStatus="edit" style="width:95%;">
                                selectFormula('fdCompanyFormula','fdCompanyText');
                            </xform:dialog>
                            <span class="txtstrong">*</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherType')}
                    </td>
                    <td width="35%">
                        <%-- 凭证类型--%>
                        <div id="_xform_fdVoucherTypeFlag" _xform_type="radio">
                            <xform:radio property="fdVoucherTypeFlag" required="true" onValueChange="onChangFlag" htmlElementProperties="id='fdVoucherTypeFlag'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                            </xform:radio>
                        </div>
                        <div id="_xform_fdVoucherTypeId" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdVoucherTypeId" propertyName="fdVoucherTypeName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherType')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_voucher_type_getVoucherType','fdVoucherTypeId','fdVoucherTypeName');
                            </xform:dialog>
                            <span class="txtstrong">*</span>
                        </div>
                        <div id="_xform_fdVoucherTypeFormula" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdVoucherTypeFormula" propertyName="fdVoucherTypeText" idValue="${fsscVoucherRuleConfigForm.fdVoucherTypeFormula}" nameValue="${fsscVoucherRuleConfigForm.fdVoucherTypeText}" showStatus="edit" style="width:95%;">
                                selectFormula('fdVoucherTypeFormula','fdVoucherTypeText');
                            </xform:dialog>
                            <span class="txtstrong">*</span>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCurrency')}
                    </td>
                    <td width="35%">
                        <%-- 凭证货币--%>
                        <div id="_xform_fdCurrencyFlag" _xform_type="radio">
                            <xform:radio property="fdCurrencyFlag" required="true" onValueChange="onChangFlag" htmlElementProperties="id='fdCurrencyFlag'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                            </xform:radio>
                        </div>
                        <div id="_xform_fdCurrencyId" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCurrency')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                            </xform:dialog>
                            <span class="txtstrong">*</span>
                        </div>
                        <div id="_xform_fdCurrencyFormula" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdCurrencyFormula" propertyName="fdCurrencyText" idValue="${fsscVoucherRuleConfigForm.fdCurrencyFormula}" nameValue="${fsscVoucherRuleConfigForm.fdCurrencyText}" showStatus="edit" style="width:95%;">
                                selectFormula('fdCurrencyFormula','fdCurrencyText');
                            </xform:dialog>
                            <span class="txtstrong">*</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherDateFormula')}
                    </td>
                    <td width="35%">
                        <%-- 凭证日期--%>
                        <div id="_xform_fdVoucherDateFormula" _xform_type="text">
                            <xform:dialog propertyId="fdVoucherDateFormula" propertyName="fdVoucherDateText" idValue="${fsscVoucherRuleConfigForm.fdVoucherDateFormula}" nameValue="${fsscVoucherRuleConfigForm.fdVoucherDateText}" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('fdVoucherDateFormula','fdVoucherDateText');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdNumberFormula')}
                    </td>
                    <td width="35%">
                        <%-- 单据数--%>
                        <div id="_xform_fdNumberFormula" _xform_type="text">
                            <xform:dialog propertyId="fdNumberFormula" propertyName="fdNumberText" idValue="${fsscVoucherRuleConfigForm.fdNumberFormula}" nameValue="${fsscVoucherRuleConfigForm.fdNumberText}" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('fdNumberFormula','fdNumberText');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherTextFormula')}
                    </td>
                    <td width="35.0%">
                        <%-- 凭证抬头文本--%>
                        <div id="_xform_fdVoucherTextFormula" _xform_type="text">
                            <xform:dialog propertyId="fdVoucherTextFormula" propertyName="fdVoucherTextText" idValue="${fsscVoucherRuleConfigForm.fdVoucherTextFormula}" nameValue="${fsscVoucherRuleConfigForm.fdVoucherTextText}" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('fdVoucherTextFormula','fdVoucherTextText');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdPushType')}
                    </td>
                    <td width="35.0%">
                        <%-- 推送方式--%>
                        <div id="_xform_fdPushType" _xform_type="radio">
                            <xform:radio property="fdPushType" htmlElementProperties="id='fdPushType'" required="true" showStatus="edit">
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
                        <div id="_xform_fdOrderMakingPersonFlag" _xform_type="radio">
                            <xform:radio property="fdOrderMakingPersonFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdOrderMakingPersonFlag'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                            </xform:radio>
                        </div>
                        <div id="_xform_fdOrderMakingPersonId" style="display: none;" _xform_type="dialog">
                            <xform:address propertyId="fdOrderMakingPersonId" propertyName="fdOrderMakingPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdOrderMakingPerson')}" style="width:35%;" />
                            <span class="txtstrong">*</span>
                        </div>
                        <div id="_xform_fdOrderMakingPersonFormula" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdOrderMakingPersonFormula" propertyName="fdOrderMakingPersonText" idValue="${fsscVoucherRuleConfigForm.fdOrderMakingPersonFormula}" nameValue="${fsscVoucherRuleConfigForm.fdOrderMakingPersonText}" showStatus="edit" style="width:35%;">
                                selectFormula('fdOrderMakingPersonFormula','fdOrderMakingPersonText');
                            </xform:dialog>
                            <span class="txtstrong">*</span>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdMergeEntry')}
                    </td>
                    <td width="35%">
                        <%-- 合并分录--%>
                        <div id="_xform_fdMergeEntry" _xform_type="radio">
                            <xform:radio property="fdMergeEntry" htmlElementProperties="id='fdMergeEntry'" required="true" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_voucher_fd_merge_entry" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" width="100%">
                        <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                            <tr align="center" class="tr_normal_title">
                                <td style="width:20px;"></td>
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
                                <td style="width:80px;">
                                </td>
                            </tr>
                            <tr KMSS_IsReferRow="1" style="display:none;">
                                <td align="center">
                                    <input type='checkbox' name='DocList_Selected' />
                                </td>
                                <td align="center" KMSS_IsRowIndex="1">
                                    !{index}
                                </td>
                                <td align="center">
                                    <%-- 生成规则--%>
                                    <input type="hidden" name="fdDetail_Form[!{index}].fdId" value="" disabled="true" />
                                    <div id="_xform_fdDetail_Form[!{index}].fdRuleFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdRuleFormula" propertyName="fdDetail_Form[!{index}].fdRuleText" required="true" showStatus="edit" style="width:91%;">
                                            selectFormula('fdDetail_Form[*].fdRuleFormula','fdDetail_Form[*].fdRuleText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 是否与付款有关--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdIsPayment" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdIsPayment" htmlElementProperties="id='fdDetail_Form[!{index}].fdIsPayment'" showStatus="edit" required="true" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdIsPayment')}" value="false">
                                            <xform:enumsDataSource enumsType="common_yesno" />
                                        </xform:radio>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 借/贷--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdTypeFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdTypeFormula" propertyName="fdDetail_Form[!{index}].fdTypeText" required="true" showStatus="edit" style="width:91%;">
                                            selectFormula('fdDetail_Form[*].fdTypeFormula','fdDetail_Form[*].fdTypeText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 会计科目--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseAccountsFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseAccountsFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseAccountsFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseAccountsId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseAccountsId" propertyName="fdDetail_Form[!{index}].fdBaseAccountsName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseAccounts')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_accounts_fdMinLevel','fdDetail_Form[*].fdBaseAccountsId','fdDetail_Form[*].fdBaseAccountsName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseAccountsFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseAccountsFormula" propertyName="fdDetail_Form[!{index}].fdBaseAccountsText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseAccountsFormula','fdDetail_Form[*].fdBaseAccountsText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 成本中心--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCostCenterFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseCostCenterFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseCostCenterFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCostCenterId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCostCenterId" propertyName="fdDetail_Form[!{index}].fdBaseCostCenterName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCostCenter')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetail_Form[*].fdBaseCostCenterId','fdDetail_Form[*].fdBaseCostCenterName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCostCenterFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCostCenterFormula" propertyName="fdDetail_Form[!{index}].fdBaseCostCenterText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseCostCenterFormula','fdDetail_Form[*].fdBaseCostCenterText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 个人--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseErpPersonFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseErpPersonFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseErpPersonFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseErpPersonId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseErpPersonId" propertyName="fdDetail_Form[!{index}].fdBaseErpPersonName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseErpPerson')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_erp_person_fdERPPerson','fdDetail_Form[*].fdBaseErpPersonId','fdDetail_Form[*].fdBaseErpPersonName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseErpPersonFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseErpPersonFormula" propertyName="fdDetail_Form[!{index}].fdBaseErpPersonText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseErpPersonFormula','fdDetail_Form[*].fdBaseErpPersonText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 现金流量项目--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCashFlowFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseCashFlowFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseCashFlowFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCashFlowId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCashFlowId" propertyName="fdDetail_Form[!{index}].fdBaseCashFlowName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCashFlow')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetail_Form[*].fdBaseCashFlowId','fdDetail_Form[*].fdBaseCashFlowName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCashFlowFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCashFlowFormula" propertyName="fdDetail_Form[!{index}].fdBaseCashFlowText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseCashFlowFormula','fdDetail_Form[*].fdBaseCashFlowText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 客户--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCustomerFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseCustomerFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseCustomerFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCustomerId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCustomerId" propertyName="fdDetail_Form[!{index}].fdBaseCustomerName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCustomer')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_customer_getCustomer','fdDetail_Form[*].fdBaseCustomerId','fdDetail_Form[*].fdBaseCustomerName',{'fdType':'1'});
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCustomerFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCustomerFormula" propertyName="fdDetail_Form[!{index}].fdBaseCustomerText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseCustomerFormula','fdDetail_Form[*].fdBaseCustomerText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 供应商--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseSupplierFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseSupplierFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseSupplierFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseSupplierId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseSupplierId" propertyName="fdDetail_Form[!{index}].fdBaseSupplierName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseSupplier')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_supplier_getSupplier','fdDetail_Form[*].fdBaseSupplierId','fdDetail_Form[*].fdBaseSupplierName',{'fdType':'2'});
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseSupplierFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseSupplierFormula" propertyName="fdDetail_Form[!{index}].fdBaseSupplierText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseSupplierFormula','fdDetail_Form[*].fdBaseSupplierText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 核算项目--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseProjectFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseProjectFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseProjectFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseProjectId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseProjectId" propertyName="fdDetail_Form[!{index}].fdBaseProjectName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseProject')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_project_project','fdDetail_Form[*].fdBaseProjectId','fdDetail_Form[*].fdBaseProjectName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseProjectFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseProjectFormula" propertyName="fdDetail_Form[!{index}].fdBaseProjectText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseProjectFormula','fdDetail_Form[*].fdBaseProjectText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 银行--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayBankFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBasePayBankFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBasePayBankFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayBankId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBasePayBankId" propertyName="fdDetail_Form[!{index}].fdBasePayBankName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBasePayBank')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form[*].fdBasePayBankId','fdDetail_Form[*].fdBasePayBankName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayBankFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBasePayBankFormula" propertyName="fdDetail_Form[!{index}].fdBasePayBankText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBasePayBankFormula','fdDetail_Form[*].fdBasePayBankText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 合同编号--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdContractCodeFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdContractCodeFormula" propertyName="fdDetail_Form[!{index}].fdContractCodeText" showStatus="edit" style="width:91%;">
                                            selectFormula('fdDetail_Form[*].fdContractCodeFormula','fdDetail_Form[*].fdContractCodeText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 部门--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdDeptFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdDeptFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdDeptFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdDeptId" style="display: none;" _xform_type="address">
                                        <xform:address propertyId="fdDetail_Form[!{index}].fdDeptId" propertyName="fdDetail_Form[!{index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:90%;" />
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdDeptFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdDeptFormula" propertyName="fdDetail_Form[!{index}].fdDeptText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdDeptFormula','fdDetail_Form[*].fdDeptText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- WBS号--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseWbsFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseWbsFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseWbsFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseWbsId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseWbsId" propertyName="fdDetail_Form[!{index}].fdBaseWbsName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseWbs')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_wbs_fdWbs','fdDetail_Form[*].fdBaseWbsId','fdDetail_Form[*].fdBaseWbsName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseWbsFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseWbsFormula" propertyName="fdDetail_Form[!{index}].fdBaseWbsText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseWbsFormula','fdDetail_Form[*].fdBaseWbsText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 内部订单--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseInnerOrderFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseInnerOrderFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[!{index}].fdBaseInnerOrderFlag'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseInnerOrderId" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseInnerOrderId" propertyName="fdDetail_Form[!{index}].fdBaseInnerOrderName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseInnerOrder')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdDetail_Form[*].fdBaseInnerOrderId','fdDetail_Form[*].fdBaseInnerOrderName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseInnerOrderFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseInnerOrderFormula" propertyName="fdDetail_Form[!{index}].fdBaseInnerOrderText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseInnerOrderFormula','fdDetail_Form[*].fdBaseInnerOrderText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 金额--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdMoneyFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdMoneyFormula" propertyName="fdDetail_Form[!{index}].fdMoneyText" required="true" showStatus="edit" style="width:91%;">
                                            selectFormula('fdDetail_Form[*].fdMoneyFormula','fdDetail_Form[*].fdMoneyText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 摘要文本--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdVoucherTextFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdVoucherTextFormula" propertyName="fdDetail_Form[!{index}].fdVoucherTextText" required="true" showStatus="edit" style="width:91%;">
                                            selectFormula('fdDetail_Form[*].fdVoucherTextFormula','fdDetail_Form[*].fdVoucherTextText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                    </a>
                                </td>
                            </tr>
                            <c:forEach items="${fsscVoucherRuleConfigForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                                <tr KMSS_IsContentRow="1">
                                    <td align="center">
                                        <input type="checkbox" name="DocList_Selected" />
                                    </td>
                                    <td align="center">
                                        ${vstatus.index+1}
                                    </td>
                                    <td align="center">
                                        <%-- 生成规则--%>
                                        <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdRuleFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdRuleFormula" propertyName="fdDetail_Form[${vstatus.index}].fdRuleText" idValue="${fdDetail_FormItem.fdRuleFormula}" nameValue="${fdDetail_FormItem.fdRuleText}" required="true" showStatus="edit" style="width:91%;">
                                                selectFormula('fdDetail_Form[*].fdRuleFormula','fdDetail_Form[*].fdRuleText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 是否与付款有关--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdIsPayment" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdIsPayment" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdIsPayment'" showStatus="edit" required="true" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdIsPayment')}">
                                                <xform:enumsDataSource enumsType="common_yesno" />
                                            </xform:radio>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <%-- 借/贷--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdTypeFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdTypeFormula" propertyName="fdDetail_Form[${vstatus.index}].fdTypeText" idValue="${fdDetail_FormItem.fdTypeFormula}" nameValue="${fdDetail_FormItem.fdTypeText}" required="true" showStatus="edit" style="width:91%;">
                                                selectFormula('fdDetail_Form[*].fdTypeFormula','fdDetail_Form[*].fdTypeText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <%-- 会计科目--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseAccountsFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseAccountsFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseAccountsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseAccountsName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseAccounts')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_accounts_fdMinLevel','fdDetail_Form[*].fdBaseAccountsId','fdDetail_Form[*].fdBaseAccountsName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseAccountsFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseAccountsText" idValue="${fdDetail_FormItem.fdBaseAccountsFormula}" nameValue="${fdDetail_FormItem.fdBaseAccountsText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseAccountsFormula','fdDetail_Form[*].fdBaseAccountsText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 成本中心--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCostCenterFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseCostCenterFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseCostCenterFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCostCenterName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCostCenter')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetail_Form[*].fdBaseCostCenterId','fdDetail_Form[*].fdBaseCostCenterName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCostCenterFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCostCenterFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCostCenterText" idValue="${fdDetail_FormItem.fdBaseCostCenterFormula}" nameValue="${fdDetail_FormItem.fdBaseCostCenterText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseCostCenterFormula','fdDetail_Form[*].fdBaseCostCenterText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 个人--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseErpPersonFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseErpPersonFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseErpPersonFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseErpPersonName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseErpPerson')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_erp_person_fdERPPerson','fdDetail_Form[*].fdBaseErpPersonId','fdDetail_Form[*].fdBaseErpPersonName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseErpPersonFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseErpPersonFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseErpPersonText" idValue="${fdDetail_FormItem.fdBaseErpPersonFormula}" nameValue="${fdDetail_FormItem.fdBaseErpPersonText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseErpPersonFormula','fdDetail_Form[*].fdBaseErpPersonText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 现金流量项目--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCashFlowFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseCashFlowFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseCashFlowFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCashFlowName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCashFlow')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetail_Form[*].fdBaseCashFlowId','fdDetail_Form[*].fdBaseCashFlowName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCashFlowFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCashFlowFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCashFlowText" idValue="${fdDetail_FormItem.fdBaseCashFlowFormula}" nameValue="${fdDetail_FormItem.fdBaseCashFlowText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseCashFlowFormula','fdDetail_Form[*].fdBaseCashFlowText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 客户--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCustomerFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseCustomerFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseCustomerFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCustomerId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCustomerId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCustomerName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseCustomer')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_customer_getCustomer','fdDetail_Form[*].fdBaseCustomerId','fdDetail_Form[*].fdBaseCustomerName',{'fdType':'1'});
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCustomerFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCustomerFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCustomerText" idValue="${fdDetail_FormItem.fdBaseCustomerFormula}" nameValue="${fdDetail_FormItem.fdBaseCustomerText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseCustomerFormula','fdDetail_Form[*].fdBaseCustomerText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 供应商--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseSupplierFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseSupplierFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseSupplierFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseSupplierId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseSupplierId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseSupplierName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseSupplier')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_supplier_getSupplier','fdDetail_Form[*].fdBaseSupplierId','fdDetail_Form[*].fdBaseSupplierName',{'fdType':'2'});
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseSupplierFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseSupplierFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseSupplierText" idValue="${fdDetail_FormItem.fdBaseSupplierFormula}" nameValue="${fdDetail_FormItem.fdBaseSupplierText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseSupplierFormula','fdDetail_Form[*].fdBaseSupplierText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 核算项目--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseProjectFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseProjectFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseProjectFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseProjectId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseProjectId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseProjectName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseProject')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_project_project','fdDetail_Form[*].fdBaseProjectId','fdDetail_Form[*].fdBaseProjectName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseProjectFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseProjectFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseProjectText" idValue="${fdDetail_FormItem.fdBaseProjectFormula}" nameValue="${fdDetail_FormItem.fdBaseProjectText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseProjectFormula','fdDetail_Form[*].fdBaseProjectText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 银行--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBasePayBankFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBasePayBankFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBasePayBank')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form[*].fdBasePayBankId','fdDetail_Form[*].fdBasePayBankName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankText" idValue="${fdDetail_FormItem.fdBasePayBankFormula}" nameValue="${fdDetail_FormItem.fdBasePayBankText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBasePayBankFormula','fdDetail_Form[*].fdBasePayBankText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 合同编号--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdContractCodeFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdContractCodeFormula" propertyName="fdDetail_Form[${vstatus.index}].fdContractCodeText" idValue="${fdDetail_FormItem.fdContractCodeFormula}" nameValue="${fdDetail_FormItem.fdContractCodeText}" showStatus="edit" style="width:91%;">
                                                selectFormula('fdDetail_Form[*].fdContractCodeFormula','fdDetail_Form[*].fdContractCodeText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
	                                    <%-- 部门--%>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdDeptFlag" _xform_type="radio">
	                                        <xform:radio property="fdDetail_Form[${vstatus.index}].fdDeptFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdDeptFlag'" showStatus="edit">
	                                            <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
	                                        </xform:radio>
	                                    </div>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdDeptId" style="display: none;" _xform_type="address">
	                                        <xform:address propertyId="fdDetail_Form[${vstatus.index}].fdDeptId" propertyName="fdDetail_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:90%;" />
	                                    </div>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdDeptFormula" style="display: none;" _xform_type="dialog">
	                                        <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdDeptFormula" propertyName="fdDetail_Form[${vstatus.index}].fdDeptText" idValue="${fdDetail_FormItem.fdDeptFormula}" nameValue="${fdDetail_FormItem.fdDeptText}" showStatus="edit" style="width:95%;">
	                                            selectFormula('fdDetail_Form[*].fdDeptFormula','fdDetail_Form[*].fdDeptText');
	                                        </xform:dialog>
	                                    </div>
	                                </td>
                                    <td align="center">
                                            <%-- WBS号--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseWbsFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseWbsFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseWbsFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseWbsId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseWbsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseWbsName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseWbs')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_wbs_fdWbs','fdDetail_Form[*].fdBaseWbsId','fdDetail_Form[*].fdBaseWbsName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseWbsFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseWbsFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseWbsText" idValue="${fdDetail_FormItem.fdBaseWbsFormula}" nameValue="${fdDetail_FormItem.fdBaseWbsText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseWbsFormula','fdDetail_Form[*].fdBaseWbsText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 内部订单--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseInnerOrderFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderFlag" onValueChange="onChangFlag" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdBaseInnerOrderFlag'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_voucher_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderName" subject="${lfn:message('fssc-voucher:fsscVoucherRuleDetail.fdBaseInnerOrder')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdDetail_Form[*].fdBaseInnerOrderId','fdDetail_Form[*].fdBaseInnerOrderName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseInnerOrderFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderText" idValue="${fdDetail_FormItem.fdBaseInnerOrderFormula}" nameValue="${fdDetail_FormItem.fdBaseInnerOrderText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseInnerOrderFormula','fdDetail_Form[*].fdBaseInnerOrderText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 金额--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdMoneyFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdMoneyFormula" propertyName="fdDetail_Form[${vstatus.index}].fdMoneyText" idValue="${fdDetail_FormItem.fdMoneyFormula}" nameValue="${fdDetail_FormItem.fdMoneyText}" required="true" showStatus="edit" style="width:91%;">
                                                selectFormula('fdDetail_Form[*].fdMoneyFormula','fdDetail_Form[*].fdMoneyText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 摘要文本--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdVoucherTextFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdVoucherTextFormula" propertyName="fdDetail_Form[${vstatus.index}].fdVoucherTextText" idValue="${fdDetail_FormItem.fdVoucherTextFormula}" nameValue="${fdDetail_FormItem.fdVoucherTextText}" required="true" showStatus="edit" style="width:91%;">
                                                selectFormula('fdDetail_Form[*].fdVoucherTextFormula','fdDetail_Form[*].fdVoucherTextText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                            <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                        </a>
                                        &nbsp;
                                        <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                            <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr type="optRow" class="tr_normal_opt" invalidrow="true">
                                <td colspan="17">
                                    <a href="javascript:void(0);" onclick="DocList_AddRow();">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />${lfn:message('doclist.add')}
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />${lfn:message('doclist.moveup')}
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />${lfn:message('doclist.movedown')}
                                    </a>
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="fdDetail_Flag" value="1">
                        <script>
                            Com_IncludeFile("doclist.js");
                        </script>
                        <script>
                            DocList_Info.push('TABLE_DocList_fdDetail_Form');
                        </script>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 是否有效--%>
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" required="true" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
