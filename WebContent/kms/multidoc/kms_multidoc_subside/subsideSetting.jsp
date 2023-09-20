<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.view">
<template:replace name="content">
<html:form action="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do">
    <input type="hidden" name="fdId" value="${HtmlParam.fdId }"/>
    <input type="hidden" name="serviceName" value="${HtmlParam.serviceName }"/>
    <input type="hidden" name="userSetting" value="1"/>
    <table class="tb_normal" width="100%">
    	<!-- 存放路径 -->
    	<tr>
			<td class="td_normal_title" width=35%>
				<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.category"/>
			</td>
			<td colspan="3">
	         <xform:dialog subject="${lfn:message('kms-multidoc:kms.knowledge.subside.category') }"  required="true"  propertyId="categoryId" propertyName="categoryName" showStatus="edit" style="width:220px;">
            	modifyMultidocCate();
            </xform:dialog>
			</td>
		</tr>
		<!-- 审批意见 -->
		<tr>
			<td class="td_normal_title" width=35%>
				<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.Approval"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdSaveApproval" 
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<ui:button text="${lfn:message('button.submit') }" onclick="submitSubside()"></ui:button>
				<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
			</td>
		</tr>
    </table>
</html:form>
</template:replace>
</template:include>
<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|validation.js|plugin.js|validation.jsp");
function modifyMultidocCate() {

	seajs
			.use(
					[ 'lui/dialog' ],
					function(dialog, env) {

						dialog
								.simpleCategory({
									modelName : 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
									authType : 0,
									idField : 'categoryId',
									nameField : 'categoryName',
									mulSelect : false,
									canClose : true,
									notNull : false,
									___urlParam : {
										'fdTemplateType' : '1,3'
									}
								});
					})

}

function submitSubside(){
	var validator = $KMSSValidation(document.kmsMultidocSubsideForm);
	if(!validator.validate())
		return;
	var url = '${LUI_ContextPath}/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc';
	var formObj = document.getElementsByName('kmsMultidocSubsideForm')[0];
	var dia =window.parent.document.getElementById('lui-id-111');
	if(dia!=null){
		dia.style.display='none';
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url:url,
		data:$(formObj).serialize(),
		success:function(result){
			seajs.use(['lui/dialog'],function(dialog){
				if(!result.status){
					dialog.alert(result.errorMessage);
					return ;
				}
				dialog.confirm('<bean:message key="kmsMultidocSubside.isTurnTo" bundle="kms-multidoc"/>',function(value){
					if(result.id){
						if(value == true){
							var url = "${LUI_ContextPath}/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId="+result.id;
							window.open(url,"_blank");
							top.location.reload(true);
						}else{
							top.location.reload(true);
						}
					}else{
						alert("沉淀失败");
					}
					
				});
			});
		},
		error:function(){
			alert("沉淀失败");
		}
	});
	//Com_DisableFormOpts();
}
</script>