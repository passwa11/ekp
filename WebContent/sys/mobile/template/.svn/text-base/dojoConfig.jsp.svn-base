<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%
	if("true".equals(ResourceUtil.getKmssConfigString("third.phone.dev.enabled"))){
		Date __refreshCache = new Date();
		request.setAttribute("MUI_DEV", "true");
		request.setAttribute("MUI_Cache", __refreshCache.getTime());
 	 }  

	SysAgendaBaseConfig calendarConfig = new SysAgendaBaseConfig();
	String firstDayInWeek = calendarConfig.getCalendarWeekStartDate();
	if(StringUtil.isNull(firstDayInWeek)) {
		firstDayInWeek = "1";
	}
	
	String minuteStep = calendarConfig.getCalendarMinuteStep();
	if(StringUtil.isNull(minuteStep)){
		minuteStep = "1";
	}

%>
<script type="text/javascript">
var dojoConfig={parseOnLoad:false,async:true,dingXForm:'<%=SysFormDingUtil.getEnableDing()%>',baseUrl:'<%=request.getContextPath()%>/',locale:'<%= ResourceUtil.getLocaleStringByUser(request) %>',cacheBust:'s_cache=${MUI_Cache}',<%--MUI_Cache--%>Date_format:"<%= ResourceUtil.getString("date.format.date") %>",DateTime_format:"<%= ResourceUtil.getString("date.format.datetime") %>",Time_format:"<%= ResourceUtil.getString("date.format.time") %>",CurrentUserId:"<%= UserUtil.getKMSSUser(request).getUserId() %>",CurrentUserName:"<%= StringUtil.XMLEscape(UserUtil.getKMSSUser(request).getUserName()) %>",packages:[{name:"dojo",location:"<%=request.getContextPath()%>/sys/mobile/js/dojo"},{name:"dijit",location:"<%=request.getContextPath()%>/sys/mobile/js/dojo"},{name:"dojox",location:"<%=request.getContextPath()%>/sys/mobile/js/dojo"},{name:'mui',location:'<%=request.getContextPath()%>/sys/mobile/js/mui'},{name:'lib',location:'<%=request.getContextPath()%>/sys/mobile/js/lib'}],paths:{"mui/mui":"<%=request.getContextPath()%>/sys/mobile/js/mui/mui._min_"},calendar:{"firstDayInWeek":parseInt(<%=firstDayInWeek%>)-1,"minuteStep":parseInt(<%=minuteStep%>)}<kmss:ifModuleExist path="/sys/attend"><c:import url="/sys/attend/import/map_config.jsp" charEncoding="UTF-8"></c:import></kmss:ifModuleExist>};
</script>