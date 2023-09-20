<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysTimeVacation" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>

		<list:data-column property="fdName" title="${ lfn:message('sys-time:sysTimeVacation.fdName') }">
		</list:data-column>

		<list:data-column col="hbmStartTime" title="${ lfn:message('sys-time:sysTimeVacation.time') }"  escape="false">
				<kmss:showDate value="${sysTimeVacation.fdStartDate}" type='date'/>
				<kmss:showDate value="${sysTimeVacation.fdStartTime}" type='time'/>
				<bean:message  bundle="sys-time" key="sysTimeVacation.end"/>
				<kmss:showDate value="${sysTimeVacation.fdEndDate}" type='date'/>
				<kmss:showDate value="${sysTimeVacation.fdEndTime}" type='time'/>				
		</list:data-column>

		<list:data-column property="docCreator.fdName" title="${ lfn:message('sys-time:sysTimeVacation.docCreatorId') }">
		</list:data-column>

		<list:data-column col="docCreateTime" title="${ lfn:message('sys-time:sysTimeVacation.docCreateTime') }" escape="false">
			<kmss:showDate value="${sysTimeVacation.docCreateTime}" type="datetime"/>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>