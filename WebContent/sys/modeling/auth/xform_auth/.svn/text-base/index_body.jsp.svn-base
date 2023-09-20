<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<c:if test="${param.hasFlow eq 'true'}">
		<c:import url="/sys/modeling/auth/xform_auth/index_body_has.jsp" charEncoding="UTF-8">
			<c:param name="fdAppModelId" value="${param.fdAppModelId}" />
		</c:import>
	</c:if>
	<c:if test="${param.hasFlow eq 'false'}">
		<c:import url="/sys/modeling/auth/xform_auth/index_body_no.jsp" charEncoding="UTF-8">
			<c:param name="fdAppModelId" value="${param.fdAppModelId}" />
		</c:import>
	</c:if>