define("km/imeeting/mobile/resource/js/list/_PlaceStoreNavBarMixin", ["dojo/_base/declare", "dojo/dom-class",
				"dojox/mobile/_StoreMixin", "dojo/_base/array", "./item/PlaceItem",
				"dojo/store/JsonRest", "dojo/topic",'dojo/_base/lang',  "mui/util"], function(
				declare, domClass, StoreMixin, array, NavItem, JsonRest, topic,lang,
				util) {
			var cls = declare('km.imeeting._PlaceStoreNavBarMixin', StoreMixin, {
				// 渲染模板
				itemRenderer : NavItem,
				
				// 默认请求url
				url : '/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=listAvailable',
				
				fdTime : '',
				
				curCateId:'',

				buildRendering : function() {
					this.inherited(arguments);
				},

				onComplete : function(items) {
					if(items){
						this.generateList(items);
					}
					topic.publish('/km/imeeting/place/onComplete', this, items);
				},

				generateList : function(items) {
					this.containerNode.innerHTML = "";
					array.forEach(items, function(item, index) {
						if (index == 0) {
							this.addFirstChild(this.createListItem(item));
							return;
						}
						this.addChild(this.createListItem(item));
						if (item[this.childrenProperty]) {
							array.forEach(item[this.childrenProperty], function(child,
									index) {
								this.addChild(this.createListItem(child));
							}, this);
						}
					}, this);
				},

				addFirstChild:function(item){
					this.addChild(item);
				},

				// 构建子项
				createListItem : function(item) {
					var item = new this.itemRenderer(this
							._createItemProperties(item));
					return item;
				},

				// 格式化数据
				_createItemProperties : function(item) {
					return item;
				},
				
				handleTimeChanged: function(srcObj,evt) {
					this.fdTime = evt.value;
					var store = new JsonRest({
						idProperty : 'fdId',
						target :util.formatUrl(this.url)
					});
					this.setStore(store, this.buildQuery(), this.queryOptions);
				},
				
				handleCateChangeChange :function(evt) {
					//console.log("categoryId:"+evt.value);
					this.curCateId = evt.value;
					var store = new JsonRest({
						idProperty : 'fdId',
						target :util.formatUrl(this.url)
					});
					this.setStore(store, this.buildQuery(), this.queryOptions);
				},
				
				buildQuery : function(xtime) {
					return lang.mixin([] , {
							fdTime : this.fdTime,
							parentId :this.curCateId
					});
				},

				startup : function() {
					if (this._started)
						return;
					if (!this.store && !this.url)
						return;
					if (!this.store && this.url)
						var store = new JsonRest({
									idProperty : 'fdId',
									target : util.formatUrl(this.url)+"&parentId="+this.curCateId+"&_="+new Date().getTime()
								});
					else
						store = this.store;
					this.store = null;
					this.setStore(store, this.query, this.queryOptions);
					this.subscribe('/km/imeeting/navitem/selected','handleTimeChanged');
					this.subscribe("/km/imeeting/category/change",'handleCateChangeChange');
					this.inherited(arguments);
				}
			});
			return cls;
		});