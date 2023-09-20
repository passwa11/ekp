<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="auto">
	<template:replace name="body">
		<html:hidden property="fdModelId"></html:hidden>
		<%-- 表单 --%>
		<div id="sysModelingXform">
			<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="modelingAppSimpleMainForm" />
				<c:param name="fdKey" value="modelingApp" />
				<c:param name="useTab" value="false" />
			</c:import>
		</div>
		<script>
			Com_IncludeFile("iframe.js",Com_Parameter.ContextPath+'sys/modeling/main/resources/js/view/','js',true);
		</script>
	</template:replace>
</template:include>