/**
 * 创建操作按钮
 */
function createOpr(){
	//获取所有的新审批操作控件
	var operMethodsGroup = $("textarea[fd_type='newAuditNote']")
	if (operMethodsGroup.length == 0){
		return;
	}
	for (var j = 0; j < operMethodsGroup.length; j++){
		var vtype = $(operMethodsGroup[j]).attr('view-type');
		var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
		var operationType = $(operMethodsGroup[j]).attr("operationtype");
		var id = $(operMethodsGroup[j]).attr("tagid");
		var nodeOprLangs={};
		if(_isLangSuportEnabled){
			var currNodeInfo = lbpm.globals.getCurrentNodeObj();
			if(currNodeInfo.operations && currNodeInfo.operations.refId != null){
				var data = new KMSSData();
				data.AddBeanData("getOperationsByDefinitionService&fdId="+currNodeInfo.operations.refId);
				data = data.GetHashMapArray();
				if (data.length > 0){
					var operations = data[0].operation;
					operations = $.parseJSON(operations);
					for(var index=0; index<operations.length; index++){
						if(typeof operations[index].langs!="undefined"){
							nodeOprLangs[operations[index].type+"-"+operations[index].name]=$.parseJSON(operations[index].langs);
						}
					}
				}
			}else{
				if(typeof currNodeInfo.langs!="undefined"){
					if(currNodeInfo.langs!=""){
						var langsJson = $.parseJSON(currNodeInfo.langs);
						nodeOprLangs = langsJson.nodeOpr||{};
					}
				}
			}
		}
		if (!currNodeInfo){
			currNodeInfo = lbpm.globals.getCurrentNodeObj();
		}
		var isSignNode = false;
		if (currNodeInfo["XMLNODENAME"] == "signNode"){
			generateSignNodeNextInfo(id);
			isSignNode = true;
		}
		var view = vtype == 'select' ? (function(oprValue, oprName, oprItem, operInfo){
			var langs = nodeOprLangs[oprValue];
			if(typeof langs=="undefined"){
				langs = nodeOprLangs[oprValue+"-"+oprName];
			}
			if(typeof langs!="undefined"){
				oprName = _getLangLabel(oprName,langs,_userLang);
			}
			var optionHtml = "<option value='" + oprValue + ":" + oprName + "' ";
			if(oprValue == lbpm.defaultOperationType){
				optionHtml += "selected='true' ";
				optionHtml += ">" + oprName + "</option>";
				return optionHtml;
			}
		}) : (function(oprValue, oprName, oprItem, operInfo){
			var langs = nodeOprLangs[oprValue];
			if(typeof langs=="undefined"){
				langs = nodeOprLangs[oprValue+"-"+oprName];
			}
			if(typeof langs!="undefined"){
				oprName = _getLangLabel(oprName,langs,_userLang);
			}
			//判断新审批操作控件设置的操作项是否包含当前操作项或者当前的操作项是取消沟通或者回复沟通
			if (operationType.indexOf(oprValue) > -1 || oprValue == 'handler_superRefuse'
				|| (operationType.indexOf("handler_communicate") && (oprValue == 'handler_cancelCommunicate' || oprValue == 'handler_returnCommunicate'))
				|| (operationType.indexOf("handler_assign") && (oprValue == 'handler_assignCancel' || oprValue == 'handler_assignPass' || oprValue == "handler_assignRefuse"))
				|| oprValue == 'handler_robTodo'){
				//节点操作设置了超级驳回优先读取超级驳回
				var isSuperRefuse = false;
				if(oprValue == "handler_refuse"){
					for (var index = 0; index < operInfo.operations.length; index++){
						var operation = operInfo.operations[index];
						if (operation["id"] == "handler_superRefuse"){
							isSuperRefuse = true;
							oprName = Com_HtmlEscape(operation["name"]);
						}
					}
				}
				//如果操作项只配置超级驳回,而新审批配置驳回
				if (oprValue == "handler_superRefuse"){
					var oprStr = '';
					for (var j =0; j < operInfo.operations.length; j++){
						var opera = operInfo.operations[j];
						oprStr += opera["id"];
					}
					if (oprStr.indexOf("handler_refuse") < 0 && operationType.indexOf("handler_refuse") > -1){
						isSuperRefuse = true;
					}else{
						return " ";
					}
				}
				if (isSuperRefuse){
					oprValue = "handler_superRefuse";
					langs = nodeOprLangs[oprValue];
					if(typeof langs=="undefined"){
						langs = nodeOprLangs[oprValue+"-"+oprName];
					}
					if(typeof langs!="undefined"){
						oprName = _getLangLabel(oprName,langs,_userLang);
					}
					var radioButtonHTML = "<nobr><label style='padding-right:5px;'>"
						+"<input type='radio' alertText='' key='operationType' name='newAuditNoteOprGroup_" + id + "' value='" + oprValue + ":" + oprName + "' ";

				}else{
					//操作单选按钮dom元素
					var radioButtonHTML = "<nobr><label style='padding-right:5px;'>"
						+"<input type='radio' alertText='' key='operationType' name='newAuditNoteOprGroup_" + id + "' value='" + oprValue + ":" + oprName + "' ";
				}
				//默认选中通过选项
				if(oprValue == "handler_pass"){
					radioButtonHTML += "checked='true' ";
					//加载即将流向
					handlerPass(oprName,id);
				}
				radioButtonHTML += " onclick='newAuditNoteClickOperation(this,\"" +  id + "\");'>" + oprName + "</label></nobr>";
				radioButtonHTML += " ";
				return radioButtonHTML;
			}
			return "";
		});
		var notify = vtype == 'select' ? (function(){
			$("#operationMethodsGroup_" + id).change(function(){
				lbpm.globals.clickOperation(this);
			});
			$("#operationMethodsGroup_" + id + " option").each(function(){
				var item=this;
				var opt = lbpm.operations[item.value.split(':')[0]];
				if (opt && opt.isPassType){
					$(item).attr('selected', true);
					return false;
				}
			});
			lbpm.globals.clickOperation($("#operationMethodsGroup")[0].value);
		}) : (function(){
			//获取操作按钮
			var radio = $("#operationMethodsGroup_" + id + " input[name*='newAuditNoteOprGroup']");
			if (radio.length == 1){
				//radio.attr('disabled', true);
				radio.attr('checked', true);
				var opinfo = radio.val();
				var opvalue = opinfo.split(":")[0];
				if(opvalue=="drafter_submit"
					||opvalue=="handler_pass"){
					var operationAuditRow = $("#operationAuditRow_" + id);
					lbpm.globals.hiddenObject(operationAuditRow, true);
				}
			}else if(radio.length > 1){
				var radioArray = new Array();
				//是否有默认选中项
				var isChecked = false;
				//回复沟通默认选中
				for(var index = 0; index < radio.length; index++){
					var ope = radio[index].defaultValue;
					var oper = ope.split(":")[0];
					if(oper == "handler_returnCommunicate"){
						radio[index].checked = true;
                        isChecked = true;
					}
					radioArray.push(oper);
				}
				//有多个通过的操作项配置，默认排序选中第一个通过选项
				if(radioArray.indexOf("handler_pass") > -1){
					for(var i = 0; i < radioArray.length; i++){
						if(radioArray[i] == "handler_pass"){
							radio[i].checked = true;
                            isChecked = true;
							break;
						}
					}
				}
				var operationAuditRow = $("#operationAuditRow_" + id);
				lbpm.globals.hiddenObject(operationAuditRow, false);
			}
			if (radio.length == 0){
				return;
			}
			// if (!isChecked) {
            //     radio[0].checked = true;
            // }
			radio.each(function(){
				//触发默认操作项的点击事件
				var thisRadio = this;
				if (this.checked){
					newAuditNoteClickOperation(thisRadio,id);
					return false;
				}
				var opt = lbpm.operations[thisRadio.value.split(':')[0]];
				if (opt && opt.isPassType){
					$(thisRadio).attr('checked', true);
					newAuditNoteClickOperation(thisRadio,id);
					return false;
				}
			});
		});
		var html = '';
		//遍历当前节点的操作项
		$.each(operatorInfo.operations, function(i, item){
			html += view(item.id, Com_HtmlEscape(item.name), item, operatorInfo);
		});
		//判断是否为签字节点,是的话显示签字操作项
		if (isSignNode){
			var langs = nodeOprLangs["handler_sign"];
			var hadlerSignName = "签字";
			$.each(operatorInfo.operations,function(i,item){
				if (item.id == "handler_sign"){
					if(typeof langs=="undefined"){
						langs = nodeOprLangs[item.id+"-"+Com_HtmlEscape(item.name)];
					}
					if(typeof langs!="undefined"){
						hadlerSignName = _getLangLabel(Com_HtmlEscape(item.name),langs,_userLang);
					}
				}
			})
			html += "<nobr><label style='padding-right:5px;'>" +
				"<input type='radio' alertText='' key='operationType' name='newAuditNoteOprGroup_"+ id +"' value='handler_sign" + ":" + hadlerSignName + "' "
				+ "checked='true' "
				+" >" + hadlerSignName + "</label></nobr>";
		}
		$("#operationMethodsGroup_" + id).html(html);
		notify();
		//签字节点显示即将流向
		if (isSignNode){
			lbpm.globals.hiddenObject($("#operationAuditRow_" + id), false);
			isSignNode = false;
		}
        //新审批控件 当前用户有多个事务时构建事务下拉框选项
        var operationItemsRow = document.getElementById("operationItemsRow_" + id);
        var processorInfoObj = lbpm.globals.getProcessorInfoObj();
        if(operationItemsRow != undefined && processorInfoObj != undefined && !lbpm.changNewAuditnoteTransaction){
            if(processorInfoObj.length > 1){
                loadWorkflowInfo(id);
                //控制事务下拉框联动
                var $select = $("#operationItemsRow_").find("select[name*='operationItemsSelect_" + id + "']");
                $select.bind("change",function (){
                    if(lbpm.approveType != "right"){
                        $("#operationItemsRow > td > select[name='operationItemsSelect'] > option[value='" + this.value +  "']").prop("selected",true);
                    }else{
                        $("#operationItemsRow > div[class='lui-lbpm-detailNode'] > select > option[value='" + this.value +  "']").prop("selected",true);
                    }
                })
            }else{
                lbpm.globals.hiddenObject(operationItemsRow, true)
            }
        }
	}
	//有多个新审批控件时，最后一个若是隐藏的，会导致当前显示的信息被隐藏
	if(operMethodsGroup.length>1){
		setTimeout(function(){
			$("div[id^='newAuditNoteWrap']:visible input:radio[key='operationType']:checked").click();
		},100);
	}

}
//构建事务下拉框方法
function loadWorkflowInfo(id){
	var processorInfoObj = lbpm.globals.getProcessorInfoObj();
	if(processorInfoObj != null){
		var operationItemsRow = $("#operationItemsRow_" + id)[0];
		if(operationItemsRow != null){
			var operationItemsSelect = $("[name='operationItemsSelect_" + id + "']");
			if(operationItemsSelect != null){
                operationItemsSelect.empty();
				var selectedIndex = 0;
				for(var i=0; i < processorInfoObj.length; i++){
					var processInfo = processorInfoObj[i];
					var parentHandlerName=(processInfo.parentHandlerName?processInfo.parentHandlerName+"：":"");
					var langNodeName = WorkFlow_getLangLabel(lbpm.nodes[processInfo.nodeId].name, lbpm.nodes[processInfo.nodeId]["langs"],"nodeName");
					var processInfoShowText = (_lbpmIsHideAllNodeIdentifier())? (parentHandlerName + langNodeName) : (processInfo.nodeId +"."+ parentHandlerName + langNodeName);
					if (lbpm.nodes[processInfo.nodeId].nodeDescType == lbpm.constant.NODETYPE_JOIN) {
						if (processInfo.parameter) {
							var processInfoParamJson = JSON.parse(processInfo.parameter);
							if (processInfoParamJson.conBranchSourceId) {
								var conBranchSourceId = processInfoParamJson.conBranchSourceId;
								if (conBranchSourceId.indexOf("N") == 0){
									processInfoShowText += "(" + conBranchSourceId + "."
										+ WorkFlow_getLangLabel(lbpm.nodes[conBranchSourceId].name, lbpm.nodes[conBranchSourceId]["langs"],"nodeName")
										+ ")";
								} else if (conBranchSourceId.indexOf("L") == 0) {
									processInfoShowText += "(" + conBranchSourceId + "."
										+ WorkFlow_getLangLabel(lbpm.lines[conBranchSourceId].name, lbpm.lines[conBranchSourceId]["langs"],"name")
										+ ")";
								}
							}
						}
					}
					if(processorInfoObj[i].expectedName){
						processInfoShowText += "("+processorInfoObj[i].expectedName+")";
					}
					var groupNodeId = lbpm.nodes[processorInfoObj[i].nodeId].groupNodeId;
					if (groupNodeId != null) {
						processInfoShowText = processInfoShowText + "【" + groupNodeId + "."
							+ WorkFlow_getLangLabel(lbpm.nodes[groupNodeId].name, lbpm.nodes[groupNodeId]["langs"],"nodeName")
							+ "】";
					}
					option = document.createElement("option");
					option.appendChild(document.createTextNode(processInfoShowText));
					option.value=i;
					operationItemsSelect.append(option);
					if(lbpm.defaultSelectedTaskId){
						if(lbpm.defaultSelectedTaskId == processorInfoObj[i].id){
							selectedIndex = i;

						}
					}else{
						if (processorInfoObj[i] == lbpm.nowProcessorInfoObj) {
							selectedIndex = i;
						}
					}
				}
				operationItemsSelect.selectedIndex = selectedIndex;
				if(lbpm.defaultSelectedTaskId){
					$(operationItemsSelect).change();
					$(operationItemsSelect).parent().find("span:eq(0)").text($(operationItemsSelect).find("option:selected").text());
				}
			}
			if(processorInfoObj.length == 1) lbpm.globals.hiddenObject(operationItemsRow, true); else lbpm.globals.hiddenObject(operationItemsRow, false);
		}
	}
};

/**
 * 功能：当前用户有多个事务时，切换事务函数
 * @param selectObj 当一个用户有多个工作项，处理事务一栏的多个事务的下拉框对象
 * @param inInit 标识是否是初始加载，区别于用户的手工切换事务
 */
function auditOperationItemsChanged(selectObj,isInit){
	if(selectObj){
		lbpm.nowProcessorInfoObj=lbpm.processorInfoObj[selectObj.selectedIndex];
	}
	//var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
	//获取当前节点ID(指当前人所处的节点，当不是处理人，lbpm.nowNodeId不存在)
	var operatorInfo = lbpm.nowProcessorInfoObj;
	lbpm.nowNodeId=operatorInfo.nodeId;
	lbpm.globals.handlerOperationClearOperationsRow();
	lbpm.changNewAuditnoteTransaction = true;
	//lbpm.globals.createOperationMethodGroups();
	$("[name='operationItemsSelect']").val(selectObj.value).trigger("change");
	lbpm.changNewAuditnoteTransaction = false;

	setTimeout(function(){
		if(lbpm.globals.checkModifyAuthorization)
			lbpm.globals.checkModifyAuthorization(lbpm.nowNodeId);
		if(lbpm.globals.initialWorkitemParams){
			lbpm.globals.initialWorkitemParams();
		}
		//初始化意见
		var saveedArr=WorkFlow_LoadXMLData($("input[name='sysWfBusinessForm.fdCurNodeSavedXML']")[0].value);
		if(saveedArr.tasks && saveedArr.tasks.length>0){
			var tasksArr=$.grep(saveedArr.tasks,function(n,i){
				return n.id==operatorInfo.id;
			});
			if(tasksArr.length>0){
				var usageContent=lbpm.operations.getfdUsageContent();
				if(usageContent) {
					usageContent.value=lbpm.globals.htmlUnEscape(tasksArr[0].data);
					//加载@处理人信息
					lbpm.globals.initNoticeHandlersDataAction();
				}
			}
		}
		if(!isInit){
			lbpm.events.fireListener(lbpm.constant.EVENT_CHANGEWORKITEM,null);
			lbpm.events.fireListener("updateShowLimitTimeOperation",null);
		}
	},100);
};

//生成签字节点流向信息
function generateSignNodeNextInfo(id){
	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	if(operationAuditTDContent){
		operationAuditTDTitle.innerHTML = XForm_NewAudit_Note.handlerOperationTypepass;
		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
		if(nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
			var calcBranchLabel = '&nbsp;<label id="calc" style="color:#dd772c;cursor:pointer;" onclick="newAuditNoteCalcBranch(\'' + id +'\');">'
				+ XForm_NewAudit_Note.calcBranch;
			operationAuditTDTitle.innerHTML=operationAuditTDTitle.innerHTML + calcBranchLabel;
		}
		lbpm.globals.hiddenObject(operationAuditRow, false);
		var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
		if (operatorInfo != null) {
			var html = newAuditNoteGenerateNextNodeInfo(null,id);
			if (window.dojo) {
				lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationAuditTDContent);
			} else {
				operationAuditTDContent.innerHTML = html;
			}
		}
	}
}

$(function(){

	//避免打印页面不存在lbpm对象报错
	if (typeof lbpm === "undefined"){
		return ;
	}
	//#54318 修复 新版审批操作控件，表单中无法下拉选择修改后续节点处理人 by 李文昌
	if (typeof newAuditNoteIsInit !== "undefined" && newAuditNoteIsInit){
		return;
	}
	//获取流程审批的常用意见下拉列表选项
	var option = $("select[name='commonUsages']").find("option");
	//获取新审批操作控件
	var operMethodsGroup = $("textarea[fd_type='newAuditNote']");
	for (var i = 0;i < operMethodsGroup.length; i++){
		var id = $(operMethodsGroup[i]).attr("tagid");
		var name = "newAuditNoteCommonUsages_" + id;
		initialCommonUsages(name);
		//将流程审批中的默认审批意见复制到新审批操作控件中
		$(operMethodsGroup[i]).val($("textarea[name='fdUsageContent']").val());
		$('#operationAuditTDContent_'+ id).delegate('.divselect .cite', 'click', function(event) {
			var ul = $(this).closest('.divselect').find('ul');
			if(ul.css("display") == "none" && event.target.tagName != 'A'){
				ul.slideDown("fast");
			}else{
				ul.slideUp("fast");
			}
			event.stopPropagation();
		});
		$('#operationAuditTDContent_'+ id).delegate('.divselect .cite a', 'click', function(event){
			var label = $(this);
			var selectedText = label.text();
			label.closest('.divselect').find('ul li a').each(function(){
				var self = $(this);
				if (self.text() == selectedText){
					self.click();
					return false;
				}
			});
			event.stopPropagation();
		});
		$('#operationAuditTDContent_'+ id).delegate(".divselect ul li a", 'click', function(event){
			var self = $(this);
			var txt = self.text();
			self.closest('.divselect').find('.cite a').html(txt);
			self.closest('.divselect').find('ul').hide();
			//联动审批流程的下拉列表
			$("#operationsTDContent .divselect .cite a, #nextNodeTD .divselect .cite a").html(txt);
			event.stopPropagation();
		});
		$('#operationAuditTDContent_'+ id).click(function(event){
			var divselect = $(this).find('.divselect'), rtn = false;
			if (divselect.length > 0){
				divselect.each(function(){
					if ($.contains(this, event.target)){
						rtn = true;
						return false;
					}
				});
				if (rtn) return;
			}
			$(".divselect ul").hide();
		});
		//联动审批流程处理人下拉列表
		$("#operationsTDContent").on("newAuditNoteChooseHandler",function(event){
			//联动审批流程的下拉列表
			var arr = $("#operationsTDContent .divselect ul li a");
			if (arr.length > 0){
				var newAuditNoteHandlerType = $('#operationAuditTDContent_'+ id).find(".divselect");
				for (var i = 0; i < newAuditNoteHandlerType.length ; i++){
					if($(newAuditNoteHandlerType[i]).css("display") == "inline"){
						var text = $(newAuditNoteHandlerType[i]).find(".cite a").text();
						var index = i;
						var handlerType = $('#operationsTDContent').find(".divselect");
						$(handlerType[index]).find(".cite a").html(text);
					}else{
						$(newAuditNoteHandlerType[i]).find(".cite a").html($(arr[0]).text());
					}
				}
			}
			event.stopPropagation();
		});
	}
	newAuditNoteIsInit = true;
	$(document).click(function(){
		$(".divselect ul").hide();
	});
})

