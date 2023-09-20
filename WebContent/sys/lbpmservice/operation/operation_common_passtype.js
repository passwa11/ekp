( function(lbpm) {
//通过类型的操作的提交前检测
lbpm.globals.common_operationCheckForPassType = function() {
	if (!checkFutureNodeSelected()) {
		return false;
	} else if (!checkFutureNodeObjs()) {
		return false;
	} else if (!checkAdHocSubFlowNodeSelected()) {
		return false;
	} else if(!checkIdentityValidation()){
		//弹出身份校验框
		identityValidate(); 
		return false;
	}
	return true;
};
//判断是否需要弹出“必须修改该节点的提示” #作者：曹映辉 #日期：2012年7月4日 
lbpm.globals.judgeIsNecessaryAlert=function(nextNode){
	var unnecessaryAlert=false;
	if(lbpm.globals.getThroughNodesCache) {
		var throughNodesCache;
		//#164904 获取未审批节点、表单可能存在改变值，不能取缓存数据
		lbpm.globals.getThroughNodes(function(){
			throughNodesCache = arguments[0];
		},null,null,false,lbpm.nowNodeId);
		//#164904 获取未审批节点、表单可能存在改变值，不能取缓存数据
		if(throughNodesCache){
			var throughNodesStr= lbpm.globals.getIdsByNodes(throughNodesCache);
			throughNodesStr=throughNodesStr+","
			//若为组节点内的节点，则取组节点
			if(nextNode.groupNodeId){
				if(throughNodesStr.indexOf(nextNode.groupNodeId+",") == -1){
					//分支计算到不通过该节点时不需要弹出必须修改该节点的提示
					unnecessaryAlert=true;
				}
			} else if(throughNodesStr.indexOf(nextNode.id+",") == -1){
				//分支计算到不通过该节点时不需要弹出必须修改该节点的提示
				unnecessaryAlert=true;
			}
		}
	}
	return !unnecessaryAlert;
};
// 检查多个即将流向节点是否被选上
function checkFutureNodeSelected() {
	if ($("input[name='futureNode']").length == 0  || lbpm.noValidateFutureNode == true)
		return true;
	if ($("input[name='futureNode']:checked").length > 0)
		return true;
	alert(lbpm.constant.CHKNEXTNODENOTNULL);
	// 标签页是否展开
	if(lbpm.approveType!="right"){
		var tab = LUI('process_review_tabcontent');
		if (tab != null) {
			if (!tab.isShow) {
				var panel = tab.parent;
				$.each(panel.contents, function(i) {
					if (this == tab) {
						//修复#105841 会议变更提交会报错
						try{
							panel.onToggle(i, false, false);
						}
						catch(e){
							
						}
						return false;
					}
				});
			}
		}
	}else{
		var $spreadBtn = $('#spreadBtn');
		if($spreadBtn.hasClass('spread')){
			$spreadBtn.click();
		}
	}
	//#45993 修复 新审批操作控件，未选分支的情况下点击提交，关闭弹出提示后，定位到流程处理处，而不是新审批操作控件
	if (!lbpm.globals.isNewAuditNoteSubimit && lbpm.approveType!="right"){
		$('html, body').animate({
			scrollTop: $("#descriptionRow").offset().top - 200
		}, 800); // scrollIntoView
	}
	lbpm.globals.isNewAuditNoteSubimit = false;
	return false;
}

// 检查即席子流程下一环节是否被选上
function checkAdHocSubFlowNodeSelected() {
	if ($("input[name='nextAdHocRouteId']").length == 0)
		return true;
	if ($("input[name='nextAdHocRouteId']:checked").length > 0)
		return true;
	alert(lbpm.constant.CHKNEXTNODENOTNULL);
	return false;
}

// 获得所有的节点
// startNodeId为开始查找的NodeId， endNodeId为结束查询的NodeId（若为空则为结束结点）
// exceptionXmlNodeNames:不包括的节点
function getAllNextNodeArray(startNodeId) {
	var rtnNodeArray = new Array();
	var startPush = false;
	if (!startNodeId)
		startPush = true;
	var throughtNodesFilter = function(throughtNodes) {
		$.each(throughtNodes, function(index, nodeObj) {
			if (startPush) {
				rtnNodeArray.push(nodeObj.id);
			}
			if (startNodeId && (nodeObj.id == startNodeId || (nodeObj.targetId && nodeObj.targetId == startNodeId))){
				startPush = true;
			}
		});
	};
	lbpm.globals.getThroughNodes(throughtNodesFilter, null, null, false, startNodeId);
	return rtnNodeArray;
}
// 判断节点为必须修改的节点，请设置该节点的处理人后再进行提交操作
function checkMustModifyHandlerNodeIds(nextNodeArray, operatorInfo) {
	var currentNodeObj = lbpm.globals.getCurrentNodeObj();
	var roleType = lbpm.constant.ROLETYPE;
	for (var i = 0; i < nextNodeArray.length; i++) {
		if ((roleType == '' || roleType == lbpm.constant.PROCESSORROLETYPE)
				&& lbpm.globals.checkModifyNodeAuthorization(currentNodeObj,
						nextNodeArray[i])) {
			var nextNode = lbpm.globals.getNodeObj(nextNodeArray[i]);
			if(nextNode.XMLNODENAME != "embeddedSubFlowNode"){
				if (nextNode.handlerIds == null || nextNode.handlerIds == "") {
					if (currentNodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL) {
						var currNodeNextHandlersId = lbpm.globals.getOperationParameterJson("currNodeNextHandlersId");
						var toRefuseThisNodeId = lbpm.globals.getOperationParameterJson("toRefuseThisNodeId");
						// 节点的最后处理人、非驳回返回本节点
						if (!currNodeNextHandlersId && !toRefuseThisNodeId
								&& (lbpm.operations[lbpm.currentOperationType].isPassType)) {
							if (lbpm.globals.judgeIsNecessaryAlert(nextNode)) {
								var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
								alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL);
								if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
									lbpm.globals.changeProcessorClick();
								}
								return false;
							}
						}
					} else if (lbpm.operations[lbpm.currentOperationType].isPassType) {
						if (lbpm.globals.judgeIsNecessaryAlert(nextNode)) {
							var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
							alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL);
							if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
								lbpm.globals.changeProcessorClick();
							}
							return false;
						}
					}
				}else{//如果处理人不空时，需要计算处理人，如果存在岗位成员为空或组织架构对象无效，则阻止提交并给出提示
					var names = _getNextNodeHandlerNames(nextNode);
					if(names=="" 
							|| names.indexOf("postEmpty")!=-1
							|| names.indexOf("elementDisabled")!=-1
							|| names.indexOf("parseError")!=-1){
						if (currentNodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL) {
							var currNodeNextHandlersId = lbpm.globals.getOperationParameterJson("currNodeNextHandlersId");
							var toRefuseThisNodeId = lbpm.globals.getOperationParameterJson("toRefuseThisNodeId");
							// 节点的最后处理人、非驳回返回本节点
							if (!currNodeNextHandlersId && !toRefuseThisNodeId
									&& (lbpm.operations[lbpm.currentOperationType].isPassType)) {
								if (lbpm.globals.judgeIsNecessaryAlert(nextNode)) {
									var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
									if (names.indexOf("elementDisabled")!=-1) {
										alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED);
										if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
											lbpm.globals.changeProcessorClick();
										}
										return false;
									}
									if (names.indexOf("postEmpty")!=-1) {
										alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY);
										if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
											lbpm.globals.changeProcessorClick();
										}
										return false;
									}
									if (names=="" && nextNode.ignoreOnHandlerEmpty != "true") {
										alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL);
										if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
											lbpm.globals.changeProcessorClick();
										}
										return false;
									}
									/*var flag = confirm(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEISNULL);
									if(flag){
										if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
											lbpm.globals.changeProcessorClick();
										}
										return false;
									}else{
										return true;
									}*/
								}
							}
						} else if (lbpm.operations[lbpm.currentOperationType].isPassType) {
							if (lbpm.globals.judgeIsNecessaryAlert(nextNode)) {
								var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
								if (names.indexOf("elementDisabled")!=-1) {
									alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED);
									if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
										lbpm.globals.changeProcessorClick();
									}
									return false;
								}
								if (names.indexOf("postEmpty")!=-1) {
									alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY);
									if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
										lbpm.globals.changeProcessorClick();
									}
									return false;
								}
								if (names=="" && nextNode.ignoreOnHandlerEmpty != "true") {
									alert(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL);
									if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
										lbpm.globals.changeProcessorClick();
									}
									return false;
								}
								/*var flag = confirm(nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEISNULL);
								if(flag){
									if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
										lbpm.globals.changeProcessorClick();
									}
									return false;
								}else{
									return true;
								}*/
							}
						}
						//==============

					}
				}
			}
		}
	}
	return true;
}

