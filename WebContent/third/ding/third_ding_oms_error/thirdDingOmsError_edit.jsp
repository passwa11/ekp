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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_oms_error/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ding/third_ding_oms_error/thirdDingOmsError.do">
    <div id="optBarDiv">
        <%-- <c:choose>
            <c:when test="${thirdDingOmsErrorForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.thirdDingOmsErrorForm, 'update');">
            </c:when>
            <c:when test="${thirdDingOmsErrorForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.thirdDingOmsErrorForm, 'save');">
            </c:when>
        </c:choose> --%>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingOmsError') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdOms')}
                    </td>
                    <td width="35%">
                        <%-- 同步方--%>
                        <div id="_xform_fdOms" _xform_type="radio">
                            <xform:radio property="fdOms" htmlElementProperties="id='fdOms'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_oms" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdOper')}
                    </td>
                    <td width="35%">
                        <%-- 操作标志--%>
                        <div id="_xform_fdOper" _xform_type="radio">
                            <xform:radio property="fdOper" htmlElementProperties="id='fdOper'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_opertype" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdEkpId')}
                    </td>
                    <td width="35%">
                        <%-- EKP组织--%>
                        <div id="_xform_fdEkpId" _xform_type="text">
                            <xform:text property="fdEkpId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdDingId')}
                    </td>
                    <td width="35%">
                        <%-- 钉钉组织--%>
                        <div id="_xform_fdDingId" _xform_type="text">
                            <xform:text property="fdDingId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdEkpType')}
                    </td>
                    <td width="35%">
                        <%-- EKP组织类型--%>
                        <div id="_xform_fdEkpType" _xform_type="radio">
                            <xform:radio property="fdEkpType" htmlElementProperties="id='fdEkpType'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_ekptype" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdDingType')}
                    </td>
                    <td width="35%">
                        <%-- 钉钉组织类型--%>
                        <div id="_xform_fdDingType" _xform_type="radio">
                            <xform:radio property="fdDingType" htmlElementProperties="id='fdDingType'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_dingtype" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdEkpName')}
                    </td>
                    <td width="35%">
                        <%-- EKP组织名称--%>
                        <div id="_xform_fdEkpName" _xform_type="text">
                            <xform:text property="fdEkpName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdDingName')}
                    </td>
                    <td width="35%">
                        <%-- 钉钉组织名称--%>
                        <div id="_xform_fdDingName" _xform_type="text">
                            <xform:text property="fdDingName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOmsError.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 异常描述--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
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