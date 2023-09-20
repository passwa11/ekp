<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

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
					<div id="imeetingList" 
						data-dojo-type="mhui/list/ItemListBase"
						data-dojo-mixins="mhui/list/SwiperItemListMixin,km/imeeting/maxhub/resource/js/list/IMeetingItemListMixin"
						data-dojo-props="lazy:true"></div>
			    
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
					data-dojo-props="icon:'mui mui-arrow-left',text:'返回首页',onClick:'goBack'"></div>
					
				<div data-dojo-type="mhui/toolbar/ToolbarAvatarButton"
					data-dojo-props="avatar:'<person:headimageUrl contextPath="true" personId="${currentUser.userId}" size="m" />',text:'${currentUser.userName}'"></div>
					
			</div>
			<!-- 
			<div data-dojo-type="mhui/toolbar/ToolbarItem">
				<kmss:authShow roles="ROLE_KMIMEETING_CREATE_CLOUD">
					<div id="btnNewIMeeting"
						class="mhui-hidden"
						data-dojo-type="mhui/toolbar/ToolbarButton"
						data-dojo-props="text:'面对面<br/>建会议',onClick:'createIMeeting',type:'primary',size:'lg'"></div>
				</kmss:authShow>
			</div>
		 	-->
			<div data-dojo-type="mhui/toolbar/ToolbarItem"
				data-dojo-props="align:'right'">
				
				<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
					data-dojo-props="icon:'mui mui-setting',text:'设置',onClick:'goSetting'"></div>
				
			</div>
		</div>
		
		<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/maxhub/resource/js/list.js"></script>
	</template:replace>	
</template:include>