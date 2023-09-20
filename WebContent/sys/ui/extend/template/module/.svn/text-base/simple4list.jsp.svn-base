<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="renderer" content="webkit" />
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
			seajs.use(['theme!list', 'theme!portal']);	
		</script>
		<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
		<title>
			<template:block name="title" />
		</title>
		<template:block name="head" />
	</head>
	<c:if test="${not empty param.rwd && param.rwd eq 'true' }">
		<c:set var="rwdClass" value="rwd" />
	</c:if>
	<body class="lui_list_body tTemplate ${rwdClass }">
	
		<c:if test="${not empty param.spa && param.spa eq 'true'  }">
			<div data-lui-type="lui/spa!Spa" style="display: none;" >
				<script type="text/config">
					{"groups": "${JsParam['spa-groups']}" }
				</script>
			</div>
		</c:if>
		
		<div class="lui_list_body_frame" style="padding:0px">
			<div id="queryListView" style="width:100%">
				<template:block name="path" />
				<template:block name="content" />
			</div>
			<div id="mainContent" class="lui_list_mainContent" style="display: none;margin: 0">
				<div class="lui_list_mainContent_close" onclick="openQuery()" title="${lfn:message('button.back') }">
				</div>
				<iframe id="mainIframe" style="width: 100%;border: 0px;">
				</iframe>		
			</div>
		</div>
		<ui:top id="top"></ui:top>
		<template:block name="script" />
		<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	</body>
</html>