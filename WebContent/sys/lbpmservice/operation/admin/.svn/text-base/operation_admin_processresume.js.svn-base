( function(operations) {
	operations['admin_processResume'] = {
			click:OperationClick,
			check:OperationCheck,
			setOperationParam:setOperationParam
	};	
	//特权人操作： 流程唤醒
	function OperationClick(operationName){
		//增加选择节点通知方式 add by wubing date:2015-05-06
		lbpm.globals.setAdminNodeNotifyType(lbpm.nowNodeId);
	};
	//“ 流程唤醒”操作的检查
	function OperationCheck(){	
		return true;
	};	
	//" 流程唤醒"操作的获取参数
	function setOperationParam(){};
})(lbpm.operations);