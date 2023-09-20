<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="staff_resume_itemlist_tb_content">
	<ui:dataview id="qualification">
		<ui:source type="AjaxJson">
			{url:'/hr/staff/hr_staff_person_experience/qualification/hrStaffPersonExperienceQualification.do?method=listData&personInfoId=${JsParam.personInfoId}'}
		</ui:source>
		<ui:render type="Template">
			{$
			<table class="staff_resume_normal_tb tb_normal">
				<tr>
					<th class="td_normal_title" width="40%">${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdCertificateName') }</th>
					<th class="td_normal_title" width="20%">${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdBeginDate') }</th>
					<th class="td_normal_title" width="20%">${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdEndDate') }</th>
					<th class="td_normal_title" width="20%">${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdAwardUnit') }</th>
					<c:if test="${!param.isPrint}">
					<th class="opt_td td_normal_title"><span>${ lfn:message('list.operation') }</span></th>
					</c:if>
				</tr>
			$}
		
			if(data.length < 1) {
			{$
			<tr>
				<c:if test="${!param.isPrint}">
				<td colspan="5">
				</c:if>
				<c:if test="${param.isPrint}">
				<td colspan="4">
				</c:if>
					${ lfn:message('message.noRecord') }
				</td>
			</tr>
			$}
			}
			for(var i=0; i<data.length; i++) {
				{$
				<tr>
					<td>
						<span class="com_subject">{% data[i].fdCertificateName %}</span>
					</td>
					<td>{% data[i].fdBeginDate %}</td>
					<td>{% data[i].fdEndDate %}</td>
					<td>{% data[i].fdAwardUnit %}</td>
					<c:if test="${!param.isPrint}">
					<td class="opt_td">
						<span class="lui_icon_s icon_opt_del" onclick="delDetail('qualification', '{% data[i].fdId %}');" title="${ lfn:message('button.delete') }"></span>
						<span class="lui_icon_s icon_opt_edit" onclick="addOrEdit('qualification', '{% data[i].fdId %}');" title="${ lfn:message('button.edit') }"></span>
					</td>
					</c:if>
				</tr>
				$}
			}
			{$
				<c:if test="${!param.isPrint}">
				<tr>
					<td colspan="5" class="add_td" onclick="addOrEdit('qualification');" title="${ lfn:message('button.add') }"> <span class="lui_icon_s icon_opt_add"></span><span>${ lfn:message('button.add') }</span></td>
				</tr>
				</c:if>
			</table>
			$}
		</ui:render>
	</ui:dataview>
</div>
