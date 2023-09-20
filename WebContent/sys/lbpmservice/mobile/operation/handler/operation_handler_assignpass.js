define(['sys/lbpmservice/mobile/workitem/workitem_common'],function(itemCommon){
	//"通过加签"操作类
	
	var assignPass={};
	
	var OperationBlur = function () {
		lbpm.globals.clearDefaultUsageContent("handler_assignPass");
	};
	
	// 处理人操作：通过加签
	var OperationClick = function(operationName) {
		lbpm.globals.setDefaultUsageContent('handler_assignPass');
	};

	// “通过加签”操作的检查
	var OperationCheck= function(){
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	};
	
	// 设置"通过加签"操作的参数
	var setOperationParam = function() {

	};
	assignPass['handler_assignPass'] = lbpm.operations['handler_assignPass'] = {
			click : OperationClick,
			check : OperationCheck,
			blur : OperationBlur,
			setOperationParam : setOperationParam
		};
	
	assignPass.init = function() {
	};
	
	return assignPass;
	
});
