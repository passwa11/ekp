<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-lbpmext-authorize" key="table.lbpmAuthorizeLog"/></div>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="lbpmAuthorizeLogForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdCreateTime"/>
		</td><td width=35%>
			<bean:write name="lbpmAuthorizeLogForm" property="fdCreateTime"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdIp"/>
		</td><td width=35%>
			<bean:write name="lbpmAuthorizeLogForm" property="fdIp"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdOperator"/>
		</td><td width=35%>
			<bean:write name="lbpmAuthorizeLogForm" property="fdOperator"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdOperatorId"/>
		</td><td width=35%>
			<bean:write name="lbpmAuthorizeLogForm" property="fdOperatorId"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdParaMethod"/>
		</td><td width=35%>
			<% try{ %>
				<bean:message key="button.${sysLogAppForm.fdParaMethod}"/>
			<% }catch(Exception e){ %>
				<bean:write name="lbpmAuthorizeLogForm" property="fdParaMethod"/>
			<% } %>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdTargetId"/>
		</td><td width=35%>
			<bean:write name="lbpmAuthorizeLogForm" property="fdTargetId"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdUrl"/>
		</td><td colspan=3>
			<bean:write name="lbpmAuthorizeLogForm" property="fdUrl"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdMethod"/>
		</td><td width=35% colspan=3> 
			<bean:write name="lbpmAuthorizeLogForm" property="fdMethod"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdBrowser"/>
		</td><td width=35%> 
			<bean:write name="lbpmAuthorizeLogForm" property="fdBrowser"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdEquipment"/>
		</td><td width=35%> 
			<bean:write name="lbpmAuthorizeLogForm" property="fdEquipment"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorizeLog.fdDetails"/>
		</td><td width=35% colspan="3">
			<bean:write name="lbpmAuthorizeLogForm" property="fdDetails"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>