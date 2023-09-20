<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	request.setAttribute("useCloud", KmImeetingConfigUtil.isUseCloudMng());
%>

<template:include ref="mobile.list" bodyClass="lui_imeeting_body"> 
	<template:replace name="title">
		<c:if test="${JsParam.moduleName!=null && JsParam.moduleName!=''}">
			${HtmlParam.moduleName}
		</c:if>
		<c:if test="${JsParam.moduleName==null or JsParam.moduleName==''}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-module.css" cacheType="md5" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/index.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meeting.css?s_cache=${MUI_Cache}" />
		<mui:min-file name="mui-imeeting.js"/>
	</template:replace>
	<template:replace name="content">
	
		<c:set var="canCreateMeeting" value="false"></c:set>
		<c:set var="canCreateCloud" value="false"></c:set>
		<c:set var="canCreateBook" value="false"></c:set>
		
		<kmss:authShow roles="ROLE_KMIMEETING_CREATE">
			<c:set var="canCreateMeeting" value="true"></c:set>
		</kmss:authShow>
		<c:if test="${useCloud}"> 
			<kmss:authShow roles="ROLE_KMIMEETING_CREATE_CLOUD">
				<c:set var="canCreateCloud" value="true"></c:set>
			</kmss:authShow>
		</c:if>
		<kmss:authShow roles="ROLE_KMIMEETING_BOOKCREATE">
			<c:set var="canCreateBook" value="true"></c:set>
		</kmss:authShow>
	
		<%@ include file="/km/imeeting/mobile/index_calendar.jsp"%>
		<%-- <%@ include file="/km/imeeting/mobile/index_place.jsp"%>
		<%@ include file="/km/imeeting/mobile/index_summary.jsp"%>
		<%@ include file="/km/imeeting/mobile/index_other.jsp"%> --%>
		
		<div class="lui_db_footer">
		    <div class="lui_db_footer_item active" onclick="navigate2View(0)">
		      <div class="lui_db_footer_item_img my"></div>
		      <p><bean:message bundle="km-imeeting" key="mobile.myMeeting" /></p>
		    </div>
		    <div class="lui_db_footer_item" onclick="navigate2View(1)">
		      <div class="lui_db_footer_item_img place"></div>
		      <p><bean:message bundle="km-imeeting" key="mobile.meetingRoom" /></p>
		    </div>
		    <div class="lui_db_footer_item" onclick="navigate2View(2)">
		        <div class="lui_db_footer_item_img summary"></div>
		        <p><bean:message bundle="km-imeeting" key="mobile.meetingSummary" /></p>
		    </div>
		    <div class="lui_db_footer_item" onclick="navigate2View(3)">
		        <div class="lui_db_footer_item_img more"></div>
		        <p><bean:message bundle="km-imeeting" key="mobile.other" /></p>
		    </div>
		</div>
		
	</template:replace>
</template:include>

<script type="text/javascript">
	require(['dijit/registry', 'mui/calendar/CalendarUtil', 'dijit/registry', 'dojo/topic','km/imeeting/mobile/resource/js/Guide'],function(registry, util,registry, topic,Guide){
		
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
		
		//新建会议预约(无模板模式)
		window.create = function(type){
			var url="${LUI_ContextPath}/km/imeeting/mobile/index_book.jsp?moduleName=${JsParam.moduleName }&createType="+type;
			window.open(url,'_self');
		};
		
		window.navigate2View = function(view) {
			var url ="${LUI_ContextPath}/km/imeeting/mobile/";
			if(0 == view){
				url += "index.jsp"
			}else if(1 == view){
				url += "index_place_new.jsp"
			}else if(2 == view){
				url += "index_summary.jsp"
			}else if(3 == view){
				url += "index_other.jsp"
			}
			window.open(url, '_self');
		}
		
		window.onload = function(){
			//公司测试环境排除地址
			var excludeIp = "http://192.168.2.8:9186;http://192.168.2.8:9183;http://192.168.2.8:9184;http://192.168.2.8:9185"
			var host = location.host;
			if(excludeIp.indexOf(host) == -1){
				var isFirst = localStorage.getItem(location.pathname);
				if(isFirst != "true"){
					new Guide();
					localStorage.setItem(location.pathname,"true");
				}
			}
 			
		}
		
	});
</script>