//钉钉UI初始化常用审批意见
function inditialNewCommonUsages(name,usageContents) {
	var $commonUsage = $("[name='"+ name +"']");
	var $commonUsageUl = $("[name='"+ name +"'] ul");
	var $commonUsageBox = $commonUsage.closest(".lui-audit-common-usage");
	var $commonUsageIcon = $commonUsageBox.find(".lui-audit-downIcon");
	$commonUsageUl.html("");
	if(usageContents != null){
		for ( var i = 0; i < usageContents.length; i++) {
			var usageContent = usageContents[i];
			while (usageContent.indexOf("nbsp;") != -1) {
				usageContent = usageContent.replace("&nbsp;", " ");
			}
			var optTextLength = 20;
			var optText = usageContent.length > optTextLength ? usageContent
				.substr(0, optTextLength) + '...'
				: usageContent;
			$commonUsageUl.append("<li data-value='"+usageContent+"'>"+optText+"</li>")
		}
	}
	//===============常用意见事件==============================
	$commonUsageBox.mouseover(function(){
		lbpm.opinionFlag=true;
		lbpm.opinionTimer= setTimeout(function(){
			if(lbpm.opinionFlag){
				$commonUsage.addClass('hoverStyle')
				$commonUsageIcon.addClass('lui-lbpm-foldIcon');
			}
		},1000);
	});
	$commonUsageBox.mouseout(function(){
		if(!$commonUsage.hasClass('hoverStyle')){
			$commonUsageIcon.removeClass('lui-lbpm-foldIcon');
		}else{
			var event = window.event;
			var toEle = event.toElement || event.relatedTarget;
			if(!(toEle && $(toEle).closest('.lui-audit-common-usage').length>0)){
				$commonUsage.removeClass('hoverStyle');
				$commonUsageIcon.removeClass('lui-lbpm-foldIcon');
			}
		}
		lbpm.opinionFlag=false;
		clearTimeout(lbpm.opinionTimer);
	});
	$commonUsage.mouseenter(function(){
		$(this).addClass('hoverStyle');
		$commonUsageIcon.addClass('lui-lbpm-foldIcon');
	});
	$commonUsage.mouseleave(function(){
		var event = window.event;
		var toEle = event.toElement || event.relatedTarget;
		if(!(toEle && $(toEle).closest('.lui-audit-common-usage').length>0)){
			$commonUsage.removeClass('hoverStyle');
			$commonUsageIcon.removeClass('lui-lbpm-foldIcon');
		}
	});
	//常用意见
	$commonUsageUl.on('click','li',function(){
		$commonUsage.removeClass('hoverStyle');
		$commonUsageIcon.removeClass('lui-lbpm-foldIcon');
		var controlId = $commonUsage.closest("xformflag").attr("flagId");
		var fdUsageContent = $("[name='textareaNote_" + controlId + "']");
		var oldUsage = fdUsageContent.val() || "";
		fdUsageContent.val(oldUsage + $(this).attr("data-value"));
	});
	//===============常用意见事件===============================================
}

window.inputCheckboxChange =function(str){

	var name = $(str).attr("name");
	var input = $("input[name='"+name+"'][value='"+$(str).val()+"']");
	var checked ;
	for(var i=0;i<input.length;i++){
		if(input[i] === str){
			checked = str.checked;
		}
	}

	for(var i=0;i<input.length;i++){
		if(checked == true){
			input[i].checked = true;
		}else if(checked == false){
			input[i].checked = false;
		}

	}
}

function initialCommonUsages(name){
	var kmssData = new KMSSData();
	kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesInfo");
	var result = kmssData.GetHashMapArray();
	if(result && result[0]) {
		var names = result[0].usagesInfo ? decodeURIComponent(result[0].usagesInfo) : null;
		var usageContents;
		if (names != null && names != ""){
			usageContents = names.split("\n");
		}
		if (Com_Parameter.dingXForm == "true") {
			inditialNewCommonUsages(name,usageContents);
		} else {
			if (lbpm.globals.initialCommonUsageObj){
				lbpm.globals.initialCommonUsageObj(name,usageContents);
				lbpm.globals.initialCommonUsageObj("commonSimpleUsages",usageContents);
			}
		}
	}
	lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = "true";
	var kmssData2 = new KMSSData();
	kmssData2.AddBeanData("lbpmUsageTarget&type=getUsagesIsAppend");
	var result = kmssData2.GetHashMapArray();
	if(result && result[0]){
		var isAppend = result[0].isAppend ? result[0].isAppend : null;
		if (isAppend != null && isAppend != "") {
			lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = isAppend;
		}
	}
}

//常用意见下拉列表值改变事件
function newAuditNoteSetUsages(commonUsagesObj,id){
	if (commonUsagesObj.selectedIndex > 0){
		var fdUsageContent = document.getElementsByName("textareaNote_" + id)[0];
		var fdSimpleUsageContent = document.getElementsByName("fdSimpleUsageContent")[0];
		lbpm.globals.clearDefaultUsageContent(lbpm.currentOperationType, fdUsageContent,fdSimpleUsageContent);
		if(lbpm.workitem.constant.COMMONUSAGES_ISAPPEND=="true"){
			fdUsageContent.value += commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
			if (fdSimpleUsageContent != null){
				fdSimpleUsageContent.value += commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
			}
		}else{
			fdUsageContent.value = commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
			if (fdSimpleUsageContent != null){
				fdSimpleUsageContent.value = commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
			}
		}
		$("textarea[name='fdUsageContent']").val($(fdUsageContent).val());
	}
	$("select[name='newAuditNoteCommonUsages_"+id+"']").val('');
}

function newAuditNoteClickOperation(obj,id){
	lbpm.globals.isNewAuditNote = true;
	//获取点击的操作类型
	var valueAndName = (typeof(obj)=="string"?obj:obj.value).split(":");
	if (!valueAndName) return;
	var operationName = valueAndName[0];
	var lastTimeOprValue = lbpm.currentOperationType;
	//控制选择非沟通时“限制子级沟通范围“不出现
	var operationsRow_Scope = document.getElementById("operationsRow_Scope_"+id);
	if(operationName != "handler_communicate"){
		operationsRow_Scope.style.display = "none";
		lbpm.globals.handlerOperationClearOperationsRow_Scope && lbpm.globals.handlerOperationClearOperationsRow_Scope();
	}
	if (lastTimeOprValue){
		newAuditNoteclearDefaultUsageContent(lastTimeOprValue,id);
	}
	if(operationName == "handler_returnCommunicate"){
		var operationAuditRow = $("#operationAuditRow_" + id);
		lbpm.globals.hiddenObject(operationAuditRow, true);
	}
	lbpm.globals.clickOperation(obj);

	//通过
	if (operationName == "handler_pass"){
		handlerPass(valueAndName[1],id);
	}
	//驳回
	if (operationName == "handler_refuse"){
		handlerRefuse(valueAndName[1],id);
	}
	//转办
	if (operationName == "handler_commission"){
		handlerCommission(valueAndName[1],id);
	}
	//沟通
	if (operationName == "handler_communicate"){
		handlerCommunicate(valueAndName[1],id);
	}
	//补签
	if (operationName == "handler_additionSign"){
		handlerAdditionSign(valueAndName[1],id);
	}
	//超级驳回
	if (operationName == "handler_superRefuse"){
		handlerSuperRefuse(valueAndName[1],id);
	}
	//取消沟通
	if (operationName == "handler_cancelCommunicate"){
		handlerCancelCommunicate(valueAndName[1],id);
	}
	//加签
	if (operationName == "handler_assign"){
		handlerAssign(valueAndName[1],id);
	}
	//收回加签
	if (operationName == "handler_assignCancel"){
		handlerAssignCancel(valueAndName[1],id);
	}
	//通过加签
	if (operationName == "handler_assignPass"){
		handlerAssignPass(valueAndName[1],id);
	}
	//退回加签
	if (operationName == "handler_assignRefuse"){
		handlerAssignRefuse(valueAndName[1],id);
	}
	//联动流程中操作按钮
	$("input[type='radio'][name='oprGroup'][value='" + obj.value + "']").prop("checked",true);
	lbpm.globals.isNewAuditNote = false;
}

//通过
function handlerPass(operationName,id){
	//点击通过操作时用户没有 输入审批内容时设置默认审批内容
	setNewAuditNoteDefaultUsageContent('handler_pass',id);
	//即将流向
	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	//隐藏即将流向
	if(lbpm && lbpm.canHideNextNodeTr && Lbpm_SettingInfo && Lbpm_SettingInfo.isHideOperationsRow == "true"){
		lbpm.globals.hiddenObject(operationAuditRow, true);
		if(operationAuditTDContent){
			operationAuditTDTitle.innerHTML = "";
			operationAuditTDContent.innerHTML = "";
		}
	}else{
		if(operationAuditTDContent){
			operationAuditTDTitle.innerHTML = XForm_NewAudit_Note.handlerOperationTypepass;
			//获取下一节点信息
			var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
			//条件分支,在即将流向后面添加(计算分支)四个字
			if(nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
				var calcBranchLabel = '&nbsp;<label id="calc" style="color:#dd772c;cursor:pointer;" onclick="newAuditNoteCalcBranch(\'' + id +'\');">'
					+ XForm_NewAudit_Note.calcBranch;
				operationAuditTDTitle.innerHTML=operationAuditTDTitle.innerHTML + calcBranchLabel;
			}
			lbpm.globals.hiddenObject(operationAuditRow, false);
			var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
			if (operatorInfo != null) {
				//生成即将流向节点html
				var html = newAuditNoteGenerateNextNodeInfo(null,id);
				if (window.dojo) {
					lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationAuditTDContent);
				} else {
					operationAuditTDContent.innerHTML = html;
					syncNotifyType(id);
				}
			}
		}
	}

}

//计算条件分支流向
newAuditNoteCalcBranch = function(id){
	var operationsTDContent = ($("#operationAuditTDContent_" + id)[0]!=null)?$("#operationAuditTDContent_" + id)[0]:$("#nextNodeTD")[0];
	operationsTDContent.innerHTML = newAuditNoteGenerateNextNodeInfo(true);
};

function _getLangLabel(defLabel,langsArr,lang){
	if (langsArr == null){
		return defLabel;
	}
	for (var i = 0; i < langsArr.length; i++){
		if (lang == langsArr[i]["lang"]){
			return langsArr[i]["value"] || defLabel;

		}
	}
	return defLabel;
}
//生成即将流向节点的HTML信息
function newAuditNoteGenerateNextNodeInfo(fromCalcBranch,id){
	//判断即将流向的处理人是否还是同一节点：
	var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
	/*
	 * currNodeNextHandlersId:当前节点处理人的下一处理人(串行)
	 * currNodeNextHandlersName:当前节点处理人的下一处理人名称(串行)
	 * toRefuseThisNodeId:驳回时如果选择重新回到本节点时，驳回时节点的的ID（如N1,N2）
	 * toRefuseThisHandlerIds:驳回时如果选择重新回到本节点时，驳回时未处理人的ID集
	 * toRefuseThisHandlerNames:驳回时如果选择重新回到本节点时，驳回时未处理人名称集
	 */
	var operatorInfo=lbpm.globals.getOperationParameterJson(
		"currNodeNextHandlersId"
		+":currNodeNextHandlersName"
		+":toRefuseThisNodeId"
		+":toRefuseThisHandlerIds"
		+":toRefuseThisHandlerNames"
		+":futureNodeId");

	var html = '';
	// 是则显示同一节点下一个处理人并不允许编辑。
	// 不是则显示下一个节点的所有处理人并根据权限显示是否编辑
	if(operatorInfo.currNodeNextHandlersId && currentNodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL){
		var langNodeName = WorkFlow_getLangLabel(currentNodeObj.name,currentNodeObj["langs"],"nodeName");
		html = '<label id="nextNodeName"><b>' + currentNodeObj.id + "." + langNodeName + '</b></label>';
		html += '<input type="hidden" id="handlerIds" name="handlerIds" value="' +operatorInfo.currNodeNextHandlersId+ '">';
		html += '<input type="hidden" id="handlerNames" name="handlerNames" readonly class="inputSgl" onChange="lbpm.globals.setHandlerInfoes();" value="' + Com_HtmlEscape(operatorInfo.currNodeNextHandlersName) + '">';
		html += '<label id="handlerShowNames" class=handlerNamesLabel nodeId="' + currentNodeObj.id + '">(' + Com_HtmlEscape(operatorInfo.currNodeNextHandlersName.replace(/;/g, '; ')) + ')</label>';

		/* html +=lbpm.globals.getNotifyType4Node(currentNodeObj); */
	}else if(operatorInfo.toRefuseThisNodeId){
		html=lbpm.globals.generateRefuseThisNodeIdInfo(
			operatorInfo.toRefuseThisNodeId,
			operatorInfo.toRefuseThisHandlerIds,
			operatorInfo.toRefuseThisHandlerNames,
			operatorInfo.currNodeNextHandlersId,
			operatorInfo.currNodeNextHandlersName);
	}else{
		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
		var routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(lbpm.globals.getCurrentNodeObj(),nextNodeObj, true);
		var noCalcResult = false;
		// 即将流向过滤并行分支节点不走的分支
		if((nextNodeObj.nodeDescType == "splitNodeDesc" && nextNodeObj.splitType != "custom") || nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
			var filterRouteLine = new Array();
			lbpm.globals.getThroughNodes(function(throughtNodes){
				var throughtIds = lbpm.globals.getIdsByNodes(throughtNodes)+",";
				for(var i=0;i<routeLines.length;i++){
					var lineLinkNode = routeLines[i].startNodeId+","+routeLines[i].endNodeId+",";
					if(throughtIds.indexOf(lineLinkNode)>-1){
						filterRouteLine.push(routeLines[i]);
					}
				}
			},null,null,false,lbpm.nowNodeId);
			routeLines = filterRouteLine;
			if(nextNodeObj.nodeDescType=="autoBranchNodeDesc" && filterRouteLine.length != 1){
				noCalcResult = true;
				routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(lbpm.globals.getCurrentNodeObj(),nextNodeObj, false);
			}
		}
		html = newAuditNoteGetNextRouteInfo(routeLines,noCalcResult,fromCalcBranch,id);

		// 针对即将流向的节点以及当前节点是即席子流程内的子节点时
		if ((lbpm.nodes[lbpm.nowNodeId].groupNodeId != null && lbpm.nodes[lbpm.nowNodeId].groupNodeType == "adHocSubFlowNode") && nextNodeObj.nodeDescType=="groupEndNodeDesc") {
			lbpm.nowAdHocSubFlowNodeId = lbpm.nodes[lbpm.nowNodeId].groupNodeId;
			lbpm.adHocRouteId = lbpm.nodes[lbpm.nowNodeId].routeId;
			html = getNextAdHocSubFlowRouteInfoNew();
		} else if (nextNodeObj.nodeDescType == "adHocSubFlowNodeDesc") {
			lbpm.nowAdHocSubFlowNodeId = nextNodeObj.id;
			lbpm.adHocRouteId = null;
			html = getNextAdHocSubFlowRouteInfoNew();
		}
	}
	return html;
}

