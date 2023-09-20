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
    if("${fsscCtripModelForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('fssc-ctrip:table.fsscCtripModel') }";
    }
    if("${fsscCtripModelForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('fssc-ctrip:table.fsscCtripModel') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    var initData = {
        contextPath: '${LUI_ContextPath}'
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_model/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/ctrip/fssc_ctrip_model/fsscCtripModel.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscCtripModelForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripModelForm, 'update');}">
            </c:when>
            <c:when test="${fsscCtripModelForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripModelForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-ctrip:table.fsscCtripModel') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdName')}
                    </td>
                    <td width="85%" colspan="3">
                        <%-- 模块名--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdCateKey')}
                    </td>
                    <td width="35%">
                        <%-- 分类在主表的字段名--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdCateKey" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdKey')}
                    </td>
                    <td width="35%">
                        <%-- 模版对应的key--%>
                        <div id="_xform_fdKey" _xform_type="text">
                            <xform:text property="fdKey" showStatus="edit" style="width:95%;" />
                            <br><span class="com_help">${lfn:message('fssc-ctrip:fsscCtripModel.fdKey.tips')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdModelName')}
                    </td>
                    <td width="35%">
                        <%-- 模块modelName--%>
                        <div id="_xform_fdModelName" _xform_type="text">
                            <xform:text property="fdModelName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdCategoryName')}
                    </td>
                    <td width="35%">
                        <%-- 对应分类或者模版的model--%>
                        <div id="_xform_fdCategoryName" _xform_type="text">
                            <xform:text property="fdCategoryName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscCtripModelForm.docCreatorId}" personName="${fsscCtripModelForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.docCreateTime')}
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
                        ${lfn:message('fssc-ctrip:fsscCtripModel.docAlteror')}
                    </td>
                    <td width="35%">
                        <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${fsscCtripModelForm.docAlterorId}" personName="${fsscCtripModelForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.docAlterTime')}
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