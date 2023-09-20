<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv"><kmss:auth
	requestURL="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do?method=edit&fdId=${param.fdId}"
	requestMethod="GET">
	<input type="button" value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysSmsSortofpeople.do?method=edit&fdId=${JsParam.fdId}','_self');">
</kmss:auth> <kmss:auth
	requestURL="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do?method=delete&fdId=${param.fdId}"
	requestMethod="GET">
	<input type="button" value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysSmsSortofpeople.do?method=delete&fdId=${JsParam.fdId}','_self');">
</kmss:auth> <input type="button" value="<bean:message key="button.close"/>"
	onclick="Com_CloseWindow();"></div>
<p class="txttitle"><bean:message bundle="sys-sms"
	key="table.sysSmsSortofpeople" /></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsSortofpeopleForm.fdName" /></td>
		<td width=35%><c:out value="${sysSmsSortofpeopleForm.fdName}" /></td>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsSortedperson.fdSortedpersonsId" /></td>
		<td width=35%><c:out
			value="${sysSmsSortofpeopleForm.sortedPersonNames}" />
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsSortofpeople.docCreatorId" /></td>
		<td width=35%><c:out
			value="${sysSmsSortofpeopleForm.docCreatorName}" /></td>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsSortofpeople.docCreateTime" /></td>
		<td width=35%><c:out
			value="${sysSmsSortofpeopleForm.docCreateTime}" /></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsSortofpeople.fdOrder" /></td>
		<td width=35%><c:out value="${sysSmsSortofpeopleForm.fdOrder}" />
		</td>
		<td class="td_normal_title" width=15%>&nbsp;</td>
		<td width=35%>&nbsp;</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsSortofpeople.docContent" /></td>
		<td colspan="3"><c:out value="${sysSmsSortofpeople.docContent}" /></td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
