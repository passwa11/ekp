//<--------------自由流JS------------->
//引入jquery-ui，排序用，有的模块没引会报错，这里再引一次
Com_IncludeFile('jquery.ui.js', 'js/jquery-ui/');
lbpm.freeFlow = new Array();
lbpm.freeFlow.defOperRefId = null;
lbpm.freeFlow.defOperRefSignId = null;
lbpm.freeFlow.defFlowPopedom = null;

//自由流添加节点，弹出类型，人员选择框
lbpm.globals.beforeAddNodeInFreeFlow=function(nodeType,beginId) {
	if(!lbpm.globals.isCanEdit()){
		return;
	}
	var FlowChartObject = getFreeFlowChartObject();
	if (window.LUI) {
		seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
			var page = "/sys/lbpm/flowchart/page/addfreeflow.jsp?notTitle=true&nodeType="+nodeType;
			var title = Data_GetResourceString("sys-lbpmservice:lbpmservice.freeflow.addTitle");
			if(nodeType=="signNode"){
				title += Data_GetResourceString("sys-lbpmservice:lbpm.nodeType.signNode");
			}else if(nodeType=="sendNode"){
				title += Data_GetResourceString("sys-lbpmservice:lbpm.nodeType.sendNode");
			}else{
				title += Data_GetResourceString("sys-lbpmservice:lbpm.nodeType.reviewNode");
			}
			var modelName = lbpm.modelName;
			if(Lbpm_SettingInfo["isBusinessAuthEnabled"] == "true"
				&& nodeType =="reviewNode"
				&& (modelName == "com.landray.kmss.km.imissive.model.KmImissiveSendMain"
					||modelName == "com.landray.kmss.km.imissive.model.KmImissiveReceiveMain"
					||modelName == "com.landray.kmss.km.imissive.model.KmImissiveSignMain")){
                Dialog_Address(true, 'handlerIds_imissive', 'handlerNames_imissive', ';', ORG_TYPE_POSTORPERSON|ORG_TYPE_ROLE,function(str){
                    $("[name='handlerIds_imissive']").val("");
                    $("[name='handlerNames_imissive']").val("");
                    var handlerData = {};
                    var handlerShow = [];
                    let ruleVal = str.data;
                    if(ruleVal.length > 0){
                        let handlerId = '';
                        let handlerName = '';
                        for(var i=0;i<ruleVal.length;i++) {
                            var handler = {};
                            handler['id']=ruleVal[i].id;
                            handler['name']=ruleVal[i].name;
                            handlerShow.push(handler);
                            handlerId +=ruleVal[i].id+";";
                            handlerName +=ruleVal[i].name+";";
                        }
                        handlerId = handlerId.substring(0, handlerId.length - 1);
                        handlerName = handlerName.substring(0, handlerName.length - 1);
                        handlerData['id']=handlerId;
                        handlerData['name']=handlerName;
                        handlerData = JSON.stringify(handlerData);
                        handlerShow = JSON.stringify(handlerShow);

                        var widthGw = 700;
                        var heightGw = 320;
                        var nodeGwSize ='false';
						var sizegw = selectRule(handlerId);
                        if(ruleVal.length == 1 && sizegw == 1){
							widthGw = -100;
							heightGw = -100;
							nodeGwSize = 'true';
						}
                        page = "/sys/lbpm/flowchart/page/imissiveAddFreeFlow.jsp?notTitle=true&nodeType="+nodeType+"&modelName="+lbpm.modelName+"&handlerData="+handlerData+"&handlerShow="+handlerShow+"&nodeGwSize="+nodeGwSize;
                        dialog.iframe(page,title,function(dataParam){
                            lbpm.globals.addNodeInFreeFlow(dataParam,nodeType,beginId);
                        },{width : widthGw,height : heightGw,cahce : false});
                    }
                });
                return;
            }
			dialog.iframe(page,title,function(dataParam){
				lbpm.globals.addNodeInFreeFlow(dataParam,nodeType,beginId);
			},{width : 500,height : 320,cahce : false});
		});
	}else{;
		FlowChartObject.Nodes.beforeCreateNodeInFreeFlow(nodeType,function(dataParam){
			lbpm.globals.addNodeInFreeFlow(dataParam,nodeType,beginId);
		});
	}
}

function selectRule(handlerId){
	if(handlerId){
		var size = 0;
		var urlPath = Com_Parameter.ContextPath + "km/imissive/km_imissive_rule/kmImissiveRule.do?method=findRuleListMobile";
		$.ajax({
			url: urlPath,
			async: false,
			data:{'fdImissiveType':lbpm.modelName,"handlerIds":handlerId,"handlerType":'3'},
			type: "POST",
			dataType: 'json',
			success: function (data) {
				console.log(data);
				if(data.length > 0){
					size =data.length;
				}
			},
			error: function (er) {

			}
		});
		return size;
	}
}

// 选人添加节点(地址本返回值，节点类型（默认审批节点），开始节点Id)
lbpm.globals.addNodeInFreeFlow=function(dataParam,nodeType,beginId) {
	if (dataParam){
		nodeType = nodeType||"reviewNode";
		if(nodeType=="reviewNode"){
			if (!lbpm.freeFlow.defOperRefId) {
				var data = new KMSSData();
				data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowReviewNode");
				data = data.GetHashMapArray();
				for(var j=0;j<data.length;j++){
					if(data[j].isDefault=="true"){
						lbpm.freeFlow.defOperRefId = data[j].value;
						break;
					}
				}
			}
		}else if(nodeType=="signNode"){
			if (!lbpm.freeFlow.defOperRefSignId) {
				var data = new KMSSData();
				data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowSignNode");
				data = data.GetHashMapArray();
				for(var j=0;j<data.length;j++){
					if(data[j].isDefault=="true"){
						lbpm.freeFlow.defOperRefSignId = data[j].value;
						break;
					}
				}
			}
		}
		if(!lbpm.freeFlow.defFlowPopedom){
			lbpm.freeFlow.defFlowPopedom = (Lbpm_SettingInfo["defaultFlowPopedom"] > lbpm.nodes[lbpm.nowNodeId].flowPopedom)?lbpm.nodes[lbpm.nowNodeId].flowPopedom:Lbpm_SettingInfo["defaultFlowPopedom"];
		}
		var FlowChartObject = getFreeFlowChartObject();
		var newNodeObj,beginNode;
		if(beginId){
			beginNode = FlowChartObject.Nodes.GetNodeById(beginId);
		}else{
			var endNodeObj = FlowChartObject.Nodes.GetNodeById("N3");
			beginNode = endNodeObj.LineIn[0].StartNode;
		}
		var nodeList = [];
		if(beginNode.Type == "splitNode"){
			nodeList = FlowChartObject.Nodes.spitNodecreateFreeFlow(beginNode,nodeType,true,dataParam)
		}else{
			nodeList = FlowChartObject.Nodes._CreateFreeFlowSpitMore(dataParam,beginNode,nodeType,true);
		}
		for(var i=0;i<nodeList.length;i++){
			var newNodeObj = nodeList[i];
			lbpm.myAddedNodes.push(newNodeObj.Data.id);
			if(newNodeObj.Type=="reviewNode"){
				newNodeObj.Data["operations"]["refId"]=lbpm.freeFlow.defOperRefId;
			}else if(newNodeObj.Type=="signNode"){
				newNodeObj.Data["operations"]["refId"]=lbpm.freeFlow.defOperRefSignId;
			}
			if(newNodeObj.Type=="reviewNode"||newNodeObj.Type=="signNode"){
				newNodeObj.Data["flowPopedom"]=lbpm.freeFlow.defFlowPopedom;
				newNodeObj.Data["canAddAuditNoteAtt"]=Lbpm_SettingInfo["isCanAddAuditNoteAtt"];
				newNodeObj.Data["canModifyMainDoc"]=Lbpm_SettingInfo["isEditMainDocument"];
				newNodeObj.Data["processType"]="2";
			}
			newNodeObj.Data["notifyType"]=Lbpm_SettingInfo["defaultNotifyType"];
		}
		beginNode = newNodeObj;
		//更新流程图
		lbpm.globals.reflushFreeFlow();
		//更新节点列表
		lbpm.globals.updateFreeFlowNodeUL();
		//新增节点默认选中
		$(".lbpm_freeflow_draglist_li[data-node-id='"+nodeList[0].Data.id+"']").click();
		if(!beginId){
			$('.lbpm_freeflow_draglist').scrollTop($('.lbpm_freeflow_draglist')[0].scrollHeight);
		}
	}
}

