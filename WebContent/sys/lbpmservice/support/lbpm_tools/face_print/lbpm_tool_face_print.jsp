<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<table class="tb_normal lbpmToolsTable" width="90%">
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.nodeName" /></td>
		<td width="80%">
			<input type="text" name="facePrintNodeName" subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeName') }" style="width:98%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeNameDesc') }">
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.range" /></td>
		<td width="80%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="facePrintCateIds"/>
			<input type="hidden" name="facePrintCateNames"/>
			<input type="hidden" name="facePrintModelNames"/>
			<input type="hidden" name="facePrintModuleNames"/>
			<input type="hidden" name="facePrintTemplateIds"/>
			<input type="hidden" name="facePrintTemplateNames"/>
			<input type="hidden" name="facePrintTemplateId">
			<input type="text" name="facePrintTemplateName" readOnly style="width:88%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.rangeDesc') }">
			<a href="javascript:void(0);" onclick="lbpmToolsSelectSubFlow('facePrintTemplateId','facePrintTemplateName','facePrintCateIds','facePrintCateNames','facePrintModelNames','facePrintModuleNames','facePrintTemplateIds','facePrintTemplateNames');" style="color:#666"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			</br>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.facePrint" /></td>
		<td width="80%">
			<table style="width:100%;margin-left:-10px;">
				<tr>
					<td style="width:30%;"><ui:switch id="facePrintSwitch" property="isCanFacePrint" disabledText="${ lfn:message('sys-lbpmservice-support:lbpmTools.facePrint.close') }" enabledText="${ lfn:message('sys-lbpmservice-support:lbpmTools.facePrint.open') }" checked="true" onValueChange="facePrintSwitch();"></ui:switch></td>
					<td style="word-wrap:break-word;word-break:break-all;"><span style="color:#aab2bd;" id="facePrintSwitchMsg"><kmss:message key="lbpmTools.facePrint.openMsg" bundle="sys-lbpmservice-support" /></span></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;">
			<br>
		 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.search') }" style="width:150px" onclick="searchFacePrint();"></ui:button>
		</td>
	</tr>
</table>
<br>
<script type="text/javascript">
function facePrintSwitch(){
	if(LUI('facePrintSwitch').checkbox.is(':checked')){
		$("#facePrintSwitchMsg").text('<kmss:message key="lbpmTools.facePrint.openMsg" bundle="sys-lbpmservice-support" />');
	}else{
		$("#facePrintSwitchMsg").text('<kmss:message key="lbpmTools.facePrint.closeMsg" bundle="sys-lbpmservice-support" />');
	}
}

seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.searchFacePrint = function() {
		window.del_load = dialog.loading();
		$("#lbpmToolDiv").show();
		var myIframe=LUI("lbpmToolIframe");
		myIframe.src="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/face_print/lbpm_tool_face_print_list.jsp?cateIds="+$("input[name='facePrintCateIds']").val()+"&modelNames="+$("input[name='facePrintModelNames']").val()+"&templateIds="+$("input[name='facePrintTemplateIds']").val()+"&nodeName="+encodeURIComponent($("input[name='facePrintNodeName']").val())+"&isCanFacePrint="+$("input[name='isCanFacePrint']").val();
		myIframe.reload();
	}
});
</script>