<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<tr id="secretaryAdvanceApprovalFormTr">
	<td width="100px"><kmss:message key="lbpmProcess.secretaryAdvanceApproval" bundle="sys-lbpmservice-support" /></td>
	<td>
		<label>
			<input name="wf_secretaryAdvanceApproval" type="radio" value="1" >
			<kmss:message key="message.yes" />
		</label>
		<label>
			<input name="wf_secretaryAdvanceApproval" type="radio" value="0" checked>
			<kmss:message key="message.no" />
		</label>
	</td>
</tr>


<script>
$('input[type=radio][name=wf_processType]').change(function() {
	if(this.value=="0"){
		$("#secretaryAdvanceApprovalFormTr").show();//显示div
	}else{
		$("#secretaryAdvanceApprovalFormTr").hide();//隐藏div
	}
});

AttributeObject.Init.AllModeFuns.push(function() {
	var processType = AttributeObject.NodeData["processType"];
	//开启预审（processType等于0的时候就会开启预审的选择行）
	processType = processType?processType:0;
	if(processType!="0"){
		$("#secretaryAdvanceApprovalFormTr").hide();//隐藏div
	}
});
</script>

