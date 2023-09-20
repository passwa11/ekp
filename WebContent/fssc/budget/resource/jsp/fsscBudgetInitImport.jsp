<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog" sidebar="auto">
	<template:replace name="content">
		<html:form action="/fssc/budget/fssc_budget_main/fsscBudgetMain.do" method="post" enctype="multipart/form-data">
		<script>
		</script>
		<p class="txttitle">${lfn:message('fssc-budget:message.import.title') }</p>
		<table class="tb_normal" width="95%">
			<tr>
				<td width="25%" class="td_normal_title">${lfn:message('fssc-budget:message.table.label.selectFile') }</td>
				<td width="75%">
					<input type="file" name="fdFile">
					<img src="${KMSS_Parameter_ResPath}style/common/images/loading.gif" border="0" id="loadingImg" align="bottom" style="display: none;"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<ui:button text="${lfn:message('fssc-budget:fsscBudget.button.downTemplate') }" onclick="downTemplate()"/>
					<ui:button text="${lfn:message('button.submit') }" style="margin-left:10px;" onclick="submit()"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="left">
					<div style="margin-left:15px;">
						${lfn:message('fssc-budget:message.budget.init.tips')}
					</div>
				</td>
			</tr>
		</table>
		<script>
			seajs.use(['lui/dialog'],function(dialog){
				window.submit = function(){
					var fdFile = $("[name=fdFile]").val();
					if(!fdFile){
						dialog.alert("${lfn:message('fssc-budget:message.import.file.tip') }");
						return;
					}
					document.getElementById("loadingImg").style.display = "";
					Com_Submit(document.forms[0],'initSaveImport');
				}
				window.downTemplate=function(){
					var url='${LUI_ContextPath}/fssc/budget/resource/jsp/fsscBudgetInit_template.xls';
					window.open(url,"_blank");
				}
			});
		</script>
		</html:form>
	</template:replace>
</template:include>
