lbpm.load_Frame=function(){
	// 审批日志
	lbpm.globals.load_Frame('historyInfoTableTD', lbpm.urls.auditNodeUrl);
};
lbpm.flow_chart_load_Frame=function(){
	if (lbpm.isFreeFlow) {//自由流
		//IE8浏览器自由流有问题，提示警告，
	    var DEFAULT_VERSION = 8.0;  
	    var ua = navigator.userAgent.toLowerCase();  
	    var isIE = ua.indexOf("msie")>-1;  
	    var safariVersion;  
	    if(isIE){  
	    	safariVersion =  ua.match(/msie ([\d.]+)/)[1];  
	    }  
	    if(safariVersion <= DEFAULT_VERSION ){  
	    	jNotify(lbpm.urls.freeChartUrlMessage, {
				TimeShown: 50000,
				autoHide:false,
				VerticalPosition: 'top',
				HorizontalPosition:'right',
				ShowOverlay: false
			});
	    }; 
		if(!($("#workflowInfoTD").closest("div").hasClass("process_body_checked_false")||
				$("#workflowInfoTD").closest("div").hasClass("process_body_checked_true"))){
			$("#workflowInfoTD").closest("div").addClass("process_body_checked_false");
		}
		if (lbpm.nowProcessorInfoObj) {
			if (!$("#WF_IFrame").attr('src')) {
				var fdIsModify=$("input[name='sysWfBusinessForm.fdIsModify']")[0];
				if(fdIsModify==null || fdIsModify.value!="1"){
					var processXml = lbpm.globals.getProcessXmlString();
					document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0].value = processXml;
				}
			}
		}
		var url = lbpm.urls.freeChartUrl;
		url+="&flowPopedom="+lbpm.nowNodeFlowPopedom;
		lbpm.globals.load_Frame('workflowInfoTD', url);
		$('#flowGraphicTemp').hide();
		$('#flowNodeDIV').show();
	} else {
		lbpm.globals.load_Frame('workflowInfoTD', lbpm.urls.chartUrl);
	}
	lbpm.globals.flowChartLoaded = true;
};
lbpm.flow_chart_load_Frame_Ding=function(){
	if (lbpm.isFreeFlow) {//自由流
		//IE8浏览器自由流有问题，提示警告，
	    var DEFAULT_VERSION = 8.0;  
	    var ua = navigator.userAgent.toLowerCase();  
	    var isIE = ua.indexOf("msie")>-1;  
	    var safariVersion;  
	    if(isIE){  
	   	    safariVersion =  ua.match(/msie ([\d.]+)/)[1];  
	    }  
	    if(safariVersion <= DEFAULT_VERSION ){  
	    	jNotify(lbpm.urls.freeChartUrlMessage, {
				TimeShown: 50000,
				autoHide:false,
				VerticalPosition: 'top',
				HorizontalPosition:'right',
				ShowOverlay: false
			});
	    }; 
		if(!($("#workflowInfoTD").closest("div").hasClass("process_body_checked_false")||
				$("#workflowInfoTD").closest("div").hasClass("process_body_checked_true"))){
			$("#workflowInfoTD").closest("div").addClass("process_body_checked_false");
		}
		if (lbpm.nowProcessorInfoObj) {
			if (!$("#WF_IFrame").attr('src')) {
				var fdIsModify=$("input[name='sysWfBusinessForm.fdIsModify']")[0];
				if(fdIsModify==null || fdIsModify.value!="1"){
					var processXml = lbpm.globals.getProcessXmlString();
					document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0].value = processXml;
				}
			}
		}
		var url = lbpm.urls.freeChartUrl+"&source=ding";
		url+="&flowPopedom="+lbpm.nowNodeFlowPopedom;
		lbpm.globals.load_Frame('workflowInfoTD', url);
		$('#flowGraphicTemp').hide();
		$('#flowNodeDIV').show();
	} else {
		lbpm.globals.load_Frame('workflowInfoTD', lbpm.urls.chartUrl+"&source=ding");
	}
	lbpm.globals.flowChartLoaded = true;
};
lbpm.flow_table_load_Frame=function(){
	lbpm.globals.load_Frame('workflowTableTD', lbpm.urls.flowTableUrl);
};
lbpm.flow_log_load_Frame=function(){
	lbpm.globals.load_Frame('flowLogTableTD', lbpm.urls.flowLogUrl);
};	

lbpm.process_status_load_Frame=function(){
	lbpm.globals.load_Frame('processStatusTD', lbpm.urls.processStatusUrl);
};

lbpm.process_status_load_Frame_Ding=function(fdModelId,modelClassName){
	lbpm.globals.load_Frame('processStatusTD', lbpm.urls.processStatusDinUrl
		+'?fdModelId='+fdModelId+'&fdModelName='+modelClassName);
};

lbpm.process_restart_Log_Frame=function(){
	lbpm.globals.load_Frame('lbpmProcessRestartLogTD', lbpm.urls.processRestartLogUrl);
};

