define(["dojo/_base/declare", 
	"dojo/topic", 
	"dijit/registry", 
	"mui/form/_ValidateMixin",
	"mui/form/_FormBase",
	"mui/i18n/i18n!sys-lbpmservice:lbpmservice",
	"dojo/query",
	"dojo/dom-attr",
	"mui/i18n/i18n!sys-lbpmservice:FlowChartObject.Lang.Node.needHandWritten",
	"mui/i18n/i18n!sys-lbpmext-auditpoint:lbpmExtAuditPoint.important.cannot.benullMobile"],
		function(declare, topic, registry, _ValidateMixin,FormBase, msg, query, domAttr, msg1, msg2){
	
	//流程校验配置
	var LbpmValidates = {
		'freeflowNodes':{//自由流节点
			error : lbpm.constant.FREEFLOW_MUSTAPPENDNODE,
			test  : function(v) {
				//暂存不需要校验
				if(isNotValidate()){
					return true;
				}
				if (lbpm.isFreeFlow && lbpm.globals.getNodeSize() <= 3) {
					return false;
				}
				return true;
			}
		},
		'optMethodGroup':{//操作类型
			error:lbpm.constant.VALIDATEOPERATIONTYPEISNULL,
			test:function(v){
				/*if(isNotValidate()){
					return true;
				}*/
                var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
                if (!operatorInfo) {
                    return true;
                }
				if(v || (optInfo && optInfo.value)){
					return true;
				}else{
					return false;
				}
			}
		},
		'fdUsageContent':{//意见必填校验
			error:lbpm.constant.opt.MustSignYourSuggestion,
			test:function(v){
				if(isNotValidate()){
					return true;
				}
				if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
					return lbpm.globals.validateMustSignYourSuggestion(true);
				}
				return true;
			}
		},
		'usageContentMaxLen':{//意见长度校验
			error:lbpm.constant.ERROR_FDUSAGECONTENT_MAXLENGTH.replace(/\{name\}/, lbpm.constant.OPINION).replace(/\{maxLength\}/, 4000),
			test:function(v){
				/*if(isNotValidate()){
					return true;
				}*/
				var fdUsageContent=lbpm.operations.getfdUsageContent();
				if (fdUsageContent != null && fdUsageContent.value != "") {
					var contentVal = fdUsageContent.value || "";
					var newvalue = contentVal.replace(/[^\x00-\xff]/g, "***");
					if (newvalue.length > 4000) {
						return false;
					}
				}
				return true;
			}
		},
		'privateOpinion':{
			error:msg['lbpmservice.auditnote.placeholder'],
			test:function(v){
				/*if(isNotValidate()){
					return true;
				}*/
				if (lbpm.isFreeFlow && Lbpm_SettingInfo.isPrivateOpinion == "true"){
					var switchWgt = registry.byId('privateOpinion');
					if (switchWgt && switchWgt.checked==true) {
						var myWgt = registry.byId('privateOpinionPerson');
						if(myWgt && !myWgt.curIds){
							return false;
						}
					}
				}
				return true;
			}
		},
		'futureNodeSelected':{//检查多个即将流向节点是否被选上,其实用了必填后就没必要加这个校验了，但是为了保留原来流程的样子，加上去也没问题
			error:lbpm.constant.CHKNEXTNODENOTNULL,
			test:function(v){
				if(isNotValidate() || lbpm.noValidateFutureNode == true){
					return true;
				}
				if(query("input[name='futureNode']").length > 0){
					//如果有分支设置分支
					var futureNodeCheckboxs=query("input[name='futureNode'][type='checkbox']");
					//判断是并行分支还是人工决策分支（如checkbox类型则是并行分支）
					if(futureNodeCheckboxs.length>0){
						var futureNodesChekBoxGroup=registry.byId("sys_lbpmservice_mobile_workitem_FutureNodesChekBoxGroup");
						if(futureNodesChekBoxGroup.value&&futureNodesChekBoxGroup.value!="")
							return true;
					}
					else{
						if (query("input[name='futureNode']:checked").length > 0)
							return true;
					}
				}else if(query("input[name='__futureNode__']").length > 0){
					//如果有分支设置分支
					var futureNodeCheckboxs=query("input[name='__futureNode__'][type='checkbox']");
					//判断是并行分支还是人工决策分支（如checkbox类型则是并行分支）
					if(futureNodeCheckboxs.length>0){
						var futureNodesChekBoxGroup=registry.byId("sys_lbpmservice_mobile_workitem_FutureNodesChekBoxGroup");
						if(futureNodesChekBoxGroup.value&&futureNodesChekBoxGroup.value!="")
							return true;
					}
					else{
						if (query("input[name='__futureNode__']:checked").length > 0)
							return true;
					}
				}
				return false;
			}
		},
		'manualFutureNodeIdSelect':{
			error:'请选择分支节点走向！',
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var isPass = false;
				if(!v){
					return false;
				}else{
					var values = e.values || [];
					for(var i=0; i<values.length; i++){
						if(values[i].value == v){
							isPass = true;
							break;
						}
					}
				}
				return isPass;
			}
		},
		'futureNodeObjs':{
			error:'{errorMsg}',
			test:function(v,elem,o){
				if(isNotValidate() || !lbpm.currentOperationType || lbpm.noValidateFutureNode == true){//打开流程审批页面不进行校验
					return true;
				}
				var checkedNode = null;
				var checkedNodes = [];
				var futureNodeObjs = document.getElementsByName("futureNode");
				for (var i = 0; i < futureNodeObjs.length; i++) {
					var futureNodeObj = futureNodeObjs[i];
					if (futureNodeObj.checked) {
						checkedNode = lbpm.globals.getNodeObj(futureNodeObj.value);
						checkedNodes.push(checkedNode);
						if(elem && checkedNode && checkedNode.id != elem.nodeid){
							continue;
						}
						var handlerIdsObj = document.getElementsByName("handlerIds["
								+ futureNodeObj.getAttribute("index") + "]")[0];
						if(handlerIdsObj == null){//兼容移动端
							handlerIdsObj = document.getElementsByName("handlerIds_"
									+ futureNodeObj.value)[0];
						}
						if (handlerIdsObj != null && handlerIdsObj.value == ""
								&& checkedNode && checkedNode.ignoreOnHandlerEmpty == "false") {
							o['errorMsg'] = lbpm.constant.VALIDATENEXTNODEHANDLERISNULL;
							return false;
						}
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
				//加入其它的checknodeid
				for(var i=0; i<checkedNodes.length; i++){
					var checkedNode = checkedNodes[i];
					if(allNextNodeArray.indexOf(checkedNode.id) == -1){
						allNextNodeArray.push(checkedNode.id);
					}
				}
				
				if(elem.nodeid && allNextNodeArray.indexOf(elem.nodeid) == -1){
					return true;//如果需要检测的节点不包括当前校验的节点，则跳过
				}
				
				// 检查未选中的节点是否是被选中节点的后续节点
				for (var i = 0; i < futureNodeObjs.length; i++) {
					var futureNodeObj = futureNodeObjs[i];
					if (futureNodeObj.checked || elem.nodeid) {
						continue;
					}

					//for (var j = 0; j < allNextNodeArray.length; j++) {
						//var furtureFromSelectedNodeId = allNextNodeArray[j];
						var furtureFromSelectedNodeId = elem.nodeid;
						if (futureNodeObj.value == furtureFromSelectedNodeId) {
							var handlerIdsObj = document.getElementsByName("handlerIds["
									+ futureNodeObj.getAttribute("index") + "]")[0];
							var furtureFromSelectedNodeObj = lbpm.globals.getNodeObj(futureNodeObj.value);
							if (handlerIdsObj != null && handlerIdsObj.value == ""
									&& furtureFromSelectedNodeObj && furtureFromSelectedNodeObj.ignoreOnHandlerEmpty == "false") {
								var fn= WorkFlow_getLangLabel(furtureFromSelectedNodeObj.name,furtureFromSelectedNodeObj["langs"],"nodeName");
								var cn= WorkFlow_getLangLabel(checkedNode.name,checkedNode["langs"],"nodeName");

								o['errorMsg'] = lbpm.constant.FLOWCONTENTMUSTMODIFYNODENEXTHANDLER
										.replace('{0}', furtureFromSelectedNodeObj.id)
										.replace('{1}', fn)
										.replace('{2}', checkedNode.id).replace('{3}', cn);
								return false;
							}
						}
					//}
				}
				
				var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
				// 判断节点为必须修改的节点
				if (elem.nodeid && !checkMustModifyHandlerNodeIdsNew(elem.nodeid, operatorInfo,o))
					return false;
				return true;
			}
		},
		'adHocSubFlowNodeSelected':{
			error:lbpm.constant.CHKNEXTNODENOTNULL,
			test:function() {
				if(isNotValidate()){
					return true;
				}
				if (query("input[name='nextAdHocRouteId']").length == 0)
					return true;
				if (query("input[name='nextAdHocRouteId']:checked").length > 0)
					return true;
				return false;
			}
		},
		'jumpToNodeRequired':{//驳回节点必填
			error:lbpm.workitem.constant.noRefuseNode,
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var input = query("#operationsTDContent [name='jumpToNodeIdSelectObj']")[0];
				if(v || (input && input.value)){
					return true;
				}else{
					o['refuse'] = lbpm.currentOperationLabel;
					return false;
				}
			}
		},
		'jumpRequired':{
			error:lbpm.workitem.constant.noRefuseNode,
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var input = query("#operationsTDContent [name='jumpToNodeIdSelectObj']")[0];
				if(v || (input && input.value)){
					return true;
				}else{
					o['jump'] = lbpm.currentOperationLabel;
					return false;
				}
			}
		},
		'commissionHandlerRequired':{//转办人员必填
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var input = query("#toOtherHandlerIds")[0]
			    if (v || (input && input.value)) {
			    	return true;
			    }else{
			    	o['errorMsg'] = lbpm.constant.opt.CommissionIsNull;
			    	return false;
			    }
			}
		},
		'additionSignHandlerRequired':{//补签人员必填
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var input = query("#toOtherHandlerIds")[0];
				if((input && input.value) || v){
					return true;
				}else{
					o['errorMsg'] = lbpm.constant.opt.AdditionSignIsNull;
					return false;
				}
			}
		},
		'assignhandlerRequired':{//加签人员必填
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var input = query("#toAssigneeIds")[0];
				if(v || (input && input.value)){
					return true;
				}else{
					o['errorMsg'] = lbpm.constant.opt.AssigneeIsNull + lbpm.currentOperationLabel + lbpm.constant.opt.Assignee;
					return false;
				}
			}
		},
		'assignCancelHandlerRequired':{//收回加签人员必填
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var WorkFlow_CancelAssignWorkitemsGroup = registry.byId('WorkFlow_CancelAssignWorkitemsGroup');
				var val = WorkFlow_CancelAssignWorkitemsGroup.get('value');
				if(val == null || val == '') {
					o["errorMsg"] = lbpm.constant.opt.AssignNeedSelectCanceler;
					return false;
				}
				return true;
			}
		},
		'communicateHandlerRequired':{//沟通人员必填
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var input = query("#toOtherHandlerIds")[0];
				if(v || (input && input.value)){
					return true;
				}else{
					o['errorMsg'] = lbpm.constant.opt.CommunicateIsNull + "沟通" + lbpm.constant.opt.CommunicatePeople;
					return false;
				}
			}
		},
		'cancelCommunicateHandlerRequired':{//取消沟通人员必填
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var WorkFlow_CelRelationWorkitemsGroup = registry.byId('WorkFlow_CelRelationWorkitemsGroup');
				var val = WorkFlow_CelRelationWorkitemsGroup.get('value');
				if(val == null || val == '') {
					o['errorMsg'] = lbpm.constant.opt.CommunicateNeedSelectCanceler;
					return false;
				}
				return true;
			}
		},
		'cancelconbranchRequired':{//取消分支必填
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var Checkbox_cancelConbranchsGroup = registry.byId('cancelConbranchsGroup');
				var val = Checkbox_cancelConbranchsGroup.get('value');
				if(val == null || val == '') {
					o['errorMsg'] = lbpm.constant.opt.CancelbranchIsNull;
					return false;
				}
				return true;	
			}
		},
		'restartConbranchRequired':{//重启分支
			error:"{errorMsg}",
			test:function(v,e,o){
				if(isNotValidate()){
					return true;
				}
				var Checkbox_restartConbranchsGroup = registry.byId('restartConbranchsGroup');
				var val = Checkbox_restartConbranchsGroup.get('value');
				if(val == null || val == '') {
					o['errorMsg'] = lbpm.constant.opt.RestartconbranchIsNull;
					return false;
				}
				return true;
			}
		},
		'hwSignRequired':{//手写签批必填校验
			error:msg1['FlowChartObject.Lang.Node.needHandWritten'],
			test:function(){
				var _imageDivSubmit = query("#imgUl");
				if (!_imageDivSubmit || _imageDivSubmit.length === 0 || _imageDivSubmit.innerHTML() == "") {
					return false;
				}
				return true;
			}
		},
		'auditPointRequired':{//审批要点必填
			error:msg2["lbpmExtAuditPoint.important.cannot.benullMobile"],
			test:function(){
				if(isNotValidate()){
					return true;
				}
				var opr=query("#operationMethodsGroup")[0] ? query("#operationMethodsGroup")[0].value : "";
				if(opr.indexOf("handler_pass")>-1
						||opr.indexOf("handler_sign")>-1
						||opr.indexOf("drafter_submit")>-1){
					var records=query("input[type='hidden'][name*='lbpmext_auditpoint_chk_']");
					for(var i=0;i<records.length;i++){
						var e=records[i];
						var fdId=e.name.substring('lbpmext_auditpoint_chk_'.length,e.name.length);
						var source = query("[id='"+fdId+"']")[0];
						var fdIdImportant=domAttr.get(source,"lbpmext-auditpoint-data-important");
						var fdIsPass=e.value;
						if("true"==fdIdImportant&&!"true"==fdIsPass){
							return false;
						}
					}
				}
				
				lbpm.globals.extAuditPointParameter();
				return true;
			}
		}
	}
	
	window.isNotValidate = function(){
		var saveDraftButtonElem = registry.byId("saveDraftButton");
		if (saveDraftButtonElem && saveDraftButtonElem.saveDraft) {
			return true;
		} else {
			return false;
		}
	}
	
	window.checkMustModifyHandlerNodeIdsNew=function(nextNodeId, operatorInfo, o) {
		var currentNodeObj = lbpm.globals.getCurrentNodeObj();
		var roleType = lbpm.constant.ROLETYPE;
		//for (var i = 0; i < nextNodeArray.length; i++) {
			if (roleType == ''
					&& lbpm.globals.checkModifyNodeAuthorization(currentNodeObj,
							nextNodeId)) {
				var nextNode = lbpm.globals.getNodeObj(nextNodeId);
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
									langNodeName=langNodeName.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
									o['errorMsg']=nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL;
									return false;
								}
							}
						} else if (lbpm.operations[lbpm.currentOperationType].isPassType) {
							if (lbpm.globals.judgeIsNecessaryAlert(nextNode)) {
								var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
								langNodeName=langNodeName.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
								o['errorMsg']=nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL;
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
										langNodeName=langNodeName.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
										if (names.indexOf("elementDisabled")!=-1) {
											//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED,width:'260',height:'150'});
											o['errorMsg']=nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED;
											return false;
										}
										if (names.indexOf("postEmpty")!=-1) {
											//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY,width:'260',height:'150'});
											o['errorMsg']=nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY;
											return false;
										}
										if (names=="" && nextNode.ignoreOnHandlerEmpty != "true") {
											//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL,width:'260',height:'150'});
											o['errorMsg']=nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL;
											return false;
										}
									}
								}
							} else if (lbpm.operations[lbpm.currentOperationType].isPassType) {
								if (lbpm.globals.judgeIsNecessaryAlert(nextNode)) {
									var langNodeName = WorkFlow_getLangLabel(nextNode.name,nextNode["langs"],"nodeName");
									langNodeName=langNodeName.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
									if (names.indexOf("elementDisabled")!=-1) {
										//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED,width:'260',height:'150'});
										o['errorMsg']=nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED;
										return false;
									}
									if (names.indexOf("postEmpty")!=-1) {
										//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY,width:'260',height:'150'});
										o['errorMsg']=nextNode.id + "." + langNodeName + lbpm.constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY;
										return false;
									}
									if (names=="" && nextNode.ignoreOnHandlerEmpty != "true") {
										//tip.tip({icon:'mui mui-wrong', text:nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL,width:'260',height:'150'});
										o['errorMsg']=nextNode.id + "." + langNodeName + " " + lbpm.constant.MUSTMODIFYHANDLERNODEIDSISNULL;
										return false;
									}
								}
							}
						}
					}
				}
			//}
		}
		return true;
	};
	
	return declare("sys/lbpmservice/mobile/common/LbpmValidateMixin",[_ValidateMixin],{

		postCreate: function() {
			this.inherited(arguments);
			this.subscribe("mui/form/validationInitFinish","_addLbpmValidate");
		},

		validate : function(elements) {
			if(this.showType == 'dialog'){
				var elems = [];
				if (elements) {
					elems = elems.concat(elements);
				}
				var extElems = this.getValidateElements();
				if (extElems) {
					elems = elems.concat(extElems);
				}
				if (this.notValRequired != undefined) {
					if (this.notValRequired) {
						// 移除必填校验
						elems = this.removeElementValidate(elems,
								"required");
					} else {
						// 重置校验
						elems = this.resetElementValidate(elems);
					}
				}
				var result = true;
				var first = null;
				var errors = [];
				for (var i = 0; i < elems.length; i++) {
					if (!this._validation.validateElement(elems[i])) {
						if (result) {
							first = elems[i];
							result = false;
						}
						errors.push(elems[i]);
					}
				}
				if (first != null) {
					var scollDom = first;
					if (first instanceof FormBase) {
						scollDom = first.domNode;
					}
					if(scollDom == query("#hwSignEmpty")[0]){
						//手写签批特殊处理
						scollDom = query("#hwSignEmptyDiv")[0];
					}
					var domOffsetTop = this
							._getDomOffsetTop(scollDom);
					
					topic.publish("/lbpm/validate/toTop", this, {
						y : 0 - domOffsetTop + 110
					});
				}
				topic.publish("/mui/validate/afterValidate", this,
						errors);
				topic.publish("/mui/list/resize");
				return result;
			}else{
				this.inherited(arguments);
			}
		},
		
		_addLbpmValidate:function(wgt){
			for (var type in LbpmValidates) {
				wgt._validation.addValidator(type, LbpmValidates[type].error, LbpmValidates[type].test);
			}
		}
	});
});