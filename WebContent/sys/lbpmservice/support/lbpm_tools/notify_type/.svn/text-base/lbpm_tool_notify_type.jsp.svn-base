<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<table class="tb_normal lbpmToolsTable" width="90%">
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.range" /></td>
		<td width="80%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="notifyTypeCateIds"/>
			<input type="hidden" name="notifyTypeCateNames"/>
			<input type="hidden" name="notifyTypeModelNames"/>
			<input type="hidden" name="notifyTypeModuleNames"/>
			<input type="hidden" name="notifyTypeTemplateIds"/>
			<input type="hidden" name="notifyTypeTemplateNames"/>
			<input type="hidden" name="notifyTypeTemplateId">
			<input type="text" name="notifyTypeTemplateName" readOnly style="width:88%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.rangeDesc') }">
			<a href="javascript:void(0);" onclick="lbpmToolsSelectSubFlow('notifyTypeTemplateId','notifyTypeTemplateName','notifyTypeCateIds','notifyTypeCateNames','notifyTypeModelNames','notifyTypeModuleNames','notifyTypeTemplateIds','notifyTypeTemplateNames');" style="color:#666"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			</br>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.notifyType" /></td>
		<td width="80%">
			<kmss:editNotifyType property="notifyType" required="true" />
		</td>
	</tr>
	<tr>
		<td colspan="2" style="word-wrap:break-word;word-break:break-all;">
			<span style="color:#aab2bd;"><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.notifyTypeMsg" /></span>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;">
			<br>
		 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.search') }" style="width:150px" onclick="searchNotifyType();"></ui:button>
		</td>
	</tr>
</table>
<br>
<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.searchNotifyType = function() {
		var notifyType = $("input[name='notifyType']");
		if(notifyType.val()){
			window.del_load = dialog.loading();
			$("#lbpmToolDiv").show();
			var myIframe=LUI("lbpmToolIframe");
			myIframe.src="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/notify_type/lbpm_tool_notify_type_list.jsp?cateIds="+$("input[name='notifyTypeCateIds']").val()+"&modelNames="+$("input[name='notifyTypeModelNames']").val()+"&templateIds="+$("input[name='notifyTypeTemplateIds']").val()+"&notifyType="+notifyType.val();
			myIframe.reload();
		}
	}
});
</script>