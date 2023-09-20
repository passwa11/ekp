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
	<kmss:auth requestURL="/tic/core/log/tic_core_log_main/ticCoreLogMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCoreLogMain.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-log" key="table.ticCoreLogMain"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.funcName"/>
		</td><td width="35%">
			<xform:text property="funcName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdUrl"/>
		</td><td width="35%">
			<xform:text property="fdUrl" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdLogType"/>
		</td><td width="35%">
			${ticCoreLogMainForm.displayTypeName}
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdExecSource"/>
		</td><td width="35%">
			${ticCoreLogMainForm.displayFdExecSource}
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdStartTime"/>
		</td><td width="35%">
			<xform:datetime property="fdStartTime" />
			
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdEndTime"/>
		</td><td width="35%"> 
			<xform:datetime property="fdEndTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdMessages"/>
		</td><td colspan="3" width="85%">
			<c:out value="${ticCoreLogMainForm.fdMessages }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdImportParOri"/>
		</td><td colspan="3" width="85%">
	        <c:out value="${ticCoreLogMainForm.fdImportParOri }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdExportParOri"/>
		</td><td colspan="3" width="85%">
		    <c:out value="${ticCoreLogMainForm.fdExportParOri }"></c:out>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdImportParTrans"/>
		</td><td colspan="3" width="85%">
			<c:out value="${ticCoreLogMainForm.fdImportParTrans }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdExportParTrans"/>
		</td><td colspan="3" width="85%">
		   	 <c:out value="${ticCoreLogMainForm.fdExportParTrans }"></c:out>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdSourceFuncInXml"/>
		</td><td colspan="3" width="85%">
		${ticCoreLogMainForm.fdSourceFuncInXml }
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdSourceFuncOutXml"/>
		</td><td colspan="3" width="85%">
		${ticCoreLogMainForm.fdSourceFuncOutXml }
		</td>
	</tr>
    <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdTimeConsuming"/>
		</td><td  width="35%">
			${ticCoreLogMainForm.fdTimeConsuming}ms
		</td>
		<c:if test="${!empty ticCoreLogMainForm.fdTimeConsumingOrg}">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdTimeConsumingOrg"/>
		</td><td  width="35%">
			${ticCoreLogMainForm.fdTimeConsumingOrg}ms
		</td>
		</c:if>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>