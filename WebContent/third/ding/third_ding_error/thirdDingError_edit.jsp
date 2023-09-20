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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_error/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ding/third_ding_error/thirdDingError.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${thirdDingErrorForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.thirdDingErrorForm, 'update');">
            </c:when>
            <c:when test="${thirdDingErrorForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.thirdDingErrorForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingError') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdName')}
                    </td>
                    <td width="35.0%">
                        <%-- 功能名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
	                    ${lfn:message('third-ding:thirdDingError.fdCount')}
	                </td>
	                <td width="35.0%">
	                    <%-- 执行次数--%>
	                    <div id="_xform_fdCount" _xform_type="text">
	                        <xform:text property="fdCount" showStatus="edit" style="width:95%;" />
	                    </div>
	                </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdModelId')}
                    </td>
                    <td width="35%">
                        <%-- 业务Id--%>
                        <div id="_xform_fdModelId" _xform_type="text">
                            <xform:text property="fdModelId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdModelName')}
                    </td>
                    <td width="35%">
                        <%-- 业务模型名--%>
                        <div id="_xform_fdModelName" _xform_type="text">
                            <xform:text property="fdModelName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdContent')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 消息内容--%>
                        <div id="_xform_fdContent" _xform_type="text">
                            <xform:textarea property="fdContent" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdErrorMsg')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 错误消息--%>
                        <div id="_xform_fdErrorMsg" _xform_type="text">
                            <xform:textarea property="fdErrorMsg" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                 <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('third-ding:thirdDingError.fdHandleError')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <%-- 错误消息--%>
	                    <div id="_xform_fdHandleError" _xform_type="text">
	                        <xform:textarea property="fdHandleError" showStatus="edit" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdServiceName')}
                    </td>
                    <td width="35%">
                        <%-- 执行服务--%>
                        <div id="_xform_fdServiceName" _xform_type="text">
                            <xform:text property="fdServiceName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdServiceMethod')}
                    </td>
                    <td width="35%">
                        <%-- 执行方法--%>
                        <div id="_xform_fdServiceMethod" _xform_type="text">
                            <xform:text property="fdServiceMethod" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdMethodParam')}
                    </td>
                    <td width="35%">
                        <%-- 执行参数--%>
                        <div id="_xform_fdMethodParam" _xform_type="text">
                            <xform:text property="fdMethodParam" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingError.fdCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_fdCreateTime" _xform_type="datetime">
                            <xform:datetime property="fdCreateTime" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
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