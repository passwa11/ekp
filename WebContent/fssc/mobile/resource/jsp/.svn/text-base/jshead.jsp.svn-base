<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@page import="com.landray.kmss.sys.recycle.util.SysRecycleUtil"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
var Com_Parameter = {
	ContextPath:"${LUI_ContextPath}"||"${KMSS_Parameter_ContextPath}",
	ResPath:"${KMSS_Parameter_ResPath}",
	Style:"${KMSS_Parameter_Style}",
	JsFileList:new Array,
	StylePath:"${KMSS_Parameter_StylePath}",
	Lang:"<%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(ResourceUtil.getLocaleStringByUser(request)) %>",
	CurrentUserId:"${KMSS_Parameter_CurrentUserId}",
	Cache:${ LUI_Cache },
	SessionExpireTip:"<%= ResourceUtil.getString("session.expire.tip") %>",
	ServiceNotAvailTip:"<%= ResourceUtil.getString("service.notAvail.tip") %>",
	Date_format:"<%= ResourceUtil.getString("date.format.date") %>",
	DateTime_format:"<%= ResourceUtil.getString("date.format.datetime") %>",
	SoftDeleteEnableModules:"<%= SysRecycleUtil.getEnableModules() %>",
	ComfirmDelete:"<%= ResourceUtil.getString("page.comfirmDelete") %>",
	ComfirmSoftDelete:"<%= SysRecycleUtil.getComfirmSoftDeleteInfo() %>",
	OptSuccessInfo:"<%= ResourceUtil.getString("return.optSuccess") %>",
	OptFailureInfo:"<%= ResourceUtil.getString("return.optFailure") %>"
};
if(!/\/$/.test(Com_Parameter.ContextPath)){
	Com_Parameter.ContextPath=Com_Parameter.ContextPath+"/";
}
</script>
<script type="text/javascript" src='${LUI_ContextPath}/resource/js/domain.js?s_cache=${ LUI_Cache }'></script>
<script type="text/javascript" src='${LUI_ContextPath}/sys/ui/js/LUI.js?s_cache=${ LUI_Cache }'></script>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/common.js?s_cache=${ LUI_Cache }"></script>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/sea.js?s_cache=${ LUI_Cache }"></script>
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
 			time : '<%= ResourceUtil.getString("date.format.time", request.getLocale()) %>'
 		},
 		locale : "<%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(ResourceUtil.getLocaleStringByUser(request)) %>",
 		config : <%= JSONObject.fromObject(ResourceUtil.getKmssUiConfig()).toString() %>,
 		cache : ${LUI_Cache}
 	}
});
seajs.use([ 'lui/parser', 'lui/jquery'],
	function(parser, $) {
		$(document).ready(function() {
			parser.parse();
		});
	}); 
</script>
