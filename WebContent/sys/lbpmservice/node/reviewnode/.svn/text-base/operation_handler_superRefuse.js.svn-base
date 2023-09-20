/*******************************************************************************
 * 功能：处理人“驳回”操作的审批所用JSP，此JSP路径在处理人“驳回”操作扩展点定义的reviewJs参数匹配
  使用：
  作者：罗荣飞
 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['handler_superRefuse'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};

	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_superRefuse');
	}
	
	//处理人操作：驳回
	function OperationClick(operationName){
		lbpm.globals.setDefaultUsageContent('handler_superRefuse');
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = lbpm.constant.opt.handlerOperationTypeRefuse.replace("{refuse}", operationName);
		var html = '<select name="jumpToNodeIdSelectObj" alertText="" key="jumpToNodeId" onchange="lbpm.globals.superJumpToNodeItemsChanged(this);"></select>';
		//在驳回时，增加选择节点通知方式 add by wubing date:2015-05-06
		html+="<label id='refuseSuperNotifyTypeDivId' style='display:none'></label></br>";
		html+="<div id='_triageHandler' style='margin-top:4px;'></div>";
		lbpm.rejectOptionsEnabled = true; // 驳回选项开关是否开启标识
		if (Lbpm_SettingInfo && (Lbpm_SettingInfo["isShowRefuseOptonal"] === "false")) {
			lbpm.rejectOptionsEnabled = false;
		}
		// 驳回选项开关开启时才生成驳回选项html
		if (lbpm.rejectOptionsEnabled) {
			html += lbpm.globals._superRefusePassedToThisNodeLabel(operationName);
		}
		// 驳回后流经的子流程重新流转选项html
		var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
		if (isPassedSubprocessNode) {
			html += '<br/><label id="isRecoverPassedSubprocessLabel" class="lui-lbpm-checkbox" style="margin-left: 0px;';
			// if (operatorInfo.refusePassedToThisNode == "true") {
			// 	html += 'display:none;';
			// }
			html += '"><input type="checkbox" id="isRecoverPassedSubprocess" value="true" alertText="" key="isRecoverPassedSubprocess">';
			html += ('<span class="checkbox-label">'+lbpm.constant.opt.abandonSubprocess+'</span>').replace("{refuse}", operationName);
			html += '</label>';
		}
		
		operationsTDContent.innerHTML = html;
		lbpm.globals.hiddenObject(operationsRow, false);

		//#154197 by wangliyong
		//流程模板配置流程选项时勾选‘驳回的节点通过后，返回我（默认值）’时，驳回默认勾选‘驳回的节点通过后，返回我’
		if(lbpm.flowcharts.rejectReturn == 'true' && lbpm.rejectOptionsEnabled){
            $('#refusePassedToSequenceFlowNode').prop("checked", false);
            $('#refusePassedToThisNodeOnNode').prop("checked", false);
            $('#refusePassedToTheNode').prop("checked", false);
            $('#refusePassedToThisNode').prop("checked", true);
		}

		// 获取可驳回到的节点集合（带节点重复过滤）
		var currNodeInfo = lbpm.globals.getCurrentNodeObj();
		var currNodeId = currNodeInfo.id;
		var url = Com_Parameter.ContextPath+"sys/lbpm/engine/jsonp.jsp";
		var pjson = {"s_bean": "lbpmRefuseRuleDataBean", "processId": $("[name='sysWfBusinessForm.fdProcessId']").val(), "nodeId": currNodeId, "_d": new Date().toString(),"refuseType":"superRefuse"};
		var passNodeArray = [];
		$.ajaxSettings.async = false;
		$.getJSON(url, pjson, function(json) {
			passNodeArray = json;
		});
		// 获取分之内节点
		var check_passNodeArray = [];
		var check_pjson = {"s_bean": "lbpmRefuseRuleDataBean", "processId": $("[name='sysWfBusinessForm.fdProcessId']").val(), "nodeId": currNodeId, "_d": new Date().toString()};
		$.getJSON(url, check_pjson, function(json) {
			check_passNodeArray = json;
		});
        $.ajaxSettings.async = true;
		var newPassNodeArray = [];
		var indexNode = 0;
		var hasDefaultRefuse = false;
		$.each(passNodeArray, function(index, nodeId) {
			if(nodeId.indexOf("#") > -1)
			{
				var nodeIdIndex = nodeId.split("#");
				nodeId = nodeIdIndex[0];
				indexNode = nodeIdIndex[1];
				hasDefaultRefuse = true;
			}
			newPassNodeArray.push(nodeId);
		});
		passNodeArray = newPassNodeArray;
		var nodeHandlerNameArray = []; // 可驳回到的节点的处理人名称信息集合
		nodeHandlerNameArray = _getPassNodeHandlerName(passNodeArray, true);
		// 构建可驳回节点下拉列表选项
		var jumpToNodeIdSelectObj = $("select[name='jumpToNodeIdSelectObj']")[0];
		for(var i = 0; i < passNodeArray.length; i++){
			var nodeInfo = lbpm.nodes[passNodeArray[i]];
			
			var	option = document.createElement("option");
			var langNodeName = WorkFlow_getLangLabel(nodeInfo.name,nodeInfo["langs"],"nodeName");
			// 开启了隐藏节点编号中的流程中的节点编号则隐藏节点编号
			var itemShowStr = (lbpm.globals.lbpmIsRemoveNodeIdentifier() || lbpm.globals.lbpmIsHideAllNodeIdentifier()) ? langNodeName : (nodeInfo.id + "." + langNodeName);			itemShowStr += nodeHandlerNameArray[nodeInfo.id];
			option.title = itemShowStr;
			var optTextLength = 65;
			itemShowStr = itemShowStr.length > optTextLength ? itemShowStr
					.substr(0, optTextLength) + '...'
					: itemShowStr;
			option.appendChild(document.createTextNode(itemShowStr));
			option.value=passNodeArray[i];
			jumpToNodeIdSelectObj.appendChild(option);
		}
		if(jumpToNodeIdSelectObj.options.length > 0){
			if (!hasDefaultRefuse && Lbpm_SettingInfo && Lbpm_SettingInfo["isRefuseToPrevNodeDefault"] == "true") {
				jumpToNodeIdSelectObj.selectedIndex = jumpToNodeIdSelectObj.options.length - 1;
			} else {
				jumpToNodeIdSelectObj.selectedIndex = indexNode;
			}
			// 在驳回时，增加默认的选择节点通知方式 add by wubing date:2015-05-06
			var defaultToNodeId = jumpToNodeIdSelectObj.value;
			lbpm.globals.setRefuseSuperNodeNotifyType(defaultToNodeId);

			// <----------以下为驳回返回选项相关的逻辑处理，只有在驳回选项开关开启的情况下才会执行---------->
			if (lbpm.rejectOptionsEnabled) {
				// 驳回返回本人
				var refusePassedToThisNode = document.getElementById("refusePassedToThisNode");
				var refusePassedToThisNodeLabel = document.getElementById("refusePassedToThisNodeLabel");
				// 驳回返回本节点，add by wubing date:2016-07-29
				var refusePassedToThisNodeOnNode = document.getElementById("refusePassedToThisNodeOnNode");
				var refusePassedToThisNodeOnNodeLabel = document.getElementById("refusePassedToThisNodeOnNodeLabel");
				// 驳回返回指定节点
				var refusePassedToTheNode = document.getElementById("refusePassedToTheNode");
				var refusePassedToTheNodeLabel = document.getElementById("refusePassedToTheNodeLabel");

				//驳回的节点是否在分支内
				lbpm.globals._isJumpNodeInJoin = false;
				//默认判断是否在分之内
				var isInJoin = false;
				for(var i=0;i<check_passNodeArray.length;i++){
					if(check_passNodeArray[i]==jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value){
						isInJoin = true;
						break;
					}     
				}
				lbpm.globals._isJumpNodeInJoin = isInJoin;

				/** 驳回选项的显示规则：1、一旦勾中了其中一个，则隐藏掉其它的选项；2、驳回返回本人和驳回返回本节点只能在分支内显示 */
				if(isInJoin == false){
					// 分支外，则隐藏驳回本人和驳回本节点的开关
					if(refusePassedToThisNode)
						refusePassedToThisNode.checked = false;
					if(refusePassedToThisNodeLabel){
						refusePassedToThisNodeLabel.style.display = "none";
						$(refusePassedToThisNodeLabel).next().css("display","none");
					}
						
					if(refusePassedToThisNodeOnNode)
						refusePassedToThisNodeOnNode.checked = false;
					if(refusePassedToThisNodeOnNodeLabel){
						refusePassedToThisNodeOnNodeLabel.style.display = "none";
						$(refusePassedToThisNodeOnNodeLabel).next().css("display","none");
					}
					if(refusePassedToTheNodeLabel)
						refusePassedToTheNodeLabel.style.display = "";
				}
				// 构建超级驳回后可返回到的节点选项（For驳回返回指定节点）
				lbpm.globals.buildSuperReturnBackToNodeIdSelectOption(jumpToNodeIdSelectObj);

				// 监听切换驳回节点
				Com_AddEventListener(jumpToNodeIdSelectObj, "change", function(){
					//默认判断是否在分之内
					var isInJoin = false;
					for(var i=0;i<check_passNodeArray.length;i++){
						if(check_passNodeArray[i]==jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value){
							isInJoin = true;
							break;
						}     
					}
					lbpm.globals._isJumpNodeInJoin = isInJoin;
					/** 切换时驳回选项的相关规则：
					 *  1、若切换之前勾选了驳回返回指定节点，则切换的时候自动去掉勾选；
					 *  2、若切换之前勾选了是驳回返回本人和驳回放回本节点，则在切换后的节点（分支内）有驳回返回本人和驳回放回本节点选项时，继承切换前的勾选；
					 */
					if(isInJoin == false){
						// 分支外，则去掉勾选且隐藏驳回本人和驳回本节点的开关
						if(refusePassedToThisNode)
							refusePassedToThisNode.checked = false;
						if(refusePassedToThisNodeLabel){
							refusePassedToThisNodeLabel.style.display = "none";
							$(refusePassedToThisNodeLabel).next().css("display","none");
						}
						if(refusePassedToThisNodeOnNode)
							refusePassedToThisNodeOnNode.checked = false;
						if(refusePassedToThisNodeOnNodeLabel){
							refusePassedToThisNodeOnNodeLabel.style.display = "none";
							$(refusePassedToThisNodeOnNodeLabel).next().css("display","none");
						}
						if(refusePassedToTheNodeLabel)
							refusePassedToTheNodeLabel.style.display = "";
					}else{
						// 分支内，则根据情况控制返回本人和返回本节点的开关显示
						if (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked != true) {
							refusePassedToThisNodeLabel.style.display = "";
							$(refusePassedToThisNodeLabel).next().css("display","block");
						}
						if (refusePassedToThisNode && refusePassedToThisNode.checked != true) {
							refusePassedToThisNodeOnNodeLabel.style.display = "";
							$(refusePassedToThisNodeOnNodeLabel).next().css("display","block");
						}
					}
					// 切换驳回节点的时候，自动取消驳回返回指定节点的勾选
					if (refusePassedToTheNode && refusePassedToTheNode.checked == true) {
						refusePassedToTheNode.checked = false;
						refusePassedToTheNode.disabled = false;
						lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], true);
					}

					if (isPassedSubprocessNode) {
						if (!((refusePassedToThisNode && refusePassedToThisNode.checked) || (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) || (refusePassedToTheNode && refusePassedToTheNode.checked))) {
							// 没有驳回返回选项被勾选时，驳回后流经的子流程重新流转选项可以显示出来
							document.getElementById("isRecoverPassedSubprocessLabel").style.display = "";
						}
					}
				});

				// 驳回后流经的子流程重新流转选项
				if (isPassedSubprocessNode) {
					var isRecoverPassedSubprocess = document.getElementById("isRecoverPassedSubprocess");
					var isRecoverPassedSubprocessLabel = document.getElementById("isRecoverPassedSubprocessLabel");
					if (refusePassedToThisNode.checked || refusePassedToThisNodeOnNode.checked || refusePassedToTheNode.checked) {
						isRecoverPassedSubprocessLabel.style.display = "none";
					}
					Com_AddEventListener(refusePassedToThisNode, "click", function(){
						if (refusePassedToThisNode && refusePassedToThisNode.checked) {
							isRecoverPassedSubprocess.checked = false;
						}
						isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNode && refusePassedToThisNode.checked) ? "none" : "";
					});
					Com_AddEventListener(refusePassedToThisNodeOnNode, "click", function(){
						if (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) {
							isRecoverPassedSubprocess.checked = false;
						}
						isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) ? "none" : "";
					});
					Com_AddEventListener(refusePassedToTheNode, "click", function(){
						if (refusePassedToTheNode && refusePassedToTheNode.checked) {
							isRecoverPassedSubprocess.checked = false;
						}
						isRecoverPassedSubprocessLabel.style.display = (refusePassedToTheNode && refusePassedToTheNode.checked) ? "none" : "";
					});
				}
			}
			// <----------END---------->
		}
		
			var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
			//开关开启才会去执行驳回具体审批人
			if(lbpm.settingInfo.isRefuseSelectPeople=="true"){
				if(processDefine&&processDefine.refuseSelectPeople&&processDefine.refuseSelectPeople=="true"){
					if(jumpToNodeIdSelectObj.selectedIndex!==undefined&&jumpToNodeIdSelectObj.selectedIndex>-1){
				    	 var nodeData = lbpm.nodes[jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value];
				    		//会审才会显示具体人员
							if(nodeData.processType&&nodeData.processType=="2"){
								var selectTrialHtml=_buildSelectTrialStaff(jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value);
								$("#_triageHandler").html(selectTrialHtml);	
								$("xformflag[flagtype='xform_fSelect']").fSelect();
							}else{
								$("#_triageHandler").html("");
							}
				     }
				}
			}
		
		
		if (jumpToNodeIdSelectObj.options.length == 0) {
			operationsTDContent.innerHTML = (lbpm.constant.opt.noRefuseNode+'<input type="hidden" alertText="'+lbpm.constant.opt.noRefuseNode+'" key="jumpToNodeId">').replace(/{refuse}/g, operationName);
		}
	};
	
	//“驳回”操作的检查
	function OperationCheck(){
		var jumpToNodeIdOptionObj = $("select[name='jumpToNodeIdSelectObj']").find("option:selected");
		if (jumpToNodeIdOptionObj.length == 0) {
			alert(lbpm.constant.opt.noRefuseNode.replace("{refuse}", lbpm.currentOperationName));
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	};

	//"驳回"操作的获取参数
	function setOperationParam()
	{	
		var refusePassedToThisNode = document.getElementById("refusePassedToThisNode");
		// 驳回返回本节点，add by wubing date:2016-07-29
		var refusePassedToThisNodeOnNode = document.getElementById("refusePassedToThisNodeOnNode");
		// 驳回返回指定节点
		var refusePassedToTheNode = document.getElementById("refusePassedToTheNode");
		var jumpStr=$("[key='jumpToNodeId']")[0].value;
		var jumpArr=jumpStr.split(":");	
		lbpm.globals.setOperationParameterJson(jumpArr[0],"jumpToNodeId", "param");
		if(jumpArr.length>1){
			lbpm.globals.setOperationParameterJson(jumpArr[1],"jumpToNodeInstanceId", "param");
		}else{
			lbpm.globals.setOperationParameterJson("","jumpToNodeInstanceId", "param");
		}	
		
		if (lbpm.rejectOptionsEnabled) {
			if(refusePassedToThisNode)
				lbpm.globals.setOperationParameterJson($("[key='refusePassedToThisNode']")[0],"refusePassedToThisNode", "param");
			//增加驳回返回本节点，add by wubing date:2016-07-29
			if(refusePassedToThisNodeOnNode)
				lbpm.globals.setOperationParameterJson($("[key='refusePassedToThisNodeOnNode']")[0],"refusePassedToThisNodeOnNode", "param");
			//增加驳回返回指定节点，add by linbb
			if(refusePassedToTheNode)
				lbpm.globals.setOperationParameterJson($("[key='refusePassedToTheNode']")[0],"refusePassedToTheNode", "param");
			if (refusePassedToTheNode && $("[key='refusePassedToTheNode']")[0].checked) {
				lbpm.globals.setOperationParameterJson($("[key='returnBackToNodeId']")[0],"returnBackToNodeId", "param");
			}
		} else {
			if(refusePassedToThisNode)
				lbpm.globals.setOperationParameterJson(false, "refusePassedToThisNode", "param");
			if(refusePassedToThisNodeOnNode)
				lbpm.globals.setOperationParameterJson(false, "refusePassedToThisNodeOnNode", "param");
			if(refusePassedToTheNode)
				lbpm.globals.setOperationParameterJson(false, "refusePassedToTheNode", "param");
		}
		
	    //会审驳回到具体人
		if($("[key='lbpmHandlerTriage']").length>0&&$("[key='lbpmHandlerTriage']")[0]){
			var lbpmHandlerTriageStr=$("[key='lbpmHandlerTriage']")[0].value;
			lbpm.globals.setOperationParameterJson(lbpmHandlerTriageStr,"lbpmHandlerTriage", "param");
		}
		
		
		// 驳回后流经的子流程重新流转
		lbpm.globals.setOperationParameterJson($("[key='isRecoverPassedSubprocess']")[0],"isRecoverPassedSubprocess", "param");
	};	
})(lbpm.operations);
	