// 自由流行的节点构建
function appendFreeFlowNodeLI(nodeObj, html, joinNodeId, parentJoinNodeId){
	if(nodeObj.XMLNODENAME == "splitNode" && !joinNodeId){
		//若为并行分支，构建将并行分支后面至其结束节点放到一个ul里
		html.push("<ul class='lbpm_freeflow_draglist_split' data-split-id='"+nodeObj.id+"'>");
		var relatedNodeId = nodeObj.relatedNodeIds;
		//循环并行分支
		appendFreeFlowNodeLI(nodeObj, html, relatedNodeId);
		nodeObj = lbpm.nodes[relatedNodeId].startLines[0].startNode;
		joinNodeId = parentJoinNodeId || "";
		parentJoinNodeId = "";
		//创建并行分支结束节点
		appendFreeFlowNodeLI(nodeObj, html, joinNodeId);
		nodeObj = lbpm.nodes[relatedNodeId];
		html.push("</ul>");
	}
	for(var h = 0 ;h<nodeObj.endLines.length;h++){
		//并行分支每条线放在一个子ul中，拖动排序不相影响
		if(nodeObj.XMLNODENAME == "splitNode"){
			html.push("<ul class='lbpm_freeflow_draglist_sub'>");
		}
		var nodeId = nodeObj.endLines[h].endNode.id;
		if(nodeObj.endLines[h].endNode.XMLNODENAME == "joinNode" && nodeId == joinNodeId){
			html.push("</ul>");
		}
		if (nodeId == "N3" || nodeId == joinNodeId) {
			return;
		}
		var node = lbpm.nodes[nodeId];
		var cleanJoinNodeId = false;
		if(node.XMLNODENAME == "splitNode" && joinNodeId){
			parentJoinNodeId = joinNodeId;
			cleanJoinNodeId = true;
		}
		var handlerNames = "";
		var handlerDisplayNames = "";
		if(nodeId == "N2"){
			handlerNames = handlerDisplayNames = $("[name='sysWfBusinessForm.fdDraftorName']").val();
		}
		if (node.handlerNames) {
			var handlerIds = node.handlerIds==null?"":node.handlerIds;
			handlerNames = node.handlerNames;
			var dataNextNodeHandler;
			var nextNodeHandlerNames4View="";
			if(node.handlerSelectType){
				if (node.handlerSelectType=="formula") {
					dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,node));
				} else if (node.handlerSelectType=="matrix") {
					dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,node));
				} else if (node.handlerSelectType=="rule") {
					dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(node.id,handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,node));
				} else {
					dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,node));
				}
				for(var j=0;j<dataNextNodeHandler.length;j++){
					if(nextNodeHandlerNames4View==""){
						nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
					}else{
						nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
					}
				}
			}
			if(nextNodeHandlerNames4View == "" && node.handlerIds != null) {
				nextNodeHandlerNames4View = lbpm.constant.COMMONNODEHANDLERORGNULL;
			}
			if(nextNodeHandlerNames4View){
				handlerNames = nextNodeHandlerNames4View;
			}
			var namesArray = handlerNames.split(";");
			// 根据流转方式决定分隔符的具体显示
			var delimiter = ";";
			if (node.processType == "1") {
				delimiter = "/";
			} else if (node.processType == "2") {
				delimiter = "+";
			}
			if (delimiter!=";") {
				handlerNames = handlerNames.replace(/;/g,delimiter);
			}
			// 控制显示长度
			var totalLength=0, maxLength=31;
			if(lbpm && lbpm.approveType == "right"){
				maxLength=10;
			}
			for (var i=0;i<namesArray.length;i++) {
				if (totalLength + namesArray[i].length < maxLength) {
					if (handlerDisplayNames != "") {
						handlerDisplayNames += delimiter + namesArray[i];
					} else {
						handlerDisplayNames += namesArray[i];
					}
				} else {
					if (handlerDisplayNames!="") {
						handlerDisplayNames += delimiter + "...";
					} else {
						handlerDisplayNames = namesArray[i].slice(0,maxLength) + "...";
					}
					break;
				}
				totalLength = handlerDisplayNames.length;
			}
		}
		//节点简称
		var sortName = WorkFlow_getLangLabel(node.name,node["langs"],"nodeName");//lbpm.nodedescs[node.nodeDescType].getSortName();
		if(node.showNewText){
			sortName=node.showNewText;
		}
		//是否可以移动排序
		var isCanMove = node.Status == lbpm.constant.STATUS_NORMAL;
		//图片标识及title
		var statusClassAndTitle = "lbpm_freeflow_status_normal'";
		//节点是否可以编辑
		var canUpdate = false;
		if (node.Status == lbpm.constant.STATUS_NORMAL && node.isFixedNode != 'true') {
			if (lbpm.nowNodeFlowPopedom=="2") {
				canUpdate = true;
			} else if (lbpm.nowNodeFlowPopedom=="1") {
				if (lbpm.myAddedNodes.contains(node.id)) {
					canUpdate = true;
				}
			}
		}
		//编辑流程图按钮是否出现
		var canShowOperation = false;
		var editFreeFlowDIV = document.getElementById("editFreeFlowDIV");
		if(editFreeFlowDIV && editFreeFlowDIV.style.display!="none" && lbpm.globals.isCanAddOtherNode(node.id)){
			canShowOperation = true;
		}
		if(Lbpm_SettingInfo["isBusinessAuthEnabled"] == "true"
			&& (lbpm.modelName == "com.landray.kmss.km.imissive.model.KmImissiveSendMain"
				||lbpm.modelName == "com.landray.kmss.km.imissive.model.KmImissiveReceiveMain"
				||lbpm.modelName == "com.landray.kmss.km.imissive.model.KmImissiveSignMain")){
			$("#freeflow_appendNodeDIV .lbpm_freeflow_appendNode").hide();
		}
		if(canUpdate && canShowOperation){
			statusClassAndTitle += " title='"+lbpm.constant.FREEFLOW_TIELE_DRAG+"'";
		}
		if(node.isFixedNode == 'true' || (node.XMLNODENAME == "joinNode" && lbpm.nodes[node.relatedNodeIds].isFixedNode == 'true')){
			statusClassAndTitle = "lbpm_freeflow_status_fixed' title='"+lbpm.constant.FREEFLOW_TIELE_FIXED+"'";
		}
		if (node.Status == "2"){
			statusClassAndTitle = "lbpm_freeflow_status_passed' title='"+lbpm.constant.FREEFLOW_TIELE_PASSED+"'";
		} else if (node.Status == "3"){
			statusClassAndTitle = "lbpm_freeflow_status_running' title='"+lbpm.constant.FREEFLOW_TIELE_RUNNING+"'";
		}
		html.push("<li class='"+(isCanMove?"lbpm_freeflow_draglist_li canMove":(node.Status == "2"?
				"lbpm_freeflow_draglist_li_passed":"lbpm_freeflow_draglist_li"))+ 
				(node.isFixedNode == 'true'?" fixed":"")+(!canUpdate ?" fixed":"")+
				"' data-node-id='"+nodeId+"' data-node-type='"+node.XMLNODENAME+"' onclick='lbpmFreeFlowChangeActive(this);'>" +
				"<div class='lbpm_freeflow_item'>");
		if(node.XMLNODENAME == "splitNode"){
			html.push("<i class='lbpm_freeflow_split_status'></i>");
		}
		html.push("<i class='lbpm_freeflow_status "+statusClassAndTitle+"></i>" +
				"<div class='lbpm_freeflow_sortName lbpm_freeflow_sortName_"+node.XMLNODENAME+"' title='"+sortName+"'>"+sortName+"</div>" +
				"<div class='lbpm_freeflow_handlerName' title='"+handlerNames+"'>");
		html.push("<input type='hidden' name='freeflow_"+nodeId+"_handlerIds' value='"+(node.handlerIds||"")+"'/>" +
				"<input type='hidden' name='freeflow_"+nodeId+"_handlerNames' value='"+(node.handlerNames||"")+"'/>");
		if(!handlerDisplayNames && node.isFixedNode != 'true'){
			handlerDisplayNames = lbpm.constant.FREEFLOW_TIELE_ADDHANDLER+"<span class='txtstrong'>*</span>";
		}
		if(node.XMLNODENAME == "joinNode" || node.XMLNODENAME == "splitNode"){
			html.push("<span></span>");
		}else if (canShowOperation && canUpdate){
			html.push("<span onclick='lbpmFreeFlowUpdateHandler(this)'>"+handlerDisplayNames+"</span>");
		}else{
			html.push("<span>"+handlerDisplayNames+"</span>");
		}
		html.push("</div>");
		if (canShowOperation && node.Status != lbpm.constant.STATUS_PASSED){
			html.push("<div class='lbpm_freeflow_operation'>"+
			"<a class='lbpm_freeflow_add' title='"+lbpm.constant.FREEFLOW_TIELE_ADD+"'><i></i></a>");
			if (canUpdate){
				//if(node.XMLNODENAME != "splitNode"){
					html.push("<a class='lbpm_freeflow_edit' title='"+lbpm.constant.FREEFLOW_TIELE_EDIT+"'><i></i></a>");
				//}
				if(node.XMLNODENAME != "joinNode"){
					html.push("<a class='lbpm_freeflow_delete' title='"+lbpm.constant.FREEFLOW_TIELE_DELETE+"'><i></i></a>");
				}
			}
			html.push("</div>");
		} 
		html.push("</div><div class='lbpm_freeflow_split'></div></li>");
		if(node.XMLNODENAME != "joinNode"){
			appendFreeFlowNodeLI(node, html, cleanJoinNodeId?"":joinNodeId, parentJoinNodeId);
		}
	}
}

