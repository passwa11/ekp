define(function(){
	var submitFlow={};
	//提交表单事件
	submitFlow.submitFormEvent = lbpm.globals.submitFormEvent=function(formObj, method, clearParameter, moreOptions){
		if(!lbpm.globals.validateBeforeSumbitVersion())
			return false;

		var callBack1 = arguments.callee.caller;

		if(lbpm.globals != undefined && lbpm.nowNodeId != undefined && lbpm.globals.getNodeObj(lbpm.nowNodeId).processType != undefined && lbpm.constant.PROCESSTYPE_SINGLE != undefined){
			if(callBack1 == Com_Submit && lbpm.globals.getNodeObj(lbpm.nowNodeId).processType == lbpm.constant.PROCESSTYPE_SINGLE){

				//循环拿到节点属性配置为 handler_communicate 的操作类型，作为弹窗提示语的拼接参数
				var handlerType = lbpm.currentOperationType;
				if (lbpm.nowProcessorInfoObj) {
					var operInfo = lbpm.nowProcessorInfoObj.operations;
					var operName = null;
					if(operInfo != null){
						for(var i =0; i<operInfo.length; i++){
							if(operInfo[i].id == "handler_communicate"){
								operName = operInfo[i].name;
								break;
							}
						}
					}
					//获取当前操作类型,当操作类型为，通过、驳回、补签时进入校验
					//校验并行审核时是否有处于沟通中的工作项
					//window.commuflag 放行标识，防止提交时再次进入此方法
					if((handlerType == "handler_refuse" || handlerType == "handler_pass" || handlerType == "handler_additionSign" || handlerType == "handler_superRefuse" || handlerType == "handler_abandon") && (window.commuflag == false || window.commuflag == null)){
						var proId = lbpm.modelId;
						var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=getCommunicateWorkitem&fdProcessId='+proId;
						var kmssData = new KMSSData();
						kmssData.SendToUrl(ajaxurl, function(http_request) {
							var responseText = http_request.responseText;
							var json = eval("("+responseText+")");
							if(json.hasCommunicate == true) {
								require({async: false},["mui/dialog/Confirm"],function(Confirm){
									var contentHtml = "当前已有其他审批人" + json.personList + "正处于" + operName + "中，您是否还需要继续审批？";
									var title = "提示信息";
									var callback = function (bool, dialog) {
										if (bool) {
											window.commuflag = true;
											Com_Submit(formObj, method, clearParameter, moreOptions);
										} else {
											return;
										}
									}
									Confirm(contentHtml, title, callback);
								});
							}else{
								window.commuflag = true;
								Com_Submit(formObj, method, clearParameter, moreOptions);
							}

						},true);
						//阻塞弹窗，使弹窗能正常执行回调
						return false;
					}
				}


			}
		}





		//当简易流程界面是直接返回true,因为逻辑在lbpm.globals.simpleFlowSubmitEvent函数中
		var docStatus = lbpm.constant.DOCSTATUS;
		if(parseInt(docStatus) >= lbpm.constant.STATE_COMPLETED){
			return true;
		}
		var canStartProcess = document.getElementById("sysWfBusinessForm.canStartProcess");
		var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
		if(operatorInfo == null){
			canStartProcess.value = "false";
			return true;
		}
		// 兼容暂存，暂存则不校验
		if(typeof lbpm.saveFormData !='undefined' && canStartProcess.value === 'false'){
			lbpm.globals.setParameterOnSubmit();
			return true;
		}
		//下以注释代码为忽视当前处理人不是流程处理人时的校验
		var currentHandlerIds = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds").value;
		if(lbpm.constant.METHOD == lbpm.constant.METHODEDIT && canStartProcess != null){
			var currentIdArray = currentHandlerIds.split(";");
			var flag = false;
			for(var i = 0; i < currentIdArray.length; i++){
				if(lbpm.handlerId.indexOf(currentIdArray[i]) != -1){
					flag = true;
					break;
				}
			}
			if(!flag){
				canStartProcess.value = "false";
				return true;
			}
		}
		if (lbpm.isFreeFlow && lbpm.globals.getNodeSize() <= 3) {
			if (moreOptions) {
				if (!moreOptions.saveDraft) {
					require(["mui/dialog/Tip"],function(tip){
						tip["warn"]({text:lbpm.constant.FREEFLOW_MUSTAPPENDNODE});
					});
					//alert(lbpm.constant.FREEFLOW_MUSTAPPENDNODE);
					return false;
				}
			} else {
				require(["mui/dialog/Tip"],function(tip){
					tip["warn"]({text:lbpm.constant.FREEFLOW_MUSTAPPENDNODE});
				});
				//alert(lbpm.constant.FREEFLOW_MUSTAPPENDNODE);
				return false;
			}
		}
		//检验是否选择了操作项
		var optInfo = $("#operationMethodsGroup").val();
		if(optInfo==""){
			require(["mui/dialog/Tip"],function(tip){
				tip["warn"]({text:lbpm.constant.VALIDATEOPERATIONTYPEISNULL});
			});
			//alert(lbpm.constant.VALIDATEOPERATIONTYPEISNULL);
			return false;
		}
		if(optInfo){
			var oprArr=optInfo.split(":");
			lbpm.currentOperationType=oprArr[0];
			lbpm.currentOperationName=oprArr[1];
		}

		//意见长度检查
		var fdUsageContent=lbpm.operations.getfdUsageContent();
		if (fdUsageContent != null && fdUsageContent.value != "") {
			var maxLength = 4000;
			var contentVal = fdUsageContent.value || "";
			var newvalue = contentVal.replace(/[^\x00-\xff]/g, "***");
			if (newvalue.length > maxLength) {
				//新增审批意见长度判断
//				var msg = lbpm.constant.ERRORMAXLENGTH;
//				var title = lbpm.constant.CREATEDRAFTCOMMONUSAGES;
				var msg = lbpm.constant.ERROR_FDUSAGECONTENT_MAXLENGTH;
				var title = lbpm.constant.OPINION;
				msg = msg.replace(/\{name\}/, title).replace(/\{maxLength\}/, maxLength);
				require(["mui/dialog/Tip"],function(tip){
					tip["warn"]({text:msg,width:'260',height:'150'});
				});
				//alert(msg);
				return false;
			}
		};


		var isValidated = lbpm.globals.isUsageContenValidated(lbpm.currentOperationType);
		// 若审批意见校验开关开启则执行如下操作
		if(isValidated == true){
			// 获取校验的意见
			var defalutUsage = "";
			defalutUsage = lbpm.globals.getOperationDefaultUsageValidate(lbpm.currentOperationType);
			var deafultUsageName = defalutUsage.replace("{xx}","");
			// 获取当前提交的输入框中的审批意见
			var fdUsageContent = lbpm.operations.getfdUsageContent();
			if((fdUsageContent.value).indexOf("{xx}")>=0){
				require(["mui/dialog/Tip"],function(tip){
					tip["warn"]({text:Data_GetResourceString('sys-lbpmservice:lbpmUsageContent.fdDescription.details.7')});
				});
				return false;
			}else if(!(deafultUsageName == (fdUsageContent.value))){
				var defalutUsageRep = defalutUsage;
				// 审批意见包含{xx}自定义输入符则进行正则表达式校验
				if(defalutUsage.indexOf("{xx}")>=0){
					//将默认审批中的{xx}替换成\S+正则表达式进行校验
					defalutUsageRep = defalutUsage.replace(/\{xx\}/g,"(\\S{0,})");
				}
				defalutUsageRep = new RegExp("^"+defalutUsageRep+"$");
				// 若校验不通过
				if(!(defalutUsageRep.test(fdUsageContent.value))){
					var prompt = Data_GetResourceString("sys-lbpmservice:lbpmUsageContent.fdDescription.details.7")
					prompt = prompt.replace("{0}",defalutUsage);
					require(["mui/dialog/Tip"],function(tip){
						tip["warn"]({text:prompt,width:'300',height:'200'});
					});
					return false;
				}

			}

		}

		var isExcludeValidated = lbpm.globals.isUsageContenExcludeValidated(lbpm.currentOperationType);
		// 若审批意见排除校验开关开启则执行如下操作
		if(isExcludeValidated == true){
			var excludeUsage = "";
			// 获取排除校验的意见内容
			excludeUsage = lbpm.globals.getOperationDefaultUsageValidate(lbpm.currentOperationType);
			// 获取当前提交的输入框中的审批意见
			var fdUsageContent = lbpm.operations.getfdUsageContent();
			// 审批意见是否包含排除的内容
			if((fdUsageContent.value).indexOf(excludeUsage) !=-1){
				var prompt = Data_GetResourceString("sys-lbpmservice:lbpmUsageContent.fdDescription.details.9")
				prompt = prompt.replace("{0}",excludeUsage);
				require(["mui/dialog/Tip"],function(tip){
					tip["warn"]({text:prompt,width:'300',height:'200'});
				});
				return false;
			}
		}
		//自由流私密意见校验
		if (lbpm.isFreeFlow && Lbpm_SettingInfo.isPrivateOpinion == "true"){
			var isCanpass = true;
			require(["dijit/registry"], function(registry) {
				var switchWgt = registry.byId('privateOpinion');
				if (switchWgt && switchWgt.value=='on') {
					var myWgt = registry.byId('privateOpinionPerson');
					if(myWgt && !myWgt.curIds){
						//alert(Data_GetResourceString('sys-lbpmservice:lbpmservice.auditnote.placeholder'));
						require(["mui/dialog/Tip"],function(tip){
							tip["warn"]({text:Data_GetResourceString('sys-lbpmservice:lbpmservice.auditnote.placeholder')});
						});
						isCanpass = false;
					}
				}
			});
			if(!isCanpass){
				return false;
			}
		}

		var oprClass = null;
		if(lbpm.currentOperationType){
			oprClass=lbpm.operations[lbpm.currentOperationType];
			if(oprClass!=null){
				if(!oprClass.check(formObj, method, clearParameter, moreOptions)) return false;
			}
		}

		if(lbpm && lbpm.processorInfoObj && ((lbpm.nowProcessorInfoObj.type == "reviewWorkitem" &&  lbpm.currentOperationType =='handler_pass')
			|| (lbpm.nowProcessorInfoObj.type == "signWorkitem" &&  lbpm.currentOperationType =='handler_sign')) && lbpm.globals.handlerWrittenValidator) {
			// 手写校验
			if (!lbpm.globals.handlerWrittenValidator()) return false;
		}


		//提交时才执行提交人身份校验
		if(callBack1 == Com_Submit){
			//提交人身份校验
			if(!lbpm.constant.handlerIdentityIsSameDept && !lbpm.isLbpmIdentityCheck && !lbpm.globals.lbpmIdentityCheck(formObj, method, clearParameter, moreOptions)){
				return false;
			}
		}

		lbpm.globals.setParameterOnSubmit();
		if(oprClass != null && oprClass.setOperationParam) {
			oprClass.setOperationParam();
			lbpm.events.fireListener(lbpm.constant.EVENT_SETOPERATIONPARAM, null);
		}
		if ($("input[name='futureNode']:checked").length > 0){
			var eleObj = $("input[name='sysWfBusinessForm.fdParameterJson']");
			if(eleObj.length>0 && eleObj[0].value){
				var jsonObj = $.parseJSON(eleObj[0].value);
				if(!jsonObj["param"] || !jsonObj["param"]["futureNodeId"]){
					require(["mui/dialog/Tip"],function(tip){
						tip["warn"]({text:lbpm.constant.CHKNEXTNODENOTNULL});
					});
					return false;
				}
			}
		}
		//动态子流程即将流向
		var isCheck = true;
		$("input[name^='dynamicNextNodeIds_']").each(function(){
			var $futureNode = $(this).closest(".muiRadioItem").find("input[name='futureNode']");
			if($futureNode.length==0 || $futureNode.is(':checked')){
				if(!$(this).val()){
					var _name = $(this).attr("name");
					var __node = lbpm.nodes[_name.split("_")[1]];
					require(["mui/dialog/Tip","mui/i18n/i18n!sys-mobile:mui.form.please.select"],function(tip,Msg){
						tip["warn"]({text:Msg['mui.form.please.select']+(__node.dynamicGroupShowName?__node.dynamicGroupShowName:__node.name)});
					});
					isCheck = false;
					return false;
				}
			}
		})
		if(!isCheck){
			return false;
		}
		//alert(document.getElementById("sysWfBusinessForm.fdParameterJson").value);
		return true;
	};

	lbpm.globals.getSplitNodeInfo = function(nodeId){
		var nodeInfo;
		require(["mui/util","dojo/request"],function(util, request){
			var processId = lbpm.modelId;
			var url = "/sys/lbpm/engine/jsonp.jsp?s_bean=lbpmProcessDefinitionDetailService&processId="+processId+"&nodeId="+nodeId;
			url = util.formatUrl(url);
			request.get(url,
				{handleAs:'json',sync:true}).then(
				function(data){
					//请求成功后的回调
					nodeInfo = WorkFlow_LoadXMLData(data.data)
				});
		});
		return nodeInfo;
	}

	//设置标准的参数项在提交前,此函数一定要在操作的setOperationParam函数前运行，方便操作扩展的时候可以覆盖一些标准的参数
	submitFlow.setParameterOnSubmit = lbpm.globals.setParameterOnSubmit=function(){
		var operatorInfo = lbpm.nowProcessorInfoObj;
		//var operatorInfo = lbpm.globals.getOperationParameterJson("workitemId:handlerId");
		var taskId = lbpm.globals.getOperationParameterJson("id");
		//设置Json参数
		lbpm.globals.setOperationParameterJson(taskId==null?"":taskId,"taskId"); //工作项ID
		//lbpm.globals.setOperationParameterJson(lbpm.handlerId==null?"":lbpm.handlerId,"handlerId"); //处理人ID
		//流程实例ID
		lbpm.globals.setOperationParameterJson($("[name='sysWfBusinessForm.fdProcessId']")[0].value,"processId");
		//活动类型
		lbpm.globals.setOperationParameterJson(operatorInfo.type,"activityType");
		//设置操作类型
		if(lbpm.currentOperationType)
			lbpm.globals.setOperationParameterJson(lbpm.currentOperationType.toString(),"operationType");
		//设置操作名称
		if(lbpm.currentOperationName)
			lbpm.globals.setOperationParameterJson(lbpm.currentOperationName,"operationName", "param");

		//如果有分支设置分支
		var futureNodeCheckboxs=$("input[name='futureNode'][type='checkbox']");
		//判断是并行分支还是人工决策分支（如checkbox类型则是并行分支）
		if(futureNodeCheckboxs.length>0){
			var futureNodes="";
			var futureNodesChekBoxGroup;
			require(['dojo/topic','dijit/registry'],function(topic,registry){
				futureNodesChekBoxGroup=registry.byId("sys_lbpmservice_mobile_workitem_FutureNodesChekBoxGroup");
				futureNodes=futureNodesChekBoxGroup.value.replace(/;/g, ',');
			});
			lbpm.globals.setOperationParameterJson(futureNodes,"futureNodeId","param");
		}
		else{
			$("input[name='futureNode']:checked").each(function(i){
				lbpm.globals.setOperationParameterJson(this,null,"param");
			});
		}

		//通知方式
		$("input[name='sysWfBusinessForm.fdSystemNotifyType']").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"notifyType", "param");
		});

		//通知方式优先级 add by wubing date:2014-09-18
		$("input[name='sysWfBusinessForm.fdNotifyLevel']:checked").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"notifyLevel","param");
		});

		//流程结束后 --notifyOnFinish
		$("#notifyOnFinish").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"notifyOnFinish", "param");
		});

		if($('#opinionConfig').css('display')=='block'){
			//意见是否显示在稿纸 add by 洪健 date:2020-06-17
			var notifyIsScript="";
			var approveOpinionType="";
			require(['dojo/topic','dijit/registry'],function(topic,registry){
				notifyIsScript=registry.byId("notifyIsScript").value;
				approveOpinionType=registry.byId("approveOpinionType").value;
			});

			lbpm.globals.setOperationParameterJson(notifyIsScript,"notifyIsScript", "param");
			lbpm.globals.setOperationParameterJson(approveOpinionType,"approveOpinionType", "param");
		}

		//意见--auditNode
		var auditNodeObj=lbpm.operations.getfdUsageContent();
		if(auditNodeObj) lbpm.globals.setOperationParameterJson(auditNodeObj,"auditNote", "param");
		//私密意见
		var $privateOpinion=$("input[name='privateOpinionCanViewIds']");
		if($privateOpinion.val()) lbpm.globals.setOperationParameterJson($privateOpinion[0],"privateOpinionIds", "param");
		//@处理人
		if(lbpm.globals.filterNoticeHandler){
			var noticeHandlerIds = lbpm.globals.filterNoticeHandler();
			var noticeHandlerIdsObj = $("input[name='noticeHandlerIds']")[0];
			if(noticeHandlerIds){
				lbpm.globals.setOperationParameterJson(noticeHandlerIdsObj,"noticeHandlerIds", "param");
			}
		}
		$("input[name='sysWfBusinessForm.fdAuditNoteFdId']").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"auditNoteFdId", "param");
		});
		$("input[name='sysWfBusinessForm.fdAuditNoteFrom']").each(function(i){
			lbpm.globals.setOperationParameterJson(this, "auditNoteFrom", "param");
		});
		//动态子流程即将流向
		$("input[name^='dynamicNextNodeIds_']").each(function(i){
			var $futureNode = $(this).closest(".muiRadioItem").find("input[name='futureNode']");
			if($futureNode.length==0 || $futureNode.is(':checked')){
				if(this.value){
					lbpm.globals.setOperationParameterJson(this.value.replace(/;/g,","), "_futureNodeId", "param");
				}
			}
		});
		//即席子流程提交前处理
		$("input[name='nextAdHocRouteId']:checked").each(function(i){
			lbpm.nextAdHocRouteId = this.value;
			lbpm.globals.setAdHocSubNode();
		});
		//特权人操作或者起草人操作无需初始化
		if(lbpm.currentOperationType && lbpm.operations[lbpm.currentOperationType] && lbpm.operations[lbpm.currentOperationType].isPassType){
			//初始化嵌入子流程信息（已初始化过的节点将不再初始化，包含在起草节点修改组节点信息中的初始化）
			lbpm.globals.setEmbeddedNodeInfo();
			//自由子流程有初始化节点时，且设置了筛选排序，将初始节点加入
			lbpm.globals.setFreeflowNodeInfo();
			//初始化动态子流程信息
			lbpm.globals.setDynamicNodeInfo();
		}
		lbpm.globals.setModifyParameterOnSubmit();
	};

	lbpm.globals.setEmbeddedNodeInfo = function(isInit){
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
					for(var j=0;i<flowInfo.lines.length;j++){
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
		//当前节点下标
		var nowNodeIndex = parseInt(nowFlow.nodesIndex, 10);
		//当前连线下标
		var nowLineIndex = parseInt(nowFlow.linesIndex, 10);
		//嵌入的节点及连线信息
		var newNodeAndLineInfo = {};
		//嵌入子流程节点，fdRefId对应信息
		var fdEmbeddedInfo = [];
		var formFieldList = lbpm.globals.getFormFieldList();
		for(var i=0;i<nowFlow.nodes.length;i++){
			if(nowFlow.nodes[i].XMLNODENAME == "embeddedSubFlowNode" && nowFlow.nodes[i].embeddedRefId && nowFlow.nodes[i].isInit != "true"){
				var fdContent = getContentByRefId(nowFlow.nodes[i].embeddedRefId);
				if(fdContent){
					if(nowFlow.nodes[i].extAttributes){
						for(var j = 0;j<nowFlow.nodes[i].extAttributes.length;j++){
							if(nowFlow.nodes[i].extAttributes[j].name == "paramsConfig" && nowFlow.nodes[i].extAttributes[j].value){
								var paramsConfig = JSON.parse(nowFlow.nodes[i].extAttributes[j].value);
								fdContent = fdContent.replace(/\$(\w+)\$/g,function($1,$2){
									for(var k=0;k<paramsConfig.length;k++){
										if(paramsConfig[k].fdParamValue == $2){
											var formValue = paramsConfig[k].fdFormValue;
											for(var l=0;l<formFieldList.length;l++) {
												var info = formFieldList[l];
												if(formValue === info.name){
													if(!(info.name.indexOf("$")>-1)){
														formValue = "$"+info.name+"$";
													}
													break;
												}
											}
											return formValue.replace(/\"/g,"&quot;");
										}
									}
									return $1;
								});
								break;
							}
						}
					}
					//嵌入的流程图对象
					var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
					//新旧节点ID对应关系,key:旧id，value:新id
					var nodeInfo = {};
					//嵌入的节点
					var newNodes = [];
					for(var j = 0;j<embeddedFlow.nodes.length;j++){
						//复制嵌入的节点，添加groupNodeId，groupNodeType，坐标变为负的（隐藏），
						//重置id，并记录新旧节点id对应关系，添加到嵌入的节点数组中
						var newNode = $.extend(true, {}, embeddedFlow.nodes[j]);
						newNode.groupNodeId = nowFlow.nodes[i].id;
						newNode.groupNodeType = "embeddedSubFlowNode";
						newNode.x = -newNode.x;
						newNode.y = -newNode.y;
						nodeInfo[embeddedFlow.nodes[j].id] = newNode.id = "N"+(++nowNodeIndex);
						// 记录原始子节点ID到扩展属性中
						if(!newNode.extAttributes){
							newNode.extAttributes = [];
						}
						var extAttribute = [];
						extAttribute.XMLNODENAME = "attribute";
						extAttribute.name = "sourceSubId";
						extAttribute.value = embeddedFlow.nodes[j].id;
						newNode.extAttributes.push(extAttribute);
						//从组节点继承属性
						setSubNodeInfoByGroupNode(newNode,nowFlow.nodes[i]);
						newNodes.push(newNode);
					}
					lbpm.globals.replaceModifyHandlerNodeIds(newNodes,nodeInfo,nowFlow);
					//将嵌入的节点记录到嵌入的节点及连线信息对象中，key：当前嵌入子流程节点id
					newNodeAndLineInfo[nowFlow.nodes[i].id] = {};
					newNodeAndLineInfo[nowFlow.nodes[i].id]["newNodes"] = newNodes;
					//嵌入的连线
					var newLines = [];
					for(var j = 0;j<embeddedFlow.lines.length;j++){
						//复制嵌入的连线，根据记录的新旧节点id对应关系修改startNodeId，endNodeId
						//重置id，添加到嵌入的连线数组中
						var newLine = $.extend(true, {}, embeddedFlow.lines[j]);
						newLine.startNodeId = nodeInfo[newLine.startNodeId];
						newLine.endNodeId = nodeInfo[newLine.endNodeId];
						newLine._points = newLine.points;
						delete newLine.points;
						newLine.id = "L"+(++nowLineIndex);
						newLines.push(newLine);
					}
					//将嵌入的连线记录到嵌入的节点及连线信息对象中，key：当前嵌入子流程节点id
					newNodeAndLineInfo[nowFlow.nodes[i].id]["newLines"] = newLines;
					//添加标识，标识该节点已经初始化过
					nowFlow.nodes[i].isInit = "true";
					fdEmbeddedInfo.push({nodeId:nowFlow.nodes[i].id,fdRefId:nowFlow.nodes[i].embeddedRefId});
				}
			}
		}
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
		//嵌入子流程节点，fdRefId对应信息
		if(fdEmbeddedInfo.length>0){
			var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
			//修改当前流程图对象的节点下标及连线下标
			nowFlow.nodesIndex = nowNodeIndex+"";
			nowFlow.linesIndex = nowLineIndex+"";
			//为fdFlowContent重新设值
			processXMLObj.value = WorkFlow_BuildXMLString(nowFlow);
			if(isInit){
				lbpm.globals.parseXMLObj();
			}
			if(!lbpm.modifys){
				lbpm.modifys = {};
			}
			$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
			lbpm.lbpmEmbeddedInfo = fdEmbeddedInfo;
		}
	}

	lbpm.globals.setFreeflowNodeInfo = function(){
		var nowFlow = WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
		if(!nowFlow || !nowFlow.nodes || !nowFlow.opinionSortIds){
			return;
		}
		for(var i=0;i<nowFlow.nodes.length;i++){
			if(nowFlow.nodes[i].XMLNODENAME == "freeSubFlowNode" && nowFlow.nodes[i].initSubNodeId){
				var nextObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
				var oIds = nowFlow.opinionSortIds.split(";");
				if(nextObj && nextObj.id == nowFlow.nodes[i].id && Com_ArrayGetIndex(oIds, nowFlow.nodes[i].id) > -1){
					if(Com_ArrayGetIndex(oIds, nowFlow.nodes[i].initSubNodeId) == -1){
						oIds.push(nowFlow.nodes[i].initSubNodeId);
						nowFlow.opinionSortIds = oIds.join(";");
						lbpm.nowFreeSubFlowNodeId = nowFlow.nodes[i].initSubNodeId;
						var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
						processXMLObj.value = WorkFlow_BuildXMLString(nowFlow);
						if(!lbpm.modifys){
							lbpm.modifys = {};
						}
						$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
					}
				}
			}
		}
	}

	lbpm.globals.setDynamicNodeInfo = function(isInit){
		if(!lbpm.constant.IS_HANDER || lbpm.constant.IS_HANDER=="false"){
			return;
		}
		var nexNodeIds = [];
		if(lbpm.nowNodeId){
			var nextNodes = lbpm.globals.getNextNodeObjs(lbpm.nowNodeId);
			for(var i = 0;i<nextNodes.length;i++){
				if(nextNodes[i].XMLNODENAME == "dynamicSubFlowNode" && nextNodes[i].isInit != "true"){
					nexNodeIds.push(nextNodes[i].id);
				}else if(nextNodes[i].XMLNODENAME == "manualBranchNode"){
					var _nextNodes = lbpm.globals.getNextNodeObjs(nextNodes[i].id);
					for(var j = 0;j<_nextNodes.length;j++){
						if(_nextNodes[j].XMLNODENAME == "dynamicSubFlowNode" && _nextNodes[j].isInit != "true"){
							nexNodeIds.push(_nextNodes[j].id);
						}
					}
				}
			}
		}
		/*//嵌入子流程根据redId获得流程图xml
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
        //动态子流程根据fdId获得参数配置
        var getParamsByFdId = function(fdId){
            var fdContent = "";
            var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=getParamsByFdId&fdId='+fdId;
            var kmssData = new KMSSData();
            kmssData.SendToUrl(ajaxurl, function(http_request) {
                var responseText = http_request.responseText;
                var json = eval("("+responseText+")");
                if (json.fdParamContent){
                    fdContent = json.fdParamContent;
                }
            },false);
            return fdContent;
        }*/
		//动态子流程根据fdId获得nodeGroups
		var getGroupsByFdId = function(fdId){
			var fields = new KMSSData().AddBeanData("lbpmDynamicSubFlowTreeServiceImp&type=groups&fdId="+fdId).GetHashMapArray();
			if(fields && fields.length>0 && fields[0].groups){
				return fields[0].groups;
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
		//根据节点id获取节点对象
		var getNodeById = function(nodes,nodeId){
			for(var i=0;i<nodes.length;i++){
				if(nodes[i].id==nodeId){
					return nodes[i];
				}
			}
		}
		//获取连线
		var getSplitLineBySplitNodeId = function(nodeId,flowInfo){
			for(var j=0;j<flowInfo.lines.length;j++){
				if(flowInfo.lines[j]["startNodeId"] == nodeId){
					return flowInfo.lines[j];
				}
			}
		}
		//获取每条分支的条件等信息
		var getGroupInfoById = function(extAttributes,id){
			var groupsConfig = [];
			if(extAttributes){
				for(var i = 0;i<extAttributes.length;i++){
					if(extAttributes[i].name == "groupsConfig" && extAttributes[i].value){
						groupsConfig = JSON.parse(extAttributes[i].value);
						break;
					}
				}
			}
			for(var i=0;i<groupsConfig.length;i++){
				if(groupsConfig[i].fdId == id){
					return groupsConfig[i];
				}
			}
			return null;
		}
		//当前流程图对象
		var nowFlow = WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
		if(!nowFlow || !nowFlow.nodes){
			return;
		}
		//当前节点下标
		var nowNodeIndex = parseInt(nowFlow.nodesIndex, 10);
		//当前连线下标
		var nowLineIndex = parseInt(nowFlow.linesIndex, 10);
		//动态子流程节点，fdRefId对应信息
		var fdDynamicInfo = [];
		for(var i=0;i<nowFlow.nodes.length;i++){
			if(nowFlow.nodes[i].XMLNODENAME == "dynamicSubFlowNode" && nowFlow.nodes[i].isInit != "true"){
				if(nowFlow.nodes[i].splitType == "custom" && (isInit || Com_ArrayGetIndex(nexNodeIds, nowFlow.nodes[i].id) < 0)){
					continue;
				}
				//动态子流程内置的并行分支开始节点
				var splitNode = getNodeById(nowFlow.nodes,nowFlow.nodes[i].splitNodeId);
				//从动态子流程节点复制一些属性到内置并行分支开始节点上，默认启动分支需要特殊处理
				var copyAtt = ['splitType','startBranchHandlerSelectType','startBranchHandlerId','startBranchHandlerName','defaultStartBranchNames','canSelectDefaultBranch'];
				for(var j=0;j<copyAtt.length;j++){
					var _key = copyAtt[j];
					if(nowFlow.nodes[i][_key]){
						splitNode[_key] = nowFlow.nodes[i][_key];
					}
				}
				//记录默认启动分支
				var _defaultStartBranchIds = [];
				var __defaultStartBranchIds = [];
				if(nowFlow.nodes[i]["defaultStartBranchIds"]){
					_defaultStartBranchIds = nowFlow.nodes[i]["defaultStartBranchIds"].split(";");
				}
				//内置的并行分支之间的连线
				var splitLine = getSplitLineBySplitNodeId(nowFlow.nodes[i].splitNodeId,nowFlow);
				//复制一份
				var _splitLine = $.extend(true, {}, splitLine);
				var _groups = getGroupsByFdId(nowFlow.nodes[i].dynamicGroupId);
				if(_groups){
					var ___groups = JSON.parse(_groups);
					//记录每条分支最左侧的x位置
					var leftX = 80;
					//记录最大的y位置
					var maxY = 0;
					for(var h=0;h<___groups.length;h++){
						var param = ___groups[h];
						var fdContent = param.fdContent;//getContentByRefId(param.fdRefId);
						if(fdContent){
							if(param.fdParamContent && nowFlow.nodes[i].extAttributes){
								//替换参数映射配置
								for(var k = 0;k<nowFlow.nodes[i].extAttributes.length;k++){
									if(nowFlow.nodes[i].extAttributes[k].name == "paramsConfig" && nowFlow.nodes[i].extAttributes[k].value){
										var paramsConfig = JSON.parse(nowFlow.nodes[i].extAttributes[k].value);
										var __params = param.fdParamContent;//getParamsByFdId(param.fdId);
										if(__params){
											var _params = JSON.parse(__params);
											fdContent = fdContent.replace(/\$(\w+)\$/g,function($1,$2){
												for(var l=0;l<_params.length;l++){
													if(_params[l].fdParamData == $2){
														if(_params[l].orgOrFormula == "org"){
															return "$"+_params[l].fdParamValue+"$";
														}else if(_params[l].orgOrFormula == "formula"){
															var fdFactParamValue = _params[l].fdFactParamValue;
															return fdFactParamValue.replace(/\$(\w+)\$/g,function($11,$22){
																for(var ll=0;ll<paramsConfig.length;ll++){
																	if(paramsConfig[ll].fdParamValue == $22){
																		return "$"+paramsConfig[ll].fdFormValue+"$";
																	}
																}
																return $11;
															});
														}
													}
												}
												return $1;
											});
										}
										break;
									}
								}
							}
							//嵌入的流程图对象
							var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
							//新旧节点ID对应关系,key:旧id，value:新id
							var nodeInfo = {};
							//嵌入的节点
							var newNodes = [];
							//最小x坐标和最大x坐标
							var minX = 999999;
							var maxX = 0;
							for(var k = 0;k<embeddedFlow.nodes.length;k++){
								//复制嵌入的节点，添加groupNodeId，groupNodeType，
								//重置id，并记录新旧节点id对应关系，添加到嵌入的节点数组中
								var newNode = $.extend(true, {}, embeddedFlow.nodes[k]);
								newNode.groupNodeId = nowFlow.nodes[i].id;
								newNode.groupNodeType = "dynamicSubFlowNode";
								if(minX>parseInt(newNode.x)){
									minX = parseInt(newNode.x);
								}
								if(maxX<parseInt(newNode.x)){
									maxX = parseInt(newNode.x);
								}
								if(maxY<parseInt(newNode.y)){
									maxY = parseInt(newNode.y);
								}
								nodeInfo[embeddedFlow.nodes[k].id] = newNode.id = "N"+(++nowNodeIndex);
								// 记录原始子节点ID及片段组明细id到扩展属性中
								if(!newNode.extAttributes){
									newNode.extAttributes = [];
								}
								var extAttribute = [];
								extAttribute.XMLNODENAME = "attribute";
								extAttribute.name = "sourceSubId";
								extAttribute.value = embeddedFlow.nodes[k].id;
								newNode.extAttributes.push(extAttribute);
								var extAttribute2 = [];
								extAttribute2.XMLNODENAME = "attribute";
								extAttribute2.name = "nodeGroupId";
								extAttribute2.value = param.fdId;
								newNode.extAttributes.push(extAttribute2);
								//记录组别名
								newNode.fdGroupAlias = param.fdAlias;
								//从组节点继承属性
								setSubNodeInfoByGroupNode(newNode,nowFlow.nodes[i]);
								newNodes.push(newNode);
							}
							//处理引用的ID，替换嵌入流程图中ID为真实ID
							lbpm.globals.replaceModifyHandlerNodeIds(newNodes,nodeInfo,nowFlow);
							//嵌入的连线
							var newLines = [];
							for(var k = 0;k<embeddedFlow.lines.length;k++){
								//复制嵌入的连线，根据记录的新旧节点id对应关系修改startNodeId，endNodeId
								//重置id，添加到嵌入的连线数组中
								var newLine = $.extend(true, {}, embeddedFlow.lines[k]);
								newLine.startNodeId = nodeInfo[newLine.startNodeId];
								newLine.endNodeId = nodeInfo[newLine.endNodeId];
								//处理拐点，因各条分支均需移动x,y，拐点也需平移，x平移(x-(minX-leftX)),y全体向下平移80
								if(newLine.points){
									var __points = newLine.points.split(";");
									for(var o =0;o<__points.length;o++){
										__points[o] = (parseInt(__points[o].split(",")[0]) - (minX-leftX)) + "," + (parseInt(__points[o].split(",")[1])+80);
									}
									newLine._points = __points.join(";");
									delete newLine.points;
								}
								newLine.id = "L"+(++nowLineIndex);
								newLines.push(newLine);
							}
							//平移节点，x平移(x-(minX-leftX)),y全体向下平移80，然后变成负数（隐藏）
							for(var k=0;k<newNodes.length;k++){
								newNodes[k].x = parseInt(newNodes[k].x) - (minX-leftX);
								newNodes[k].x = -newNodes[k].x;
								newNodes[k].y = -newNodes[k].y-80;
							}
							//后续分支最左侧的x位置需要加上前面的最左侧位置加上当前分支占的宽度加上一个节点的宽度
							leftX += (maxX-minX)+160;
							//将嵌入的节点及嵌入的连线合并到当前流程图对象中
							nowFlow.nodes = nowFlow.nodes.concat(newNodes);
							nowFlow.lines = nowFlow.lines.concat(newLines);
							//找出没有连入和连出的节点
							var noStartId = "";var noEndId = "";
							for(var l = 0;l<newNodes.length;l++){
								var newNodeId = newNodes[l].id;
								var isExitStart = false;var isExitEnd = false;
								for(var m = 0;m<newLines.length;m++){
									if(newLines[m].startNodeId == newNodeId){
										isExitStart = true;
									}
									if(newLines[m].endNodeId == newNodeId){
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
							//若有默认启动分支，需要替换成真实的节点ID
							if(Com_ArrayGetIndex(_defaultStartBranchIds, param.fdId) > -1){
								__defaultStartBranchIds.push(noEndId);
							}
							//复制一份，一个修改startNodeId为没有连入节点，一个修改endNodeId为没有连出的节点
							var newLine = $.extend(true, {}, _splitLine);
							newLine.startNodeId = noStartId;
							newLine.id = "L"+(++nowLineIndex);
							//添加复制的连线到当前流程图对象中
							nowFlow.lines.push(newLine);
							if(h==0){
								splitLine.name = param.fdAlias;
								splitLine.endNodeId = noEndId;
								if(splitNode.splitType=="condition"){
									var _groupInfo = getGroupInfoById(nowFlow.nodes[i].extAttributes,param.fdId);
									if(_groupInfo){
										splitLine.condition = _groupInfo.lineCondition;
										splitLine.disCondition = _groupInfo.lineDisCondition;
									}else{
										splitLine.condition = "0";
										splitLine.disCondition = "0";
									}
									splitLine.priority = h;
								}
							}else{
								var _newLine = $.extend(true, {}, _splitLine);
								_newLine.endNodeId = noEndId;
								_newLine.id = "L"+(++nowLineIndex);
								_newLine.name = param.fdAlias;
								if(splitNode.splitType=="condition"){
									var _groupInfo = getGroupInfoById(nowFlow.nodes[i].extAttributes,param.fdId);
									if(_groupInfo){
										_newLine.condition = _groupInfo.lineCondition;
										_newLine.disCondition = _groupInfo.lineDisCondition;
									}else{
										_newLine.condition = "0";
										_newLine.disCondition = "0";
									}
									splitLine.priority = h;
								}
								//添加复制的连线到当前流程图对象中
								nowFlow.lines.push(_newLine);
							}
						}
					}
					//调整内置并行分支坐标,并置为负数
					splitNode.x = -((leftX-80)/2);
					splitNode.y = -40;
					var joinNode = getNodeById(nowFlow.nodes,nowFlow.nodes[i].joinNodeId);
					joinNode.x = -((leftX-80)/2);
					joinNode.y = -(maxY+160);
					//调整内置并行分支开始节点的流出和并行分支结束的流入线条的拐点
					for(var h=0;h<nowFlow.lines.length;h++){
						var _newLine = nowFlow.lines[h];
						if(_newLine.startNodeId == nowFlow.nodes[i].splitNodeId){
							if(_newLine.points){
								var startNode = getNodeById(nowFlow.nodes,_newLine.startNodeId);
								var endNode = getNodeById(nowFlow.nodes,_newLine.endNodeId);
								var __points = _newLine.points.split(";");
								__points.splice(1,0,(-endNode.x)+","+(-startNode.y+20));
								_newLine._points = __points.join(";");
								delete _newLine.points;
							}
						}else if(nowFlow.lines[h].endNodeId == nowFlow.nodes[i].joinNodeId){
							if(_newLine.points){
								var startNode = getNodeById(nowFlow.nodes,_newLine.startNodeId);
								var endNode = getNodeById(nowFlow.nodes,_newLine.endNodeId);
								var __points = _newLine.points.split(";");
								__points.splice(1,0,(-startNode.x)+","+(-endNode.y-20));
								_newLine._points = __points.join(";");
								delete _newLine.points;
							}
						}
					}
					//添加标识，标识该节点已经初始化过
					nowFlow.nodes[i].isInit = "true";
					//记录引用关系
					fdDynamicInfo.push({nodeId:nowFlow.nodes[i].id,fdId:nowFlow.nodes[i].dynamicGroupId});
				}
				//将真实的默认启动分支赋值给内置的并行分支开始节点
				if(__defaultStartBranchIds.length>0){
					splitNode["defaultStartBranchIds"] = __defaultStartBranchIds.join(";");
					nowFlow.nodes[i]["defaultStartBranchIds"] = __defaultStartBranchIds.join(";");
				}
			}
		}
		//嵌入子流程节点，fdRefId对应信息
		if(fdDynamicInfo.length>0){
			var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
			//修改当前流程图对象的节点下标及连线下标
			nowFlow.nodesIndex = nowNodeIndex+"";
			nowFlow.linesIndex = nowLineIndex+"";
			//为fdFlowContent重新设值
			processXMLObj.value = WorkFlow_BuildXMLString(nowFlow);
			//lbpm.globals.parseXMLObj();
			if(!lbpm.modifys){
				lbpm.modifys = {};
			}
			$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
			lbpm.lbpmDynamicInfo = fdDynamicInfo;
		}
	}

	//替换子流程节点的节点属性中的的节点Id
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

	//设置下一环节（即席子流程节点)
	lbpm.globals.setAdHocSubNode = function(){
		if ($("input[name='nextAdHocRouteId']").length == 1) {
			return;
		}
		var adHocSubFlowNode = lbpm.nodes[lbpm.nowAdHocSubFlowNodeId];
		var firstSubNode = lbpm.nodes[adHocSubFlowNode.startNodeId].endLines[0].endNode;

		//当前详版的流程图对象
		var nowFlow = WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());

		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
		// 重新进入即席子流程节点前要清除掉原来的子节点定义
		if (nextNodeObj.id == lbpm.nowAdHocSubFlowNodeId) {
			var getGroupSubStartLine = function(groupStartNodeId,flowInfo){
				for(var i=0;i<flowInfo.lines.length;i++){
					if(flowInfo.lines[i]["startNodeId"] == groupStartNodeId){
						return flowInfo.lines[i]; //获取组开始节点的流出线
					}
				}
				return null;
			};

			var deleteSubNodes = function(subNode, flowInfo){
				if (subNode.endLines.length > 0) {
					var targetNodeIndex = -1;
					for(var i=0;i<flowInfo.nodes.length;i++){
						if(flowInfo.nodes[i].id == subNode.id){
							var targetLineIndex = -1;
							for(var j=0;j<flowInfo.lines.length;j++){
								if(flowInfo.lines[j]["startNodeId"] == subNode.id){
									targetLineIndex = j;
									break;
								}
							}
							if (targetLineIndex != -1) {
								flowInfo.lines.splice(targetLineIndex,1); //移除连线
								targetNodeIndex = i;
								break;
							}
						}
					}
					if (targetNodeIndex != -1) {
						flowInfo.nodes.splice(targetNodeIndex,1); //移除组内子节点
					}

					deleteSubNodes(subNode.endLines[0].endNode, flowInfo);
				}
			};

			// 删除原组内子节点以及调整组内连线
			deleteSubNodes(firstSubNode,nowFlow);
			var subStartLine = getGroupSubStartLine(adHocSubFlowNode.startNodeId, nowFlow);
			subStartLine.endNodeId = adHocSubFlowNode.endNodeId;
			delete subStartLine.points;
		}

		//当前节点下标
		var nowNodeIndex = parseInt(nowFlow.nodesIndex, 10);
		//当前连线下标
		var nowLineIndex = parseInt(nowFlow.linesIndex, 10);
		//新旧节点ID对应关系,key:旧id，value:新id
		var nodeMap = {};
		// 添加被选中的route内的子节点到流程图内
		for (var i=0;i<lbpm.adHocRoutes.length; i++) {
			if (lbpm.adHocRoutes[i].startNodeId == lbpm.nextAdHocRouteId) {
				var adHocRoute = lbpm.adHocRoutes[i];
				var newNodes = [];
				for (var j=0;j<adHocRoute.subNodes.length; j++) {
					var subNodeObj = adHocRoute.subNodes[j].data;
					var subNode = $.extend(true, {}, subNodeObj);
					// 设置节点处理人id以及name信息
					subNode.routeId = lbpm.nextAdHocRouteId;
					subNode.sourceNodeId = subNodeObj.id;
					nodeMap[subNodeObj.id] = subNode.id = "N"+(++nowNodeIndex);
					subNode.x = -subNodeObj["x"];
					subNode.y = -subNodeObj["y"];
					subNode.groupNodeId = lbpm.nowAdHocSubFlowNodeId;
					subNode.groupNodeType = "adHocSubFlowNode";
					// 记录原始子节点ID到扩展属性中
					if(!subNode.extAttributes){
						subNode.extAttributes = [];
					}
					var extAttribute = [];
					extAttribute.XMLNODENAME = "attribute";
					extAttribute.name = "sourceSubId";
					extAttribute.value = subNodeObj.id;
					subNode.extAttributes.push(extAttribute);
					newNodes.push(subNode);
				}
				//替换可修改和必须修改的节点Id
				lbpm.globals.replaceModifyHandlerNodeIds(newNodes,nodeMap,nowFlow);
				var newLines = [];
				for(var j = 0;j<adHocRoute.subLines.length;j++){
					//复制被选中的route内的连线，根据记录新旧节点id对应关系修改连线的startNodeId，endNodeId
					var subLineObj = adHocRoute.subLines[j].data;
					var subLine = $.extend(true, {}, subLineObj);
					subLine.startNodeId = nodeMap[subLine.startNodeId];
					subLine.endNodeId = nodeMap[subLine.endNodeId];
					delete subLine.points;
					subLine.id = "L"+(++nowLineIndex);
					newLines.push(subLine);
				}

				//将被选中的route内的节点及连线合并到当前流程图对象中
				nowFlow.nodes = nowFlow.nodes.concat(newNodes);
				nowFlow.lines = nowFlow.lines.concat(newLines);

				var getGroupSubEndLine = function(groupEndNodeId,flowInfo){
					for(var j=0;j<flowInfo.lines.length;j++){
						if(flowInfo.lines[j]["endNodeId"] == groupEndNodeId){
							return flowInfo.lines[j];
						}
					}
					return null;
				}

				var line = getGroupSubEndLine(adHocSubFlowNode.endNodeId, nowFlow);
				var newLine = $.extend(true, {}, line);
				newLine.startNodeId = newNodes[newNodes.length-1].id;
				line.endNodeId = newNodes[0].id;
				newLine.id = "L"+(++nowLineIndex);
				delete newLine.points;
				delete line.points;
				nowFlow.lines.push(newLine);

				nowFlow.nodesIndex = nowNodeIndex+"";
				nowFlow.linesIndex = nowLineIndex+"";
			}
		}

		var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
		processXMLObj.value = WorkFlow_BuildXMLString(nowFlow);
		if(!lbpm.modifys){
			lbpm.modifys = {};
		}
		$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
	};

	submitFlow.getProcessXmlString = lbpm.globals.getProcessXmlString = function(){
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

	submitFlow.setModifyParameterOnSubmit = lbpm.globals.setModifyParameterOnSubmit = function() {
		if(lbpm.modifys){
			var fdFlowContent=$("[name='sysWfBusinessForm.fdFlowContent']")[0];
			var fdIsModify=$("input[name='sysWfBusinessForm.fdIsModify']")[0];
			var jsonArr=new Array();
			if(fdIsModify.value!="1"){
				var nodesModifyXML="";
				$.each(lbpm.modifys, function(index, nodeData) {
					nodesModifyXML+=WorkFlow_BuildXMLString(nodeData,lbpm.nodes[index].XMLNODENAME, true);
				});
				if(nodesModifyXML!=""){
					nodesModifyXML="<process><nodes>"+nodesModifyXML+"</nodes></process>";
					//fdFlowContent.value=nodesModifyXML;
					//附加操作类型
					var jsonObj={};
					jsonObj.xml=nodesModifyXML;
					if(lbpm.globals.toRefuseThisNodeHandlerChange){
						jsonObj.toRefuseThisNodeHandlerChange = lbpm.globals.toRefuseThisNodeHandlerChange;
					}
					var paramObj={};
					paramObj.param=jsonObj;
					paramObj.type="additions_modifyNodeAttribute";
					jsonArr.push(paramObj);
				}
			}else{
				fdFlowContent.value=lbpm.globals.getProcessXmlString();
				//附加操作类型
				var jsonObj={};
				jsonObj.type="additions_modifyProcess";
				if (lbpm.nowFreeSubFlowNodeId != null || lbpm.nowAdHocSubFlowNodeId != null || lbpm.lbpmEmbeddedInfo || lbpm.lbpmDynamicInfo) {
					jsonObj.type="additions_modifySubFlowNode";
					if(lbpm.lbpmEmbeddedInfo){
						jsonObj.lbpmEmbeddedInfo = lbpm.lbpmEmbeddedInfo;
					}
					if(lbpm.lbpmDynamicInfo){
						jsonObj.lbpmDynamicInfo = lbpm.lbpmDynamicInfo;
					}
				}
				jsonObj.field="fdFlowContent";
				jsonArr.push(jsonObj);
			}
			if(jsonArr.length>0) {
				var additionParamObj=$("input[name='sysWfBusinessForm.fdAdditionsParameterJson']")[0];
				additionParamObj.value=lbpm.globals.objectToJSONString(jsonArr);
			}
		}
	};

	//提交人校验
	require(["dijit/registry","dojo/topic","dojo/dom-construct","dojo/dom-style","mui/dialog/Dialog","mui/util","dojo/_base/array","mui/i18n/i18n!sys-mobile"],
		function(registry,topic,domConstruct,domStyle,Dialog,util,array,msg){


			window.createRolesListItem = function(props) {
				var itemRenderer = '<input type="checkbox" data-dojo-type="mui/form/CheckBox" name="_select_box_roles" value="!{value}" data-dojo-props="tag:\'li\',mul:false,text:\'!{text}\',checked:!{checked},pop:true">';
				itemRenderer = itemRenderer.replace(
					'!{text}', util.formatText(props.text.replace("'","&#039;"))).replace(
					'!{checked}', props.selected).replace(
					'!{value}', props.value);
				var item = domConstruct.toDom(itemRenderer);
				return item;
			};

			lbpm.globals.lbpmIdentityCheck= function(formObj, method, clearParameter, moreOptions){
				//暂存无需必填
				if(moreOptions && moreOptions.saveDraft){
					return true;
				}

				var handlerIdentityRow = document.getElementById("handlerIdentityRow");
				if(lbpm.nowNodeId=='N2' && Lbpm_SettingInfo['isShowDraftsmanStatus'] == 'true'
					&& Lbpm_SettingInfo['isPopupWindowRemindSubmitter'] == 'true' && handlerIdentityRow.style.display != 'none'){

					var rolesSelectObj = registry.byId('rolesSelectObj');
					if (rolesSelectObj == null) {
						return;
					}

					var handlerIdentityIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
					var handlerIdentityNamesObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoNames");

					var handlerIdentityIds = handlerIdentityIdsObj.value;
					var rolesIdsArray = handlerIdentityIds.split(";");
					var handlerIdentityNames = handlerIdentityNamesObj.value;
					var handlerRoleInfoNames = lbpm.globals.getHandlerRoleInfoNamesByOrgConfig(handlerIdentityIds);
					if(handlerRoleInfoNames){
						handlerIdentityNames = handlerRoleInfoNames;
					}
					var rolesNamesArray = handlerIdentityNames.split(";")
					if(rolesIdsArray.length <= 1){//只有一个岗位时直接跳过
						return true;
					}

					var dialogNode = domConstruct.create('div', {
						className : 'muiCheckBoxPopWarp'
					});
					var listNode = domConstruct.create('ul', {
						className : 'muiRadioGroupPopList'
					},dialogNode);

					var data = [];
					var rolesValue = rolesSelectObj.get('value');
					for (var i = 0; i < rolesIdsArray.length; i++) {
						var obj = {value: rolesIdsArray[i], text: rolesNamesArray[i], selected : false};
						if(rolesValue == rolesIdsArray[i]){
							obj.selected = true;
						}
						data.push(obj);
					}

					for(var i = 0;i<data.length;i++){
						var listItem = createRolesListItem(data[i]);
						listNode.appendChild(listItem);
					}

					var buttons = [{
						title:msg['mui.button.cancel'],
						fn : function(dialog) {
							dialog.hide();
						}
					},{
						title:msg['mui.button.ok'],
						fn : function(dialog) {
							array.forEach(dialog.htmlWdgts,function(wdt){
								if (wdt && wdt.name == '_select_box_roles' && wdt.checked) {
									rolesSelectObj.set('value',wdt.value);
									return false;
								}
							});
							lbpm.isLbpmIdentityCheck = true;
							dialog.hide();
							//回调
							Com_Submit(formObj, method, clearParameter, moreOptions);//回调Com_submit函数
						}
					}];
					var rolesDialog = Dialog.element({
						canClose : false,
						showClass : 'muiDialogElementShow muiFormSelect',
						element : dialogNode,
						buttons : buttons,
						position:'bottom',
						'scrollable' : false,
						'parseable' : true
					});
					var title = domConstruct.toDom("<div class='muiDialogElementButton_bottom' style='width:50%;float:left;'><bean:message bundle='sys-lbpmservice' key='lbpmProcess.processor.identity.validate.title' /></div>");
					domConstruct.place(title, rolesDialog.buttonsDom[0],'after');
					domStyle.set(title,'font-size','1.7rem');
					domStyle.set(rolesDialog.buttonsDom[0],'width','15%');
					domStyle.set(rolesDialog.buttonsDom[1],'width','15%');
					domStyle.set(title,'width','70%');
					return false;
				}
				return true;
			};
		});

	/**
	 *  版本冲突进行覆盖提交时需要先进行校验或者正常提交校验
	 * 目的是校验会审/会签支持人工决策（最后一个需要进行人工决策的处理）
	 */
	window.validateBeforeSumbitVersion = lbpm.globals.validateBeforeSumbitVersion = function(){
		if(!lbpm || !lbpm.nowNodeId){
			return true;
		}
		/*会审/会签人工决策校验*/
		//先进行操作判断，如果不满足给定的操作则默认通过，给定的操作是通过即将流向设置方法全局搜索得到的可能性，和产品沟通是通过和签字才有
		var opers = ["handler_pass","handler_giveup","drafter_submit","handler_sign"];
		if(lbpm.currentOperationType && opers.indexOf(lbpm.currentOperationType) == -1){
			return true;
		}
		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
		if(!currentNodeObj || !nextNodeObj
			|| (lbpm.nowProcessorInfoObj.type != "reviewWorkitem"
				&& lbpm.nowProcessorInfoObj.type != "signWorkitem")
			|| nextNodeObj.nodeDescType != "manualBranchNodeDesc"
			|| currentNodeObj.processType != "2"
			|| !lbpm.globals.isLastHandler()){
			return true;
		}

		//若是最后一个处理人（会审/会签+人工决策），需要将即将流程内容设置为可编辑，并校验
		lbpm.noValidateFutureNode = false;//需要校验
		var operationsTDContent = document.getElementById("operationsTDContent");
		var html = lbpm.globals.generateNextNodeInfo();
		lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationsTDContent);

		//执行校验进行定位
		return lbpm.globals.common_operationCheckForPassType();
	};

	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = lbpm.globals.submitFormEvent;
	require(["dojo/request","dojo/query"],function(request,query){
		//暂存时保存流程相关的参数
		Com_Parameter.event["submit"].push(function(formObj, method, clearParameter, moreOptions){
			var docStatus = document.getElementsByName("docStatus")[0].value;
			if(!(docStatus=="10" || docStatus=="11")){
				return true;
			}

			var fdModelId =  document.getElementsByName("sysWfBusinessForm.fdModelId")[0].value;
			var fdModelName =  document.getElementsByName("sysWfBusinessForm.fdModelName")[0].value;
			var fdKey =  document.getElementsByName("sysWfBusinessForm.fdKey")[0].value;

			if(moreOptions && !moreOptions.saveDraft){
				var url = Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp";
				var pjson = {
					"s_bean" : "lbpmTempStorageDataBean",
					"fdModelId" : fdModelId,
					"fdModelName" : fdModelName,
					"fdKey" : fdKey,
					"op" : "delete",
					"_d" : new Date().toString()
				};
				request.post(url,{data:pjson,handleAs:'json'}).then(
					function(data){
						//errcode=0
					},
					function(error){
						//errcode=1
					}
				);
				return true;
			}else{
				document.getElementById("sysWfBusinessForm.canStartProcess").value = "false";
			}

			var tdata = {};

			//如果有分支设置分支
			var futureNodeCheckboxs=query("input[name='futureNode'][type='checkbox']");
			//判断是并行分支还是人工决策分支（如checkbox类型则是并行分支）
			if(futureNodeCheckboxs.length>0){
				//启动并行分支是多选，所以单独处理
				tdata["futureNode"]="";
				query("input[name='futureNode']:checked").forEach(function(domObj){
					tdata["futureNode"]+=tdata["futureNode"]==""?domObj.value:","+domObj.value;
				});
			}
			else{
				query("input[name='futureNode']:checked").forEach(function(domObj){
					tdata["futureNode"]=domObj.value;
				});
			}


			//通知方式优先级
			query("input[name='sysWfBusinessForm.fdNotifyLevel']:checked").forEach(function(domObj){
				tdata["fdNotifyLevel"] = domObj.value;
			});

			//意见--auditNode
			var usageContent  = document.getElementsByName("fdUsageContent")[0];
			if(usageContent){
				var fdUsageContent =  usageContent.value;
				tdata["fdUsageContent"] = fdUsageContent;
			}
			var data = lbpm.globals.objectToJSONString(tdata);
			var url = Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp";
			var pjson = {
				"s_bean" : "lbpmTempStorageDataBean",
				"fdModelId" : fdModelId,
				"fdModelName" : fdModelName,
				"fdKey" : fdKey,
				"op" : "add",
				"fdData" :data,
				"_d" : new Date().toString()
			};
			request.post(url,{data:pjson,handleAs:'json'}).then(
				function(data){
					//errcode=0
				},
				function(error){
					//errcode=1
				}
			);
			return true;
		});
	});
	return submitFlow;
});
require(["dojo/ready","dojo/request","dojo/query","dojo/topic","dijit/registry"],function(ready,request,query,topic,registry){
	//暂存时加载流程相关的参数
	topic.subscribe('initComplete',function(){
		if(lbpm.constant.ISINIT){
			//初始化嵌入子流程信息
			lbpm.globals.setEmbeddedNodeInfo(true);
			//自由子流程有初始化节点时，且设置了筛选排序，将初始节点加入
			lbpm.globals.setFreeflowNodeInfo();
			//初始化动态子流程信息
			lbpm.globals.setDynamicNodeInfo(true);
		}
		var docStatus = document.getElementsByName("docStatus")[0].value;
		if(!(docStatus=="10" || docStatus=="11")){
			return ;
		}

		var fdModelId =  document.getElementsByName("sysWfBusinessForm.fdModelId")[0].value;
		var fdModelName =  document.getElementsByName("sysWfBusinessForm.fdModelName")[0].value;
		var fdKey =  document.getElementsByName("sysWfBusinessForm.fdKey")[0].value;
		var url = Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp";
		var pjson = {
			"s_bean" : "lbpmTempStorageDataBean",
			"fdModelId" : fdModelId,
			"fdModelName" : fdModelName,
			"fdKey" : fdKey,
			"op" : "get",
			"_d" : new Date().toString()
		};
		request.post(url,{data:pjson,handleAs:'json'}).then(
			function(json){
				if(json["errcode"]=="0"){
					var fdUsageContent = document.getElementsByName("fdUsageContent");
					if(fdUsageContent[0] != null){
						fdUsageContent[0].value=json["fdUsageContent"]||"";
					}
				}
			},
			function(error){
				//errcode=1
			}
		);
	});
});