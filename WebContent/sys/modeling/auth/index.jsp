<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
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
<script type="text/javascript" src="${ LUI_ContextPath }/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="${ LUI_ContextPath }/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
<link type="text/css" rel="stylesheet" href="${ LUI_ContextPath }/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${ LUI_ContextPath }/sys/ui/extend/theme/default/style/icon.css?s_cache=${LUI_Cache}" />

<title>
<template:block name="title" />
</title>
<template:block name="head" />
</head>
<body class="lui_list_body" style="margin: 5px;">

<table style="width:100%; ">
	<tr>
		<td valign="top" class="lui_list_body_td">
			<div class="lui_list_body_frame">
				<div id="queryListView" style="width:100%">
					<template:block name="path" />
					<template:block name="content" />
				</div>
				<div id="mainContent" class="lui_list_mainContent" style="margin: 0">
					<c:if test="${param.main eq 'right_form'}">
						<c:import url="/sys/modeling/auth/xform_auth/index_body.jsp" charEncoding="UTF-8">
							<c:param name="fdAppModelId" value="${param.fdAppModelId}" />
							<c:param name="hasFlow" value="${param.hasFlow}" />
						</c:import>
					</c:if>	
					<c:if test="${param.main eq 'right_opr'}">
						<c:import url="/sys/modeling/auth/sys_opr_auth/index_body.jsp" charEncoding="UTF-8">
							<c:param name="fdAppModelId" value="${param.fdAppModelId}" />
						</c:import>
					</c:if>	
					<c:if test="${param.main eq 'right_flow'}">
						<c:import url="/sys/modeling/auth/flow_auth/index_body.jsp" charEncoding="UTF-8">
							<c:param name="fdAppModelId" value="${param.fdAppModelId}" />
						</c:import>
					</c:if>
					<c:if test="${param.main eq 'right_default'}">
						<c:import url="/sys/modeling/auth/default_auth/index_body.jsp" charEncoding="UTF-8">
							<c:param name="fdAppModelId" value="${param.fdAppModelId}" />
							<c:param name="hasFlow" value="${param.hasFlow}" />
						</c:import>
					</c:if>	
				</div>
			</div>
		</td>
	</tr>
</table>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	<script>
	function renderList(flag){
		Com_OpenWindow("${LUI_ContextPath}/sys/modeling/auth/index.jsp?main="+flag+"&hasFlow=${param.hasFlow}&fdAppModelId=${param.fdAppModelId}","_self");
	}

	$(function(){
		var viewHeight = window.innerHeight-10;
		$('#_menu').css({height:viewHeight});
		$('#menu_nav').css({height:viewHeight-5});
		
	});

	</script>
</body>
</html>
