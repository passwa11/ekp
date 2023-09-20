<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="net.sf.json.JSONObject"%>
define(function(require, exports, module) {
<%
	//设置缓存
	//long expires = 7 * 24 * 60 * 60;
	//long nowTime = System.currentTimeMillis();
	//response.addDateHeader("Last-Modified", nowTime + expires);
	//response.addDateHeader("Expires", nowTime + expires * 1000);
	//response.addHeader("Cache-Control", "max-age=" + expires);

	JSONObject json = new JSONObject();
	try {
		SysAgendaBaseConfig calendarConfig = new SysAgendaBaseConfig();

		//周起始日
		String firstDayInWeekStr = calendarConfig.getCalendarWeekStartDate();
		if(StringUtil.isNotNull(firstDayInWeekStr)){
			Integer firstDayInWeek = Integer.parseInt(firstDayInWeekStr) - 1;
			json.put("firstDayInWeek", firstDayInWeek);
		}
		//日历默认展现模式
		String displayView = calendarConfig.getCalendarDisplayType();
		if(StringUtil.isNotNull(displayView)){
			json.put("displayView", displayView);
		}
		out.print("module.exports = " + json.toString() + ";");
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
});
