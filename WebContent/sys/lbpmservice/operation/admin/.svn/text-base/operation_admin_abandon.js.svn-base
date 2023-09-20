/*******************************************************************************
 * 功能：处理人“驳回”操作的审批所用JSP，此JSP路径在处理人“驳回”操作扩展点定义的reviewJs参数匹配
  使用：
  作者：罗荣飞
 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['admin_abandon'] = {
		click:OperationClick,
		check:OperationCheck,
		setOperationParam:setOperationParam
	};	


	//特权人操作：直接废弃
	function OperationClick(operationName){
		$("#rerunIfErrorRow").show();
		lbpm.globals.setAdminNodeNotifyType(lbpm.nowNodeId);
	};
	//“驳回”操作的检查
	function OperationCheck(){
		var flag = true;
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
		if(flag == true){
			return lbpm.globals.validateMustSignYourSuggestion();
		}	
		return flag;
	};	
	//"驳回"操作的获取参数
	function setOperationParam(){
		if ($('#rerunIfError').length > 0) {
			lbpm.globals.setOperationParameterJson($("#rerunIfError").attr("checked"), "rerunIfError", "param");
		}
	};	

})(lbpm.operations);