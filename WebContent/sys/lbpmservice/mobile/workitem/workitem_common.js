define(["dojo/_base/lang","dijit/registry","dojo/topic","sys/lbpmservice/mobile/common/syslbpmprocess","mui/dialog/Tip",
	"mui/util","dojo/request"],function(lang, registry, topic,syslbpmprocess, tip, util, request){
	var workitem={};

	var _getLangLabel=function(defLabel,langsArr,lang){
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

	//创建操作栏
	var createOperationMethodGroups=function(){
		var operMethodsGroup = $("#operationMethodsGroup");
		if (operMethodsGroup.length == 0) {
			return;
		}
		var vtype = operMethodsGroup.attr('view-type');
		if (vtype == 'input') {
			return;
		}
		var operatorInfo = syslbpmprocess.analysisProcessorInfoToObject();
/*
		var nodeOprLangs={};
		if(_isLangSuportEnabled){
			var currNodeInfo = lbpm.globals.getCurrentNodeObj();
			if(typeof currNodeInfo.langs!="undefined"){
				var langsJson = $.parseJSON(currNodeInfo.langs);
				nodeOprLangs = langsJson.nodeOpr||{};
			}
		}
*/
		var nodeOprLangs={};
		if(_isLangSuportEnabled){
			var currNodeInfo = lbpm.globals.getCurrentNodeObj();
			if(currNodeInfo.operations && currNodeInfo.operations.refId != null){//操作引用
				var data = new KMSSData();
				data.AddBeanData("getOperationsByDefinitionService&fdId="+currNodeInfo.operations.refId);
				data = data.GetHashMapArray();
				if (data.length > 0) {
					var operations = data[0].operation;
					operations = $.parseJSON(operations);
					for(var i=0; i<operations.length; i++){
						if(typeof operations[i].langs!="undefined"){
							nodeOprLangs[operations[i].type+"-"+operations[i].name]=$.parseJSON(operations[i].langs);
						}
					}
				}
			}else{//操作自定义
				if(typeof currNodeInfo.langs!="undefined"){
					if(currNodeInfo.langs!=""){
						var langsJson = $.parseJSON(currNodeInfo.langs);
						nodeOprLangs = langsJson.nodeOpr||{};
					}
				}
			}
		}

		var view = vtype == 'select' ? (function(oprValue, oprName, oprItem, operInfo) {
			var optionHtml = "<option value='" + oprValue + ":" + oprName + "' ";
			if(oprValue == lbpm.defaultOperationType){
				optionHtml += "selected='true' ";
			}
			optionHtml += ">" + oprName + "</option>";
			return optionHtml;
		}) : (function(oprValue, oprName, oprItem, operInfo) {
			var radioButtonHTML = "<nobr><label style='padding-right:5px;'>"
					+"<input type='radio' alertText='' key='operationType' name='oprGroup' value='" + oprValue + ":" + oprName + "' ";
			if(oprValue == lbpm.defaultOperationType){
				radioButtonHTML += "checked='true' ";
			}
			radioButtonHTML += " onclick='lbpm.globals.clickOperation(this);'>" + oprName + "</label></nobr>";
			radioButtonHTML += " "; // 避免不能换行
			return radioButtonHTML;
		});
		var notify = vtype == 'select' ? (function() {
			$("#operationMethodsGroup").change(function() {
				lbpm.globals.clickOperation(this);
			});
			$("#operationMethodsGroup  option").each(function(){
				var item=this;
				var opt = lbpm.operations[item.value.split(':')[0]];
				if (opt && opt.isPassType) {
					$(item).attr('selected', true);
					return false;
				}
			});
			lbpm.globals.clickOperation($("#operationMethodsGroup")[0].value);
		}) : (function() {
			var radio = $("#operationMethodsGroup input[name='oprGroup']");
			if (radio.length == 1) {
				radio.attr('disabled', false);
				radio.attr('checked', true);
				radio.attr('onclick', 'return;');
			}
			if (radio.length == 0) {
				return;
			}
			radio.each(function() {
				var thisRadio = this;
				if (this.checked) {
					lbpm.globals.clickOperation(thisRadio);
					return false;
				}
				var opt = lbpm.operations[thisRadio.value.split(':')[0]];
				if (opt && opt.isPassType) {
					$(thisRadio).attr('checked', true);
					lbpm.globals.clickOperation(thisRadio);
					return false;
				}
			});
		});
		var html = '';
		$.each(operatorInfo.operations, function(i, item) {
			var langs = nodeOprLangs[item.id];
			var oprName = item.name;
			if(typeof langs=="undefined"){
				langs = nodeOprLangs[item.id+"-"+oprName];
			}
			if(typeof langs!="undefined"){
				oprName = _getLangLabel(oprName,langs,_userLang);
			}
			html += view(item.id, Com_HtmlEscape(oprName), item, operatorInfo);
		});
		operMethodsGroup.html(html);
		notify();
	};
	
	//流程暂存
	workitem.saveDraftAction = lbpm.globals.saveDraftAction=function(btn) {
		var operatorInfo = lbpm.nowProcessorInfoObj;
		if(!operatorInfo) {
			return;
		}
		var fdUsageContent=lbpm.operations.getfdUsageContent();
		if(!fdUsageContent) {
			return;
		}
		//意见长度检查
		if(fdUsageContent.value != "") {
			var maxLength = 4000;
			if (fdUsageContent.value.length > maxLength) {
				//新增审批意见长度判断
				var msg = lbpm.constant.ERRORMAXLENGTH;
				var title = lbpm.constant.CREATEDRAFTCOMMONUSAGES;
				msg = msg.replace(/\{0\}/, title).replace(/\{1\}/, maxLength);
				//alert(msg);
				return ;
			}
		} else {
			//alert(lbpm.workitem.constant.COMMONUSAGECONTENTNOTNULL);
			return ;
		}
		if (btn)
			btn.disabled = true;
		try {
			var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=saveDraft&ajax=true';
			var kmssData = new KMSSData();
			var obj = {};
			obj["taskId"] = syslbpmprocess.getOperationParameterJson("id");
			obj["processId"] = $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
			obj["auditNote"] = fdUsageContent.value;
			obj["auditNoteFdId"] = $("input[name='sysWfBusinessForm.fdAuditNoteFdId']").val();
			if(lbpm.noticeHandlers){
				//处理格式上传
				var noticeHandlers = "";
				for(var i=0; i<lbpm.noticeHandlers.length; i++){
					var handler = lbpm.noticeHandlers[i];
					noticeHandlers += handler.id + ";";
					noticeHandlers += handler.name + ";";
					noticeHandlers += handler.startPos + ";";
					if(i == lbpm.noticeHandlers.length-1){
						noticeHandlers += handler.endPos;
					}else{
						noticeHandlers += handler.endPos + "|";
					}
				}
				obj["noticeHandlers"] = noticeHandlers;
			}
			kmssData.AddHashMap(obj);
			kmssData.SendToUrl(url, function(http_request) {
				if (btn) {
					btn.disabled = false;
					alert(http_request.responseText);
				}
			});
		} catch(e) {
			throw e;
			if (btn)
				btn.disabled = false;
		}
	};
	
	//设置即将流向处理人
	workitem.setHandlerInfoes = lbpm.globals.setHandlerInfoes=function(handlerSelectType){
		var handlerIdsObj;
		var handlerNamesObj;
		var handlerShowNames;
	
		if(!lbpm.nowNodeId){
			return;
		}
		handlerIdsObj = document.getElementsByName("handlerIds")[0];
		if(!handlerIdsObj){
			return;
		}
	
		handlerNamesObj = document.getElementsByName("handlerNames")[0];
		handlerShowNames = document.getElementById("handlerShowNames");
		handlerShowNames.innerHTML = "("+handlerNamesObj.value+")";
	
		// 取当前节点的下一个节点ID
		var currentNodeObj=lbpm.globals.getCurrentNodeObj();
		var nextNodeObj=lbpm.globals.getNextNodeObj(currentNodeObj.id);
		nextNodeObj.handlerIds = handlerIdsObj.value;
		nextNodeObj.handlerNames = handlerNamesObj.value;
	   
		if(handlerSelectType!=null){
			nextNodeObj.handlerSelectType =handlerSelectType;
		}
	
		//返回json对象
		var rtnNodesMapJSON= new Array();
		rtnNodesMapJSON.push({
			id:nextNodeObj.id,
			handlerIds:nextNodeObj.handlerIds,
			handlerNames:nextNodeObj.handlerNames
		});
		var param={};
		param.nodeInfos=rtnNodesMapJSON;
		lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,param);
	};
	/**
	 * 功能：判断当前用户是否有修改下一节点处理人的权限（即将流向栏出现：从组织架构选择　从备选列表中选择　使用公式定义器）
	 * @param nextNodeId 下一个节点的编号如N1,N2
	 */
	workitem.checkModifyNextNodeAuthorization = lbpm.globals.checkModifyNextNodeAuthorization=function(nextNodeId){
		if(lbpm.nowNodeId == null){
			return false;
		}
		var currentNodeObj = lbpm.globals.getCurrentNodeObj();
		if(currentNodeObj.canModifyHandlerNodeIds != null && currentNodeObj.canModifyHandlerNodeIds != ""){
			var index = (currentNodeObj.canModifyHandlerNodeIds + ";").indexOf(nextNodeId + ";");
			if(index != -1){
				return true;
			}
		}
		if(currentNodeObj.mustModifyHandlerNodeIds != null && currentNodeObj.mustModifyHandlerNodeIds != ""){
			var index = (currentNodeObj.mustModifyHandlerNodeIds + ";").indexOf(nextNodeId + ";");
			if(index != -1){
				return true;
			}
		}
		return false;
	};
	
	 /**
	  * 功能：展示当前节点的帮助信息
	  */
	workitem.getCurrentNodeDescription = lbpm.globals.getCurrentNodeDescription=function(){
	 	var currentNodeObj = lbpm.globals.getCurrentNodeObj();
	 	if(currentNodeObj && currentNodeObj.description){
	 		var description = currentNodeObj.description;

			var description = WorkFlow_getLangLabel(currentNodeObj.description,currentNodeObj["langs"],"nodeDesc");

			var re=/\[url=([^\]]+)\]([^\[]*)\[\/url\]/ig;
			description = description.replace(re,function($0,$1,$2){
					if($2){
						return '<span><a href='+$1+' target=_blank>'+$2+'</a></span>';
					}
					else{
						//没有输入链接描述时 链接地址即为描述
					  return '<span><a href='+$1+' target=_blank>'+$1+'</a></span>';
					}
				});
			//description="<pre>"+description+"</pre>";
			description=description.replace(/\r\n/ig,"<br/>").replace(/\n/ig,"<br/>");

			//description=description.replace(/(<pre>)|(<\/pre>)/ig,"");
			if(description.indexOf("<a href=") != -1 && description.indexOf("script") != -1) {
				description = description.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
			}
	 		$("#currentNodeDescription").html(description);
	 		$("#nodeDescriptionRow").show();
	 		$("#lbpmOtherInfo").show();
	 		lbpm.isShowLbpmOtherInfo = true;
	 	} else {
	 		$("#currentNodeDescription").html("");
	 		$("#nodeDescriptionRow").hide();
	 		if(!lbpm.isShowLbpmOtherInfo){
	 			$("#lbpmOtherInfo").hide();
	 			lbpm.isShowLbpmOtherInfo = false;
	 		}
	 	}
	 };
	 /**
	  * 功能：当前用户有多个事务时，切换事务函数
	  * @param selectObj 当一个用户有多个工作项，处理事务一栏的多个事务的下拉框对象
	  * @param inInit 标识是否是初始加载，区别于用户的手工切换事务
	  */
	 workitem.operationItemsChanged = lbpm.globals.operationItemsChanged=function(selectObj,isInit){
		if(selectObj)	lbpm.nowProcessorInfoObj=lbpm.processorInfoObj[selectObj.selectedIndex];
	 	//var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
		//获取当前节点ID(指当前人所处的节点，当不是处理人，lbpm.nowNodeId不存在)
		var operatorInfo = lbpm.nowProcessorInfoObj;
		lbpm.nowNodeId=operatorInfo.nodeId;
		lbpm.globals.handlerOperationClearOperationsRow();
	 	createOperationMethodGroups();
	 	//初始化意见
		var saveedArr=WorkFlow_LoadXMLData($("input[name='sysWfBusinessForm.fdCurNodeSavedXML']")[0].value);
		if(saveedArr.tasks && saveedArr.tasks.length>0){
			var tasksArr=$.grep(saveedArr.tasks,function(n,i){
				 return n.id==operatorInfo.id;
			});
			if(tasksArr.length>0){
				var fdUsageContent = registry.byId('fdUsageContent');		
				if(fdUsageContent){
					fdUsageContent.set("value",lbpm.globals.htmlUnEscape(tasksArr[0].data));
					//加载@处理人信息
					lbpm.globals.initNoticeHandlersDataAction();
				}
			}
		}
		if(!isInit){
			lbpm.events.fireListener(lbpm.constant.EVENT_CHANGEWORKITEM,null);
			lbpm.events.fireListener("updateShowLimitTimeOperation",null);
		}
	 };
	 
	 /**
	  * 更新是否显示限时
	  */
	 workitem.updateShowLimitTimeOperation = lbpm.globals.updateShowLimitTimeOperation = function(){
		 if(!lbpm.nowProcessorInfoObj){
			 return;
		 }
		 //var taskId = lbpm.nowProcessorInfoObj.id;
		 var nodeFactId = lbpm.nowProcessorInfoObj.nodeId;
		 var processId = lbpm.modelId;
		 var type = lbpm.nowProcessorInfoObj.type;
		 var beanName = "lbpmProcessLimitTimeOperationLogService&processId="+processId+"&nodeFactId="+nodeFactId+"&type="+type;
		 var rtnVal = new KMSSData().AddBeanData(beanName).GetHashMapArray()[0];
		 if(rtnVal && rtnVal.status){
			 var status = parseInt(rtnVal.status);
			 var title;
			 if(status == 0){//超时
				 title = Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.timeoutRow");
				 lbpm.isTimeoutTotal = "true";
			 }else if(status == 1){
				 title = Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.limitTimeRow");
				 lbpm.isTimeoutTotal = "false";
			 }else{
				// $("#lbpmLimitTimeMethodTable").parents(".actionView").eq(0).hide();
				 $("#lbpmLimitTimeMethodTable").hide();
				 $(".limitOptionsSplitLine").removeClass("optionsSplitLine");
				 $(".limitOptionsSplitLine").hide();
				 lbpm.limitTotalTime = null;
			 }
			 if(status == 0 || status == 1){
				 var detail = '<span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.day+'</span> '+Data_GetResourceString("sys-lbpmservice:FlowChartObject.Lang.Node.day")+' <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.hour+'</span> : <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.minute+'</span> : <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.second+'</span>';
				 //$("#lbpmLimitTimeMethodTable").parents(".actionView").eq(0).show();
				 $("#lbpmLimitTimeMethodTable").show();
				 $(".limitOptionsSplitLine").show();
				 $(".limitOptionsSplitLine").addClass("optionsSplitLine");
				 $("#limitTimeMethodRowTitle .titleNode").html(title);
				 $("#limitTimeMethodRow .detailNode").html(detail);
				 lbpm.limitTotalTime = rtnVal.total ? rtnVal.total : null;
			 }
		 }else{
			// $("#lbpmLimitTimeMethodTable").parents(".actionView").eq(0).hide();
			 $("#lbpmLimitTimeMethodTable").hide();
			 $(".limitOptionsSplitLine").removeClass("optionsSplitLine");
			 $(".limitOptionsSplitLine").hide();
		 }
	 }
	
	 /**
	  * @param obj 操作栏的操作的单选框对象
	  * 功能：操作单击事件
	  */
	 workitem.clickOperation = lbpm.globals.clickOperation=function(obj){
	 	var valueAndName = (typeof(obj)=="string"?obj:obj.value).split(":");
	 	if (!valueAndName) return;
	 	var oprValue = valueAndName[0];
	 	var oldOpt = lbpm.currentOperationType;
	 	if (oldOpt) {
	 		var oldClass = lbpm.operations[oldOpt];
	 		if (oldClass && oldClass.blur)
	 			oldClass.blur();
	 	}
	 	var oprClass=lbpm.operations[oprValue];
	 	lbpm.globals.handlerOperationClearOperationsRow();
	 	if(oprClass){
	 		oprClass.click(valueAndName[1], obj);
	 		//隐藏即将流向
	 		var operationsRow = document.getElementById("operationsRow");
	 		if(oprClass.isHideOperationsRow && lbpm.canHideNextNodeTr && Lbpm_SettingInfo.isHideOperationsRow == "true"){
	 			lbpm.globals.hiddenObject(operationsRow, true);
	 			var actionTop = $(".actionTop").offset();
	 			var optionsSplitLine = $(".optionsSplitLine").eq(0).offset();
	 			if(actionTop && optionsSplitLine && optionsSplitLine.top-actionTop.top<=20){
	 				$(".optionsSplitLine").eq(0).hide();
	 				$(".actionCenter").hide();
	 			}
	 		}else if(operationsRow && operationsRow.style.display != "none"){
	 			$(".optionsSplitLine").eq(0).show();
	 			$(operationsRow).closest("div.actionCenter").show();
	 		}
	 	}	
	 	// 设置当前操作是哪个
		lbpm.currentOperationType=oprValue;
		lbpm.events.fireListener(lbpm.constant.EVENT_CHANGEOPERATION,lbpm.currentOperationType);
	};
	
	 /*
	  * @param obj 对象、也可以是字符串
	  * @param key 如果此参数为null ,取obj对象的key属性
	  * @param root 根JSON对象
	  * @param saveObj 把解析出来的json字符保存的对象，默认为sysWfBusinessForm.fdParameterJson
	  * 设置工作项参数的JSON字符串
	  */
	workitem.setOperationParameterJson = lbpm.globals.setOperationParameterJson=function(obj,key,root,saveObj){
		 if(obj == null) {
			 return;
		 }
		 var jsonObj, objValue;
		 if(saveObj){
			 if(typeof(saveObj) == "string") {
				 if (saveObj == '') {
					 jsonObj = null;
				 } else {
					 jsonObj = $.parseJSON(saveObj);
				 }
			 }
			 else {
				 if (saveObj == null || saveObj.value == '') {
					 jsonObj = null;
				 } else {
					 jsonObj = $.parseJSON(saveObj.value);
				 }
			 }
		 } else {
			 saveObj = $("input[name='sysWfBusinessForm.fdParameterJson']")[0];
			 if (saveObj == null || saveObj.value == '') {
				 jsonObj = null;
			 } else {
				 jsonObj = $.parseJSON(saveObj.value);
			 }
		 }
		 
		 if(!jsonObj) 
			 jsonObj = {};
		 if(obj==null){
			 objValue=null;
		 } else if(typeof(obj)=="object"){
			 var qobj = $(obj);
			 key = key || (qobj.attr('key') ? qobj.attr('key') : qobj.attr('name'));
			 if(obj.type == "checkbox"){
				 objValue = obj.checked;
			 }else if(obj.type == "select-one"){
				 if (obj.options.length > 0) {
					 objValue = obj.options[obj.selectedIndex].value;
				 } else {
					 objValue = '';
				 }
			 } else{
				 objValue = obj.value;
			 }
		 }else{
			 objValue=obj;
		 }
		 
		 if(key == null){
			 return;
		 }
	
		if(root){
			var scriptOut="if(!jsonObj."+root+") jsonObj."+root+"=new Object();jsonObj."+root+"."+key+"=objValue;";
			(new Function("jsonObj","objValue",scriptOut))(jsonObj,objValue);
		}	
		else{
			var scriptOut="jsonObj."+key+"=objValue;";
			(new Function("jsonObj","objValue",scriptOut))(jsonObj,objValue);
		}
		if(typeof(saveObj)=="string") 
			saveObj=lbpm.globals.objectToJSONString(jsonObj);
		else
			saveObj.value=lbpm.globals.objectToJSONString(jsonObj);
	 };
	 
	 //获取当前处理人提交身份
	 workitem.getRolesSelectObj = lbpm.globals.getRolesSelectObj=function(){
	 	var handlerId = "";
		var defaultRole = function() {
	 		var handlerIdentityIdsObj = document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
	 		var rolesIdsArray = handlerIdentityIdsObj.value.split(";");
	 		return rolesIdsArray[0];
		};
		var rolesSelectObj = registry.byId('rolesSelectObj');
		if (!rolesSelectObj) {
			return defaultRole();
		}
		handlerId = rolesSelectObj.get('value');
		if (handlerId != null && handlerId != '') {
			return handlerId;
		}
		return defaultRole();
	};
	workitem.validateMustSignYourSuggestion = lbpm.globals.validateMustSignYourSuggestion = function(noShowAlert) {
		var flag = false;
		lbpm.events.fireListener(lbpm.constant.EVENT_validateMustSignYourSuggestion, null, function(result){
			if(result){
				flag = true;
			}
		});
		if(!flag) {
			if(!noShowAlert){
				tip["warn"]({text:lbpm.constant.opt.MustSignYourSuggestion});
				//alert(lbpm.constant.opt.MustSignYourSuggestion);
			}
			return false;
		}
		return true;
	};
	
	/*
	 * 根据组织架构的配置获取处理人角色的名称，组织架构的配置主要是控制部门的路径展示
	 * 暂时用于构建提交人身份
	 */
	workitem.getHandlerRoleInfoNamesByOrgConfig = lbpm.globals.getHandlerRoleInfoNamesByOrgConfig = function(handlerRoleInfoIds){
		if(!handlerRoleInfoIds){
			return null;
		}
		var result;
		var url = util.formatUrl('/sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=getHandlerRoleInfoNamesByOrgConfig');
		request.post(url, {
			sync:true,
			data : {
				"handlerRoleInfoIds":handlerRoleInfoIds	
			}
		}).then(function(data){
			if(data.indexOf("<error>") == -1){
				result = data;
			}
		});
		return result;
	}

	//是否为最后一个处理人
	workitem.isLastHandler = lbpm.globals.isLastHandler = function(){
//		if(lbpm.isLastHandler == true)
//			return true;
		//从后台获取标识
		var url = Com_Parameter.ContextPath + 'sys/lbpm/engine/jsonp.jsp';
		var pjson = {
			's_bean' : 'lbpmIsLastHandlerService',
			'processId' : lbpm.modelId,
			'nodeFactId' : lbpm.nowNodeId,
			'_d' : new Date().toString()
		};
		request.post(url,
			{data:pjson,handleAs:'json',sync:true}).then(
			function(json){
				//成功后回调
				if(json && json.isLastHandler && json.isLastHandler == true)
					lbpm.isLastHandler = true;
				else
					lbpm.isLastHandler = false;
			},function(error){
				lbpm.isLastHandler = false;
			});
		return lbpm.isLastHandler;
	};
	
	workitem.init = function(){
		lbpm.events.addListener(lbpm.constant.EVENT_validateMustSignYourSuggestion,function() {
			var fdContentObj=lbpm.operations.getfdUsageContent();
			if(fdContentObj && lang.trim(fdContentObj.value) != ""){
				return true;
			}
			return false;
		});
	};
	return workitem;
});