<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:dataview id="brief">
	<ui:source type="AjaxJson">
		{url:'/hr/staff/hr_staff_person_experience/brief/hrStaffPersonExperienceBrief.do?method=listData&personInfoId=${JsParam.personInfoId}'}
	</ui:source>
	<ui:render type="Template">
		if(data.length < 1) {
		{$
		<div class="hr_staff_page_resume_item">
			<div class="hr_staff_page_resume_title_bar">
				<a href="javascript:void(0)" class="item_name"><i class="icon_person"></i><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.brief') }</span></a>
			</div>
			<div>
				${ lfn:message('message.noRecord') }
			</div>
			<c:if test="${JsParam.personInfoId eq KMSS_Parameter_CurrentUserId}">
				<div class="hr_staff_page_resume_btn_bar" onclick="addOrEdit('brief');">
					<span><i class="lui_icon_s icon_add"></i><span>${ lfn:message('hr-staff:hr.staff.btn.add') }</span></span>
				</div>
			</c:if>
		</div>
		$}
		}
	
	
	for(var i=0; i<data.length; i++){
		data[i].fdContent = env.fn.formatText(data[i].fdContent);
		{$
		<div class="hr_staff_page_resume_item">
			<div class="hr_staff_page_resume_title_bar">
				<a href="javascript:void(0)" class="item_name"><i class="icon_person"></i><span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.brief') }</span></a>
				<c:if test="${JsParam.personInfoId eq KMSS_Parameter_CurrentUserId}">
					<a href="javascript:void(0)" class="hr_staff_page_resume_edit_btn" title="${ lfn:message('hr-staff:hr.staff.btn.edit') }" onclick="addOrEdit('brief', '{% data[i].fdId %}');"><i class="lui_icon_s icon_edit"></i></a>
				</c:if>
			</div>
			<div class="hr_staff_page_resume_item_content">
				<div class="hr_staff_rtf_content">
					<div class="hr_staff_resume_project_desc">
						{% data[i].fdContent%}
					</div>
				</div>
			</div>
		</div>
		$}
	}
	</ui:render>
</ui:dataview>
