<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="title">
		<c:out value="${modelingAppModelMainForm.fdModelName}"></c:out>
</template:replace>
<template:replace name="toolbar">
	<script type="text/javascript">
		seajs.use(['lui/toolbar','lui/popup'],function() {
		}); 
	</script>
	<style>
		.lui_widget_btn_txt {
			white-space: nowrap;
			overflow: hidden;
			text-overflow:ellipsis;
			width:60px;
		}
	</style>
	<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="3" style="display:none;">  
		<%-- 业务操作按钮 --%>
		<%@ include file="/sys/modeling/base/view/ui/viewopers.jsp"%>
		<ui:button text="${lfn:message('button.close')}" order="5" onclick="window.close();" />
	</ui:toolbar>
</template:replace>