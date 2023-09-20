define([
	"dojo/_base/declare",
	"dijit/_WidgetBase",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/topic",
	"dojo/html",
	"mui/util",
	"dojo/request",
	"mui/dialog/Dialog",
	"dojo/query",
	"dojo/dom-style",
	"dijit/registry",
	"mui/form/validate/Validation",
	"mui/dialog/Tip",
	"dojo/_base/lang",
	"mui/i18n/i18n!sys-lbpmservice:lbpmNode"
],function(declare,WidgetBase,domConstruct,domClass,topic,html, util, request, Dialog, query, domStyle,registry,Validation,Tip,lang, msg1){
	return declare("sys.lbpmservice.mobile.lbpmSummaryApproval.LbpmSummarySelection", [WidgetBase], {
		selArr:[],//选中的id
		
		selItemArr:[],//选中的对象
		
		templURL:"/sys/lbpmservice/mobile/lbpm_summary_approval/dialog.jsp",
		
		nodeType:"",
		
		buildRendering: function() {
	      this.inherited(arguments)
	      this.containerNode = domConstruct.create(
	        "div",
	        {className: "muiLbpmSummarySecContainer"},
	        this.domNode
	      )
	      // 左侧
	      this.leftArea = domConstruct.create(
	        "div",
	        {className: "muiLbpmSummarySecLeft"},
	        this.containerNode
	      )
	      this.selectArea = domConstruct.create(
	    	"div",
	        {
	           className: "muiLbpmSummarySelArea"
	        },
	        this.leftArea,
	        "first"
	      ) //用于占位
	      this.selectNode = domConstruct.create(
	    	"div",
	    	{
	    		className: "muiLbpmSummarySel"
	    	},
	    	this.selectArea
	      )
	      this.selectTextNode = domConstruct.create(
	    	"div",
	    	{
	    		className: "muiLbpmSummarySelText",
	    		innerHTML:"全选"
	    	},
	    	this.selectArea
	      )
	      // 右侧按钮
	      this.rightArea = domConstruct.create(
	        "div",
	        {className: "muiLbpmSummarySecRight"},
	        this.containerNode
	      )
	      if(this.nodeType != 'signNode' && this.isBatchReject == "true"){
	    	  this.batchRejectButtonNode = domConstruct.create(
		        "span",
		        {
		          className: "muiLbpmSummaryBatchBtn muiLbpmSummaryBatchRejectBtn",
		          innerHTML: "批量驳回"
		        },
		        this.rightArea
		      )
		      this.connect(this.batchRejectButtonNode, "click", "_batchHandlerReject");
	      }
	      if(this.isBatchApprove == "true"){
	    	  this.batchPassButtonNode = domConstruct.create(
		        "span",
		        {
		          className: "muiLbpmSummaryBatchBtn muiLbpmSummaryBatchPassBtn ",
		          innerHTML: "批量通过"
		        },
		        this.rightArea
		      )
	      }
	      
	      //绑定dom事件
	      this.connect(this.selectArea, "click", "_selectCate")
	      this.connect(this.batchPassButtonNode, "click", "_batchHandlerPass");
	    },
	    
	    postCreate : function(){
		  this.subscribe("/mui/lbpmSummary/destoryDialog", "_destoryDialog");
		  this.subscribe("/mui/lbpmSummary/approve","_approveMany");
		  this.subscribe("/mui/lbpmSummary/selection/reset","_resetSel");
	    },
	    
	    _selectCate: function(evt) {
	    	 if (this.checkedIcon) {
	              domClass.remove(this.selectNode, "muiLbpmSummarySeled");
	              domConstruct.destroy(this.checkedIcon);
	              this.checkedIcon = null;
	              //发出事件
	 	    	 topic.publish("/mui/lbpmSummary/selected",{"oprType":"unselected"});
	    	 }else{
	    		 this.checkedIcon = domConstruct.create(
		            "i",
		            {
		              className: "mui mui-checked muiLbpmSummarySelected"
		            },
		            this.selectNode
		        )
		        domClass.add(this.selectNode, "muiLbpmSummarySeled")
		        topic.publish("/mui/lbpmSummary/selected",{"oprType":"selected"});
	    	 }
	    },
	    
	    //重置全选
	    _resetSel:function(){
	    	domClass.remove(this.selectNode, "muiLbpmSummarySeled");
            domConstruct.destroy(this.checkedIcon);
            this.checkedIcon = null;
            this.selArr = [];
            this.selIds = [];
            this.selItemArr = [];
	    },
	    
	    _batchHandlerPass:function(){
	    	//过滤没有对应操作的数据
    		this.selIds = [];
    		for(var i=0; i<this.selItemArr.length; i++){
    			var selItem = this.selItemArr[i];
    			if(selItem.btn.isFastApprove){
    				this.selIds.push(selItem.fdId);
    			}
    		}
	    	if(this.selIds.length == 0){
	    		Tip['warn']({text:"您没有选择需要通过的数据"});
	    		return;
	    	}
	    	 window.currentOperationType = "handler_pass";
			 this.initOperationDefaultUsage();
			 this.fdUsageContentValue = window.defaultUsageContent;
			 this.processInfoClassName = "";
			 this.processName = "";
			 this.processId = "";
			 this.opType = "pass";
			  
			 this.showDialog("批量通过");
	    },
	    
	    _batchHandlerReject:function(){
	    	//过滤没有对应操作的数据
    		this.selIds = [];
    		for(var i=0; i<this.selItemArr.length; i++){
    			var selItem = this.selItemArr[i];
    			if(selItem.btn.isFastReject){
    				this.selIds.push(selItem.fdId);
    			}
    		}
	    	if(this.selIds.length == 0){
	    		Tip['warn']({text:"您没有选择需要驳回的数据"});
	    		return;
	    	}
	    	 window.currentOperationType = "handler_refuse";
			 this.initOperationDefaultUsage();
			 this.fdUsageContentValue = window.defaultUsageContent_refuse;
			 this.processInfoClassName = "";
			 this.processName = "";
			 this.processId = "";
			 this.opType = "refuse";
			  
			 this.showDialog("批量驳回");
	    },
	    
	    initOperationDefaultUsage:function(){
			  if(window.initOperationDefaultUsage){//已经初始化过就不再初始化
				  return;
			  }
			  var _self = this;
			  var href = location.href;	
		      var fdNodeFactId = util.getUrlParameter(href, "fdNodeFactId");
		      var fdProcessId = this.selIds[0];
			  var url = "/sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=getOperationDefaultUsage&fdProcessId="+fdProcessId+"&fdNodeFactId="+fdNodeFactId;
			  url = util.formatUrl(url);
			  request.get(url,{handleAs:'json',sync:true}).then(
				function(data){
					//成功后回调
					if(data.length > 0){
						window.initOperationDefaultUsage = true;
						if(data[0].defaultUsageContent!=null){
							window.defaultUsageContent = unescape(data[0].defaultUsageContent ||  "").replace(/\n/g, "\\n");
						}
						if(data[0].defaultUsageContent_refuse!=null){
							window.defaultUsageContent_refuse = unescape(data[0].defaultUsageContent_refuse || "").replace(/\n/g, "\\n");
						}
						if(data[0].isPassContentRequired!=null){
							window.isPassContentRequired = unescape(data[0].isPassContentRequired || "").replace(/\n/g, "\\n");
						}
						if(data[0].isRefuseContentRequired!=null){
							window.isRefuseContentRequired = unescape(data[0].isRefuseContentRequired || "").replace(/\n/g, "\\n");
						}
					}
				},function(error){
			        //错误回调
					console.log(error);
				}
			 )
		 },
		 
		 //解析模板文件
		  _parser: function(){
			  var _self = this;
			  var url = util.urlResolver(this.templURL, this);
			  url = util.formatUrl(url);
			  url = "dojo/text!" + url;
			  if(registry.byId("fdUsageContent")){
				  registry.byId("fdUsageContent").destroy();
			  }
			  if(registry.byId("lbpmSummaryTabBar")){
				  registry.byId("lbpmSummaryTabBar").destroy(); 
			  }
			  
			  //创建审批弹窗的div
			  this.dialogContainerDiv = domConstruct.create(
		          "div",
		          {className: "muiApproveDiaglogContainer "}
		      )
			  
			  require([url], function(tmplStr) {
				  var dhs = new html._ContentSetter({
			          node: _self.dialogContainerDiv,
			          parseContent: true,
			          cleanContent: true,
			          onBegin: function() {
			            this.content = lang.replace(this.content, {categroy: _self})
			            this.inherited("onBegin", arguments)
			          }
			        })

			        dhs.set(tmplStr)
			        dhs.parseDeferred.then(function(results) {
			          _self.parseResults = results;
			          //构建校验对象
		          		var usageWdt = registry.byId("fdUsageContent");
		          		usageWdt._validation = new Validation();
		          		_self._addValidate(usageWdt);
			        })
			        dhs.tearDown() 
			  });
		 },
		 
		 showDialog:function(title){
			  this._parser();
			  this.dialog = Dialog.element({
				title:title || '',
				canClose : false,
				element : this.dialogContainerDiv,
				buttons : [],
				position:'bottom',
				'scrollable' : false,
				'parseable' : false,
				showClass : 'muiApproveDialog',
				callback : lang.hitch(this, function(win,evt) {
					domStyle.set(query("body")[0],{
						"overflow-y":""
					})
					this.dialog = null;
				}),
				onDrawed:lang.hitch(this, function(evt) {
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
					 if(registry.byId("lbpmSummaryTabBar")){
						 registry.byId("lbpmSummaryTabBar").resize();
					 }
				})
			});  
		 },
		 
		 _destoryDialog:function(){
			  if (this.dialog && this.dialog.callback){
				 this.dialog.hide();
				 this.dialog.callback(window, this.dialog);
			  }
		  },
		 
		 _approveMany:function(data){
			  var processId = data.processId;
			  if(processId){
				  return;
			  }
			  var opType = data.opType;
			  var _self = this;
			  var url = "/sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=approveMany";
			  url = util.formatUrl(url);
			  var params = {
				"processId" : this.selIds.join(';'),
				"opType" : opType,
				"usageContent":query("[name='fdUsageContent']")[0].value || ""
			  };
			  request.post(url,{data:params,handleAs:'json'}).then(
			  function(data){
			     //成功后回调
				  var code = data.code;
				  if(code == 1){//处理成功
					 Tip["success"]({text:"操作成功",time:400});
					 _self.defer(function(){
						 _self._destoryDialog();
						 topic.publish("/mui/lbpmSummary/reloadList");
						 _self._resetSel();
					 },500);
				  }else if(code == 2){
					  Tip["success"]({text:"有部分流程审批失败，请查看列表",time:400});
					  _self.defer(function(){
						 _self._destoryDialog();
						 topic.publish("/mui/lbpmSummary/reloadList");
						 _self._resetSel();
					  },500);
				  }
			  },function(error){
			     //错误回调
				  Tip["fail"]({text:"操作失败"});
			  });
		  },
		 
		 _addValidate:function(wgt){
			  var validates = {
				  'fdUsageContentNoLbpm':{//意见必填校验（没有流程的情况下，目前只支持处理人通过和驳回两种情况）
						error:msg1["lbpmNode.mustSignYourSuggestion"],
						test:function(v){
							if((window.currentOperationType == 'handler_pass' && window.isPassContentRequired) || 
									(window.currentOperationType == 'handler_refuse' && window.isRefuseContentRequired)){
								 var fdUsageContent = query("[name=fdUsageContent]")[0];
								 if(fdUsageContent && fdUsageContent.value){
									 return true;
								 }else{
									 return false;
								 }
							}
							return true;
						}
					},
					'usageContentMaxLenNoLbpm':{//意见长度校验（没有流程的情况下）
						error:msg1["lbpmNode.createDraft.opinion.maxLength"].replace(/\{name\}/, msg1["lbpmNode.createDraft.opinion"]).replace(/\{maxLength\}/, 4000),
						test:function(v){
							var fdUsageContent=query("[name=fdUsageContent]")[0];
							if (fdUsageContent != null && fdUsageContent.value != "") {
								var contentVal = fdUsageContent.value || "";
								var newvalue = contentVal.replace(/[^\x00-\xff]/g, "***");
								if (newvalue.length > 4000) {
									return false;
								}
							}
							return true;
						}
					}
			  };
			  for (var type in validates) {
				wgt._validation.addValidator(type, validates[type].error, validates[type].test);
			  }
		  }
	})
})