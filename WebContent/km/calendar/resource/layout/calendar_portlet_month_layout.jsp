<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String[] weekArray = ResourceUtil.getString("date.shortWeekDayNames").replaceAll("\"", "").split(",");
	request.setAttribute("weekArray", weekArray);
%>
{$
<div class="lui_calendar_portlet_month_header">
	<div class="lui_pch_title" data-lui-mark="calendar_datetxt"></div>
	<div class="lui_pch_date_switch">
         <i class="lui_pch_date_prevSelect" data-lui-mark="calendar_pre"></i>
         <span data-lui-mark="calendar_yearmonth"></span>
         <i class="lui_pc_date_nextSelect" data-lui-mark="calendar_next"></i>
    </div>
    <div class="lui_pch_button">
         <a class="lui_pch_currentDate" data-lui-mark="calendar_today" href="javaScript:void(0)"><c:out value="${lfn:message('km-calendar:calendar.today')}" /></a>
         <a class="lui_pch_renovate" data-lui-mark="calendar_refresh" href="javaScript:void(0)"></a>
    </div>
</div>
<div class="lui_calendar_portlet_month_main">
	<div class="lui_pcd_shadeBox">
		<table>
			<thead>
				<tr>
					<c:forEach	items="${weekArray}" var="week">
						<td data-lui-mark="calendar_weekName"></td>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach begin="0" end="5" varStatus="status">
					<c:choose>
						<c:when test="${status.first == true}">
							<tr class="lui_calendar_portlet_month_tr first">
						</c:when>
						<c:when test="${status.last == true}">
							<tr class="lui_calendar_portlet_month_tr last">
						</c:when>
						<c:otherwise>
							<tr class="lui_calendar_portlet_month_tr">
						</c:otherwise>
					</c:choose>
						<c:forEach begin="0" end="6" varStatus="innerStatus">
							<td class="" data-lui-mark="calendar_date"></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>	
		</table>	
	</div>
	<div class="lui_pcd_dropdowm_box">
		<div class="lui_pcd_dropdowm_button">
            <i></i>
          </div>
	</div>
</div>
<div class="lui_calendar_portlet_month_data">
	<ul class="lui_calendar_month_ul" data-lui-mark="calendar_footer"></ul>
</div>
<div class="lui_calendar_portlet_month_nodata" style="display:none;">
  <div class="lui_pce_iconStyle">
    <i class="lui_pce_is_emptyImg"></i>
    <div class="lui_pce_is_title">${lfn:message('km-calendar:calendar.portlet.month.nodata')}</div>
    <div class="lui_pce_is_text">${lfn:message('km-calendar:calendar.portlet.month.nodata.advice')}</div>
  </div>
</div>
$}