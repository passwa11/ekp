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
		<script type="text/javascript">
			seajs.use(['theme!list', 'theme!form','theme!portal']);
		</script>
		<title><portal:title/></title>
	</head>
	<body class="tTemplate">
	<!--随意一个不为空则不显示页眉-->
		<c:if test="${empty param.j_rIframe && empty param.j_iframe}">
			<portal:header scene="${empty page_scene ? 'portal' : page_scene}" var-width="100%" />
		</c:if>
		<c:if test="${empty param.j_aside  || param.j_aside == true }">
			<div class="lui_list_left_sidebar_frame lui_list_left_sidebar_td">
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
								<template:block name="aside" />
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
		</c:if>
		<div class="lui_list_body_frame">
			<template:block name="content" />
		</div>
		<portal:footer var-width="980px" />
		<ui:top id="top"></ui:top>
		<script src="${ LUI_ContextPath }/sys/ui/extend/template/resource/js/tTemplate.js?s_cache=${LUI_Cache}"></script>
	</body>
</html>
</c:if>
<%-- 急速模式下只返回body --%>
<c:if test="${not empty param['j_content'] && param['j_content'] == true }">
	<div class="lui_portal_quick_content">
		<div class="lui_portal_aside lui_list_left_sidebar_frame">
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
							<template:block name="aside" />
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
		<div class="lui_list_body_frame">
			<template:block name="content" />
		</div>
		<div data-lui-type="lui/title!Title" style="display: none;">
			<portal:title/>
		</div>
		<div data-lui-type="lui/title!portalPageTitle" style="display: none;">
			${headerPortalPageName}
		</div>
		<portal:footer scene="portal" var-width="100%"/>
	</div>
	
</c:if>

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