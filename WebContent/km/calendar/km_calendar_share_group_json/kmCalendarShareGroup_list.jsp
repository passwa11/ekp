<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %> 
<json:object prettyPrint="true">
	<json:property name="errcode" value="0"></json:property>
	<json:property name="errmsg" value="ok"></json:property>
	<json:array name="data" items="${queryPage.list}" var="kmCalendarShareGroup">
		<json:object>
			<json:property name="fdId" value="${kmCalendarShareGroup.fdId }"></json:property>
			<json:property name="name" value="${kmCalendarShareGroup.fdName }"></json:property>	
			<json:property name="fdDescription" value="${kmCalendarShareGroup.fdDescription }"></json:property>	
			<json:property name="docCreatorId" value="${kmCalendarShareGroup.docCreator.fdLoginName }"></json:property>	
			<json:property name="docCreatorName" value="${kmCalendarShareGroup.docCreator.fdName }"></json:property>
			<json:property name="docCreateTime" value="${kmCalendarShareGroup.docCreateTime.time }"></json:property>
			<json:array name="shareGroupMembers" items="${kmCalendarShareGroup.shareGroupMembers }" var="shareGroupMember">
				<json:object>
					<c:if test="${ shareGroupMember.fdOrgType == 8}">
						<json:property name="fdId" value="${shareGroupMember.fdLoginName }"></json:property>
					</c:if>
					<c:if test="${ shareGroupMember.fdOrgType != 8}">
						<json:property name="fdId" value="${shareGroupMember.fdId }"></json:property>
					</c:if>
					<json:property name="fdName" value="${shareGroupMember.fdName }"></json:property>
					<json:property name="fdOrgType" value="${shareGroupMember.fdOrgType }"></json:property>
				</json:object>
			</json:array>	
		</json:object>	
	</json:array>
</json:object>