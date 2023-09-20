define(["sys/lbpmservice/mobile/operation/operation_common_passtype","mui/dialog/Tip"],function(operationCommon,tip){
	var drafterSubmitOperation={};
	
	//起草人操作：提交文档
	var OperationClick = function(operationName){
		var processorInfo = lbpm.globals.analysisProcessorInfoToObject();
		if(processorInfo != null) {
			var nextNodeTD = document.getElementById("nextNodeTD");
			var handlerIdentityRow = document.getElementById("handlerIdentityRow");
			var handlerIdentityRowTr = document.getElementById("handlerIdentityRowTr");
			if(handlerIdentityRow){
				/*#144417-流程引擎关闭提交人身份开关，在移动端新建流程还是可以选择提交身份-开始*/
				if (Lbpm_SettingInfo.isShowDraftsmanStatus == "false") {
					lbpm.globals.hiddenObject(handlerIdentityRowTr,
						true)
					lbpm.globals.hiddenObject(handlerIdentityRow, true);
				} else {
					lbpm.globals.hiddenObject(handlerIdentityRowTr,
						false)
					lbpm.globals
						.hiddenObject(handlerIdentityRow, false);
				}
				//lbpm.globals.hiddenObject(handlerIdentityRow, false);
				/*#144417-流程引擎关闭提交人身份开关，在移动端新建流程还是可以选择提交身份-结束*/
			}
			if(nextNodeTD != null){
				lbpm.globals.generateNextNodeInfo(function(html){
					lbpm.globals.innerHTMLGenerateNextNodeInfo(html, nextNodeTD);
					lbpm.globals.hiddenObject(nextNodeTD.parentNode, false);
					//隐藏即将流向
					if(Lbpm_SettingInfo.isHideOperationsRow == "true" && lbpm.canHideNextNodeTr){
						lbpm.globals.hiddenObject(nextNodeTD.parentNode, true);
			 		}
				});
				
			} else {
				// 兼容文档状态为20情况下，人工分支不能显示问题
				var operationsTDContent = document.getElementById("operationsTDContent");
				if(operationsTDContent){
					var operationsTDTitle = document.getElementById("operationsTDTitle");
					operationsTDTitle.innerHTML = '即将流向';
					lbpm.globals.generateNextNodeInfo(function(html){
						lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationsTDContent);
						lbpm.globals.hiddenObject(operationsTDContent.parentNode, false);
					});
				}
			}
		}
	};
	//“驳回”操作的检查
	var OperationCheck = function(formObj, method, clearParameter, moreOptions){
		//暂存无需检查
		if(moreOptions && moreOptions.saveDraft){
			return true;
		}
		if (!lbpm.globals.isSelectAllManualNode()) {
			//<bean:message bundle='sys-lbpmservice' key='lbpmNode.manualNodeOnDraft.noSelectAll'/>
			tip["warn"]({text:"请选择分支节点走向！"});
			//alert("请选择分支节点走向！");
			return false;
		}
		return lbpm.globals.common_operationCheckForPassType();
	};	
	//"起草人提交"操作的获取参数
	var setOperationParam = function(){
		//设置起草人提交身份参数
		if (window.require) {
			var rolesSelectObj = dijit.registry.byId('rolesSelectObj');
			if(rolesSelectObj){
				lbpm.globals.setOperationParameterJson(rolesSelectObj.get('value'),"identityId", "param");
			}
		} else {
			var obj = document.getElementsByName("rolesSelectObj")[0];
			lbpm.globals.setOperationParameterJson(obj,"identityId", "param");
		}

		//流程结束后通知我
		$("#notifyDraftOnFinish").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"notifyOnFinish", "param");
		});
		
		//天后仍未完成--dayOfNotifyDrafter
		$("#dayOfNotifyDrafter").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"dayOfNotifyDrafter", "param");
		});
		$("#hourOfNotifyDrafter").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"hourOfNotifyDrafter", "param");
		});
		$("#minuteOfNotifyDrafter").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"minuteOfNotifyDrafter", "param");
		});

		//设置起草人选择人工决策节点分支参数
		var selectedManualNode = lbpm.globals.getSelectedManualNode();
		if(selectedManualNode.length>0){
			var param=lbpm.globals.objectToJSONString(selectedManualNode);
		    lbpm.globals.setOperationParameterJson(param, "draftDecidedFactIds", "param");
		}			
	};	
	
	drafterSubmitOperation['drafter_submit'] = lbpm.operations['drafter_submit'] = {
			click:OperationClick,
			check:OperationCheck,
			isPassType:true,
			setOperationParam:setOperationParam
	};	
	
	return drafterSubmitOperation;
});
