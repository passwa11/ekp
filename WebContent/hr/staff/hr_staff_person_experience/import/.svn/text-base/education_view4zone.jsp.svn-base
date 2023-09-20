<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:dataview id="education">
	<ui:source type="AjaxJson">
		{url:'/hr/staff/hr_staff_person_experience/education/hrStaffPersonExperienceEducation.do?method=listData&personInfoId=${JsParam.personInfoId}'}
	</ui:source>
	<ui:render type="Template">
	{$
	<div class="hr_staff_page_resume_item">
		<div class="hr_staff_page_resume_title_bar">
			<a href="javascript:void(0)" class="item_name"><i class="icon_teach"></i><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.education') }</span></a>
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
				<!-- 教育经历 -->
			<div class="hr_staff_resume_project_item">
				<div class="hr_staff_resume_project_head">
					<!-- 学校信息 -->
					<div class="hr_staff_resume_project_info">
						<h3 class="school_name">
						<c:if test="${JsParam.personInfoId eq KMSS_Parameter_CurrentUserId}">
							<div class="hr_staff_opt_btn">
								<i class="lui_icon_s icon_edit" title="${ lfn:message('hr-staff:hr.staff.btn.edit') }" onclick="addOrEdit('education', '{% data[i].fdId %}');"></i>
								<i class="lui_icon_s icon_del" title="${ lfn:message('hr-staff:hr.staff.btn.del') }" onclick="delDetail('education', '{% data[i].fdId %}');"></i>
							</div>
						</c:if>
						<span>
							{% data[i].fdSchoolName %}
						</span>
						<em>
		$}
				if(data[i].fdDegree  || data[i].fdMajor){
					{$
						<c:out value="（"></c:out>
					$}
				}
		
				if(data[i].fdDegree ){
					{$
						<c:out value="{%data[i].fdDegree%} "></c:out>
					$}
				}	
				if(data[i].fdDegree && data[i].fdMajor){
					{$
						<c:out value="."></c:out>
					$}
				}	
				if(data[i].fdMajor ){
					{$
						<c:out value="{%data[i].fdMajor%} "></c:out>
					$}
				}	
				if(data[i].fdDegree  || data[i].fdMajor){
					{$
						<c:out value="）"></c:out>
					$}
				}
		{$			
						 </em>
						</h3>
						<p class="teach_date">
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
			<div class="hr_staff_page_resume_btn_bar" onclick="addOrEdit('education');">
				<span><i class="lui_icon_s icon_add"></i><span>${ lfn:message('hr-staff:hr.staff.btn.add') }</span></span>
			</div>
		</c:if>
	</div>
	$}
	</ui:render>
</ui:dataview>
