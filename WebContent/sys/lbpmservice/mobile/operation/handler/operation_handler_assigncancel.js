define(['dojo/query', "dijit/registry", "dojo/store/Memory","mui/dialog/Tip"],function(query, registry, Memory, tip){
	//"收回加签"操作类
	var assignCancel={};
	// 处理人操作：收回加签
	var OperationClick = function(operationName) {
		lbpm.globals.getOperationParameterJson("relationWorkitemId:relations",true); // 加载后端数据
		var operationsRow = document.getElementById("operationsRow");
		var relationInfoObj = getCurrAssignRelationInfo();
		if (relationInfoObj.length > 0) {
			var operationsTDTitle = document.getElementById("operationsTDTitle");
			operationsTDTitle.innerHTML = lbpm.constant.opt.Assignee;
			var html = '<div data-dojo-type="mui/form/CheckBoxGroup" id="WorkFlow_CancelAssignWorkitemsGroup" '
					+ 'data-dojo-props="name:\'WorkFlow_CancelAssignWorkitemsGroup\',validate:\'assignCancelHandlerRequired required\',required:true"></div>';
			var data = [];
			for ( var i = 0; i < relationInfoObj.length; i++) {
				data.push({value: relationInfoObj[i].itemId, text: relationInfoObj[i].userName});
			}
			query('#operationsTDContent').html(html, {parseContent: true, onEnd: function() {
				this.inherited("onEnd", arguments);
				if (this.parseDeferred) {
					this.parseDeferred.then(function() {
						var WorkFlow_CancelAssignWorkitemsGroup = registry.byId('WorkFlow_CancelAssignWorkitemsGroup');
						WorkFlow_CancelAssignWorkitemsGroup.setStore(new Memory({
							data : data
						}));
					});
				}
			}});
			lbpm.globals.hiddenObject(operationsRow, false);
			
		} else {
			lbpm.globals.hiddenObject(operationsRow, true);
		}
	};

	// “收回加签”操作的检查
	var OperationCheck= function(){
		var WorkFlow_CancelAssignWorkitemsGroup = dijit.registry.byId('WorkFlow_CancelAssignWorkitemsGroup');
		var val = WorkFlow_CancelAssignWorkitemsGroup.get('value');
		if(val == null || val == '') {
			tip["warn"]({text:lbpm.constant.opt.AssignNeedSelectCanceler});
			//alert(lbpm.constant.opt.AssignNeedSelectCanceler);
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	};
	
	// 设置"收回加签"操作的参数
	var setOperationParam = function() {
		var WorkFlow_CancelAssignWorkitemsGroup = dijit.registry.byId('WorkFlow_CancelAssignWorkitemsGroup');
		var val = WorkFlow_CancelAssignWorkitemsGroup.get('value');
		lbpm.globals.setOperationParameterJson(val, "cancelAssigneeIds", "param");
		// 所有的加签人员ID
		var allAssigneeIds="";
		query("[name='_WorkFlow_CancelAssignWorkitemsGroup_single']").forEach(function(node,index){
			if(allAssigneeIds=='')
				allAssigneeIds=node.value;
			else
				allAssigneeIds+=";"+node.value;
		});
		lbpm.globals.setOperationParameterJson(allAssigneeIds, "allAssigneeIds", "param");
	};
	
	assignCancel['handler_assignCancel'] = lbpm.operations['handler_assignCancel'] = {
			click : OperationClick,
			check : OperationCheck,
			setOperationParam : setOperationParam
		};
	
	assignCancel.init = function() {
	};
	
	return assignCancel;
});
