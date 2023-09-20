<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsMedalLog" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column style="width: 130px;" property="docHonoursTime" title="${ lfn:message('kms-medal:kmsMedalLog.docHonoursTime') }">
		</list:data-column>
		<list:data-column col="fdHonours" title="${ lfn:message('kms-medal:kmsMedalLog.fdHonours') }" >
			<kmss:joinListProperty value="${kmsMedalLog.fdSubHonours}" properties="fdName" split=";"/>
			<c:if test="${fn:length(kmsMedalLog.fdHonours) > 30 }">...</c:if> 
		</list:data-column>	
		<list:data-column style="width: 100px;" col="docOperator.fdName" title="${ lfn:message('kms-medal:kmsMedalLog.docOperator') }">
			<c:out value="${kmsMedalLog.docOperator.fdName}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
