<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 无数据预览，当example为图片时 -->
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
	</template:replace>
	<template:replace name="title">Portlet</template:replace>
	<template:replace name="body">
		<%
			String imgPath = request.getParameter("imgPath");
			pageContext.setAttribute("imgPath", imgPath);
		%>
		<div style="width: 100%;height: 100%;text-align: center;">
			<img src="${LUI_ContextPath}${imgPath}">
		</div>
	</template:replace>
</template:include>