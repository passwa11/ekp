/*******************************************************************************
 * 功能：处理人“抢办”操作的审批所用JS
  使用：
  作者：王逍
 创建时间：2021-12-03
 ******************************************************************************/
( function(operations) {
	operations['handler_robTodo'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};	

	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_robTodo');
	}

	//处理人操作：抢办
	function OperationClick(operationName){
		lbpm.globals.setDefaultUsageContent('handler_robTodo');
	};

	//“抢办”操作的检查
	function OperationCheck(){
		return true;
	}
		
	//设置"抢办"操作的参数
	function setOperationParam()
	{
		if(!lbpm.hasAddComfirm){
			lbpm.hasAddComfirm = true;
			Com_Parameter.event["confirm"].unshift(_doSubmit);
			Com_Parameter.event["submit_failure_callback"].push(_callback);
		}
	}

	function _doSubmit(formObj, method, clearParameter, moreOptions) {
		if(lbpm.hasPassComfirm){
			return true;
		}
		var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=updateIsCanRobTodo';
		var data = {"fdNodeId":lbpm.nowNodeId,"fdProcessId":lbpm.modelId};
		$.ajax({
			type : "POST",
			data : data,
			url : url,
			async : true,
			dataType : "json",
			success : function(result){
				if (result['status']) {
					lbpm.hasPassComfirm = true;
					//回调Com_submit函数
					seajs.use(['lui/dialog'], function(dialog) {
						dialog.success({status:true,title:result["msg"]},null,function(){
							Com_Submit(formObj, method, clearParameter, moreOptions);
						});
					})
				}else{
					seajs.use(['lui/dialog'], function(dialog) {
						refreshNotify();
						dialog.failure({title:result["msg"]},null,function(){
							Com_CloseWindow();
						});
					})
				}
			}
		});
		return false;
	}

	//提交失败,
	function _callback(){
		var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=removeCanRobTodo';
		var data = {"fdNodeId":lbpm.nowNodeId,"fdProcessId":lbpm.modelId};
		$.ajax({
			type : "POST",
			data : data,
			url : url,
			async : true,
			dataType : "json",
			success : function(result){

			}
		});
	}

	function refreshNotify(){
		try{
			if(window.opener!=null) {
				try {
					if (window.opener.LUI) {
						window.opener.LUI.fire({ type: "topic", name: "successReloadPage" });
						return;
					}
				} catch(e) {}
				if (window.LUI) {
					LUI.fire({ type: "topic", name: "successReloadPage" }, window.opener);
				}
				var hrefUrl= window.opener.location.href;
				var localUrl = location.href;
				if(hrefUrl.indexOf("/sys/notify/")>-1 && localUrl.indexOf("/sys/notify/")==-1)
					window.opener.location.reload();
			} else if(window.frameElement && window.frameElement.tagName=="IFRAME" && window.parent){
				if (window.parent.LUI) {
					window.parent.LUI.fire({ type: "topic", name: "successReloadPage" });
				}
			}
		}catch(e){}
	}
})(lbpm.operations);