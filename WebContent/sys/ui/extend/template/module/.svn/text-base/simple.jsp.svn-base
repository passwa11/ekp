<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/simple.js?s_cache=${LUI_Cache}"></script>
<template:block name="head">
</template:block>
<title><template:block name="title"></template:block></title>
</head>
<c:if test="${not empty param.rwd && param.rwd eq 'true' }">
	<c:set var="rwdClass" value="rwd" />
</c:if>
<body class="${rwdClass} ${HtmlParam.bodyClass}">
	<c:if test="${not empty param.spa && param.spa eq 'true'  }">
		<div data-lui-type="lui/spa!Spa" style="display: none;"></div>
	</c:if>
  <template:block name="body">
  </template:block>
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>