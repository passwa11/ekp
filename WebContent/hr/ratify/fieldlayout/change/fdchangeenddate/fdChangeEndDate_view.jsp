<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<span class="xform_fieldlayout" style="<%=parse.getStyle()%>">
	<c:choose>
		<c:when test="${hrRatifyChangeForm.fdIsLongtermContract == true }">
			${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.1') }
		</c:when>
		<c:otherwise>
			<c:out value="${hrRatifyChangeForm.fdChangeEndDate}"/>
		</c:otherwise>
	</c:choose>
</span>