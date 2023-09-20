<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog" sidebar="auto">
	<template:replace name="content">
		<html:form action="/fssc/budget/fssc_budget_main/fsscBudgetMain.do">
		<script>
			Com_IncludeFile("plugin.js", "${LUI_ContextPath}/resource/js/", 'js', true);
			Com_IncludeFile("security.js");
		    Com_IncludeFile("domain.js");
		    Com_IncludeFile("form.js");
			Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_main/", 'js', true);
			Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_main/", 'js', true);
		</script>
		<p class="txttitle">${lfn:message('fssc-budget:message.table.label.selectCompany') }</p>
		<table class="tb_normal" width="95%">
			<tr>
				<td width="25%" class="td_normal_title">${lfn:message('fssc-budget:message.transfer.date') }</td>
				<td width="75%">
					<kmss:period property="fdStartMonth" periodTypeValue="1"/>
						&nbsp;&nbsp;${lfn:message('fssc-budget:message.transfer.date.to') }&nbsp;&nbsp;
					<kmss:period property="fdEndMonth" periodTypeValue="1"/>
				</td>
			</tr>
			<tr>
				<td width="25%" class="td_normal_title">${lfn:message('fssc-budget:message.table.label.selectCompany') }</td>
				<td width="75%">
					<xform:dialog textarea="true" showStatus="edit" style="width:85%" propertyName="fdCompanyNames" propertyId="fdCompanyIds">
						dialogSelect(true,'eop_basedata_finanac_company_fdCompany','fdCompanyIds','fdCompanyNames');
					</xform:dialog>
					<br><span class="com_help" style="color: red;">${lfn:message('fssc-budget:fsscBudgetMain.Transfer.tip')}</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><ui:button text="${lfn:message('button.submit') }" onclick="if(validateDate()){Com_Submit(document.fsscBudgetMainForm, 'transferBudget');}"/></td>
			</tr>
		</table>
		<script>
			seajs.use(['lui/dialog'],function(dialog){
				window.validateDate = function(){
					var fdCompanyIds = $("[name=fdCompanyIds]").val();
					var fdStartMonth=$("select[name='fdStartMonth']").val();
					var fdEndMonth=$("select[name='fdEndMonth']").val();
					var startMonth=fdStartMonth.substring(1,7);
					var endMonth=fdEndMonth.substring(1,7);
					if(!(endMonth-startMonth==1||endMonth-startMonth==89)){//1是同一年相邻月，89是跨年相邻
						dialog.alert("${lfn:message('fssc-budget:message.month.adjacent') }");
						return;
					}
					return true;
				}
			});
		</script>
		</html:form>
	</template:replace>
</template:include>
