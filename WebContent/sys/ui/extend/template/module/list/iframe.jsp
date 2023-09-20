<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html class="lui_portal_quick">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="renderer" content="webkit" />
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<!-- 是否开启合并加载js模式  -->
		<c:choose>
			<c:when test="${compressSwitch eq 'true' && lfn:jsCompressEnabled('sysUiCompressExecutor', 'default_list_iframe_combined')}">
				<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("sysUiCompressExecutor","default_list_iframe_combined") %>?s_cache=${ LUI_Cache }">
				</script>
			</c:when>
		</c:choose>
		<template:block name="preloadJs"/>
		<script type="text/javascript">
			seajs.use(['theme!list', 'theme!portal']);	
		</script>
		<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
		<template:block name="head" />
	</head>
	<c:if test="${not empty param.rwd && param.rwd eq 'true' }">
		<c:set var="rwdClass" value="rwd" />
	</c:if>
	<body class="lui_list_body tTemplate ${rwdClass}">
		<c:if test="${not empty param.spa && param.spa eq 'true'  }">
			<div data-lui-type="lui/spa!Spa" style="display: none;" >
				<script type="text/config">
					{"groups": "${JsParam['spa-groups']}" }
				</script>
			</div>
		</c:if>

		<c:if test="${ (empty param.j_aside  || param.j_aside == true) && (empty param.j_rIframe  || param.j_rIframe == false) }">
			<div class="lui_list_left_sidebar_frame lui_list_left_sidebar_td">
				<div class="lui_list_left_sidebar_innerframe">
					<template:block name="nav" />
				</div>
			</div>
			<div class="lui_list_body_scroll_mask"></div>
			<script type="text/javascript">
				seajs.use(['lui/jquery'], function( $ ) {
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
		<c:if test="${empty param.j_notop || (not empty param.j_notop && param.j_notop == false) }">
				<ui:top id="top"></ui:top>
				<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp" charEncoding="UTF-8"></c:import>
				<kmss:ifModuleExist path="/sys/help">
					<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
				</kmss:ifModuleExist>
		</c:if>
	</body>
	<div data-lui-type="lui/title!Title" style="display: none;">
		<template:block name="title" />
	</div>
	<template:block name="script" />
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</html>
