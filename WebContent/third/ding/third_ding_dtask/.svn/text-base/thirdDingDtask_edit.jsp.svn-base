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
    if("${thirdDingDtaskForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('third-ding:table.thirdDingDtask') }";
    }
    if("${thirdDingDtaskForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('third-ding:table.thirdDingDtask') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_dtask/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ding/third_ding_dtask/thirdDingDtask.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${thirdDingDtaskForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingDtaskForm, 'update');}">
            </c:when>
            <c:when test="${thirdDingDtaskForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingDtaskForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingDtask') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.fdName')}
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
                        ${lfn:message('third-ding:thirdDingDtask.fdStatus')}
                    </td>
                    <td width="35%">
                        <%-- 发送状态--%>
                        <div id="_xform_fdStatus" _xform_type="radio">
                            <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_status" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.fdInstance')}
                    </td>
                    <td width="35%">
                        <%-- 所属待办实例--%>
                        <div id="_xform_fdInstanceId" _xform_type="select">
                            <xform:select property="fdInstanceId" htmlElementProperties="id='fdInstanceId'" showStatus="edit">
                                <xform:beanDataSource serviceBean="thirdDingDinstanceService" selectBlock="fdId,fdName" />
                            </xform:select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.fdDingUserId')}
                    </td>
                    <td width="35%">
                        <%-- 钉钉接收人--%>
                        <div id="_xform_fdDingUserId" _xform_type="text">
                            <xform:text property="fdDingUserId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.fdEkpUser')}
                    </td>
                    <td width="35%">
                        <%-- EKP人员--%>
                        <div id="_xform_fdEkpUserId" _xform_type="address">
                            <xform:address propertyId="fdEkpUserId" propertyName="fdEkpUserName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.fdTaskId')}
                    </td>
                    <td width="35%">
                        <%-- 任务Id--%>
                        <div id="_xform_fdTaskId" _xform_type="text">
                            <xform:text property="fdTaskId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.fdEkpTaskId')}
                    </td>
                    <td width="35%">
                        <%-- EKP任务Id--%>
                        <div id="_xform_fdEkpTaskId" _xform_type="text">
                            <xform:text property="fdEkpTaskId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.fdUrl')}
                    </td>
                    <td width="35%">
                        <%-- 任务地址--%>
                        <div id="_xform_fdUrl" _xform_type="text">
                            <xform:text property="fdUrl" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDtask.docCreateTime')}
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
                        ${lfn:message('third-ding:thirdDingDtask.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 发送详情--%>
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