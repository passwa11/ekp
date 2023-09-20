<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/lib/form.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/organization.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/reset.css">			
	</template:replace>
	<template:replace name="content">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<div class="hr_contract_sign_wrap">
			<html:form action="/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do">
				<div class="lui_hr_tb_simple_wrap">
					<table class="tb_simple lui_hr_tb_simple trlineheight40">
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="sysOrganizationStaffingLevel.fdName"/>
							</td>
							<td class="newInput">
								<xform:text property="fdName" validators="uniqueName" showStatus="edit" style="width:80%" 
								htmlElementProperties="placeholder='${lfn:message('hr-organization:hr.organization.info.level.placeholder.name') }'"/>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationDuty.fdCode"/>
							</td>
							<td class="newInput">
								<xform:text property="fdLevel" style="width:95%" showStatus="edit" validators="min(1)"  
								htmlElementProperties="placeholder='${lfn:message('hr-organization:hr.organization.info.level.placeholder.level') }'"
								/>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="sysOrganizationStaffingLevel.fdDescription"/>
							</td>
							<td class="newTextarea">
								<xform:textarea property="fdDescription"  showStatus="edit" style="width:95%" />
							</td>
						</tr>
					</table>
				</div>
				<html:hidden property="fdId" />
				<html:hidden property="method_GET" />
			</html:form>
		</div>
<%-- 		<div class="lui_hr_footer_btnGroup">
			<ui:button text="${lfn:message('button.ok') }" onclick="_submit();"></ui:button>
            <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
        </div> --%>
	</template:replace>
</template:include>
<script>
	Com_IncludeFile("dialog.js");
	var _validation = $KMSSValidation();
	var NameValidators = {
			'uniqueName' : {
				error : "<bean:message key='sysOrganizationStaffingLevel.error.fdName.mustUnique' bundle='sys-organization' />",
				test : function (value) {
					var fdId = document.getElementsByName("fdId")[0].value;
					var result = checkNameUnique("sysOrganizationStaffingLevelService", value, fdId);
					if (!result) 
						return false;
					return true;
			      }
			}
 	};
	_validation.addValidators(NameValidators);
	//校验名称是否唯一
	function checkNameUnique(bean, fdName, fdId) {
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
				+ bean + "&fdName=" + fdName + "&fdId=" + fdId + "&date=" + new Date());
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
	
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		window._submit = function(){
			if ($KMSSValidation().validate()) {
				var method_GET = '${sysOrganizationStaffingLevelForm.method_GET}';
				var url = '${LUI_ContextPath}/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=save';
				if(method_GET == 'edit'){
					url = '${LUI_ContextPath}/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=update';
				}
				$.post(url, $(document.sysOrganizationStaffingLevelForm).serialize(),function(data){
				   if(data!=""){
					  	dialog.success('<bean:message key="return.optSuccess" />');
						setTimeout(function (){
							window.$dialog.hide("success");
						}, 1500);
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				})
			}
		}
	});
</script>