//生成即将流向节点的HTML信息
lbpm.globals.generateNextNodeInfo=function(fromCalcBranch){
	//如果发现即将处理人在缓存中有时，直接获取缓存中的记录（不用重新计算）
	if(lbpm.nowProcessorInfoObj["lbpm_nextHandlerName"]!=null && lbpm.nowProcessorInfoObj["lbpm_nextHandlerNameId"]!=null  && lbpm.nowProcessorInfoObj["lbpm_nextNodeId"]!=null){
		var node = lbpm.nodes[lbpm.nowProcessorInfoObj["lbpm_nextNodeId"]];
		
		var langNodeName = WorkFlow_getLangLabel(node.name,node["langs"],"nodeName");
		langNodeName=langNodeName.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		var nextShowHandlerName=lbpm.nowProcessorInfoObj["lbpm_nextHandlerName"];
		var identifier =  _lbpmIsRemoveNodeIdentifier() ? "" : (node.id + ".");
		html = '<label id="nextNodeName"><b>' + identifier + langNodeName + '</b></label>';
		html += '<input type="hidden" id="handlerIds" name="handlerIds" value="' +lbpm.nowProcessorInfoObj["lbpm_nextHandlerNameId"]+ '">';
		html += '<input type="hidden" id="handlerNames" name="handlerNames" readonly class="inputSgl" onChange="lbpm.globals.setHandlerInfoes();" value="' + Com_HtmlEscape(nextShowHandlerName) + '">';
		html += '<label id="handlerShowNames" class=handlerNamesLabel nodeId="' + node.id + '">(' + Com_HtmlEscape(nextShowHandlerName.replace(/;/g, '; ')) + ')</label>';
		
		var rightHtml="";
		if(lbpm.approveType == "right"){
			rightHtml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;";//右侧模式带缩进
		}
		
		var notifyTypeHtml=lbpm.globals.getNotifyType4Node(node);
		
		$(notifyTypeHtml).find('.lui-lbpm-checkbox');
        var notifyTypeLength=0;//有多少子项，例如待办，邮件，短信
        if($(notifyTypeHtml)){
            for(var z=0;z<$(notifyTypeHtml).length;z++){
                var $notifyTypeHtml = $($(notifyTypeHtml)[z]);

                var lbpmCheckClass=$($notifyTypeHtml).attr('class');
                if(lbpmCheckClass=="lui-lbpm-checkbox"){
                    notifyTypeLength++;
                }
            }
        }
        
        //通知项大于显示
        if(notifyTypeLength>1){
            html +="<span class='labelNotifyType' >"+ rightHtml+notifyTypeHtml+"</span>";
        }else{
            html +="<span class='labelNotifyType' style='display: none' >"+ rightHtml+notifyTypeHtml+"</span>";
        }

		return html;
	};
	//判断即将流向的处理人是否还是同一节点：
	var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
	/*
	 * currNodeNextHandlersId:当前节点处理人的下一处理人(串行)
	 * currNodeNextHandlersName:当前节点处理人的下一处理人名称(串行)
	 * toRefuseThisNodeId:驳回时如果选择重新回到本节点时，驳回时节点的的ID（如N1,N2）
	 * toRefuseThisHandlerIds:驳回时如果选择重新回到本节点时，驳回时未处理人的ID集
	 * toRefuseThisHandlerNames:驳回时如果选择重新回到本节点时，驳回时未处理人名称集
	 */
	var operatorInfo=lbpm.globals.getOperationParameterJson(
			"currNodeNextHandlersId"
			+":currNodeNextHandlersName"
			+":toRefuseThisNodeId"
			+":toRefuseThisHandlerIds"
			+":toRefuseThisHandlerNames"
			+":futureNodeId");
	
	var html = '';
	// 是则显示同一节点下一个处理人并不允许编辑。
	// 不是则显示下一个节点的所有处理人并根据权限显示是否编辑
	if(operatorInfo.currNodeNextHandlersId && currentNodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL){
		var langNodeName = WorkFlow_getLangLabel(currentNodeObj.name,currentNodeObj["langs"],"nodeName");
		langNodeName=langNodeName.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (currentNodeObj.id + ".");
		html = '<label id="nextNodeName"><b>' + identifier + langNodeName + '</b></label>';
		html += '<input type="hidden" id="handlerIds" name="handlerIds" value="' +operatorInfo.currNodeNextHandlersId+ '">';
		html += '<input type="hidden" id="handlerNames" name="handlerNames" readonly class="inputSgl" onChange="lbpm.globals.setHandlerInfoes();" value="' + Com_HtmlEscape(operatorInfo.currNodeNextHandlersName) + '">';
		html += '<label id="handlerShowNames" class=handlerNamesLabel nodeId="' + currentNodeObj.id + '">(' + Com_HtmlEscape(operatorInfo.currNodeNextHandlersName.replace(/;/g, '; ')) + ')</label>';
		
		var rightHtml="";
		if(lbpm.approveType == "right"){
			rightHtml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;";//右侧模式带缩进
		}
		
		
		var notifyTypeHtml=lbpm.globals.getNotifyType4Node(currentNodeObj);
        $(notifyTypeHtml).find('.lui-lbpm-checkbox');
        var notifyTypeLength=0;//有多少子项，例如待办，邮件，短信
        if($(notifyTypeHtml)){
            for(var z=0;z<$(notifyTypeHtml).length;z++){
                var $notifyTypeHtml = $($(notifyTypeHtml)[z]);

                var lbpmCheckClass=$($notifyTypeHtml).attr('class');
                if(lbpmCheckClass=="lui-lbpm-checkbox"){
                    notifyTypeLength++;
                }
            }
        }

        //通知项大于显示
        if(notifyTypeLength>1){
            html +="<span class='labelNotifyType'>"+ rightHtml+notifyTypeHtml+"</span>";
        }else{
            html +="<span class='labelNotifyType' style='display: none' >"+ rightHtml+notifyTypeHtml+"</span>";
        }
		
		
	}else if(operatorInfo.toRefuseThisNodeId && lbpm.globals.getNodeObj(operatorInfo.toRefuseThisNodeId)){
		html=lbpm.globals.generateRefuseThisNodeIdInfo(
				operatorInfo.toRefuseThisNodeId,
				operatorInfo.toRefuseThisHandlerIds,
				operatorInfo.toRefuseThisHandlerNames,
				operatorInfo.currNodeNextHandlersId,
				operatorInfo.currNodeNextHandlersName);
	}else{
		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
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
			},null,null,false,lbpm.nowNodeId);
			routeLines = filterRouteLine;
			if(nextNodeObj.nodeDescType=="autoBranchNodeDesc" && filterRouteLine.length != 1){
				noCalcResult = true;
				routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(lbpm.globals.getCurrentNodeObj(),nextNodeObj, false);
			}
		}
		// 如果连线的结束节点为组结束节点则显示对应组节点的下一节点
	    if (nextNodeObj.nodeDescType == "groupEndNodeDesc"&&nextNodeObj.groupNodeType!="freeSubFlowNode") {
	      routeLines = lbpm.nodes[nextNodeObj.groupNodeId].endLines
	    }
	    
		html = lbpm.globals.getNextRouteInfo(routeLines,noCalcResult,fromCalcBranch);
		// 针对即将流向的节点以及当前节点是即席子流程内的子节点时
		if ((lbpm.nodes[lbpm.nowNodeId].groupNodeId != null && lbpm.nodes[lbpm.nowNodeId].groupNodeType == "adHocSubFlowNode") && nextNodeObj.nodeDescType=="groupEndNodeDesc") {
			lbpm.nowAdHocSubFlowNodeId = lbpm.nodes[lbpm.nowNodeId].groupNodeId;
			lbpm.adHocRouteId = lbpm.nodes[lbpm.nowNodeId].routeId;
			html = lbpm.globals.getNextAdHocSubFlowRouteInfo();
		} else if (nextNodeObj.nodeDescType == "adHocSubFlowNodeDesc") {
			lbpm.nowAdHocSubFlowNodeId = nextNodeObj.id;
			lbpm.adHocRouteId = null;
			html = lbpm.globals.getNextAdHocSubFlowRouteInfo();
		}
	}
	
	return html; 
};

