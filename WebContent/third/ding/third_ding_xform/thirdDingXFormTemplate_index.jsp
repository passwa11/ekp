<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>

<template:include ref="config.edit" sidebar="no">
	 <template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("templateList.css","${LUI_ContextPath}/third/ding/third_ding_xform/resource/css/","css",true);
			var parentId = '${JsParam.parentId}';
		</script>
	 </template:replace>
	 <template:replace name="content">
	 	<div class="cm-hot-template">
	 		<!-- 分类 -->
	 		<div id="categoryList" data-lui-type="third/ding/third_ding_xform/resource/js/navDataView!navDataView" style="margin-left:15px;" class="lui_nav_main_content hot-template-top">
	 			<ui:source type="AjaxJson">
					{
						url : "/third/ding/xform/thirdDingXFormTemplate.do?method=listCategory&type=0"
					}
				</ui:source>
				<ui:render type="Javascript">
					<c:import url="/third/ding/third_ding_xform/resource/js/navRender.js" charEncoding="UTF-8"></c:import>
				</ui:render>
	 		</div>
	 		<!-- 列表 -->
		  	<div id="templateList" class="hot-template-main" data-lui-type="third/ding/third_ding_xform/resource/js/thirdDingXFormTemplateDataView!thirdDingXFormTemplateDataView">
				<ui:source type="AjaxJson">
					{
						url : "/third/ding/xform/thirdDingXFormTemplate.do?method=listTemplate&type=1&rowsize=9&category=attendance"
					}
				</ui:source>
				<ui:render type="Javascript">
					<c:import url="/third/ding/third_ding_xform/resource/js/thirdDingXFormTemplateRender.js" charEncoding="UTF-8"></c:import>
				</ui:render>
			</div>
		</div>
	</template:replace>
</template:include>