//取得有效的上一历史节点对象
lbpm.globals.getHistoryPreviousNodeInfo=function(){
	var passNodeString = lbpm.globals.getAvailableHistoryRoute();
	var passNodeArray = passNodeString.split(";");
	for(var i = passNodeArray.length - 1; i >= 0; i--){
		var passNodeInfo = passNodeArray[i].split(":");
		var nodeInfo = lbpm.nodes[passNodeInfo[0]];
		if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_START, nodeInfo)
				|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END, nodeInfo)
				|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_AUTOBRANCH, nodeInfo)
				|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH, nodeInfo)
				|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND, nodeInfo)) {
			continue;
		}
		if(nodeInfo.id == lbpm.nowNodeId){
			continue;
		}
		return passNodeArray[i];
	}
};

//取得有效的上一历史路径
lbpm.globals.getAvailableHistoryRoute=function(){
	var fdTranProcessObj = document.getElementById("sysWfBusinessForm.fdTranProcessXML");
	var statusData = WorkFlow_GetStatusObjectByXML(fdTranProcessObj.value);
	for(var i=0; i<statusData.runningNodes.length; i++){
		var nodeInfo = statusData.runningNodes[i];
		if(nodeInfo.id == lbpm.nowNodeId){
			return nodeInfo.routePath;
		}
	}
	return "";
}

