/*******************************************************************************
 * 功能：处理人“转办”操作的审批所用JSP，此JSP路径在处理人“转办”操作扩展点定义的reviewJs参数匹配
  使用：
  作者：罗荣飞
 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['handler_commission'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};	

	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_commission');
	}
	
	//处理人操作：转办
	function OperationClick(operationName, obj){
		lbpm.globals.setDefaultUsageContent('handler_commission');
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.CommissionPeople;
		var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");

		var options = {
				mulSelect : false,
				idField : 'toOtherHandlerIds',
				nameField : 'toOtherHandlerNames', 
				splitStr : ';',
				selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
				notNull : true,
				exceptValue : currentOrgIdsObj.value.split(';'),
				text : lbpm.constant.SELECTORG
		};
		if (lbpm.approveType == "right") {
			options["width"] = "95%";
		}
	    var autoPopAction = null;
	    var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		
		if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_commission"]){
			var operationScope = currentNodeObj.operationScope["handler_commission"];
			if(operationScope  == "custom"){
				var scopeType = currentNodeObj.operationScope["handler_commission"] == null?"":currentNodeObj.operationScope["handler_commission"];
				var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
				var customHandlerSelectType = currentNodeObj.operationScope["handler_commission_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_commission_customHandlerSelectType"];
				var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
				var customHandlerIds = currentNodeObj.operationScope["handler_commission_customIds"] == null?"":currentNodeObj.operationScope["handler_commission_customIds"];
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
					+ "&exceptValue=" + currentOrgIdsObj.value;
				var searchBean = defaultOptionBean + "&keyword=!{keyword}";
				
				options.onclick = "Dialog_AddressList(false, 'toOtherHandlerIds', 'toOtherHandlerNames', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", null)";
				
				autoPopAction = function(){
					Dialog_AddressList(false, 'toOtherHandlerIds', 'toOtherHandlerNames', ';',defaultOptionBean,
						options.action, searchBean, null,  options.notNull, null);
				};
			} else {
				var deptLimit = "";
				if(operationScope == "org"){
					deptLimit = "myOrg";
				}else if(operationScope == "dept"){
					deptLimit = "myDept";
				}
				options.deptLimit = deptLimit;
				autoPopAction = function(){
					Dialog_Address(false, 'toOtherHandlerIds', 'toOtherHandlerNames', ';',options.selectType,
						options.action, null, null,  options.notNull, null,
						options.text, options.exceptValue, deptLimit);
				};
			}
		} else {
			autoPopAction = function(){
				Dialog_Address(false, 'toOtherHandlerIds', 'toOtherHandlerNames', ';',options.selectType,
					options.action, null, null,  options.notNull, null,
					options.text, options.exceptValue);
			};
		}
		var html = lbpm.address.html_build(options);
		// 是否转办隐藏意见
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isHiddeTurnToDoNoteConfigurable']=="true" && lbpm.flowcharts['isHiddeTurnToDoNoteConfigurable']!='true'){
			html += "&nbsp;<label class='lui-lbpm-checkbox'>";
			html += "<input type='checkbox' key='isHiddenNote' value='true'><span class='checkbox-label'>" + lbpm.constant.opt.TurnToDoNoteHiddenNote + "</span></label><br>";
		}
		html += "&nbsp;";
		html += lbpm.globals.getNotifyType4Node(currentNodeObj);
		// 在转办时，增加“流程重新流经本节点时，直接由转办人员处理 ”的开关
		if (currentNodeObj.handlerSelectType=="org") {
			html += '<br>&nbsp;&nbsp;<label id="returnToCommissionedPersonLabel" class="lui-lbpm-checkbox"><input type="checkbox" id="returnToCommissionedPerson" value="true" alertText="" key="returnToCommissionedPerson"><span class="checkbox-label">'+lbpm.constant.opt.returnToCommissionedPerson.replace("{commission}",operationName)+'</span></label>';
		}
		operationsTDContent.innerHTML = html;
		lbpm.globals.hiddenObject(operationsRow, false);
		if (typeof lbpm.globals.isNewAuditNote == "undefined" || lbpm.globals.isNewAuditNote == false){
			setTimeout(autoPopAction,100);
		}
	};

	//“转办”操作的检查
	function OperationCheck(){
		var input=$("#toOtherHandlerIds")[0];
		if(input && input.value == ""){
			alert(lbpm.constant.opt.CommissionIsNull);
			return false;
		}
		//sysWfBusinessForm.fdHandlerRoleInfoIds不能选自己或者自己的岗位
		
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;			
	};	
	//设置"转办"操作的参数
	function setOperationParam()
	{
		//转办人员
		var input=$("#toOtherHandlerIds")[0];
		lbpm.globals.setOperationParameterJson(input,null,"param");
		if ($("[key='returnToCommissionedPerson']")[0]) {
			lbpm.globals.setOperationParameterJson($("[key='returnToCommissionedPerson']")[0], "returnToCommissionedPerson", "param");
		}
		 // 隐藏转办意见
        input = $("input[key='isHiddenNote']")[0];
        if(input) {
            lbpm.globals.setOperationParameterJson(input,
                "isHiddenNote", "param");
        }
	};
})(lbpm.operations);