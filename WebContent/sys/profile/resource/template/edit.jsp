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
	seajs.use(['theme!form']);
</script>
<script type="text/javascript">
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("docutil.js|validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
</script>
<title>
<template:block name="title" />
</title>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/icon.css" />
<template:block name="head" />
</head>
<body class="lui_config_form">
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8" ></c:import>
	<template:block name="toolbar" />
	<template:block name="path" >
		<% if(request.getParameter("s_path")!=null){ %>
		  <span class="txtlistpath"><div class="lui_icon_s lui_icon_s_home" style="float: left;"></div><div style="float: left;"><bean:message key="page.curPath"/>${fn:escapeXml(param.s_path)}</div></span>
		<% } %>
	</template:block>
	<template:block name="content" />
	<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
	<c:if test="${frameShowTop=='yes' }">
		<ui:top id="top"></ui:top>
		<kmss:ifModuleExist path="/sys/help">
			<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
		</kmss:ifModuleExist>
	</c:if> 
</body>
</html>
