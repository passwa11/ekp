<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.tag.model.SysTagCategory"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="sysTagCategory" list="${queryPage.list}"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdName"
			title="${ lfn:message('sys-tag:sysTagGroupDetail.fdCates') }">
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>