<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysTagTags" list="${queryPage.list}" varIndex="vstatus">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column  col="fdName" title="${ lfn:message('sys-tag:sysTagTags.fdName') }" escape="false">
				<c:out value="${sysTagTags.fdName}" />
		</list:data-column>
		<list:data-column  col="fdAlias" title="${ lfn:message('sys-tag:sysTagTags.fdAlias') }">
				<kmss:joinListProperty value="${sysTagTags.hbmAlias}" properties="fdName" split=";" />
		</list:data-column>
		<list:data-column  col="fdCategorys" title="${ lfn:message('sys-tag:sysTagTags.fdCategoryId') }">
				<kmss:joinListProperty value="${sysTagTags.fdCategorys}" properties="fdName" split=";" />
		</list:data-column>
		<list:data-column  col="fdStatus" title="${ lfn:message('sys-tag:sysTagTags.fdStatus') }">
				<sunbor:enumsShow value="${sysTagTags.fdStatus}" enumsType="sysTagTags_fdStatus" bundle="sys-tag"/>
		</list:data-column>
		<list:data-column  col="docCreator" title="${ lfn:message('sys-tag:sysTagTags.docCreatorId') }">
				<c:out value="${sysTagTags.docCreator.fdName}" />
		</list:data-column>
		<list:data-column  col="docCreateTime" title="${ lfn:message('sys-tag:sysTagTags.docCreateTime') }">
			<kmss:showDate value="${sysTagTags.docCreateTime}" type="datetime"/>
		</list:data-column>
		<list:data-column  col="fdCountQuoteTimes" title="${ lfn:message('sys-tag:sysTagTags.fdQuoteTimes') }">
			<c:out value="${sysTagTags.fdCountQuoteTimes}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
