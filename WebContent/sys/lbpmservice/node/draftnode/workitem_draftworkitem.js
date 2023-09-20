lbpm.onLoadEvents.delay.push(function(){
	lbpm.globals.getOperationParameterJson("futureNodeId:draftDecidedFactIds"
		+":dayOfNotifyDrafter:hourOfNotifyDrafter:minuteOfNotifyDrafter"+":currNodeNextHandlersId:currNodeNextHandlersName"
		+":toRefuseThisNodeId:toRefuseThisHandlerIds:toRefuseThisHandlerNames:futureNodeId"); // 统一加载数据,减少请求
});
lbpm.onLoadEvents.delay.push(function(){
	lbpm.globals.showHandlerIdentityRow();
	lbpm.globals.showManualBranchNodeRow();
	var dayOfNotifyDrafterObj = $("#dayOfNotifyDrafter")[0];
	if(dayOfNotifyDrafterObj != null){
		var dayOfNotifyDrafterParam=lbpm.globals.getOperationParameterJson("dayOfNotifyDrafter");
		dayOfNotifyDrafterObj.value = (!dayOfNotifyDrafterParam || dayOfNotifyDrafterParam=="")?(lbpm.flowcharts["dayOfNotifyDrafter"]?lbpm.flowcharts["dayOfNotifyDrafter"]:"0"):dayOfNotifyDrafterParam;
	}
	var hourOfNotifyDrafter = $("#hourOfNotifyDrafter")[0];
	if(hourOfNotifyDrafter != null){
		var hourOfNotifyDrafterParam=lbpm.globals.getOperationParameterJson("hourOfNotifyDrafter");
		hourOfNotifyDrafter.value = (!hourOfNotifyDrafterParam || hourOfNotifyDrafterParam=="")?(lbpm.flowcharts["hourOfNotifyDrafter"]?lbpm.flowcharts["hourOfNotifyDrafter"]:"0"):hourOfNotifyDrafterParam;
	}
	var minuteOfNotifyDrafter = $("#minuteOfNotifyDrafter")[0];
	if(dayOfNotifyDrafterObj != null){
		var minuteOfNotifyDrafterParam=lbpm.globals.getOperationParameterJson("minuteOfNotifyDrafter");
		minuteOfNotifyDrafter.value = (!minuteOfNotifyDrafterParam || minuteOfNotifyDrafterParam=="")?(lbpm.flowcharts["minuteOfNotifyDrafter"]?lbpm.flowcharts["minuteOfNotifyDrafter"]:"0"):minuteOfNotifyDrafterParam;
	}
});
//显示草稿页面－提交身份的行
lbpm.globals.showHandlerIdentityRow=function(){
	if(lbpm.isInitIdentityRow){
		return;
	}
	lbpm.isInitIdentityRow = true;
	var defaultIdentity="";
	if(document.getElementsByName("sysWfBusinessForm.fdDefaultIdentity").length>0){
		defaultIdentity=document.getElementsByName("sysWfBusinessForm.fdDefaultIdentity")[0].value
	}
	var handlerIdentityRow = document.getElementById("handlerIdentityRow");
	var handlerIdentityIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
	var handlerIdentityNamesObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoNames");
	var handlerIdentityIds = handlerIdentityIdsObj.value;
	var rolesIdsArray = handlerIdentityIds.split(";");
	var handlerIdentityNames = handlerIdentityNamesObj.value;
	var handlerRoleInfoNames = lbpm.globals.getHandlerRoleInfoNamesByOrgConfig(handlerIdentityIds);
	if(handlerRoleInfoNames){
		handlerIdentityNames = handlerRoleInfoNames;
	}
	var rolesNamesArray = handlerIdentityNames.split(";");
	if(rolesIdsArray.length <= 1 && handlerIdentityRow != null){
		lbpm.globals.hiddenObject(handlerIdentityRow, true);
	}else{
		lbpm.globals.hiddenObject(handlerIdentityRow, false);
	}
	var rolesSelectObj = document.getElementsByName("rolesSelectObj")[0];
	if(rolesSelectObj != null){
		lbpm.constant.handlerIdentityIsSameDept = (function(rolesIds){
			var result = false;
			if(rolesIds && rolesIds.length==2){
				var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=getHandlerRoleInfoIsSameParent&ajax=true';
				var obj = {
					"handlerRoleInfoIds":rolesIds	
				}
				var result;
				var data = new KMSSData();
				data.AddHashMap(obj);
				data.SendToUrl(url, function(http_request) {
					if(http_request.responseText.indexOf("<error>") == -1){
						result = http_request.responseText=="true";
					}
				},false);
			}
			return result;
		})(rolesIdsArray);
		var fdIdentityId = document.getElementById("sysWfBusinessForm.fdIdentityId");
		var option = null;
	 	for(var i = 0; i < rolesIdsArray.length; i++){
			option = document.createElement("option");
			var rolesName = rolesNamesArray[i];
			var rolesId = rolesIdsArray[i];
			option.appendChild(document.createTextNode(rolesName));
			option.value=rolesId;
			if(fdIdentityId != null && fdIdentityId.value == rolesId) {
				option.selected = true;
			}
			if(lbpm.constant.ISINIT && defaultIdentity==rolesId){
				option.selected = true;
			}
			rolesSelectObj.appendChild(option);
	 	}
	 	$(rolesSelectObj).change(function(){
	 		lbpm.events.mainFrameSynch();
	 		lbpm.events.sysLbpmTableSynch();
	 	});
	}
	
	//提交人身份-开关控制
 	if (handlerIdentityRow != null &&
 			Lbpm_SettingInfo.isShowDraftsmanStatus == "false"){
 		lbpm.globals.hiddenObject(handlerIdentityRow, true);
 	}
};

