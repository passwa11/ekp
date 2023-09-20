/*******************************************************************************
 * 功能：处理人“驳回”操作的审批所用JSP，此JSP路径在处理人“驳回”操作扩展点定义的reviewJs参数匹配
  使用：
  作者：罗荣飞
 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['admin_changeCurHandler'] = {
		click: OperationClick,
		check: OperationCheck,
		setOperationParam:setOperationParam
	};

	//特权人操作：修改当前处理人
	function OperationClick(operationName){
		var typeHtml = [];
		typeHtml.push('<label class="lui-lbpm-radio"><input type="radio" name="operationType" onclick="lbpm.globals.clickChangeCurHandlerType(this);" checked value="replace" /><span class="radio-label">'+lbpm.constant.opt.adminOperationTypeReplace+'</span></label>');
		typeHtml.push('<label class="lui-lbpm-radio"><input type="radio" name="operationType" onclick="lbpm.globals.clickChangeCurHandlerType(this);" value="append" /><span class="radio-label">'+lbpm.constant.opt.adminOperationTypeAppend+'</span></label>');
		$("#operationsTDTitle_Type").html(lbpm.constant.opt.adminOperationTypeModifyType);
		$("#operationsTDContent_Type").html(typeHtml.join(''));
		$("#operationsRow_Type").show();
	
		var param=lbpm.globals.getOperationParameterJson("curHandlers");
		var curHandlerHTML = [];
		for ( var i = 0; i < param.length; i++) {
			curHandlerHTML.push('<label class="lui-lbpm-checkbox"><input type="checkbox" name="curHandlers" checked value="'
							+ param[i].value
							+ '" username="'+param[i].name+'"><span class="checkbox-label">'
							+ param[i].name + '</span></label>');
		}

		$("#operationsTDTitle").html(lbpm.constant.opt.adminOperationTypeChgCurHandler);
		$("#operationsTDContent").html(curHandlerHTML.join(''));
		$("#operationsRow").show();
		
		//增加选择节点通知方式 add by wubing date:2015-05-06
		lbpm.globals.setAdminNodeNotifyType(lbpm.nowNodeId);

		var exceptValue = [];
		for ( var i = 0; i < param.length; i++) {
			if(!$("#operationsRow").find("input[name='curHandlers'][value="+param[i].value+"]").is(':checked')){
				exceptValue.push(param[i].handlerId);
			}
		}
		var allExpecter=lbpm.globals.getOperationParameterJson("allExpecter");
		for ( var j = 0; j < allExpecter.length; j++) {
			if(!allExpecter[j].isActive){
				exceptValue.push(allExpecter[j].handlerId);
			}
		}
		var handlerIds = "";
		var handlerNames = "";
		var html = "<input type='hidden' name='repHandlerIds' alertText='' value='" + handlerIds + "'><input type='text' name='repHandlerNames' alertText='' readonly class='inputSgl' style='width:70%;' value='" + handlerNames + "'>";
		html += '<' + 'a href="javascript:void(0)"';
		if(lbpm.nowProcessorInfoObj && lbpm.nowProcessorInfoObj.type=="shareReviewNode"){
			// 微审批节点处理人选择控制
			html += ' onclick="Com_EventPreventDefault();Dialog_Address(false, \'repHandlerIds\',\'repHandlerNames\', \';\', ORG_TYPE_PERSON, null, null, null, null, null , null,'+JSON.stringify(exceptValue).replace(/\"/g,"'")+');"';
		}else{
			html += ' onclick="Com_EventPreventDefault();Dialog_Address(true, \'repHandlerIds\',\'repHandlerNames\', \';\', ORG_TYPE_PERSON|ORG_TYPE_POST, null, null, null, null, null , null,'+JSON.stringify(exceptValue).replace(/\"/g,"'")+');"';
		}
		html += '>'+lbpm.constant.opt.selectOrg;
		html += '</a>';
		html += "&nbsp;<span class='txtstrong'>*</span>";
		$("#operationsTDTitle_Scope").html(lbpm.constant.opt.adminOperationTypeRepCurHandler);
		$("#operationsTDContent_Scope").html(html);
		$("#operationsRow_Scope").show();
	};
	//操作提交前检查
	function OperationCheck(){
		var repHandlerType = $("[name='operationType']:checked").val();
		if(repHandlerType == 'replace'){
			var checkedArr=$("[name='curHandlers']:checked");
			if(checkedArr.length==0){
				alert(lbpm.constant.opt.mustSelectCurHandler);
				return false;
			}
		}
		var currentHandler=$("[name='repHandlerIds']").val();
		if(currentHandler == '' || currentHandler == " "){
			if(repHandlerType == 'replace'){
				alert(lbpm.constant.opt.mustChangeOperator);
			}else{
				alert(lbpm.constant.opt.mustChangeAppendOperator);
			}
			return false;
		}
		return true;
	};			
	//操作提交的参数
	function setOperationParam(){
		input = $("[name='repHandlerIds']")[0];
		lbpm.globals.setOperationParameterJson(input, "repHandlerIds", "param");
		var repHandlerType = $("[name='operationType']:checked").val();
		lbpm.globals.setOperationParameterJson(repHandlerType, "repHandlerType", "param");
		//要替换的当前人工作项ID
		var cancelHandlerIds=""
		var cancelHandlerNames=""
		
		$("[name='curHandlers']:checked").each(function(i){
			if(cancelHandlerIds==''){
				cancelHandlerIds=this.value;
				cancelHandlerNames=$(this).attr("username");
			}	
			else{
				cancelHandlerIds+=";"+this.value;
				cancelHandlerNames+=";"+$(this).attr("username");
			}	
		});	
		
		lbpm.globals.setOperationParameterJson(cancelHandlerIds, "taskIds", "param");
		lbpm.globals.setOperationParameterJson(cancelHandlerNames, "taskUserNames", "param");
	};	
})(lbpm.operations);