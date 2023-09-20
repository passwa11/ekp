<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscBudgetExecuteForm.fdModelName} - " />
        <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" />
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
                basePath: '/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

            <!--edit-->
            <kmss:auth requestURL="/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=edit&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscBudgetExecute.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
            </kmss:auth>
            <!--delete-->
            <kmss:auth requestURL="/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscBudgetExecute.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" href="/fssc/budget/fssc_budget_execute/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">

        <ui:tabpage expand="false" var-navwidth="90%">
            <ui:content title="${ lfn:message('fssc-budget:py.JiBenXinXi') }" expand="true">
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdModelId')}
                        </td>
                        <td width="35%">
                            <%-- 单据ID--%>
                            <div id="_xform_fdModelId" _xform_type="text">
                                <xform:text property="fdModelId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdModelName')}
                        </td>
                        <td width="35%">
                            <%-- 单据模块名--%>
                            <div id="_xform_fdModelName" _xform_type="text">
                                <xform:text property="fdModelName" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdMoney')}
                        </td>
                        <td width="35%">
                            <%-- 金额--%>
                            <div id="_xform_fdMoney" _xform_type="text">
                                <xform:text property="fdMoney" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdType')}
                        </td>
                        <td width="35%">
                            <%-- 类型--%>
                            <div id="_xform_fdType" _xform_type="text">
                                <xform:text property="fdType" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetId')}
                        </td>
                        <td width="35%">
                            <%-- 预算ID--%>
                            <div id="_xform_fdBudgetId" _xform_type="text">
                                <xform:text property="fdBudgetId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdDetailId')}
                        </td>
                        <td width="35%">
                            <%-- 单据明细ID--%>
                            <div id="_xform_fdDetailId" _xform_type="text">
                                <xform:text property="fdDetailId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyGroupId')}
                        </td>
                        <td width="35%">
                            <%-- 公司组--%>
                            <div id="_xform_fdCompanyGroupId" _xform_type="text">
                                <xform:text property="fdCompanyGroupId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyId')}
                        </td>
                        <td width="35%">
                            <%-- 公司--%>
                            <div id="_xform_fdCompanyId" _xform_type="text">
                                <xform:text property="fdCompanyId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterId')}
                        </td>
                        <td width="35%">
                            <%-- 成本中心--%>
                            <div id="_xform_fdCostCenterId" _xform_type="text">
                                <xform:text property="fdCostCenterId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetItemId')}
                        </td>
                        <td width="35%">
                            <%-- 预算科目--%>
                            <div id="_xform_fdBudgetItemId" _xform_type="text">
                                <xform:text property="fdBudgetItemId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdProjectId')}
                        </td>
                        <td width="35%">
                            <%-- 项目--%>
                            <div id="_xform_fdProjectId" _xform_type="text">
                                <xform:text property="fdProjectId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdInnerOrderId')}
                        </td>
                        <td width="35%">
                            <%-- 内部订单--%>
                            <div id="_xform_fdInnerOrderId" _xform_type="text">
                                <xform:text property="fdInnerOrderId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdWbsId')}
                        </td>
                        <td width="35%">
                            <%-- WBS--%>
                            <div id="_xform_fdWbsId" _xform_type="text">
                                <xform:text property="fdWbsId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdPersonId')}
                        </td>
                        <td width="35%">
                            <%-- 人员--%>
                            <div id="_xform_fdPersonId" _xform_type="text">
                                <xform:text property="fdPersonId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyGroupCode')}
                        </td>
                        <td width="35%">
                            <%-- 公司组编码--%>
                            <div id="_xform_fdCompanyGroupCode" _xform_type="text">
                                <xform:text property="fdCompanyGroupCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyCode')}
                        </td>
                        <td width="35%">
                            <%-- 公司编码--%>
                            <div id="_xform_fdCompanyCode" _xform_type="text">
                                <xform:text property="fdCompanyCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterGroupId')}
                        </td>
                        <td width="35%">
                            <%-- 成本中心组--%>
                            <div id="_xform_fdCostCenterGroupId" _xform_type="text">
                                <xform:text property="fdCostCenterGroupId" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterCode')}
                        </td>
                        <td width="35%">
                            <%-- 成本中心编码--%>
                            <div id="_xform_fdCostCenterCode" _xform_type="text">
                                <xform:text property="fdCostCenterCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterGroupCode')}
                        </td>
                        <td width="35%">
                            <%-- 成本中心组编码--%>
                            <div id="_xform_fdCostCenterGroupCode" _xform_type="text">
                                <xform:text property="fdCostCenterGroupCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetItemCode')}
                        </td>
                        <td width="35%">
                            <%-- 预算科目编码--%>
                            <div id="_xform_fdBudgetItemCode" _xform_type="text">
                                <xform:text property="fdBudgetItemCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdProjectCode')}
                        </td>
                        <td width="35%">
                            <%-- 项目编码--%>
                            <div id="_xform_fdProjectCode" _xform_type="text">
                                <xform:text property="fdProjectCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdInnerOrderCode')}
                        </td>
                        <td width="35%">
                            <%-- 内部订单编码--%>
                            <div id="_xform_fdInnerOrderCode" _xform_type="text">
                                <xform:text property="fdInnerOrderCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdWbsCode')}
                        </td>
                        <td width="35%">
                            <%-- WBS号--%>
                            <div id="_xform_fdWbsCode" _xform_type="text">
                                <xform:text property="fdWbsCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdPersonCode')}
                        </td>
                        <td width="35%">
                            <%-- 人员编码--%>
                            <div id="_xform_fdPersonCode" _xform_type="text">
                                <xform:text property="fdPersonCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetExecute.fdCurrency')}
                        </td>
                        <td width="35%">
                            <%-- 预算对应币种--%>
                            <div id="_xform_fdCurrency" _xform_type="text">
                                <xform:text property="fdCurrency" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td colspan="2">
                        </td>
                    </tr>
                </table>
            </ui:content>
        </ui:tabpage>
    </template:replace>

</template:include>
