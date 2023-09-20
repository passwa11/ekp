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
    if("${fsscCtripAppkeyTypeForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('fssc-ctrip:table.fsscCtripAppkeyType') }";
    }
    if("${fsscCtripAppkeyTypeForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('fssc-ctrip:table.fsscCtripAppkeyType') }";
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_appkey_type/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/ctrip/fssc_ctrip_appkey_type/fsscCtripAppkeyType.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscCtripAppkeyTypeForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripAppkeyTypeForm, 'update');}">
            </c:when>
            <c:when test="${fsscCtripAppkeyTypeForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripAppkeyTypeForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-ctrip:table.fsscCtripAppkeyType') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppkeyType.fdAppType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- appkey是否通用--%>
                        <div id="_xform_fdAppType" _xform_type="radio">
                            <xform:radio property="fdAppType" htmlElementProperties="id='fdAppType'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_ctrip_appkey_type" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppkeyType.fdIsOpenBudgetControl')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 是否启用携程预算控制--%>
                        <div id="_xform_fdIsOpenBudgetControl" _xform_type="radio">
                            <xform:radio property="fdIsOpenBudgetControl" htmlElementProperties="id='fdIsOpenBudgetControl'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_ctrip_is_enable_budget" />
                            </xform:radio>
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