function _lbpmIsRemoveNodeIdentifier(){
	var isRemove = false;
	if (Lbpm_SettingInfo){
		if ((lbpm.settingInfo.isHideNodeIdentifier === "false" && Lbpm_SettingInfo.hideNodeIdentifierType === "false")&&
			Lbpm_SettingInfo.isRemoveNodeIdentifier === "true"){
			isRemove = true;
		}else if (Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType  === "isRemoveNodeIdentifier"){
			isRemove = true;
		}
	}
	return isRemove;
}
function _lbpmIsHideAllNodeIdentifier(){
	var isHideAllNodeIdentifier = false;
	if (Lbpm_SettingInfo &&
		Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType  === "isHideAllNodeIdentifier"){
		isHideAllNodeIdentifier = true;
	}
	return isHideAllNodeIdentifier;
}
//计算条件分支流向
lbpm.globals.calcBranch = function(){
	var operationsTDContent = ($("#operationsTDContent")[0]!=null)?$("#operationsTDContent")[0]:$("#nextNodeTD")[0];
	operationsTDContent.innerHTML = lbpm.globals.generateNextNodeInfo(true);
};

function _parseNextNodeHandler(nodeObj,handlerIds){
	var html=""
	//如果是处理人为公式计算则不显示原公式改为显示“公式计算” modify by limh 2010年11月29日
	var dataNextNodeHandler;
	var nextNodeHandlerNames4View="";
	if(nodeObj.handlerSelectType){
		if(nodeObj.handlerSelectType=="formula"){
			dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else if (nodeObj.handlerSelectType=="matrix") {
			dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else if (nodeObj.handlerSelectType=="rule") {
			dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id, handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
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
	html +=  (nextNodeHandlerNames4View.replace(/;/g, '; ')) ;
	return html;
}

//点击通过时，当前节点如果是某个节点驳回过来的时，下个节点的即将流程向应该是那个驳回过来的节点
lbpm.globals.generateRefuseThisNodeIdInfo=function(
			toRefuseThisNodeId,
			toRefuseThisHandlerIds,
			toRefuseThisHandlerNames,
			nextShowHandlerId,
			nextShowHandlerName)
{
	var nodeObj = lbpm.globals.getNodeObj(toRefuseThisNodeId);
	var nextHandlerIds = toRefuseThisHandlerIds || "";
	var nextHandlerNames = toRefuseThisHandlerNames || "";
	if (nextHandlerIds) {
		// 审批路由类型为串行、串行时，返回驳回时第一个未处理人
		if(nextHandlerIds!="_onNode" && nextHandlerIds!="_onTheNode"){
			if(nodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL || nodeObj.processType == lbpm.constant.PROCESSTYPE_SINGLE) {
				nextHandlerIds = nextHandlerIds.split(";")[0];
				nextHandlerNames = nextHandlerNames.split(";")[0];
			}
		}else{
			if(nodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL) {
				nextHandlerIds = nodeObj.handlerIds.split(";")[0];
				nextHandlerNames = _parseNextNodeHandler(nodeObj,nodeObj.handlerIds.split(";")[0]);
			}else{
				nextHandlerIds = nodeObj.handlerIds;
				nextHandlerNames = _parseNextNodeHandler(nodeObj,nodeObj.handlerIds);
			}
		}
	} else if(nextShowHandlerId != null){
		// 不可到达
		nodeObj = lbpm.globals.getCurrentNodeObj();
		nextHandlerIds = nextShowHandlerId;
		nextHandlerNames = nextShowHandlerName;
	}
	
	var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
	langNodeName=langNodeName.replace(/</g, "&lt;").replace(/>/g, "&gt;");
	var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeObj.id + ".");
	html = "<label id='nextNodeName[0]'><b>" + identifier + langNodeName + "</b></label>";
	html += "<input type='hidden' id='handlerIds[0]' name='handlerIds[0]' value='" + nextHandlerIds + "'>";
	html += "<input type='hidden' id='handlerNames[0]' name='handlerNames[0]' readonly class='inputSgl' onChange='lbpm.globals.setHandlerInfoes();' value='" + Com_HtmlEscape(nextHandlerNames) + "'>";
	html += "<label id='handlerShowNames[0]' nodeId='" + nodeObj.id + "'>(" + Com_HtmlEscape(nextHandlerNames.replace(/;/g, '; ')) + ")</label>";
	if(lbpm.approveType == "right"){
		html += "<br/>";
	}
	//html +=lbpm.globals.getNotifyType4Node(nodeObj);
	
	var rightHtml="";
	if(lbpm.approveType == "right"){
		rightHtml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;";//右侧模式带缩进
	}
	
	var notifyTypeHtml=lbpm.globals.getNotifyType4Node(nodeObj);
	
	$(notifyTypeHtml).find('.lui-lbpm-checkbox');
    var notifyTypeLength=0;//有多少子项，例如待办，邮件，短信
    if($(notifyTypeHtml)){
        for(var z=0;z<$(notifyTypeHtml).length;z++){
            var $notifyTypeHtml = $($(notifyTypeHtml)[z]);

            var lbpmCheckClass=$($notifyTypeHtml).attr('class');
            if(lbpmCheckClass=="lui-lbpm-checkbox"){
                notifyTypeLength++;
            }
        }
    }
    
    //通知项大于显示
    if(notifyTypeLength>1){
        html +="<span class='labelNotifyType' >"+ rightHtml+notifyTypeHtml+"</span>";
    }else{
        html +="<span class='labelNotifyType' style='display: none' >"+ rightHtml+notifyTypeHtml+"</span>";
    }
    

	return html;
};
lbpm.globals.setFutureHandlerFormulaDialog=function(idField, nameField, modelName, personOnly) {
	var action = function(rtv){lbpm.globals.afterChangeFurtureHandlerInfoes(rtv,lbpm.constant.ADDRESS_SELECT_FORMULA);};
	lbpm.globals.setHandlerFormulaDialog_(idField, nameField, modelName, action, personOnly);
};
lbpm.globals.setHandlerFormulaDialog=function(idField, nameField, modelName, nodeId) {
	var action = function(rtv){lbpm.globals.afterChangeHandlerInfoes(rtv,lbpm.constant.ADDRESS_SELECT_FORMULA,nodeId);};
	if (lbpm.globals.getNodeObj(nodeId).nodeDescType=="shareReviewNodeDesc") {
		// 微审批节点处理人选择控制
		lbpm.globals.setHandlerFormulaDialog_(idField, nameField, modelName, action, true);
	} else {
		lbpm.globals.setHandlerFormulaDialog_(idField, nameField, modelName, action);
	}
};
//显示或隐藏即将流向节点选项框
lbpm.globals.showFutureNodeSelectedLink=function(futureNodeObj,isClick) {
	var index = futureNodeObj.getAttribute("index");
	var isFinished = false;
	$("#operationsTDContent, #nextNodeTD").find('.divselect').each(function() {
		var self = $(this);
		if (self.attr("index") == index) {
			self.show();
		} else {
			self.hide();
		}
	});
	
    $("#operationsTDContent, #nextNodeTD").find('.labelNotifyType').each(function() {
        var self1 = $(this);
        var lbpmCheckboxLength=self1.find('.lui-lbpm-checkbox').length;
        if (self1.attr("index") == index&&lbpmCheckboxLength>1) {
            self1.show();
        } else {
            self1.hide();
        }
    });
    
    
	var futureNodeLinkObjs = futureNodeObj.parentNode.parentNode.getElementsByTagName("a");
	for(var i = 0; i < futureNodeLinkObjs.length; i++){
		var futureNodeLinkObj = futureNodeLinkObjs[i];
		if(futureNodeLinkObj.getAttribute("index") != null) {
			if(futureNodeLinkObj.getAttribute("index") == futureNodeObj.getAttribute("index")){
				// 是否点击事件
				if(typeof isClick !="undefined" && isClick === "true" && !isFinished){
					//是组织架构选择/备选处理人
					isFinished = true;
					//弹出地址本
					if($(futureNodeObj).attr("isChange") != "true"){
						// 如果是可修改节点就不弹出地址本
						var nodeid = $(futureNodeObj).closest(".lbpmNextRouteInfoRow").find("label[id^=handlerShowNames]").attr("nodeid");
						// 当前节点信息
					    var nowNodeObj = lbpm.globals.getNodeObj(lbpm.nowNodeId);
					    // 必须修改的节点配置
					    var mustModifyHandlerNodeIds = nowNodeObj.mustModifyHandlerNodeIds;
					    // 此选中的节点是必须修改节点里面的才走以下逻辑
						if(mustModifyHandlerNodeIds && mustModifyHandlerNodeIds.indexOf(nodeid)>-1){
							var futureNodeObjVal_=$(futureNodeObj).closest(".lbpmNextRouteInfoRow").find("input[name^=handlerIds]").val();
							//当处理人为空才需要弹出地址本
							if(futureNodeObjVal_==""){
								$(futureNodeLinkObj).click();
							}
						}

						//$(futureNodeLinkObj).click();
					}else{
						$(futureNodeObj).removeAttr("isChange");
					}
				}
				futureNodeLinkObj.parentNode.style.display = '';
			} else {
				futureNodeLinkObj.parentNode.style.display = 'none';
			}
		}
	}

	lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE,null);
};
lbpm.globals.innerHTMLGenerateNextNodeInfo = function(html, dom, cb) {
	if (!window.require) {
		dom.innerHTML = html;
		if (cb) {
			cb();
		}
		return;
	}
	require(['dojo/query', 'dojo/ready', 'dojo/_base/array'], function(query, ready, array) {
		ready(function() {
			lbpm.globals.destroyOperations();
			query(dom).html(html, {parseContent: true, onEnd: function() {
				this.inherited("onEnd", arguments);
				if (this.parseDeferred && cb) {
					this.parseDeferred.then(cb);
				}
			}});
		});
	});
};
//取得手工决策节点下的所有节点的信息(routeLines连接集合)
lbpm.globals.getNextRouteInfo=function(routeLines,noCalcResult,fromCalcBranch,showNextStep){
	
	var html = "<table><tr><td>";
	var onlyOneSelect=false;//只有一个选择框
	if(routeLines.length==1) {
		onlyOneSelect=true;
		lbpm.canHideNextNodeTr = true;
	}
	var futureNodeId = lbpm.globals.getOperationParameterJson("futureNodeId");
	
	var routeLinesLength=routeLines.length;

	var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
	var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);

	var isLastHandler = lbpm.globals.isLastHandler();
	if(routeLines.length>1
		&& nextNodeObj.nodeDescType == "manualBranchNodeDesc"
		&& currentNodeObj.processType == "2"
		&& !isLastHandler){
		lbpm.noValidateFutureNode = true;
		html += "<div style='color:#999999;margin-bottom: 5px;' id='futureNodeTip'>"+lbpm.workitem.constant.FUTURENODESTIP+"</div>";
	}


	$.each(routeLines, function(i, lineObj) {
		var nodeObj=lineObj.endNode;
		//如果连线的结束节点为组结束节点则显示对应组节点的下一节点
		if (nodeObj.nodeDescType=="groupEndNodeDesc") {
			if (nodeObj.groupNodeType=="freeSubFlowNode") {
				html = lbpm.globals.getNextRouteInfo(lbpm.nodes[nodeObj.groupNodeId].endLines,noCalcResult,fromCalcBranch,true);
				return true;
			}
		}
		
		var lineNodeName = WorkFlow_getLangLabel(lineObj.name,lineObj["langs"]);
		var lineName = lineNodeName == null?"":lineNodeName + " ";
		lineName = lineName.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		html += "<div class='lbpmNextRouteInfoRow' ";
		if(i==0){
			html += ">";
		}else{
			/*html += "style ='margin-top:25px;'>";*/
			html += ">";
		}
		var inputType = "";
		//如果连线的开始节点为人工分支类节点则显示单选框
		if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,lineObj.startNode)){
			//如果下个节点是人工决策，并且当前节点的流转方式是会审/会签，则需要进入判断环节
			//若不是最后一个处理人，则即将流向显示，但是不可以进行编辑
			//若是最后一个处理人，则以下逻辑正常执行
			var edit = "";
			if(currentNodeObj.processType == "2" && !isLastHandler){
				edit = "disabled";
			}
			html += "<label class='lui-lbpm-radio' style='line-height:26px;' id='nextNodeName[" + i + "]'><input " + edit + " " + (onlyOneSelect==true?"style='display:none' checked=true ":((nodeObj.id == futureNodeId || lineObj.id == lineObj.startNode.defaultBranch) ? "checked=true " :""))+"type='radio' manualBranchNodeId='"+lineObj.startNode.id+"'name='futureNode' validate='required' subject='"+lbpm.constant.opt.handlerOperationTypepass+"' key='futureNodeId' index='" + i + "' value='" + nodeObj.id
			+ "' onclick='lbpm.globals.showFutureNodeSelectedLink(this,\"true\");_hidOtherAddressOpt(" + i + ");'>" ;
			inputType = "radio";
		}
		//如果连线的开始节点为并行分支则显示复选框
		if(lineObj.startNode.nodeDescType=='splitNodeDesc'){
			if(lineObj.startNode.splitType&&lineObj.startNode.splitType=="custom"){
				//获取默认分支和是否可以选择
				var info = getSplitNodeInfo(lineObj.startNode.id);
				var defaultStartBranchIds,canSelectDefaultBranch,isDefault = false;
				if(info){
					defaultStartBranchIds = info.defaultStartBranchIds;
					if(defaultStartBranchIds && defaultStartBranchIds.indexOf(nodeObj.id) != -1){
						isDefault = true;
					}
					canSelectDefaultBranch = info.canSelectDefaultBranch;
				}
				html += "<label class='lui-lbpm-checkbox' style='line-height:26px;' id='nextNodeName[" + i + "]'><input "+((isDefault && canSelectDefaultBranch == 'false') ? 'disabled':"validate='required' subject='"+lbpm.constant.opt.handlerOperationTypepass+"'")+" "+(onlyOneSelect==true?"style='display:none' checked=true ":((nodeObj.id == futureNodeId || lineObj.id == lineObj.startNode.defaultBranch || isDefault) ? "checked=true " :""))+"type='checkbox' manualBranchNodeId='"+lineObj.startNode.id+"'name='futureNode' key='futureNodeId' index='" + i + "' value='" + nodeObj.id
				+ "' onclick='lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE,null);'>" ;
				inputType = "checkbox";
			}	
		}
		if(!lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,lineObj.startNode)&&!(lineObj.startNode.nodeDescType=='splitNodeDesc'&&lineObj.startNode.splitType&&lineObj.startNode.splitType=="custom")){
			if(typeof lbpm.canHideNextNodeTr == "undefined"){
				lbpm.canHideNextNodeTr = true;
			}
		}
		var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
		var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeObj.id + ".");
		if(inputType == "radio"){
			html += "<b class='radio-label'>";
		}else if(inputType == "checkbox"){
			html += "<b class='checkbox-label'>";
		}else{
			html += "<b>";
		}
		langNodeName=langNodeName.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		html += lineName + identifier + langNodeName + "</b></label>";
		//如果连线的结束节点为嵌入子流程节点则显示入子流程节点的第一个节点
		if (nodeObj.nodeDescType=="embeddedSubFlowNodeDesc" && nodeObj.isInit == "true") {
			nodeObj = lbpm.globals.getNextNodeObj(nodeObj.startNodeId);
			var _langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
			_langNodeName=_langNodeName.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
			var _identifier =   (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeObj.id + ".");
			html += "("+_identifier+_langNodeName+")";
		}else if(nodeObj.nodeDescType=="dynamicSubFlowNodeDesc" && nodeObj.splitType == "custom"){
			lbpm.canHideNextNodeTr = false;
			html += "&nbsp;&nbsp;<input type='hidden' name='dynamicNextNodeIds_"+nodeObj.id+"' data-nodeid='"+nodeObj.id+"'><a class='com_btn_link' onclick='lbpm.globals.getNextDynamicSubFlowRouteInfo(\""+nodeObj.id+"\",this);'>"+lbpm.constant.pleaseSelect+(nodeObj.dynamicGroupShowName?nodeObj.dynamicGroupShowName:nodeObj.name)+"</a>";
		}
		if(!lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,nodeObj)){
			var handlerIds, handlerNames, isFormulaType = (nodeObj.handlerSelectType == lbpm.constant.ADDRESS_SELECT_FORMULA || nodeObj.handlerSelectType == lbpm.constant.ADDRESS_SELECT_RULE);
			handlerIds = nodeObj.handlerIds==null?"":nodeObj.handlerIds;
			handlerNames = nodeObj.handlerNames==null?"":
				(isFormulaType?lbpm.workitem.constant.COMMONHANDLERISFORMULA:nodeObj.handlerNames);
			var hiddenIdObj = "<input type='hidden' name='handlerIds[" + i + "]' value='" + handlerIds + "' isFormula='" + isFormulaType.toString() +"' />";
			html += hiddenIdObj;
			var hiddenNameObj = "<input type='hidden' name='handlerNames[" + i + "]' value='" + Com_HtmlEscape(handlerNames) + "' />";
			
			//如果是处理人为公式计算则不显示原公式改为显示“公式计算” modify by limh 2010年11月29日
			var dataNextNodeHandler;
			var nextNodeHandlerNames4View="";
			if(nodeObj.handlerSelectType){
				if (nodeObj.handlerSelectType=="formula") {
					dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),null,nodeObj.id);
				} else if (nodeObj.handlerSelectType=="matrix") {
					dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),null,nodeObj.id);
				} else if (nodeObj.handlerSelectType=="rule") {
					dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id,handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
				} else {
					dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),null,nodeObj.id);
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
			if(nextNodeHandlerNames4View != "")
				html += "(";
			html += hiddenNameObj;
			html += "<label id='handlerShowNames[" + i + "]' class='handlerNamesLabel'";
			html += " nodeId='" + nodeObj.id + "'>" + (nextNodeHandlerNames4View.replace(/;/g, '; ')) + "</label>";
			if(nextNodeHandlerNames4View != "")
				html += ")";
			if(lbpm.globals.checkModifyNextNodeAuthorization(nodeObj.id) && !lbpm.address.is_pda()){
				lbpm.canHideNextNodeTr = false;
				if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SPLIT,lineObj.startNode) || lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_AUTOBRANCH,lineObj.startNode)){
					html += lbpm.globals.getModifyHandlerHTML(nodeObj,i,false,"lbpm.globals.afterChangeHandlerInfoes",null,"lbpm.globals.setHandlerFormulaDialog","handlerIds["+i+"]","handlerNames["+i+"]");
				}
				else{
					html += lbpm.globals.getModifyHandlerHTML(nodeObj,i,(onlyOneSelect==true?false:((nodeObj.id == futureNodeId || lineObj.id == lineObj.startNode.defaultBranch)?false:true)),"lbpm.globals.afterChangeFurtureHandlerInfoes",null,null,"handlerIds["+i+"]","handlerNames["+i+"]");
				}
			}
		}
		
		if(noCalcResult==true && fromCalcBranch == true){
			html += "&nbsp;&nbsp;" + lbpm.constant.NOCALCBRANCHCRESULT;
		}

		html += "&nbsp;&nbsp;"
		
		var rightHtml="";
		if(lbpm.approveType == "right"){
			rightHtml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;";//右侧模式带缩进
		}
		
		var notifyTypeHtml=lbpm.globals.getNotifyType4Node(nodeObj);

        $(notifyTypeHtml).find('.lui-lbpm-checkbox');
        var notifyTypeLength=0;//有多少子项，例如待办，邮件，短信
        if($(notifyTypeHtml)){
            for(var z=0;z<$(notifyTypeHtml).length;z++){
                var $notifyTypeHtml = $($(notifyTypeHtml)[z]);

                var lbpmCheckClass=$($notifyTypeHtml).attr('class');
				if(lbpmCheckClass=="lui-lbpm-checkbox"){
                    notifyTypeLength++;
				}
            }
        }

        //路由线路大于1，先隐藏
        if(routeLinesLength>1){
        	  
        	//并行分支，按要求都显示
        	if(lineObj.startNode.nodeDescType=='splitNodeDesc'){
        		//并行分支，按要求都显示
            	if(lineObj.startNode.nodeDescType=='splitNodeDesc'||(lineObj.startNode.nodeDescType=="manualBranchNodeDesc"&&lineObj.startNode.defaultBranch&&lineObj.startNode.defaultBranch!="")){
            		//通知项大于显示
                	if(notifyTypeLength>1){
                        html +="<span class='labelNotifyType' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
        			}else{
                        html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
        			}
            	}else{
            		html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
            	}
        	}else{
        		html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
        	}
        	
        	
        }else{
            //通知项大于显示
        	if(notifyTypeLength>1){
                html +="<span class='labelNotifyType' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
			}else{
                html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
			}
		}
        
		
		// 自由子节点即将流向相关
		if(nodeObj.nodeDescType=='freeSubFlowNodeDesc') {
			lbpm.canHideNextNodeTr = false;
			if (lbpm.nodes[nodeObj.startNodeId].endLines[0].endNode.nodeDescType != "groupEndNodeDesc") {
				var groupFirstSubNode = lbpm.nodes[nodeObj.startNodeId].endLines[0].endNode;
				html += "&nbsp;" + '<b>&rarr;&nbsp;&nbsp;[';
				if (!(_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier())) {
					html += groupFirstSubNode.id  + '.';
				}
				html += groupFirstSubNode.name + '(<label style="color:#dd772c">' + groupFirstSubNode.handlerNames +'</label>)]</b>';
			}
			if (nodeObj.initSubNodeId == null || nodeObj.initSubNodeId == "") {
				lbpm.globals.loadFreeSubFlowNodeUL(nodeObj);
				var groupNodeId = nodeObj.id;
				html += "&nbsp;&nbsp;" + "<a href=\"javascript:;\" class=\"com_btn_link\" style=\"margin: 0 10px;\" onclick=\"lbpm.globals.setNextSubNode('"+groupNodeId+"');\">";
				if (lbpm.myAddedSubNodes.length > 0) {
					html += lbpm.constant.UPDATENEXTSTEP;
				} else {
					html += lbpm.constant.SETNEXTSTEP;
				}
				html +=  '</a>';
			}
		} else if (lineObj.startNode.groupNodeId != null && lineObj.startNode.groupNodeType == "freeSubFlowNode") {
			lbpm.canHideNextNodeTr = false;
			var groupNodeId = lbpm.nodes[lbpm.nowNodeId].groupNodeId;
			lbpm.globals.loadFreeSubFlowNodeUL(lbpm.nodes[groupNodeId]);
			html += "&nbsp;&nbsp;" + "<a href=\"javascript:;\" class=\"com_btn_link\" style=\"margin: 0 10px;\" onclick=\"lbpm.globals.setNextSubNode('"+groupNodeId+"');\">";
			if (lbpm.myAddedSubNodes.length > 0) {
				html += lbpm.constant.UPDATENEXTSTEP;
			} else {
				html += lbpm.constant.SETNEXTSTEP;
			}
			html +=  '</a>';
		} else if (showNextStep == true) {
			lbpm.canHideNextNodeTr = false;
			lbpm.globals.loadFreeSubFlowNodeUL(lineObj.startNode);
			var groupNodeId = lineObj.startNode.id;
			html += "&nbsp;&nbsp;" + "<a href=\"javascript:;\" class=\"com_btn_link\" style=\"margin: 0 10px;\" onclick=\"lbpm.globals.setNextSubNode('"+groupNodeId+"');\">";
			if (lbpm.myAddedSubNodes.length > 0) {
				html += lbpm.constant.UPDATENEXTSTEP;
			} else {
				html += lbpm.constant.SETNEXTSTEP;
			}
			html +=  '</a>';
		}

		html += "</div>"; 
	});