lbpm.events.sysLbpmTableSynch=function(){
	var param={};
	lbpm.events.fireListener(lbpm.constant.EVENT_CHANGEROLE,param);
};

//显示草稿页面－由起草人选择人工决策节点的行
lbpm.globals.showManualBranchNodeRow=function(){
	//alert("showManualBranchNodeRow");
	var html = "";
	// 解析简版XML，查找由起草人决定分支的人工决策节点
	var draftParams = lbpm.globals.getOperationParameterJson("draftDecidedFactIds:toRefuseThisNodeId");
	var draftDecidedFactIds = [];
	if (draftParams['draftDecidedFactIds'] != null && draftParams['draftDecidedFactIds'] != '') {
		draftDecidedFactIds = $.parseJSON(draftParams['draftDecidedFactIds']);
	}
	var isToRefuseThis = (draftParams['toRefuseThisNodeId'] != null && draftParams['toRefuseThisNodeId'] != '');
	var isSkipUnchecked = draftDecidedFactIds.length > 0;
	$.each(lbpm.nodes, function(index, node) {
		if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH, node)
				&& node.decidedBranchOnDraft == "true") {
			var isUncheckedNode = true;
			// 获取当前语言
			var nodeDataLang = Com_Parameter.Lang;
			$.each(draftDecidedFactIds, function(_i, decidedNode) {
				if (decidedNode.NodeName == node.id) {
					isUncheckedNode = false;
					return false;
				}
			});
			if (isSkipUnchecked && isUncheckedNode)
				return;
			//多语言情况下
			if(node.hasOwnProperty("langs")){
				// 分别获取节点nodeData的语言并且对name值进行特殊处理
				var srcNodeData = node.langs;
				var srcNodeDataJson = "";
				if(srcNodeData!=null && srcNodeData!="" && isJSON(srcNodeData)){
					srcNodeDataJson = JSON.parse(srcNodeData);
					var nodes = srcNodeDataJson.nodeName;
					if(nodes!=null && nodes!=""){
						for(var k=0;k<nodes.length;k++){
							var srcNodeLang = nodes[k].lang.toLowerCase();
							if(nodeDataLang === srcNodeLang){
								node.name = nodes[k].value;
							}
						}
					}
					
				}
			}
			
			
			// 查找该节点的分支，拼装人工决策节点radio
			html += lbpm.globals.getNodeBranchesInfo(node, draftDecidedFactIds) + "<br/>";
		}
	});
	
	if (html == "") {
		// 不存在由起草人决定分支的人工决策节点则隐藏行
		lbpm.globals.hiddenObject(document.getElementById("manualBranchNodeRow"), true);
	} else {
		// 去除最后的<br/>
		html = html.substring(0, html.length - 5);
		var manualNodeSelectTD = document.getElementById("manualNodeSelectTD");
		if(manualNodeSelectTD != null){
			manualNodeSelectTD.innerHTML = html;
			if(!isToRefuseThis){
				var nextNodeTd=$("#nextNodeTD");
				if(nextNodeTd.length>0){
					var nextObj=lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
					if(nextObj && nextObj.decidedBranchOnDraft=='true'){
						$("#nextNodeTD")[0].innerHTML="";
						if ((nextObj.defaultBranch && nextObj.defaultBranch!="")){
							isSkipUnchecked = true;
						}
					}
				}
			}
		}
		lbpm.globals.hiddenObject(document.getElementById("manualBranchNodeRow"), isToRefuseThis);
		if (isSkipUnchecked){
			//lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDMANUAL, $("input[key='manualFutureNodeId']:checked").first());
			lbpm.globals.setNextBranchNodes($("input[key='manualFutureNodeId']:checked").first());
		}
	}
};
//获取人工决策节点分支信息（人工决策节点名称：分支信息1，分支信息2...）
lbpm.globals.getNodeBranchesInfo=function(nodeData, manualNodeSelect){
	// 找到这个人工决策节点选择的分支
	manualNodeSelect = manualNodeSelect || lbpm.globals.getSelectedManualNode();
	var checkedId = "";
	$.each(manualNodeSelect, function(index, json){
		if (json.NodeName == nodeData.id) {
			checkedId = json.NextRoute;
		}
	});
	var html = "";
	// 获取当前语言
	var lang = Com_Parameter.Lang;
	
	// 循环人工决策节点的每条分支
	$.each(nodeData.endLines, function(index, endLine) {
		// 过滤掉产生闭环的节点分支
		if (lbpm.globals.isClosedLoop(nodeData, endLine)) {
			return;
		}
		if(endLine.hasOwnProperty("langs")){
			// 分别获取连接线上的语言并且对name值进行特殊处理
			var srcEndLine = endLine.langs;
			var srcEndLineJson = "";
			if(srcEndLine!=null && srcEndLine!="" && isJSON(srcEndLine)){
				srcEndLineJson = JSON.parse(srcEndLine);
				for(var j=0;j<srcEndLineJson.length;j++){
					var srcEndLineLang = srcEndLineJson[j].lang.toLowerCase();
					if(lang === srcEndLineLang){
						endLine.name = srcEndLineJson[j].value;
					}
				}
			}
		}
		if(endLine.endNode.hasOwnProperty("langs")){
			
			// 分别获取连接线上的节点语言并且对name值进行特殊处理
			var srcEndNodeData = endLine.endNode.langs;
			var srcEndNodeJson = "";
			if(srcEndNodeData!=null && srcEndNodeData!="" && isJSON(srcEndNodeData)){
				srcEndNodeJson = JSON.parse(srcEndNodeData);
				var endNodes = srcEndNodeJson.nodeName;
				if(endNodes!=null && endNodes!=""){
					for(var i=0;i<endNodes.length;i++){
						var srcEndNodeLang = endNodes[i].lang.toLowerCase();
						if(lang === srcEndNodeLang){
							endLine.endNode.name = endNodes[i].value;
						}
					}
				}
			}
		}
		
		var radioName = endLine.name;
		if (radioName == null) {
			radioName = endLine.endNode.id + "." + endLine.endNode.name;
		} else {
			radioName = radioName + "(" + endLine.endNode.id + "." + endLine.endNode.name + ")";
		}
		html += "<label style='margin-right:7px;' class='lui-lbpm-radio'><input type='radio' manualBranchNodeId='"+nodeData.id+"' key='manualFutureNodeId' name='manualFutureNodeId_"+nodeData.id+"'" 
			+ " value='" + endLine.endNode.id + "' "
		 	+ " onclick='lbpm.globals.setNextBranchNodes(this)'" 
			+ ((checkedId == endLine.endNode.id || (checkedId == "" && endLine.id == nodeData.defaultBranch)) ? 'checked' : '') + " />";
	    html += "<b class='radio-label'>" + radioName + "</b></label>";
	});
	if (html != "") {
		html = "<b>" + nodeData.id + "." + nodeData.name + "：</b>" + html;
	}
	return html;
};
//判断是否是json
function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            console.log('error：'+str+'!!!'+e);
            return false;
        }
    }
    console.log('It is not a string!')
}

