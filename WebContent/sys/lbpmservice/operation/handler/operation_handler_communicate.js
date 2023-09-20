/*******************************************************************************
 * 功能：处理人“沟通”操作的审批所用JSP，此JSP路径在处理人“沟通”操作扩展点定义的reviewJs参数匹配 使用： 作者：罗荣飞
 * 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['handler_communicate'] = {
		click : OperationClick,
		check : OperationCheck,
		blur : OperationBlur,
		setOperationParam : setOperationParam
	};
	
	var addressHtml = '';
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_communicate');
	}

	// 处理人操作：沟通
	function OperationClick(operationName) {
		/*
		 * relations当前工作项关联的工作项XML(多级沟通的XML)
		 * relationWorkitemId,关联的工作项ID(指父级工作项ID)
		 * relationScope,沟通范围
		 */
		lbpm.globals.setDefaultUsageContent('handler_communicate');
		lbpm.globals.getOperationParameterJson("relationWorkitemId:relationScope:relations:isMutiCommunicate",true); // 加载后端数据
		handlerOperationTypeCommunicate(operationName);
	}

	//“沟通”操作的检查
	function OperationCheck() {
		var input = $("#toOtherHandlerIds")[0];
		if (input && input.value == "") {
			alert(input.getAttribute("alertText"));
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	};
	
	// 设置"沟通"操作的参数
	function setOperationParam() {
		//沟通人员
		var input = $("#toOtherHandlerIds")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		//沟通人员名称
		input = $("#toOtherHandlerNames")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		// 是否允许多级沟通
		input = $("input[key='isMutiCommunicate']")[0];
		if (input) {
			lbpm.globals.setOperationParameterJson(input, null, "param");
		}
		// 限制子级沟通范围
		input = $("input[key='communicateScopeHandlerIds']")[0];
		if (input) {
			lbpm.globals.setOperationParameterJson(input,
					"communicateScopeHandlerIds", "param");
		}
		// 隐藏沟通意见
		input = $("input[key='isHiddenNote']")[0];
		if(input) {
			lbpm.globals.setOperationParameterJson(input,
					"isHiddenNote", "param");
		}
	};

// 处理人操作：沟通
var handlerOperationTypeCommunicate = function(operationName,isACommunicate) {

	//清除所有operationsRow_ALL信息
	var html = "";

	// 设置显示当前正在沟通人员
	var relationInfoObj = lbpm.globals.getCurrRelationInfo();
	var ids = "";
	var names = "";
	if (relationInfoObj.length > 0) {
		for ( var i = 0; i < relationInfoObj.length; i++) {
			ids += relationInfoObj[i].userId + ";";
			names += relationInfoObj[i].userName + ";";
		}
		if (ids) {
			ids = ids.substr(0, ids.lastIndexOf(";"));
		}
		if (names) {
			names = names.substr(0, names.lastIndexOf(";"));
		}
		html += "<input type='hidden' name='currentCommunicateIds' value='"
				+ ids + "'/>";
		html += "<label>" + names + "</label><br/>";
	}
	var operationsRow = document.getElementById("operationsRow");
	var operationsTDTitle = document.getElementById("operationsTDTitle");
	var operationsTDContent = document.getElementById("operationsTDContent");
	operationsTDTitle.innerHTML = operationName
			+ lbpm.constant.opt.CommunicatePeople;
	var operatorInfo = lbpm.globals
			.getOperationParameterJson("relationWorkitemId:relationScope");
	var currentOrgIdsObj = document
			.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
	var exceptValueStr = lbpm.globals.stringConcat(currentOrgIdsObj.value, ids);
	
	var options = {
			mulSelect : true,
			idField : 'toOtherHandlerIds',
			nameField : 'toOtherHandlerNames', 
			splitStr : ';',
			selectType : ORG_TYPE_POSTORPERSON,
			action : "lbpm.globals._checkRepeatPersonInPost",//检测岗位中是否包含有所选择的人
			notNull : ids.length < 1, //如果已存在沟通人员则当前沟通人员不一定为必选的
			alertText : (lbpm.constant.opt.CommunicateIsNull + operationName + lbpm.constant.opt.CommunicatePeople),
			text : lbpm.constant.SELECTORG,
			exceptValue : exceptValueStr.split(';')
	};
	if (lbpm.approveType == "right") {
		options["width"] = "95%";
	}
	// 被沟通对象被限定范围时
	var autoPopAction = null;
	var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
	if (operatorInfo.relationWorkitemId && operatorInfo.relationScope) {
		var dataBean = "lbpmCommunicateScopeService&scopeHandles="
				+ operatorInfo.relationScope;
		var searchBean = dataBean + "&keyword=!{keyword}";
		addressHtml = options.onclick = "Dialog_AddressList(true,'toOtherHandlerIds','toOtherHandlerNames',';','"
			+ dataBean
			+ "',lbpm.globals._checkRepeatPersonInPost,'"
			+ searchBean
			+ "','','',lbpm.constant.opt.CommunicateCheckObj)";
	}else if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_communicate"]){
		var operationScope = currentNodeObj.operationScope["handler_communicate"];
		if(operationScope  == "custom"){
			var scopeType = currentNodeObj.operationScope["handler_communicate"] == null?"":currentNodeObj.operationScope["handler_communicate"];
			var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
			var customHandlerSelectType = currentNodeObj.operationScope["handler_communicate_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_communicate_customHandlerSelectType"];
			var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
			var customHandlerIds = currentNodeObj.operationScope["handler_communicate_customIds"] == null?"":currentNodeObj.operationScope["handler_communicate_customIds"];
			var handlerIdentity = $("input[name='sysWfBusinessForm.fdHandlerRoleInfoIds']").val().split(";")[0];
			//限定范围人员
			var defaultOptionBean = "lbpmScopeHandlerTreeService" 
				+ "&currentId=" + lbpm.nowProcessorInfoObj.expectedId
				+ "&handlerIdentity=" + handlerIdentity
				+ "&customHandlerSelectType=" + customHandlerSelectType
				+ "&customHandlerIds=" + encodeURIComponent(customHandlerIds)
				+ "&scopeType=" + scopeType
				+ "&fdModelName=" + lbpm.modelName
				+ "&fdModelId=" + lbpm.modelId
				+ "&exceptValue=" + exceptValueStr;
			var searchBean = defaultOptionBean + "&keyword=!{keyword}";
			addressHtml = options.onclick = "Dialog_AddressList(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", '"+options.alertText+"')";
			autoPopAction = function(){
				Dialog_AddressList(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';',defaultOptionBean,
					options.action, searchBean, null,  options.notNull, options.alertText);
			};
		}else{
			var deptLimit = "";
			if(operationScope == "org"){
				deptLimit = "myOrg";
			}else if(operationScope == "dept"){
				deptLimit = "myDept";
			}
			autoPopAction = function(){
				Dialog_Address(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';', options.selectType,
						options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue, deptLimit);
			};
			if(deptLimit){
				addressHtml = options.onclick = "Dialog_Address(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';', "+options.selectType+", "+options.action+", null, null, "+options.notNull+", '"+options.alertText+"','"+options.text+"', "+JSON.stringify(options.exceptValue).replace(/\"/g,"'")+", '"+deptLimit+"')";
			}
		}
	} else {
		autoPopAction = function(){
			Dialog_Address(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';', options.selectType,
					options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue);
		};
	}
	
	
	html += lbpm.address.html_build(options);
	options.action = lbpm.globals._checkRepeatPersonInPost;
	var isHasBr = false;
	//判断是否为节点处理的第一个沟通发起者,设置是否允许多级沟通
	if (!isACommunicate && (operatorInfo.isMutiCommunicate==true || operatorInfo.isMutiCommunicate==null)) {
		// 是否可进行多级沟通
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiCommunicateConfigurable']=="true" && lbpm.flowcharts['multiCommunicateEnabled']!="false"){
			if (lbpm.approveType == "right") {
				html += "<br><label class='lui-lbpm-checkbox'>";
				isHasBr = true;
			}else{
				html += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
			}
			html += "<input id='_mutiCommunicate' type='checkbox' key='isMutiCommunicate' onclick='lbpm.globals.setCommunicateScope(this);' operationName='"+operationName+"' ><span class='checkbox-label'>";
			html += lbpm.constant.opt.CommunicateScopeAllowMuti + operationName;
			html += "</span></label>";
			lbpm.globals.setCommunicateScope({checked:false}); // hack
		}
	}
	if (!operatorInfo.relationWorkitemId) {
		// 是否可隐藏沟通意见
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isHiddenCommunicateNoteConfigurable']=="true" && lbpm.flowcharts['hiddenCommunicateNoteEnabled']!='false'){
			if (lbpm.approveType == "right" && !isHasBr) {
				html += "<br><label class='lui-lbpm-checkbox'>";
			}else{
				html += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
			}
			html += "<input type='checkbox' key='isHiddenNote' value='true'><span class='checkbox-label'>" + lbpm.constant.opt.CommunicateHiddenNote + "</span></label>";
		}
	}

	html +="<br>"+lbpm.globals.getNotifyType4Node(currentNodeObj);
	
	operationsTDContent.innerHTML = html;
	lbpm.globals.hiddenObject(operationsRow, false);
	
	// 如果在节点属性设置了限定范围，则不带出默认沟通人
	if(addressHtml==''){
		// 设置默认沟通人
		if (currentNodeObj.operationScope && currentNodeObj.operationScope["handler_communicate"] == 'all' 
				&& currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"] 
				&& currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"]!="") {
			if($("#toOtherHandlerIds").val()=="" && $("#toOtherHandlerNames").val()==""){
				var defaultHandlerIds = currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"];
				var defaultHandlerNames = currentNodeObj.operationScope["handler_communicate_defaultHandlerNames"];
				var defaultIds = defaultHandlerIds.split(";");
				var defaultNames = defaultHandlerNames.split(";");
				var canCommunicate = true;
				
				defaultHandlerIds = "";
				defaultHandlerNames = "";
				for (var i=0;i<defaultIds.length;i++) {
					// 被沟通对象被限定范围时,默认沟通人需要在限定范围内
					if(operatorInfo.relationScope){
						if (operatorInfo.relationScope.indexOf(defaultIds[i]) == -1) {
							canCommunicate = false;
						}
					}
					if (!canCommunicate) {
						continue;
					}
					// 被沟通对象不能是自己或正在沟通的人
					if (exceptValueStr) {
						if (exceptValueStr.indexOf(defaultIds[i]) != -1) {
							canCommunicate = false;
						}
					}
					if (canCommunicate) {
						if (defaultHandlerIds == "") {
							defaultHandlerIds = defaultIds[i];
							defaultHandlerNames = defaultNames[i];
						} else {
							defaultHandlerIds += ";" + defaultIds[i];
							defaultHandlerNames += ";" + defaultNames[i];
						}
					}
				}
				$("#toOtherHandlerIds").val(defaultHandlerIds);
				$("#toOtherHandlerNames").val(defaultHandlerNames);
			}
		} else {
			if(!currentNodeObj.operationScope || !currentNodeObj.operationScope["handler_communicate"] 
				|| (currentNodeObj.operationScope["handler_communicate"] == 'all' && !currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"])){
				if(Lbpm_SettingInfo && Lbpm_SettingInfo['isCommunicateWithCreatorDefault']=="true"){
					if($("#toOtherHandlerIds").val()=="" && $("#toOtherHandlerNames").val()==""){
						//var curHanderId = $("[name='sysWfBusinessForm.fdCurHanderId']").val();
						var draftorId = $("[name='sysWfBusinessForm.fdDraftorId']").val();
						var canCommunicate = true;
						if (operatorInfo.relationScope) {
							if(operatorInfo.relationScope.indexOf(draftorId)==-1){
								canCommunicate = false;
							}
						}
						if(canCommunicate && (exceptValueStr && exceptValueStr.indexOf(draftorId) == -1)){
							$("#toOtherHandlerIds").val(draftorId);
							$("#toOtherHandlerNames").val($("[name='sysWfBusinessForm.fdDraftorName']").val());
						}
					}
				}
			}
		}
	}

	if (!isACommunicate && (operatorInfo.isMutiCommunicate==true || operatorInfo.isMutiCommunicate==null)) {
		// 是否默认勾选多级沟通
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiCommunicateConfigurable']=="true" && lbpm.flowcharts['multiCommunicateEnabled']!="false" && Lbpm_SettingInfo['isMultiCommunicateDefault'] == "true"){
			var mule = document.getElementById("_mutiCommunicate");
			mule.checked = true;
			lbpm.globals.setCommunicateScope(mule);
		}
	}

		// 被沟通对象被限定范围时
		if (operatorInfo.relationScope) {
			if (typeof lbpm.globals.isNewAuditNote == "undefined" || lbpm.globals.isNewAuditNote == false){
				setTimeout(function(){
					var dataBean = "lbpmCommunicateScopeService&scopeHandles="
							+ operatorInfo.relationScope;
					var searchBean = dataBean + "&keyword=!{keyword}";
					Dialog_AddressList(true,'toOtherHandlerIds','toOtherHandlerNames',';',
						 dataBean
						,lbpm.globals._checkRepeatPersonInPost,
						searchBean
						,'','',lbpm.constant.opt.CommunicateCheckObj);
					}
					,100);   
			}
		}else{
			if (typeof lbpm.globals.isNewAuditNote == "undefined" || lbpm.globals.isNewAuditNote == false){
				setTimeout(autoPopAction,100);	
			}
		}

};

