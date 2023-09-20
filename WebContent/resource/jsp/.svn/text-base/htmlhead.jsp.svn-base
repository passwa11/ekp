<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ include file="/resource/jsp/common.jsp"%>
<!DOCTYPE html>
<%@ page import="javax.servlet.http.Cookie, com.landray.kmss.util.*,com.landray.kmss.web.filter.ResourceCacheFilter,com.landray.kmss.sys.recycle.util.SysRecycleUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-Cache">
<script type="text/javascript">
<%
	String KMSS_Parameter_Style = "default";
	request.setAttribute("KMSS_Parameter_Style", KMSS_Parameter_Style);
	String KMSS_Parameter_ContextPath = request.getContextPath()+"/";
	request.setAttribute("KMSS_Parameter_ContextPath", KMSS_Parameter_ContextPath);
	String KMSS_Parameter_ResPath = KMSS_Parameter_ContextPath+"resource/";
	request.setAttribute("KMSS_Parameter_ResPath", KMSS_Parameter_ResPath);
	String KMSS_Parameter_StylePath = KMSS_Parameter_ResPath + "style/"+KMSS_Parameter_Style+"/";
	request.setAttribute("KMSS_Parameter_StylePath", KMSS_Parameter_StylePath);
	request.setAttribute("KMSS_Parameter_CurrentUserId", UserUtil.getKMSSUser(request).getUserId());
	request.setAttribute("LUI_Cache",ResourceCacheFilter.cache);
	
	SysAgendaBaseConfig calendarConfig = new SysAgendaBaseConfig();
	
	String kmssConfigStartdate = calendarConfig.getCalendarWeekStartDate();
	if(StringUtil.isNull(kmssConfigStartdate)){
		kmssConfigStartdate = "1";
	}
	
	String kmssConfigCalendarMinuteStep = calendarConfig.getCalendarMinuteStep();
	if(StringUtil.isNull(kmssConfigCalendarMinuteStep)){
		kmssConfigCalendarMinuteStep = "5";
	}
	
%>
var Com_Parameter = {
	ContextPath:"${KMSS_Parameter_ContextPath}",
	ResPath:"${KMSS_Parameter_ResPath}",
	Style:"${KMSS_Parameter_Style}",
	JsFileList:new Array,
	StylePath:"${KMSS_Parameter_StylePath}",
	Lang:"<%= ResourceUtil.getLocaleStringByUser(request) %>",
	CurrentUserId:"${KMSS_Parameter_CurrentUserId}",
	Cache:${LUI_Cache},
	FirstDayInWeek:parseInt('<%=kmssConfigStartdate%>') - 1,
	CalendarMinuteStep:parseInt('<%=kmssConfigCalendarMinuteStep%>'),
	SessionExpireTip:"<%= ResourceUtil.getString("session.expire.tip") %>",
	ServiceNotAvailTip:"<%= ResourceUtil.getString("service.notAvail.tip") %>",
	Date_format:"<%= ResourceUtil.getString("date.format.date") %>",
	DateTime_format:"<%= ResourceUtil.getString("date.format.datetime") %>",
	SoftDeleteEnableModules:"<%= SysRecycleUtil.getEnableModules() %>",
	ComfirmDelete:"<%= ResourceUtil.getString("page.comfirmDelete") %>",
	ComfirmSoftDelete:"<%= SysRecycleUtil.getComfirmSoftDeleteInfo() %>",
	OptSuccessInfo:"<%= ResourceUtil.getString("return.optSuccess") %>",
	OptFailureInfo:"<%= ResourceUtil.getString("return.optFailure") %>",
	serverPrefix : "<%= ResourceUtil.getKmssConfigString("kmss.urlPrefix") %>",
	isSysMouring : "<%=SysUiConfigUtil.getFdIsSysMourning()%>",
    YearMonth_format:"<%= ResourceUtil.getString("date.format.yearMonth") %>",
    Year_format:"<%= ResourceUtil.getString("date.format.year") %>"

};
</script>
<link rel="shortcut icon" href="${KMSS_Parameter_ContextPath}favicon.ico"> 
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>

<%@ include file="/resource/jsp/watermarkPc.jsp" %>