function getFreeFlowChartObject(){
	var sysWfBusinessFormPrefix = $("input[name='sysWfBusinessFormPrefix']").val();
	return document.getElementById(sysWfBusinessFormPrefix + "WF_IFrame").contentWindow.FlowChartObject;
}

//修改处理人（仅选中且可编辑的才能更改）
function lbpmFreeFlowUpdateHandler(dom){
	if(!lbpm.globals.isCanEdit()){
		return;
	}
	var $activeLi = $(dom).closest(".lbpm_freeflow_draglist_li");
	var $handlerInfoDiv = $(dom).closest(".lbpm_freeflow_handlerName");
	if($activeLi.hasClass("active")){
		var oldHandlerIds = $handlerInfoDiv.find("input[name$=handlerIds]").val();
		var idField = $handlerInfoDiv.find("input[name$=handlerIds]").attr("name");
		var nameField = $handlerInfoDiv.find("input[name$=handlerNames]").attr("name");
		var nodeId = $activeLi.attr("data-node-id");
		var node = lbpm.nodes[nodeId];
		if(node){
			var callback = function(){
				var handlerIds = $handlerInfoDiv.find("input[name$=handlerIds]").val();
				if(oldHandlerIds!=handlerIds){
					var nodeId = $activeLi.attr("data-node-id");
					var handlerNames = $handlerInfoDiv.find("input[name$=handlerNames]").val();
					var FlowChartObject = getFreeFlowChartObject();
					var node = FlowChartObject.Nodes.GetNodeById(nodeId);
					node.Data["handlerIds"]=handlerIds;
					node.Data["handlerNames"]=handlerNames;
					node.Refresh();
					lbpm.globals.reflushFreeFlow();
					lbpmFreeFlowChangeHandlerInfo(node,$handlerInfoDiv);
					lbpm.globals.saveOrUpdateFreeflowVersion();
				}
			}
			if(node.handlerSelectType=='org'){
				var nodeType = $activeLi.attr("data-node-type");
				var orgType = ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE;
				if(nodeType=="sendNode"){
					orgType = ORG_TYPE_ALL | ORG_TYPE_ROLE;
				}
				selectByOrg(idField,nameField,orgType,callback);
			}else if(node.handlerSelectType=='matrix'){
				selectByMatrix(idField,nameField,callback);
			}else if(node.handlerSelectType=='formula'){
				selectByFormula(idField,nameField,callback);
			}
		}
	}
}

//使用组织架构选择
function selectByOrg(idField, nameField, orgType, callback){
	var orgType = ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE;
	Dialog_Address(true, idField, nameField, null, orgType, callback);
}

//使用公式定义器选择
function selectByFormula(idField, nameField, callback){
	Formula_Dialog(idField,
			nameField,
			lbpm.globals.getFormFieldList(), 
			"com.landray.kmss.sys.organization.model.SysOrgElement[]",
			callback,
			"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
			lbpm.modelName);
}

//使用矩阵组织选择
function selectByMatrix(idField, nameField, callback){
	// 弹出矩阵组织设置窗口
	var dialog = new KMSSDialog();
	dialog.FormFieldList = lbpm.globals.getFormFieldList();
	dialog.ModelName = lbpm.modelName;
	dialog.BindingField(idField, nameField);
	dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/node/common/node_handler_matrix_config.jsp";
	var size = getSizeForAddress();
	dialog.SetAfterShow(callback);
	dialog.Show(size.width, size.height);
}

