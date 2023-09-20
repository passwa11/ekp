<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<template:include ref="mobile.view" compatibleMode="true">
    <template:replace name="title">
        <c:out value="${fsscBudgetMainForm.fdDesc} - " />
        <c:out value="${lfn:message('fssc-budget:table.fsscBudgetMain') }" />
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
        <html:form action="/fssc/budget/fssc_budget_main/fsscBudgetMain.do">

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
                                            <c:out value="${fsscBudgetMainForm.fdCompanyName}" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdYear')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdYear" _xform_type="text">
                                            <xform:text property="fdYear" showStatus="view" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdBudgetScheme')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdBudgetSchemeId" _xform_type="select">
                                            <xform:select property="fdBudgetSchemeId" htmlElementProperties="id='fdBudgetSchemeId'" showStatus="view" mobile="true">
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
                                            <c:out value="${fsscBudgetMainForm.fdCurrencyName}" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdEnableDate')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdEnableDate" _xform_type="datetime">
                                            <xform:datetime property="fdEnableDate" showStatus="view" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdDetailList_Form","scrollView");}'>
                                            <div layout="left">${lfn:message('fssc-budget:table.fsscBudgetDetail')}</div>
                                            <div layout="right">${lfn:message('fssc-budget:fssc.budget.detail.view')}
                                            </div>
                                        </div>
                                        <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdDetailList_Form_view">
                                            <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                <div class="muiHeaderDetailTitle">${lfn:message('fssc-budget:table.fsscBudgetDetail')}
                                                </div>
                                                <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdDetailList_Form')">
                                                    <bean:message key="button.back" />
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
                                                            <c:forEach items="${fsscBudgetMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
                                                                <tr KMSS_IsContentRow="1">
                                                                    <td class="detail_wrap_td">
                                                                        <xform:text showStatus="noShow" property="fdDetailList_Form[${vstatus.index}].fdId" />
                                                                        <table class="muiSimple">
                                                                            <tr>
                                                                                <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                    <span>${lfn:message('page.the')}${vstatus.index+1}${ lfn:message('page.row') }</span>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </table>
                                                        <br/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.fdDesc')}
                                    </td>
                                    <td>
                                        <div id="_xform_fdDesc" _xform_type="textarea">
                                            <xform:textarea property="fdDesc" showStatus="view" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.docCreator')}
                                    </td>
                                    <td>
                                        <ui:person personId="${fsscBudgetMainForm.docCreatorId}" personName="${fsscBudgetMainForm.docCreatorName}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('fssc-budget:fsscBudgetMain.docCreateTime')}
                                    </td>
                                    <td>
                                        <c:out value="${fsscBudgetMainForm.docCreateTime}"></c:out>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'></ul>
            </div>

            <script type="text/javascript">
                require(["mui/form/ajax-form!fsscBudgetMainForm"]);
            </script>
        </html:form>
    </template:replace>
</template:include>
