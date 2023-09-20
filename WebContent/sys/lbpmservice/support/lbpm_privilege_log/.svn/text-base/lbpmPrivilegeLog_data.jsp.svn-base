<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmPrivilegeLog" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="fdSubject" title="${ lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdSubject') }">
			<%--<c:out value="${lbpmPrivilegeLog.fdSubject}" />--%>
		</list:data-column> 
		<list:data-column col="fdActionInfo" title="${ lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdActionInfo') }">
			${lbpmPrivilegeLog.fdActionInfo}
	 	</list:data-column>
		<list:data-column col="fdHandlerName" title="${ lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdHandler') }">
			<c:out value="${lbpmPrivilegeLog.fdHandler.fdName }" />
		</list:data-column>
		
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdCreateTime') }">
		</list:data-column>
		<list:data-column col="fdIpAddr" title="${ lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdIpAddr') }">
			<c:out value="${lbpmPrivilegeLog.fdIpAddr}" />
		</list:data-column>
		<c:if test="${lbpmPrivilegeLog.authArea.fdName ne null}">
			<list:data-column col="authAreaName" title="${ lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.authAreaName') }">
				<c:out value="${lbpmPrivilegeLog.authArea.fdName}" />
			</list:data-column>
		</c:if>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>














































