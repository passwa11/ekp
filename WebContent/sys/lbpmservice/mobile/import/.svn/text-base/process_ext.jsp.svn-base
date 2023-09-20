<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.service.spring.*" %>
<%@ page import="java.util.Set" %>
<%@ include file="/resource/jsp/common.jsp"%>

<%
String modelName = request.getParameter("modelName");
Set exts = LbpmProcessJspExtManager.getInstance().getMobileJspExts(modelName);
pageContext.setAttribute("jspExts", exts);
%>

<c:if test = "${not empty jspExts}">
	<c:forEach items="${jspExts}" var="jspExt">
		<c:import url="${jspExt}" charEncoding="UTF-8">
			<c:param name="modelName" value="${param.modelName}" />
			<c:param name="modelId" value="${param.modelId}" />
			<c:param name="formName" value="${param.formName}" />
		</c:import>
	</c:forEach>
</c:if>