<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/sms/sys_sms_sorts/sysSmsSorts.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/sms/sys_sms_sorts/sysSmsSorts.do?method=add" requestMethod="GET">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/sms/sys_sms_sorts/sysSmsSorts.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/sms/sys_sms_sorts/sysSmsSorts.do?method=deleteall" requestMethod="GET">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysSmsSortsForm, 'deleteall');">
		</kmss:auth>
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
				<td width="40pt"><bean:message key="page.serial"/></td>
				<sunbor:column property="sysSmsSorts.fdName">
					<bean:message  bundle="sys-sms" key="sysSmsSorts.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysSmsSorts.docContent">
					<bean:message  bundle="sys-sms" key="sysSmsSorts.docContent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysSmsSorts" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/sms/sys_sms_sorts/sysSmsSorts.do" />?method=view&fdId=${sysSmsSorts.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysSmsSorts.fdId}">
				</td>
				<td>${vstatus.index+1}</td>
				<td>
					<c:out value="${sysSmsSorts.fdName}" />
				</td>
				<td>
					<c:out value="${sysSmsSorts.docContent}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
