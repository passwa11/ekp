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
	<kmss:auth requestURL="/tic/core/log/tic_core_log_opt/ticCoreLogOpt.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCoreLogOpt.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-log" key="table.ticCoreLogOpt"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogOpt.fdPerson"/>
		</td><td width="35%">
			<xform:text property="fdPerson" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogOpt.fdAlertTime"/>
		</td><td width="35%">
			<xform:datetime property="fdAlertTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogOpt.fdUrl"/>
		</td><td colspan="3" width="85%">
			<xform:text property="fdUrl" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogOpt.fdContent"/>
		</td><td colspan="3" width="85%">
			<xform:rtf property="fdContent" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>