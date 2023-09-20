<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
	seajs.use(['theme!list', 'theme!zone']);
</script>
<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
<div data-lui-type="lui/title!Title" style="display: none;">
	<template:block name="title" />
</div>
<template:block name="head" />
</head>
<body class="lui_list_body">
<c:set var="frameWidth" scope="page" value="${(empty param.width) ? '90%' : fn:escapeXml(param.width)}"/>  
	<div style="width:${frameWidth}; min-width:980px;max-width:${fdPageMaxWidth}; margin:10px auto;">
	 	<template:block name="path" />
		<template:block name="content" />
	</div>
<ui:top id="top"></ui:top>
</body>
</html>