// 判断人工决策节点分支是否会产生闭环
lbpm.globals.isClosedLoop=function(nodeData, endline) {
	var isClosedLoop = false;
	var nodeArray = new Array();
	nodeArray.push(endline.endNode);
	// 已查找节点，避免死循环
	var searchedNodeArray = new Array();
	outer:
	while (nodeArray.length > 0) {
		for (var i = 0, size = nodeArray.length; i < size; i++) {
			var node = nodeArray[i];
			searchedNodeArray.push(node);
			if (node.id == nodeData.id) {
				isClosedLoop = true;
				break outer;
			}
		}
		nodeArray = lbpm.globals.getNextNodesByManual(nodeArray);
		// 过滤已查找节点
		nodeArray = lbpm.globals.filterSearchedNodes(nodeArray, searchedNodeArray);
	}
	return isClosedLoop;
};

// 根据选择过滤不会走到的人工决策节点
lbpm.globals.setNextBranchNodes=function(curObj){
	var html = "";
	var manualNodeArray = lbpm.globals.getManualNodeArray();
	$.each(manualNodeArray, function(index, nodeData) {
		html += lbpm.globals.getNodeBranchesInfo(nodeData) + "<br/>";
	});
	html = html.substring(0, html.length - 5);
	document.getElementById("manualNodeSelectTD").innerHTML = html;
	lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDMANUAL,curObj);
};


