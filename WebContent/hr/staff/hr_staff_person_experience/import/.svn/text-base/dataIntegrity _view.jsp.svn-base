<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.hr.staff.util.HrStaffPrivateUtil"%>
<c:set var="userId" value="${JsParam.personInfoId}" scope="request"/>
<%
	String fdPersonId = (String)request.getAttribute("userId");
%>

<%if(HrStaffPrivateUtil.isSelf(fdPersonId)){%>
	<ui:dataview>
		<ui:source type="AjaxJson">
			{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=dataIntrgrity&personInfoId=${JsParam.personInfoId}'}
		</ui:source>
		<ui:render type="Template">
		if(data.length > 0) {
			{$
				<div class="resume_intrgrity">
					<h4>${lfn:message('hr-staff:hrStaffPerson.resume.integrity')}<em>{%data[0].intrgrity%}%</em></h4>
					<div class="sys_zone_progress_bar"><span style="width:{%data[0].intrgrity%}%"></span></div>
				</div>
			$}
		}else{
			{$<div></div>$}
		}	
		</ui:render>
	</ui:dataview>
<%} %>