//	if(html.lastIndexOf("<br>") == (html.length - 4)){
//		html = html.substring(0, html.length - 4);
//	};
	html += "</table></tr></td>";
	return html; 

};
/*
 * 获取启动并行分支节点的属性(包括默认分支和是否选中）
 */
function getSplitNodeInfo(nodeId){
	var processId = lbpm.modelId;
	var params = new KMSSData().AddBeanData("lbpmProcessDefinitionDetailService&processId="+processId+"&nodeId="+nodeId).GetHashMapArray();
	if(params && params.length > 0){
		return WorkFlow_LoadXMLData(params[0].key0)
	}
	return null;
}
//获取当前节点选择的人工决策节点（[{NodeName:N3, NextRoute:N4},{NodeName:N9, NextRoute:N11}]）
lbpm.globals.getSelectedFurtureNode=function(){
	var furtureNodeSelect = new Array();
	$("input[name='futureNode']:checked").each(function(index, input){
    	var json = {};
    	input = $(input);
		json.NodeName = input.attr('manualBranchNodeId');
		json.NextRoute = input.val();
		furtureNodeSelect.push(json);
    });
	return furtureNodeSelect;
};
//人工决策节点设置即将流向处理人
lbpm.globals.setFurtureHandlerInfoes=function(rtv,handlerSelectType){
	var isNull = (rtv == null);
	var handlerIdsObj;
	var handlerNamesObj;
	var handlerShowNames;
	var nextNodeId; 
	var futureNodeObj=$("input[name='futureNode']:checked");
	var futureIndex=null;
	if(futureNodeObj.length>0){
		nextNodeId = futureNodeObj[0].value;
		var nextNodeObj=lbpm.globals.getNodeObj(nextNodeId);
		if (nextNodeObj.nodeDescType=="embeddedSubFlowNodeDesc" && nextNodeObj.isInit == "true") {
			nextNodeObj = lbpm.globals.getNextNodeObj(nextNodeObj.startNodeId);
		}
		nextNodeId=nextNodeObj.id;
		futureIndex=futureNodeObj[0].getAttribute("index");
	}else{	
		var currentNodeObj=lbpm.globals.getCurrentNodeObj();
		var nextNodeObj=lbpm.globals.getNextNodeObj(currentNodeObj.id);
		if (nextNodeObj.nodeDescType=="embeddedSubFlowNodeDesc" && nextNodeObj.isInit == "true") {
			nextNodeObj = lbpm.globals.getNextNodeObj(nextNodeObj.startNodeId);
		}
		nextNodeId=nextNodeObj.id;
		futureIndex="0";
	}
	handlerIdsObj = document.getElementsByName("handlerIds[" + futureIndex + "]")[0];
	handlerNamesObj = document.getElementsByName("handlerNames[" + futureIndex + "]")[0];
	handlerShowNames = document.getElementById("handlerShowNames[" + futureIndex + "]");
	if (isNull) {
			handlerIdsObj.value = handlerIdsObj.getAttribute("defaultValue");
			handlerNamesObj.value = handlerNamesObj.getAttribute("defaultValue");
		return;
	}
	if(handlerSelectType==lbpm.constant.ADDRESS_SELECT_FORMULA){
		handlerIdsObj.setAttribute("isFormula", "true");
	}
	else{
		handlerIdsObj.setAttribute("isFormula", "false");
	}
	handlerIdsObj.setAttribute("defaultValue", handlerIdsObj.value);
	handlerNamesObj.setAttribute("defaultValue", handlerNamesObj.value);
	//handlerShowNames.innerHTML = handlerNamesObj.value;
	var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
	if(operatorInfo == null){
		return;
	}	
	var currentNodeId = lbpm.nowNodeId; 
	//返回json对象
	var rtnNodesMapJSON= new Array();
	var nodeObj=new Object();
	nodeObj.id=nextNodeId;
	nodeObj.handlerIds=handlerIdsObj.value;
	nodeObj.handlerNames=handlerNamesObj.value;
	if(handlerSelectType!=null){
		nodeObj.handlerSelectType=handlerSelectType;
	}
	rtnNodesMapJSON.push(nodeObj);
	var param={};
	param.nodeInfos=rtnNodesMapJSON;
	lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,param);
};

