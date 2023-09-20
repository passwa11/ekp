<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdModelId">
		</list:data-column>
		<list:data-column property="fdModelName">
		</list:data-column>
		<list:data-column col="docCreateTime" title="${ lfn:message('sys-tag:sysTagMain.docCreateTime')}">
			<kmss:showDate isInterval="true" value="${item.docCreateTime}" /> 
		</list:data-column>
		<list:data-column col="docAlterTime" title="${ lfn:message('sys-tag:sysTagMain.docAlterTime')}" escape="false">
			<kmss:showDate value="${item.docAlterTime}" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdUrl" title="${ lfn:message('sys-tag:sysTagMain.fdUrl')}" escape="false">
			${item.fdUrl}
		</list:data-column>
		<list:data-column property="docSubject" title="${ lfn:message('sys-tag:sysTagMain.docTitle')}">
			${item.docSubject}
		</list:data-column>
		<list:data-column col="fdAppName" title="${ lfn:message('sys-tag:sysTagMain.fdAppName')}" escape="false">
			${item.fdAppName}
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-tag:sysTagMain.docCreatorName')}" escape="false">
			${item.docCreator.fdName}
		</list:data-column>
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>