<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no" >
<template:replace name="body">
<script>
	seajs.use("sys/tag/resource/css/tag_cloud.css");
</script>
	<!-- 标签云 -->
	<ui:ajaxtext>
		<ui:dataview>
			<ui:source type="AjaxJson">
				{"url":"/sys/tag/sys_tag_portlet/sysTagPortlet.do?method=getHotTags&dataInfoType=cloud"}
			</ui:source>
			<ui:render type="Javascript">
				<c:import url="/sys/tag/resource/js/tag_cloud.js"
				charEncoding="UTF-8"></c:import>
			</ui:render>
		</ui:dataview>
	</ui:ajaxtext>
	</template:replace>
</template:include>