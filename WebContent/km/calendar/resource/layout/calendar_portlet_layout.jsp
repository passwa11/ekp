<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String[] weekArray = ResourceUtil.getString("date.shortWeekDayNames").replaceAll("\"", "").split(",");
	request.setAttribute("weekArray", weekArray);
%>
{$
<div class="lui_calendar_portlet_header">
	<div class="lui_calendar_portlet_left" data-lui-mark="calendar_datetext"></div>
	<div class="lui_calendar_portlet_right">
		<div class="lui_calendar_portlet_opt">
			<div class="lui_calendar_portlet_today" data-lui-mark="calendar_today">
				<c:out value="${lfn:message('calendar.today')}" />
			</div>		
			<div class="lui_calendar_portlet_refresh" data-lui-mark="calendar_refresh">&nbsp;</div>
		</div>
		<div class="lui_calendar_portlet_monyear">
			<span class="lui_calendar_opt_pre" data-lui-mark="calendar_pre">&lt;</span>
			<span class="lui_calendar_portlet_monyeartext" data-lui-mark="calendar_monthyeartext">2015-12</span>  
			 <span class="lui_calendar_opt_next" data-lui-mark="calendar_next">&gt;</span>
		</div>		
	</div>
</div>
<div class="lui_calendar_portlet_body">
	<table class="lui_calendar_portlet_table">
		<thead class="lui_calendar_portlet_thead">
			<tr class="lui_calendar_portlet_tr">
				<c:forEach	items="${weekArray}" var="week">
					<th class="lui_calendar_portlet_td"  data-lui-mark="calendar_weekName"></th>
				</c:forEach>
			</tr>
		</thead>
		<tbody class="lui_calendar_portlet_tbody">
			<c:forEach begin="0" end="5" varStatus="status">
				<c:choose>
					<c:when test="${status.first == true}">
						<tr class="lui_calendar_portlet_tr first">
					</c:when>
					<c:when test="${status.last == true}">
						<tr class="lui_calendar_portlet_tr last">
					</c:when>
					<c:otherwise>
						<tr class="lui_calendar_portlet_tr">
					</c:otherwise>
				</c:choose>
					<c:forEach begin="0" end="6" varStatus="innerStatus">
						<td class="lui_calendar_portlet_td lui_calendar_portlet_date"  data-lui-mark="calendar_date"></td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>	
	</table>
</div>
<div class="lui_calendar_portlet_footer">
	<ul class="lui_calendar_portlet_footer_ul" data-lui-mark="calendar_footer"></ul>
</div>

$}