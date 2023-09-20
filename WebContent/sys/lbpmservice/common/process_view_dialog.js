var afterConfirm=false;
//审批操作弹框，操作弹框，checkedValue不为空则默认选中相应操作
function process_handerApprove(checkedValue) {
	//若当前操作项为通过类型，则校验所有；非通过，则校验除必填外的校验
	if (document.forms && document.forms.length > 0 && window.$GetFormValidation) {
		var validation = $GetFormValidation(document.forms[0]);
		if (validation) {
			//判断是否含有自定义表单
			var $xform = $(".sysDefineXform");
			if($xform.length>0){
				var operationType  = lbpm.currentOperationType;
				if(checkedValue){
					operationType  = checkedValue.split(":")[0];
				}
				var isPassType = operationType && lbpm.operations[operationType] && lbpm.operations[operationType].isPassType;
				if(!isPassType){
					//移除表单必填校验
					validation.removeElements($xform[0],'required');
					//移除附件必填
					_lbpmChangeAttValidate(true);
				}
				var $AuditPointCfgTr = $("#_lbpmExtAuditPointCfgTr");
				if($AuditPointCfgTr.length>0){
					validation.removeElements($AuditPointCfgTr[0],'required');
					validation.removeElements($AuditPointCfgTr[0],'lbpmext_auditpoint_required');
				}
				$('.lbpmNextRouteInfoRow').each(function(index,ele){
					validation.removeElements(ele,'required');
				});
				var isCanPass = validation.validate();
				if(!isPassType){
					//重置表单必填校验
					validation.resetElementsValidate($xform[0]);
					//重置附件必填
					_lbpmChangeAttValidate(false);
				}
				if($AuditPointCfgTr.length>0){
					validation.resetElementsValidate($AuditPointCfgTr[0]);
				}
				$('.lbpmNextRouteInfoRow').each(function(index,ele){
					validation.resetElementsValidate(ele);
				});
				if(!isCanPass){
					return;
				}
			}
		}
	}
	//若checkedValue有值，则认为是操作按钮平铺模式，隐藏事物切换，选中对应操作，并触发点击事件
	if(checkedValue){
		var $operation = $("select[name='operationItemsSelect']");
		$operation.hide();
		$operation.parent().find("span:eq(0)").text($operation.find("option:selected").text());
		$("input[name='oprGroup'][value='"+checkedValue+"']").click();
		$("#operationMethodsRow").hide();
	}
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		var obj =  document.getElementById("processDev");
		if (obj.style.display == "none") {
			dialog.build( {
				config : {
					width:800,
					height:500,
					lock : true,
					cache : true,
					close: true,
					content : {
						type : "element",
						elem : obj,
						scroll : true,
						buttons : [ {
							name : Data_GetResourceString("button.submit"),
							value : true,
							focus : true,
							fn : function(value, dialog) {
								if(!afterConfirm){
									Com_Parameter.event["confirm"].push(function(){
										if(dialog.element){
											dialog.hide(value);
										}
										return true;
									});
									afterConfirm = true;
								}
								$("#process_review_button").trigger($.Event("click"));
							}
						}, {
							name : Data_GetResourceString("button.cancel"),
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						} ]
					},
					title : Data_GetResourceString("sys-lbpmservice:lbpmview.approve.model.dialog.view")
				},

				callback : function(value, dialog) {
					
				},
				actor : {
					type : "default"
				},
				trigger : {
					type : "default"
				}
			}).show();
		}
	});
}
lbpm.onLoadEvents.delay.push(function(){
LUI.ready(function() {
	//控制审批按钮是否需要出现
	var descriptionRow = document.getElementById("descriptionRow");
	var approveModel = '';
	if(Lbpm_SettingInfo && Lbpm_SettingInfo['approveModel']){
		approveModel = Lbpm_SettingInfo['approveModel'];
	}
	if(approveModel=='dialog'){
		if(descriptionRow){
			$("#approveButton").css("display",descriptionRow.style.display);
		}
		var historyhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj();
		if(historyhandlerInfoObj && historyhandlerInfoObj.length > 0) {
			$("#historyhandlerOptButton").show();
		}
	}else if(approveModel=='tiled'){
		var historyhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj();
		if(historyhandlerInfoObj && historyhandlerInfoObj.length > 0) {
			$("#historyhandlerOptButton").show();
		}
		var myToolbar = LUI("toolbar");
		if(!myToolbar){
			return;
		}
		if(!lbpm.operationButtons){
			lbpm.operationButtons = [];
		}
		var processorInfoObj = lbpm.globals.getProcessorInfoObj();
		//若有当前事物，且大于1，则构建切换事物按钮（切换当前审批事物）
		if(processorInfoObj && processorInfoObj.length>0){
			if(processorInfoObj.length>1){
				seajs.use('lui/toolbar',function(toolbar){
					var button = new toolbar.Button(
						 {
							 click : "lbpmChangeReviewTask();",
							 text : Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems"),
							 order : 1
						 });
					button.startup();
					myToolbar.addButton(button);
				});
			}
		}else{
			//若无当前事物，且有多个起草人事物，则构建切换事物按钮（切换起草人事物）
			var drafterInfoObj = lbpm.globals.getDrafterInfoObj();
   			if(drafterInfoObj && drafterInfoObj.length > 0){
   				if(drafterInfoObj.length > 1){
   					seajs.use('lui/toolbar',function(toolbar){
   						var button = new toolbar.Button(
   							 {
   								 click : "lbpmChangeDrafterTask();",
   								 text : Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems"),
   								 order : 1
   							 });
   						button.startup();
   						myToolbar.addButton(button);
   					});
   				}
   				if(!lbpm.drafterTaskId){
					lbpm.drafterTaskId = drafterInfoObj[0].id;
				}
			}else{
				//若无起草人事物，且有多个已处理人事物，则构建切换事物按钮（切换已处理人事物）
				var historyhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj();
				if(historyhandlerInfoObj && historyhandlerInfoObj.length > 0){
					if(historyhandlerInfoObj.length > 1){
						seajs.use('lui/toolbar',function(toolbar){
	   						var button = new toolbar.Button(
	   							 {
	   								 click : "lbpmChangeHistoryhandlerTask();",
	   								 text : Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems"),
	   								 order : 1
	   							 });
	   						button.startup();
	   						myToolbar.addButton(button);
	   					});
					}
   					if(!lbpm.historyhandlerTaskId){
   						lbpm.historyhandlerTaskId = historyhandlerInfoObj[0].id;
   					}
				}
			}
		}
		//构建各种操作按钮按钮
		lbpmBuiledOprGroup();
	}
});
});
//构建审批事物切换按钮
function lbpmChangeReviewTask(){
	var operationValue = $("select[name='operationItemsSelect']").val();
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		var msg = [];
		msg.push('<table id="operationChangeTaskTb" style="word-break:break-all;border:0px;"><tr>');
		msg.push('<td class="" style="padding-right: 8px;">'+Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems")+'</td>');
		msg.push('<td colspan="3"><select id="myTaskSelect" style="width: 200px;">');
		//option取原本的事物切换的option
		msg.push($("select[name='operationItemsSelect']").html());
		msg.push('</select></tr></table>');
		dialog.build({
			config : {
				width : 400,
				cahce : false,
				title : Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems"),
				content : {
					type : "common",
					html : msg.join(''),
					iconType : '',
					buttons : [ {
						name : Data_GetResourceString("button.ok"),
						value : true,
						focus : true,
						fn : function(value, dialog) {
							//若发生值改变，则赋予事物下拉值为当前选中的事物
							if(operationValue != $("#myTaskSelect").val()){
								$("select[name='operationItemsSelect']").val($("#myTaskSelect").val()).change();//修改选择的身份值
								//重新构建操作按钮
								lbpmBuiledOprGroup();
							}
							dialog.hide(value);
						}
					}, {
						name : Data_GetResourceString("button.cancel"),
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]
				}
			}
		}).on('show', function() {
			//赋予初始值为原本的选中的事物
			$("#myTaskSelect").val($("select[name='operationItemsSelect']").val());
			this.element.find(".lui_dialog_common_content_right").css("max-width","100%");
			this.element.find(".lui_dialog_common_content_right").css("margin-left","0px");
		}).show();
	});
}

