<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<form id="workUpdateForm" method="post" action="${pageContext.request.contextPath}/sys/lbpmservice/support/LbpmToolsAction.do?method=updateWorkTimeData">
	<table class="tb_normal lbpmToolsTable" width="90%">
		<tr>
			<td><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.workTimeUpdate.dateFrame" /></td>
			<td>
				<xform:datetime subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.workTimeUpdate.startDate') }" htmlElementProperties="id='startDate' validate='required'" property="workTimeStartDate" showStatus="edit" required="true" />
				-
				<xform:datetime subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.workTimeUpdate.endDate') }" htmlElementProperties="id='endDate' validate='required'" property="workTimeEndDate" showStatus="edit" required="true" />
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;">
				<br>
			 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.update') }" style="width:150px" onclick="submitWorkTimeUpdate();"></ui:button>
			</td>
		</tr>

	</table>
</form>
<br>
<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.submitWorkTimeUpdate = function() {

		var workTimeTemplates = $("input[name^='workTime']");
		var validateNum=0;
		var _elements = new Elements()
		for(var i=0;i<workTimeTemplates.length;i++){
			_elements.serializeElement(workTimeTemplates[i]);
		}	
		for(var i=0;i<workTimeTemplates.length;i++){
			//调用校验框架校验必填
			var validate = lbpm_tools_validation._doValidateElement(new Element(workTimeTemplates[i]),lbpm_tools_validation.getValidator("required"));
			if(validate==true){
				validateNum++;
			}
		}	
		if(workTimeTemplates.length==validateNum){
			var url = '${LUI_ContextPath}/sys/lbpmservice/support/LbpmToolsAction.do?method=updateWorkTimeData';
			window.del_load = dialog.loading();
			$.ajax({
				url : url,
				type : 'POST',
				dataType : 'json',
				data : $.param({"startDate" : $("#startDate").val(),"endDate":$("#endDate").val()}, true),
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