<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:dataview id="training">
	<ui:source type="AjaxJson">
		{url:'/hr/staff/hr_staff_person_experience/training/hrStaffPersonExperienceTraining.do?method=listData&personInfoId=${JsParam.personInfoId}'}
	</ui:source>
	<ui:render type="Template">
	{$
	<div class="hr_staff_page_resume_item">
		<div class="hr_staff_page_resume_title_bar">
			<a href="javascript:void(0)" class="item_name"><i class="icon_train"></i><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.training') }</span></a>
		</div>
	$}
	if(data.length < 1) {
	{$
		<div>
			${ lfn:message('message.noRecord') }
		</div>
	$}
	}
	{$
	<div class="hr_staff_page_resume_item_content">
			<div class="hr_staff_rtf_content">
	$}
	for(var i=0; i<data.length; i++){
		{$
				<!-- 培训经理 -->
				<div class="hr_staff_resume_project_item">
					<div class="hr_staff_resume_project_head">
						<!-- 岗位信息 -->
						<div class="hr_staff_resume_project_info">
							<h3 class="training_name">
							<c:if test="${JsParam.personInfoId eq KMSS_Parameter_CurrentUserId}">
								<div class="hr_staff_opt_btn">
									<i class="lui_icon_s icon_edit" title="${ lfn:message('hr-staff:hr.staff.btn.edit') }" onclick="addOrEdit('training', '{% data[i].fdId %}');"></i>
									<i class="lui_icon_s icon_del" title="${ lfn:message('hr-staff:hr.staff.btn.del') }" onclick="delDetail('training', '{% data[i].fdId %}');"></i>
								</div>
							</c:if>
							<span>
								{% data[i].fdTrainingName %}
							</span>
							</h3>
							<h3 class="unit_name">{% data[i].fdTrainingUnit %}</h3>
							<p class="training_date">
							{% data[i].fdBeginDate %} 
			$}
			if(data[i].fdBeginDate && data[i].fdEndDate){ 
				{$			
					<c:out value="~"></c:out>
				$}
			} 
			{$
							
							 {% data[i].fdEndDate %}</p>
						</div>
					</div>
					<div class="hr_staff_resume_project_desc">
						{% data[i].fdMemo %}
					</div>
				</div>
	 	 $}
	}
	 {$
	 </div>
		</div>
	 $}
	 {$
		 <c:if test="${JsParam.personInfoId eq KMSS_Parameter_CurrentUserId}">
		 	<div class="hr_staff_page_resume_btn_bar" onclick="addOrEdit('training');">
				<span><i class="lui_icon_s icon_add"></i><span>${ lfn:message('hr-staff:hr.staff.btn.add') }</span></span>
			</div>
		</c:if>
	</div>
	 $}
	</ui:render>
</ui:dataview>
