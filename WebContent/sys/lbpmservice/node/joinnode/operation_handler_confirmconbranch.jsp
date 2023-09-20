<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
lbpm.globals.includeFile("/sys/lbpmservice/operation/operation_common_passtype.js");
/*******************************************************************************
 * 功能：并行分支人工回收，确认操作（结束到达分支)
 * 创建时间：2018-11-06
 ******************************************************************************/
( function(operations) {
	operations['handler_confirmconbranch'] = {
			click:OperationClick,
			check:OperationCheck,
			blur:OperationBlur,
			setOperationParam:setOperationParam
	};		
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_confirmconbranch');
	}

	function OperationClick(operationName) {
		/* var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		lbpm.globals.hiddenObject(operationsRow, false); */
	};
	//“结束到达分支”操作的检查
	function OperationCheck(workitemObjArray){
		return true;
	};	
	
	//设置"结束到达分支"操作的参数
	function setOperationParam() {
	};
})(lbpm.operations);

</script>
