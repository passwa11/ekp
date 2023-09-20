<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttendStat" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="fdDate" title="${ lfn:message('sys-attend:sysAttendStat.fdDate') }">
		</list:data-column>
		<list:data-column property="fdCategoryId" title="${ lfn:message('sys-attend:sysAttendStat.fdCategoryId') }">
		</list:data-column>
		<list:data-column property="fdTotalTime" title="${ lfn:message('sys-attend:sysAttendStat.fdTotalTime') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendStat.docCreateTime') }">
		</list:data-column>
		<list:data-column property="fdLateTime" title="${ lfn:message('sys-attend:sysAttendStat.fdLateTime') }">
		</list:data-column>
		<list:data-column property="fdLeftTime" title="${ lfn:message('sys-attend:sysAttendStat.fdLeftTime') }">
		</list:data-column>
		<list:data-column property="fdStatus" title="${ lfn:message('sys-attend:sysAttendStat.fdStatus') }">
		</list:data-column>
		<list:data-column col="fdOutside" title="${ lfn:message('sys-attend:sysAttendStat.fdOutside') }">
			<sunbor:enumsShow
				value="${sysAttendStat.fdOutside}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column property="fdCategoryName" title="${ lfn:message('sys-attend:sysAttendStat.fdCategoryName') }">
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-attend:sysAttendStat.docCreator') }">
			<c:out value="${sysAttendStat.docCreator.fdName}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>