if(window.seajs){
	seajs.use(['lui/imageP/preview'], function(preview) {
		window.previewImage = preview;
	});
};

var lbpmPinArray = [];

//判断是否给元素绑定了卡片弹出框
___hasLbpmPin = function(curObj){
	var isBind = false;
	for(var i=0; i<lbpmPinArray.length; i++){
		if(lbpmPinArray[i] && lbpmPinArray[i].obj == curObj){
			isBind = true;
			break;
		}
	}
	return isBind;
};

lbpm.person = function(event, element, type){
	seajs.use(['lui/jquery','lui/parser','lui/pinwheel'],function($,parser,p){
		p($,parser);
		if(!___hasLbpmPin(element)){
			lbpmPinArray.push({obj:element});
			if(type=="historyInfoTableIframe"){
				if($(element).parents("div#auditNoteTable").length>0){
					type="auditNoteRight";
				}
				//审批记录内的个人卡片弹出框
				$(element).pinwheel({"top":$(element).offset().top + $("#"+type).offset().top, "left":$(element).offset().left + $("#"+type).offset().left});
			}else if(type=="flowNodeUL"){
				//自由流个人卡片弹出框
				$(element).pinwheel({"top":$(element).offset().top, "left":$(element).offset().left});
			}
		}
	});
};

lbpm.globals.checkIsError = function(){
	var get_node_txt = function(nodeId) {
		var n = lbpm.nodes[nodeId];
		return (n ? (n.id + "." + n.name) : "");
	};
	lbpm.globals.isError = true;
	var nameInfo = lbpm.errorMsg.nameInfo;
	var tmpNotify = lbpm.errorMsg.tmpNotify;
	var notifyText = tmpNotify.replace("{admin}", nameInfo[0].adminName);
	jError(notifyText, {
		TimeShown: 8000,
		VerticalPosition: 'top',
		HorizontalPosition: 'right',
		ShowOverlay: false
	});
	var tmpFull = lbpm.errorMsg.tmpFull;
	var tmpMsg = lbpm.errorMsg.tmpMsg;
	var tmpDef = lbpm.errorMsg.tmpDef;
	var msg = lbpm.errorMsg.msg;
	var text = [];
	$.each(msg, function(index, row) {
		var errorNode = get_node_txt(row['errorId']);
		var errorType = row['errorType'];
		var errorMsg = row['errorMessage'];
		var errorText = null;
		if (errorType != null && errorType != '' && errorMsg != null && errorMsg != '') {
			errorText = tmpFull.replace("{node}", errorNode).replace("{type}", errorType).replace("{msg}", errorMsg);
		}
		else if (errorMsg != null && errorMsg != '') {
			errorText = (tmpMsg.replace("{node}", errorNode).replace("{msg}", errorMsg));
		}
		else {
			errorText = (tmpDef.replace("{node}", errorNode));
		}
		row['errorText'] = errorText;
		row['errorNodeName'] = errorNode;
		row['sourceNodeName'] = get_node_txt(row['sourceId']);

		var tmp = lbpm.errorMsg.tmp;
		tmp = tmp.replace('{user}', row['user']).replace('{node}', row['sourceNodeName']).replace('{opr}', row['operationName']);
		row['errorOperText'] = tmp;
		text.push(errorText);
	});
	$("#currentHandlersLabel").after("<div class='lbpm_error_info_div'><div class='lbpm_error_detail_div'></div><div style='text-align: right;color:#2f84fb'><span class='lui-lbpm-more lookMore'>"+lbpm.errorMsg.lbpmRightLookmore+"</span><span class='lui-lbpm-moreFold'>"+lbpm.errorMsg.lbpmRightClose+"</span></div></div>");
	$(".lbpm_error_info_div .lbpm_error_detail_div").text(text.join(""));
	
	// 流程出错提示
	$('.lbpm_error_info_div .lui-lbpm-more').click(function(){
		$('.lbpm_error_info_div .lbpm_error_detail_div').css({
			"max-height":"none",
		})
		$(this).removeClass('lookMore').next().addClass('lookMore')
	})
	$('.lbpm_error_info_div .lui-lbpm-moreFold').click(function(){
		$('.lbpm_error_info_div .lbpm_error_detail_div').css({
			"max-height":"40px",
		})
		$(this).removeClass('lookMore').prev().addClass('lookMore')
	})
	lbpm.globals.errorMessages = msg;
	LUI.ready(function(){
		 setTimeout(function(){
			//流程出错的提示
			 if($('.lbpm_error_info_div .lbpm_error_detail_div').height()<42){ 
		    	$('.lbpm_error_info_div .lui-lbpm-more').removeClass('lookMore');
			 }
		},0)  
	})
}

