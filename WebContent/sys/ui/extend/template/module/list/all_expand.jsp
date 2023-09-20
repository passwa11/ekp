<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<!doctype html>
<html class="lui_portal_default">
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
	<body class="lui_list_body tTempalte ${rwdClass }">
	
		<c:if test="${not empty param.spa && param.spa eq 'true'  }">
			<div data-lui-type="lui/spa!Spa" style="display: none;" >
				<script type="text/config">
					{"groups": "${JsParam['spa-groups']}" }
				</script>
			</div>
		</c:if>
		
		<portal:header var-width="100%" />
		
		<div class="lui_navExpand_banner" data-lui-mark="need-header-margin" >
			<template:block name="banner"/>
		</div>
		
		<div class="lui_navExpand_bodyContent status_spread ${not empty param.bodyContentClass ?  param.bodyContentClass : '' }" 
			data-lui-body-mark="lui_expand_body_content">
			<c:if test="${empty param.j_aside  || param.j_aside == true }">
				<div class="lui_navExpand_sideBar_frame" data-lui-mark="navExpand_sideBar">
					<template:block name="nav" />
				</div>
			</c:if>
			<div class="lui_navExpand_container_frame" <c:if test="${not empty param.j_noPadding && param.j_noPadding eq 'true'}">style='padding:0px;'</c:if>>
				
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
		</div>
		<ui:top id="top"></ui:top>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
		<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp" charEncoding="UTF-8"></c:import>
		<kmss:ifModuleExist path="/sys/help">
			<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
		</kmss:ifModuleExist>
		<template:block name="script" />
		<script src="${ LUI_ContextPath }/sys/ui/extend/template/resource/js/tTemplate.js?s_cache=${LUI_Cache}"></script>
	</body>
</html>