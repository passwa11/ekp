define("mui/nav/_StoreNavBarMixin", ["dojo/_base/declare","dojo/dom-attr", "dojo/dom-class","dojo/dom-style","dojo/dom-construct","dojo/query",
				"dojox/mobile/_StoreMixin", "dojo/_base/array", "./NavItem",
				"mui/store/JsonRest", "dojo/topic", "mui/util", 'dojo/_base/lang'], function(
				declare,domAttr, domClass,domStyle, domConstruct,query,StoreMixin, array, NavItem, JsonRest, topic,
				util, lang) {
			var cls = declare('mui.nav._StoreNavBarMixin', StoreMixin, {
				// 渲染模板
				itemRenderer : NavItem,
				
				// 默认请求url
				defaultUrl : '',

				// 是否默认过，防止请求死循环
				defaulted : false,				

				buildRendering : function() {
					this.inherited(arguments);
				},

				_setUrlAttr : function(url) {
					this.url = util.formatUrl(url);
					this._url = util.formatUrl(url);
				},

				_setDefaultUrlAttr : function(url) {
					this.defaultUrl = util.formatUrl(url);
				},

				onComplete : function(items) {
					// // 无数据启用默认url
					if (items.length == 0 && !this.defaulted && this.defaultUrl) {
						this.set('defaulted', true);
						this.url = this.defaultUrl;
						this.store.target = this.url;
						this.setQuery();
						return;
					}
					this.generateList(items);
					topic.publish('/mui/nav/onComplete', this, items);
					if (this.selectedItem) {
						this.selectedItem.setSelected();
						if (this.selectedItem.moveTo) {
							this.selectedItem.transitionTo(this.selectedItem.moveTo);
						}
					}
				},
				generateList : function(items) {
					array.forEach(items, function(item, index) {
						if (index == 0) {
							this.addFirstChild(this.createListItem(lang.mixin(item, {tabIndex: index})));							
							return;
						}
						this.addChild(this.createListItem(lang.mixin(item, {tabIndex: index})));
						if (item[this.childrenProperty]) {
							array.forEach(item[this.childrenProperty], function(child,
									index) {
								this.addChild(this.createListItem(lang.mixin(item, {tabIndex: index})));
							}, this);
						}
					}, this);
				},

				selectedItem : null,
				
				addFirstChild:function(item){
					this.addChild(item);
				},
				
				channelName:"",

				// 构建子项
				createListItem : function(item) {
					var item = new this.itemRenderer(this
							._createItemProperties(item));
					if (item.selected === true)
						this.selectedItem = item;
					
					if(this.channelName){
						item.channelName = this.channelName;
					}
					return item;
				},

				// 格式化数据
				_createItemProperties : function(item) {
					return item;
				},

				startup : function() {
					if (this._started)
						return;
					
					if (!this.isTiny) {
						if (!this.url) {
							this.url = this.defaultUrl;
							this.set('defaulted', true);
						}
						
						if (!this.store && !this.url)
							return;

						if (!this.store && this.url)
							var store = new JsonRest({
										idProperty : 'fdId',
										target : this.url,
										defaultType : 'get'
									});
						else
							store = this.store;
						this.store = null;
						setTimeout(lang.hitch(this, function() {
							this.setStore(store, this.query,
									this.queryOptions);
						}), 1)

					}
					
					this.inherited(arguments);
				}
			});
			return cls;
		});