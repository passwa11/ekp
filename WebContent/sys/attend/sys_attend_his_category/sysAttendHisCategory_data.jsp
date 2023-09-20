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
		<list:data-column property="fdName" title="${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }">
		</list:data-column>
		<list:data-column property="fdBeginTime" title="${ lfn:message('sys-attend:sysAttendCategoryTargetNew.fdBeginTime') }">
		</list:data-column>
		<list:data-column property="fdEndTime" title="${ lfn:message('sys-attend:sysAttendCategoryTargetNew.fdEndTime') }">
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>