<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.attend.service.ISysAttendStatService" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,net.sf.json.JSONArray,net.sf.json.JSONObject" %>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list">
	<template:replace name="title">
		移动签到
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>

	</template:replace>
	<template:replace name="content">
			<%
				ISysAttendCategoryService cateService = (ISysAttendCategoryService) SpringBeanUtil
						.getBean("sysAttendCategoryService");
				ISysAttendStatService statService = (ISysAttendStatService)SpringBeanUtil
						.getBean("sysAttendStatService");
				//JSONArray cateList = cateService.getAttendCategorys(request);
				
				//request.setAttribute("cateList", cateList.toString());
				request.setAttribute("isStatReader", statService.isStatReader());
				request.setAttribute("isSignStatReader", statService.isSignStatReader());
				request.setAttribute("isStatAllReader", statService.isStatAllReader());
				
			%>
	 		
		<c:if test="${isSignStatReader }">
			<div id="signStatView" data-dojo-type="mui/list/StoreScrollableView" class="white">
				<ul data-dojo-type="mui/list/JsonStoreList"
		             data-dojo-mixins="sys/attend/mobile/resource/js/stat/SignStatItemListMixin" class="muiEkpSubClockInStatistics"
					 data-dojo-props="url:'/sys/attend/sys_attend_category/sysAttendCategory.do?method=listStat&orderby=&rowsize=100',lazy:true">
				</ul>
			</div>
		</c:if>
		
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiEkpSubClockInFooter">
		   	<li class="calendarUnSelected calendarLi"
		   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-checking-in muiFontSizeM'" onclick="onStat();">
		   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.sign"/>
		   	</li>
			<li class="calendarUnSelected calendarLi"
		   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-my-schedule muiFontSizeM'" onclick="onStat('calendar');">
		   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.calendar"/>
		   	</li>
		   	<c:if test="${isStatReader }">
			   	<li class="calendarUnSelected calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-statistics muiFontSizeM'" onclick="onStat('attend');">
			   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.attend"/>
			   	</li>
		   	</c:if>
		   	<c:if test="${isSignStatReader }">
			   	<li class="calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-cartogram muiFontSizeM'" onclick="onStat('cust');">
			   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.cust"/>
			   	</li>
			</c:if>
		</ul>
	</template:replace>
</template:include>
<script>
	require(['mui/util','dojo/ready','dojo/topic','dojo/query','dijit/registry'],function(util,ready,topic,query,registry){
		window.onStat=function(type){
			var categoryId = "${HtmlParam.categoryId }";
			var url = "/sys/attend/mobile/index.jsp";
			if (type == 'calendar') {
				url = "/sys/attend/mobile/index_stat.jsp?categoryId=" + categoryId;
			} else if (type == 'attend') {
				url =  "/sys/attend/mobile/index_stat_attend.jsp?categoryId=" + categoryId;
			} else if (type == 'cust') {
				url = "/sys/attend/mobile/index_stat_cust.jsp?categoryId=" + categoryId;
			}
			location.href=util.formatUrl(url);
		};
	});
</script>