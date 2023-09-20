<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
lbpm.globals.includeFile("/sys/lbpmservice/operation/operation_common_passtype.js");
/*******************************************************************************
 * 功能：并行分支人工回收 取消分支操作
 * 作者 曹映辉 #日期 2018年11月6日
 ******************************************************************************/
( function(operations) {
	operations['handler_cancelconbranch'] = {
			click:OperationClick,
			check:OperationCheck,
			blur:OperationBlur,
			setOperationParam:setOperationParam
	};		
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_cancelconbranch');
	}

	// 处理人操作：取消分支操作
	function OperationClick(operationName) {
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = '<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmOperations.fdOperType.processor.joinnode.cancelconbranch" />';
		var relatedNodeIds = lbpm.nodes[lbpm.nowNodeId].relatedNodeIds;
		var splitNode=lbpm.nodes[relatedNodeIds];
		
		var kmssData = new KMSSData();
		kmssData.AddBeanData("getConBranchStatusService&type=runing&splitNode="+relatedNodeIds+"&processId="+lbpm.modelId);
		var data = kmssData.GetHashMapArray();
		var html="";
		for(var j=0;j<data.length;j++){
			html += "<label class='lui-lbpm-checkbox'><input type='checkbox' name='cancelConbranchsObj' key='cancelConbranchs'  value='" + data[j].id
			+ "' onclick=''><span class='checkbox-label'>"+data[j].name+"</span></input></label>";
			if(j<data.length-1){
				html += "<br/>";
			}
		}
		html += "<span class='txtstrong'>*</span>"
		operationsTDContent.innerHTML=html;
		lbpm.globals.hiddenObject(operationsRow, false);
			
	};
	//“取消分支操作”操作的检查
	function OperationCheck(workitemObjArray){
		if($('input[name="cancelConbranchsObj"]:checked').length==0){
			alert('<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmProcess.joinnode.conbranch.cancelbranch.check" />');
			return false;
		}
		return true;
	};	
	//设置"取消分支操作"操作的参数
	function setOperationParam()
	{
		var checkedBranchNodes=[];
		 $.each($('input[name="cancelConbranchsObj"]:checked'),function(){
			 checkedBranchNodes.push($(this).val());
         });
		lbpm.globals.setOperationParameterJson(checkedBranchNodes.join(";"),"cancelConbranchs", "param");
	};
})(lbpm.operations);

</script>
