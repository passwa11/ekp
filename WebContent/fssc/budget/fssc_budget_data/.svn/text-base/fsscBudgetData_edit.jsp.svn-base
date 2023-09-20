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
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_data/", 'js', true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetDataForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budget:table.fsscBudgetData') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetDataForm.fdYear} - " />
                <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetData') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetDataForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetDataForm, 'update');" />
                </c:when>
                <c:when test="${ fsscBudgetDataForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetDataForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetData') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budget/fssc_budget_data/fsscBudgetData.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-budget:py.JiBenXinXi') }" expand="true">
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdYear')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdYear" _xform_type="text">
                                    <xform:text property="fdYear" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdBudgetScheme')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdBudgetSchemeId" _xform_type="radio">
                                    <xform:radio property="fdBudgetSchemeId" htmlElementProperties="id='fdBudgetSchemeId'" showStatus="edit">
                                        <xform:beanDataSource serviceBean="eopBasedataBudgetSchemeService" selectBlock="fdId,fdName" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdPeriodType')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdPeriodType" _xform_type="text">
                                    <xform:text property="fdPeriodType" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdPeriod')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdPeriod" _xform_type="text">
                                    <xform:text property="fdPeriod" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdCompanyGroup')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdCompanyGroupId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCompanyGroupId" propertyName="fdCompanyGroupName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_company_group_fdGroup','fdCompanyGroupId','fdCompanyGroupName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdCompany')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdCompanyId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdCostCenterGroupId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCostCenterGroupId" propertyName="fdCostCenterGroupName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_cost_center_fdParent','fdCostCenterGroupId','fdCostCenterGroupName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdCostCenter')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdBudgetItemId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdBudgetItemId" propertyName="fdBudgetItemName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_budget_item_com_fdBudgetItem','fdBudgetItemId','fdBudgetItemName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdProject')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdProjectId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdProjectId" propertyName="fdProjectName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_project_project','fdProjectId','fdProjectName');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdInnerOrder')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdInnerOrderId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdInnerOrderId" propertyName="fdInnerOrderName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdInnerOrderId','fdInnerOrderName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdWbs')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdWbsId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdWbsId" propertyName="fdWbsName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_wbs_fdWbs','fdWbsId','fdWbsName');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdPerson')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdPersonId" _xform_type="address">
                                    <xform:address propertyId="fdPersonId" propertyName="fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdCurrency')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdMoney')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdMoney" _xform_type="text">
                                    <xform:text property="fdMoney" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdElasticPercent')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdElasticPercent" _xform_type="text">
                                    <xform:text property="fdElasticPercent" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdBudgetStatus')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdBudgetStatus" _xform_type="text">
                                    <xform:text property="fdBudgetStatus" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetData.fdRule')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdRule" _xform_type="text">
                                    <xform:text property="fdRule" showStatus="edit" style="width:95%;" />
                                </div>
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
