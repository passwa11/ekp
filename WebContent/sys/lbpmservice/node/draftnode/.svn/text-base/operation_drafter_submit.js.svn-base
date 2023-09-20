(function(operations) {
	operations['drafter_submit'] = {
			click:OperationClick,
			check:OperationCheck,
			isPassType:true,
			isHideOperationsRow : true,
			setOperationParam:setOperationParam
	};	

	//起草人操作：提交文档
	function OperationClick(operationName){
		var processorInfo = lbpm.globals.analysisProcessorInfoToObject();
		if(processorInfo != null) {
			var nextNodeTD = document.getElementById("nextNodeTD");
			var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
			var calcBranchLabel = '&nbsp;<label id="calc" style="color:#dd772c;cursor:pointer;" onclick="lbpm.globals.calcBranch();">'
				+ lbpm.constant.opt.calcBranch;
			if(nextNodeTD != null){
				//初始化即将流向时，需先初始化提交人身份
				typeof lbpm.globals.showHandlerIdentityRow != "undefined" && lbpm.globals.showHandlerIdentityRow();
				var html = lbpm.globals.generateNextNodeInfo();
				lbpm.globals.innerHTMLGenerateNextNodeInfo(html, nextNodeTD);
				if(nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
					var nextNodeTDTitle = document.getElementById("nextNodeTDTitle");
					nextNodeTDTitle.innerHTML=nextNodeTDTitle.innerHTML + calcBranchLabel;
				}
				lbpm.globals.hiddenObject(nextNodeTD.parentNode, false);
			} else {
				// 兼容文档状态为20情况下，人工分支不能显示问题
				var operationsTDContent = document.getElementById("operationsTDContent");
				if(operationsTDContent){
					var operationsTDTitle = document.getElementById("operationsTDTitle");
					operationsTDTitle.innerHTML = lbpm.constant.opt.handlerOperationTypepass;
					var html = lbpm.globals.generateNextNodeInfo();
					lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationsTDContent);
					if(nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
						operationsTDTitle.innerHTML=operationsTDTitle.innerHTML + calcBranchLabel;
					}
					lbpm.globals.hiddenObject(operationsTDContent.parentNode, false);
				}
			}
		}
	};
	//“驳回”操作的检查
	function OperationCheck(workitemObjArray){
		var target = Com_GetEventObject();
		if(Com_Parameter.preOldSubmit!=null){
			target = Com_Parameter.preOldSubmit;
		}
		if((target && target.currentTarget && target.currentTarget.title == lbpm.constant.opt.savedraft) || (target && target.srcElement && target.srcElement.innerText == lbpm.constant.opt.savedraft) 
			|| (target && target.srcElement && target.srcElement.title == lbpm.constant.opt.savedraft)){
			return true;
		}
		if (!lbpm.globals.isSelectAllManualNode()) {
			alert(lbpm.constant.opt.noSelectAll);
			// 标签页是否展开
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
			$('html, body').animate({
		        scrollTop: $("#notifyLevelRow").offset().top - 370
		    }, 800); // scrollIntoView
			return false;
		}
		return lbpm.globals.common_operationCheckForPassType(workitemObjArray);
	};	
	//"起草人提交"操作的获取参数
	function setOperationParam()
	{
		//设置起草人提交身份参数
		if (window.require) {
			var rolesSelectObj = dijit.registry.byId('rolesSelectObj');
			lbpm.globals.setOperationParameterJson(rolesSelectObj.get('value'),"identityId", "param");
		} else {
			var obj = document.getElementsByName("rolesSelectObj")[0];
			lbpm.globals.setOperationParameterJson(obj,"identityId", "param");
		}

		//流程结束后通知我
		$("#notifyDraftOnFinish").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"notifyOnFinish", "param");
		});
		
		//流程变化通知我 --notifyForFollow add by linbb date:2017-06-15
		$("#notifyForFollow").each(function(i){
			lbpm.globals.setOperationParameterJson(this,"notifyForFollow", "param");
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
})(lbpm.operations);