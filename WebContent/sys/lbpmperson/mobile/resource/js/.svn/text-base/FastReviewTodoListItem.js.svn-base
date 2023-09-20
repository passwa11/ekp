define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
	"dojo/topic",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin"
	], function(declare, domConstruct,domClass , domStyle , domAttr , topic, ItemBase , util,openProxyMixin) {
	var item = declare("sys.lbpmperson.mobile.FastReviewTodoListItem", [ItemBase,openProxyMixin], {
		tag:"li",

		//流程简要信息
		lbpmCurrNodeValue:"",
		
		//模块名称
		modelNameText:"",
		
		//创建时间
		created:"",
		
		//创建者
		creator:"",
		
		//创建人图像
		icon:"",
		
		//状态
		status:"",
		
		reviewOp:"",
		
		uuid:"",
		
		modelId:"",
		
		selected:false,
		
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create( this.tag, { className : 'muiFastReviewTodoListItem' });
			this.tableNode = domConstruct.create("div", {className: "muiFastReviewTodoContentTable"}, this.contentNode);
			this.tableLeftNode = domConstruct.create("div", {className: "muiFastReviewTodoContentLeft"}, this.tableNode);
			this.buildSelArea();
			this.tableRightNode = domConstruct.create("div", {className: "muiFastReviewTodoContentRight"}, this.tableNode);
	        this.buildContentRender();
	        this.buildOperateBtnRender();
			domConstruct.place(this.contentNode,this.containerNode);
		},
		
		buildSelArea:function(){
			this.selectNode = domConstruct.create("div", {className: "muiFastReviewTodoSelArea"}, this.tableLeftNode);
			this.connect(this.tableLeftNode, "click", "_selectCate");
		},
		
		_selectCate: function(evt) {
	        if (evt) {
	            if (evt.stopPropagation) evt.stopPropagation()
	            if (evt.cancelBubble) evt.cancelBubble = true
	            if (evt.preventDefault) evt.preventDefault()
	            if (evt.returnValue) evt.returnValue = false
	        }

			/* 连续点击不能超过500毫秒，防止快速双击
			（click事件未知原因会连续触发两次，下面的的 nowTime、clickTime的条件判断只是为了防止一次点击多次处理，是一种变通的方式来防止双击）*/
			var nowTime = new Date().getTime();
		    var lastClickTime = this.ctime;
		    if( (lastClickTime != 'undefined' && (nowTime - lastClickTime < 500)) || this.selectCateCalling ){
		        return false;
	        } else {
	        	this.ctime = new Date().getTime();
	        	this.selectCateCalling = true;
	            if (this.selectNode) {
	                //存在选择区域时设置是否选中
	                if (this.checkedIcon != null) {
	                    this._toggleSelect(false);
	                } else {
	                    this._toggleSelect(true);
	                }
	            }
	            this.ctime = new Date().getTime();
	            this.selectCateCalling = false;
	    	    return true;
	        }
	    },
		
	    _cancelSelected: function() {
    		if (this.checkedIcon) {
    			domClass.remove(this.selectNode, "muiFastReviewTodoSeled");
  	            domConstruct.destroy(this.checkedIcon);
  	            this.checkedIcon = null;
  	            this.selected = false;
  	            topic.publish("/sys/lbpmperson/mobile/FastReviewTodo/ListItemCancelSelected",this);
    		}
	   },
	    _setSelected: function() {
    		if (this.checkedIcon) {
    			domConstruct.destroy(this.checkedIcon);
	            this.checkedIcon = null;
    		}
    		if (!domClass.contains(this.selectNode, "muiFastReviewTodoSeled")) {
                domClass.add(this.selectNode, "muiFastReviewTodoSeled");
            }
    		this.checkedIcon = domConstruct.create(
               "i",
               {
                  className: "mui mui-checked muiFastReviewTodoSelected"
               },
               this.selectNode
            )
            topic.publish("/sys/lbpmperson/mobile/FastReviewTodo/ListItemSelected",this);
	    },
	    
	    _toggleSelect: function(select) {
	        if (select) {
	          this._setSelected()
	        } else {
	          this._cancelSelected()
	        }
	    },
	    
	    _selectAll:function(){
	    	this._toggleSelect(true);
	    },
	    
	    _cancelSelectAll:function(){
	    	this._toggleSelect(false);
	    },
		/**
		 * 构建基本信息
		 */		
		buildContentRender : function() {
			
	    	// 流程快速审批内容外层容器DOM
	        var fastReviewTodoContentContainer = domConstruct.create("div", {className: "muiFastReviewTodoContentContainer"}, this.tableRightNode);
			
	        // 流程标题
	        var fastReviewToTitleDom = domConstruct.create("div", {className: "muiFastReviewToTitleLabel muiFontSizeM muiFontColorInfo", innerHTML:this.label }, fastReviewTodoContentContainer); 
			// 绑定标题点击事件
			if(this.href){
				this.proxyClick(fastReviewToTitleDom, this.href, '_blank');
			}
	        
	        // 消息基本信息容器DOM（展示：人员头像、人员姓名、时间日期、模块名称、流程节点名称）
	        var muiFastReviewTodoBaseInfo = domConstruct.create("div", {className: "muiFastReviewTodoBaseInfo muiFontSizeS muiFontColorMuted"}, fastReviewTodoContentContainer);
	        
			// 人员头像图标
			if(this.icon){
			   var personHeadIconNode = domConstruct.create('div', {className : 'muiFastReviewTodoPersonHeadIcon'}, muiFastReviewTodoBaseInfo);
			   domConstruct.create('img', {src :util.formatUrl(this.icon),className : 'muiFastReviewTodoPersonHeadImg'}, personHeadIconNode);
			}
	        
			// 基本信息文本容器DOM
			var fastReviewTodoTextNode = domConstruct.create('div', {className : 'muiFastReviewTodoTextInfo muiFontSizeS'}, muiFastReviewTodoBaseInfo);
			
			
			// 人员姓名
			if(this.creator){
			   var creatorNode = domConstruct.create('span', { innerHTML:this.creator, className:'muiFastReviewTodoPersonName' }, fastReviewTodoTextNode);
			}
			  
		    // 时间日期
		    if(this.created){
			   var createdNode = domConstruct.create('span', { innerHTML:this.created, className:'muiFastReviewTodoDate' }, fastReviewTodoTextNode);
		    }
		  
		    // 业务模块名称
		    if(this.modelNameText){
			   var modelNode = domConstruct.create('span', { innerHTML:this.modelNameText, className:'muiFastReviewTodoModelName' }, fastReviewTodoTextNode);
		    }
		  
		    // 流程节点状态
            if(this.lbpmCurrNodeValue) {
        	   var processStatusNode = domConstruct.create('span', { innerHTML:this.lbpmCurrNodeValue, className:'muiFastReviewTodoProcessStatus' }, fastReviewTodoTextNode);
            }
			
		},
		
		
		/**
		 * 构建操作按钮（通过、驳回...）
		 */	      
		buildOperateBtnRender: function() {
	    	  // 操作按钮展示外层容器DOM
	          var notifyOperateBtnContainer = domConstruct.create("div", {className: "muiFastReviewTodoOperateBtnContainer muiFontSizeS muiFontColor","id":"reviewOp_"+this.uuid}, this.tableRightNode);	
	          notifyOperateBtnContainer.appendChild(domConstruct.toDom(this.reviewOp));
	    },		
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
			this.subscribe("/sys/lbpmperson/FastReviewTodoSelectAll","_selectAll");
			this.subscribe("/sys/lbpmperson/CancelFastReviewTodoSelectAll","_cancelSelectAll");
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});