<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include file="/sys/mportal/module/mobile/jsp/module.jsp">
	<template:replace name="head">
		<mui:cache-file name="mui-kms-common.js" cacheType="md5" />
		<mui:cache-file name="mui-kms-common.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="title">
		知识门户
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/mportal/module/mobile/Module"
			data-dojo-mixins="kms/common/mobile/module/js/ModuleMixin"></div>
	</template:replace>
</template:include>