//取得手工决策节点下的所有节点的信息(routeLines连接集合)
function newAuditNoteGetNextRouteInfo(routeLines,noCalcResult,fromCalcBranch,id){
	var html = "";
	var onlyOneSelect=false;//只有一个选择框
	if(routeLines.length==1) onlyOneSelect=true;
	var futureNodeId = lbpm.globals.getOperationParameterJson("futureNodeId");
	var routeLinesLength=routeLines.length;
	$.each(routeLines, function(i, lineObj) {
		var nodeObj=lineObj.endNode;

		var lineNodeName = WorkFlow_getLangLabel(lineObj.name,lineObj["langs"]);
		var lineName = lineNodeName == null?"":lineNodeName + " ";
		html += "<div class='lbpmNextRouteInfoRow'><label style='line-height:26px;' id='newAuditNoteNextNodeName[" + i + "]_" + id + "'>";
		var inputType = "";
        var currentNodeObj = lbpm.globals.getCurrentNodeObj();
		//如果连线的开始节点为人工分支类节点则显示单选框
		if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,lineObj.startNode)){
			//如果下个节点是人工决策，并且当前节点的流转方式是会审/会签，则需要进入判断环节
			//若不是最后一个处理人，则即将流向显示，但是不可以进行编辑
			//若是最后一个处理人，则以下逻辑正常执行
			var edit = "";
			if(currentNodeObj.processType == "2" && !isLastHandler){
				edit = "disabled";
			}
			html += "<label class='lui-lbpm-radio' style='line-height:26px;' id='nextNodeName[" + i + "]'><input " + edit + " " + (onlyOneSelect==true?"style='display:none' checked=true ":((nodeObj.id == futureNodeId || lineObj.id == lineObj.startNode.defaultBranch) ? "checked=true " :""))+"type='radio' manualBranchNodeId='"+lineObj.startNode.id+"' name='newAuditNotefutureNode_" + id  + "' subject='即将流向' key='futureNodeId' index='" + i + "' value='" + nodeObj.id
				+ "' onclick='newAuditNoteShowFutureNodeSelectedLink(this,\"" + id +"\");_hidOtherAddressOpt(" + i + ");'>" ;
			inputType = "radio";
		}
		//如果连线的开始节点为并行分支则显示复选框
		if(lineObj.startNode.nodeDescType=='splitNodeDesc'){
			if(lineObj.startNode.splitType&&lineObj.startNode.splitType=="custom"){
				//获取默认分支和是否可以选择
				var info = getSplitNodeInfo(lineObj.startNode.id);
				var defaultStartBranchIds,canSelectDefaultBranch,isDefault = false;
				if(info){
					defaultStartBranchIds = info.defaultStartBranchIds;
					if(defaultStartBranchIds && defaultStartBranchIds.indexOf(nodeObj.id) != -1){
						isDefault = true;
					}
					canSelectDefaultBranch = info.canSelectDefaultBranch;
				}
				html += "<label class='lui-lbpm-checkbox' style='line-height:26px;' id='nextNodeName[" + i + "]'><input "+((isDefault && canSelectDefaultBranch == 'false') ? 'disabled':" subject='即将流向'")+" "+(onlyOneSelect==true?"style='display:none' checked=true ":((nodeObj.id == futureNodeId || lineObj.id == lineObj.startNode.defaultBranch || isDefault) ? "checked=true " :""))+"type='checkbox' manualBranchNodeId='"+lineObj.startNode.id+"' name='newAuditNotefutureNode_" + id  + "' key='futureNodeId' index='" + i + "' value='" + nodeObj.id
					+ "' onclick='newAuditNoteFutureNodeClick(this);'>" ;
				inputType = "checkbox";
			}
		}
		var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeObj.id + ".");
		var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
		if(inputType == "radio"){
			html += "<b class='radio-label'>";
		}else if(inputType == "checkbox"){
			html += "<b class='checkbox-label'>";
		}else{
			html += "<b>";
		}
		langNodeName=langNodeName.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		html += lineName + identifier + langNodeName + "</b></label>";
		if(nodeObj.nodeDescType=="dynamicSubFlowNodeDesc" && nodeObj.splitType == "custom"){
			html += "&nbsp;&nbsp;<a class='com_btn_link' onclick='lbpm.globals.getNextDynamicSubFlowRouteInfo(\""+nodeObj.id+"\",this);'>"+lbpm.constant.pleaseSelect+(nodeObj.dynamicGroupShowName?nodeObj.dynamicGroupShowName:nodeObj.name)+"</a>";
		}
		if(!lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,nodeObj)){
			var handlerIds, handlerNames, isFormulaType = (nodeObj.handlerSelectType == lbpm.constant.ADDRESS_SELECT_FORMULA);
			handlerIds = nodeObj.handlerIds==null?"":nodeObj.handlerIds;
			handlerNames = nodeObj.handlerNames==null?"":
				(isFormulaType?lbpm.workitem.constant.COMMONHANDLERISFORMULA:nodeObj.handlerNames);
			var hiddenIdObj = "<input type='hidden' name='handlerIds[" + i + "]_"+ id + "' value='" + handlerIds + "' isFormula='" + isFormulaType.toString() +"' />";
			html += hiddenIdObj;
			var hiddenNameObj = "<input type='hidden' name='handlerNames[" + i + "]_" + id + "' value='" + Com_HtmlEscape(handlerNames) + "' />";

			var dataNextNodeHandler;
			var nextNodeHandlerNames4View="";
			//handlerSelectType 处理人选择类型,1.组织架构 2.公式选择器 3.矩阵组织
			if(nodeObj.handlerSelectType){
				if(nodeObj.handlerSelectType=="formula"){
					dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
				}else if(nodeObj.handlerSelectType=="matrix"){
					dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
				}else if(nodeObj.handlerSelectType=="rule"){
					dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id,handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
				}else{
					dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
				}
				for(var j=0;j<dataNextNodeHandler.length;j++){
					if(nextNodeHandlerNames4View==""){
						nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
					}else{
						nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
					}
				}
			}
			if(nextNodeHandlerNames4View == "" && nodeObj.handlerIds != null) {
				nextNodeHandlerNames4View = lbpm.constant.COMMONNODEHANDLERORGNULL;
			}
			if(nextNodeHandlerNames4View != "")
				html += "(";
			html += hiddenNameObj;
			html += "<label id='handlerShowNames[" + i + "]_" + id + "' class='handlerNamesLabel'";
			html += " nodeId='" + nodeObj.id + "'>" + (nextNodeHandlerNames4View.replace(/;/g, '; ')) + "</label>";
			if(nextNodeHandlerNames4View != "")
				html += ")";
			html += "</label>"; //+"</br>"
			if(lbpm.globals.checkModifyNextNodeAuthorization(nodeObj.id) && !lbpm.address.is_pda()){
				if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SPLIT,lineObj.startNode) || lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_AUTOBRANCH,lineObj.startNode)){
					html += newAuditNoteGetModifyHandlerHTML(nodeObj,i,false,"lbpm.globals.afterChangeHandlerInfoes",null,"lbpm.globals.setHandlerFormulaDialog","handlerIds["+i+"]_" + id,"handlerNames["+i+"]_" + id,id);
				}
				else{
					html += newAuditNoteGetModifyHandlerHTML(nodeObj,i,(onlyOneSelect==true?false:(nodeObj.id == futureNodeId?false:true)),"newAudtiNoteafterChangeFurtureHandlerInfoes",null,null,"handlerIds["+i+"]_" + id,"handlerNames["+i+"]_" + id,id);
				}
			}
		}

		if(noCalcResult==true && fromCalcBranch == true){
			html += "&nbsp;&nbsp;" + lbpm.constant.NOCALCBRANCHCRESULT;
		}

		var rightHtml="";

		var notifyTypeHtml=GetAuditNoteNotifyType4Node(nodeObj,id);

		$(notifyTypeHtml).find('.lui-lbpm-checkbox');
		var notifyTypeLength=0;//有多少子项，例如待办，邮件，短信
		if($(notifyTypeHtml)){
			for(var z=0;z<$(notifyTypeHtml).length;z++){
				var $notifyTypeHtml = $($(notifyTypeHtml)[z]);

				var lbpmCheckClass=$($notifyTypeHtml).attr('class');
				if(lbpmCheckClass=="lui-lbpm-checkbox"){
					notifyTypeLength++;
				}
			}
		}

		//路由线路大于1，先隐藏
		if(routeLinesLength>1){

			//并行分支，按要求都显示
			if(lineObj.startNode.nodeDescType=='splitNodeDesc'){
				//并行分支，按要求都显示
				if(lineObj.startNode.nodeDescType=='splitNodeDesc'||(lineObj.startNode.nodeDescType=="manualBranchNodeDesc"&&lineObj.startNode.defaultBranch&&lineObj.startNode.defaultBranch!="")){
					//通知项大于显示
					if(notifyTypeLength>1){
						html +="<span class='labelNotifyType' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
					}else{
						html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
					}
				}else{
					html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
				}
			}else{
				html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
			}


		}else{
			//通知项大于显示
			if(notifyTypeLength>1){
				html +="<span class='labelNotifyType' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
			}else{
				html +="<span class='labelNotifyType' style='display: none' index='"+i+"'>"+ rightHtml+notifyTypeHtml+"</span>";
			}
		}

		html += "</div>";
	});

	return html;
}
function _lbpmIsRemoveNodeIdentifier(){
	var isRemove = false;
	if (Lbpm_SettingInfo){
		if ((lbpm.settingInfo.isHideNodeIdentifier === "false" && Lbpm_SettingInfo.hideNodeIdentifierType === "false")&&
			Lbpm_SettingInfo.isRemoveNodeIdentifier === "true"){
			isRemove = true;
		}else if (Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType  === "isRemoveNodeIdentifier"){
			isRemove = true;
		}
	}
	return isRemove;
}
function _lbpmIsHideAllNodeIdentifier(){
	var isHideAllNodeIdentifier = false;
	if (Lbpm_SettingInfo &&
		Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType  === "isHideAllNodeIdentifier"){
		isHideAllNodeIdentifier = true;
	}
	return isHideAllNodeIdentifier;
}
function newAuditNoteShowFutureNodeSelectedLink(futureNodeObj,id){
	var index = futureNodeObj.getAttribute("index");
	$('#operationAuditTDContent_'+ id + ', #nextNodeTD').find('.divselect').each(function() {
		var self = $(this);
		if (self.attr("index") == index) {
			self.show();
		} else {
			self.hide();
		}
	});
	$("#operationAuditTDContent_"+ id + ", #nextNodeTD").find('.labelNotifyType').each(function() {
		var self1 = $(this);
		var lbpmCheckboxLength=self1.find('.lui-lbpm-checkbox').length;
		if (self1.attr("index") == index&&lbpmCheckboxLength>1) {
			self1.show();
		} else {
			self1.hide();
		}
	});
	var futureNodeLinkObjs = futureNodeObj.parentNode.parentNode.parentNode.getElementsByTagName("a");
	for(var i = 0; i < futureNodeLinkObjs.length; i++){
		var futureNodeLinkObj = futureNodeLinkObjs[i];
		if(futureNodeLinkObj.getAttribute("index") != null) {
			if(futureNodeLinkObj.getAttribute("index") == futureNodeObj.getAttribute("index")){
				futureNodeLinkObj.parentNode.style.display = '';
			} else {
				futureNodeLinkObj.parentNode.style.display = 'none';
			}
		}
	}
	//联动流程中的单选框
	varNodeId = $(futureNodeObj).val();
	var obj = $("input[name='futureNode'][value='" + varNodeId + "']").prop("checked",true)[0];
	lbpm.globals.showFutureNodeSelectedLink(obj);
	lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE,null);
}

function newAuditNoteFutureNodeClick(dom){
	$("input[name='futureNode'][value='" + dom.value + "']").trigger("click");
}

//设置可以修改节点处理人HTML
function newAuditNoteGetModifyHandlerHTML(nodeObj,hrefIndex,defaultHide,afterChangeFunc,dialogAddsFunc,formulaDialogFunc,handlerIdObj,handlerNameObj,id){
	if(hrefIndex==null) var hasIndex=false; else var hasIndex=true;
	if(dialogAddsFunc==null) dialogAddsFunc="lbpm.globals.dialog_Address";
	if(formulaDialogFunc==null) formulaDialogFunc="lbpm.globals.setFutureHandlerFormulaDialog";
	var html="";
	var handlerIdentity = (function() {
		if (nodeObj.optHandlerCalType == null || nodeObj.optHandlerCalType == '2') {
			var rolesSelectObj = $("select[name='rolesSelectObj']");
			if (rolesSelectObj.length > 0 && rolesSelectObj[0].selectedIndex > -1) {
				return rolesSelectObj.val();
			}
			return $("input[name='sysWfBusinessForm.fdIdentityId']").val();
		}
		var rolesIdsArray = $("input[name='sysWfBusinessForm.fdHandlerRoleInfoIds']").val().split(";");
		return rolesIdsArray[0];
	})();
	var optHandlerIds = nodeObj.optHandlerIds == null?"":nodeObj.optHandlerIds;
	var nodeHandlerIds = nodeObj.handlerIds == null?"":nodeObj.handlerIds;
	var optHandlerSelectType = nodeObj.optHandlerSelectType == null?"org":nodeObj.optHandlerSelectType;
	var handlerSelectType = nodeObj.handlerSelectType == null?"org":nodeObj.handlerSelectType;
	//备选公式化
	var defaultOptionBean = "lbpmOptHandlerTreeService&optHandlerIds=" + encodeURIComponent(optHandlerIds)
		+ "&currentIds=" + ((handlerSelectType=="formula") ? "" : encodeURIComponent(nodeHandlerIds))
		+ "&handlerIdentity=" + handlerIdentity
		+ "&optHandlerSelectType=" + optHandlerSelectType
		+ "&fdModelName=" + lbpm.modelName
		+ "&fdModelId=" + lbpm.modelId;

	var searchBean = defaultOptionBean + "&keyword=!{keyword}";
	var modelName = lbpm.modelName;
	var hrefObj = "<a href=\"javascript:void(0);\" index=\"" + (hasIndex?hrefIndex:0) + "\"";
	if(nodeObj.useOptHandlerOnly == "true"){
		if(handlerSelectType=="formula"){
			hrefObj += " onclick=\"{Dialog_AddressList(true, null,null, ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+ id +"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
		}
		else{
			hrefObj += " onclick=\"{Dialog_AddressList(true, '"+handlerIdObj+"','"+handlerNameObj+"', ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+ id +"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
		}
		hrefObj += " class='com_btn_link'>" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "</a>";
		html += '　　<span id="_addressSpanIndex_'+ (hasIndex?hrefIndex:0)+'" style=\''+(defaultHide?"display:none":"")+';\'>' + hrefObj + '</span>';
	}else{
		var hrefObj_formula = hrefObj;
		var optHrefObj = hrefObj;
		var optHrefObjTemp = optHrefObj;

		if(handlerSelectType=="formula"){
			var selectType = (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj)) ? 'ORG_TYPE_ALL | ORG_TYPE_ROLE' : 'ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE';
			hrefObj += " onclick=\"{"+dialogAddsFunc+"(true, null,null, ';', "+selectType+", function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+ id +"')}, null, null, null, null, null, null, '" + nodeObj.id + "','"+defaultOptionBean+"');}\"";
			optHrefObj += " onclick=\"{Dialog_AddressList(true, null,null, ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+ id +"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
			hrefObj_formula += " onclick=\"{"+formulaDialogFunc+"('"+handlerIdObj+"','"+handlerNameObj+"', '"+modelName+"', '"+nodeObj.id+"')}\"";
		}
		else{
			var selectType = (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj)) ? 'ORG_TYPE_ALL | ORG_TYPE_ROLE' : 'ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE';
			hrefObj += " onclick=\"{"+dialogAddsFunc+"(true, '"+handlerIdObj+"','"+handlerNameObj+"', ';', "+selectType+", function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+ id +"')}, null, null, null, null, null, null, '" + nodeObj.id + "','"+defaultOptionBean+"');}\"";
			optHrefObj += " onclick=\"{Dialog_AddressList(true, '"+handlerIdObj+"','"+handlerNameObj+"', ';', '" + defaultOptionBean + "',function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+ id +"')}, '"+searchBean+"', null, null, lbpm.workitem.constant.COMMONSELECTALTERNATIVE);}\"";
			hrefObj_formula += " onclick=\"{"+formulaDialogFunc+"(null, null, '"+modelName+"', '"+nodeObj.id+"')}\"";
		}
		hrefObj += " title='" + lbpm.workitem.constant.COMMONSELECTADDRESS + "'>" + lbpm.workitem.constant.COMMONSELECTADDRESS + "</a>";
		hrefObj_formula +=  " title='" + lbpm.workitem.constant.COMMONSELECTFORMLIST + "'>" + lbpm.workitem.constant.COMMONSELECTFORMLIST + "</a>";
		optHrefObj +=  " title='" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "'>" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "</a>";
		var handlerChooses = [hrefObj];
		var isShowOptHrefObj = false;
		//如果选择的是组织架构或者公式定义器，需要判断是否设置了备选列表；
		//如果选择的是本部门或者本机构，默认显示
		if ((nodeObj.optHandlerIds && $.trim(nodeObj.optHandlerIds) != "") ){
			handlerChooses.push(optHrefObj);
			isShowOptHrefObj = true;
		}


		//如果选择了本部门和本机构，重构optHrefObj
		var content =  newresetContent(nodeObj, afterChangeFunc, handlerIdObj, handlerNameObj,id);
		if(content){
			optHrefObj = optHrefObjTemp + content;
			optHrefObj +=  " title='" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "'>" + lbpm.workitem.constant.COMMONSELECTALTERNATIVE + "</a>";

			handlerChooses.push(optHrefObj);
		}

		if (window.LUI) {
			html = newAuditNoteGetModifyHandlerHTML_LUI(defaultHide, lbpm.workitem.constant.COMMONSELECTADDRESS, handlerChooses, hrefIndex);
		} else {
			//html += '　　<span id="_addressSpanIndex_'+ (hasIndex?hrefIndex:0)+'" style=\''+(defaultHide?"display:none":"")+';\'>' + hrefObj + '&nbsp;&nbsp;' + hrefObj_formula + '</span>';
			html += '　　<span id="_addressSpanIndex_'+ (hasIndex?hrefIndex:0)+'" style=\''+(defaultHide?"display:none":"")+';\'>' + hrefObj;
			if (isShowOptHrefObj){
				html +=  '&nbsp;&nbsp;' + optHrefObj;
			}
			html += '</span>';
		}
	}
	return html;
}

//重构备选列表标签的onclick事件内容
function newresetContent(nodeObj, afterChangeFunc, handlerIdObj, handlerNameObj,id){
	//如果选择了本部门和本机构，重构hrefObj
	var optHandlerSelectType = nodeObj.optHandlerSelectType == null?"org":nodeObj.optHandlerSelectType;
	if(optHandlerSelectType == 'dept' || optHandlerSelectType == 'mechanism' || optHandlerSelectType == "otherOrgDept"){
		//如果选择的是本部门或者本机构，弹窗地址本
		var deptLimit;
		if(optHandlerSelectType == 'dept'){
			deptLimit = 'myDept';
		}else if(optHandlerSelectType == "otherOrgDept"){
			deptLimit = 'otherOrgDept-' + nodeObj.optHandlerIds;
		}else{
			deptLimit = 'myOrg';
		}
		var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		var reg = /;/g;
		var ids = currentOrgIdsObj ? currentOrgIdsObj.value.split(reg) : [];

		var exceptValue="";
		for(var i=0; i<ids.length; i++){
			if(i == ids.length-1){
				exceptValue += "'" + ids[i] + "'";
			}else{
				exceptValue += "'" + ids[i] + "',";
			}
		}
		var selectType = ORG_TYPE_POSTORPERSON;
		if(nodeObj.nodeDescType=="shareReviewNodeDesc"){
			// 微审批节点处理人选择控制
			return " onclick=\"{Dialog_Address(false,'"+handlerIdObj+"','"+handlerNameObj+"', ';', "+selectType+",function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+id+"')}, null, null, true, null, null, ["+exceptValue+"], '"+deptLimit+"');}\"";
		} else {
			return " onclick=\"{Dialog_Address(true,'"+handlerIdObj+"','"+handlerNameObj+"', ';', "+selectType+",function(rtv){"+afterChangeFunc+"(rtv,'"+lbpm.constant.ADDRESS_SELECT_ORG+"','"+nodeObj.id+"','"+id+"')}, null, null, true, null, null, ["+exceptValue+"], '"+deptLimit+"');}\"";
		}
	}
	return null;
}


function newAuditNoteGetModifyHandlerHTML_LUI(defaultHide, displayText, actions, hrefIndex){

	var html = "<span style='"+(defaultHide?"display:none":"")+";width:150px;margin:0 0 0 10px;' index='"+hrefIndex+"' class='divselect'><span class='cite' style='width:100px;margin-top:0px;' title='" + displayText + "'><a>"+displayText+"</a></span><ul style='width:136px'>";
	for (var i = 0; i < actions.length; i ++) {
		var act = actions[i];
		html += "<li>" + act + "</li>";
	}
	html += "</ul></span>";
	return html;
}

//人工选择节点修改处理人后设置即将流向处理人
function newAudtiNoteafterChangeFurtureHandlerInfoes(rtv,handlerSelectType,nodeId,id){
	var handlerIdsObj ;
	var handlerNamesObj ;
	var handlerShowNames;
	if(rtv){
		var rtvArray = rtv.GetHashMapArray();
		if(rtvArray){
			var futureNodeObj=$("input[name='newAuditNotefutureNode_" + id + "']:checked");
			var futureIndex=null;
			if(futureNodeObj.length>0){
				futureIndex=futureNodeObj[0].getAttribute("index");
			}else{
				futureIndex="0";
			}
			handlerIdsObj = document.getElementsByName("handlerIds[" + futureIndex + "]_"+ id)[0];
			handlerNamesObj = document.getElementsByName("handlerNames[" + futureIndex + "]_" + id)[0];
			handlerShowNames = document.getElementById("handlerShowNames[" + futureIndex + "]_" + id);
			var idValue = "";
			var nameValue = "";
			for(var i=0;i<rtvArray.length;i++){
				idValue += ";"+rtvArray[i]["id"];
				nameValue += ";"+rtvArray[i]["name"];
			}
			handlerIdsObj.value = idValue.substring(1);
			handlerNamesObj.value =  nameValue.substring(1);
			newAuditNoteSetFurtureHandlerInfoes(rtv,handlerSelectType,id);
		}
	}
};

//人工决策节点设置即将流向处理人
function newAuditNoteSetFurtureHandlerInfoes(rtv,handlerSelectType,id){
	var isNull = (rtv == null);
	var handlerIdsObj;
	var handlerNamesObj;
	var handlerShowNames;
	var nextNodeId;
	var futureNodeObj=$("input[name='newAuditNotefutureNode_" + id + "']:checked");
	var futureIndex=null;
	if(futureNodeObj.length>0){
		nextNodeId = futureNodeObj[0].value;
		futureIndex=futureNodeObj[0].getAttribute("index");
	}else{
		var currentNodeObj=lbpm.globals.getCurrentNodeObj();
		var nextNodeObj=lbpm.globals.getNextNodeObj(currentNodeObj.id);
		nextNodeId=nextNodeObj.id;
		futureIndex="0";
	}
	handlerIdsObj = document.getElementsByName("handlerIds[" + futureIndex + "]_" + id)[0];
	handlerNamesObj = document.getElementsByName("handlerNames[" + futureIndex + "]_" + id)[0];
	handlerShowNames = document.getElementById("handlerShowNames[" + futureIndex + "]_" + id);
	if (isNull) {
		handlerIdsObj.value = handlerIdsObj.getAttribute("defaultValue");
		handlerNamesObj.value = handlerNamesObj.getAttribute("defaultValue");
		return;
	}
	if(handlerSelectType==lbpm.constant.ADDRESS_SELECT_FORMULA){
		handlerIdsObj.setAttribute("isFormula", "true");
	}
	else{
		handlerIdsObj.setAttribute("isFormula", "false");
	}
	handlerIdsObj.setAttribute("defaultValue", handlerIdsObj.value);
	handlerNamesObj.setAttribute("defaultValue", handlerNamesObj.value);
	handlerShowNames.innerHTML = handlerNamesObj.value;
	var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
	if(operatorInfo == null){
		return;
	}
	var currentNodeId = lbpm.nowNodeId;
	//返回json对象
	var rtnNodesMapJSON= new Array();
	var nodeObj=new Object();
	nodeObj.id=nextNodeId;
	nodeObj.handlerIds=handlerIdsObj.value;
	nodeObj.handlerNames=handlerNamesObj.value;
	if(handlerSelectType!=null){
		nodeObj.handlerSelectType=handlerSelectType;
	}
	rtnNodesMapJSON.push(nodeObj);
	var param={};
	param.nodeInfos=rtnNodesMapJSON;
	lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,param);
};

