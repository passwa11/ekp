<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" bodyClass="lui_seat_no_template_body">

	<%-- 样式 --%>
	<template:replace name="head">
		<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
	</template:replace>
	
	<%-- 按钮栏--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<kmss:auth requestURL="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=save&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.save') }" order="2" onclick="window.submit('save')">
				</ui:button>
			</kmss:auth>
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
			
			<ui:step id="__step"  onSubmit="window.submit('save')" >
			
				<ui:content title="座席设置" toggle="false">
					<div style="float:right;">
						<div style="vertical-align: middle;">
							<ui:toolbar id="Btntoolbar">
								<!-- 添加行-->
								<ui:button text="${lfn:message('km-imeeting:py.addRow')}" onclick="addRow()" order="1" ></ui:button>
								<!-- 添加列-->
								<ui:button text="${lfn:message('km-imeeting:py.addCol')}" onclick="addCol()" order="1" ></ui:button>
								<!-- 清空全部 -->
								<ui:button text="清空全部" order="1" onclick="window.clearAllCustomData()">
								</ui:button>
							</ui:toolbar>
						</div>
					</div>
					<!-- 坐席设置 Starts -->
				  	<div class="lui_seat_setting_wrap">
				    	<div class="lui_seat_setting_aside">
				    		<div class="lui_seat_setting_aside_inner">
				      			<div class="lui_seat_setting_aside_item">
				      				<h3 class="lui_seat_setting_aside_item_title">座席模板</h3>
							        <xform:select property="fdSeatTemplateId" showStatus="edit" onValueChange="changeSeatTemplate(this.value);" style="width:85%">
                                        <xform:beanDataSource serviceBean="kmImeetingSeatTemplateService" selectBlock="fdId,fdName" />
                                    </xform:select>
						        	<div class="lui_seat_setting_seat_wrapper">
						        		<span class="lui_seat_setting_seat">座位数：
											<c:choose>
												<c:when test="${kmImeetingSeatTemplateForm.fdSeatCount != null }">
													<span id="seatCount" class="lui_seat_setting_seat_number">${kmImeetingSeatTemplateForm.fdSeatCount}</span>
												</c:when>
												<c:otherwise>
													<span id="seatCount" class="lui_seat_setting_seat_number">0</span>
												</c:otherwise>
											</c:choose>
										</span>
						        	</div>
				      			</div>
						      	<div class="lui_seat_setting_aside_item">
						          	<h3 class="lui_seat_setting_aside_item_title">会议室元素</h3>
						          	<div id="seatTemplateElement">
						          	</div>
						      	</div>
				    		</div>
				    	</div>
				    
				    	<div id="seatTemplate" class="lui_seat_setting_content">
				    	</div>
				  	</div>
				</ui:content>
				
				<ui:content title="座席安排" toggle="false">
					<p class="lui_form_subject">
						<c:if test="${not empty kmImeetingSeatPlanForm.docSubject}">
							<c:out value="${kmImeetingSeatPlanForm.docSubject}" />
						</c:if>
					</p>
					<div class="lui_seat_arrangement_wrap">
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
				</ui:content>
			</ui:step>
			
		</html:form>
		<c:choose>
			<c:when test="${kmImeetingSeatPlanForm.fdIsShowTopic == 'true'}">
				<%@include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_edit_no_template_js.jsp"%>
			</c:when>
			<c:otherwise>
				<%@include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_edit_no_template_no_topic_js.jsp"%>
			</c:otherwise>
		</c:choose>
		
		
	</template:replace>
</template:include>