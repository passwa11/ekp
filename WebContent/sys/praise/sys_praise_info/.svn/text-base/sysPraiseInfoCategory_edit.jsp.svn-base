<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" >
			<c:choose>
				<c:when test="${ sysPraiseInfoCategoryForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysPraiseInfoCategoryForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysPraiseInfoCategoryForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysPraiseInfoCategoryForm, 'save');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/sys/praise/sys_praise_info_category/sysPraiseInfoCategory.do">
 
<p class="txttitle"><bean:message bundle="sys-praise" key="sysPraiseInfoCategory.config"/></p>

<center>
<table class="tb_normal" id="Label_Tabel" width=95%>
		<c:import url="/sys/simplecategory/include/sysCategoryMain_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="sysPraiseInfoCategoryForm" />
			<c:param name="requestURL" value="/sys/praise/sys_praise_info_category/sysPraiseInfoCategory.do?method=add" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
			<c:param name="fdKey" value="mainDoc" />
		</c:import>
</table> 
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
</script>
</html:form>

	</template:replace>
</template:include>
