<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/core/log/tic_core_log_main/ticCoreLogMain.do">
<div id="optBarDiv">
	<c:if test="${ticCoreLogMainForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.ticCoreLogMainForm, 'update');">
	</c:if>
	<c:if test="${ticCoreLogMainForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.ticCoreLogMainForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.ticCoreLogMainForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-log" key="table.ticCoreLogMain"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdType"/>
		</td><td width="35%">
			<xform:text property="fdType" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdUrl"/>
		</td><td width="35%">
			<xform:text property="fdUrl" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdPoolName"/>
		</td><td width="35%">
			<xform:text property="fdPoolName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdStartTime"/>
		</td><td width="35%">
			<xform:datetime property="fdStartTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdEndTime"/>
		</td><td width="35%">
			<xform:datetime property="fdEndTime" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdImportParOri"/>
		</td><td width="35%">
			<xform:rtf property="fdImportParOri" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdExportParOri"/>
		</td><td width="35%">
			<xform:rtf property="fdExportParOri" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdMessages"/>
		</td><td width="35%">
			<xform:textarea property="fdMessages" style="width:85%" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("calendar.js");
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>