//驳回
function handlerRefuse(operationName,id){
	setNewAuditNoteDefaultUsageContent('handler_refuse',id);
	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	operationAuditTDTitle.innerHTML = XForm_NewAudit_Note.handlerOperationTypeRefuse.replace("{refuse}", operationName);
	var html = '<select controlId="'+ id  +'" name="newAuditNoteJumpToNodeIdSelectObj_' + id +'" alertText="" key="jumpToNodeId_' + id +'" style="max-width:200px;" onchange=jumpToNodeItemsChanged(this) ></select>';
	//在驳回时，增加选择节点通知方式 add by wubing date:2015-05-06
	html+="<label id='refuseNotifyTypeDivId' style='display:none'></label></br>";
	html+="<div id='triageHandler-' style='margin-top:4px;'></div>";
	lbpm.rejectOptionsEnabled = true; // 驳回选项开关是否开启标识
	if (Lbpm_SettingInfo && (Lbpm_SettingInfo["isShowRefuseOptonal"] === "false")) {
		lbpm.rejectOptionsEnabled = false;
	}
	// 驳回选项开关开启时才生成驳回选项html
	if (lbpm.rejectOptionsEnabled) {
		html += refusePassedToThisNodeLabel(operationName,id);
	}
	// 驳回后流经的子流程重新流转选项html
	var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
	if (isPassedSubprocessNode) {
		html += '<label id="isRecoverPassedSubprocessLabel_" class="lui-lbpm-checkbox" style="margin-left: 8px;';
		html += '"><input type="checkbox" id="isRecoverPassedSubprocess_" value="true" alertText="" key="isRecoverPassedSubprocess_">';
		html += ('<span class="checkbox-label">'+lbpm.constant.opt.abandonSubprocess+'</span>').replace("{refuse}", operationName);
		html += '</label>';
	}
	operationAuditTDContent.innerHTML = html;
    if(lbpm.rejectOptionsEnabled && lbpm.flowcharts.rejectReturn == 'true'){
        $('#refusePassedToSequenceFlowNode_' + id).prop('checked', false);
        $('#refusePassedToThisNodeOnNode_' + id).prop('checked', false);
        $('#refusePassedToTheNode_' + id).prop('checked', false);
        $('#refusePassedToThisNode_' + id).prop('checked', true);
    }
	lbpm.globals.hiddenObject(operationAuditRow, false);

	// 增加驳回节点重复过滤
	var currNodeInfo = lbpm.globals.getCurrentNodeObj();
	var currNodeId = currNodeInfo.id;

	var url = Com_Parameter.ContextPath+"sys/lbpm/engine/jsonp.jsp";
	var pjson = {"s_bean": "lbpmRefuseRuleDataBean", "processId": $("[name='sysWfBusinessForm.fdProcessId']").val(), "nodeId": currNodeId, "_d": new Date().toString()};
	var passNodeArray = [];
	$.ajaxSettings.async = false;
	$.getJSON(url, pjson, function(json) {
		passNodeArray = json;
	});
	$.ajaxSettings.async = true;
	var nodeHandlerNameArray = [];
	var newPassNodeArray = [];
	var indexNode = 0;
	var hasDefaultRefuse = false;
	$.each(passNodeArray, function(index, nodeId) {
		if(nodeId.indexOf("#") > -1) //默认驳回
		{
			var nodeIdIndex = nodeId.split("#");
			nodeId = nodeIdIndex[0];
			indexNode = nodeIdIndex[1];
			hasDefaultRefuse = true;
		}
		newPassNodeArray.push(nodeId);
	});
	passNodeArray = newPassNodeArray;
	nodeHandlerNameArray = getPassNodeHandlerName(passNodeArray, true);

	var jumpToNodeIdSelectObj = $("select[name='newAuditNoteJumpToNodeIdSelectObj_" + id + "']")[0];
	for(var i = 0; i < passNodeArray.length; i++){
		var nodeInfo = lbpm.nodes[passNodeArray[i]];

		var	option = document.createElement("option");
		var langNodeName = WorkFlow_getLangLabel(nodeInfo.name,nodeInfo["langs"],"nodeName");
		var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeInfo.id + ".");
		var itemShowStr = identifier + langNodeName;
		itemShowStr += nodeHandlerNameArray[nodeInfo.id];
		option.title = itemShowStr;
		var optTextLength = 65;
		itemShowStr = itemShowStr.length > optTextLength ? itemShowStr
			.substr(0, optTextLength) + '...'
			: itemShowStr;
		option.appendChild(document.createTextNode(itemShowStr));
		option.value=passNodeArray[i];
		jumpToNodeIdSelectObj.appendChild(option);

	}

	if(jumpToNodeIdSelectObj.options.length > 0){
		jumpToNodeIdSelectObj.selectedIndex = 0;
		if (!hasDefaultRefuse && Lbpm_SettingInfo && Lbpm_SettingInfo["isRefuseToPrevNodeDefault"] == "true") {
			jumpToNodeIdSelectObj.selectedIndex = jumpToNodeIdSelectObj.options.length - 1;
		} else {
			jumpToNodeIdSelectObj.selectedIndex = indexNode;
		}
		//在驳回时，增加默认的选择节点通知方式
		var defaultToNodeId = jumpToNodeIdSelectObj.value;
		lbpm.globals.setRefuseNodeNotifyType(defaultToNodeId);

		// <----------以下为驳回返回选项相关的逻辑处理，只有在驳回选项开关开启的情况下才会执行---------->
		if (lbpm.rejectOptionsEnabled) {
			var refusePassedToThisNode = document.getElementById("refusePassedToThisNode_");
			var refusePassedToThisNodeOnNode = document.getElementById("refusePassedToThisNodeOnNode_");
			var refusePassedToTheNode = document.getElementById("refusePassedToTheNode_");
			// 构建驳回后可返回到的节点选项
			buildReturnBackToTheNodeSelectOption(jumpToNodeIdSelectObj);
			// 驳回后流经的子流程重新流转选项
			if (isPassedSubprocessNode) {
				var isRecoverPassedSubprocess = document.getElementById("isRecoverPassedSubprocess_");
				var isRecoverPassedSubprocessLabel = document.getElementById("isRecoverPassedSubprocessLabel_");
				if ((refusePassedToThisNode && refusePassedToThisNode.checked) || (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) || (refusePassedToTheNode && refusePassedToTheNode.checked)) {
					isRecoverPassedSubprocessLabel.style.display = "none";
				}
				Com_AddEventListener(refusePassedToThisNode, "click", function(){
					if (refusePassedToThisNode && refusePassedToThisNode.checked) {
						isRecoverPassedSubprocess.checked = false;
					}
					isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNode && refusePassedToThisNode.checked) ? "none" : "";
				});
				Com_AddEventListener(refusePassedToThisNodeOnNode, "click", function(){
					if (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) {
						isRecoverPassedSubprocess.checked = false;
					}
					isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) ? "none" : "";
				});
				Com_AddEventListener(refusePassedToTheNode, "click", function(){
					if (refusePassedToTheNode && refusePassedToTheNode.checked) {
						isRecoverPassedSubprocess.checked = false;
					}
					isRecoverPassedSubprocessLabel.style.display = (refusePassedToTheNode && refusePassedToTheNode.checked) ? "none" : "";
				});
				Com_AddEventListener(jumpToNodeIdSelectObj, "change", function(){
					if (!((refusePassedToThisNode && refusePassedToThisNode.checked) || (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) || (refusePassedToTheNode && refusePassedToTheNode.checked))) {
						// 没有驳回返回选项被勾选时，驳回后流经的子流程重新流转选项可以显示出来
						isRecoverPassedSubprocessLabel.style.display = "";
					}
				});
			}
		}
	}

	var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
	//开关开启才会去执行驳回具体审批人
	if(lbpm.settingInfo.isRefuseSelectPeople=="true"){
		if(processDefine&&processDefine.refuseSelectPeople&&processDefine.refuseSelectPeople=="true"){
			if(jumpToNodeIdSelectObj.selectedIndex!==undefined&&jumpToNodeIdSelectObj.selectedIndex>-1){
				var nodeData = lbpm.nodes[jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value];
				//会审才会显示具体人员
				if(nodeData.processType&&nodeData.processType=="2"){
					var selectTrialHtml=buildRefuseSelectTrialStaff(jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value);
					$("#triageHandler-").html(selectTrialHtml);
					$("xformflag[flagtype='xform_fSelect']").fSelect();
				}else{
					$("#triageHandler-").html("");
				}
			}
		}
	}

	if (jumpToNodeIdSelectObj.options.length == 0) {
		//#145275 修复 并行分支内第一个人点击驳回时提示不准确
		operationAuditTDContent.innerHTML = (XForm_NewAudit_Note.noRefuseNode + '<input type="hidden" alertText="' + XForm_NewAudit_Note.noRefuseNode + '" key="jumpToNodeId">').replace(/{refuse}/g, operationName);
		alert(XForm_NewAudit_Note.noRefuseNode.replace(/{refuse}/g, operationName));
	}
	//联动流程审批中的操作项
	var $select = $("#operationAuditTDContent_" + id).find("select[name*='newAuditNoteJumpToNodeIdSelectObj_']");
	$select.bind("change",function (){
		$("#operationsTDContent > select > option[value='" + this.value +  "']").prop("selected",true);
	})
	//驳回的节点通过后，返回指定节点下拉框联动控制
	var $select = $("#operationAuditTDContent_" + id).find("select[name*='_returnBackToNodeIdSelectObj" + id +"']");
	$select.bind("change",function (){
		$("#operationsTDContent > select[name='returnBackToNodeIdSelectObj'] > option[value='" + this.value +  "']").prop("selected",true);
	})

	//待办勾选复选框联动
	var $checkbox = $("#operationAuditTDContent_" + id).find("input[value*='todo']");
	$checkbox.bind("click",function (){
		var notifyCheck = $("#operationAuditTDContent_"+id+" > #refuseNotifyTypeDivId > label[class='lui-lbpm-checkbox'] > input[value='todo']").is(":checked");
		if(notifyCheck){
			$("#operationsTDContent > #refuseNotifyTypeDivId > label[class='lui-lbpm-checkbox'] > input[value='todo']").prop("checked",true);
		}else{
			$("#operationsTDContent > #refuseNotifyTypeDivId > label[class='lui-lbpm-checkbox'] > input[value='todo']").prop("checked",false);
		}
	})
}

function buildRefuseSelectTrialStaff(nodeId){
	var handlerArray=getPassNodeHandlerObj(nodeId);
	var nodeData = lbpm.nodes[nodeId];
	var trialStaffPeopleHtmlStart="<xformflag flagid='fd_trialStaffPeople' id='refuse_xform_trialStaffPeople' property='trialStaffPeople' flagtype='xform_fSelect' _xform_type='fSelect'>"+
		"<div class='select_div_box xform_Select' fd_type='fSelect' style='display: inline-block; width: auto; text-align: left;'>"+
		"<div id='refuseDiv_trialStaffPeople' style='display:none'>"+
		"<input name='trialStaffPeople' type='hidden' value='' key='lbpmHandlerTriage'>"+
		"</div>"+
		"<div class='fs-wrap multiple'>"+
		"<div class='fs-label-wrap'>"+
		"<div class='fs-label' >=="+lbpm.constant.opt.refusePeople+"==</div>"+
		"<span class='fs-arrow'></span>"+
		"</div>"+
		"<div class='fs-dropdown'>"+
		"<div class='fs-search'>"+
		"<input type='text' autocomplete='off' placeholder='"+lbpm.constant.opt.refusePeopleSearch+"'>"+
		"<i class='fs-search-icon-del'></i>"+
		"</div>";

	var optionHtml="";
	if(handlerArray.length>0){
		for(var i=0;i<handlerArray.length;i++){
			optionHtml+="<div class='fs-options'>"+
				"<div data-value='"+handlerArray[i].handlerId+"' class='fs-option' data-index='0' onclick='peopleOptionSelect(this)'>"+
				"<span class='fs-checkbox'><i></i></span>"+
				"<div class='fs-option-label'>"+handlerArray[i].handlerName+"</div>"+
				"<input type='hidden' name='_trialStaffPeople' value='"+handlerArray[i].handlerId+"'>"+
				"</div>"+
				"</div>";
		}
	}

	var trialStaffPeopleHtmlEnd="</div>"+
		"</div>"+
		"</div></xformflag>";

	var selectHtml=trialStaffPeopleHtmlStart+optionHtml+trialStaffPeopleHtmlEnd;
	return selectHtml;
}

function refusePassedToThisNodeLabel(operationName,id){
	var extAttrs=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	var refuseTypes = [];
	var isDisable = false;
	var index = 0;
	for(var i = 0;extAttrs && i < extAttrs.length;i++){
		if(extAttrs[i].name == 'refuse_types'){
			refuseTypes=extAttrs[i].value.split(";");
			break;
		}
	}

	//只有一条时要加上只读属性
	if(refuseTypes && refuseTypes.length==1){
		isDisable = true;
	}
	var html = '';
	/*html += '<HR align=left width=100% color=white SIZE=0.3>';*/
	// zl
	if(showOption('refusePassedToSequenceFlowNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += 'id="refusePassedToSequenceFlowNodeLabel_'+ id +'" class="lui-lbpm-checkbox" onclick= handleRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.sequenceFlow.replace("{refuse}", operationName) + '"><input type="checkbox" id="refusePassedToSequenceFlowNode_'+ id +'" value="true" alertText="" key="refusePassedToSequenceFlowNode_'+ id +'"';
		if(index==0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.sequenceFlowTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}
	if(showOption('refusePassedToThisNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="refusePassedToThisNodeLabel_' + id + '" class="lui-lbpm-checkbox" onclick=handleRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.returnBackMe.replace("{refuse}", operationName) +'" ><input type="checkbox" id="refusePassedToThisNode_' + id + '" value="true" alertText="" key="refusePassedToThisNode_' + id + '"';
		if(index==0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackMeTitle.replace("{refuse}", operationName)+'</span></label></br>';
		html += '<label ';
		index += 1;
	}
	if(showOption('refusePassedToThisNodeOnNodeLabel',refuseTypes)){
		//增加驳回返回本节点，add by wubing date:2016-07-29
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="refusePassedToThisNodeOnNodeLabel_'+ id +'" class="lui-lbpm-checkbox" onclick=handleRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.returnBackTitle.replace("{refuse}", operationName) + '"';
		html += '><input type="checkbox" id="refusePassedToThisNodeOnNode_'+ id +'" value="true" alertText="" key="refusePassedToThisNodeOnNode_'+ id +'"';
		if(isDisable){
			html += " disabled='disabled'";
		}
		if(index==0){
			html += " checked='true'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}
	if(showOption('refusePassedToTheNodeLabel',refuseTypes)){
		//增加驳回返回指定节点 add by linbb
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="refusePassedToTheNodeLabel_'+ id +'" class="lui-lbpm-checkbox" onclick=handleRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.returnBackTheNode.replace("{refuse}", operationName) +'" ';
		html += '><input type="checkbox" id="refusePassedToTheNode_'+ id +'" value="true" alertText="" key="refusePassedToTheNode_'+ id +'"';
		if(isDisable){
			html += " disabled='disabled'";
		}
		if(index==0){
			html += " checked='true'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackTheNodeTitle.replace("{refuse}", operationName)+'</span></label>';
		html += '<select name="_returnBackToNodeIdSelectObj_' + id + '" alertText="" key="_returnBackToNodeId_'+ id +'" style="display:none;max-width:200px;margin-left:4px;"></select>';
		index += 1;
	}
	return html;
}

function handleRefuseOption(el,id){
	var refusePassedToSequenceFlowNode = document.getElementById("refusePassedToSequenceFlowNode_" + id);
	var refusePassedToThisNodeOnNode = document.getElementById("refusePassedToThisNodeOnNode_" + id);
	var refusePassedToTheNode = document.getElementById("refusePassedToTheNode_" + id);
	var refusePassedToThisNode = document.getElementById("refusePassedToThisNode_" + id);

	//流程里驳回复选框选项
	var lbpmRefuseToSequenceFlowNode = document.getElementById("refusePassedToSequenceFlowNode");
	var lbpmRefuseToThisNodeOnNode = document.getElementById("refusePassedToThisNodeOnNode");
	var lbpmRefuseToTheNode = document.getElementById("refusePassedToTheNode");
	var lbpmRefuseToThisNode = document.getElementById("refusePassedToThisNode");
	if(el.id=="refusePassedToSequenceFlowNodeLabel_" + id){
		var thisCheck = refusePassedToSequenceFlowNode;
		var othersNoSelect = (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToThisNode && !refusePassedToThisNode.checked);
		lbpmRefuseToSequenceFlowNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNodeOnNode)
				refusePassedToThisNodeOnNode.checked = false;
			lbpmRefuseToThisNodeOnNode.checked = false;
			if(refusePassedToTheNode)
				refusePassedToTheNode.checked = false;
			lbpmRefuseToTheNode.checked = false;
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			lbpmRefuseToThisNode.checked = false;
		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}
		var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
		if(isPassedSubprocessNode){
			var isRecoverPassedSubprocessLabel = document.getElementById("isRecoverPassedSubprocessLabel_");
			isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNode.checked) ? "none" : "";
		}

	}

	if(el.id=="refusePassedToThisNodeLabel_" + id){
		var thisCheck = refusePassedToThisNode;
		var othersNoSelect = (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked);
		lbpmRefuseToThisNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNodeOnNode)
				refusePassedToThisNodeOnNode.checked = false;
			lbpmRefuseToThisNodeOnNode.checked = false;
			if(refusePassedToTheNode)
				refusePassedToTheNode.checked = false;
			lbpmRefuseToTheNode.checked = false;
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
			lbpmRefuseToSequenceFlowNode.checked = false;
		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}

	}
	if(el.id=="refusePassedToThisNodeOnNodeLabel_" + id){
		var thisCheck = refusePassedToThisNodeOnNode;
		var othersNoSelect = (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToThisNode && !refusePassedToThisNode.checked);
		lbpmRefuseToThisNodeOnNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			lbpmRefuseToThisNode.checked = false;
			if(refusePassedToTheNode)
				refusePassedToTheNode.checked = false;
			lbpmRefuseToTheNode.checked = false;
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
			lbpmRefuseToSequenceFlowNode.checked = false;
		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}

	}
	if(el.id=="refusePassedToTheNodeLabel_" + id){
		var thisCheck = refusePassedToTheNode;
		var othersNoSelect = (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked) && (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToThisNode && !refusePassedToThisNode.checked);
		lbpmRefuseToTheNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			lbpmRefuseToThisNode.checked = false;
			if(refusePassedToThisNodeOnNode)
				refusePassedToThisNodeOnNode.checked = false;
			lbpmRefuseToThisNodeOnNode.checked = false;
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
			lbpmRefuseToSequenceFlowNode.checked = false;

		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}

		if (thisCheck.checked) {
			buildReturnBackToNodeIdSelectOption(id);
			lbpm.globals.hiddenObject($("select[name='_returnBackToNodeIdSelectObj_"+ id + "']")[0], false);
			lbpm.globals.buildReturnBackToNodeIdSelectOption();
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], false);
		} else {
			lbpm.globals.hiddenObject($("select[name='_returnBackToNodeIdSelectObj_'"+ id + "]")[0], true);
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], true);
			lbpm.events.fireListener("clickRefusePassedToTheNodeLabel", null);
		}

	} else {
		lbpm.globals.hiddenObject($("select[name='_returnBackToNodeIdSelectObj_'"+ id + "]")[0], true);
		lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], true);
	}
}

