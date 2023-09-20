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
			<html:form action="/hr/organization/hr_organization_rank/hrOrganizationRank.do">
				<div class="lui_hr_tb_simple_wrap">
					<table class="tb_simple lui_hr_tb_simple trlineheight40 newTableTop">
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationRank.fdGrade"/>
							</td>
							<td class="newHtmlSelect">
							     <xform:select property="fdGradeId" htmlElementProperties="id='fdGradeId'" showStatus="edit" required="true">
                                     <xform:beanDataSource serviceBean="hrOrganizationGradeService" selectBlock="fdId,fdName" />
                                 </xform:select>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationRank.fdName"/>
							</td>
							<td class="newInput">
								<xform:text property="fdName" showStatus="edit" style="width:80%" required="true" validators="uniqueName afterGrade" 
								htmlElementProperties="placeholder='${lfn:message('hr-organization:hr.organization.info.rant.placeholder.name') }'"
								/>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
							${lfn:message('hr-organization:hrOrganizationGrade.fdWeight') }
							</td>
							<td class="newInput">
								<xform:text property="fdWeight" required="true"></xform:text>
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
	var afterGradeValidators ={
			'afterGrade':{
				error:"${lfn:message('hr-organization:hr.organization.info.rant.tip.1') }",
				test:function(value){
					var fdGradeId=$("#fdGradeId option:selected").val();
					console.log(fdGradeId);
					if(!fdGradeId){
						return false;
					}else{
						return true;
					}
				}
			}
	}
	var NameValidators = {
			'uniqueName' : {
				error : "${lfn:message('hr-organization:hr.organization.info.rant.tip.2') }",
				test : function (value) {
					var result = null;
					var fdId = '${hrOrganizationRankForm.fdId}';
					var fdGradeId=$("#fdGradeId option:selected").val();
					$.ajax({
				        type: "post",
				        url: "${LUI_ContextPath}/hr/organization/hr_organization_rank/hrOrganizationRank.do?method=checkNameUnique",
				        data: {"fdName":value, "fdId":fdId,"fdGradeId":fdGradeId},
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
	_validation.addValidators(afterGradeValidators);	
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		window._submit = function(){
			if ($KMSSValidation().validate()) {
				var method_GET = '${hrOrganizationRankForm.method_GET}';
				var url = '${LUI_ContextPath}/hr/organization/hr_organization_rank/hrOrganizationRank.do?method=save';
				if(method_GET == 'edit'){
					url = '${LUI_ContextPath}/hr/organization/hr_organization_rank/hrOrganizationRank.do?method=update';
				}
				$.post(url, $(document.hrOrganizationRankForm).serialize(),function(data){
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