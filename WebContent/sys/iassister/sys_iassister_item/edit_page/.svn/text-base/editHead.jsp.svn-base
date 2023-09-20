<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="head">
	<script
		src="${LUI_ContextPath }/sys/iassister/resource/js/vue.min.js?s_cache=${LUI_Cache}"></script>
	<script
		src="${LUI_ContextPath }/sys/iassister/resource/js/element.js?s_cache=${LUI_Cache}"></script>
	<link rel="stylesheet" type="text/css"
		href="${ LUI_ContextPath}/sys/iassister/resource/css/element.css?s_cache=${LUI_Cache}" />
	<script type="text/javascript">
		Com_IncludeFile("security.js");
		Com_IncludeFile("domain.js");
		Com_IncludeFile("form.js");
		Com_IncludeFile("swf_attachment.js",
				"${LUI_ContextPath}/sys/attachment/js/", "js", true);
		Com_IncludeFile("item.css", "${LUI_ContextPath}/${baseUrl}/css/",
				"css", true);
		Com_IncludeFile("edit.css",
				"${LUI_ContextPath}/${baseUrl}/edit_page/css/", "css", true);
		var frontEnd = null;
		var langHere = null;
		var ruleInfo = ${sysIassisterItemForm.ruleInfo};
		var luiReady = function() {
			var jsPath = '${baseUrl}/js/edit.js';
			seajs.use([ jsPath ], function(front) {
				front.init({
					ctxPath : "${LUI_ContextPath}",
					actionUrl : "${actionUrl}",
					method : "${sysIassisterItemForm.method_GET}",
					categoryId : "${sysIassisterItemForm.docCategoryId}"
				});
				frontEnd = front;
				langHere = front.lang;
				front.loaded();
			})
		}
		LUI.ready(luiReady);
	</script>
</template:replace>
<template:replace name="title">
	<c:choose>
		<c:when test="${sysIassisterItemForm.method_GET == 'add'}">
			<c:out
				value="${ lfn:message('button.add') } - ${lfn:message('sys-iassister:table.sysIassisterItem')}"></c:out>
		</c:when>
		<c:when test="${ sysIassisterItemForm.method_GET == 'edit'}">
			<c:out
				value="${ lfn:message('button.edit') } - ${sysIassisterItemForm.fdName }"></c:out>
		</c:when>
	</c:choose>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" var-navwidth="90%"
		layout="sys.ui.toolbar.float" count="3">
		<ui:button text="${ lfn:message('button.save') }" order="5"
			onclick="frontEnd.save()" />
		<c:if test="${sysIassisterItemForm.method_GET == 'add'}">
			<ui:button text="${ lfn:message('button.saveadd') }" order="5"
				onclick="frontEnd.saveAdd()" />
		</c:if>
		<ui:button text="${ lfn:message('button.close') }" order="5"
			onclick="Com_CloseWindow();" />
	</ui:toolbar>
</template:replace>
<template:replace name="path">
</template:replace>