lbpm.globals.checkNodeMsg = function(msgCheckNode){
	var checkNodeMsg=msgCheckNode;
	if(checkNodeMsg["msg"]){
		var html = '<div class="lbpm_msg_info_div"></div>';
		if(lbpm.approveType){
			html = '<div class="lui-lbpm-warning-tips">';
			html +='  <div class="lui-lbpm-warning-tips-iocn"></div>';
			html +='  <label class="lui-lbpm-warning-tips-text"></label>';
			html +='</div>';
		}
		$("#currentHandlersLabel").after(html);
		if(lbpm.approveType){
			$(".lui-lbpm-warning-tips-text").text(checkNodeMsg["msg"]);
		}else{
			$(".lbpm_msg_info_div").text(checkNodeMsg["msg"]);
		}
	}
}
	
lbpm.globals.checkQueueLock = function(msg){
	new KMSSData().SendToBean("flowQueueLock&processId=" + lbpm.globals.getWfBusinessFormModelId(), function(rtnData){
		if(!rtnData){
			return;
		}
		if(rtnData.GetHashMapArray()[0] && rtnData.GetHashMapArray()[0].key0 == "true") {
			jNotify(msg, {
				TimeShown: 5000,
				VerticalPosition: 'top',
				HorizontalPosition: 'right',
				ShowOverlay: false,
				MinWidth: 300
			});
		}
	});
}

lbpm.globals.checkShortReview = function(msg){
	if (!window.LUI) {
		return;
	}
	//简单弹出框模式和 操作按钮平铺模式不需要出现快速审批
	if(Lbpm_SettingInfo && Lbpm_SettingInfo['approveModel'] && (Lbpm_SettingInfo['approveModel']=='dialog' 
			|| Lbpm_SettingInfo['approveModel']=='tiled')){
		return;
	}
	//右侧审批模式不需要出现快速审批
	if(lbpm.approveType=="right"){
		return;
	}
	if (lbpm.nowProcessorInfoObj) {
		lbpm.globals.initShortReview(msg);
	}
}

function autoSaveDraftAction() {
	var oldValue = "", timer = null;
	var doSave = function() {
		try {
			$("[name='fdUsageContent']").each(function(i, fdUsageContent) {
				if (oldValue != fdUsageContent.value) {
					var defalutUsage = "";
					defalutUsage = lbpm.globals.getOperationDefaultUsage(lbpm.currentOperationType);
					if(defalutUsage != fdUsageContent.value){
						oldValue = fdUsageContent.value;
						lbpm.globals.saveDraftAction();
					}
				}
			});
		} catch (e) {}
		timer = setTimeout(doSave, 8000);
	};
	timer = setTimeout(doSave, 8000);
}

lbpm.globals.initAssign = function(){
	// 若当前节点的下一个节点是结束节点，流程提交后马上就要结束了，则不再加载分发操作信息行
	if (lbpm.nowNodeId && (lbpm.nodes[lbpm.nowNodeId]["canAssign"] != "true" || lbpm.nodes[lbpm.nowNodeId].endLines[0].endNode.id == "N3")) {
		return;
	}
	function loadAssignInfo() {
		// 构建分发操作信息行
		var options = {
				mulSelect : true,
				idField : 'toAssigneeIds',
				nameField : 'toAssigneeNames', 
				splitStr : ';',
				selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
				notNull : false,
				exceptValue : document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds").value.split(';'),
				text : lbpm.constant.SELECTORG
		};
		if (lbpm.approveType == "right") {
			options["width"] = "99%";
		}
		var rowContentHtml = "";
		rowContentHtml += lbpm.address.html_build(options);
		rowContentHtml += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
		rowContentHtml += "<input id='canMultiAssign' type='checkbox' key='canMultiAssign'>";
		rowContentHtml += '<span class="checkbox-label">' + lbpm.assignMsg.allowMuti + lbpm.assignMsg.assign + '</span>';
		rowContentHtml += "</label>";
		if (lbpm.approveType == "right") {
			var oprRowHtml = '<div id="assignOprRow" lbpmmark="operation">';
			oprRowHtml += '<div class="lui-lbpm-titleNode" id="operationsTDTitle">' + lbpm.assignMsg.assign + '</div>';
			oprRowHtml += '<div class="lui-lbpm-detailNode" id="operationsTDContent">';
			oprRowHtml += rowContentHtml;
			oprRowHtml += '</div></div>';
			$("#operationsRow_Scope").after(oprRowHtml);
		} else {
			var oprRow = $('<tr id="assignOprRow" lbpmMark="operation"></tr>');
			var rowTitle = $('<td class="td_normal_title" width="15%">' + lbpm.assignMsg.assign + '</td>');
			var rowContent = $('<td>' +rowContentHtml+ '</td>');
			oprRow.append(rowTitle);
			oprRow.append(rowContent);
			$("#operationsRow_Scope").after(oprRow);
		}
	}
	if (lbpm.currentOperationType == "handler_pass") {
		loadAssignInfo();
	}
	lbpm.events.addListener(lbpm.constant.EVENT_CHANGEOPERATION, function() {
		// 当前选中的操作是通过操作时，加载分发操作信息行
		if (lbpm.currentOperationType == "handler_pass") {
			loadAssignInfo();
		}
	});
	lbpm.events.addListener(lbpm.constant.EVENT_SETOPERATIONPARAM, function() {
		// 通过操作提交时，填充分发操作信息到流程操作参数里面
		if (lbpm.currentOperationType == "handler_pass") {
			if ($("#toAssigneeIds")[0].value != "") {
				var assignParam = {};
				assignParam["toAssigneeIds"] = $("#toAssigneeIds")[0].value;
				assignParam["canMultiAssign"] = $("input[key='canMultiAssign']")[0].checked;
				lbpm.globals.setOperationParameterJson(JSON.stringify(assignParam), "assignParam", "param");
			}
		}
	});
}