lbpm.globals._superRefusePassedToThisNodeLabel = function(operationName){
	var extAttrs=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	var refuseTypes = [];
	var isDisable = false;
	var index = 0;
	for(var i = 0;extAttrs && i < extAttrs.length;i++){
		if(extAttrs[i].name == 'refuse_types'){
			refuseTypes=extAttrs[i].value.split(";");
			break;
		}
	}
	//只有一条时要加上只读属性
	if(refuseTypes && refuseTypes.length==1){
		isDisable = true;
	}
	var html = '';
	
	// zl
	if(showOption('refusePassedToSequenceFlowNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '<br>';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += 'id="refusePassedToSequenceFlowNodeLabel" class="lui-lbpm-checkbox" onclick="lbpm.globals.handleSuperRefuseOption(this)" title="' + lbpm.constant.opt.sequenceFlow.replace("{refuse}", operationName) + '"><input type="checkbox" id="refusePassedToSequenceFlowNode" value="true" alertText="" key="refusePassedToSequenceFlowNode"';
		if(index==0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.sequenceFlowTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}
	if(showOption('refusePassedToThisNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '<br>';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="refusePassedToThisNodeLabel" class="lui-lbpm-checkbox" onclick="lbpm.globals.handleSuperRefuseOption(this)" title="' + lbpm.constant.opt.returnBackMe.replace("{refuse}", operationName) +'"><input type="checkbox" id="refusePassedToThisNode" value="true" alertText="" key="refusePassedToThisNode"';
		if(index==0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackMeTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}
	
	//增加驳回返回本节点，add by wubing date:2016-07-29
	if(showOption('refusePassedToThisNodeOnNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '<br>';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="refusePassedToThisNodeOnNodeLabel" class="lui-lbpm-checkbox" onclick="lbpm.globals.handleSuperRefuseOption(this)" title="' + lbpm.constant.opt.returnBack.replace("{refuse}", operationName) + '"';
		html += '><input type="checkbox" id="refusePassedToThisNodeOnNode" value="true" alertText="" key="refusePassedToThisNodeOnNode"';
		if(index == 0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html+='>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}
	
	
	//增加驳回返回指定节点 add by linbb
	if(showOption('refusePassedToTheNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '<br>';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="refusePassedToTheNodeLabel" class="lui-lbpm-checkbox" onclick="lbpm.globals.handleSuperRefuseOption(this)" title="' + lbpm.constant.opt.returnBackTheNode.replace("{refuse}", operationName) + '"';
		html += '><input type="checkbox" id="refusePassedToTheNode" value="true" alertText="" key="refusePassedToTheNode"';
		if(index == 0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html+='>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackTheNodeTitle.replace("{refuse}", operationName)+'</span></label>';
		html += '<select name="returnBackToNodeIdSelectObj" alertText="" key="returnBackToNodeId" style="max-width:200px;margin-left:4px;'+(index > 0 ? 'display:none':'')+'"></select>';
		index += 1;
	}
	
	
	return html;
}

lbpm.globals.setRefuseSuperNodeNotifyType=function(nodeId){
	var refuseNotifyTypeDivIdEl = document.getElementById("refuseSuperNotifyTypeDivId");
	refuseNotifyTypeDivIdEl.innerHTML=lbpm.globals.getNotifyType4NodeHTML(nodeId);
	refuseNotifyTypeDivIdEl.style.display="";
}

lbpm.globals.handleSuperRefuseOption=function(el){
	 var refusePassedToSequenceFlowNode = document.getElementById("refusePassedToSequenceFlowNode");
	 var refusePassedToThisNodeOnNode = document.getElementById("refusePassedToThisNodeOnNode");
	 var refusePassedToTheNode = document.getElementById("refusePassedToTheNode");
	 var refusePassedToThisNode = document.getElementById("refusePassedToThisNode");
	 
	 if(el.id=="refusePassedToSequenceFlowNodeLabel"){
			var thisCheck = refusePassedToSequenceFlowNode;
			var othersNoSelect = (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToThisNode && !refusePassedToThisNode.checked);
			
			if(thisCheck) {
				if(refusePassedToThisNodeOnNode)
					refusePassedToThisNodeOnNode.checked = false;
				if(refusePassedToTheNode)
					refusePassedToTheNode.checked = false;
				if(refusePassedToThisNode)
					refusePassedToThisNode.checked = false;
			} 
			if(othersNoSelect && !thisCheck.checked) {
				thisCheck.checked = true;
			} 
			var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
			if(isPassedSubprocessNode){
				var isRecoverPassedSubprocessLabel = document.getElementById("isRecoverPassedSubprocessLabel");
				isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNode.checked) ? "none" : "";
			}
		}
	 
	 if(el.id=="refusePassedToThisNodeLabel"){
		 var thisCheck = refusePassedToThisNode;
			var othersNoSelect = (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked);
			
			if(thisCheck) {
				if(refusePassedToThisNodeOnNode)
					refusePassedToThisNodeOnNode.checked = false;
				if(refusePassedToTheNode)
					refusePassedToTheNode.checked = false;
				if(refusePassedToSequenceFlowNode)
					refusePassedToSequenceFlowNode.checked = false;
			} 
			if(othersNoSelect && !thisCheck.checked) {
				thisCheck.checked = true;
			} 		
	}
	if(el.id=="refusePassedToThisNodeOnNodeLabel"){
		var thisCheck = refusePassedToThisNodeOnNode;
		var othersNoSelect = (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToThisNode && !refusePassedToThisNode.checked);
		
		if(thisCheck) {
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			if(refusePassedToTheNode)
				refusePassedToTheNode.checked = false;
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
		} 
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		} 
	}
	if(el.id=="refusePassedToTheNodeLabel"){
		var thisCheck = refusePassedToTheNode;
		var othersNoSelect = (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked) && (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToThisNode &&!refusePassedToThisNode.checked);
		
		if(thisCheck) {
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			if(refusePassedToThisNodeOnNode)
				refusePassedToThisNodeOnNode.checked = false;
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
		} 
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		} 
	
		if (thisCheck.checked) {
			lbpm.globals.buildSuperReturnBackToNodeIdSelectOption();
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], false);
		} else {
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], true);
			lbpm.events.fireListener("clickRefusePassedToTheNodeLabel", null);
		}
	} else {
		lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], true);
	}
}

