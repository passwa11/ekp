define(['dojo/query', "dijit/registry"], function(query, registry) {
	/*******************************************************************************
	 * 功能：并行分支人工结束--确认操作（结束到达分支)
	 * 作者 曹映辉 #日期 2018年11月6日
	 ******************************************************************************/
	function OperationClick(operationName) {
//		var operationsRow = document.getElementById("operationsRow");
//		var operationsTDTitle = document.getElementById("operationsTDTitle");
//		var operationsTDContent = document.getElementById("operationsTDContent");
//		lbpm.globals.hiddenObject(operationsRow, false);
	}
	
	// “结束到达分支”操作的检查
	function OperationCheck(workitemObjArray) {
		return true;
	}
	
	// 设置"结束到达分支"操作的参数
	function setOperationParam() {
	}
	
	function OperationBlur() {
	}

	var confirmConBranch={};
	
	confirmConBranch['handler_confirmconbranch'] = lbpm.operations['handler_confirmconbranch'] = {
		click : OperationClick,
		check : OperationCheck,
		blur : OperationBlur,
		setOperationParam : setOperationParam
	};
	
	confirmConBranch.init = function(){
	};
	
	return confirmConBranch;
});
