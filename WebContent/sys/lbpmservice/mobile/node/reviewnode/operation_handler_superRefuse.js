define(['dojo/query', "dojo/store/Memory", "dijit/registry", "dojo/request", "dojo/_base/array", "dojo/dom", "dojo/dom-prop", "dojo/dom-construct", "mui/dialog/Tip"],
	function(query, Memory, registry, request, array, dom, domProp, domConstruct, tip) {
		lbpm.operations['handler_superRefuse'] = {
			click : OperationClick,
			check : OperationCheck,
			blur : OperationBlur,
			setOperationParam : setOperationParam
		};
		
		function OperationBlur() {
			lbpm.globals.clearDefaultUsageContent('handler_superRefuse');
		}
		
		function OperationClick(operationName) {
			lbpm.globals.setDefaultUsageContent('handler_superRefuse');
			var operationsRow = document.getElementById("operationsRow");
			var operationsTDTitle = document.getElementById("operationsTDTitle");
			operationsTDTitle.innerHTML = lbpm.workitem.constant.handlerOperationTypeRefuse.replace("{refuse}", operationName);
	
			// 增加驳回节点重复过滤
			var currNodeInfo = lbpm.globals.getCurrentNodeObj();
			var currNodeId = currNodeInfo.id;
			
			var extAttrs=lbpm.nodes[lbpm.nowNodeId].extAttributes;
			var refuseTypes = [];
			var index = 0;
			var onlyRefusePassedToTheNode = false;
			for(var i = 0;extAttrs && i < extAttrs.length;i++){
				if(extAttrs[i].name == 'refuse_types'){
					refuseTypes=extAttrs[i].value.split(";");
					break;
				}
			}
			var url = Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp";
			var pjson = {
				"s_bean" : "lbpmRefuseRuleDataBean",
				"processId" : query("[name='sysWfBusinessForm.fdProcessId']").val(),
				"nodeId" : currNodeId,
				"_d" : new Date().toString(),
				"refuseType" : "superRefuse"
			};
			var check_pjson = {
				"s_bean" : "lbpmRefuseRuleDataBean",
				"processId" : query("[name='sysWfBusinessForm.fdProcessId']").val(),
				"nodeId" : currNodeId,
				"_d" : new Date().toString()
			};
			
			//驳回的节点是否在分支内
			lbpm.globals._isJumpNodeInJoin = false;
	
			request.post(url, {
						handleAs : 'json',
						data : pjson
					}).then(
						function(passNodeArray) {
							return request.post(url, {
										handleAs : 'json',
										data : check_pjson
									}).then(
										function(check_passNodeArray) {
											return [passNodeArray, check_passNodeArray];
										});
						}).then(
							function(nodeArrays) {
								var passNodeArray = nodeArrays[0], check_passNodeArray = nodeArrays[1];
	
								if (passNodeArray && passNodeArray.length) {
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
									
									var checkIsInJoin = function(nodeValue) {
										return array.some(check_passNodeArray,
											function(v) {
												return v == nodeValue;
											});
									};
									var html = '<div id="jumpToNodeIdSelectObj" data-dojo-type="mui/form/Select" key="jumpToNodeId" '
											+ 'data-dojo-props="validate:\'jumpToNodeRequired required\',required:true,name:\'jumpToNodeIdSelectObj\', value:\'\', mul:false" ></div>';
									
									//增加驳回会审
							        html +='<div id="_super_triageHandler"></div>';
							        
									lbpm.rejectOptionsEnabled = true; // 驳回选项开关是否开启标识
									if (Lbpm_SettingInfo && (Lbpm_SettingInfo["isShowRefuseOptonal"] === "false")) {
										lbpm.rejectOptionsEnabled = false;
									}
									if (lbpm.rejectOptionsEnabled){

										var rejectOptionsCheckedIndex = 0;
										//模板勾选 驳回节点通过后，返回我
										if(lbpm.flowcharts && lbpm.flowcharts.rejectReturn == "true"){
											rejectOptionsCheckedIndex = 1;
										}

										if(showOption('refusePassedToSequenceFlowNodeLabel',refuseTypes)){
											html += '<div class="refusePassedToSequenceFlowNode refuseNode">'
											html += '<div id="refusePassedToSequenceFlowNode" alertText="" key="refusePassedToSequenceFlowNode" data-dojo-type="mui/form/CheckBox"'
											html += " data-dojo-props=\"name:'refusePassedToSequenceFlowNode', value:'true', mul:false, text:'"
											html += lbpm.workitem.constant.handlerOperationTypeRefuse_sequenceFlow.replace("{refuse}", operationName) + "'";
											html += ",checked:" + (index == rejectOptionsCheckedIndex ? true : false);
											html += '"></div></div>';
											index += 1;
										}
										//
										if(showOption('refusePassedToThisNodeLabel',refuseTypes)){
											html += '<div id="refusePassedToThisNodePane" class="refusePassedToThisNode refuseNode">';
											html += '<div id="refusePassedToThisNode" alertText="" key="refusePassedToThisNode" data-dojo-type="mui/form/CheckBox"';
											html += ' data-dojo-props="name:\'refusePassedToThisNode\', value:\'true\', mul:false, text:\'';
											html += lbpm.workitem.constant.handlerOperationTypeRefuse_returnBackMe.replace("{refuse}", operationName) + '\'';
											html += ",checked:" + (index == rejectOptionsCheckedIndex ? true : false);
											html += '"></div></div>';
											index += 1;
										}
										//增加驳回返回本节点，add by wubing date:2016-07-29
										if(showOption('refusePassedToThisNodeOnNodeLabel',refuseTypes)){
											html += '<div id="refusePassedToThisNodeOnNodePane" class="refusePassedToThisNodeOnNode refuseNode" ';
											
											html += '>';
											html += '<div id="refusePassedToThisNodeOnNode" alertText="" key="refusePassedToThisNodeOnNode" data-dojo-type="mui/form/CheckBox"';
											html += ' data-dojo-props="name:\'refusePassedToThisNodeOnNode\', value:\'true\', mul:false, text:\'';
											html += lbpm.workitem.constant.handlerOperationTypeRefuse_returnBack.replace("{refuse}", operationName) + '\'';
											html += ",checked:" + (index == rejectOptionsCheckedIndex ? true : false);
											html += '"></div></div>';
											index += 1;
										}
										//增加驳回返回指定节点
										if(showOption('refusePassedToTheNodeLabel',refuseTypes)){
											html += '<div id="refusePassedToTheNodePane" class="refusePassedToTheNode refuseNode"';
											
											html += '>';
											html += '<div id="refusePassedToTheNode" alertText="" key="refusePassedToTheNode" data-dojo-type="mui/form/CheckBox"';
											html += ' data-dojo-props="name:\'refusePassedToTheNode\', value:\'true\', mul:false, text:\'';
											html += lbpm.workitem.constant.handlerOperationTypeRefuse_returnBackTheNode.replace("{refuse}", operationName) + '\'';
											html += ",checked:" + (index == rejectOptionsCheckedIndex ? true : false);
											if(index == 0)
												onlyRefusePassedToTheNode = true;
											html += '"></div></div>';
											html += '<div id="returnBackToNodeIdSelectObj" data-dojo-type="mui/form/Select" key="returnBackToNodeId" '
												+ 'data-dojo-props="name:\'returnBackToNodeIdSelectObj\', value:\'\', mul:false" style="display:none"></div>';
											html += '<div id="noReturnBackNodeLabel" style="display:none">'+lbpm.workitem.constant.noReturnBackNode+'</div>';
											index += 1;
										}
									}
									// 驳回后流经的子流程重新流转选项html
									var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
									if (isPassedSubprocessNode) {
										html += "<div class='isRecoverPassedSubprocessLabel refuseNode'>";
										html += '<div id="isRecoverPassedSubprocess" alertText="" key="isRecoverPassedSubprocess" data-dojo-type="mui/form/CheckBox"';
										html += ' data-dojo-props="name:\'isRecoverPassedSubprocess\', value:\'true\', mul:false, text:\'';
										html += lbpm.workitem.constant.handlerOperationTypeRefuse_abandonSubprocess.replace("{refuse}", operationName) + '\'';
										html += '"></div></div>';
									}
									
									var nodeHandlerNameArray = []; // 可驳回到的节点的处理人名称信息集合
									nodeHandlerNameArray = _getPassNodeHandlerName(passNodeArray, true);
									var data = [];
									for (var i = 0; i < passNodeArray.length; i++) {
										var nodeInfo = lbpm.nodes[passNodeArray[i]];
										var langNodeName = WorkFlow_getLangLabel(nodeInfo.name,nodeInfo["langs"],"nodeName");
										var itemShowStr = (lbpm.globals.lbpmIsRemoveNodeIdentifier() || lbpm.globals.lbpmIsHideAllNodeIdentifier()) ? langNodeName : (nodeInfo.id + "." + langNodeName);
										itemShowStr += nodeHandlerNameArray[nodeInfo.id];
										data.push({
											text : itemShowStr,
											value : passNodeArray[i]
										});
									}
									
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
															//只配置了返回指定节点一个选项时，需要构造下拉框
															if(onlyRefusePassedToTheNode){
																var jumpToNodeIdSelectObj = registry.byId('jumpToNodeIdSelectObj');
																jumpToNodeIdSelectObj.setStore(new Memory(
																	{
																		data : data
																	}));
																if (!hasDefaultRefuse && Lbpm_SettingInfo && Lbpm_SettingInfo["isRefuseToPrevNodeDefault"] == "true") {
																	jumpToNodeIdSelectObj.set("value", passNodeArray[passNodeArray.length - 1]);
																} else {
																	jumpToNodeIdSelectObj.set("value", passNodeArray[indexNode]);
																}
																
																if(passNodeArray.length==1){
												                	  _trialStaffPeopleHtmlFunc(passNodeArray[passNodeArray.length - 1]);
												                }
																lbpm.globals.buildSuperReturnBackToNodeIdSelectOption(jumpToNodeIdSelectObj);
															}
															if (!hasDefaultRefuse && Lbpm_SettingInfo && Lbpm_SettingInfo["isRefuseToPrevNodeDefault"] == "true") {
																jumpToNodeIdSelectObj.set("value", passNodeArray[passNodeArray.length - 1]);
																 _trialStaffPeopleHtmlFunc(passNodeArray[passNodeArray.length - 1]);
															} else {
																jumpToNodeIdSelectObj.set("value", passNodeArray[indexNode]);
																 _trialStaffPeopleHtmlFunc(passNodeArray[indexNode]);
															}
											                
															
															// <----------以下为驳回返回选项相关的逻辑处理，只有在驳回选项开关开启的情况下才会执行---------->
															if (lbpm.rejectOptionsEnabled) {
																// 驳回后重新流转
																var refusePassedToSequenceFlowNode = registry.byId("refusePassedToSequenceFlowNode");
												                var refusePassedToSequenceFlowNodePane = query(".refusePassedToSequenceFlowNode");
																// 驳回返回本人
																var refusePassedToThisNode = registry.byId('refusePassedToThisNode');
																var refusePassedToThisNodePane = query('#refusePassedToThisNodePane');
																// 驳回返回本节点
																var refusePassedToThisNodeOnNode = registry.byId('refusePassedToThisNodeOnNode');
																var refusePassedToThisNodeOnNodePane = query('#refusePassedToThisNodeOnNodePane');
																// 驳回返回指定节点
																var refusePassedToTheNode = registry.byId('refusePassedToTheNode');
																var refusePassedToTheNodePane = query('#refusePassedToTheNodePane');

																// 驳回的节点是否在分支内
																lbpm.globals._isJumpNodeInJoin = checkIsInJoin(jumpToNodeIdSelectObj.value);
																if (!lbpm.globals._isJumpNodeInJoin) {
																	// 分支外，则隐藏驳回本人和驳回本节点的开关
																	if(refusePassedToThisNode){
																		refusePassedToThisNode.set("checked",false);
																		refusePassedToThisNodePane.style("display", 'none');
																	}
																	if(refusePassedToThisNodeOnNode){
																		refusePassedToThisNodeOnNode.set("checked",false);
																		refusePassedToThisNodeOnNodePane.style("display", 'none');
																	}	
																	
																	refusePassedToTheNodePane.style("display", '');
																}
																
																// #102686 驳回返回后重新流转 zhanlei
																if(refusePassedToSequenceFlowNode){
																	refusePassedToSequenceFlowNode.watch("checked", function(
																			name,
																			old,
																			val
																		) {
																			// #102686 保证单选，不允许单独取消
																		if(val) {
																			if(refusePassedToThisNode)
																				refusePassedToThisNode.set("checked", false);
																			if(refusePassedToThisNodeOnNode)
																				refusePassedToThisNodeOnNode.set("checked", false);
																			if(refusePassedToTheNode)
																				refusePassedToTheNode.set("checked", false);
																		} else {
																			var flag = false;
																			if(refusePassedToThisNode && refusePassedToThisNode.checked)
																				flag = true;
																			if(refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked)
																				flag = true;
																			if(refusePassedToTheNode && refusePassedToTheNode.checked)
																				flag = true;
																			if(!flag) {
																				refusePassedToSequenceFlowNode.set("checked", true);
																			}
																		}

																		var isRecoverPassedSubprocess = registry.byId("isRecoverPassedSubprocess");
																		if (isRecoverPassedSubprocess) {
																			if (this.checked) {
																				isRecoverPassedSubprocess.set("checked", false)
																				query(".isRecoverPassedSubprocessLabel").style(
																					"display",
																					""
																				)
																			} else {
																				isRecoverPassedSubprocess.set("checked", false)
																				query(".isRecoverPassedSubprocessLabel").style(
																					"display",
																					"none"
																				)
																			}
																		}
																	});
																}
																
																
																// 驳回返回选项变更监听
																if(refusePassedToThisNode){
																	refusePassedToThisNode.watch('checked',function(name, old, val) {
																		// #102686 保证单选，不允许单独取消
																		if(val) {
																			if(refusePassedToThisNodeOnNode)
																				refusePassedToThisNodeOnNode.set("checked", false);
																			if(refusePassedToTheNode)
																				refusePassedToTheNode.set("checked", false);
																			if(refusePassedToSequenceFlowNode)
																				refusePassedToSequenceFlowNode.set("checked", false);
													                      } else {
													                    	  var flag = false;
													                    	  if(refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked)
													                    		  flag = true;
													                    	  if(refusePassedToTheNode && refusePassedToTheNode.checked)
													                    		  flag = true;
													                    	  if(refusePassedToSequenceFlowNode && refusePassedToSequenceFlowNode.checked)
													                    		  flag = true;
													                     	 if(!flag) {
													                     		refusePassedToThisNode.set("checked", true);
													                     	 }
													                      }

																		var isRecoverPassedSubprocess = registry.byId('isRecoverPassedSubprocess');
																		if (isRecoverPassedSubprocess) {
																			if (val) {
																				isRecoverPassedSubprocess.set("checked", false);
																				query('.isRecoverPassedSubprocessLabel').style("display", 'none');
																			} else {
																				isRecoverPassedSubprocess.set("checked", false);
																				query('.isRecoverPassedSubprocessLabel').style('display', '');
																			}
																		}
																	});
																}
																if(refusePassedToThisNodeOnNode){
																	refusePassedToThisNodeOnNode.watch('checked',function(name, old, val) {
																		// #102686 保证单选，不允许单独取消
													                     if(val) {
													                    	 if(refusePassedToThisNode)
													                    		 refusePassedToThisNode.set("checked", false);
													                    	 if(refusePassedToTheNode)
													                    		 refusePassedToTheNode.set("checked", false);
													                    	 if(refusePassedToSequenceFlowNode)
													                    		 refusePassedToSequenceFlowNode.set("checked", false);
													                      } else {
													                    	  var flag = false;
													                    	  if(refusePassedToThisNode && refusePassedToThisNode.checked)
													                    		  falg = true;
													                    	  if(refusePassedToTheNode && refusePassedToTheNode.checked)
													                    		  flag = true;
													                    	  if(refusePassedToSequenceFlowNode && refusePassedToSequenceFlowNode.checked)
													                    		  flag  =true;
													                      	 if(!flag) {
													                      		refusePassedToThisNodeOnNode.set("checked", true);
													                      	 }
													                      }

																		var isRecoverPassedSubprocess = registry.byId('isRecoverPassedSubprocess');
																		if (isRecoverPassedSubprocess) {
																			if (val) {
																				isRecoverPassedSubprocess.set("checked", false);
																				query('.isRecoverPassedSubprocessLabel').style("display", 'none');
																			} else {
																				isRecoverPassedSubprocess.set("checked", false);
																				query('.isRecoverPassedSubprocessLabel').style('display', '');
																			}
																		}
																	});
																}
																if(refusePassedToTheNode){
																	refusePassedToTheNode.watch('checked', function(name, old, val) {
																		// #102686 保证单选，不允许单独取消
												                        if(val) {
												                        	if(refusePassedToThisNode)
												                        		refusePassedToThisNode.set("checked", false);
												                        	if(refusePassedToSequenceFlowNode)
												                        		refusePassedToSequenceFlowNode.set("checked", false);
												                        	if(refusePassedToThisNodeOnNode)
												                        		refusePassedToThisNodeOnNode.set("checked", false);
												                        } else {
												                        	var flag = false;
												                        	if(refusePassedToThisNode && refusePassedToThisNode.checked)
												                        		flag = true;
												                        	if(refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked)
												                        		flag = true;
												                        	if(refusePassedToSequenceFlowNode && refusePassedToSequenceFlowNode.checked)
												                        		flag = true;
												                          	 if(!flag) {
												                          		refusePassedToTheNode.set("checked", true);
												                          		return;
												                          	 }
												                        }
												                        
																	
																		if (val) {
																			lbpm.globals.buildSuperReturnBackToNodeIdSelectOption(jumpToNodeIdSelectObj);
																		} else {
																			query('#returnBackToNodeIdSelectObj').style("display",'none');
																			query('#noReturnBackNodeLabel').style('display','none');
																		}

																		var isRecoverPassedSubprocess = registry.byId('isRecoverPassedSubprocess');
																		if (isRecoverPassedSubprocess) {
																			if (val) {
																				isRecoverPassedSubprocess.set("checked", false);
																				query('.isRecoverPassedSubprocessLabel').style("display", 'none');
																			} else {
																				isRecoverPassedSubprocess.set("checked", false);
																				query('.isRecoverPassedSubprocessLabel').style('display', '');
																			}
																		}
																	});
																}
																

																// 驳回节点变更监听
																jumpToNodeIdSelectObj.on("attrmodified-value", function(evt) {
																	var val = evt.detail.newValue;
																	lbpm.globals._isJumpNodeInJoin = checkIsInJoin(val);
																	/** 切换时驳回选项的相关规则：
																	 *  1、若切换之前勾选了驳回返回指定节点，则切换的时候自动去掉勾选；
																	 *  2、若切换之前勾选了是驳回返回本人和驳回放回本节点，则在切换后的节点（分支内）有驳回返回本人和驳回放回本节点选项时，继承切换前的勾选；
																	 */
																	if (!lbpm.globals._isJumpNodeInJoin) {
																		// 分支外，则去掉勾选且隐藏驳回本人和驳回本节点的开关
																		if(refusePassedToThisNode){
																			refusePassedToThisNode.set("checked",false);
																			refusePassedToThisNodeOnNode.set("checked",false);
																		}
																		if(refusePassedToTheNode){
																			refusePassedToTheNode.set("checked",false);
																			refusePassedToThisNodePane.style("display", 'none');
																		}
																		if(refusePassedToThisNodeOnNodePane)
																			refusePassedToThisNodeOnNodePane.style("display", 'none');
																		if(refusePassedToTheNodePane)
																			refusePassedToTheNodePane.style("display", '');
																	} else {
																		// 分支内，则根据情况控制返回本人和返回本节点的开关显示
																		if (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked != true) {
																			refusePassedToThisNodePane.style("display", '');
																		}
																		if (refusePassedToThisNode && refusePassedToThisNode.checked != true) {
																			refusePassedToThisNodeOnNodePane.style("display", '');
																		}
																	}
																	
																	if (refusePassedToTheNode && refusePassedToTheNode.checked) {
																		// 切换驳回节点时，去掉勾选返回指定节点
																		if(refusePassedToSequenceFlowNode){
																			refusePassedToSequenceFlowNode.set("checked",true);
																		}else if(refusePassedToThisNode){
																			refusePassedToThisNode.set("checked",true);
																		}else if(refusePassedToThisNodeOnNode){
																			refusePassedToThisNodeOnNode.set("checked",true);
																		}
																		
																		refusePassedToTheNode.set("checked",false);
																		//query('#returnBackToNodeIdSelectObj').style("display",'none');
																	}
																	if (isPassedSubprocessNode) {
																		if (!((refusePassedToThisNode && refusePassedToThisNode.checked) || (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) || (refusePassedToTheNode && refusePassedToTheNode.checked))) {
																			// 没有驳回返回选项被勾选时，驳回后流经的子流程重新流转选项可以显示出来
																			query('.isRecoverPassedSubprocessLabel').style("display", '');
																		}
																	}
																	
																	_trialStaffPeopleHtmlFunc(val);
																	 
																});
																
																// 驳回后流经的子流程重新流转选项
																if (isPassedSubprocessNode) {
																	if ((refusePassedToThisNode && refusePassedToThisNode.checked) || (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) || (refusePassedToTheNode && refusePassedToTheNode.checked)) {
																		query('.isRecoverPassedSubprocessLabel').style("display", 'none');
																	}
																}
															}else{
																
																// 驳回节点变更监听
																jumpToNodeIdSelectObj.on("attrmodified-value", function(evt) {
																	var val = evt.detail.newValue;
																	_trialStaffPeopleHtmlFunc(val);
																});
																
															}
															// <----------END---------->
														};
														if (this.parseDeferred) {
															this.parseDeferred.then(thenFun);
														}
													}
												});
								} else {
									query("#operationsTDContent")
										.html(lbpm.workitem.constant.noRefuseNode.replace("{refuse}", operationName).replace("{jump}", operationName) + '<input type="hidden" alertText="' + lbpm.workitem.constant.noRefuseNode.replace("{refuse}", operationName).replace("{jump}", operationName) + '" key="jumpToNodeId">');
									 //构建一个隐藏的校验域
							          var divNode = domConstruct.create("div",{
											id:"noRefuseNodeEmptyDiv",
											style:"position:relative"
										});
										domConstruct.place(divNode,query("#operationsTDContent")[0],'last');
										var nodeHtml = "<div data-dojo-type='mui/form/Input' data-dojo-props='\"name\":\"jumpToNodeIdEmpty\",\"showStatus\":\"edit\",\"validate\":\"jumpToNodeRequired\"' id='jumpToNodeIdEmpty' style='display:none;'></div>";
										query("#noRefuseNodeEmptyDiv").html(nodeHtml,{parseContent:true});
								}
							});
	
			lbpm.globals.hiddenObject(operationsRow, false);
		}
		
		function OperationCheck() {
			var val = query("#operationsTDContent [name='jumpToNodeIdSelectObj']").val();
			if (val == null || val == '') {
				tip["warn"]({text:lbpm.workitem.constant.noRefuseNode.replace("{refuse}", lbpm.currentOperationName)});
				//alert(lbpm.workitem.constant.noRefuseNode.replace("{refuse}", lbpm.currentOperationName));
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
			var refusePassedToThisNode = registry.byId('refusePassedToThisNode');
			if (refusePassedToThisNode) {
				lbpm.globals.setOperationParameterJson(refusePassedToThisNode.checked, "refusePassedToThisNode", "param");
			} else {
				lbpm.globals.setOperationParameterJson(false, "refusePassedToThisNode", "param");
			}

			//增加驳回返回本节点，add by wubing date:2016-07-29
			var refusePassedToThisNodeOnNode = registry.byId('refusePassedToThisNodeOnNode');
			if (refusePassedToThisNodeOnNode) {
				lbpm.globals.setOperationParameterJson(refusePassedToThisNodeOnNode.checked, "refusePassedToThisNodeOnNode", "param");
			} else {
				lbpm.globals.setOperationParameterJson(false, "refusePassedToThisNodeOnNode", "param");
			}
			 //会审驳回到具体人
			var lbpmHandlerTriageStr= query("[name='trialStaffPeople']").val();
			lbpm.globals.setOperationParameterJson(lbpmHandlerTriageStr,"lbpmHandlerTriage", "param");
			
			//增加驳回返回指定节点
			var refusePassedToTheNode = registry.byId('refusePassedToTheNode');
			if (refusePassedToTheNode) {
				lbpm.globals.setOperationParameterJson(refusePassedToTheNode.checked, "refusePassedToTheNode", "param");
				if (refusePassedToTheNode.checked) {
					lbpm.globals.setOperationParameterJson(registry.byId('returnBackToNodeIdSelectObj').value, "returnBackToNodeId", "param");
				}
			} else {
				lbpm.globals.setOperationParameterJson(false, "refusePassedToTheNode", "param");
			}

			var isRecoverPassedSubprocess = registry.byId('isRecoverPassedSubprocess');
			if (isRecoverPassedSubprocess) {
				// 子流程
				lbpm.globals.setOperationParameterJson(isRecoverPassedSubprocess.checked, "isRecoverPassedSubprocess", "param");
			}
		}
		// 取得有效的上一历史节点对象
		lbpm.globals.getHistoryPreviousNodeInfo = function() {
			var passNodeString = lbpm.globals.getAvailableHistoryRoute();
			var passNodeArray = passNodeString.split(";");
			for (var i = passNodeArray.length - 1; i >= 0; i--) {
				var passNodeInfo = passNodeArray[i].split(":");
				var nodeInfo = lbpm.nodes[passNodeInfo[0]];
				if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_START, nodeInfo)
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END, nodeInfo)
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_AUTOBRANCH, nodeInfo)
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH, nodeInfo)
						|| lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND, nodeInfo)) {
					continue;
				}
				if (nodeInfo.id == lbpm.nowNodeId) {
					continue;
				}
				return passNodeArray[i];
			}
		};
		// 取得有效的上一历史路径
		lbpm.globals.getAvailableHistoryRoute = function() {
			var fdTranProcessObj = document.getElementById("sysWfBusinessForm.fdTranProcessXML");
			var statusData = WorkFlow_GetStatusObjectByXML(fdTranProcessObj.value);
			for (var i = 0; i < statusData.runningNodes.length; i++) {
				var nodeInfo = statusData.runningNodes[i];
				if (nodeInfo.id == lbpm.nowNodeId) {
					return nodeInfo.routePath;
				}
			}
			return "";
		};
		
		lbpm.globals.buildSuperReturnBackToNodeIdSelectOption=function(jumpToNodeIdSelectObj){
			if (jumpToNodeIdSelectObj == null) {
				jumpToNodeIdSelectObj = registry.byId('jumpToNodeIdSelectObj');
			}
			
			var jumpToNodeIdSelectOptions = jumpToNodeIdSelectObj.store.data;
			var jumpToNodeId = jumpToNodeIdSelectObj.value;
			var returnNodes = getReturnNodes(lbpm.globals.getNodeObj(jumpToNodeId));
			
			var data = [];
			for(var j = 0; j < jumpToNodeIdSelectOptions.length; j++){
				var nodeInfo = lbpm.globals.getNodeObj(jumpToNodeIdSelectOptions[j].value);
				if (!containNode(returnNodes, nodeInfo)){
					continue;
				}
				data.push({
					text : jumpToNodeIdSelectOptions[j].text,
					value : jumpToNodeIdSelectOptions[j].value
				});
			}
			
			if (containNode(returnNodes, lbpm.globals.getNodeObj(lbpm.nowNodeId))) {
				data.push({
					text : lbpm.workitem.constant.thisNode,
					value : lbpm.nowNodeId
				});
			}
			
			var returnBackToNodeIdSelectObj = registry.byId('returnBackToNodeIdSelectObj');
			returnBackToNodeIdSelectObj.setStore(new Memory(
					{
						data : data
					}));
			
			if (data.length == 0) {
				returnBackToNodeIdSelectObj.set("value", "");
				query('#returnBackToNodeIdSelectObj').style("display",'none');
				query('#noReturnBackNodeLabel').style('display','');
			} else {
				returnBackToNodeIdSelectObj.set("value", data[0].value);
				query('#returnBackToNodeIdSelectObj').style("display",'');
				query('#noReturnBackNodeLabel').style('display','none');
			}
		};
		
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
		 function showOption(param,ar){
			if(!ar || ar.length == 0)
				return true;
			for(var i = 0;i < ar.length;i++){
				if(param == ar[i])
					return true;
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
		  function _getPassNodeHandlerObj(nodeId) {
		  	var url = "lbpmHandlerTriageService&modelId=" + lbpm.modelId + "&fdFactId=" + nodeId;
		  	var handlerArray=[];
		  	var data = new KMSSData();
		  	handlerArray = data.AddBeanData(url).GetHashMapArray();
		      return handlerArray;
		  }
		  
		  function _trialStaffPeopleHtmlFunc(nodeId){
			  var mobile_controls_trialStaffPeople = registry.byId("mobile_controls_trialStaffPeople");
			   if(mobile_controls_trialStaffPeople){
           	   mobile_controls_trialStaffPeople.destroy();
              }   
			   
			 //流程模板定义的开关
             var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
			 //开关判断
             if(lbpm.workitem.constant.isRefuseSelectPeople=="true"&&processDefine&&processDefine.refuseSelectPeople&&processDefine.refuseSelectPeople=="true"){
           	  var handlerArray= _getPassNodeHandlerObj(nodeId);
                 var storeHtml="&quot;store&quot;:[";
                 if(handlerArray.length>0){
             		for(var i=0;i<handlerArray.length;i++){
             			if(i==0){
             				storeHtml+="{&quot;text&quot;:&quot;"+handlerArray[i].handlerName+"&quot;,&quot;value&quot;:&quot;"+handlerArray[i].handlerId+"&quot;}";	
             			}else{
             				storeHtml+=",{&quot;text&quot;:&quot;"+handlerArray[i].handlerName+"&quot;,&quot;value&quot;:&quot;"+handlerArray[i].handlerId+"&quot;}";
             			}
             		}
             	 }
                 storeHtml+="],";
                 
                 var dojoPropsHtml="&quot;name&quot;:&quot;trialStaffPeople&quot;,"+storeHtml+"&quot;mul&quot;:true,&quot;showPleaseSelect&quot;:true,&quot;leastNItem&quot;:null,&quot;showStatus&quot;:&quot;edit&quot;,&quot;subject&quot;:&quot;"+lbpm.workitem.constant.refuseSelectPeopleMessage+"&quot;,&quot;orient&quot;:&quot;none&quot;,&quot;onValueChange&quot;:&quot;__xformDispatch&quot;";
                 
                 var trialStaffPeopleHtml = '<xformflag id="_xform_trialStaffPeople" _xform_type="fSelect">'+
	          			'<div data-dojo-type="sys/xform/mobile/controls/fSelect/FSelect" data-dojo-props="'+dojoPropsHtml+'" class="oldMui muiFormEleWrap popup muiFormStatusEdit muiFormRight showTitle trialStaffPeople" style="width:100%;margin-top:4px;" id="mobile_controls_trialStaffPeople">'+
	          			'</div>'+
	          	'</xformflag>';
                 
                 var nodeData = lbpm.nodes[nodeId];
         			//会审才会显示具体人员
             		if(nodeData.processType&&nodeData.processType=="2"){
             			
             			query("#_super_triageHandler").html(trialStaffPeopleHtml,{parseContent:true});
             		}else{
             			query("#_super_triageHandler").html("");
             		}
             }
		  }
		  
	});
