<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.attribute.ExtNodeDescriptionManager" %>
<%@ page import="java.util.List,java.util.Iterator" %>
<%@ include file="/resource/jsp/common.jsp"%>

<%

ExtNodeDescriptionManager instance = ExtNodeDescriptionManager.getInstance();
pageContext.setAttribute("includes", instance.getConfigJspList(request.getParameter("modelName")));

%>

<c:forEach items="${includes}" var="include">
	<c:import url="${include}" charEncoding="UTF-8">
		<c:param name="modelName" value="${param.modelName}" />
		<c:param name="nodeType" value="${param.nodeType}" />
	</c:import>
</c:forEach>