<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include compatibleMode="true"
	file="/sys/mobile/template/edit_tiny.jsp">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/multidoc/mobile/css/edit.css" />
		<mui:cache-file name="mui-xform.js" cacheType="md5" />
		<mui:cache-file name="sys-lbpm-note.js" cacheType="md5" />
	</template:replace>
	<template:replace name="content">
	</template:replace>
</template:include>