//附件控件设置了审批人必填，仅通过操作做提示即可，转办和驳回等操作不提示必填
Com_Submit.ajaxBeforeSubmit = function modAttRequired(){
	if(typeof(Attachment_ObjectInfo) == "undefined" || (lbpm && lbpm.constant && "false" === lbpm.constant.IS_HANDER)){
		return;
	}
	if (lbpm && lbpm.currentOperationType != "handler_pass" && lbpm.currentOperationType != "drafter_submit" && lbpm.currentOperationType != "handler_sign"){
		for(var attachment_ObjectInfo in Attachment_ObjectInfo){
			Attachment_ObjectInfo[attachment_ObjectInfo]._required = Attachment_ObjectInfo[attachment_ObjectInfo].required;
			Attachment_ObjectInfo[attachment_ObjectInfo].required = false;
		}
	}
};
//(未提交成功)还原附件控件必填属性
Com_Submit.ajaxCancelSubmit = function restoreAttRequired(){
	if(typeof(Attachment_ObjectInfo) == "undefined" || (lbpm && lbpm.constant && "false" === lbpm.constant.IS_HANDER)){
		return;
	}
	if (lbpm && lbpm.currentOperationType != "handler_pass" && lbpm.currentOperationType != "drafter_submit" && lbpm.currentOperationType != "handler_sign"){
		for(var attachment_ObjectInfo in Attachment_ObjectInfo){
			if(Attachment_ObjectInfo[attachment_ObjectInfo]._required)
			Attachment_ObjectInfo[attachment_ObjectInfo].required = Attachment_ObjectInfo[attachment_ObjectInfo]._required;
		}
	}
};

/*
 * 分发开始
 * */
