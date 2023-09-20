<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compaticle" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<body class="lui_list_body tTemplate">
	<portal:header var-width="100%" />
	<div class="lui_list_left_sidebar_frame lui_list_left_sidebar_td">
		<div class="lui_list_left_sidebar_innerframe">
			<template:block name="nav" />
		</div>
	</div>
	<div class="lui_list_body_scroll_mask"></div>
	<div class="lui_list_body_frame" <c:if test="${not empty param.j_noPadding && param.j_noPadding eq 'true'}">style='padding:0px;'</c:if>>
		<div id="queryListView" style="width:100%">
			<iframe id="ticMainIframe" style="width: 100%; border-top-color: currentColor; border-right-color: currentColor; border-bottom-color: currentColor; border-left-color: currentColor; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;" src=""></iframe>
		</div>
		<div id="mainContent" class="lui_list_mainContent" style="display: none;margin: 0">
			<div class="lui_list_mainContent_close" onclick="openQuery()" title="${lfn:message('button.back') }"></div>
			<iframe id="mainIframe" style="width: 100%;border: 0px;"></iframe>		
		</div>
	</div>
	<portal:footer var-width="100%" />
	<ui:top id="top"></ui:top>
</body>
</html>
<script type="text/javascript">
function ticOpenPageResize(){
	try{
		var ifr = LUI.$("#ticMainIframe");
		var sh = ifr[0].contentWindow.document.body.scrollHeight;
		var oh = ifr[0].contentWindow.document.body.offsetHeight;			 
		var chs = ifr[0].contentWindow.document.body.childNodes;
		var bh = 0;
		var bw = 0;
		for(var i=0;i<chs.length;i++){
			var tbh = chs[i].offsetTop + chs[i].offsetHeight;
			var tbw = chs[i].offsetLeft + chs[i].offsetWidth;
			if(tbh > bh){
				bh = tbh;
			}
			if(tbw > bw){
				bw = tbw;
			}
		}
		if(ifr.contents().innerWidth() > ifr.width()){
			bh = bh + 28;
		}
		if(ifr.contents().innerHeight() > bh){
			bh = ifr.contents().innerHeight();
		}
		ifr[0].style.height = (bh) + 'px';
	}catch(e){}
	openPageReisizeTimeout = window.setTimeout(ticOpenPageResize, 200);
}
</script>
<script src="${ LUI_ContextPath }/sys/ui/extend/template/resource/js/tTemplate.js?s_cache=${LUI_Cache}"></script>