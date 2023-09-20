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
		Com_IncludeFile("item.css", "${LUI_ContextPath}/${baseUrl}/css/",
				"css", true);
		Com_IncludeFile("view.css",
				"${LUI_ContextPath}/${baseUrl}/view_page/css/", "css", true);
		var frontEnd = null;
		var langHere = null;
		var ruleInfo = ${sysIassisterItemForm.ruleInfo};
		var luiReady = function() {
			var jsPath = '${baseUrl}/js/view.js';
			seajs.use([ jsPath ], function(front) {
				front.init({
					ctxPath : "${LUI_ContextPath}",
					actionUrl : "${actionUrl}"
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
	<c:out
		value="${ lfn:message('sys-iassister:table.sysIassisterItem') } - ${sysIassisterItemForm.fdName }"></c:out>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" var-navwidth="90%"
		layout="sys.ui.toolbar.float" count="3">
		<kmss:auth
			requestURL="/sys/iassister/sys_iassister_item/sysIassisterItem.do?method=edit&fdId=${sysIassisterItemForm.fdId}"
			requestMethod="GET">
			<ui:button text="${ lfn:message('button.edit') }" order="5"
				onclick="frontEnd.edit('${sysIassisterItemForm.fdId }')" />
		</kmss:auth>
		<ui:button text="${ lfn:message('button.close') }" order="5"
			onclick="Com_CloseWindow();" />
	</ui:toolbar>
</template:replace>
<template:replace name="path">
</template:replace>
