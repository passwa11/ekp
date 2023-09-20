<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/km/calendar/km_calendar_auth/kmCalendarAuth.do">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>" onclick="Com_Submit(document.kmCalendarAuthForm, 'update');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="km-calendar" key="sysCalendarShareGroup.persons"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
   		<%--允许以下人员创建我的日程安排--%>
   		<td width="28%" class="td_normal_title">
   			${lfn:message('km-calendar:sysCalendarShareGroup.canCreate')}
   		</td>
   		<td>
   			<xform:address propertyId="authEditorIds" propertyName="authEditorNames" showStatus="edit"
   				mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="true" style="width:98%"/>
   		</td>
   	</tr>
   	<tr>
   		<%--允许以下人员阅读我的日程安排--%>
   		<td width="28%" class="td_normal_title">
   			${lfn:message('km-calendar:sysCalendarShareGroup.canRead')}
   		</td>
   		<td>
   			<xform:address propertyId="authReaderIds" propertyName="authReaderNames"  showStatus="edit"
   				mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="true" style="width:98%" />
   		</td>
   	</tr>
   	<tr>
   		<%--允许以下人员维护我的日程安排--%>
   		<td width="28%" class="td_normal_title">
   			${lfn:message('km-calendar:sysCalendarShareGroup.canEdit')}
   		</td>
   		<td>
   			<xform:address propertyId="authModifierIds" propertyName="authModifierNames"  showStatus="edit"
   				mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="true" style="width:98%"/>
   		</td>
   	</tr>
	
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="docCreateId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>