lbpm.globals.initOtherAssign = function(){
	var isAssignee = false; // 是否为被分发人
	var assignData = lbpm.assignMsg.assignData;
	if (assignData != null && assignData.length > 0) {
		isAssignee = true;
		lbpm.nowAssignItem = assignData[0];
	} else {
		return;
	}
	// 当前用户非当前审批人且是被分发人时加载分发信息, 构建分发操作信息行
	var canAssign = (lbpm.nowAssignItem.isCanAssign == "true");
	var options = {
			mulSelect : true,
			idField : 'toAssigneeIds',
			nameField : 'toAssigneeNames', 
			splitStr : ';',
			selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
			notNull : false,
			exceptValue : document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds").value.split(';'),
			text : lbpm.constant.SELECTORG
	};
	if (lbpm.approveType == "right") {
		options["width"] = "99%";
	}
	var rowContentHtml = "";
	rowContentHtml += '<div id="assignTypeDIV">';
	rowContentHtml += "<label style='padding-right:10px' class='lui-lbpm-radio'><input type='radio' name='assignType' value='0' onclick='document.getElementById(\"assignSelectDIV\").style.display=\"none\";' checked>"+'<span class="radio-label">'+lbpm.assignMsg.replyAssign+"</span></label>";
	rowContentHtml += "<label class='lui-lbpm-radio'><input type='radio' name='assignType' value='1' onclick='document.getElementById(\"assignSelectDIV\").style.display=\"\";'>"+'<span class="radio-label">'+lbpm.assignMsg.assign+"</span></label><br/>";
	rowContentHtml += '</div>';
	rowContentHtml += '<div id="assignSelectDIV" style="margin-top:3px;display:none;">';
	rowContentHtml += lbpm.address.html_build(options);
	rowContentHtml += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
	rowContentHtml += "<input id='canMultiAssign' type='checkbox' key='canMultiAssign'>";
	rowContentHtml += '<span class="checkbox-label">'+lbpm.assignMsg.allowMuti +lbpm.assignMsg.assign+ '</span>';
	rowContentHtml += "</label>";
	rowContentHtml += '</div>';
	
	if (lbpm.approveType == "right") {
		var oprRowHtml = '<div id="assignOprRow"  style="'+(canAssign?'':'display:none')+'">';
		oprRowHtml += '<div class="lui-lbpm-titleNode" id="operationsTDTitle">'+lbpm.assignMsg.assign + '</div>';
		oprRowHtml += '<div class="lui-lbpm-detailNode" id="operationsTDContent">';
		oprRowHtml += rowContentHtml;
		oprRowHtml += '</div></div>';
		$("#operationsRow_Scope").after(oprRowHtml);
		
		// 构建分发意见行
		var opinionRowHtml = '<div id="assignOpinionRow" >';
		opinionRowHtml += '<div class="lui-lbpm-titleNode" id="operationsTDTitle">'+lbpm.assignMsg.assignOpinion + '</div>';
		opinionRowHtml += '<div class="lui-lbpm-detailNode" id="operationsTDContent">';
		rowContentHtml = '<table width=100% border=0 class="tb_noborder"><tr><td width="85%">'
               + '<textarea name="fdAssignOpinion" class="inputMul" style="width:100%;" validate="maxLength(4000)"></textarea></td>'
               + '<td width="15%"><input id="process_assign_button" class="process_review_button" style="margin-left: 8px;" type=button value="'+lbpm.assignMsg.submit+'" onclick="lbpm.globals.assignOprOnSubmit();"/>'
               + '</td></tr></table>';
        opinionRowHtml += rowContentHtml;
        opinionRowHtml += '</div></div>';
		$("#assignOprRow").after(opinionRowHtml);
		
	} else {
		var oprRow = $('<tr id="assignOprRow" style="'+(canAssign?'':'display:none')+'" lbpmMark="operation"></tr>');
		var rowTitle = $('<td class="td_normal_title" width="15%">'+lbpm.assignMsg.assignOption+'</td>');
		var rowContent = $('<td>' +rowContentHtml+ '</td>');
		oprRow.append(rowTitle);
		oprRow.append(rowContent);
		if (Lbpm_SettingInfo && (Lbpm_SettingInfo.approveModel == "dialog" || Lbpm_SettingInfo.approveModel == "tiled")) {
			$("#historyTableTR").after(oprRow);
		} else {
			$("#operationsRow_Scope").after(oprRow);
		}
		if ($("#descriptionRow")[0].style.display == "none") {
			// 构建分发意见行
			var opinionRow = $('<tr id="assignOpinionRow" lbpmMark="operation"></tr>');
			rowTitle = $('<td class="td_normal_title" width="15%">' +lbpm.assignMsg.assignOpinion+ '</td>');
			rowContentHtml = '<table width=100% border=0 class="tb_noborder"><tr><td width="85%">'
               + '<textarea name="fdAssignOpinion" class="inputMul" style="width:100%;" validate="maxLength(4000)"></textarea></td>'
               + '<td width="15%"><input id="process_assign_button" class="process_review_button" style="margin-left: 8px;" type=button value="'+lbpm.assignMsg.submit+'" onclick="lbpm.globals.assignOprOnSubmit();"/>'
               + '</td></tr></table>';
			rowContent = $('<td>' +rowContentHtml+ '</td>');
			opinionRow.append(rowTitle);
			opinionRow.append(rowContent);
			$("#assignOprRow").after(opinionRow);
		}
	}
}

lbpm.globals.assignOprOnSubmit = function() {
	var kmssData = new KMSSData();
	var assignFormData = [];
	assignFormData.push({"fdAssignItemId":lbpm.nowAssignItem.id});
	assignFormData.push({"fdAssignOpinion":$("textarea[name='fdAssignOpinion']")[0].value});
	if ($('input[name="assignType"]:checked').val() == "1") {
		assignFormData.push({"toAssigneeIds":$("#toAssigneeIds")[0].value});
		assignFormData.push({"fdIsCanAssign":$("input[key='canMultiAssign']")[0].checked});
	}
	kmssData.AddHashMapArray(assignFormData);
	kmssData.SendToUrl(Com_Parameter.ContextPath
			+ 'sys/lbpmservice/support/lbpm_assign/lbpmAssign.do?method=doAssign', function(request) {
		var responseText = request.responseText;
		var successOrFailure = Com_GetUrlParameter(responseText, "operationKey");
		if(successOrFailure != lbpm.constant.SUCCESS && successOrFailure != lbpm.constant.FAILURE){
			return;
		}
		var url= Com_Parameter.ContextPath+'sys/lbpmservice/support/lbpm_assign/lbpmAssign.do?method=' + successOrFailure;
		document.forms[0].action=url;
		document.forms[0].submit();
	}, true);
};
/*
 * 分发结束
 */

/*
 * 流程跟踪开始
 */
