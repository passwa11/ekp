define(['sys/lbpmservice/mobile/workitem/workitem_common'],function(itemCommon){
	//"回复沟通"操作类
	
	var rtnCommunite={};
	// 处理人操作：回复沟通
	var OperationClick = function(operationName) {
		
	};

	// “回复沟通”操作的检查
	var OperationCheck= function(){
		return lbpm.globals.validateMustSignYourSuggestion();
	};
	
	// 设置"回复沟通"操作的参数
	var setOperationParam = function() {

	};
	rtnCommunite['handler_returnCommunicate'] = lbpm.operations['handler_returnCommunicate'] = {
			click : OperationClick,
			check : OperationCheck,
			setOperationParam : setOperationParam
		};
	
	rtnCommunite.init = function() {
	};
	
	return rtnCommunite;
	
});
