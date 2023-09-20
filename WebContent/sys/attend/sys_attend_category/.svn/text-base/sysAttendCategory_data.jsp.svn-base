<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.sys.attend.util.AttendUtil,java.util.Date,com.landray.kmss.sys.attend.model.SysAttendCategory" %>
<list:data>
	<list:data-columns var="sysAttendCategory" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<c:if test="${sysAttendCategory.fdType eq 1}">
			<c:set var='fdNameTitle' value="${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }" />
		</c:if>
		<c:if test="${sysAttendCategory.fdType eq 2}">
			<c:set var='fdNameTitle' value="${ lfn:message('sys-attend:sysAttendCategory.custom.fdName') }" />
		</c:if>
		<list:data-column property="fdName" title="${ fdNameTitle }">
		</list:data-column>
		<list:data-column property="fdOrder" title="${ lfn:message('sys-attend:sysAttendCategory.fdOrder') }">
		</list:data-column>
		<list:data-column property="fdStartTime" title="${ lfn:message('sys-attend:sysAttendCategory.fdStartTime') }">
		</list:data-column>
		<list:data-column property="fdEndTime" title="${ lfn:message('sys-attend:sysAttendCategory.fdEndTime') }">
		</list:data-column>
		<list:data-column col="fdType" title="${ lfn:message('sys-attend:sysAttendCategory.fdType') }">
			<sunbor:enumsShow value="${sysAttendCategory.fdType}" enumsType="sysAttend_sysAttendCategory_fdType" />
		</list:data-column>
		<list:data-column property="fdPeriodType" title="${ lfn:message('sys-attend:sysAttendCategory.fdPeriodType') }">
		</list:data-column>
		<list:data-column property="fdWeek" title="${ lfn:message('sys-attend:sysAttendCategory.fdWeek') }">
		</list:data-column>
		<list:data-column property="fdAppName" title="${ lfn:message('sys-attend:sysAttendCategory.fdAppName') }">
		</list:data-column>
		<list:data-column col="fdStatus" title="${ lfn:message('sys-attend:sysAttendCategory.fdStatus') }">
			<sunbor:enumsShow value="${empty __fdStatus ? sysAttendCategory.fdStatus : __fdStatus}" enumsType="sysAttendCategory_fdStatus" />
		</list:data-column>
		<list:data-column col="_fdStatus" title="">
			${empty __fdStatus ? sysAttendCategory.fdStatus : __fdStatus}
		</list:data-column>
		<list:data-column property="fdAppUrl" title="${ lfn:message('sys-attend:sysAttendCategory.fdAppUrl') }">
		</list:data-column>
		<list:data-column property="docAlterTime" title="${ lfn:message('sys-attend:sysAttendCategory.docAlterTime') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendCategory.docCreateTime') }">
		</list:data-column>
		<list:data-column property="fdWork" title="${ lfn:message('sys-attend:sysAttendCategory.fdWork') }">
		</list:data-column>
		<list:data-column col="docAlteror.fdName" title="${ lfn:message('sys-attend:sysAttendCategory.docAlteror') }">
			<c:out value="${sysAttendCategory.docAlteror.fdName}" />
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-attend:sysAttendCategory.docCreator') }">
			<c:out value="${sysAttendCategory.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="fdManager.fdName" title="${ lfn:message('sys-attend:sysAttendCategory.fdManager') }">
			<c:out value="${sysAttendCategory.fdManager.fdName}" />
		</list:data-column>
		<list:data-column col="fdATemplate.fdName" title="${ lfn:message('sys-attend:sysAttendCategory.fdATemplate') }">
			<c:out value="${sysAttendCategory.fdATemplate.fdName}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>