<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmTemplate" list="${queryPage.list}">
		<list:data-column property="fdId" title="ID">
		</list:data-column>
		<list:data-column property="fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmTemplate.fdName') }">
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${ queryPage }"></list:data-paging>
</list:data>