<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
	<%@ include file="/sys/ui/jsp/jshead.jsp" %>
	<script type="text/javascript">
        seajs.use(['theme!list', 'theme!portal']);
    </script>
    <script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="${ LUI_ContextPath }/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/ui/extend/theme/default/style/icon.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>

    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>

<title>
<template:block name="title" />
</title>
<template:block name="head" />
</head>
<body class="lui_list_body" >
<div id="mainContent" class="lui_list_mainContent fullscreen ${param.main}" style="margin: 0;display:none;">

	<c:if test="${param.main eq 'listview_main'}">
		<c:import url="/sys/modeling/base/listview/config/index.jsp" charEncoding="UTF-8">
			<c:param name="fdModelId" value="${param.fdModelId}" />
			<c:param name="method" value="edit" />
		</c:import>
	</c:if>
	<c:if test="${param.main eq 'view_main'}">
		<c:import url="/sys/modeling/base/view/config/index.jsp" charEncoding="UTF-8">
			<c:param name="fdModelId" value="${param.fdModelId}" />
		</c:import>
	</c:if>
	<c:if test="${param.main eq 'portletView_main'}">
		<c:import url="/sys/modeling/base/portlet/index.jsp" charEncoding="UTF-8">
			<c:param name="fdModelId" value="${param.fdModelId}" />
		</c:import>
	</c:if>
	<c:if test="${param.main eq 'business' }">
		<c:import url="/sys/modeling/base/views/business/index.jsp" charEncoding="UTF-8">
			<c:param name="fdModelId" value="${param.fdModelId}"/>
		</c:import>
	</c:if>
	<c:if test="${param.main eq 'treeView_main' }">
		<c:import url="/sys/modeling/base/treeView/index.jsp" charEncoding="UTF-8">
			<c:param name="fdModelId" value="${param.fdModelId}"/>
		</c:import>
	</c:if>
</div>

<script>
function renderList(flag){
	Com_OpenWindow("${LUI_ContextPath}/sys/modeling/base/view/index.jsp?main="+flag+"&fdModelId=${param.fdModelId}","_self");
}

	$(function(){
		var viewHeight = window.innerHeight-10;
		$('#_menu').css({height:viewHeight});
		$('#menu_nav').css({height:viewHeight-5});
		
	});
	Com_AddEventListener(window,"load",function(){
 		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	 		var url = '<c:url value="/sys/modeling/base/modelingAppFlow.do" />?method=hasXForm&fdModelId=${param.fdModelId}';
	 		$.ajax({
					url: url,
					type: 'GET',
					dataType: 'json',
					success: function(rtn){
						if(rtn.hasXForm === "true"){
							$("#mainContent").css("display","block");
						}else{
							//dialog.alert("${lfn:message('sys-modeling-base:modeling.flow.validateForm')}");
							var nurl = Com_Parameter.ContextPath + "sys/modeling/base/noxform/noxform.jsp";
							window.location.href = nurl;
						}
					}
			   });
 		})
 	})
</script>
</body>
</html>
