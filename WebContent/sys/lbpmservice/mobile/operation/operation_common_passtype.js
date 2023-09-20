define(["sys/lbpmservice/mobile/common/syslbpmprocess_nodes_filter","mui/dialog/Tip","dijit/registry", "sys/authentication/identity/js/mobile/auth"],function(nodesFilter, tip, registry, auth){
	var common={};
	
	// 检查多个即将流向节点是否被选上
	var checkFutureNodeSelected = function() {
		if ($("input[name='futureNode']").length == 0  || lbpm.noValidateFutureNode == true)
			return true;
		//如果有分支设置分支
		var futureNodeCheckboxs=$("input[name='futureNode'][type='checkbox']");
		//判断是并行分支还是人工决策分支（如checkbox类型则是并行分支）
		if(futureNodeCheckboxs.length>0){
			var futureNodesChekBoxGroup;
			require(['dojo/topic','dijit/registry'],function(topic,registry){
				futureNodesChekBoxGroup=registry.byId("sys_lbpmservice_mobile_workitem_FutureNodesChekBoxGroup");
			});
			if(futureNodesChekBoxGroup.value&&futureNodesChekBoxGroup.value!="")
				return true;
		}
		else{
			if ($("input[name='futureNode']:checked").length > 0)
				return true;
		}
		tip["warn"]({text:lbpm.constant.CHKNEXTNODENOTNULL});
		//alert(lbpm.constant.CHKNEXTNODENOTNULL);
		return false;
	};
	
	// 检查即席子流程下一环节是否被选上
	var checkAdHocSubFlowNodeSelected = function() {
		if ($("input[name='nextAdHocRouteId']").length == 0)
			return true;
		if ($("input[name='nextAdHocRouteId']:checked").length > 0)
			return true;
		tip["warn"]({text:lbpm.constant.CHKNEXTNODENOTNULL});
		//alert(lbpm.constant.CHKNEXTNODENOTNULL);
		return false;
	};
	
	// 获得所有的节点
	// startNodeId为开始查找的NodeId， endNodeId为结束查询的NodeId（若为空则为结束结点）
	// exceptionXmlNodeNames:不包括的节点
	var getAllNextNodeArray = function(startNodeId) {
		var rtnNodeArray = new Array();
		var startPush = false;
		if (!startNodeId)
			startPush = true;
		var throughtNodesFilter = function(throughtNodes) {
			$.each(throughtNodes, function(index, nodeObj) {
				if (startPush){
					rtnNodeArray.push(nodeObj.id);
				}
				if (startNodeId && (nodeObj.id == startNodeId || (nodeObj.targetId && nodeObj.targetId == startNodeId))){
					startPush = true;
				}
			});
		};
		lbpm.globals.getThroughNodes(throughtNodesFilter, null, null, false, startNodeId);
		return rtnNodeArray;
	};
	
	// 判断节点为必须修改的节点，请设置该节点的处理人后再进行提交操作
	var checkMustModifyHandlerNodeIds=function(nextNodeArray, operatorInfo) {
		var currentNodeObj = lbpm.globals.getCurrentNodeObj();
		var roleType = lbpm.constant.ROLETYPE;
		for (var i = 0; i < nextNodeArray.length; i++) {
			if (roleType == ''
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
								if (lbpm.globals.judgeIsNecessaryAlert(nextNode)&& nextNode.ignoreOnHandlerEmpty != "true") {
									var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
									tip["warn"]({text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL});
									//alert(nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL);
									return false;
								}
							}
						} else if (lbpm.operations[lbpm.currentOperationType].isPassType) {
							if (lbpm.globals.judgeIsNecessaryAlert(nextNode)&& nextNode.ignoreOnHandlerEmpty != "true") {
								var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
								tip["warn"]({text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL});
								//alert(nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL);
								return false;
							}
						}
					} else {//如果处理人不空时，需要计算处理人，如果存在岗位成员为空或组织架构对象无效，则阻止提交并给出提示
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
											//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED,width:'260',height:'150'});
											tip["warn"]({text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED,width:'260',height:'150'});
											return false;
										}
										if (names.indexOf("postEmpty")!=-1) {
											//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY,width:'260',height:'150'});
											tip["warn"]({text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY,width:'260',height:'150'});
											return false;
										}
										if (names=="" && nextNode.ignoreOnHandlerEmpty != "true") {
											//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL,width:'260',height:'150'});
											tip["warn"]({text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL,width:'260',height:'150'});
											return false;
										}
									}
								}
							} else if (lbpm.operations[lbpm.currentOperationType].isPassType) {
								if (lbpm.globals.judgeIsNecessaryAlert(nextNode)) {
									var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
									if (names.indexOf("elementDisabled")!=-1) {
										//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED,width:'260',height:'150'});
										tip["warn"]({text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED,width:'260',height:'150'});
										return false;
									}
									if (names.indexOf("postEmpty")!=-1) {
										//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY,width:'260',height:'150'});
										tip["warn"]({text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY,width:'260',height:'150'});
										return false;
									}
									if (names=="" && nextNode.ignoreOnHandlerEmpty != "true") {
										//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL,width:'260',height:'150'});
										tip["warn"]({text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL,width:'260',height:'150'});
										return false;
									}
								}
							}
							//==============

						}
					}
				}
			}
		}
		return true;
	};
	//如果处理人不空时，需要计算处理人
	window._getNextNodeHandlerNames = function(nodeObj){
		var dataNextNodeHandler;
		var handlerIds = nodeObj.handlerIds==null?"":nodeObj.handlerIds;
		var nextNodeHandlerNames="";
		if(nodeObj.handlerSelectType){
			if (nodeObj.handlerSelectType=="formula") {
				dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),undefined,nodeObj.id);
			} else if (nodeObj.handlerSelectType=="matrix") {
				dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),undefined,nodeObj.id);
			} else if (nodeObj.handlerSelectType=="rule") {
				dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id, handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
			} else {
				dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),undefined,nodeObj.id);
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
	 var _getLinearMustModifyHandlerNodeIds= function(nodeIds, nodeId, perLinearNodeIds) {
		if(!nodeId) {
			return;
		}
		var nodeObj = lbpm.globals.getNodeObj(nodeId);
		if(nodeObj) {
			if(lbpm.nowNodeId != nodeId && nodeObj.mustModifyHandlerNodeIds != null && nodeObj.mustModifyHandlerNodeIds != ""
				&& nodeObj.ignoreOnHandlerEmpty == "false"){
				var mustModifyHandlerNodeIds = nodeObj.mustModifyHandlerNodeIds.split(";");
				for (var i = 0; i < mustModifyHandlerNodeIds.length; i++) {
					if(mustModifyHandlerNodeIds[i] && $.inArray(mustModifyHandlerNodeIds[i], perLinearNodeIds) == -1) {
						nodeIds.push(mustModifyHandlerNodeIds[i]);
					}
				}
			}
			perLinearNodeIds.push(nodeId);
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
	};
	var  getAllNextNodeArrayExclude = window.getAllNextNodeArrayExclude = function(nodeId) {
		var rtnNodeArray = [];
		var allNextNodeArray = getAllNextNodeArray(nodeId);
		var linearMustModifyHandlerNodeIds = [];
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
	};
	var checkFutureNodeObjs=function() {
		if(lbpm.noValidateFutureNode == true)
			return true;
		var checkedNode = null;
		var futureNodeObjs = document.getElementsByName("futureNode");
		for (var i = 0; i < futureNodeObjs.length; i++) {
			var futureNodeObj = futureNodeObjs[i];
			//这里加个组件checked的判断，是解决dom没checked的情况
			var futureNodeWgt = registry.byNode(futureNodeObj);
			if (futureNodeObj.checked || (futureNodeWgt && futureNodeWgt.checked)) {
				checkedNode = lbpm.globals.getNodeObj(futureNodeObj.value);
				var handlerIdsObj = document.getElementsByName("handlerIds["
						+ futureNodeObj.getAttribute("index") + "]")[0];
				if(handlerIdsObj == null){//兼容移动端
					handlerIdsObj = document.getElementsByName("handlerIds_"
							+ futureNodeObj.value)[0];
				}
				if (handlerIdsObj != null && handlerIdsObj.value == ""
						&& checkedNode && checkedNode.ignoreOnHandlerEmpty == "false") {
					tip["warn"]({text:lbpm.constant.VALIDATENEXTNODEHANDLERISNULL});
					//alert(lbpm.constant.VALIDATENEXTNODEHANDLERISNULL);
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
						var fn= WorkFlow_getLangLabel(furtureFromSelectedNodeObj.name,furtureFromSelectedNodeObj["langs"],"nodeName");
						var cn= WorkFlow_getLangLabel(checkedNode.name,checkedNode["langs"],"nodeName");
						tip["warn"]({text:lbpm.constant.FLOWCONTENTMUSTMODIFYNODENEXTHANDLER
							.replace('{0}', furtureFromSelectedNodeObj.id)
							.replace('{1}', fn)
							.replace('{2}', checkedNode.id).replace('{3}', cn)});
						/*alert(lbpm.constant.FLOWCONTENTMUSTMODIFYNODENEXTHANDLER
								.replace('{0}', furtureFromSelectedNodeObj.id)
								.replace('{1}', fn)
								.replace('{2}', checkedNode.id).replace('{3}', cn));*/
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
	};
	
	//通过类型的操作的提交前检测
	common.common_operationCheckForPassType = lbpm.globals.common_operationCheckForPassType = function() {
		if (!checkFutureNodeSelected()) {
			return false;
		} else if (!checkFutureNodeObjs()) {
			return false;
		} else if (!checkAdHocSubFlowNodeSelected()) {
			return false;
		} else if(!checkIdentityValidation()){
			//弹出身份校验框
			var element = registry.byId('process_review_button');
			element.setDisabled(true);
			identityValidate();
			return false;
		}
		return true;
	};
	
	//判断是否需要弹出“必须修改该节点的提示” #作者：曹映辉 #日期：2012年7月4日 
	common.judgeIsNecessaryAlert=lbpm.globals.judgeIsNecessaryAlert=function(nextNode){
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
				throughNodesStr=throughNodesStr+",";
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
	}; 
	var element = registry.byId('process_review_button');
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
 		var element = registry.byId('process_review_button');
 		element.setDisabled(false);
 		if(result){
 			hasIdentityValidate = true;
 			Com_Parameter.isSubmit = false;
 			element.onClick();
 		}
 		
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


	return common;
});
