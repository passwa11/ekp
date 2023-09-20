lbpm.onLoadEvents.once.push(function() {
	lbpm.globals.controlExtendRoleOptRow();
});

// 打开起草人或特权人处理流程的小窗口, roleType的值为drafter、authority或者historyhandler,branchadmin，operationType:默认选中的操作  
// selectedTaskId：平铺模式下选中的事物，isShowHisHandlerOpt: 当roleType为historyhandler,是否显示操作项
lbpm.globals.openExtendRoleOptWindow = function(roleType, operationType, selectedTaskId, isShowHisHandlerOpt) {
	var thirdSysFormList = [];
	if (typeof (_thirdSysFormList) == "object") {
		thirdSysFormList = _thirdSysFormList;
	}

	var param = {
		_thirdSysFormList : thirdSysFormList,// 第三方系统集成表单参数
		Window : window,
		AfterShow : function(rtnVal) {
			lbpm.globals.redirectPage(rtnVal);
		},
		roleType : roleType
	};
	var path = Com_Parameter.ContextPath+"sys/lbpmservice/include/sysLbpmProcess_panel_frame.jsp?modelId=";
	var seaJsPath = "/sys/lbpmservice/include/sysLbpmProcess_panel_frame.jsp?modelId=";
	var url = lbpm.globals.getWfBusinessFormModelId() + "&roleType=" + roleType
			+ (operationType ? "&operationType=" + operationType : "")
			+ "&docStatus=" + lbpm.constant.DOCSTATUS + "&modelName="
			+ lbpm.modelName + (selectedTaskId ? "&selectedTaskId=" + selectedTaskId : "")
			+ (isShowHisHandlerOpt ? "&isShowHisHandlerOpt=" + isShowHisHandlerOpt : "");
	if($("[name='fdModelId']").length>0){
		url = url + "&modelingModelId=" + $("[name='fdModelId']").val();;
	}
	if(roleType == lbpm.constant.BRANCHADMINROLETYPE){
		url += "&curNodeId="+lbpm.nowNodeId;
	}
	if (typeof seajs != "undefined") {
		seaJsPath += url;
		Com_Parameter.Dialog = param;
		var roleTitle = "";
		if(roleType === "drafter"){
			roleTitle = lbpm.constant.opt.drafter;
		}else if(roleType === "authority"){
			roleTitle = lbpm.constant.opt.authority;
		}else if(roleType === "historyhandler"){
			roleTitle = lbpm.constant.opt.historyhandler;
		}else if(roleType === "branchadmin"){
			roleTitle = lbpm.constant.opt.branchadmin;
		}
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			dialog.iframe(seaJsPath,roleTitle,function(rtnVal){
				if(rtnVal != null){
					lbpm.globals.redirectPage(rtnVal);
				}
			},{width:600,height:400,params:param});
		});
	}else{
		path += url;
		lbpm.globals.popupWindow(path, 600, 400, param);
	}
}

lbpm.globals.popupWindow = function(url, width, height, param) {
	var left = (screen.width - width) / 2;
	var top = (screen.height - height) / 2;
	var isWebKit = navigator.userAgent.indexOf('AppleWebKit') != -1;
	var isSafari = navigator.userAgent.indexOf('Safari') > -1 && navigator.userAgent.indexOf('Chrome') == -1;
	if ((window.showModalDialog && !isWebKit) || isSafari) {
		var winStyle = "resizable:1;scroll:1;dialogwidth:" + width
				+ "px;dialogheight:" + height + "px;dialogleft:" + left
				+ ";dialogtop:" + top;
		var rtnVal = window.showModalDialog(url, param, winStyle);
		if (param.AfterShow)
			param.AfterShow(rtnVal);
	} else {
		var winStyle = "resizable=1,scrollbars=1,width=" + width + ",height="
				+ height + ",left=" + left + ",top=" + top
				+ ",dependent=yes,alwaysRaised=1";
		Com_Parameter.Dialog = param;
		var tmpwin = window.open(url, "_blank", winStyle);
		if (tmpwin) {
			tmpwin.onbeforeunload = function() {
				
				if (navigator.userAgent.indexOf("Edge") > -1) {
					if (param.AfterShow && !param.AfterShow._isShow) {
						param.AfterShow._isShow = true;
						param.AfterShow(tmpwin.returnValue,tmpwin.FlowChartObject ? tmpwin.FlowChartObject.otherContentInfo : {});
					}
			   }else{
				   setTimeout(function(){
						if (param.AfterShow && !param.AfterShow._isShow) {
							param.AfterShow._isShow = true;
							param.AfterShow(tmpwin.returnValue,tmpwin.FlowChartObject ? tmpwin.FlowChartObject.otherContentInfo : {});
						}
					},0);
			   }
				
			}
		}
	}
}

