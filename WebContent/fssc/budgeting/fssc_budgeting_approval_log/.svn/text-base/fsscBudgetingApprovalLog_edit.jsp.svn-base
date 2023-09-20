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
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_approval_log/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetingApprovalLogForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budgeting:table.fsscBudgetingApprovalLog') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetingApprovalLogForm.fdMainId} - " />
                <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingApprovalLog') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetingApprovalLogForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetingApprovalLogForm, 'update');" />
                </c:when>
                <c:when test="${ fsscBudgetingApprovalLogForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetingApprovalLogForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-budgeting:table.fsscBudgetingApprovalLog') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budgeting/fssc_budgeting_approval_log/fsscBudgetingApprovalLog.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-budgeting:py.JiBenXinXi') }" expand="true">
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.docSubject')}
                            </td>
                            <td width="35%">
                                <%-- 标题--%>
                                <div id="_xform_docSubject" _xform_type="text">
                                    <xform:text property="docSubject" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdMainId')}
                            </td>
                            <td width="35%">
                                <%-- 预算编制主表ID--%>
                                <div id="_xform_fdMainId" _xform_type="text">
                                    <xform:text property="fdMainId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdDetailId')}
                            </td>
                            <td width="35%">
                                <%-- 预算编制明细ID--%>
                                <div id="_xform_fdDetailId" _xform_type="text">
                                    <xform:text property="fdDetailId" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdOperator')}
                            </td>
                            <td width="35%">
                                <%-- 审批人--%>
                                <div id="_xform_fdOperatorId" _xform_type="address">
                                    <xform:address propertyId="fdOperatorId" propertyName="fdOperatorName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdApprovalType')}
                            </td>
                            <td width="35%">
                                <%-- 审批方式--%>
                                <div id="_xform_fdApprovalType" _xform_type="text">
                                    <xform:text property="fdApprovalType" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdApprovalTime')}
                            </td>
                            <td width="35%">
                                <%-- 审批时间--%>
                                <div id="_xform_fdApprovalTime" _xform_type="datetime">
                                    <xform:datetime property="fdApprovalTime" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
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
