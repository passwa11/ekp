<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="renderer" content="webkit" />
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
			seajs.use(['theme!list', 'theme!form','theme!portal']);
		</script>
		<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
		<template:block name="head" />
	</head>
	<body class="lui_portal_body tTemplate" style="margin-top: 0;">
		<c:if test="${not empty param.spa && param.spa eq 'true'  }">
			<div data-lui-type="lui/spa!Spa" style="display: none;" >
				<script type="text/config">
					{"groups": "${param['spa-groups']}" }
				</script>
			</div>
		</c:if>
		<div class="lui_portal_body_content" style="width:100%; min-width:100%;max-width:100%; margin:0; table-layout: fixed;">
			<c:if test="${not empty param['j_aside'] && param['j_aside'] == true }">
				<div class="lui_list_left_sidebar_frame">
					<div class="lui_list_left_sidebar_innerframe">
						<div class="lui_portal_sidebar_h_l">
							<div class="lui_portal_sidebar_h_r">
								<div class="lui_portal_sidebar_h_c">	
								</div>
							</div>
						</div>
						<div class="lui_portal_sidebar_c_l">
							<div class="lui_portal_sidebar_c_r">
								<div class="lui_portal_sidebar_c_c">							
									<template:block name="nav" />
								</div>
							</div>
						</div>
						<div class="lui_portal_sidebar_f_l">
							<div class="lui_portal_sidebar_f_r">
								<div class="lui_portal_sidebar_f_c">	
								</div>
							</div>
						</div>
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
		</div>
		<portal:footer var-width="980px" />
		<template:block name="script" />
	</body>
</html>