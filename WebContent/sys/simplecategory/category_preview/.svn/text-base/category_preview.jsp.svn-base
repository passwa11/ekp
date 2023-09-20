<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">

	<template:replace name="head">
		<template:super />

		<!--[if IE 8]>
		<script type="text/javascript"
			src="${LUI_ContextPath}/sys/simplecategory/category_preview/flash/Kanvas.js?s_cache=${ LUI_Cache }"></script>
		<![endif]-->

		<!--[if gt IE 8]>
		<script type="text/javascript"
			src="${LUI_ContextPath }/resource/js/jquery.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${LUI_ContextPath }/sys/simplecategory/category_preview/h5/kmsjsmap.min.css">
		<script type="text/javascript"
			src="${LUI_ContextPath}/sys/simplecategory/category_preview/h5/kmsjsmap.min.js?s_cache=${ LUI_Cache }"></script>
		<![endif]-->

		<!--[if !IE]><!-->

		<script type="text/javascript"
			src="${LUI_ContextPath }/resource/js/jquery.js?s_cache=${ LUI_Cache }"></script>
		<link rel="stylesheet" type="text/css"
			href="${LUI_ContextPath }/sys/simplecategory/category_preview/h5/kmsjsmap.min.css?s_cache=${ LUI_Cache }">
		<link rel="stylesheet" type="text/css"
			href="${LUI_ContextPath }/sys/simplecategory/category_preview/h5/btn.css?s_cache=${ LUI_Cache }">
		<script type="text/javascript"
			src="${LUI_ContextPath}/sys/simplecategory/category_preview/h5/kmsjsmap.min.js?s_cache=${ LUI_Cache }"></script>
		<!--<![endif]-->

	</template:replace>

	<template:replace name="body">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content
				title="${lfn:message('sys-simplecategory:menu.sysSimpleCategory.overview') }">
				<div id="previewContent" style="height: 620px"></div>
	
				<ui:event event="show">
					setTimeout(function(){
						initPreview();
					},500)
				</ui:event>
			</ui:content>

		</ui:tabpanel>

		<script type="text/javascript">
		
			// 此方法需要延迟500ms才能准确计算宽度，因为list.js中是延时500ms后才显示
			function initPreview() {
				
				seajs
						.use(
								'sys/simplecategory/category_preview/Mind',
								function(Mind) {

									var mind = new Mind();

									mind
											.init({
												url : "/sys/sc/categoryPreivew.do?method=getXMLContent&service=${lfn:escapeJs(service)}",
												container : 'previewContent'
											})

								});

			}
		</script>
	</template:replace>
</template:include>
