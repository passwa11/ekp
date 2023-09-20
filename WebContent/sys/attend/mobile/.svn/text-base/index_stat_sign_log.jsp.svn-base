<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.attend.service.ISysAttendStatService" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,net.sf.json.JSONArray,net.sf.json.JSONObject" %>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:table.sysAttendSignLog') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>

	</template:replace>
	<template:replace name="content">
		<div id="attendStatLogList" class="attendStatLogList" data-dojo-type='dojox/mobile/View'>
			<div class="muiExcListHeading">

			</div>
			<div id="attendExcScroll" data-dojo-type="mui/list/StoreScrollableView" >
				<div class="muiExcPanel">
					<ul class="muiSignInSignLogList"
						data-dojo-type="mui/list/JsonStoreList"
						data-dojo-mixins="sys/attend/mobile/resource/js/stat/AttendStatLogListMixin"
						data-dojo-props="url:'/sys/attend/sys_attend_main/sysAttendMain.do?method=getMySignLog&currentDate=${HtmlParam.date }',lazy:false">
					</ul>
				</div>
			</div>

			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" style="display:none;">
				<li class="muiAttendExcBack" data-dojo-type="mui/back/BackButton"></li>
				<li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
					<div data-dojo-type="mui/back/HomeButton"></div>
				</li>
			</ul>
		</div>
	</template:replace>
</template:include>