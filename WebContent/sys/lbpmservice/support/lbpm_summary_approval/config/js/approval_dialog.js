//初始化常用意见
function initCommonUsages(){
	var kmssData = new KMSSData();
	kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesInfo");
	var result = kmssData.GetHashMapArray();
	if(result && result[0]){
		var names = result[0].usagesInfo ? decodeURIComponent(result[0].usagesInfo) : null;
		var usageContents;
		if (names != null && names != "") {
			usageContents = names.split("\n");
		}
		var commonUsageObj = document.getElementsByName("commonUsages");
		if (commonUsageObj != null && commonUsageObj.length > 0) {
			commonUsageObj = commonUsageObj[0];
			while (commonUsageObj.childNodes.length > 0) {
				commonUsageObj.removeChild(commonUsageObj.childNodes[0]);
			}
			option = document.createElement("option");
			option.appendChild(document.createTextNode("请选择"));
			option.value = "";
			commonUsageObj.appendChild(option);
			if (usageContents != null) {
				for ( var i = 0; i < usageContents.length; i++) {
					option = document.createElement("option");
					var usageContent = usageContents[i];
					while (usageContent.indexOf("nbsp;") != -1) {
						usageContent = usageContent.replace("&nbsp;", " ");
					}
					var optTextLength = 30;
					var optText = usageContent.length > optTextLength ? usageContent
						.substr(0, optTextLength) + '...'
						: usageContent;
					option.appendChild(document.createTextNode(optText));
					option.value = usageContent;
					commonUsageObj.appendChild(option);
				}
			}
		}
	}
}
//修改审批意见
function changeUsageContent(obj){
	if (obj.selectedIndex > 0){
		clearDefaultUsageContent(parent.operationType);
		var val = $("[name='fdUsageContent']").val() + $(obj).val();
		$("[name='fdUsageContent']").val(val);
	}
}
//删除默认意见
function clearDefaultUsageContent(operationType){
	var defalutUsage = "";
	defalutUsage = getOperationDefaultUsage(operationType);
	var usageContent = document.getElementsByName("fdUsageContent")[0];
	if (usageContent && defalutUsage && usageContent.value.replace(/\s*/ig, '') == defalutUsage.replace(/\s*/ig, '')) {
		usageContent.value = "";
	}
}
//获取默认审批意见
function getOperationDefaultUsage(operationType){
	if(operationType == 'handler_pass'){
		return window.defaultUsageContent;
	}else if(operationType == 'handler_refuse'){
		return window.defaultUsageContent_refuse;
	}else{
		return "";
	}
}
//设置默认的审批意见
function setDefaultUsageContent(operationType){
	var defalutUsage = "";
	defalutUsage = lbpm.globals.getOperationDefaultUsage(operationType);
	usageContent = usageContent || document.getElementsByName("fdUsageContent")[0];
	// 审批意见为空时才设置默认审批意见
	if (usageContent && !usageContent.value.replace(/\s*/ig, '')) {
		usageContent.value = defalutUsage;
	}
}
//初始化默认审批意见
function initOperationDefaultUsage(processId,nodeFactId){
	//获取节点自定义
	var data = new KMSSData();
	data.AddBeanData("lbpmSummaryApprovalService&fdProcessId="+processId+"&fdNodeFactId="+nodeFactId);
	data = data.GetHashMapArray();
	if(data.length > 0){
		if(data[0].customizeUsageContent!=null){
			window.defaultUsageContent = unescape(data[0].defaultUsageContent);
		}
		if(data[0].customizeUsageContentRefuse!=null){
			window.defaultUsageContent_refuse = unescape(data[0].defaultUsageContent_refuse);
		}

		if(data[0].isPassContentRequired!=null){
			window.isPassContentRequired = data[0].isPassContentRequired;
		}
		if(data[0].isRefuseContentRequired!=null){
			window.isRefuseContentRequired = data[0].isRefuseContentRequired;
		}
	}else{
		//获取全局
		data = new KMSSData();
		data.AddBeanData("lbpmUsageContentService");
		data = data.GetHashMapArray();
		if(data.length>0){
			if(data[0].defaultUsageContent!=null){
				window.defaultUsageContent = unescape(data[0].defaultUsageContent);
			}
			if(data[0].defaultUsageContent_refuse!=null){
				window.defaultUsageContent_refuse = unescape(data[0].defaultUsageContent_refuse);
			}

			if(data[0].isPassContentRequired!=null){
				window.isPassContentRequired = data[0].isPassContentRequired;
			}
			if(data[0].isRefuseContentRequired!=null){
				window.isRefuseContentRequired = data[0].isRefuseContentRequired;
			}
		}
	}
}
//初始化审批意见框
function initUsageContent(operationType){
	var defaultUsage = "";
	if(operationType == 'handler_pass'){
		defaultUsage = window.defaultUsageContent;
	}else if(operationType == 'handler_refuse'){
		defaultUsage = window.defaultUsageContent_refuse
	}
	$("[name='fdUsageContent']").val(defaultUsage);
}
//初始化是否必填
function initRequired(operationType){
	if((operationType == 'handler_pass' && window.isPassContentRequired) || (operationType == 'handler_refuse' && window.isRefuseContentRequired)){
		$("#mustSignStar").show();
		window.requiredUsageContent = true;
	}else{
		$("#mustSignStar").hide();
		window.requiredUsageContent = false;
	}
}
//初始化是否显示流程名称
function initProcessInfo(processName){
	if(processName){
		$("#processName").text(processName);
		$("#processInfo").show();
	}
}
Com_AddEventListener(window,'load',function(){
	var operationType = parent.operationType;
	var processId = parent.processId;
	var nodeFactId = parent.nodeFactId;
	var processName = parent.processName;
	initProcessInfo(processName);
	initOperationDefaultUsage(processId,nodeFactId);
	//初始化是否必填
	initRequired(operationType);
	initCommonUsages();
	initUsageContent(operationType);
})