lbpm.globals.buildSuperReturnBackToNodeIdSelectOption=function(jumpToNodeIdSelectObj){
	if (jumpToNodeIdSelectObj == null) {
		jumpToNodeIdSelectObj = $("select[name='jumpToNodeIdSelectObj']")[0];
	}
	
	var jumpToNodeIdSelectOptions = jumpToNodeIdSelectObj.options;
	var jumpToNodeId = jumpToNodeIdSelectOptions[jumpToNodeIdSelectObj.selectedIndex].value;
	var returnNodes = getReturnNodes(lbpm.globals.getNodeObj(jumpToNodeId));
	
	$("select[name='returnBackToNodeIdSelectObj']").empty();
	var returnBackToNodeIdSelectObj = $("select[name='returnBackToNodeIdSelectObj']")[0];
	if(!returnBackToNodeIdSelectObj)
		return;
	for(var j = 0; j < jumpToNodeIdSelectOptions.length; j++){
		var nodeInfo = lbpm.globals.getNodeObj(jumpToNodeIdSelectOptions[j].value);
		if (!containNode(returnNodes, nodeInfo)){
			continue;
		}
		var	option = document.createElement("option");
		option.title = jumpToNodeIdSelectOptions[j].title;
		var itemShowStr = option.title.length > 65 ? option.title.substr(0, 65) + '...' : option.title;
		option.appendChild(document.createTextNode(itemShowStr));
		option.value=jumpToNodeIdSelectOptions[j].value;
		returnBackToNodeIdSelectObj.appendChild(option);
	}
	if (containNode(returnNodes, lbpm.globals.getNodeObj(lbpm.nowNodeId))) {
		var	option = document.createElement("option");
		option.title = lbpm.constant.opt.thisNode;
		option.appendChild(document.createTextNode(option.title));
		option.value=lbpm.nowNodeId;
		returnBackToNodeIdSelectObj.appendChild(option);
	}
	
	if (returnBackToNodeIdSelectObj.options.length == 0) {
		var	option = document.createElement("option");
		option.appendChild(document.createTextNode(lbpm.constant.opt.noReturnBackNode));
		option.value=null;
		returnBackToNodeIdSelectObj.appendChild(option);
	}
}

