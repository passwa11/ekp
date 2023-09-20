<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String[] weekArray = ResourceUtil.getString("date.shortWeekDayNames").replaceAll("\"", "").split(",");
	request.setAttribute("weekArray", weekArray);
%>
{$
<body>
<div class="lui_calendar_portlet_dirction">
    <div class="lui_calendar_portlet_dirction_left">
      <div class="lui_tcp_dateShow">
        <div class="lui_tcp_dateNumber">
          <span class="lui_tcp_dateNumber_currentDay" data-lui-mark="calendar_datetxt"></span>
          <div class="lui_tcp_dateNumber_box">
            <i class="lui_tcp_ds--prevButton" data-lui-mark="calendar_pre"></i>
         	<span data-lui-mark="calendar_yearmonth"></span>
        	 <i class="lui_tcp_ds--nextButton" data-lui-mark="calendar_next"></i>
          </div>
        </div>
      </div>
      <!-- 日程时间轴 -->
       <div class="lui_calendar_portlet_dirction_data"  data-lui-mark="calendar_footer_error">
        <ul class="lui_tct_ul" data-lui-mark="calendar_footer">
        </ul>
      </div>
    </div>
    <div class="lui_calendar_portlet_dirction_right">
      <div class="lui_calendar_dirction_content">
        <!-- 日历日程头部--选择 -->
        <div class="lui_calendar_direction_header">
          <div class="lui_tch_button">
	         <a class="lui_tch_currentDate" data-lui-mark="calendar_today" href="javaScript:void(0)">今</a>
	         <a class="lui_tch_renovate" data-lui-mark="calendar_refresh" href="javaScript:void(0)"></a>
   		 </div>
        </div>
        <!-- 日历日程主体 -->
        <div class="lui_calendar_dirction_main">
          <div class="lui_tcd_shadeBox">
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
								<tr class="lui_tcd_table--tr first">
							</c:when>
							<c:when test="${status.last == true}">
								<tr class="lui_tcd_table--tr last">
							</c:when>
							<c:otherwise>
								<tr class="lui_tcd_table--tr">
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

  </div>
  </body>
$}