//初使化常用审批语
lbpm.onLoadEvents.delay.push( function() {
	lbpm.globals.initialCommonUsages();
});
lbpm.globals.initialCommonUsages = function(notUseCache) {
	var kmssData = new KMSSData();
	if(notUseCache){
		kmssData.UseCache = false;
	}
	kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesInfo");
	var result = kmssData.GetHashMapArray();
	if(result && result[0]){
		var names = result[0].usagesInfo ? decodeURIComponent(result[0].usagesInfo) : null;
		var usageContents;
		if (names != null && names != "") {
			usageContents = names.split("\n");
		}
		lbpm.globals.initialCommonUsageObj("commonUsages",
			usageContents);
		lbpm.globals.initialCommonUsageObj("commonSimpleUsages",
			usageContents);
		//初始化审批操作控件常用审批意见 作者 曹映辉 #日期 2015年1月7日
		$("select[name*='commonUsages_']").each(function(){
			lbpm.globals.initialCommonUsageObj($(this).attr("name"),
				usageContents);
		});
		if(lbpm.approveType == "right"){
			$(".lui-lbpm-opinion-outerBox .commonUsedOpinionList ul").html("");
			if(usageContents != null){
				for ( var i = 0; i < usageContents.length; i++) {
					var usageContent = usageContents[i];
					while (usageContent.indexOf("nbsp;") != -1) {
						usageContent = usageContent.replace("&nbsp;", " ");
					}
					//处理xss攻击
					usageContent=Com_HtmlEscape(usageContent);
					/*var optTextLength = 20;
					var optText = usageContent.length > optTextLength ? usageContent
							.substr(0, optTextLength) + '...'
							: usageContent;*/
					$(".lui-lbpm-opinion-outerBox .commonUsedOpinionList ul").append("<li style='white-space: nowrap;overflow: hidden; text-overflow: ellipsis; display: block' data-value='"+usageContent+"'>"+usageContent+"</li>")
				}
				$(".commonUsedOpinionList").css("height",((usageContents.length>6?6:usageContents.length)+1)*40);
			}else{
				$(".commonUsedOpinionList").css("height",40);
			}
		}
	}
	lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = "true";
	var kmssData2 = new KMSSData();
	if(notUseCache){
		kmssData2.UseCache = false;
	}
	kmssData2.AddBeanData("lbpmUsageTarget&type=getUsagesIsAppend");
	var result = kmssData2.GetHashMapArray();
	if(result && result[0]){
		var isAppend = result[0].isAppend ? result[0].isAppend : null;
		if (isAppend != null && isAppend != "") {
			lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = isAppend;
		}
	}
};

