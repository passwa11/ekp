define(["dijit/registry","mui/form/Address",'dojo/query', 'sys/lbpmservice/mobile/common/syslbpmprocess', "dojo/store/Memory","mui/dialog/Tip"],
		function(registry, Address, query, syslbpmprocess,Memory,tip){
	/*******************************************************************************
	 * 功能：并行分支人工回收：取消分支
	  使用：
	  作者：曹映辉
	 创建时间：2018-11
	 ******************************************************************************/
	//并行分支人工回收：取消分支
	var OperationClick = function(operationName){
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML =lbpm.constant.opt.CancelconbranchTitle;
		
		//启动并行分支节点id
		var relatedNodeIds = lbpm.nodes[lbpm.nowNodeId].relatedNodeIds;
		var splitNode=lbpm.nodes[relatedNodeIds];
		var kmssData = new KMSSData();
		kmssData.AddBeanData("getConBranchStatusService&type=runing&splitNode="+relatedNodeIds+"&processId="+lbpm.modelId);
		var data = kmssData.GetHashMapArray();
		var dataCheckbox = [];
		for ( var i = 0; i < data.length; i++) {
			dataCheckbox.push({value: data[i].id, text: data[i].name});
		}
		var html = '<div data-dojo-type="mui/form/CheckBoxGroup" id="cancelConbranchsGroup" '
			+ 'data-dojo-props="validate:\'cancelconbranchRequired required\',required:true,name:\'cancelConbranchsGroup\'"></div>';
		
		query('#operationsTDContent').html(html, {parseContent: true, onEnd: function() {
			this.inherited("onEnd", arguments);
			if (this.parseDeferred) {
				this.parseDeferred.then(function() {
					var Checkbox_cancelConbranchsGroup = registry.byId('cancelConbranchsGroup');
					Checkbox_cancelConbranchsGroup.setStore(new Memory({
						data : dataCheckbox
					}));
				});
			}
		}});
		lbpm.globals.hiddenObject(operationsRow, false);
	};
	
	//并行分支人工回收：取消分支 的检查
	var OperationCheck = function(){
		var Checkbox_cancelConbranchsGroup = registry.byId('cancelConbranchsGroup');
		var val = Checkbox_cancelConbranchsGroup.get('value');
		if(val == null || val == '') {
			tip["warn"]({text:lbpm.constant.opt.CancelbranchIsNull});
			//alert(lbpm.constant.opt.CancelbranchIsNull);
			return false;
		}
		return true;	
	};
	//并行分支人工回收：取消分支的参数
	var setOperationParam = function(){
		var Checkbox_cancelConbranchsGroup = registry.byId('cancelConbranchsGroup');
		var val = Checkbox_cancelConbranchsGroup.get('value');
		lbpm.globals.setOperationParameterJson(val,"cancelConbranchs","param");
	};
	
	var OperationBlur = function() {
	};
	
	var cancelConBranch={};
	
	cancelConBranch['handler_cancelconbranch'] = lbpm.operations['handler_cancelconbranch'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};
	
	cancelConBranch.init = function(){
	};
	
	return cancelConBranch;
});
