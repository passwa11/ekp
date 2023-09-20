define(["mui/form/Address","dojo/topic", 'dojo/query', 'sys/lbpmservice/mobile/common/syslbpmprocess',"mui/dialog/Tip","mui/i18n/i18n!sys-lbpmservice:lbpmNode",'dojo/NodeList-html'],
		function(Address,topic, query, syslbpmprocess,tip,msg){
	/*******************************************************************************
	 * 功能：处理人“补签”操作的审批所用JSP
	  使用：
	  作者：
	 创建时间：2016-09-01
	 ******************************************************************************/
	var additionSign={};
	 
	var OperationBlur = function() {
		lbpm.globals.clearDefaultUsageContent("handler_additionSign");
	};

	//处理人操作：补签
	var OperationClick = function(operationName){
		lbpm.globals.setDefaultUsageContent("handler_additionSign");
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.AdditionSignPeople;
		var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		var addressSubject = msg['lbpmNode.toOtherHandler.address.subject.select']+operationName+msg['lbpmNode.toOtherHandler.address.subject.person'];
		
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.AdditionSignPeople;
		
		var options = {
				mulSelect : true,
				idField : 'toOtherHandlerIds',
				nameField : 'toOtherHandlerNames', 
				splitStr : ';',
				selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
				notNull : true,
				exceptValue : currentOrgIdsObj.value.split(';'),
				text : lbpm.constant.SELECTORG,
				alertText:lbpm.constant.opt.AdditionSignIsNull,
				validate:'additionSignHandlerRequired required',
				id:'toHandlerAddress',
				isShowSubject:true,
				subject:addressSubject
		};
		
		var html = "";
		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_additionSign"] && currentNodeObj.operationScope["handler_additionSign"] != "all"){
			var scopeType = currentNodeObj.operationScope["handler_additionSign"] == null?"":currentNodeObj.operationScope["handler_additionSign"];
			var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
			var customHandlerSelectType = currentNodeObj.operationScope["handler_additionSign_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_additionSign_customHandlerSelectType"];
			var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
			var customHandlerIds = currentNodeObj.operationScope["handler_additionSign_customIds"] == null?"":currentNodeObj.operationScope["handler_additionSign_customIds"];
			var handlerIdentity = $("input[name='sysWfBusinessForm.fdHandlerRoleInfoIds']").val().split(";")[0];
			//限定范围人员 
			var dataUrl =  "&currentId=" + lbpm.nowProcessorInfoObj.expectedId 
			+ "&handlerIdentity=" + handlerIdentity
			+ "&customHandlerSelectType=" + customHandlerSelectType
			+ "&customHandlerIds=" + encodeURIComponent(customHandlerIds)
			+ "&scopeType=" + scopeType
			+ "&fdModelName=" + lbpm.modelName
			+ "&fdModelId=" + lbpm.modelId
			+ "&exceptValue=" + currentOrgIdsObj.value;
		
			html += "<input type='hidden' name='toOtherHandlerIds' id='toOtherHandlerIds'/>";
			html += "<input type='hidden' name='toOtherHandlerNames' id='toOtherHandlerNames'/>";
			html += '<div id=\'toHandlerAddress\' data-dojo-type="sys/lbpmservice/mobile/opthandler/OptHandler"'
				 + ' data-dojo-props="subject:\''+addressSubject+'\',validate:\'additionSignHandlerRequired required\',required:true,showHeadImg:true,idField:\'toOtherHandlerIds\','
				 + 'dataUrl:\'/sys/lbpmservice/mobile/opthanlder.do?method=scopeHandlers'+dataUrl+'\','
				 + 'nameField:\'toOtherHandlerNames\'"></div>';

		} else {
			html = lbpm.address.html_build(options);
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
	};
	
	//“补签”操作的检查
	var OperationCheck = function(){
		var input = query("#toOtherHandlerIds")[0];
		if(input && input.value == ""){
			var optContent =document.getElementById('operationsTDTitle').innerHTML;
			var alertContent = constant.opt.PleaseChoose + " " + optContent;
			tip["warn"]({text:alertContent});
			//alert(alertContent);
			return false;
		}
		if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
			return lbpm.globals.validateMustSignYourSuggestion();
		}
		return true;			
	};
	
	//设置"补签"操作的参数
	var setOperationParam = function(){
		var input=query("#toOtherHandlerIds")[0];
		lbpm.globals.setOperationParameterJson(input,null,"param");

	};
	
	additionSign['handler_additionSign'] = lbpm.operations['handler_additionSign'] = {
		click:OperationClick,
		check:OperationCheck,
		blur:OperationBlur,
		setOperationParam:setOperationParam
	};	

	additionSign.init = function(){
	};
	return additionSign;
});
