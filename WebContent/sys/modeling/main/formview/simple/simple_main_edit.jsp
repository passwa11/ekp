<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:out value="${modelingAppSimpleMainForm.fdModelName}"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">  
			<c:if test="${modelingAppSimpleMainForm.method_GET=='add'}">
				<ui:button text="${saveButtonText}" order="1" onclick="submitForm('save');"></ui:button>
			</c:if>
			<c:if test="${modelingAppSimpleMainForm.method_GET=='edit'}">
				<ui:button text="${updateButtonText}" order="1" onclick="submitForm('update');"></ui:button>
			</c:if>
			<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow()"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:hidden property="detailOperationAuthConfig" value="${detailOperationAuthConfig}" />
		<html:form action="/sys/modeling/main/modelingAppSimpleMain.do" >
			<html:hidden property="listviewId" value="${param.listviewId}"/>
			<html:hidden property="fdId" value="${modelingAppSimpleMainForm.fdId}"/>
			<html:hidden property="fdModelId" />
			<html:hidden property="docCreateTime"/>
			<html:hidden property="docCreatorId"/>
			<html:hidden property="fdTreeNodeData" value="${modelingAppModelMainForm.fdTreeNodeData}"/>
			<br/>
			<%-- 表单 --%>
			<div id="sysModelingXform">
				<c:import url="/sys/xform/include/sysForm_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppSimpleMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent" />
					<c:param name="useTab" value="false" />
				</c:import>
			</div>
			<br/>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:if test="${empty tabList }">
					<%-- 权限 --%>
					<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="modelingAppSimpleMainForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain" />
						<c:param name="expand" value="true"></c:param>
						<c:param name="isModeling" value="true"></c:param>
					</c:import>
				</c:if>
				<%-- 查看视图定义标签 --%>
				<c:import url="/sys/modeling/base/view/ui/viewtabs.jsp" charEncoding="UTF-8">
					<c:param name="expand" value="true" />
				</c:import>
			</ui:tabpage>
			<script>
				Com_IncludeFile('view_common.js','${LUI_ContextPath}/sys/modeling/main/resources/js/','js',true);
				Com_IncludeFile("detailOperationAuth.js", "${LUI_ContextPath}/sys/modeling/main/resources/js/", 'js', true);
			</script>
		</html:form>
		<script>
			var validation = $KMSSValidation();
			function submitForm(method){
				if (validation.validate()){
					Com_Submit(document.modelingAppSimpleMainForm, method);
				}
			}
			$(function(){
				$("#lui_validate_message").css("display","none");
				initDetailOperationAuth($("input[name='detailOperationAuthConfig']").val(), false, "${param.method}");
			})
		</script>
	</template:replace>
</template:include>