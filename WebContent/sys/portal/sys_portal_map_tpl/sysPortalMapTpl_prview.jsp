<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" width="95%" sidebar="no">
	<template:replace name="body">
		<script>
		
				window.setForm=function(data,inletArr){
						window._templateData = JSON.stringify(data);
						window._templateInlet = JSON.stringify(inletArr);
				}
				
				
		</script>
		
		<ui:dataview format="sys.ui.mapTpl" id="mapTpl">
			<ui:source type="AjaxJson">
				{url:"/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=portlet&fdId=!{fdId}"}
			</ui:source>
			<ui:render type="Template" ref="sys.ui.mapTpl.default">
			</ui:render>
		</ui:dataview>
	</template:replace>
</template:include>