lbpm.globals.superJumpToNodeItemsChanged=function(){
	if (lbpm.rejectOptionsEnabled && $("[key='refusePassedToTheNode']")[0].checked) {
		lbpm.globals.buildSuperReturnBackToNodeIdSelectOption();
	}
	var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
	
	//开关开启才会去执行驳回具体审批人
	if(lbpm.settingInfo.isRefuseSelectPeople=="true"){
	     if(processDefine&&processDefine.refuseSelectPeople&&processDefine.refuseSelectPeople=="true"){
	    	 if(arguments.length>0){
	 			var op=arguments[0];
	 			var nodeData = lbpm.nodes[op.value];
	 			//会审才会显示具体人员
	 			if(nodeData.processType&&nodeData.processType=="2"){
	 				var selectTrialHtml=_buildSelectTrialStaff(op.value);
	 				var operationsTDContent = document.getElementById("operationsTDContent");
	 				$("#_triageHandler").html(selectTrialHtml);	
	 				$("xformflag[flagtype='xform_fSelect']").fSelect();
	 			}else{
	 				$("#_triageHandler").html("");
	 			}
	 		}
	     }
	}
}

function getReturnNodes(jumpToNode) {
	var nodes = [];
	nodes = _findReturnNodes(jumpToNode, nodes);
	return nodes;
}

