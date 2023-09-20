<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
	<json:property name="errcode" value="0"></json:property>
	<json:property name="errmsg" value="ok"></json:property>
	<json:object name="data">
		<json:property name="docCreatorId" value="${kmCalendarAuth.docCreator.fdLoginName }"></json:property>
		<json:property name="docCreatorName" value="${kmCalendarAuth.docCreator.fdName }"></json:property>
		<json:array name="authEditor" var="editor" items="${kmCalendarAuth.authEditors }">
			<json:object>
				<c:if test="${ editor.fdOrgType == 8}">
					<json:property name="fdId" value="${editor.fdLoginName }"></json:property>	
				</c:if>	
				<c:if test="${ editor.fdOrgType != 8}">
					<json:property name="fdId" value="${editor.fdId }"></json:property>	
				</c:if>
				<json:property name="fdName" value="${editor.fdName }"></json:property>	
				<json:property name="fdOrgType" value="${editor.fdOrgType }"></json:property>				
			</json:object>
		</json:array>
		<json:array name="authReader" var="reader" items="${kmCalendarAuth.authReaders }">
			<json:object>
				<c:if test="${ reader.fdOrgType == 8}">
					<json:property name="fdId" value="${reader.fdLoginName }"></json:property>	
				</c:if>		
				<c:if test="${ reader.fdOrgType != 8}">
					<json:property name="fdId" value="${reader.fdId }"></json:property>	
				</c:if>		
				<json:property name="fdName" value="${reader.fdName }"></json:property>	
				<json:property name="fdOrgType" value="${reader.fdOrgType }"></json:property>				
			</json:object>
		</json:array>
		<json:array name="authModifier" var="modifiers" items="${kmCalendarAuth.authModifiers }">
			<json:object>
				<c:if test="${ modifiers.fdOrgType == 8}">
					<json:property name="fdId" value="${modifiers.fdLoginName }"></json:property>	
				</c:if>
				<c:if test="${ modifiers.fdOrgType != 8}">
					<json:property name="fdId" value="${modifiers.fdId }"></json:property>	
				</c:if>
				<json:property name="fdName" value="${modifiers.fdName }"></json:property>		
				<json:property name="fdOrgType" value="${modifiers.fdOrgType }"></json:property>		
			</json:object>
		</json:array>
	</json:object>
</json:object>