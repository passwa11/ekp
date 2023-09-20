<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view">
	<template:replace name="title">
		<c:out value="${param.title }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css"
			href="${LUI_ContextPath}/sys/attachment/mobile/viewer/base/css/attViewer.css?s_cache=${MUI_Cache}"></link>
		<script>
		
			var authDownload = false;
			<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${param.fdId}" requestMethod="GET">
				authDownload = true;
			</kmss:auth>
		</script>

	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/attachment/mobile/viewer/video/VideoViewer"
			data-dojo-props="fdId:'${param.fdId }',scalable:false,authDownload:'authDownload'"></div>
			
		<!-- 页面计时组件 -->
		<c:if test="${not empty param._templateId && not empty param._contentId}">
			<div data-dojo-type="sys/attachment/mobile/js/AttachmentViewPageTime"
				data-dojo-props="fdId:'${param.fdId}',_templateId:'${param._templateId}',_contentId:'${param._contentId}'" />
		</c:if>
				
	</template:replace>
</template:include>
