<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include compatibleMode="true"
	file="/sys/mobile/template/edit_tiny.jsp">
	<template:replace name="head">
		<mui:cache-file name="mui-review-view.css" cacheType="md5" />	
		<mui:cache-file name="mui-review-edit.js" cacheType="md5" />
		<mui:cache-file name="mui-xform.js" cacheType="md5" />
		<mui:cache-file name="sys-lbpm-note.js" cacheType="md5" />
		<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
			<mui:cache-file name="mui-review-edit_ding.css" cacheType="md5"/>
		</c:if>
	</template:replace>
	<template:replace name="content">
	</template:replace>
</template:include>