//构建起草人事物切换按钮
function lbpmChangeDrafterTask(){
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		var msg = [];
		msg.push('<table id="operationChangeTaskTb" style="word-break:break-all;border:0px;"><tr>');
		msg.push('<td class="" style="padding-right: 8px;">'+Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems")+'</td>');
		msg.push('<td colspan="3"><select id="myTaskSelect" style="width: 200px;">');
		var drafterInfoObj = lbpm.globals.getDrafterInfoObj();
		if(!lbpm.drafterTaskId){
			lbpm.drafterTaskId = drafterInfoObj[0].id;
		}
		for(var i=0;i<drafterInfoObj.length;i++){
			msg.push('<option value="'+drafterInfoObj[i].id+'"');
			if(drafterInfoObj[i].id == lbpm.drafterTaskId){
				msg.push(' selected="selected"');
			}
			msg.push('>'+drafterInfoObj[i].nodeId+'.'+lbpm.nodes[drafterInfoObj[i].nodeId].name+'</option>');
		}
		msg.push('</select></tr></table>');
		dialog.build({
			config : {
				width : 400,
				cahce : false,
				title : Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems"),
				content : {
					type : "common",
					html : msg.join(''),
					iconType : '',
					buttons : [ {
						name : Data_GetResourceString("button.ok"),
						value : true,
						focus : true,
						fn : function(value, dialog) {
							//若发生值改变，保存当前选中的事物到变量中
							if(lbpm.drafterTaskId != $("#myTaskSelect").val()){
								lbpm.drafterTaskId = $("#myTaskSelect").val();
								//重新构建操作按钮
								lbpmBuiledOprGroup();
							}
							dialog.hide(value);
						}
					}, {
						name : Data_GetResourceString("button.cancel"),
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]
				}
			}
		}).on('show', function() {
			this.element.find(".lui_dialog_common_content_right").css("max-width","100%");
			this.element.find(".lui_dialog_common_content_right").css("margin-left","0px");
		}).show();
	});
}

