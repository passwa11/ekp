define(['sys/lbpmservice/mobile/workitem/workitem_common'],function(itemCommon){
	/*******************************************************************************
	 * 功能：处理人“节点唤醒”操作的审批所用JSP，此JSP路径在处理人“节点唤醒”操作扩展点定义的reviewJs参数匹配
	  使用：
	  作者：林彬彬
	 创建时间：2016-09-22
	 ******************************************************************************/
	var nodeResume={};
	 
	var OperationBlur = function() {
		lbpm.globals.clearDefaultUsageContent('handler_nodeResume');
	};
	
	//处理人操作：节点唤醒
	var OperationClick = function(operationName){
		lbpm.globals.setDefaultUsageContent('handler_nodeResume');
	};

	//“节点唤醒”操作的检查
	var OperationCheck = function(){
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	};
		
	//设置"节点唤醒"操作的参数
	var setOperationParam = function(){

	};
	 
	nodeResume['handler_nodeResume'] = lbpm.operations['handler_nodeResume'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};
	
	nodeResume.init = function(){
	};
	
	return nodeResume;
});
