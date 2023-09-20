<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<tr id="isFixedNodeTr">
	<td><kmss:message key="lbpmProcess.freeflow.node" bundle="sys-lbpmservice-support" /></td>
	<td>
		<label>
			<input name="wf_isFixedNode" type="checkbox" value="true" >
			<kmss:message key="lbpmProcess.freeflow.Fixed" bundle="sys-lbpmservice-support" />
		</label>
	</td>
</tr>
<script type="text/javascript">
 	AttributeObject.Init.AllModeFuns.push(function() {
 		if(!FlowChartObject.IsTemplate){
 			var isFixedNodeTr = document.getElementById("isFixedNodeTr");
 			AttributeObject.Utils.disabledOperation(isFixedNodeTr);
 		}
 		if(!$("input[name='wf_isFixedNode']").is(':checked')){
 			$(".lbpm_fixedNode_attr").hide();
 		}else{
 			$(".lbpm_fixedNode_attr").show();
 		}
	});
 	
 	$("input[name='wf_isFixedNode']").click(function(){
 		if($(this).is(':checked')){
 			$(".lbpm_fixedNode_attr").show();
 		}else{
 			$(".lbpm_fixedNode_attr").hide();
 		}
 		//重新调用保存，防止先触发change后触发click，导致click时修改的内容未保存
 		saveNodeData();
 	});
</script>