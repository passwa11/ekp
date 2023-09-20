<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.kmCalendarShareGroupForm, 'deleteall');">
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
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="kmCalendarShareGroup.fdName">
					<bean:message bundle="km-calendar" key="kmCalendarShareGroup.fdName"/>
				</sunbor:column>
				<sunbor:column property="kmCalendarShareGroup.fdDescription">
					<bean:message bundle="km-calendar" key="kmCalendarShareGroup.fdDescription"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="kmCalendarShareGroup" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do" />?method=view&fdId=${kmCalendarShareGroup.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${kmCalendarShareGroup.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${kmCalendarShareGroup.fdName}" />
				</td>
				<td>
					<c:out value="${kmCalendarShareGroup.fdDescription}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>