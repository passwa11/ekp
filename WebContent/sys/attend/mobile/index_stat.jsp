<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil,com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.attend.service.ISysAttendStatService" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,com.alibaba.fastjson.JSONArray,com.alibaba.fastjson.JSONObject" %>
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
				String categoryId = request.getParameter("categoryId");
				String cateId = "";
				JSONArray cateList = cateService.getAttendCategorys(request);
				// 获取当前category的fdId
				if (!cateList.isEmpty()) {
					JSONObject json = (JSONObject) cateList.get(0);
					String __cateId = (String) json.get("fdId");
					boolean isSignCust=false;
					for (int i = 0; i < cateList.size(); i++) {
						JSONObject jsonObject = (JSONObject) cateList.get(i);
						String fdId = jsonObject.getString("fdId");
						int cateType = jsonObject.getInteger("fdType");
						if (StringUtil.isNotNull(categoryId) && fdId.equals(categoryId) && cateType == 2) {
							isSignCust=true;
							break;
						}
					}
					cateId = StringUtil.isNotNull(categoryId) && isSignCust ? categoryId
							: __cateId;
				}
				pageContext.setAttribute("cateId", cateId);
				request.setAttribute("cateList", cateList.toString());
				request.setAttribute("isStatReader", statService.isStatReader());
				request.setAttribute("isSignStatReader", statService.isSignStatReader());
				request.setAttribute("isStatAllReader", statService.isStatAllReader());
				
			%>
	 		
		<div id="personCalendarView" class="muiCalendarContainer" data-dojo-type="dojox/mobile/View">
			<div class="muiEkpSubClockInTitle emphasize" data-dojo-type="sys/attend/mobile/resource/js/list/AttendCategorySelect"
				data-dojo-props='store:${cateList},currentCategoryId:"${cateId}",
				openUrl:"${LUI_ContextPath}/sys/attend/mobile/index_stat.jsp"'>
			</div>
			<div id="calendar" 
				data-dojo-type="mui/calendar/CalendarView" 
					data-dojo-mixins="sys/attend/mobile/resource/js/list/CalendarViewMixin" data-dojo-props="isStatReader:${isStatReader }">
				<div style="margin: 1rem 1rem 0;">
					<div data-dojo-type="mui/calendar/CalendarHeader" data-dojo-props=""></div>
				</div>
				<div data-dojo-type="mui/calendar/CalendarWeek" data-dojo-mixins="sys/attend/mobile/resource/js/list/CalendarWeekMixin"></div>
				<div data-dojo-type="mui/calendar/CalendarContent"
					 data-dojo-mixins="sys/attend/mobile/resource/js/list/CalendarContentMixin"></div>
				
				<div data-dojo-type="mui/calendar/CalendarBottom" data-dojo-props="url:''">
					<div class="categoryOpt muiSignInList" data-dojo-type="sys/attend/mobile/resource/js/list/CalendarBottomOpt">
					</div>
					<div id="_muiSignInDropDownshade"></div>
					<div class="muiEkpSubClockInScheduleTitle" data-dojo-type="sys/attend/mobile/resource/js/list/AttendStatItem"
						 data-dojo-props='fdCategoryId:"${cateId}"'
					></div>
					<div data-dojo-type="sys/attend/mobile/resource/js/list/AttendHeadTip" class="muiAttendBussTip"
			  			 data-dojo-props='fdCategoryId:"${cateId}"'></div>
					<div class="CalendarListView muiSignInPanelBody" data-dojo-type="mui/calendar/CalendarListScrollableView">
						<ul data-dojo-type="sys/attend/mobile/resource/js/list/GroupJsonStoreList" class="muiEkpSubClockInSchedule" id="groupCalendarList"
							data-dojo-mixins="sys/attend/mobile/resource/js/list/GroupItemListMixin"
							data-dojo-props="noSetLineHeight:true,nodataText:'<bean:message bundle="sys-attend" key="mui.no.sign"/>',nodataImg:'${LUI_ContextPath}/sys/attend/mobile/resource/image/nodata.png',
							url:'/sys/attend/sys_attend_main/sysAttendMain.do?method=listGroupCalendar&categoryId=${cateId}&fdStart=!{fdStart}&fdEnd=!{fdEnd}'">
						</ul>
						<div style="height: 16rem;"></div>
					</div>
				</div>
				
			</div>
		</div>
		
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiEkpSubClockInFooter">
		   	<li class="calendarUnSelected calendarLi"
		   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-checking-in muiFontSizeM'" onclick="onStat();">
		   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.sign"/>
		   	</li>
			<li class="calendarLi"
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
			   	<li class="calendarUnSelected calendarLi"
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
			var categoryId = "${cateId }";
			var url = "/sys/attend/mobile/index.jsp";
			if (type == 'calendar') {
				url = "/sys/attend/mobile/index_stat.jsp?categoryId=" + categoryId;
			} else if (type == 'attend') {
				url =  "/sys/attend/mobile/index_stat_attend.jsp?categoryId=" + categoryId;
				<c:if test="${param.navIndex=='2' && param.forward=='exclist' }">
					url +="&forward=exclist";
				</c:if>
			} else if (type == 'cust') {
				url = "/sys/attend/mobile/index_stat_cust.jsp?categoryId=" + categoryId;
			}
			location.href=util.formatUrl(url);
		};
		
		window.backToCalendar=function(){
			location.href=util.formatUrl('/sys/attend/mobile/index.jsp?moduleName=${param.moduleName}');
		};
		ready(9999,function(){
			<c:if test="${param.navIndex=='2' }">
				onStat('attend');
			</c:if>
			<c:if test="${param.navIndex=='2' && param.forward=='exclist' }">
				topic.publish('/attend/stat/exclist/show');
			</c:if>
		});
		
	});
</script>