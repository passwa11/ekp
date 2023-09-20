/*******************************************************************************
 * 功能：投票人“弃权”操作的审批所用JSP
 * 创建时间：2018-08-20
 ******************************************************************************/
( function(operations) {
	operations['handler_abstain'] = {
		click : OperationClick,
		check : OperationCheck,
		setOperationParam : setOperationParam
	};
	
	// 投票人操作：弃权
	function OperationClick(operationName) {
	}

	//“弃权”操作的检查
	function OperationCheck() {
		return true;
	}
	
	// 设置"弃权"操作的参数
	function setOperationParam() {
	};

})(lbpm.operations);