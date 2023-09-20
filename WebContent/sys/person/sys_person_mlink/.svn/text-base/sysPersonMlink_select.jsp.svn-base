<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.IDGenerator" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}">
		<list:data-column title="ID" col="fdId" escape="false">
			<%=IDGenerator.generateID()%>
		</list:data-column>
		<list:data-column col="name" title="${lfn:message('sys-person:sysPersonMyNavLink.fdName')}">
				${item.name} 
		</list:data-column>
		<list:data-column col="url" title="${lfn:message('sys-person:sysPersonMyNavLink.fdUrl')}">
			${item.url} 
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>