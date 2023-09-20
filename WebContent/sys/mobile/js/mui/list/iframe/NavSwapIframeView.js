define([
	'dojo/_base/declare', 
	'dojo/_base/lang',
	'dojo/_base/array',
	'dojo/dom-construct',
	'dojo/dom-class',
	'dojo/dom-style',
	'dijit/registry',
	'dijit/_WidgetBase',
	'dijit/_Container',
	"dijit/_Contained",
	'dojox/mobile/SwapView',
	'dojox/mobile/View',
	'dojo/parser',
	'dojo/topic',
	'mui/list/StoreScrollableView',
	'./Iframe'
	], function(declare, lang, array, domCtr, domClass,domStyle ,registry, WidgetBase, Container, _Contained, 
		SwapView, View, parser, topic, ScrollableView, Iframe) {
	
	var InnerSwap = declare([View], {
			
		onAfterTransitionIn: function() {
			this.inherited(arguments);
			// 提示：滑动切换并不会触发此方法
			topic.publish('/dojox/mobile/viewChanged', this);
			this.resize();
		}
	
	});
	
	return declare('mui.list.iframe.SwapIframeView', [WidgetBase, Container, _Contained], {
		
		innerSwapClass: InnerSwap,
		
		currView: null,
		
		canStore: true,
		
		postCreate: function() {
			this.inherited(arguments);
			this.subscribe('/mui/nav/onComplete', 'handleNavOnComplete');
			this.subscribe('/dojox/mobile/viewChanged', 'handleViewChanged');
		},
		
		handleNavOnComplete: function(widget, items) {
			this.generateSwapList(widget.getChildren(), widget);
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
					item.moveTo = swap.id; // 绑定view跳转
					
					var iframe = new Iframe({
						url : decodeURIComponent(item.url)
					});
					swap.addChild(iframe);
					
				}else if(item.moveTo){
					var swap = registry.byId(item.moveTo);
					this.addChild(swap);
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
					new TransitionEvent(document.body, {moveTo: self.getChildren()[loadIndex].id}).dispatch();
				})
			}
			
			var selectedItem = widget.getChildren()[loadIndex];
			selectedItem.setSelected();
			
		},
		
		handleViewChanged: function(view) {
			if (!(view instanceof View)) {
				return;
			}
			array.forEach(this.getChildren(), function(child) {
				if (child === view) {
					var reloadTime = view.reloadTime || 0;
					var nowTime = new Date().getTime();
					var needLoad = (this.currView != view) && (reloadTime == 0);
					this.currView = view;
					view.containerNode.style.paddingTop = "0";
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
		
		_createSwap: function() {
			return new this.innerSwapClass();
		}
		
	});
});