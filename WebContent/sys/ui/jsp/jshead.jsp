<%@page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil"%>
<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.util.*,com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@page import="com.landray.kmss.sys.recycle.util.SysRecycleUtil"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.SecureUtil"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.lang.String" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
<%@ page import="com.landray.kmss.sys.ui.util.PCJsCompressUtil" %>
<%@ page import="com.landray.kmss.sys.appconfig.model.BaseAppConfig" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

<kmss:ifModuleExist  path="/third/pda">
	<c:import url="/sys/ui/jsp/mobileRedirect.jsp" charEncoding="UTF-8">
		<c:param name="mobileUrl" value="${param.mobileUrl}" />
		<c:param name="LUI_ContextPath" value="${LUI_ContextPath}" />
		<c:param name="LUI_Cache" value="${LUI_Cache}"/>
	</c:import>
</kmss:ifModuleExist>

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
	String smallMaxSize = "";
    if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"))){
    	smallMaxSize = ResourceUtil.getKmssConfigString("sys.att.smallMaxSize");
	}
	pageContext.setAttribute("smallMaxSize",smallMaxSize);

	//获取钉钉集成开关
	String attendanceEnableds ="";
	BaseAppConfig dingConfig =BaseAppConfig.getAppConfigByClassName("com.landray.kmss.third.ding.model.DingConfig");
	if(dingConfig!=null){
		attendanceEnableds = dingConfig.getDataMap().get("attendanceEnabled");
	}
	pageContext.setAttribute("attendanceEnabled",attendanceEnableds==null?"":attendanceEnableds);
%>

<%--常用多语言缓存--%>
<c:import url="/sys/ui/jsp/base_template_resource_cache.jsp"></c:import>

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
	Date_format_yearMonth:"<%= ResourceUtil.getString("date.format.yearMonth") %>",
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
	dingXForm : "<%=SysFormDingUtil.getEnableDing() %>",
	smallMaxSize : "${smallMaxSize}",
	attendanceEnabled : "${attendanceEnabled}",
	isFromDingPC : "<%=SysFormDingUtil.isFromPCBrowser(request)%>",
	possibleResourceSites : []
};

</script>
<!-- 是否开启合并加载js模式  -->
<c:choose>
	<c:when test="${compressSwitch eq 'true' && lfn:jsCompressEnabled('sysUiCompressExecutor', 'jsHead_com_combined')}">
		<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("sysUiCompressExecutor","jsHead_com_combined") %>?s_cache=${ LUI_Cache }">
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript" src='${LUI_ContextPath}/resource/js/domain.js?s_cache=${ LUI_Cache }'></script>
		<script type="text/javascript" src='${LUI_ContextPath}/sys/ui/js/LUI.js?s_cache=${ LUI_Cache }'></script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/common.js?s_cache=${ LUI_Cache }"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/sea.js?s_cache=${ LUI_Cache }"></script>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
seajs.config({
	themes : <%=SysUiPluginUtil.getThemes(request)%>,
	paths: {
		'lui': 'sys/ui/js'
	},
	alias: {
		'lui/jquery': 'resource/js/jquery',
		'lui/jquery-ui': 'resource/js/jquery-ui/jquery.ui'
	},
	preload: ['${LUI_ContextPath}/resource/js/plugin-theme.js','${LUI_ContextPath}/resource/js/plugin-lang.js'],
	debug: true,
 	base: '${LUI_ContextPath}',
 	env : {
 	 	contextPath: '${LUI_ContextPath}',
 		now : '<%= DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, request.getLocale()) %>',
 		pageMaxWidth:'${fdMaxWidth}',
 		pattern : {
 			date : '<%= ResourceUtil.getString("date.format.date", request.getLocale()) %>',
 			datetime : '<%= ResourceUtil.getString("date.format.datetime", request.getLocale()) %>',
 			time : '<%= ResourceUtil.getString("date.format.time", request.getLocale()) %>',
			yearMonth :"<%= ResourceUtil.getString("date.format.yearMonth", request.getLocale()) %>",
			year :"<%= ResourceUtil.getString("date.format.year", request.getLocale()) %>"
 		},
 		locale : "<%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(ResourceUtil.getLocaleStringByUser(request)) %>",
 		config : <%= JSONObject.fromObject(ResourceUtil.getKmssUiConfig()).toString() %>,
 		cache : ${LUI_Cache}
 	}
});
</script>

<!-- 是否开启合并加载js模式  -->
<c:choose>
	<c:when test="${compressSwitch eq 'true' && lfn:jsCompressEnabled('sysUiCompressExecutor', 'jshead_combined')}">
		<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("sysUiCompressExecutor","jshead_combined") %>?s_cache=${ LUI_Cache }">
		</script>
	</c:when>
</c:choose>

<c:if test="${KMSS_Parameter_ClientType=='-5'}">
		<script type="text/javascript" src="https://open-doc.welink.huaweicloud.com/docs/jsapi/2.0.3/hwh5-cloudonline.js"></script>
</c:if>


<c:choose>
	<c:when test="${KMSS_Parameter_ClientType=='-3'}">
		<script src="https://g.alicdn.com/dingding/dingtalk-jsapi/2.7.13/dingtalk.open.js"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/dingtalk-win.js?s_cache=${ LUI_Cache }"></script>
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/resource/style/ding-modal.css?s_cache=${LUI_Cache}" />
	</c:when>
	<c:when test="${KMSS_Parameter_ClientType=='-6'}">
		<script src="https://sf6-scmcdn-tos.pstatp.com/goofy/ee/lark/h5jssdk/js_sdk/h5-js-sdk-1.4.15.js"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/feishu-win.js?s_cache=${ LUI_Cache }"></script>
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/resource/style/ding-modal.css?s_cache=${LUI_Cache}" />
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			seajs.use([ 'lui/parser', 'lui/jquery', 'lui/dialog', 'theme!common', 'theme!icon','theme!iconfont' ],function(parser, $) {

                $.ajaxSettings.beforeSend=function(cb,cd){
                    try{
                        if(window.siteSelect){
                            this.url=siteSelect(this.url);
                        }
                    }catch(e){}
                };


				$(document).ready(function() {
					parser.parse();
				});
			});
		</script>
	</c:otherwise>
</c:choose>
<%
	Object titleImage = request.getSession().getAttribute("title_image");
	if(titleImage != null) {
		Object titleImageLoginUrl = request.getSession().getAttribute("title_image_login_url");
		String jsp = "";
		if(titleImageLoginUrl!=null){
			jsp = titleImageLoginUrl.toString();
		}else{
			jsp = LoginTemplateUtil.getLoginUrl();
			jsp = jsp.substring(1, jsp.lastIndexOf("."));
		}
%>
<link rel="shortcut icon" href="${LUI_ContextPath}<%=LoginTemplateUtil.getLoginImagePath() %>/<%=jsp%>/${title_image}">
<%}else { %>
<link rel="shortcut icon" href="${LUI_ContextPath}/favicon.ico">
<%}%>

<script type="text/javascript">
	// 获取地址本标题中显示的公司名称
	function getOrgName() {
		var orgName = "<%=com.landray.kmss.sys.config.util.LicenseUtil.get("license-address-title")%>";
		if(orgName == "null") {
			orgName = "";
		}
		if(orgName.length > 0) {
			orgName = "(" + orgName + ")";
		}
		return orgName;
	}
</script>

<%@ include file="/resource/jsp/watermarkPc.jsp" %>