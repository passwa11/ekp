define([
	'dojo/_base/declare', 
	'dojo/_base/lang',
	'dojo/_base/array',
	'dojo/dom-construct',
	'dojo/dom-class',
	'dijit/registry',
	'dijit/_WidgetBase',
	'dijit/_Container',
	"dijit/_Contained",
	'dojox/mobile/SwapView',
	'dojox/mobile/View',
	'dojo/parser',
	'dojo/topic',
	'dojo/Deferred',
	'mui/list/StoreScrollableView',
	], function(declare, lang, array, domCtr, domClass, registry, WidgetBase, Container, _Contained, 
		SwapView, View, parser, topic, Deferred, ScrollableView) {
	
	var InnerSwap = declare([SwapView], {
			
			onAfterTransitionIn: function() {
				this.inherited(arguments);
				// 提示：滑动切换并不会触发此方法
				topic.publish('/dojox/mobile/viewChanged', this);
				this.resize();
			}
	});
	
	return declare('mui.list.SwapScrollableView', [WidgetBase, Container, _Contained], {
		
		innerSwapClass: InnerSwap,
		
		innerScrollableView : ScrollableView,
		
		stopParser: true,
		
		templateString: null,
		
		refNavBar: null,
		
		currView: null,
		
		canStore: true,
		
		postCreate: function() {
			this.inherited(arguments);
			this.subscribe('/dojox/mobile/viewChanged', 'handleViewChanged');
			this.connect(window,'orientationchange','_handleOrientation');
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			// 从starup提到此处，避免静态nav数据源加载过快导致此处未执行
			if (!this.templateString) {
				this.templateString = this.domNode.innerHTML;
				this.domNode.innerHTML = '';
			}
		},
		
		startup: function() {
			if(this._started) {
				return;
			}
			this.inherited(arguments);
		},
		
		_handleOrientation : function(){
			try{
				var self = this;
				var timeout = 120;
				var deferred = new Deferred();
				function detect(i, height0){
					window.innerHeight != height0 || i >= timeout ? deferred.resolve() : window.requestAnimationFrame(function(){
						detect(i + 1, height0);
					});
				}
				detect(0, window.innerHeight);
				deferred.then(function(){
					self.resize && self.resize();
				});
			}catch(e){
			}
		},
		
		handleViewChanged: function(view) {
			
			if (!(view instanceof SwapView)) {
				return;
			}
			array.forEach(this.getChildren(), function(child) {
				if (child === view) {
					var reloadTime = view.reloadTime || 0;
					var nowTime = new Date().getTime();
					var needLoad = (this.currView != view) && (reloadTime == 0);
					this.currView = view;
					view.containerNode.style.paddingTop = "0";
					this.onSwapViewChanged(view);
					if (needLoad) {
						view.reloadTime = nowTime;
						if(view.getChildren().length > 0 && view.getChildren()[0].reload){
							view.getChildren()[0].reload();
						}
					}
					if (window.localStorage && this.canStore) {
						try {
							localStorage.setItem("swapIndex:" + location.pathname, view.getIndexInParent());
						} catch (e) {
							if(window.console)
								console.log(e.name);
						}
					}
					return false;
				}
			}, this);
		},
		
		onSwapViewChanged: function(view) {
			
		},

		_createScroll: function(item) {
			return new this.innerScrollableView({rel: item});
		},
		
		_createSwap: function() {
			return new this.innerSwapClass();
		},
		
		generateSwapList: function(items, widget) {
			var loadIndex = 0;
			// 修复数据源selected参数无效的问题
			if(widget && widget.selectedItem)
				loadIndex = array.indexOf(items, widget.selectedItem);
			
			array.forEach(items, function(item, i) {
				if(item.url){
					var swap = this._createSwap();
					this.addChild(swap);
					var scroll = this._createScroll(item);
					swap.addChild(scroll);
					
					item.moveTo = swap.id; // 绑定view跳转
					
					parser.parse(domCtr.create('div', {innerHTML: this.templateString}))
							.then(function(widgetList) {
								array.forEach(widgetList, function(widget, index) {
									scroll.addChild(widget, index + 1);
								});
							});
				}else if(item.moveTo){
					if(widget.id != "categoryNavBar"){
						var swap = registry.byId(item.moveTo);
						this.addChild(swap);
					}
				}
				
			}, this);
			
			if (window.localStorage && this.canStore) {
				loadIndex = localStorage.getItem("swapIndex:" + location.pathname);
				if (loadIndex == null) {
					loadIndex = 0;
				} else {
					loadIndex = parseInt(loadIndex);
				}
			}
			
			if (loadIndex == 0) {
				this.handleViewChanged(this.getChildren()[loadIndex]);
			} else {
				var self = this;
				require(['dojox/mobile/TransitionEvent'], function(TransitionEvent) {
					setTimeout(function(){
						new TransitionEvent(document.body, {moveTo: self.getChildren()[loadIndex].id}).dispatch();
					},1)
					
				})
			}

			this.resize();
		},

		resize: function() {
			if(this.domNode.parentNode){
				var node, len, i, _fixedAppFooter;
				for(i = 0, len = this.domNode.parentNode.childNodes.length; i < len; i++){
					node = this.domNode.parentNode.childNodes[i];
					if(node.nodeType === 1){
						var fixed = node.getAttribute("fixed")
							|| node.getAttribute("data-mobile-fixed")
							|| (registry.byNode(node) && registry.byNode(node).fixed);
						if(fixed === "bottom"){
							domClass.add(node, "mblFixedBottomBar");
							_fixedAppFooter = node;
						}
					}
				}
			}

			this.inherited(arguments); // scrollable#resize() will be called
			array.forEach(this.getChildren(), function(child){
				if(child.resize){
					if (!child._fixedAppFooter) {
						child._fixedAppFooter = _fixedAppFooter;
						child.getChildren()[0]._fixedAppFooter = _fixedAppFooter;
					}
					child.resize(); 
				}
			});
		}
	});
});