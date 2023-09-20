<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit">
	<template:replace name="content">
<html:form action="/hr/organization/hr_org_file_author/hrOrgFileAuthor.do">
 <div style="margin-top:25px">
<p class="txttitle"><bean:message bundle="hr-staff" key="hrStaffFileAuthor.setting"/></p>
<center>
<table class="tb_normal" width=85%> 
	<tr>
		<td class="td_normal_title" width=15%> 
			${lfn:message('hr-organization:hr.organization.hrStaffFileAuthor')}
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" showStatus="noShow" />
			<xform:address subject="${ lfn:message('hr-staff:hrStaffFileAuthor.view') }" propertyId="authorDetailIds" propertyName="authorDetailNames" showStatus="edit" mulSelect="true" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" textarea="true" style="width:85%" />
		</td>
	</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
  <ui:button text="${ lfn:message('button.save') }" onclick="_submit();"></ui:button>
</div>
</center>
</div>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("dialog.js");
	$KMSSValidation();
	
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		
		window.check = function(){
			var result = null;
			var fdName = "${hrOrgFileAuthorForm.fdName}"; 
			$.ajax({
		        type: "post",
		        url: "${LUI_ContextPath}/hr/organization/hr_org_file_author/hrOrgFileAuthor.do?method=checkStaffFileAuthor",
		        data: {"fdId":fdName},
		        async : false,
		        dataType: "json",
		        success: function (data ,textStatus, jqXHR){
		        	result = data.result;
		        }
	    	 });
			return result;
		}
		
		window._submit = function(){
			if (check()) {
				Com_Submit(document.hrOrgFileAuthorForm, 'update');
			}else{
				dialog.confirm("${lfn:message('hr-organization:hr.organization.info.tip.36')}", function(isOk) {
                    if(isOk) {
                    	Com_Submit(document.hrOrgFileAuthorForm, 'update');
                    }
                });
			}
		}
	});
	
</script>
</html:form>
	</template:replace>
</template:include>