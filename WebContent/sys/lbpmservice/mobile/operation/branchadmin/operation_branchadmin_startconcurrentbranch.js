define(["dijit/registry","mui/form/Address",'dojo/query', 'sys/lbpmservice/mobile/common/syslbpmprocess', "dojo/store/Memory","mui/dialog/Tip",
	"mui/i18n/i18n!sys-lbpmservice-node-joinnode:lbpmNode","mui/i18n/i18n!sys-lbpmservice-node-joinnode:lbpmProcess"],
		function(registry, Address, query, syslbpmprocess,Memory,tip,Msg1,Msg2){
	var branchAdminOpt={};
	
	branchAdminOpt['branchadmin_startconcurrentbranch']  = {
			click:function(param){
				var curTask = param ? param.curTask : "";
				//var operationsRow = document.getElementById("operationsRow");
				// #127720 这里之所以用 td[id='operationsTDTitle']" 替代原来的 by id 查找是因为自由流的情况下有重复id
				var operationsTDTitle = query("td[id='operationsTDTitle']")[0];
				var operationsTDContent = query("td[id='operationsTDContent']")[0];
				var operationsRow=operationsTDTitle.parentNode;
				operationsTDTitle.innerHTML = Msg1['lbpmNode.processingNode.operationsTDTitle.restartconbranch'];
				
				var html="";
				if(!window.isInitLbpmNode){
					lbpm.globals.parseXMLObj();
				}
				if(!lbpm.nodes || lbpm.nodes.length == 0 || !curTask){
					return;
				}else{
					window.isInitLbpmNode = true;
				}
				var splitNode=this.getSplitNode(lbpm.nodes[curTask.nodeId]);
				var dataCheckbox = [];
				var kmssData = new KMSSData();
				kmssData.AddBeanData("getConBranchStatusService&type=startConcurrent&splitNode="+splitNode.id+"&processId="+lbpm.modelId);
				var data = kmssData.GetHashMapArray();
				var html="";
				for(var j=0;j<data.length;j++){
					dataCheckbox.push({value:data[j].id, text: data[j].name});
				}
				var html = '<div data-dojo-type="mui/form/CheckBoxGroup" id="restartConbranchsGroup" '
					+ 'data-dojo-props="validate:\'restartConbranchRequired required\',required:true,name:\'restartConbranchsGroup\'"></div>';
				if(dataCheckbox.length > 0){
					query("td[id='operationsTDContent']").html(html, {parseContent: true, onEnd: function() {
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
				}else{
					query('#operationsTDContent').html("无可启动的分支");
				}
				lbpm.globals.hiddenObject(operationsRow, false);
			},
			//获取启动分支节点
			getSplitNode:function(nowNode){
				for(var i=0;i<nowNode.startLines.length;i++){
					var lineObj=nowNode.startLines[i];
					var startNode = lineObj.startNode;
					if(!startNode){
						continue;
					}
					if(startNode.XMLNODENAME == "splitNode"){
						//考虑分支嵌套的情况，要跳过对应的启动分支
						if(window.routeJoinNodeRelatedNodeIds && window.routeJoinNodeRelatedNodeIds.indexOf(startNode.id) != -1){
							var result = this.getSplitNode(startNode);
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
						var result = this.getSplitNode(startNode);
						if(result == null){
							continue;
						}else{
							return result;
						}
					}
				}
				return null;
			},
			check:function(){
				var Checkbox_restartConbranchsGroup = registry.byId('restartConbranchsGroup');
				if(!Checkbox_restartConbranchsGroup){
					tip["warn"]({text:Msg2['lbpmProcess.joinnode.conbranch.restartbranch.check']});
					return false;
				}
				var val = Checkbox_restartConbranchsGroup.get('value');
				if(val == null || val == '') {
					tip["warn"]({text:Msg2['lbpmProcess.joinnode.conbranch.restartbranch.check']});
					return false;
				}
				return true;
			},
			setOperationParam: function(param){
				var Checkbox_restartConbranchsGroup = registry.byId('restartConbranchsGroup');
				var val = Checkbox_restartConbranchsGroup.get('value');
				param["restartConbranchs"] = val;
			}
	};	
	
	branchAdminOpt.init = function(){
		
	};
	return branchAdminOpt;
});