// 从指定的驳回的节点往下遍历（遇到启动并行分支需直接跳到对应的结束并行分支节点再往下遍历）
function _findReturnNodes(curr, nodes) {
	var nexts = lbpm.globals.getNextNodeObjs(curr.id);
	for (var i = 0; i < nexts.length; i ++) {
		var nNode = nexts[i];
		if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,nNode)) {
			continue;
		}
		if (containNode(nodes, nNode)) {
			continue;
		}
		nodes.push(nNode);
		if (nNode.id == lbpm.nowNodeId) {
			continue;
		}
		if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SPLIT,nNode)) {
			nNode = lbpm.globals.getNodeObj(nNode.relatedNodeIds);
			nodes.push(nNode);
		}
		_findReturnNodes(nNode, nodes);
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

//isParse为true时，计算出节点处理人
function _getPassNodeHandlerName(passNodeArray, isParse) {
	var nodeHandlerNameArray = [];
	if (isParse) {
		var nodeHandlerForParse = [];
		$.each(passNodeArray, function(index, nodeId) {
			var nodeData = lbpm.nodes[nodeId];
			if(nodeData.handlerIds){
				nodeHandlerForParse.push({"nodeId": nodeData.id, "handlerIds": nodeData.handlerIds, "handlerSelectType": nodeData.handlerSelectType, "distinct": false});
			}
		});
		nodeHandlerNameArray = _parsePassNodesHandler(nodeHandlerForParse);
	}
	
	for(var i = 0; i < passNodeArray.length; i++){
		var nodeHandlerName = nodeHandlerNameArray[passNodeArray[i]];
		if (nodeHandlerName == null || nodeHandlerName == "") {
			var nodeInfo = lbpm.nodes[passNodeArray[i]];
			if(nodeInfo.handlerNames != null && nodeInfo.handlerSelectType == 'org'){
				nodeHandlerNameArray[passNodeArray[i]] = "(" + nodeInfo.handlerNames + ")";
			} else if (nodeInfo.handlerSelectType != null)  {
				nodeHandlerNameArray[passNodeArray[i]] = "(" + lbpm.workitem.constant.COMMONLABELFORMULASHOW + ")";
			} else {
				nodeHandlerNameArray[passNodeArray[i]] = "";
			}
		} else {
			nodeHandlerNameArray[passNodeArray[i]] = "(" +nodeHandlerName + ")";
		}
	}
	return nodeHandlerNameArray;
}

