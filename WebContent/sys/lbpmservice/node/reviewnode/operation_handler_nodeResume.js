( function(operations) {
	operations['handler_nodeResume'] = {
			click:OperationClick,
			check:OperationCheck,
			blur:OperationBlur,
			setOperationParam:setOperationParam
	};
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_nodeResume');
	}	
	//处理人操作： 节点唤醒
	function OperationClick(operationName){
		lbpm.globals.setDefaultUsageContent('handler_nodeResume');
	};
	//“ 节点唤醒”操作的检查
	function OperationCheck(){
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}	
		return true;
	};	
	//" 节点唤醒"操作的获取参数
	function setOperationParam(){};
})(lbpm.operations);