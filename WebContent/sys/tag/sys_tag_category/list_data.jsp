<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysTagCategory" list="${queryPage.list}" varIndex="vstatus">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column  col="fdName" title="${ lfn:message('sys-tag:sysTagCategory.fdName') }">
				<c:out value="${sysTagCategory.fdName}" />
		</list:data-column>
		<list:data-column escape="false" col="authEditors" title="${ lfn:message('sys-tag:sysTagCategory.fdManagerId') }">
					<c:forEach items="${sysTagCategory.authEditors}" var="u">
						<d>${u.fdName}</d>
					</c:forEach> 
		</list:data-column>
		<list:data-column  col="fdTagQuoteTimes" title="${ lfn:message('sys-tag:sysTagCategory.fdTagQuoteTimes') }">
				<c:out value="${sysTagCategory.fdTagQuoteTimes}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
