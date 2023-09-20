<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
	
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/lib/form.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/organization.css">
	</template:replace>
	<template:replace name="content">
		<script src="../resource/weui_switch.js"></script>
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<div class="hr_contract_sign_wrap">
			<html:form action="/hr/organization/hr_organization_org/hrOrganizationOrg.do">
				<div class="lui_hr_tb_simple_wrap">
					<table class="tb_simple lui_hr_tb_simple">
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdNo"/>
							</td>
							<td>
								<xform:text property="fdNo" required="true" showStatus="edit" style="width:80%"></xform:text>
							</td>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdName"/>
							</td>
							<td>
								<xform:text property="fdName" showStatus="edit" style="width:80%"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdOrgType"/>
							</td>
							<td>
								<xform:select property="fdOrgType" showStatus="readOnly" value="1" style="width:95%">
									<xform:enumsDataSource enumsType="hr_organization_type" />
								</xform:select>
							</td>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.hbmParentOrg"/>
							</td>
							<td>
								<xform:address isHrAddress="true" propertyId="fdParentId" propertyName="fdParentName" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.hbmThisLeader"/>
							</td>
							<td>
								<xform:address isHrAddress="true" propertyId="fdThisLeaderId" propertyName="fdThisLeaderName" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
							</td>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdBranLeader"/>
							</td>
							<td>
								<xform:address isHrAddress="true" propertyId="fdBranLeaderId" propertyName="fdBranLeaderName" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdNameAbbr"/>
							</td>
							<td>
								<xform:text property="fdNameAbbr" showStatus="edit" style="width:80%"></xform:text>
							</td>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.docCreateTime"/>
							</td>
							<td>
								<xform:datetime property="fdCreateTime" dateTimeType="date" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdOrder"/>
							</td>
							<td>
								<xform:text property="fdOrder" showStatus="edit" style="width:80%"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdIsVirOrg"/>
							</td>
							<td>
								<%-- 是否启用--%>
		                        <html:hidden property="fdIsVirOrg" /> 
								<label class="weui_switch">
									<span class="weui_switch_bd" id="weui_switch_vir_org">
										<input type="checkbox" ${'true' eq hrOrganizationOrgForm.fdIsVirOrg ? 'checked' : '' } />
										<span></span>
										<small></small>
									</span>
									<span id="fdIsVirOrgText"></span>
								</label>
								<script type="text/javascript">
									function setFdIsVirOrgText(status) {
										if(status) {
											$("#fdIsVirOrgText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.true" />');
										} else {
											$("#fdIsVirOrgText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.false" />');
										}
									}
									$("#weui_switch_vir_org :checkbox").on("click", function() {
										var status = $(this).is(':checked');
										$("input[name=fdIsVirOrg]").val(status);
										setFdIsVirOrgText(status);
									});
									setFdIsVirOrgText('${hrOrganizationOrgForm.fdIsVirOrg}');
								</script>
							</td>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdIsCorp"/>
							</td>
							<td>
								<%-- 是否启用--%>
		                        <html:hidden property="fdIsCorp" /> 
								<label class="weui_switch">
									<span class="weui_switch_bd" id="weui_switch_corp">
										<input type="checkbox" ${'true' eq hrOrganizationOrgForm.fdIsCorp ? 'checked' : '' } />
										<span></span>
										<small></small>
									</span>
									<span id="fdIsCorpText"></span>
								</label>
								<script type="text/javascript">
									function setFdIsCorpText(status) {
										if(status) {
											$("#fdIsCorpText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.true" />');
										} else {
											$("#fdIsCorpText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.false" />');
										}
									}
									$("#weui_switch_corp :checkbox").on("click", function() {
										var status = $(this).is(':checked');
										$("input[name=fdIsCorp]").val(status);
										setFdIsCorpText(status);
									});
									setFdIsCorpText('${hrOrganizationOrgForm.fdIsCorp}');
								</script>
							</td>
						</tr>
						
					</table>
				</div>
				<html:hidden property="fdId" />
				<html:hidden property="method_GET" />
			</html:form>
		</div>
		<div class="lui_hr_footer_btnGroup">
			<ui:button text="${lfn:message('button.ok') }" onclick="_submit();"></ui:button>
            <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
        </div>
	</template:replace>
</template:include>
<script>
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("form.js");
	Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/organization/hr_organization_org/", 'js', true);
	Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/hr/organization/resource/js/", 'js', true);
	var _validation = $KMSSValidation();
		
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		window._submit = function(){
			if ($KMSSValidation().validate()) {
				var url = '${LUI_ContextPath}/hr/organization/hr_organization_org/hrOrganizationOrg.do?method=save';
				$.post(url, $(document.hrOrganizationOrgForm).serialize(),function(data){
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