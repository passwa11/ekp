define("km/imeeting/mobile/resource/js/list/_DateStoreNavBarMixin", ["dojo/_base/declare", "dojo/dom-class",
				"dojox/mobile/_StoreMixin", "dojo/_base/array", "./item/NavItem",
				"dojo/store/JsonRest", "dojo/topic", "mui/util"], function(
				declare, domClass, StoreMixin, array, NavItem, JsonRest, topic,
				util) {
			var cls = declare('km.imeeting._DateStoreNavBarMixin', StoreMixin, {
				// 渲染模板
				itemRenderer : NavItem,
				
				// 默认请求url
				url : '/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=generateTime',

				buildRendering : function() {
					this.inherited(arguments);
				},

				onComplete : function(items) {
					this.generateList(items);
					topic.publish('/km/imeeting/time/onComplete', this, items);
					if (this.selectedItem) {
						this.selectedItem.setSelected();
					}
				},

				generateList : function(items) {
					array.forEach(items, function(item, index) {
						this.addChild(this.createListItem(item));
					}, this);
					
				},

				selectedItem : null,
				
				addFirstChild:function(item){
					this.addChild(item);
				},

				// 构建子项
				createListItem : function(item) {
					var item = new this.itemRenderer(this
							._createItemProperties(item));
					if (item.selected === true)
						this.selectedItem = item;
					return item;
				},

				// 格式化数据
				_createItemProperties : function(item) {
					return item;
				},

				startup : function() {
					if (this._started)
						return;

					if (!this.store && !this.url)
						return;
					if (!this.store && this.url)
						var store = new JsonRest({
									idProperty : 'fdId',
									target : util.formatUrl(this.url)
								});
					else
						store = this.store;
					this.store = null;
					this.setStore(store, this.query, this.queryOptions);
					this.inherited(arguments);
				}
			});
			return cls;
		});