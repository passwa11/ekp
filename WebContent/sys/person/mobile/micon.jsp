<%@page import="com.landray.kmss.sys.person.util.SysPersonMiconUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<style>
			body {
				background: #1d9d74;
				color: #fff;
			}
			
			.container:after {
				display: block;
				content: "clear";
				clear: both;
				line-height: 0;
				visibility: hidden;
			}
			
			.container>div {
				padding: 6px 0;
				width: 10%;
				float: left;
				cursor: pointer;
			}
			
			.container>div:hover {
				background: #fff;
				color: #1d9d74;
			}
			
			@font-face {
				font-family: 'FontMui';
				src:url("<%=request.getContextPath()%>/sys/mobile/css/font/fontmui.woff");
			}
			
			.mui{
				font-size: 36px!important;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>
		<%
			request.setAttribute("clazs",SysPersonMiconUtil.toClass());
		%>
		<div style="margin: 20px auto; width: 95%;">
			<div class="container">
				<c:forEach items="${clazs }" var="claz">
					<div class="mui ${claz }" claz="${claz }"></div>
				</c:forEach>
			</div>
		</div>

		<script>
			seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				$('.container').on('click', function(evt) {
					var $target = $(evt.target);
					if ($target.hasClass('mui')) {
						var claz = $target.attr('claz');
						window.$dialog.hide(claz);
					}
				});
			});
		</script>

	</template:replace>
</template:include>