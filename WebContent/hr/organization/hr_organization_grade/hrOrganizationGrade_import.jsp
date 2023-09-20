<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">

	<template:replace name="body">
   		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/organization.css">
   		<html:form action="/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=importGrade"  enctype="multipart/form-data" styleClass="lui_hr_import_form">
   			<iframe name="file_frame" style="display:none;"></iframe>	
       		<c:import url="/hr/organization/hr_organization_common/hr_organization_import.jsp" charEncoding="UTF-8">
       			<c:param name="downloadTemplateUrl" value="${LUI_ContextPath}/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=downloadTemplate"></c:param>
  				<c:param name="formName" value="hrOrganizationGradeForm"></c:param>
  			</c:import>
        </html:form>
	</template:replace>
</template:include>
