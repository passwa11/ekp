( function(operations) {
	operations['admin_press'] = {
			click:OperationClick,
			check:OperationCheck,
			setOperationParam:setOperationParam
	};	
	//特权人操作：催办
	function OperationClick(operationName){
		//增加选择节点通知方式 add by wubing date:2015-05-06
		lbpm.globals.setAdminNodeNotifyType(lbpm.nowNodeId,"press");
	};
	//“催办”操作的检查
	function OperationCheck(){	
		return true;
	};	
	//"催办"操作的获取参数
	function setOperationParam(){};
})(lbpm.operations);