<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:dataview id="project">
	<ui:source type="AjaxJson">
		{url:'/hr/staff/hr_staff_person_experience/project/hrStaffPersonExperienceProject.do?method=listData&personInfoId=${JsParam.personInfoId}'}
	</ui:source>
	<ui:render type="Template">
	{$
	<div class="hr_staff_page_resume_item">
		<div class="hr_staff_page_resume_title_bar">
			<a href="javascript:void(0)" class="item_name"><i class="icon_project"></i><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.project') }</span></a>
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
			data[i].fdName = env.fn.formatText(data[i].fdName);
			{$
				<!-- 项目经历 -->
				<div class="hr_staff_resume_project_item">
					<div class="hr_staff_resume_project_head">
						<!-- 项目信息 -->
						<div class="hr_staff_resume_project_info">
							<h3 class="project_name">
							<c:if test="${JsParam.personInfoId eq KMSS_Parameter_CurrentUserId}">
								<div class="hr_staff_opt_btn">
									<i class="lui_icon_s icon_edit" title="${ lfn:message('hr-staff:hr.staff.btn.edit') }" onclick="addOrEdit('project', '{% data[i].fdId %}');"></i>
									<i class="lui_icon_s icon_del" title="${ lfn:message('hr-staff:hr.staff.btn.del') }" onclick="delDetail('project', '{% data[i].fdId %}');"></i>
								</div>
							</c:if>
							<span>
								{%data[i].fdName %}
							</span>
							<em>
			$}
				if(data[i].fdRole!=""){
					data[i].fdRole = env.fn.formatText(data[i].fdRole);
					{$
						<c:out value="（{%data[i].fdRole%}）"></c:out>
					$}
				}
		{$ 
							</em>
							</h3>
							<p class="project_date">
							{% data[i].fdBeginDate %}
		$}
			if(data[i].fdBeginDate && data[i].fdEndDate){ 
				{$			
					<c:out value="~"></c:out>
				$}
			}				 
			data[i].fdMemo = env.fn.formatText(data[i].fdMemo);				 
		{$
							 {% data[i].fdEndDate %}</p>
						</div>
						<!-- 项目参与人员 -->
					</div>
					<div class="hr_staff_resume_project_desc">
						<c:out value="{% data[i].fdMemo%}" escapeXml="false"></c:out> 
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
			<div class="hr_staff_page_resume_btn_bar" onclick="addOrEdit('project');">
				<span><i class="lui_icon_s icon_add"></i><span>${ lfn:message('hr-staff:hr.staff.btn.add') }</span></span>
			</div>
		</c:if>
		</div>
	$}
	</ui:render>
</ui:dataview>