function handleSuperRefuseOption(el,id){
	var refusePassedToSequenceFlowNode = document.getElementById("superRefusePassedToSequenceFlowNode_" + id);
	var refusePassedToThisNodeOnNode = document.getElementById("superRefusePassedToThisNodeOnNode_" + id);
	var refusePassedToTheNode = document.getElementById("superRefusePassedToTheNode_" + id);
	var refusePassedToThisNode = document.getElementById("superRefusePassedToThisNode_" + id);

	//流程里超级驳回复选框选项
	var lbpmRefuseToSequenceFlowNode = document.getElementById("refusePassedToSequenceFlowNode");
	var lbpmRefuseToThisNodeOnNode = document.getElementById("refusePassedToThisNodeOnNode");
	var lbpmRefuseToTheNode = document.getElementById("refusePassedToTheNode");
	var lbpmRefuseToThisNode = document.getElementById("refusePassedToThisNode");

	if(el.id == ("superRefusePassedToSequenceFlowNodeLabel_" + id)){
		var thisCheck = refusePassedToSequenceFlowNode;
		var othersNoSelect = (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToThisNode && !refusePassedToThisNode.checked);
		lbpmRefuseToSequenceFlowNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNodeOnNode)
				refusePassedToThisNodeOnNode.checked = false;
			if (lbpmRefuseToThisNodeOnNode) {
			    lbpmRefuseToThisNodeOnNode.checked = false;
            }
			if(refusePassedToTheNode)
				refusePassedToTheNode.checked = false;
			if (lbpmRefuseToTheNode) {
			    lbpmRefuseToTheNode.checked = false;
            }
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			if (lbpmRefuseToThisNode) {
			    lbpmRefuseToThisNode.checked = false;
		    }
		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}
		var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
		if(isPassedSubprocessNode){
			var isRecoverPassedSubprocessLabel = $("#isRecoverPassedProcessLabel_" + id);
			isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNode.checked) ? "none" : "";
		}
	}

	if(el.id == ("superRefusePassedToThisNodeLabel_" + id)){
		var thisCheck = refusePassedToThisNode;
		var othersNoSelect = (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked);
		lbpmRefuseToThisNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNodeOnNode)
				refusePassedToThisNodeOnNode.checked = false;
			if (lbpmRefuseToThisNodeOnNode) {
			    lbpmRefuseToThisNodeOnNode.checked = false;
            }
			if(refusePassedToTheNode)
				refusePassedToTheNode.checked = false;
			if (lbpmRefuseToTheNode) {
			    lbpmRefuseToTheNode.checked = false;
            }
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
			if (lbpmRefuseToSequenceFlowNode) {
			    lbpmRefuseToSequenceFlowNode.checked = false;
		    }
		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}
	}
	if(el.id == ("superRefusePassedToThisNodeOnNodeLabel_" + id)){
		var thisCheck = refusePassedToThisNodeOnNode;
		var othersNoSelect = (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked) && (refusePassedToTheNode && !refusePassedToTheNode.checked) && (refusePassedToThisNode && !refusePassedToThisNode.checked);
		lbpmRefuseToThisNodeOnNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			if (lbpmRefuseToThisNode) {
			    lbpmRefuseToThisNode.checked = false;
            }
			if(refusePassedToTheNode)
				refusePassedToTheNode.checked = false;
			if (lbpmRefuseToTheNode) {
			    lbpmRefuseToTheNode.checked = false;
            }
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
			if (lbpmRefuseToSequenceFlowNode) {
			    lbpmRefuseToSequenceFlowNode.checked = false;
		    }
		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}
	}
	if(el.id == ("superRefusePassedToTheNodeLabel_" + id)){
		var thisCheck = refusePassedToTheNode;
		var othersNoSelect = (refusePassedToSequenceFlowNode && !refusePassedToSequenceFlowNode.checked) && (refusePassedToThisNodeOnNode && !refusePassedToThisNodeOnNode.checked) && (refusePassedToThisNode &&!refusePassedToThisNode.checked);
		lbpmRefuseToTheNode.checked = true;
		if(thisCheck) {
			if(refusePassedToThisNode)
				refusePassedToThisNode.checked = false;
			if (lbpmRefuseToThisNode) {
			    lbpmRefuseToThisNode.checked = false;
            }
			if(refusePassedToThisNodeOnNode)
				refusePassedToThisNodeOnNode.checked = false;
			if (lbpmRefuseToThisNodeOnNode) {
			    lbpmRefuseToThisNodeOnNode.checked = false;
            }
			if(refusePassedToSequenceFlowNode)
				refusePassedToSequenceFlowNode.checked = false;
			if (lbpmRefuseToSequenceFlowNode) {
			    lbpmRefuseToSequenceFlowNode.checked = false;
            }
		}
		if(othersNoSelect && !thisCheck.checked) {
			thisCheck.checked = true;
		}

		if (thisCheck.checked) {
			//控制超级驳回的节点通过后，返回指定节点时的下拉框加载及显示隐藏
			buildSuperReturnBackToNodeIdSelectOption(id);
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj_" + id + "']")[0], false);
			lbpm.globals.buildSuperReturnBackToNodeIdSelectOption();
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], false);
		} else {
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj_" + id + "']")[0], true);
			lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], true);
			lbpm.events.fireListener("clickRefusePassedToTheNodeLabel", null);
		}
	} else {
		lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj_" + id + "']")[0], true);
		lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj']")[0], true);
	}
}
function buildReturnBackToNodeIdSelectOption(id){

	var jumpToNodeIdSelectObj = $("select[name='newAuditNoteJumpToNodeIdSelectObj_" + id + "']")[0];
	var jumpToNodeIdSelectOptions = jumpToNodeIdSelectObj.options;
	var jumpToNodeId = jumpToNodeIdSelectOptions[jumpToNodeIdSelectObj.selectedIndex].value;
	var returnNodes = getReturnNodes(lbpm.globals.getNodeObj(jumpToNodeId));

	$("select[name='_returnBackToNodeIdSelectObj_" + id +"']").empty();
	var returnBackToNodeIdSelectObj = $("select[name='_returnBackToNodeIdSelectObj_" + id +"']")[0];
	if(!returnBackToNodeIdSelectObj)
		return;
	for(var j = 0; j < jumpToNodeIdSelectOptions.length; j++){
		var nodeInfo = lbpm.globals.getNodeObj(jumpToNodeIdSelectOptions[j].value);
		if (!containNode(returnNodes, nodeInfo)){
			continue;
		}
		var	option = document.createElement("option");
		option.title = jumpToNodeIdSelectOptions[j].text;
		var itemShowStr = option.title.length > 65 ? option.title.substr(0, 65) + '...' : option.title;
		option.appendChild(document.createTextNode(itemShowStr));
		option.value=jumpToNodeIdSelectOptions[j].value;
		returnBackToNodeIdSelectObj.appendChild(option);
	}

	var	option = document.createElement("option");
	option.title = lbpm.constant.opt.thisNode;
	option.appendChild(document.createTextNode(option.title));
	option.value=lbpm.nowNodeId;
	returnBackToNodeIdSelectObj.appendChild(option);
}

function buildSuperReturnBackToNodeIdSelectOption(id){
	var jumpToNodeIdSelectObj = $("select[name='newAuditNoteJumpToNodeIdSelectObj_" + id + "']")[0];
	var jumpToNodeIdSelectOptions = jumpToNodeIdSelectObj.options;
	var jumpToNodeId = jumpToNodeIdSelectOptions[jumpToNodeIdSelectObj.selectedIndex].value;
	var returnNodes = getReturnNodes(lbpm.globals.getNodeObj(jumpToNodeId));

	$("select[name='returnBackToNodeIdSelectObj_" + id + "']").empty();
	var returnBackToNodeIdSelectObj = $("select[name='returnBackToNodeIdSelectObj_" + id + "']")[0];
	if(!returnBackToNodeIdSelectObj)
		return;
	for(var j = 0; j < jumpToNodeIdSelectOptions.length; j++){
		var nodeInfo = lbpm.globals.getNodeObj(jumpToNodeIdSelectOptions[j].value);
		if (!containNode(returnNodes, nodeInfo)){
			continue;
		}
		var	option = document.createElement("option");
		option.title = jumpToNodeIdSelectOptions[j].text;
		var itemShowStr = option.title.length > 65 ? option.title.substr(0, 65) + '...' : option.title;
		option.appendChild(document.createTextNode(itemShowStr));
		option.value=jumpToNodeIdSelectOptions[j].value;
		returnBackToNodeIdSelectObj.appendChild(option);
	}
	if (containNode(returnNodes, lbpm.globals.getNodeObj(lbpm.nowNodeId))) {
		var	option = document.createElement("option");
		option.title = lbpm.constant.opt.thisNode;
		option.appendChild(document.createTextNode(option.title));
		option.value=lbpm.nowNodeId;
		returnBackToNodeIdSelectObj.appendChild(option);
	}

	if (returnBackToNodeIdSelectObj.options.length == 0) {
		var	option = document.createElement("option");
		option.appendChild(document.createTextNode(lbpm.constant.opt.noReturnBackNode));
		option.value=null;
		returnBackToNodeIdSelectObj.appendChild(option);
	}
}

//转办
function handlerCommission(operationName,id){
	setNewAuditNoteDefaultUsageContent('handler_commission',id);
	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	operationAuditTDTitle.innerHTML = operationName + lbpm.constant.opt.CommissionPeople;
	var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
	var options = {
		mulSelect : false,
		idField : 'toOtherHandlerIds_' + id,
		nameField : 'toOtherHandlerNames_' + id,
		splitStr : ';',
		selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
		notNull : true,
		exceptValue : currentOrgIdsObj.value.split(';'),
		text : lbpm.constant.SELECTORG
	};
	var autoPopAction = null;
	var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
	var ids = 'toOtherHandlerIds_' + id;
	var name = 'toOtherHandlerNames_' + id;
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
			options.onclick = "Dialog_AddressList(false, '" + ids + "', '" + name + "', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", null)";
			autoPopAction = function(){
				Dialog_AddressList(false, ids, name, ';',defaultOptionBean,
					options.action, searchBean, null,  options.notNull, null);
			};
		}else {
			var deptLimit = "";
			if(operationScope == "org"){
				deptLimit = "myOrg";
			}else if(operationScope == "dept"){
				deptLimit = "myDept";
			}
			options.deptLimit = deptLimit;
			autoPopAction = function(){
				Dialog_Address(false, ids, name, ';',options.selectType,
					options.action, null, null,  options.notNull, null,
					options.text, options.exceptValue, deptLimit);
			};
		}
	} else {
		autoPopAction = function(){
			Dialog_Address(false, ids, name, ';',options.selectType,
				options.action, null, null,  options.notNull, null,
				options.text, options.exceptValue);
		};
	}

	var html = lbpm.address.html_build(options);
    // 是否转办隐藏意见
    if(Lbpm_SettingInfo && Lbpm_SettingInfo['isHiddeTurnToDoNoteConfigurable']=="true" && lbpm.flowcharts['isHiddeTurnToDoNoteConfigurable']!='true'){
        html += "&nbsp;<label class='lui-lbpm-checkbox'>";
        html += "<input onclick='handlerCommissionHiddenNote(this);' type='checkbox' key='isHiddenNote_" + id + "' value='true'><span class='checkbox-label'>" + lbpm.constant.opt.TurnToDoNoteHiddenNote + "</span></label>";
    }
    html += "&nbsp;";
    html += GetAuditNoteNotifyType4Node(currentNodeObj, id);
    // 在转办时，增加“流程重新流经本节点时，直接由转办人员处理 ”的开关
    if (currentNodeObj.handlerSelectType=="org") {
        html += '<br><label id="returnToCommissionedPersonLabel_' + id + '" class="lui-lbpm-checkbox"><input onclick="handlerCommissionReturnToCommissionedPerson(this);" type="checkbox" id="returnToCommissionedPerson_' + id +'" value="true" alertText="" key="returnToCommissionedPerson_' + id + '"><span class="checkbox-label">'+lbpm.constant.opt.returnToCommissionedPerson.replace("{commission}",operationName)+'</span></label>';
    }
	operationAuditTDContent.innerHTML = html;
    syncNotifyType(id);
	lbpm.globals.hiddenObject(operationAuditRow, false);
	setTimeout(autoPopAction,100);
	//联动流程审批中的操作项
	$("#toOtherHandlerIds_" + id).bind('change',function(){
		$("#toOtherHandlerIds").val(this.value);
		$("#toOtherHandlerNames").val($("#toOtherHandlerNames_" + id).val());
	});
}

function GetAuditNoteNotifyType4Node(currentNodeObj, id) {
    var notifyType4Node = lbpm.globals.getNotifyType4Node(currentNodeObj);
    notifyType4Node = notifyType4Node.replace(/_notifyType_node/ig, "_notifyType_node_" + id);
    notifyType4Node = notifyType4Node.replace(/__notify_type_4opr_/ig, "__notify_type_4opr_" + id + "_");
    return notifyType4Node;
}

function GetAuditNoteNotifyType4NodeHTML(controlId, nodeId) {
    var notifyType4Node = lbpm.globals.getNotifyType4NodeHTML(nodeId);
    notifyType4Node = notifyType4Node.replace(/_notifyType_node/ig, "_notifyType_node_" + controlId);
    notifyType4Node = notifyType4Node.replace(/__notify_type_4opr_/ig, "__notify_type_4opr_" + controlId + "_");
    return notifyType4Node;
}



function handlerCommissionHiddenNote(src) {
    if (src.checked) {
        $("[key='isHiddenNote']").prop("checked", true);
    } else {
        $("[key='isHiddenNote']").prop("checked", false);
    }
}

function handlerCommissionReturnToCommissionedPerson(src) {
    if (src.checked) {
        $("#returnToCommissionedPerson").prop("checked", true);
    } else {
        $("#returnToCommissionedPerson").prop("checked", false);
    }
}

//沟通
var addressHtml = ""; //增加地址本弹窗全局变量
function handlerCommunicate(operationName,id){
	setNewAuditNoteDefaultUsageContent('handler_communicate',id);
	//清除所有operationsRow_ALL信息
	var html = "";
	// 设置显示当前正在沟通人员
	var relationInfoObj = lbpm.globals.getCurrRelationInfo();
	var ids = "";
	var names = "";
	var idFields = 'toOtherHandlerIds_' + id;
	var nameFields = 'toOtherHandlerNames_' + id;
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
		html += "<input type='hidden' name='currentCommunicateIds_" + id + "' value='"
			+ ids + "'/>";
		html += "<label>" + names + "</label><br/>";
	}
	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDConten = document.getElementById("operationAuditTDContent_" + id);
	operationAuditTDTitle.innerHTML = operationName
		+ lbpm.constant.opt.CommunicatePeople;
	var operatorInfo = lbpm.globals
		.getOperationParameterJson("relationWorkitemId:relationScope");
	var currentOrgIdsObj = document
		.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
	var exceptValueStr = lbpm.globals.stringConcat(currentOrgIdsObj.value, ids);

	var options = {
		mulSelect : true,
		idField : idFields,
		nameField : nameFields,
		splitStr : ';',
		selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
		action : "lbpm.globals._checkRepeatPersonInPost",//检测岗位中是否包含有所选择的人
		notNull : ids.length < 1, //如果已存在沟通人员则当前沟通人员不一定为必选的
		alertText : (lbpm.constant.opt.CommunicateIsNull + operationName + lbpm.constant.opt.CommunicatePeople),
		text : lbpm.constant.SELECTORG,
		exceptValue : exceptValueStr.split(';')
	};
	// 判断是否为节点处理的第一个沟通发起者
	if (!operatorInfo.relationWorkitemId) {
		//获取所有已选的范围
		var exceptValue = lbpm.globals
			.stringConcat(currentOrgIdsObj.value, ids);
		options.exceptValue = exceptValue.split(';');
	}else {
		// 被沟通对象被限定范围时
		if (operatorInfo.relationScope) {
			if (lbpm.address.is_pda()) {
				operationAuditTDConten.innerHTML = lbpm.constant.opt.EnvironmentUnsupportOperation;
				lbpm.globals.hiddenObject(operationAuditRow, false);
				return;
			}
			var dataBean = "lbpmCommunicateScopeService&scopeHandles="
				+ operatorInfo.relationScope;
			var searchBean = dataBean + "&keyword=!{keyword}";
			addressHtml = options.onclick = "Dialog_AddressList(true,'" + idFields + "','" + nameFields + "',';','"
				+ dataBean
				+ "',lbpm.globals._checkRepeatPersonInPost,'"
				+ searchBean
				+ "','','',lbpm.constant.opt.CommunicateCheckObj)";
		}
	}
	var autoPopAction = null;
	var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
	if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_communicate"] && !operatorInfo.relationScope){
		var operationScope = currentNodeObj.operationScope["handler_communicate"];
		if(operationScope  == "custom"){
			var scopeType = currentNodeObj.operationScope["handler_communicate"] == null?"":currentNodeObj.operationScope["handler_communicate"];
			var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
			var customHandlerSelectType = currentNodeObj.operationScope["handler_communicate_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_communicate_customHandlerSelectType"];
			var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
			var customHandlerIds = currentNodeObj.operationScope["handler_communicate_customIds"] == null?"":currentNodeObj.operationScope["handler_communicate_customIds"];
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
				+ "&exceptValue=" + exceptValueStr;
			if(!operatorInfo.relationWorkitemId){
				defaultOptionBean += "&exceptValue=" + lbpm.globals.stringConcat(currentOrgIdsObj.value, ids);
			} else {
				defaultOptionBean += "&exceptValue=" + currentOrgIdsObj.value;
			}
			var searchBean = defaultOptionBean + "&keyword=!{keyword}";
			addressHtml = options.onclick = "Dialog_AddressList(true, '" + idFields + "', '" + nameFields + "', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", '"+options.alertText+"')";
			autoPopAction = function(){
				Dialog_AddressList(true, idFields, nameFields, ';',defaultOptionBean,
					options.action, searchBean, null,  options.notNull, options.alertText);
			};
		}else{
			var deptLimit = "";
			if(operationScope == "org"){
				deptLimit = "myOrg";
			}else if(operationScope == "dept"){
				deptLimit = "myDept";
			}
			autoPopAction = function(){
				Dialog_Address(true, idFields, nameFields, ';', options.selectType,
					options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue, deptLimit);
			};
			if(deptLimit){
				addressHtml = options.onclick = "Dialog_Address(true, '" + idFields +"', '" + nameFields + "', ';', "+options.selectType+", "+options.action+", null, null, "+options.notNull+", '"+options.alertText+"','"+options.text+"', "+JSON.stringify(options.exceptValue).replace(/\"/g,"'") + ", '" + deptLimit + "')";
			}
		}
	} else {
		autoPopAction = function(){
			Dialog_Address(true, idFields, nameFields, ';', options.selectType,
				options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue);
		};
	}


	html += lbpm.address.html_build(options);
	options.action = lbpm.globals._checkRepeatPersonInPost;
	var isHasBr = false;
	//判断是否为节点处理的第一个沟通发起者,设置是否允许多级沟通
	if (operatorInfo.isMutiCommunicate==true || operatorInfo.isMutiCommunicate==null) {
		// 是否可进行多级沟通
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiCommunicateConfigurable']=="true" && lbpm.flowcharts['multiCommunicateEnabled']!="false"){
			if (lbpm.approveType == "right") {
				html += "<br><label class='lui-lbpm-checkbox'>";
				isHasBr = true;
			}else{
				html += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
			}
			var funstr = "onclick=\"CommunicateScope(this,'"+operationName+"','"+id+"')\"";
			html += "<input id='mutiCommunicate_" + id + "' type='checkbox' key='isMutiCommunicate_" + id + "' operationName='"+operationName+"' "+funstr+" ><span class='checkbox-label'>";
			html += lbpm.constant.opt.CommunicateScopeAllowMuti + operationName;
			html += "</span></label>";
			CommunicateScope({checked:false},operationName,id);
		}
	}
	if (!operatorInfo.relationWorkitemId) {
		// 是否可隐藏沟通意见
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isHiddenCommunicateNoteConfigurable']=="true" && lbpm.flowcharts['isHiddenCommunicateNoteEnabled']!='true'){
			if (lbpm.approveType == "right" && !isHasBr) {
				html += "<br><label class='lui-lbpm-checkbox'>";
			}else{
				html += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
			}
			html += "<input id='_isHiddenNote' type='checkbox' key='isHiddenNote_' value='true' onclick = HiddenNoteClick(this) ><span class='checkbox-label'>" + lbpm.constant.opt.CommunicateHiddenNote + "</span></label>";
		}
	}
	html +="<br>"+GetAuditNoteNotifyType4Node(currentNodeObj, id);
	operationAuditTDConten.innerHTML = html;
    syncNotifyType(id);
	lbpm.globals.hiddenObject(operationAuditRow, false);

	// 如果在节点属性设置了限定范围，则不带出默认沟通人
	if(addressHtml==''){
		// 设置默认沟通人
		if (currentNodeObj.operationScope && currentNodeObj.operationScope["handler_communicate"] == 'all'
			&& currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"]
			&& currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"]!="") {
			if($("#" + idFields).val()=="" && $("#" + nameFields).val()==""){
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
				$("#" + idFields).val(defaultHandlerIds);
				$("#" + nameFields).val(defaultHandlerNames);
			}
		} else {
			if(!currentNodeObj.operationScope || !currentNodeObj.operationScope["handler_communicate"]
				|| (currentNodeObj.operationScope["handler_communicate"] == 'all' && !currentNodeObj.operationScope["handler_communicate_defaultHandlerIds"])){
				if(Lbpm_SettingInfo && Lbpm_SettingInfo['isCommunicateWithCreatorDefault']=="true"){
					if($("#" + idFields).val()=="" && $("#" + nameFields).val()==""){
						var draftorId = $("[name='sysWfBusinessForm.fdDraftorId']").val();
						var canCommunicate = true;
						if (operatorInfo.relationScope) {
							if(operatorInfo.relationScope.indexOf(draftorId)==-1){
								canCommunicate = false;
							}
						}
						if(canCommunicate && (exceptValueStr && exceptValueStr.indexOf(draftorId) == -1)){
							$("#" + idFields).val(draftorId);
							$("#" + nameFields).val($("[name='sysWfBusinessForm.fdDraftorName']").val());
						}
					}
				}
			}
		}
	}

	if (operatorInfo.isMutiCommunicate==true || operatorInfo.isMutiCommunicate==null) {
		// 是否默认勾选多级沟通
		if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiCommunicateConfigurable']=="true" && lbpm.flowcharts['multiCommunicateEnabled']!="false" && Lbpm_SettingInfo['isMultiCommunicateDefault'] == "true"){
			var mule = document.getElementById("mutiCommunicate_"+id);
			mule.checked = true;
			CommunicateScope(mule,operationName,id);
            $("#operationsRow_Scope_" + id).show();
		} else {
		    $("#operationsRow_Scope_" + id).hide();
        }
	}
	// 被沟通对象被限定范围时
	if (operatorInfo.relationScope) {
		if (typeof lbpm.globals.isNewAuditNote == "undefined"){
			setTimeout(function(){
					var dataBean = "lbpmCommunicateScopeService&scopeHandles="
						+ operatorInfo.relationScope;
					var searchBean = dataBean + "&keyword=!{keyword}";
					Dialog_AddressList(true,idFields,nameFields,';',
						dataBean
						,lbpm.globals._checkRepeatPersonInPost,
						searchBean
						,'','',lbpm.constant.opt.CommunicateCheckObj);
				}
				,100);
		}
	}else{
		//if (typeof lbpm.globals.isNewAuditNote == "undefined" || lbpm.globals.isNewAuditNote == false){
		setTimeout(autoPopAction,100);
		//}
	}

	//联动流程审批中沟通人员选择
	$("#toOtherHandlerIds_" + id).bind('change',function(){
		$("#toOtherHandlerIds").val(this.value);
		$("#toOtherHandlerNames").val($("#toOtherHandlerNames_" + id).val());
	});

	//联动流程审批中限制子级沟通范围人员选择
	$("#limitScopeHandlerIds").bind('change',function(){
		$("#communicateScopeHandlerIds").val(this.value);
		$("#communicateScopeHandlerNames").val($("#limitScopeHandlerNames").val());
	});
}

