( function(operations) {
	operations['admin_nodeSuspend'] = {
			click:OperationClick,
			check:OperationCheck,
			blur:OperationBlur,
			setOperationParam:setOperationParam
	};
	function OperationBlur() {
	}	
	//特权人操作： 节点暂停
	function OperationClick(operationName){
		//增加选择节点通知方式 add by wubing date:2015-05-06
		lbpm.globals.setAdminNodeNotifyType(lbpm.nowNodeId);
	};
	//“ 节点暂停”操作的检查
	function OperationCheck(){	
		return true;
	};	
	//" 节点暂停"操作的获取参数
	function setOperationParam(){};
})(lbpm.operations);