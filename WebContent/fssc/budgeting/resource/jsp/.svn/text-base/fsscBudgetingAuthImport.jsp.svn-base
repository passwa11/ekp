<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog" sidebar="auto">
	<template:replace name="content">
		<html:form action="/fssc/budgeting/fssc_budgeting_approval_auth/fsscBudgetingApprovalAuth.do" method="post" enctype="multipart/form-data">
		<script>
		</script>
		<p class="txttitle">${lfn:message('fssc-budgeting:message.import.title') }</p>
		<table class="tb_normal" width="95%">
			<tr>
				<td width="25%" class="td_normal_title">${lfn:message('fssc-budgeting:message.table.label.selectFile') }</td>
				<td width="75%">
					<input type="file" name="fdFile">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<ui:button text="${lfn:message('button.submit') }" onclick="submit()"/>
				</td>
			</tr>
		</table>
		<script>
			seajs.use(['lui/dialog'],function(dialog){
				$(document).ready(function(){
					if("budgeting"=="${lfn:escapeHtml(param.type)}"){
						setTimeout(function(){
							$("form").attr("action","${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do");
						},1000);	
					}
				});
				window.submit = function(){
					var fdFile = $("[name=fdFile]").val();
					if(!fdFile){
						dialog.alert("${lfn:message('fssc-budgeting:message.import.file.tip') }");
						return;
					}
					window.parent.dialog=$dialog;
					Com_Submit(document.forms[0],'saveImport');
				}
			});
		</script>
		</html:form>
	</template:replace>
</template:include>
