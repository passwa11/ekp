/*******************************************************************************
 * 功能：投票人“不同意”操作的审批所用JSP
 * 创建时间：2018-08-20
 ******************************************************************************/
( function(operations) {
	operations['handler_disagree'] = {
		click : OperationClick,
		check : OperationCheck,
		setOperationParam : setOperationParam
	};
	
	// 投票人操作：不同意
	function OperationClick(operationName) {
	}

	//“不同意”操作的检查
	function OperationCheck() {
		return true;
	}
	
	// 设置"不同意"操作的参数
	function setOperationParam() {
	};

})(lbpm.operations);