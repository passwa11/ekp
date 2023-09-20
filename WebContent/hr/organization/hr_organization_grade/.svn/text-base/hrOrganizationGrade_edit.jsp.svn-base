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
			<html:form action="/hr/organization/hr_organization_grade/hrOrganizationGrade.do?">
				<div class="lui_hr_tb_simple_wrap"> 
					<table class="tb_simple lui_hr_tb_simple trlineheight40">
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationGrade.fdName"/>
							</td>
							<td class="newInput">
								<xform:text property="fdName" showStatus="edit" style="width:80%" validators="uniqueName" 
								htmlElementProperties="placeholder='${lfn:message('hr-organization:hr.organization.info.grade.placeholder.name')}'"/>
							</td>
						</tr>
						<%-- <tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationGrade.fdCode"/>
							</td>
							<td class="newInput">
								<xform:text property="fdCode" validators="uniqueCode" style="width:95%" showStatus="edit" required="true"/>
							</td>
						</tr> --%>
						<tr>
							<td class="tr_normal_title">${lfn:message('hr-organization:hrOrganizationGrade.fdWeight')}</td>
							<td>
								<xform:text property="fdWeight" showStatus="edit" required="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationGrade.fdDesc"/>
							</td>
							<td class="newTextarea">
								<xform:textarea property="fdDesc" showStatus="edit" style="width:80%" />
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
	var CodeValidators = {
			'uniqueCode' : {
				error : "${lfn:message('hr-organization:hr.organization.info.tip.25')}",
				test : function (value) {
					var result = null;
					var fdId = '${hrOrganizationGradeForm.fdId}';
					$.ajax({
				        type: "post",
				        url: "${LUI_ContextPath}/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=checkCodeUnique",
				        data: {"fdCode":value, "fdId":fdId},
				        async : false,
				        dataType: "json",
				        success: function (data ,textStatus, jqXHR){
				        	result = data.result;
				        }
			    	 });
					return result;
			    }
			}
		};
	
	var NameValidators = {
			'uniqueName' : {
				error : "${lfn:message('hr-organization:hr.organization.info.tip.26')}",
				test : function (value) {
					var result = null;
					var fdId = '${hrOrganizationGradeForm.fdId}';
					$.ajax({
				        type: "post",
				        url: "${LUI_ContextPath}/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=checkNameUnique",
				        data: {"fdName":value, "fdId":fdId},
				        async : false,
				        dataType: "json",
				        success: function (data ,textStatus, jqXHR){
				        	result = data.result;
				        }
			    	 });
					return result;
			    }
			}
		};
	
	_validation.addValidators(NameValidators);
	_validation.addValidators(CodeValidators);
	
		
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		window._submit = function(){
			if ($KMSSValidation().validate()) {
				var method_GET = '${hrOrganizationGradeForm.method_GET}';
				var url = '${LUI_ContextPath}/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=save';
				if(method_GET == 'edit'){
					url = '${LUI_ContextPath}/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=update';
				}
				$.post(url, $(document.hrOrganizationGradeForm).serialize(),function(data){
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