if (window.LUI) {
	LUI.ready(function(){
		setTimeout(initLbpmFollow,100);
	});
}else{
	lbpm.onLoadEvents.delay.push(initLbpmFollow);
}
//判断obj是否为json对象
function isJson(obj){
	var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
	return isjson;
}
function initLbpmFollow() {
	var followOptButton = document.getElementById("followOptButton");
	var cancelFollowOptButton = document.getElementById("cancelFollowOptButton");
	
	if(followOptButton == null && cancelFollowOptButton == null){
		return;
	}
	var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=getIsFollowed';
	url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
	var kmssData = new KMSSData();
	var followInfo = {};
		
	window.lbpmFollowRecordTypeFun = function(src){
		var val = src.value;
		var nodeObjs = $("[name='node']",$(".lbpmFollow"));
		if (val === "1"){
			nodeObjs.prop("checked", false);
			nodeObjs.prop("disabled", true);
			for (let i = 0; i < nodeObjs.length; i++) {
				$form(nodeObjs[i]).required(false);
			}
		}else{
			nodeObjs.prop("disabled", false);
			for (let i = 0; i < nodeObjs.length; i++) {
				let nodeObj = nodeObjs[i];
				if (nodeObj._xForm_cache && nodeObj._xForm_cache.maxLevel==1){
					nodeObj._xForm_cache.maxLevel=2;
				}
				$form(nodeObj).required(true);
			}
		}
	};
		
	//构建跟踪节点html
	var buildReocrdHtml = function(){
		var html = [];
		var isAll = followInfo.nodeIds ? false : true;
		html.push("<div class='lbpmFollow'>");
		html.push("<div class='recordType'><label><input type='radio' onclick='lbpmFollowRecordTypeFun(this);' name='recordType' " + (isAll ? "checked='true'" : "") + " value='1'/>"+lbpm.followMsg.allNodes+"</label><br/>");
		html.push("<label><input type='radio'  onclick='lbpmFollowRecordTypeFun(this);' name='recordType'" + (isAll ? "" : "checked='true'") +" value='0'/>"+lbpm.followMsg.specifiedNode+"</label></div>");
		var nodes = lbpm.nodes;
		html.push("<div class='nodeIds'>")
		for (var key in nodes){
			var nodeObj = nodes[key];
			if('signNode' == nodeObj.XMLNODENAME 
					|| 'reviewNode' == nodeObj.XMLNODENAME
					|| 'robtodoNode' == nodeObj.XMLNODENAME){
				
				html.push("<label><input type='checkBox' name='node' value='" + nodeObj.id + "'");
				if (!isAll){
					var followIdArr = followInfo.nodeIds.split(";");
					for (var i = 0; i < followIdArr.length; i++){
						var nodeId = followIdArr[i];
						if (key === nodeId){
							html.push(" checked='true'");
							break;
						}
					}
				}else{
					html.push(" disabled ");
				}
				
				var nodeLangs="";
				try{
					nodeLangs=JSON.parse(nodeObj.langs);
				}catch(e){}
				
				var nodeNameTemp="";
				if(isJson(nodeLangs)){
					try{
						var nodeNames= nodeLangs.nodeName;
						for(var z=0;z<nodeNames.length;z++){
							if(nodeNames[z].lang.toLowerCase()==Com_Parameter.Lang){
								nodeNameTemp=nodeNames[z].value;
							}
						}
					}catch(e){}
				}
				
				if(nodeNameTemp==""){
						html.push("/>" + nodeObj.name  + "</label><br/>");
				}else{
					html.push("/>" + nodeNameTemp + "</label><br/>");
				}
				
			}
		}
		html.push("</div>");
		html.push("</div>");
		return html.join("");
	};
	//取消跟踪
	var cancelText = lbpm.followMsg.cancelText;
	//跟踪流程
	var followText = lbpm.followMsg.followText;
	//取消/跟踪流程
	var followCancelText = lbpm.followMsg.followCancelText;
	
	var followUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_follow/lbpmFollowConfirm.jsp?isFollow=true";
	var cancelUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_follow/lbpmFollowConfirm.jsp?isFollow=false";
	//跟踪函数
	var followFun = function(){
		if (typeof(seajs) != 'undefined'){
			seajs.use([ 'lui/dialog' ], function(dialog) {
				var config = { config : {
					width : 400,
					cahce : false,
					title : followText,
					height : 600,
					content : {
						type : "common",
						html : buildReocrdHtml(),
						iconType : '',
						buttons : [ {
							name : lbpm.followMsg.ok,
							value : true,
							focus : true,
							fn : function(value, dialog) {
								followCallback();
								dialog.hide(value);
							}
						}, {
							name : lbpm.followMsg.cancel,
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						} ]
					}
				}};
				
				dialog.build(config).on('show', function() {
					this.element.find(".lui_dialog_common_content_right").css("max-width","100%");
					this.element.find(".lui_dialog_common_content_right").css("margin-left","0px");
				}).show();
			});
		}else{
			var lbpmInfo = {"lbpm":lbpm,"followInfo":followInfo,"newFollow":true};
			LbpmFollow_PopupWindow(followUrl,lbpmInfo,followCallback);
		}
	};

	//跟踪回调
	var followCallback = function(rtnData){
		//跟踪类型 1:全部节点;0:指定节点
		var recordType = $("[name='recordType']:checked").val();
		if (recordType == '0'){
			if($("input[name=node]:checked",$(".lbpmFollow")).length==0){
				alert("指定节点不能为空");
				return;
			}
		}
		//有返回值,说明用的是旧UI
		var oldUINodeIds;
		if (rtnData){
			var value = rtnData.GetHashMapArray()[0];
			recordType = value.recordType;
			if (!value.follow){
				return;
			}
			if (value.nodeIds){
				oldUINodeIds = value.nodeIds;
			}
		}
		//新UI可以从 $("[name='recordType']:checked").val()获取值,如果获取不到,又没有rtnData,则说明使用旧UI直接关掉了窗口导致没有返回值
		if (!recordType){
			return;
		}
		var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=recordFollow';
		url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value + "&modelName=" + $("[name='sysWfBusinessForm.fdModelName']")[0].value;
		url += "&recordType=" + recordType ;
		//指定节点
		var nodeIds = [];
		if (recordType === "0"){
			var nodeObjs = $("[name='node']:checked");
			nodeObjs.each(function(index,obj){
				nodeIds.push(obj.value);
			});
			url += "&nodeIds=" + (nodeIds.join(";") || (oldUINodeIds ? oldUINodeIds : ""));
		}else{
			url += "&nodeIds=";
		}
		var kmssData = new KMSSData();
		kmssData.SendToUrl(url, function(http_request) {
			var responseText = http_request.responseText;
			var json = eval("(" + responseText + ")");
			followInfo.nodeIds = json.nodeIds;
			if (json.result == "true") {
				//cancel标识是跟踪还是取消
				//isAll标识跟踪类型是全部节点还是指定节点
				if (json.isCancel === "true"){
					//显示跟踪
					lbpm.globals.hiddenObject(followOptButton, false);
					//隐藏取消
					lbpm.globals.hiddenObject(cancelFollowOptButton, true);
				}else{
					//隐藏跟踪
					lbpm.globals.hiddenObject(followOptButton, true);
					//显示取消
					lbpm.globals.hiddenObject(cancelFollowOptButton, false);
					if(json.isAll === "true"){
						//如果全部节点或者流程结束则显示"取消跟踪",
						if($("#cancelFollowOptButton")[0].tagName.toLowerCase()=="a"){
							$("#cancelFollowOptButton").text(cancelText);
						}else{
							$("#cancelFollowOptButton").attr("title",cancelText);
							var cancelFollowLUI = LUI("cancelFollowOptButton");
							if(cancelFollowLUI!=null){
								cancelFollowLUI.textContent.text(cancelText);
							}
						}
						cancelFollowOptButton.onclick = cancelFollowFun;
					}else{
						//如果指定节点则显示"跟踪/取消跟踪"
						if($("#cancelFollowOptButton")[0].tagName.toLowerCase()=="a"){
							$("#cancelFollowOptButton").text(followCancelText);
						}else{
							$("#cancelFollowOptButton").attr("title",followCancelText);
							var cancelFollowLUI = LUI("cancelFollowOptButton");
							if(cancelFollowLUI!=null){
								cancelFollowLUI.textContent.text(followCancelText);
							}
						}
						cancelFollowOptButton.onclick = followFun;
					}
				}
			}
		});
	};
	
	//旧交互跟踪函数
	var oldFollowFun = function(){
		if (typeof(seajs) != 'undefined'){
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.confirm(lbpm.followMsg.followConfirm,oldFollowFunCallback);
			});
		}else{
			var lbpmInfo = {"lbpm":lbpm,"followInfo":followInfo,"oldFollow":true};
			var oldFollowUrl = followUrl + "&oldFollow=true";
			LbpmFollow_PopupWindow(followUrl,lbpmInfo,oldFollowFunCallback);
		}
	};
	
	//旧交互跟踪回调
	var oldFollowFunCallback = function(rtnData){
		if(!rtnData)return;
		var flag;
		if (jQuery.type(rtnData) === "boolean"){
			flag = rtnData;
		}else{
			var value = rtnData.GetHashMapArray()[0];
			flag = value.follow;
		}
		if(flag==true){
			var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=recordFollow';
			url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value + "&modelName=" + $("[name='sysWfBusinessForm.fdModelName']")[0].value;
			var kmssData = new KMSSData();
			kmssData.SendToUrl(url, function(http_request) {
				var responseText = http_request.responseText;
				var json = eval("(" + responseText + ")");
				if (json.result == "true") {
					lbpm.globals.hiddenObject(followOptButton, true);
					lbpm.globals.hiddenObject(cancelFollowOptButton, false);
				}
			});
	    }else{
	    	return;
		}
	};
		
	//取消跟踪函数
	var cancelFollowFun = function(){
		if (typeof(seajs) != 'undefined'){//
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.confirm(lbpm.followMsg.cancelFollowConfirm,cancelFollowFunCallback);
			});
		}else{
			var lbpmInfo = {"lbpm":lbpm,"followInfo":followInfo,"cancel":true};
			var winOpen = LbpmFollow_PopupWindow(cancelUrl,lbpmInfo,cancelFollowFunCallback);
		}
	};
	//取消跟踪回调
	var cancelFollowFunCallback = function(rtnData){
		if(!rtnData)return;
		var flag;
		//新UI返回boolean类型的值
		if (jQuery.type(rtnData) === "boolean"){
			flag = rtnData;
		}else{//旧UI返回kmssdialog对象
			var value = rtnData.GetHashMapArray()[0];
			flag = value.cancel;
		}
		if(flag==true){
			var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=cancelFollow';
			url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
			var kmssData = new KMSSData();
			kmssData.SendToUrl(url, function(http_request) {
				var responseText = http_request.responseText;
				var json = eval("(" + responseText + ")");
				if (json.result == "true") {
					lbpm.globals.hiddenObject(cancelFollowOptButton, true);
					lbpm.globals.hiddenObject(followOptButton, false);
				}
			});
	    }else{
	    	return;
		}
	};
			
	if(followOptButton!=null){
		//设置流程跟踪按钮
		if(window!=window.top){
			followOptButton.onclick = oldFollowFun
		}else{
			followOptButton.onclick = followFun;
		}
	}
	
	if(cancelFollowOptButton!=null){
		//设置取消跟踪按钮
		cancelFollowOptButton.onclick = cancelFollowFun;
	}
	var followInfo = lbpm.globals._getFollowedInfo();
	if (followInfo.isFollowed == "true") {
		if (followInfo.nodeIds){
			if($("#cancelFollowOptButton")[0].tagName.toLowerCase()=="a"){
				$("#cancelFollowOptButton").text(followCancelText);
			}else{
				$("#cancelFollowOptButton").attr("title",followCancelText);
				var cancelFollowLUI = LUI("cancelFollowOptButton");
				if(cancelFollowLUI!=null){
					cancelFollowLUI.textContent.text(followCancelText);
				}
			}
			cancelFollowOptButton.onclick = followFun;
		}
		//流程是否结束,若结束显示取消跟踪
		if (followInfo.isPassed){
			if($("#cancelFollowOptButton")[0].tagName.toLowerCase()=="a"){
				$("#cancelFollowOptButton").text(cancelText);
			}else{
				$("#cancelFollowOptButton").attr("title",cancelText);
				var cancelFollowLUI = LUI("cancelFollowOptButton");
				if(cancelFollowLUI!=null){
					cancelFollowLUI.textContent.text(cancelText);
				}
			}
			cancelFollowOptButton.onclick = cancelFollowFun;
		}
		lbpm.globals.hiddenObject(cancelFollowOptButton, false);
	} else {
		lbpm.globals.hiddenObject(followOptButton, false);
		if (followInfo.isPassed){
			followOptButton.onclick = oldFollowFun;
		}
	}
}

