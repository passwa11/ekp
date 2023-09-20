define([ "dojo/_base/declare",
         "dojo/_base/array",
         "dojo/_base/lang",
         "dojo/_base/window",
         "dojo/dom-construct",
         "dojo/query",
         "dojox/mobile/TransitionEvent", 
         "dojox/mobile/ViewController",
         "dojox/mobile/viewRegistry",
         "mui/util",
         "mui/dialog/Dialog",
         "mui/tabbar/TabBarButton",
         "dojo/Deferred",
         "mui/device/adapter",
         "mui/history/listener",
         "dojo/html",
         "dojo/dom",
         "dojo/dom-style",
         "dijit/registry",
         "dojo/topic"],
         function(declare, array, lang, win, domConstruct, query, TransitionEvent, ViewController, viewRegistry, util, Dialog, TabBarButton, Deferred, adapter, listener,html,dom,domStyle,registry,topic){
	var button = declare("sys.lbpmservice.mobile.operation.ExtOperationButton", [TabBarButton], {
		//此组件是兼容新老流程操作页面的按钮组件
		icon1:"",
		
		extOptUrl:"/sys/lbpmservice/mobile/operation/extOperationViewer.jsp",
		
		processId:"",
		
		modelName:"",
		
		modelId:"",
		
		handleView:"",
		
		showType : "dialog",
		
		tmpl : '<div data-dojo-type="mui/form/RadioGroup" ' + 
			'data-dojo-props="showStatus:\'edit\',name:\'_lbpm_opt_radio\',mul:\'false\',store:{store},orient:\'vertical\'"></div>',
		
		buildRendering: function() {
			if(!this.domNode){
				this.domNode = this.srcNodeRef || this.ownerDocument.createElement("div");
			}
			this.icon1 = "";
			var handleOptions = [];
			var extOptions = [];
			var handleTask = 0;
			var extTask = 0;
			this.tasks = {};
			var self = this;
			array.forEach(window.LBPM_CurOperationCfg,function(task,idx){
				if(task['taskFrom'] == 'workitem'){
					array.forEach(task.operations,function(operation, i){
						if(operation.operationHandlerType=='handler'){
							var optStr = "处理人:";
							handleOptions.push({
								'text':optStr + operation.operationName, 
								'value': task.taskId + ":" + operation.operationType,
								'optName':operation.operationType + ":" + operation.operationName
								});
						}
					});
					var curNodeInfo = null;
					array.forEach(window.LBPM_CurNodeCfg,function(nodeInfo){
						if(task.nodeId==nodeInfo.nodeId){
							curNodeInfo = nodeInfo;
						}
					});
					var extInfo = (task.parentHandlerName)?(task.parentHandlerName +":"):"";
					extInfo = task.nodeId + '.' + extInfo + curNodeInfo.nodeName + "(" + task.expectedName+')';
					var taskObj = {'text': extInfo, 'value':task.taskId, "optView":self.handleView,"nodeInfo":task.nodeId + '.'+curNodeInfo.nodeName,"taskFrom":"workitem"};
					self.tasks[task.taskId] = taskObj;
					handleTask++;
				}
				if(task['taskFrom'] == 'node'){
					array.forEach(task.operations,function(operation, i){
						if(operation.operationHandlerType=='drafter'||operation.operationHandlerType=='historyhandler' || operation.operationHandlerType=='branchadmin'){
							var optStr = operation.operationHandlerType=='drafter'?"起草人:":(operation.operationHandlerType=='branchadmin'?"分支特权人:":"已处理:");
							extOptions.push({
								'text':optStr + operation.operationName, 
								'value': task.taskId + ":" + operation.operationType,
								'optName':operation.operationType + ":" + operation.operationName
							});
						}
					});
					var curNodeInfo = null;
					array.forEach(window.LBPM_CurNodeCfg,function(nodeInfo){
						if(task.nodeId == nodeInfo.nodeId){
							curNodeInfo = nodeInfo;
						}
					});
					var taskObj = {'text': task.nodeId + "." + curNodeInfo.nodeName,'value':task.taskId,
							"optView":"OperationView","nodeInfo":task.nodeId + '.'+curNodeInfo.nodeName,"taskFrom":"node"};
					self.tasks[task.taskId] = taskObj;
					extTask++;
				}
			});
			if(handleOptions.length>0 || extOptions.length>0){
				this.label = "流程处理";
				if(handleTask>1){
					array.forEach(handleOptions,function(opt,idx){
						var opts = opt.value.split(":");
						opt['text'] = self.tasks[opts[0]].text + "." + opt['text'];
					});
				}
				if(extTask>1){
					array.forEach(extOptions,function(opt,idx){
						var opts = opt.value.split(":");
						opt['text'] = self.tasks[opts[0]].text + "." + opt['text'];
					});
				}
				this.options = handleOptions.concat(extOptions);
				this.dialogDom = domConstruct.toDom(lang.replace(this.tmpl,
						{store:JSON.stringify(this.options).replace(/\"/g,"\'")}));
				
			}
			this.inherited(arguments);
		},
		
		_onClick: function(evt){
			evt.preventDefault();
			evt.stopPropagation();
			this.defer(function(){
				var self = this;
				this.dialog = Dialog.element({
					canClose : false,
					showClass : 'muiDialogElementShow muiFormSelect',
					element:this.dialogDom,
					position:'bottom',
					'scrollable' : false,
					'parseable' : true,
					onDrawed:function(){
						self.initDialog();
					}
				});
			},500);
		},
		
		initDialog:function(){
			var self = this;
			this._curTime = 0;
			query(".muiRadioItem",this.dialogDom).on("click",function(evt){
				evt.preventDefault();
				evt.stopPropagation();
				var curTime = new Date();
				if(curTime - self._curTime<500){	//防止iphone双击
					return;
				}
				self._curTime = curTime;
				var srcDom = evt.target;
				var fieldObj;
				if(srcDom.className!='' && srcDom.className.indexOf('muiRadioItem')>-1){
					fieldObj = srcDom;
				}else{
					fieldObj = query(evt.target).parents(".muiRadioItem")[0];
				}
				self.selOperation = query("input",fieldObj).val();//不需要key信息
				self._afterSelect(function(){
					self.dialog.hide();
				});
			});
		},
		
		_afterSelect:function(callback){
			if(!this.selOperation){
				return;
			}
			var opts = this.selOperation.split(":");
			var taskId = opts[0];
			var optType = opts[1];
			var forwardView = this.tasks[taskId].optView;
			var view = null;
			var _selfObj = this;
	    	if(this.showType != 'dialog'){
	    		if(this.backTo!=null){
					view = viewRegistry.hash[this.backTo];
				}else{
					view = viewRegistry.getEnclosingView(this.domNode);
					view = viewRegistry.getParentView(view) || view;
					this.backTo = view.id;
				}
	    	}else{
	    		view = viewRegistry.getEnclosingView(this.domNode);
				view = viewRegistry.getParentView(view) || view;
				
				//如果是弹窗类型，要先进行校验（兼容表单查看页编辑）
				if(this.showType == 'dialog'){
					if(view.validate && !view.validate()){
						return;
					}
				}
	    	}
			var _extView = viewRegistry.hash[forwardView];
			if(_extView == null){
				var url = util.formatUrl(this.extOptUrl);
				url = util.setUrlParameter(url, "backTo", view.id);
				url = util.setUrlParameter(url, "processId", this.processId);
				url = util.setUrlParameter(url, "modelName", this.modelName);
				url = util.setUrlParameter(url, "modelId", this.modelId);
				var _selfObj = this
		        if(this.showType == 'dialog'){
		        	  url = "dojo/text!" + url;
		        	  this.viewNode = domConstruct.create('div');
		        	  domConstruct.place(this.viewNode,view.domNode.parentNode,'last');
		        	  require([url], function(tmplStr) {
		        		  	var dhs = new html._ContentSetter({
			    	          node: _selfObj.viewNode,
			    	          parseContent: true,
			    	          cleanContent: false
			    	        })
			
			    	        dhs.set(tmplStr)
			    	        dhs.parseDeferred.then(function(results) {
			    	        	var extView = viewRegistry.hash["OperationView"]
			    	            if (extView && extView.initExtOpertion) {
			    	              extView.initExtOpertion(_selfObj)
			    	              if (callback) callback()
			    	            }
			    	        }).then(function(){
			    	        	_selfObj.defer(function(){
			    	        		//初始化完数据后显示弹窗
				    	        	_selfObj.showOperationDialog();
			    	        	},300)
			    	        });
			    	        dhs.tearDown();
		        	  })
		        }else{
		        	var vc = ViewController.getInstance();
					vc.openExternalView({
						url:url,
						transition: "slide"
					}, view.domNode.parentNode).then(function(){
						var extView = viewRegistry.hash["OperationView"];
						if(extView && extView.initExtOpertion){
							extView.initExtOpertion(_selfObj);
							if(callback) callback();
						}
					});
		        }
			}else{
				if(this.showType != 'dialog'){
					view.performTransition(_extView.id, 1, "slide");
				}
				if(forwardView=="OperationView"){
					if(_extView.initExtOpertion){
						_extView.initExtOpertion(this);
						if(callback) callback();
					}
				}else{
					var curOpt = ""; 
					array.forEach(this.options,function(opt){
						if(opt.value == _selfObj.selOperation){
							curOpt = opt.optName;
						}
					});
					query("#operationMethodsGroup")[0].value = curOpt;
					lbpm.globals.clickOperation(curOpt);
					array.forEach(lbpm.processorInfoObj,function(infoObj,idx){
						if(taskId==infoObj.id){
							lbpm.nowProcessorInfoObj = infoObj;
							query("#operationItemsRow .detailNode").html(_selfObj.tasks[taskId].text);
							query("#operationItemsSelect")[0].value = idx;
						}
					});
					var operations = curOpt.split(":");
					query("#lbpmOperationMethodTable .detailNode").html(this.tasks[taskId].nodeInfo+ ":" + operations[1]);
					if(callback) callback();
				}
				if(this.showType == 'dialog'){
					this.showOperationDialog();
				}
			}
			var _self=this;
			window.Com_BeforeSubmitDeferred = lang.hitch(this,this.defaultDeferred);
			if(this.showType != 'dialog'){
				listener.add({callback:function(){
					new TransitionEvent(win.body(), {moveTo: _self.backTo, transition: "slide", transitionDir: -1}).dispatch();
				}});
			}
		},
		
		showOperationDialog : function(){
			var opts = this.selOperation.split(":");
			var taskId = opts[0];
			var optType = opts[1];
    	  	this.operationView = this.tasks[taskId].optView;
			this.dialogContentNode = domConstruct.create('div');
			var contentId = "lbpmOperContent";
			if(this.tasks[taskId].taskFrom == "node"){
				contentId = "lbpmExtOperContent";
			}
			domConstruct.place(dom.byId(contentId),this.dialogContentNode,'first');
			var buttons = [];
			this.dialog = Dialog.element({
				title:this.currentOprText || '',
				canClose : false,
				element : this.dialogContentNode,
				buttons : buttons,
				position:'bottom',
				appendTo:this.operationView,
				'scrollable' : false,
				'parseable' : false,
				showClass : 'muiLbpmDialog',
				callback : lang.hitch(this, function(win,evt) {
					domStyle.set(this.operationView,{
						"display":"none",
						"position":"",
						"z-index":""
					})
					domStyle.set(query("body")[0],{
						"overflow-y":""
					})
					domConstruct.place(query("#"+contentId,evt.element)[0],this.operationView,'first');
					this.lastDialog = this.dialog;
					this.dialog = null;
				}),
				onDrawed:lang.hitch(this, function(evt) {
					domStyle.set(this.operationView,{
						"display":"block",
						"position":"fixed",
						"bottom":"0",
						"z-index":"100"
					})
					domStyle.set(query("body")[0],{
						"overflow-y":"hidden"
					})
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
					 //重置按钮宽度
					 var lbpmOperationTabBar = registry.byId("lbpmViewOperationTabBar");
					 lbpmOperationTabBar.resize();
				})
			});
		},
		
		defaultDeferred: function(){
			var defer = new Deferred();
			defer.resolve();
			if(this.showType != 'dialog'){
				history.back();
			}else{
				this._hideDialog();
			}
			return defer;
		},
		
		postCreate: function() {
			this.inherited(arguments);
			if(this.showType == 'dialog'){
				this.subscribe("/lbpm/operation/hideDialog","_hideDialog");
				this.subscribe("/lbpm/operation/showDialog","_showDialog");
				this.subscribe("/lbpm/extOperation/destroyDialog","_destroyDialog");
				this.subscribe("/mui/Category/valueChange","_toHandlerValChange");
				this.subscribe("/lbpm/operation/toHandlerAddress/parseFinish","_toSelectCate")
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
			this.defer(function(){
				domStyle.set(dom.byId(this.handleView),{
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
			if(this.handleView && dom.byId(this.handleView)){
				domStyle.set(dom.byId(this.handleView),{
					"display":"none"
				})
			}else if(this.dialog){
				domStyle.set(this.dialog.domNode,{
					"display":"none"
				})
			}
		},
		
		_destroyDialog:function(evt){
			if (this.dialog && this.dialog.callback)
				this.dialog.callback(window, this.dialog);
			this.lastDialog.hide();
			this.lastDialog = null;
		},
		
		startup: function() {
			this.inherited(arguments);
		}
	});
	window.closeExtDialog = function(evt){
		evt = evt || window.event || arguments[0];
        evt.cancelBubble = true;
        if (evt.preventDefault) {
         	evt.preventDefault();
        }
        if (evt.stopPropagation) {
          	evt.stopPropagation();
        }
        var nowTime = new Date().getTime();
        var clickTime = window.ctime;
        if (clickTime != "undefined" && nowTime - clickTime < 500) {
           return false;
        }
        window.ctime = nowTime;
		topic.publish("/lbpm/extOperation/destroyDialog");
	}
	return button;
});
