<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<table class="tb_normal lbpmToolsTable" width="90%">
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.handler.beforeName" /></td>
		<td width="80%">
			<input type="hidden" name="handlerNameBeforeId">
			<input type="text" name="handlerNameBeforeName" subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.handler.beforeName') }" validate="required" style="width:80%" class="inputSgl">
			<span class=txtstrong>*</span>
			<a href="#" onclick="clickAddressBefore(this)"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.handler.afterName" /></td>
		<td width="80%">
			<input type="hidden" name="handlerNameAfterId">
			<input type="text" name="handlerNameAfterName" subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.handler.afterName') }" validate="required"  style="width:80%" class="inputSgl">
			<span class=txtstrong>*</span>
			<a href="#" onclick="clickAddressAfter(this)"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.range" /></td>
		<td width="80%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="handlerNameCateIde"/>
			<input type="hidden" name="handlerNameCateNamee"/>
			<input type="hidden" name="handlerNameModelNamee"/>
			<input type="hidden" name="handlerNameModuleNamee"/>
			<input type="hidden" name="handlerNameTemplateIde"/>
			<input type="hidden" name="handlerNameTemplateNamee"/>
			<input type="hidden" name="handlerNameTemplateIe">
			<input type="text" name="handlerNameTemplateNamd" readOnly style="width:80%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.rangeDesc') }">
			<span class=txtstrong>*</span>
			<a href="javascript:void(0);" onclick="lbpmToolsSelectSubFlow('handlerNameTemplateIe','handlerNameTemplateNamd','handlerNameCateIde','handlerNameCateNamee','handlerNameModelNamee','handlerNameModuleNamee','handlerNameTemplateIde','handlerNameTemplateNamee');" validate="required" style="color:#666"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			</br>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;">
			<br>
		 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.search') }" style="width:150px" onclick="searchHandlerName();"></ui:button>
		</td>
	</tr>
</table>
<br>
<script type="text/javascript">
	function clickAddressBefore(res){
		var idField = $(res).parents("tr").eq(0).find("input[name='handlerNameBeforeId']")[0];
		var nameField = $(res).parents("tr").eq(0).find("input[name='handlerNameBeforeName']")[0];
		Dialog_Address(false, idField, nameField, null, "ORG_TYPE_PERSON|ORG_TYPE_POST");
	}
	function clickAddressAfter(res){
		var idField = $(res).parents("tr").eq(0).find("input[name='handlerNameAfterId']")[0];
		var nameField = $(res).parents("tr").eq(0).find("input[name='handlerNameAfterName']")[0];
		Dialog_Address(false, idField, nameField, null, "ORG_TYPE_PERSON|ORG_TYPE_POST");
	}
seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.searchHandlerName = function() {
		var beforeId = $("input[name='handlerNameBeforeId']");
		var afterId = $("input[name='handlerNameAfterId']");
		var beforeName = $("input[name='handlerNameBeforeName']");
		var afterName = $("input[name='handlerNameAfterName']");
		//调用校验框架校验必填
		var _elements = new Elements()
		_elements.serializeElement(beforeName[0]);
		_elements.serializeElement(afterName[0]);
		var beforeValidate = lbpm_tools_validation._doValidateElement(new Element(beforeName[0]),lbpm_tools_validation.getValidator(beforeName.attr("validate")));
		var afterValidate = lbpm_tools_validation._doValidateElement(new Element(afterName[0]),lbpm_tools_validation.getValidator(afterName.attr("validate")));
		if(beforeValidate && afterValidate){
			window.del_load = dialog.loading();
			$("#lbpmToolDiv").show();
			var myIframe=LUI("lbpmToolIframe");
			myIframe.src="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/handler_name/lbpm_tool_handler_name_list.jsp?cateIds="+$("input[name='handlerNameCateIde']").val()+"&modelNames="+$("input[name='handlerNameModelNamee']").val()+"&templateIds="+$("input[name='handlerNameTemplateIde']").val()+"&beforeName="+encodeURIComponent(beforeName.val())+"&beforeId="+encodeURIComponent(beforeId.val())+"&updateName="+encodeURIComponent(afterName.val())+"&updateId="+encodeURIComponent(afterId.val());
			myIframe.reload();
		}
	}
});
</script>