<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String[] weekArray = ResourceUtil.getString("date.shortWeekDayNames").replaceAll("\"", "").split(",");
	request.setAttribute("weekArray", weekArray);
%>
{$
<body>
<div class="lui_calendar_portlet_white">
    <div class="lui_calendar_portlet_white_left">
      <div class="lui_calendar_white_content">
        <!-- 日历日程头部--选择 -->
        <div class="lui_calendar_white_header">
          <div class="lui_calendar_header_date" data-lui-mark="calendar_datetxt"></div>
          <div class="lui_calendar_header_switch">
            <i class="lui_calendar_header_switch_prev" data-lui-mark="calendar_pre"></i>
            <span data-lui-mark="calendar_yearmonth"></span>
            <i class="lui_calendar_header_switch_next" data-lui-mark="calendar_next"></i>
          </div>
          <div class="lui_calendar_header_button">
            <a class="lui_currentDate" data-lui-mark="calendar_today" href="javaScript:void(0)">今</a>
            <a class="lui_refresh" data-lui-mark="calendar_refresh" href="javaScript:void(0)"></a>
          </div>
        </div>
        <!-- 日历日程主体 -->
        <div class="lui_calendar_white_main">
          <div class="lui_smd--shadeBox">
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
								<tr class="lui_smd_table--tr first">
							</c:when>
							<c:when test="${status.last == true}">
								<tr class="lui_smd_table--tr last">
							</c:when>
							<c:otherwise>
								<tr class="lui_smd_table--tr">
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
        </div>
      </div>
    </div>
    <div class="lui_calendar_portlet_white_right">
      <div class="lui_calendar_white_data" data-lui-mark="calendar_footer_error">
        <ul class="lui_smt_ul" data-lui-mark="calendar_footer">
        </ul>
      </div>
    </div>

  </div>
  </body>
$}