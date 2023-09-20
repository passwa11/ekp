/**
 * 配置型列表，根据nav.json配置渲染列表
 */
define([
	'dojo/_base/declare',
	'dojo/_base/lang',
	'dojo/topic',
	'dojo/_base/array',
	'dojo/Deferred',
	'dojox/mobile/View',
	'mui/list/StoreElementScrollableView',
	'dojo/dom-construct',
	'dojo/dom-attr',
	'dojo/parser',
	'mui/util',
	'./NavViewHashMixin'
	], function(declare, lang, topic, array, Deferred, View, StoreElementScrollableView, domConstruct, domAttr, parser, util, NavViewHashMixin) {
	
	return declare('mui.list.NavView', [ View, NavViewHashMixin ], {
		
		
		canStore : true,
		
		refNavBar: null,
		
		templateString: null,
		
		currView: null,
		
		postCreate: function() {
			this.inherited(arguments);
			this.subscribe('/dojox/mobile/viewChanged', 'handleViewChanged');
			this.subscribe('/mui/nav/onComplete', 'handleNavOnComplete');
			this.subscribe('/mui/navView/resize', 'navViewResize');
			this.connect(window, 'orientationchange', '_handleOrientation');
		},
		
		
		buildRendering : function() {
			this.inherited(arguments);
			// 从starup提到此处，避免静态nav数据源加载过快导致此处未执行
			if (!this.templateString) {
				this.templateString = this.domNode.innerHTML;
				this.domNode.innerHTML = '';
			}
		},
		
		navViewResize: function(src,evt) {
			if(!evt){
				return;
			}
			if(this.getChildren().length>0){
				this.getChildren()[evt.key].resize();
			}
		},
		
		startup: function() {
			if(this._started) {
				return;
			}
			this.inherited(arguments);
		},
		
		handleNavOnComplete: function(widget, items) {
			
			if(widget.channelName && widget.channelName != this.channelName){
				return;
			}
			this.refNavBar = widget;
			this.generateList(widget.getChildren(), this.ensureloadIndex(widget), items);
		},
		
		
		_createScrollView :  function(item) {
			return new StoreElementScrollableView({rel: item});
		},
		
		channelName : "",
		
		generateList : function(items, loadIndex, itemMetas) {
			
			array.forEach(items, function(item, i) {
				if(item.url) {
					var meta = itemMetas[i];
					var scroll = this._createScrollView(item);
					// 绑定key，避免不同视图之间的组件通信出现混乱
					
					scroll.key = this.channelName + i; 
					this.addChild(scroll);
					item.moveTo = scroll.id; // 绑定view跳转
					// 渲染列表视图
					var options = {
						key: this.channelName + i,
						parent: scroll
					};
					if(meta.listTemplate){
						var deferred = new Deferred();
						require([util.formatUrl(meta.listTemplate)], lang.hitch(this,function(tmplStr){
							this.renderListWithTemplate(tmplStr, options);
							deferred.resolve(tmplStr);
						}));
						scroll._listPromise = deferred.promise;
					}else{
						this.renderListWithTemplate(this.templateString, options);
					}
				}
			}, this);
			
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
		
		renderListWithTemplate: function(tmplStr, options){
			// 所有的子组件绑定key，避免不同视图下组件通信混乱
			var node = domConstruct.create('div', {innerHTML: tmplStr});
			array.forEach(node.childNodes, function(element){
				if(element.nodeType === 1){
					domAttr.set(element, 'key', options.key);
				}
			}, this);
			parser.parse(node, { noStart: true }).then(function(widgetList) {
				array.forEach(widgetList, function(widget, index) {
					options.parent && options.parent.addChild(widget, index + 1);
				});
			});
		},
		
		/**
		 * 计算选中的Tab导航
		 * 	1、如果Hash中没有，则从localStoreage中取
		 * 	2、如果localStoreage中没有，则从默认配置的selected中获取
		 */
		ensureloadIndex : function(widget){
			var maxLength = widget ? widget.getChildren().length : 0;
			var loadIndex = 0;
			
			// 从selected中取 （为修复数据源selected参数无效的问题，使用selectedItem获取选中值）
			if(widget && widget.selectedItem){
				var items = widget.getChildren();
				loadIndex = array.indexOf(items, widget.selectedItem);
			}
			
			// 从localStoreage中取
			if (window.localStorage && this.canStore) {
				loadIndex = localStorage.getItem("swapIndex:" + location.pathname);
				if (loadIndex == null) {
					loadIndex = 0;
				} else {
					loadIndex = parseInt(loadIndex) + 1 > maxLength ? 0 : parseInt(loadIndex);
				}
			}
			
			var hashLoadIndex = this.inherited(arguments);
			if(hashLoadIndex !== null){
				loadIndex = parseInt(hashLoadIndex) + 1 > maxLength ? 0: parseInt(hashLoadIndex);
			}
			
			return loadIndex;
		},
		
		handleViewChanged: function(view){
			if(view._listPromise){
				var promise = view._listPromise;
				promise.then(lang.hitch(this, function(){
					this.handleViewChanged$(view);
					view._listPromise = null;
				}))
			}else{
				this.handleViewChanged$(view);
			}
		},
		
		handleViewChanged$ : function(view) {
			
			var selectedIndex = 0;
			if (!(view instanceof StoreElementScrollableView)) {
				return;
			}
			array.forEach(this.getChildren(), function(child, index) {
				if (child === view) {
					var reloadTime = view.reloadTime || 0;
					var nowTime = new Date().getTime();
					var needLoad = (this.currView != view) && (reloadTime == 0);
					this.currView = view;
					//view.containerNode.style.paddingTop = "0";
					
					//让nav选中
					if (this.refNavBar) {
						var cIndex = array.indexOf(this.getChildren(), view);
						var selectedItem = this.refNavBar.getChildren()[cIndex];
						this.refNavBar.selectedItem = selectedItem;
						selectedItem.setSelected();
					}
					
					
					if (needLoad) {
						view.reloadTime = nowTime;
						if(view.reload){
							view.reload();
						}
					}
					
					selectedIndex = index;
					
					this.setLocalStorage("swapIndex:" + location.pathname, view.getIndexInParent());
					
					/** Start----------解决切换回第一个页签导致CSS控制显示省略号-webkit-line-clamp失效（注：该问题未能找到核心根本原因，暂时通过强制修改触发重绘文字的CSS属性来达到正常显示省略号的目的）----------Start **/
					if(selectedIndex==0){
						if(!this.switchFirstTabCount){this.switchFirstTabCount=0;}
						if(this.switchFirstTabCount<=1){
							view.domNode.style['font-style']="italic";
							setTimeout(function(){
								view.domNode.style['font-style']="inherit";
							},1);
						}
						this.switchFirstTabCount++;
					}
					/** End----------解决切换回第一个页签导致CSS控制显示省略号-webkit-line-clamp失效（注：该问题未能找到核心根本原因，暂时通过强制修改触发重绘文字的CSS属性来达到正常显示省略号的目的）----------End **/
					
					return false;
				}
			}, this);
			
			this.inherited(arguments, [view, selectedIndex]);
			
		},
		
		setLocalStorage : function(key, value) {
			if (window.localStorage && this.canStore) {
				try {
					localStorage.setItem(key, value);
				} catch (e) {
					if(window.console)
						console.log(e.name);
				}
			}
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
		}
	});
});