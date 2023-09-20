<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}">
		<list:data-column property="id" title="id" col="fdId">
		</list:data-column>
		<list:data-column col="name" title="${lfn:message('sys-mportal:sysMportalPortal.fdName')}">
				${item.name} 
		</list:data-column>
		<list:data-column col="url" title="${lfn:message('sys-mportal:sysMportalMenuItem.fdUrl')}">
			${item.url} 
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>