//人工选择节点修改处理人后设置即将流向处理人 limh 2011年3月30日
lbpm.globals.afterChangeFurtureHandlerInfoes=function(rtv,handlerSelectType){
	var handlerIdsObj ;
	var handlerNamesObj ;
	var handlerShowNames;
	if(rtv){
		var rtvArray = rtv.GetHashMapArray();
		if(rtvArray){
			var futureNodeObj=$("input[name='futureNode']:checked");
			var futureIndex=null;
			if(futureNodeObj.length>0){
				futureIndex=futureNodeObj[0].getAttribute("index");
			}else{
				futureIndex="0";
			}
			handlerIdsObj = document.getElementsByName("handlerIds[" + futureIndex + "]")[0];
			handlerNamesObj = document.getElementsByName("handlerNames[" + futureIndex + "]")[0];
			handlerShowNames = document.getElementById("handlerShowNames[" + futureIndex + "]");
			var idValue = "";
			var nameValue = "";
			for(var i=0;i<rtvArray.length;i++){
				idValue += ";"+rtvArray[i]["id"];
				nameValue += ";"+rtvArray[i]["name"];
			}
			handlerIdsObj.value = idValue.substring(1);
			handlerNamesObj.value =  nameValue.substring(1);
			lbpm.globals.setFurtureHandlerInfoes(rtv,handlerSelectType);
		}
	}
};

