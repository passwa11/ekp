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
<template:include ref="config.edit" sidebar="no">
	<template:replace name="title">
		<c:if test="${ modelingAppModelForm.method_GET == 'createPrint' || modelingAppModelForm.method == 'createPrint' }">
			<bean:message bundle="sys-print" key="multiPrint.new"/>
		</c:if>
		<c:if test="${ modelingAppModelForm.method_GET == 'editPrint' || modelingAppModelForm.method == 'editPrint' }">
			<bean:message bundle="sys-print" key="multiPrint.edit"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<style>
			body{
				overflow-y:auto!important;
			}
		</style>
		<script type="text/javascript" src="${LUI_ContextPath }/resource/js/base64.js"></script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ modelingAppModelForm.method_GET == 'createPrint' || modelingAppModelForm.method == 'createPrint' }">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="submitForm('create');" />
				</c:when>
				<c:when test="${ modelingAppModelForm.method_GET == 'editPrint' || modelingAppModelForm.method == 'editPrint' }">
					<ui:button text="${lfn:message('button.update')}" order="1" onclick="submitForm('update');" />
				</c:when>
			</c:choose>
	    	<ui:button text="${lfn:message('button.close') }" order="2" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class='print_template_head txttitle'>
			<c:if test="${ modelingAppModelForm.method_GET == 'createPrint' || modelingAppModelForm.method == 'createPrint' }">
				<bean:message bundle="sys-print" key="multiPrint.new"/>
			</c:if>
			<c:if test="${ modelingAppModelForm.method_GET == 'editPrint' || modelingAppModelForm.method == 'editPrint' }">
				<bean:message bundle="sys-print" key="multiPrint.edit"/>
			</c:if>
		</div>
		<div class='print_template_content'>
			<html:form action="/sys/modeling/base/modelingAppModel.do">
				<c:import url="/sys/print/include/multi_template/sysPrintTemplate_edit_content.jsp" charEncoding="UTF-8" >
					<c:param name="formName" value="${formName }"></c:param>
					<c:param name="fdKey" value="modelingApp"></c:param>
					<c:param name="modelName" value="${modelName }"></c:param>
					<c:param name="templateModelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppModel"></c:param>
					<c:param name="_isHide" value='false'></c:param>
					<c:param name="_isHidePrintDesc" value="false"></c:param>
					<c:param name="_isHideDefaultSetting" value="true"></c:param>
					<c:param name="_isShowName" value="true"></c:param>
					<c:param name="_isHidePrintMode" value="${_isHidePrintMode }"></c:param>
					<c:param name="_isXForm" value="true"></c:param>
					<c:param name="enableFlow" value='${enableFlow }'></c:param>
                    <c:param name="_isModeling" value='true'></c:param>
				</c:import>
			</html:form>
		</div>
		<script>
			new $KMSSValidation(document.modelingAppModelForm);
			function submitForm(operType){
				//提交前对表单的设计文本进行加密
				var fdDesignerHtmlObj = $("[name='sysFormTemplateForms.modelingApp.fdDesignerHtml']")[0];
				if(fdDesignerHtmlObj){
					fdDesignerHtmlObj.value = base64Encodex(fdDesignerHtmlObj.value);
				}
				//兼容单表单的多表单表现
				var fdDesignerHtmlObj1 = $("[name='sysFormTemplateForms.modelingApp.fdSubForms[0].fdDesignerHtml']")[0];
				if(fdDesignerHtmlObj1){
					fdDesignerHtmlObj1.value = base64Encodex(fdDesignerHtmlObj1.value);
				}
				var fdPrintDesignerHtml = $("[name='fdPrintForms[0].fdPrintDesignerHtml']")[0];
				if(fdPrintDesignerHtml){
					fdPrintDesignerHtml.value = base64Encodex(fdPrintDesignerHtml.value);
				}


				var formObj = document.modelingAppModelForm;
				var url = Com_CopyParameter(formObj.action);
				url = Com_SetUrlParameter(url, "operType", operType);
				formObj.action = url;
				url = Com_SetUrlParameter(url, "fdModelId", '${requestScope[formName].fdId}');
				Com_Submit(document.modelingAppModelForm, 'updatePrint');
			}
		</script>
	</template:replace>
</template:include>
