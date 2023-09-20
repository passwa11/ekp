// operationButtonType为流程提交按扭的Flag值，通过"handler_pass"、驳回"handler_refuse"
lbpm.globals.simpleTagFlowSubmitEvent = function(operationButtonType,contentId) {
	$("input[name=oprGroup]").each( function() {
		var _this = $(this);
		var radioObjVal = _this.val().split(":");
		if (radioObjVal[0] == operationButtonType) {
			_this.attr("checked", "true");
			_this.trigger("click");
			return false;
		}
	});
	
	$("textarea[name=fdUsageContent]").val($("textarea[name='textareaNote_"+contentId+"']").val());
	//驳回类型
	var rejectType=$("textarea[name='textareaNote_"+contentId+"']").attr("rejectType");
	if('handler_pass'==operationButtonType){
		
	}
	else if('handler_refuse'==operationButtonType || 'handler_superRefuse' == operationButtonType){
		//增加延时 如果驳回的节点列表没有加载，就等待加载成功再继续
		if(!$("select[name=jumpToNodeIdSelectObj]")[0]||$("select[name=jumpToNodeIdSelectObj]")[0].options.length==0){
			//防止并发分支没有可驳回节点,导致死循环
			var jumpToNode = $("input[type='hidden'][key='jumpToNodeId']");
			if (!jumpToNode[0]){
				setTimeout(function(){
					lbpm.globals.simpleTagFlowSubmitEvent(operationButtonType,contentId);
					}
				,100);
			}else{
				alert("无可驳回节点");
			}
			return;
		}
		//驳回选择节点
		if(rejectType=='1'){
			$("div[id*='refuseNodesDiv_']").each(function(){
				$(this).hide();
			});
			$("#refuseNodesDiv_"+contentId).show();
			$("#refuseNodesDiv_"+contentId).html("<a href='#' style='text-decoration:underline' onclick='$(this).parent().hide();'>关闭</a> <br/><select id='refuseNodes_"+contentId+"' style='margin-top:5px;width:140px;' onchange='$(\"select[name=jumpToNodeIdSelectObj]\").val($(this).val())'>"+$("select[name=jumpToNodeIdSelectObj]").html()+"</select>&nbsp&nbsp<a href='#' style='text-decoration:underline' tagId='"+contentId+"' onclick='rejectNode_controlSimpleTagWorkflow_ok(this)'>确定</a>");
			var jumpToNodeIdSelectObj = $("#refuseNodes_"+contentId)[0];
			$("#refuseNodes_"+contentId)[0].options[0].selected=true;
			//#47401 新、旧审批操作的驳回到要跟随流程引擎的“驳回时默认驳回到上一节点”变化 by liwenchang
			if (Lbpm_SettingInfo && Lbpm_SettingInfo["isRefuseToPrevNodeDefault"] == "true") {
				jumpToNodeIdSelectObj.selectedIndex = jumpToNodeIdSelectObj.options.length - 1;
			} else {
				jumpToNodeIdSelectObj.selectedIndex = 0;
			}
			return ;
		}
	}
	$("#process_review_button").trigger("click");
};
lbpm.globals.newAuditSimpleTagFlowSubmitEvent = function(src,controlId){
    //检验是否选择了操作项
    var oprGroup = $("[name='newAuditNoteOprGroup_" + controlId +"']");
    var checkFlag = false;
    oprGroup.each(function() {
        if (this.checked || this.type == 'select' || this.type == 'select-one') {
            checkFlag = true;
            return false;
        }
    });
    if((oprGroup.length > 0) && !checkFlag){
        alert(lbpm.constant.VALIDATEOPERATIONTYPEISNULL);
        return false;
    }
	//#45993 修复 新审批操作控件，未选分支的情况下点击提交，关闭弹出提示后，定位到流程处理处，而不是新审批操作控件
	lbpm.globals.isNewAuditNoteSubimit = true;
	var controlId = $(src).attr("controlId") || controlId;
	//新审批控件中上传的签名
	var signaturePicUL = $("#signaturePicUL_" + controlId);
	var tempFileIds = [];
	newAuditNote_getSignatureFileId(signaturePicUL,tempFileIds);
	//流程中上传的签名
	var processSignaturePicUL = $("#signaturePicUL");
	newAuditNote_getSignatureFileId(processSignaturePicUL,tempFileIds,controlId,true);
	fileIds = tempFileIds;
	$("#process_review_button").trigger("click");
}


