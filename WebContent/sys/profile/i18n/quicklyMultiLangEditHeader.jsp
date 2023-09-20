<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.util.*,com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@page import="com.landray.kmss.sys.recycle.util.SysRecycleUtil"%>
<%@page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.util.SecureUtil"%>
<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@page import="com.landray.kmss.web.filter.ResourceCacheFilter"%>

<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

<%
if(ResourceUtil.isQuicklyEdit()){
	if(request.getAttribute("LUI_ContextPath")==null){
		request.setAttribute("fdPageMaxWidth", SysUiConfigUtil.getFdMaxWidth());
		String LUI_ContextPath = request.getContextPath();
		request.setAttribute("LUI_ContextPath", LUI_ContextPath);
		request.setAttribute("LUI_CurrentTheme",SysUiPluginUtil.getThemeById("default"));
		request.setAttribute("MUI_Cache",ResourceCacheFilter.mobileCache);
		request.setAttribute("LUI_Cache",ResourceCacheFilter.cache);
		request.setAttribute("KMSS_Parameter_Style", "default");
		request.setAttribute("KMSS_Parameter_ContextPath", LUI_ContextPath+"/");
		request.setAttribute("KMSS_Parameter_ResPath", LUI_ContextPath+"/resource/");
		request.setAttribute("KMSS_Parameter_StylePath", LUI_ContextPath+"/resource/style/default/");
		request.setAttribute("KMSS_Parameter_CurrentUserId", UserUtil.getKMSSUser(request).getUserId());
		request.setAttribute("KMSS_Parameter_Lang", UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-'));
		request.setAttribute("KMSS_Parameter_ClientType", MobileUtil.getClientType(request));
	}

	request.setAttribute("fdMaxWidth", SysUiConfigUtil.getFdMaxWidth());
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
%>
	<script type="text/javascript">
		if(typeof(Com_Parameter) === "undefined")
		{
			window.Com_Parameter = {
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
				smallMaxSize : "${smallMaxSize}",
				isSysMouring : "<%=SysUiConfigUtil.getFdIsSysMourning()%>"
			};
		}
		function loadJS(url, callback){
		    var script = document.createElement('script');
		    script.type = 'text/javascript';
		    //IE
		    if(script.readyState){
		        script.onreadystatechange = function(){
		            if( script.readyState == 'loaded' || script.readyState == 'complete' ){
		                script.onreadystatechange = null;
		                callback();
		            }
		        };
		    }else{
		        //其他浏览器
		        script.onload = function(){
		        	callback();
		        };
		    }
		    script.src = url;
		    document.getElementsByTagName('head')[0].appendChild(script);
		}
		function initSeajs(){
			try{
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
				 		config : <%= net.sf.json.JSONObject.fromObject(ResourceUtil.getKmssUiConfig()).toString() %>,
				 		cache : ${LUI_Cache}
				 	}
				});
			}
			catch(err){
				console.error(err);
			}
		}
		function loadNeccesarryJS(url, typeOfVal, callBack){
			if(typeOfVal === "undefined"){
				loadJS(url, callBack);
			}
			else{
				callBack();
			}
		}
		if(typeof(seajs) === "undefined")
		{
			loadNeccesarryJS('${LUI_ContextPath}/resource/js/domain.js?s_cache=${ LUI_Cache }', typeof(domain), function(){
				loadNeccesarryJS('${LUI_ContextPath}/sys/ui/js/LUI.js?s_cache=${ LUI_Cache }', typeof(LUI), function(){
					loadNeccesarryJS('${LUI_ContextPath}/resource/js/common.js?s_cache=${ LUI_Cache }', typeof(Com_Submit), function(){
						loadNeccesarryJS('${LUI_ContextPath}/resource/js/sea.js?s_cache=${ LUI_Cache }', typeof(seajs), function(){
							initSeajs();
							quicklyMultiLangEditOnload();
						});
					});
				});
			});
		}
		else
		{
			quicklyMultiLangEditOnload();
		}
	</script>
<%
}
%>