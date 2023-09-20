<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page language="java" pageEncoding="UTF-8"%>

<%
	SysAgendaBaseConfig calendarConfig = new SysAgendaBaseConfig();
	String firstDayInWeek = calendarConfig.getCalendarWeekStartDate();
	if(StringUtil.isNull(firstDayInWeek)) {
		firstDayInWeek = "1";
	}
%>

<script type="text/javascript">
	var dojoConfig = {
		parseOnLoad: false,
		async: true,
		baseUrl:'/',
		cacheBust: 's_cache=${MUI_Cache}',
		packages: [{
		    name: "dojo",
		    location: "/sys/mobile/js/dojo"
		},{
		    name: "dijit",
		    location: "/sys/mobile/js/dijit"
		},{
		    name: "dojox",
		    location: "/sys/mobile/js/dojox"
		},{
			name: 'mui',
			location: '/sys/mobile/js/mui'
		},{
			name : 'lib',
			location :'/sys/mobile/js/lib'
		}],
		paths: {
			"mui/mui": "/sys/mobile/js/mui/mui._min_"
		},
		calendar:{
			//周起始日,跟标准保持一致(admin.do里将星期天设为了1,星期一设为了2....)
			"firstDayInWeek":parseInt(<%= firstDayInWeek %>)-1
		},
		offline : true,
		requestProvider : 'mui/device/kk5/xhr',
		serverPrefix : '<%= ResourceUtil.getKmssConfigString("kmss.urlPrefix") %>'
	};
	window.offlineParam = function(param){
		var re = new RegExp();
		re.compile("[\\?&]" + param + "=([^&]*)", "i");
		var arr = re.exec(location.href);
		if (arr == null)
			return null;
		else
			return decodeURIComponent(arr[1]);
	};
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/mui/device/kk5/mapping.js?s_cache=${MUI_Cache}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/dojo/dojo.js.uncompressed.js?s_cache=${MUI_Cache}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/dojox/mobile.js.uncompressed.js?s_cache=${MUI_Cache}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/mui/device/kk5/dojo.custom.js?s_cache=${MUI_Cache}"></script>