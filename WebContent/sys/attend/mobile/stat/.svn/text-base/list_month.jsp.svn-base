<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="java.text.DecimalFormat"%>

<list:data>
	<list:data-columns var="sysAttendStatMonth" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }">
		</list:data-column>
		<list:data-column col="fdTotalTime" title="">
			<c:out value="${sysAttendStatMonth.fdTotalTime}" />
		</list:data-column>
		<list:data-column col="dept" title="">
			<c:out value="${sysAttendStatMonth.docCreator.fdParent.fdName}" />
		</list:data-column>
		<list:data-column col="docCreatorName" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }">
			<c:out value="${sysAttendStatMonth.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="docCreatorImg" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }" escape="false">
			<person:headimageUrl contextPath="true" personId="${sysAttendStatMonth.docCreator.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="docCreatorId" title="" escape="false">
			${sysAttendStatMonth.docCreator.fdId}
		</list:data-column>
		<list:data-column col="fdLateCount" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdLateCount}" />
		</list:data-column>
		<list:data-column col="fdLeftCount" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdLeftCount}" />
		</list:data-column>
		<list:data-column col="fdStatusDays" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdStatusDays}" />
		</list:data-column>
		
		<c:set var="_fdAbsentDaysCount" value="${sysAttendStatMonth.fdAbsentDaysCount }"></c:set>
		<c:set var="_fdAbsentDays" value="${sysAttendStatMonth.fdAbsentDays}"></c:set>
		<%
			DecimalFormat df = new DecimalFormat("#.#");
			Float _fdAbsentDaysCount = (Float) pageContext.getAttribute("_fdAbsentDaysCount");
			Integer _fdAbsentDays = (Integer) pageContext.getAttribute("_fdAbsentDays");
			pageContext.setAttribute("__fdAbsentDaysCount", _fdAbsentDaysCount == null ? (_fdAbsentDays == null ? 0 : _fdAbsentDays) : df.format(_fdAbsentDaysCount));
		%>
		<list:data-column col="fdAbsentDays" title="" escape="false">
			${__fdAbsentDaysCount}
		</list:data-column>
		
		<list:data-column col="fdMissedCount" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdMissedCount}" />
		</list:data-column>
		<list:data-column col="fdOutsideCount" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdOutsideCount}" />
		</list:data-column>
		<list:data-column col="fdTripDays" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdTripDays}" />
		</list:data-column>
		<list:data-column col="fdOffDays" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdOffDays}" />
		</list:data-column>
		<list:data-column col="fdOutgoingTime" title="" escape="false">
			<c:out value="${sysAttendStatMonth.fdOutgoingTime}" />
		</list:data-column>
		<list:data-column col="fdType" title="" escape="false">
			${fdType}
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>