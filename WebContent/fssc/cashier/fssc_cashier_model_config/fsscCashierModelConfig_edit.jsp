<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/cashier/fssc_cashier_model_config/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/cashier/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/cashier/fssc_cashier_model_config/fsscCashierModelConfig.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscCashierModelConfigForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscCashierModelConfigForm, 'update');">
            </c:when>
            <c:when test="${fsscCashierModelConfigForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscCashierModelConfigForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-cashier:table.fsscCashierModelConfig') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 模块名--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.fdModelName')}
                    </td>
                    <td width="35%">
                        <%-- 模块modelNAme--%>
                        <div id="_xform_fdModelName" _xform_type="text">
                            <xform:text property="fdModelName" showStatus="edit" style="width:95%;" required="true" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.fdCategoryName')}
                    </td>
                    <td width="35%">
                        <%-- 对应分类或者模版的model--%>
                        <div id="_xform_fdCategoryName" _xform_type="text">
                            <xform:text property="fdCategoryName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.fdCategoryPropertyName')}
                    </td>
                    <td width="35%">
                        <%-- 对应分类或者模版的字段名--%>
                        <div id="_xform_fdCategoryPropertyName" _xform_type="text">
                            <xform:text property="fdCategoryPropertyName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.fdPath')}
                    </td>
                    <td width="35%" colspan="3">
                        <%-- 模块路径--%>
                        <div id="_xform_fdPath" _xform_type="text">
                            <xform:text property="fdPath" showStatus="edit" style="width:40%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscCashierModelConfigForm.docCreatorId}" personName="${fsscCashierModelConfigForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.docAlteror')}
                    </td>
                    <td width="35%">
                        <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${fsscCashierModelConfigForm.docAlterorId}" personName="${fsscCashierModelConfigForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-cashier:fsscCashierModelConfig.docAlterTime')}
                    </td>
                    <td width="35%">
                        <%-- 更新时间--%>
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
<%@ include file="/resource/jsp/edit_down.jsp" %>
