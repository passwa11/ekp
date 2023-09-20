//页面打开更新节点信息，判断是追加或者替换模式 
lbpm.globals.initNodeInfoByOperatorMode=function(){
	//当前节点的下一个节点
	var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
	
	//获取当前节点到下一个节点的路由线路信息
	var routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(lbpm.globals.getCurrentNodeObj(),nextNodeObj, true);
	
	
	var noCalcResult = false;
	// 即将流向过滤并行分支节点不走的分支
	if((nextNodeObj.nodeDescType == "splitNodeDesc" && nextNodeObj.splitType != "custom") || nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
		var filterRouteLine = new Array();
		lbpm.globals.getThroughNodes(function(throughtNodes){
			var throughtIds = lbpm.globals.getIdsByNodes(throughtNodes)+",";
			for(var i=0;i<routeLines.length;i++){
				var lineLinkNode = routeLines[i].startNodeId+","+routeLines[i].endNodeId+",";
				if(throughtIds.indexOf(lineLinkNode)>-1){
					filterRouteLine.push(routeLines[i]);
				}
			}
		},null,null,false);
		routeLines = filterRouteLine;
		if(nextNodeObj.nodeDescType=="autoBranchNodeDesc" && filterRouteLine.length != 1){
			noCalcResult = true;
			routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(lbpm.globals.getCurrentNodeObj(),nextNodeObj, false);
		}
	}

	//获取所有节点
	var rtnNodeArray = new Array();
	$.each(lbpm.nodes, function(index, node) {
		if(!lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_START,node) && !lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,node) && !lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,node)){
			rtnNodeArray.push(node);
		}
	});
	rtnNodeArray.sort(lbpm.globals.getNodeSorter());
	
	
	var param = {};
		lbpm.globals.getThroughNodes(function(throughtNodes){
			param = {throughtNodes:throughtNodes};
			},
			function (){
			},
			function(){
				
			},
			false
	);
	
	
	var throughNodesStr = lbpm.globals.getIdsByNodes(param.throughtNodes);
	
	for(var i = 0; i < rtnNodeArray.length; i++){
		if(checkModifyNodeAuthorization(lbpm.globals.getNodeObj(lbpm.nowNodeId), rtnNodeArray[i].id,throughNodesStr)){
			var nodeInfo=lbpm.globals.getNodeInfoByProcessIdAndNodeId(lbpm.modelId,rtnNodeArray[i].id);
			
			if(nodeInfo!=null&&nodeInfo.processPersonModel!=null&&nodeInfo.processPersonModel==lbpm.constant.PROCESS_PERSON_MODEL){
				lbpm.nodes[rtnNodeArray[i].id].handlerIds="";
				lbpm.nodes[rtnNodeArray[i].id].handlerNames="";
			}
		}
	}

	
	$.each(routeLines, function(i, lineObj) {
		var nodeObj=lineObj.endNode;
		var nodeInfo=lbpm.globals.getNodeInfoByProcessIdAndNodeId(lbpm.modelId,nodeObj.id);
		
		//为追加模式，清空值，重新设置 2019/04/19 洪健新增
		if(nodeInfo!=null&&nodeInfo.processPersonModel!=null&&nodeInfo.processPersonModel==lbpm.constant.PROCESS_PERSON_MODEL){
			handlerIds="";
			handlerNames="";
		}
	});
};

/**
 * 当前js里面判断节点是否修改
 */
checkModifyNodeAuthorization=function(nodeObj, allowModifyNodeId,throughNodesStr){
	var index, nodeIds;
	throughNodesStr+=",";
	//如果要修改的节点不在当前计算后应该出现的节点中（及自动决策将流向的分支）这不出现在待修改列表中
	if(throughNodesStr.indexOf(allowModifyNodeId+",") == -1){
		
		return false;
	}
	if(nodeObj.canModifyHandlerNodeIds != null && nodeObj.canModifyHandlerNodeIds != ""){
		nodeIds = nodeObj.canModifyHandlerNodeIds + ";";
		index = nodeIds.indexOf(allowModifyNodeId + ";");
		if(index != -1){
			return true;
		}
	}
	if(nodeObj.mustModifyHandlerNodeIds != null && nodeObj.mustModifyHandlerNodeIds != ""){
		nodeIds = nodeObj.mustModifyHandlerNodeIds + ";";
		index = nodeIds.indexOf(allowModifyNodeId + ";");
		if(index != -1){
			return true;
		}
	}

	return false;
};
