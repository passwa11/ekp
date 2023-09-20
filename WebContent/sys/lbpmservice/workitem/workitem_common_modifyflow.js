lbpm.onLoadEvents.delay.push(function(){ 
	if(lbpm.nowProcessorInfoObj==null) return;
	lbpm.globals.initNotionPopedomTR();
}); 
//修改其他节点处理人
lbpm.globals.changeProcessorClick=function (){
	var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
	if(operatorInfo == null){
		return;
	}
	if (Com_Parameter.dingXForm === "true" && typeof seajs != "undefined") {
		lbpm.globals.dingChangeProcessorClick();
		return;
	}
	var handlerId = lbpm.globals.getRolesSelectObj();
	var url = Com_Parameter.ContextPath+"sys/lbpmservice/workitem/sysLbpmMain_mainframe.jsp?nodeId=" + lbpm.nowNodeId + "&handlerIdentity=" + handlerId + "&fdModelName="+lbpm.modelName+"&fdModelId="+lbpm.modelId;
	var seaJsUrl = "/sys/lbpmservice/workitem/sysLbpmMain_mainframe.jsp?nodeId=" + lbpm.nowNodeId + "&handlerIdentity=" + handlerId + "&fdModelName="+lbpm.modelName+"&fdModelId="+lbpm.modelId;
	var IndicatorDiv;
	
	//var getFormFieldListFunc="lbpm.globals.getFormFieldList_"+lbpm.constant.FDKEY;
	//var fieldList = (new Function('return (' + getFormFieldListFunc + '());'))();
	var param = {};
	lbpm.globals.getThroughNodes(function(throughtNodes){
		lbpm.globals.clearIndicatorDiv(IndicatorDiv);
		param = {
		     FormFieldList:lbpm.globals.getFormFieldList(),
		     win:this,
		     throughtNodes:throughtNodes,
		     AfterShow:function(rtnVal){
		    	 if(rtnVal != null){
		    		this.win.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,rtnVal);		
		 		  }
				}
		    };
		},
		function (){
			IndicatorDiv = lbpm.globals.openIndicatorDiv(this);
		},
		function(){
			
		},
		false,lbpm.nowNodeId
	);
	// 解决跨域问题
	var isCrossDomain = false;
	try{
		top.Com_Parameter.Dialog = param;
	}catch (e){
		isCrossDomain = true;
		if(window.console){
			console.log(e);
		}
	}
	try{
		if (!isCrossDomain && typeof seajs != "undefined") {
			top.Com_Parameter.Dialog = param;
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				dialog.iframe(seaJsUrl,"修改处理人",function(rtnVal){
					if(rtnVal != null){
						lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,rtnVal);
					}
				},{width:1080,height:640,params:param});
			});
		}else{
			lbpm.globals.popupWindow(url,720,400,param);
		}

	}catch(e){
		if(window.console){
			console.error(e);
		}
	}
}

lbpm.globals.dingChangeProcessorClick = function(){
	var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
	if(operatorInfo == null){
		return;
	}
	var handlerId = lbpm.globals.getRolesSelectObj();
	var url = "/sys/lbpmservice/workitem/dingSuit/sysLbpmMain_mainframe.jsp?nodeId=" + lbpm.nowNodeId + "&handlerIdentity=" + handlerId + "&fdModelName="+lbpm.modelName+"&fdModelId="+lbpm.modelId;

	var IndicatorDiv;
	
	var param = {};
	lbpm.globals.getThroughNodes(function(throughtNodes){
			lbpm.globals.clearIndicatorDiv(IndicatorDiv);
			param = {
			     FormFieldList:lbpm.globals.getFormFieldList(),
			     win:this,
			     throughtNodes:throughtNodes
			}
		},
		function (){
			IndicatorDiv = lbpm.globals.openIndicatorDiv(this);
		},
		function(){
			
		},
		false,lbpm.nowNodeId
	);
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,"修改处理人",function(rtnVal){
			 if(rtnVal != null){
		    	lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,rtnVal);		
		 	}
		},{width:1080,height:640,params:param});
	});
}


//修改流程信息
lbpm.globals.modifyProcess=function(contentField, statusField){
	if(lbpm.isFreeFlow && (lbpm.approveType=="right" || lbpm.constant.ROLETYPE=='' || lbpm.constant.ROLETYPE==lbpm.constant.PROCESSORROLETYPE)){
		lbpm.globals.modifyFreeFlow(contentField, statusField);
		return;
	}
	var fieldList = lbpm.globals.getFormFieldList();

	if(typeof(_thirdSysFormList) == "object" ){//第三方系统集成表单参数
		fieldList = fieldList.concat(_thirdSysFormList);
	}
	var param = {
		processData: lbpm.globals.getProcessXmlString(),
		statusData: document.getElementsByName(statusField)[0].value,
		Window:window,
		FormFieldList:fieldList,
		AfterShow:function(rtnVal,otherContentInfo){
			if(rtnVal!=null){
				var param={};
			    param.xml=rtnVal;
			    if(this.Window.lbpm && this.Window.lbpm.events)
			    	this.Window.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
			    else
			    	this.Window.parent.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
			}
			if(!lbpm.isFreeFlow){
				if(otherContentInfo){
					//处理流程其他数据，设置到页面字段上，兼容修改流程图
					this.Window.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODERULE,otherContentInfo);
				}
			}
		}
	};
	//TODO 这个字段暂时默认都为0
	var fdIsAllowSetupApprovalType="0";
	//var fdIsAllowSetupApprovalType = document.getElementById("sysWfBusinessForm.fdIsAllowSetupApprovalType").value;
	var fdTemplateModelName = document.getElementById("sysWfBusinessForm.fdTemplateModelName").value;
	var fdTemplateKey = document.getElementById("sysWfBusinessForm.fdTemplateKey").value;
	var modelName = lbpm.globals.getWfBusinessFormModelName();
	var url=Com_Parameter.ContextPath+'sys/lbpm/flowchart/page/panel.html?edit=true&extend=oa&template=false&modelName='+modelName+'&deployApproval=' + fdIsAllowSetupApprovalType + '&templateModel=' + fdTemplateModelName + '&templateKey=' + fdTemplateKey + "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&popup=true&modelingModelId=" + lbpm.modelingModelId;
	var seaJsPath = '/sys/lbpm/flowchart/page/panel.html?edit=true&extend=oa&template=false&modelName='+modelName+'&deployApproval=' + fdIsAllowSetupApprovalType + '&templateModel=' + fdTemplateModelName + '&templateKey=' + fdTemplateKey + "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&popup=true&modelingModelId=" + lbpm.modelingModelId;
	if(lbpm.isFreeFlow){
		url=Com_Parameter.ContextPath+'sys/lbpm/flowchart/page/freeflowPanel.jsp?edit=true&extend=oa&template=false&flowType=1&modelName='+modelName+'&deployApproval=' + fdIsAllowSetupApprovalType + '&templateModel=' + fdTemplateModelName + '&templateKey=' + fdTemplateKey + "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&popup=true&modelingModelId=" + lbpm.modelingModelId;
		seaJsPath = '/sys/lbpm/flowchart/page/freeflowPanel.jsp?edit=true&extend=oa&template=false&flowType=1&modelName='+modelName+'&deployApproval=' + fdIsAllowSetupApprovalType + '&templateModel=' + fdTemplateModelName + '&templateKey=' + fdTemplateKey + "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&popup=true&modelingModelId=" + lbpm.modelingModelId;

	}else{
		var otherContentInfoObj = document.getElementById("sysWfBusinessForm.fdOtherContentInfo");
		if(otherContentInfoObj){
			var otherContentInfo = otherContentInfoObj.value;
			param.otherContentInfo = otherContentInfo;
		}
	}
	if (typeof seajs != "undefined") {
		top.Com_Parameter.Dialog = param;
		var roleTitle = lbpm.constant.lbpmNode_modifyFlow;
		var screenWidth = window.screen.width;
		var screenHeight = window.screen.height;
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			dialog.iframe(seaJsPath,roleTitle,function(rtnVal){
				if(rtnVal != null){
					if(rtnVal != null && param.AfterShow){
						param.AfterShow(rtnVal);
					}
				}                        
			},{width:screenWidth,height:screenHeight,params:param});
		});
	}else{
		lbpm.globals.popupWindow(url,window.screen.width,window.screen.height,param);
	}
}
//获取流程图XML
lbpm.globals.getProcessXmlString=function(){
	// 到服务器加载详细信息
	var processXml = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0].value;
	var data = new KMSSData();
	var fdIsModify=$("input[name='sysWfBusinessForm.fdIsModify']")[0];
	if(fdIsModify==null || fdIsModify.value!="1"){
		// data.SendToUrl(Com_Parameter.ContextPath + "sys/lbpm/flowchart/page/detail.jsp?processId=" + lbpm.globals.getWfBusinessFormModelId(), function(req) {
		// 	processXml = req.responseText;
		// }, false);
		data.AddBeanData("lbpmProcessDefinitionDetailService&processId="+lbpm.globals.getWfBusinessFormModelId());
		var result = data.GetHashMapArray();
		if(result && result.length>0){
			if(result[0]["key0"]){
				processXml = result[0]["key0"];
			}
		}
	}
	var xmlObj = XML_CreateByContent(processXml);	
	var xmlNodes = XML_GetNodesByPath(xmlObj,"/*/nodes/*");
	if(xmlNodes && lbpm.modifys){
		$.each(lbpm.modifys, function(index, nodeData) {
			for(var i=0,l=xmlNodes.length;i <l;i++){
				if(xmlNodes[i].getAttribute("id") == nodeData.id){
					for(nodeField in nodeData){
						xmlNodes[i].setAttribute(nodeField,nodeData[nodeField]);
					}	
				}	
			}
		});	
	}	
	return (xmlObj.xml || new XMLSerializer().serializeToString(xmlObj));
};

