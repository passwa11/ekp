<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysOrgPerson" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdParent.fdName" title="${ lfn:message('sys-organization:sysOrgPerson.fdParent') }" style="text-align:center;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width80" property="fdName" title="${ lfn:message('sys-organization:sysOrgPerson.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width180" property="fdLoginName" title="${ lfn:message('sys-organization:sysOrgPerson.fdLoginName') }">
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>