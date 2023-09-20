<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<html:form action="/sys/property/sys_property_category/sysPropertyCategory.do">
<div id="optBarDiv">
	<c:if test="${sysPropertyCategoryForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="editDoc(document.sysPropertyCategoryForm, 'update')">
	</c:if>
	<c:if test="${sysPropertyCategoryForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="editDoc(document.sysPropertyCategoryForm, 'save')">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="editDoc(document.sysPropertyCategoryForm, 'saveadd')">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-property" key="table.sysPropertyCategory"/></p>
 
<center>
	<table id="Label_Tabel" width="95%">

	<c:import url="/sys/simplecategory/include/sysCategoryMain_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="sysPropertyCategoryForm" />
		<c:param name="requestURL" value="sys/property/sys_property_category/sysPropertyCategory.do?method=add" />
		<c:param name="fdModelName" value="com.landray.kmss.sys.property.model.SysPropertyCategory" />
	</c:import>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
		var fdName = document.getElementsByName("fdName")[0].value;
		if (fdName) {
			return true;
		} else {
			alert('<bean:message key="sysPropertyCategory.valation.fdName.notNull" bundle="sys-property"/>');
			return false;
		}
	}
	
	function editDoc(formName, method){
		seajs.use(['lui/dialog'],function(dialog){
 			var fdId = "${sysPropertyCategoryForm.fdId}";
 			var fdNameEl = document.getElementsByName("fdName")[0];
 			var fdParentId = document.getElementsByName("fdParentId")[0].value;
 			var fdName = fdNameEl.value;
 			if(fdName == null || fdName == ""){
 				dialog.alert('<bean:message key="sysPropertyCategory.valation.fdName.notNull" bundle="sys-property"/>');
 				return;
 			}
			var url = "${LUI_ContextPath}/sys/property/sys_property_category/sysPropertyCategory.do?method=checkUnique";
			var data = {
					"fdId": fdId,
					"fdName": fdName,
					"fdParentId": fdParentId
				};
			$.ajax({
				url:url,
				type: "POST",
				async: false,
				data:data,
				success:function(data){
					if(data == "true"){
						dialog.alert('<bean:message key="sysPropertyCategory.valation.fdName.unique" bundle="sys-property"/>');
						fdNameEl.focus();
					} else {
					  Com_Submit(formName, method); 				
					}
				}
			})
		})
		

	}
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>