lbpm.globals.initialCommonUsageObj = function(commonUsageObjName, usageContents) {
	var commonUsageObj = document.getElementsByName(commonUsageObjName);
	if (commonUsageObj != null && commonUsageObj.length > 0) {
		commonUsageObj = commonUsageObj[0];
		while (commonUsageObj.childNodes.length > 0) {
			commonUsageObj.removeChild(commonUsageObj.childNodes[0]);
		}
		var option = document.createElement("option");
		option.appendChild(document
				.createTextNode(window.dojo ? lbpm.workitem.constant.COMMONUSAGES : lbpm.workitem.constant.COMMONPAGEFIRSTOPTION));
		option.value = "";
		commonUsageObj.appendChild(option);
		if (usageContents != null) {
			option = null;
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
};

// 自定义常用审批语
lbpm.globals.openDefiniateUsageWindow = function() {
	var url = Com_Parameter.ContextPath
			+ "sys/lbpmservice/support/lbpm_usage/lbpmUsage_mainframe.jsp";
	var param={
		AfterShow:function(rtnVal){
			lbpm.globals.initialCommonUsages(true);
		}
	}
	lbpm.globals.popupWindow(url,800,600,param);
};
// 设置常用审批语
lbpm.globals.setUsages = function(commonUsagesObj) {
	if (commonUsagesObj.selectedIndex > 0 || lbpm.approveType == "right") {
		var fdUsageContent = document.getElementsByName("fdUsageContent")[0];
		var fdSimpleUsageContent = document
				.getElementsByName("fdSimpleUsageContent")[0];

		if(lbpm.workitem.constant.COMMONUSAGES_ISAPPEND=="true"){
			if(lbpm.approveType == "right"){
				fdUsageContent.value += $(commonUsagesObj).attr("data-value");
			}else{
				fdUsageContent.value += commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
				if (fdSimpleUsageContent != null) {
					fdSimpleUsageContent.value += commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
				}
			}
		} else {
			// 清除默认审批意见
			lbpm.globals.clearDefaultUsageContent(lbpm.currentOperationType, fdUsageContent,
					fdSimpleUsageContent);
			if(lbpm.approveType == "right"){
				fdUsageContent.value = $(commonUsagesObj).attr("data-value");
			}else{
				fdUsageContent.value = commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
				if (fdSimpleUsageContent != null) {
					fdSimpleUsageContent.value += commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
				}
			}
		}
		if(commonUsagesObj.options){			
			commonUsagesObj.options[0].selected = true; 
		}
	}
};

// 根据操作类型获取默认审批意见
lbpm.globals.getOperationDefaultUsage = function(operationType){
	
	var defaultUsage = "";
	if(!operationType || operationType==null){
		return defaultUsage;
	}
	

	var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	if(extAttributes){//查看扩展属性值
		for(var i=0;i<extAttributes.length;i++){
			if(extAttributes[i]["name"]=="lbpmCustomizeContentJson"){
				
				var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
				
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//根据操作类型得到相应的默认操作变量
					var customizeUsageName=lbpm.globals.customizeUsageName(operationType);
					//获取当前语言
					var currentLang = Data_GetResourceString("locale.language");
					
					//如果官方语言为空，说明是单语言环境，直接从json字符串获取
					if(jQuery.isEmptyObject(_langJson.official)){
						//判断Json字符串是否包含设置多语言值
						if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageName.handlerContent)){
							var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageName.handlerContent];
							return customizeContent_lang;
						}
					}else{
						//首先从多语言里面获取
						if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageName.handlerContent+"_lang")){
							var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageName.handlerContent+"_lang"];
							if(customizeContent_lang){
								var customizeUsageContentLangJson=JSON.parse(customizeContent_lang);
								for(var z=0;z<customizeUsageContentLangJson.length;z++){
									if(customizeUsageContentLangJson[z].lang==currentLang){
										defaultUsage=customizeUsageContentLangJson[z].value;
										return defaultUsage;
									}
								}
							}
						}
					}
				}
				break;
			}
		}
	}
	
	if(lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT){
		defaultUsage = lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT[operationType];
		if (defaultUsage == null) {
			defaultUsage = "";
		}
	}
	return defaultUsage;
};

//根据操作类型获取默认审批校验意见
lbpm.globals.getOperationDefaultUsageValidate = function(operationType){
	
	var defaultUsage = "";
	if(!operationType || operationType==null){
		return defaultUsage;
	}
	

	var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	if(extAttributes){//查看扩展属性值
		for(var i=0;i<extAttributes.length;i++){
			if(extAttributes[i]["name"]=="lbpmCustomizeValidateContentJson"){
				
				var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
				
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//根据操作类型得到相应的默认操作变量
					var customizeUsageNameValidate=lbpm.globals.customizeUsageNameValidate(operationType);
					//获取当前语言
					var currentLang = Data_GetResourceString("locale.language");
					
					//如果官方语言为空，说明是单语言环境，直接从json字符串获取
					if(jQuery.isEmptyObject(_langJson.official)){
						//判断Json字符串是否包含设置多语言值
						if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageNameValidate.handlerContent)){
							var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageNameValidate.handlerContent];
							return customizeContent_lang;
						}
					}else{
						//首先从多语言里面获取
						if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageNameValidate.handlerContent+"_lang")){
							var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageNameValidate.handlerContent+"_lang"];
							if(customizeContent_lang){
								var customizeUsageContentLangJson=JSON.parse(customizeContent_lang);
								for(var z=0;z<customizeUsageContentLangJson.length;z++){
									if(customizeUsageContentLangJson[z].lang==currentLang){
										defaultUsage=customizeUsageContentLangJson[z].value;
										return defaultUsage;
									}
								}
							}
						}
					}
				}
				break;
			}
		}
	}
	
	if(lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT){
		defaultUsage = lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT[operationType];
		if (defaultUsage == null) {
			defaultUsage = "";
		}
	}
	return "";
};


