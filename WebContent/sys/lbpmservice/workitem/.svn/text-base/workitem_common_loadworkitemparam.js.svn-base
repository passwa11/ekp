lbpm.onLoadEvents.once.push(function(){ 
	if(lbpm.nowProcessorInfoObj==null) return;
	lbpm.globals.loadWorkflowInfo();
	lbpm.globals.loadDefaultParameters();
	setTimeout("lbpm.globals.getThroughNodes()",200);
	if(lbpm.defaultSelectedTaskId){
		$("#operationMethodsRow").hide();
		$("#operationMethodsGroup input[name='oprGroup']:checked").click();
	}
});   

lbpm.globals.loadWorkflowInfo=function(){
	var processorInfoObj = lbpm.globals.getProcessorInfoObj();
	if(processorInfoObj != null){
		var operationItemsRow = $("#operationItemsRow")[0];
		if(operationItemsRow != null){
			var operationItemsSelect = $("[name='operationItemsSelect']")[0];
		  	if(operationItemsSelect != null){
			  	while(operationItemsSelect.childNodes.length > 0){
			  		operationItemsSelect.removeChild(operationItemsSelect.childNodes[0]);
	 		 	}
			  	var selectedIndex = 0;
				for(var i=0; i < processorInfoObj.length; i++){
					var processInfo = processorInfoObj[i];
					var parentHandlerName=(processInfo.parentHandlerName?processInfo.parentHandlerName+"：":"");
					var langNodeName = WorkFlow_getLangLabel(lbpm.nodes[processInfo.nodeId].name, lbpm.nodes[processInfo.nodeId]["langs"],"nodeName");
					var processInfoShowText = (_lbpmIsHideAllNodeIdentifier())? (parentHandlerName + langNodeName) : (processInfo.nodeId +"."+ parentHandlerName + langNodeName);
					if (lbpm.nodes[processInfo.nodeId].nodeDescType == lbpm.constant.NODETYPE_JOIN) {
						if (processInfo.parameter) {
							var processInfoParamJson = JSON.parse(processInfo.parameter);
							if (processInfoParamJson.conBranchSourceId) {
								var conBranchSourceId = processInfoParamJson.conBranchSourceId;
								if (conBranchSourceId.indexOf("N") == 0){
									processInfoShowText += "(" + conBranchSourceId + "."
									+ WorkFlow_getLangLabel(lbpm.nodes[conBranchSourceId].name, lbpm.nodes[conBranchSourceId]["langs"],"nodeName")
									+ ")";
								} else if (conBranchSourceId.indexOf("L") == 0) {
									processInfoShowText += "(" + conBranchSourceId + "."
									+ WorkFlow_getLangLabel(lbpm.lines[conBranchSourceId].name, lbpm.lines[conBranchSourceId]["langs"],"name")
									+ ")";
								}
							}
						}
					}
					if(processorInfoObj[i].expectedName){
						processInfoShowText += "("+processorInfoObj[i].expectedName+")";
					}
					var groupNodeId = lbpm.nodes[processorInfoObj[i].nodeId].groupNodeId;
					if (groupNodeId != null) {
						processInfoShowText = processInfoShowText + "【" + groupNodeId + "."
						+ WorkFlow_getLangLabel(lbpm.nodes[groupNodeId].name, lbpm.nodes[groupNodeId]["langs"],"nodeName")
						+ "】";
					}
					option = document.createElement("option");
					option.appendChild(document.createTextNode(processInfoShowText));
					option.value=i;
					operationItemsSelect.appendChild(option);
					if(lbpm.defaultSelectedTaskId){
						if(lbpm.defaultSelectedTaskId == processorInfoObj[i].id){
							selectedIndex = i;
							
						}
					}else{
						if (processorInfoObj[i] == lbpm.nowProcessorInfoObj) {
							selectedIndex = i;
						}
					}
				}
				operationItemsSelect.selectedIndex = selectedIndex;
				if(lbpm.defaultSelectedTaskId){
					$(operationItemsSelect).change();
					$(operationItemsSelect).parent().find("span:eq(0)").text($(operationItemsSelect).find("option:selected").text());
				}
		  	}
		  	if(processorInfoObj.length == 1) lbpm.globals.hiddenObject(operationItemsRow, true); else lbpm.globals.hiddenObject(operationItemsRow, false);
		}	
	}
};
function _lbpmIsHideAllNodeIdentifier(){
	var isHideAllNodeIdentifier = false;
	if (Lbpm_SettingInfo &&
		Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType  === "isHideAllNodeIdentifier"){
		isHideAllNodeIdentifier = true;
	}
	return isHideAllNodeIdentifier;

}
//设置WorkitemXML的初使值
lbpm.globals.loadDefaultParameters=function(){
	var operatorInfo = lbpm.globals.getProcessorInfoObj();
	if(operatorInfo == null){
		lbpm.globals.validateControlItem();//当没有工作项时隐藏相关按钮 20120627 by luorf
		return;
	}
	var operationItemsSelect = $("[name='operationItemsSelect']")[0];
	lbpm.globals.operationItemsChanged(operationItemsSelect,true);
	setTimeout(function(){
		delete lbpm.isLastHandler;
		lbpm.LastHandlernotUseCache = true;
	},200);
}

