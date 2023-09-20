<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<template:include ref="mobile.list" loadRef="loading.calendar.week"> 
	<template:replace name="title">
		<c:if test="${JsParam.moduleName!=null && JsParam.moduleName!=''}">
			${HtmlParam.moduleName}
		</c:if>
		<c:if test="${JsParam.moduleName==null or JsParam.moduleName==''}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/index.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/mobile/css/themes/default/select.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<mui:min-file name="mui-imeeting.js"/>
		<style>
			.muiMeetingBookInformation {
				bottom:0;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<c:set var="canCreateMeeting" value="false"></c:set>
		<c:set var="canCreateCloud" value="false"></c:set>
		<c:set var="canCreateBook" value="false"></c:set>
		<%@ include file="/km/imeeting/mobile/index_place.jsp"%>
	</template:replace>
</template:include>

<script type="text/javascript">
	require(['dijit/registry', 'mui/calendar/CalendarUtil', 'dijit/registry', 'dojo/topic'],function(registry, util,registry, topic){
		
		var currentDate=new Date();
		topic.subscribe('/mui/calendar/valueChange',function(widget,args){
			currentDate=args.currentDate;
		});
		
		topic.subscribe('/km/imeeting/changeview', function(view) {
			if(view){
				var viewObj = registry.byId(view);
				viewObj.resize();
			}
    	});
		
		topic.subscribe('/km/imeeting/PlaceBottom/ready', function(view) {
			if(view){
				view.showStatusArea();
			}
    	});
		
		window.navigate2View = function(view) {
			localStorage['swapIndex:${LUI_ContextPath}/km/imeeting/mobile'] = view;
			topic.publish('/km/imeeting/changeview', view);
		}
		
		//新建会议(无模板模式)
		window.create = function(type){
			var url="${LUI_ContextPath}/km/imeeting/mobile/index_book.jsp?moduleName=${JsParam.moduleName }&createType="+type;
			window.open(url,'_self');
		};
	});
</script>
