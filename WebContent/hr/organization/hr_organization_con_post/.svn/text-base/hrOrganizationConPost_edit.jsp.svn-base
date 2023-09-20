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
			<html:form action="/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=save">
				<div class="lui_hr_tb_simple_wrap">
					<table class="tb_simple lui_hr_tb_simple trlineheight40">
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationConPost.fdType"/>
							</td>
							<td> 
								<bean:message bundle="hr-organization" key="enums.office_type.2"/> 
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationConPost.fdPerson"/>
							</td>
							<td class="newAddress">
							     <xform:address required="true" propertyId="fdPersonInfoId" propertyName="fdPersonInfoName" orgType="ORG_TYPE_PERSON" isHrAddress="true" showStatus="edit" style="width:95%;" />
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationConPost.fdStartTime"/>
							</td>
							<td class="newDate">
								<xform:datetime property="fdEntranceBeginDate" style="width:80%;" dateTimeType="date" required="true" showStatus="edit" 
								htmlElementProperties="placeholder='${ lfn:message('hr-organization:hr.organization.info.tip.29') }'"/>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationConPost.fdEndTime"/>
							</td>
							<td class="newDate">
								<xform:datetime property="fdEntranceEndDate" validators="after" style="width:80%;" dateTimeType="date" required="false" showStatus="edit" 
								htmlElementProperties="placeholder='${ lfn:message('hr-organization:hr.organization.info.tip.30') }'"/>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationConPost.fdPost"/>
							</td>
							<td class="newAddress">
								<xform:address propertyId="fdHrOrgPostId" propertyName="fdHrOrgPostName" orgType="ORG_TYPE_POST" isHrAddress="true" showStatus="edit" style="width:95%;" required="true"/>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationConPost.fdDept"/>
							</td>
							<td class="newAddress">
								<xform:address required="true" propertyId="fdHrOrgDeptId" propertyName="fdHrOrgDeptName" orgType="ORG_TYPE_DEPT" isHrAddress="true" showStatus="edit" style="width:95%;" />
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationConPost.fdDuty"/>
							</td>
							<td class="newSelect">
								<xform:dialog propertyId="fdStaffingLevelId" propertyName="fdStaffingLevelName" showStatus="edit" style="width:50%;" htmlElementProperties="placeholder='${ lfn:message('hr-organization:hr.organization.info.tip.31') }'">
                                    dialogSelect(false,'hr_organization_staffing_level','fdStaffingLevelId','fdStaffingLevelName');
                                </xform:dialog>
							</td>
						</tr>
					</table>
				</div>
				<html:hidden property="fdId" />
				<html:hidden property="fdType" value="2"/>
				<html:hidden property="fdStatus" value="1"/>
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
	Com_IncludeFile("form.js");
	Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/organization/hr_organization_con_post/", 'js', true);
	Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/hr/organization/resource/js/", 'js', true);
	var _validation = $KMSSValidation();
	_validation.addValidator("conPostTime","${ lfn:message('hr-organization:hr.organization.info.tip.32') }",function(v, e, o){
		var startTime = $("input[name=fdEntranceBeginDate]").val();
		var endTime = $("input[name=fdEntranceEndDate]").val();
		var startDate = new Date(startTime).getTime();
		var endDate = new Date(endTime).getTime();
		if((endDate-startDate)>0){
			return true;
		}else{
			return false;
		}
	})	
	
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		
		window.check = function(){
			var fdHrOrgDeptId = $("input[name=fdHrOrgDeptId]").val();
			var fdHrOrgPostId = $("input[name=fdHrOrgPostId]").val();
			var fdPersonInfoId = $("input[name=fdPersonInfoId]").val();
			var fdId = $("input[name=fdId]").val();
			var result = null;
			$.ajax({
		        type: "post",
		        url: "${LUI_ContextPath}/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=checkUnique",
		        data: {"fdId":fdId, "fdHrOrgDeptId":fdHrOrgDeptId, "fdHrOrgPostId":fdHrOrgPostId, "fdPersonInfoId":fdPersonInfoId},
		        async : false,
		        dataType: "json",
		        success: function (data ,textStatus, jqXHR){
		        	result = data.result;
		        	if(!result){
		        		dialog.failure("${ lfn:message('hr-organization:hr.organization.info.tip.33') }");
		        	}
		        }
	    	 });
			return result;
		}
		
		
		window.validateRole = function(){
			var fdHrOrgDeptId = $("input[name=fdHrOrgDeptId]").val();
			var result = null;
			$.ajax({
		        type: "post",
		        url: "${LUI_ContextPath}/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=validateConPostRole",
		        data: {"fdHrOrgDeptId":fdHrOrgDeptId},
		        async : false,
		        dataType: "json",
		        success: function (data ,textStatus, jqXHR){
		        	result = data.result;
		        	if(!result){
		        		dialog.failure("${ lfn:message('hr-organization:hr.organization.info.tip.34') }");
		        	}
		        }
	    	 });
			return result;
		}
		
		
		window._submit = function(){
			_validation.removeElements($("input[name='fdEntranceEndDate']")[0]);
			var endTime = $("input[name=fdEntranceEndDate]").val();
			if(null != endTime && endTime != ""){
				_validation.addElements($("input[name='fdEntranceEndDate']")[0],"conPostTime after");
			}
			if ($KMSSValidation().validate() && check() && validateRole()) {
				var method_GET = '${hrOrganizationPostSeqForm.method_GET}';
				var url = '${LUI_ContextPath}/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=save';
				if(method_GET == 'edit'){
					url = '${LUI_ContextPath}/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=update';
				}
				$.post(url, $(document.hrStaffTrackRecordForm).serialize(),function(data){
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