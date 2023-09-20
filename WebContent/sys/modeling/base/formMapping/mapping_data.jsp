<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="modelingFormMapping" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdComClass" title="${lfn:message('sys-modeling-base:modeling.model.fdName')}" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${modelingFormMapping.fdComClass}" /></span>
		</list:data-column>
		<list:data-column col="fdComName" title="${lfn:message('sys-modeling-base:modeling.model.fdName')}" style="text-align:left;min-width:180px" escape="false">
			<c:out value="${modelingFormMapping.fdComName}" />
		</list:data-column>

		<list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modeling.modelMain.docCreateTime')}" style="text-align:left;min-width:180px" escape="false">
			<kmss:showDate value="${modelingFormMapping.docCreateTime}" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${lfn:message('sys-modeling-base:mmodelingAppListview.docCreator')}" style="text-align:left;min-width:180px" escape="false">
			<c:out value="${modelingFormMapping.docCreator.fdName}" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>