//如果处理人不空时，需要计算处理人
function _getNextNodeHandlerNames(nodeObj){
	var dataNextNodeHandler;
	var handlerIds = nodeObj.handlerIds==null?"":nodeObj.handlerIds;
	var nextNodeHandlerNames="";
	if(nodeObj.handlerSelectType){
		if (nodeObj.handlerSelectType=="formula") {
			dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else if (nodeObj.handlerSelectType=="matrix") {
			dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else if (nodeObj.handlerSelectType=="rule") {
			dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id, handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		} else {
			dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
		}
		for(var j=0;j<dataNextNodeHandler.length;j++){
			if(nextNodeHandlerNames==""){
				nextNodeHandlerNames=dataNextNodeHandler[j].parseResult;
			}else{
				nextNodeHandlerNames+=";"+dataNextNodeHandler[j].parseResult;
			}
		}
	}
	return nextNodeHandlerNames;
}

// 获取后续非分支节点中必须修改处理人的节点（用于支持计算最后一个节点才提示必须修改处理人）
function _getLinearMustModifyHandlerNodeIds(nodeIds, startNodeId, perLinearNodeIds) {
	if(!startNodeId) {
		return;
	}
	var nodeObj = lbpm.globals.getNodeObj(startNodeId);
	if(nodeObj) {
		if(lbpm.nowNodeId != startNodeId && nodeObj.mustModifyHandlerNodeIds != null && nodeObj.mustModifyHandlerNodeIds != ""
			&& nodeObj.ignoreOnHandlerEmpty == "false"){
			var mustModifyHandlerNodeIds = nodeObj.mustModifyHandlerNodeIds.split(";");
			for (var i = 0; i < mustModifyHandlerNodeIds.length; i++) {
				if(mustModifyHandlerNodeIds[i] && $.inArray(mustModifyHandlerNodeIds[i], perLinearNodeIds) == -1) {
					nodeIds.push(mustModifyHandlerNodeIds[i]);
				}
			}
		}
		perLinearNodeIds.push(startNodeId);
		if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SPLIT,nodeObj) && nodeObj.relatedNodeIds) { // 并发分支
			_getLinearMustModifyHandlerNodeIds(nodeIds, nodeObj.relatedNodeIds, perLinearNodeIds);
		} else {
			var nextNodeObjs = lbpm.globals.getNextNodeObjs(nodeObj.id);
			
			//2个以上的人工决策节点，可能有些后续节点路线未覆盖到，也必须考虑 洪健 add 2020-4-3
            if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,nodeObj)) { // 人工分支节点
                for(var z=0;z< nextNodeObjs.length;z++){
                    if($.inArray(nextNodeObjs[z].id, perLinearNodeIds) == -1&&nextNodeObjs[z].id!=lbpm.nowNodeId){
                        _getLinearMustModifyHandlerNodeIds(nodeIds, nextNodeObjs[z].id, perLinearNodeIds);
                    }
                }
            }
            
			if(nextNodeObjs.length == 1) {
				_getLinearMustModifyHandlerNodeIds(nodeIds, nextNodeObjs[0].id, perLinearNodeIds);
			}
		}
	}
}
function getAllNextNodeArrayExclude(nodeId) {
	var rtnNodeArray = [];
	var allNextNodeArray = getAllNextNodeArray(nodeId);
	
	var linearMustModifyHandlerNodeIds = []; // 后续节点必需修改的节点
	var perLinearNodeIds = []; // 当前节点往下遍历到第一个分支之间的节点
	_getLinearMustModifyHandlerNodeIds(linearMustModifyHandlerNodeIds, nodeId, perLinearNodeIds);
	
	for(var i = 0; i < allNextNodeArray.length; i++){
		var found = false;
		for(var j = 0; j < linearMustModifyHandlerNodeIds.length; j++) {
			if(allNextNodeArray[i] == linearMustModifyHandlerNodeIds[j]) {
				found = true;
				break;
			}
		}
		if(!found){
			rtnNodeArray.push(allNextNodeArray[i]);
		}
	}
	return rtnNodeArray;
}
function checkFutureNodeObjs() {
	if(lbpm.noValidateFutureNode == true)
		return true;
	var checkedNode = null;
	var futureNodeObjs = document.getElementsByName("futureNode");
	for (var i = 0; i < futureNodeObjs.length; i++) {
		var futureNodeObj = futureNodeObjs[i];
		if (futureNodeObj.checked) {
			checkedNode = lbpm.globals.getNodeObj(futureNodeObj.value);
			var handlerIdsObj = document.getElementsByName("handlerIds["
					+ futureNodeObj.getAttribute("index") + "]")[0];
			if(handlerIdsObj == null){//兼容移动端
				handlerIdsObj = document.getElementsByName("handlerIds_"
						+ futureNodeObj.value)[0];
			}
			if (handlerIdsObj != null && handlerIdsObj.value == ""
					&& checkedNode && checkedNode.ignoreOnHandlerEmpty == "false") {
				alert(lbpm.constant.VALIDATENEXTNODEHANDLERISNULL);
				
				//#29151 优化 选择即将流向之后，如果没有选择比修改处理人，直接给出提示信息，点击确定后，给出弹框选择，现在操作步骤太过繁琐
				if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
					lbpm.globals.changeProcessorClick();
				}

				return false;
			}
			//即将流向多选时，此时break会只校验到第一个
			//break;
		}
	}
	
	var allNextNodeArray = [];
	if(checkedNode && checkedNode.id) {
		if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,checkedNode)) {
			return true;
		}
		allNextNodeArray = getAllNextNodeArrayExclude(checkedNode.id);
		//即将流向是需要人工进行选择的时候，需要将选择的id加入到数组中，参加处理人空岗的判断
		allNextNodeArray.unshift(checkedNode.id);
	} else {
		allNextNodeArray = getAllNextNodeArrayExclude(lbpm.nowNodeId);
	}
	
	// 检查未选中的节点是否是被选中节点的后续节点
	for (var i = 0; i < futureNodeObjs.length; i++) {
		var futureNodeObj = futureNodeObjs[i];
		if (futureNodeObj.checked) {
			continue;
		}

		for (var j = 0; j < allNextNodeArray.length; j++) {
			var furtureFromSelectedNodeId = allNextNodeArray[j];
			if (futureNodeObj.value == furtureFromSelectedNodeId) {
				var handlerIdsObj = document.getElementsByName("handlerIds["
						+ futureNodeObj.getAttribute("index") + "]")[0];
				var furtureFromSelectedNodeObj = lbpm.globals.getNodeObj(futureNodeObj.value);
				if (handlerIdsObj != null && handlerIdsObj.value == ""
						&& furtureFromSelectedNodeObj && furtureFromSelectedNodeObj.ignoreOnHandlerEmpty == "false") {
					var fn = WorkFlow_getLangLabel(furtureFromSelectedNodeObj.name,furtureFromSelectedNodeObj["langs"],"nodeName");
					var cn = WorkFlow_getLangLabel(checkedNode.name,checkedNode["langs"],"nodeName");
					alert(lbpm.constant.FLOWCONTENTMUSTMODIFYNODENEXTHANDLER
							.replace('{0}', furtureFromSelectedNodeObj.id)
							.replace('{1}', fn)
							.replace('{2}', checkedNode.id).replace('{3}', cn));

					//#29151 优化 选择即将流向之后，如果没有选择比修改处理人，直接给出提示信息，点击确定后，给出弹框选择，现在操作步骤太过繁琐
					if (!lbpm.address.is_pda() && lbpm.globals.changeProcessorClick) {
						lbpm.globals.changeProcessorClick();
					}

					return false;
				}
			}
		}
	}

	var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
	// 判断节点为必须修改的节点
	if (!checkMustModifyHandlerNodeIds(allNextNodeArray, operatorInfo))
		return false;
	return true;
}