//选择可查看当前节点的节点add by limh 2010年9月14日
lbpm.globals.selectNotionNodes=function(){
	var curNodeId = lbpm.nowNodeId;
	var data = new KMSSData();

	$.each(lbpm.nodes, function(index, node) {
		if(node.id != curNodeId){
			var langNodeName = WorkFlow_getLangLabel(node.name,node["langs"],"nodeName");
			if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_HANDLER,node)) data.AddHashMap({id:node.id, name:node.id+"."+langNodeName});
		}
	});
	var dialog = new KMSSDialog(true, true);
	dialog.winTitle = "";
	dialog.AddDefaultOption(data);
	dialog.BindingField(document.getElementsByName("wf_nodeCanViewCurNodeIds")[0], document.getElementsByName("wf_nodeCanViewCurNodeNames")[0], ";");
	dialog.SetAfterShow(function myFunc(rtv){lbpm.globals.updateXml(rtv,"nodeCanViewCurNode");});
	dialog.Show();
}

//将其他可阅读者设定写回fdcontent域 add by limh 2010年9月24日
lbpm.globals.updateXml=function(rtv,type){
	var curNodeId = lbpm.nowNodeId;
	if(rtv){
		//返回json对象
		var rtnNodesMapJSON= new Array();
		var ids = lbpm.globals.arrayToStringByKey(rtv.GetHashMapArray(),"id");
		var names = lbpm.globals.arrayToStringByKey(rtv.GetHashMapArray(),"name");
		if(type=="otherCanViewCurNode"){
			rtnNodesMapJSON.push({
				id:curNodeId,
				otherCanViewCurNodeIds:ids,
				otherCanViewCurNodeNames:names,
				orgattr:"otherCanViewCurNodeIds:otherCanViewCurNodeNames"
			});
		}else if(type=="nodeCanViewCurNode"){
			rtnNodesMapJSON.push({
				id:curNodeId,
				nodeCanViewCurNodeIds:ids,
				nodeCanViewCurNodeNames:names
			});
		}	
		
		var param={};
		param.nodeInfos=rtnNodesMapJSON;
		lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,param);
	}	
};

lbpm.globals.isRootWorkitemOperation = function() {
	if (!lbpm.nowProcessorInfoObj)
		return false;
	var isRootWorkitemOperation = false;
	$.each(lbpm.nowProcessorInfoObj.operations, function() {
		if (lbpm.operations[this.id] && lbpm.operations[this.id].isPassType) {
			isRootWorkitemOperation = true;
			return false;
		}
	});
	return isRootWorkitemOperation;
};

//初始化意见权限add by limh 2010年9月24日
lbpm.globals.initNotionPopedomTR=function(){
	var curNodeId = lbpm.nowProcessorInfoObj.nodeId;
	var wf_nodeCanViewCurNodeIds = document.getElementsByName("wf_nodeCanViewCurNodeIds")[0];
	if (wf_nodeCanViewCurNodeIds == null) {
		return;
	}
	var wf_nodeCanViewCurNodeNames = document.getElementsByName("wf_nodeCanViewCurNodeNames")[0];
	var wf_otherCanViewCurNodeIds = document.getElementsByName("wf_otherCanViewCurNodeIds")[0];
	var wf_otherCanViewCurNodeNames = document.getElementsByName("wf_otherCanViewCurNodeNames")[0];

	var nodeCanViewCurNodeTR = document.getElementById("nodeCanViewCurNodeTR");
	var otherCanViewCurNodeTR = document.getElementById("otherCanViewCurNodeTR");
	var curNodeInfoObj = lbpm.globals.getNodeObj(curNodeId);	

	if (!lbpm.globals.isRootWorkitemOperation()) {
		lbpm.globals.hiddenObject(nodeCanViewCurNodeTR, true);
		lbpm.globals.hiddenObject(otherCanViewCurNodeTR, true);
		//隐藏高级标签
		$('#lbpm_highLevelTab').each(function() {
			lbpm.globals.setNotionPopedomTRHidden(this);
		});
		return;
	}
	//如果是可修改意见权限
	if(curNodeInfoObj.canModifyNotionPopedom=="true"){
		lbpm.globals.hiddenObject(nodeCanViewCurNodeTR, false);
		lbpm.globals.hiddenObject(otherCanViewCurNodeTR, false);
		//为权限设置域赋值
		if(curNodeInfoObj.nodeCanViewCurNodeIds){
			wf_nodeCanViewCurNodeIds.value=curNodeInfoObj.nodeCanViewCurNodeIds;
		}
		if(curNodeInfoObj.nodeCanViewCurNodeNames){
			wf_nodeCanViewCurNodeNames.value=curNodeInfoObj.nodeCanViewCurNodeNames;
		}
		if(curNodeInfoObj.otherCanViewCurNodeIds){
			wf_otherCanViewCurNodeIds.value=curNodeInfoObj.otherCanViewCurNodeIds;
		}
		if(curNodeInfoObj.otherCanViewCurNodeNames){
			wf_otherCanViewCurNodeNames.value=curNodeInfoObj.otherCanViewCurNodeNames;
		}	
	}
	//如果是不可修改意见权限
	else{
		lbpm.globals.hiddenObject(nodeCanViewCurNodeTR, true);
		lbpm.globals.hiddenObject(otherCanViewCurNodeTR, true);
		//隐藏高级标签
		$('#lbpm_highLevelTab').each(function() {
			lbpm.globals.setNotionPopedomTRHidden(this);
		});
	}
};

