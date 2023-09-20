<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<template:include ref="default.view">
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
            var local_value = "<%=ResourceUtil.getLocaleStringByUser() %>";
            var formInitData = {

            };
            var messageInfo = {
                "fssc-voucher:table.js.property.null" : "${lfn:message('fssc-voucher:table.js.property.null')}",
                "fssc-voucher:enums.fd_type.1" : "${lfn:message('fssc-voucher:enums.fd_type.1')}",
                "fssc-voucher:enums.fd_type.2" : "${lfn:message('fssc-voucher:enums.fd_type.2')}"
            };
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("table.js", "${LUI_ContextPath}/fssc/voucher/resource/js/", 'js', true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscVoucherMainForm.docNumber} - " />
        <c:out value="${ lfn:message('fssc-voucher:table.fsscVoucherMain') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                        if(isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
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
                basePath: '/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
            <!--记账-->
            <c:if test="${fsscVoucherMainForm.fdBookkeepingStatus == '10' || fsscVoucherMainForm.fdBookkeepingStatus == '11'}" >
            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=bookkeeping&fdId=${param.fdId}">
              	<ui:button text="${lfn:message('fssc-voucher:button.bookkeeping')}" id="bookkeepingButton" onclick="bookkeeping();" order="1" />
            </kmss:auth>
            </c:if>
            <!--edit-->
            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=edit&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.edit')}" id="editButton" onclick="Com_OpenWindow('fsscVoucherMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
            </kmss:auth>
            <!--delete-->
            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscVoucherMain.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
        <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
        <ui:menu-item text="${ lfn:message('fssc-voucher:table.fsscVoucherMain') }" href="/fssc/voucher/fssc_voucher_main/" target="_self" />
    </ui:menu>
