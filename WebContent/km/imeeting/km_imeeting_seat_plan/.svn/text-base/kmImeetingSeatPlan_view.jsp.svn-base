<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" bodyClass="lui_seat_body">
	<%-- 样式 --%>
	<template:replace name="head">
		<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
	</template:replace>
	
	<%-- 按钮栏--%>
	<template:replace name="toolbar">
	
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6"> 
			
			<kmss:auth requestURL="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=edit&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}" requestMethod="GET">
				<c:if test="${isBegin != 'true'}">
				<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('kmImeetingSeatPlan.do?method=edit&fdId=${JsParam.fdId}','_self');" order="1" ></ui:button>
				</c:if>
				<ui:button text="打印" onclick="printDoc();" order="1" ></ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" order="2"  onclick="Com_CloseWindow()">
			</ui:button>
		</ui:toolbar>  
	
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content">
		<p class="lui_form_subject">
			<c:if test="${not empty kmImeetingSeatPlanForm.docSubject}">
				<c:out value="${kmImeetingSeatPlanForm.docSubject}" />
			</c:if>
		</p>
		<div class="lui_seat_arrangement_wrap">
			<div id="seat" class="lui_seat_arrangement_content">
			</div>
			
			<div class="lui_seat_arrangement_aside">
				<c:if test="${kmImeetingSeatPlanForm.fdIsTopicPlan == 'true' }">
				    <div class="lui_seat_arrangement_aside_item">
						<div class="lui_seat_arrangement_aside_item_head">
							<h3 class="lui_seat_arrangement_aside_item_title">按议题排位
							</h3>
						</div>
						<ul class="lui_seat_arrangement_list" id="topicTab"></ul>
				    </div>
			    </c:if>
			    
					
				<!--主持人-->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">主持人</h4>
					</div>
					<div id="hostTab" class="lui_seat_person_list">
					</div>
				</div>
				      
				    
				      
			    <!-- 参加人员 -->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">参加人员</h4>
					</div>
					<div id="attendTab" class="lui_seat_person_list">
					</div>
				</div>
					
				<!-- 纪要人员 -->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="summaryWrap">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">纪要人员</h4>
					</div>
					<div id="summaryTab" class="lui_seat_person_list">
					</div>
				</div>
					
				<!-- 列席人员 -->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="participantWrap">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">列席人员</h4>
					</div>
					<div id="participantTab" class="lui_seat_person_list">
					</div>
				</div>
					
				<!-- 上会材料汇报人 -->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="reportersWrap">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">上会材料汇报人</h4>
					</div>
					<div id="reportersTab" class="lui_seat_person_list">
					</div>
				</div>
					
				<!-- 议题汇报人 -->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="reporterWrap">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">议题汇报人</h4>
					</div>
					<div id="reporterTab" class="lui_seat_person_list">
					</div>
				</div>
					
				<!-- 议题列席人员 -->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="attendUnitWrap">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">议题列席人员</h4>
					</div>
					<div id="attendUnitTab" class="lui_seat_person_list">
					</div>
				</div>
					
				<!-- 议题旁听人员 -->
				<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="listenUnitWrap">
					<div class="lui_seat_arrangement_subItem_head">
						<h4 class="lui_seat_arrangement_subItem_title">议题旁听人员</h4>
					</div>
					<div id="listenUnitTab" class="lui_seat_person_list">
					</div>
				</div>
			</div>
		</div>
		<%@include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_view_js.jsp"%>
	</template:replace>
</template:include>