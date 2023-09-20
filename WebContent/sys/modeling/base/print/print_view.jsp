<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
boolean enableFlow = (Boolean)request.getAttribute("enableFlow");
if(enableFlow) {
	request.setAttribute("modelName", "com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
}else {
	request.setAttribute("modelName", "com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain");
}
%>
<template:include ref="config.view" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-print" key="multiPrint.view"/>
	</template:replace>
	<template:replace name="head">
		<link href="${KMSS_Parameter_ContextPath}sys/print/resource/css/print_template.css" type="text/css" rel="stylesheet" />
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
	    	<ui:button text="${lfn:message('button.close') }" order="2" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class='print_template_head txttitle'>查看打印模板</div>
		<div class='print_template_content'>
			<c:import url="/sys/print/include/sysPrintTemplate_view.jsp">
				<c:param name="formName" value="${formName }" />
				<c:param name="fdKey" value="modelingApp" />
				<c:param name="modelName" value="${modelName }"/>
				<c:param name="templateModelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppModel"/>
				<c:param name="_isHide" value='false'></c:param>
				<c:param name="_isHidePrintDesc" value="false"></c:param>
				<c:param name="_isHideDefaultSetting" value="true"></c:param>
				<c:param name="_isShowName" value="true"></c:param>
				<c:param name="fdModelTemplateId" value="${modelId}"></c:param>
			</c:import>
		</div>
	</template:replace>
</template:include>
