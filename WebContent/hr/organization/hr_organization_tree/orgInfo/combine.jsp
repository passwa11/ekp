<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/staffingLevel.css" />
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/reset.css" />
		<script type="text/javascript">
			seajs.use(['theme!form']);
			Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|data.js", null, "js");
		</script>
		<style>
			.txtstrong{
			color:red!important;
			}
		</style>			
	</template:replace>
	<template:replace name="content">
	<html:form action="hr/organization/hr_organization_element/hrOrganizationElement.do">
		<html:hidden property="fdId"/>
		<div class="hr-org-combine" style="display:none;">
			<div class="hr-org-combine-title hr-dialog-tip"><i></i>
				${ lfn:message('hr-organization:hr.organization.info.tip.0') }
			</div>
			<div class="hr-org-combine-cont">
			<table width="100%">
				<tr>
					<td>
					${ lfn:message('hr-organization:hr.organization.info.currentOrg') }
					</td>
					<td>
						<span>${param.curOrg }</span></span>
						<input type="hidden" name="currOrgId" value="${param.curOrgId }"/>
					</td>
				</tr>
				<tr>
					<td>${ lfn:message('hr-organization:hrOrganizationPost.job.num') }</td>
					<td class="person-info-number">${param.onpostNum }</td>

				</tr>
				<tr>
					<td>${ lfn:message('hr-organization:hr.organization.info.mergingOrg.after') }</td>
					<td class="newAddress ">
						<xform:address required="true" orgType="ORG_TYPE_ORGORDEPT" isHrAddress="true" propertyName="newOrgName" showStatus="edit" propertyId="newOrgId"></xform:address>
					</td>
				</tr>
			</table>
			</div>
		</div>
	</html:form>
	<script>
	seajs.use(['lui/dialog','lang!hr-organization'],function(dialog,lang){
		var loading = dialog.loading();
		var personNumUrl =Com_Parameter.ContextPath+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=findPersonList&queryPerson=${param.curOrgId }&IsHrOrg=true"
		$.ajax(personNumUrl,{
			dataType:'json',
			type:'post',
			success:function(data){
				if(data&&data.page){
					$(".person-info-number").text(data.page.totalSize);
					$(".hr-org-combine").show();
					loading.hide();
				}
			},error:function(data){
				
			}
		})
		window.orgEditSubmit=function (){
			var _validator = $KMSSValidation(document.forms['hrOrganizationElement']);
			 var url = Com_Parameter.ContextPath+"hr/organization/hr_organization_element/hrOrganizationElement.do?method=mergeOrg"
			 if(_validator.validate()){
				 $.ajax(url,{
					 data:$("form[name=hrOrganizationElementForm]").serializeArray(),
					 dataType:'json',
					 type:"post",
					 success:function(data){
						if(data['flag']==true||data['flag']=='true'){
							dialog.success(lang['hr.organization.info.tip.1']);
							window.$dialog.hide("success");
						}else{
							dialog.alert(data['msg']);
						}
					 },
					 error:function(data){
						 dialog.failure();
					 }
				 })				 
			 }

		}		
	})

	</script>	
	</template:replace>
</template:include>
