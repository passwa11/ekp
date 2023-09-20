<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget"%>
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
            var formInitData = {

            };
            var messageInfo = {

            };
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
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
                basePath: '/fssc/budget/fssc_budget_main/fsscBudgetMain.do',
                customOpts: {

                    ____fork__: 0
                }
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

            <!--edit-->
           <%--  <kmss:auth requestURL="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=edit&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscBudgetMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
            </kmss:auth> --%>
            <!--delete-->
            <kmss:auth requestURL="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscBudgetMain.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetMain') }" href="/fssc/budget/fssc_budget_main/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">

        <ui:tabpage expand="false" var-navwidth="90%">
            <ui:content title="${ lfn:message('fssc-budget:py.JiBenXinXi') }" expand="true">
                <table class="tb_normal" width="100%" style="word-break:break-all;table-layout:fixed">
                	<c:set var="containCompany" value="false"></c:set>
                   	<budget:budgetScheme fdSchemeId="${fsscBudgetMainForm.fdBudgetSchemeId}" type="dimension" value="2">
                   		<c:set var="containCompany" value="true"></c:set>
                   	</budget:budgetScheme>
                   	<%-- 维度包含公司 --%>
                   	<c:if test="${containCompany}">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.fdYear')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdYear" _xform_type="text">
                                <kmss:showPeriod property="fdYear" value="${fsscBudgetMainForm.fdYear}"></kmss:showPeriod>
                            </div>
                        </td>
                    </tr>
                    </c:if>
                    <%-- 维度不包含公司 --%>
                	<c:if test="${!containCompany}">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.fdYear')}
                        </td>
                        <td width="85%" colspan="3">
                            <div id="_xform_fdYear" _xform_type="text">
                                <kmss:period property="fdYear" periodTypeValue="5"/>
                            </div>
                        </td>
                    </tr>
                    </c:if>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.fdBudgetScheme')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdBudgetSchemeId" _xform_type="select">
                                <xform:select property="fdBudgetSchemeId" htmlElementProperties="id='fdBudgetSchemeId'" showStatus="view">
                                    <xform:beanDataSource serviceBean="eopBasedataBudgetSchemeService" selectBlock="fdId,fdName" />
                                </xform:select>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.fdCurrency')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.fdEnableDate')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdEnableDate" _xform_type="datetime">
                                <xform:datetime property="fdEnableDate" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" width="100%;" >
                             <c:import url="/fssc/budget/fssc_budget_detail/fsscBudgetDetail_view.jsp" charEncoding="UTF-8"></c:import>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.fdDesc')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdDesc" _xform_type="textarea">
                                <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.docCreator')}
                        </td>
                        <td width="35%">
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${fsscBudgetMainForm.docCreatorId}" personName="${fsscBudgetMainForm.docCreatorName}" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetMain.docCreateTime')}
                        </td>
                        <td width="35%">
                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                </table>
            </ui:content>
        </ui:tabpage>
    </template:replace>

</template:include>