//初使化WorkitemParameter
lbpm.globals.initialWorkitemParams=function(){	
	
	var processorInfo = lbpm.globals.analysisProcessorInfoToObject();
	if(processorInfo == null){
		return;
	}
	
	lbpm.globals.getCurrentNodeDescription();
	
	var followInfo = lbpm.globals._getFollowedInfo();
	var isFollowed = followInfo&&followInfo.isFollowed == "true" ? true : false;
	//流程结束后通知审批人
	var notifyOnFinishObj = $("#notifyOnFinish")[0];
	if(notifyOnFinishObj != null && lbpm.flowcharts["notifyOnFinish"] && (isFollowed == false)){
		notifyOnFinishObj.checked = lbpm.flowcharts["notifyOnFinish"] == "true"?true:false;
	}
	
	//流程结束后通知起草人
	var notifyDraftOnFinishObj = $("#notifyDraftOnFinish")[0];
	if(notifyDraftOnFinishObj != null && lbpm.flowcharts["notifyDraftOnFinish"] && (isFollowed == false)){
		notifyDraftOnFinishObj.checked = lbpm.flowcharts["notifyDraftOnFinish"] == "true"?true:false;
	}
	
	//流程变化通知我（流程跟踪）
	var notifyForFollowObj = $("#notifyForFollow")[0];
	
	if(notifyForFollowObj != null && isFollowed){
		notifyForFollowObj.checked = true;
		//如果是流程跟踪。则显示出流程跟踪的选项 #172444
		notifyForFollowObj.parentNode.style.display = '';
	}
	
	//流程变化通知我（流程跟踪）与 流程结束后通知审批人 互斥
	if(notifyOnFinishObj != null && notifyForFollowObj != null){
		if(notifyOnFinishObj.checked == true){
			notifyForFollowObj.disabled = true;
		} else if(notifyForFollowObj != null && notifyForFollowObj.checked == true){
			notifyOnFinishObj.disabled = true;
		}
		notifyOnFinishObj.onclick = function(){
			if($("#notifyOnFinish")[0].checked){
				$("#notifyForFollow")[0].checked =false;
				$("#notifyForFollow")[0].disabled =true;
			} else {
				$("#notifyForFollow")[0].disabled =false;
			}
		}
		notifyForFollowObj.onclick = function(){
			if($("#notifyForFollow")[0].checked){
				$("#notifyOnFinish")[0].checked =false;
				$("#notifyOnFinish")[0].disabled =true;
			} else {
				$("#notifyOnFinish")[0].disabled =false;
			}
		}
	}
	
	//流程变化通知我（流程跟踪）与 流程结束后通知起草人 互斥
	if(notifyDraftOnFinishObj != null && notifyForFollowObj != null){
		if(notifyDraftOnFinishObj.checked == true){
			notifyForFollowObj.disabled = true;
		} else if(notifyForFollowObj != null && notifyForFollowObj.checked == true){
			notifyDraftOnFinishObj.disabled = true;
		}
		notifyDraftOnFinishObj.onclick = function(){
			if($("#notifyDraftOnFinish")[0].checked){
				$("#notifyForFollow")[0].checked =false;
				$("#notifyForFollow")[0].disabled =true;
			} else {
				$("#notifyForFollow")[0].disabled =false;
			}
		}
		notifyForFollowObj.onclick = function(){
			if($("#notifyForFollow")[0].checked){
				$("#notifyDraftOnFinish")[0].checked =false;
				$("#notifyDraftOnFinish")[0].disabled =true;
			} else {
				$("#notifyDraftOnFinish")[0].disabled =false;
			}
		}
	}
	
	//系统通知方式
	var systemNotifyTypeTD = document.getElementById("systemNotifyTypeTD");
	if(systemNotifyTypeTD != null){
		//var currentNodeObj = lbpm.globals.getCurrentNodeObj();
		//var notifyType = (currentNodeObj && currentNodeObj["notifyType"]) || lbpm.flowcharts["notifyType"];
		//if(notifyType) {
		// 如果没有选中的操作则清空通知方式,有默认选中就不清空
		if($("input[name='oprGroup']:checked") && $("input[name='oprGroup']:checked").length==0){
			// 清空通知方式
			var notifyType = "";
			WorkFlow_RefreshNotifyType("systemNotifyTypeTD", notifyType);
			var systemNotifyTypeObj = document.getElementsByName("sysWfBusinessForm.fdSystemNotifyType")[0];
			if(systemNotifyTypeObj != null){
				systemNotifyTypeObj.value = notifyType;
			}
		}

		//}
		
		// 如果是一个选项，就隐藏
		var fields = systemNotifyTypeTD.getElementsByTagName("INPUT");
		if (fields.length <= 2) {
			systemNotifyTypeTD.parentNode.style.display = 'none';
		}
	}
	
	//通知方式优先级 add by wubing date:2014-09-18
	var notifyLevelTD = document.getElementById("notifyLevelTD");
	if(notifyLevelTD != null){
		var fdNotifyLevelObj = document.getElementsByName("sysWfBusinessForm.fdNotifyLevel");
		if(fdNotifyLevelObj[0] != null){
			var levelObj = lbpm.globals._getProcessParameterFromAjax({paramName:"notifyLevel"});
			var levelValue = levelObj["notifyLevel"];
			if(levelValue==""){
				levelValue = 3;
			}

			for(var i=0;i<fdNotifyLevelObj.length;i++){
				if(fdNotifyLevelObj[i].value==levelValue){
					fdNotifyLevelObj[i].checked=true;
				}
			}
		}
	}

	//=================

};

//add by wubing date:2014-09-28，获取流程参数值(如jsonObj:{paramName:"notifyLevel"})
lbpm.globals._getProcessParameterFromAjax = function(jsonObj) {
	var data = new KMSSData();
	data.AddBeanData("lbpmProcessParameterAccessService&processId="+lbpm.modelId+"&"+$.param(jsonObj));
	var result = data.GetHashMapArray();
	if(result && result[0] && result[0].param){
		return JSON.parse(result[0].param);
	}
	return {};
}