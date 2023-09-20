<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:if test="${not empty param.spa && param.spa eq 'true'  }">
	<div data-lui-type="lui/spa!Spa" style="display: none;" >
		<script type="text/config">
			{"groups": "${param['spa-groups']}" }
		</script>
	</div>
</c:if>
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
	
	<div class="lui_list_body_frame">
		<template:block name="content" />
		<portal:footer scene="portal" var-width="100%"/>
	</div>
	<div data-lui-type="lui/title!Title" style="display: none;">
		<template:block name="title" />${HtmlParam.scene }
	</div>
	<div data-lui-type="lui/title!portalPageTitle" style="display: none;">
		${headerPortalPageName}
	</div>
</div>
