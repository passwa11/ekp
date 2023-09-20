<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null or param.moduleName==''}">
			<bean:message bundle="km-calendar" key="module.km.calendar"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/mobile/resource/css/group.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<mui:min-file name="mui-calendar.js"/>
	</template:replace>
	<template:replace name="content">
	
		<div class="muiCalendarContainer">
			<div id="calendar" class="muiCalendarListView" data-dojo-type="mui/calendar/CalendarView">
				
				<div style="margin: 1rem 1rem 0;">
					<div data-dojo-type="mui/calendar/CalendarHeader"></div>
				</div>
				<div data-dojo-type="mui/calendar/CalendarWeek"></div>
				<div data-dojo-type="mui/calendar/CalendarContent"></div>
				<div data-dojo-type="mui/calendar/CalendarBottom" data-dojo-props="url:''">
					<div data-dojo-type="mui/calendar/CalendarBottomOpt">
						<c:set var="personGroupId" value="${param.personGroupId}"></c:set>
							<div data-dojo-type="mui/form/Select" id="personGroupList"
								 data-dojo-props="name:'personGroupList',value:'${param.personGroupId}',mul:false,
								 subject:'<bean:message bundle="km-calendar" key="kmCalendarMain.group.header.title" />',
								 showStatus:'edit',orient:'vertical',
								 url:'${LUI_ContextPath}/km/calendar/km_calendar_person_group/kmCalendarPersonGroup.do?method=listPersonGroupSelectJson'" >
							</div>


<%--						<div data-dojo-type="km/calendar/mobile/resource/js/GroupSelect"--%>
<%--							data-dojo-props="value:'${personGroupId}',--%>
<%--							url:'${LUI_ContextPath}/km/calendar/km_calendar_person_group/kmCalendarPersonGroup.do?method=listPersonGroupJson',--%>
<%--							openUrl:'${LUI_ContextPath}/km/calendar/mobile/personGroup.jsp',--%>
<%--							type:'personGroup'">--%>
<%--						</div>--%>
					</div>
					<div class="CalendarListView" data-dojo-type="mui/calendar/CalendarListScrollableView">
						<%
							if ("zh".equals(UserUtil.getKMSSUser().getLocale().getLanguage())) {
								request.setAttribute("nodataImgPath", request.getContextPath() + 
										"/km/calendar/mobile/resource/images/calendar_nodate.png");
							} else {
								request.setAttribute("nodataImgPath", request.getContextPath() + 
										"/km/calendar/mobile/resource/images/calendar_nodate_en.png");
							}
						%>
						<ul class="mui_ekp_portal_date_datails"
							data-dojo-type="km/calendar/mobile/resource/js/GroupJsonStoreList"
							data-dojo-mixins="km/calendar/mobile/resource/js/list/GroupItemListMixin"
							data-dojo-props="noSetLineHeight:true,nodataImg:'${nodataImgPath}',nodataText:'',
							url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=listPersonGroupCalendar&loadAll=true&personGroupId=${personGroupId}&fdStart=!{fdStart}&fdEnd=!{fdEnd}'">
						</ul>
					</div>
				</div>
			</div>
			
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
			   	<li class="calendarUnSelected calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-my-schedule muiFontSizeM'" onclick="changePage(1)">
			   		<bean:message bundle="km-calendar"  key="kmCalendarMain.calendar.header.title"/>
			   	</li>
				<li class="calendarUnSelected calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-apply muiFontSizeM'" onclick="changePage(2)">
			   		<bean:message bundle="km-calendar"  key="module.km.calendar.tree.share.calendar"/>
			   	</li>
			   	<li class="calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-cluster muiFontSizeM'" onclick="changePage(3)">
			   		<bean:message bundle="km-calendar"  key="kmCalendarMain.group.header.title"/>
			   	</li>
			</ul>
			
		</div>
		
	</template:replace>
</template:include>
<script>
	require(['mui/util', 'dojo/topic','mui/calendar/CalendarUtil','mui/device/adapter','dijit/registry','dojo/ready'],function(util,topic,cutil,adapter,registry,ready){
		var currentDate=new Date();
		topic.subscribe('/mui/calendar/valueChange',function(widget,args){
			currentDate=args.currentDate;
		});
		topic.subscribe('mui/form/select/callback',function(widget){
			//监听群组选择事件
			if(widget && widget.valueField == 'personGroupList' && widget.value){
				window.location = '${LUI_ContextPath}/km/calendar/mobile/personGroup.jsp?personGroupId='+widget.value;
			}
		});
		//新建日程
		window.create=function(){
			var url='${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=addEvent&moduleName=${param.moduleName}',
				currentStr=cutil.formatDate(currentDate);
			if( currentStr ){
				url+='&startTime='+currentStr+'&endTime='+currentStr;
			}
			url+='&ownerId=${pageScope.currentUserId}';
			window.open(url,'_self');
		};
		//覆盖默认的后退(防止删除页面后列表页面后退到不存在的页面)
		window.doback=function(){
			adapter.closeWindow();
		};
		
		window.changePage = function (type) {
			if(type == 1) {
				location.href = util.formatUrl('/km/calendar/mobile/self.jsp');
			} else if(type == 2) {
				location.href = util.formatUrl('/km/calendar/mobile/group.jsp');
			} else {
				location.href = util.formatUrl('/km/calendar/mobile/personGroup.jsp' + '?personGroupId=${personGroupId}');
			}
		}
	});

</script>