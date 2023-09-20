define(["dijit/registry",'dojo/query', "dojo/store/Memory","mui/dialog/Tip",'sys/lbpmservice/mobile/common/syslbpmprocess',
	"mui/i18n/i18n!sys-lbpmservice-operation-branchadmin:lbpmOperations.endbranch",
	"mui/i18n/i18n!sys-lbpmservice-operation-branchadmin:lbpmOperations.fdOperType.processor.endbranch"],
	function(registry,query,Memory,tip,syslbpmprocess,Msg,Msg2){
	var branchAdminOpt={};
	
	branchAdminOpt['branchadmin_endbranch']  = {
			click:function(param){
				var curTask = param ? param.curTask : "";
				var operationsTDTitle = query("td[id='operationsTDTitle']")[0];
				var operationsTDContent = query("td[id='operationsTDContent']")[0];
				var operationsRow=operationsTDTitle.parentNode;
				operationsTDTitle.innerHTML = Msg2["lbpmOperations.fdOperType.processor.endbranch"];
				if(!window.isInitLbpmNode){
					lbpm.globals.parseXMLObj();
				}
				if(!lbpm.nodes || lbpm.nodes.length == 0 || !curTask){
					return;
				}else{
					window.isInitLbpmNode = true;
				}
				var splitNode=this._getSplitNode(lbpm.nodes[curTask.nodeId]);
				var dataCheckbox = [];
				var kmssData = new KMSSData();
				kmssData.AddBeanData("getConBranchStatusService&type=runing&splitNode="+splitNode.id+"&processId="+lbpm.modelId);
				var data = kmssData.GetHashMapArray();
				var html="";
				for(var j=0;j<data.length;j++){
					dataCheckbox.push({value:data[j].id, text: data[j].name});
				}
				var html = '<div data-dojo-type="mui/form/CheckBoxGroup" id="endConbranchsGroup" '
					+ 'data-dojo-props="validate:\'required\',required:true,name:\'endConbranchsGroup\'"></div>';
				if(dataCheckbox.length > 0){
					query("td[id='operationsTDContent']").html(html, {parseContent: true, onEnd: function() {
						this.inherited("onEnd", arguments);
						if (this.parseDeferred) {
							this.parseDeferred.then(function() {
								var Checkbox_restartConbranchsGroup = registry.byId('endConbranchsGroup');
								Checkbox_restartConbranchsGroup.setStore(new Memory({
									data : dataCheckbox
								}));
							});
						}
					}});
					lbpm.globals.hiddenObject(operationsRow, false);
				}
			},
			check:function(){
				var Checkbox_backConbranchsGroup = registry.byId('endConbranchsGroup');
				var val = Checkbox_backConbranchsGroup.get('value');
				if(val == null || val == '') {
					tip["warn"]({text:Msg["lbpmOperations.endbranch.msg"]});
					return false;
				}
				return true;
			},
			setOperationParam: function(param){
				var Checkbox_backConbranchsGroup = registry.byId('endConbranchsGroup');
				var val = Checkbox_backConbranchsGroup.get('value');
				param["endConbranchs"] = val;
			},
			//获取启动分支节点
			_getSplitNode:function(nowNode){
				for(var i=0;i<nowNode.startLines.length;i++){
					var lineObj=nowNode.startLines[i];
					var startNode = lineObj.startNode;
					if(!startNode){
						continue;
					}
					if(startNode.XMLNODENAME == "splitNode"){
						//考虑分支嵌套的情况，要跳过对应的启动分支
						if(window.routeJoinNodeRelatedNodeIds && window.routeJoinNodeRelatedNodeIds.indexOf(startNode.id) != -1){
							var result = this._getSplitNode(startNode);
							if(result == null){
								continue;
							}else{
								return result;
							}
						}else{
							return startNode;
						}
					}else{
						if(startNode.XMLNODENAME == "joinNode"){
							//考虑分支嵌套的情况，要跳过对应的启动分支
							if(!window.routeJoinNodeRelatedNodeIds){
								window.routeJoinNodeRelatedNodeIds = [];
							}
							if(window.routeJoinNodeRelatedNodeIds.indexOf(startNode.relatedNodeIds) == -1){
								window.routeJoinNodeRelatedNodeIds.push(startNode.relatedNodeIds);
							}
						}
						var result = this._getSplitNode(startNode);
						if(result == null){
							continue;
						}else{
							return result;
						}
					}
				}
				return null;
			}
	};	
	
	branchAdminOpt.init = function(){
		
	};
	return branchAdminOpt;
});