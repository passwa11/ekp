define(["dijit/registry", "dojo/topic", "mui/form/Address", "sys/lbpmservice/mobile/common/syslbpmprocess", 'dojo/query', 'dojo/_base/array',"mui/dialog/Tip","mui/i18n/i18n!sys-lbpmservice:lbpmNode"],
		function(registry, topic,Address, syslbpmprocess,query , array, tip, msg){
	/*******************************************************************************
	 * 功能：处理人“加签”操作的审批所用JSP
	 * 创建时间：2018-08-08
	 ******************************************************************************/
	var assign={};
	 
	var OperationBlur = function () {
		lbpm.globals.clearDefaultUsageContent("handler_assign");
	};
	
	// 处理人操作：加签
	var OperationClick = function(operationName) {
		lbpm.globals.setDefaultUsageContent('handler_assign');
		lbpm.globals.getOperationParameterJson("relationWorkitemId:relations:isAssignPassSkipChecked",true); // 加载后端数据
		assign.handlerOperationTypeAssign(operationName);
	};

	//“加签”操作的检查
	var OperationCheck = function() {
		var input = $("#toAssigneeIds")[0];
		if (input && input.value == "") {
			tip["warn"]({text:input.getAttribute("alertText")});
			//alert(input.getAttribute("alertText"));
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;
	};
	
	// 设置"加签"操作的参数
	var setOperationParam = function() {
		//加签人员
		var input = $("#toAssigneeIds")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		//加签人员名称
		input = $("#toAssigneeNames")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		var widget = registry.byId("isMultiAssign");
		if (widget) {
			lbpm.globals.setOperationParameterJson(widget.get("checked"),
					"isMultiAssign", "param");
		}
		// 是否勾选了加签人员通过后跳过我
		var _assignPassSkip = registry.byId("_assignPassSkip");
		if (_assignPassSkip) {
			lbpm.globals.setOperationParameterJson(_assignPassSkip.get("checked"), "isAssignPassSkip", "param");
		}

		var assignType = $("input[name='assignType']:checked")[0].value;
		lbpm.globals.setOperationParameterJson(assignType,"assignType","param");
	};
	
	assign.handlerOperationTypeAssign = function(operationName) {
		var html = "";
		// 设置显示当前正在加签人员
		var relationInfoObj = getCurrAssignRelationInfo();
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
			html += "<input type='hidden' name='currentAssigneeIds' value='"
					+ ids + "'/>";
			html += "<label>" + names + "</label><br/>";
		}
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.Assignee;

		var operationsTDTitle_Scope = document.getElementById("operationsTDTitle_Scope");
		var operationsTDContent_Scope = document.getElementById("operationsTDContent_Scope");
		var operationsRow_Scope = document.getElementById("operationsRow_Scope");

		operationsTDTitle_Scope.innerHTML = operationName + lbpm.constant.opt.AssignType;

		var operatorInfo = lbpm.globals.getOperationParameterJson("relationWorkitemId");
		var isAssignPassSkipChecked = lbpm.globals.getOperationParameterJson("isAssignPassSkipChecked");
		var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		var exceptValueStr = assign.stringsConcat(currentOrgIdsObj.value, ids);
		var addressSubject = msg['lbpmNode.toOtherHandler.address.subject.select']+operationName+msg['lbpmNode.toOtherHandler.address.subject.person'];
		var options = {
				mulSelect : true,
				idField : 'toAssigneeIds',
				nameField : 'toAssigneeNames', 
				splitStr : ';',
				selectType : ORG_TYPE_POSTORPERSON,
				notNull : ids.length < 1, //如果已存在加签人员则当前加签人员不一定为必选的
				text : lbpm.constant.SELECTORG,
				exceptValue : exceptValueStr.split(';'),
				validate:'assignhandlerRequired required',
				id:'toHandlerAddress',
				isShowSubject:true,
				subject:addressSubject
		};
		
		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_assign"] && currentNodeObj.operationScope["handler_assign"] != "all"){
			var scopeType = currentNodeObj.operationScope["handler_assign"] == null?"":currentNodeObj.operationScope["handler_assign"];
			var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
			var customHandlerSelectType = currentNodeObj.operationScope["handler_assign_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_assign_customHandlerSelectType"];
			var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
			var customHandlerIds = currentNodeObj.operationScope["handler_assign_customIds"] == null?"":currentNodeObj.operationScope["handler_assign_customIds"];
			var handlerIdentity = $("input[name='sysWfBusinessForm.fdHandlerRoleInfoIds']").val().split(";")[0];
			//限定范围人员 
			var dataUrl = "&currentId=" + lbpm.nowProcessorInfoObj.expectedId 
				+ "&handlerIdentity=" + handlerIdentity
				+ "&customHandlerSelectType=" + customHandlerSelectType
				+ "&customHandlerIds=" + encodeURIComponent(customHandlerIds)
				+ "&scopeType=" + scopeType
				+ "&fdModelName=" + lbpm.modelName
				+ "&fdModelId=" + lbpm.modelId
				+ "&exceptValue=" + exceptValueStr;
			var addressHtml = '';
			addressHtml += "<input type='hidden' name='toAssigneeIds' id='toAssigneeIds'/>";
			addressHtml += "<input type='hidden' name='toAssigneeNames' id='toAssigneeNames'/>";
			addressHtml += '<div id=\'toHandlerAddress\' data-dojo-type="sys/lbpmservice/mobile/opthandler/OptHandler"'
				 + ' data-dojo-props="subject:\''+addressSubject+'\',validate:\'assignhandlerRequired required\',required:true,showHeadImg:true,idField:\'toAssigneeIds\',isMul:'+options.mulSelect+','
				 + 'dataUrl:\'/sys/lbpmservice/mobile/opthanlder.do?method=scopeHandlers'+dataUrl+'\','
				 + 'nameField:\'toAssigneeNames\'"></div>';
			html += addressHtml;
		} else {
			html += lbpm.address.html_build(options);
		}
		
		// 是否可进行多级加签
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiAssignEnabled']=="true"){
			html += "<div class='isMutiAssignCheckbox'>";
			html += '<div id="isMultiAssign" alertText="" key="isMultiAssign" data-dojo-type="mui/form/CheckBox"';
			html += ' data-dojo-props="name:\'isMultiAssign\', value:\'true\', mul:false, text:\'';
			html += lbpm.constant.opt.AssignAllowMulti + operationName + '\'';
			html += '"></div></div>';
		}

		// 被加签人才会显示加签人员通过后跳过我
		if(operatorInfo!="" && operatorInfo){
			html += "<div class='isMutiAssignCheckbox'>";
			html += '<div id="_assignPassSkip" alertText="" key="isAssignPassSkip" data-dojo-type="mui/form/CheckBox"';
			html += ' data-dojo-props="name:\'isAssignPassSkip\', value:\'true\', mul:false, text:\'';
			html += operationName+lbpm.constant.opt.AssignPassSkip + '\'';
			html += '"></div></div>';
		}

		query('#operationsTDContent').html(html, {parseContent: true,onEnd: function() {
			this.inherited("onEnd", arguments);
			if (this.parseDeferred) {
				this.parseDeferred.then(function() {
					//这里的时候地址本已经解析完毕，发出事件让弹窗出现
					topic.publish("/lbpm/operation/toHandlerAddress/parseFinish");
				});
			}
		}});
		lbpm.globals.hiddenObject(operationsRow, false);
		
		// 是否默认勾选多级加签
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiAssignEnabled']=="true" && Lbpm_SettingInfo['isMultiAssignDefault'] == "true"){
			setTimeout(function() {
				var widget = registry.byId("isMultiAssign");
				widget.set("checked",true);
			},100);
		}
		// 是否默认勾选加签人员通过后跳过我
		if(isAssignPassSkipChecked!="" && isAssignPassSkipChecked){
			setTimeout(function() {
				var _assignPassSkip = registry.byId("_assignPassSkip");
				if(_assignPassSkip){
					_assignPassSkip.set("checked",true);
				}
			},100);
		}
		var additionAssignHtml = "<label class='muiRadioItem' style='padding-left: 0rem'>" +
			"<input name='assignType' data-dojo-type='mui/form/Radio' data-dojo-props='checked:false,showStatus:'edit',renderType:'normal',value:'10'' type='radio' value='10' />"+lbpm.constant.opt.AssignType_0+"</label>"+
			"</br><label class='muiRadioItem' style='padding-left: 0rem'><input name='assignType' data-dojo-type='mui/form/Radio' data-dojo-props='checked:false,showStatus:'edit',renderType:'normal',value:'1'' type='radio' value='1'/>"+lbpm.constant.opt.AssignType_1+"</label>"+
			"</br><label class='muiRadioItem' style='padding-left: 0rem'><input name='assignType' data-dojo-type='mui/form/Radio' data-dojo-props='checked:true,showStatus:'edit',renderType:'normal',value:'2'' type='radio' value='2' checked />"+lbpm.constant.opt.AssignType_21+"</label>";
		operationsRow_Scope.style.display = "";
		operationsTDContent_Scope.innerHTML = additionAssignHtml;

	};
	
	assign.stringsConcat = function(str1, str2, splitChar) {
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
	
	assign['handler_assign'] = lbpm.operations['handler_assign'] = {
			click : OperationClick,
			check : OperationCheck,
			blur : OperationBlur,
			setOperationParam : setOperationParam
		};
	
	assign.init = function() {
	};
	
	return assign;
});