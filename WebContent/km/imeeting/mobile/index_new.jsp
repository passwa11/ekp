<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@ page import="com.landray.kmss.km.imeeting.util.ImeetingCateUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<% 
	request.setAttribute("defaultCateId", ImeetingCateUtil.getfirstCate()); 
	request.setAttribute("nowDate", DateUtil.convertDateToString(new Date(), ResourceUtil.getString("date.format.date")));
%>
<template:include file="/sys/mportal/module/mobile/jsp/module.jsp">

    <%-- 浏览器title --%>
	<template:replace name="title">
		<c:if test="${JsParam.moduleName!=null && JsParam.moduleName!=''}">
			${HtmlParam.moduleName}
		</c:if>
		<c:if test="${JsParam.moduleName==null or JsParam.moduleName==''}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	
	<%-- 导入JS、CSS --%>
	<template:replace name="head">
		<c:set var="canCreateMeeting" value="false"></c:set>
		<c:set var="canCreateBook" value="false"></c:set>
		
		<kmss:authShow roles="ROLE_KMIMEETING_CREATE">
			<c:set var="canCreateMeeting" value="true"></c:set>
		</kmss:authShow>
		<kmss:authShow roles="ROLE_KMIMEETING_BOOKCREATE">
			<c:set var="canCreateBook" value="true"></c:set>
		</kmss:authShow>
        <link href="../mobile/module/css/meeting.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/index.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<script type="text/javascript">
			window.buttons=[];  
			<%-- 新建会议预约(按钮) --%>
			<kmss:authShow roles="ROLE_KMIMEETING_BOOKCREATE">
				window.buttons.push("book_create");
			</kmss:authShow>
		   	<%-- 新建会议安排(按钮) --%>
		   	<kmss:authShow roles="ROLE_KMIMEETING_CREATE">
				window.buttons.push("meeting_create");
				<%if("true".equals(KmImeetingConfigUtil.isCycle())){ %>
				window.buttons.push("cycle_create");
				<%} %>
				<%if(KmImeetingConfigUtil.isVideoMeetingEnable()){ %>
				window.buttons.push("video_create");
				<%} %>
			</kmss:authShow>
			
			window.defaultCateId = '${defaultCateId}';
			window.nowDate = '${nowDate}';
		</script>
		
		<script>
	    	require(['dijit/registry', 'dojo/dom', 'dojo/on', 'dojo/dom-attr', 'dojo/topic', 'mui/util', 'dojo/_base/lang',
	                 'mui/dialog/Tip', 'dojo/domReady'],
	        		function(registry, dom, on, domAttr, topic, util, lang, Tip, ready){
	    		
				var placeNode = dom.byId('place');
				
				window.__MEETING_CREATE_PAYLOAD__ = {};
				
				/* on(dom.byId('ImeetingCreateFrame'), 'click', function(e) {
					var target = e.target;
					if(target && target.id == 'ImeetingCreateFrame') {
						domAttr.set(placeNode, 'data-create-active', 'false');
					}
				}); */
	        	
	        	topic.subscribe('/km/imeeting/create', function(ctx, payload) {
	        		
	        		if('${canCreateMeeting}' == 'true' || '${canCreateBook}' == 'true') {  
		        		domAttr.set(placeNode, 'data-create-active', 'true');
		        		window.__MEETING_CREATE_PAYLOAD__ = payload;
	        		} else {
	        			Tip.tip({icon:'mui mui-warn', text:'很抱歉，您没有预约会议权限！',width:'260',height:'60'});
	        		}
	        		
	        	});
	        	
	        	topic.subscribe('/km/imeeting/changeview', function(view) {
	        		if(view != 'place') {
	        			return;
	        		}
	        		
	        		topic.publish('/km/imeeting/place/resize');
	    			topic.publish('/km/imeeting/timeNavBar/resetTransform');
	    			topic.publish('/km/imeeting/placeNavBar/resetTransform');
	        	});
	        	
				window.createBook = function() {
					
					var url = lang.replace('${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=add&' + 
							'fdTime={fdTime}&fdStratTime={fdStartTime}&fdFinishTime={fdFinishTime}&resId={resId}', window.__MEETING_CREATE_PAYLOAD__ || {});
					
					window.open(url, '_self');
				}
				
				window.createCloudMeeting = function() {
					
					var url = lang.replace('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&' + 
							'fdTime={fdTime}&fdStratTime={fdStartTime}&fdFinishTime={fdFinishTime}&resId={resId}', window.__MEETING_CREATE_PAYLOAD__ || {});
					
					window.open(url, '_self');
					
				}
	
				window.onresize = function(){
	    	        	
					var timeBar = registry.byId('timeBar');
					var placeContent = registry.byId('placeContent');
	    			 
					timeBar.setTransformMaxHeight();
					placeContent.resize();
	    			 
	    	    };
	    	    
	    	    // 解决首次进入会议室列表错位情况
	    	    ready(function() {
	    	    	topic.publish('/km/imeeting/changeview', 'place');
	    	    });
	    	    
	    	});
		</script>          
	</template:replace>
	
	<%-- 页面内容 --%>
	<template:replace name="content">
		<div data-dojo-type="sys/mportal/module/mobile/Module"
			 data-dojo-mixins="km/imeeting/mobile/module/js/ModuleMixin"></div>
	</template:replace>

</template:include>