function newAuditNote_getSignatureFileId(context,fileIds,controlId,isProcess){
	if(context && context.length > 0){
		//获取其它控件上传的签名Id
		var otherControlFileIds = [];
		if (isProcess) {
			var otherControlUl = $("[id*='signaturePicUL_'][id!='signaturePicUL_" + controlId + "']");
			var isExistInOther = false;
			otherControlUl.each(function(index,otherControlUlObj){
				var imgObj = $("li",$(otherControlUlObj));
				for (var i = 0; i < imgObj.length; i++){
					var otherControlFileId = $(imgObj[i]).attr("id");
					if (Com_ArrayGetIndex(otherControlFileIds,otherControlFileId) < 0) {
						otherControlFileIds.push(otherControlFileId);
					}
				}
			});
		} 
		var liObjs = $("li",context);
		liObjs.each(function(index,liObj){
			var fileId = $(liObj).attr("id");
			var isContain = false;
			for (var i = 0; i < fileIds.length; i++){
				var fileObj = fileIds[i];
				if (fileId == fileObj.fdAttId){
					isContain = true;
					break;
				}
			}
			if (!isContain) {
				//流程中的还要进一步判断是其它控件上传的，还是直接在流程中上传的
				if (isProcess) {
					var isInOther = false;
					for (var i = 0; i < otherControlFileIds.length; i++) {
						var otherId = otherControlFileIds[i];
						if (fileId === otherId) {
							isInOther = true;
							break;
						}
					}
					if (!isInOther) {
						fileIds.push({"fdAttId":fileId});
					}
				} else {
					fileIds.push({"fdAttId":fileId});
				}
			}
		});
	}
}

