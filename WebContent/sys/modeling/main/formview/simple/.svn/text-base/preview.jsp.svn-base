<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:out value="${modelingAppSimpleMainForm.fdModelName}"></c:out>
	</template:replace>
	<template:replace name="content"> 
		<script>
			Com_IncludeFile("data.js");
		</script>
		<html:form action="/sys/modeling/main/modelingAppSimpleMain.do" >
			<html:hidden property="fdId" value="${modelingAppSimpleMainForm.fdId}"/>
			<html:hidden property="fdModelId" />
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
		</html:form>
	</template:replace>
</template:include>