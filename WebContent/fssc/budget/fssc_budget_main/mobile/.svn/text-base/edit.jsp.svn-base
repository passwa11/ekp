<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>


    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="mobile.edit" compatibleMode="true">
    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetMainForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetMainForm.fdDesc} - " />
                <c:out value="${lfn:message('fssc-budget:table.fsscBudgetMain') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="head">
        <style>
            .detailTips{
            				color: red;
            	    		font-weight: lighter;
            	    		display: inline-block;
            	    		font-size: 1rem;
            			}
            			.muiFormNoContent{
            				padding-left:1rem;
            				border-top:1px solid #ddd;
            				border-bottom: 1px solid #ddd;
            			 }
            			 .muiDocFrameExt{
            				margin-left: 0rem;
            			 }
            			 .muiDocFrameExt .muiDocInfo{
            				border: none;
            			 }
        </style>
        <script type="text/javascript">
            var formInitData = {

            };
            var lang = {
                "the": "${lfn:message('page.the')}",
                "row": "${lfn:message('page.row')}"
            };
            var messageInfo = {

            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_main/", 'js', true);
        </script>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=save">

            <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                <div data-dojo-type="mui/panel/AccordionPanel">
                    <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-budget:py.JiBenXinXi') }',icon:'mui-ul'">
                        <div class="muiFormContent">
                            <table class="muiSimple" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCompanyId',nameField:'fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscBudgetMainForm.fdCompanyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscBudgetMainForm.fdCompanyName))}',afterSelect:afterDialogSel">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdYear')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdYear" _xform_type="text">
                                            <xform:text property="fdYear" showStatus="edit" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdBudgetScheme')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdBudgetSchemeId" _xform_type="select">
                                            <xform:select property="fdBudgetSchemeId" htmlElementProperties="id='fdBudgetSchemeId'" showStatus="edit" required="true" subject="${lfn:message('fssc-budget:fsscBudgetMain.fdBudgetScheme')}" mobile="true">
                                                <xform:beanDataSource serviceBean="eopBasedataBudgetSchemeService" selectBlock="fdId,fdName" />
                                            </xform:select>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdCurrency')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCurrencyId',nameField:'fdCurrencyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',dataURL:getSource('eop_basedata_currency_fdCurrency'),subject:'${lfn:message('fssc-budget:fsscBudgetMain.fdCurrency')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscBudgetMainForm.fdCurrencyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscBudgetMainForm.fdCurrencyName))}',afterSelect:afterDialogSel">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdEnableDate')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdEnableDate" _xform_type="datetime">
                                            <xform:datetime property="fdEnableDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdDetailList_Form","scrollView");}'>
                                            <div layout="left">${lfn:message('fssc-budget:fsscBudgetMain.fdDetailList')}
                                            </div>
                                            <div layout="right">${lfn:message('fssc-budget:fssc.budget.detail.view')}
                                            </div>
                                        </div>
                                        <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdDetailList_Form_view">
                                            <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                <div class="muiHeaderDetailTitle">${lfn:message('fssc-budget:fsscBudgetMain.fdDetailList')}
                                                </div>
                                                <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdDetailList_Form')">
                                                    <bean:message key="button.save" />
                                                </div>
                                            </div>
                                            <br/>
                                            <br/>
                                            <table cellspacing="0" cellpadding="0" class="detailTableSimple">
                                                <tr>
                                                    <td class="detailTableSimpleTd">
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdDetailList_Form">
                                                            <tr style="display:none;">
                                                                <td>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <br/>
                                                    </td>
                                                </tr>
                                            </table>
                                            <br/>
                                            <br/>
                                        </div>
                                        <input type="hidden" name="fdDetailList_Flag" value="1">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdDesc')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdDesc" _xform_type="textarea">
                                            <xform:textarea property="fdDesc" showStatus="edit" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.docCreator')}
                                    </td>
                                    <td>
                                        <div id="_xform_docCreatorId" _xform_type="address">
                                            <ui:person personId="${fsscBudgetMainForm.docCreatorId}" personName="${fsscBudgetMainForm.docCreatorName}" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.docCreateTime')}
                                    </td>
                                    <td>
                                        <div id="_xform_docCreateTime" _xform_type="datetime">
                                            <xform:datetime property="docCreateTime" showStatus="view" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
                    <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:2,onClick:function(){submitFormValidate();}">
                        <bean:message key="button.submit" />
                    </li>
                </ul>
            </div>
            <html:hidden property="fdId" />
            <html:hidden property="method_GET" />


        </html:form>
    </template:replace>
</template:include>
