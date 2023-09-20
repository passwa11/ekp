<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
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

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="maxhub.list">

	<template:replace name="head">
	</template:replace>

	<template:replace name="content">
	
	  	<!-- 主体内容区 Starts -->
		<section class="mhui-main-content">
			<div class="mhui-row">
			    <div class="mhui-col-xs-12">
			    
					<div class="mhuiTitle">
						<span id="placeName"></span>&nbsp;<span id="currentTime"></span>
					</div>
					<div class="mhuiHomeAction">
						<c:if test="${useCloud}">
							<kmss:authShow roles="ROLE_KMIMEETING_CREATE_CLOUD">
								
								<div id="btnNewMeeting" class="mhuiHomeActionItem" onclick="createMeeting()">
									<div class="mhuiHomeActionIcon mhuiHomeCreateIcon"></div>
									<div class="mhuiHomeActionLabel">面对面建会议</div>								
								</div>
							</kmss:authShow>
						</c:if>
						<div class="mhuiHomeActionItem" onclick="goMettingList()">
							<div class="mhuiHomeActionIcon mhuiHomeListIcon"></div>
							<div class="mhuiHomeActionLabel">查看会议列表</div>								
						</div>

					</div>
			    
			 	</div>
			</div>
		</section>
		<!-- 主体内容区 Ends -->
		<div data-dojo-type="mhui/toolbar/Toolbar">
			<div data-dojo-type="mhui/toolbar/ToolbarItem"
				data-dojo-props="align:'left'">
				
				<div
					class="mhui-btn-return" 
					data-dojo-type="mhui/toolbar/ToolbarIconButton"
					data-dojo-props="icon:'mui mui-arrow-left',text:'退出登录',isReturn:true,onClick:'goExit'"></div>
					
				<div data-dojo-type="mhui/toolbar/ToolbarAvatarButton"
					data-dojo-props="avatar:'<person:headimageUrl contextPath="true" personId="${currentUser.userId}" size="m" />',text:'${currentUser.userName}'"></div>
					
			</div>
			<div data-dojo-type="mhui/toolbar/ToolbarItem"
				data-dojo-props="align:'right'">
				
				<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
					data-dojo-props="icon:'mui mui-toggle-iroom',text:'我的会议',onClick:'goMap'"></div>
					
				<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
					data-dojo-props="icon:'mui mui-setting',text:'设置',onClick:'goSetting'"></div>
				
			</div>
		</div>
		
		<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/maxhub/resource/js/index.js"></script>
	</template:replace>	
</template:include>