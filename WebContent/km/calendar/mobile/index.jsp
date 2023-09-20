<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:import charEncoding="utf-8" url="/km/calendar/mobile/self.jsp"/>


<%-- <template:include ref="mobile.list">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null or param.moduleName==''}">
			<bean:message bundle="km-calendar" key="module.km.calendar"/>
		</c:if>
	</template:replace>
	<template:replace name="csshead">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<mui:min-file name="mui-calendar.js"/>
	</template:replace>
	<template:replace name="content">
	
		
	
		<div class="muiCalendarContainer">

			<div id="calendar" class="muiCalendarListView"
				data-dojo-type="mui/calendar/CalendarView">
				
				<div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
					<div
						data-dojo-type="mui/nav/MobileCfgNavBar" 
						data-dojo-mixins="km/calendar/mobile/resource/js/NavBarMixin"
						data-dojo-props=" defaultUrl:'/km/calendar/mobile/index_nav.jsp?hasPersonGroup=${hasPersonGroup}'">
					</div>
					<script type="text/javascript">
						require(['dojo/dom-style', 'dojo/dom', 'dojo/topic', 'dijit/registry', 'dojo/ready'], function(domStyle, dom, topic, registry, ready){
							function handleNavItemChange(tabIndex){
								switch(tabIndex){
									case 0:
										var iframe = document.getElementById("calendar-iframe");
										iframe.src = 'self.jsp?fdSourceType=${param.fdSourceType}&fdSessionId=${param.fdSessionId}&fdSessionType=${param.fdSessionType}&fdTypeId=${param.fdTypeId}&fdSessionName=${param.fdSessionName}';
										break;
									case 1:
										var iframe = document.getElementById("calendar-iframe");
										iframe.src = 'group.jsp';
										break;
									case 2:
										var iframe = document.getElementById("calendar-iframe");
										iframe.src = 'personGroup.jsp?personGroupId=${personGroupId}';
										break;
								}
							}
							topic.subscribe('/mui/navitem/_selected', function(tab, data){
								handleNavItemChange(tab.tabIndex);
							});
							ready(function(){
								var calendar="calendar:kmCalendarRequestAuth";
								if(localStorage && localStorage.hasOwnProperty(calendar)) {
									var type=localStorage.getItem(calendar)
									setTimeout(function(){
										topic.publish("/mui/navitem/selected",this,{key:type});
									},100)
									handleNavItemChange(1);
									localStorage.removeItem(calendar);
								}
								else{
									handleNavItemChange(0);
								}
							});
						});
					</script>
				</div>
				<iframe id="calendar-iframe" src="" width="100%" height="93%" style="border: 0px"></iframe>		
			</div>
		</div>
		
	</template:replace>
</template:include>
<script>
	window.openUrl = function(url){
		location.href = url;
	}
</script> --%>