//获取操作类型对应的自定义名称
lbpm.globals.customizeUsageName= function(operationType){
	var customizeUsage={};
	if(operationType=='handler_pass'){
		customizeUsage={"handlerContent":"customizeUsageContent","isRequired":"isPassContentRequired","isValidated":"isPassContentValidated"};
	}
	
	if(operationType=='handler_commission'){
		customizeUsage={"handlerContent":"customizeUsageContentCommission","isRequired":"isCommissionContentRequired","isValidated":"isCommissionContentValidated"};
	}
	
	if(operationType=='handler_nodeSuspend'){
		customizeUsage={"handlerContent":"customizeUsageContentNodeSuspend","isRequired":"isNodeSuspendContentRequired","isValidated":"isNodeSuspendContentValidated"};
	}
	
	if(operationType=='handler_communicate'){
		customizeUsage={"handlerContent":"customizeUsageContentCommunicate","isRequired":"isCommunicateContentRequired","isValidated":"isCommunicateContentValidated"};
	}
	
	if(operationType=='handler_refuse'){
		customizeUsage={"handlerContent":"customizeUsageContentRefuse","isRequired":"isRefuseContentRequired","isValidated":"isRefuseContentValidated"};
	}
	
	if(operationType=='handler_additionSign'){
		customizeUsage={"handlerContent":"customizeUsageContentAdditionSign","isRequired":"isAdditionSignContentRequired","isValidated":"isAdditionSignContentValidated"};
	}
	
	if(operationType=='handler_superRefuse'){
		customizeUsage={"handlerContent":"customizeUsageContentSuperRefuse","isRequired":"isSuperRefuseContentRequired","isValidated":"isSuperRefuseContentValidated"};
	}
	
	if(operationType=='handler_nodeResume'){
		customizeUsage={"handlerContent":"customizeUsageContentNodeResume","isRequired":"isNodeResumeContentRequired","isValidated":"isNodeResumeContentValidated"};
	}
	
	if(operationType=='handler_assign'){
		customizeUsage={"handlerContent":"customizeUsageContentAssign","isRequired":"isAssignContentRequired","isValidated":"isAssignContentValidated"};
	}
	if(operationType=='handler_assignPass'){
		customizeUsage={"handlerContent":"customizeUsageContentAssignPass","isRequired":"isAssignPassContentRequired","isValidated":"isAssignPassContentValidated"};
	}
	if(operationType=='handler_assignRefuse'){
		customizeUsage={"handlerContent":"customizeUsageContentAssignRefuse","isRequired":"isAssignRefuseContentRequired","isValidated":"isAssignRefuseContentValidated"};
	}
	if(operationType=='handler_jump'){
		customizeUsage={"handlerContent":"customizeUsageContentJump","isRequired":"isJumpContentRequired","isValidated":"isJumpContentValidated"};
	}
	if(operationType=='handler_abandon'){
		customizeUsage={"handlerContent":"customizeUsageContentAandon","isRequired":"isAbandonContentRequired","isValidated":"isAbandonContentValidated"};
	}
	
	if(operationType=='handler_sign'){
		customizeUsage={"handlerContent":"customizeUsageContentSign","isRequired":"isSignContentRequired","isValidated":"isSignContentValidated"};
	}
	
	
	return customizeUsage;
}

