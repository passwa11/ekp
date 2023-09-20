( function(operations) {
	operations['drafter_press'] = {
			click:commonDrafterOperationClick,
			check:OperationCheck,
			setOperationParam:setOperationParam
	};	
	operations['drafter_return'] = {
			click:commonDrafterOperationClick,
			check:OperationCheck,
			setOperationParam:setOperationParam
	};	
	operations['drafter_abandon'] = {
			click:commonDrafterOperationClick,
			check:OperationCheckForAbandon,
			setOperationParam:setOperationParam
	};	
	//起草人操作：催办、撤回、废弃
	function commonDrafterOperationClick(operationName){

		//增加选择节点通知方式 add by wubing date:2015-05-06
		lbpm.globals.setAdminNodeNotifyType(lbpm.nowNodeId);

	};

	//“驳回”操作的检查
	function OperationCheck(){	
		return true;
	};	
	//"驳回"操作的获取参数
	function setOperationParam(){

	};	
	function OperationCheckForAbandon(){
		if(lbpm.globals.validateMustSignYourSuggestion()){
			return window.confirm(lbpm.constant.opt.abandonConfirm);
		} else {
			return false;
		}
	}
})(lbpm.operations);