//fixed by wubing #29509 流程处理中，A沟通给B，原先“沟通”能选择岗位的，现在只能选人选不到岗位了，提示有重复
lbpm.globals._checkRepeatPersonInPost = function(rtnVal) {
	if(rtnVal){
		var ids=[];
		var amap = rtnVal.GetHashMapArray();
		for(var i=0;i<amap.length;i++){
			var map = amap[i];
			ids.push(map.id);
		}
		if(ids.length>0){
			var str = ids.join(';');
			var url = Com_Parameter.ContextPath + 'sys/lbpm/engine/jsonp.jsp';
			var pjson = {
					's_bean' : 'lbpmCheckDataBean',
					'handlerIds' : str,
					'op' : 'checkRepeatPersonInPost',
					'_d' : new Date().toString()
				};
			$.ajaxSettings.async = false;
			$.getJSON(url, pjson, function(json) {
				//errcode=1出错了，errcode=0 有重复
				if(json['errcode']=='0'){
					alert(json['errmsg']);
				}
			});
			$.ajaxSettings.async = true;
		}
	}
};

lbpm.globals.hidden_Communicate_Scope = function() {
	var operationsRow_Scope = document.getElementById("operationsRow_Scope");

	if (operationsRow_Scope)
		operationsRow_Scope.style.display = "none";
};
//限定沟通范围处理
lbpm.globals.setCommunicateScope = function(sel) {
	var operationsRow_Scope = document.getElementById("operationsRow_Scope");
	// 不限定范围处理情况
	if (!sel.checked) {
		operationsRow_Scope.style.display = "none";
		lbpm.globals.handlerOperationClearOperationsRow_Scope();
	}
	//勾选限定范围处理情况
	else {
		operationsRow_Scope.style.display = "";
		var operationsTDTitle_Scope = document
				.getElementById("operationsTDTitle_Scope");
		var operationsTDContent_Scope = document
				.getElementById("operationsTDContent_Scope");
		// 范围包括沟通人员及 (限制子级沟通范围)
		operationsTDTitle_Scope.innerHTML = lbpm.constant.opt.CommunicateScopeLimitSub
				+ ($(sel).attr('operationName') || sel.operationName)
				+ lbpm.constant.opt.CommunicateScopeLimitScope;
		var htmlContent = "";
		var currentOrgIdsObj = document
				.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		// alert(document.getElementById("currentCommunicateIds").value);
		var exceptValue = currentOrgIdsObj.value;
		if (document.getElementById("currentCommunicateIds")) {
			exceptValue = lbpm.globals.stringConcat(exceptValue, document
					.getElementById("currentCommunicateIds").value);

		}
		exceptValue = lbpm.globals.stringConcat(exceptValue, document
				.getElementById("toOtherHandlerIds").value);
		
		var options = {
				mulSelect : true,
				idField : 'communicateScopeHandlerIds',
				nameField : 'communicateScopeHandlerNames', 
				splitStr : ';',
				selectType : ORG_TYPE_POSTORPERSON,
				notNull : false,
				exceptValue : exceptValue.split(';'),
				text : lbpm.constant.SELECTORG
		};
		if(addressHtml!=''){
			options.onclick = addressHtml.replace('toOtherHandlerIds','communicateScopeHandlerIds').replace('toOtherHandlerNames','communicateScopeHandlerNames');
		}
		htmlContent += lbpm.address.html_build(options);
		htmlContent += lbpm.constant.opt.CommunicateScopeIsNullNoLimit;
		operationsTDContent_Scope.innerHTML = htmlContent;
	}
};

