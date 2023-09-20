<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-log" key="table.sysLogFaileJob"/></div>
<center>
<table class="tb_normal" width=95%>
	<html:hidden name="sysLogFaileJobForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogFaileJob.docSubject"/>
		</td><td width=85% colspan="3">
			<bean:write name="sysLogFaileJobForm" property="docSubject"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogFaileJob.fdNotifyTargets"/>
		</td><td width=85% colspan="3">
			<bean:write name="sysLogFaileJobForm" property="fdNotifyTargets"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogFaileJob.docContent"/>
		</td><td width=85% colspan="3">
			<bean:write name="sysLogFaileJobForm" property="docContent" filter="false"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogFaileJob.fdNotifyType"/>
		</td><td width=35%>
			<bean:write name="sysLogFaileJobForm" property="fdNotifyType"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogFaileJob.docCreateTime"/>
		</td><td width=35%>
			<bean:write name="sysLogFaileJobForm" property="docCreateTime"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>