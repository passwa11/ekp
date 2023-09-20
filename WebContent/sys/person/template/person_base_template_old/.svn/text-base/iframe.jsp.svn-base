<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%
	request.setAttribute("fdWidth", SysUiConfigUtil.getFdWidth());
	request.setAttribute("fdPersonLeftSide", SysUiConfigUtil.getFdPersonLeftSide());
%>
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
<title>
<template:block name="title" />${HtmlParam.scene }
</title>
<template:block name="head" />
</head>
<body class="lui_portal_body" style="margin-top: 0;">

<table style="width:${empty param.iframe ? ((empty param.pagewidth) ? fdWidth : (param.pagewidth)) : '100%'}; min-width:${empty param.iframe ? '980px' : '100%'};max-width:${empty param.iframe ? fdPageMaxWidth : '100%'}; margin:${empty param.iframe ? '15px auto' : '0'};table-layout: fixed;">
	<tr>
		<c:if test="${not empty param['j_aside'] && param['j_aside'] == true }">		
		<td valign="top" style="width: ${fdPersonLeftSide}px">
			<div class="lui_list_left_sidebar_frame old" style="width: ${fdPersonLeftSide}px">
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
		</td>
		<td style="width: 15px;"></td>
		</c:if>
		<td valign="top">
			<div class="lui_list_body_frame">
				<%-- <template:block name="path" /> --%>
				<template:block name="content" />
			</div>
		</td>
	</tr>
</table>
<portal:footer var-width="980px" />
<script>
seajs.use(['lui/jquery'], function($) {
	$(document).ready(function() {
		domain.autoResize(); 
	});
});
</script>

</body>
</html>