//更改节点列表显示信息（包括处理人及提示）
function lbpmFreeFlowChangeHandlerInfo(node,$handlerInfoDiv,needUpdate){
	if(node.Type == "splitNode" || node.Type == "joinNode"){
		return;
	}
	if(!lbpm.freeFlow.emptyHandlerNodes){
		lbpm.freeFlow.emptyHandlerNodes = [];
	}
	var index = $.inArray(node.Data["id"],lbpm.freeFlow.emptyHandlerNodes);
	if(node.Data["handlerIds"]){
		if(index>-1){
			lbpm.freeFlow.emptyHandlerNodes.splice(index,1);
		}
	}else{
		if(index<0){
			lbpm.freeFlow.emptyHandlerNodes.push(node.Data["id"]);
		}
	}
	var handlerNames = node.Data.handlerNames;
	var dataNextNodeHandler;
	var nextNodeHandlerNames4View="";
	var nodeObj = lbpm.nodes[node.Data["id"]];
	if(nodeObj.handlerSelectType){
		var handlerIds = nodeObj.handlerIds==null?"":nodeObj.handlerIds;
		if (nodeObj.handlerSelectType=="formula") {
			dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else if (nodeObj.handlerSelectType=="matrix") {
			dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else if (nodeObj.handlerSelectType=="rule") {
			dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id,handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else {
			dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		}
		for(var j=0;j<dataNextNodeHandler.length;j++){
			if(nextNodeHandlerNames4View==""){
				nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
			}else{
				nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
			}
		}
	}
	if(nextNodeHandlerNames4View == "" && nodeObj.handlerIds != null) {
		nextNodeHandlerNames4View = lbpm.constant.COMMONNODEHANDLERORGNULL;
	}
	if(nextNodeHandlerNames4View){
		handlerNames = nextNodeHandlerNames4View;
	}
	var titleHandlerName = handlerNames;
	var handlerDisplayNames = "";
	var namesArray = handlerNames.split(";");
	// 根据流转方式决定分隔符的具体显示
	var delimiter = ";";
	if (node.Data.processType == "1") {
		delimiter = "/";
	} else if (node.Data.processType == "2") {
		delimiter = "+";
	}
	if (delimiter!=";") {
		titleHandlerName = handlerNames.replace(/;/g,delimiter);
	}
	// 控制显示长度
	var totalLength=0, maxLength=31;
	if(lbpm && lbpm.approveType == "right"){
		maxLength=10;
	}
	for (var i=0;i<namesArray.length;i++) {
		if (totalLength + namesArray[i].length < maxLength) {
			if (handlerDisplayNames != "") {
				handlerDisplayNames += delimiter + namesArray[i];
			} else {
				handlerDisplayNames += namesArray[i];
			}
		} else {
			if (handlerDisplayNames!="") {
				handlerDisplayNames += delimiter + "...";
			} else {
				handlerDisplayNames = namesArray[i].slice(0,maxLength) + "...";
			}
			break;
		}
		totalLength = handlerDisplayNames.length;
	}
	$handlerInfoDiv.attr("title",titleHandlerName);
	if(!handlerDisplayNames || handlerDisplayNames === lbpm.constant.COMMONNODEHANDLERORGEMPTY){
		handlerDisplayNames = lbpm.constant.FREEFLOW_TIELE_ADDHANDLER+"<span class='txtstrong'>*</span>";
	}
	$handlerInfoDiv.find("span").html(handlerDisplayNames);
	if(needUpdate){
		$handlerInfoDiv.find("input[name$=handlerIds]").val(node.Data["handlerIds"]);
		$handlerInfoDiv.find("input[name$=handlerNames]").val(node.Data["handlerNames"]);
		var nodeName = WorkFlow_getLangLabel(node.Data["name"],node.Data["langs"],"nodeName");
		$handlerInfoDiv.prev(".lbpm_freeflow_sortName").attr("title",nodeName).text(nodeName);
	}
}

//更改选中的节点
function lbpmFreeFlowChangeActive(dom){
	if($(dom).hasClass("lbpm_freeflow_draglist_li_passed")){
		return;
	}
	$(".lbpm_freeflow_draglist").find('.lbpm_freeflow_draglist_li').removeClass('active');
	$(dom).addClass('active');
}

// 自由流行内删除节点
function deleteFreeFlowNode(dom){
	if(!lbpm.globals.isCanEdit()){
		return;
	}
	var nodeId = $(dom).closest("li.lbpm_freeflow_draglist_li").attr("data-node-id");
	lbpm.globals.delNodeInFreeFlow(nodeId);
	lbpm.globals.updateFreeFlowNodeUL();
	if(lbpm.freeFlow.emptyHandlerNodes){
		var index = $.inArray(nodeId,lbpm.freeFlow.emptyHandlerNodes);
		if(index>-1){
			lbpm.freeFlow.emptyHandlerNodes.splice(index,1);
		}
	}
}

//删除节点
lbpm.globals.delNodeInFreeFlow=function(nodeId) {
	var FlowChartObject = getFreeFlowChartObject();
	var delNodeObj = FlowChartObject.Nodes.GetNodeById(nodeId);
	FlowChartObject.Nodes.deleteNodeInFreeFlow(delNodeObj);
	lbpm.globals.reflushFreeFlow();
}

//更新流程图
lbpm.globals.reflushFreeFlow=function() {
	var FlowChartObject = getFreeFlowChartObject();
	var flowXml = FlowChartObject.BuildFlowXML();
	if (!flowXml)
		return;
	var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
	processXMLObj.value = flowXml;
	lbpm.globals.parseXMLObj();
	lbpm.modifys = {};
	$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
	lbpm.events.mainFrameSynch();
}

// 更新整个自由流行的显示
lbpm.globals.updateFreeFlowNodeUL=function(isInit) {
	if(isInit && lbpm.globals.getProcessXmlString){
		var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
		if(processDefine && processDefine.isCanAddOtherNode && processDefine.isCanAddOtherNode=="true"){
			lbpm.freeFlow.isCanAddOtherNode = true;
		}else{
			lbpm.freeFlow.isCanAddOtherNode = false;
		}
	}
	var flowNodeUL = $("#flowNodeUL");
	if(flowNodeUL.length > 0){
		lbpm.freeFlow.nextNodes = [];
		flowNodeUL.html('');
		var html = [];
		appendFreeFlowNodeLI(lbpm.nodes["N1"], html);
		flowNodeUL.html(html.join(""));
		var lastNode = lbpm.nodes["N3"].startLines[0].startNode;
		if(lastNode){
			if(!lbpm.globals.isCanAddOtherNode(lastNode.id)){
				$("#freeflow_appendNodeDIV .lbpm_freeflow_appendNode").hide();
			}
		}
		//每有一层分支，向右偏移length*15
		$(".lbpm_freeflow_draglist_sub").each(function(){
			var length = $(this).parents(".lbpm_freeflow_draglist_split").length;
			$(this).find(">li").css("padding-left",length*15+"px");
		});
		//每有一层分支，并行结束节点向右偏移length*15，且计算左边线条宽度及left，top
		$(".lbpm_freeflow_draglist_split").each(function(){
			var length = $(this).parents(".lbpm_freeflow_draglist_split").length;
			$(this).find(">li").css("padding-left",length*15+"px");
			var splitNodeId = $(this).attr("data-split-id");
			$("[data-node-id='"+splitNodeId+"']").find(">style").remove();
			var height = this.offsetHeight-50;
			if(height>0){
				$("[data-node-id='"+splitNodeId+"']").append("<style>.lbpm_freeflow_draglist li[data-node-type='splitNode'][data-node-id='"+splitNodeId+"']:after{display:block;width:"+height+"px;left:-"+(height/2-5)+"px;top:"+height/2+"px}</style>");
			}
		});
		$(".lbpm_freeflow_split_status").click(function(){
			var $li =  $(this).closest("li.lbpm_freeflow_draglist_li,li.lbpm_freeflow_draglist_li_passed");
			var splitNodeId = $li.attr("data-node-id");
			if($li.hasClass("fold")){
				$li.removeClass("fold");
				$("ul[data-split-id='"+splitNodeId+"']").show();
    		}else{
    			$li.addClass("fold");
    			$("ul[data-split-id='"+splitNodeId+"']").hide();
    		}
			//每有一层分支，并行结束节点向右偏移length*15，且计算左边线条宽度及left，top
			$(".lbpm_freeflow_draglist_split").each(function(){
				var length = $(this).parents(".lbpm_freeflow_draglist_split").length;
				$(this).find(">li").css("padding-left",length*15+"px");
				var splitNodeId = $(this).attr("data-split-id");
				$("[data-node-id='"+splitNodeId+"']").find(">style").remove();
				var height = this.offsetHeight-50;
				if(height>0){
					$("[data-node-id='"+splitNodeId+"']").append("<style>.lbpm_freeflow_draglist li[data-node-type='splitNode'][data-node-id='"+splitNodeId+"']:after{display:block;width:"+height+"px;left:-"+(height/2-5)+"px;top:"+height/2+"px}</style>");
				}
			});
		});
		//选中当前节点
		$(".lbpm_freeflow_draglist .lbpm_freeflow_status_running").closest("li.lbpm_freeflow_draglist_li").addClass("active");
		var canShowOperation = false;
		var editFreeFlowDIV = document.getElementById("editFreeFlowDIV");
		if(editFreeFlowDIV && editFreeFlowDIV.style.display!="none"){
			canShowOperation = true;
		}
		if(canShowOperation){
			//初始化拖动排序
			var sortableParam = {
				items : ">li.canMove",
				cancel : "li.fixed",
				placeholder : 'lbpm_freeflow_placeholder',
				update : function(event){
					if(!lbpm.globals.isCanEdit()){
						return;
					}
					var oldInfo = lbpm.freeFlow.oldInfo;
					//当前按顺序排列的节点
					var newInfo = [];
					if($(this).hasClass("lbpm_freeflow_draglist_sub")){
						newInfo.push($(this).parent().attr("data-split-id"));
					}else{
						newInfo.push("N1");
					}
					$(this).children("li").each(function(){
						var nodeId = $(this).attr("data-node-id");
						newInfo.push(nodeId);
						if(lbpm.nodes[nodeId] && lbpm.nodes[nodeId].XMLNODENAME == "splitNode"){
							newInfo.push(lbpm.nodes[nodeId].relatedNodeIds);
						}
					});
					if($(this).hasClass("lbpm_freeflow_draglist_sub")){
						newInfo.push($(this).parent().find(">li[data-node-type='joinNode']").attr("data-node-id"));
					}else{
						newInfo.push("N3");
					}
					//按新顺序，若当前节点和下一节点与旧信息中不一致，则认为发生改变，获得旧信息中的连线重新连接当前节点及下一节点
					var FlowChartObject = getFreeFlowChartObject();
					for(var i=0;i<newInfo.length-1;i++){
						var beginNode = FlowChartObject.Nodes.GetNodeById(newInfo[i]);
						var endNode = FlowChartObject.Nodes.GetNodeById(newInfo[i+1]);
						if(oldInfo[newInfo[i]] && oldInfo[newInfo[i]]["endNodeId"] && oldInfo[newInfo[i]]["endNodeId"]!=newInfo[i+1]){
							var line = FlowChartObject.Lines.GetLineById(oldInfo[newInfo[i]]["lineId"]);
							if(line){
								line.LinkNode(beginNode, endNode, '3', '1');
								line.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
							}
						}
					}
					//格式化节点位置
					FlowChartObject.Nodes.FlowChart_formatNodes();
					//更新流程图
					lbpm.globals.reflushFreeFlow();
					lbpm.globals.saveOrUpdateFreeflowVersion();
					lbpm.freeFlow.oldInfo = [];
				},
				start : function(event){
					//隐藏切换流转方式下拉
					$(".lbpm_freeflow_process_typeList").hide();
					//拖动时隐藏子分支节点
					$(this).find("ul.lbpm_freeflow_draglist_split").hide();
					//若有子，则拖动时改变了坐标位置，需要刷新坐标
					if($(this).find("ul.lbpm_freeflow_draglist_split").length>0){
						$(this).sortable('refreshPositions');
					}
					//拖动时给最外层添加class标识，隐藏分支节点的连线
					$(".lbpm_freeflow_draglist").addClass("lbpm_freeflow_sortable");
					//记录拖动前的节点流转顺序
					var oldInfo = lbpm.freeFlow.oldInfo = [];
					$(this).children("li.lbpm_freeflow_draglist_li").each(function(){
						var FlowChartObject = getFreeFlowChartObject();
						var nodeId = $(this).attr("data-node-id");
						var node = FlowChartObject.Nodes.GetNodeById(nodeId);
						var line = node.LineIn[0];
						if(line){
							if(!oldInfo[line.Data.startNodeId]){
								oldInfo[line.Data.startNodeId]={};
								oldInfo[line.Data.startNodeId]["lineId"] = line.Data.id;
								oldInfo[line.Data.startNodeId]["endNodeId"] = line.Data.endNodeId;
							}
						}
						line = node.LineOut[0];
						if(line){
							if(!oldInfo[line.Data.startNodeId]){
								oldInfo[line.Data.startNodeId]={};
								oldInfo[line.Data.startNodeId]["lineId"] = line.Data.id;
								var startNode = node;
								if(startNode.Type == "splitNode"){
									oldInfo[line.Data.startNodeId]["endNodeId"] = startNode.Data.relatedNodeIds;
									var joinNode = FlowChartObject.Nodes.GetNodeById(startNode.Data.relatedNodeIds);
									line = joinNode.LineOut[0];
									if(!oldInfo[line.Data.startNodeId]){
										oldInfo[line.Data.startNodeId]={};
										oldInfo[line.Data.startNodeId]["lineId"] = line.Data.id;
										oldInfo[line.Data.startNodeId]["endNodeId"] = line.Data.endNodeId;
									}
								}else{
									oldInfo[line.Data.startNodeId]["endNodeId"] = line.Data.endNodeId;
								}
							}
						}
					});
				},
				stop : function(event){
					$(this).find("ul.lbpm_freeflow_draglist_split").each(function(){
						var splitNodeId = $(this).attr("data-split-id");
						//将分支节点ul接到分支节点li后面
						$(".lbpm_freeflow_draglist_li[data-node-id='"+splitNodeId+"']").after($(this));
						//拖动完成后安原本是否隐藏恢复子分支节点显示
						var $li =  $(".lbpm_freeflow_draglist_li[data-node-id='"+splitNodeId+"']");
						if(!$li.hasClass("fold")){
							$(this).show();
			    		}
					});
					//移除拖动中标识
					$(".lbpm_freeflow_draglist").removeClass("lbpm_freeflow_sortable");
				}
			};
			var num = setInterval(function(){//兼容Safari最新版没办法动态加载COM_INCLUDEFILE导致的sortable找不到问题 122229 by suyb
				if(typeof $(".lbpm_freeflow_draglist").sortable !== 'undefined' && $(".lbpm_freeflow_draglist").sortable){
					clearInterval(num);
					$(".lbpm_freeflow_draglist,.lbpm_freeflow_draglist_sub").sortable(sortableParam);
				}
			}, 200)
			$(".lbpm_freeflow_add").mouseover(function(){
				var nodeId = $(this).closest("li.lbpm_freeflow_draglist_li").attr("data-node-id");
				$(".lbpm_freeflow_add_moreList").attr("data-node-id",nodeId);
				$(".lbpm_freeflow_add_moreList").show();
				var left = $(this).offset().left-$(".lbpm_freeflow_add_moreList").width()/2+14;
				var top = $(this).offset().top+23;
				var h = document.documentElement.scrollTop || document.body.scrollTop;
				$(".lbpm_freeflow_add_moreList").css({
					"left" : left,
				    "top" : top-h
				});
			});
			$(".lbpm_freeflow_add").mouseout(function(){
				var event = window.event;
				var toEle = event.toElement || event.relatedTarget;
		    	if(toEle && $(toEle).closest('.lbpm_freeflow_add_moreList').length>0){
		    		var $li =  $(this).closest("li.lbpm_freeflow_draglist_li");
		    		if(!$li.hasClass("active")){
		    			$li.addClass("temp");
		    		}
		    		return;
		    	}
				$(".lbpm_freeflow_add_moreList").hide();
			});
			$(".lbpm_freeflow_edit").click(function(){
				if(!lbpm.globals.isCanEdit()){
					return;
				}
				var $li = $(this).closest("li.lbpm_freeflow_draglist_li");
				var nodeId = $li.attr("data-node-id");
				var FlowChartObject = getFreeFlowChartObject();
				var editNodeObj = FlowChartObject.Nodes.GetNodeById(nodeId);
				editNodeObj.ShowAttributePanel(function(Node){
					lbpm.globals.reflushFreeFlow();
					lbpmFreeFlowChangeHandlerInfo(Node,$li.find(".lbpm_freeflow_handlerName"),true);
				});
			});
			$(".lbpm_freeflow_delete").click(function(){
				if(!lbpm.globals.isCanEdit()){
					return;
				}
				var _self = this;
				if (window.LUI) {
					seajs.use(['lui/dialog'], function(dialog) {
						dialog.confirm(lbpm.constant.FREEFLOW_DELETENODEMSG,function(data){
							if(data){
								deleteFreeFlowNode(_self);
							}
						});
					});
				}else{
					if(confirm(lbpm.constant.FREEFLOW_DELETENODEMSG)){
						deleteFreeFlowNode(_self);
					}
				}
			});
			//节点名称切换
			$(".lbpm_freeflow_sortName").mouseover(function(){
				//没有编辑权限，不显示切换节点名称下拉
				if($(this).parent().find("a.lbpm_freeflow_edit").length==0){
					return;
				}
				if($(".lbpm_freeflow_sortName_nameList").length==0){
					var html = "<ul class='lbpm_freeflow_sortName_nameList'>";
					//加载节点名列表
					var data = new KMSSData();
					data.AddBeanData("lbpmBaseInfoService");
					data = data.GetHashMapArray();
					if(data.length>0 && data[0].nodeNameSelectItem!=null && data[0].nodeNameSelectItem!=""){
						var nodeNameList = data[0].nodeNameSelectItem.split(";");
						for(var i=0; i<nodeNameList.length; i++){
							html+="<li title='"+nodeNameList[i]+"'>"+nodeNameList[i]+"</li>";
						}
					}
					html+="</ul>"
					$("#flowNodeDIV").append(html);
					$(".lbpm_freeflow_sortName_nameList").mouseout(function(){
						var event = window.event;
						var toEle = event.toElement || event.relatedTarget;
				    	if(toEle && $(toEle).closest('.lbpm_freeflow_sortName_nameList').length>0){
				    		return;
				    	}
						$(this).hide();
					});
					$(".lbpm_freeflow_sortName_nameList li").click(function(){
						if(!lbpm.globals.isCanEdit()){
							return;
						}
						var nodeId = $(this).parent().attr("data-node-id");
						var $li = $(".lbpm_freeflow_draglist_li[data-node-id='"+ nodeId +"']");
						var FlowChartObject = getFreeFlowChartObject();
						var nodeObj = FlowChartObject.Nodes.GetNodeById(nodeId);
						var newName = $(this).text();
						nodeObj.Data["name"] = newName;
						if(nodeObj.Data["langs"]){
							var langs = JSON.parse(nodeObj.Data["langs"]);
							if(langs["nodeName"] && _userLang){
								for(var i=0;i<langs["nodeName"].length;i++){
									if(langs["nodeName"][i]["lang"]==_userLang){
										langs["nodeName"][i]["value"]=newName;
										break;
									}
								}
								nodeObj.Data["langs"] = JSON.stringify(langs);
							}
						}
						lbpm.globals.reflushFreeFlow();
						lbpm.globals.saveOrUpdateFreeflowVersion();
						$li.find(".lbpm_freeflow_sortName").attr("title",newName).text(newName);
						$(".lbpm_freeflow_sortName_nameList").hide();
					});
				}
				if($(".lbpm_freeflow_sortName_nameList li").length>0){
					var nodeId = $(this).closest("li.lbpm_freeflow_draglist_li").attr("data-node-id");
					$(".lbpm_freeflow_sortName_nameList").attr("data-node-id",nodeId);
					$(".lbpm_freeflow_sortName_nameList").show();
					var left = $(this).offset().left-$(".lbpm_freeflow_sortName_nameList").width()/2+$(this).width()/2;
					var top = $(this).offset().top+20;
					var h = document.documentElement.scrollTop || document.body.scrollTop;
					$(".lbpm_freeflow_sortName_nameList").css({
						"left" : left,
					    "top" : top-h
					});
				}
			});
			$(".lbpm_freeflow_sortName").mouseout(function(){
				var event = window.event;
				var toEle = event.toElement || event.relatedTarget;
		    	if(toEle && $(toEle).closest('.lbpm_freeflow_sortName_nameList').length>0){
		    		return;
		    	}
				$(".lbpm_freeflow_sortName_nameList").hide();
			});
			//流转方式切换
			$(".lbpm_freeflow_status").mouseover(function(){
				//没有编辑权限，不显示切换流转方式下拉
				if($(this).parent().find("a.lbpm_freeflow_edit").length==0){
					return;
				}
				if($(".lbpm_freeflow_process_typeList").length==0){
					var html = "<ul class='lbpm_freeflow_process_typeList'>";
					html+="<li data-value='"+lbpm.constant.PROCESSTYPE_SERIAL+"'>"+lbpm.constant.COMMONNODEHANDLERPROCESSTYPESERIAL+"</li>";
					html+="<li data-value='"+lbpm.constant.PROCESSTYPE_SINGLE+"'>"+lbpm.constant.COMMONNODEHANDLERPROCESSTYPESINGLE+"</li>";
					html+="<li data-value='"+lbpm.constant.PROCESSTYPE_ALL+"'>"+lbpm.constant.COMMONNODEHANDLERPROCESSTYPEALL+"</li>";
					html+="</ul>"
					$("#flowNodeDIV").append(html);
					$(".lbpm_freeflow_process_typeList").mouseout(function(){
						var event = window.event;
						var toEle = event.toElement || event.relatedTarget;
				    	if(toEle && $(toEle).closest('.lbpm_freeflow_process_typeList').length>0){
				    		return;
				    	}
						$(this).hide();
					});
					$(".lbpm_freeflow_process_typeList li").click(function(){
						if(!lbpm.globals.isCanEdit()){
							return;
						}
						var nodeId = $(this).parent().attr("data-node-id");
						var $li = $(".lbpm_freeflow_draglist_li[data-node-id='"+ nodeId +"']");
						var $handlerInfoDiv =$li.find(".lbpm_freeflow_handlerName");
						var FlowChartObject = getFreeFlowChartObject();
						var nodeObj = FlowChartObject.Nodes.GetNodeById(nodeId);
						nodeObj.Data["processType"] = $(this).attr("data-value");
						lbpm.globals.reflushFreeFlow();
						lbpm.globals.saveOrUpdateFreeflowVersion();
						$(".lbpm_freeflow_process_typeList").hide();
						lbpmFreeFlowChangeHandlerInfo(nodeObj,$handlerInfoDiv);
					});
				}
				var nodeId = $(this).closest("li.lbpm_freeflow_draglist_li").attr("data-node-id");
				$(".lbpm_freeflow_process_typeList").attr("data-node-id",nodeId);
				if(lbpm.nodes[nodeId] && lbpm.nodes[nodeId]["processType"]){
					$(".lbpm_freeflow_process_typeList li").removeClass("checked");
					$(".lbpm_freeflow_process_typeList li[data-value='"+lbpm.nodes[nodeId]["processType"]+"']").addClass("checked");
					$(".lbpm_freeflow_process_typeList").show();
					var left = $(this).offset().left-$(".lbpm_freeflow_process_typeList").width()/2+$(this).width()/2;
					var top = $(this).offset().top+20;
					var h = document.documentElement.scrollTop || document.body.scrollTop;
					$(".lbpm_freeflow_process_typeList").css({
						"left" : left,
					    "top" : top-h
					});
				}
			});
			$(".lbpm_freeflow_status").mouseout(function(){
				var event = window.event;
				var toEle = event.toElement || event.relatedTarget;
		    	if(toEle && $(toEle).closest('.lbpm_freeflow_process_typeList').length>0){
		    		return;
		    	}
				$(".lbpm_freeflow_process_typeList").hide();
			});
			if(isInit){
				$(".lbpm_freeflow_add_moreList").mouseout(function(){
					var event = window.event;
					var toEle = event.toElement || event.relatedTarget;
			    	if(toEle && $(toEle).closest('.lbpm_freeflow_add_moreList').length>0){
			    		return;
			    	}
			    	$(".lbpm_freeflow_draglist_li[data-node-id='"+ $(this).attr("data-node-id")+"']").removeClass('temp');
			    	$(this).hide();
				});
				$(".lbpm_freeflow_add_moreList li").click(function(){
					var nodeType = $(this).attr("data-node-type");
					var nodeId = $(this).parent().attr("data-node-id");
					lbpm.flow_chart_load_Frame();
					lbpm.globals.beforeAddNodeInFreeFlow(nodeType,nodeId);
					$(".lbpm_freeflow_add_moreList").hide();
				});
			}
		}
	}
}

lbpm.globals.isCanAddOtherNode = function(nodeId){
	if(lbpm.freeFlow.isCanAddOtherNode){
		if(lbpm.freeFlow.nextNodes.length==0){
			lbpm.globals.getNextFreeFlowNodes(lbpm.nowNodeId);
		}
		if(Com_ArrayGetIndex(lbpm.freeFlow.nextNodes, nodeId) == -1){
			return false;
		}
	}
	return true;
}

lbpm.globals.getNextFreeFlowNodes = function(nodeId){
	if(Com_ArrayGetIndex(lbpm.freeFlow.nextNodes, nodeId) == -1){
		lbpm.freeFlow.nextNodes.push(nodeId);
	}
	var nodeObj = lbpm.globals.getNodeObj(nodeId);
	for(var i = 0 ;i<nodeObj.endLines.length;i++){
		var nextNode = nodeObj.endLines[i].endNode;
		if(nextNode.XMLNODENAME != "endNode" && nextNode.XMLNODENAME != "joinNode"){
			lbpm.globals.getNextFreeFlowNodes(nextNode.id);
		}else if(nextNode.XMLNODENAME == "joinNode"){
			if(Com_ArrayGetIndex(lbpm.freeFlow.nextNodes, nextNode.relatedNodeIds) > -1){
				lbpm.globals.getNextFreeFlowNodes(nextNode.id);
			}else{
				if(Com_ArrayGetIndex(lbpm.freeFlow.nextNodes, nextNode.id) == -1){
					lbpm.freeFlow.nextNodes.push(nextNode.id);
				}
			}
		}
	}
}

//初始化公式定义器所需的变量
lbpm.globals.initFreeFlowFormList = function(){
	var sysWfBusinessFormPrefix = $("input[name='sysWfBusinessFormPrefix']").val();
	var iframe = document.getElementById(sysWfBusinessFormPrefix + "WF_IFrame").contentWindow;
	if(iframe && iframe.FlowChartObject && iframe.FlowChartObject.FormFieldList){
		iframe.FlowChartObject.FormFieldList = lbpm.globals.getFormFieldList();
		if(lbpm.constant.ISINIT){
			lbpm.globals.initFreeFlowDrafter(iframe.FlowChartObject);
		}
		//每有一层分支，并行结束节点向右偏移length*15，且计算左边线条宽度及left，top
		$(".lbpm_freeflow_draglist_split").each(function(){
			var length = $(this).parents(".lbpm_freeflow_draglist_split").length;
			$(this).find(">li").css("padding-left",length*15+"px");
			var splitNodeId = $(this).attr("data-split-id");
			$("[data-node-id='"+splitNodeId+"']").find(">style").remove();
			var height = this.offsetHeight-50;
			if(height>0){
				$("[data-node-id='"+splitNodeId+"']").append("<style>.lbpm_freeflow_draglist li[data-node-type='splitNode'][data-node-id='"+splitNodeId+"']:after{display:block;width:"+height+"px;left:-"+(height/2-5)+"px;top:"+height/2+"px}</style>");
			}
		});
	}else{
		setTimeout(lbpm.globals.initFreeFlowFormList,500);
	}
}

lbpm.globals.initFreeFlowDrafter = function(FlowChartObject){
	if(FlowChartObject && FlowChartObject.Nodes && FlowChartObject.Nodes.GetNodeById && FlowChartObject.Nodes.GetNodeById("N2")){
		var drafterNode = FlowChartObject.Nodes.GetNodeById("N2");
		drafterNode.Data.showText = $("[name='sysWfBusinessForm.fdDraftorName']").val();
		drafterNode.Refresh();
		var flowXml = FlowChartObject.BuildFlowXML();
		if (!flowXml)
			return;
		var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
		processXMLObj.value = flowXml;
		lbpm.globals.parseXMLObj();
		lbpm.modifys = {};
		$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
		lbpm.events.mainFrameSynch();
	}else{
		setTimeout(function(){
			lbpm.globals.initFreeFlowDrafter(FlowChartObject);
		},500);
	}
}

// 查看自由流的流程图
lbpm.globals.viewFreeFlow=function(contentField, statusField){
	var fieldList = lbpm.globals.getFormFieldList();

	if(typeof(_thirdSysFormList) == "object" ){//第三方系统集成表单参数
		fieldList = fieldList.concat(_thirdSysFormList);
	}
	
	var param = {
		processData: document.getElementsByName(contentField)[0].value,
		statusData: document.getElementsByName(statusField)[0].value,
		Window:window,
		FormFieldList:fieldList,
	};
	var fdIsAllowSetupApprovalType="0";
	var fdTemplateModelName = document.getElementById("sysWfBusinessForm.fdTemplateModelName").value;
	var fdTemplateKey = document.getElementById("sysWfBusinessForm.fdTemplateKey").value;
	var modelName = lbpm.globals.getWfBusinessFormModelName();
	var flowPopedom = lbpm.nowNodeFlowPopedom;
	var url='/sys/lbpm/flowchart/page/freeflowPanel.jsp?edit=false&extend=oa&template=false&flowType=1&flowPopedom='+ flowPopedom +'&modelName='+modelName+'&deployApproval=' + fdIsAllowSetupApprovalType + '&templateModel=' + fdTemplateModelName + '&templateKey=' + fdTemplateKey + "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&popup=true";
	Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			dialog.iframe(url,lbpm.constant.FREEFLOW,function(rtn){
			},{width:1080,height:640,params:param});
	});
}

//自由流私密意见
lbpm.globals.setPrivateOpinion=function(dom){
	if ($(dom).is(":checked")) {
		$("#privateOpinionCanViewTr").show();
		$("input[name='privateOpinionCanViewNames']").attr("validate","required");
	}else{
		$("#privateOpinionCanViewTr").hide();
		$("input[name='privateOpinionCanViewIds']").val("");
		$("input[name='privateOpinionCanViewNames']").val("").attr("validate","");
		$("#privateOpinionCanViewTr").closest("td").find(".validation-advice").hide();
	}
}

//自由流默认模板初始化
$(function(){
	$(".lbpm_freeflow_defaultTemp_btn").mouseover(function(){
		$(".lbpm_freeflow_defaultTemp_btnList").show();
		var left = $(this).offset().left-$(".lbpm_freeflow_defaultTemp_btnList").width()/2+$(this).width()/2;
		var top = $(this).offset().top+23;
		var h = document.documentElement.scrollTop || document.body.scrollTop;
		$(".lbpm_freeflow_defaultTemp_btnList").css({
			"left" : left,
		    "top" : top-h
		});
	});
	$(".lbpm_freeflow_defaultTemp_btn").mouseout(function(){
		var event = window.event;
		var toEle = event.toElement || event.relatedTarget;
    	if(toEle && $(toEle).closest('.lbpm_freeflow_defaultTemp_btnList').length>0){
    		return;
    	}
		$(".lbpm_freeflow_defaultTemp_btnList").hide();
	});
	$(".lbpm_freeflow_defaultTemp_btnList").mouseout(function(){
		var event = window.event;
		var toEle = event.toElement || event.relatedTarget;
    	if(toEle && $(toEle).closest('.lbpm_freeflow_defaultTemp_btnList').length>0){
    		return;
    	}
		$(".lbpm_freeflow_defaultTemp_btnList").hide();
	});
})

//自由流存为默认模板
lbpm.globals.saveFreeFlowDefaultTemp=function(){
	$(".lbpm_freeflow_defaultTemp_btnList").hide();
	//自由流检查处理人是否为空
	if (lbpm.freeFlow.emptyHandlerNodes && lbpm.freeFlow.emptyHandlerNodes.length>0){
		alert(lbpm.constant.FREEFLOW_TIELE_NODE+lbpm.freeFlow.emptyHandlerNodes.join("、")+lbpm.constant.FREEFLOW_TIELE_NOHANDLER);
		return;
	}
	if (window.LUI) {
		seajs.use(['lui/dialog'], function(dialog) {
			dialog.confirm(lbpm.constant.FREEFLOW_TIELE_SAVECONFIRM,function(data){
				if(data){
					lbpm.globals.saveFreeFlowTemp();
				}
			});
		});
	}else{
		if(confirm(lbpm.constant.FREEFLOW_TIELE_SAVECONFIRM)){
			lbpm.globals.saveFreeFlowTemp();
		}
	}
}

//自由流保存默认模板
lbpm.globals.saveFreeFlowTemp=function(){
	var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
	var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmFreeflowDefaultTempAction.do?method=saveOrUpdateDefaultTemp';
	var data = {"fdProcessId":lbpm.modelId,"fdContent":processXMLObj.value};
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async : false,
		dataType : "json",
		success : function(json){
			if (window.LUI) {
				seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert(json.msg);
				});
			}else{
				alert(json.msg);
			}
		},
		beforeSend : function() {
			if (window.LUI) {
				seajs.use(['lui/dialog'], function(dialog) {
					window.freeflow_save = dialog.loading();
				});
			}
		},
		complete : function() {
			if (window.LUI) {
				seajs.use(['lui/dialog'], function(dialog) {
					if(window.freeflow_save != null) {
						window.freeflow_save.hide(); 
					}
				});
			}
		}
	});
}

