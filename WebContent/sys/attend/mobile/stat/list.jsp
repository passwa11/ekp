<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="sysAttendStat" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }">
		</list:data-column>
		<list:data-column col="fdTotalTime" title="">
			<c:out value="${sysAttendStat.fdTotalTime}" />
		</list:data-column>
		<list:data-column col="dept" title="">
			<c:out value="${sysAttendStat.docCreator.fdParent.fdName}" />
		</list:data-column>
		<list:data-column col="docCreatorId" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }">
			<c:out value="${sysAttendStat.docCreator.fdId}" />
		</list:data-column>
		<list:data-column col="docCreatorName" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }">
			<c:out value="${sysAttendStat.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="docCreatorImg" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }" escape="false">
			<person:headimageUrl contextPath="true" personId="${sysAttendStat.docCreator.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="fdLateTime" title="" escape="false">
			<c:out value="${sysAttendStat.fdLateTime}" />
		</list:data-column>
		<list:data-column col="fdLeftTime" title="" escape="false">
			<c:out value="${sysAttendStat.fdLeftTime}" />
		</list:data-column>
		<list:data-column col="fdOverTime" title="" escape="false">
			<c:out value="${sysAttendStat.fdOverTime}" />
		</list:data-column>
		<list:data-column col="fdOutgoingTime" title="" escape="false">
			<c:out value="${sysAttendStat.fdOutgoingTime}" />
		</list:data-column>
		<list:data-column col="fdType" title="" escape="false">
			${fdType}
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>