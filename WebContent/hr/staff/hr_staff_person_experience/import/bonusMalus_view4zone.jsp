<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.hr.staff.util.HrStaffPrivateUtil"%>
<c:set var="userId" value="${JsParam.personInfoId}" scope="request"/>
<%
	String fdPersonId = (String)request.getAttribute("userId");
	if(!HrStaffPrivateUtil.isBonusPrivate(fdPersonId)){
%>

		<ui:button id="_experience" parentId="top"  onclick="gotoExpView();"
			styleClass="lui_zone_experience_off" title="${lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }" text="${lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }" style="pading:5px;">
		</ui:button>
<ui:dataview id="bonusMalus">
	<ui:source type="AjaxJson">
		{url:'/hr/staff/hr_staff_person_experience/bonusMalus/hrStaffPersonExperienceBonusMalus.do?method=listData&personInfoId=${JsParam.personInfoId}'}
	</ui:source>
	<ui:render type="Template">
		{$
		<div class="lui_sys_zone_panel_frame">
			<div class="lui_sys_zone_panel_nav">
				<div class="lui_sys_zone_panel_item">${ lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }</div>
			</div>
			<div class="lui_sys_zone_panel_content">
				<ul class="lui_sys_zone_honor_list">
		$}
		if(data.length < 1) {
		{$
			<div>
				${ lfn:message('message.noRecord') }
			</div>
		$}
		}
		for(var i=0; i<data.length; i++){
			{$
				<li>
					<a href="javascript:void(0)"><i class="lui_icon_s icon_honor"></i><span>{% data[i].fdBonusMalusName%}</span></a>
				</li>
			$}
		}			
		{$
				</ul>
			</div>
		</div>
		$}
	</ui:render>
</ui:dataview>
<%
	}
%>