//自由流加载默认模板
lbpm.globals.loadFreeFlowDefaultTemp=function(){
	$(".lbpm_freeflow_defaultTemp_btnList").hide();
	var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmFreeflowDefaultTempAction.do?method=loadFreeFlowDefaultTemp';
	var data = {"fdProcessId":lbpm.modelId};
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async : false,
		dataType : "json",
		success : function(json){
			if(json && json.status){
				if(json.status == "00"){
					var param={};
				    param.xml=json.fdContent;
					lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
				}
				if (window.LUI) {
					seajs.use(['lui/dialog'], function(dialog) {
						dialog.alert(json.msg);
					});
				}else{
					alert(json.msg);
				}
			}
		},
		beforeSend : function() {
			if (window.LUI) {
				seajs.use(['lui/dialog'], function(dialog) {
					window.freeflow_load = dialog.loading();
				});
			}
		},
		complete : function() {
			if (window.LUI) {
				seajs.use(['lui/dialog'], function(dialog) {
					if(window.freeflow_load != null) {
						window.freeflow_load.hide(); 
					}
				});
			}
		}
	});
}

//自由流保存其他模板
lbpm.globals.saveFreeFlowOtherTemp=function(){
	$(".lbpm_freeflow_defaultTemp_btnList").hide();
	var msg = [];
	msg.push('<table><tr><td><span>'+lbpm.constant.FREEFLOW_INSERTTEMPLATENAME+'：</span>');
	msg.push('<input name="fdOtherTempName" type="text" subject="'+lbpm.constant.FREEFLOW_TEMPLATENAME+'" class="inputsgl" validate="required"><span class="txtstrong">*</span>');
	msg.push('</td></tr></able>')
	seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
		dialog.build({
			config : {
				width : 400,
				cahce : false,
				title : lbpm.constant.FREEFLOW_SAVEOTHERTEMP,
				content : {
					type : "common",
					html : msg.join(''),
					iconType : '',
					buttons : [ {
						name : lbpm.constant.BTNOK,
						value : true,
						focus : true,
						fn : function(value, dialog) {
							var tempObj = $("input[name='fdOtherTempName']",dialog.element);
							var validateObject=$GetFormValidation(document.forms[0]);
							if(validateObject && tempObj.length>0){
								var _elements = new Elements();
								_elements.serializeElement(tempObj[0]);
								var beforeValidate = validateObject._doValidateElement(new Element(tempObj[0]),validateObject.getValidator(tempObj.attr("validate")));
								if(beforeValidate){
									var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
									var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmFreeflowDefaultTempAction.do?method=saveOtherTemp';
									var data = {"fdProcessId":lbpm.modelId,"fdContent":processXMLObj.value,"fdName":tempObj.val()};
									$.ajax({
										type : "POST",
										data : data,
										url : url,
										async : false,
										dataType : "json",
										success : function(json){
											if (window.LUI) {
												seajs.use(['lui/dialog'], function(dialog) {
													dialog.alert(json.msg);
												});
											}else{
												alert(json.msg);
											}
										},
										beforeSend : function() {
											dialog.hide(value);
											if (window.LUI) {
												seajs.use(['lui/dialog'], function(dialog) {
													window.freeflow_save = dialog.loading();
												});
											}
										},
										complete : function() {
											if (window.LUI) {
												seajs.use(['lui/dialog'], function(dialog) {
													if(window.freeflow_save != null) {
														window.freeflow_save.hide();
													}
												});
											}
										}
									});
								}
							}else{
								dialog.hide(value);
							}
						}
					}, {
						name : lbpm.constant.BTNCANCEL,
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]
				}
			}
		}).on('show', function() {
			this.element.find(".lui_dialog_common_content_right").css("max-width","100%");
			this.element.find(".lui_dialog_common_content_right").css("margin-left","0px");
		}).show();
	});
}