function _parsePassNodesHandler(nodeHandlers) {
	var nodeHandlerNameArray = [];
	if(nodeHandlers && nodeHandlers.length > 0){
		var url = "lbpmHandlerParseService&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&modelName=" + lbpm.globals.getWfBusinessFormModelName();
		for(var i = 0; i < nodeHandlers.length; i++) {
			var node = nodeHandlers[i];
			if(!node.handlerIds) {
				continue;
			}
			url += "&nodeId=" + node.nodeId;
			url += "&handlerIds=" + encodeURIComponent(node.handlerIds);
			url += "&isFormula=" + (node.handlerSelectType == "formula" ? "true" : "false");
			url += "&isMatrix=" + (node.handlerSelectType == "matrix" ? "true" : "false");
			url += "&isRule=" + (node.handlerSelectType == "rule" ? "true" : "false");
			url += "&distinct=" + (node.distinct ? "true" : "false");
		}
		var data = new KMSSData();
		var handlerArray = data.AddBeanData(url).GetHashMapArray();
		if (handlerArray && handlerArray.length > 0) {
			for ( var j = 0; j < handlerArray.length; j++) {
				nodeHandlerNameArray[handlerArray[j].nodeId] = lbpm.globals.htmlUnEscape(handlerArray[j].name);
			}
		}
	}
	return nodeHandlerNameArray;
}

