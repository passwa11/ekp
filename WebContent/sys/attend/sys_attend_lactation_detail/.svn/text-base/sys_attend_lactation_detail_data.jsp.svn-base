<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendLactationDetail"%>
<list:data>
	<list:data-columns var="sysAttendLactationDetail" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column col="fdStartTime"  title="${ lfn:message('sys-attend:sysAttendLactationDetail.fdStartTime')}">
			<fmt:formatDate value="${sysAttendLactationDetail.fdStartTime}" pattern="HH:mm" />
		</list:data-column>
		<list:data-column col="fdDate"  title="${ lfn:message('sys-attend:sysAttendLactationDetail.fdDate') }">
			<fmt:formatDate value="${sysAttendLactationDetail.fdDate}" pattern="yyyy-MM-dd"/>
		</list:data-column>

		<list:data-column col="fdEndTime"  title="${ lfn:message('sys-attend:sysAttendLactationDetail.fdEndTime')}">
			<fmt:formatDate value="${sysAttendLactationDetail.fdEndTime}" pattern="HH:mm" />
		</list:data-column>
		<list:data-column col="docCreator" title="${ lfn:message('sys-attend:sysAttendLactationDetail.docCreator') }">
			<c:out value="${sysAttendLactationDetail.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="fdCountHour" title="${ lfn:message('sys-attend:sysAttendLactationDetail.fdCountHour') }">
			<c:out value="${sysAttendLactationDetail.fdCountHour}" />
		</list:data-column>
		<%-- <list:data-column col="fdType" title="${ lfn:message('sys-attend:sysAttendLactationDetail.fdType') }">
			<sunbor:enumsShow value="${sysAttendLactationDetail.fdType}" enumsType="sysAttendLactationDetail_fdType" />
		</list:data-column> --%>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>