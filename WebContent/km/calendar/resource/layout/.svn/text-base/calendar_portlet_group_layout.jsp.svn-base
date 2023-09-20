<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String[] weekArray = ResourceUtil.getString("date.shortWeekDayNames").replaceAll("\"", "").split(",");
	request.setAttribute("weekArray", weekArray);
%>
{$
<div class="lui_calendar_portlet_group">
		<table>
			<thead>
				<tr class="lui_group_header">
					<th>
						<div class="lui_pch_button">
							 <span class="lui_group_month" data-lui-mark="calendar_month"></span>
					         <a class="lui_pch_currentDate" data-lui-mark="calendar_today" href="javaScript:void(0)"><c:out value="${lfn:message('km-calendar:calendar.today')}" /></a>
					         <a class="lui_pch_renovate" data-lui-mark="calendar_refresh" href="javaScript:void(0)"></a>
					    </div>
						<div class="lui_pch_date_switch">
					         <i class="lui_pch_date_prevSelect" data-lui-mark="calendar_pre"></i>
					         <span data-lui-mark="calendar_yearweek"></span>
					         <i class="lui_pc_date_nextSelect" data-lui-mark="calendar_next"></i>
					    </div>
						
					</th>
					<c:forEach	items="${weekArray}" var="week">
						<th><span class="lui_group_weekName" data-lui-mark="calendar_weekName"></span><span class="lui_group_date" data-lui-mark="calendar_date"></span></th>
					</c:forEach>
					<th class="lui_group_weekNum" style="display:none">
						<div class="week_num_content">
							<span class="prev"></span><span class="week" data-lui-mark="calendar_weekNum"></span><span class="next"></span>
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr class="lui_group_nodata" style="display:none; background-color:#fcfcfc;">
					<td colspan="8">
						<div class="lui_pce_iconStyle">
						    <i class="lui_pce_is_emptyImg"></i>
						    <div class="lui_pce_is_title">暂无权限查看成员日程</div>
						  </div>
					</td>
				</tr>
				<tr class="lui_group_nogroup" style="display:none;">
					<td colspan="8">
						<div class="lui_pce_iconStyle">
						    <i class="lui_pce_is_emptyImg"></i>
						    <div class="lui_pce_is_title">暂无群组日程</div>
						  </div>
					</td>
				</tr>
			</tbody>	
		</table>	
</div>
$}