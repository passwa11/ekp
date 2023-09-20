<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%  request.setAttribute("fdWidth", SysUiConfigUtil.getFdWidth()); %>
<%-- 非急速模式下返回完整页面 --%>
<c:if test="${empty param['j_content'] }">
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="renderer" content="webkit" />
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/person/resource/css/person.css?s_cache=${LUI_Cache }"/>
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/ui/extend/theme/default/style/module.css?s_cache=${LUI_Cache }" />
		<script type="text/javascript">
			seajs.use(['theme!list', 'theme!form','theme!portal']);
		</script>
		<title><portal:title/></title>
	</head>
	<body class="lui_portal_body">
	<!--随意一个不为空则不显示页眉-->
	<c:if test="${empty param.j_rIframe && empty param.j_iframe}">
		<portal:header scene="${empty page_scene ? 'portal' : page_scene}" var-width="${ (empty param.pagewidth) ? fdWidth : (param.pagewidth) }" />
	</c:if>
		<div class="lui_portal_personal_content">
			<%@ include file="/sys/person/portal/persontitle.jsp" %>
			<div style="margin: 0px auto;${empty param['pagewidth'] ? 'width:980px' : lfn:concat('width:',param['pagewidth']) };min-width:980px; max-width:${fdPageMaxWidth}">
				<div class="lui_list_body_frame" >
					<template:block name="content"></template:block>
				</div>
			</div>
			<ui:iframe id="idx_personal_body_iframe" ></ui:iframe>
		</div>
		<portal:footer scene="portal" var-width="${empty param['pagewidth'] ? '980px' : param['pagewidth'] }"/>
		<ui:top id="top"></ui:top>
	</body>
</html>
</c:if>
<%-- 急速模式下只返回body --%>
<c:if test="${not empty param['j_content'] && param['j_content'] == true }">
	<div class="lui_portal_personal_content" >
		<%@ include file="/sys/person/portal/persontitle.jsp" %>
			<div class="lui_list_body_frame" style="margin: 0px auto;${empty param['pagewidth'] ? 'width:980px' : lfn:concat('width:',param['pagewidth']) };min-width:980px; max-width:${fdPageMaxWidth}">
				<template:block name="content"></template:block>
			</div>
			<ui:iframe id="idx_personal_body_iframe" ></ui:iframe>
			<portal:footer scene="portal" var-width="100%"/>
			<div data-lui-type="lui/title!Title" style="display: none;">
				<portal:title/>
			</div>
			<div data-lui-type="lui/title!portalPageTitle" style="display: none;">
				${headerPortalPageName}
			</div>
			<ui:top id="top"></ui:top>
	</div>
	
</c:if>