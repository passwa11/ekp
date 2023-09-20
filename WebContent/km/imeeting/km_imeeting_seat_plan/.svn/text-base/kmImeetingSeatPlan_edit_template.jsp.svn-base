<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" bodyClass="lui_seat_body">

	<%-- 样式 --%>
	<template:replace name="head">
		<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
	</template:replace>
	
	<%-- 按钮栏--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<c:if test="${kmImeetingSeatPlanForm.method_GET=='edit'}">
				<kmss:auth requestURL="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=update&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}" requestMethod="GET">
					<ui:button text="${lfn:message('button.update') }" order="2" onclick="window.submit('update')">
					</ui:button>
				</kmss:auth>
			</c:if>
			<c:if test="${kmImeetingSeatPlanForm.method_GET=='add'}">
				<kmss:auth requestURL="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=save&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}" requestMethod="GET">
					<ui:button text="${lfn:message('button.save') }" order="2" onclick="window.submit('save')">
					</ui:button>
				</kmss:auth>
			</c:if>
			<ui:button text="${ lfn:message('button.close') }" order="3"  onclick="Com_CloseWindow()">
			</ui:button>
		</ui:toolbar>  
	
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content">
		<html:form action="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do">
			<html:hidden property="method_GET"/>
			<html:hidden property="fdId"/>
			<html:hidden property="fdImeetingMainId"/>
			<html:hidden property="fdSeatDetail"/>
			<html:hidden property="fdCols"/>
			<html:hidden property="fdRows"/>
			<p class="lui_form_subject">
				<c:if test="${not empty kmImeetingSeatPlanForm.docSubject}">
					<c:out value="${kmImeetingSeatPlanForm.docSubject}" />
				</c:if>
			</p>
			<div class="lui_seat_arrangement_wrap">
				<!-- 临时坐席 -->
				<div class="lui_seat_fixBar" id="tempSeatTab">
				</div>
				<div id="seat" class="lui_seat_arrangement_content">
				</div>
				<div class="lui_seat_arrangement_aside">
					<c:if test="${kmImeetingSeatPlanForm.fdIsShowTopic == 'true' }">
						<div class="lui_seat_arrangement_aside_item">
							<div class="lui_seat_arrangement_aside_item_head">
								<h3 class="lui_seat_arrangement_aside_item_title">按议题排位
								</h3>
								<div class="lui_seat_arrangement_aside_item_opt">
									<c:choose>
										<c:when test="${kmImeetingSeatPlanForm.method_GET == 'add' }">
											<ui:switch property="fdIsTopicPlan" checkVal="true" unCheckVal="false" enabledText="${ lfn:message('km-imeeting:kmImeeting.topicMng.on') }" 
												disabledText="${ lfn:message('km-imeeting:kmImeeting.topicMng.off') }" onValueChange="topicPlanChange();"></ui:switch>
										</c:when>
										<c:otherwise>
											<html:hidden property="fdIsTopicPlan"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<ul class="lui_seat_arrangement_list" id="topicTab"></ul>
					    </div>
				    </c:if>
				    
				    <!--人员安排-->
					<div class="lui_seat_arrangement_aside_item">
						<div class="lui_seat_arrangement_aside_item_head">
							<h3 class="lui_seat_arrangement_aside_item_title">人员安排</h3>
						</div>
						<xform:checkbox property="showPlan" onValueChange="showPlanChange()">
		          			<xform:simpleDataSource value="true">仅显示未排位人员</xform:simpleDataSource>
		          		</xform:checkbox>
		          		<xform:checkbox property="fdFeedback" onValueChange="feedbackChange()">
		          			<xform:simpleDataSource value="true" >仅显示已回执且参加人员</xform:simpleDataSource>
		          		</xform:checkbox>
		          		
						<div class="lui_seat_btn_group">
							<span class="lui_seat_btn lui_seat_btn_primary com_bgcolor_d" onclick="autoPlan(0);">一键排位</span>
							<span class="lui_seat_btn lui_seat_btn_plain lui_seat_btn_block" onclick="resetPerson(0);">重置</span>
						</div>
					</div> 
					
					<!--主持人-->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">主持人</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(1);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(1);">重置</span>
							</span>
						</div>
						<div id="hostTab" class="lui_seat_person_list">
						</div>
					</div>
				      
				    <!-- 参加人员 -->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">参加人员</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(2);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(2);">重置</span>
							</span>
						</div>
						<div id="attendTab" class="lui_seat_person_list">
						</div>
					</div>
					
					<!-- 纪要人员 -->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="summaryWrap">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">纪要人员</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(3);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(3);">重置</span>
							</span>
						</div>
						<div id="summaryTab" class="lui_seat_person_list">
						</div>
					</div>
					
					<!-- 列席人员 -->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="participantWrap">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">列席人员</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(4);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(4);">重置</span>
							</span>
						</div>
						<div id="participantTab" class="lui_seat_person_list">
						</div>
					</div>
					
					<!-- 上会材料汇报人 -->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="reportersWrap">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">上会材料汇报人</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(5);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(5);">重置</span>
							</span>
						</div>
						<div id="reportersTab" class="lui_seat_person_list">
						</div>
					</div>
					
					<!-- 议题汇报人 -->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="reporterWrap">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">议题汇报人</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(6);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(6);">重置</span>
							</span>
						</div>
						<div id="reporterTab" class="lui_seat_person_list">
						</div>
					</div>
					
					<!-- 议题列席人员 -->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="attendUnitWrap">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">议题列席人员</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(7);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(7);">重置</span>
							</span>
						</div>
						<div id="attendUnitTab" class="lui_seat_person_list">
						</div>
					</div>
					
					<!-- 议题旁听人员 -->
					<div class="lui_seat_arrangement_subItem lui_seat_arrangement_item_wrapper" id="listenUnitWrap">
						<div class="lui_seat_arrangement_subItem_head">
							<h4 class="lui_seat_arrangement_subItem_title">议题旁听人员</h4>
							<span class="lui_seat_arrangement_subItem_opt">
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="autoPlan(8);">一键排位</span>
								<span class="lui_seat_btn lui_seat_btn_plain" onclick="resetPerson(8);">重置</span>
							</span>
						</div>
						<div id="listenUnitTab" class="lui_seat_person_list">
						</div>
					</div>
				</div>
				
			</div>
		</html:form>
		<c:choose>
			<c:when test="${kmImeetingSeatPlanForm.fdIsShowTopic == 'true'}">
				<%@include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_edit_template_js.jsp"%>
			</c:when>
			<c:otherwise>
				<%@include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_edit_template_no_topic_js.jsp"%>
			</c:otherwise>
		</c:choose>
	</template:replace>
</template:include>