/*******************************************************************************
 * 功能：处理人“补签”操作的审批所用JSP
  使用：
  作者：
 创建时间：2016-09-01
 ******************************************************************************/
( function(operations) {
	operations['handler_additionSign'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};

	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent("handler_additionSign");
	}	

	//fixed by wubing 提示有重复
	lbpm.globals._checkAdditionSignRepeatPersonInPost = function(rtnVal) {
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
						'oprType' : 'additionSign',
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

	//处理人操作：补签
	function OperationClick(operationName){
		lbpm.globals.setDefaultUsageContent("handler_additionSign");
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");

		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.AdditionSignPeople;
		var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");

		var options = {
			mulSelect : true,
			idField : 'toOtherHandlerIds',
			nameField : 'toOtherHandlerNames',
			splitStr : ';',
			selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
			action : "lbpm.globals._checkAdditionSignRepeatPersonInPost",//检测岗位中是否包含有所选择的人
			notNull : true,
			exceptValue : currentOrgIdsObj.value.split(';'),
			text : lbpm.constant.SELECTORG
		};
		if (lbpm.approveType == "right") {
			options["width"] = "95%";
		}
		var autoPopAction = null;
		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];

		if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_additionSign"]){
			var operationScope = currentNodeObj.operationScope["handler_additionSign"];
			if(operationScope  == "custom"){
				var scopeType = currentNodeObj.operationScope["handler_additionSign"] == null?"":currentNodeObj.operationScope["handler_additionSign"];
				var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
				var customHandlerSelectType = currentNodeObj.operationScope["handler_additionSign_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_additionSign_customHandlerSelectType"];
				var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
				var customHandlerIds = currentNodeObj.operationScope["handler_additionSign_customIds"] == null?"":currentNodeObj.operationScope["handler_additionSign_customIds"];
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
				options.onclick = "Dialog_AddressList(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", null)";

				autoPopAction = function(){
					Dialog_AddressList(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';',defaultOptionBean,
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
					Dialog_Address(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';',options.selectType,
						options.action, null, null,  options.notNull, null,
						options.text, options.exceptValue, deptLimit);
				};
			}
		} else {
			autoPopAction = function(){
				Dialog_Address(true, 'toOtherHandlerIds', 'toOtherHandlerNames', ';',options.selectType,
					options.action, null, null,  options.notNull, null,
					options.text, options.exceptValue);
			};
		}

		var html = lbpm.address.html_build(options);
		options.action = lbpm.globals._checkAdditionSignRepeatPersonInPost;
		html +=lbpm.globals.getNotifyType4Node(currentNodeObj);

		if (window.dojo) {
			require(['dojo/query', 'dojo/NodeList-html'], function(query) {
				query('#operationsTDContent').html(html, {parseContent: true});
				lbpm.globals.hiddenObject(operationsRow, false);
			});
		} else {
			// 设置补签的内容选择
			operationsTDContent.innerHTML = html;
			lbpm.globals.hiddenObject(operationsRow, false);

		}
		//兼容新审批操作控件
		if (typeof lbpm.globals.isNewAuditNote == "undefined"  || lbpm.globals.isNewAuditNote == false){
			setTimeout(autoPopAction,100);
		}

	};



	//“补签”操作的检查
	function OperationCheck(){
		var input=$("#toOtherHandlerIds")[0];
		if(input && input.value == ""){
			var operConent = $("input[name='oprGroup']:checked")[0].value.split(":")[1];
			var alertContent = lbpm.constant.opt.PleaseChoose +" "+ operConent + " " + lbpm.constant.opt.Staff;
			alert(alertContent);
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		//sysWfBusinessForm.fdHandlerRoleInfoIds不能选自己或者自己的岗位
		return true;			
	};	
	//设置"补签"操作的参数
	function setOperationParam()
	{
		//补签人员以及流转方式
		var input=$("#toOtherHandlerIds")[0];
		lbpm.globals.setOperationParameterJson(input,null,"param");
	};

})(lbpm.operations);