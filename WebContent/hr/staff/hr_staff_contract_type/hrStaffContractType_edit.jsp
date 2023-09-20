<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<html:form action="/hr/staff/hr_staff_contract_type/hrStaffContractType.do" > 
	<html:hidden property="method_GET"/> 
	<div id="optBarDiv">
		<c:if test="${hrStaffContractTypeForm.method_GET=='edit'}">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.hrStaffContractTypeForm, 'update');">
		</c:if>
		<c:if test="${hrStaffContractTypeForm.method_GET=='add'}">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="Com_Submit(document.hrStaffContractTypeForm, 'save');">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="Com_Submit(document.hrStaffContractTypeForm, 'saveadd');">
		</c:if>
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffContractType"/></p>
	<center>
		<html:hidden property="fdId"/>
		<table id="Label_Tabel" width=95%>
			<tr LKS_LabelName="基本信息">
				<td>
					<table class="tb_normal" width=95%> 
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message  bundle="hr-staff" key="hrStaffContractType.fdOrder"/>
							</td><td width=35%>
								<xform:text	property="fdOrder" style="width:90%;" />
							</td>
								<td class="td_normal_title" width=15%>
								<bean:message  bundle="hr-staff" key="hrStaffContractType.fdName"/>
							</td><td width=35%>
								<xform:text	property="fdName" validators="uniqueFdName" style="width:90%;" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 打印机制 -->
			<c:import url="/sys/print/include/sysPrintTemplate_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrStaffContractTypeForm" />
				<c:param name="fdKey" value="hrStaffPersonExperienceContract" />
				<c:param name="modelName" value="com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract"></c:param>
				<c:param name="templateModelName" value="com.landray.kmss.hr.staff.model.HrStaffContractType"></c:param>
				<c:param name="usePrintTemplate" value="是否启用打印模板"></c:param>
			</c:import>
		</table>
	</center>
</html:form>

<script>
	Com_IncludeFile("security.js");
	var _validation = $KMSSValidation(document.forms['hrStaffContractTypeForm']);
	var FdNameValidators = {
		'uniqueFdName': {
			error : "合同类型名称已使用,请重新输入!",
			test :  function(value){
					var result = fdNameCheckUnique("hrStaffContractTypeService",value);
					if (!result) 
						return false;
					return true;
			}
		}
	};
	
	_validation.addValidators(FdNameValidators);
		
	
	function fdNameCheckUnique(bean,fdName) {
		var fdId = document.getElementsByName("fdId")[0].value;
		var	url=encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" + bean + "&fdName=" + fdName + "&fdId=" + fdId);
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>