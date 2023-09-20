<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
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
    if("${eopBasedataAuthorizeForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataAuthorize') }";
    }
    if("${eopBasedataAuthorizeForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataAuthorize') }";
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_authorize/", 'js', true);
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataAuthorizeForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataAuthorizeForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataAuthorizeForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataAuthorizeForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataAuthorize') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('eop-basedata:eopBasedataAuthorize.fdDesc')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <%-- 说明--%>
	                        <div id="_xform_fdDesc" _xform_type="text">
	                            <xform:text property="fdDesc" showStatus="edit" validators="maxLength(200)" required="true" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAuthorize.fdAuthorizedBy')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 授权人--%>
                        <div id="_xform_fdAuthorizedById" _xform_type="address">
                        	<kmss:authShow roles="ROLE_EOPBASEDATA_ACCOUNT">
                        		<c:set var="account_auth" value="true"></c:set>
                        	</kmss:authShow>
                        	<c:choose>
                        		<c:when test="${account_auth=='true'}">
                        			<xform:address propertyId="fdAuthorizedById" propertyName="fdAuthorizedByName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataAuthorize.fdAuthorizedBy')}" style="width:95%;" />
                        		</c:when>
                        		<c:otherwise>
                        			<xform:address propertyId="fdAuthorizedById" propertyName="fdAuthorizedByName" orgType="ORG_TYPE_PERSON" showStatus="readOnly" required="true" subject="${lfn:message('eop-basedata:eopBasedataAuthorize.fdAuthorizedBy')}" style="width:95%;" />
                        		</c:otherwise>
                        	</c:choose>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAuthorize.fdToOrg')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 被授权人--%>
                        <div id="_xform_fdToOrgIds" _xform_type="address">
                            <xform:address propertyId="fdToOrgIds" propertyName="fdToOrgNames" mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" style="width:95%;" />
                            <br><span class="com_help">${lfn:message('eop-basedata:eopBasedataAuthorize.tips')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAuthorize.fdIsAvailable')}
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
                        ${lfn:message('eop-basedata:eopBasedataAuthorize.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataAuthorizeForm.docCreatorId}" personName="${eopBasedataAuthorizeForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAuthorize.docCreateTime')}
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
                        ${lfn:message('eop-basedata:eopBasedataAuthorize.docAlteror')}
                    </td>
                    <td width="35%">
                        <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataAuthorizeForm.docAlterorId}" personName="${eopBasedataAuthorizeForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAuthorize.docAlterTime')}
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
