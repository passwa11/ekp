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

            };

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_execute/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetExecuteForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetExecuteForm.fdModelName} - " />
                <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetExecuteForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetExecuteForm, 'update');" />
                </c:when>
                <c:when test="${ fsscBudgetExecuteForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetExecuteForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do">

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
                                    <xform:text property="fdModelId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdModelName')}
                            </td>
                            <td width="35%">
                                <%-- 单据模块名--%>
                                <div id="_xform_fdModelName" _xform_type="text">
                                    <xform:text property="fdModelName" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdMoney" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdType')}
                            </td>
                            <td width="35%">
                                <%-- 类型--%>
                                <div id="_xform_fdType" _xform_type="text">
                                    <xform:text property="fdType" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdBudgetId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdDetailId')}
                            </td>
                            <td width="35%">
                                <%-- 单据明细ID--%>
                                <div id="_xform_fdDetailId" _xform_type="text">
                                    <xform:text property="fdDetailId" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdCompanyGroupId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyId')}
                            </td>
                            <td width="35%">
                                <%-- 公司--%>
                                <div id="_xform_fdCompanyId" _xform_type="text">
                                    <xform:text property="fdCompanyId" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdCostCenterId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetItemId')}
                            </td>
                            <td width="35%">
                                <%-- 预算科目--%>
                                <div id="_xform_fdBudgetItemId" _xform_type="text">
                                    <xform:text property="fdBudgetItemId" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdProjectId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdInnerOrderId')}
                            </td>
                            <td width="35%">
                                <%-- 内部订单--%>
                                <div id="_xform_fdInnerOrderId" _xform_type="text">
                                    <xform:text property="fdInnerOrderId" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdWbsId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdPersonId')}
                            </td>
                            <td width="35%">
                                <%-- 人员--%>
                                <div id="_xform_fdPersonId" _xform_type="text">
                                    <xform:text property="fdPersonId" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdCompanyGroupCode" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyCode')}
                            </td>
                            <td width="35%">
                                <%-- 公司编码--%>
                                <div id="_xform_fdCompanyCode" _xform_type="text">
                                    <xform:text property="fdCompanyCode" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdCostCenterGroupId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterCode')}
                            </td>
                            <td width="35%">
                                <%-- 成本中心编码--%>
                                <div id="_xform_fdCostCenterCode" _xform_type="text">
                                    <xform:text property="fdCostCenterCode" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdCostCenterGroupCode" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetItemCode')}
                            </td>
                            <td width="35%">
                                <%-- 预算科目编码--%>
                                <div id="_xform_fdBudgetItemCode" _xform_type="text">
                                    <xform:text property="fdBudgetItemCode" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdProjectCode" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdInnerOrderCode')}
                            </td>
                            <td width="35%">
                                <%-- 内部订单编码--%>
                                <div id="_xform_fdInnerOrderCode" _xform_type="text">
                                    <xform:text property="fdInnerOrderCode" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdWbsCode" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetExecute.fdPersonCode')}
                            </td>
                            <td width="35%">
                                <%-- 人员编码--%>
                                <div id="_xform_fdPersonCode" _xform_type="text">
                                    <xform:text property="fdPersonCode" showStatus="edit" style="width:95%;" />
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
                                    <xform:text property="fdCurrency" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td colspan="2">
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
            <html:hidden property="fdId" />

            <html:hidden property="method_GET" />
        </html:form>
    </template:replace>


</template:include>