//启动并行分支节点修改处理人后设置即将流向处理人
lbpm.globals.afterChangeHandlerInfoes = function(rtv,handlerSelectType,nodeId,controlId){
  	if(rtv){
  		if(rtv.GetHashMapArray()){
  			
  			var handlersArray = rtv.GetHashMapArray();
  			var handlerIds="";
  			var handlerNames="";
  			if(handlersArray.length > 0) {
	  			for(var i=0;i<handlersArray.length;i++){
	  				handlerIds+=";"+handlersArray[i].id;
	  				handlerNames+=";"+handlersArray[i].name;
	  			}
	  			handlerIds = handlerIds.substring("1");
	  			handlerNames = handlerNames.substring("1");
  			} 	
  			// 返回json对象
  			var rtnNodesMapJSON= new Array();
  			rtnNodesMapJSON.push({
  				id:nodeId,
  				handlerSelectType:handlerSelectType,
  				handlerIds:handlerIds,
  				handlerNames:handlerNames
  			});
  			if (typeof controlId !== "undefined"){
  				lbpm.globals.newAuditControlId = controlId;
  			}else{
  				lbpm.globals.newAuditControlId = null;
  			}
  		    var param={};
  		    param.nodeInfos=rtnNodesMapJSON;
  		    lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,param);
  		}
  	}
};

