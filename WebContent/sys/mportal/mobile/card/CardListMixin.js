define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"./item/CardItemMixin","./item/CardItemFirstRowImgMixin", "dojo/_base/array", "dojo/dom-class", "dojo/dom-construct" ],
		function(declare, _TemplateItemListMixin, CardItemMixin,CardItemFirstRowImgMixin, array,
				domClass, domConstruct) {

			return declare("sys.mportal.CardListMixin",
					[ _TemplateItemListMixin ], {
				        pic : true,
				        
						itemRenderer : null,

						postMixInProperties: function(){
							this.itemRenderer = this.pic?CardItemFirstRowImgMixin:CardItemMixin;
							this.inherited(arguments);
						},
						
						generateList : function(items) {
							if (items.length == 0)
								return;
							
							if (this.pic) {
								// 找到第一张有图片的数据索引
								var index = 0, icon;
								array.map(items, function(item, idx) {
									if (item.icon && !icon) {
										index = idx;
										icon = item.icon;
									}
									item.icon = null;
									return item;
								});
								// 将有图片的数据移动至数组中的第一项
								var _item = items[index];
								_item.icon = icon;
								items.splice(index, 1);
								items.splice(0, 0, _item);

								// 首图存在的特殊样式
								if (items[0].icon)
									domClass.add(this.domNode,'muiPortalPicList');
								else
									domClass.remove(this.domNode,'muiPortalPicList');

							} else {
								array.forEach(items, function(item, idx) {
									item.icon = null;
								});
							}

							for(var i = 0; i < items.length; i++){
								items[i].index = i;
							}
							
							this.containerNode =  domConstruct.create('ul', {
								className : this.pic?'mui_ekp_portal_new_knowledge_row1_img-content':'mui_ekp_portal_new_knowledge-content'
							});

							this.inherited(arguments);
							
							domConstruct.place(this.containerNode, this.domNode, 'last');
							
						},

						REFRESH : "/mui/mportal/card/refresh",
						buildRendering : function() {
							this.inherited(arguments);
							this.subscribe(this.REFRESH, "_refresh");
						},

						_refresh : function(evt) {
							if (!evt)
								return;
							if (this.getParent() == evt) {
								this.reload();
							}
						}
					});
		});