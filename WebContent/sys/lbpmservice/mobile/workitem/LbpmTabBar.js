define([
        "dojo/_base/declare",
        "dojo/_base/lang",
        "dojo/_base/window", 
        "mui/tabbar/TabBar",
        "mui/dialog/Dialog",
        "mui/history/listener",
        "./OperationButton",
        "./OperationSwitchButton",
        "./TransactionTabBarBtn",
        "dojox/mobile/TransitionEvent", 
        "dojox/mobile/viewRegistry",
    	"dojo/_base/array",
    	"dojo/Deferred",
        "mui/device/adapter",
    	"dojo/query",
    	"dijit/registry",
    	"mui/i18n/i18n!sys-lbpmservice:FlowChartObject.Lang.Node.needHandWritten",
    	"dojo/dom",
    	"dojo/dom-style", 
    	"dojo/dom-construct",
    	"mui/dialog/Tip"
	], function(declare, lang, win, TabBar, Dialog, listener, OperationButton, OperationSwitchButton, TransactionTabBarBtn, TransitionEvent, viewRegistry, array, Deferred, adapter, query, registry,msg,dom,domStyle,domConstruct, tip) {
	
	return declare("sys.lbpmservice.mobile.workitem.LbpmTabBar", [TabBar], {
		fill:'grid',
		
		viewName:null,
		
		curTaskId:null,
		
		_operateConfig:{},
		
		_taskIds:[],
		
		transactionWgt : null, // 事务组件
		
		oldTaskId:"",
		
		showType:"dialog",
		
		signValidPushed: false,
		
		buildRendering : function() {
			this.inherited(arguments);
			this.initOperateInfo();
		},
		
		postCreate: function() {
			this.inherited(arguments);
			this.subscribe("/lbpm/operation/switch","switchButton");
			if(this.showType == 'dialog'){
				this.subscribe("/lbpm/operation/hideDialog","_hideDialog");
				this.subscribe("/lbpm/operation/showDialog","_showDialog");
				this.subscribe("/lbpm/operation/destroyDialog","_destroyDialog");
				this.subscribe("/lbpm/ext/auditNoteHandler/change","_changeHWSign");
				this.subscribe("/mui/Category/valueChange","_toHandlerValChange");
				this.subscribe("/lbpm/operation/toHandlerAddress/parseFinish","_toSelectCate")
			}
		},
		
		initOperateInfo:function(){
			var _self = this;
			var i = 0;
			array.forEach(window.LBPM_CurOperationCfg,function(operateInfo,idx){
				if(operateInfo['taskFrom'] == 'workitem'){
					var ableFlag = true;
					if (lbpm && lbpm.isSubForm && lbpm.nowSubFormId != operateInfo['subFormMobileId']) {
						ableFlag = false;
					}
					if (ableFlag) {
						_self._operateConfig[operateInfo.taskId] = operateInfo;
						_self._taskIds[i] = operateInfo.taskId;
						i++;
					}
				}
			});
			if(!this.curTaskId){
				var selectedIndex = 0;
				if (this._taskIds.length > 1) {
					for(var i=0;i<this._taskIds.length;i++){
						if (lbpm && lbpm.defaultTaskId && this._taskIds[i] == lbpm.defaultTaskId) {
							selectedIndex = i;
							break;
						}
					}
				}
				this.curTaskId = this._taskIds[selectedIndex];
			}
		},
		
		startup : function() {
			if (this._started)
				return;
			this.renderTabrBar(this.curTaskId);
			this.inherited(arguments);
		},
		
		fillLbpmInfo:function(ctx){
			if(ctx.value!=this.curOperationType || this.oldTaskId!=this.curTaskId){//事务或操作变化都要刷新信息
				var curOpt = ctx.value + ":" + ctx.label;
				query("#operationMethodsGroup")[0].value = curOpt;
				lbpm.globals.clickOperation(curOpt);
				var self = this;
				array.forEach(lbpm.processorInfoObj,function(infoObj,idx){
					if(self.curTaskId==infoObj.id){
						lbpm.nowProcessorInfoObj = infoObj;
						lbpm.nowNodeId = lbpm.nowProcessorInfoObj.nodeId;
						var optionText = self.curTaskName;
						var defalutRep = new RegExp("^"+"N.");
						// 开启了隐藏节点编号中的流程中的节点编号则隐藏节点编号
						if(defalutRep.test(optionText)){
							if (lbpm && lbpm.settingInfo){
								if (lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
									var opt = optionText.substring(
										optionText.indexOf("N"),
										optionText.indexOf(".") + 1);
									optionText = optionText.replace(opt, "");
								}
							}
						}
						query("#operationItemsRow .detailNode").html(self.curTaskName);
						query("#operationItemsSelect")[0].value = idx;
					}
				});
				query("#lbpmOperationMethodTable .detailNode").html(this.curNodeInfo +":" + ctx.label);
				this.curOperationType = ctx.value;
				lbpm._mobileView = this.backTo; 
			}
		},
		
		switchButton:function(wgt,ctx){
			if(ctx){
				this.currentOperationData = ctx;
				if(ctx.methodSwitch){//操作切换
					var view = null;
					if(this.backTo == null){
						view = viewRegistry.getEnclosingView(this.domNode);
						view = viewRegistry.getParentView(view) || view;
						this.backTo = view.id;
					}
					var element = registry.byId(this.backTo);
					if(element){
						if(ctx.value && ctx.value!='handler_pass' && lbpm && lbpm.nowProcessorInfoObj 
								&& (lbpm.nowProcessorInfoObj.type=="reviewWorkitem" || lbpm.nowProcessorInfoObj.type=="assignWorkitem")){
							element.notValRequired = true;
						} else{
							element.notValRequired = false;
							//让当前聚焦的dom失焦(单行失焦后才会重设value)，防止校验时获取的value不准确
							var activeElement = document.activeElement;
							if(activeElement && activeElement.blur){
								activeElement.blur();
							}
						}
					}
					//如果是弹窗类型，要先进行校验（兼容表单查看页编辑）
					if(this.showType == 'dialog'){
						//只有标记为通过类型的才需要进行校验，和PC保持一致
						if(ctx.value && lbpm.operations && lbpm.operations[ctx.value] && lbpm.operations[ctx.value].isPassType){
							var scrollView = viewRegistry.getEnclosingView(this.domNode);
							scrollView = viewRegistry.getParentView(scrollView) || scrollView;
							if(scrollView.validate && !scrollView.validate()){
								return;
							}
						}
					}
					if(this.showType != 'dialog'){
						new TransitionEvent(win.body(), {moveTo: this.viewName, transition: "slide", transitionDir: 1}).dispatch();
					}
					window.Com_BeforeSubmitDeferred = lang.hitch(this,this._validateFingerPrint);
					var _self=this;
					if(this.showType != 'dialog'){
						listener.add({callback:function(){
							new TransitionEvent(win.body(), {moveTo: _self.backTo, transition: "slide", transitionDir: -1}).dispatch();
							window.Com_BeforeSubmitDeferred = null;
						}});
					}
					this.fillLbpmInfo(ctx);
					this.addHWSignValidator(ctx);
					if(this.showType == 'dialog'){
						//转办、沟通、加签、补签操作先选择目标人员
						var operationTypes = ["handler_commission","handler_communicate","handler_additionSign","handler_assign"];
						if(!(ctx.value && operationTypes.indexOf(ctx.value) >= 0)){
							this.showOperationDialog(ctx);
						}
						if(dojoConfig.dingXForm === "true" && ctx.value && ctx.value=="handler_communicate"){
						    $("#toHandlerAddress").removeClass("muiFormLeft");
						}
					}
				}else{//事务切换
					if(this.curTaskId!=ctx.value){
						var self=this;
						array.forEach(this.getChildren(),function(childWgt){
							if((childWgt instanceof OperationButton) || (childWgt instanceof OperationSwitchButton)){
								self.removeChild(childWgt);
							}
						});
						this.oldTaskId = this.curTaskId;
						this.curTaskId = ctx.value;
						this.renderTabrBar(ctx.value);
						setTimeout(function(){
							self.resize();
						},20);
						array.forEach(lbpm.processorInfoObj,function(infoObj,idx){
							if(self.curTaskId==infoObj.id){
								lbpm.nowProcessorInfoObj = infoObj;
								lbpm.nowNodeId = lbpm.nowProcessorInfoObj.nodeId;
								query("#operationItemsRow .detailNode").html(self.curTaskName);
								query("#operationItemsSelect")[0].value = idx;
							}
						});
					}
				}
			}
		},
		
		_toSelectCate:function(){
			//如果是弹窗类型，要先进行校验（兼容表单查看页编辑）
			if(this.showType == 'dialog'){
				var scrollView = viewRegistry.getEnclosingView(this.domNode);
				scrollView = viewRegistry.getParentView(scrollView) || scrollView;
				if(scrollView.validate && !scrollView.validate()){
					return;
				}
			}
			
			var toHandlerAddress = registry.byId("toHandlerAddress");
			toHandlerAddress._selectCate();
		},
		
		_toHandlerValChange:function(evt,data){
			var toHandlerAddress = registry.byId("toHandlerAddress");
			if(toHandlerAddress && toHandlerAddress == evt && this.dialog == null){//显示弹窗
				this.showOperationDialog(this.currentOperationData);
			}
		},
		
		_showDialog :function(evt,data){
			//禁止滚动
			domStyle.set(query("body")[0],{
				"overflow-y":"hidden",
				"position":"fixed"
			})
			
			this.defer(function(){
				domStyle.set(dom.byId(this.viewName),{
					"display":"block"
				})
				if(data && data.handwriting){
					//滚动定位到手写位置
					var domOffsetTop = this._getDomOffsetTop(query("#handwriting")[0]);
			        var target = window;
			        if(this.showType == 'dialog'){
			        	target = query(".muiLbpmDialog .muiDialogElementContent_bottom")[0];
			        }
			        this._scrollTo(target,{ y: 0 - domOffsetTop + 110 });
				}
			},500);
		},
		
		_hideDialog:function(evt){
			domStyle.set(dom.byId(this.viewName),{
				"display":"none"
			})
			//恢复滚动
			domStyle.set(query("body")[0],{
				"overflow-y":"",
				"position":""
			})
		},
		
		_destroyDialog:function(evt){
			if (this.dialog && this.dialog.callback)
				this.dialog.callback(window, this.dialog);
			this.lastDialog.hide();
			this.lastDialog = null;
		},
		
		showOperationDialog : function(ctx){
			//移除意见框的校验
			var fdUsageContent = registry.byId('fdUsageContent');
			if(fdUsageContent.validation){
				//fdUsageContent.validation.validateElement(fdUsageContent);
				fdUsageContent.validation.hideWarnHint(fdUsageContent.domNode);
			}
			
			if(lbpm && lbpm.events){
				this.defer(function() {
					// 更新 “即将流向” 信息
					lbpm.events.mainFrameSynch();
				},100);
			}
			this.lbpmViewDom = dom.byId(this.viewName);
			this.dialogContentNode = domConstruct.create('div');
			domConstruct.place(dom.byId("lbpmOperContent"),this.dialogContentNode,'first');
			var buttons = [];
			lbpm.currentOperationLabel = ctx.label;
			var title = "<span style='font-size:1.6rem'>" + ctx.label + "</span>";
			var currentNodeObj = lbpm.globals.getCurrentNodeObj();
		 	if(currentNodeObj && currentNodeObj.description){
		 		title += "<span class='nodeDescriptionHelp' onclick='showNodeHelpDialog()'>节点帮助</span>";
		 	}
			this.dialog = Dialog.element({
				title:title || '',
				canClose : false,
				element : this.dialogContentNode,
				buttons : buttons,
				position:'bottom',
				appendTo:this.lbpmViewDom,
				'scrollable' : false,
				'parseable' : false,
				showClass : 'muiLbpmDialog',
				callback : lang.hitch(this, function(win,evt) {
					domStyle.set(this.lbpmViewDom,{
						"display":"none",
						"position":"",
						"z-index":""
					})
					domStyle.set(query("body")[0],{
						"overflow-y":""
					})
					domConstruct.place(query("#lbpmOperContent",evt.element)[0],this.lbpmViewDom,'first');
					window.Com_BeforeSubmitDeferred = null;
					this.lastDialog = this.dialog;
					this.dialog = null;
				}),
				onDrawed:lang.hitch(this, function(evt) {
					domStyle.set(this.lbpmViewDom,{
						"display":"block",
						"position":"fixed",
						"bottom":"0",
						"z-index":"100"
					})
					domStyle.set(query("body")[0],{
						"overflow-y":"hidden"
					})
					this.defer(function(){
						 var contentHeight = document.documentElement.clientHeight*0.9;
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
						 domStyle.set(evt.contentNode, {
							   'max-height' : contentHeight + 'px',
							   "overflow-x":"hidden"
						 });
						 evt.scrollViewNode = evt.contentNode;
					 },800)	
					 //重置按钮宽度
					 var lbpmOperationTabBar = registry.byId("lbpmViewOperationTabBar");
					 lbpmOperationTabBar.resize();
				})
			});
		},
		
		//指纹、刷脸验证
		_validateFingerPrint : function(){
			var _self = this;
			var defer = new Deferred(),
				extAttribute = lbpm.globals.getExtAttribute &&  lbpm.globals.getExtAttribute(lbpm.globals.getCurrentNodeObj(),'fingerPrintReview'),
				facePrint = lbpm.globals.getExtAttribute &&  lbpm.globals.getExtAttribute(lbpm.globals.getCurrentNodeObj(),'facePrintReview');
			//审批操作才做指纹校验
			if(extAttribute && extAttribute.value == 'true'){
				//当前客户端不支持指纹审批（产品要求不支持的先让他可以审批）
				if(adapter.supportfingerPrint == 1){
					defer.resolve();
					if(_self.showType == 'dialog'){
						_self._hideDialog();
					}else{
						history.back();
					}
					return;
				}
				//当前客户端版本不支持指纹审批（产品要求不支持的先让他可以审批）
				if(adapter.supportfingerPrint == 2){
					defer.resolve();
					if(_self.showType == 'dialog'){
						_self._hideDialog();
					}else{
						history.back();
					}
					return;
				}
				adapter.validatefingerPrint(function(err){
					if(err){
						defer.reject(err);
						return;
					}
					defer.resolve();
					if(_self.showType == 'dialog'){
						_self._hideDialog();
					}else{
						history.back();
					}
				},function(err){
					defer.reject(err);
					return;
				});
			}else if(facePrint && facePrint.value == 'true'){
				//刷脸审批
				//当前客户端不支持刷脸审批，则不校验。
				if(adapter.supportfingerPrint == 1){
					defer.resolve();
					if(_self.showType == 'dialog'){
						_self._hideDialog();
					}else{
						history.back();
					}
					return;
				}
				//当前客户端版本不支持刷脸验证，则不校验。
				if(adapter.supportFacePrint == 2){
					defer.resolve();
					if(_self.showType == 'dialog'){
						_self._hideDialog();
					}else{
						history.back();
					}
					return;
				}
				adapter.checkFace(function(err){
					if(err){
						defer.reject(err);
						return;
					}
					defer.resolve();
					if(_self.showType == 'dialog'){
						_self._hideDialog();
					}else{
						history.back();
					}
				},function(err){
					if(err.code == 6){
						defer.resolve();
						if(_self.showType == 'dialog'){
							_self._hideDialog();
						}else{
							history.back();
						}
						return;
					}
					defer.reject(err);
					return;
				});
			}else{
				defer.resolve();
				if(_self.showType == 'dialog'){
					_self._hideDialog();
				}else{
					history.back();
				}
			}
			return defer;
		},
		
		renderTabrBar:function(taskId){
			if(!taskId || !this._operateConfig[taskId]){
				return;
			}
			var buttonIdx = 0;
			if(this._taskIds.length>1){
				var options=[];
				for(var i=0; i<this._taskIds.length;i++){
					var tmpTask = this._taskIds[i];
					if(!this._operateConfig[tmpTask]){
						continue;
					}
					var nodeId = this._operateConfig[tmpTask].nodeId;
					var curNodeInfo  =null;
					array.forEach(window.LBPM_CurNodeCfg,function(nodeInfo){
						if(nodeId==nodeInfo.nodeId){
							curNodeInfo = nodeInfo;
						}
					});
					var extInfo = (this._operateConfig[tmpTask].parentHandlerName)?(this._operateConfig[tmpTask].parentHandlerName +":"):"";
					extInfo = nodeId + '.' + extInfo + curNodeInfo.nodeName + "(" + this._operateConfig[tmpTask].expectedName+')';
					if(curNodeInfo && curNodeInfo.groupNodeId){
						extInfo = extInfo + "【" + curNodeInfo.groupNodeId + "."
								+ this._getLangLabel(curNodeInfo.groupNodeName, curNodeInfo.groupNodeLangs,"nodeName")
								+ "】";
					}
					if(this.curTaskId==tmpTask){
						this.curTaskName =  extInfo;
						this.curNodeInfo = nodeId + "." + curNodeInfo.nodeName ;
					}
					options.push({'text': extInfo,
						'value':tmpTask,'checked':taskId==tmpTask});
				}
				if(!this.transactionWgt){
					// 添加事务组件
					this.transactionWgt = new TransactionTabBarBtn({
						tabBar : this,
						options : options,
						curTaskId : this.curTaskId
					});					
				}
				// this.addChild(new OperationSwitchButton({methodSwitch:false, options:options}),buttonIdx);
				// buttonIdx++;
			}else{
				var nodeId = this._operateConfig[taskId].nodeId;
				var curNodeInfo= null;
				array.forEach(window.LBPM_CurNodeCfg,function(nodeInfo){
					if(nodeId==nodeInfo.nodeId){
						curNodeInfo = nodeInfo;
					}
				});
				this.curNodeInfo = nodeId + "." + curNodeInfo.nodeName ;
				this.curTaskName =  nodeId + '.' + curNodeInfo.nodeName + "(" + this._operateConfig[taskId].expectedName+')';
			}
			var curOperations = this._operateConfig[taskId].operations;
			if(curOperations.length > 0){
				var maxIdx=0;
				var showBtnCount = lbpm.isShowSubBut ? 3 : 4;
				var needMore=false;
				if(curOperations.length<=showBtnCount){
					maxIdx = curOperations.length;
				}else{
					maxIdx = showBtnCount-1;
					needMore = true;
				}
				for(var i=0;i<maxIdx;i++){
					var opration = curOperations[i];
					this.addChild(new OperationButton({label:opration.operationName,operationType:opration.operationType,tabIndex:buttonIdx}),buttonIdx);
					buttonIdx++;
				}
				if(needMore){
					var options=[];
					for(var i=maxIdx;i<curOperations.length;i++){
						var opration = curOperations[i];
						options.push({'text': opration.operationName,
							'value':opration.operationType});
					}
					this.addChild(new OperationSwitchButton({options:options}),buttonIdx);
					buttonIdx++;
				}
			}
		},

		_getLangLabel:function(defLabel,langs,key,lang){
			if(typeof langs =="undefined" || (typeof _isLangSuportEnabled !="undefined" && !_isLangSuportEnabled)){
				return defLabel;
			}
			if(typeof lang =="undefined"){
				lang = this._GetCurrUserLang();
			}
			var langArr=[];
			if(typeof key =="undefined"){
				langArr =  $.parseJSON(langs);
			}else{
				var langsJson={};
				if(langs!=null && langs!=""){
					langsJson = $.parseJSON(langs);
				}
				if(typeof langsJson[key]=="undefined"){
					return defLabel;
				}
				langArr=langsJson[key];
			}
			for(var i=0;i<langArr.length;i++){
				if(lang==langArr[i]["lang"]){
					return langArr[i]["value"]||defLabel;
				}
			}
			return defLabel;
		},

		_GetCurrUserLang : function(){
			var cl = Com_Parameter.Lang;
			var pos = cl.indexOf("-");
			if(pos!=-1){
				var pk = cl.split("-");
				return pk[0]+"-"+pk[1].toUpperCase();
			}
			return cl;
		},

		_changeHWSign:function(){//切换值，触发校验
			var hwSignEmpty = query("[name='hwSignEmpty']")[0];
			if(hwSignEmpty){
				var wdt = registry.byId(this.viewName);
				wdt.validate([hwSignEmpty]);
			}
		},
		
		// 根据配置的签字节点或者审批节点决定是否需要手写签字才能通过 zhanlei 2020-04-04
		addHWSignValidator:function (ctx) {
			var reviewNodePass = lbpm.nowProcessorInfoObj.type == "reviewWorkitem" &&  ctx.value =='handler_pass';
			var signNodePass = lbpm.nowProcessorInfoObj.type == "signWorkitem" &&  ctx.value =='handler_sign';
			if (!lbpm || !lbpm.processorInfoObj || !(reviewNodePass || signNodePass)) {
				if(this.showType == 'dialog' && this.signValidPushed){
					var wdt = registry.byId(this.viewName);
					wdt._validation.hideWarnHint(query("#hwSignEmpty")[0]);
					var hwSignEmptyWgt = registry.byId("hwSignEmpty");
					hwSignEmptyWgt.destroy();
					query("#hwSignRequiredIcon").remove();
					query("#hwSignEmptyDiv").remove();
					this.signValidPushed = false;
				}else{
					query("#hwSignRequiredIcon").remove();
				}
				return;
			}
			var _needSignature = lbpm.nodes[lbpm.nowNodeId].needMobileHandWrittenSignatureReviewNode || lbpm.nodes[lbpm.nowNodeId].needMobileHandWrittenSignatureSignNode;
			// 流程图未设置时有时不会存起默认配置，这个时候取功能开关处的默认值
			if (typeof _needSignature == 'undefined') {
				_needSignature = reviewNodePass ? Lbpm_SettingInfo.needMobileHandWrittenSignatureReviewNode:Lbpm_SettingInfo.needMobileHandWrittenSignatureSignNode;
			}
			if (_needSignature=="true" && !this.signValidPushed) {
				// 给手写图标加上*标识
				var notice = '<span id="hwSignRequiredIcon" style="color:red;display:block;position:absolute;top:0px;right:0px;line-height:10px;font-size:10px;">*</span>';
				var _handWrittenImg = query(".mui-handwrite");
				if (_handWrittenImg) {
					_handWrittenImg.html(notice);
					//添加一个隐藏域到页面，用于手写签批的校验（参与框架校验）
					if(this.showType == 'dialog'){
						var divNode = domConstruct.create("div",{
							id:"hwSignEmptyDiv",
							style:"position:relative"
						});
						domConstruct.place(divNode,dom.byId("commonUsagesDiv"),'before');
						var nodeHtml = "<div data-dojo-type='mui/form/Input' data-dojo-props='\"name\":\"hwSignEmpty\",\"showStatus\":\"edit\",\"validate\":\"hwSignRequired\"' id='hwSignEmpty' style='display:none;'></div>";
						query("#hwSignEmptyDiv").html(nodeHtml,{parseContent:true});
					}
				}
				this.signValidPushed = true;
				lbpm.globals.handlerWrittenValidator = function() {
					var _imageDivSubmit = query("#imgUl");
					if (!_imageDivSubmit || _imageDivSubmit.length === 0 || _imageDivSubmit.innerHTML() == "") {
						tip["warn"]({text:msg['FlowChartObject.Lang.Node.needHandWritten']});
						//alert(msg['FlowChartObject.Lang.Node.needHandWritten']);
						return false;
					}
					return true;
				}
			}
		},
		
		_scrollTo: function (obj, evt) {
	    	var y = 0;
	    	if (evt) {
	    		y = evt.y || 0;
	    	}

	    	var end = -y;
	    	var start = this.getPos(obj).y;
	    	var diff = end - start; // 差值
	    	var t = 300; // 时长 300ms
	    	var rate = 30; // 周期 30ms
	    	var count = 10; // 次数
	    	var step = diff / count; // 步长
	    	var i = 0; // 计数
	    	var timer = window.setInterval(function () {
	    		if (i <= count - 1) {
	    			start += step;
	    			i++;
	    			obj.scrollTo(0, start);
	    		} else {
	    			window.clearInterval(timer);
	    		}	
	    	}, rate);
	    },
	    
	    // 获取当前滚动位置
	    getPos: function (obj) {
	    	if(obj == window){
	    		return { y: document.documentElement.scrollTop || document.body.scrollTop };
	    	}else{
	    		return { y: obj.scrollTop };
	    	}
	    },
		
		_getDomOffsetTop: function (node) {
		    var offsetParent = node;
		    var nTp = 0;
		    while (offsetParent != null && offsetParent != document.body) {
		      nTp += offsetParent.offsetTop;
		      offsetParent = offsetParent.offsetParent;
		    }
		    return nTp;
	    }
	});
});