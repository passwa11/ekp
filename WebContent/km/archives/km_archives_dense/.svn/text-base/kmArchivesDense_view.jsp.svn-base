<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
<div id="optBarDiv">
    <kmss:auth requestURL="/km/archives/km_archives_dense/kmArchivesDense.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('kmArchivesDense.do?method=edit&fdId=${param.fdId}','_self');">
    </kmss:auth>
    <kmss:auth requestURL="/km/archives/km_archives_dense/kmArchivesDense.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('kmArchivesDense.do?method=delete&fdId=${param.fdId}','_self');">
    </kmss:auth>
    <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
</div>
<p class="txttitle">${ lfn:message('km-archives:table.kmArchivesDense') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('km-archives:kmArchivesDense.fdName')}
                </td>
                <td width="35%">
                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('km-archives:kmArchivesDense.fdOrder')}
                </td>
                <td width="35%">
                    <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('km-archives:kmArchivesDense.docCreator')}
                </td>
                <td width="35%">
                	<c:out value="${kmArchivesDenseForm.docCreatorName}"></c:out>
                    <%-- <ui:person personId="${kmArchivesDenseForm.docCreatorId}" personName="${kmArchivesDenseForm.docCreatorName}" /> --%>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('km-archives:kmArchivesDense.docCreateTime')}
                </td>
                <td width="35%">
                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                </td>
            </tr>
        </table>
    </div>
</center>
<script>
    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>