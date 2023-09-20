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
	seajs.use(['theme!list']);
</script>
<title>
<template:block name="title" />
</title>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/icon.css" />
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="${LUI_ContextPath}/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="${ LUI_ContextPath}/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
<template:block name="head" />
<script type="text/javascript">
	LUI.ready(function() {
		seajs.use([ 'lui/jquery','lui/toolbar' ],
			function($, toolbar) {
				var mark = new toolbar.ToolBar().____getCookie();
				// 调整导出按钮的底边距
				if (mark && mark == 'open') {
					$("body").addClass("lui_profile_list_export_body");
				}
			});
	});
</script>
</head>
<body class="lui_profile_list_body">
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	<c:if test="${not empty param.spa && param.spa eq 'true'  }">
		<div data-lui-type="lui/spa!Spa" style="display: none;" >
				<script type="text/config">
					{"groups": "${param['spa-groups']}" }
				</script>
		</div>
	</c:if>
	<template:block name="toolbar" />
	<template:block name="path" >
		<% if(request.getParameter("s_path")!=null){ %>
		 <span class="txtlistpath"><div class="lui_icon_s lui_icon_s_home" style="float: left;"></div><div style="float: left;"><bean:message key="page.curPath"/>${fn:escapeXml(param.s_path)}</div></span>
		<% } %>
	</template:block>
	<template:block name="content" /> 
	<ui:top id="top"></ui:top>
</body>

<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
</html>
