<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.organization.service.ISysOrgPersonRestrictService"%>
<%@page import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>受限制用户信息</title>
<style>
table { border-collapse: collapse;border-spacing: 0;}
td {padding:2px 8px;border: 1px #d2d2d2 solid;word-break:break-all;}
</style>
</head>
<body style="margin:20px; line-height:24px; font-size:12px;">
<%
ISysOrgPersonRestrictService sysOrgPersonRestrictService = (ISysOrgPersonRestrictService)SpringBeanUtil.getBean("sysOrgPersonRestrictService");
List<Map<String, String>> datas = sysOrgPersonRestrictService.getAllData();
pageContext.setAttribute("datas", datas);
%>
<b>受限制用户信息(${fn:length(datas)})</b>
<table style="width:80%">
	<tr style="text-align: center;">
		<td width="8%">序号</td>
		<td width="23%">ID</td>
		<td width="23%">登录名</td>
		<td width="23%">名称</td>
		<td width="23%">所属部门</td>
	</tr>
<c:forEach items="${datas}" var="data" varStatus="status">
	<tr>
		<td><c:out value="${status.index + 1}" /></td>
		<td><c:out value="${data.fdId}" /></td>
		<td><c:out value="${data.fdLoginName}" /></td>
		<td><c:out value="${data.fdName}" /></td>
		<td><c:out value="${data.fdParentName}" /></td>
	</tr>
</c:forEach>
</table>
</body>
</html>