_hidOtherAddressOpt=function(currIndex){
	$("input[name='futureNode']").each(function(i){
		if(i!=currIndex){
			var addressSpanId = "_addressSpanIndex_"+ i;
			var el = document.getElementById(addressSpanId);
			if(el){
	 			lbpm.globals.hiddenObject(el, true);
			}
		}
	});
}

//在即将流向此增加通知方式 add by wubing date:2016-05-04 
lbpm.globals.getNotifyType4Node = function(node){
	if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_REVIEW,node)
		||lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SIGN,node)
		||lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_HANDLER,node)
		||lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,node)
        ||lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_AUTOBRANCH,node)
        ||lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SPLIT,node)){
		return lbpm.globals.getNotifyType4NodeHTML(node.id);
	}else{
		return "";
	}
};

lbpm.globals.getNotifyType4NodeHTML = function(nodeId) {
	//条件分支不需要通知方式  by xuwh start
	if(lbpm.nodes[nodeId] && lbpm.nodes[nodeId].nodeDescType == "autoBranchNodeDesc"){
		return "";
	}
	//条件分支不需要通知方式  by xuwh end
	var notifyTypeJson = null;
	$("input[name='futureNode']").each(function(i){
		if(notifyTypeJson == null){
			notifyTypeJson = {};
		}
		var ntn = "_notifyType_"+this.value;
		var ntv = "";
		if($("input[name='"+ntn+"']").length>0){
			ntv = $("input[name='"+ntn+"']")[0].value;
		}
		notifyTypeJson[this.value]=ntv;
	});
	if(notifyTypeJson==null){
		notifyTypeJson = {};
		$("input[name='_notifyType_node']").each(function(i){
			var ntn = "_notifyType_"+this.value;
			var ntv = "";
			if($("input[name='"+ntn+"']").length>0){
				ntv = $("input[name='"+ntn+"']")[0].value;
			}
			notifyTypeJson[this.value]=ntv;
		});
	}
	var ntv = "";
	if(notifyTypeJson!=null){
		ntv = notifyTypeJson[nodeId] || (lbpm.nodes[nodeId] && lbpm.nodes[nodeId]["notifyType"]) || "";
	}
	
	var url=Com_Parameter.ContextPath+"sys/lbpmservice/include/sysLbpmNotifyTypedata.jsp" + "?m_Seq="+Math.random();
	url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
	url += "&nodeId=" + nodeId;
	if(ntv!=""){
		url += "&notifyType=" + ntv;
	}

	$.ajaxSettings.async = false;
	var html="";
	$.get(url, function(result){
		html =result;
	});
	$.ajaxSettings.async = true;
	return html;
	
}

