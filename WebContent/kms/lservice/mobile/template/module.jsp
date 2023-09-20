<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<template:include ref="mobile.list">

	<template:replace name="head">

		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/kms/lservice/mobile/style/mobile.css" />

	</template:replace>

	<template:replace name="content">

		<script>
			var ___url___ = '${param.navJsp}';
		</script>


		<div data-dojo-type="kms/lservice/mobile/js/Menu"
			data-dojo-props="url:___url___"></div>

	</template:replace>
</template:include>
