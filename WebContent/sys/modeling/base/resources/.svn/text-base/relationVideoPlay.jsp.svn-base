<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	request.setAttribute("themePath", SysUiPluginUtil.getThemePath(request));
%>

<template:include ref="default.simple">
	<template:replace name="title">三分钟了解业务关系</template:replace>
	<template:replace name="head">
		<template:super/>
	</template:replace>
	<template:replace name="body">
		<video width="800" height="360" controls="controls">
			<source src="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/MP4/relation.mp4" type="video/mp4" />
			<object data="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/MP4/relation.mp4" width="320" height="240">
			</object>
		</video>
<%--		<video src="sys/modeling/base/resources/MP4/relation.mp4" controls="controls">--%>
<%--			your browser does not support the video tag--%>
<%--		</video>--%>
	</template:replace>
</template:include>