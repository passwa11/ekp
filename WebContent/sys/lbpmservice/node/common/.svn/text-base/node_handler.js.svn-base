/**********************************************************
功能：流程图的扩展功能
使用：
	通过流程图html中的extend参数声明该文件名，由panel.js引入
必须实现的方法：
	FlowChartObject.Nodes.InitializeTypes（节点类型定义）
作者：叶中奇
创建时间：2008-05-05
修改记录：
**********************************************************/

FlowChartObject.Lang.Include("sys-lbpm-engine");

if(!FlowChartObject.OAProcess)
	FlowChartObject.OAProcess = new Object();

//审批、签字节点的更新提示信息
function FlowChart_OAProcess_ReviewNodeRefreshInfo(){
	var topframe = parent.parent;
	if (topframe.lbpm && topframe.lbpm.globals
			&& topframe.lbpm.globals.buildNextNodeHandlerNodeRefreshInfo) {
		this.Info = topframe.lbpm.globals.buildNextNodeHandlerNodeRefreshInfo(this.Data.id + '.' + (this.Data.name).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"), this.Data.processType, this.Data.handlerIds, this.Data.handlerSelectType);
		return;
	}
	//支持从主流程文档中打开子流程图后，在子流程图中鼠标悬停显示节点信息时计算节点处理人，rdm单号“#46295”  2017-11-22 王祥
	if(topframe.LbpmNextHandler){
		this.Info = topframe.LbpmNextHandler.buildNextNodeHandlerNodeRefreshInfo(this.Data.id + '.' + (this.Data.name).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"), this.Data.processType, this.Data.handlerIds, this.Data.handlerSelectType);
		return;
	}
	var info = "·" + FlowChartObject.Lang.Node.name + ": " + (this.Data.name).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
	info += "\r\n·" + FlowChartObject.Lang.Node.id + ": " + this.Data.id;
	if(this.Status==STATUS_UNINIT){
		info += "\r\n·" + FlowChartObject.Lang.Node.AttributeNotSetting;
	}else{
		//处理人
		info += "\r\n·" + FlowChartObject.Lang.Node.handlerNames + ": ";
		info += (this.Data.handlerSelectType=="formula"?FlowChartObject.Lang.Node.HandlerIsFormula:
			(this.Data.handlerSelectType=="matrix"?FlowChartObject.Lang.Node.HandlerIsMatrix:
			(this.Data.handlerSelectType=="rule"?FlowChartObject.Lang.Node.HandlerIsRule:
			(this.Data.handlerNames==null || this.Data.handlerNames==""?FlowChartObject.Lang.Node.HandlerIsEmpty:this.Data.handlerNames))));
		//审批方式
		var processType = "";
		switch(this.Data.processType){
			case "0":
			case "1":
				processType = FlowChartObject.Lang.Node["processType_"+this.Data.processType];
			break;
			case "2":
				processType = FlowChartObject.Lang.Node["processType_"+this.Data.processType + "" + this.holdType];
				//processType = this.Type=="reviewNode"?FlowChartObject.Lang.Node.processType_20:FlowChartObject.Lang.Node.processType_21;
			break;
		}
		info += "\r\n·" + FlowChartObject.Lang.Node.processType + ": " + processType;
		//勾选项
		info += "\r\n·" + FlowChartObject.Lang.Node.Options + ": ";
		if(this.Data.ignoreOnHandlerEmpty=="true")
			info += FlowChartObject.Lang.Node.ignoreOnHandlerEmpty + ", ";
		if(this.Data.ignoreOnHandlerSame=="true"){
			if(this.Data.onAdjoinHandlerSame=="true"){
				info += FlowChartObject.Lang.Node.onAdjoinHandlerSame + ", ";
			}else{
				info += FlowChartObject.Lang.Node.onSkipHandlerSame + ", ";
			}
		}else if(this.Data.ignoreOnFutureHandlerSame=="true"){
			info += FlowChartObject.Lang.Node.ignoreOnFutureHandlerSame + ", ";//后续处理人身份重复跳过当前
		}else if(this.Data.ignoreOnHandlerSame==null && this.Data.onAdjoinHandlerSame == null){
			info += FlowChartObject.Lang.Node.onAdjoinHandlerSame + ", ";
		}else{
			info += FlowChartObject.Lang.Node.ignoreOnHandlerSame + ", ";
		}
		if(this.Data.canModifyMainDoc=="true")
			info += FlowChartObject.Lang.Node.canModifyMainDoc + ", ";
		if(this.Data.canAddAuditNoteAtt=="true")
			info += FlowChartObject.Lang.Node.canAddAuditNoteAtt + ", ";
		if(info.substring(info.length-2)==", "){
			info = info.substring(0, info.length-2);
		}else{
			info += FlowChartObject.Lang.None;
		}
		//节点帮助 增加对界面帮助描述超链接的转换功能 @作者：曹映辉 @日期：2011年9月1日 
		info += "\r\n·" + FlowChartObject.Lang.Node.description + ": " + (this.Data.description==null?"":this.Data.description.replace(/<span><a[^>]*href=[\'\"\s]*([^\s\'\"]*)[^>]*>(.+?)<\/a><\/span>/ig,"$2").replace(/(<pre>)|(<\/pre>)/ig,"").replace(/<br\/>/ig,"\r\n"));
	}
	this.Info = Com_FormatText(info);
}

FlowChartObject.OAProcess.ReviewNodeRefreshInfo = FlowChart_OAProcess_ReviewNodeRefreshInfo;

function FlowChart_OAProcess_ReviewNodeCheck(){
	if(!FlowChartObject.CheckFlowNode(this)){
		return false;
	}
	//设置快速审批校验
	if(this.Data.canFastReview=='true'){
		//存在修改其他节点处理人的审批节点，不允许快速通过
		if(this.Data.mustModifyHandlerNodeIds){
			alert(FlowChartObject.Lang.GetMessage(FlowChartObject.Lang.checkFastReviewMustModify,this.Data.id + "." + this.Data.name));
			return false;
		}
		//审批节点的下一个节点是人工分支，不允许快速通过
		if(this.LineOut[0] && this.LineOut[0].EndNode.Type=="manualBranchNode"){
			alert(FlowChartObject.Lang.GetMessage(FlowChartObject.Lang.checkFastReviewHasManualBranch,this.Data.id + "." + this.Data.name));
			return false;
		}
		//审批节点的包含审批要点，并且必须是重要的，不允许校验
		if(this.Data.extAttributes){
			for(var i=0;i<this.Data.extAttributes.length;i++){
				if(this.Data.extAttributes[i].name=="lbpmExtAuditPointCfg" && this.Data.extAttributes[i].value){
					var jsonAuditPoint=JSON.parse(this.Data.extAttributes[i].value);
					for(var point in jsonAuditPoint){
						if(jsonAuditPoint[point].fdIsImportant=='true'){
							alert(FlowChartObject.Lang.GetMessage(FlowChartObject.Lang.checkFastReviewAuditNote,this.Data.id + "." + this.Data.name));
							return false;
						}
					}
				}
			}
		}
	}
	if(this.LineOut[0] && this.LineOut[0].EndNode.Type=="manualBranchNode"){
		if(this.LineOut[0].EndNode.Data.decidedBranchOnDraft && this.LineOut[0].EndNode.Data.decidedBranchOnDraft == "true") {
			// 在起草节点决定节点走向
			return true;
		}
		//下个节点是人工决策节点
		// if(this.Data.processType=="2"){
		// 	alert(FlowChartObject.Lang.GetMessage(
		// 		FlowChartObject.Lang.checkFlowCanNotToManualBranch,
		// 		FlowChartObject.Lang.Node["processType_"+this.Data.processType + "" + this.holdType],
		// 		//this.Type=="reviewNode"?FlowChartObject.Lang.Node.processType_20:FlowChartObject.Lang.Node.processType_21,
		// 		this.Data.id + "." + this.Data.name));
		// 	return false;
		// }
		if(this.Data.ignoreOnHandlerEmpty=="true"){
			alert(FlowChartObject.Lang.GetMessage(FlowChartObject.Lang.checkFlowIgnoreOnHandlerEmpty,this.Data.id + "." + this.Data.name));
			return false;
		}
	}
	//启动并行分支节点设置了自定义启动后，不能接入会审节点
	if(this.LineOut[0] && (this.LineOut[0].EndNode.Type=="splitNode" || this.LineOut[0].EndNode.Type=="dynamicSubFlowNode")){
		var endNode=this.LineOut[0].EndNode;
		if(endNode.Data.splitType&&endNode.Data.splitType=="custom"){
			//判断是否是会审节点
			if(this.Data.processType=="2"){
				alert(FlowChartObject.Lang.GetMessage(
						FlowChartObject.Lang.checkFlowCanNotToSplitNode,
						FlowChartObject.Lang.Node["processType_"+this.Data.processType + "" + this.holdType],
						this.Data.id + "." + this.Data.name));
					return false;
			}
			//自定义启动分支不允许身份重复跳过
			if(this.Data.ignoreOnHandlerSame&&this.Data.ignoreOnHandlerSame!="false"){
				alert(FlowChartObject.Lang.GetMessage(
						FlowChartObject.Lang.checkFlowCanNotToSplitNode_3,
						endNode.Data.id + "." + endNode.Data.name,
						this.Data.id + "." + this.Data.name));
				return false;
			}
		}
	}
	return true;
}

FlowChartObject.OAProcess.ReviewNodeCheck = FlowChart_OAProcess_ReviewNodeCheck;

//流程检测
FlowChartObject.CheckFlow_Extend = function(startNodes, endNodes){
	for(var i=0; i<startNodes.length; i++){
		//检查处理人情况
		if(!FlowChart_OAProcess_TraceCheck(startNodes[i])){
			return false;
		}
	}
	return true;
};

//检查处理人未设置的情况
function FlowChart_OAProcess_TraceCheck(node){
	var traceCheck = function(node, idChain, parentMap, parentId){
		if(node.Type=="joinNode"){
			var startNodeId = node.RelatedNodes[0].Data.id;
			if(parentId.substring(0, startNodeId.length+1)!=startNodeId+"."){
				idChain.push(node.Data.id);
				FlowChart_OAProcess_Check_SelectTrace(idChain);
				alert(FlowChartObject.Lang.checkSplitNodeError1);
				return false;
			}
			parentId = parentMap[startNodeId];
		}else{
			if(parentMap[node.Data.id]==null){
				parentMap[node.Data.id] = parentId;
			}else{
				if(parentMap[node.Data.id]!=parentId){
					idChain.push(node.Data.id);
					var idChainTmp = [];
					if(parentId!=""){
						parentId = parentId.split(".")[0];
						var startPush = false;
						for (var i = 0; i < idChain.length; i++) {
							if(parentId == idChain[i]){
								startPush = true;
							}
							if(startPush) {
								idChainTmp.push(idChain[i]);
							}
						}
					}
					if(idChainTmp.length == 0) {
						idChainTmp = idChain;
					}
					FlowChart_OAProcess_Check_SelectTrace(idChainTmp);
					alert(FlowChartObject.Lang.checkSplitNodeError2);
					return false;
				}
			}
		}
		//该ID在id链中出现，说明产生了循环
		for (var i = idChain.length - 1; i >= 0; i--) {
			if(node.Data.id == idChain[i]){
				return true;
			}
		}
		idChain.push(node.Data.id);
		switch(node.Type){
			case "reviewNode":
			case "robtodoNode":
			case "signNode":
			case "sendNode":
			case "shareReviewNode":
				if((node.Data.handlerIds==null || node.Data.handlerIds=="") && (node.Data.ignoreOnHandlerEmpty=="false" || Com_IsFreeFlow())){
					emptyHandlerNodes.push(node);
				}
			case "draftNode":
				if(node.Type!="sendNode"){
					node.Data.canModifyHandlerNodeIds = FlowChart_OAProcess_RemoveInvalidIds(node.Data.canModifyHandlerNodeIds);
					node.Data.mustModifyHandlerNodeIds = FlowChart_OAProcess_RemoveInvalidIds(node.Data.mustModifyHandlerNodeIds);
					modifyHandlerIds += node.Data.mustModifyHandlerNodeIds + ";";
				}
			break;
			case "splitNode":
				parentId = node.Data.id;
			break;
		}
		for(var i=0; i<node.LineOut.length; i++){
			var nxtParentId = node.Type=="splitNode"?(parentId+"."+i):parentId;
			if(!traceCheck(node.LineOut[i].EndNode, idChain, parentMap, nxtParentId))
				return false;
		}
		return true;
	};
	
	var modifyHandlerIds = "", emptyHandlerNodes = [];
	if (!traceCheck(node, [], {}, "")) {
		return false;
	}
	for (var i = 0; i < emptyHandlerNodes.length; i++) {
		var node = emptyHandlerNodes[i];
		if(modifyHandlerIds.indexOf(node.Data.id+";")==-1){
			var message = FlowChartObject.Lang.checkFlowHandlerEmpty;
			if(Com_IsFreeFlow()){
				message = FlowChartObject.Lang.checkFreeFlowHandlerEmpty;
			}
			alert(FlowChartObject.Lang.GetMessage(message, node.Data.id + "." + node.Data.name));
			FlowChartObject.SelectElement(node);
			return false;
		}
	}
	return true;
}

function FlowChart_OAProcess_Check_SelectTrace(ids){
	if(ids && ids.length > 0) {
		var node = FlowChartObject.Nodes.GetNodeById(ids[0]);
		FlowChartObject.SelectElement(node);
		for(var i=1; i<ids.length; i++){
			var nxtNode = FlowChartObject.Nodes.GetNodeById(ids[i]);
			for(var j=0; j<node.LineOut.length; j++){
				if(node.LineOut[j].EndNode==nxtNode){
					FlowChartObject.SelectElement(node.LineOut[j]);
					break;
				}
			}
			FlowChartObject.SelectElement(nxtNode);
			node = nxtNode;
		}
	}
}