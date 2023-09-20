<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<% 
	//唯一标示Id,防止页面出现多个portlet时Id冲突
	String luiId=request.getParameter("LUIID").replace("-","");
	request.setAttribute("pageId","calendar_" + luiId);
%>
<div id="${lfn:escapeHtml(pageId)}" class="lui_calendar_portlet_month"></div>
<script type="text/javascript">
seajs.use( [ 'km/calendar/resource/css/calendar_portlet_month.css']);
seajs.use(['lui/jquery','km/calendar/resource/js/calendar_portlet_month'],function($,PortletCalendar){
	$(function(){
		//参数设置
		var config = {
			id : '${lfn:escapeJs(pageId)}',
			//根节点,JQuery表达式写法 *
			element : $('#${lfn:escapeJs(pageId)}'),
			rowsize : '${JsParam.rowsize}'
		};
		var __calendar_portlet_month = new PortletCalendar.CalendarPortlet(config);
		__calendar_portlet_month.draw();
		window.calendarPortletMonthOpen = function(url,method,type){
			__calendar_portlet_month.openCalendarDialog(url,method,type);
		};
	});
});
</script>