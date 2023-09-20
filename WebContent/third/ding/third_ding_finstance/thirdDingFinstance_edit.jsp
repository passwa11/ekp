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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_finstance/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ding/third_ding_finstance/thirdDingFinstance.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${thirdDingFinstanceForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.thirdDingFinstanceForm, 'update');">
            </c:when>
            <c:when test="${thirdDingFinstanceForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.thirdDingFinstanceForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingFinstance') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdTemplateId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdTemplateId" _xform_type="text">
                            <xform:text property="fdTemplateId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdModelId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdModelId" _xform_type="text">
                            <xform:text property="fdModelId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdModelName')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdModelName" _xform_type="text">
                            <xform:text property="fdModelName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdEkpStatus')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdEkpStatus" _xform_type="text">
                            <xform:text property="fdEkpStatus" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdStartFlow')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdStartFlow" _xform_type="radio">
                            <xform:radio property="fdStartFlow" htmlElementProperties="id='fdStartFlow'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_start_flow" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdProcessCode')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdProcessCode" _xform_type="text">
                            <xform:text property="fdProcessCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdInstanceId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdInstanceId" _xform_type="text">
                            <xform:text property="fdInstanceId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingFinstance.fdDingStatus')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdDingStatus" _xform_type="text">
                            <xform:text property="fdDingStatus" showStatus="edit" style="width:95%;" />
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