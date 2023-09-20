<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.kms.lservice.util.UrlsUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	List<Map<String, String>> adminUrls = UrlsUtil.getAdminUrls();

	if (adminUrls.size() == 0)
		return;

	Map<String, String> bank = adminUrls.get(0);

	String defaultJsp = bank.get("defaultJsp");

	if (StringUtil.isNull(defaultJsp))
		return;
%>

<c:import url="<%=defaultJsp%>" charEncoding="utf-8">
</c:import>


