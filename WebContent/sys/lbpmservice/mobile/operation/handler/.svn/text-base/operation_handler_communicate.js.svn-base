define(["dijit/registry", "mui/form/Address", "sys/lbpmservice/mobile/common/syslbpmprocess", 'dojo/query', "dojo/topic", 'dojo/_base/array',"mui/dialog/Tip","mui/i18n/i18n!sys-lbpmservice:lbpmNode", 'dojo/NodeList-html'],
		function(registry, Address, syslbpmprocess,query , topic, array, tip, msg){
	/*******************************************************************************
	 * 功能：处理人“沟通”操作的审批所用JSP，此JSP路径在处理人“驳回”操作扩展点定义的reviewJs参数匹配 使用： 作者：罗荣飞
	 * 创建时间：2012-06-06
	 ******************************************************************************/
	var communite={};
	var addressHtml = '';
	 
	var OperationBlur = function () {
		lbpm.globals.clearDefaultUsageContent("handler_communicate");
	};
	
	// 处理人操作：沟通
	var OperationClick = function(operationName) {
		/*
		 * relations当前工作项关联的工作项XML(多级沟通的XML)
		 * relationWorkitemId,关联的工作项ID(指父级工作项ID)
		 * relationScope,沟通范围
		 */
		lbpm.globals.setDefaultUsageContent('handler_communicate');
		lbpm.globals.getOperationParameterJson("relationWorkitemId:relationScope:relations:isMutiCommunicate",true); // 加载后端数据
		communite.handlerOperationTypeCommunicate(operationName);
	};

	//“沟通”操作的检查
	var OperationCheck = function() {
		var input = $("#toOtherHandlerIds")[0];
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
	// 设置"沟通"操作的参数
	var setOperationParam = function() {
		//沟通人员
		var input = $("#toOtherHandlerIds")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		//沟通人员名称
		input = $("#toOtherHandlerNames")[0];
		lbpm.globals.setOperationParameterJson(input, null, "param");
		
		var widget = registry.byId("isMutiCommunicate");
		if (widget) {
			lbpm.globals.setOperationParameterJson(widget.get("checked"),
					"isMutiCommunicate", "param");
		}
		input = $("input[key='communicateScopeHandlerIds']")[0];
		if (input) {
			lbpm.globals.setOperationParameterJson(input,
					"communicateScopeHandlerIds", "param");
		}
		widget = registry.byId("isHiddenNoteCheckbox");
		if (widget) {
			lbpm.globals.setOperationParameterJson(widget.get("checked"),
					"isHiddenNote", "param");
		}
	};

	// 处理人操作：沟通
	communite.handlerOperationTypeCommunicate = function(operationName,isACommunicate) {
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
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.CommunicatePeople;

		var operatorInfo = lbpm.globals
				.getOperationParameterJson("relationWorkitemId:relationScope");
		var currentOrgIdsObj = document
				.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		var exceptValueStr = lbpm.globals.stringConcat(currentOrgIdsObj.value, ids);
	    var addressSubject = msg['lbpmNode.toOtherHandler.address.subject.select']+operationName+msg['lbpmNode.toOtherHandler.address.subject.person'];
		var options = {
				mulSelect : true,
				idField : 'toOtherHandlerIds',
				nameField : 'toOtherHandlerNames', 
				splitStr : ';',
				selectType : ORG_TYPE_POSTORPERSON,
				notNull : ids.length < 1, //如果已存在沟通人员则当前沟通人员不一定为必选的
				alertText : (lbpm.constant.opt.CommunicateIsNull + operationName + lbpm.constant.opt.CommunicatePeople),
				text : lbpm.constant.SELECTORG,
				exceptValue : exceptValueStr.split(';'),
				validate:'communicateHandlerRequired required',
				id:"toHandlerAddress",
				subject:addressSubject,
				isShowSubject:true
		};
		
		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_communicate"] && currentNodeObj.operationScope["handler_communicate"] != "all"){
			var scopeType = currentNodeObj.operationScope["handler_communicate"] == null?"":currentNodeObj.operationScope["handler_communicate"];
			var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
			var customHandlerSelectType = currentNodeObj.operationScope["handler_communicate_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_communicate_customHandlerSelectType"];
			var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
			var customHandlerIds = currentNodeObj.operationScope["handler_communicate_customIds"] == null?"":currentNodeObj.operationScope["handler_communicate_customIds"];
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
			addressHtml = '';
			addressHtml += "<input type='hidden' name='toOtherHandlerIds' id='toOtherHandlerIds'/>";
			addressHtml += "<input type='hidden' name='toOtherHandlerNames' id='toOtherHandlerNames'/>";
			addressHtml += '<div id=\'toHandlerAddress\' data-dojo-type="sys/lbpmservice/mobile/opthandler/OptHandler"'
				 + ' data-dojo-props="subject:\''+addressSubject+'\',validate:\'communicateHandlerRequired required\',required:true,showHeadImg:true,idField:\'toOtherHandlerIds\',isMul:'+options.mulSelect+','
				 + 'dataUrl:\'/sys/lbpmservice/mobile/opthanlder.do?method=scopeHandlers'+dataUrl+'\','
				 + 'nameField:\'toOtherHandlerNames\'"></div>';
		}
		
		// 如果在节点属性设置了限定范围，则不带出默认沟通人
		if (addressHtml=='') {
			// 设置默认沟通人
			if (currentNodeObj.operationScope && currentNodeObj.operationScope["handler_communicate"] == 'all' 
				&& currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"] 
				&& currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"]!="") {
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
				
				options['idValue'] = defaultHandlerIds;
				options['nameValue'] = defaultHandlerNames;
			} else {
				if(Lbpm_SettingInfo && Lbpm_SettingInfo['isCommunicateWithCreatorDefault']=="true"){
					//var curHanderId = $("[name='sysWfBusinessForm.fdCurHanderId']").val();
					var draftorId = $("[name='sysWfBusinessForm.fdDraftorId']").val();
					var canCommunicate = true;
					if (operatorInfo.relationScope) {
						if(operatorInfo.relationScope.indexOf(draftorId)==-1){
							canCommunicate = false;
						}
					}
					if(canCommunicate && (exceptValueStr && exceptValueStr.indexOf(draftorId) == -1)){
						options['idValue'] = draftorId;
						options['nameValue'] = $("[name='sysWfBusinessForm.fdDraftorName']").val();
					}
				}
			}
		}
		
		// 被沟通对象被限定范围时
		if (operatorInfo.relationWorkitemId && operatorInfo.relationScope) {
				html += "<input type='hidden' name='toOtherHandlerIds' id='toOtherHandlerIds'/>";
				html += "<input type='hidden' name='toOtherHandlerNames' id='toOtherHandlerNames'/>";
				html += '<div id=\'toHandlerAddress\' data-dojo-type="sys/lbpmservice/mobile/opthandler/OptHandler"'
					 + ' data-dojo-props="subject:\''+addressSubject+'\',validate:\'communicateHandlerRequired required\',required:true,showHeadImg:true,idField:\'toOtherHandlerIds\','
					 + 'optHandlerIds:\'' + operatorInfo.relationScope + '\','
					 + 'dataUrl:\'/sys/lbpmservice/mobile/opthanlder.do?method=communicateHandlers&scopeHandles=!{optHandlerIds}\','
					 + 'nameField:\'toOtherHandlerNames\'"></div>';
		} else {
			if(addressHtml!=''){
				html += addressHtml;
			} else {
				html += lbpm.address.html_build(options);
			}
		}
		
		//判断是否为节点处理的第一个沟通发起者,设置是否允许多级沟通
		if (!isACommunicate && (operatorInfo.isMutiCommunicate==true || operatorInfo.isMutiCommunicate==null)) {
			// 是否可进行多级沟通
			if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiCommunicateConfigurable']=="true" && lbpm.flowcharts['multiCommunicateEnabled']!="false"){
				html += "<div class='isMutiCommunicateCheckbox'>";
				html += '<div id="isMutiCommunicate" alertText="" key="isMutiCommunicate" data-dojo-type="mui/form/CheckBox"';
				html += ' data-dojo-props="name:\'isMutiCommunicate\', value:\'true\', mul:false, text:\'';
				html += lbpm.constant.opt.CommunicateScopeAllowMuti + operationName + '\'';
				html += '"></div></div>';
				if (!lbpm.globals.setCommunicateScope.listener) {
					require(["dojo/topic"], function(topic) {
						lbpm.globals.setCommunicateScope.listener = topic.subscribe('mui/form/checkbox/valueChange', function(checkbox, args) {
							if (args.name == 'isMutiCommunicate') {
								lbpm.globals.setCommunicateScope({checked:checkbox.checked, operationName: operationName});
							}
						});
					});
				}
				lbpm.globals.setCommunicateScope({checked:false}); // hack
			}
		};

		if (!operatorInfo.relationWorkitemId) {
			// 是否可隐藏沟通意见
			if(Lbpm_SettingInfo && Lbpm_SettingInfo['isHiddenCommunicateNoteConfigurable']=="true" && lbpm.flowcharts['hiddenCommunicateNoteEnabled']!='false'){
				html += "<div class='isHiddenNoteCheckbox'>";
				html += '<div id="isHiddenNoteCheckbox" alertText="" key="isHiddenNoteCheckbox" data-dojo-type="mui/form/CheckBox"';
				html += ' data-dojo-props="name:\'isHiddenNoteCheckbox\', value:\'true\', mul:false, text:\'';
				html += lbpm.constant.opt.CommunicateHiddenNote + '\'';
				html += '"></div></div>';
			}
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

		if (!isACommunicate && (operatorInfo.isMutiCommunicate==true || operatorInfo.isMutiCommunicate==null)) {
			// 是否默认勾选多级沟通
			if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiCommunicateConfigurable']=="true" && lbpm.flowcharts['multiCommunicateEnabled']!="false" && Lbpm_SettingInfo['isMultiCommunicateDefault'] == "true"){
				setTimeout(function() {
					var widget = registry.byId("isMutiCommunicate");
					widget.set("checked",true);
					lbpm.globals.setCommunicateScope({checked:true, operationName: operationName});
				},100);
				
			}
		}
	};

	communite.hidden_Communicate_Scope = lbpm.globals.hidden_Communicate_Scope = function() {
		var operationsRow_Scope = document.getElementById("operationsRow_Scope");

		if (operationsRow_Scope)
			operationsRow_Scope.style.display = "none";
	};
	//限定沟通范围处理
	communite.setCommunicateScope = lbpm.globals.setCommunicateScope = function(sel) {
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
					+ lbpm.constant.opt.CommunicateScopeLimitScope
					+ "<span style='color:#999'>" + lbpm.constant.opt.CommunicateScopeIsNullNoLimit + "</span>";
			var htmlContent = "";
			var currentOrgIdsObj = document
					.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
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
				htmlContent += addressHtml.replace(/toOtherHandlerIds/g,'communicateScopeHandlerIds').replace(/toOtherHandlerNames/g,'communicateScopeHandlerNames').replace(/id='toHandlerAddress'/g,"").replace(/id="toHandlerAddress"/g,"");
			} else {
				options.id = "";//重置掉id
				htmlContent += lbpm.address.html_build(options);
			}
			//htmlContent += lbpm.constant.opt.CommunicateScopeIsNullNoLimit;
			query(operationsTDContent_Scope).html(htmlContent, {parseContent: true});
		}
	};

	communite.getCurrRelationInfo = lbpm.globals.getCurrRelationInfo = function() {
		var relationInfoObj = communite.getRelationInfoByWorkitemId(lbpm.nowProcessorInfoObj.id);
		return relationInfoObj;
	};

	communite.getRelationInfoByWorkitemId = function(id) {
		var relationInfoObj = communite.getRelationInfo();
		if(relationInfoObj==null) return [];
		var rtn = [];
		for ( var i = 0; i < relationInfoObj.length; i++) {
			if (relationInfoObj[i].srcId == id) {
				rtn.push(relationInfoObj[i]);
			}
		}
		return rtn;
	};

	communite.getRelationInfo = function() {
		var relationInfoXML=lbpm.globals.getOperationParameterJson("relations",true);
		if(relationInfoXML==null) return null;
		var relationInfoObj = WorkFlow_LoadXMLData(relationInfoXML);
		return relationInfoObj;
	};

	//清除operationsRow_Scope信息
	communite.handlerOperationClearOperationsRow_Scope = lbpm.globals.handlerOperationClearOperationsRow_Scope = function() {
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
	communite.stringConcat = lbpm.globals.stringConcat = function(str1, str2, splitChar) {
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
	
	communite['handler_communicate'] = lbpm.operations['handler_communicate'] = {
			click : OperationClick,
			check : OperationCheck,
			blur : OperationBlur,
			setOperationParam : setOperationParam
		};
	
	communite.init = function() {
	};
	
	return communite;
});
