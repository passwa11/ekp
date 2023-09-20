<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCashierRuleConfig.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscCashierRuleConfig.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
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
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig')}
                </td>
                <td width="35%">
                    <%-- 出纳规则模板配置--%>
                    <div id="_xform_fdCashierModelConfigId" _xform_type="dialog">
                        <xform:dialog propertyId="fdCashierModelConfigId" propertyName="fdCashierModelConfigName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'fssc_cashier_model_config_fdCashierPaymentModelConfig','fdCashierModelConfigId','fdCashierModelConfigName');
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
                    <div id="_xform_fdCategoryName" _xform_type="text">
                        <xform:text property="fdCategoryName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdRuleFormula')}
                </td>
                <td width="35%">
                    <%-- 生成规则--%>
                    <div id="_xform_fdRuleFormula" _xform_type="text">
                        <xform:text property="fdRuleFormula" showStatus="view" style="width:95%;" />
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
                        <xform:text property="fdModelNumberFormula" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCompanyFlag')}
                </td>
                <td width="35%">
                    <%-- 公司--%>
                    <c:if test="${fsscCashierRuleConfigForm.fdCompanyFlag == '1'}">
                        <div id="_xform_fdCompanyId"  _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                            </xform:dialog>
                        </div>
                    </c:if>
                    <c:if test="${fsscCashierRuleConfigForm.fdCompanyFlag == '2'}">
                        <div id="_xform_fdCompanyFormula" _xform_type="dialog">
                            ${fsscCashierRuleConfigForm.fdCompanyText}
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdBaseCurrencyFormula')}
                </td>
                <td >
                    <%-- 货币--%>
                    <c:if test="${fsscCashierRuleConfigForm.fdBaseCurrencyFlag == '1'}">
                        <div id="_xform_fdBaseCurrencyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdBaseCurrencyId" propertyName="fdBaseCurrencyName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdBaseCurrencyId','fdBaseCurrencyName');
                            </xform:dialog>
                        </div>
                    </c:if>
                    <c:if test="${fsscCashierRuleConfigForm.fdBaseCurrencyFlag == '2'}">
                        <div id="_xform_fdBaseCurrencyFormula" _xform_type="dialog">
                            ${fsscCashierRuleConfigForm.fdBaseCurrencyText}
                        </div>
                    </c:if>
                </td>
                 <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdPaymentTypeFormula')}
                    </td>
                    <td >
                        <%-- 付款类型--%>
                        <div id="_xform_fdPaymentTypeFormula" _xform_type="radio">
                            <xform:radio property="fdPaymentTypeFormula" onValueChange="onChangFlag" htmlElementProperties="id='fdPaymentTypeFormula'" showStatus="view">
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
                            <kmss:showNumber value="${fsscCashierRuleConfigForm.fdMaxMoney }" pattern="###,##0.00"/>
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
                            <xform:dialog propertyId="docSubjectFormula" propertyName="docSubjectText" required="true" showStatus="view" style="width:95%;">
                                selectFormula('docSubjectFormula','docSubjectText');
                            </xform:dialog>
                        </div>
                    </td>
                    <td colspan="2">
                    </td>
                </tr>
            <tr>
                <td colspan="4" width="100%">
                    <table class="tb_normal" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true" style="table-layout:fixed;white-space: normal;word-break:break-all; width: 100%;">
                        <tr align="center" class="tr_normal_title">
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
                                ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdPayeeBankCityFormula')}
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
                                <fssc:checkUseBank fdBank="CMB,CMInt">
                                 <td>
                                    ${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdCStlchnText')}
                                </td>
                                </fssc:checkUseBank>
                        </tr>
                        <c:forEach items="${fsscCashierRuleConfigForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                            <tr KMSS_IsContentRow="1">
                                <td align="center">
                                    ${vstatus.index+1}
                                </td>
                                <td align="center">
                                    <%-- 生成规则--%>
                                    <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdRuleFormula" _xform_type="text">
                                        ${fdDetail_FormItem.fdRuleText}
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 付款方式--%>
                                    <c:if test="${fdDetail_FormItem.fdBasePayWayFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayWayId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayWayId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayWayName" showStatus="view" style="width:95%;">
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBasePayWayFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayWayFormula" _xform_type="dialog">
                                                ${fdDetail_FormItem.fdBasePayWayText}
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                        <%-- 付款银行--%>
                                    <c:if test="${fdDetail_FormItem.fdBasePayWayFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankName" showStatus="view" style="width:95%;">
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBasePayWayFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankFormula" _xform_type="dialog">
                                                ${fdDetail_FormItem.fdBasePayBankText}
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                    <%-- 货币--%>
                                    <c:if test="${fdDetail_FormItem.fdBaseCurrencyFlag == '1'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCurrencyId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCurrencyId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCurrencyName" showStatus="view" style="width:95%;">
                                            </xform:dialog>
                                        </div>
                                    </c:if>
                                    <c:if test="${fdDetail_FormItem.fdBaseCurrencyFlag == '2'}">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCurrencyFormula" _xform_type="dialog">
                                                ${fdDetail_FormItem.fdBaseCurrencyText}
                                        </div>
                                    </c:if>
                                </td>
                                <td align="center">
                                        <%-- 汇率--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdRateFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdRateText}
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 收款人名称--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeNameFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdPayeeNameText}
                                    </div>
                                </td>
                                <td align="center">
                                        <%-- 收款人账号--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeAccountFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdPayeeAccountText}
                                    </div>
                                </td>
                                <td align="center">
                                        <%-- 收款行名称--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankNameFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdPayeeBankNameText}
                                    </div>
                                </td>
                                   <td align="center">
                                        <%-- 开户地--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankCityFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdPayeeBankCity}
                                    </div>
                                </td>
                                <fssc:checkUseBank fdBank="BOC">
                                <td align="center">
                                        <%-- 银联号--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankNoFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdPayeeBankNoText}
                                    </div>
                                	</td>
                                </fssc:checkUseBank>
                                <td align="center">
                                        <%-- 关联明细id--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdDetailModelIdFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdDetailModelIdText}
                                    </div>
                                </td>
                                <td align="center">
                                        <%-- 关联明细name--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdDetailModelNameFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdDetailModelNameText}
                                    </div>
                                </td>
                                <fssc:checkUseBank fdBank="CBS">
                                <td align="center">
                                        <%-- 是否同城--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdSameDiffCity" _xform_type="text">
                                            ${fdDetail_FormItem.fdSameDiffCityText}
                                    </div>
                                </td>
                                </fssc:checkUseBank>
                                <td align="center">
                                        <%-- 付款金额--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdMoneyFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdMoneyText}
                                    </div>
                                </td>
                                 <td align="center">
                                        <%-- 预计付款时间+天--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPlanPaymentDateFormula" _xform_type="text">
                                            ${fdDetail_FormItem.fdPlanPaymentDateFormula}
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 摘要--%>
                                    	<div id="_xform_fdDetail_Form[${vstatus.index}].fdRemarksFormula" _xform_type="text">
                                        ${fdDetail_FormItem.fdRemarksFormula}
	                                    </div>
	                                </td>
	                                <fssc:checkUseBank fdBank="CMB,CMInt">
	                                <td align="center">
	                                    <%-- 结算方式--%>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdCStlchnFormula" _xform_type="text">
	                                        ${fdDetail_FormItem.fdCStlchnFormula}
	                                    </div>
	                                </td>
	                                </fssc:checkUseBank>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdIsAvailable')}
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
        basePath: '/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
