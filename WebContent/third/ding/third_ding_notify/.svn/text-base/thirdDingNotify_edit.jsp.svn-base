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
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_notify/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ding/third_ding_notify/thirdDingNotify.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${thirdDingNotifyForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.thirdDingNotifyForm, 'update');">
            </c:when>
            <c:when test="${thirdDingNotifyForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.thirdDingNotifyForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingNotify') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingNotify.fdDingUserId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdDingUserId" _xform_type="text">
                            <xform:text property="fdDingUserId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingNotify.fdRecordId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdRecordId" _xform_type="text">
                            <xform:text property="fdRecordId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingNotify.fdEkpUserId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdEkpUserId" _xform_type="text">
                            <xform:text property="fdEkpUserId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingNotify.fdParam1')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdParam1" _xform_type="text">
                            <xform:text property="fdParam1" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingNotify.fdParam2')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdParam2" _xform_type="text">
                            <xform:text property="fdParam2" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingNotify.fdModelId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdModelId" _xform_type="text">
                            <xform:text property="fdModelId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingNotify.fdModelName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdModelName" _xform_type="text">
                            <xform:text property="fdModelName" showStatus="edit" style="width:95%;" />
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