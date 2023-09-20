<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.km.calendar.service.IKmCalendarMainService,com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.sys.task.service.ISysTaskMainService,net.sf.json.JSONObject,com.landray.kmss.util.StringUtil"%>

<%
		IKmCalendarMainService calendarService = (IKmCalendarMainService) SpringBeanUtil
			.getBean("kmCalendarMainService");
		ISysTaskMainService taskervice = (ISysTaskMainService) SpringBeanUtil
			.getBean("sysTaskMainService");
		JSONObject taskJson = taskervice.getKKConfig();
		JSONObject calendarJson = calendarService.getKKConfig();
		Boolean enableTaskTransfer = (Boolean)taskJson.get("enableTransfer");
		Boolean enableCalendarTransfer = (Boolean)taskJson.get("enableTransfer");
		
		request.setAttribute("enableTaskTransfer",enableTaskTransfer.booleanValue());
		request.setAttribute("enableCalendarTransfer",enableCalendarTransfer.booleanValue());
%>
<template:include ref="mobile.list">
	<template:replace name="title">
		群应用
	</template:replace>
	<template:replace name="head">
		

	</template:replace>
	<template:replace name="content">
		<div id="groupAppNavBar" data-dojo-type='mui/nav/NavBar' data-dojo-props='height:"3.8rem"'>
			<c:if test="${enableTaskTransfer eq true}">
				<div class='muiNavitem'
					data-dojo-type='third/im/kk/kk_app/mobile/resource/js/GroupAppNavItem'
					data-dojo-props='key:"task",text:"任务",value:1,href:"${KMSS_Parameter_ContextPath }sys/task/mobile/index_list.jsp?fdSourceType=KK_IM&fdSessionId=${param.sessionId}&fdSessionType=${param.sessionType}&fdTypeId=${param.typeId}&fdSessionName=${param.sessionName}"'>
				</div>
			</c:if>
			<c:if test="${enableCalendarTransfer eq true}">
				<div class='muiNavitem'
					data-dojo-type='third/im/kk/kk_app/mobile/resource/js/GroupAppNavItem'
					data-dojo-props='key:"calendar",text:"日程",value:0,href:"${KMSS_Parameter_ContextPath }km/calendar/mobile/index.jsp?fdSourceType=KK_IM&fdSessionId=${param.sessionId}&fdSessionType=${param.sessionType}&fdTypeId=${param.typeId}&fdSessionName=${param.sessionName}"'>
				</div>
			</c:if>
		</div>
		<div data-dojo-type='third/im/kk/kk_app/mobile/resource/js/GroupAppContentView'
			data-dojo-props='enableTaskTransfer:"${enableTaskTransfer }",enableCalendarTransfer:"${enableCalendarTransfer}"'>
			<iframe src="" frameborder="no" border="0" marginwidth="0" marginheight="0" height="100%" width="100%"></iframe>
		</div>
		
	</template:replace>
</template:include>
<script>
	require(['mui/util','dojo/ready','dojo/topic'],function(util,ready,topic){
		
	});
</script>