</template:replace>
<template:replace name="content">

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
                            <xform:dialog propertyId="fdBaseVoucherTypeId" propertyName="fdBaseVoucherTypeName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_voucher_type_getVoucherType','fdBaseVoucherTypeId','fdBaseVoucherTypeName');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdVoucherDate')}
                    </td>
                    <td width="16.6%">
                        <%-- 凭证日期--%>
                        <div id="_xform_fdVoucherDate" _xform_type="datetime">
                            <xform:datetime property="fdVoucherDate" showStatus="view" style="width:95%;" />
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
                            <xform:text property="fdAccountingYear" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdPeriod')}
                    </td>
                    <td width="16.6%">
                        <%-- 期间--%>
                        <div id="_xform_fdPeriod" _xform_type="text">
                            <xform:text property="fdPeriod" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseCurrency')}
                    </td>
                    <td width="16.6%">
                        <%-- 凭证货币--%>
                        <div id="_xform_fdBaseCurrencyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdBaseCurrencyId" propertyName="fdBaseCurrencyName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdBaseCurrencyId','fdBaseCurrencyName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdCompanyName')}
                    </td>
                    <td width="16.6%">
                        <%-- 公司--%>
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdCompanyCode')}
                    </td>
                    <td width="16.6%">
                        <%-- 公司编号--%>
                        <div id="_xform_fdCompanyCode" _xform_type="text">
                            <xform:text property="fdCompanyCode" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdNumber')}
                    </td>
                    <td width="16.6%">
                        <%-- 单据数--%>
                        <div id="_xform_fdNumber" _xform_type="text">
                            <xform:text property="fdNumber" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdVoucherText')}
                    </td>
                    <td width="16.6%">
                        <%-- 凭证抬头文本--%>
                        <div id="_xform_fdVoucherText" _xform_type="text">
                            <xform:text property="fdVoucherText" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-voucher:fsscVoucherMain.fdPushType')}
                    </td>
                    <td width="16.6%">
                        <%-- 推送方式--%>
                        <div id="_xform_fdPushType" _xform_type="radio">
                            <xform:radio property="fdPushType" htmlElementProperties="id='fdPushType'" required="true" showStatus="view">
                                <xform:enumsDataSource enumsType="fssc_voucher_fd_push_type" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdMergeEntry')}
                    </td>
                    <td width="16.6%">
                        <%-- 合并分录--%>
                        <div id="_xform_fdMergeEntry" _xform_type="radio">
                            <xform:radio property="fdMergeEntry" htmlElementProperties="id='fdMergeEntry'" required="true" showStatus="view">
                                <xform:enumsDataSource enumsType="fssc_voucher_fd_merge_entry" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingStatus')}
                    </td>
                    <td colspan="5" width="16.6%">
                        <%-- 记账状态--%>
                        <div id="_xform_fdBookkeepingStatus" _xform_type="select">
                            <xform:select property="fdBookkeepingStatus" showStatus="view" style="width:95%;" >
                                <xform:enumsDataSource enumsType="fssc_voucher_fd_bookkeeping_status" />
                            </xform:select>
                        </div>
                    </td>
                </tr>
                <c:if test="${fsscVoucherMainForm.fdBookkeepingStatus == '11'}">
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingMessage')}
                        </td>
                        <td colspan="5" width="16.6%">
                                <%-- 记账失败原因--%>
                            <div id="_xform_fdBookkeepingMessage" _xform_type="text">
                                <xform:text property="fdBookkeepingMessage" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="6" width="100%">
                        <span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('fssc-voucher:fsscVoucherMain.fd_detail') }
                    </td>
                </tr>
                <tr>
                    <td colspan="6" width="100%">
                        <c:import url="/fssc/voucher/fssc_voucher_detail/fsscVoucherDetail_view.jsp" charEncoding="UTF-8">
                        </c:import>
                    </td>
                </tr>
            </table>
            <c:import url="/fssc/voucher/fssc_voucher_detail/fsscVoucherDetail_new_view.jsp" charEncoding="UTF-8"></c:import>
            <html:hidden property="fdIsChechedSAP" value="${fdIsChechedSAP }"/>
            <script>

                $(function () {
                    initSAP();//初始化SAP字段
                });
                //初始化SAP字段
                function initSAP(){
                    var fdIsChechedSAP = $("input[name='fdIsChechedSAP']").val();
                    if(fdIsChechedSAP=="SAP"){//开启了SAP
                        $(".fdBaseSupplierContent").attr("colspan", "1");
                        $(".fdBaseWbs").show();
                        $(".fdBaseInnerOrder").show();
                    }else{
                        $(".fdBaseSupplierContent").attr("colspan", "5");
                        $(".fdBaseWbs").hide();
                        $(".fdBaseInnerOrder").hide();
                    }

                }

                //记账
                function bookkeeping(){
                    var fdVoucherMainId = '${fsscVoucherMainForm.fdId}';
                    if(!fdVoucherMainId){
                        return;
                    }
                    seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
                        dialog.confirm('${ lfn:message("fssc-voucher:button.bookkeeping.confirm") }', function(isOk) {
                            if(isOk) {
                                var del_load = dialog.loading("${lfn:message('fssc-voucher:bookkeeping.loading')}");
                                $.post('${LUI_ContextPath}/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=bookkeeping&fdId=${param.fdId}',
                                    $.param({"fdVoucherMainId":fdVoucherMainId},true),function(data){
                                        if(del_load != null){
                                            del_load.hide();
                                        }
                                        var fdIsBoolean = data.fdIsBoolean;//true 记账成功 false 记账失败
                                        //记账成功
                                        if(fdIsBoolean){
                                            setTimeout(function(){
                                                window.location.reload();//刷新页面
                                            },1000);
                                        }
                                        var messageStr = data.message;
                                        if(messageStr && messageStr.length > 0){
                                            dialog.alert(messageStr);
                                        }
                                },'json');
                            }
                        });
                    });
                }
            </script>
        </ui:tabpage>
    </template:replace>

</template:include>*