function rejectNode_controlSimpleTagWorkflow_ok(obj){
	$("textarea[name=fdUsageContent]").val($("textarea[name='textareaNote_"+$(obj).attr('tagId')+"']").val());
	$("#process_review_button").trigger("click");
}
lbpm.globals.controlSimpleTagWorkflowComponents = function(isShow,obj) {
	var tagId=$(obj).attr("tagId");
	var auditNoteObj = $(obj).closest("div[name='div_" + tagId + "']");
	//新建的时候不需要出现审批框
	auditNoteObj.attr("isShow",false);
	if(!document.getElementById("process_review_button")){
		$("div[name='div_"+tagId+"']").hide();
		$("div[name='tip_"+tagId+"']").show();
		return;
	}
	if (isShow) {
		var nowProcessorInfoObj = lbpm.nowProcessorInfoObj;		
		var canShow=false;
		if (nowProcessorInfoObj) {
			//按节点
			if($(obj).attr('mould')=='21'){
				var info = $(obj).attr("info");
				if(info && typeof info == 'string'){
					try{
						info = eval('('+info+')');
					}catch(e){
					}
				}
				var wfIds = [];
				if(info && info["wfIds"]){
					wfIds = info["wfIds"].split(";");
				}
				var fdId = lbpm.fdTemplateModelId;
				if(wfIds && wfIds.length > 0 && !fdId){
					//发送请求获取lbpmtemplateModelid
					var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=getTemplateModelIdByModelId&modelId=" + lbpm.modelId;
					$.ajax({
						url: url,
						method: 'GET',
						async: false,
						dataType:'text',//后台返回的是字符串，ajax请求需指定返回格式“text”,否则会报parsererror转换错误
					}).success(function (ret) {
						lbpm.fdTemplateModelId = ret;
						fdId = ret;
					});
				}
				var setNodes=$(obj).attr('wfValue').split(";");
				var hasIn=false;
				for(var i=0;i<setNodes.length;i++){
					if(wfIds && wfIds.length > 0){
						if(fdId == wfIds[i] && nowProcessorInfoObj['nodeId']==setNodes[i]){
							hasIn=true;
							break;
						}
					}else{
						if(nowProcessorInfoObj['nodeId']==setNodes[i]){
							hasIn=true;
							break;
						}
					}
				}
				canShow=hasIn;
			}
			//按人
			else{
				canShow=$(obj).attr('resValue')=='true';
			}
			if(canShow){
				$("textarea[name='textareaNote_" + tagId + "']").val($("textarea[name='fdUsageContent']").val());
				//是否有需要显示的操作按钮
				var hasOp=false;
				if (nowProcessorInfoObj.operations) {
					for ( var i = 0; i < nowProcessorInfoObj.operations.length; i++) {
						var operation = nowProcessorInfoObj.operations[i];
						//通过或签字
						if ("handler_pass" == operation.id||'handler_sign'== operation.id) {
							$("#pass_"+tagId).html(Com_HtmlEscape(operation.name));
							$("#pass_"+tagId).css("display","inline-block");
							hasOp=true;
						} else if ("handler_refuse" == operation.id || "handler_superRefuse" == operation.id) {
							//判断操作设置是否有超级驳回
							var superRefuseName = "";
							var isSuperRefuse = false;
							for(var j = 0; j < nowProcessorInfoObj.operations.length; j++){
								var opr = nowProcessorInfoObj.operations[j];
								if ("handler_superRefuse" == opr.id){
									isSuperRefuse = true;
									superRefuseName = opr.name;
									break;
								}
							}
							//如果设置了超级驳回,优先使用超级驳回
							if(isSuperRefuse){
								if($(obj).attr('rejectType')=='1'){
									$("#refuse_"+tagId).html(superRefuseName);
									//先解除掉原先绑定的驳回点击事件,重新绑定超级驳回事件
									$("#refuse_"+tagId).attr("onclick","lbpm.globals.simpleTagFlowSubmitEvent('handler_superRefuse','" + tagId + "');");
								}
								else{
									$("#refuse_"+tagId).html(Com_HtmlEscape(superRefuseName)+ lbpm.constant.NODETYPE_DRAFTNODE);
								}
							}else{
								if($(obj).attr('rejectType')=='1'){
									$("#refuse_"+tagId).html(operation.name);
								}
								else{
									$("#refuse_"+tagId).html(Com_HtmlEscape(operation.name)+lbpm.constant.NODETYPE_DRAFTNODE);
								}
							}
							$("#refuse_"+tagId).css("display","inline-block");
							hasOp=true;
						}else if ("handler_communicate" == operation.id){
							hasOp = true;
						}else if ("handler_assign" == operation.id || "handler_assignPass" == operation.id){
							hasOp = true;
						}else if ("handler_returnCommunicate" == operation.id){
							hasOp = true;
						}else if ("handler_robTodo" == operation.id){
							hasOp = true;
						}
					}
				}
				//没有任何操作时直接隐藏整个控件
				if(hasOp){
					$("div[name='div_"+tagId+"']").css("display","inline-block");
					auditNoteObj.attr("isShow",true);
				} else {
					$("div[name='div_"+tagId+"']").hide();
					$("div[name='tip_"+tagId+"']").show();
					auditNoteObj.attr("isShow",false);
				}
			}
			else{
				$("div[name='div_"+tagId+"']").hide();
				$("div[name='tip_"+tagId+"']").show();
			}
		}
	} else {
		$("div[name='div_"+tagId+"']").hide();
		$("div[name='tip_"+tagId+"']").show();
	}
};

