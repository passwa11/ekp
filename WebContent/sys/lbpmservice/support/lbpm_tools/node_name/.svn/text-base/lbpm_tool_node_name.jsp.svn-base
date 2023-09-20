<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<table class="tb_normal lbpmToolsTable" width="90%">
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.nodeName.beforeName" /></td>
		<td width="80%">
			<input type="text" name="nodeNameBeforeName" subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeName.beforeName') }" validate="required" style="width:98%" class="inputSgl"><span class=txtstrong>*</span>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.nodeName.afterName" /></td>
		<td width="80%">
			<input type="text" name="nodeNameAfterName" subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeName.afterName') }" validate="required" style="width:98%" class="inputSgl"><span class=txtstrong>*</span>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.range" /></td>
		<td width="80%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="nodeNameCateIds"/>
			<input type="hidden" name="nodeNameCateNames"/>
			<input type="hidden" name="nodeNameModelNames"/>
			<input type="hidden" name="nodeNameModuleNames"/>
			<input type="hidden" name="nodeNameTemplateIds"/>
			<input type="hidden" name="nodeNameTemplateNames"/>
			<input type="hidden" name="nodeNameTemplateId">
			<input type="text" name="nodeNameTemplateName" readOnly style="width:88%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.rangeDesc') }">
			<a href="javascript:void(0);" onclick="lbpmToolsSelectSubFlow('nodeNameTemplateId','nodeNameTemplateName','nodeNameCateIds','nodeNameCateNames','nodeNameModelNames','nodeNameModuleNames','nodeNameTemplateIds','nodeNameTemplateNames');" style="color:#666"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			</br>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;">
			<br>
		 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.search') }" style="width:150px" onclick="searchNodeName();"></ui:button>
		</td>
	</tr>
</table>
<br>
<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.searchNodeName = function() {
		var beforeName = $("input[name='nodeNameBeforeName']");
		var afterName = $("input[name='nodeNameAfterName']");
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
			myIframe.src="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/node_name/lbpm_tool_node_name_list.jsp?cateIds="+$("input[name='nodeNameCateIds']").val()+"&modelNames="+$("input[name='nodeNameModelNames']").val()+"&templateIds="+$("input[name='nodeNameTemplateIds']").val()+"&nodeName="+encodeURIComponent(beforeName.val())+"&updateName="+encodeURIComponent(afterName.val());
			myIframe.reload();
		}
	}
});
</script>