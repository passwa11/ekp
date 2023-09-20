<%@ page language="java" contentType="text/json; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingTopic"%>
<%@page import="com.landray.kmss.sys.unit.model.KmImissiveUnit"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement,java.util.*,com.landray.kmss.util.*"%>
<list:data>
	<list:data-columns var="kmImeetingTopic" list="${queryPage.list }">
		<%--ID--%> 
		<list:data-column property="fdId"></list:data-column>
		<%--名称--%>
		<list:data-column  headerClass="width200" col="docSubject" title="${ lfn:message('km-imeeting:kmImeetingTopic.docSubject')}" style="text-align:left" escape="false">
			<span class="com_subject"><c:out value="${kmImeetingTopic.docSubject}" /></span>
			<c:if test="${kmImeetingTopic.fdIsAccept == true}">
				(<bean:message key="kmImeetingTopic.fdIsAccept.mobile.true" bundle="km-imeeting"/>)
			</c:if>
			<c:if test="${kmImeetingTopic.fdIsAccept == null or kmImeetingTopic.fdIsAccept == false }">
				(<bean:message key="kmImeetingTopic.fdIsAccept.mobile.false" bundle="km-imeeting"/>)
			</c:if>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>