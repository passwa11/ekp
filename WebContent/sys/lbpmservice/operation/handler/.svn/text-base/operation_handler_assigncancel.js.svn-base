/*******************************************************************************
 * 功能：处理人“收回加签”操作的审批所用JSP
 * 创建时间：2018-08-06
 ******************************************************************************/
( function(operations) {
	operations['handler_assignCancel'] = {
		click : OperationClick,
		check : OperationCheck,
		setOperationParam : setOperationParam
	};
	
	// 处理人操作：收回加签
	function OperationClick(operationName) {
		lbpm.globals.getOperationParameterJson("relationWorkitemId:relations:",true); 
		var operationsRow = document.getElementById("operationsRow");
		var relationInfoObj = getCurrAssignRelationInfo();
		if (relationInfoObj.length > 0) {
			var operationsTDTitle = document.getElementById("operationsTDTitle");
			var operationsTDContent = document.getElementById("operationsTDContent");
			operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.Assignee;
			var html = [];
			for ( var i = 0; i < relationInfoObj.length; i++) {
				html.push('<label class="lui-lbpm-checkbox"><input type="checkbox" name="WorkFlow_CancelAssignWorkitems" checked value="' + relationInfoObj[i].itemId + '"><span class="checkbox-label">' + relationInfoObj[i].userName + '</span></label>');
			}
			
			var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
			html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+lbpm.globals.getNotifyType4Node(currentNodeObj));
			
			operationsTDContent.innerHTML = html.join('');
			lbpm.globals.hiddenObject(operationsRow, false);
		} else {
			lbpm.globals.hiddenObject(operationsRow, true);
		}
	}

	//“收回加签”操作的检查
	function OperationCheck() {
		var arr=$("[name='WorkFlow_CancelAssignWorkitems']:checked");
		if(arr.length==0) {
			alert(lbpm.constant.opt.AssignNeedSelectCanceler);
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	}

	// 设置"收回加签"操作的参数
	function setOperationParam() {
		//要收回加签的人员ID
		var cancelAssigneeIds="";
		$("[name='WorkFlow_CancelAssignWorkitems']:checked").each(function(i){
			if(cancelAssigneeIds=='')
				cancelAssigneeIds=this.value;
			else
				cancelAssigneeIds+=";"+this.value;
		});
		lbpm.globals.setOperationParameterJson(cancelAssigneeIds, "cancelAssigneeIds", "param");

		// 所有的加签人员ID
		var allAssigneeIds="";
		$("[name='WorkFlow_CancelAssignWorkitems']").each(function(i){
			if(allAssigneeIds=='')
				allAssigneeIds=this.value;
			else
				allAssigneeIds+=";"+this.value;
		});
		lbpm.globals.setOperationParameterJson(allAssigneeIds, "allAssigneeIds", "param");

	};

})(lbpm.operations);