function syncNotifyType(id) {
    $("[name*='__notify_type_4opr_" + id +"']").unbind();
    $("[name*='__notify_type_4opr_" + id +"']").click(function(){
        var processNotifyName = $(this).attr("name").replace(id + "_", "");
        var val = $(this).val();
        $("[name='" + processNotifyName +"'][value='" + val +"']").click();
    });
}

function HiddenNoteClick(his){
	var opnion = $("input[key='isHiddenNote']");
	if(his.checked){
		for(var i=0; i<opnion.length; i++){
			opnion[i].checked=true;
		}
	}else{
		for(var i=0; i<opnion.length; i++){
			opnion[i].checked=false;
		}
	}
}
function CommunicateScope(sel,operationName,id){
	var idFields = 'toOtherHandlerIds_' + id;
	var nameFields = 'toOtherHandlerNames_' + id;
	var operationsRow_Scope = document.getElementById("operationsRow_Scope_"+id);
	var mule = document.getElementById("_mutiCommunicate");
	// 不限定范围处理情况
	if (!sel.checked) {
		mule.checked = false;
		operationsRow_Scope.style.display = "none";
		lbpm.globals.handlerOperationClearOperationsRow_Scope();
		lbpm.globals.hidden_Communicate_Scope();
	}
	//勾选限定范围处理情况
	else {
		//联动流程的允许多级沟通控制和限制子级沟通范围显示隐藏控制
		mule.checked = true;
		lbpm.globals.setCommunicateScope(sel);
		operationsRow_Scope.style.display = "";

		var operationsTDTitle_Scope = document
			.getElementById("operationsTDTitle_Scope_"+id);
		var operationsTDContent_Scope = document
			.getElementById("operationsTDContent_Scope_"+id);
		// 范围包括沟通人员及 (限制子级沟通范围)
		operationsTDTitle_Scope.innerHTML = lbpm.constant.opt.CommunicateScopeLimitSub
			+ operationName
			+ lbpm.constant.opt.CommunicateScopeLimitScope;
		var htmlContent = "";
		var currentOrgIdsObj = document
			.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
		// alert(document.getElementById("currentCommunicateIds").value);
		var exceptValue = currentOrgIdsObj.value;
		if (document.getElementById("currentCommunicateIds")) {
			exceptValue = lbpm.globals.stringConcat(exceptValue, document
				.getElementById("currentCommunicateIds").value);

		}
		exceptValue = lbpm.globals.stringConcat(exceptValue, $("#" + idFields).val());

		var options = {
			mulSelect : true,
			idField : 'limitScopeHandlerIds_' + id,
			nameField : 'limitScopeHandlerNames_' + id,
			splitStr : ';',
			selectType : ORG_TYPE_POSTORPERSON,
			notNull : false,
			exceptValue : exceptValue.split(';'),
			text : lbpm.constant.SELECTORG
		};
		if(addressHtml!=''){
			options.onclick = addressHtml.replace(idFields,'limitScopeHandlerIds_' + id).replace(nameFields,'limitScopeHandlerNames_' + id);
		}
		htmlContent += lbpm.address.html_build(options);
		htmlContent += lbpm.constant.opt.CommunicateScopeIsNullNoLimit;
		operationsTDContent_Scope.innerHTML = htmlContent;
        $("#limitScopeHandlerIds_" + id).closest("div").css("width", "65%");
		$("#limitScopeHandlerIds_" + id).bind('change',function(){
			$("#communicateScopeHandlerIds").val(this.value);
			$("#communicateScopeHandlerNames").val($("#limitScopeHandlerNames_" + id).val());
		});
	}
}
//补签
function handlerAdditionSign(operationName,id){
	setNewAuditNoteDefaultUsageContent('handler_additionSign',id);
	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	operationAuditTDTitle.innerHTML = operationName + lbpm.constant.opt.AdditionSignPeople;
	var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
	var idFields = 'toOtherHandlerIds_' + id;
	var nameFields = 'toOtherHandlerNames_' + id;
	var options = {
		mulSelect : true,
		idField : idFields,
		nameField : nameFields,
		splitStr : ';',
		selectType : ORG_TYPE_PERSON|ORG_TYPE_POST,
		action : "lbpm.globals._checkAdditionSignRepeatPersonInPost",//检测岗位中是否包含有所选择的人
		notNull : true,
		exceptValue : currentOrgIdsObj.value.split(';'),
		text : lbpm.constant.SELECTORG
	};
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
			options.onclick = "Dialog_AddressList(true, '" + idFields + "', '" + nameFields + "', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", null)";

			autoPopAction = function(){
				Dialog_AddressList(true, idFields, nameFields, ';',defaultOptionBean,
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
				Dialog_Address(true, idFields, nameFields, ';',options.selectType,
					options.action, null, null,  options.notNull, null,
					options.text, options.exceptValue, deptLimit);
			};
		}
	} else {
		autoPopAction = function(){
			Dialog_Address(true, idFields, nameFields, ';',options.selectType,
				options.action, null, null,  options.notNull, null,
				options.text, options.exceptValue);
		};
	}

	var html = lbpm.address.html_build(options);
	options.action = lbpm.globals._checkAdditionSignRepeatPersonInPost;
	if (window.dojo) {
		require(['dojo/query', 'dojo/NodeList-html'], function(query) {
			query('#operationsTDContent').html(html, {parseContent: true});
			lbpm.globals.hiddenObject(operationAuditRow, false);
		});
	} else {
		html += GetAuditNoteNotifyType4Node(currentNodeObj, id);
		operationAuditTDContent.innerHTML = html;
		syncNotifyType(id);
		lbpm.globals.hiddenObject(operationAuditRow, false);
	}

	//联动流程审批中的操作项
	$("#toOtherHandlerIds_" + id).bind('change',function(){
		$("#toOtherHandlerIds").val(this.value);
		$("#toOtherHandlerNames").val($("#toOtherHandlerNames_" + id).val());
	});
	setTimeout(autoPopAction,100);
}

//加签
function handlerAssign(operationName, id){
	setNewAuditNoteDefaultUsageContent('handler_assign',id);
	var html = "";
	// 设置显示当前正在加签人员
	var relationInfoObj = getCurrAssignRelationInfo();
	var ids = "";
	var names = "";
	var idFields = "toAssigneeIds_" + id;
	var nameFields = "toAssigneeNames_" + id;
	if (relationInfoObj.length > 0) {
		for (var i = 0; i < relationInfoObj.length; i++) {
			ids += relationInfoObj[i].userId + ";";
			names += relationInfoObj[i].userName + ";";
		}
		if (ids) {
			ids = ids.substr(0, ids.lastIndexOf(";"));
		}
		if (names) {
			names = names.substr(0, names.lastIndexOf(";"));
		}
		html += "<input type='hidden' name='currentAssigneeIds_"+ id +"' value='"
			+ ids + "'/>";
		html += "<label>" + names + "</label><br/>";
	}

	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	operationAuditTDTitle.innerHTML = operationName + lbpm.constant.opt.Assignee;

	var operatorInfo = lbpm.globals.getOperationParameterJson("relationWorkitemId");
	var isAssignPassSkipChecked = lbpm.globals.getOperationParameterJson("isAssignPassSkipChecked");
	var currentOrgIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
	var exceptValueStr = _stringsConcat(currentOrgIdsObj.value, ids);

	var options = {
		mulSelect : true,
		idField : idFields,
		nameField : nameFields,
		splitStr : ';',
		selectType : ORG_TYPE_POSTORPERSON,
		action : "lbpm.globals._checkRepeatPersonInPost4Assign",//检测岗位中是否包含有所选择的人
		notNull : ids.length < 1, //如果已存在加签人员则当前加签人员不一定为必选的
		alertText : (lbpm.constant.opt.AssigneeIsNull + operationName + lbpm.constant.opt.Assignee),
		text : lbpm.constant.SELECTORG,
		exceptValue : exceptValueStr.split(';')
	};

	var autoPopAction = null;
	var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
	if(currentNodeObj.operationScope && currentNodeObj.operationScope["handler_assign"]){
		var operationScope = currentNodeObj.operationScope["handler_assign"];
		if(operationScope  == "custom"){
			var scopeType = currentNodeObj.operationScope["handler_assign"] == null?"":currentNodeObj.operationScope["handler_assign"];
			var nodeHandlerIds = currentNodeObj.handlerIds == null?"":currentNodeObj.handlerIds;
			var customHandlerSelectType = currentNodeObj.operationScope["handler_assign_customHandlerSelectType"] == null?"org":currentNodeObj.operationScope["handler_assign_customHandlerSelectType"];
			var handlerSelectType = currentNodeObj.handlerSelectType == null?"org":currentNodeObj.handlerSelectType;
			var customHandlerIds = currentNodeObj.operationScope["handler_assign_customIds"] == null?"":currentNodeObj.operationScope["handler_assign_customIds"];
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
				+ "&exceptValue=" + exceptValueStr;
			var searchBean = defaultOptionBean + "&keyword=!{keyword}";
			options.onclick = "Dialog_AddressList(true, '" + idFields + "', '" + nameFields + "', ';','"+defaultOptionBean+"',null, '"+searchBean+"', null,  "+options.notNull+", '"+options.alertText+"')";
			autoPopAction = function(){
				Dialog_AddressList(true, idFields, nameFields, ';',defaultOptionBean, options.action, searchBean, null,  options.notNull, options.alertText);
			};
		}else {
			var deptLimit = "";
			if(operationScope == "org"){
				deptLimit = "myOrg";
			}else if(operationScope == "dept"){
				deptLimit = "myDept";
			}
			options.deptLimit = deptLimit;
			autoPopAction = function(){
				Dialog_Address(true, idFields, nameFields, ';', options.selectType, options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue, deptLimit);
			};
		}
	} else {
		autoPopAction = function(){
			Dialog_Address(true, idFields, nameFields, ';', options.selectType, options.action, null, null, options.notNull, options.alertText,options.text, options.exceptValue);
		};
	}

	html += lbpm.address.html_build(options);
	options.action = lbpm.globals._checkRepeatPersonInPost4Assign;
	if(Lbpm_SettingInfo && Lbpm_SettingInfo['isMultiAssignEnabled']=="true"){
		html += "<br/><label style='margin-left: 5px;'>";
		html += "<input id='_multiAssign_" + id + "' type='checkbox' key='isMultiAssign_" + id + "'";
		if(Lbpm_SettingInfo['isMultiAssignDefault'] == "true"){
			html += " checked";
		}
		html += ">";
		html += lbpm.constant.opt.AssignAllowMulti + operationName;
		html += "</label><br/>";
	}
	// 被加签人才会显示加签人员通过后跳过我
	if(operatorInfo!="" && operatorInfo){
		html += "<label class='lui-lbpm-checkbox' style='margin-left: 5px;'>";
		html += "<input id='_assignPassSkip_" + id + "' type='checkbox' key='isAssignPassSkip_" + id + "'><span class='checkbox-label'>";
		html += operationName+lbpm.constant.opt.AssignPassSkip;
		html += "</span></label><br/>";
	}
	html += GetAuditNoteNotifyType4Node(currentNodeObj, id);
	operationAuditTDContent.innerHTML = html;
	syncNotifyType(id);

	lbpm.globals.hiddenObject(operationAuditRow, false);

	// 是否默认勾选加签人员通过后跳过我
	if(isAssignPassSkipChecked!="" && isAssignPassSkipChecked){
		var _assignPassSkip = document.getElementById("_assignPassSkip_"+id);
		_assignPassSkip.checked = true;
	}
	//联动流程审批中的操作项
	$("#toAssigneeIds_" + id).bind('change',function(){
		$("#toAssigneeIds").val(this.value);
		$("#toAssigneeNames").val($("#toAssigneeNames_" + id).val());
	});
	$("#_multiAssign_" + id).bind('change',function(){
		var targetChecked = this.checked;
		$("#_multiAssign").each(function(i){
			this.checked = targetChecked;
		});
	});
	$("#_assignPassSkip_" + id).bind('change',function(){
		var targetChecked = this.checked;
		$("#_assignPassSkip").each(function(i){
			this.checked = targetChecked;
		});
	});
	setTimeout(autoPopAction,100);
}

//收回加签
function handlerAssignCancel(operationName, id){
	lbpm.globals.getOperationParameterJson("relationWorkitemId:relations",true); // 加载后端数据
	var operationsRow = document.getElementById("operationAuditRow_" + id);
	var relationInfoObj = getCurrAssignRelationInfo();
	if (relationInfoObj.length > 0) {
		var operationsTDTitle = document.getElementById("operationAuditTDTitle_" + id);
		var operationsTDContent = document.getElementById("operationAuditTDContent_" + id);
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.Assignee;
		var html = [];
		for ( var i = 0; i < relationInfoObj.length; i++) {
			html.push('<label><input type="checkbox" name="WorkFlow_CancelAssignWorkitems_' + id + '" checked value="'
				+ relationInfoObj[i].itemId
				+ '">'
				+ relationInfoObj[i].userName + '</label>');
		}

		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		operationsTDContent.innerHTML = html.join('') + GetAuditNoteNotifyType4Node(currentNodeObj, id);
		lbpm.globals.hiddenObject(operationsRow, false);
		//联动流程审批中的操作项
		$("[name='WorkFlow_CancelAssignWorkitems_" + id + "']").bind('change',function(){
			var targetChecked = this.checked;
			var targetValue = this.value;
			$("[name='WorkFlow_CancelAssignWorkitems']").each(function(i){
				if (this.value == targetValue) {
					this.checked = targetChecked;
				}
			});
		});
		//通知方式
		syncNotifyType(id);
	} else {
		lbpm.globals.hiddenObject(operationsRow, true);
	}
}

