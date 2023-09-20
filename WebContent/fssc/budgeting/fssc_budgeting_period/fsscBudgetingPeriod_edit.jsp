<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.edit">
<template:replace name="head">
<link href="${LUI_ContextPath}/fssc/common/resource/css/jedate.css" rel="stylesheet" type="text/css"/>
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
    Com_IncludeFile("form.js|data.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_period/", 'js', true);
    Com_IncludeFile("fsscBudgetPeriod.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_period/", 'js', true);
    Com_IncludeFile("jedate.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
</template:replace>

  
    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetingPeriodForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budgeting:table.fsscBudgetingPeriod') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingPeriod') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetingPeriodForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetingPeriodForm, 'update');" />
                </c:when>
                <c:when test="${ fsscBudgetingPeriodForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetingPeriodForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
	<template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-budgeting:table.fsscBudgetingPeriod') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
<html:form action="/fssc/budgeting/fssc_budgeting_period/fsscBudgetingPeriod.do">
    <p class="txttitle">${ lfn:message('fssc-budgeting:table.fsscBudgetingPeriod') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdName')}
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
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdStartPeriod')}
                    </td>
                    <td width="35%">
                        <%-- 开始期间--%>
                        <div id="_xform_fdStartPeriod" _xform_type="text">
                            <xform:text property="fdStartPeriod" required="true" htmlElementProperties="id='fdStartPeriod'" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdEndPeriod')}
                    </td>
                    <td width="35%">
                        <%-- 结束期间--%>
                        <div id="_xform_fdEndPeriod" _xform_type="text">
                            <xform:text property="fdEndPeriod"  required="true"  htmlElementProperties="id='fdEndPeriod'" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdIsAvailable')}
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
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscBudgetingPeriodForm.docCreatorId}" personName="${fsscBudgetingPeriodForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docCreateTime')}
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
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docAlteror')}
                    </td>
                    <td width="35%">
                        <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${fsscBudgetingPeriodForm.docAlterorId}" personName="${fsscBudgetingPeriodForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docAlterTime')}
                    </td>
                    <td width="35%">
                        <%-- 更新时间--%>
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
</html:form>
</template:replace>
</template:include>
