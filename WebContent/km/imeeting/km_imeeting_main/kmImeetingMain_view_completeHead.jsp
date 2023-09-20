<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	//判断是否是pc企业微信客户端
	if(MobileUtil.THIRD_WXWORK == MobileUtil.getClientType(new RequestContext(request))){
		request.setAttribute("isQywx", true);
	}else{
		request.setAttribute("isQywx", false);
	}
%>
<template:replace name="head">
	<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view.css?s_cache=${MUI_Cache}" />
	<script type="text/javascript" src="${LUI_ContextPath}/sys/mobile/js/mui/device/weixin/wxPCAgentConfig.js"></script>
	<style>
		.meeting_cyclicity{
			top:-20px
		}
	</style>
</template:replace>
<template:replace name="title">
	<c:out value="${ kmImeetingMainForm.fdName} - ${ lfn:message('km-imeeting:table.kmImeetingMain') }"></c:out>
</template:replace>
<%--操作栏--%>
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="1">
		<%--直播按钮条件 1、有企业微信模块且企业开启企业微信集成和直播开关 2、会议未召开||(会议状态是发布&&正在进行)--%>
		<kmss:ifModuleExist path="/third/weixin/">
			<c:if test="${isCanLiving == true}">
				<c:if test="${(kmImeetingMainForm.docStatus=='30' && isBegin==false)|| (kmImeetingMainForm.docStatus=='30' && isDoing ==true)}">
					<%--参会人--%>
					<c:if test="${isAttendPerson == true}">
						<c:if test="${haveLiving ==true}">
							<%--观看直播--%>
							<ui:button id="viewVidio" order="1" title="${lfn:message('km-imeeting:kmImeetingMain.watch.wxvidio') }"
						   	text="${lfn:message('km-imeeting:kmImeetingMain.watch.wxvidio') }" onclick="openMyLiving();">
							</ui:button>
						</c:if>
					</c:if>
					<%--主持人或者创建人--%>
					<c:if test="${canCreatePerson == true}">
						<c:choose>
							<c:when test="${haveLiving == true}">
								<%--观看直播--%>
								<ui:button id="viewVidio" order="1" title="${lfn:message('km-imeeting:kmImeetingMain.watch.wxvidio') }"
										   text="${lfn:message('km-imeeting:kmImeetingMain.watch.wxvidio') }" onclick="openMyLiving();">
								</ui:button>
							</c:when>
							<c:otherwise>
								<%--创建直播--%>
								<ui:button id="viewVidio" order="1" title="${lfn:message('km-imeeting:kmImeetingMain.botton.wxvidio') }"
								   text="${lfn:message('km-imeeting:kmImeetingMain.botton.wxvidio') }" onclick="createMyLiving();">
								</ui:button>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:if>
			</c:if>
		</kmss:ifModuleExist>
	</ui:toolbar>
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
		<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
		<%--发送会议通知，条件：1、通知类型为手动通知 2、未发送会议通知 3、会议未开始 --%>
		<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=sendNotify&fdId=${JsParam.fdId}">
			<c:if test="${kmImeetingMainForm.fdNotifyType=='2' && kmImeetingMainForm.isNotify!='true' && isBegin==false}">
				<ui:button id="sendNotify" order="1" title="${lfn:message('km-imeeting:kmImeetingMain.createStep.sendNotify') }" 
					text="${lfn:message('km-imeeting:kmImeetingMain.createStep.sendNotify') }"   onclick="sendNotify();">
				</ui:button>
			</c:if>
		</kmss:auth>
		<%-- 催办会议，条件：1、已发送会议通知 2、会议未开始  --%>
		<c:if test="${kmImeetingMainForm.isNotify==true && isBegin==false}">
			<kmss:auth
				requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=hastenMeeting&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button order="1" title="${ lfn:message('km-imeeting:kmImeeting.btn.hastenMeeting') }"
					 text="${ lfn:message('km-imeeting:kmImeeting.btn.hastenMeeting') }" onclick="showHastenMeeting()">
				</ui:button>
			</kmss:auth>
		</c:if>
		<%-- 坐席安排，条件：1、已发送会议通知，2、会议未开始 --%>
		<c:if test="${(kmImeetingMainForm.fdNeedPlace eq 'true' or not empty kmImeetingMainForm.fdPlaceId or not empty kmImeetingMainForm.fdOtherPlace) && not empty kmImeetingMainForm.fdTemplateId}">
			<c:choose>
				<c:when test="${kmImeetingMainForm.fdIsSeatPlan == 'true' }">
					<ui:button  order="1" text="${ lfn:message('km-imeeting:kmImeetingMain.viewAgent') }"  onclick="viewSeatPlan();">
					</ui:button>
				</c:when>
				<c:otherwise>
					<c:if test="${isBegin==false}">
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=add&fdImeetingMainId=${JsParam.fdId}" requestMethod="GET">
							<ui:button order="1" text="${ lfn:message('km-imeeting:kmImeetingMain.seatArr') }"  onclick="addSeatPlan();">
							</ui:button>
						</kmss:auth>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>
		<%-- 
		<c:if test="${isBegin==false and kmImeetingMainForm.fdIsVideo eq 'true' and empty roomId}">
			<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=addSyncToKk&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button  order="1" text="同步到kk"  onclick="syncVedioMeeting();">
				</ui:button>
			</kmss:auth>
		</c:if>	
		--%>
		<%-- 会议提前结束，条件：1、已发送会议通知 2、正在召开的会议 --%>
		<c:if test="${isBegin==true && isEnd==false}">
			<kmss:auth
				requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=earlyEndMeeting&fdId=${JsParam.fdId}"
				requestMethod="GET">
				<ui:button order="4" text="${lfn:message('km-imeeting:kmImeeting.btn.earlyEnd') }" 
					onclick="earlyEndMeeting();">
				</ui:button>
			</kmss:auth>
		</c:if>
		<%-- 会议变更，条件：1、已发送会议通知 2、未录入纪要 3、会议未开始 --%>
		<c:if test="${kmImeetingMainForm.fdSummaryFlag=='false' && isBegin==false}">
			<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button order="4" text="${lfn:message('km-imeeting:kmImeeting.btn.change') }" onclick="updateChange();">
				</ui:button>
			</kmss:auth>
		</c:if>
		<%-- 取消会议，条件：1、已发送会议通知，2、会议未开始 --%>
		<c:if test="${isBegin==false}">
			<kmss:auth
				requestURL="/km/imeeting/km_imeeting_main/kmImeetingMainCancel.do?method=cancelMeeting&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button id="cancelbtn" order="4" text="${lfn:message('km-imeeting:kmImeeting.btn.cancel') }"  onclick="showCancelMeeting();">
				</ui:button>
			</kmss:auth>
		</c:if>
		<c:if test="${isEnd==false && kmImeetingMainForm.isBegin != 'true'}">
			<%if(KmImeetingConfigUtil.isBoenEnable()){ %>
				<c:if test="${canBegin || isAdmin}">
					<ui:button  order="1" text="${lfn:message('km-imeeting:kmImeeting.btn.begin') }"  onclick="beginMeeting();">
					</ui:button>
				</c:if>
			<%} %>
		</c:if>
		<c:if test="${isEnd==true}">
			<%if(KmImeetingConfigUtil.isBoenEnable()){ %>
				<c:if test="${canBegin || isAdmin}">
					<ui:button  order="1" text="${lfn:message('km-imeeting:kmImeetingMain.notes') }"  onclick="getMeetingAtt();">
					</ui:button>
				</c:if>
			<%} %>
		</c:if>
		<c:if test="${not empty kmImeetingMainForm.fdTemplateId}">
			<%-- 会议纪要，条件：1、已发送会议通知 2、录入人才可录入 --%>
			<c:if test="${kmImeetingMainForm.fdSummaryFlag=='false'}">
				<kmss:authShow extendOrgIds="${kmImeetingMainForm.fdSummaryInputPersonId};${kmImeetingMainForm.docCreatorId}" roles="SYSROLE_ADMIN,ROLE_KMIMEETING_SUMMARY_CREATE_WITHMEETING">
					<kmss:auth
						requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=operateSummary&meetingId=${JsParam.fdId}"
						requestMethod="GET">
						<ui:button order="4" text="${lfn:message('km-imeeting:kmImeeting.btn.addSummary') }" 
							onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=operateSummary&meetingId=${JsParam.fdId}','_blank');">
					    </ui:button>
					</kmss:auth>
				</kmss:authShow>
			</c:if>
			<%-- 会议纪要(会议纪要创建后，所有可阅读者可见) --%>
			<c:if test="${kmImeetingMainForm.fdSummaryFlag=='true' and not empty summaryId}">
				<kmss:auth
					requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=${summaryId}"
					requestMethod="GET">
					<ui:button order="4" text="${lfn:message('km-imeeting:kmImeeting.btn.viewSummary') }" 
							onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=${summaryId}','_blank');">
					</ui:button>
				</kmss:auth>
			</c:if>
		</c:if>
		
		</c:if>
		<%-- 复制会议 --%> 
		<c:if test="${isTempAvailable && kmImeetingMainForm.docStatus=='30' }">
		<kmss:auth
			requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&meetingId=${JsParam.fdId}&copyMeeting=true"
			requestMethod="GET">
			     <ui:button order="5" text="${lfn:message('km-imeeting:kmImeeting.btn.copy') }" 
						onclick="copyMeeting();">
				 </ui:button>
		</kmss:auth> 
		</c:if>
		 <%-- 编辑文档 --%> 
		<c:if test="${kmImeetingMainForm.docStatus!='00' && kmImeetingMainForm.docStatus!='30'&& kmImeetingMainForm.docStatus!='41'&&kmImeetingMainForm.fdChangeMeetingFlag=='false'}">
			 <kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
				 <ui:button order="3" text="${ lfn:message('button.edit') }"  onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${JsParam.fdId}','_self');">
				 </ui:button>
			</kmss:auth>
		</c:if>
		<c:if test="${kmImeetingMainForm.fdChangeMeetingFlag=='true'}">
			<c:if test="${kmImeetingMainForm.docStatus=='10'}">
				<ui:button text="${ lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${JsParam.fdId}','_self');">
				</ui:button>
			</c:if>
		</c:if>
		<%-- 删除文档 --%>
		<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=delete&fdId=${JsParam.fdId}" requestMethod="GET">
			<ui:button order="4" text="${ lfn:message('button.delete') }"  onclick="Delete();"></ui:button>
		</kmss:auth>
		
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
	</ui:toolbar>
</template:replace>
<%--路径--%>
<template:replace name="path">
	<ui:combin ref="menu.path.category">
		<ui:varParams moduleTitle="${ lfn:message('km-imeeting:module.km.imeeting') }"
		    modulePath="/km/imeeting/"
			modelName="com.landray.kmss.km.imeeting.model.KmImeetingTemplate"
		    autoFetch="false" 
		    extHash="j_path=/kmImeeting_fixed&except=docStatus:00&cri.q=fdTemplate:!{value}"
		    href="/km/imeeting/"
			categoryId="${kmImeetingMainForm.fdTemplateId}" />
	</ui:combin>
</template:replace>
