<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
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
    
</style>
<script type="text/javascript">
    var editOption = {
        formName: 'kmArchivesUnitForm',
        modelName: 'com.landray.kmss.km.archives.model.KmArchivesUnit'


    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/km/archives/km_archives_unit/kmArchivesUnit.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${kmArchivesUnitForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.kmArchivesUnitForm, 'update');">
            </c:when>
            <c:when test="${kmArchivesUnitForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.kmArchivesUnitForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('km-archives:table.kmArchivesUnit') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('km-archives:kmArchivesUnit.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('km-archives:kmArchivesUnit.fdAdmin')}
                    </td>
                    <td width="35%">
                        <xform:address propertyId="fdAdminId" propertyName="fdAdminName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('km-archives:kmArchivesUnit.fdAdmin')}" style="width:95%;" />
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('km-archives:kmArchivesUnit.fdOrder')}
                    </td>
                    <td width="35%">
                        <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('km-archives:kmArchivesUnit.docCreator')}
                    </td>
                    <td width="35%">
                    	<c:out value="${kmArchivesUnitForm.docCreatorName}"></c:out>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('km-archives:kmArchivesUnit.docCreateTime')}
                    </td>
                    <td width="35%">
                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
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