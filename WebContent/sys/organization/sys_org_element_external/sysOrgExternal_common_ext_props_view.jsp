<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="orgForm" value="${requestScope[param.formName]}"/>
<c:forEach items="${props}" var="prop" varStatus="status">
	<c:choose>
		<c:when test="${status.index % 2 == 0}">
		<tr>
		</c:when>
		<c:when test="${status.index != 0 && status.index % 2 == 0}">
		</tr>
		</c:when>
	</c:choose>
		<td width=15% class="td_normal_title">
			<c:out value="${prop.fdName}"/>
		</td>
		<c:choose>
			<c:when test="${status.index % 2 == 0 && status.index == fn:length(props) - 1}">
			<td width=85% colspan="3">
			</c:when>
			<c:otherwise>
			<td width=35%>
			</c:otherwise>
		</c:choose>
			${orgForm.dynamicMap[prop.fdFieldName]}
		</td>
	<c:if test="${status.index == fn:length(props) - 1}">
	</tr>
	</c:if>
</c:forEach>
