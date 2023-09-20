<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
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
        "fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig.null" : "${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig.null')}",
        "fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig.change" : "${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig.change')}"
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("formula.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/cashier/fssc_cashier_rule_config/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/cashier/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_IncludeFile("fsscCashierRuleConfig_edit.js", "${LUI_ContextPath}/fssc/cashier/fssc_cashier_rule_config/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscCashierRuleConfigForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscCashierRuleConfigForm, 'update');">
            </c:when>
            <c:when test="${fsscCashierRuleConfigForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscCashierRuleConfigForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-cashier:table.fsscCashierRuleConfig') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 出纳规则名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig')}
                    </td>
                    <td width="35%">
                        <%-- 出纳规则模板配置--%>
                        <div id="_xform_fdCashierModelConfigId" _xform_type="dialog">
                            <input type="hidden" name="fdCashierModelConfigModelName" value="${fsscCashierRuleConfigForm.fdCashierModelConfigModelName}"/>
                            <xform:dialog propertyId="fdCashierModelConfigId" propertyName="fdCashierModelConfigName" subject="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig')}" showStatus="edit" style="width:95%;">
                                oldFdCashierModelConfigId = $('[name=fdCashierModelConfigId]').val();
                                oldFdCashierModelConfigName = $('[name=fdCashierModelConfigName]').val();
                                dialogSelect(false,'fssc_cashier_model_config_fdCashierPaymentModelConfig','fdCashierModelConfigId','fdCashierModelConfigName', null, selectFdCashierModelConfigNameCallback);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCategoryName')}
                    </td>
                    <td width="35%">
                        <%-- 对应分类名称--%>
                        <div id="_xform_fdCategoryId" style="display: none;" _xform_type="dialog">
                            <input type="hidden" name="fdCategoryModelName" value="${fsscCashierRuleConfigForm.fdCategoryModelName}"/>
                            <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" subject="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCategoryName')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'fssc_cashier_model_config_fdCategory','fdCategoryId','fdCategoryName',{'fdCategoryModelName':$('[name=fdCategoryModelName]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdRuleFormula')}
                    </td>
                    <td width="35%">
                        <%-- 生成规则--%>
                        <div id="_xform_fdRuleFormula" _xform_type="text">
                            <xform:dialog propertyId="fdRuleFormula" propertyName="fdRuleText" idValue="${fsscCashierRuleConfigForm.fdRuleFormula}" nameValue="${fsscCashierRuleConfigForm.fdRuleText}" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('fdRuleFormula','fdRuleText');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdModelNumberFormula')}
                    </td>
                    <td width="35%">
                        <%-- 来源单据编号--%>
                        <div id="_xform_fdModelNumberFormula" _xform_type="text">
                            <xform:dialog propertyId="fdModelNumberFormula" propertyName="fdModelNumberText" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('fdModelNumberFormula','fdModelNumberText');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCompanyFlag')}
                    </td>
                    <td width="35%">
                        <%-- 公司--%>
                        <div id="_xform_fdCompanyFlag" _xform_type="radio">
                            <xform:radio property="fdCompanyFlag" required="true" onValueChange="onChangFlag" htmlElementProperties="id='fdCompanyFlag'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                            </xform:radio>
                        </div>
                        <div id="_xform_fdCompanyId" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" subject="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCompanyFlag')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                            </xform:dialog>
                        </div>
                        <div id="_xform_fdCompanyFormula" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyFormula" propertyName="fdCompanyText" idValue="${fsscCashierRuleConfigForm.fdCompanyFormula}" nameValue="${fsscCashierRuleConfigForm.fdCompanyText}" showStatus="edit" style="width:95%;">
                                selectFormula('fdCompanyFormula','fdCompanyText');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdBaseCurrencyFormula')}
                    </td>
                    <td >
                        <%-- 货币--%>
                        <div id="_xform_fdBaseCurrencyFlag" _xform_type="radio">
                            <xform:radio property="fdBaseCurrencyFlag" required="true" onValueChange="onChangFlag" htmlElementProperties="id='fdBaseCurrencyFlag'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                            </xform:radio>
                        </div>
                        <div id="_xform_fdBaseCurrencyId" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdBaseCurrencyId" propertyName="fdBaseCurrencyName" subject="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdBaseCurrencyFormula')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdBaseCurrencyId','fdBaseCurrencyName');
                            </xform:dialog>
                        </div>
                        <div id="_xform_fdBaseCurrencyFormula" style="display: none;" _xform_type="dialog">
                            <xform:dialog propertyId="fdBaseCurrencyFormula" propertyName="fdBaseCurrencyText" idValue="${fsscCashierRuleConfigForm.fdBaseCurrencyFormula}" nameValue="${fsscCashierRuleConfigForm.fdBaseCurrencyText}" showStatus="edit" style="width:95%;">
                                selectFormula('fdBaseCurrencyFormula','fdBaseCurrencyText');
                            </xform:dialog>
                        </div>
                    </td>
                     <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdPaymentTypeFormula')}
                    </td>
                    <td >
                        <%-- 付款类型--%>
                        <div id="_xform_fdPaymentTypeFormula" _xform_type="radio">
                            <xform:radio property="fdPaymentTypeFormula" required="true" onValueChange="onChangFlag" htmlElementProperties="id='fdPaymentTypeFormula'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_cashier_payment_type"/>
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                	<td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdMaxMoney')}
                    </td>
                    <td width="35%" colspan="3">
                        <%-- 金额拆分明细上限--%>
                        <div id="_xform_fdMaxMoney" _xform_type="text">
                            <input type="text" class="inputsgl" name="fdMaxMoney" value="<kmss:showNumber value="${fsscCashierRuleConfigForm.fdMaxMoney }" pattern="0.00"/>" showStatus="edit" style="width:95%;" validate="currency-dollar" />
                        </div>
                    </td>
                </tr>
                 <tr hidden="true">
                 	 <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.docSubjectFormula')}
                    </td>
                    <td width="35%" >
                        <%-- 来源单据标题--%>
                        <div id="_xform_docSubjectFormula" _xform_type="text">
                            <xform:dialog propertyId="docSubjectFormula" propertyName="docSubjectText" idValue="$docSubject$" nameValue="$标题$" required="true" showStatus="edit" style="width:95%;">
                                selectFormula('docSubjectFormula','docSubjectText');
                            </xform:dialog>
                        </div>
                    </td>
                    <td colspan="2">
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
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdRuleFormula')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBasePayWay')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBasePayBank')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBaseCurrencyFormula')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdRateFormula')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdPayeeNameFormula')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdPayeeAccountFormula')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdPayeeBankNameFormula')}
                                </td>
                                 <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdPayeeBankCity')}
                                </td>
                                <fssc:checkUseBank fdBank="BOC">
                                 <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdPayeeBankNoFormula')}
                                </td>
                                </fssc:checkUseBank>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdDetailModelIdFormula')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdDetailModelNameFormula')}
                                </td>
                                <fssc:checkUseBank fdBank="CBS">
                                    <td>
                                        ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdSameDiffCity')}
                                    </td>
                                </fssc:checkUseBank>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdMoneyFormula')}
                                </td>
                                 <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdPlanPaymentDateFormula')}
                                </td>
                                <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdRemarksText')}
                                </td>
                                 <fssc:checkUseBank fdBank="CMB">
                                 <td title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCStlchnMemo')} ">
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdCStlchnText')} 
                                </td>
                                </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CMInt">
                                    <td title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCStlchnMemo1')} ">
                                            ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdCStlchnText')}
                                    </td>
                                </fssc:checkUseBank>
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
                                    <%-- 付款方式--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayWayFlag" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBasePayWayFlag" onValueChange="onChangFlag" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayWayId" style="display: none;"  _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBasePayWayId" propertyName="fdDetail_Form[!{index}].fdBasePayWayName" subject="${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBasePayWay')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_pay_way_fdPayWay','fdDetail_Form[*].fdBasePayWayId','fdDetail_Form[*].fdBasePayWayName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayWayFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBasePayWayFormula" propertyName="fdDetail_Form[!{index}].fdBasePayWayText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBasePayWayFormula','fdDetail_Form[*].fdBasePayWayText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 付款银行--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayBankFlag" _xform_type="dialog">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBasePayBankFlag" onValueChange="onChangFlag" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBasePayBankId" style="display: none;"  _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBasePayBankId" propertyName="fdDetail_Form[!{index}].fdBasePayBankName" subject="${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBasePayBank')}" showStatus="edit" style="width:95%;">
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
                                    <%-- 货币--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCurrencyFlag" _xform_type="dialog">
                                        <xform:radio property="fdDetail_Form[!{index}].fdBaseCurrencyFlag" onValueChange="onChangFlag" showStatus="edit">
                                            <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                                        </xform:radio>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCurrencyId" style="display: none;"  _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCurrencyId" propertyName="fdDetail_Form[!{index}].fdBaseCurrencyName" subject="${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBaseCurrencyFormula')}" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetail_Form[*].fdBaseCurrencyId','fdDetail_Form[*].fdBaseCurrencyName');
                                        </xform:dialog>
                                    </div>
                                    <div id="_xform_fdDetail_Form[!{index}].fdBaseCurrencyFormula" style="display: none;" _xform_type="dialog">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCurrencyFormula" propertyName="fdDetail_Form[!{index}].fdBaseCurrencyText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdBaseCurrencyFormula','fdDetail_Form[*].fdBaseCurrencyText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 汇率--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdRateFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdRateFormula" propertyName="fdDetail_Form[!{index}].fdRateText" required="true" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdRateFormula','fdDetail_Form[*].fdRateText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 收款人名称--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdPayeeNameFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdPayeeNameFormula" propertyName="fdDetail_Form[!{index}].fdPayeeNameText" required="true" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdPayeeNameFormula','fdDetail_Form[*].fdPayeeNameText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 收款人账号--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdPayeeAccountFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdPayeeAccountFormula" propertyName="fdDetail_Form[!{index}].fdPayeeAccountText" required="true" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdPayeeAccountFormula','fdDetail_Form[*].fdPayeeAccountText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 收款行名称--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdPayeeBankNameFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdPayeeBankNameFormula" propertyName="fdDetail_Form[!{index}].fdPayeeBankNameText" required="true" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdPayeeBankNameFormula','fdDetail_Form[*].fdPayeeBankNameText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                 <td align="center">
                                    <%-- 收款账户开户地--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdPayeeBankCityFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdPayeeBankCityFormula" propertyName="fdDetail_Form[!{index}].fdPayeeBankCity" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdPayeeBankCityFormula','fdDetail_Form[*].fdPayeeBankCity');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <fssc:checkUseBank fdBank="BOC">
                                	<td align="center">
                                    <%-- 银联号--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdPayeeBankNoFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdPayeeBankNoFormula" propertyName="fdDetail_Form[!{index}].fdPayeeBankNoText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdPayeeBankNoFormula','fdDetail_Form[*].fdPayeeBankNoText');
                                        </xform:dialog>
                                    </div>
                                	</td>
                                </fssc:checkUseBank>
                                
                                <td align="center">
                                    <%-- 关联明细id--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdDetailModelIdFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdDetailModelIdFormula" propertyName="fdDetail_Form[!{index}].fdDetailModelIdText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdDetailModelIdFormula','fdDetail_Form[*].fdDetailModelIdText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 关联明细name--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdDetailModelNameFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdDetailModelNameFormula" propertyName="fdDetail_Form[!{index}].fdDetailModelNameText" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdDetailModelNameFormula','fdDetail_Form[*].fdDetailModelNameText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <fssc:checkUseBank fdBank="CBS">
                                    <td align="center">
                                            <%-- 是否同城--%>
                                        <div id="_xform_fdDetail_Form[!{index}].fdSameDiffCity" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[!{index}].fdSameDiffCityFormula" propertyName="fdDetail_Form[!{index}].fdSameDiffCityText" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdSameDiffCityFormula','fdDetail_Form[*].fdSameDiffCityText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                <td align="center">
                                    <%-- 付款金额--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdMoneyFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdMoneyFormula" propertyName="fdDetail_Form[!{index}].fdMoneyText" required="true" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdMoneyFormula','fdDetail_Form[*].fdMoneyText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 计划付款时间--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdPlanPaymentDateFormula" _xform_type="text">
                                        <xform:text property="fdDetail_Form[!{index}].fdPlanPaymentDateFormula" style="width:85%;"></xform:text>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 摘要--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdRemarksFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdRemarksFormula" propertyName="fdDetail_Form[!{index}].fdRemarksText" required="true" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdRemarksFormula','fdDetail_Form[*].fdRemarksText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                <fssc:checkUseBank fdBank="CMB">
                                <td align="center" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCStlchnMemo')} ">
                                    <%-- 结算方式--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdCStlchnFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[!{index}].fdCStlchnFormula" propertyName="fdDetail_Form[!{index}].fdCStlchnText"  showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdCStlchnFormula','fdDetail_Form[*].fdCStlchnText');
                                        </xform:dialog>
                                    </div>
                                </td>
                                </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CMInt">
                                    <td align="center" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCStlchnMemo1')} ">
                                            <%-- 结算方式--%>
                                        <div id="_xform_fdDetail_Form[!{index}].fdCStlchnFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[!{index}].fdCStlchnFormula" propertyName="fdDetail_Form[!{index}].fdCStlchnText"  showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdCStlchnFormula','fdDetail_Form[*].fdCStlchnText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
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
                            <c:forEach items="${fsscCashierRuleConfigForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
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
                                        <%-- 付款方式--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayWayFlag" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBasePayWayFlag" onValueChange="onChangFlag" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayWayId" style="display: none;"  _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayWayId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayWayName" subject="${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBasePayWay')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_pay_way_fdPayWay','fdDetail_Form[*].fdBasePayWayId','fdDetail_Form[*].fdBasePayWayName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayWayFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayWayFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayWayText" idValue="${fdDetail_FormItem.fdBasePayWayFormula}" nameValue="${fdDetail_FormItem.fdBasePayWayText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBasePayWayFormula','fdDetail_Form[*].fdBasePayWayText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <%-- 付款银行--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankFlag" _xform_type="dialog">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBasePayBankFlag" onValueChange="onChangFlag" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankId" style="display: none;"  _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankName" subject="${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBasePayBank')}" showStatus="edit" style="width:95%;">
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
                                            <%-- 货币--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCurrencyFlag" _xform_type="dialog">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdBaseCurrencyFlag" onValueChange="onChangFlag" showStatus="edit">
                                                <xform:enumsDataSource enumsType="fssc_cashier_operation_type"/>
                                            </xform:radio>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCurrencyId" style="display: none;"  _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCurrencyId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCurrencyName" subject="${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdBaseCurrencyFormula')}" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetail_Form[*].fdBaseCurrencyId','fdDetail_Form[*].fdBaseCurrencyName');
                                            </xform:dialog>
                                        </div>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCurrencyFormula" style="display: none;" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCurrencyFormula" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCurrencyText" idValue="${fdDetail_FormItem.fdBaseCurrencyFormula}" nameValue="${fdDetail_FormItem.fdBaseCurrencyText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdBaseCurrencyFormula','fdDetail_Form[*].fdBaseCurrencyText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 汇率--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdRateFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdRateFormula" propertyName="fdDetail_Form[${vstatus.index}].fdRateText" idValue="${fdDetail_FormItem.fdRateFormula}" nameValue="${fdDetail_FormItem.fdRateText}" required="true" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdRateFormula','fdDetail_Form[*].fdRateText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <%-- 收款人名称--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeNameFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdPayeeNameFormula" propertyName="fdDetail_Form[${vstatus.index}].fdPayeeNameText" idValue="${fdDetail_FormItem.fdPayeeNameFormula}" nameValue="${fdDetail_FormItem.fdPayeeNameText}" required="true" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdPayeeNameFormula','fdDetail_Form[*].fdPayeeNameText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <%-- 收款人账号--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeAccountFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdPayeeAccountFormula" propertyName="fdDetail_Form[${vstatus.index}].fdPayeeAccountText" idValue="${fdDetail_FormItem.fdPayeeAccountFormula}" nameValue="${fdDetail_FormItem.fdPayeeAccountText}" required="true" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdPayeeAccountFormula','fdDetail_Form[*].fdPayeeAccountText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <%-- 收款行名称--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankNameFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdPayeeBankNameFormula" propertyName="fdDetail_Form[${vstatus.index}].fdPayeeBankNameText" idValue="${fdDetail_FormItem.fdPayeeBankNameFormula}" nameValue="${fdDetail_FormItem.fdPayeeBankNameText}" required="true" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdPayeeBankNameFormula','fdDetail_Form[*].fdPayeeBankNameText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                        <td align="center">
                                        <%-- 收款账户开户地--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankCityFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdPayeeBankCityFormula" propertyName="fdDetail_Form[${vstatus.index}].fdPayeeBankCity" idValue="${fdDetail_FormItem.fdPayeeBankCityFormula}" nameValue="${fdDetail_FormItem.fdPayeeBankCity}"  showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdPayeeBankCityFormula','fdDetail_Form[*].fdPayeeBankCity');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                     <fssc:checkUseBank fdBank="BOC">
	                                  <td align="center">
	                                    <%-- 银联号--%>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankNoFormula" _xform_type="text">
	                                        <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdPayeeBankNoFormula" propertyName="fdDetail_Form[${vstatus.index}].fdPayeeBankNoText" required="true" idValue="${fdDetail_FormItem.fdPayeeBankNoFormula}" nameValue="${fdDetail_FormItem.fdPayeeBankNoText}"   showStatus="edit" style="width:95%;">
	                                            selectFormula('fdDetail_Form[*].fdPayeeBankNoFormula','fdDetail_Form[*].fdPayeeBankNoText');
	                                        </xform:dialog>
	                                    </div>
	                                  </td>
	                                </fssc:checkUseBank>
                                    <td align="center">
                                            <%-- 关联明细id--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdDetailModelIdFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdDetailModelIdFormula" propertyName="fdDetail_Form[${vstatus.index}].fdDetailModelIdText" idValue="${fdDetail_FormItem.fdDetailModelIdFormula}" nameValue="${fdDetail_FormItem.fdDetailModelIdText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdDetailModelIdFormula','fdDetail_Form[*].fdDetailModelIdText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                            <%-- 关联明细name--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdDetailModelNameFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdDetailModelNameFormula" propertyName="fdDetail_Form[${vstatus.index}].fdDetailModelNameText" idValue="${fdDetail_FormItem.fdDetailModelNameFormula}" nameValue="${fdDetail_FormItem.fdDetailModelNameText}" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdDetailModelNameFormula','fdDetail_Form[*].fdDetailModelNameText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <fssc:checkUseBank fdBank="CBS">
                                        <td align="center">
                                                <%-- 是否同城--%>
                                            <div id="_xform_fdDetail_Form[${vstatus.index}].fdSameDiffCity" _xform_type="text">
                                                <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdSameDiffCityFormula" propertyName="fdDetail_Form[${vstatus.index}].fdSameDiffCityText" showStatus="edit" style="width:95%;">
                                                    selectFormula('fdDetail_Form[*].fdSameDiffCityFormula','fdDetail_Form[*].fdSameDiffCityText');
                                                </xform:dialog>
                                            </div>
                                        </td>
                                    </fssc:checkUseBank>
                                    <td align="center">
                                        <%-- 付款金额--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdMoneyFormula" _xform_type="text">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdMoneyFormula" propertyName="fdDetail_Form[${vstatus.index}].fdMoneyText" idValue="${fdDetail_FormItem.fdMoneyFormula}" nameValue="${fdDetail_FormItem.fdMoneyText}" required="true" showStatus="edit" style="width:95%;">
                                                selectFormula('fdDetail_Form[*].fdMoneyFormula','fdDetail_Form[*].fdMoneyText');
                                            </xform:dialog>
                                        </div>
                                    </td>
                                     <td align="center">
                                    <%-- 计划付款时间--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPlanPaymentDateFormula" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdPlanPaymentDateFormula" style="width:80%;"></xform:text>
                                    </div>
                                	</td>
                                	<td align="center">
                                    <%-- 摘要--%>
                                    	<div id="_xform_fdDetail_Form[${vstatus.index}].fdRemarksFormula" _xform_type="text">
                                        <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdRemarksFormula" propertyName="fdDetail_Form[${vstatus.index}].fdRemarksText" idValue="${fdDetail_FormItem.fdRemarksFormula}" nameValue="${fdDetail_FormItem.fdRemarksText}" required="true" showStatus="edit" style="width:95%;">
                                            selectFormula('fdDetail_Form[*].fdRemarksFormula','fdDetail_Form[*].fdRemarksText');
                                        </xform:dialog>
	                                    </div>
	                                </td>
	                                <fssc:checkUseBank fdBank="CMB">
	                                <td align="center" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCStlchnMemo')} ">
	                                    <%-- 结算方式--%>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdCStlchnFormula" _xform_type="text">
	                                        <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdCStlchnFormula" propertyName="fdDetail_Form[${vstatus.index}].fdCStlchnText" idValue="${fdDetail_FormItem.fdCStlchnFormula}" nameValue="${fdDetail_FormItem.fdCStlchnText}"  showStatus="edit" style="width:95%;">
	                                            selectFormula('fdDetail_Form[*].fdCStlchnFormula','fdDetail_Form[*].fdCStlchnText');
	                                        </xform:dialog>
	                                    </div>
	                                </td>
                                	</fssc:checkUseBank>
                                    <fssc:checkUseBank fdBank="CMInt">
                                        <td align="center" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCStlchnMemo1')} ">
                                                <%-- 结算方式--%>
                                            <div id="_xform_fdDetail_Form[${vstatus.index}].fdCStlchnFormula" _xform_type="text">
                                                <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdCStlchnFormula" propertyName="fdDetail_Form[${vstatus.index}].fdCStlchnText" idValue="${fdDetail_FormItem.fdCStlchnFormula}" nameValue="${fdDetail_FormItem.fdCStlchnText}"  showStatus="edit" style="width:95%;">
                                                    selectFormula('fdDetail_Form[*].fdCStlchnFormula','fdDetail_Form[*].fdCStlchnText');
                                                </xform:dialog>
                                            </div>
                                        </td>
                                    </fssc:checkUseBank>
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
                                <td colspan="10">
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
                            ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdIsAvailable')}
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
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-cashier:fsscCashierRuleConfig.docCreator')}
                    </td>
                    <td width="35%">
                            <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscCashierRuleConfigForm.docCreatorId}" personName="${fsscCashierRuleConfigForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-cashier:fsscCashierRuleConfig.docCreateTime')}
                    </td>
                    <td width="35%">
                            <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-cashier:fsscCashierRuleConfig.docAlteror')}
                    </td>
                    <td width="35%">
                            <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${fsscCashierRuleConfigForm.docAlterorId}" personName="${fsscCashierRuleConfigForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-cashier:fsscCashierRuleConfig.docAlterTime')}
                    </td>
                    <td width="35%">
                            <%-- 更新时间--%>
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
