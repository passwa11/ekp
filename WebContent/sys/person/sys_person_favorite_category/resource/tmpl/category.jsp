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
<c:if test="${empty param.noFavorite}">
	<c:set var="noFavorite" value="${(param.isShowTemp eq '0') ? 'true' : 'false'}" scope="page" />
</c:if>
<script type="text/javascript">
	seajs.use( [ 'theme!common', 'theme!icon', 'theme!category' ]);
	seajs
		.use(
			[ 'lui/category/category', 'lui/jquery', 'lui/topic'],
			function(category, $, topic) {
				$(document)
					.ready(
						function() {
							var cate = new category.Category(
									{
										elem : '#categoryCtx',
										url : '/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName=${JsParam.modelName}&parentId=!{parentId}&searchText=!{searchText}&currId=!{currId}&type=!{type}&getTemplate=${JsParam.isShowTemp}&authType=${JsParam.authType}&pAdmin=!{pAdmin}&tempKey=${JsParam.tempKey}',
										mulSelect : "${JsParam.mulSelect}",
										currId : "${JsParam.currId}",
										authType: "${JsParam.authType}",
										modelName: "${JsParam.modelName}",
										errorMessage:"${lfn:message('sys-ui:ui.dialog.template.message')}",
										noFavorite : "${(empty param.noFavorite) ? noFavorite : (JsParam.noFavorite)}"
									});
							cate.draw();
							topic
								.subscribe(
										'category.selected.changed',
										function(evt) {
											var data = evt.data;
											var ids = '', names = '';
											var selected = false;
											for ( var i = 0; i < data.length; i++) {
												for ( var j = 0; j < data[i].data.length; j++) {
													if (!data[i].data[j].nodeType || data[i].data[j].nodeType == 'template') {
														var d = data[i].data[0];
														ids += i === 0 ? d.value
																: (';' + d.value);
														names += i === 0 ? d.text
																: (';' + d.text);
														selected = true;
														break;
													}
												}
											}
											selected ? window.urlParams = [
												ids,
												names ]
												: window.urlParams = [];
								});
						});
				});
</script>
<style>
	body{background-color: white !important;}
</style>