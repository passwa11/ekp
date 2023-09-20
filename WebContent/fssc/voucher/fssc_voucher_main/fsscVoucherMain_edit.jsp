<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="default.edit">
    <template:replace name="head">
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
                "fssc-voucher:fsscVoucherMain.deleteAll.detail" : "${lfn:message('fssc-voucher:fsscVoucherMain.deleteAll.detail')}",
                "fssc-voucher:table.js.property.null" : "${lfn:message('fssc-voucher:table.js.property.null')}",
                "fssc-voucher:enums.fd_type.1" : "${lfn:message('fssc-voucher:enums.fd_type.1')}",
                "fssc-voucher:enums.fd_type.2" : "${lfn:message('fssc-voucher:enums.fd_type.2')}",
                "fssc-voucher:errors.borrowingAmount.equation" : "${lfn:message('fssc-voucher:errors.borrowingAmount.equation')}",
                "fssc-voucher:errors.validateTotalMoney.equation" : "${lfn:message('fssc-voucher:errors.validateTotalMoney.equation')}"
            };

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/voucher/fssc_voucher_main/", 'js', true);
            Com_IncludeFile("fsscVoucherMain_edit.js", "${LUI_ContextPath}/fssc/voucher/fssc_voucher_main/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/voucher/resource/js/", 'js', true);
            Com_IncludeFile("table.js", "${LUI_ContextPath}/fssc/voucher/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscVoucherMainForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-voucher:table.fsscVoucherMain') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscVoucherMainForm.docNumber} - " />
                <c:out value="${ lfn:message('fssc-voucher:table.fsscVoucherMain') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscVoucherMainForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="mySubmit(document.fsscVoucherMainForm, 'update');" />
                </c:when>
                <c:when test="${ fsscVoucherMainForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="mySubmit(document.fsscVoucherMainForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-voucher:table.fsscVoucherMain') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <div class='lui_form_title_frame'>
                    <div class='lui_form_subject'>
                        ${lfn:message('fssc-voucher:table.fsscVoucherMain')}
                    </div>
                    <div class='lui_form_baseinfo'>

                    </div>
                </div>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.docFinanceNumber')}
                        </td>
                        <td width="16.6%">
                            <%-- 财务凭证号--%>
                            <div id="_xform_docFinanceNumber" _xform_type="text">
                                <xform:text property="docFinanceNumber" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.docNumber')}
                        </td>
                        <td width="16.6%">
                            <%-- 费控凭证号--%>
                            <div id="_xform_docNumber" _xform_type="text">
                                <xform:text property="docNumber" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdModelNumber')}
                        </td>
                        <td width="16.6%">
                            <xform:text property="fdModelMoney" showStatus="noShow" style="width:95%;" />
                            <%-- 来源单据编号--%>
                            <div id="_xform_fdModelNumber" _xform_type="text">
                                <c:if test="${not empty fsscVoucherMainForm.fdModelId}" >
                                    <a href="${LUI_ContextPath}${fsscVoucherMainForm.fdModelUrl}" target="_blank">${fsscVoucherMainForm.fdModelNumber}</a>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseVoucherType')}
                        </td>
                        <td width="16.6%">
                            <%-- 凭证类型--%>
                            <div id="_xform_fdBaseVoucherTypeId" _xform_type="dialog">
                                <xform:dialog propertyId="fdBaseVoucherTypeId" propertyName="fdBaseVoucherTypeName" subject="${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseVoucherType') }" showStatus="edit" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_voucher_type_getVoucherType','fdBaseVoucherTypeId','fdBaseVoucherTypeName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdVoucherDate')}
                        </td>
                        <td width="16.6%">
                            <%-- 凭证日期--%>
                            <div id="_xform_fdVoucherDate" _xform_type="datetime">
                                <xform:datetime property="fdVoucherDate" showStatus="edit" onValueChange="onChangFdVoucherDate" dateTimeType="date" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingDate')}
                        </td>
                        <td width="16.6%">
                            <%-- 记账日期--%>
                            <div id="_xform_fdBookkeepingDate" _xform_type="datetime">
                                <xform:datetime property="fdBookkeepingDate" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdAccountingYear')}
                        </td>
                        <td width="16.6%">
                            <%-- 会计年度--%>
                            <div id="_xform_fdAccountingYear" _xform_type="text">
                                <xform:text property="fdAccountingYear" showStatus="edit" required="true" style="width:91%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdPeriod')}
                        </td>
                        <td width="16.6%">
                            <%-- 期间--%>
                            <div id="_xform_fdPeriod" _xform_type="text">
                                <xform:text property="fdPeriod" showStatus="edit" required="true" style="width:91%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseCurrency')}
                        </td>
                        <td width="16.6%">
                            <%-- 凭证货币--%>
                            <div id="_xform_fdBaseCurrencyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdBaseCurrencyId" propertyName="fdBaseCurrencyName" showStatus="readOnly" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_currency_fdCurrency','fdBaseCurrencyId','fdBaseCurrencyName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdCompanyName')}
                        </td>
                        <td width="16.6%">
                            <%-- 公司名称--%>
                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" required="true" subject="${lfn:message('fssc-voucher:fsscVoucherMain.fdCompanyName') }" showStatus="edit" style="width:95%;">
                                    oldFdCompanyId = $('[name=fdCompanyId]').val();
                                    oldFdCompanyName = $('[name=fdCompanyName]').val();
                                    dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName', null, {fdModelName:'com.landray.kmss.fssc.voucher.model.FsscVoucherMain'}, selectFdCompanyNameCallback);
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdCompanyCode')}
                        </td>
                        <td width="16.6%">
                            <%-- 公司编号 --%>
                            <div id="_xform_fdModelName" _xform_type="text">
                                <xform:text property="fdCompanyCode" showStatus="readOnly" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdNumber')}
                        </td>
                        <td width="16.6%">
                            <%-- 单据数--%>
                            <div id="_xform_fdNumber" _xform_type="text">
                                <xform:text property="fdNumber" showStatus="edit" required="true" validators="digits" style="width:91%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdVoucherText')}
                        </td>
                        <td colspan="5" width="16.6%">
                            <%-- 凭证抬头文本--%>
                            <div id="_xform_fdVoucherText" _xform_type="text">
                                <xform:text property="fdVoucherText" showStatus="edit" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" width="100%">
                            <span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('fssc-voucher:fsscVoucherMain.fd_detail') }
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" width="100%">
                            <c:import url="/fssc/voucher/fssc_voucher_detail/fsscVoucherDetail_edit.jsp" charEncoding="UTF-8">
                            </c:import>
                            <input type="hidden" name="methodNew" id="methodNew" value="">
                            <input type="hidden" name="fdDetail_Flag" value="1">
                            <script>
                                Com_IncludeFile("doclist.js");
                            </script>
                            <script>
                                DocList_Info.push('TABLE_DocList');
                            </script>
                        </td>
                    </tr>
                </table>
            </ui:tabpage>
            <html:hidden property="fdId" />

            <html:hidden property="method_GET" />
            <html:hidden property="fdIsChechedSAP" value="${fdIsChechedSAP }"/>
            <html:hidden property="fdPushType" value="${fsscVoucherMainForm.fdPushType }"/>
            <input type="hidden"  value="" name="rowNo" id="rowNo"/>
        </html:form>
        <c:import url="/fssc/voucher/fssc_voucher_detail/fsscVoucherDetail_new_edit.jsp" charEncoding="UTF-8"></c:import>
    </template:replace>


</template:include>
