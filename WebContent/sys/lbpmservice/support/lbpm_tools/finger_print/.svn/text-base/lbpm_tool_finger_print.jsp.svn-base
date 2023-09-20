<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<table class="tb_normal lbpmToolsTable" width="90%">
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.nodeName" /></td>
		<td width="80%">
			<input type="text" name="fingerPrintNodeName" subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeName') }" style="width:98%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeNameDesc') }">
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.range" /></td>
		<td width="80%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="fingerPrintCateIds"/>
			<input type="hidden" name="fingerPrintCateNames"/>
			<input type="hidden" name="fingerPrintModelNames"/>
			<input type="hidden" name="fingerPrintModuleNames"/>
			<input type="hidden" name="fingerPrintTemplateIds"/>
			<input type="hidden" name="fingerPrintTemplateNames"/>
			<input type="hidden" name="fingerPrintTemplateId">
			<input type="text" name="fingerPrintTemplateName" readOnly style="width:88%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.rangeDesc') }">
			<a href="javascript:void(0);" onclick="lbpmToolsSelectSubFlow('fingerPrintTemplateId','fingerPrintTemplateName','fingerPrintCateIds','fingerPrintCateNames','fingerPrintModelNames','fingerPrintModuleNames','fingerPrintTemplateIds','fingerPrintTemplateNames');" style="color:#666"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			</br>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.fingerPrint" /></td>
		<td width="80%">
			<table style="width:100%;margin-left:-10px;">
				<tr>
					<td style="width:30%;"><ui:switch id="fingerPrintSwitch" property="isCanFingerPrint" disabledText="${ lfn:message('sys-lbpmservice-support:lbpmTools.fingerPrint.close') }" enabledText="${ lfn:message('sys-lbpmservice-support:lbpmTools.fingerPrint.open') }" checked="true" onValueChange="fingerPrintSwitch();"></ui:switch></td>
					<td style="word-wrap:break-word;word-break:break-all;"><span style="color:#aab2bd;" id="fingerPrintSwitchMsg"><kmss:message key="lbpmTools.fingerPrint.openMsg" bundle="sys-lbpmservice-support" /></span></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;">
			<br>
		 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.search') }" style="width:150px" onclick="searchFingerPrint();"></ui:button>
		</td>
	</tr>
</table>
<br>
<script type="text/javascript">
function fingerPrintSwitch(){
	if(LUI('fingerPrintSwitch').checkbox.is(':checked')){
		$("#fingerPrintSwitchMsg").text('<kmss:message key="lbpmTools.fingerPrint.openMsg" bundle="sys-lbpmservice-support" />');
	}else{
		$("#fingerPrintSwitchMsg").text('<kmss:message key="lbpmTools.fingerPrint.closeMsg" bundle="sys-lbpmservice-support" />');
	}
}

seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.searchFingerPrint = function() {
		window.del_load = dialog.loading();
		$("#lbpmToolDiv").show();
		var myIframe=LUI("lbpmToolIframe");
		myIframe.src="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/finger_print/lbpm_tool_finger_print_list.jsp?cateIds="+$("input[name='fingerPrintCateIds']").val()+"&modelNames="+$("input[name='fingerPrintModelNames']").val()+"&templateIds="+$("input[name='fingerPrintTemplateIds']").val()+"&nodeName="+encodeURIComponent($("input[name='fingerPrintNodeName']").val())+"&isCanFingerPrint="+$("input[name='isCanFingerPrint']").val();
		myIframe.reload();
	}
});
</script>