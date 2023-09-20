/*******************************************************************************
 * 功能：处理人“退回加签”操作的审批所用JSP
 * 创建时间：2018-08-06
 ******************************************************************************/
( function(operations) {
	operations['handler_assignRefuse'] = {
		click : OperationClick,
		check : OperationCheck,
		blur : OperationBlur,
		setOperationParam : setOperationParam
	};
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent("handler_assignRefuse");
	}
	
	// 处理人操作：退回加签
	function OperationClick(operationName) {
		lbpm.globals.setDefaultUsageContent('handler_assignRefuse');
	}

	//“退回加签”操作的检查
	function OperationCheck() {
		if (lbpm.nowProcessorInfoObj["type"] != "assignWorkitem") {
			return false;
		}
		
		if (lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		
		return true;
	}
	
	// 设置"退回加签"操作的参数
	function setOperationParam() {
	};

})(lbpm.operations);