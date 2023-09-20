<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv"><!-- 
<kmss:auth
	requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=edit&fdId=${param.fdId}"
	requestMethod="GET">
	<input type="button" value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysSmsMain.do?method=edit&fdId=${JsParam.fdId}','_self');">
</kmss:auth> <kmss:auth
	requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=delete&fdId=${param.fdId}"
	requestMethod="GET">
	<input type="button" value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysSmsMain.do?method=delete&fdId=${JsParam.fdId}','_self');">
</kmss:auth> 
 --> <input type="button" value="<bean:message key="button.close"/>"
	onclick="Com_CloseWindow();"></div>
<p class="txttitle"><bean:message bundle="sys-sms"
	key="table.sysSmsMain" /></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="table.sysSmsRecPerson" /></td>
		<td colspan="3"><kmss:showText
			value="${sysSmsMainForm.fdRecPersonNames }" /></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsMain.fdReceiverNumber" /></td>
		<td colspan="3"><kmss:showText
			value="${sysSmsMainForm.fdMobileNo }" /></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsMain.docContent" /></td>
		<td colspan="3"><c:out value="${sysSmsMainForm.docContent}" /></td>
	</tr>
	<tr>
	<%--
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsMain.fdNotifyType" /></td>
		<td width=35%><kmss:showNotifyType
			value="${sysSmsMainForm.fdNotifyType}" /></td> --%>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsMain.fdCreatorId" /></td>
		<td colspan="3"><c:out value="${sysSmsMainForm.fdCreatorName}" />
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsMain.docCreateTime" /></td>
		<td width=35%><c:out value="${sysSmsMainForm.docCreateTime}" /></td>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-sms" key="sysSmsMain.docStatus" /></td>
		<td width=35%><sunbor:enumsShow
			value="${sysSmsMainForm.docStatus}"
			enumsType="sysSmsMainDocStatus" bundle="sys-sms" /></td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
