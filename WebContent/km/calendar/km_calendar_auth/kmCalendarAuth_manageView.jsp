<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<kmss:auth requestURL="/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('kmCalendarAuth.do?method=manageEdit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="km-calendar" key="sysCalendarShareGroup.persons"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdName"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="docCreateName" style="width:85%" />
		</td>
	</tr>
	<tr>
   		<%--允许以下人员创建我的日程安排--%>
   		<td width="28%" class="td_normal_title">
   			${lfn:message('km-calendar:sysCalendarShareGroup.canCreate')}
   		</td>
   		<td>
   			<xform:address propertyId="authEditorIds" propertyName="authEditorNames" 
   				mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="true" style="width:98%"/>
   		</td>
   	</tr>
   	<tr>
   		<%--允许以下人员阅读我的日程安排--%>
   		<td width="28%" class="td_normal_title">
   			${lfn:message('km-calendar:sysCalendarShareGroup.canRead')}
   		</td>
   		<td>
   			<xform:address propertyId="authReaderIds" propertyName="authReaderNames" 
   				mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="true" style="width:98%" />
   		</td>
   	</tr>
   	<tr>
   		<%--允许以下人员维护我的日程安排--%>
   		<td width="28%" class="td_normal_title">
   			${lfn:message('km-calendar:sysCalendarShareGroup.canEdit')}
   		</td>
   		<td>
   			<xform:address propertyId="authModifierIds" propertyName="authModifierNames" 
   				mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="true" style="width:98%"/>
   		</td>
   	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>