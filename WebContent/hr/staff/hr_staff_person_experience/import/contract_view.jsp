<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="staff_resume_itemlist_tb_content">
	<ui:dataview id="contract">
		<ui:source type="AjaxJson">
			{url:'/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=listData&personInfoId=${JsParam.personInfoId}'}
		</ui:source>
		<ui:render type="Template">
			{$
			<table id="contract" class="staff_resume_normal_tb tb_normal">
				<tr>
					<th class="td_normal_title" width="15%">${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }</th>
					<th class="td_normal_title" width="15%">${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContType') }</th>
					<th class="td_normal_title" width="15%">${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdSignType') }</th>
					<th class="td_normal_title" width="15%">${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }</th>
					<th class="td_normal_title" width="15%">${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }</th>
					<th class="td_normal_title" width="15%">${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContStatus') }</th>
					<c:if test="${!param.isPrint}">
					<th class="opt_td td_normal_title"><span>${ lfn:message('list.operation') }</span></th>
					</c:if>
				</tr>
			$}
			if(data.length < 1) {
			{$
			<tr>
				<c:if test="${!param.isPrint}">
				<td colspan="7">
				</c:if>
				<c:if test="${param.isPrint}">
				<td colspan="6">
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
						<span class="com_subject"><a href="javascript:void(0);" onclick="viewDetail('contract', '{% data[i].fdId %}');">{% data[i].fdName %}</a></span>
					</td>
					<td>{% data[i].fdContType %}</td>
					<td>{% data[i].fdSignType %}</td>
					<td>{% data[i].fdBeginDate %}</td>
					<td>{% data[i].fdEndDate %}</td>
					<td>{% data[i].fdContStatus %}</td>
					<c:if test="${!param.isPrint}">
					<td class="opt_td">
						<span class="lui_icon_s icon_opt_del" onclick="delDetail('contract', '{% data[i].fdId %}');" title="${ lfn:message('button.delete') }"></span>
						<span class="lui_icon_s icon_opt_edit" onclick="contAddOrEdit('{% data[i].fdId %}');" title="${ lfn:message('button.edit') }"></span>
					</td>
					</c:if>
				</tr>
				$}
			}
			{$
				<c:if test="${!param.isPrint}">
				<tr>
					<td colspan="7" class="add_td" onclick="contAddOrEdit();" title="${ lfn:message('button.add') }"> <span class="lui_icon_s icon_opt_add"></span><span>${ lfn:message('button.add') }</span></td>
				</tr>
				</c:if>
			</table>
			$}
		</ui:render>
	</ui:dataview>
</div>
<script>
	seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
		window.contAddOrEdit = function(id){
			var iframeUrl = "/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=add&personInfoId=${JsParam.personInfoId}&type=contract";
			var method = "save";
			var title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.contract"/>';
			if(id) {
				iframeUrl = "/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=edit&fdId=" + id+ "&personInfoId=${JsParam.personInfoId}&type=contract";
			}
			dialog.iframe(iframeUrl, title, function(data) {
				LUI('contract').load();
			}, {
				width : 900,
				height : 500
			});
		};
	});
</script>