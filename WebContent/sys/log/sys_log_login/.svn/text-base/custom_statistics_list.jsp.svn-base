<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="map" list="${queryPage.list }">
		<list:data-column col="fdId">
			${map.fdId}
		</list:data-column>
		<list:data-column col="loginName" title="${ lfn:message('sys-organization:sysOrgPerson.fdLoginName') }">
			${map.loginName}
		</list:data-column>
		<list:data-column col="name" title="${ lfn:message('sys-log:sysLogOnline.fdPerson') }">
			${map.name}
		</list:data-column>
		<list:data-column col="deptName" title="${ lfn:message('sys-log:sysLogOnline.fdDept') }">
			${map.deptName}
		</list:data-column>
		<list:data-column col="loginNum" title="${ lfn:message('sys-log:sysLogOnline.fdLoginNum') }">
			${map.loginNum}
		</list:data-column>
		<list:data-column col="loginTime" title="${ lfn:message('sys-log:sysLogOnline.lastLoginTime') }">
			${map.loginTime}
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>