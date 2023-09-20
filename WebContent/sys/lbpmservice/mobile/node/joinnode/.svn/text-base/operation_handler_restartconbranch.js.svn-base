define(["dijit/registry","mui/form/Address",'dojo/query', 'sys/lbpmservice/mobile/common/syslbpmprocess', "dojo/store/Memory","mui/dialog/Tip"],
		function(registry, Address, query, syslbpmprocess,Memory,tip){
	/*******************************************************************************
	 * 功能：并行分支人工回收：启动新分支
	 ******************************************************************************/
	 
	var OperationBlur = function() {
	};
	 
	//并行分支人工回收：启动新分支
	var OperationClick = function(operationName){
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML =lbpm.constant.opt.RestartconbranchTDTitle;
		
		var html="";
		var relatedNodeIds = lbpm.nodes[lbpm.nowNodeId].relatedNodeIds;
		var splitNode=lbpm.nodes[relatedNodeIds];

		var dataCheckbox = [];
		for(var i=0;i<splitNode.endLines.length;i++){
			var lineObj=splitNode.endLines[i];
			if(lbpm.nodes[lineObj.endNodeId].Status!=1){
				continue;
			}
			var descRoute=lineObj.endNodeId+"."+lbpm.nodes[lineObj.endNodeId].name;
			dataCheckbox.push({value:lineObj.endNodeId, text: descRoute});
		}
		var html = '<div data-dojo-type="mui/form/CheckBoxGroup" id="restartConbranchsGroup" '
			+ 'data-dojo-props="validate:\'restartConbranchRequired required\',required:true,name:\'restartConbranchsGroup\'"></div>';
		
		query('#operationsTDContent').html(html, {parseContent: true, onEnd: function() {
			this.inherited("onEnd", arguments);
			if (this.parseDeferred) {
				this.parseDeferred.then(function() {
					var Checkbox_restartConbranchsGroup = registry.byId('restartConbranchsGroup');
					Checkbox_restartConbranchsGroup.setStore(new Memory({
						data : dataCheckbox
					}));
				});
			}
		}});
		lbpm.globals.hiddenObject(operationsRow, false);
	};
	
	//并行分支人工回收：启动新分支的检查
	var OperationCheck = function(){
		var Checkbox_restartConbranchsGroup = registry.byId('restartConbranchsGroup');
		var val = Checkbox_restartConbranchsGroup.get('value');
		if(val == null || val == '') {
			tip["warn"]({text:lbpm.constant.opt.RestartconbranchIsNull});
			//alert(lbpm.constant.opt.RestartconbranchIsNull);
			return false;
		}
		return true;		
	};
	
	//并行分支人工回收：启动新分支的参数
	var setOperationParam = function(){
		var Checkbox_restartConbranchsGroup = registry.byId('restartConbranchsGroup');
		var val = Checkbox_restartConbranchsGroup.get('value');
		lbpm.globals.setOperationParameterJson(val,"restartConbranchs","param");
	};
	
	var restartConBranch = {};
	
	restartConBranch['handler_restartconbranch'] = lbpm.operations['handler_restartconbranch'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};
	
	restartConBranch.init = function(){
	};

	return restartConBranch;
});
