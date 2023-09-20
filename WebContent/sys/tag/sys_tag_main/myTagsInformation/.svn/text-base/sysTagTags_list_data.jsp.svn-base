<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdIsSpecial" title="${lfn:message('sys-tag:sysTag.tree.normalorspecial.tag') }" escape="false">
			<c:choose>  
			<c:when test="${item.fdIsSpecial eq 1}">${lfn:message('sys-tag:sysTag.tree.special.tag') }</c:when>
			<c:otherwise>${lfn:message('sys-tag:sysTag.tree.normal.tag') }</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdName" title="${lfn:message('sys-tag:sysTagTags.fdName') }" escape="false">
			${item.fdName}
		</list:data-column>
		<list:data-column col="fdStatus" title="${lfn:message('sys-tag:sysTagTags.fdStatus.trueorfalse') }" escape="false">
			<c:choose>  
				<c:when test="${item.fdStatus eq 1}">${lfn:message('sys-tag:sysTag.tree.status.true') }
				</c:when>
				<c:otherwise>${lfn:message('sys-tag:sysTag.tree.status.false') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdIsPrivate" title="${lfn:message('sys-tag:sysTag.tree.publicorprivate') }" escape="false">
			<c:choose>  
				<c:when test="${item.fdIsPrivate eq 1}">${lfn:message('sys-tag:sysTag.tree.public') }
				</c:when>
				<c:otherwise>${lfn:message('sys-tag:sysTag.tree.private') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column property="fdQuoteTimes" title="${lfn:message('sys-tag:sysTagTags.used.for.fdCountQuoteTimes') }">
			${item.fdQuoteTimes}
		</list:data-column>
		<list:data-column col="docCreateTime" title="${ lfn:message('sys-tag:sysTagMain.docCreateTime')}">
			<kmss:showDate isInterval="true" value="${item.docCreateTime}" /> 
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-tag:sysTagMain.docCreatorId')}" escape="false">
			${item.docCreator.fdName}
		</list:data-column>
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>