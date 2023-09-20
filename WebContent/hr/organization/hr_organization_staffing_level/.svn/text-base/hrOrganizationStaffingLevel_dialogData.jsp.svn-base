<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrganizationStaffingLevel" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 职务 -->
		<list:data-column headerClass="width300"  property="fdName" title="${ lfn:message('sys-organization:sysOrganizationStaffingLevel.fdName') }">
		</list:data-column>
		<!-- 职务编码 -->
		<list:data-column headerClass="width100" property="fdLevel" title="${ lfn:message('hr-organization:hrOrganizationDuty.fdCode') }">
		</list:data-column>
		<list:data-column col="docCreator.name" escape="false" title="${lfn:message('hr-organization:hrOrganizationDuty.docCreator')}">
            <c:out value="${sysOrganizationStaffingLevel.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-organization:hrOrganizationDuty.docCreateTime')}">
            <kmss:showDate value="${sysOrganizationStaffingLevel.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>