//判断当前节点是否具有修改流程信息的权限
lbpm.globals.checkModifyAuthorization=function(currentNodeId){
	var currentNodeObj = lbpm.globals.getNodeObj(currentNodeId);
	var modifyFlowDIV = document.getElementById("modifyFlowDIV");
	var changeProcessorDIV = document.getElementById("changeProcessorDIV");
	var modifyEmbeddedSubFlowDIV = document.getElementById("modifyEmbeddedSubFlowDIV");
	var checkChangeFlowTR = document.getElementById("checkChangeFlowTR");
	
	if((lbpm.constant.ROLETYPE == "" || lbpm.constant.ROLETYPE == lbpm.constant.PROCESSORROLETYPE)&&!lbpm.isFreeFlow){
		var hiddenRowFlag = 0;
		var showChangeFlowTR = lbpm.globals.isRootWorkitemOperation();
		if(currentNodeObj.canModifyFlow == "true"){
			if(modifyFlowDIV != null && showChangeFlowTR){
				lbpm.globals.hiddenObject(modifyFlowDIV, false);
			}
		}else{
			if(modifyFlowDIV != null){
				lbpm.globals.hiddenObject(modifyFlowDIV, true);
				hiddenRowFlag++;
			}
		}
		if(((currentNodeObj.canModifyHandlerNodeIds != null && currentNodeObj.canModifyHandlerNodeIds != "")
				|| (currentNodeObj.mustModifyHandlerNodeIds != null && currentNodeObj.mustModifyHandlerNodeIds != ""))){
			var embeddedNodeIds = [];
			var otherNodeNum = 0;
			if(currentNodeObj.canModifyHandlerNodeIds){
				var nodeIds = currentNodeObj.canModifyHandlerNodeIds.split(";");
				for(var i =0;i<nodeIds.length;i++){
					if(lbpm.nodes[nodeIds[i]]){
						if(lbpm.nodes[nodeIds[i]].XMLNODENAME == "embeddedSubFlowNode"){
							embeddedNodeIds.push(nodeIds[i]);
						}else{
							otherNodeNum++;
						}
					}
				}
			}
			if(currentNodeObj.mustModifyHandlerNodeIds){
				var nodeIds = currentNodeObj.mustModifyHandlerNodeIds.split(";");
				for(var i =0;i<nodeIds.length;i++){
					if(lbpm.nodes[nodeIds[i]]){
						if(lbpm.nodes[nodeIds[i]].XMLNODENAME == "embeddedSubFlowNode"){
							embeddedNodeIds.push(nodeIds[i]);
						}else{
							otherNodeNum++;
						}
					}
				}
			}
			if(changeProcessorDIV != null && showChangeFlowTR && otherNodeNum>0){
				lbpm.globals.hiddenObject(changeProcessorDIV, false);
			}
			if(modifyEmbeddedSubFlowDIV != null && showChangeFlowTR && embeddedNodeIds.length>0){
				lbpm.globals.hiddenObject(modifyEmbeddedSubFlowDIV, false);
				lbpm.globals.setModifyEmbeddedSubFlowDivInfo(embeddedNodeIds,modifyEmbeddedSubFlowDIV);
			}
		}else{
			if(changeProcessorDIV != null){
				lbpm.globals.hiddenObject(changeProcessorDIV, true);
				hiddenRowFlag++;
			}
			if(modifyEmbeddedSubFlowDIV != null){
				lbpm.globals.hiddenObject(modifyEmbeddedSubFlowDIV, true);
				hiddenRowFlag++;
			}
		}
		if(hiddenRowFlag == 3){
			lbpm.globals.hiddenObject(checkChangeFlowTR, true);
		}else{
			var oprNames=lbpm.globals.getOperationParameterJson("operations");
			if(oprNames == null || oprNames.length==0){
				lbpm.globals.hiddenObject(checkChangeFlowTR, true);
			}else{
				if (showChangeFlowTR) {
					lbpm.globals.hiddenObject(checkChangeFlowTR, false);
				} else {
					lbpm.globals.hiddenObject(checkChangeFlowTR, true);
				}
			}
		}
	}else{
		lbpm.globals.hiddenObject(modifyFlowDIV, true);
		lbpm.globals.hiddenObject(changeProcessorDIV, true);
		lbpm.globals.hiddenObject(checkChangeFlowTR, true);
	}
};
lbpm.globals.setModifyEmbeddedSubFlowDivInfo = function(embeddedNodeIds,modifyEmbeddedSubFlowDIV){
	$(modifyEmbeddedSubFlowDIV).html("");
	for(var i=0;i<embeddedNodeIds.length;i++){
		$(modifyEmbeddedSubFlowDIV).append("<a class='com_btn_link' onclick='lbpm.globals.modifyEmbeddedSubFlow(\""+embeddedNodeIds[i]+"\")'>"+lbpm.constant.select_modify+(lbpm.globals.lbpmIsHideAllNodeIdentifier()?"":(embeddedNodeIds[i]+"."))+lbpm.nodes[embeddedNodeIds[i]].name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;")
	}
};

lbpm.globals.modifyEmbeddedSubFlow = function(embeddedNodeId){
	if(embeddedNodeId){
		//嵌入子流程根据redId获得流程图xml
		var getContentByRefId = function(fdRefId){
			var fdContent = "";
			var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=getContentByRefId&fdRefId='+fdRefId;
			var kmssData = new KMSSData();
			kmssData.SendToUrl(ajaxurl, function(http_request) {
				var responseText = http_request.responseText;
				var json = eval("("+responseText+")");
				if (json.fdContent){
					fdContent = json.fdContent;
				}
			},false);
			return fdContent;
		}
		//获得组节点之间的连线
		var getGroupLineById = function(nodeId,flowInfo){
			for(var i=0;i<flowInfo.nodes.length;i++){
				if(flowInfo.nodes[i].groupNodeId == nodeId && flowInfo.nodes[i].XMLNODENAME == ("groupStartNode")){
					for(var j=0;j<flowInfo.lines.length;j++){
						if(flowInfo.lines[j]["startNodeId"] == flowInfo.nodes[i].id){
							return flowInfo.lines[j];
						}
					}
				}
			}
			return null;
		}
		//需要从组节点继承的属性
		var needCopyData = ["subFormId","subFormName","subFormMobileId","subFormMobileName","subFormPrintId", "subFormPrintName",
		    "nodeCanViewCurNodeIds","nodeCanViewCurNodeNames","otherCanViewCurNodeIds","otherCanViewCurNodeNames","canModifyNotionPopedom","canModifyFlow"];
		//从组节点继承属性
		var setSubNodeInfoByGroupNode = function(newNode,groupNode){
			for(var i=0;i<needCopyData.length;i++){
				if((!newNode[needCopyData[i]] || newNode[needCopyData[i]]=="false") && groupNode[needCopyData[i]] != undefined){
					newNode[needCopyData[i]] = groupNode[needCopyData[i]];
				}
			}
		}
		//当前流程图对象
		var nowFlow = WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
		for(var i=0;i<nowFlow.nodes.length;i++){
			if(nowFlow.nodes[i].id == embeddedNodeId && nowFlow.nodes[i].embeddedRefId){
				var nowNode = nowFlow.nodes[i];
				var subNodesXML = "";
				if(nowNode.isInit == "true"){
					// 构建初始的空白子流程的processData
					var processData = new Array();
					processData.XMLNODENAME = "process";
					processData.nodes = new Array();
					processData.lines = new Array();
					var nodeIds = new Array();
					var groupEndNodeId = "";
					for(var j=0;j<nowFlow.nodes.length;j++){
						if(nowFlow.nodes[j].groupNodeId == embeddedNodeId){
							if(nowFlow.nodes[j].XMLNODENAME != "groupStartNode" &&  nowFlow.nodes[j].XMLNODENAME != "groupEndNode"){
								var newNode = $.extend(true, {}, nowFlow.nodes[j]);
								newNode.x = -newNode.x;
								newNode.y = -newNode.y;
								processData.nodes.push(newNode);
								nodeIds.push(nowFlow.nodes[j].id);
								nowFlow.nodes.splice(j, 1);
								j--;
							}else if(nowFlow.nodes[j].XMLNODENAME == "groupEndNode"){
								groupEndNodeId = nowFlow.nodes[j].id;
							}
						}
					}
					for(var j=0;j<nowFlow.lines.length;j++){
						if(Com_ArrayGetIndex(nodeIds, nowFlow.lines[j].startNodeId) > -1 && nowFlow.lines[j].endNodeId!=groupEndNodeId){
							var _line = $.extend(true, {}, nowFlow.lines[j])
							if(_line._points){
								_line.points = _line._points;
							}
							processData.lines.push(_line);
							nowFlow.lines.splice(j, 1);
							j--;
						}
					}
					processData.nodesIndex = nowFlow.nodesIndex;
					processData.linesIndex = nowFlow.linesIndex;
					subNodesXML = WorkFlow_BuildXMLString(processData);
				}else{
					subNodesXML = getContentByRefId(nowFlow.nodes[i].embeddedRefId);
					if(nowFlow.nodes[i].extAttributes){
						for(var j = 0;j<nowFlow.nodes[i].extAttributes.length;j++){
							if(nowFlow.nodes[i].extAttributes[j].name == "paramsConfig" && nowFlow.nodes[i].extAttributes[j].value){
								var paramsConfig = JSON.parse(nowFlow.nodes[i].extAttributes[j].value);
								subNodesXML = subNodesXML.replace(/\$(\w+)\$/g,function($1,$2){
									for(var k=0;k<paramsConfig.length;k++){
										if(paramsConfig[k].fdParamValue == $2){
											return "$"+paramsConfig[k].fdFormValue+"$";
										}
									}
									return $1;
								});
								break;
							}
						}
					}
				}
				var fieldList = lbpm.globals.getFormFieldList();

				if(typeof(_thirdSysFormList) == "object" ){//第三方系统集成表单参数
					fieldList = fieldList.concat(_thirdSysFormList);
				}
				
				var param = {
					processData: subNodesXML,
					statusData: document.getElementsByName("sysWfBusinessForm.fdTranProcessXML")[0].value,
					Window:window,
					FormFieldList:fieldList,
					AfterShow:function(rtnVal){
						if(rtnVal!=null){
							var modifyFlow = WorkFlow_LoadXMLData(rtnVal);
							if(nowNode.isInit == "true"){
								nowFlow.nodesIndex = modifyFlow.nodesIndex;
								nowFlow.linesIndex = modifyFlow.linesIndex;
								for(var j=0;j<modifyFlow.nodes.length;j++){
									var newNode = $.extend(true, {}, modifyFlow.nodes[j]);
									newNode.x = -newNode.x;
									newNode.y = -newNode.y;
									newNode.id = newNode.id.replace("E","");
									newNode.groupNodeId = nowNode.id;
									newNode.groupNodeType = "embeddedSubFlowNode";
									nowFlow.nodes.push(newNode);
								}
								for(var j=0;j<modifyFlow.lines.length;j++){
									var newLine = $.extend(true, {}, modifyFlow.lines[j]);
									newLine.id = newLine.id.replace("E","");
									newLine.startNodeId = newLine.startNodeId.replace("E","");
									newLine.endNodeId = newLine.endNodeId.replace("E","");
									newLine._points = newLine.points;
									delete newLine.points;
									nowFlow.lines.push(newLine);
								}
								//找出没有连入和连出的节点
								var noStartId = "";var noEndId = "";
								for(var j = 0;j<modifyFlow.nodes.length;j++){
									var newNodeId = modifyFlow.nodes[j].id;
									var isExitStart = false;var isExitEnd = false;
									for(var k = 0;k<modifyFlow.lines.length;k++){
										if(modifyFlow.lines[k].startNodeId == newNodeId){
											isExitStart = true;
										}
										if(modifyFlow.lines[k].endNodeId == newNodeId){
											isExitEnd = true;
										}
									}
									if(!isExitStart){
										noStartId = newNodeId.replace("E","");;
									}
									if(!isExitEnd){
										noEndId = newNodeId.replace("E","");;
									}
								}
								for(var j=0;j<nowFlow.nodes.length;j++){
									if(nowFlow.nodes[j].groupNodeId == nowNode.id && nowFlow.nodes[j].XMLNODENAME == "groupStartNode"){
										for(var k=0;k<nowFlow.lines.length;k++){
											if(nowFlow.lines[k]["startNodeId"] == nowFlow.nodes[j].id){
												nowFlow.lines[k].endNodeId = noEndId;
												break;
											}
										}
									}else if(nowFlow.nodes[j].groupNodeId == nowNode.id && nowFlow.nodes[j].XMLNODENAME == "groupEndNode"){
										for(var k=0;k<nowFlow.lines.length;k++){
											if(nowFlow.lines[k]["endNodeId"] == nowFlow.nodes[j].id){
												nowFlow.lines[k].startNodeId = noStartId;
												break;
											}
										}
									}
								}
								if(!lbpm.lbpmEmbeddedInfo){
									lbpm.lbpmEmbeddedInfo = [];
								}
								var param={};
							    param.xml=WorkFlow_BuildXMLString(nowFlow);
							    if(this.Window.lbpm && this.Window.lbpm.events)
							    	this.Window.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
							    else
							    	this.Window.parent.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
							}else{
								//当前节点下标
								var nowNodeIndex = parseInt(nowFlow.nodesIndex, 10);
								//当前连线下标
								var nowLineIndex = parseInt(nowFlow.linesIndex, 10);
								//嵌入的节点及连线信息
								var newNodeAndLineInfo = {};
								//嵌入子流程节点，fdRefId对应信息
								var fdEmbeddedInfo = [];
								//新旧节点ID对应关系,key:旧id，value:新id
								var nodeInfo = {};
								//嵌入的节点
								var newNodes = [];
								for(var j = 0;j<modifyFlow.nodes.length;j++){
									//复制嵌入的节点，添加groupNodeId，groupNodeType，坐标变为负的（隐藏），
									//重置id，并记录新旧节点id对应关系，添加到嵌入的节点数组中
									var newNode = $.extend(true, {}, modifyFlow.nodes[j]);
									newNode.groupNodeId = nowNode.id;
									newNode.groupNodeType = "embeddedSubFlowNode";
									newNode.x = -newNode.x;
									newNode.y = -newNode.y;
									nodeInfo[modifyFlow.nodes[j].id] = newNode.id = "N"+(++nowNodeIndex);
									// 记录原始子节点ID到扩展属性中
									if(!newNode.extAttributes){
										newNode.extAttributes = [];
									}
									var extAttribute = [];
									extAttribute.XMLNODENAME = "attribute";
									extAttribute.name = "sourceSubId";
									extAttribute.value = modifyFlow.nodes[j].id;
									newNode.extAttributes.push(extAttribute);
									//从组节点继承属性
									setSubNodeInfoByGroupNode(newNode,nowNode);
									newNodes.push(newNode);
								}
								lbpm.globals.replaceModifyHandlerNodeIds(newNodes,nodeInfo,nowFlow);
								//将嵌入的节点记录到嵌入的节点及连线信息对象中，key：当前嵌入子流程节点id
								newNodeAndLineInfo[nowNode.id] = {};
								newNodeAndLineInfo[nowNode.id]["newNodes"] = newNodes;
								//嵌入的连线
								var newLines = [];
								for(var j = 0;j<modifyFlow.lines.length;j++){
									//复制嵌入的连线，根据记录的新旧节点id对应关系修改startNodeId，endNodeId
									//重置id，添加到嵌入的连线数组中
									var newLine = $.extend(true, {}, modifyFlow.lines[j]);
									newLine.startNodeId = nodeInfo[newLine.startNodeId];
									newLine.endNodeId = nodeInfo[newLine.endNodeId];
									newLine._points = newLine.points;
									delete newLine.points;
									newLine.id = "L"+(++nowLineIndex);
									newLines.push(newLine);
								}
								//将嵌入的连线记录到嵌入的节点及连线信息对象中，key：当前嵌入子流程节点id
								newNodeAndLineInfo[nowNode.id]["newLines"] = newLines;
								nowNode.isInit = "true";
								fdEmbeddedInfo.push({nodeId:nowNode.id,fdRefId:nowNode.embeddedRefId});
									
								for(var nodeId in newNodeAndLineInfo){
									//遍历嵌入的节点及连线信息对象，将嵌入的节点及嵌入的连线合并到当前流程图对象中
									nowFlow.nodes = nowFlow.nodes.concat(newNodeAndLineInfo[nodeId]["newNodes"]);
									nowFlow.lines = nowFlow.lines.concat(newNodeAndLineInfo[nodeId]["newLines"]);
									//找出没有连入和连出的节点
									var noStartId = "";var noEndId = "";
									for(var i = 0;i<newNodeAndLineInfo[nodeId]["newNodes"].length;i++){
										var newNodeId = newNodeAndLineInfo[nodeId]["newNodes"][i].id;
										var isExitStart = false;var isExitEnd = false;
										for(var j = 0;j<newNodeAndLineInfo[nodeId]["newLines"].length;j++){
											if(newNodeAndLineInfo[nodeId]["newLines"][j].startNodeId == newNodeId){
												isExitStart = true;
											}
											if(newNodeAndLineInfo[nodeId]["newLines"][j].endNodeId == newNodeId){
												isExitEnd = true;
											}
										}
										if(!isExitStart){
											noStartId = newNodeId;
										}
										if(!isExitEnd){
											noEndId = newNodeId;
										}
									}
									//找到组之间的连线
									var line = getGroupLineById(nodeId,nowFlow);
									if(line!=null){
										//复制一份，一个修改startNodeId为没有连入节点，一个修改endNodeId为没有连出的节点
										var newLine = $.extend(true, {}, line);
										newLine.startNodeId = noStartId;
										line.endNodeId = noEndId;
										newLine.id = "L"+(++nowLineIndex);
										newLine._points = newLine.points;
										line._points = line.points;
										delete newLine.points;
										delete line.points;
										//添加复制的连线到当前流程图对象中
										nowFlow.lines.push(newLine);
									}
								}
								if(fdEmbeddedInfo.length>0){
									//修改当前流程图对象的节点下标及连线下标
									nowFlow.nodesIndex = nowNodeIndex+"";
									nowFlow.linesIndex = nowLineIndex+"";
									if(!lbpm.lbpmEmbeddedInfo){
										lbpm.lbpmEmbeddedInfo = [];
									}
									lbpm.lbpmEmbeddedInfo = lbpm.lbpmEmbeddedInfo.concat(fdEmbeddedInfo);
									var param={};
								    param.xml=WorkFlow_BuildXMLString(nowFlow);
								    if(this.Window.lbpm && this.Window.lbpm.events)
								    	this.Window.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
								    else
								    	this.Window.parent.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
								}
							}
						}
					}
				};
				//TODO 这个字段暂时默认都为0
				var fdIsAllowSetupApprovalType="0";
				//var fdIsAllowSetupApprovalType = document.getElementById("sysWfBusinessForm.fdIsAllowSetupApprovalType").value;
				var fdTemplateModelName = document.getElementById("sysWfBusinessForm.fdTemplateModelName").value;
				var fdTemplateKey = document.getElementById("sysWfBusinessForm.fdTemplateKey").value;
				var modelName = lbpm.globals.getWfBusinessFormModelName();
				
				var url=Com_Parameter.ContextPath+'sys/lbpm/flowchart/page/panel.html?embedded=true&edit=true&extend=oa&template=false&modelName='+modelName+'&deployApproval=' + fdIsAllowSetupApprovalType + '&templateModel=' + fdTemplateModelName + '&templateKey=' + fdTemplateKey + "&modelId=" + lbpm.globals.getWfBusinessFormModelId();
				lbpm.globals.popupWindow(url,window.screen.width,window.screen.height,param);
				break;
			}
		}
	}
};

lbpm.globals.replaceModifyHandlerNodeIds=function(nodes,nodeMap,nowFlow){
	//替换组节点中相互引用节点id
	var attMap = ["defaultStartBranchIds","relatedNodeIds","canModifyHandlerNodeIds","mustModifyHandlerNodeIds","canHandlerJumpNodeIds","nodeCanViewCurNodeIds","deRefuseNodeIds","refuseNodeIds"];
	for(var i=0;i<nodes.length; i++){
		var nodeObj = nodes[i];
		for(var h=0;h<attMap.length; h++){
			var attr = attMap[h];
			if(nodeObj[attr]){
				var oldIds = nodeObj[attr].split(";");
				for(var j = 0;j<oldIds.length;j++){
					if(nodeMap[oldIds[j]]){
						oldIds[j] = nodeMap[oldIds[j]];
					}
				}
				nodeObj[attr] = oldIds.join(";");
			}
		}
	}
	if(nodes.length>0 && nodes[0].groupNodeId){
		var groupNodeId = nodes[0].groupNodeId;
		if(nowFlow.opinionSortIds){
			//替换意见排序引入组节点的id
			var oIds = nowFlow.opinionSortIds.split(";");
			for(var i = 0;i<oIds.length;i++){
				if(oIds[i] && oIds[i].indexOf("-")>-1){
					for(var key in nodeMap){
						if(oIds[i] == groupNodeId+"-"+key){
							oIds[i] = nodeMap[key];
							break;
						}
					}
				}
			}
			nowFlow.opinionSortIds = oIds.join(";");
		}
		//替换外部节点引用组里面节点的id属性，目前仅下列两项
		var nAttMap = ["canModifyHandlerNodeIds","mustModifyHandlerNodeIds","distributeNoteNodeIds"];
		for(var i=0;i<nowFlow.nodes.length; i++){
			var nodeObj = nowFlow.nodes[i];
			for(var h=0;h<nAttMap.length; h++){
				var attr = nAttMap[h];
				if(nodeObj[attr]){
					var oldIds = nodeObj[attr].split(";");
					for(var j = 0;j<oldIds.length;j++){
						if(oldIds[j] && oldIds[j].indexOf("-")>-1){
							for(var key in nodeMap){
								if(oldIds[j] == groupNodeId+"-"+key){
									oldIds[j] = nodeMap[key];
									break;
								}
							}
						}
					}
					nodeObj[attr] = oldIds.join(";");
				}
			}
		}
	}
}

lbpm.globals.parseNodeHandler4CalcType = function(nodeObj){
	if(!nodeObj || (lbpm.nodeParseHandlers && lbpm.nodeParseHandlers[nodeObj.id])){
		return;//已经存在的不再进行解析
	}
	var isSendNode=lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj);
	var handlerArray = null;
	if (nodeObj.handlerSelectType=="formula") {
		handlerArray=lbpm.globals.formulaNextNodeHandler(nodeObj.handlerIds,true,isSendNode);
	} else if (nodeObj.handlerSelectType=="matrix") {
		handlerArray=lbpm.globals.matrixNextNodeHandler(nodeObj.handlerIds,true,isSendNode);
	} else if (nodeObj.handlerSelectType=="rule") {
		handlerArray=lbpm.globals.ruleNextNodeHandler(nodeObj.id, nodeObj.handlerIds,true,isSendNode);
	}
	if (handlerArray && handlerArray.length > 0) {
		var ids = '',names='';
		for ( var j = 0; j < handlerArray.length; j++) {
			ids += handlerArray[j].id + ";";
			names += handlerArray[j].name + ";";
		}
		ids = ids ? ids.substring(0,ids.length-1) : ids;
		names = names ? names.substring(0,names.length-1) : names;
		if(!lbpm.nodeParseHandlers){
			lbpm.nodeParseHandlers = {};
		}
		lbpm.nodeParseHandlers[nodeObj.id] = {id:ids,name:names};
	}
}
//设置公式定义器/矩阵组织/规则引起，解析后人员的id和name
lbpm.globals.setNodeParseHandler = function(nodeData){
	lbpm.globals.parseNodeHandler4CalcType(nodeData);
	if(lbpm.nodeParseHandlers && lbpm.nodeParseHandlers[nodeData.id]){
		var nodeParseHandler = lbpm.nodeParseHandlers[nodeData.id];
		lbpm.nodeParseHandlerId = nodeParseHandler.id ? {value:nodeParseHandler.id} : null;
		lbpm.nodeParseHandlerName = nodeParseHandler.name ? {value:nodeParseHandler.name} : null;
		//提供两个input结构给地址本作为field，这两个结构不会在页面存在，若是后续出现问题，可以加到页面上解决这个问题
		var idInput = document.createElement("input");
		idInput.value = nodeParseHandler.id;
		lbpm.nodeParseHandlerIdNode = nodeParseHandler.id ? idInput : null;
		var nameInput = document.createElement("input");
		nameInput.value = nodeParseHandler.name;
		lbpm.nodeParseHandlerNameNode = nodeParseHandler.name ? nameInput : null;
	}
}

