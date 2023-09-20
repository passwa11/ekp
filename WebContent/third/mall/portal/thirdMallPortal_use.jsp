<%@page import="com.landray.kmss.third.mall.util.MallUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.view" sidebar="no">
	 <template:replace name="title">
		${lfn:message('third-mall:thirdMall.useTemplate')}
	 </template:replace>
	 <template:replace name="head">
	 	<style>
	 		.content {
	 			padding: 10px;
	 			text-align: center;
	 		}
			.content img {
				width: 900px;
			}
		</style>
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/dialog', 'third/mall/resource/js/thirdPortalUse'], function($, dialog, thirdPortalUse) {
			thirdPortalUse.useTpl('${JsParam.fdTemplateId}', '${JsParam.type}', function(val) {
				setTimeout(function() {Com_CloseWindow();}, 1500);
			});
		});
		</script>
	 </template:replace>
	 <template:replace name="content">
	 	<div class="content">
		 	<br>
		 	<h2>${HtmlParam.docSubject}</h2>
		 	<c:if test="${empty param.picId}">
		 		<img src="<%=MallUtil.MALL_DOMMAIN%>/km/reuse/km_reuse_page/images/kmReuseXformSetImage.png">
		 	</c:if>
		 	<c:if test="${not empty param.picId}">
		 		<c:if test="${JsParam.type=='login' || JsParam.type=='theme'}">
		 			<img src="<%=MallUtil.MALL_DOMMAIN%>/km/reuse/mobile/kmReusePublicMobileAction.do?method=downloadPic&fdId=${param.picId}">
		 		</c:if>
		 		<c:if test="${JsParam.type=='render' || JsParam.type=='panel' || JsParam.type=='header' ||JsParam.type=='footer' ||JsParam.type=='template'}">
		 			<img src="<%=MallUtil.MALL_DOMMAIN%>/km/reuse/mobile/kmReusePublicMobileAction.do?method=downloadThumb&thumb=${param.picId}">
		 		</c:if>
		 	</c:if>
	 	</div>
	</template:replace>
</template:include>