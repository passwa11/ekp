define([
  "dojo/_base/declare",
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
  "mui/history/listener",
  "mui/i18n/i18n!sys-lbpmservice",
  "mui/i18n/i18n!sys-lbpmservice:lbpmSetting.approve",
  "mui/dialog/Tip",
  "dojo/html",
  "dojo/dom",
  "dojo/dom-style",
  "dijit/registry",
  "dojo/topic"
], function(
  declare,
  array,
  lang,
  win,
  domConstruct,
  query,
  TransitionEvent,
  ViewController,
  viewRegistry,
  util,
  Dialog,
  TabBarButton,
  Deferred,
  listener,
  Msg,
  Msg2,
  Tip,
  html,
  dom,
  domStyle,
  registry,
  topic
) {
  var button = declare(
    "sys.lbpmservice.mobile.operation.OperationButton",
    [TabBarButton],
    {
      //起草人及已处理人操作
      icon1: "",

      extOptUrl: "/sys/lbpmservice/mobile/operation/extOperationViewer.jsp",

      processId: "",

      modelName: "",

      modelId: "",
      
      showType : "dialog",

      tmpl:
        '<div data-dojo-type="mui/form/RadioGroup" ' +
        "data-dojo-props=\"showStatus:'edit',renderType:'table',name:'_lbpm_opt_radio',mul:'false',store:{store},orient:'vertical'\"></div>",

      buildRendering: function() {
        if (!this.domNode) {
          this.domNode =
            this.srcNodeRef || this.ownerDocument.createElement("div")
        }
        var options = []
        var historyOptions = []
        var historyOptionsDefault = []
        var otherOptions = []
        this.tasks = []
        var _taskMap = {}
        var self = this
		var isHideNodeIdentifier = false
        if (lbpm && lbpm.settingInfo){
        	if (lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
        		isHideNodeIdentifier = true;
        	}
        }
        array.forEach(window.LBPM_CurOperationCfg, function(task, idx) {
          if (task["taskFrom"] == "node") {
            array.forEach(task.operations, function(operation, i) {
              if (
                operation.operationHandlerType == "drafter" ||
                operation.operationHandlerType == "historyhandler" ||
                operation.operationHandlerType == "branchadmin"
              ) {
            	 if(operation.operationHandlerType == "historyhandler"){
            		 var historyStr = Msg["mui.operation.processed"] + ":";

            		 if(operation.operationType == "history_handler_back"){
            			 historyOptionsDefault.push({
                             text: historyStr + operation.operationName,
							 value: task.taskId + ":" + operation.operationType

                         }) 
            		 }else{
            			 historyOptions.push({
                             text: historyStr + operation.operationName,
							 value: task.taskId + ":" + operation.operationType
                         }) 
            		 }
                     
            	 }else{
            		 var OtherStr =
                         operation.operationHandlerType == "drafter"
                           ? Msg["mui.operation.drafter"] + ":"
                           : Msg["mui.operation.branchadmin"] + ":";
                      otherOptions.push({
                         text: OtherStr + operation.operationName,
						  value: task.taskId + ":" + operation.operationType
                      })
            	 }
               
              }
            })
            
            var curNodeInfo = null
            array.forEach(window.LBPM_CurNodeCfg, function(nodeInfo) {
              if (task.nodeId == nodeInfo.nodeId) {
                curNodeInfo = nodeInfo
              }
            })
			var valueStr = task.nodeId + "." + curNodeInfo.nodeName;
			if(isHideNodeIdentifier){
				valueStr = curNodeInfo.nodeName;
			}
            var taskObj = {
            	text: valueStr,
              	value: task.taskId
            }

            self.tasks.push(taskObj)
            _taskMap[task.taskId] = taskObj
          }
        })
        options = historyOptionsDefault.concat(historyOptions.concat(otherOptions));
        if (options.length > 0) {
          this.label = Msg["mui.operation.process"]
          this.icon1 = ""
          if (this.tasks.length > 1) {
            array.forEach(options, function(opt, idx) {
              var opts = opt.value.split(":")
              opt["text"] = _taskMap[opts[0]].text + "." + opt["text"]
            })
          }
          this.dialogDom = domConstruct.toDom(
            lang.replace(this.tmpl, {
              store: JSON.stringify(options).replace(/\"/g, "'")
            })
          )
        }
        this.inherited(arguments)
      },

      _onClick: function(evt) {
    	//#115011 修复多次点击弹出多层
		if (this.dialog)
			return;
        evt.preventDefault()
        evt.stopPropagation()
        
         //避免ios kk 双击
		var nowTime = new Date().getTime();
        var clickTime = this.cbtime;
        if (clickTime != "undefined" && nowTime - clickTime < 500) {
           return false;
        }
        this.cbtime = nowTime;
        
        this.defer(function() {
          var self = this
          this.dialog = Dialog.element({
            canClose: false,
            showClass: "muiDialogElementShow muiFormSelect",
            element: this.dialogDom,
            position: "bottom",
            scrollable: false,
            parseable: true,
            callback: lang.hitch(this, function() {
				this.dialog = null;
			}),
            onDrawed: function() {
              this.defer(function() {
                self.initDialog()
              }, 200)
            }
          })
        }, 500)
      },

      initDialog: function() {
        var self = this
        this._curTime = 0
        query(".muiRadioTableItem", this.dialogDom).on("click", function(evt) {
          evt.preventDefault()
          evt.stopPropagation()
          
          var curTime = new Date()
          if (curTime - self._curTime < 500) {
            //防止iphone双击
            return
          }
          
          self._curTime = curTime
          var srcDom = evt.target
          var fieldObj
          if (srcDom.className == "muiRadioTableItem") {
            fieldObj = srcDom
          } else {
            fieldObj = query(evt.target).parents(".muiRadioTableItem")[0]
          }
          
          var fieldObjValue=query("input", fieldObj).val();
          self.currentOprText = query(".muiRadioTableText", fieldObj).text();
          
          if(fieldObjValue&&fieldObjValue!=""){
        	  var fieldSplit = fieldObjValue.split(':')
        	  if(fieldSplit.length>1){
        		  var handlerType=fieldSplit[1];
        		  var handlerTaskId=fieldSplit[0];
        		  if(handlerType=="history_handler_press"||handlerType=="drafter_press"){
        			  var pressFlag=false;
        			  $.ajax({
        					url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=pressNodeIdJson",
        					async: false,
        					data: {workItemId:handlerTaskId},
        					type: "POST",
        					dataType: 'json',
        					success: function (data) {
        						if(data.pressTimes>0){
        							pressFlag=true;
        						}else{
        							pressFlag=false;
        						}
        					},
        					error: function (er) {
        						if(console){
        							console.log(er);
        						}			
        					}
        				});	
        			  
        			  if(pressFlag){
        				  //Tip.tip({text:Msg2["lbpmSetting.approve.ReminderCooldown.noPress"],icon:'mui mui-wrong'});
        				  Tip["warn"]({text:Msg2["lbpmSetting.approve.ReminderCooldown.noPress"]});
        				  return false;
        			  }
        		  }
        	  }
          }
          
          self.selOperation = fieldObjValue //不需要key信息
          self._afterSelect(function() {
            self.dialog.hide()
          })
        })
          //#127275【A计划回归测试 - 流程管理】起草人提交文档后，移动端流程处理，样式下划线建议加全
          if (dojoConfig.dingXForm === "true") {
              $(".muiDialogElementContent_bottom").children().removeClass("muiFormLeft");
          }
      },

      _afterSelect: function(callback) {
    	  var view = null
    	if(this.showType != 'dialog'){
    		if (this.backTo != null) {
	          view = viewRegistry.hash[this.backTo]
	        } else {
	          view = viewRegistry.getEnclosingView(this.domNode)
	          view = viewRegistry.getParentView(view) || view
	          this.backTo = view.id
	        }
    	}else{
    		view = viewRegistry.getEnclosingView(this.domNode)
	        view = viewRegistry.getParentView(view) || view
    	}
        var _extView = viewRegistry.hash["OperationView"]
        var OperationViewWgt = registry.byId("OperationView");
        if(OperationViewWgt){
        	if(this.selOperation != OperationViewWgt.selOperation){
        		OperationViewWgt.destroy();
        		_extView = null;
        	}
        }
        if (_extView == null) {
          var url = util.formatUrl(this.extOptUrl)
          url = util.setUrlParameter(url, "backTo", view.id)
          url = util.setUrlParameter(url, "processId", this.processId)
          url = util.setUrlParameter(url, "modelName", this.modelName)
          url = util.setUrlParameter(url, "modelId", this.modelId)
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
	    	              extView.selOperation = _selfObj.selOperation;
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
        	  var vc = ViewController.getInstance()
        	  vc.openExternalView(
	            {
	              url: url,
	              transition: "slide"
	            },
	            view.domNode.parentNode
	          ).then(function() {
	            var extView = viewRegistry.hash["OperationView"]
	            if (extView && extView.initExtOpertion) {
	              extView.initExtOpertion(_selfObj)
	              if (callback) callback()
	            }
	          }) 
          }
        } else {
          if(this.showType == 'dialog'){
        	  if (_extView.initExtOpertion) {
	              _extView.initExtOpertion(this)
	              if (callback) callback()
	            }
        	  this.showOperationDialog();
          }else{
        	  view.performTransition(_extView.id, 1, "slide")
        	  if (_extView.initExtOpertion) {
	            _extView.initExtOpertion(this)
	            if (callback) callback()
	          }
          }
        }
        var _self = this;
        window.Com_BeforeSubmitDeferred = lang.hitch(this,this.defaultDeferred);
        if(this.showType != 'dialog'){
        	listener.add({
	            callback: function() {
	              new TransitionEvent(win.body(), {
	                moveTo: _self.backTo,
	                transition: "slide",
	                transitionDir: -1
	              }).dispatch()
	              window.Com_BeforeSubmitDeferred = null
	            }
	          })
        }
      },
      
      showOperationDialog : function(){
    	  	this.operationView = dom.byId("OperationView");
			this.dialogContentNode = domConstruct.create('div');
			domConstruct.place(dom.byId("lbpmExtOperContent"),this.dialogContentNode,'first');
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
					domConstruct.place(query("#lbpmExtOperContent",evt.element)[0],this.operationView,'first');
					window.Com_BeforeSubmitDeferred = null;
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
					 var lbpmOperationTabBar = registry.byId("lbpmExtOperationTabBar");
					 if(lbpmOperationTabBar){
						 lbpmOperationTabBar.resize();
					 }
					 var lbpmViewOperationTabBar = registry.byId("lbpmViewOperationTabBar");
					 if(lbpmViewOperationTabBar){
						 lbpmViewOperationTabBar.resize();
					 }
					 //重置视图的大小
					 var OperationViewWgt = registry.byId("OperationView");
					 if(OperationViewWgt){
						 OperationViewWgt.resize();
					 }
				})
			});
		},
		
		_destroyDialog:function(evt){
			if (this.dialog && this.dialog.callback)
				this.dialog.callback(window, this.dialog);
			this.lastDialog.hide();
			this.lastDialog = null;
		},
		
		_hideDialog:function(evt){
			if(this.dialog){
				domStyle.set(this.dialog.domNode,{
					"display":"none"
				})
			}
			if(dom.byId("OperationView")){
				domStyle.set(dom.byId("OperationView"),{
					"display":"none"
				})
			}
		},

      defaultDeferred: function() {
    	  	var defer = new Deferred();
			defer.resolve();
			if(this.showType != 'dialog'){
				history.back();
			}else{
				this._hideDialog();
			}
			return defer;
      },
      
      _showDialog:function(){
    	  if(this.dialog){
  			domStyle.set(this.dialog.domNode,{
  				"display":"block"
  			})
      	  }
      	  if(dom.byId("OperationView")){
  			domStyle.set(dom.byId("OperationView"),{
  				"display":"block"
  			})
      	  }
      },

      postCreate: function() {
        this.inherited(arguments)
        if(this.showType == 'dialog'){
			this.subscribe("/lbpm/extOperation/destroyDialog","_destroyDialog");
			this.subscribe("/lbpm/operation/hideDialog","_hideDialog");
			this.subscribe("/lbpm/operation/showDialog","_showDialog");
		}
      },

      startup: function() {
        this.inherited(arguments)
      }
    }
  )
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
  return button
})
