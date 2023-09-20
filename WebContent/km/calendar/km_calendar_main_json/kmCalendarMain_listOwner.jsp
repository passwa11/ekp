<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %> 
<json:object>
	<json:property name="errcode" value="0"></json:property>
	<json:property name="errmsg" value="ok"></json:property>
	<json:array name="data" var="auth" items="${kmCalendarAuths}" >
		<json:object>
			<c:if test="${not empty auth.docCreator && auth.docCreator.fdIsAvailable != false }">
				<json:property name="fdId" value="${auth.docCreator.fdLoginName }"></json:property>	
				<json:property name="fdName" value="${ auth.docCreator.fdName}"></json:property>
			</c:if>
		</json:object>
	</json:array>
</json:object>