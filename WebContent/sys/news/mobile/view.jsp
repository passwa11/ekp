<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include compatibleMode="true" file="/sys/mobile/template/view_tiny.jsp">
	<template:replace name="head">
		<mui:cache-file name="sys-lbpm-note.js" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
	</template:replace>
</template:include>