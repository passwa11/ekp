<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>

<%
	boolean isAdmin = UserUtil.getKMSSUser().isAdmin()||UserUtil.checkRole("ROLE_SYSPRAISEINFO_MAINTAINER");
	request.setAttribute("isAdmin", isAdmin);
	request.setAttribute("fdUserId", UserUtil.getUser().getFdId());
%>

<list:data>
<list:data-columns var="sysPraiseInfo" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="docSubject" title="${ lfn:message('sys-praise:sysPraiseInfo.docSubject') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-praise:sysPraiseInfo.docCreateTime') }">
		</list:data-column>
		<list:data-column property="fdReason" title="${ lfn:message('sys-praise:sysPraiseInfo.fdReason') }">
		</list:data-column>
		<list:data-column col="fdRich" title="${ lfn:message('sys-praise:sysPraiseInfo.fdRich') }">
			<c:choose>
				<c:when test="${empty sysPraiseInfo.fdRich}">
					<c:out value="0"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfo.fdRich }"/> 
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdType" title="${ lfn:message('sys-praise:sysPraiseInfo.fdType') }">
			 <c:choose>
			 	<c:when test="${sysPraiseInfo.fdType == 1}">
			 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.praise') }"/>
			 	</c:when>
			 	<c:when test="${sysPraiseInfo.fdType == 2}">
			 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.rich') }"/>
			 	</c:when>
			 	<c:when test="${sysPraiseInfo.fdType == 3}">
			 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.unPraise') }"/>
			 	</c:when>
			 </c:choose>
		</list:data-column>
		<list:data-column col="fdPraisePersonName" title="${ lfn:message('sys-praise:sysPraiseInfo.fdPraisePerson') }">
			<c:choose>
				<c:when test="${ !isAdmin &&  (not empty sysPraiseInfo.fdIsHideName && sysPraiseInfo.fdIsHideName eq 'true') && fdUserId ne sysPraiseInfo.fdPraisePerson.fdId}">
					<c:out value="${lfn:message('sys-praise:sysPraiseInfo.name.hide') }"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfo.fdPraisePerson.fdName }"/>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdTargetPersonName" title="${ lfn:message('sys-praise:sysPraiseInfo.receivePerson') }">
			<c:out value="${sysPraiseInfo.fdTargetPerson.fdName }"/>
		</list:data-column>
		<list:data-column col="docCategoryName" title="${ lfn:message('sys-praise:sysPraiseInfoCategory.fdName') }">
			<c:out value="${sysPraiseInfo.docCategory.fdName }"/>
		</list:data-column>
		<list:data-column col="fdIsHideName" title="${ lfn:message('sys-praise:sysPraiseInfo.fdIsHideName') }">
			<sunbor:enumsShow
				value="${sysPraiseInfo.fdIsHideName}"
				enumsType="common_yesno" />
		</list:data-column>
</list:data-columns>
<list:data-paging page="${queryPage}" />
</list:data>