lbpm.globals.simpleTagFlowSetCommonUsages=function(obj){
	if (obj.selectedIndex > 0) {
		var tagId = $(obj).attr("tagId");
		var auditUsageContent = document.getElementsByName("textareaNote_" + tagId)[0];
		var fdUsageContent = $("textarea[name='fdUsageContent']").val();
		if (lbpm.workitem.constant.COMMONUSAGES_ISAPPEND == "true") {
			fdUsageContent += obj.options[obj.selectedIndex].value;
			auditUsageContent.value += obj.options[obj.selectedIndex].value;
			$("textarea[name ='textareaNote_" + tagId + "']").val($(auditUsageContent).val());
			$("textarea[name='fdUsageContent']").val(fdUsageContent);
		} else {
			$("textarea[name ='textareaNote_" + tagId + "']").val($(obj).val());
			$("textarea[name='fdUsageContent']").val($(obj).val());
		}
	}
	$("select[name='commonUsages_"+tagId+"']").val('');
}
// 初始化简易流程的参数
lbpm.onLoadEvents.once.push( function() {
	$("textarea[name *='textareaNote_']").each(function(){
			if (lbpm.nowProcessorInfoObj) {
				lbpm.globals.controlSimpleTagWorkflowComponents(true,this);
			} else {
				lbpm.globals.controlSimpleTagWorkflowComponents(false,this);
			}
	});
	//点阵笔签批初始化
	$(".onlyHandwriteDiv").each(function(){
		if (lbpm.nowProcessorInfoObj) {
			var tagId=$(this).attr("tagId");
			//新建的时候不需要出现审批框
			if(document.getElementById("process_review_button")){
				var nowProcessorInfoObj = lbpm.nowProcessorInfoObj;		
				var canShow=false;
				if (nowProcessorInfoObj) {
					//按节点
					if($(this).attr('mould')=='21'){
						var info = $(this).attr("info");
						if(info && typeof info == 'string'){
							try{
								info = eval('('+info+')');
							}catch(e){
							}
						}
						var wfIds = [];
						if(info && info["wfIds"]){
							wfIds = info["wfIds"].split(";");
						}
						var fdId = lbpm.fdTemplateModelId;
						if(wfIds && wfIds.length > 0 && !fdId){
							//发送请求获取lbpmtemplateModelid
							var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=getTemplateModelIdByModelId&modelId=" + lbpm.modelId;
							$.ajax({
								url: url,
								method: 'GET',
								async: false
							}).success(function (ret) {
								lbpm.fdTemplateModelId = ret;
								fdId = ret;
							});
						}
						var setNodes=$(this).attr('wfValue').split(";");
						var hasIn=false;
						for(var i=0;i<setNodes.length;i++){
							if(wfIds && wfIds.length > 0){
								if(fdId == wfIds[i] && nowProcessorInfoObj['nodeId']==setNodes[i]){
									hasIn=true;
									break;
								}
							}else{
								if(nowProcessorInfoObj['nodeId']==setNodes[i]){
									hasIn=true;
									break;
								}
							}
						}
						canShow=hasIn;
					}
					//按人
					else{
						canShow=$(this).attr('resValue')=='true';
					}
					if(canShow){
						$(this).show();
						var self = this;
						setTimeout(function() {
							if(window.handInit) {
								window.handInit();
							}
						},300);
						$(".process_review_content[name='fdUsageContent']").attr("disabled","false").val("");
						$(this).attr("onmouseover","latticeMouseover(this)");
						$(this).attr("onmouseout","latticeMouseout(this)");
						$(this).find(".latticeWriteDiv").attr("lattice","true");
					}
				}
			}
		}
	});
});

lbpm.onLoadEvents.delay.push(function () {
	if(lbpm && lbpm.events && lbpm.events.addListener){
		lbpm.events.addListener(lbpm.constant.EVENT_HANDLERTYPECHANGE, function(param){
			if(param!='' && param!=lbpm.constant.PROCESSORROLETYPE){
				$(".onlyHandwriteDiv").hide();
				$(".process_review_content[name='fdUsageContent']").attr("disabled",false);
				$("textarea[name *='textareaNote_']").each(function(){
					var tagId=$(this).attr("tagId");
					$("div[name='div_"+tagId+"']").hide();
					$(this).val("");
				});
			}else{
				$(".onlyHandwriteDiv").each(function(){
					if($(this).find(".latticeWriteDiv[lattice='true']").length>0){
						$(".process_review_content[name='fdUsageContent']").attr("disabled","false").val("");
						$(this).show();
					}
				});
				$("textarea[name *='textareaNote_']").each(function(){
					var tagId=$(this).attr("tagId");
					$("div[name='div_"+tagId+"'][isShow='true']").show();
				});
			}
		});		
	}
	var kmssData = new KMSSData();
	kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesIsAppend");
	var result = kmssData.GetHashMapArray();
	if(result && result[0]){
		var isAppend = result[0].isAppend ? result[0].isAppend : null;
		if (isAppend != null && isAppend != "") {
			lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = isAppend;
		}
	}
});