//通过加签
function handlerAssignPass(operationName, id){
	setNewAuditNoteDefaultUsageContent('handler_assignPass',id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	if(operationAuditTDContent){
		operationAuditTDTitle.innerHTML = "";
		operationAuditTDContent.innerHTML = "";
	}
}

//退回加签
function handlerAssignRefuse(operationName, id){
	setNewAuditNoteDefaultUsageContent('handler_assignRefuse',id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	if(operationAuditTDContent){
		operationAuditTDTitle.innerHTML = "";
		operationAuditTDContent.innerHTML = "";
	}
}

//超级驳回
function handlerSuperRefuse(operationName,id){
	setNewAuditNoteDefaultUsageContent('handler_superRefuse',id);

	var operationAuditRow = document.getElementById("operationAuditRow_" + id);
	var operationAuditTDTitle = document.getElementById("operationAuditTDTitle_" + id);
	var operationAuditTDContent = document.getElementById("operationAuditTDContent_" + id);
	operationAuditTDTitle.innerHTML = XForm_NewAudit_Note.handlerOperationTypeRefuse.replace("{refuse}", operationName);
	var html = '<select name="newAuditNoteJumpToNodeIdSelectObj_'+ id + '" alertText="" key="jumpToNodeId_' + id + '" onchange=superJumpToNodeItemsChanged(this,"'+id+'");></select>';
	//在驳回时，增加选择节点通知方式
	html+="<label id='refuseSuperNotifyTypeDivId_" + id + "' style='display:none'></label><br>";
	html+="<div id='triageHandler_" + id + "' style='margin-top:4px;display: inline-block'></div>";
	lbpm.rejectOptionsEnabled = true; // 驳回选项开关是否开启标识
	if (Lbpm_SettingInfo && (Lbpm_SettingInfo["isShowRefuseOptonal"] === "false")) {
		lbpm.rejectOptionsEnabled = false;
	}
	// 驳回选项开关开启时才生成驳回选项html
	if (lbpm.rejectOptionsEnabled) {
		html += superRefusePassedToThisNodeLabel(operationName,id);
	}
	// 驳回后流经的子流程重新流转选项html
	var isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode();
	if (isPassedSubprocessNode) {
		html += '<label id="isRecoverPassedProcessLabel_' + id + '"  class="lui-lbpm-checkbox" style="margin-left: 8px;';
		// if (operatorInfo.refusePassedToThisNode == "true") {
		// 	html += 'display:none;';
		// }
		html += '"><input type="checkbox" id="isRecoverPassedProcess_' + id + '" value="true" alertText="" key="isRecoverPassedProcess_">';
		html += ('<span class="checkbox-label">'+lbpm.constant.opt.abandonSubprocess+'</span>').replace("{refuse}", operationName);
		html += '</label>';
	}
	operationAuditTDContent.innerHTML = html;
    if(lbpm.rejectOptionsEnabled && lbpm.flowcharts.rejectReturn == 'true'){
        $('#superRefusePassedToSequenceFlowNode_' + id).prop('checked', false);
        $('#superRefusePassedToThisNodeOnNode_' + id).prop('checked', false);
        $('#superRefusePassedToTheNode_' + id).prop('checked', false);
        $('#superRefusePassedToThisNode_' + id).prop('checked', true);
    }
	lbpm.globals.hiddenObject(operationAuditRow, false);

	// 增加驳回节点重复过滤
	var currNodeInfo = lbpm.globals.getCurrentNodeObj();
	var currNodeId = currNodeInfo.id;

	var url = Com_Parameter.ContextPath+"sys/lbpm/engine/jsonp.jsp";
	var pjson = {"s_bean": "lbpmRefuseRuleDataBean", "processId": $("[name='sysWfBusinessForm.fdProcessId']").val(), "nodeId": currNodeId, "_d": new Date().toString(),"refuseType":"superRefuse"};
	var passNodeArray = [];
	$.ajaxSettings.async = false;
	$.getJSON(url, pjson, function(json) {
		passNodeArray = json;
	});
	var check_passNodeArray = [];
	//获取分之内节点
	var check_pjson = {"s_bean": "lbpmRefuseRuleDataBean", "processId": $("[name='sysWfBusinessForm.fdProcessId']").val(), "nodeId": currNodeId, "_d": new Date().toString()};
	$.getJSON(url, check_pjson, function(json) {
		check_passNodeArray = json;
	});
	$.ajaxSettings.async = true;
	var nodeHandlerNameArray = [];
	nodeHandlerNameArray = _getPassNodeHandlerName(passNodeArray, true);

	var jumpToNodeIdSelectObj = $("select[name='newAuditNoteJumpToNodeIdSelectObj_" + id + "']")[0];

	for(var i = 0; i < passNodeArray.length; i++){
		var nodeInfo = lbpm.nodes[passNodeArray[i]];

		var	option = document.createElement("option");
		var langNodeName = WorkFlow_getLangLabel(nodeInfo.name,nodeInfo["langs"],"nodeName");
		var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeInfo.id + ".");
		var itemShowStr = identifier + langNodeName;
		itemShowStr += nodeHandlerNameArray[nodeInfo.id];
		option.appendChild(document.createTextNode(itemShowStr));
		option.value=passNodeArray[i];
		jumpToNodeIdSelectObj.appendChild(option);
	}

	if(jumpToNodeIdSelectObj.options.length > 0){
		jumpToNodeIdSelectObj.selectedIndex = 0;
		//新、旧审批操作的驳回到要跟随流程引擎的“驳回时默认驳回到上一节点”变化
		if (Lbpm_SettingInfo && Lbpm_SettingInfo["isRefuseToPrevNodeDefault"] == "true") {
			jumpToNodeIdSelectObj.selectedIndex = jumpToNodeIdSelectObj.options.length - 1;
		} else {
			jumpToNodeIdSelectObj.selectedIndex = 0;
		}
		// 在驳回时，增加默认的选择节点通知方式
		var defaultToNodeId = jumpToNodeIdSelectObj.value;
        setRefuseSuperNodeNotifyType(id, defaultToNodeId);
		//lbpm.globals.setRefuseSuperNodeNotifyType(defaultToNodeId);

		// <----------以下为驳回返回选项相关的逻辑处理，只有在驳回选项开关开启的情况下才会执行---------->
		if (lbpm.rejectOptionsEnabled) {
			// 驳回返回本人
			var refusePassedToThisNode = document.getElementById("superRefusePassedToThisNode_" + id);
			var refusePassedToThisNodeLabel = document.getElementById("superRefusePassedToThisNodeLabel_" + id);
			// 驳回返回本节点，add by wubing date:2016-07-29
			var refusePassedToThisNodeOnNode = document.getElementById("superRefusePassedToThisNodeOnNode_" + id);
			var refusePassedToThisNodeOnNodeLabel = document.getElementById("superRefusePassedToThisNodeOnNodeLabel_" + id);
			// 驳回返回指定节点
			var refusePassedToTheNode = document.getElementById("superRefusePassedToTheNode_" + id);
			var refusePassedToTheNodeLabel = document.getElementById("superRefusePassedToTheNodeLabel_" + id);

			//驳回的节点是否在分支内
			lbpm.globals._isJumpNodeInJoin = false;
			//默认判断是否在分之内
			var isInJoin = false;
			for(var i=0;i<check_passNodeArray.length;i++){
				if(check_passNodeArray[i]==jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value){
					isInJoin = true;
					break;
				}
			}
			lbpm.globals._isJumpNodeInJoin = isInJoin;

			/** 驳回选项的显示规则：1、一旦勾中了其中一个，则隐藏掉其它的选项；2、驳回返回本人和驳回返回本节点只能在分支内显示 */
			if(isInJoin == false){
				// 分支外，则隐藏驳回本人和驳回本节点的开关
				if(refusePassedToThisNode)
					refusePassedToThisNode.checked = false;
				if(refusePassedToThisNodeLabel){
					refusePassedToThisNodeLabel.style.display = "none";
					$(refusePassedToThisNodeLabel).next().css("display","none");
				}

				if(refusePassedToThisNodeOnNode)
					refusePassedToThisNodeOnNode.checked = false;
				if(refusePassedToThisNodeOnNodeLabel){
					refusePassedToThisNodeOnNodeLabel.style.display = "none";
					$(refusePassedToThisNodeOnNodeLabel).next().css("display","none");
				}
				if(refusePassedToTheNodeLabel)
					refusePassedToTheNodeLabel.style.display = "";
			}
			// 构建超级驳回后可返回到的节点选项（For驳回返回指定节点）
			buildSuperReturnBackToTheNodeSelectOption(jumpToNodeIdSelectObj,id);

			// 监听切换驳回节点
			Com_AddEventListener(jumpToNodeIdSelectObj, "change", function(){
				//默认判断是否在分之内
				var isInJoin = false;
				for(var i=0;i<check_passNodeArray.length;i++){
					if(check_passNodeArray[i]==jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value){
						isInJoin = true;
						break;
					}
				}
				lbpm.globals._isJumpNodeInJoin = isInJoin;
				/** 切换时驳回选项的相关规则：
				 *  1、若切换之前勾选了驳回返回指定节点，则切换的时候自动去掉勾选；
				 *  2、若切换之前勾选了是驳回返回本人和驳回放回本节点，则在切换后的节点（分支内）有驳回返回本人和驳回放回本节点选项时，继承切换前的勾选；
				 */
				if(isInJoin == false){
					// 分支外，则去掉勾选且隐藏驳回本人和驳回本节点的开关
					if(refusePassedToThisNode)
						refusePassedToThisNode.checked = false;
					if(refusePassedToThisNodeLabel){
						refusePassedToThisNodeLabel.style.display = "none";
						$(refusePassedToThisNodeLabel).next().css("display","none");
					}
					if(refusePassedToThisNodeOnNode)
						refusePassedToThisNodeOnNode.checked = false;
					if(refusePassedToThisNodeOnNodeLabel){
						refusePassedToThisNodeOnNodeLabel.style.display = "none";
						$(refusePassedToThisNodeOnNodeLabel).next().css("display","none");
					}
					if(refusePassedToTheNodeLabel)
						refusePassedToTheNodeLabel.style.display = "";
				}else{
					// 分支内，则根据情况控制返回本人和返回本节点的开关显示
					if (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked != true) {
						refusePassedToThisNodeLabel.style.display = "";
						$(refusePassedToThisNodeLabel).next().css("display","block");
					}
					if (refusePassedToThisNode && refusePassedToThisNode.checked != true) {
						refusePassedToThisNodeOnNodeLabel.style.display = "";
						$(refusePassedToThisNodeOnNodeLabel).next().css("display","block");
					}
				}
				// 切换驳回节点的时候，自动取消驳回返回指定节点的勾选
				if (refusePassedToTheNode && refusePassedToTheNode.checked == true) {
					refusePassedToTheNode.checked = false;
					refusePassedToTheNode.disabled = false;
					lbpm.globals.hiddenObject($("select[name='returnBackToNodeIdSelectObj_" + id + "']")[0], true);
				}

				if (isPassedSubprocessNode) {
					if (!((refusePassedToThisNode && refusePassedToThisNode.checked) || (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) || (refusePassedToTheNode && refusePassedToTheNode.checked))) {
						// 没有驳回返回选项被勾选时，驳回后流经的子流程重新流转选项可以显示出来
						document.getElementById("isRecoverPassedProcessLabel_" + id).style.display = "";
					}
				}
			});

			// 驳回后流经的子流程重新流转选项
			if (isPassedSubprocessNode) {
				var isRecoverPassedSubprocess = document.getElementById("isRecoverPassedProcess_" + id);
				var isRecoverPassedSubprocessLabel = document.getElementById("isRecoverPassedProcessLabel_" + id);
				if (refusePassedToThisNode.checked || refusePassedToThisNodeOnNode.checked || refusePassedToTheNode.checked) {
					isRecoverPassedSubprocessLabel.style.display = "none";
				}
				Com_AddEventListener(refusePassedToThisNode, "click", function(){
					if (refusePassedToThisNode && refusePassedToThisNode.checked) {
						isRecoverPassedSubprocess.checked = false;
					}
					isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNode && refusePassedToThisNode.checked) ? "none" : "";
				});
				Com_AddEventListener(refusePassedToThisNodeOnNode, "click", function(){
					if (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) {
						isRecoverPassedSubprocess.checked = false;
					}
					isRecoverPassedSubprocessLabel.style.display = (refusePassedToThisNodeOnNode && refusePassedToThisNodeOnNode.checked) ? "none" : "";
				});
				Com_AddEventListener(refusePassedToTheNode, "click", function(){
					if (refusePassedToTheNode && refusePassedToTheNode.checked) {
						isRecoverPassedSubprocess.checked = false;
					}
					isRecoverPassedSubprocessLabel.style.display = (refusePassedToTheNode && refusePassedToTheNode.checked) ? "none" : "";
				});
			}
		}
		// <----------END---------->
	}

	var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
	//开关开启才会去执行驳回具体审批人
	if(lbpm.settingInfo.isRefuseSelectPeople=="true"){
		if(processDefine&&processDefine.refuseSelectPeople&&processDefine.refuseSelectPeople=="true"){
			if(jumpToNodeIdSelectObj.selectedIndex!==undefined&&jumpToNodeIdSelectObj.selectedIndex>-1){
				var nodeData = lbpm.nodes[jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value];
				//会审才会显示具体人员
				if(nodeData.processType&&nodeData.processType=="2"){
					var selectTrialHtml=buildSuperRefuseSelectTrialStaff(jumpToNodeIdSelectObj.options[jumpToNodeIdSelectObj.selectedIndex].value);
					$("#triageHandler_" + id).html(selectTrialHtml);
					$("xformflag[flagtype='xform_fSelect']").fSelect();
				}else{
					$("#triageHandler_"+ id).html("");
				}
			}
		}
	}

	if (jumpToNodeIdSelectObj.options.length == 0) {
		operationAuditTDContent.innerHTML = XForm_NewAudit_Note.noRefuseNode + '<input type="hidden" alertText="'+ XForm_NewAudit_Note.noRefuseNode +'" key="jumpToNodeId">'.replace("{refuse}", operationName);
	}
	//联动流程审批中的操作项
	var $select = $("#operationAuditTDContent_" + id).find("select[name*='newAuditNoteJumpToNodeIdSelectObj_']");
	$select.bind("change",function (){
		$("#operationsTDContent > select > option[value='" + this.value +  "']").prop("selected",true);
	})
	//超级驳回的节点通过后，返回指定节点下拉框联动控制
	var $select = $("#operationAuditTDContent_" + id).find("select[name*='returnBackToNodeIdSelectObj_']");
	$select.bind("change",function (){
		$("#operationsTDContent > select[name='returnBackToNodeIdSelectObj'] > option[value='" + this.value +  "']").prop("selected",true);
	})

}

function setRefuseSuperNodeNotifyType(controlId, nodeId){
    var refuseNotifyTypeDivIdEl = document.getElementById("refuseSuperNotifyTypeDivId_" + controlId);
    refuseNotifyTypeDivIdEl.innerHTML=GetAuditNoteNotifyType4NodeHTML(controlId, nodeId);
    syncNotifyType(controlId);
    refuseNotifyTypeDivIdEl.style.display="";
}

function buildSuperReturnBackToTheNodeSelectOption(jumpToNodeIdSelectObj,id){
	if (jumpToNodeIdSelectObj == null) {
		jumpToNodeIdSelectObj = $("select[name='newAuditNoteJumpToNodeIdSelectObj_" + id +"']")[0];
	}

	var jumpToNodeIdSelectOptions = jumpToNodeIdSelectObj.options;
	var jumpToNodeId = jumpToNodeIdSelectOptions[jumpToNodeIdSelectObj.selectedIndex].value;
	var returnNodes = getReturnNodes(lbpm.globals.getNodeObj(jumpToNodeId));

	$("select[name='returnBackToNodeIdSelectObj_" + id + "']").empty();
	var returnBackToNodeIdSelectObj = $("select[name='returnBackToNodeIdSelectObj_"+ id +"]")[0];
	if(!returnBackToNodeIdSelectObj)
		return;
	for(var j = 0; j < jumpToNodeIdSelectOptions.length; j++){
		var nodeInfo = lbpm.globals.getNodeObj(jumpToNodeIdSelectOptions[j].value);
		if (!containNode(returnNodes, nodeInfo)){
			continue;
		}
		var	option = document.createElement("option");
		option.title = jumpToNodeIdSelectOptions[j].text;
		var itemShowStr = option.title.length > 65 ? option.title.substr(0, 65) + '...' : option.title;
		option.appendChild(document.createTextNode(itemShowStr));
		option.value=jumpToNodeIdSelectOptions[j].value;
		returnBackToNodeIdSelectObj.appendChild(option);
	}
	if (containNode(returnNodes, lbpm.globals.getNodeObj(lbpm.nowNodeId))) {
		var	option = document.createElement("option");
		option.title = lbpm.constant.opt.thisNode;
		option.appendChild(document.createTextNode(option.title));
		option.value=lbpm.nowNodeId;
		returnBackToNodeIdSelectObj.appendChild(option);
	}

	if (returnBackToNodeIdSelectObj.options.length == 0) {
		var	option = document.createElement("option");
		option.appendChild(document.createTextNode(lbpm.constant.opt.noReturnBackNode));
		option.value=null;
		returnBackToNodeIdSelectObj.appendChild(option);
	}
}

function buildReturnBackToTheNodeSelectOption(jumpToNodeIdSelectObj){
	if (jumpToNodeIdSelectObj == null) {
		jumpToNodeIdSelectObj = $("select[name='jumpToNodeIdSelectObj']")[0];
	}

	var jumpToNodeIdSelectOptions = jumpToNodeIdSelectObj.options;
	var jumpToNodeId = jumpToNodeIdSelectOptions[jumpToNodeIdSelectObj.selectedIndex].value;
	var returnNodes = getReturnNodes(lbpm.globals.getNodeObj(jumpToNodeId));

	$("select[name='newAuditNoteJumpToNodeIdSelectObj_']").empty();
	var returnBackToNodeIdSelectObj = $("select[name='newAuditNoteJumpToNodeIdSelectObj_']")[0];
	if(!returnBackToNodeIdSelectObj)
		return;
	for(var j = 0; j < jumpToNodeIdSelectOptions.length; j++){
		var nodeInfo = lbpm.globals.getNodeObj(jumpToNodeIdSelectOptions[j].value);
		if (!containNode(returnNodes, nodeInfo)){
			continue;
		}
		var	option = document.createElement("option");
		option.title = jumpToNodeIdSelectOptions[j].text;
		var itemShowStr = option.title.length > 65 ? option.title.substr(0, 65) + '...' : option.title;
		option.appendChild(document.createTextNode(itemShowStr));
		option.value=jumpToNodeIdSelectOptions[j].value;
		returnBackToNodeIdSelectObj.appendChild(option);
	}

	var	option = document.createElement("option");
	option.title = lbpm.constant.opt.thisNode;
	option.appendChild(document.createTextNode(option.title));
	option.value=lbpm.nowNodeId;
	returnBackToNodeIdSelectObj.appendChild(option);
}

function superJumpToNodeItemsChanged(el,id){
	if (lbpm.rejectOptionsEnabled && $("[key='superRefusePassedToTheNode_" + id + "']")[0].checked) {
		buildSuperReturnBackToNodeIdSelectOption(id);
	}
	var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());

	//开关开启才会去执行驳回具体审批人
	if(lbpm.settingInfo.isRefuseSelectPeople=="true"){
		if(processDefine&&processDefine.refuseSelectPeople&&processDefine.refuseSelectPeople=="true"){
			if(arguments.length>0){
				var op=arguments[0];
				var nodeData = lbpm.nodes[op.value];
				//会审才会显示具体人员
				if(nodeData.processType&&nodeData.processType=="2"){
					var selectTrialHtml=buildSuperRefuseSelectTrialStaff(op.value);
					var operationsTDContent = document.getElementById("operationAuditTDContent_"+id);
					$("#triageHandler_"+id).html(selectTrialHtml);
					//联动控制流程审批中驳回会审节点具体审批人下拉复选框控件显示
					$("#_triageHandler").html(_buildSelectTrialStaff(op.value));
					$("xformflag[flagtype='xform_fSelect']").fSelect();
				}else{
					$("#triageHandler_"+id).html("");
					$("#_triageHandler").html("");
				}
			}
		}
	}
}

function jumpToNodeItemsChanged(src){
    var id = $(src).attr("controlId");
    var $refusePassedToTheNode = $("[key='refusePassedToTheNode_" + id +"']");
	if ($refusePassedToTheNode.length>0&& $refusePassedToTheNode.prop("checked")) {
        $refusePassedToTheNode.prop("checked", false);
		lbpm.globals.hiddenObject(document.getElementById("refusePassedToThisNodeLabel_" + id), false);
		lbpm.globals.hiddenObject(document.getElementById("refusePassedToThisNodeOnNodeLabel_" + id), false);
		lbpm.globals.hiddenObject($("select[name='_returnBackToNodeIdSelectObj_" + id +"']")[0], true);
	}
	var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());

	//开关开启才会去执行驳回具体审批人
	if(lbpm.settingInfo.isRefuseSelectPeople=="true"){
		if(processDefine&&processDefine.refuseSelectPeople&&processDefine.refuseSelectPeople=="true"){
			if(arguments.length>0){
				var op=arguments[0];
				var nodeData = lbpm.nodes[op.value];
				//会审才会显示具体人员
				if(nodeData.processType&&nodeData.processType=="2"){
					var selectTrialHtml=buildRefuseSelectTrialStaff(op.value);
					$("#triageHandler-").html(selectTrialHtml);
					//联动控制流程审批中驳回会审节点具体审批人下拉复选框控件显示
					$("#triageHandler").html(_buildSelectTrialStaff(op.value));
					$("xformflag[flagtype='xform_fSelect']").fSelect();
				}else{
					$("#triageHandler-").html("");
					$("#triageHandler").html("");
				}
			}
		}
	}
}

function _buildSelectTrialStaff(nodeId){
	var handlerArray=getPassNodeHandlerObj(nodeId);
	var nodeData = lbpm.nodes[nodeId];

	var trialStaffPeopleHtmlStart="<xformflag flagid='fd_trialStaffPeople' id='_xform_trialStaffPeople' property='trialStaffPeople' flagtype='xform_fSelect' _xform_type='fSelect'>"+
		"<div class='select_div_box xform_Select' fd_type='fSelect' style='display: inline-block; width: auto; text-align: left;'>"+
		"<div id='div_trialStaffPeople' style='display:none'>"+
		"<input name='trialStaffPeople' type='hidden' value='' key='lbpmHandlerTriage'>"+
		"</div>"+
		"<div class='fs-wrap multiple'>"+
		"<div class='fs-label-wrap'>"+
		"<div class='fs-label' >=="+lbpm.constant.opt.refusePeople+"==</div>"+
		"<span class='fs-arrow'></span>"+
		"</div>"+
		"<div class='fs-dropdown'>"+
		"<div class='fs-search'>"+
		"<input type='text' autocomplete='off' placeholder='"+lbpm.constant.opt.refusePeopleSearch+"'>"+
		"<i class='fs-search-icon-del'></i>"+
		"</div>";

	var optionHtml="";
	if(handlerArray.length>0){
		for(var i=0;i<handlerArray.length;i++){
			optionHtml+="<div class='fs-options'>"+
				"<div data-value='"+handlerArray[i].handlerId+"' class='fs-option' data-index='0'>"+
				"<span class='fs-checkbox'><i></i></span>"+
				"<div class='fs-option-label'>"+handlerArray[i].handlerName+"</div>"+
				"<input type='hidden' name='_trialStaffPeople' value='"+handlerArray[i].handlerId+"'>"+
				"</div>"+
				"</div>";
		}
	}

	var trialStaffPeopleHtmlEnd="</div>"+
		"</div>"+
		"</div></xformflag>";

	var selectHtml=trialStaffPeopleHtmlStart+optionHtml+trialStaffPeopleHtmlEnd;
	return selectHtml;
}

