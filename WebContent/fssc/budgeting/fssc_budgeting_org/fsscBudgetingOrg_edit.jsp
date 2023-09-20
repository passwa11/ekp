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
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_org/", 'js', true);
            Com_IncludeFile("fsscBudgetingOrg.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_org/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetingOrgForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budgeting:table.fsscBudgetingOrg') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetingOrgForm.fdName} - " />
                <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingOrg') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetingOrgForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetingOrgForm, 'update');" />
                </c:when>
                <c:when test="${ fsscBudgetingOrgForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetingOrgForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-budgeting:table.fsscBudgetingOrg') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budgeting/fssc_budgeting_org/fsscBudgetingOrg.do">
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budgeting:fsscBudgetingOrg.fdName')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 名称--%>
                            <div id="_xform_fdName" _xform_type="text">
                                <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budgeting:fsscBudgetingOrg.fdOrgs')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 预算编制员工表--%>
                            <div id="_xform_fdOrgIds" _xform_type="address">
                                <xform:address propertyId="fdOrgIds" propertyName="fdOrgNames" mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingOrg.fdNotifyType')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdNotifyType" _xform_type="text">
                        <xform:checkbox property="fdNotifyType" subject="${lfn:message('fssc-budgeting:fsscBudgetingOrg.fdNotifyType')}" value="${fsscBudgetingOrgForm.fdNotifyType}">
                        	<c:if test="${todo}">
                        		<xform:simpleDataSource value="todo">${lfn:message('fssc-budgeting:enums.budgeting.notify_todo')}</xform:simpleDataSource>
                        	</c:if>
                        	<c:if test="${email}">
                        		<xform:simpleDataSource value="email">${lfn:message('fssc-budgeting:enums.budgeting.notify_email')}</xform:simpleDataSource>
                        	</c:if>
                        	<c:if test="${mobile}">
                        		<xform:simpleDataSource value="mobile">${lfn:message('fssc-budgeting:enums.budgeting.notify_message')}</xform:simpleDataSource>
                        	</c:if>
                        </xform:checkbox>
                    </div>
                </td>
            </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budgeting:fsscBudgetingOrg.docCreateTime')}
                        </td>
                        <td width="35%">
                            <%-- 创建时间--%>
                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budgeting:fsscBudgetingOrg.docCreator')}
                        </td>
                        <td width="35%">
                            <%-- 创建人--%>
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${fsscBudgetingOrgForm.docCreatorId}" personName="${fsscBudgetingOrgForm.docCreatorName}" />
                            </div>
                        </td>
                    </tr>
                </table>
            <html:hidden property="fdId" />

            <html:hidden property="method_GET" />
        </html:form>
    </template:replace>


</template:include>
