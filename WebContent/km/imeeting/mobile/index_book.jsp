<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.km.imeeting.util.ImeetingCateUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	String modelName = "com.landray.kmss.km.imeeting.model.KmImeetingConfig";
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map map = sysAppConfigService.findByKey(modelName);
	request.setAttribute("kmImeetingConfig", map);
	request.setAttribute("useCloud", KmImeetingConfigUtil.isUseCloudMng());
%>

<template:include ref="mobile.list">
	<template:replace name="title">
		会议室预约
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meetingBook.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/mobile/css/themes/default/select.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="content">
	<%  request.setAttribute("defaultCateId", ImeetingCateUtil.getfirstCate());%>
				<div data-dojo-type="mui/header/Header" class="meetingHeader" data-dojo-props="height:'4rem'"  style="border-bottom: none">
					 <div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceCateSelect" class="placeCateSelect" style="height:100%"
					 	  data-dojo-props="url:'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=getAllCate',name:'fdPeriodId',mul:false,showStatus:'edit',value:'${defaultCateId}'">
					 </div>
					 <div class="meetingHeaderItem  mui mui-calendar" onclick="backToCalendar();"></div>
					 <div class="meetingHeaderItem  mui mui-listView" onclick="backToList();">
					 </div>
				</div> 
				<div data-dojo-type="mui/header/Header" style="border-bottom: none">
					<div data-dojo-type="km/imeeting/mobile/resource/js/list/DateNavBar"></div>
				</div>
				<div class="muiMeetingBookContainer">
		            <div id="timeBar" data-dojo-type="km/imeeting/mobile/resource/js/list/TimeBar" ></div>
			       	<div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceBar"  data-dojo-props="curCateId:'${defaultCateId}'">
			       	</div>
			       	<div id="placeContent" data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceContentScrollView">
			        	<div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceContent" class="placeContentContainer"></div>
			        </div>
		        </div>
		        <c:set var="canCreateMeeting" value="false"></c:set>
		        <c:if test="${useCloud}">
			        <kmss:authShow roles="ROLE_KMIMEETING_CREATE_CLOUD">	
			        	<c:set var="canCreateMeeting" value="true"></c:set>
			        </kmss:authShow>
		        </c:if>
		        <c:set var="canCreateBook" value="false"></c:set>
		        <kmss:authShow roles="ROLE_KMIMEETING_BOOKCREATE">	
		        	<c:set var="canCreateBook" value="true"></c:set>
		        </kmss:authShow>
        		<div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceBottom"  data-dojo-props="canCreateMeeting:${canCreateMeeting},canCreateBook:${canCreateBook},createType:'${JsParam.createType}'"></div>
        <script>
        require(["dijit/registry",'mui/util'],function(registry,util){
    		window.backToCalendar=function(){
    			location.href=util.formatUrl('/km/imeeting/mobile/index.jsp');
    		};
    		window.backToList=function(){
    			location.href=util.formatUrl('/km/imeeting/mobile/index_list.jsp');
    		};
    		 window.onresize = function(){
    	        	
    			 var timeBar = registry.byId('timeBar');
    			 var placeContent = registry.byId('placeContent');
    			 
    			 timeBar.setTransformMaxHeight();
    			 placeContent.resize();
    			 
    	    };
    	});
        
       
      
        </script>
	</template:replace>
</template:include>

