<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="sysAttendSignStat" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }">
		</list:data-column>
		
		<list:data-column col="dept" title="">
			<c:out value="${sysAttendSignStat.docCreator.fdParent.fdName}" />
		</list:data-column>
		<list:data-column col="docCreatorName" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }">
			<c:out value="${sysAttendSignStat.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="docCreatorImg" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }" escape="false">
			<person:headimageUrl contextPath="true" personId="${sysAttendSignStat.docCreator.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="docCreatorId" title="" escape="false">
			<c:out value="${sysAttendSignStat.docCreator.fdId}" />
		</list:data-column>
		<list:data-column col="fdSignCount" title="">
			<c:out value="${sysAttendSignStat.fdSignCount}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>