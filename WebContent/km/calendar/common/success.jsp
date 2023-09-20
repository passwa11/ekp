<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
	<json:property name="errcode" value="0"></json:property>
	<json:property name="errmsg" value="ok"></json:property>
	<c:if test="${not empty data }">
		<json:object name="data">
			<c:forEach items="${data}" var="dataItem">
				<json:property name="${dataItem.key }" value="${dataItem.value}" escapeXml="false"></json:property>
			</c:forEach>
		</json:object>
	</c:if>
</json:object>