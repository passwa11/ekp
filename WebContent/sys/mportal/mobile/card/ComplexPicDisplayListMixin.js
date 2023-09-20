define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"./item/ComplexPicDisplayItemMixin", "dojo/_base/array", "dojo/dom-class" ],
		function(declare, _TemplateItemListMixin, ComplexPicDisplayItemMixin, array,
				domClass) {

			return declare("sys.mportal.ComplexPicDisplayListMixin",
					[ _TemplateItemListMixin ], {
						itemRenderer : ComplexPicDisplayItemMixin,

						pic : true,

						generateList : function(items) {

							if (items.length == 0)
								return;

							if (this.pic) {
								var index = 0, icon;
								array.map(items, function(item, idx) {
									if (idx==0) {
										item.picType = "big";
									}else{
										item.picType = "small";
									}
									return item;
								});

							} else {
								array.forEach(items, function(item, idx) {
									item.icon = null;
								});
							}

							this.inherited(arguments);
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