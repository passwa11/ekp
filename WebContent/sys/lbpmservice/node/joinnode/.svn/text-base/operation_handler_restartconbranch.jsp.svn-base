<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
lbpm.globals.includeFile("/sys/lbpmservice/operation/operation_common_passtype.js");
/*******************************************************************************
 * 功能：并行分支人工回收 重启新分支操作
 * 作者 曹映辉 #日期 2018年11月6日
 ******************************************************************************/
( function(operations) {
	operations['handler_restartconbranch'] = {
			click:OperationClick,
			check:OperationCheck,
			blur:OperationBlur,
			setOperationParam:setOperationParam
	};		
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_restartconbranch');
	}

	// 处理人操作：重启新分支操作
	function OperationClick(operationName) {
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document
				.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = '<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmNode.processingNode.operationsTDTitle.restartconbranch" />';
			
		var relatedNodeIds = lbpm.nodes[lbpm.nowNodeId].relatedNodeIds;
		var splitNode=lbpm.nodes[relatedNodeIds];
		var html="";
		var lines = [];
		$.each(splitNode.endLines, function(i, lineObj) {
			//只有未启动的分支才可以重启
			if(lbpm.nodes[lineObj.endNodeId].Status!=1){
				return;
			}
			lines.push(lineObj);
		});
		$.each(lines, function(i, lineObj) {
			var lineNodeName = WorkFlow_getLangLabel(lineObj.name,lineObj["langs"]);
			var lineName = lineNodeName == null?"":lineNodeName + " ";
			var descRoute=lineName;
			if(!descRoute){
				descRoute=lineObj.endNodeId+"."+lbpm.nodes[lineObj.endNodeId].name;
			}
			html += "<label class='lui-lbpm-checkbox'><input type='checkbox' name='restartConbranchsObj' key='restartConbranchs'  index='" + i + "' value='" + lineObj.endNodeId
			+ "' onclick=''><span class='checkbox-label'>"+descRoute+"</span></input></label>" ;
			if(i<lines.length-1){
				html += "<br/>";
			}
		});
		html += "<span class='txtstrong'>*</span>"
		operationsTDContent.innerHTML=html;
		lbpm.globals.hiddenObject(operationsRow, false);
			
	};
	//“重启新分支操作”操作的检查
	function OperationCheck(workitemObjArray){
		if($('input[name="restartConbranchsObj"]:checked').length==0){
			alert('<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmProcess.joinnode.conbranch.restartbranch.check" />');
			return false;
		}
		return true;
	};	
	//设置"重启新分支操作"操作的参数
	function setOperationParam()
	{
		var checkedBranchNodes=[];
		 $.each($('input[name="restartConbranchsObj"]:checked'),function(){
			 checkedBranchNodes.push($(this).val());
         });
		lbpm.globals.setOperationParameterJson(checkedBranchNodes.join(";"),"restartConbranchs", "param");
	};
})(lbpm.operations);

</script>