lbpm.globals.extendRoleOptWindowSubmit = function(submitType,approveType) {
	if (!lbpm.globals.submitFormEvent()) {
		return;
	}
	if(lbpm.isFreeFlow){
		//清除锁信息
		var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmFreeflowVersionAction.do?method=deleteVersionOnConfirm';
		var data = {"fdProcessId":lbpm.modelId};
		$.ajax({
			type : "POST",
			data : data,
			url : url,
			async : false,
			dataType : "json",
			success : function(json){

			}
		});
	}
	var kmssData = new KMSSData();
	kmssData.AddHashMapArray(lbpm.globals
			.buildFormToHashArray(document.forms['sysWfProcessForm']||document.forms[0]));
	kmssData.SendToUrl(Com_Parameter.ContextPath
			+ 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method='
			+ submitType, function(request) {
		var responseText = request.responseText;
		var operationKey = Com_GetUrlParameter(responseText, "operationKey");
		if(approveType=='right'){
			lbpm.globals.redirectPage(operationKey);
		}else{
			if(parent && parent.$dialog){
				parent.$dialog.hide(operationKey);
			} else{
				top.returnValue = operationKey;
				top.close();
			}
		}
	}, true);
}

/**
 * 封装数据,把form表单里面所有需要提交的数据封装 所有需要提交数据封装 formobj form对象 return string
 */
lbpm.globals.buildFormToHashArray = function(formObj) {
	var array = [], elementsObj, obj;
	if (formObj) {
		elementsObj = formObj.elements;
		if (elementsObj) {
			for (var i = 0; i < elementsObj.length; i++) {
				obj = elementsObj[i];
				if (obj.name != undefined && obj.name != "") {
					var arg = {};
					arg[obj.name] = obj.value;
					array.push(arg);
				}
			}
		}
	}
	return array;
}

lbpm.globals.buildExtendRoleOptButton = function(cfg) {
	if (window.LUI) {
		return $('<a class="com_btn_link" id="' + cfg.optType + 'Button"'
				+ ' style="margin: 0 10px 0 0;" title="' + cfg.optTypeName
				+ '"' + ' onclick="lbpm.globals.openExtendRoleOptWindow(\''
				+ cfg.roleType + '\', \'' + cfg.optType
				+ '\');" href="javascript:void(0);">' + cfg.optTypeName
				+ '</a>');
	} else {
		return $('<input class="btnopt" type=button id="' + cfg.optType
				+ 'Button"' + ' value="' + cfg.optTypeName + '"'
				+ ' onclick="lbpm.globals.openExtendRoleOptWindow(\''
				+ cfg.roleType + '\', \'' + cfg.optType + '\');"/>');
	}
}

var countCommonDownList = [];
/**
 * 定时任务倒计时
 * @param op
 * @param milliseconds
 * @returns
 */
function countNodeReviewDown(op,milliseconds){
		var countDownHtml=lbpm.globals.countDownHtml(milliseconds);
		document.getElementById(op).innerHTML=countDownHtml;
		var countCommonDownHander =setTimeout(function () { countNodeReviewDown(op,milliseconds); },1000);
		countCommonDownList.push(countCommonDownHander);
}

/**
 * 清理定时任务
 * @param list
 * @returns
 */
function learTimeoutList (list) {
    $.each(list,function(n,value) { 
		window.clearTimeout(value);
	});
}
	
/**
 * 初始化div
 * @param button
 * @returns
 */
function buttonCommonReviewPressTimeInit(button,opId){
	//初始化按钮事件
	onNodePressClickEvent==$(button).attr("onclick");
	var pressTimes=lbpm.globals.ajaxPressTime(opId);
	
	$(button).css("position","relative");
	//$(button.element).addClass("kmReviewDrafterPressBtn");
	var $tipElement = $("<div class='pressTimeTip' id='informCommonReview"+opId+"' style='display:none; width:170px;height:20px;border-radius:4px;padding:4px 6px 4px 6px;text-align:center;position:absolute;left:-56px;top:19px;background-color: #D8D8D8; color:#4285f4;'></div>");
	$(button).append($tipElement);
	
	$(button).bind("mouseover",function(){
		if(onNodePressClickEvent==null||onNodePressClickEvent==""){
			onNodePressClickEvent==$(button).attr("onclick");
		}
		var pressTimesTemp=lbpm.globals.ajaxPressTime(opId);//绑定的这个悬浮事件去访问
		if(pressTimesTemp>0){
			countNodeReviewDown('informCommonReview'+opId,pressTimesTemp);
			$tipElement.show();
			$(button).removeAttr('onclick');
		}else{
			$(button).attr('onclick',onNodePressClickEvent);
		}
	});
	$(button).bind("mouseout",function(){
		learTimeoutList(countCommonDownList);
		$tipElement.hide();
	});
	
	if(pressTimes>0){
		curNodeCommonPressCount=(lbpm.globals.remainMilliseconds(pressTimes)/1000);
		onNodeCommonPressClickName=$(button).attr("onclick");
		$(button).removeAttr('onclick');
		restoreNodeCommonClick=button;
		InterNodeCommonPressValObj = window.setInterval(setNodeCommonPressRemainTime, 1000); //启动计时器，1秒执行一次
	}
}

