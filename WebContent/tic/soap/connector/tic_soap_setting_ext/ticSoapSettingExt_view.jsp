<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/tic/soap/connector/tic_soap_setting_ext/ticSoapSettingExt.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticSoapSettingExt.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/soap/connector/tic_soap_setting_ext/ticSoapSettingExt.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticSoapSettingExt.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-soap-connector" key="table.ticSoapSettingExt"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSettingExt.fdWsExtName"/>
		</td><td width="35%">
			<xform:text property="fdWsExtName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSettingExt.fdWsExtValue"/>
		</td><td width="35%">
			<xform:text property="fdWsExtValue" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSettingExt.fdServer"/>
		</td><td width="35%">
			<c:out value="${ticSoapSettingExtForm.fdServerName}" />
		</td>
		<td class="td_normal_title" width=15%>&nbsp;</td><td width=35%>&nbsp;</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
