<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
lbpm.globals.includeFile("/sys/lbpmservice/operation/operation_common_passtype.js");
/*******************************************************************************
 * 功能：处理人“签字”操作的审批所用JSP，此JSP路径在处理人“通过”操作扩展点定义的reviewJs参数匹配 使用： 作者：罗荣飞
 * 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['handler_sign'] = {
			isPassType:true,
			isHideOperationsRow : true,
			click:OperationClick,
			check:OperationCheck,
			blur:OperationBlur,
			setOperationParam:setOperationParam
	};

	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_sign');
	}		

	// 处理人操作：签字
	function OperationClick(operationName) {
		lbpm.globals.setDefaultUsageContent('handler_sign');
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document
				.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypepass" />';
		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
		if(nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
			var calcBranchLabel = '&nbsp;<label id="calc" style="color:#dd772c;cursor:pointer;" onclick="lbpm.globals.calcBranch();">'
				+ '<bean:message bundle="sys-lbpmservice" key="lbpmNode.calcBranch" />';
			operationsTDTitle.innerHTML=operationsTDTitle.innerHTML + calcBranchLabel;
		}
		lbpm.globals.hiddenObject(operationsRow, false);
		var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
		if (operatorInfo != null) {
			var html = lbpm.globals.generateNextNodeInfo();
			if (window.dojo) {
				lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationsTDContent);
			} else {
				operationsTDContent.innerHTML = html;
			}
		}
	};
	//“通过”操作的检查
	function OperationCheck(){
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			if(lbpm.globals.validateMustSignYourSuggestion()){
				return lbpm.globals.common_operationCheckForPassType();
			}
			return false;
		}
		return lbpm.globals.common_operationCheckForPassType();
	};	
	//设置"通过"操作的参数
	function setOperationParam()
	{

	};
})(lbpm.operations);

</script>