//设置可以修改节点处理人HTML
lbpm.globals.getModifyHandlerHTML=function(nodeObj,hrefIndex,defaultHide,afterChangeFunc,dialogAddsFunc,formulaDialogFunc,handlerIdObj,handlerNameObj){
	if(hrefIndex==null) var hasIndex=false; else var hasIndex=true;
	if(dialogAddsFunc==null) dialogAddsFunc="lbpm.globals.dialog_Address";
	if(formulaDialogFunc==null) formulaDialogFunc="lbpm.globals.setFutureHandlerFormulaDialog";
	var html="";
	var handlerIdentity = (function() {
		if (nodeObj.optHandlerCalType == null || nodeObj.optHandlerCalType == '2') {
			var rolesSelectObj = $("select[name='rolesSelectObj']");
			if (rolesSelectObj.length > 0 && rolesSelectObj[0].selectedIndex > -1) {
				return rolesSelectObj.val();
			}
			return $("input[name='sysWfBusinessForm.fdIdentityId']").val();
		}
 		var rolesIdsArray = $("input[name='sysWfBusinessForm.fdHandlerRoleInfoIds']").val().split(";");
		return rolesIdsArray[0];
	})();
	var optHandlerIds = nodeObj.optHandlerIds == null?"":nodeObj.optHandlerIds;
	var nodeHandlerIds = nodeObj.handlerIds == null?"":nodeObj.handlerIds;
	var optHandlerSelectType = nodeObj.optHandlerSelectType == null?"org":nodeObj.optHandlerSelectType;
	var handlerSelectType = nodeObj.handlerSelectType == null?"org":nodeObj.handlerSelectType;
	//备选公式化 
	var defaultOptionBean = "lbpmOptHandlerTreeService&optHandlerIds=" + encodeURIComponent(optHandlerIds) 
		+ "&currentIds=" + ((handlerSelectType=="formula" || handlerSelectType=="rule") ? "" : encodeURIComponent(nodeHandlerIds)) 
		+ "&handlerIdentity=" + handlerIdentity
		+ "&optHandlerSelectType=" + optHandlerSelectType
		+ "&fdModelName=" + lbpm.modelName
		+ "&fdModelId=" + lbpm.modelId
		+ "&nodeId=" + nodeObj.id;
		//增加搜索条 add by limh 2010年11月4日
	var searchBean = defaultOptionBean + "&keyword=!{keyword}";
	var modelName = lbpm.modelName;
	var hrefObj = "<a href=\"javascript:void(0);\" index=\"" + (hasIndex?hrefIndex:0) + "\"";
	if(nodeObj.useOptHandlerOnly == "true"){
		var optHrefObj = hrefObj;
		if(handlerSelectType=="formula" || handlerSelectType=="rule" || handlerSelectType=="matrix"){
			//公式定义器，矩阵组织和规则引擎的处理人数据是公式，不可以直接作为idField和nameField处理，
			// 需要解析成具体的人员id和name做对象传入，如：lbpm.nodeParseHandlerIdNode 和 lbpm.nodeParseHandlerNameNode
			lbpm.globals.setNodeParseHandler(nodeObj);
			if(nodeObj.nodeDescType=="shareReviewNodeDesc"){
				// 微审批节点处理人选择控制
				hrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(false, lbpm.nodeParseHandlerIdNode || null,lbpm.nodeParseHandlerNameNode || null, ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
			} else {
				hrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(true, lbpm.nodeParseHandlerIdNode || null,lbpm.nodeParseHandlerNameNode || null, ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
			}
		}
		else{
			if(nodeObj.nodeDescType=="shareReviewNodeDesc"){
				// 微审批节点处理人选择控制
				hrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(false, '"+handlerIdObj+"','"+handlerNameObj+"', ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
			}
			else{
				hrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(true, '"+handlerIdObj+"','"+handlerNameObj+"', ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
			}
			
		}
		//如果选择了本部门和本机构，重构onclick的内容
		var content = resetContent(nodeObj, afterChangeFunc, handlerIdObj, handlerNameObj);
		if(content){
			hrefObj = optHrefObj + content;
		}
		
		hrefObj += " class='com_btn_link'>" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "</a>";
		html += '　　<span id="_addressSpanIndex_'+ (hasIndex?hrefIndex:0)+'" style=\''+(defaultHide?"display:none":"")+';\'>' + hrefObj + '</span>';
	}
	else{
		var hrefObj_formula = hrefObj;
		var optHrefObj = hrefObj;
		var optHrefObjTemp = optHrefObj;
		if(handlerSelectType=="formula" || handlerSelectType=="rule" || handlerSelectType=="matrix"){
			//公式定义器，矩阵组织和规则引擎的处理人数据是公式，不可以直接作为idField和nameField处理，
			// 需要解析成具体的人员id和name做对象传入，如：lbpm.nodeParsehandlerIdNode 和 lbpm.nodeParseHandlerNameNode
			lbpm.globals.setNodeParseHandler(nodeObj);
			if(nodeObj.nodeDescType=="shareReviewNodeDesc"){
				// 微审批节点处理人选择控制
				var selectType = 'ORG_TYPE_PERSON';
				hrefObj += " onclick=\"{Com_EventStopPropagation();"+dialogAddsFunc+"(false, lbpm.nodeParseHandlerIdNode || null,lbpm.nodeParseHandlerNameNode || null, ';', "+selectType+", function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, null, null, null, null, null, null, '" + nodeObj.id + "','"+defaultOptionBean+"');}\"";
				optHrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(false, null,null, ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
				hrefObj_formula += " onclick=\"{Com_EventStopPropagation();"+formulaDialogFunc+"('"+handlerIdObj+"','"+handlerNameObj+"', '"+modelName+"', '"+nodeObj.id+"',true)}\"";
			} else{
				var selectType = (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj)) ? 'ORG_TYPE_ALL | ORG_TYPE_ROLE' : 'ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE';
				hrefObj += " onclick=\"{Com_EventStopPropagation();"+dialogAddsFunc+"(true, lbpm.nodeParseHandlerIdNode || null,lbpm.nodeParseHandlerNameNode || null, ';', "+selectType+", function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, null, null, null, null, null, null, '" + nodeObj.id + "','"+defaultOptionBean+"');}\"";
				optHrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(true, null,null, ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
				hrefObj_formula += " onclick=\"{Com_EventStopPropagation();"+formulaDialogFunc+"('"+handlerIdObj+"','"+handlerNameObj+"', '"+modelName+"', '"+nodeObj.id+"')}\"";
			}
		}
		else{
			if(nodeObj.nodeDescType=="shareReviewNodeDesc"){
				// 微审批节点处理人选择控制
				var selectType = 'ORG_TYPE_PERSON';
				hrefObj += " onclick=\"{Com_EventStopPropagation();"+dialogAddsFunc+"(false, '"+handlerIdObj+"','"+handlerNameObj+"', ';', "+selectType+", function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, null, null, null, null, null, null, '" + nodeObj.id + "','"+defaultOptionBean+"');}\"";
				optHrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(false, '"+handlerIdObj+"','"+handlerNameObj+"', ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
				hrefObj_formula += " onclick=\"{Com_EventStopPropagation();"+formulaDialogFunc+"(null, null, '"+modelName+"', '"+nodeObj.id+"',true)}\"";
			} else {
				var selectType = (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj)) ? 'ORG_TYPE_ALL | ORG_TYPE_ROLE' : 'ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE';
				hrefObj += " onclick=\"{Com_EventStopPropagation();"+dialogAddsFunc+"(true, '"+handlerIdObj+"','"+handlerNameObj+"', ';', "+selectType+", function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, null, null, null, null, null, null, '" + nodeObj.id + "','"+defaultOptionBean+"');}\"";
				optHrefObj += " onclick=\"{Com_EventStopPropagation();Dialog_AddressList(true, '"+handlerIdObj+"','"+handlerNameObj+"', ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
				hrefObj_formula += " onclick=\"{Com_EventStopPropagation();"+formulaDialogFunc+"(null, null, '"+modelName+"', '"+nodeObj.id+"')}\"";
			}
		}

		//如果选择了本部门和本机构，重构optHrefObj
		var content =  resetContent(nodeObj, afterChangeFunc, handlerIdObj, handlerNameObj);
		if(content){
			optHrefObj = optHrefObjTemp + content;
		}
		
		hrefObj += " title='" + lbpm.workitem.constant.COMMONSELECTADDRESS + "'>" + lbpm.workitem.constant.COMMONSELECTADDRESS + "</a>";
		hrefObj_formula +=  " title='" + lbpm.workitem.constant.COMMONSELECTFORMLIST + "'>" + lbpm.workitem.constant.COMMONSELECTFORMLIST + "</a>";
		optHrefObj +=  " title='" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "'>" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "</a>";
		var handlerChooses = [hrefObj];
		var isShowOptHrefObj = false;
		//如果选择的是组织架构或者公式定义器，需要判断是否设置了备选列表；
		//如果选择的是本部门或者本机构，默认显示
		if ((nodeObj.optHandlerIds && $.trim(nodeObj.optHandlerIds) != "") || optHandlerSelectType == "dept" || optHandlerSelectType == "mechanism"){
			handlerChooses.push(optHrefObj);
			isShowOptHrefObj = true;
		}
		if (window.LUI) {
			//html = lbpm.globals.getModifyHandlerHTML_LUI(defaultHide, lbpm.workitem.constant.COMMONSELECTADDRESS, [hrefObj, hrefObj_formula, optHrefObj], hrefIndex);
			//#49606 可修改节点处理人和比修改节点处理人，审批节点的界面修改入口隐藏“使用公式定义器” by liwc
			html = lbpm.globals.getModifyHandlerHTML_LUI(defaultHide, lbpm.workitem.constant.COMMONSELECTADDRESS, handlerChooses, hrefIndex);
		} else {
//			html += '　　<span id="_addressSpanIndex_'+ (hasIndex?hrefIndex:0)+'" style=\''+(defaultHide?"display:none":"")+';\'>' + hrefObj + '&nbsp;&nbsp;' + optHrefObj + '&nbsp;&nbsp;' + hrefObj_formula + '</span>';
			html += '　　<span id="_addressSpanIndex_'+ (hasIndex?hrefIndex:0)+'" style=\''+(defaultHide?"display:none":"")+';\'>' + hrefObj;
			if (isShowOptHrefObj){
				html +=  '&nbsp;&nbsp;' + optHrefObj;
			}
			html += '</span>';
		}
	}
	return html;
};

//重构备选列表标签的onclick事件内容
function resetContent(nodeObj, afterChangeFunc, handlerIdObj, handlerNameObj){
	//如果选择了本部门和本机构，重构hrefObj
	var optHandlerSelectType = nodeObj.optHandlerSelectType == null?"org":nodeObj.optHandlerSelectType;
	if(optHandlerSelectType == 'dept' || optHandlerSelectType == 'mechanism' || optHandlerSelectType == "otherOrgDept"){
		//如果选择的是本部门或者本机构，弹窗地址本
		var deptLimit;
		if(optHandlerSelectType == 'dept'){
			deptLimit = 'myDept';
		}else if(optHandlerSelectType == "otherOrgDept"){
			deptLimit = 'otherOrgDept-' + nodeObj.optHandlerIds;
		}else{
			deptLimit = 'myOrg';
		}
		var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		var reg = /;/g;
		var ids = currentOrgIdsObj ? currentOrgIdsObj.value.split(reg) : [];
		
		var exceptValue="";
		for(var i=0; i<ids.length; i++){
			if(i == ids.length-1){
				exceptValue += "'" + ids[i] + "'";
			}else{
				exceptValue += "'" + ids[i] + "',";
			}
		}
		var selectType = ORG_TYPE_POSTORPERSON;
		if(nodeObj.nodeDescType=="shareReviewNodeDesc"){
			// 微审批节点处理人选择控制
			return " onclick=\"{Com_EventStopPropagation();Dialog_Address(false,'"+handlerIdObj+"','"+handlerNameObj+"', ';', "+selectType+",function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, null, null, true, null, null, ["+exceptValue+"], '"+deptLimit+"');}\"";
		} else {
			return " onclick=\"{Com_EventStopPropagation();Dialog_Address(true,'"+handlerIdObj+"','"+handlerNameObj+"', ';', "+selectType+",function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"')}, null, null, true, null, null, ["+exceptValue+"], '"+deptLimit+"');}\"";
		}
	}
	return null;
}

//新ued备选列表
lbpm.globals.getModifyHandlerHTML_LUI=function(defaultHide, displayText, actions, hrefIndex){
/*  <div id="divselect">
    <cite>请选择特效分类</cite>
    <ul>
       <li><a href="javascript:;" selectid="1">导航菜单</a></li>
       <li><a href="javascript:;" selectid="2">焦点幻灯片</a></li>
    </ul>
	</div> */
	var html = "<span id='choseFromSysOrgForHandler' style='"+(defaultHide?"display:none":"")+"' index='"+hrefIndex+"' class='divselect'><span class='cite' title='" + displayText + "'><a>"+displayText+"</a><ul>";
	for (var i = 0; i < actions.length; i ++) {
		var act = actions[i];
		html += "<li>" + act + "</li>";
	}
	html += "</ul></span></span>";
	return html;
};
lbpm.onLoadEvents.delay.push(function() {
	if(Com_Parameter.dingXForm === "true" && typeof lbpm.globals.modifyflowFlag != "undefined"){
		return;
	}
	$('#operationsTDContent, #nextNodeTD').delegate('.divselect .cite', 'click', function(event) {
		var ul = $(this).closest('.divselect').find('ul');
		if(ul.css("display") == "none" && event.target.tagName != 'A'){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
		event.stopPropagation();
	});
	$('#operationsTDContent, #nextNodeTD').delegate('.divselect .cite a', 'click', function(event) {
		var label = $(this);
		var selectedText = label.text();
		label.closest('.divselect').find('ul li a').each(function() {
			var self = $(this);
			if (self.text() == selectedText) {
				self.click();
				return false;
			}
		});
		event.stopPropagation();
	});
	$('#operationsTDContent, #nextNodeTD').delegate(".divselect ul li a", 'click', function(event){
		var self = $(this);
		var txt = self.text();
		self.closest('.divselect').find('.cite a').html(txt);
		self.closest('.divselect').find('ul').hide();
		event.stopPropagation();
	});
	$('#operationsTDContent, #nextNodeTD').click(function(event) {
		var divselect = $(this).find('.divselect'), rtn = false;
		if (divselect.length > 0) {
			divselect.each(function() {
				if ($.contains(this, event.target)) {
					rtn = true;
					return false;
				}
			});
			if (rtn)
				return;
		}
		$(".divselect ul").hide();
	});
	$(document).click(function() {
		$(".divselect ul").hide();
	});
	lbpm.globals.modifyflowFlag = 1;
});
//修改流程信息
lbpm.globals.modifyFreeFlow=function(contentField, statusField){
	if(!lbpm.globals.isCanEdit()){
		return;
	}
	lbpm.globals.saveOrUpdateFreeflowVersion();
	var fieldList = lbpm.globals.getFormFieldList();

	if(typeof(_thirdSysFormList) == "object" ){//第三方系统集成表单参数
		fieldList = fieldList.concat(_thirdSysFormList);
	}
	
	var param = {
		processData: lbpm.globals.getProcessXmlString(),
		statusData: document.getElementsByName(statusField)[0].value,
		Window:window,
		FormFieldList:fieldList,
		MyAddedNodes:lbpm.myAddedNodes,
		AfterShow:function(rtnVal){
			if(rtnVal!=null){
				if(!lbpm.globals.isCanEdit()){
					return;
				}
				lbpm.globals.saveOrUpdateFreeflowVersion();
				var param={};
			    param.xml=rtnVal;
			    if(this.Window.lbpm && this.Window.lbpm.events)
			    	this.Window.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
			    else
			    	this.Window.parent.lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYPROCESS,param);
			}
		}
	};
	//TODO 这个字段暂时默认都为0
	var fdIsAllowSetupApprovalType="0";
	var fdTemplateModelName = document.getElementById("sysWfBusinessForm.fdTemplateModelName").value;
	var fdTemplateKey = document.getElementById("sysWfBusinessForm.fdTemplateKey").value;
	var modelName = lbpm.globals.getWfBusinessFormModelName();
	var flowPopedom = lbpm.nowNodeFlowPopedom;
	if(lbpm.constant.ROLETYPE==lbpm.constant.AUTHORITYROLETYPE){
		flowPopedom = "2";
	}
	var url='/sys/lbpm/flowchart/page/freeflowPanel.jsp?edit=true&extend=oa&template=false&flowType=1&flowPopedom='+ flowPopedom +'&modelName='+modelName+'&deployApproval=' + fdIsAllowSetupApprovalType + '&templateModel=' + fdTemplateModelName + '&templateKey=' + fdTemplateKey + "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&popup=true";
	//#154468-IE浏览器，修改其它节点处理人弹出框没有内容
	top.Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,lbpm.constant.FREEFLOW,function(rtn){
			if(rtn!=null && param.AfterShow){
				param.AfterShow(rtn);
			}
		},{width:document.documentElement.clientWidth || document.body.clientWidth,height:document.documentElement.clientHeight || document.body.clientHeight,params:param});
	});
}