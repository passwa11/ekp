define(['dojo/query', "dijit/registry", "dojo/store/Memory", "mui/dialog/Tip"],function( query, registry, Memory, tip){
	var cancelComunite={};
	
	var OperationClick = function(operationName) {
		lbpm.globals.getOperationParameterJson("relationWorkitemId:relationScope:relations:isMutiCommunicate",true); // 加载后端数据
		var operationsRow = document.getElementById("operationsRow");
		var relationInfoObj = lbpm.globals.getCurrRelationInfo();
		if (relationInfoObj.length > 0) {
			var operationsTDTitle = document.getElementById("operationsTDTitle");
			operationsTDTitle.innerHTML = lbpm.constant.opt.CommunicatePeople;
			var html = '<div data-dojo-type="mui/form/CheckBoxGroup" id="WorkFlow_CelRelationWorkitemsGroup" '
					+ 'data-dojo-props="name:\'WorkFlow_CelRelationWorkitemsGroup\',validate:\'cancelCommunicateHandlerRequired required\',required:true"></div>';
			var data = [];
			for ( var i = 0; i < relationInfoObj.length; i++) {
				data.push({value: relationInfoObj[i].itemId, text: relationInfoObj[i].userName});
			}
			//TODO 需要验证
			query('#operationsTDContent').html(html, {parseContent: true, onEnd: function() {
				this.inherited("onEnd", arguments);
				if (this.parseDeferred) {
					this.parseDeferred.then(function() {
						var WorkFlow_CelRelationWorkitemsGroup = registry.byId('WorkFlow_CelRelationWorkitemsGroup');
						WorkFlow_CelRelationWorkitemsGroup.setStore(new Memory({
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

	// “取消沟通”操作的检查
	var OperationCheck = function() {
		var WorkFlow_CelRelationWorkitemsGroup = dijit.registry.byId('WorkFlow_CelRelationWorkitemsGroup');
		var val = WorkFlow_CelRelationWorkitemsGroup.get('value');
		if(val == null || val == '') {
			tip["warn"]({text:lbpm.constant.opt.CommunicateNeedSelectCanceler});
			//alert(lbpm.constant.opt.CommunicateNeedSelectCanceler);
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;

	};
	var setOperationParam = function() {
		var WorkFlow_CelRelationWorkitemsGroup = dijit.registry.byId('WorkFlow_CelRelationWorkitemsGroup');
		var val = WorkFlow_CelRelationWorkitemsGroup.get('value');
		lbpm.globals.setOperationParameterJson(val, "cancelHandlerIds", "param");
	};
	
	cancelComunite['handler_cancelCommunicate'] = lbpm.operations['handler_cancelCommunicate']={
			click : OperationClick,
			check : OperationCheck,
			setOperationParam : setOperationParam
		};
	
	cancelComunite.init  = function(){
	};
	return cancelComunite;
});
