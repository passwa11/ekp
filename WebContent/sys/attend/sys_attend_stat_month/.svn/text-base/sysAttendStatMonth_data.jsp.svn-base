<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttendStatMonth" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="fdMonth" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdMonth') }">
		</list:data-column>
		<list:data-column property="fdTotalTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdTotalTime') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.docCreateTime') }">
		</list:data-column>
		<list:data-column col="docCreatorId" title="${ lfn:message('sys-attend:sysAttendStatMonth.docCreatorId') }">
			${sysAttendStatMonth.docCreator.fdId}
		</list:data-column>
		<list:data-column property="fdLateTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLateTime') }">
		</list:data-column>
		<list:data-column property="fdLeftTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftTime') }">
		</list:data-column>
		<list:data-column col="fdStatus" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdStatus') }">
			<sunbor:enumsShow
				value="${sysAttendStatMonth.fdStatus}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="fdLate" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLate') }">
			<sunbor:enumsShow
				value="${sysAttendStatMonth.fdLate}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="fdLeft" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeft') }">
			<sunbor:enumsShow
				value="${sysAttendStatMonth.fdLeft}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="fdOutside" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOutside') }">
			<sunbor:enumsShow
				value="${sysAttendStatMonth.fdOutside}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="fdMissed" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdMissed') }">
			<sunbor:enumsShow
				value="${sysAttendStatMonth.fdMissed}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="fdAbsent" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdAbsent') }">
			<sunbor:enumsShow
				value="${sysAttendStatMonth.fdAbsent}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="fdTrip" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdTrip') }">
			<sunbor:enumsShow
				value="${sysAttendStatMonth.fdTrip}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-attend:sysAttendStatMonth.docCreator') }">
			<c:out value="${sysAttendStatMonth.docCreator.fdName}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>