<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view">
	<template:replace name="title">
		<c:out value="${param.title }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css"
		href="${LUI_ContextPath}/sys/attachment/mobile/viewer/zip/css/zipViewer.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content">

		<div data-dojo-type="sys/attachment/mobile/viewer/zip/js/ZipViewer"
			data-dojo-props="
				url: '${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=zipView&fdId=${param.fdId }',
				title: '${param.title }'
			">

		</div>

	</template:replace>
</template:include>