//传入节点Id，得到驳回节点的已处理人集合
function getPassNodeHandlerObj(nodeId) {
	var url = "lbpmHandlerTriageService&modelId=" + lbpm.modelId + "&fdFactId=" + nodeId;
	var handlerArray=[];
	var data = new KMSSData();
	handlerArray = data.AddBeanData(url).GetHashMapArray();
    return handlerArray;
}

function _buildSelectTrialStaff(nodeId){
	var handlerArray=getPassNodeHandlerObj(nodeId);
	var nodeData = lbpm.nodes[nodeId];
	
	var trialStaffPeopleHtmlStart="<xformflag flagid='fd_trialStaffPeople' id='_xform_trialStaffPeople' property='trialStaffPeople' flagtype='xform_fSelect' _xform_type='fSelect'>"+
		"<div class='select_div_box xform_Select' fd_type='fSelect' style='display: inline-block; width: auto; text-align: left;'>"+
	"<div id='div_trialStaffPeople' style='display:none'>"+
		"<input name='trialStaffPeople' type='hidden' value='' key='lbpmHandlerTriage'>"+
	"</div>"+
	"<div class='fs-wrap multiple'>"+
		"<div class='fs-label-wrap'>"+
			"<div class='fs-label' >=="+lbpm.constant.opt.refusePeople+"==</div>"+
			"<span class='fs-arrow'></span>"+
		"</div>"+
		"<div class='fs-dropdown'>"+
			"<div class='fs-search'>"+
				"<input type='text' autocomplete='off' placeholder='"+lbpm.constant.opt.refusePeopleSearch+"'>"+
				"<i class='fs-search-icon-del'></i>"+
			"</div>";
			
			var optionHtml="";
	if(handlerArray.length>0){
		for(var i=0;i<handlerArray.length;i++){
			optionHtml+="<div class='fs-options'>"+
				"<div data-value='"+handlerArray[i].handlerId+"' class='fs-option' data-index='0'>"+
					"<span class='fs-checkbox'><i></i></span>"+
					"<div class='fs-option-label'>"+handlerArray[i].handlerName+"</div>"+
					"<input type='hidden' name='_trialStaffPeople' value='"+handlerArray[i].handlerId+"'>"+
				"</div>"+
			"</div>";
		}
	}
				
	var trialStaffPeopleHtmlEnd="</div>"+
		"</div>"+
	"</div></xformflag>";
	
	var selectHtml=trialStaffPeopleHtmlStart+optionHtml+trialStaffPeopleHtmlEnd;
	return selectHtml;
}
function showOption(param,ar){
	if(!ar || ar.length == 0)
		return true;
	for(var i = 0;i < ar.length;i++){
		if(param == ar[i])
			return true;
	}
	return false;
}