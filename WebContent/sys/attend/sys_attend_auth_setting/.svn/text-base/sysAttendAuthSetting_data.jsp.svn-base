<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttendAuthSetting" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index" >
		 	${status+1}
		</list:data-column>
		<list:data-column col="fdElementNames" escape="false" title="${ lfn:message('sys-attend:sysAttendAuthSetting.fdElements') }" headerStyle="min-width: 200px">
			<c:forEach items="${sysAttendAuthSetting.fdElements }" var="fdELement" varStatus="vstatus">
				${vstatus.index eq 0 ? '' : ';'}${fdELement.fdName}
			</c:forEach>
		</list:data-column>
		<list:data-column col="fdAuthNames" escape="false" title="${ lfn:message('sys-attend:sysAttendAuthSetting.fdAuthList') }" headerStyle="min-width: 200px">
			<c:forEach items="${sysAttendAuthSetting.fdAuthList }" var="fdAuth" varStatus="vstatus">
				${vstatus.index eq 0 ? '' : ';'}${fdAuth.fdName}
			</c:forEach>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>