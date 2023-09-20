define([
	"dojo/_base/declare", 
	"dojo/_base/lang", 
	'dojo/topic', 
	'dojo/dom-construct',
	'dojo/query',
	'dojo/_base/array',
	'dojox/mobile/SwapView',
	'dojo/parser',
	"dijit/registry",
	"dojo/dom-class",
	"dojox/mobile/viewRegistry",
	"mui/util",
	"mui/list/StoreScrollableView"
	], function(declare, lang, topic, domCtr, query, array,
				SwapView, parser,registry,domClass, viewRegistry,util,StoreScrollableView) {
	
	return declare("kms.lservice._ViewNavSwapMixin", null, {
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe('/dojox/mobile/viewChanged', 'handleViewChanged');
			this.subscribe('/mui/nav/onComplete', 'handleNavOnComplete');
		},

		startup : function(){
			if(this._started){ return; }
			this.inherited(arguments);
			//this.initSwap();
		},
		
		handleNavOnComplete: function(widget, items) {
			this.refNavBar = widget;
			this.generateSwapList(widget.getChildren());
			//this.refNavBar.getChildren()[0].setSelected();
		},
		
		
		initSwap: function() {
			var swap = this.getChildren()[0];
			var scorll = swap.getChildren()[0];
			scorll.reload();
			scorll.resize();
			this.currView = swap;
			swap.reloadTime = new Date().getTime();
		},
		
		
		
		generateSwapList: function(items) {
			array.forEach(items, function(item, i) {
				item.moveTo = this.getChildren()[i].id; // 绑定view跳转
			}, this);
			var loadIndex = 0;
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

			this.resize();
		},
		
		getListByView : function(view) {
			var children = view.getChildren();
			if(children && children.length > 0) {
				for(var i = 0; i < children.length; i ++) {
					if(children[i].isInstanceOf(StoreScrollableView)) {
						return children[i];
					}
				}
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
						var list = this.getListByView(view)
						if(list) {
							view.reloadTime = nowTime;
							list.reload();	
						}
					}
					//记住当前选中的标签页
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
			this.inherited(arguments);
			if (this.refNavBar) {
				var index = array.indexOf(this.getChildren(), view);
				this.refNavBar.getChildren()[index].setSelected();
			}
		}
	});
});