//判断当前用户是否是流程的跟踪者
lbpm.globals._getFollowedInfo = function() {
	if(typeof lbpm.followInfo != "undefined"){
		return lbpm.followInfo;
	}
	var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=getIsFollowed';
	url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
	var kmssData = new KMSSData();
	var followInfo = {};
	kmssData.SendToUrl(url, function(http_request) {
		var responseText = http_request.responseText;
		var json = eval("(" + responseText + ")");
		followInfo = lbpm.followInfo = json;
	},false);
	return followInfo;
}

//旧UI弹窗
function LbpmFollow_PopupWindow(url,lbpmInfo,action){
	var dialog = new KMSSDialog();
	var lbpmInfo = lbpmInfo;
	dialog.parameter = lbpmInfo;
	dialog.BindingField(null, null);
	dialog.SetAfterShow(function(value){
		action(value);
	});
	dialog.URL = url;
	dialog.Show(window.screen.width*400/1366,150);
} 
/**
 * 流程跟踪结束
 */

//更新工作项查看状态
lbpm.globals.updateIsLook = function() {
	var result = null;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=updateIsLook",
		async: false,
		data: {fdModelId:lbpm.modelId},
		type: "POST",
		dataType: 'json',
		success: function (data) {
			result = data;
		},
		error: function (er) {

		}
	});
	return result;
}

