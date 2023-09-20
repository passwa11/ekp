<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<form method="post" action="${pageContext.request.contextPath}/sys/lbpmservice/support/lbpm_cost_time/lbpmCostTime.do?method=updateCostTimeData">
	<table class="tb_normal lbpmToolsTable" width="90%">
		<tr>
			<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.range" /></td>
			<td width="80%">
				<!-- 被选中的模板ID集合“;”分割 -->
				<input type="hidden" name="costTimeCateIds"/>
				<input type="hidden" name="costTimeCateNames"/>
				<input type="hidden" name="costTimeModelNames"/>
				<input type="hidden" name="costTimeModuleNames"/>
				<input type="hidden" name="costTimeTemplateIds"/>
				<input type="hidden" name="costTimeTemplateNames"/>
				<input type="hidden" name="costTimeTemplateId">
				<input type="text" name="costTimeTemplateName" readOnly subject="${lfn:message('sys-lbpmservice-support:lbpmTools.range')}" validate="required" style="width:88%" class="inputSgl"><span class=txtstrong>*</span>
				<a href="javascript:void(0);" onclick="lbpmToolsSelectSubFlow('costTimeTemplateId','costTimeTemplateName','costTimeCateIds','costTimeCateNames','costTimeModelNames','costTimeModuleNames','costTimeTemplateIds','costTimeTemplateNames');" style="color:#666"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
				</br>
			</td>
		</tr>
		<tr>
			<td width=20%></td>
			<td width="80%">
				<input type="checkbox" name="cIsCostTime"><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.costTime.total" />
			</td>
		</tr>
		<tr>
			<td colspan="2" style="word-wrap:break-word;word-break:break-all;">
				<span style="color:#aab2bd;"><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.costTime.desc" /></span>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;">
				<br>
			 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.update') }" style="width:150px" onclick="submitAction();"></ui:button>
			</td>
		</tr>
	</table>
</form>
<br>
<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.submitAction = function() {
		var costTime = $("input[name='costTimeTemplateName']");
		//调用校验框架校验必填
		var _elements = new Elements()
		_elements.serializeElement(costTime[0]);
		var costTimeValidate = lbpm_tools_validation._doValidateElement(new Element(costTime[0]),lbpm_tools_validation.getValidator(costTime.attr("validate")));
		if(costTimeValidate){
			var url = '${LUI_ContextPath}/sys/lbpmservice/support/LbpmToolsAction.do?method=updateCostTimeData';
			window.del_load = dialog.loading();
			$.ajax({
				url : url,
				type : 'POST',
				dataType : 'json',
				data : $.param({"cateIds" : $("input[name='costTimeCateIds']").val(),"modelNames" : $("input[name='costTimeModelNames']").val(),"templateIds" : $("input[name='costTimeTemplateIds']").val(),"cIsCostTime":$("input[name='cIsCostTime']").val()}, true),
				error : function(data) {
					if(window.del_load != null) {
						window.del_load.hide(); 
					}
					dialog.result(data.responseJSON);
				},
				success: function(data) {
					if(window.del_load != null){
						window.del_load.hide(); 
					}
					dialog.result(data);
				}
		   });
		}
	}
});

</script>