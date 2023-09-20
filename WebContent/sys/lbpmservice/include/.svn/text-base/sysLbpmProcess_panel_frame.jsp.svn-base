<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil"%>
<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.util.*,com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@page import="com.landray.kmss.sys.recycle.util.SysRecycleUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.SecureUtil"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>

<%
	request.setAttribute("fdMaxWidth", SysUiConfigUtil.getFdMaxWidth());
	request.setAttribute("isEnabledEco", SysOrgEcoUtil.IS_ENABLED_ECO);
	SysAgendaBaseConfig sysAgendaBaseConfig = new SysAgendaBaseConfig();
	String startdate = sysAgendaBaseConfig.getCalendarWeekStartDate();
	if(StringUtil.isNull(startdate)){
		startdate = "1";
	}
	
	String calendarMinuteStep = sysAgendaBaseConfig.getCalendarMinuteStep();
	if(StringUtil.isNull(calendarMinuteStep)){
		calendarMinuteStep = "1";
	}
%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=5" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><bean:message key="lbpmProcess.extendRoleOptWindow.title" bundle="sys-lbpmservice"/></title>
		
		<script type="text/javascript">
			var Com_Parameter = {
				ContextPath:"${KMSS_Parameter_ContextPath}",
				ResPath:"${KMSS_Parameter_ResPath}",
				Style:"${KMSS_Parameter_Style}",
				JsFileList:new Array,
				StylePath:"${KMSS_Parameter_StylePath}",
				Lang:"<%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(ResourceUtil.getLocaleStringByUser(request)) %>",
				CurrentUserId:"${KMSS_Parameter_CurrentUserId}",
				Cache:${ LUI_Cache },
				FirstDayInWeek: parseInt('<%=startdate%>') - 1,
				CalendarMinuteStep:parseInt('<%=calendarMinuteStep%>'),
				SessionExpireTip:"<%= ResourceUtil.getString("session.expire.tip") %>",
				ServiceNotAvailTip:"<%= ResourceUtil.getString("service.notAvail.tip") %>",
				Date_format:"<%= ResourceUtil.getString("date.format.date") %>",
				DateTime_format:"<%= ResourceUtil.getString("date.format.datetime") %>",
				SoftDeleteEnableModules:"<%= SysRecycleUtil.getEnableModules() %>",
				ComfirmDelete:"<%= ResourceUtil.getString("page.comfirmDelete") %>",
				ComfirmSoftDelete:"<%= SysRecycleUtil.getComfirmSoftDeleteInfo() %>",
				OptSuccessInfo:"<%= ResourceUtil.getString("return.optSuccess") %>",
				OptFailureInfo:"<%= ResourceUtil.getString("return.optFailure") %>",
				clientType:"<%=MobileUtil.getClientType(request)%>",
				serverPrefix : "<%= ResourceUtil.getKmssConfigString("kmss.urlPrefix") %>",
				isSysMouring : "<%=SysUiConfigUtil.getFdIsSysMourning()%>",
				isEnabledEco: "<%=SysOrgEcoUtil.IS_ENABLED_ECO%>",
				isExternal : "<%=SysOrgEcoUtil.isExternal() %>",
				dingXForm : "<%=SysFormDingUtil.getEnableDing() %>"
			};
</script>


		<script src="<%=request.getContextPath() %>/resource/js/common.js"></script>
	</head>
	<frameset framespacing=1 bordercolor=#003048 frameborder=1 rows="*">
		<frame frameborder="0" noresize scrolling="yes" id="topFrame"
			src="<c:url value="/sys/lbpmservice/include/sysLbpmProcess_panel.jsp" />?modelId=${lfn:escapeHtml(param.modelId)}&formName=${lfn:escapeHtml(param.formName)}&roleType=${lfn:escapeHtml(param.roleType)}&operationType=${lfn:escapeHtml(param.operationType)}&docStatus=${lfn:escapeHtml(param.docStatus)}&notifyType=${lfn:escapeHtml(param.notifyType)}&modelClassName=${lfn:escapeHtml(param.modelName)}&selectedTaskId=${lfn:escapeHtml(param.selectedTaskId)}&isShowHisHandlerOpt=${lfn:escapeHtml(param.isShowHisHandlerOpt)}&modelingModelId=${lfn:escapeHtml(param.modelingModelId)}">
	</frameset>
</html>
