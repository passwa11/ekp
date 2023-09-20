define(["dojo/store/Memory", "dojo/ready", "dijit/registry", "dojo/request", 
         "dojox/mobile/viewRegistry", "dojo/query", "dojo/dom-style",
         "mui/dialog/Dialog", "dojo/_base/array", "dojo/topic", "dojo/touch","mui/i18n/i18n!sys-lbpmservice","dojo/on",
         "dojo/parser","dojo/dom-construct","dojo/_base/array"], 
		function(Memory, ready, registry, request, viewRegistry, query, domStyle, Dialog, array, topic,
				touch,msg,on,parser,domConstruct,array) {
	//初使化常用审批语
	var commonUsage={}; 
	
	var initialCommonUsages = function() {
		var kmssData = new KMSSData();
		kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesInfo");
		kmssData.GetHashMapArray(function(data){
			var names = data && data.data.length>0 && data.data[0].usagesInfo ? decodeURIComponent(data.data[0].usagesInfo) : null;
			var usageContents = [];
			if (names != null && names != "") {
				usageContents = names.split("\n");
			}
			initialCommonUsageObj("commonUsages",
				usageContents);
		});
		lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = "true";
		var kmssData2 = new KMSSData();
		kmssData2.AddBeanData("lbpmUsageTarget&type=getUsagesIsAppend");
		kmssData2.GetHashMapArray(function(data){
			var isAppend = data && data.data.length>0 && data.data[0].isAppend ? data.data[0].isAppend : null;
			if (isAppend != null && isAppend != "") {
				lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = isAppend;
			}
		});
	};
	
	var getNewestCommonUsages = function(){
		var usageContents = [];
		var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=getUsagesInfo';
		request.get(url, {handleAs:'text',sync:true}).then(function(responseText) {
			var names = responseText ? decodeURIComponent(responseText) : null;
			if (names != null && names != "") {
				usageContents = names.split("\n");
			}
			window.currentUsageContents = usageContents;
		});
		return usageContents;
	};
	
	var getUsageContents = function(usageContents) {
		return array.map(usageContents, function(usageContent) {
			while (usageContent.indexOf("nbsp;") != -1) {
				usageContent = usageContent.replace("&nbsp;", " ");
			}
			//处理移动端dojo单引问题
			usageContent = usageContent.replace(/\'/g,"\\\'").replace(/\"/g, "&quot;");
			return {text: usageContent, value: usageContent};
		});
	};
	
	var temp = '<input type="checkbox" data-dojo-type="mui/form/CheckBox" name="_select_box_commonUsageObjName" value="!{value}" data-dojo-props="mul:false,text:\'!{text}\'">';

	var buildItemHtml = function(props) {
		return temp.replace(
				'!{text}', props.text).replace(
				'!{value}', props.value);
	};
	
	var  buildContentHtml = function(usageContents) {
		var ucs = getUsageContents(usageContents);
		var html;
		if (ucs.length == 0) {
			html = "<p class='commonUsageNoneElement'>" + msg['mui.operation.commonUsage.none'] + "</p>";
		}else{
			html = array.map(ucs, function(props) {
				return buildItemHtml(props);
			});
			html = html.join("");
		}
		html = "<div class='muiFormSelectElement commonUsageSlectElement'>" + html + "</div>";
		//获取当前视图id
		var view = viewRegistry.getEnclosingView(query("#commonUsages")[0]);
		var viewId=null;
		if(!view){
			if(window.backToViewId){
				viewId = window.backToViewId;
			}
		}else{
			var pView = viewRegistry.getParentView(view);
			if(!pView && window.backToViewId){
				//同级别的主文档view
				viewId = window.backToViewId;
			}else{
				view = pView || view;
				viewId = view.id;
			}
		}
		//构建按钮区域
		var buttons = [];
		buttons.push({id:"addUsageButton",label:msg['mui.commonUsage.add'],icon1:"mui mui-plus",backTo:viewId,viewId:'commonUsageCreateView'});
		buttons.push({id:"manageUsageButton",label:msg['mui.commonUsage.manage'],icon1:"mui mui-file-rar",backTo:viewId,viewId:"commonUsageManageView"});
		buttons = JSON.stringify(buttons);
		html += "<ul fixed='bottom' class='muiViewBottom' data-dojo-type='sys/lbpmservice/mobile/workitem/CommonUsageTabbar' data-dojo-props='buttons:"+buttons+"'></ul>";
		return html;
	};
	
	var initialCommonUsageObj = function(commonUsageObjName, usageContents) {
		var dialog = null, html = buildContentHtml(usageContents);
		query("#" + commonUsageObjName).on("touchend", function() {
			setTimeout(function(){
				dialog = Dialog.element({
//					title : msg['mui.operation.commonUsage'],
					element : html,
					showClass: 'muiDialogSelect muiFormSelect commonUsageDialog',
					position:'bottom',
					'scrollable' : true,
					'parseable' : true,
					callback: function() {
						dialog = null;
					},
					onDrawed:function(evt) {
						var contentHeight = document.documentElement.clientHeight*0.8;
						if(evt.privateHeight){
							contentHeight=evt.privateHeight
						}
						//减去头部高度
						if(evt.divNode){
							contentHeight = contentHeight - evt.divNode.offsetHeight;
						}
						//减去按钮栏高度
						if(evt.buttonsNode){
							contentHeight = contentHeight - evt.buttonsNode.offsetHeight;
						}
						domStyle.set(evt.scrollViewNode, {
							'max-height' : contentHeight + 'px',
							"overflow-x":"hidden"
						});

						topic.subscribe("/workitem/commonUsage/hideDialog",function(){
							if(dialog){
								domStyle.set(dialog.domNode,{
									"display":"none"
								})
							}
						});
						topic.subscribe("/workitem/commonUsage/showDialog",function(){
							if(dialog){
								//更新弹窗的内容
								var currentUsageContents = getNewestCommonUsages();
								html = buildContentHtml(currentUsageContents || [])
								//先销毁原先的组件
								array.forEach(dialog.htmlWdgts,
									function(wdt) {
										if (wdt && wdt.destroy) {
											wdt.destroy();
										}
									});
								//构建新的内容
								dialog.element = html;
								domConstruct.empty(dialog.contentNode);
								var _container = dialog.contentNode;
								if (dialog.scrollable) {
									_container = domConstruct.create('div', {
										'className':'muiDialogScrollView',
										'data-dojo-type' : 'mui/NativeView'
									}, dialog.contentNode);
									isParse = true;
									dialog.scrollViewNode = _container;
								}
								domConstruct.place(html, _container);
								parser.parse(dialog.contentNode).then(function(widgetList) {
									dialog.htmlWdgts = widgetList;
									dialog.loaded();
								});
								domStyle.set(dialog.domNode,{
									"display":""
								})
								domStyle.set(dialog.scrollViewNode, {
									'max-height' : contentHeight + 'px',
									"overflow-x":"hidden"
								});
							}
						});
					}
				});
			},300);
		});
		topic.subscribe("mui/form/checkbox/change", function(box, data) {
			if (data.name != '_select_box_commonUsageObjName') {
				return;
			}
			if (dialog)
				dialog.hide();
			dialog = null;
			lbpm.globals.clearDefaultUsageContent(lbpm.currentOperationType);
			var fdUsageContent = registry.byId('fdUsageContent');
			if(lbpm.workitem.constant.COMMONUSAGES_ISAPPEND=="true"){
				fdUsageContent.set('value', fdUsageContent.get('value') + data.value.replace(/\\\'/g,"'"));
			} else {
				fdUsageContent.set('value', data.value.replace(/\\\'/g,"'"));
			}
			var target = query("textarea[name='"+fdUsageContent.name+"']")[0];
			on.emit(target,"sys/lbpmservice/mobile/NoticeHandler",{
				bubbles: true,
			    cancelable: true
			});
		});
	};
	
	// 根据操作类型获取默认审批意见
	commonUsage.getOperationDefaultUsage = lbpm.globals.getOperationDefaultUsage = function(operationType){
		var defaultUsage = "";
		if(!operationType || operationType==null){
			return defaultUsage;
		}
		
		var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
		if(extAttributes){//查看扩展属性值
			for(var i=0;i<extAttributes.length;i++){
				if(extAttributes[i]["name"]=="lbpmCustomizeContentJson"){
					
					var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
					
					if(lbpmCustomizeContentJsonStr){
						var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
						//根据操作类型得到相应的默认操作变量
						var customizeUsageName=lbpm.globals.customizeUsageName(operationType);
						//获取当前语言
						var currentLang = Data_GetResourceString("locale.language");
						
						//如果官方语言为空，说明是单语言环境，直接从json字符串获取
						if(JSON.stringify(_langJson.official) == "{}"){
							//判断Json字符串是否包含设置多语言值
							if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageName.handlerContent)){
								var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageName.handlerContent];
								return customizeContent_lang;
							}
						}else{
							//首先从多语言里面获取
							if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageName.handlerContent+"_lang")){
								var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageName.handlerContent+"_lang"];
								if(customizeContent_lang){
									var customizeUsageContentLangJson=JSON.parse(customizeContent_lang);
									for(var z=0;z<customizeUsageContentLangJson.length;z++){
										if(customizeUsageContentLangJson[z].lang==currentLang){
											defaultUsage=customizeUsageContentLangJson[z].value;
											return defaultUsage;
										}
									}
								}
							}
						}
					}
					break;
				}
			}
		}
		
		
		if(lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT){
			defaultUsage = lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT[operationType];
			if (defaultUsage == null) {
				defaultUsage = "";
			}
		}
		return defaultUsage;
	};
	
	//根据操作类型获取默认审批校验意见
	lbpm.globals.getOperationDefaultUsageValidate = function(operationType){
		
		var defaultUsage = "";
		if(!operationType || operationType==null){
			return defaultUsage;
		}
		

		var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
		if(extAttributes){//查看扩展属性值
			for(var i=0;i<extAttributes.length;i++){
				if(extAttributes[i]["name"]=="lbpmCustomizeValidateContentJson"){
					
					var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
					
					if(lbpmCustomizeContentJsonStr){
						var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
						//根据操作类型得到相应的默认操作变量
						var customizeUsageNameValidate=lbpm.globals.customizeUsageNameValidate(operationType);
						//获取当前语言
						var currentLang = Data_GetResourceString("locale.language");
						
						//如果官方语言为空，说明是单语言环境，直接从json字符串获取
						if(JSON.stringify(_langJson.official) == "{}"){
							//判断Json字符串是否包含设置多语言值
							if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageNameValidate.handlerContent)){
								var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageNameValidate.handlerContent];
								return customizeContent_lang;
							}
						}else{
							//首先从多语言里面获取
							if(lbpmCustomizeContentJson.hasOwnProperty(customizeUsageNameValidate.handlerContent+"_lang")){
								var customizeContent_lang= lbpmCustomizeContentJson[customizeUsageNameValidate.handlerContent+"_lang"];
								if(customizeContent_lang){
									var customizeUsageContentLangJson=JSON.parse(customizeContent_lang);
									for(var z=0;z<customizeUsageContentLangJson.length;z++){
										if(customizeUsageContentLangJson[z].lang==currentLang){
											defaultUsage=customizeUsageContentLangJson[z].value;
											return defaultUsage;
										}
									}
								}
							}
						}
					}
					break;
				}
			}
		}
		return "";
	};

	
	//获取操作类型对应的自定义名称
	lbpm.globals.customizeUsageName= function(operationType){
		var customizeUsage={};
		if(operationType=='handler_pass'){
			customizeUsage={"handlerContent":"customizeUsageContent","isRequired":"isPassContentRequired","isValidated":"isPassContentValidated"};
		}
		
		if(operationType=='handler_commission'){
			customizeUsage={"handlerContent":"customizeUsageContentCommission","isRequired":"isCommissionContentRequired","isValidated":"isCommissionContentValidated"};
		}
		
		if(operationType=='handler_nodeSuspend'){
			customizeUsage={"handlerContent":"customizeUsageContentNodeSuspend","isRequired":"isNodeSuspendContentRequired","isValidated":"isNodeSuspendContentValidated"};
		}
		
		if(operationType=='handler_communicate'){
			customizeUsage={"handlerContent":"customizeUsageContentCommunicate","isRequired":"isCommunicateContentRequired","isValidated":"isCommunicateContentValidated"};
		}
		
		if(operationType=='handler_refuse'){
			customizeUsage={"handlerContent":"customizeUsageContentRefuse","isRequired":"isRefuseContentRequired","isValidated":"isRefuseContentValidated"};
		}
		
		if(operationType=='handler_additionSign'){
			customizeUsage={"handlerContent":"customizeUsageContentAdditionSign","isRequired":"isAdditionSignContentRequired","isValidated":"isAdditionSignContentValidated"};
		}
		
		if(operationType=='handler_superRefuse'){
			customizeUsage={"handlerContent":"customizeUsageContentSuperRefuse","isRequired":"isSuperRefuseContentRequired","isValidated":"isSuperRefuseContentValidated"};
		}
		
		if(operationType=='handler_nodeResume'){
			customizeUsage={"handlerContent":"customizeUsageContentNodeResume","isRequired":"isNodeResumeContentRequired","isValidated":"isNodeResumeContentValidated"};
		}
		
		if(operationType=='handler_assign'){
			customizeUsage={"handlerContent":"customizeUsageContentAssign","isRequired":"isAssignContentRequired","isValidated":"isAssignContentValidated"};
		}
		if(operationType=='handler_assignPass'){
			customizeUsage={"handlerContent":"customizeUsageContentAssignPass","isRequired":"isAssignPassContentRequired","isValidated":"isAssignPassContentValidated"};
		}
		if(operationType=='handler_assignRefuse'){
			customizeUsage={"handlerContent":"customizeUsageContentAssignRefuse","isRequired":"isAssignRefuseContentRequired","isValidated":"isAssignRefuseContentValidated"};
		}
		if(operationType=='handler_jump'){
			customizeUsage={"handlerContent":"customizeUsageContentJump","isRequired":"isJumpContentRequired","isValidated":"isJumpContentValidated"};
		}
		if(operationType=='handler_abandon'){
			customizeUsage={"handlerContent":"customizeUsageContentAandon","isRequired":"isAbandonContentRequired","isValidated":"isAbandonContentValidated"};
		}
		
		if(operationType=='handler_sign'){
			customizeUsage={"handlerContent":"customizeUsageContentSign","isRequired":"isSignContentRequired","isValidated":"isSignContentValidated"};
		}
		
		
		return customizeUsage;
	}
	
	//获取操作类型对应的校验的自定义名称
	lbpm.globals.customizeUsageNameValidate= function(operationType){//isExcludeValidated
		var customizeUsageValidate={};
		if(operationType=='handler_pass'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentValidated","isExcludeValidated":"isPassContentExcludeValidated","isValidated":"isPassContentValidated"};
		}
		
		if(operationType=='handler_commission'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentCommissionValidated","isExcludeValidated":"isCommissionContentExcludeValidated","isValidated":"isCommissionContentValidated"};
		}
		
		if(operationType=='handler_nodeSuspend'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentNodeSuspendValidated","isExcludeValidated":"isNodeSuspendContentExcludeValidated","isValidated":"isNodeSuspendContentValidated"};
		}
		
		if(operationType=='handler_communicate'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentCommunicateValidated","isExcludeValidated":"isCommunicateContentExcludeValidated","isValidated":"isCommunicateContentValidated"};
		}
		
		if(operationType=='handler_refuse'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentRefuseValidated","isExcludeValidated":"isRefuseContentExcludeValidated","isValidated":"isRefuseContentValidated"};
		}
		
		if(operationType=='handler_additionSign'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentAdditionSignValidated","isExcludeValidated":"isAdditionSignContentExcludeValidated","isValidated":"isAdditionSignContentValidated"};
		}
		
		if(operationType=='handler_superRefuse'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentSuperRefuseValidated","isExcludeValidated":"isSuperRefuseContentExcludeValidated","isValidated":"isSuperRefuseContentValidated"};
		}
		
		if(operationType=='handler_nodeResume'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentNodeResumeValidated","isExcludeValidated":"isNodeResumeContentExcludeValidated","isValidated":"isNodeResumeContentValidated"};
		}
		
		if(operationType=='handler_assign'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentAssignValidated","isExcludeValidated":"isAssignContentExcludeValidated","isValidated":"isAssignContentValidated"};
		}
		if(operationType=='handler_assignPass'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentAssignPassValidated","isExcludeValidated":"isAssignPassContentExcludeValidated","isValidated":"isAssignPassContentValidated"};
		}
		if(operationType=='handler_assignRefuse'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentAssignRefuseValidated","isExcludeValidated":"isAssignRefuseContentExcludeValidated","isValidated":"isAssignRefuseContentValidated"};
		}
		if(operationType=='handler_jump'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentJumpValidated","isExcludeValidated":"isJumpContentExcludeValidated","isValidated":"isJumpContentValidated"};
		}
		if(operationType=='handler_abandon'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentAandonValidated","isExcludeValidated":"isAbandonContentExcludeValidated","isValidated":"isAbandonContentValidated"};
		}
		
		if(operationType=='handler_sign'){
			customizeUsageValidate={"handlerContent":"customizeUsageContentSignValidated","isExcludeValidated":"isSignContentExcludeValidated","isValidated":"isSignContentValidated"};
		}
		
		
		return customizeUsageValidate;
	}

	
	//根据操作类型获取审批意见是否必填
	commonUsage.isUsageContenRequired = lbpm.globals.isUsageContenRequired = function(operationType){
		if(!operationType || operationType==null){
			return null;
		}
		if(!lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED || lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED==null){
			return null;
		}
		
		var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
		if(extAttributes){//查看扩展属性值
			for(var i=0;i<extAttributes.length;i++){
				if(extAttributes[i]["name"]=="lbpmCustomizeContentJson"){
					
					var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
					
					if(lbpmCustomizeContentJsonStr){
						var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
						//根据操作类型得到相应的默认操作变量
						var customizeUsageName=lbpm.globals.customizeUsageName(operationType);
						if(customizeUsageName){
							if(lbpmCustomizeContentJson[customizeUsageName.isRequired]=="true"){
								return true;
							}else if(lbpmCustomizeContentJson[customizeUsageName.isRequired]=="false"){
								return false;
							}
						}
					}
					break;
				}
			}
		}
		
		
		if(lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED[operationType]=="true"){
			return true;
		} else if(lbpm.workitem.constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED[operationType]=="false"){
			return false;
		} else {
			return null;
		}
	};
	
	//根据操作类型获取审批意见是否包含校验
	lbpm.globals.isUsageContenValidated = function(operationType){
		if(!operationType || operationType==null){
			return null;
		}
		
		var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
		if(extAttributes){//查看扩展属性值
			for(var i=0;i<extAttributes.length;i++){
				if(extAttributes[i]["name"]=="lbpmCustomizeValidateContentJson"){
					
					var lbpmCustomizeContentJsonStr=extAttributes[i]["value"];
					
					if(lbpmCustomizeContentJsonStr){
						var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
						//根据操作类型得到相应的默认操作变量
						var customizeUsageNameValidate=lbpm.globals.customizeUsageNameValidate(operationType);
						if(customizeUsageNameValidate){
							if(lbpmCustomizeContentJson[customizeUsageNameValidate.isValidated]=="true"){
								return true;
							}else if(lbpmCustomizeContentJson[customizeUsageNameValidate.isValidated]=="false"){
								return false;
							}
						}
					}
					break;
				}
			}
		}
		
		return null;
	};

	//根据操作类型获取审批意见是否排除校验
	lbpm.globals.isUsageContenExcludeValidated = function(operationType){
		if(!operationType || operationType==null){
			return null;
		}
		
		var extAttributes=lbpm.nodes[lbpm.nowNodeId].extAttributes;
		if(extAttributes){//查看扩展属性值
			for(var i=0;i<extAttributes.length;i++){
				if(extAttributes[i]["name"]=="lbpmCustomizeValidateContentJson"){
					
					var lbpmCustomizeValidateJsonStr=extAttributes[i]["value"];
					
					if(lbpmCustomizeValidateJsonStr){
						var lbpmCustomizeValidateJson=JSON.parse(lbpmCustomizeValidateJsonStr);
						//根据操作类型得到相应的默认操作变量
						var customizeUsageNameValidate=lbpm.globals.customizeUsageNameValidate(operationType);
						if(customizeUsageNameValidate){
							if(lbpmCustomizeValidateJson[customizeUsageNameValidate.isExcludeValidated]=="true"){
								return true;
							}else if(lbpmCustomizeValidateJson[customizeUsageNameValidate.isExcludeValidated]=="false"){
								return false;
							}
						}
					}
					break;
				}
			}
		}
		return null;

	};

	commonUsage.clearDefaultUsageContent = lbpm.globals.clearDefaultUsageContent = function(operationType) {
		var defalutUsage = "";
		defalutUsage = lbpm.globals.getOperationDefaultUsage(operationType);
		var fdUsageContent = registry.byId('fdUsageContent');
		var val = fdUsageContent.get('value');
		if (defalutUsage==null ||  val.replace(/\s*/ig, '') == defalutUsage.replace(/\s*/ig, '')) {
			fdUsageContent.set('value', '');
		}
	};
	
	commonUsage.setDefaultUsageContent = lbpm.globals.setDefaultUsageContent = function(operationType, usageContent, simpleUsage) {
		var defaultUsage = "";
		defaultUsage = lbpm.globals.getOperationDefaultUsage(operationType);
		var fdUsageContent = null;
		if(usageContent!=null){
			fdUsageContent = registry.byNode(usageContent);
		}else{
			fdUsageContent = registry.byId('fdUsageContent');
		}
		var val = fdUsageContent.get('value');
		// 审批意见为空时才设置默认审批意见
		if (fdUsageContent!=null && !val.replace(/\s*/ig, '')) {
			fdUsageContent.set('value', defaultUsage);
		}
	};

	commonUsage.init= function() {
		initialCommonUsages();
		topic.subscribe('/dojox/mobile/beforeTransitionIn', function() {
			var fdUsageContent = registry.byId('fdUsageContent');
			if (fdUsageContent) {
				fdUsageContent.resizeHeight(fdUsageContent.textareaNode);
			}
		});
	};
	return commonUsage;
});