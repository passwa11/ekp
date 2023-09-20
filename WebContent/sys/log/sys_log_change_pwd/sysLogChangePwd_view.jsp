<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-log" key="table.sysLogChangePwd"/></div>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="sysLogChangePwdForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogApp.fdCreateTime"/>
		</td><td width=35%>
			<bean:write name="sysLogChangePwdForm" property="fdCreateTime"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogApp.fdIp"/>
		</td><td width=35%>
			<bean:write name="sysLogChangePwdForm" property="fdIp"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogApp.fdOperator"/>
		</td><td width=35%>
			<bean:write name="sysLogChangePwdForm" property="fdOperator"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogApp.fdOperatorId"/>
		</td><td width=35%>
			<bean:write name="sysLogChangePwdForm" property="fdOperatorId"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogOrganization.fdBrowser"/>
		</td><td width=35%> 
			<bean:write name="sysLogChangePwdForm" property="fdBrowser"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogOrganization.fdEquipment"/>
		</td><td width=35%> 
			<bean:write name="sysLogChangePwdForm" property="fdEquipment"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogOrganization.fdLocation"/>
		</td><td width=35%> 
			<bean:write name="sysLogChangePwdForm" property="fdLocation"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogOrganization.fdDetails"/>
		</td><td width=35%> 
			<bean:write name="sysLogChangePwdForm" property="fdDetails"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>