<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String cid = request.getParameter("cid");
	String LUIID = 	request.getParameter("LUIID");
	pageContext.setAttribute("cid",cid);
	pageContext.setAttribute("LUIID",LUIID);
%>
<template:include ref="default.simple">
	<template:replace name="body">	

	<iframe src="${ LUI_ContextPath }/sys/rss/sys_rss_main/sysRssMain.do?method=read&cid=${lfn:escapeHtml(cid)}&LUIID=${lfn:escapeHtml(LUIID)}" style="width:100%;border:0">
	</iframe>

	</template:replace>
</template:include>