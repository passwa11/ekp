/*******************************************************************************
 * 功能：处理人“加签”操作的审批所用JSP
 * 创建时间：2018-08-06
 ******************************************************************************/
( function(operations) {
	operations['handler_assign'] = {
		click : OperationClick,
		check : OperationCheck,
		blur : OperationBlur,
		setOperationParam : setOperationParam
	};
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_assign');
	}

	// 处理人操作：加签
	function OperationClick(operationName) {
		lbpm.globals.setDefaultUsageContent('handler_assign');
		lbpm.globals.getOperationParameterJson("relationWorkitemId:relations:isAssignPassSkipChecked",true); // 加载后端数据
		handlerOperationTypeAssgin(operationName);
	}
	
	function handlerOperationTypeAssgin(operationName) {
		var html = "";
		// 设置显示当前正在加签人员
		var relationInfoObj = getCurrAssignRelationInfo();
		var ids = "";
		var names = "";
		if (relationInfoObj.length > 0) {
			for (var i = 0; i < relationInfoObj.length; i++) {
				ids += relationInfoObj[i].userId + ";";
				names += relationInfoObj[i].userName + ";";
			}
			if (ids) {
				ids = ids.substr(0, ids.lastIndexOf(";"));
			}
			if (names) {
				names = names.substr(0, names.lastIndexOf(";"));
			}
			html += "<input type='hidden' name='currentAssigneeIds' value='"
					+ ids + "'/>";
			html += "<label>" + names + "</label><br/>";
		}
		
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.Assignee;
		
		var operatorInfo = lbpm.globals.getOperationParameterJson("relationWorkitemId");
		var isAssignPassSkipChecked = lbpm.globals.getOperationParameterJson("isAssignPassSkipChecked");
		var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		var exceptValueStr = stringsConcat(currentOrgIdsObj.value, ids);

		var operationsTDTitle_Scope = document.getElementById("operationsTDTitle_Scope");
		var operationsTDContent_Scope = document.getElementById("operationsTDContent_Scope");
		var operationsRow_Scope = document.getElementById("operationsRow_Scope");

		operationsTDTitle_Scope.innerHTML = operationName + lbpm.constant.opt.AssignType;
		
		var options = {
				mulSelect : true,
				idField : 'toAssigneeIds',
				nameField : 'toAssigneeNames', 
				splitStr : ';',
				selectType : ORG_TYPE_POSTORPERSON,
				action : "lbpm.globals._checkRepeatPersonInPost4Assign",//检测岗位中是否包含有所选择的人
				notNull : ids.length < 1, //如果已存在加签人员则当前加签人员不一定为必选的
				alertText : (lbpm.constant.opt.AssigneeIsNull + operationName + lbpm.constant.opt.Assignee),
				text : lbpm.constant.SELECTORG,
				exceptValue : exceptValueStr.split(';')
		};
		if (lbpm.approveType == "right") {
			options["width"] = "94%";
		}
		var autoPopAction = null;
		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_assign"]){
			var operationScope = currentNodeObj.operationScope["handler_assign"];
			if(operationScope  == "custom"){
				var scopeType = currentNodeObj.operationScope["handler_assign"] == null?"":currentNodeObj.operationScope["handler_assign"];
				var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
				var customHandlerSelectType = currentNodeObj.operationScope["handler_assign_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_assign_customHandlerSelectType"];
				var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
				var customHandlerIds = currentNodeObj.operationScope["handler_assign_customIds"] == null?"":currentNodeObj.operationScope["handler_assign_customIds"];
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
				options.onclick = "Dialog_AddressList(true, 'toAssigneeIds', 'toAssigneeNames', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", '"+options.alertText+"')";
				autoPopAction = function(){
					Dialog_AddressList(true, 'toAssigneeIds', 'toAssigneeNames', ';',defaultOptionBean, options.action, searchBean, null,  options.notNull, options.alertText);
				};
			}else {
				var deptLimit = "";
				if(operationScope == "org"){
					deptLimit = "myOrg";
				}else if(operationScope == "dept"){
					deptLimit = "myDept";
				}
				options.deptLimit = deptLimit;
				autoPopAction = function(){
					Dialog_Address(true, 'toAssigneeIds', 'toAssigneeNames', ';', options.selectType, options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue, deptLimit);
				};
			}
		} else {
			autoPopAction = function(){
				Dialog_Address(true, 'toAssigneeIds', 'toAssigneeNames', ';', options.selectType, options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue);
			};
		}
		
		html += lbpm.address.html_build(options);
		options.action = lbpm.globals._checkRepeatPersonInPost4Assign;
		// 是否可进行多级加签
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiAssignEnabled']=="true"){
			if (lbpm.approveType == "right") {
				html += "<br><label class='lui-lbpm-checkbox'>";
				isHasBr = true;
			}else{
				html += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
			}
			html += "<input id='_multiAssign' type='checkbox' key='isMultiAssign'><span class='checkbox-label'>";
			html += lbpm.constant.opt.AssignAllowMulti + operationName;
			html += "</span></label><br/>";
		}
		// 被加签人才会显示加签人员通过后跳过我
		if(operatorInfo!="" && operatorInfo){
			if (lbpm.approveType == "right") {
				html += "<label class='lui-lbpm-checkbox'>";
				isHasBr = true;
			}else{
				html += "<label class='lui-lbpm-checkbox' style=''>";
			}
			html += "<input id='_assignPassSkip' type='checkbox' key='isAssignPassSkip'><span class='checkbox-label'>";
			html += operationName+lbpm.constant.opt.AssignPassSkip;
			html += "</span></label><br/>";
		}
		
		html += lbpm.globals.getNotifyType4Node(currentNodeObj);

		operationsTDContent.innerHTML = html;
		
		lbpm.globals.hiddenObject(operationsRow, false);
		
		if (typeof lbpm.globals.isNewAuditNote == "undefined" || lbpm.globals.isNewAuditNote == false){
			setTimeout(autoPopAction,100);	
		}
		
		
		
		// 是否默认勾选多级加签
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiAssignEnabled']=="true" && Lbpm_SettingInfo['isMultiAssignDefault'] == "true"){
			var mule = document.getElementById("_multiAssign");
			mule.checked = true;
		}
		// 是否默认勾选加签人员通过后跳过我
		if(isAssignPassSkipChecked!="" && isAssignPassSkipChecked){
			var _assignPassSkip = document.getElementById("_assignPassSkip");
			if(_assignPassSkip){
				_assignPassSkip.checked = true;
			}
		}

		// 设置加签的流转方式选择
		var assignHtml = "<label><input name='assignType' type='radio' value='10' />"+lbpm.constant.opt.AssignType_0+"</label></br>"+
			"<label><input name='assignType' type='radio' value='1' />"+lbpm.constant.opt.AssignType_1+"</label></br>"+
			"<label><input name='assignType' type='radio' value='2' checked/>"+lbpm.constant.opt.AssignType_21+"</label></br>";
		operationsRow_Scope.style.display = "";
		operationsTDContent_Scope.innerHTML = assignHtml;
		
	};
	
	lbpm.globals._checkRepeatPersonInPost4Assign = function(rtnVal) {
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
						'oprType' : 'handler_assign',
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

	//“加签”操作的检查
	function OperationCheck() {
		var input = $("#toAssigneeIds")[0];
		if (input && input.value == "") {
			alert(input.getAttribute("alertText"));
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	}
	
	// 设置"加签"操作的参数
	function setOperationParam() {
		//加签人员
		var input = $("#toAssigneeIds")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		//加签人员名称
		input = $("#toAssigneeNames")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		// 是否允许多级加签
		input = $("input[key='isMultiAssign']")[0];
		if (input) {
			lbpm.globals.setOperationParameterJson(input, null, "param");
		}
		// 是否勾选了加签人员通过后跳过我
		input = $("input[key='isAssignPassSkip']")[0];
		if (input) {
			lbpm.globals.setOperationParameterJson(input, null, "param");
		}
		//设置"加签"操作的流转方式参数
		var assignType = $("input[name='assignType']:checked")[0].value;
		lbpm.globals.setOperationParameterJson(assignType,"assignType","param");
	};
	
	 function stringsConcat(str1, str2, splitChar) {
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
function getCurrAssignRelationInfo() {
	var relationInfoObj = getAssignRelationInfoByWorkitemId(lbpm.nowProcessorInfoObj.id);
	return relationInfoObj;
};

function getAssignRelationInfoByWorkitemId(id) {
	var relationInfoObj = getAssignRelationInfo();
	if(relationInfoObj==null) return [];
	var rtn = [];
	for ( var i = 0; i < relationInfoObj.length; i++) {
		if (relationInfoObj[i].srcId == id) {
			rtn.push(relationInfoObj[i]);
		}
	}
	return rtn;
};

function getAssignRelationInfo() {
	var relationInfoXML=lbpm.globals.getOperationParameterJson("relations",true);
	if(relationInfoXML==null) return null;
	var relationInfoObj = WorkFlow_LoadXMLData(relationInfoXML);
	return relationInfoObj;
};