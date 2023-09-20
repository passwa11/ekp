<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:out value="${modelingAppModelMainForm.fdModelName}"></c:out>
	</template:replace>
	<template:replace name="content">
		<script>
			Com_IncludeFile("data.js");
		</script>
		<html:form action="/sys/modeling/main/modelingAppModelMain.do" >
			<html:hidden property="listviewId" value="${param.listviewId}"/>
			<html:hidden property="fdId" value="${modelingAppModelMainForm.fdId}"/>
			<html:hidden property="docStatus" />
			<html:hidden property="fdModelId" />
			<br/>
			<div id="sysModelingXform">
				<c:import url="/sys/xform/include/sysForm_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent" />
					<c:param name="useTab" value="false" />
				</c:import>
			</div>
		</html:form>
	</template:replace>
</template:include>