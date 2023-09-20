<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingTopic"%>
<list:data>
	<list:data-columns var="kmImeetingTopic" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		
		<%
			KmImeetingTopic kmImeetingTopic = (KmImeetingTopic)pageContext.getAttribute("kmImeetingTopic");
     		request.setAttribute("modelName",  ModelUtil.getModelClassName(kmImeetingTopic));
     	%>
     	
     	<%-- 主题 --%>	
		<list:data-column col="label" title="${ lfn:message('km-imeeting:kmImeetingTopic.docSubject') }" escape="false">
			<c:out value="${kmImeetingTopic.docSubject}"/>
		</list:data-column>
		
		<%-- 创建时间 --%>	
		<list:data-column col="created" escape="false">
			<kmss:showDate value="${kmImeetingTopic.docCreateTime}" type="datetime"></kmss:showDate>
		</list:data-column>
		
		<list:data-column col="modelName" escape="false">
			<c:out value="${modelName}"/>
		</list:data-column>
		
		<%--链接--%>
		<list:data-column col="href" escape="false">
			/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=view&fdId=${kmImeetingTopic.fdId}
		</list:data-column>
		
		<%--汇报人--%>
		<list:data-column col="fdReporter" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdReporter') }" escape="false">
			<c:out value="${kmImeetingTopic.fdReporter.fdName}"/>
		</list:data-column>
		
		<%-- 文档状态 --%>
        <list:data-column col="docStatus" title="${lfn:message('km-imeeting:kmImeetingTopic.docStatus')}">
			<c:choose>
				<c:when test="${kmImeetingTopic.docStatus=='10' }">
					<bean:message key="status.draft"></bean:message>
				</c:when>
				<c:when test="${kmImeetingTopic.docStatus=='20' }">
					<bean:message key="kmImeeting.status.append" bundle="km-imeeting"/>
				</c:when>
				<c:when test="${kmImeetingTopic.docStatus=='11' }">
					<bean:message key="status.refuse"></bean:message>
				</c:when>
				<c:when test="${kmImeetingTopic.docStatus=='30' || kmImeetingTopic.docStatus=='40' }">
					<bean:message key="status.publish"></bean:message>
				</c:when>
				<c:when test="${kmImeetingTopic.docStatus=='00' }">
					<bean:message key="status.discard"></bean:message>
				</c:when>
			</c:choose>
		</list:data-column>
		<list:data-column col="docStatusNew" title="${lfn:message('km-imeeting:kmImeetingTopic.docStatus')}">
			<c:out value="${kmImeetingTopic.docStatus}"/>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>