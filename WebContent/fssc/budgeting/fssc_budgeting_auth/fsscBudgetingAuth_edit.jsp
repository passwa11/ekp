<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

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
	        Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_auth/", 'js', true);
	        Com_IncludeFile("fsscBudgetingAuth.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_auth/", 'js', true);
	        Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
	        Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetingAuthForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budgeting:table.fsscBudgetingAuth') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetingAuthForm.fdName} - " />
                <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingAuth') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetingAuthForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetingAuthForm, 'update');" />
                </c:when>
                <c:when test="${ fsscBudgetingAuthForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetingAuthForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-budgeting:table.fsscBudgetingAuth') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do">
                <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdName')}
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
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdPersonList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 人员--%>
                        <div id="_xform_fdPersonListIds" _xform_type="address">
                            <xform:address propertyId="fdPersonListIds" propertyName="fdPersonListNames" mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdPersonList')}" textarea="true" style="width:95%;"
                            />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 说明--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdOrgList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 组织架构--%>
                        <div id="_xform_fdOrgListIds" _xform_type="address">
                            <xform:address validators="checkOrgDept" propertyId="fdOrgListIds" propertyName="fdOrgListNames" mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" textarea="true" style="width:95%;" />
                        <br><span class="com_help">${lfn:message('fssc-budgeting:dept.costcenter.tip')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 公司--%>
                         <div id="_xform_fdCompanyListIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdCompanyList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdCostCenterList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 成本中心--%>
                        <div id="_xform_fdCostCenterListIds" _xform_type="dialog">
                            <xform:dialog validators="checkOrgDept" propertyId="fdCostCenterListIds" propertyName="fdCostCenterListNames" subject="${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdCostCenterList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_cost_center_selectCostCenter','fdCostCenterListIds','fdCostCenterListNames');
                            </xform:dialog>
                            <br><span class="com_help">${lfn:message('fssc-budgeting:dept.costcenter.tip')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdBudgetItemList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 预算科目--%>
                        <div id="_xform_fdBudgetItemListIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdBudgetItemListIds" propertyName="fdBudgetItemListNames" subject="${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdBudgetItemList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_budget_item_fdBudgetItem','fdBudgetItemListIds','fdBudgetItemListNames');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdProjectList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 项目--%>
                        <div id="_xform_fdProjectListIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdProjectListIds" propertyName="fdProjectListNames" subject="${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdProjectList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_project_project','fdProjectListIds','fdProjectListNames',null,{'fdProjectType':'1'});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 是否有效--%>
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscBudgetingAuthForm.docCreatorId}" personName="${fsscBudgetingAuthForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.docAlteror')}
                    </td>
                    <td width="35%">
                        <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${fsscBudgetingAuthForm.docAlterorId}" personName="${fsscBudgetingAuthForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingAuth.docAlterTime')}
                    </td>
                    <td width="35%">
                        <%-- 更新时间--%>
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
            </table>
            <html:hidden property="fdId" />
            <html:hidden property="method_GET" />
            <script>
		        $KMSSValidation();
		    </script>
        </html:form>
    </template:replace>


</template:include>
