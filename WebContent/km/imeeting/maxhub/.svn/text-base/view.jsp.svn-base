<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/km/imeeting/import/time.jsp"%>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<script>
	window.__fdMettingName__ = '${kmImeetingMainForm.fdName}';
	window.__fdMettingId__ = window.__fdModelId4sysAttend__ = '${kmImeetingMainForm.fdId}';
	window.__fdMettingHoldDate__ = '${kmImeetingMainForm.fdHoldDate}';
	window.__fdMettingFinishDate__ = '${kmImeetingMainForm.fdFinishDate}';
	window.__fdModelName4sysAttend__ = 'com.landray.kmss.km.imeeting.model.KmImeetingMain';
</script>

<template:include ref="maxhub.view">

	<template:replace name="head">
	
	</template:replace>

	<template:replace name="content">
		
		<!-- 主体内容区 Starts -->
		<section class="mhui-main-content">
			<div class="mhui-row">
				
				<div class="mhui-col-xs-2">
					<!-- 左侧内容区域 Starts -->
					<%@ include file="/km/imeeting/maxhub/view_left.jsp"%>
					<!-- 左侧内容区域 Ends -->
				</div>
				
				<div class="mhui-col-xs-8">
					<!-- 中间内容区域 Starts -->
					<section class="mhui-panel-area">
						<div class="mhui-panel-area-heading">
							<h2 class="mhui-panel-area-heading-title" style="font-size: 2.5rem;">
								<c:out value="${kmImeetingMainForm.fdName }" />
							</h2>
						</div>
						
						<div class="mhui-panel-area-body" id="imeetingMain">
						
							<div data-dojo-type="dijit/_WidgetBase"
								data-dojo-mixins="km/imeeting/maxhub/resource/js/FullScreenSwitchMixin"></div>
						
							<!-- 会议详情 -->
							<c:import url="/km/imeeting/maxhub/view_index.jsp" charEncoding="utf-8"/>
							
							<!-- 会议签到 -->
							<c:if test="${kmImeetingMainForm.isFace == false}">
								<kmss:ifModuleExist path="/sys/attend">
									
									<c:set var="showSignInBtn" value="false"></c:set>
									<kmss:auth
										requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=add&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain&fdModelId=${kmImeetingMainForm.fdId}" requestMethod="GET">
										<c:if test="${kmImeetingMainForm.docStatus=='30' && isEnd==false }">
											<c:set var="showSignInBtn" value="true"></c:set>
										</c:if>
									</kmss:auth>
									<c:import url="/sys/attend/maxhub/import/view.jsp" charEncoding="utf-8">
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
										<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
										<c:param name="showSignInBtn" value="${showSignInBtn}"></c:param>
										<c:param name="signInBtnFunc" value="launchSignIn"></c:param>
										<c:param name="className" value="mhuiViewSec mhui-hidden"></c:param>
									</c:import>
								</kmss:ifModuleExist>
							</c:if>
							<!-- 任务 -->
							<kmss:ifModuleExist path="/sys/task">
									<c:import url="/sys/task/maxhub/import/view.jsp" charEncoding="utf-8">
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
										<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
									</c:import>
							</kmss:ifModuleExist>
							<!-- 会议投票 -->
							<kmss:ifModuleExist path="/km/vote">
								<c:import url="/km/vote/maxhub/import/view.jsp" charEncoding="utf-8">
									<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
									<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
								</c:import>
							</kmss:ifModuleExist>
							
						</div>
	
					</section>
					<!-- 中间内容区域 Ends -->
				</div>
				
				<div class="mhui-col-xs-2">
					<!-- 右侧内容区域 Starts -->
					<%@ include file="/km/imeeting/maxhub/view_right.jsp"%>
					<!-- 右侧内容区域 Ends -->
				</div>
				
			</div>
		</section>
		<!-- 主体内容区 Ends -->
		
		
		<div id="imeetingToolbar" 
			data-dojo-type="mhui/toolbar/Toolbar">
			<div data-dojo-type="mhui/toolbar/ToolbarItem"
				data-dojo-props="align:'left'">
				
				<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
					data-dojo-props="icon:'mui mui-arrow-left',text:'返回',onClick:'goBack'"></div>
					
				<div data-dojo-type="mhui/toolbar/ToolbarAvatarButton"
					data-dojo-props="avatar:'<person:headimageUrl contextPath="true" personId="${currentUser.userId}" size="m" />',text:'${currentUser.userName}'"></div>
				
				<div data-dojo-type="mhui/list/ItemListBase"
					data-dojo-mixins="mhui/list/ExpandableItemListMixin"
					data-dojo-props="slot:true,expanded:false,key:'toolbar'">
					
					<div data-dojo-type="mhui/list/SlotBase" 
						data-dojo-props="key:'toolbar',slotName:'main'">
						<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
							data-dojo-props="icon:'mui mui-postil',text:'批注',onClick:'goNotes'"></div>
						<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
							data-dojo-props="icon:'mui mui-whiteboard',text:'白板',onClick:'goBoard'"></div>
							
						<c:if test="${isEnd==false}">
							<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
								<div id="btnInviteMeetingLeft" 
									data-dojo-type="mhui/toolbar/ToolbarIconButton"
									data-dojo-props="icon:'mui mui-codescan',text:'邀请',onClick:'inviteIMeeting'"></div>	
							</kmss:auth>
						</c:if>
						
					</div>
					
				</div>
				
			</div>
			<div data-dojo-type="mhui/toolbar/ToolbarItem">
				<kmss:auth
					requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=uploadTmpAttachment&fdId=${kmImeetingMainForm.fdId }"
					requestMethod="GET">
					<div id="btnSyncMeeting" 
						data-dojo-type="mhui/toolbar/ToolbarButton"
						data-dojo-props="text:'同步记录',type:'primary',size:'lg',onClick:'synFiles'"></div>
				</kmss:auth>
				<c:if test="${isEnd==false}">
					<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=earlyEndMeeting&fdId=${kmImeetingMainForm.fdId }"
							requestMethod="GET">
							<div id="btnFinishMeeting"
								class="<c:if test="${isBegin!=true}">mhui-hidden</c:if>" 
								data-dojo-type="mhui/toolbar/ToolbarButton"
								data-dojo-props="text:'结束会议',type:'danger',size:'lg',onClick:'finishMeeting'"></div>
						</kmss:auth>
					</kmss:auth>
				</c:if>
			</div>
			<div data-dojo-type="mhui/toolbar/ToolbarItem"
				data-dojo-props="align:'right'">
				
				<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
					data-dojo-props="icon:'mui mui-postil',text:'批注',onClick:'goNotes'"></div>
				<div data-dojo-type="mhui/toolbar/ToolbarIconButton"
					data-dojo-props="icon:'mui mui-whiteboard',text:'白板',onClick:'goBoard'"></div>
					
				<c:if test="${isEnd==false}">
					<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
						<div id="btnInviteMeetingRight" 
							data-dojo-type="mhui/toolbar/ToolbarIconButton"
							data-dojo-props="icon:'mui mui-codescan',text:'邀请',onClick:'inviteIMeeting'"></div>
					</kmss:auth>
				</c:if>
				
			</div>
		</div>
		
		<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/maxhub/resource/js/view.js"></script>
		
	</template:replace>	
</template:include>