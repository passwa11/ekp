<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/xform/maindata/mobile/resource/css/listview.css" />
	</template:replace>
	<template:replace name="content">
		<ul
		    data-dojo-type="mui/list/JsonStoreList"
		    data-dojo-mixins="sys/xform/maindata/mobile/resource/js/MainDataItemListMixin,mui/list/iframe/_IframeItemListMixin"
		    data-dojo-props="url:'${sourceURL}'">
		</ul>
	</template:replace>
</template:include>
