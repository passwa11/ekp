define(['sys/lbpmservice/mobile/workitem/workitem_common'],function(itemCommon){
	//"退回加签"操作类
	var assignRefuse={};
	
	var OperationBlur = function () {
		lbpm.globals.clearDefaultUsageContent("handler_assignRefuse");
	};
	
	// 处理人操作：退回加签
	var OperationClick = function(operationName) {
		lbpm.globals.setDefaultUsageContent('handler_assignRefuse');
	};

	// “退回加签”操作的检查
	var OperationCheck= function(){
		if (lbpm.nowProcessorInfoObj["type"] != "assignWorkitem") {
			return false;
		}
		
		if (lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		
		return true;
	};
	
	// 设置"退回加签"操作的参数
	var setOperationParam = function() {

	};
	
	assignRefuse['handler_assignRefuse'] = lbpm.operations['handler_assignRefuse'] = {
			click : OperationClick,
			check : OperationCheck,
			blur : OperationBlur,
			setOperationParam : setOperationParam
	};
	
	assignRefuse.init = function() {
	};
	
	return assignRefuse;
	
});