var onNodePressClickEvent;
var onNodeCommonPressClickName;
var InterNodeCommonPressValObj; //timer变量，控制时间
var curNodeCommonPressCount;//当前剩余秒数
var restoreNodeCommonClick=null;//需要恢复的按钮

//timer处理函数，恢复按钮
function setNodeCommonPressRemainTime() {
	if (curNodeCommonPressCount <= 0) {      
	    window.clearInterval(InterNodeCommonPressValObj);//停止计时器
	    $(restoreNodeCommonClick).attr('onclick',onNodeCommonPressClickName);
	}
	else {
		curNodeCommonPressCount--;
	}
}

// 展示进行起草人或特权人角色的操作行
lbpm.globals.controlExtendRoleOptRow = function() {
	var extendRoleOptRow = document.getElementById("extendRoleOptRow");
	var drafterOptButton = document.getElementById("drafterOptButton");
	var authorityOptButton = document.getElementById("authorityOptButton");
	var historyhandlerOpt = document.getElementById("historyhandlerOpt");
	var branchAdminOptButton = document.getElementById("branchAdminOptButton");
	var drafterInfoObj = lbpm.globals.getDrafterInfoObj();
	var authorityInfoObj = lbpm.globals.getAuthorityInfoObj();
	var historyhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj();
	var branchAdminInfoObj = lbpm.globals.getBranchAdminInfoObj();
	
	var initExtendRoleOptRow = function() {
		if (drafterInfoObj == null || drafterInfoObj.length == 0
				|| drafterInfoObj[0].operations == null
				|| drafterInfoObj[0].operations.length == 0 || lbpm.hideDrafterOptButton) {
			// 无操作时不显示 2009-8-19 by fuyx
			lbpm.globals.hiddenObject(drafterOptButton, true);
		} else {
			lbpm.globals.hiddenObject(drafterOptButton, false);
		}
		if (authorityInfoObj == null || authorityInfoObj.length == 0) {
			lbpm.globals.hiddenObject(authorityOptButton, true);
		} else {
			lbpm.globals.hiddenObject(authorityOptButton, false);
		}
		if (branchAdminInfoObj == null || branchAdminInfoObj.length == 0) {
			lbpm.globals.hiddenObject(branchAdminOptButton, true);
		} else {
			lbpm.globals.hiddenObject(branchAdminOptButton, false);
		}
		if (historyhandlerInfoObj == null
				|| historyhandlerInfoObj.length == 0) {
			lbpm.globals.hiddenObject(historyhandlerOpt, true);
		} else {
			var optTypeTmp = [];
			var contains = function(type) {
				for (var k = 0; k < optTypeTmp.length; k++) {
					if (optTypeTmp[k] == type) {
						return true;
					}
				}
				return false;
			};
			for (var i = 0; i < historyhandlerInfoObj.length; i++) {
				for (var j = 0; j < historyhandlerInfoObj[i].operations.length; j++) {
					var opt = historyhandlerInfoObj[i].operations[j];
					if (contains(opt.id + ":" + opt.name)) { // 避免并发分支按钮重复
						continue;
					}
					optTypeTmp.push(opt.id + ":" + opt.name);
					var btn = lbpm.globals.buildExtendRoleOptButton({
						optType : opt.id,
						optTypeName : opt.name,
						roleType : opt.operationHandlerType
					});
					if(opt.id=="history_handler_press"){
						buttonCommonReviewPressTimeInit(btn,lbpm.historyhandlerInfoObj[0].id);
					}
					$(historyhandlerOpt).prepend(btn);
				}
			}
			lbpm.globals.hiddenObject(historyhandlerOpt, false);
		}
	}
	if (window.LUI) {
		LUI.ready(initExtendRoleOptRow);
	}else{
		lbpm.onLoadEvents.delay.push(initExtendRoleOptRow);
	}
}

/**
 * 获取对于身份的操作，没有指定就获取第一个
 */
lbpm.globals.getOperationByRoleType = function(infoObj,roleType,id){
	if(!infoObj || !infoObj[0] || infoObj[0].operations.length == 0){
		return null;
	}
	for (var i = 0; i < infoObj[0].operations.length; i++) {
		var operation = infoObj[0].operations[i];
		if(operation.XMLNODENAME == 'operation' && operation.id == id && operation.operationHandlerType == roleType){
			return operation;
		}
	}
	return infoObj[0].operations[0];
}