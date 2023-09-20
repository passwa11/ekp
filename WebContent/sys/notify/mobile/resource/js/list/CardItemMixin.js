define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/on",
    "mui/dialog/Tip",
   	"mui/util",
   	"dojo/_base/lang",
   	"mui/device/adapter",
   	"mui/device/device",
   	"dojo/request",
   	"dojo/topic",
   	"dojox/mobile/_ItemBase",
   	"dojox/mobile/viewRegistry",
   	"mui/i18n/i18n!sys-notify:sysNotifyTodo.mobile.list.button"
	], function(declare, domConstruct, domClass, on, Tip, util, lang, adapter, device, request, topic, ItemBase, viewRegistry, buttonMsg) {
	var item = declare("sys.notify.list.item.CardItemMixin", [ItemBase], {

	      buildRendering: function() {
	    	  this.inherited(arguments);
	    	  this.domNode = domConstruct.create('li', { className : 'muiNotifyCardItem' },this.containerNode);
	          this.buildNotifyContentRender();
	          this.buildNotifyOperateBtnRender();
	      },
	      
	      
		 /**
		  * 构建基本信息
		  */
	      buildNotifyContentRender: function() {
	    	  
	    	  // 消息展示外层容器DOM
	          var notifyContentContainer = domConstruct.create("div", {className: "muiNotifyContentContainer"}, this.domNode);

	          if(this.href){
	        	  // 绑定点击跳转事件
		          this._bindGoToDetailPageClick(notifyContentContainer);
	          }else{
	        	  // 添加锁定样式
	        	  domClass.add(this.domNode,'muiNotifyCardItemLock');
	        	  // 绑定点击弹出锁定tip提醒: “暂不支持移动访问”
	        	  this.makeLockLinkTip(notifyContentContainer);
	          }
	          
	        	
	          // 消息标题
	          var notifyTitleDom = domConstruct.create("div", {className: "muiNotifyTitleLabel", innerHTML:this.label }, notifyContentContainer);
	          
	          // 消息基本信息容器DOM（展示：人员头像、人员姓名、时间日期、模块名称、流程节点名称）
	          var muiNotifyBaseInfo = domConstruct.create("div", {className: "muiNotifyBaseInfo"}, notifyContentContainer);
	          
			  // 人员头像图标
			  if(this.icon){
				 var personHeadIconNode = domConstruct.create('div', {className : 'muiNotifyPersonHeadIcon'}, muiNotifyBaseInfo);
				 domConstruct.create('img', {src :util.formatUrl(this.icon),className : 'muiNotifyPersonHeadImg'}, personHeadIconNode);
			  }
			  
			  // 基本信息文本容器DOM
			  var textInfoNode = domConstruct.create('div', {className : 'muiNotifyTextInfo'}, muiNotifyBaseInfo);
			  
			  // 人员姓名
			  if(this.creator){
				var creatorNode = domConstruct.create('span', { innerHTML:this.creator, className:'muiNotifyPersonName' }, textInfoNode);
			  }
			  
			  // 时间日期
			  if(this.created){
				var createdNode = domConstruct.create('span', { innerHTML:this.created, className:'muiNotifyDate' }, textInfoNode);
			  }
			  
			  // 业务模块名称
			  if(this.modelNameText){
				var modelNode = domConstruct.create('span', { innerHTML:this.modelNameText, className:'muiNotifyModelName' }, textInfoNode);
			  }
			  
			  // 流程节点状态
	          if (this.lbpmCurrNodeValue) {
	        	  var processStatusNode = domConstruct.create('span', { innerHTML:this.lbpmCurrNodeValue, className:'muiNotifyProcessStatus' }, textInfoNode);
	          }
	          
	      },
	      
	      
		 /**
		  * 根据类型（待办、待阅）构建操作按钮
		  */	      
	      buildNotifyOperateBtnRender: function() {
	    	  
	    	  /*----- 待办  -----*/
	    	  if((this.fdType=="1"||this.fdType=="3")&&this.isDone=="false"&&this.href){
		    	  // 操作按钮展示外层容器DOM
		          var notifyOperateBtnContainer = domConstruct.create("div", {className: "muiNotifyOperateBtnContainer"}, this.domNode);	

		          // “前往” 按钮
		          var goToBtn = domConstruct.create("div", {className: "muiNotifyOperateBtn muiNotifyGoToBtn"}, notifyOperateBtnContainer);
		          goToBtn.innerText = buttonMsg["sysNotifyTodo.mobile.list.button.goTo"];
		          
		          // 绑定“前往” 按钮点击跳转事件
		          this._bindGoToDetailPageClick(goToBtn);  

	    	  }
	    	  
	    	  /*----- 待阅  -----*/
	    	  if(this.fdType=="2"&&this.isDone=="false"){
		    	  // 操作按钮展示外层容器DOM
		          var notifyOperateBtnContainer = domConstruct.create("div", {className: "muiNotifyOperateBtnContainer"}, this.domNode);	
                  
		          if(this.href){
			          // “前往” 按钮
			          var goToBtn = domConstruct.create("div", {className: "muiNotifyOperateBtn muiNotifyGoToBtn"}, notifyOperateBtnContainer);
			          goToBtn.innerText = buttonMsg["sysNotifyTodo.mobile.list.button.goTo"];
			          
			          // 绑定“前往” 按钮点击跳转事件
			          this._bindGoToDetailPageClick(goToBtn);		          

		          }

		          // “置为已阅” 按钮
		          var setReadBtn = domConstruct.create("div", {className: "muiNotifyOperateBtn muiNotifySetReadBtn"}, notifyOperateBtnContainer);
		          setReadBtn.innerText = buttonMsg["sysNotifyTodo.mobile.list.button.setRead"];
		          
		          // 绑定  “置为已阅” 按钮点击事件
		          this.connect(setReadBtn, "click",lang.hitch(this._onSetReadBtnClick));
	    	  }
	    	  
	      },
	      
	      
		  /**
		   * 绑定跳转明细页面click事件
		   * @param element DOM对象 
		   */
	      _bindGoToDetailPageClick: function(element){
	    		 // 直接跳转至明细页面 
				 this.connect(element, 'click', lang.hitch(this, function() {
                    adapter.open(this.href, '_self');
				 }));
	      },
	      
	      
		  /**
		   * “置为已阅” 按钮click响应函数
		   * @param evt Event对象 
		   */
	      _onSetReadBtnClick : function(evt){    	  
              this._removeNotify();
	      }, 
	      
	     
		  /**
		   * 删除待办
		   */	      
	     _removeNotify: function(){
	    	  var self = this;
	    	  var setReadUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=deleteall&s_ajax=true";
			  request.post(util.formatUrl(setReadUrl), {
	                headers: {'Accept': 'application/json'},
	                handleAs: 'json',
					data : {
						"List_Selected" : [this.fdId]
					}
			  }).response.then(function(result) {
				  if( result.status==200 && result.data.status==true ){
					  // 刷新列表数据
					  self.getParent().reload();
					  // 更新角标数字
					  topic.publish("/mui/list/onRefreshNavCount", self.getParent().getParent());
				  }
		      }); 
	     },

	     
	      makeLockLinkTip: function(linkNode) {
	          on(linkNode, "click", function(evt) {
	            Tip.tip({icon: "mui mui-warn", text: "暂不支持移动访问"});
	          });
	      },
	      
	      _setLabelAttr: function(text){
				if(text)
					this._set("label", text);
		  }
		
	});
	return item;
});