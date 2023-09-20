<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.km.imeeting.model.KmImeetingRes"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${list}" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--会议室名字--%>
		<list:data-column  col="fdName" headerClass="width140" styleClass="width140"  title="${ lfn:message('km-imeeting:kmImeetingRes.fdName') }" escape="false">
		 	<c:out value="${item.fdName}"></c:out>
		</list:data-column>
		<list:data-column col="fdType">
			<%
				if(pageContext.getAttribute("item")!=null){
					Object _item = pageContext.getAttribute("item");
					if(_item instanceof KmImeetingRes){
						request.setAttribute("fdType", "res");
					}else{
						request.setAttribute("fdType", "categroy");
					}
				}
			%>
			<c:out value="${fdType }"></c:out>
		</list:data-column>
		<list:data-column col="fdUserTime">
			<c:if test="${fdType == 'res' }">
				<c:out value="${item.fdUserTime }"></c:out>
			</c:if>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="1" pageSize="0" totalSize="${fn:length(list)}" />
</list:data>