var hasIdentityValidate = false;

// 判断是否需要身份校验
function checkIdentityValidation() {
	if(hasIdentityValidate==true){
		return true;
	}
	var currentNode = lbpm.globals.getCurrentNodeObj();
	if(currentNode){
		extAttributes = currentNode.extAttributes;
		if(extAttributes && extAttributes.length>0){
			for(var i=0;i<extAttributes.length;i++){ 
				attr = extAttributes[i];
				if(attr.name.indexOf("identity_")==0){
					if(attr.value=="true"){
						return false;
					}
				}
			}
		}
	}
	return true;
}

function getIdentityValidationTypes() {
	var validateTypes = "";
	var currentNode = lbpm.globals.getCurrentNodeObj();
	if(currentNode){
		extAttributes = currentNode.extAttributes;
		if(extAttributes && extAttributes.length>0){
			for(var i=0;i<extAttributes.length;i++){ 
				attr = extAttributes[i];
				if(attr.name.indexOf("identity_")==0){
					if(attr.value=="true"){
						validateTypes+=attr.name.substring(9)+";";
					}
				}
			}
		}
	}
	return validateTypes;
}

if(window.LUI){
	LUI.ready(function(){
		seajs.use(['sys/authentication/identity/js/auth'], function(auth) {
			window.identityValidate = function(){
					var config = {};
					var modelPaths = document.getElementsByName("modelPath");
					var modelPath = "lbpm";
					if(modelPaths && modelPaths.length>0){
						modelPath = modelPaths[0];
					}
					config.modelPath = modelPath;
					config.modelName = lbpm.modelName;
					config.modelId = lbpm.modelId;
					config.callback = identityValidate_calback;
					config.validateType = getIdentityValidationTypes();
					auth.validate(config);
				};
				
		 	window.identityValidate_calback = function(result,params,msg){
		 		if(result){
		 			hasIdentityValidate = true;
		 			var review_button = document.getElementById("process_review_ui_button");
		 			if(!review_button){
		 				review_button = document.getElementById("process_review_button");
		 			}
		 			Com_Parameter.isSubmit = false;
		 			review_button.click();
		 		}
		 	}
		});
	}); 
}

})(lbpm);