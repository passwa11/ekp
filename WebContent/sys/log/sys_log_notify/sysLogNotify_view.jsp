<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-log" key="table.sysLogSystem"/></div>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogNotify.fdSubject"/>
		</td>
		<td colspan=3>
			<c:out value="${sysLogNotify.fdSubject}"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogNotify.fdNotifyTargets"/>
		</td>
		<td colspan=3>
			<c:out value="${sysLogNotify.fdNotifyTargets}"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogNotify.fdDesc"/>
		</td>
		<td colspan=3>
			<c:out value="${sysLogNotify.fdDesc}"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogNotify.fdNotifyType"/>
		</td><td width=35%>
			<c:out value="${sysLogNotify.fdNotifyType}"/>
		</td>
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogNotify.fdCreateTime"/>
		</td><td width=35%>
			<fmt:formatDate value="${ sysLogNotify.fdCreateTime}"  type="both" /> 
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>