function buildSuperRefuseSelectTrialStaff(nodeId){
	var handlerArray=getPassNodeHandlerObj(nodeId);
	var nodeData = lbpm.nodes[nodeId];

	var trialStaffPeopleHtmlStart="<xformflag flagid='fd_trialStaffPeople' id='super_xform_trialStaffPeople' property='trialStaffPeople' flagtype='xform_fSelect' _xform_type='fSelect'>"+
		"<div class='select_div_box xform_Select' id='super_ref_option' fd_type='fSelect' style='display: inline-block; width: auto; text-align: left;'>"+
		"<div id='superdiv_trialStaffPeople' style='display:none'>"+
		"<input name='trialStaffPeople' type='hidden' value='' key='lbpmHandlerTriage'>"+
		"</div>"+
		"<div class='fs-wrap multiple'>"+
		"<div class='fs-label-wrap'>"+
		"<div class='fs-label' >=="+lbpm.constant.opt.refusePeople+"==</div>"+
		"<span class='fs-arrow'></span>"+
		"</div>"+
		"<div class='fs-dropdown'>"+
		"<div class='fs-search'>"+
		"<input type='text' autocomplete='off' placeholder='"+lbpm.constant.opt.refusePeopleSearch+"'>"+
		"<i class='fs-search-icon-del'></i>"+
		"</div>";

	var optionHtml="";
	if(handlerArray.length>0){
		for(var i=0;i<handlerArray.length;i++){
			optionHtml+="<div class='fs-options'>"+
				"<div data-value='"+handlerArray[i].handlerId+"' class='fs-option' data-index='0' onclick='peopleOptionSelect(this)'>"+
				"<span class='fs-checkbox'><i></i></span>"+
				"<div class='fs-option-label'>"+handlerArray[i].handlerName+"</div>"+
				"<input type='hidden' name='_trialStaffPeople' value='"+handlerArray[i].handlerId+"'>"+
				"</div>"+
				"</div>";
		}
	}

	var trialStaffPeopleHtmlEnd="</div>"+
		"</div>"+
		"</div></xformflag>";

	var selectHtml=trialStaffPeopleHtmlStart+optionHtml+trialStaffPeopleHtmlEnd;
	return selectHtml;
}
//控制驳回会审节点返回具体人员的方法
function peopleOptionSelect(el){
	var data = el.dataset.value;
	var isSelected = el.classList.value;
	if(isSelected == "fs-option"){
		var selected = [];
		var options = [];
		var elems = [];
		$("#_xform_trialStaffPeople").find(".fs-option").each(function(i,option){
			var dataList = $(option).attr("data-value");
			if(dataList != undefined){
				if(data == dataList){
					$(option).toggleClass('selected');
					options.push(option);
					elems.push($(option).find("input")[0]);
					if ($(option).hasClass("selected")){
						selected.push($(option).attr('data-value'));
					}

				}
			}
		});
		var $xformflag = $wrap.closest("xformflag");
		var name = $xformflag.attr("id").substring("_xform_".length);
		var $valField = $xformflag.find('[type="text"][name="'+ name +'"]');
		$valField.val(selected.join(";"));
		$xformflag.fSelect('reloadDropdownLabel');
		__xformDispatch(selected.join(";"),elems);
		//触发值改变事件，兼容数据填充
		$valField.trigger("change");
	}else{
		var selected = [];
		var options = [];
		var elems = [];
		$("#_xform_trialStaffPeople").find(".fs-option").each(function(i,option){
			var dataList = $(option).attr("data-value");
			if(dataList != undefined){
				if(data == dataList){
					$(option).toggleClass('selected');
					options.push(option);
					elems.push($(option).find("input")[0]);
					if ($(option).hasClass("selected")){
						selected.push($(option).attr('data-value'));
					}

				}
			}
		});
		var $xformflag = $wrap.closest("xformflag");
		var name = $xformflag.attr("id").substring("_xform_".length);
		var $valField = $xformflag.find('[type="text"][name="'+ name +'"]');
		$valField.val(selected.join(";"));
		$xformflag.fSelect('reloadDropdownLabel');
		__xformDispatch(selected.join(";"),elems);
		//触发值改变事件，兼容数据填充
		$valField.trigger("change");
	}
}

function superRefusePassedToThisNodeLabel(operationName,id){
	var extAttrs=lbpm.nodes[lbpm.nowNodeId].extAttributes;
	var refuseTypes = [];
	var isDisable = false;
	var index = 0;
	for(var i = 0;extAttrs && i < extAttrs.length;i++){
		if(extAttrs[i].name == 'refuse_types'){
			refuseTypes=extAttrs[i].value.split(";");
			break;
		}
	}
	//只有一条时要加上只读属性
	if(refuseTypes && refuseTypes.length==1){
		isDisable = true;
	}
	var html = '';

	// zl
	if(showOption('refusePassedToSequenceFlowNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += 'id="superRefusePassedToSequenceFlowNodeLabel_' + id + '" class="lui-lbpm-checkbox" onclick= handleSuperRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.sequenceFlow.replace("{refuse}", operationName) + '"><input type="checkbox" id="superRefusePassedToSequenceFlowNode_' + id + '" value="true" alertText="" key="superRefusePassedToSequenceFlowNode_' + id + '"';
		if(index==0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.sequenceFlowTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}
	if(showOption('refusePassedToThisNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="superRefusePassedToThisNodeLabel_' + id + '" class="lui-lbpm-checkbox" onclick=handleSuperRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.returnBackMe.replace("{refuse}", operationName) +'"><input type="checkbox" id="superRefusePassedToThisNode_' + id + '" value="true" alertText="" key="superRefusePassedToThisNode_' + id + '"';
		if(index==0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html += '>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackMeTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}

	//增加驳回返回本节点，add by wubing date:2016-07-29
	if(showOption('refusePassedToThisNodeOnNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="superRefusePassedToThisNodeOnNodeLabel_' + id + '" class="lui-lbpm-checkbox" onclick=handleSuperRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.returnBack.replace("{refuse}", operationName) + '"';
		html += '><input type="checkbox" id="superRefusePassedToThisNodeOnNode_' + id + '" value="true" alertText="" key="superRefusePassedToThisNodeOnNode_' + id + '"';
		if(index == 0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html+='>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackTitle.replace("{refuse}", operationName)+'</span></label></br>';
		index += 1;
	}


	//增加驳回返回指定节点 add by linbb
	if(showOption('refusePassedToTheNodeLabel',refuseTypes)){
		if(lbpm.approveType == "right"){
			if(index==0){
				html += '';
			}
			html += '<label style="" ';
		}else{
			html += '<label ';
		}
		html += ' id="superRefusePassedToTheNodeLabel_' + id + '" class="lui-lbpm-checkbox" onclick=handleSuperRefuseOption(this,"'+id+'") title="' + lbpm.constant.opt.returnBackTheNode.replace("{refuse}", operationName) + '"';
		html += '><input type="checkbox" id="superRefusePassedToTheNode_' + id + '" value="true" alertText="" key="superRefusePassedToTheNode_' + id + '"';
		if(index == 0){
			html += " checked='true'";
		}
		if(isDisable){
			html += " disabled='disabled'";
		}
		html+='>';
		html += '<span class="checkbox-label">'+lbpm.constant.opt.returnBackTheNodeTitle.replace("{refuse}", operationName)+'</span></label>';
		html += '<select name="returnBackToNodeIdSelectObj_' + id + '" alertText="" key="returnBackToNodeId_' + id + '" style="max-width:200px;margin-left:4px;'+(index > 0 ? 'display:none':'')+'"></select>';
		index += 1;
	}


	return html;
}
function showOption(param,ar){
	if(!ar || ar.length == 0)
		return true;
	for(var i = 0;i < ar.length;i++){
		if(param == ar[i])
			return true;
	}
	return false;
}
//取消沟通
function handlerCancelCommunicate(operationName,id){
	lbpm.globals.getOperationParameterJson("relationWorkitemId:relationScope:relations:isMutiCommunicate",true); // 加载后端数据
	var operationsRow = document.getElementById("operationAuditRow_" + id);
	var relationInfoObj = lbpm.globals.getCurrRelationInfo();
	if (relationInfoObj.length > 0) {
		var operationsTDTitle = document.getElementById("operationAuditTDTitle_" + id);
		var operationsTDContent = document.getElementById("operationAuditTDContent_" + id);
		operationsTDTitle.innerHTML = operationName + lbpm.constant.opt.CommunicatePeople;
		var html = [];
		for ( var i = 0; i < relationInfoObj.length; i++) {
			html.push('<label><input type="checkbox" name="WorkFlow_CelRelationWorkitems_' + id + '" checked value="'
				+ relationInfoObj[i].itemId
				+ '">'
				+ relationInfoObj[i].userName + '</label>');
		}

		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		html +="<br>"+GetAuditNoteNotifyType4Node(currentNodeObj, id);
		operationsTDContent.innerHTML = html;
        syncNotifyType(id);
		lbpm.globals.hiddenObject(operationsRow, false);

		//联动流程审批中的操作项
		$("[name='WorkFlow_CelRelationWorkitems_" + id + "']").bind('change',function(){
			var targetChecked = this.checked;
			var targetValue = this.value;
			$("[name='WorkFlow_CelRelationWorkitems']").each(function(i){
				if (this.value == targetValue) {
					this.checked = targetChecked;
				}
			});
		});
		//待办勾选复选框联动
		var $checkbox = $("#operationAuditTDContent_" + id).find("input[value*='todo']");
		$checkbox.bind("click",function (){
			var notifyCheck = $("#operationAuditTDContent_"+id+" > label[class='lui-lbpm-checkbox'] > input[value='todo']").is(":checked");
			if(notifyCheck){
				$("#operationsTDContent > label[class='lui-lbpm-checkbox'] > input[value='todo']").prop("checked",true);
			}else{
				$("#operationsTDContent > label[class='lui-lbpm-checkbox'] > input[value='todo']").prop("checked",false);
			}
		})
	} else {
		lbpm.globals.hiddenObject(operationsRow, true);
	}
}

/**
 * 设置默认审批意见信息
 */
setNewAuditNoteDefaultUsageContent = function(operationType,id,usageContent, simpleUsage) {
	var defalutUsage = "";
	defalutUsage = lbpm.globals.getOperationDefaultUsage(operationType);
	usageContent = usageContent || document.getElementsByName("textareaNote_" + id)[0]
	// 审批意见为空时才设置默认审批意见
	if (usageContent && !usageContent.value.replace(/\s*/ig, '')) {
		usageContent.value = defalutUsage;
	}
	simpleUsage = simpleUsage || document.getElementsByName("fdSimpleUsageContent")[0];
	if (simpleUsage && !simpleUsage.value.replace(/\s*/ig, '')) {
		simpleUsage.value = defalutUsage;
	}
}

/**
 * 清除默认审批意见信息。
 */
newAuditNoteclearDefaultUsageContent = function(operationType,id, usageContent, simpleUsage) {
	var defalutUsage = "";
	defalutUsage = lbpm.globals.getOperationDefaultUsage(operationType);
	usageContent = usageContent || document.getElementsByName("textareaNote_" + id)[0];
	if (usageContent && defalutUsage && usageContent.value.replace(/\s*/ig, '') == defalutUsage.replace(/\s*/ig, '')) {
		usageContent.value = "";
	}
	simpleUsage = simpleUsage || document.getElementsByName("fdSimpleUsageContent")[0];
	if (simpleUsage && defalutUsage && simpleUsage.value.replace(/\s*/ig, '') == defalutUsage.replace(/\s*/ig, '')) {
		simpleUsage.value = "";
	}
}

function _stringsConcat(str1, str2, splitChar) {
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


//---------------------即席子流程@即将流向---------------------
getNextAdHocSubFlowRouteInfoNew = function() {
	lbpm.canHideNextNodeTr = false;
	if (lbpm.adHocSubFlowNodeInfo == null || lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId] == null) {
		initAdHocSubFlowInfoNew();
	}
	lbpm.adHocRoutes = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId];
	// 构建即席子流程的即将流向选项内容
	var html = "";

	var nextAdHocSubRoutes = [];
	// 构建内置子节点的route选择项
	for (var index = 0;index<lbpm.adHocRoutes.length; index++) {
		if (lbpm.adHocRouteId != null && lbpm.adHocRouteId == lbpm.adHocRoutes[index].startNodeId) {
			continue;
		}
		var subNode = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId].adHocSubNodes[lbpm.adHocRoutes[index].startNodeId];
		var nodeObj = subNode.data;
		nextAdHocSubRoutes.push(nodeObj);
	}
	// 构建即席子流程节点流出的下一步节点的route选择项
	nextAdHocSubRoutes.push(lbpm.nodes[lbpm.nowAdHocSubFlowNodeId].endLines[0].endNode);

	var onlyOneSelect = true;
	if (nextAdHocSubRoutes.length > 1) {
		onlyOneSelect = false;
	}

	for (index = 0;index<nextAdHocSubRoutes.length; index++) {
		html += generateAdHocSubNodeHtml(nextAdHocSubRoutes[index],onlyOneSelect,index);
	}

	return html;
};


function generateAdHocSubNodeHtml(nodeObj,onlyOneSelect,index){
	var html = "";
	html += "<div class='lbpmNextRouteInfoRow'><label style='line-height:26px;' id='nextNodeName[" + index + "]'>";

	html += "<input " +(onlyOneSelect==true ? "style='display:none' checked=true " : ((lbpm.nodes[lbpm.nowAdHocSubFlowNodeId].defaultBranch == nodeObj.id) ? " checked=true " : ""))
		+ "type='radio' adHocSubFlowNodeId='" + lbpm.nowAdHocSubFlowNodeId + "'name='nextAdHocRouteId' key='nextAdHocRouteId' index='" + index + "' value='" + nodeObj.id
		+ "' onclick='if (lbpm.globals.flowChartLoaded != true) {lbpm.flow_chart_load_Frame();}'>";

	var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
	langNodeName=langNodeName.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
	var identifier =  (_lbpmIsRemoveNodeIdentifier() || _lbpmIsHideAllNodeIdentifier()) ? "" : (nodeObj.id + ".");
	html += "<b>" + identifier + langNodeName + "</b></label>";
	var handlerIds, handlerNames, isFormulaType = (nodeObj.handlerSelectType == lbpm.constant.ADDRESS_SELECT_FORMULA || nodeObj.handlerSelectType == lbpm.constant.ADDRESS_SELECT_RULE);
	handlerIds = nodeObj.handlerIds==null?"":nodeObj.handlerIds;
	handlerNames = nodeObj.handlerNames==null?"":
		(isFormulaType?lbpm.workitem.constant.COMMONHANDLERISFORMULA:nodeObj.handlerNames);
	var hiddenIdObj = "<input type='hidden' name='handlerIds[" + index + "]' value='" + handlerIds + "' isFormula='" + isFormulaType.toString() +"' />";
	html += hiddenIdObj;
	var hiddenNameObj = "<input type='hidden' name='handlerNames[" + index + "]' value='" + Com_HtmlEscape(handlerNames) + "' />";

	var dataNextNodeHandler;
	var nextNodeHandlerNames4View="";
	if(nodeObj.handlerSelectType){
		if (nodeObj.handlerSelectType=="formula") {
			dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,false);
		} else if (nodeObj.handlerSelectType=="matrix") {
			dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,false);
		} else if (nodeObj.handlerSelectType=="rule") {
			dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id,handlerIds,true,false);
		} else {
			dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,false);
		}
		for(var j=0;j<dataNextNodeHandler.length;j++){
			if(nextNodeHandlerNames4View==""){
				nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
			}else{
				nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
			}
		}
	}
	if(nextNodeHandlerNames4View == "" && nodeObj.handlerIds != null) {
		nextNodeHandlerNames4View = lbpm.constant.COMMONNODEHANDLERORGNULL;
	}
	if(nextNodeHandlerNames4View != "")
		html += "(";
	html += hiddenNameObj;
	html += "<label id='handlerShowNames[" + index + "]' class='handlerNamesLabel'";
	html += " nodeId='" + nodeObj.id + "'>" + (nextNodeHandlerNames4View.replace(/;/g, '; ')) + "</label>";
	if(nextNodeHandlerNames4View != "")
		html += ")";
	html += "&nbsp;&nbsp;"+lbpm.globals.getNotifyType4NodeHTML(nodeObj.id);
	html += "</div>";
	return html;
};

//初始化即席子流程节点对象信息（数据来源于节点配置时的adHocSubFlowData)
initAdHocSubFlowInfoNew = function() {
	if (lbpm.adHocSubFlowNodeInfo == null) {
		//即席子流程节点的信息对象，以即席子流程节点的ID作为key来存放
		//对应的即席子流程的各环节信息(包含环节首节点ID以及环节内的子节点配置信息,默认以环节首节点ID作为环节的标识)
		//以及对应的即席子流程节点的全部子节点的配置信息
		lbpm.adHocSubFlowNodeInfo = new Object();
	}
	var adHocRoutes = new Array();
	var adHocSubNodes = new Object();
	var adHocSubLines = new Object();
	lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId] = adHocRoutes;
	lbpm.adHocRoutes = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId];
	var adHocSubFlowNode = lbpm.nodes[lbpm.nowAdHocSubFlowNodeId];
	var data = adHocSubFlowNode["adHocSubFlowData"];
	if(data){
		var adHocSubFlowData = WorkFlow_LoadXMLData(data);
		for(var i=0; i<adHocSubFlowData.nodes.length; i++){
			var nodeObj=adHocSubFlowData.nodes[i];
			adHocSubNodes[nodeObj.id] = {};
			adHocSubNodes[nodeObj.id].id = nodeObj.id;
			adHocSubNodes[nodeObj.id].data = nodeObj;
			adHocSubNodes[nodeObj.id].startLines=[];
			adHocSubNodes[nodeObj.id].endLines=[];
		}
		lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId].adHocSubNodes = adHocSubNodes;
		for(i=0; i<adHocSubFlowData.lines.length; i++){
			var lineObj=adHocSubFlowData.lines[i];
			adHocSubLines[lineObj.id] = {};
			adHocSubLines[lineObj.id].id = lineObj.id;
			adHocSubLines[lineObj.id].data = lineObj;
			adHocSubLines[lineObj.id].startNode = adHocSubNodes[lineObj.startNodeId];
			adHocSubLines[lineObj.id].endNode = adHocSubNodes[lineObj.endNodeId];
			(adHocSubNodes[lineObj.startNodeId].endLines).push(adHocSubLines[lineObj.id]);
			(adHocSubNodes[lineObj.endNodeId].startLines).push(adHocSubLines[lineObj.id]);
		}
		var routeNextAdHocSubNode = function(nodeObj,adHocRoute){
			adHocRoute.subNodes.push(nodeObj);
			for (var j=0;j<nodeObj.endLines.length;j++) {
				adHocRoute.subLines.push(nodeObj.endLines[j]);
				routeNextAdHocSubNode(nodeObj.endLines[j].endNode,adHocRoute);
			}
		};
		// 分组(adHocRoute)
		$.each(adHocSubNodes, function(index, nodeObj) {
			// 没有流入的节点就是每个adHocRoute的首节点，从首节点的流出往下遍历就能找出每个adHocRoute的全部子节点
			if (nodeObj.startLines.length == 0) {
				var adHocRoute = new Array();
				adHocRoute.subNodes = new Array();
				adHocRoute.subLines = new Array();
				adHocRoute.startNodeId = nodeObj.data.id;
				routeNextAdHocSubNode(nodeObj,adHocRoute);
				adHocRoutes.push(adHocRoute);
			}
		});
	}
};