//---------------------即席子流程@即将流向---------------------
lbpm.globals.getNextAdHocSubFlowRouteInfo = function() {
	lbpm.canHideNextNodeTr = false;
	if (lbpm.adHocSubFlowNodeInfo == null || lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId] == null) {
		lbpm.globals.initAdHocSubFlowInfo();
	}
	lbpm.adHocRoutes = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId];
	// 构建即席子流程的即将流向选项内容
	var html = "";
	
	var nextAdHocSubRoutes = [];
	// 构建内置子节点的route选择项
	for (var index = 0;index<lbpm.adHocRoutes.length; index++) {
		if (lbpm.adHocRouteId != null && lbpm.adHocRouteId == lbpm.adHocRoutes[index].startNodeId) {
			continue;
		}
		var subNode = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId].adHocSubNodes[lbpm.adHocRoutes[index].startNodeId];
		var nodeObj = subNode.data;
		nextAdHocSubRoutes.push(nodeObj);
	}
	// 构建即席子流程节点流出的下一步节点的route选择项
	nextAdHocSubRoutes.push(lbpm.nodes[lbpm.nowAdHocSubFlowNodeId].endLines[0].endNode);
	
	var onlyOneSelect = true;
	if (nextAdHocSubRoutes.length > 1) {
		onlyOneSelect = false;
	}
	
	for (index = 0;index<nextAdHocSubRoutes.length; index++) {
		html += generateAdHocSubNodeHtml(nextAdHocSubRoutes[index],onlyOneSelect,index);
	}
	
	return html;
}

function generateAdHocSubNodeHtml(nodeObj,onlyOneSelect,index){
	var html = "";
	html += "<div class='lbpmNextRouteInfoRow'><label style='line-height:26px;' id='nextNodeName[" + index + "]'>";
	
	html += "<input " +(onlyOneSelect==true ? "style='display:none' checked=true " : ((lbpm.nodes[lbpm.nowAdHocSubFlowNodeId].defaultBranch == nodeObj.id) ? " checked=true " : "")) 
	+ "type='radio' adHocSubFlowNodeId='" + lbpm.nowAdHocSubFlowNodeId + "'name='nextAdHocRouteId' key='nextAdHocRouteId' index='" + index + "' value='" + nodeObj.id 
	+ "' onclick='if (lbpm.globals.flowChartLoaded != true) {lbpm.flow_chart_load_Frame();}'>";
	
	var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
	langNodeName=langNodeName.replace(/</g, "&lt;").replace(/>/g, "&gt;");
	var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeObj.id + ".");
	html += "<b>" + identifier + langNodeName + "</b></label>";
	var handlerIds, handlerNames, isFormulaType = (nodeObj.handlerSelectType == lbpm.constant.ADDRESS_SELECT_FORMULA || nodeObj.handlerSelectType == lbpm.constant.ADDRESS_SELECT_RULE);
	handlerIds = nodeObj.handlerIds==null?"":nodeObj.handlerIds;
	handlerNames = nodeObj.handlerNames==null?"":
		(isFormulaType?lbpm.workitem.constant.COMMONHANDLERISFORMULA:nodeObj.handlerNames);
	var hiddenIdObj = "<input type='hidden' name='handlerIds[" + index + "]' value='" + handlerIds + "' isFormula='" + isFormulaType.toString() +"' />";
	html += hiddenIdObj; 
	var hiddenNameObj = "<input type='hidden' name='handlerNames[" + index + "]' value='" + Com_HtmlEscape(handlerNames) + "' />";
	
	var dataNextNodeHandler;
	var nextNodeHandlerNames4View="";
	if(nodeObj.handlerSelectType){
		if (nodeObj.handlerSelectType=="formula") {
			dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,false);
		} else if (nodeObj.handlerSelectType=="matrix") {
			dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,false);
		} else if (nodeObj.handlerSelectType=="rule") {
			dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id,handlerIds,true,false);
		} else {
			dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,false);
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
	if(nextNodeHandlerNames4View != "")
		html += "(";
	html += hiddenNameObj;
	html += "<label id='handlerShowNames[" + index + "]' class='handlerNamesLabel'";
	html += " nodeId='" + nodeObj.id + "'>" + (nextNodeHandlerNames4View.replace(/;/g, '; ')) + "</label>";
	if(nextNodeHandlerNames4View != "")
		html += ")";
	html += "&nbsp;&nbsp;"+lbpm.globals.getNotifyType4NodeHTML(nodeObj.id);
	html += "</div>";
	return html;
}