lbpm.onLoadEvents.delay.push(function() {
	//判断当前流程是否重启过，重启过则显示重启日志标签
	var liProcess_restart_Log_Frame=$("#liProcess_restart_Log_Frame");
	if(liProcess_restart_Log_Frame){
		var lbpmProcessRestartLogData = new KMSSData().AddBeanData("lbpmProcessRestartLogService&processId="+lbpm.modelId).GetHashMapArray();
		if(lbpmProcessRestartLogData.length>0){
			if(lbpmProcessRestartLogData[0].logType=="false"){
				liProcess_restart_Log_Frame.hide();
			}

		}	
	}
});
lbpm.onLoadEvents.delay.push(function(){
	//先获取一遍流程定义详细大字段内容
	var data = new KMSSData();
	data.AddBeanData("lbpmProcessDefinitionDetailService&processId="+lbpm.globals.getWfBusinessFormModelId());
	data.GetHashMapArray(function(){
		lbpm.flow_chart_load_Frame();
	});
});

//初始化
$(document).ready(function() {
	setTimeout(function(){
		for(var i=0; i<lbpm.onLoadEvents.once.length; i++){
			lbpm.onLoadEvents.once[i]();
		}
		for(var i=0; i<lbpm.onLoadEvents.delay.length; i++){
			lbpm.onLoadEvents.delay[i]();
		}
	},0);
});