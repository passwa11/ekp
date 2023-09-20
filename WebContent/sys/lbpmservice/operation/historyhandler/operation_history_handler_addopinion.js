/*******************************************************************************
 * 功能：抄送人“追加意见”操作的审批所用JSP 创建时间：2014-05-12
 ******************************************************************************/
( function(operations) {
	operations['history_handler_addOpinion'] = {
		click : OperationClick,
		check : OperationCheck,
		setOperationParam : setOperationParam
	};
	// 处理人操作：追加意见
	function OperationClick(operationName) {
	}
	// “追加意见”操作的检查
	function OperationCheck(workitemObjArray) {
		return true;
	}
	// 设置"追加意见"操作的参数
	function setOperationParam() {
	}
})(lbpm.operations);