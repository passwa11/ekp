<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget"%>

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
            		.div-relative{position:relative; width:100%; height:36px;}
            		/* css注释说明： 背景为红色 */ 
					.div-a{ position:absolute; left:0px; top:0px; width:100%; height:36px;}
					/* 背景为黄色 */ 
					.div-b{ position:absolute; left:0px; top:0px; width:100%; height:36px;}
            		
        </style>
        <link href="${LUI_ContextPath}/fssc/common/resource/css/common.css"  rel="stylesheet" />
        <script type="text/javascript">
            var formInitData = {

            };
            var messageInfo = {
            		"propertys":"${columnList}"
            };
            Com_IncludeFile("security.js|doclist.js|common.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_main/", 'js', true);
            Com_IncludeFile("fsscBudgetMain_edit.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_main/", 'js', true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetMainForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetMainForm.fdDesc} - " />
                <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetMainForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetMainForm, 'update');" />
                </c:when>
                <c:when test="${ fsscBudgetMainForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetMainForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budget/fssc_budget_main/fsscBudgetMain.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-budget:py.JiBenXinXi') }" expand="true">
                    <table class="tb_normal" width="100%">
                    	<c:set var="containCompany" value="false"></c:set>
                    	<budget:budgetScheme fdSchemeId="${HtmlParam.fdSchemeId}" type="dimension" value="2">
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
                                	<html:hidden property="fdCurrentCompanyId" value="${fsscBudgetMainForm.fdCompanyId}"/>
                                	<html:hidden property="fdCurrentCompanyName" value="${fsscBudgetMainForm.fdCompanyName}"/>
                                    <xform:dialog dialogJs="dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',changeCompany,{type:'byScheme',fdBudgetSchemeId:'${param.fdSchemeId}'});"  propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" required="true" subject="${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}" style="width:95%;">
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetMain.fdYear')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdYear" _xform_type="text">
                                    <kmss:period property="fdYear" periodTypeValue="5"/>
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
                                    <xform:select property="fdBudgetSchemeId" htmlElementProperties="id='fdBudgetSchemeId'" showStatus="view" required="true" subject="${lfn:message('fssc-budget:fsscBudgetMain.fdBudgetScheme')}">
                                        <xform:beanDataSource serviceBean="eopBasedataBudgetSchemeService" selectBlock="fdId,fdName" />
                                    </xform:select>
                                    <xform:text property="fdBudgetSchemeId" showStatus="noShow"></xform:text>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetMain.fdCurrency')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="readOnly" required="true" style="width:95%;">
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
                                    <xform:datetime property="fdEnableDate" validators="checkCurrentDate" showStatus="edit" dateTimeType="date" style="width:39%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" width="100%">
                            	<div style="float:left;margin-left:10px;margin-bottom:6px;"><ui:button onclick="downTemplate();" text="${lfn:message('fssc-budget:fsscBudget.button.downTemplate')}"></ui:button></div>
                            	<div style="float:left;margin-left:10px;margin-bottom:6px;"><ui:button onclick="importData();" text="${lfn:message('fssc-budget:fsscBudget.button.import')}"></ui:button></div>
                                <c:import url="/fssc/budget/fssc_budget_detail/fsscBudgetDetail_edit.jsp" charEncoding="UTF-8"></c:import>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetMain.fdDesc')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdDesc" _xform_type="textarea">
                                    <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
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
            <html:hidden property="fdId" />
            <html:hidden property="method_GET" />
            <script src="${LUI_ContextPath }/eop/basedata/resource/js/importDetail.js"></script>
            <script>Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);</script>
        </html:form>
    </template:replace>
</template:include>