//遍历流程图找到会走到的人工决策节点
lbpm.globals.getManualNodeArray=function(){
	var manualNodeArray = new Array();
	// 开始节点
	var nodeArray = new Array();
	nodeArray.push(lbpm.nodes['N1']);
	// 已查找节点，避免死循环
	var searchedNodeArray = new Array();
	// 遍历流程图
	while (nodeArray.length > 0) {
		$.each(nodeArray, function(index, node) {
			searchedNodeArray.push(node);
			if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH, node)
					&& node.decidedBranchOnDraft == "true") {
				manualNodeArray.push(node);
			}
		});
		nodeArray = lbpm.globals.getNextNodesByManual(nodeArray);
		// 过滤已查找节点
		nodeArray = lbpm.globals.filterSearchedNodes(nodeArray, searchedNodeArray);
	}
	return manualNodeArray;
};

//过滤已查找节点
lbpm.globals.filterSearchedNodes=function(nodeArray, searchedNodeArray) {
	var filterNodes = new Array();
	$.each(nodeArray, function(i, node) {
		var searchedFlag = false;
		$.each(searchedNodeArray, function(j, searchedNode){
			if (node.id == searchedNode.id) {
				searchedFlag = true;
				return false;
			}
		});
		if (!searchedFlag) {
			filterNodes.push(node);
		}
	});
	return filterNodes;
};


// 获取节点的可以走到的下一个节点
lbpm.globals.getNextNodesByManual=function(nodeArray) {
	var manualNodeSelect = lbpm.globals.getSelectedManualNode();
	var nextNodes = new Array();
	$.each(nodeArray, function(i, node) {
		// 人工决策节点过滤分支
		if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH, node)
				&& node.decidedBranchOnDraft == "true") {
			var nextRoute = "";
		    // 查找该人工决策节点是否决定走哪条分支
			$.each(manualNodeSelect, function(j, json){
				if (json.NodeName == node.id) {
					nextRoute = json.NextRoute;
					return false;
				}
			});
			// 遍历连线，过滤分支
			$.each(node.endLines, function(k, line) {
				// 决定走哪条分支
				if (nextRoute != "") {
					if (line.endNode.id == nextRoute) {
						lbpm.globals.pushNode(nextNodes, line.endNode);
						return false;
					}
				} else {
					lbpm.globals.pushNode(nextNodes, line.endNode);
				}
			});
		} else {
			$.each(node.endLines, function(k, line) {
				lbpm.globals.pushNode(nextNodes, line.endNode);
			});
		}
	});
	return nextNodes;
};

// 不加入相同的节点
lbpm.globals.pushNode=function(nextNodes, nodeToPush) {
	var flag = true;
	$.each(nextNodes, function(i, node) {
		if (node.id == nodeToPush.id) {
			flag = false;
			return false;
		}
	});
	if (flag) {
		nextNodes.push(nodeToPush);
	}
};

//获取已经选择了分支的人工决策节点（[{NodeName:N3, NextRoute:N4},{NodeName:N9, NextRoute:N11}]）
lbpm.globals.getSelectedManualNode=function(){
	var manualNodeSelect = new Array();
	$("input[key='manualFutureNodeId']:checked").each(function(index, input){
    	var json = {};
    	input = $(input);
		json.NodeName = input.attr('manualBranchNodeId');
		json.NextRoute = input.val();
		manualNodeSelect.push(json);
    });
	return manualNodeSelect;
};

//判断是否所有的人工决策节点都选择了要走的分支
lbpm.globals.isSelectAllManualNode=function(){
	var isSelectAll = true;
	$.each($("input[key='manualFutureNodeId']"), function(index, input){
		isSelectAll = false;
		var radioes = $("input[manualBranchNodeId='"+$(input).attr('manualBranchNodeId')+"']");
		$.each(radioes, function(j, radio){
    		if(radio.checked) {
        		isSelectAll = true;
        		return false;
    		}
    	});
    	if(!isSelectAll) {
        	return false;
        }
    });
	return isSelectAll;
};