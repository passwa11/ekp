define(
		[ "dojo/_base/declare",
				"mui/tabfilter/TabfilterSelection",
				"dojo/topic",
				"dojo/dom-construct"], function(declare, TabfilterSelection, topic, domConstruct) {
			return declare("mui.tabfilter.TabfilterListSelection",
					[ TabfilterSelection ], {
						baseClass : "muiTabfilterListSec",
						
						itemPrefix : "_tabfilter_list_s_item_",
						
						postCreate : function() {
							//弹出框发出的事件
							this.subscribe("/mui/tabfilter/dialog/submit", "_diaSubmit");
						},
						
						_diaSubmit : function(obj, evt) {
							if(evt) {
								if(this.itemContainer) {
									domConstruct.empty(this.itemContainer);
								}
								this.itemSelArr = [];
								if(evt.values) {
									var arrVal = evt.values.split(/[\s+;,]/);
									var arrText = evt.texts.split(/[\s+;,]/);
									for(var i = 0; i < arrVal.length; i ++) {
										var t = arrText[i];
										var v = arrText[i];
										this._addSelItme(null, {
											text : t,
											value : v
										});
									}
								}
							}
						}
					});
		});
