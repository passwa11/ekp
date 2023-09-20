<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsMedalMain" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 勋章名称 --%>
		<list:data-column property="fdName" title="${ lfn:message('kms-medal:kmsMedalMain.fdName') }">
		</list:data-column>
		<%-- 所属分类 --%>
		<list:data-column col="fdCategory.fdName" title="${ lfn:message('kms-medal:kmsMedalMain.fdCategory') }">
			<c:out value="${kmsMedalMain.fdCategory.fdName}" />
		</list:data-column>
		<%-- 授勋人数 --%>
		<list:data-column property="fdOwnerCount" title="${ lfn:message('kms-medal:kmsMedalMain.fdOwnerCount') }">
		</list:data-column>	
		<%-- 勋章有效期 --%>
		<list:data-column col="fdValidTime" title="${ lfn:message('kms-medal:kmsMedalMain.fdValidTime') }">
			<c:if test="${empty kmsMedalMain.fdValidTime}">
				<bean:message bundle="kms-medal" key="kmsMedalMain.fdValidTime.forever"/>
			</c:if>
			<c:if test="${not empty kmsMedalMain.fdValidTime}">
				<kmss:showDate value="${kmsMedalMain.fdValidTime}" type="date"></kmss:showDate>
			</c:if>
		</list:data-column>
		<%-- 创建者 --%>
		<list:data-column property="docCreator.fdName" title="${ lfn:message('kms-medal:kmsMedalMain.docCreator') }">
			<c:out value="${kmsMedalMain.docCreator.fdName}" />
		</list:data-column>
		<%-- 创建时间 --%>
		<list:data-column col="docCreateTime" title="${ lfn:message('kms-medal:kmsMedalMain.docCreateTime') }">
			<kmss:showDate value="${kmsMedalMain.docCreateTime }" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column property="docAlterTime" title="${ lfn:message('kms-medal:kmsMedalMain.docAlterTime') }">
		</list:data-column>
		<list:data-column property="fdIntroduction" title="${ lfn:message('kms-medal:kmsMedalMain.fdIntroduction') }">
		</list:data-column>
		<list:data-column col="docAlteror.fdName" title="${ lfn:message('kms-medal:kmsMedalMain.docAlteror') }">
			<c:out value="${kmsMedalMain.docAlteror.fdName}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