//构建已处理人事物切换按钮
function lbpmChangeHistoryhandlerTask(){
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		var msg = [];
		msg.push('<table id="operationChangeTaskTb" style="word-break:break-all;border:0px;"><tr>');
		msg.push('<td class="" style="padding-right: 8px;">'+Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems")+'</td>');
		msg.push('<td colspan="3"><select id="myTaskSelect" style="width: 200px;">');
		var historyhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj();
		if(!lbpm.historyhandlerTaskId){
			lbpm.historyhandlerTaskId = historyhandlerInfoObj[0].id;
		}
		for(var i=0;i<historyhandlerInfoObj.length;i++){
			msg.push('<option value="'+historyhandlerInfoObj[i].id+'"');
			if(historyhandlerInfoObj[i].id == lbpm.historyhandlerTaskId){
				msg.push(' selected="selected"');
			}
			msg.push('>'+historyhandlerInfoObj[i].nodeId+'.'+lbpm.nodes[historyhandlerInfoObj[i].nodeId].name+'</option>');
		}
		msg.push('</select></tr></table>');
		dialog.build({
			config : {
				width : 400,
				cahce : false,
				title : Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.operationItems"),
				content : {
					type : "common",
					html : msg.join(''),
					iconType : '',
					buttons : [ {
						name : Data_GetResourceString("button.ok"),
						value : true,
						focus : true,
						fn : function(value, dialog) {
							//若发生值改变，保存当前选中的事物到变量中
							if(lbpm.historyhandlerTaskId != $("#myTaskSelect").val()){
								lbpm.historyhandlerTaskId = $("#myTaskSelect").val();
								//重新构建操作按钮
								lbpmBuiledOprGroup();
							}
							dialog.hide(value);
						}
					}, {
						name : Data_GetResourceString("button.cancel"),
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]
				}
			}
		}).on('show', function() {
			this.element.find(".lui_dialog_common_content_right").css("max-width","100%");
			this.element.find(".lui_dialog_common_content_right").css("margin-left","0px");
		}).show();
	});
}

