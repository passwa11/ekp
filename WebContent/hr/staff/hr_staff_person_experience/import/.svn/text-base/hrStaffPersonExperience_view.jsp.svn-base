<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="userId" value="${param.personInfoId}"/>
<%--个人经历--%>
<ui:content expand="${('experienceContract' eq JsParam.anchor || 'experienceWork' eq JsParam.anchor || 'experienceEducation' eq JsParam.anchor || 'experienceTraining' eq JsParam.anchor || 'experienceQualification' eq JsParam.anchor || 'experienceBonusMalus' eq JsParam.anchor) ? 'true' : 'false'}" title="${ lfn:message('hr-staff:table.hrStaffPersonExperience') }">
	<div class="lui_tabpage_float_content_l">
		<div class="lui_tabpage_float_content_r">
			<div class="lui_tabpage_float_content_c">
				<div>
					<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
						<!-- 个人经历 列表 Starts -->
						<div id="personExperiences" class="staff_resume_itemlist_content">
							<dl>
								<dt>
									<h3 class="reusme_item_title" id="experienceContract">
										<span class="lui_icon_m icon_contract"></span><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.contract') }</span>
									</h3>
								</dt>
								<dd>
									<!-- 合同信息 Starts -->
									<c:import url="/hr/staff/hr_staff_person_experience/import/contract_view.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
									<!--合同信息 End-->
								</dd>
								<dt>
									<h3 class="reusme_item_title" id="experienceWork">
										<span class="lui_icon_m icon_work"></span><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.work') }</span>
									</h3>
								</dt>
								<dd>
									<!-- 工作经历 Starts -->
									<c:import url="/hr/staff/hr_staff_person_experience/import/work_view.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
									<!--工作经历 End-->
								</dd>
								<dt>
									<h3 class="reusme_item_title" id="experienceEducation">
										<span class="lui_icon_m icon_teach"></span><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.education') }</span>
									</h3>
								</dt>
								<dd>
									<!-- 教育经历 Starts -->
									<c:import url="/hr/staff/hr_staff_person_experience/import/education_view.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
									<!-- 教育经历 End-->
								</dd>
								<dt>
									<h3 class="reusme_item_title" id="experienceTraining">
										<span class="lui_icon_m icon_train"></span><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.training') }</span>
									</h3>
								</dt>
								<dd>
									<!-- 培训记录 Starts -->
									<c:import url="/hr/staff/hr_staff_person_experience/import/training_view.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
									<!-- 培训记录 End-->
								</dd>
								<dt>
									<h3 class="reusme_item_title" id="experienceQualification">
										<span class="lui_icon_m icon_catalog"></span><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.qualification') }</span>
									</h3>
								</dt>
								<dd>
									<!-- 资格证书 Starts -->
									<c:import url="/hr/staff/hr_staff_person_experience/import/qualification_view.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
									<!-- 资格证书 End-->
								</dd>
								<dt>
									<h3 class="reusme_item_title" id="experienceBonusMalus">
										<span class="lui_icon_m icon_catalog"></span><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }</span>
									</h3>
								</dt>
								<dd>
									<!-- 奖励信息 Starts -->
									<c:import url="/hr/staff/hr_staff_person_experience/import/bonusMalus_view.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
									<!-- 奖励信息 End -->
								</dd>
							</dl>
						</div>
						<!-- 个人经历 列表 Ends -->
					</div>
					<div data-lui-mark="panel.content.operation" class="lui_portlet_operations clearfloat"> </div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/hr/staff/hr_staff_person_experience/import/experience_operate.jsp"%>
</ui:content>
		