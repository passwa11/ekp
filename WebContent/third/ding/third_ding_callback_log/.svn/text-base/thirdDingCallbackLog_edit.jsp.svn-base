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
    if("${thirdDingCallbackLogForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('third-ding:table.thirdDingCallbackLog') }";
    }
    if("${thirdDingCallbackLogForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('third-ding:table.thirdDingCallbackLog') }";
    }
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_callback_log/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ding/third_ding_callback_log/thirdDingCallbackLog.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${thirdDingCallbackLogForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingCallbackLogForm, 'update');}">
            </c:when>
            <c:when test="${thirdDingCallbackLogForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingCallbackLogForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingCallbackLog') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingCallbackLog.fdEventType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 事件类型--%>
                        <div id="_xform_fdEventType" _xform_type="text">
                            <xform:text property="fdEventType" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingCallbackLog.docContent')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 回调内容--%>
                        <div id="_xform_docContent" _xform_type="rtf">
                            <xform:rtf property="docContent" showStatus="edit" width="95%" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingCallbackLog.fdIsSuccess')}
                    </td>
                    <td width="35%">
                        <%-- 回调是否成功--%>
                        <div id="_xform_fdIsSuccess" _xform_type="radio">
                            <xform:radio property="fdIsSuccess" htmlElementProperties="id='fdIsSuccess'" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingCallbackLog.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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