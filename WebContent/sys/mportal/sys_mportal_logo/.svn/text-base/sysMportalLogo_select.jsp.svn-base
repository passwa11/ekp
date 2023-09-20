
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.mportal.plugin.MportalMportletUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
		<link
			href="${LUI_ContextPath }/sys/mportal/sys_mportal_logo/style/mlogo.css"
			rel="stylesheet" type="text/css" />
		<script>
			function submit() {
				var src = $('.selected img').data('src');
				if(!src) {
					seajs.use(['lui/dialog'], function(dialog) {
						dialog.alert("${lfn:message('page.noSelect')}");
					})
				} else {
					$dialog.hide({src:src});
				}
			}
		
			seajs.use([ 'lui/jquery' ], function($) {
				$(function() {
					$('.lui_mportal_container').on(
							'click',
							function(evt) {
								var $target = $(evt.target);
								var $a = $target.is('a') ? $target : $target
										.parent('a');
								if ($a.length > 0) {
									$('.lui_mportal_container a').removeClass(
											'selected');
									$a.addClass('selected');
								}
							});
				});
			});
		</script>

	</template:replace>

	<template:replace name="content"> 
		<c:if test="${not empty param.logo }">
			<c:set var="logo" value="${param.logo }"></c:set>
		</c:if>
		<div class="lui_mportal_container lui_mportal_selectLogo_container">
			<div>
				<a href="javascript:;"
					<c:if test="${logo =='/resource/images/logo.png' }">class="selected"</c:if>><img
					class="lui_mportal_logo"
					src="${LUI_ContextPath }/resource/images/logo.png" alt="蓝凌"
					data-src="/resource/images/logo.png"> <span
					class="lui_mportal_tag"></span> </a>

				<c:forEach items="${paths }" var="path">
					<a href="javascript:;"
						<c:if test="${logo == path }">class="selected"</c:if>><img
						class="lui_mportal_logo" src="${LUI_ContextPath }${path }"
						data-src="${path}"> <span class="lui_mportal_tag"></span> </a>
				</c:forEach>
			</div>
			<div style="margin-top:20px;">
				<ui:button text="${lfn:message('button.ok')}" onclick="submit()" width="100px;"></ui:button>
			</div>
		</div>
	</template:replace>
</template:include>