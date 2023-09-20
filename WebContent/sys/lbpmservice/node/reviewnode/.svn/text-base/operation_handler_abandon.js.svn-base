/*******************************************************************************
 * 功能：处理人“废弃”操作的审批所用JSP，此JSP路径在处理人“驳回”操作扩展点定义的reviewJs参数匹配
  使用：
  作者：罗荣飞
 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['handler_abandon'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};	

	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_abandon');
	}

	//处理人操作：废弃
	function OperationClick(operationName){
		lbpm.globals.setDefaultUsageContent('handler_abandon');
	};

	//“通过”操作的检查
	function OperationCheck(){
		var flag = true;
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			flag = lbpm.globals.validateMustSignYourSuggestion();
		}
		if(flag==true){
			var modelId = document.getElementsByName("sysWfBusinessForm.fdModelId")[0];
			var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=checkSubInProcessAjax&fdId=';
			ajaxurl += modelId.value;
			var kmssData = new KMSSData();
			kmssData.SendToUrl(ajaxurl, function(http_request) {
				var responseText = http_request.responseText;
				var json = eval("("+responseText+")");
				if (json.checkSubInProcess == "true"){
					if(!confirm(lbpm.constant.opt.checkSubProcessInProcess)){
						flag=false;
					}
				}},false);
			}
		return flag;
	}
		
	//设置"通过"操作的参数
	function setOperationParam()
	{

	}
})(lbpm.operations);