//获取操作类型对应的校验的自定义名称
lbpm.globals.customizeUsageNameValidate= function(operationType){//isExcludeValidated
	var customizeUsageValidate={};
	if(operationType=='handler_pass'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentValidated","isExcludeValidated":"isPassContentExcludeValidated","isValidated":"isPassContentValidated"};
	}
	
	if(operationType=='handler_commission'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentCommissionValidated","isExcludeValidated":"isCommissionContentExcludeValidated","isValidated":"isCommissionContentValidated"};
	}
	
	if(operationType=='handler_nodeSuspend'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentNodeSuspendValidated","isExcludeValidated":"isNodeSuspendContentExcludeValidated","isValidated":"isNodeSuspendContentValidated"};
	}
	
	if(operationType=='handler_communicate'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentCommunicateValidated","isExcludeValidated":"isCommunicateContentExcludeValidated","isValidated":"isCommunicateContentValidated"};
	}
	
	if(operationType=='handler_refuse'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentRefuseValidated","isExcludeValidated":"isRefuseContentExcludeValidated","isValidated":"isRefuseContentValidated"};
	}
	
	if(operationType=='handler_additionSign'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentAdditionSignValidated","isExcludeValidated":"isAdditionSignContentExcludeValidated","isValidated":"isAdditionSignContentValidated"};
	}
	
	if(operationType=='handler_superRefuse'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentSuperRefuseValidated","isExcludeValidated":"isSuperRefuseContentExcludeValidated","isValidated":"isSuperRefuseContentValidated"};
	}
	
	if(operationType=='handler_nodeResume'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentNodeResumeValidated","isExcludeValidated":"isNodeResumeContentExcludeValidated","isValidated":"isNodeResumeContentValidated"};
	}
	
	if(operationType=='handler_assign'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentAssignValidated","isExcludeValidated":"isAssignContentExcludeValidated","isValidated":"isAssignContentValidated"};
	}
	if(operationType=='handler_assignPass'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentAssignPassValidated","isExcludeValidated":"isAssignPassContentExcludeValidated","isValidated":"isAssignPassContentValidated"};
	}
	if(operationType=='handler_assignRefuse'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentAssignRefuseValidated","isExcludeValidated":"isAssignRefuseContentExcludeValidated","isValidated":"isAssignRefuseContentValidated"};
	}
	if(operationType=='handler_jump'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentJumpValidated","isExcludeValidated":"isJumpContentExcludeValidated","isValidated":"isJumpContentValidated"};
	}
	if(operationType=='handler_abandon'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentAandonValidated","isExcludeValidated":"isAbandonContentExcludeValidated","isValidated":"isAbandonContentValidated"};
	}
	
	if(operationType=='handler_sign'){
		customizeUsageValidate={"handlerContent":"customizeUsageContentSignValidated","isExcludeValidated":"isSignContentExcludeValidated","isValidated":"isSignContentValidated"};
	}
	
	
	return customizeUsageValidate;
}


//根据操作类型获取审批意见是否必填
lbpm.globals.isUsageContenRequired = function(operationType){
	if(!operationType || operationType==null){
		return null;
	}
	if(!lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED || lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED==null){
		return null;
	}
	
	var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	if(extAttributes){//查看扩展属性值
		for(var i=0;i<extAttributes.length;i++){
			if(extAttributes[i]["name"]=="lbpmCustomizeContentJson"){
				
				var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
				
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//根据操作类型得到相应的默认操作变量
					var customizeUsageName=lbpm.globals.customizeUsageName(operationType);
					if(customizeUsageName){
						if(lbpmCustomizeContentJson[customizeUsageName.isRequired]=="true"){
							return true;
						}else if(lbpmCustomizeContentJson[customizeUsageName.isRequired]=="false"){
							return false;
						}
					}
				}
				break;
			}
		}
	}
	
	
	
	if(lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED[operationType]=="true"){
		return true;
	} else if(lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED[operationType]=="false"){
		return false;
	} else {
		return null;
	}
};