//自由流加载其他模板
lbpm.globals.loadFreeFlowOtherTemp=function(){
	$(".lbpm_freeflow_defaultTemp_btnList").hide();
	if (window.LUI) {
		seajs.use(['lui/dialog'], function(dialog) {
			seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
				var url = '/sys/lbpmservice/support/lbpm_template/lbpmFreeFlowTemplate_list.jsp?fdProcessId='+lbpm.modelId;
				dialog.iframe(url, lbpm.constant.FREEFLOW_LOADOTHERTEMP, function(rtn){
					if(rtn){
						var param={};
						param.xml=rtn;
						lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
						seajs.use(['lui/dialog'], function(dialog) {
							dialog.alert(lbpm.constant.FREEFLOW_LOAD_OK);
						});
					}
				}, {width:500,height:300});
			});
		});
	}
}

//自由流锁更新
lbpm.globals.saveOrUpdateFreeflowVersion=function(){
	var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmFreeflowVersionAction.do?method=saveOrUpdateFreeflowVersion';
	var data = {"fdProcessId":lbpm.modelId};
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		dataType : "json",
		success : function(json){
			if(json){
				lbpm.freeFlow.editTime = new Date();
			}
		}
	});
}

//自由流是否可编辑
lbpm.globals.isCanEdit=function(){
	var isCanEdit = true;
	if(lbpm.freeFlow.editTime){
		var splitTime = parseInt(Lbpm_SettingInfo["freezeFreeFlowTime"]);
		if(new Date() - lbpm.freeFlow.editTime < splitTime*60*1000){
			return isCanEdit;
		}
	}
	var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmFreeflowVersionAction.do?method=isCanEdit';
	var data = {"fdProcessId":lbpm.modelId};
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async : false,
		dataType : "json",
		success : function(json){
			if(json){
				isCanEdit = json.isCanEdit;
				if(isCanEdit){
					if(json.needCheck){
						var oldXmlObj = XML_CreateByContent(lbpm.globals.getProcessXmlString());
						var oldXml = (oldXmlObj.xml || new XMLSerializer().serializeToString(oldXmlObj));
						var nowXmlObj = null;
						var data = new KMSSData();
						data.UseCache = false;
						data.AddBeanData("lbpmProcessDefinitionDetailService&processId="+lbpm.globals.getWfBusinessFormModelId());
						var result = data.GetHashMapArray();
						if(result && result.length>0){
							if(result[0]["key0"]){
								processXml = result[0]["key0"];
								nowXmlObj = XML_CreateByContent(processXml);
							}
						}
						var nowXml = (nowXmlObj.xml || new XMLSerializer().serializeToString(nowXmlObj));
						if(nowXml != oldXml){
							isCanEdit = false;
							if (window.LUI && json.msg) {
								seajs.use(['lui/dialog'], function(dialog) {
									dialog.alert(json.msg);
								});
							}else{
								alert(json.msg);
							}
						}
					}
				}else{
					if(json.msg){
						if (window.LUI) {
							seajs.use(['lui/dialog'], function(dialog) {
								dialog.alert(json.msg);
							});
						}else{
							alert(json.msg);
						}
					}
				}
			}
		}
	});
	return isCanEdit;
}

