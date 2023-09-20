define([ "dojo/_base/declare", 
         "mui/dialog/_DialogBase", 
         "dojo/dom-construct", 
         "dijit/registry", 
         "dojo/dom-style", 
         "dojo/dom-class", 
         "dojo/_base/array",
		 "dojo/_base/lang", 
		 "dojo/parser", 
		 "mui/iconUtils",
		 "dojox/mobile/View",
		 "dojox/mobile/_css3", 
		 "dojo/query", 
		 "dojo/on",
		 "mui/i18n/i18n!sys-mobile:mui.button.cancel",
	     "dojo/topic"
], function(declare, _DialogBase, domConstruct, registry, domStyle, domClass, array, lang, parser, iconUtils, View, css3, query, on, cancelMsg,topic) {
	
	var claz = declare('mui.dialog.Dialog', [ _DialogBase ], {

		element : null,

		title : '',

		buttons : [],
		
		callback : null,
		
		onDrawed : null,

		showClass : 'muiDialogElementShow',

		closeClass : 'fontmuis muis-epid-close',
				
		// 是否需要滑动  默认直接弹出  right 从右往左滑动  left top bottom 类推
		transform:'',
		
		// 弹出框显示的位置
		appendTo:document.body,
		
		// 点击遮罩时是否关闭对话框, 当position为非center时生效
		closeOnClickDomNode:true,
		
		position:'center',
		
		// 是否需要在标题栏左侧显示“取消”关闭按钮
		canClose : true,
		
		// 是否在弹窗右上角显示关闭图标按钮
		iconClose: false,
		
		// 关闭后是否销毁
		destroyAfterClose:true,
		
		// 是否需要滚动，需要滚动时必须解析
		scrollable : true,
		
		// 弹窗内可滚动的DOM node (用于限制、阻止可滑动区域之外的默认事件)
		scrollViewNode: null,
		
		// 是否需要解析
		parseable : false,
		
		privateHeight:null,
		
		//是否支持父级的滚动，默认不支持
		scrollParentNode:false,
		
		// 弹出框内容加载完毕后调整位置
		loaded : function() {
			 //内容较多时高度为屏幕高度的90%
			 if(this.scrollable){
				 var contentHeight = document.documentElement.clientHeight*0.8;
				 if(this.privateHeight){
					 contentHeight=this.privateHeight
				 }
				 //减去头部高度
				 if(this.divNode){
					contentHeight = contentHeight - this.divNode.offsetHeight;
				 }
				 //减去按钮栏高度
				 if(this.buttonsNode){
					contentHeight = contentHeight - this.buttonsNode.offsetHeight;
				 }
				 domStyle.set(this.contentNode, {
					   'height' : contentHeight + 'px'
				 });
			 }
			
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			var self = this;
			this.domNode = domConstruct.create('div', {
				className : 'muiDialogElement' + (this.showClass!=null?(" " +this.showClass):'' )
			}, this.appendTo, 'last');
					
			//创建额外的div节点，点击该节点时关闭弹层，绑在domNode上时在IOS下，弹层的同时会触发关闭（不知道原因） 
			if(this.position!="center"){
				this.otherNode = domConstruct.create('div', {}, this.domNode);
			}
			
			var containerWrapNode = domConstruct.create('div', { 
				className : (this.position=="center"?'muiDialogElementContainerWrap':'muiDialogElementContainerWrap_'+this.position)
			}, this.domNode);  
			
			this.containerNode = domConstruct.create('div', {
				className : (this.position=="center"?'muiDialogElementContainer':'muiDialogElementContainer_'+this.position)
			}, containerWrapNode);
			
			// 右上角关闭图标
			if(this.iconClose){
				this.closeNode = domConstruct.create('div', { className : 'muiDialogElementCloseIcon'}, this.containerNode);
				domConstruct.create('i', { className : 'fontmuis muis-epid-close'}, this.closeNode);
				this.connect(this.closeNode, 'click', '_onClose');
			}
			
			// 头部容器
			if(this.title){
				this.divNode = domConstruct.create('div', {
					className : this.position=="center"?'muiDialogElementDiv':'muiDialogElementDiv_'+this.position
				}, this.containerNode);
				// 构建“取消”关闭按钮
				if (this.canClose && !this.iconClose && (typeof(this.buttons) === 'undefined'||this.buttons==null||this.buttons.length==0) ) {
					this.closeNode = domConstruct.create('div', { className : 'muiDialogElementCancel', innerHTML:cancelMsg['mui.button.cancel'] }, this.divNode);
					this.connect(this.closeNode, 'click', '_onClose');
				}
				// 标题节点
				this.titleNode = domConstruct.create('div', {
					className :this.position=="center"? 'muiDialogElementTitle':'muiDialogElementTitle_'+this.position,
					innerHTML : this.title
				}, this.divNode);
			}
			
			
			// 头部固定元素
			if (this.fixElement) {
				domConstruct.place(this.fixElement, this.containerNode);
			}
			
			// 内容节点
			this.contentNode = domConstruct.create('div', {
				className : this.position=="center"?'muiDialogElementContent':'muiDialogElementContent_'+this.position
			}, this.containerNode);

			// 创建按钮节点
			if (this.buttons.length > 0) {
				domClass.add(this.containerNode,'muiHasDialogElementButtons');
				this.buttonsNode = domConstruct.create('div', {
					className :this.position=="center"? 'muiDialogElementButtons':'muiDialogElementButtons_'+this.position
				}, this.containerNode);

				this.buttonsDom = [];
				array.forEach(this.buttons, lang.hitch(this, function(item) {
					var btn = domConstruct.create('div', {
						className :this.position=="center"? 'muiDialogElementButton':'muiDialogElementButton_'+this.position,
						innerHTML : item.title
					}, this.buttonsNode);
					this.buttonsDom.push(btn);
				}));
				this.connect(this.buttonsNode, 'click', '_onClick');
			}
			
			var _container = this.contentNode;
			var isParse = this.parseable;
			if (this.scrollable) {
				_container = domConstruct.create('div', {
					'className':'muiDialogScrollView',
					'data-dojo-type' : 'mui/NativeView'
				}, this.contentNode);
				isParse = true;
				this.scrollViewNode = _container;
			}
			domConstruct.place(this.element, _container);
			if(isParse){
				// 解析头部固定元素
				if(this.fixElement) {
					parser.parse(this.fixElement);
				}
				parser.parse(this.contentNode).then(function(widgetList) {
					topic.publish('parser/dialog/done');
					self.htmlWdgts = widgetList;
					self.loaded();
					var scrollWidget = self._getScrollWidgetByParentNode(self.contentNode);
					if(scrollWidget){
						self.scrollViewNode = scrollWidget.domNode;
					}
					if(self.onDrawed){
						self.onDrawed(self);
					}
					self._addDialogEventListener();
					self.afterDrawed();
				});
				
			}else{
				this.loaded();
				if(this.onDrawed){
					this.onDrawed(this);
				}
				this._addDialogEventListener();
				this.afterDrawed();
			}
			// 阻塞线程，页面渲染完再获取高度 by zhugr 2018-02-05
			this.defer(function(){
				
				if(self.position!="center"){
					domStyle.set(self.otherNode, {
						 'height' : (self.domNode.offsetHeight) + 'px'
					 });
					self.connect(self.otherNode , 'click', '_onClose');
				}else{
					self.connect(self.domNode , 'click', '_onDialogClose');
				}
			}, 400);
			
			this.show();
		},
		
		
		_addDialogEventListener: function(){
			var _self = this;
			// 监听touchmove事件，避免滑动弹框遮罩层阴影区域时，遮罩层下的内容跟随滚动（只允许弹出框内容节点进行滑动）；
			this.domNode.addEventListener('touchmove',function(evt){
	        	var e = evt || window.event, target = e.target || e.srcElement;
	        	// 判断当前触摸手滑动悬停的DOM元素是否处于弹出框允许滑动的内容DOM之内
	        	var bool = _self._isMoveTargetInDialogContent(target);
	            if(bool==false){
		            e.preventDefault();
		            e.stopPropagation();
	            }				
			},false);		
			
			// 阻止可滑动区域之外的默认事件（即：限制滑动时不要因可滑动区域在顶部或底部时继续滑动联动触发window窗口的滚动）
			var scrollNode = null;
			if (this.scrollViewNode) {
				scrollNode = this.scrollViewNode;
			}else{
				var scrollWidget = this._getScrollWidgetByParentNode(this.contentNode);
				scrollNode = scrollWidget ? scrollWidget.domNode : this.contentNode;
			}
			
			this.scrollNode = scrollNode;
			
			var startX, startY, moveEndX, moveEndY, X, Y; 
			scrollNode.addEventListener('touchstart', function(e) {
				startX = e.touches[0].pageX;
				startY = e.touches[0].pageY;
			}, false);
			
			scrollNode.addEventListener('touchmove',function(e){
			    moveEndX = e.changedTouches[0].pageX;
			    moveEndY = e.changedTouches[0].pageY;
			    
			    X = moveEndX - startX;
			    Y = moveEndY - startY;
			    
			    if ( Math.abs(Y) > Math.abs(X) && Y > 0) {
                   /*
                    * 朝顶部方向滑动，当滑动至最顶部时，阻止继续执行默认事件
                    */
			    	if(this.scrollTop<=0){
			    		if (e && e.cancelable && !e.defaultPrevented) e.preventDefault(); 
			    	}
			    } else if ( Math.abs(Y) > Math.abs(X) && Y < 0 ) {
                   /*
                    * 朝底部方向滑动，当滑动至最底部时，阻止继续执行默认事件
                    */
			        var scrollHeight = this.scrollHeight; // 滚动内容的高度
			        var clientHeight = this.clientHeight; // 滚动容器的高度
			        var scrollTop = this.scrollTop; // 当前滚动位置高度
			        var toBottom = scrollHeight - clientHeight - scrollTop; // 滚动条距离底部的距离
			        if(toBottom <= 0){
			        	if (e && e.cancelable && !e.defaultPrevented) e.preventDefault();
			        }
			    }
			   
			},false);	
			
		},
		
		/**
		* 根据父元素DOM node获取子级可滚动的dojox/mobile/View组件dojo对象
		* @param parentNode 父元素DOM
		* @return
		*/	
		_getScrollWidgetByParentNode: function(parentNode){
			var scrollWidget = null;
			var mblViewNodes = query("[data-dojo-type].mblView",parentNode);
			for(var i=0;i<mblViewNodes.length;i++){
				var viewNode = mblViewNodes[i];
				var widget = registry.byNode(viewNode);
				if(widget instanceof View){
					scrollWidget = widget;
					break;
				}				
			}
			return scrollWidget;
		},
		
		
		/**
		* 判断当前滑动的DOM是否处于Dialog弹出框的内容节点下
		* (判断逻辑：通过当前滑动的DOM一直递归向上查找父级中是否存在Dialog的内容节点，如果存在则说明当前滑动的DOM为Dialog内容节点的子孙节点)
		* @param targetNode 当前滑动的DOM
		* @return
		*/	
		_isMoveTargetInDialogContent: function(targetNode){
			if(targetNode.nodeName.toUpperCase()=='BODY'){
				return false;
			}else{
				if(targetNode==this.contentNode){
					return true;
				}else{
					return this._isMoveTargetInDialogContent(targetNode.parentElement);
				}
			}
		},
		
		
		_onClick : function(evt) {
			var target = evt.target;
			for (var i = 0; i < this.buttonsDom.length; i++) {
				if (target === this.buttonsDom[i] || target.parentNode === this.buttonsDom[i]) {
					this.buttons[i].fn.call(window, this);
					if (this.callback)
						this.callback(window, this);
					break;
				}
			}
		},
		
		_onDialogClose : function(evt){
			var target = evt.target,
				container = query(target).closest(".muiDialogElementContainer");
			if(container && container.length > 0){
				return;
			}
			if(this.closeOnClickDomNode){
				if (this.callback)
					this.callback(window, this);
				this.hide();
			}
		},

		_onClose : function(evt) {
			var target = evt.target;
			if((target === this.otherNode && this.closeOnClickDomNode && this.position!="center") || target === this.closeNode || target.parentNode == this.closeNode){
				if (this.callback)
					this.callback(window, this);
				this.hide();
			}
		},
		

		show : function() {
			var transformCass = "";
			var position = this.transform || this.position;
			if(position){
				if(position == 'bottom'){
					transformCass = "fadeInUp";
				}else if(position == 'top'){
					transformCass = "fadeInDown";
				}else if(position == 'left'){
					transformCass = "fadeInLeft";
				}else if(position == 'right'){
					transformCass = "fadeInRight";
				}
			}
			// 非以上设置的方向默认渐变出现
			if (!transformCass) {
				transformCass = "fadeIn";
			}
//			domClass.add(this.domNode, ' animated fadeIn');
//			domStyle.set(this.domNode,{display:'block'});
			domClass.add(this.containerNode, ' animated '+ transformCass);
//			domStyle.set(this.containerNode,{'display': 'block'});
			return this.inherited(arguments);
		},

		hide : function() {
			this.beforeCallback();
			domClass.add(this.containerNode,'fadeOut animated');
			
			domClass.add(this.domNode,'fadeOut animated');
			
			this.defer(function(){
				domStyle.set(this.containerNode,{display:'none'});
				domStyle.set(this.domNode,{display:'none'});
				domClass.remove(this.containerNode, "fadeOut");
				domClass.remove(this.domNode, "fadeOut");
				if (this.destroyAfterClose) {
					array.forEach(this.htmlWdgts,
							function(wdt) {
								if (wdt && wdt.destroy) {
									wdt.destroy();
								}
							});
					domConstruct.destroy(this.domNode);
					this.domNode = null;
					this.destroy();
				}
			}, 400);
			
			if(this.destroyAfterClose){
				this.inherited(arguments);
			}
		},
		
		afterDrawed:function(){
			if(this.scrollParentNode)
				return;
			//记录当前body滚动的位置
			this.bodyScrollPos = this.getBodyScrollPos();
			domStyle.set(query("body")[0],{
				"overflow-y":"hidden",
				"position":"fixed"
			})
		},
		
		beforeCallback:function(){
			if(this.scrollParentNode)
				return;
			if(query(".muiDialogElement").length > 1){
				var dialogElems = query(".muiDialogElement");
				var dialogLen = 0;
				for (var i=0; i<dialogElems.length; i++) {
					var dialogElem = dialogElems[i];
					var isNone = false;
					while(dialogElem.parentNode){
						if(dialogElem.parentNode.style && dialogElem.parentNode.style.display == "none"){
							isNone = true;
							break;
						}
						dialogElem = dialogElem.parentNode;
					}
					if(!isNone){
						dialogLen++;
					}
				}
				if(dialogLen > 1){
					return;
				}
			}
			domStyle.set(query("body")[0],{
				"overflow-y":"",
				"position":""
			})
			//恢复body滚动的位置
			if(this.bodyScrollPos){
				this.setBodyScrollPos(this.bodyScrollPos);
			}
		},
		
		// 获取body当前滚动位置
		getBodyScrollPos: function () {
	      return { y: document.documentElement.scrollTop || document.body.scrollTop };
	    },
	    //设置body滚动的位置
	    setBodyScrollPos: function(pos){
	    	document.documentElement.scrollTop = document.body.scrollTop = pos.y;
	    }
	});

	return {
		claz:claz,
		element : function(options) {
			return new claz(options);
		}
	};

})