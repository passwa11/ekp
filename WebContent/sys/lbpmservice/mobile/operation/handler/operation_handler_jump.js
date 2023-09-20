define(['dojo/query', "dojo/store/Memory", "dijit/registry", "mui/dialog/Tip"],
	function(query, Memory, registry, tip) {
		lbpm.operations['handler_jump'] = {
			click : OperationClick,
			check : OperationCheck,
			blur : OperationBlur,
			setOperationParam : setOperationParam
		};
		
		function OperationBlur() {
			lbpm.globals.clearDefaultUsageContent('handler_jump');
		}
		
		function OperationClick(operationName) {
			lbpm.globals.setDefaultUsageContent('handler_jump');
			var operationsRow = document.getElementById("operationsRow");
			var operationsTDTitle = document.getElementById("operationsTDTitle");
			operationsTDTitle.innerHTML = lbpm.workitem.constant.handlerOperationTypeJump.replace("{jump}", operationName);
	
			// 增加驳回节点重复过滤
			var currNodeInfo = lbpm.globals.getCurrentNodeObj();
			var currNodeId = currNodeInfo.id;
			
			//驳回的节点是否在分支内
			lbpm.globals._isJumpNodeInJoin = false;

			var availableNodes = getAvailableNodes();
			var canHandlerJumpNodeIds=[];
			if(currNodeInfo.canHandlerJumpNodeIds){
				canHandlerJumpNodeIds=currNodeInfo.canHandlerJumpNodeIds.split(";");
			}	
			var jumpNodes = getJumpNodes(currNodeInfo);
			var isHideAllNodeIdentifier = _isHideAllNodeIdentifier();
			if (availableNodes && availableNodes.length) {
				var html = '<div id="jumpToNodeIdSelectObj" data-dojo-type="mui/form/Select" key="jumpToNodeId" '
						+ 'data-dojo-props="validate:\'jumpRequired required\',required:true,name:\'jumpToNodeIdSelectObj\', value:\'\', mul:false" ></div>';
				// 驳回后流经的子流程重新流转选项html
				var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
				if (isPassedSubprocessNode) {
					html += "<div class='isRecoverPassedSubprocessLabel'>";
					html += '<div id="isRecoverPassedSubprocess" alertText="" key="isRecoverPassedSubprocess" data-dojo-type="mui/form/CheckBox"';
					html += ' data-dojo-props="name:\'isRecoverPassedSubprocess\', value:\'true\', mul:false, text:\'';
					html += lbpm.workitem.constant.handlerOperationTypeRefuse_abandonSubprocess.replace("{refuse}", operationName) + '\'';
					html += '"></div></div>';
				}
				var data = [];
				for (var i = 0; i < availableNodes.length; i++) {
					var nodeInfo = availableNodes[i];
					if(canHandlerJumpNodeIds.length>0 && canHandlerJumpNodeIds.indexOf(nodeInfo.id)< 0 ){
						continue;
					}					
					// 过滤并行分支中的节点
					if (!containNode(jumpNodes, nodeInfo))
						continue;
					var langNodeName = WorkFlow_getLangLabel(nodeInfo.name,nodeInfo["langs"],"nodeName");
					var itemShowStr = isHideAllNodeIdentifier ? langNodeName : (nodeInfo.id + "." + langNodeName);
					if(nodeInfo.handlerSelectType == 'org'){
						if(nodeInfo.handlerNames) {
							itemShowStr += "(" + nodeInfo.handlerNames+ ")";
						} else {
							itemShowStr += "(" + lbpm.workitem.constant.COMMONNODEHANDLERORGEMPTY+ ")";
						}
					} else if (nodeInfo.handlerSelectType != null)  {
						itemShowStr += "(" + lbpm.workitem.constant.COMMONLABELFORMULASHOW + ")";
					}
					data.push({
						text : itemShowStr,
						value : nodeInfo.id
					});
				}
				if(data.length>0){
					query("#operationsTDContent")
					.html(html,{
								parseContent : true,
								onEnd : function() {
									var rtn = this.inherited("onEnd", arguments);
									var thenFun = function(results) {
										var jumpToNodeIdSelectObj = registry.byId('jumpToNodeIdSelectObj');
										jumpToNodeIdSelectObj.setStore(new Memory(
											{
												data : data
											}));
										if(data.length>0){
											if(lbpm.isFreeFlow){
												jumpToNodeIdSelectObj.set("value", data[0].value);
											}else{
												jumpToNodeIdSelectObj.set("value", data[data.length - 1].value);
											}
										}
									};
									if (this.parseDeferred) {
										this.parseDeferred.then(thenFun);
									}
								}
							});
				}else {
					query("#operationsTDContent").html(lbpm.workitem.constant.noRefuseNode.replace("{jump}", operationName) + '<input type="hidden" alertText="' + lbpm.workitem.constant.noRefuseNode.replace("{jump}", operationName) + '" key="jumpToNodeId">');
				}
			} else {
				query("#operationsTDContent")
					.html(lbpm.workitem.constant.noRefuseNode.replace("{jump}", operationName) + '<input type="hidden" alertText="' + lbpm.workitem.constant.noRefuseNode.replace("{jump}", operationName) + '" key="jumpToNodeId">');
			}
			lbpm.globals.hiddenObject(operationsRow, false);
		}
		function _isHideAllNodeIdentifier(){
			var isHideAllNodeIdentifier = false;
			if (lbpm && lbpm.settingInfo && lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType  === "isHideAllNodeIdentifier"){
				isHideAllNodeIdentifier = true;
			}
			return isHideAllNodeIdentifier;
		}
		function OperationCheck() {
			var val = query("#operationsTDContent [name='jumpToNodeIdSelectObj']").val();
			if (val == null || val == '') {
				tip["warn"]({text:lbpm.workitem.constant.noRefuseNode.replace("{jump}", lbpm.currentOperationName)});
				//alert(lbpm.workitem.constant.noRefuseNode.replace("{jump}", lbpm.currentOperationName));
				return false;
			}
			if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
				return lbpm.globals.validateMustSignYourSuggestion();
			}
			return true;
		}
		
		function setOperationParam() {
			var jumpStr = query("#operationsTDContent [name='jumpToNodeIdSelectObj']").val();
			var jumpArr = jumpStr.split(":");
			lbpm.globals.setOperationParameterJson(jumpArr[0], "jumpToNodeId", "param");
			if (jumpArr.length > 1) {
				lbpm.globals.setOperationParameterJson(jumpArr[1], "jumpToNodeInstanceId", "param");
			} else {
				lbpm.globals.setOperationParameterJson("", "jumpToNodeInstanceId", "param");
			}
			var nodeName=lbpm.nodes[jumpArr[0]].name;
			lbpm.globals.setOperationParameterJson(nodeName, "jumpToNodeName", "param");
			var isRecoverPassedSubprocess = registry.byId('isRecoverPassedSubprocess');
			if (isRecoverPassedSubprocess) {
				// 子流程
				lbpm.globals.setOperationParameterJson(isRecoverPassedSubprocess.checked, "isRecoverPassedSubprocess", "param");
			}
		}
		
		//取得有效的节点
		function getAvailableNodes(){
			var availableNodes = [];
			lbpm.globals.setNodeLevel();
			$.each(lbpm.nodes, function(index, nodeObj) {
				if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_START,nodeObj) 
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,nodeObj) 
						|| lbpm.nowNodeId == nodeObj.id 
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_AUTOBRANCH,nodeObj) 
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,nodeObj)
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SPLIT,nodeObj)
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_JOIN,nodeObj)
						|| nodeObj.Status == lbpm.constant.STATUS_RUNNING
						|| nodeObj.endLines.length == 0 || nodeObj.startLines.length == 0){
					;
				} else {
					availableNodes.push(nodeObj);
				}
			});
			availableNodes.sort(function (a, b) {
				var na = a.level;//parseInt(a.id.substring(1));
    			var nb = b.level;//parseInt(b.id.substring(1));
    			return na - nb;
			});
			return availableNodes;
		}
		
		function getJumpNodes(curr) {
			var nodes1 = [];
			nodes1 = _findNextNodes(curr, nodes1);
			if(lbpm.isFreeFlow){
				return nodes1;
			}
			var nodes2 = [];
			nodes2 = _findPreNodes(curr, nodes2, true);
			var nodes = [];
			nodes = mergeArray(nodes1,nodes2)
			return nodes;
		}
		
		function mergeArray(arr1, arr2){ 
  			for (var i = 0 ; i < arr1.length ; i ++ ){
      			for(var j = 0 ; j < arr2.length ; j ++ ){
       				if (arr1[i] === arr2[j]){
         				arr1.splice(i,1);
    				}
    		 	}
  			}
  			for(var i = 0; i < arr2.length; i ++){
  				arr1.push(arr2[i]);
  			}
  			return arr1;
 		 }
		
		function _findPreNodes(curr, nodes, isInit) {
			var pres = lbpm.globals.getPreviousNodeObjs(curr.id);
			for (var i = 0; i < pres.length; i ++) {
				var pNode = pres[i];
				if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_START,pNode)) {
				    break;
				}else if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_GROUPSTART,pNode)){
					pNode = lbpm.globals.getPreviousNodeObj(pNode.groupNodeId);
				}
				// 往上遍历节点时，若一开始的curr是结束并行分支节点，只沿对应的流入分支往上遍历，不经其它分支
				if (isInit && lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_JOIN,curr)) {
					var preNodeOfJoin = findPreNodeOfJoin(curr);
					if (preNodeOfJoin != null && preNodeOfJoin.id != pNode.id) {
						continue;
					}
				}
				if (containNode(nodes, pNode)) {
					continue;
				}
				nodes.push(pNode);
				
				_findPreNodes(pNode, nodes);
				
				if(isCommonDecisionNode(pNode)){// 可以跳到非并发分支
					_findNextNodes(pNode, nodes);
				}
			}
			return nodes;
		}
		
		function _findNextNodes(curr, nodes) {
			var nexts = lbpm.globals.getNextNodeObjs(curr.id);
			for (var i = 0; i < nexts.length; i ++) {
				var nNode = nexts[i];
				if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,nNode)) {
					continue;
				}else if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_GROUPEND,nNode)){
					nNode = lbpm.globals.getNextNodeObj(nNode.groupNodeId);
				}
				if (containNode(nodes, nNode)) {
					continue;
				}
				nodes.push(nNode);
				_findNextNodes(nNode, nodes);
			}
			return nodes;
		}
		
		function containNode(nodes, node) {
			for (var n = 0; n < nodes.length; n ++) {
				if (node.id == nodes[n].id) {
					return true;
				}
			}
			return false;
		}
		
		function isCommonDecisionNode(node) {
			var nodeDesc = lbpm.nodedescs[node.nodeDescType];
			return nodeDesc.isBranch(node) && !nodeDesc.isConcurrent(node);
		}
		
		function findPreNodeOfJoin(join) {
			// 从事务参数中找出当前结束并行分支节点对应的流入分支源头，并调用getPreNodeOfJoinFromSource得出preNodeOfJoin的值（preNodeOfJoin代表的就是目标流入分支）
			if (lbpm.nowProcessorInfoObj.parameter) {
				var processInfoParamJson = JSON.parse(lbpm.nowProcessorInfoObj.parameter);
				if (processInfoParamJson.conBranchSourceId) {
					var conBranchSourceId = processInfoParamJson.conBranchSourceId;
					if (conBranchSourceId.indexOf("L") == 0) {
						if (lbpm.lines[conBranchSourceId]) {
							conBranchSourceId = lbpm.lines[conBranchSourceId].endNode.id;
						}
					}
					if (lbpm.nodes[conBranchSourceId]) {
						return getPreNodeOfJoinFromSource(join,lbpm.nodes[conBranchSourceId]);
					}
				}
			}
		}
		
		// 根据分支源头标识找出由该流入分支上结束并行分支节点的上一个节点
		function getPreNodeOfJoinFromSource(join, sourceNodeObj, passed) {
			if (passed == null) {
				passed = new Array();
			}
			if (!containNode(passed, sourceNodeObj)) {
				passed.push(sourceNodeObj);
				for (var i = 0; i < sourceNodeObj.endLines.length; i ++) {
					var line = sourceNodeObj.endLines[i];
					if (line.endNode == join) {
						return line.startNode;
					} else {
						var perNodeOfJoin = getPreNodeOfJoinFromSource(join, line.endNode, passed);
						if (perNodeOfJoin != null) {
							return perNodeOfJoin;
						}
					}
				}
			}
		}
		
		function _getSubString(str, len) {  
			var strlen = 0;  
			var s = "";  
			for (var i = 0; i < str.length; i++) {  
				if (str.charCodeAt(i) > 128) {  
					strlen += 2;  
				} else {  
					strlen++;  
				}  
				s += str.charAt(i);  
				if (strlen >= len) {  
					return s+"...";  
				}  
			}  
			return s;  
		}  

		function _getStringLength(str) {  
			var strlen = 0;  
			for (var i = 0; i < str.length; i++) {  
				if (str.charCodeAt(i) > 128) {  
					strlen += 2;  
				} else {  
					strlen++;  
				}
			}
			return strlen;
		}
	});