var countDialogDownList = [];
/**
 * 定时任务倒计时
 * @param op
 * @param milliseconds
 * @returns
 */
function countDialogDown(op,milliseconds){
		var countDownHtml=lbpm.globals.countDownHtml(milliseconds);
		document.getElementById(op).innerHTML=countDownHtml;
		
		var countDialogDownHander =setTimeout(function () { countDialogDown(op,milliseconds); },1000);
		countDialogDownList.push(countDialogDownHander);	
		
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
function buttonPressTimeInit(button,opId){
	var pressTimes=lbpm.globals.ajaxPressTime(opId);
	
	$(button.element).css("position","relative");
	var $tipElement = $("<div class='pressTimeTip' id='informDialog"+opId+"' style='display:none; width:170px;height:20px;border-radius:4px;padding:4px 6px 4px 6px;text-align:center;position:absolute;left:-66px;top:38px;background-color: #D8D8D8; color:#4285f4;'></div>");
	$(button.element).append($tipElement);
	
	$(button.element).bind("mouseover",function(){
		var pressTimesTemp=lbpm.globals.ajaxPressTime(opId);//绑定的这个悬浮事件去访问
		if(pressTimesTemp>0){
			button.setDisabled(true);
			countDialogDown('informDialog'+opId,pressTimesTemp);
			$tipElement.show();
		}else{
			button.setDisabled(false);
		}
	});
	$(button.element).bind("mouseout",function(){
		learTimeoutList(countDialogDownList);
		$tipElement.hide();
	});
	
	if(pressTimes>0){
		curDialogPressCount=(lbpm.globals.remainMilliseconds(pressTimes)/1000);
		button.setDisabled(true);
		restoreDialogClick=button;
		InterDialogPressValObj = window.setInterval(setDialogPressRemainTime, 1000); //启动计时器，1秒执行一次
	}
}

var InterDialogPressValObj; //timer变量，控制时间
var curDialogPressCount;//当前剩余秒数
var restoreDialogClick;//需要恢复的按钮

//timer处理函数，恢复按钮
function setDialogPressRemainTime() {
	if (curDialogPressCount <= 0) {      
	    window.clearInterval(InterDialogPressValObj);//停止计时器
	    restoreDialogClick.setDisabled(false);
	}
	else {
		curDialogPressCount--;
	}
}
//获取多语言值
function _getLangLabel(defLabel,langsArr,lang){
	if(langsArr==null){
		return defLabel;
	}
	for(var i=0;i<langsArr.length;i++){
		if(lang==langsArr[i]["lang"]){
			return langsArr[i]["value"]||defLabel;
		}
	}
	return defLabel;
}

//构建各种操作按钮（优先构建处理人操作，其次是起草人操作）
function lbpmBuiledOprGroup(){
	seajs.use('lui/toolbar',function(toolbar){
		var myToolbar = LUI("toolbar");
		var saveFormData = LUI("saveFormData");
		if(saveFormData){
			saveFormData.weight = 60;
		}
		var buttons = lbpm.operationButtons;
		if(buttons){
			for(var i = 0;i<buttons.length;i++){
				myToolbar.removeButton(buttons[i]);
				buttons.splice(i, 1);
				i--;
			}
		}
		var nowProcessorInfoObj = lbpm.nowProcessorInfoObj;
		if(nowProcessorInfoObj && lbpm.constant.DOCSTATUS!="10"){
			var oprNames=nowProcessorInfoObj.operations;
			var _docStatus = lbpm.constant.DOCSTATUS;
			if(_docStatus==null || _docStatus=='10'){
				return;
			}
			for(var i = 0;i<oprNames.length;i++){
				if(_docStatus=='11' && oprNames[i].id == "drafter_refuse_abandon"){
					continue;
				}

				//按钮多语言处理
				var langs = nodeOprLangs[oprNames[i].id];
				var oprName = oprNames[i].name;
				if(typeof langs=="undefined"){
					langs = nodeOprLangs[oprNames[i].id+"-"+oprNames[i].name];
				}
				if(typeof langs!="undefined"){
					oprName = _getLangLabel(oprNames[i].name,langs,_userLang);
				}
				var button = new toolbar.Button(
					 {
						 click : "process_handerApprove('"+oprNames[i].id+":"+oprName+"');",
						 text : oprName,
						 order : 1
					 });
				
				button.startup();
				myToolbar.addButton(button);
				buttons.push(button);
			}
		} else{
			var drafterInfoObj = lbpm.globals.getDrafterInfoObj()
   			if(drafterInfoObj && drafterInfoObj.length > 0
					&& drafterInfoObj[0].operations
					&& drafterInfoObj[0].operations.length > 0){
   				var drafterOpts = null;
   				if(lbpm.drafterTaskId){
   					for(var i = 0;i<lbpm.drafterInfoObj.length;i++){
   						if(lbpm.drafterInfoObj[i].id == lbpm.drafterTaskId){
   							drafterOpts = lbpm.drafterInfoObj[i].operations;
   							break;
   						}
   					}
   				}else{
   					drafterOpts = drafterInfoObj[0].operations;
   				}
   				if(drafterOpts==null){
   					return;
   				}
   				for(var i = 0;i<drafterOpts.length;i++){	
   					
   					var button = new toolbar.Button(
					 {
						 click : "lbpm.globals.openExtendRoleOptWindow('drafter','"+drafterOpts[i].id+"'"+(lbpm.drafterTaskId?",'"+lbpm.drafterTaskId+"'":"")+");",
						 text : drafterOpts[i].name,
						 order : 1,
						 key : drafterOpts[i].id
					 });

   					button.startup();
   					
   					if(drafterOpts[i].id=='drafter_press'){
   						buttonPressTimeInit(button,lbpm.drafterInfoObj[0].id);
   					}	
   					
					myToolbar.addButton(button);
					buttons.push(button);
   				}
   				lbpm.hideDrafterOptButton = true;
   			}else{
   				var historyhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj();
   				var contains = function(type) {
					for (var k = 0; k < optTypeTmp.length; k++) {
						if (optTypeTmp[k] == type) {
							return true;
						}
					}
					return false;
				};
				var historyhandlerOpts = null;
   				if(lbpm.historyhandlerTaskId){
   					for(var i = 0;i<lbpm.historyhandlerInfoObj.length;i++){
   						if(lbpm.historyhandlerInfoObj[i].id == lbpm.historyhandlerTaskId){
   							historyhandlerOpts = lbpm.historyhandlerInfoObj[i].operations;
   							break;
   						}
   					}
   				}else{
                    if(historyhandlerInfoObj && historyhandlerInfoObj.length > 0) {
                        historyhandlerOpts = historyhandlerInfoObj[0].operations;
                    }
   				}
   				if(historyhandlerOpts==null){
   					return;
   				}
   				if(historyhandlerOpts.length>0){
   					$("#historyhandlerOptButton").hide();
   				}
				var optTypeTmp = [];
				for (var i = 0; i < historyhandlerOpts.length; i++) {
					var opt = historyhandlerOpts[i];
					// 避免并发分支按钮重复
					if (contains(opt.id + ":" + opt.name)) { 
						continue;
					}
					optTypeTmp.push(opt.id + ":" + opt.name);
					seajs.use('lui/toolbar',function(toolbar){
						var button = new toolbar.Button(
								{
									 click: "lbpm.globals.openExtendRoleOptWindow('"+opt.operationHandlerType+"', '"+opt.id+"'"+(lbpm.historyhandlerTaskId?",'"+lbpm.historyhandlerTaskId+"'":"")+");",
									 text:opt.name,
									 order: 1
								 }
							 );
						
						//催办加漂浮提示
						if(historyhandlerOpts[i].id=='history_handler_press'){
	   						buttonPressTimeInit(button,lbpm.historyhandlerInfoObj[0].id);
	   					}	
						
						button.startup();
						myToolbar.addButton(button);
						buttons.push(button);
					});
				}
   			}	
		}
	});
}