lbpm.globals.getCurrRelationInfo = function() {
	var relationInfoObj = getRelationInfoByWorkitemId(lbpm.nowProcessorInfoObj.id);
	return relationInfoObj;
};

var getRelationInfoByWorkitemId = function(id) {
	var relationInfoObj = getRelationInfo();
	if(relationInfoObj==null) return [];
	var rtn = [];
	for ( var i = 0; i < relationInfoObj.length; i++) {
		if (relationInfoObj[i].srcId == id) {
			rtn.push(relationInfoObj[i]);
		}
	}
	return rtn;
};

var getRelationInfo = function() {
	var relationInfoXML=lbpm.globals.getOperationParameterJson("relations",true);
	if(relationInfoXML==null) return null;
	var relationInfoObj = WorkFlow_LoadXMLData(relationInfoXML);
	return relationInfoObj;
};

lbpm.globals.showCommunicateInfo = function(node, ShowInfoDialog) {
	//alert(ShowInfoDialog);
	ShowInfoDialog.setBodyUrl(Com_Parameter.ContextPath
			+ 'sys/lbpm/communicate/communicate_view.html?nodeId='
			+ node.Data.id);
	// ShowInfoDialog.show();
};

//清除operationsRow_Scope信息
lbpm.globals.handlerOperationClearOperationsRow_Scope = function() {
	var operationsTDTitle_Scope = document
			.getElementById("operationsTDTitle_Scope");
	var operationsTDContent_Scope = document
			.getElementById("operationsTDContent_Scope");
	if (operationsTDTitle_Scope)
		operationsTDTitle_Scope.innerHTML = '';
	if (operationsTDContent_Scope)
		operationsTDContent_Scope.innerHTML = '';
};
/**
 * 字符查连接方法
 */
lbpm.globals.stringConcat = function(str1, str2, splitChar) {
	var str1;
	var str2;
	var splitChar;
	if (!str1) {
		str1 = ";a;c";
	}
	if (!str2) {
		str2 = ";b;";
	}
	if (!splitChar) {
		splitChar = ";";
	}

	if (str1.lastIndexOf(splitChar) == str1.length - 1 && str1) {
		str1 = str1.substr(0, str1.lastIndexOf(splitChar));
	}
	if (str2.indexOf(splitChar) == 0 && str2) {
		str2 = str2.substr(1);
	}
	var str = str1 + splitChar + str2;

	if (str == splitChar) {
		str = "";

		return str;
	}
	if (str.lastIndexOf(splitChar) == str.length - 1) {
		str = str.substr(0, str.lastIndexOf(splitChar));
	}
	if (str.indexOf(splitChar) == 0) {
		str = str.substr(1);
	}
	return str;

};

})(lbpm.operations);