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
			seajs.use(['theme!list', 'theme!portal', 'theme!form']);	
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
					{"groups": "${param['spa-groups']}" }
				</script>
			</div>
		</c:if>
		
		<portal:header var-width="100%" />
		
		<c:if test="${empty param.j_aside  || param.j_aside == true }">
			<div class="lui_list_left_sidebar_frame lui_list_left_sidebar_td">
				<div class="lui_list_left_sidebar_innerframe">
					<template:block name="nav" />
				</div>
			</div>
			<div class="lui_list_body_scroll_mask" style="display:none;"></div>
			<script type="text/javascript">
				seajs.use(['lui/jquery'], function( $ ) {
					// 设置遮盖IE滚动条的面板高度之后再显示，避免页面初始化时遮盖到页眉影响用户体验（详见：#80341）
					$(".lui_list_body_scroll_mask").css("margin-top",$('.lui_portal_header').height()+"px");
					$(".lui_list_body_scroll_mask").show();						
					// 阻止左侧菜单滚轮滑动至顶部或底部时，右侧window的滚动条跟随滑动
					$.fn.addMouseWheel = function() {
					    return $(this).each(function() {
					        $(this).on("mousewheel DOMMouseScroll", function(event) {
					            var scrollTop = this.scrollTop,
					                scrollHeight = this.scrollHeight,
					                height = this.clientHeight;
					            var delta = (event.originalEvent.wheelDelta) ? event.originalEvent.wheelDelta : -(event.originalEvent.detail || 0);        
					            if ((delta > 0 && scrollTop <= delta) || (delta < 0 && scrollHeight - height - scrollTop <= -1 * delta)) {
					                this.scrollTop = delta > 0? 0: scrollHeight;
					                // 向上/向下滚
					                event.preventDefault();
					            }        
					        });
					    });	
					};
					$('.lui_list_left_sidebar_frame').addMouseWheel();
				});	
			</script>
		</c:if>
		<div class="lui_list_body_frame" <c:if test="${not empty param.j_noPadding && param.j_noPadding eq 'true'}">style='padding:0px;'</c:if>>
			
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
		<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp"></c:import>
		<template:block name="script" />
		<script src="${ LUI_ContextPath }/sys/ui/extend/template/resource/js/tTemplate.js?s_cache=${LUI_Cache}"></script>
	</body>
</html>