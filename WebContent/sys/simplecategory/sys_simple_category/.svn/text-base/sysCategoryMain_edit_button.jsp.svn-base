<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<script language="JavaScript">
	function submitForm(method) {
	    // 判断描述字符长度
	    var fdDesc = document.getElementsByName("fdDesc");
	    if(fdDesc.length > 0) {
		    var newvalue = fdDesc[0].value.replace(/[^\x00-\xff]/g, "***");
			if(newvalue.length > 1500) {
				var msg = '<bean:message key="errors.maxLength"/>'.replace("{0}", '<bean:message bundle="sys-simplecategory" key="sysSimpleCategory.fdDesc"/>').replace("{1}", 1500);
				alert(msg);
				return;
			}
	    }
	    
		Com_Submit(document.forms['${JsParam.formName}'], method);
	}
</script>
<c:set var="sysSimpleCategoryMain" value="${requestScope[param.formName]}" />
	<div id="optBarDiv"><c:if
		test="${sysSimpleCategoryMain.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="submitForm('update');" />
	</c:if> <c:if test="${sysSimpleCategoryMain.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="submitForm('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="submitForm('saveadd');">
	</c:if> <input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>

