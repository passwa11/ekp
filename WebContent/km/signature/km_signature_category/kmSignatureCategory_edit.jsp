<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<kmss:windowTitle subjectKey="km-signature:table.signatureCategory" moduleKey="km-signature:module.km.signature" />
<script language="JavaScript">
Com_IncludeFile("dialog.js");

function mymethod(a){
	//debugger;
	var fdName = document.getElementsByName("fdName")[0];
	if(fdName.value == "" || fdName.value == null){
		alert('<bean:message bundle="km-signature" key="signatureCategory.warn1"/>');
		return false;
	}
	if(fdName.value != "" && fdName.value != null && fdName.value.length > 200){
		alert('<bean:message bundle="km-signature" key="signatureCategory.warn4"/>');
		return false;
	}
	var fdOrder = document.getElementsByName("fdOrder")[0];
	if(fdOrder.value == null || "" == fdOrder.value || /^[0-9]*[1-9][0-9]*$/.test(fdOrder.value)){
		if(fdOrder.value.length>10){
			alert('<bean:message bundle="km-signature" key="signatureCategory.warn3"/>');
			return false;
		}
	}else{
		alert('<bean:message bundle="km-signature" key="signatureCategory.warn2"/>');
		return false;
	}
	return true;
}
</script>
<html:form action="/km/signature/km_signature_category/kmSignatureCategory.do" onsubmit="return mymethod(this);">
<div id="optBarDiv">
	<c:if test="${kmSignatureCategoryForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.kmSignatureCategoryForm, 'update');">
	</c:if>
	<c:if test="${kmSignatureCategoryForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.kmSignatureCategoryForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.kmSignatureCategoryForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="km-signature" key="table.signatureCategory"/></p>
<center>
<table width="95%" class="tb_normal">
	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_body.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmSignatureCategoryForm" />
		<c:param name="fdModelName" value="${param.fdModelName}" />
		<c:param name="requestURL" value="km/signature/km_signature_category/kmSignatureCategory.do?method=add" />
	</c:import> 
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
</html:form>
<script type="text/javascript">
	$KMSSValidation();
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>