<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAnonymLog" list="${queryPage.list}"
		varIndex="status" custom="false">
		<list:data-column property="fdId" />
		<list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="docOptTime" title="${lfn:message('sys-anonym:sysAnonymLog.docOptTime')}">
			<kmss:showDate value="${sysAnonymLog.docOptTime}" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column  col="docOptor.fdName" title="${ lfn:message('sys-anonym:sysAnonymLog.docOptor') }" escape="false">
			<ui:person personId="${sysAnonymLog.docOptor.fdId}"
				personName="${sysAnonymLog.docOptor.fdName}"></ui:person>
		</list:data-column>
		<list:data-column property="fdOptNode" title="${lfn:message('sys-anonym:sysAnonymLog.fdOptNode')}" />
		<list:data-column property="fdOptInfo" title="${lfn:message('sys-anonym:sysAnonymLog.fdOptInfo')}" />
		<list:data-column property="nowCategory.fdName" title="${lfn:message('sys-anonym:sysAnonymLog.nowCategory')}" />
	</list:data-columns>
	<%-- 分页信息生成 --%>
	<list:data-paging page="${queryPage}" />
</list:data>