//初始化即席子流程节点对象信息（数据来源于节点配置时的adHocSubFlowData)
lbpm.globals.initAdHocSubFlowInfo = function() {
	if (lbpm.adHocSubFlowNodeInfo == null) {
		//即席子流程节点的信息对象，以即席子流程节点的ID作为key来存放
		//对应的即席子流程的各环节信息(包含环节首节点ID以及环节内的子节点配置信息,默认以环节首节点ID作为环节的标识)
		//以及对应的即席子流程节点的全部子节点的配置信息
		lbpm.adHocSubFlowNodeInfo = new Object(); 
	}
	var adHocRoutes = new Array();
	var adHocSubNodes = new Object();
	var adHocSubLines = new Object();
	lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId] = adHocRoutes;
	lbpm.adHocRoutes = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId];
	var adHocSubFlowNode = lbpm.nodes[lbpm.nowAdHocSubFlowNodeId];
	var data = adHocSubFlowNode["adHocSubFlowData"];
	if(data){
		var adHocSubFlowData = WorkFlow_LoadXMLData(data);
		for(var i=0; i<adHocSubFlowData.nodes.length; i++){
			var nodeObj=adHocSubFlowData.nodes[i];
			adHocSubNodes[nodeObj.id] = {};
			adHocSubNodes[nodeObj.id].id = nodeObj.id;
			adHocSubNodes[nodeObj.id].data = nodeObj;
			adHocSubNodes[nodeObj.id].startLines=[];
			adHocSubNodes[nodeObj.id].endLines=[];
		}
		lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId].adHocSubNodes = adHocSubNodes;
		for(i=0; i<adHocSubFlowData.lines.length; i++){
			var lineObj=adHocSubFlowData.lines[i];
			adHocSubLines[lineObj.id] = {};
			adHocSubLines[lineObj.id].id = lineObj.id;
			adHocSubLines[lineObj.id].data = lineObj;
			adHocSubLines[lineObj.id].startNode = adHocSubNodes[lineObj.startNodeId];
			adHocSubLines[lineObj.id].endNode = adHocSubNodes[lineObj.endNodeId];
			(adHocSubNodes[lineObj.startNodeId].endLines).push(adHocSubLines[lineObj.id]);
			(adHocSubNodes[lineObj.endNodeId].startLines).push(adHocSubLines[lineObj.id]);
		}
		var routeNextAdHocSubNode = function(nodeObj,adHocRoute){
			adHocRoute.subNodes.push(nodeObj);
			for (var j=0;j<nodeObj.endLines.length;j++) {
				adHocRoute.subLines.push(nodeObj.endLines[j]);
				routeNextAdHocSubNode(nodeObj.endLines[j].endNode,adHocRoute);
			}
		};
		// 分组(adHocRoute)
		$.each(adHocSubNodes, function(index, nodeObj) {
			// 没有流入的节点就是每个adHocRoute的首节点，从首节点的流出往下遍历就能找出每个adHocRoute的全部子节点
			if (nodeObj.startLines.length == 0) {
				var adHocRoute = new Array();
				adHocRoute.subNodes = new Array();
				adHocRoute.subLines = new Array();
				adHocRoute.startNodeId = nodeObj.data.id;
				routeNextAdHocSubNode(nodeObj,adHocRoute);
				adHocRoutes.push(adHocRoute);
			}
		});
	}
};

//---------------------动态子流程@即将流向---------------------
lbpm.globals.getNextDynamicSubFlowRouteInfo = function(nodeId,dom){
	//暂时无需弹框提醒
	lbpm.selectedNextDynamicSubFlowRoute = true;
	if(lbpm.selectedNextDynamicSubFlowRoute){
		lbpm.globals.getNextDynamicSubFlow(nodeId,dom);
	}else{
		if (window.LUI) {
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.confirm(lbpm.constant.checkForm,function(data){
					if(data){
						lbpm.globals.getNextDynamicSubFlow(nodeId,dom);
					}
				});
			});
		}else{
			if(confirm(lbpm.constant.checkForm)){
				lbpm.globals.getNextDynamicSubFlow(nodeId,dom);
			}
		}
	}
}

lbpm.globals.getNextDynamicSubFlow = function(nodeId,dom){
	var loadDialog = null;
	if (window.LUI) {
		seajs.use(['lui/dialog'], function(dialog) {
			loadDialog = dialog.loading();
		});
	}
	setTimeout(function(){
		lbpm.globals.setDynamicNodeInfo();
		lbpm.globals.parseXMLObj();
		if(loadDialog){
			loadDialog.hide();
		}
		lbpm.selectedNextDynamicSubFlowRoute = true;
		//获取默认分支和是否可以选择
		var dynamicNode = lbpm.nodes[nodeId];
		var splitNodeId = dynamicNode.splitNodeId;
		var splitNode = lbpm.nodes[splitNodeId];
		if(splitNode){
			var canSelectDefaultBranch = splitNode.canSelectDefaultBranch;
			var store = [];
			for(var i =0;i<splitNode.endLines.length;i++){
				var endLine = splitNode.endLines[i];
				var lineNodeName = WorkFlow_getLangLabel(endLine.name,endLine["langs"]);
				if(!lineNodeName){
					lineNodeName = WorkFlow_getLangLabel(endLine.endNode.name,endLine.endNode["langs"],"nodeName");
				}
				store.push({id:endLine.endNode.id,name:lineNodeName});
			}
			var defaultStartBranchIds = splitNode.defaultStartBranchIds;
			if(defaultStartBranchIds && !$("input[name='dynamicNextNodeIds_"+nodeId+"']").val()){
				$("input[name='dynamicNextNodeIds_"+nodeId+"']").val(defaultStartBranchIds);
				var nodeIds = defaultStartBranchIds.split(";");
				var nodeNames = [];
				for(var i=0;i<nodeIds.length;i++){
					for(var j=0;j<store.length;j++){
						if(nodeIds[i]==store[j].id){
							nodeNames.push(store[j].name);
							break;
						}
					}
				}
				if($(dom).closest(".lbpmNextRouteInfoRow").find(".dynamicNextNodeNames").length==0){
					$(dom).closest(".lbpmNextRouteInfoRow").append("<div class='dynamicNextNodeNames'><div>");
				}
				$(dom).closest(".lbpmNextRouteInfoRow").find(".dynamicNextNodeNames").text(nodeNames.join(";"));
			}
			if (window.LUI) {
				seajs.use(['lui/dialog'], function(dialog) {
					var url = '/sys/lbpmservice/node/group/dynamicsubflownode/node_group_list.jsp';
					dialog.iframe(url,lbpm.constant.pleaseSelect+(dynamicNode.dynamicGroupShowName?dynamicNode.dynamicGroupShowName:dynamicNode.name),function(rtn){
						if(rtn){
							$("input[name='dynamicNextNodeIds_"+nodeId+"']").val(rtn);
							var nodeIds = rtn.split(";");
							var nodeNames = [];
							for(var i=0;i<nodeIds.length;i++){
								for(var j=0;j<store.length;j++){
									if(nodeIds[i]==store[j].id){
										nodeNames.push(store[j].name);
										break;
									}
								}
							}
							if($(dom).closest(".lbpmNextRouteInfoRow").find(".dynamicNextNodeNames").length==0){
								$(dom).closest(".lbpmNextRouteInfoRow").append("<div class='dynamicNextNodeNames'><div>");
							}
							$(dom).closest(".lbpmNextRouteInfoRow").find(".dynamicNextNodeNames").text(nodeNames.join(";"));
						}
					},{width:500,height:300,params:{defaultStartBranchIds:defaultStartBranchIds,canSelectDefaultBranch:canSelectDefaultBranch,store:store,selectedValue:$("input[name='dynamicNextNodeIds_"+nodeId+"']").val()}});
				});
			}
		}
	},50);
}