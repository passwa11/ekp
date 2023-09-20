define(['sys/lbpmservice/mobile/workitem/workitem_common'],function(itemCommon){
	/*******************************************************************************
	 * 功能：处理人“废弃”操作的审批所用JSP，此JSP路径在处理人“废弃”操作扩展点定义的reviewJs参数匹配
	  使用：
	  作者：罗荣飞
	 创建时间：2012-06-06
	 ******************************************************************************/
	var abandon={};
	 
	var OperationBlur = function() {
		lbpm.globals.clearDefaultUsageContent('handler_abandon');
	};
	
	//处理人操作：废弃
	var OperationClick = function(operationName){
		lbpm.globals.setDefaultUsageContent('handler_abandon');
	};

	//“废弃”操作的检查
	var OperationCheck = function(){
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	};
		
	//设置"废弃"操作的参数
	var setOperationParam = function(){

	};
	 
	abandon['handler_abandon'] = lbpm.operations['handler_abandon'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};	
});