//根据操作类型获取审批意见是否包含校验
lbpm.globals.isUsageContenValidated = function(operationType){
	if(!operationType || operationType==null){
		return null;
	}
	
	var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	if(extAttributes){//查看扩展属性值
		for(var i=0;i<extAttributes.length;i++){
			if(extAttributes[i]["name"]=="lbpmCustomizeValidateContentJson"){
				
				var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
				
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//根据操作类型得到相应的默认操作变量
					var customizeUsageNameValidate=lbpm.globals.customizeUsageNameValidate(operationType);
					if(customizeUsageNameValidate){
						if(lbpmCustomizeContentJson[customizeUsageNameValidate.isValidated]=="true"){
							return true;
						}else if(lbpmCustomizeContentJson[customizeUsageNameValidate.isValidated]=="false"){
							return false;
						}
					}
				}
				break;
			}
		}
	}
	
	return null;
};

//根据操作类型获取审批意见是否排除校验
lbpm.globals.isUsageContenExcludeValidated = function(operationType){
	if(!operationType || operationType==null){
		return null;
	}
	
	var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	if(extAttributes){//查看扩展属性值
		for(var i=0;i<extAttributes.length;i++){
			if(extAttributes[i]["name"]=="lbpmCustomizeValidateContentJson"){
				
				var lbpmCustomizeValidateJsonStr=extAttributes[i]["value"];
				
				if(lbpmCustomizeValidateJsonStr){
					var lbpmCustomizeValidateJson=JSON.parse(lbpmCustomizeValidateJsonStr);
					//根据操作类型得到相应的默认操作变量
					var customizeUsageNameValidate=lbpm.globals.customizeUsageNameValidate(operationType);
					if(customizeUsageNameValidate){
						if(lbpmCustomizeValidateJson[customizeUsageNameValidate.isExcludeValidated]=="true"){
							return true;
						}else if(lbpmCustomizeValidateJson[customizeUsageNameValidate.isExcludeValidated]=="false"){
							return false;
						}
					}
				}
				break;
			}
		}
	}
	return null;

};

/**
 * 清除默认审批意见信息。 #作者：曹映辉 #日期：2011年12月15日
 */
lbpm.globals.clearDefaultUsageContent = function(operationType, usageContent, simpleUsage) {
	var defalutUsage = "";
	defalutUsage = lbpm.globals.getOperationDefaultUsage(operationType);
	usageContent = usageContent || document.getElementsByName("fdUsageContent")[0];
	if (usageContent && defalutUsage && usageContent.value.replace(/\s*/ig, '') == defalutUsage.replace(/\s*/ig, '')) {
		usageContent.value = "";
	}
	simpleUsage = simpleUsage || document.getElementsByName("fdSimpleUsageContent")[0];
	if (simpleUsage && defalutUsage && simpleUsage.value.replace(/\s*/ig, '') == defalutUsage.replace(/\s*/ig, '')) {
		simpleUsage.value = "";
	}
};

/**
 * 设置默认审批意见信息。 #作者：曹映辉 #日期：2011年12月15日
 */
lbpm.globals.setDefaultUsageContent = function(operationType, usageContent, simpleUsage) {
	var defalutUsage = "";
	defalutUsage = lbpm.globals.getOperationDefaultUsage(operationType);
	usageContent = usageContent || document.getElementsByName("fdUsageContent")[0];
	// 审批意见为空时才设置默认审批意见
	if (usageContent && !usageContent.value.replace(/\s*/ig, '')) {
		usageContent.value = defalutUsage;
	}
	simpleUsage = simpleUsage || document.getElementsByName("fdSimpleUsageContent")[0];
	if (simpleUsage && !simpleUsage.value.replace(/\s*/ig, '')) {
		simpleUsage.value = defalutUsage;
	}
};
