<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/person/sys_person_favorite_category/resource/css/category.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	
	<template:replace name="content">
		<div style="margin: 0 auto;" id="categoryCtx"></div>
	</template:replace>
</template:include>
<script type="text/javascript">

	seajs.use( [ 'theme!common', 'theme!icon', 'theme!category' ]);

	seajs
		.use(
			[ 'lui/category/category', 'lui/jquery', 'lui/topic' ],
			function(category, $, topic) {
				var url = '/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=select&modelName=${JsParam.modelName}&parentId=!{parentId}&searchText=!{searchText}&currId=!{currId}&type=!{type}&authType=${JsParam.authType}&pAdmin=!{pAdmin}';

				var cate = new category.Category( {
					elem : '#categoryCtx',
					url : url,
					mulSelect : "${JsParam.mulSelect}",
					authType: "${JsParam.authType}",
					currId : "${JsParam.currId}",
					modelName : "${JsParam.modelName}",
					errorMessage:"${lfn:message('sys-ui:ui.dialog.category.message')}",
					noFavorite : "${JsParam.noFavorite}",
				});
				
				cate.draw();
				
				topic
					.subscribe(
						'category.selected.changed',
						function(evt) {
							if (!evt || !evt.data
									|| evt.data.length == 0) {
								// 置空临时变量
								window.urlParams = [];
								return;
							}
							var ids = '', names = '';
	
							for ( var i = 0; i < evt.data.length; i++) {
								var data = evt.data[i].data[0];
								if (data && !data.nodeType) {
									ids += i === 0 ? data.value
											: ';' + data.value;
									names += i === 0 ? data.text
											: ';' + data.text;
								}
							}
							window.urlParams = [];
							if (ids != '') {
								window.urlParams = [ ids,
										names ];
							}
							
						});
				
				
			});
</script>
<style>
	body{background-color: white !important;}
</style>