//提交时，去掉handlerIds，handlerNames值，此值无用且不去掉，为矩阵之类时会出非法字符提示
Com_Parameter.event["confirm"].push(function(){
	$(".lbpm_freeflow_draglist .lbpm_freeflow_draglist_li,.lbpm_freeflow_draglist_li_passed").each(function(){
		var $handlerInfoDiv = $(this).find(".lbpm_freeflow_handlerName");
		$handlerInfoDiv.find("input[name$=handlerIds]").val("");
		$handlerInfoDiv.find("input[name$=handlerNames]").val("");
	});
	//清除锁信息
	var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmFreeflowVersionAction.do?method=deleteVersionOnConfirm';
	var data = {"fdProcessId":lbpm.modelId};
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async : false,
		dataType : "json",
		success : function(json){

		}
	});
	return true;
});
//提交失败，更新节点列表，因上面去掉了handlerIds，handlerNames值，需重新添加
Com_Parameter.event["submit_failure_callback"].push(function(){
	//更新节点列表
	lbpm.globals.updateFreeFlowNodeUL();
});

//自由流保存时，若进行了修改流程图操作，需校验流程
Com_Parameter.event["submit"].push(function(){
	if(lbpm.isFreeFlow){
		if(lbpm.globals.isCanEdit()){
			if(lbpm.modifys){
				var FlowChartObject = getFreeFlowChartObject();
				if(FlowChartObject){
					return FlowChartObject.CheckFlow(true);
				}
			}
		}else{
			return false;
		}
	}
	return true;
});

lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, lbpm.globals.updateFreeFlowNodeUL);