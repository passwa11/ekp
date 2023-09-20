define(
		[ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/_base/array",
				"dojox/mobile/ScrollableView", "dojo/dom",
				"dojo/dom-construct", "dojo/dom-style", "dojo/dom-class",
				"dojo/topic", "mui/util",
				"mui/i18n/i18n!sys-mobile", "dojo/dom-geometry" ],
		function(declare, WidgetBase, array, ScrollableView, dom, domConstruct,
				domStyle, domClass, topic, util, Msg, domGeometry) {
			var selection = declare(
					"mui.tabfilter.TabfilterSelection",
					[ WidgetBase ],
					{
						values : "",

						texts : "",
						
						splitStr :  ";",

						baseClass : 'muiTabfilterSec',
						
						// 已选列表value, text
						itemSelArr : [],

						itemPrefix : "_tabfilter_s_item_",

						buildRendering : function() {
							this.inherited(arguments);
							this.itemSelArr = [];
							this.containerNode = domConstruct.create("div", {
								'className' : 'muiTabfilterSecContainer'
							}, this.domNode);
							/*
							 * 这里.muiTabfilterSecContainer必须设为相对定位，
							 * scrollableView的高度设为inherit才会元素去设根据父元素定高度，取的是offsetParent
							 */
							this.view = new ScrollableView({
								scrollBar : false,
								threshold : 100,
								scrollDir : "h",
								dirLock : true,
								height : "inherit"
							});
							this.containerNode.appendChild(this.view.domNode);
						},

						postCreate : function() {
							this.subscribe("/mui/tabfilter/item/select",
									"_addSelItme");
							this.subscribe("/mui/tabfilter/item/unselect",
									"_delSelItem");
							
							this.subscribe("/mui/tabfilter/ok", "_submitSelect");
						},

						startup : function() {
							if (this._started) {
								return;
							}
							this.inherited(arguments);
							this.view.startup();
							this._initSelection();
						},

						destroy : function() {
							this.inherited(arguments);
							if (this.view.destroy) {
								this.view.destroy();
								delete this.view;
							}
						},

						_initSelection : function() {
							if (this.values) {
								if (this.values == this.texts) {
									// 暂时只支持名称和值一样的数据，比如标签
									var arr = this.values.split(/[\s+;,]/);
									for (var i = 0; i < arr.length; i++) {
										this._addSelItme(null, {
											text : util.formatText(arr[i]),
											value : util.formatText(arr[i])
										})
									}
								}
							}
						},

						_buildSelItem : function(item) {
							var selDom = domConstruct.create("div", {
								className : "muiTabfilterSecItem",
								innerHTML : item.text,
								"id" : this.itemPrefix + item.value
							});
							this.connect(selDom,
											"click",
											function(evt) {
												this._delSelItem(null, item);
												topic.publish(
																"/mui/tabfilter/item/unselect",
																this, item);
											});
							return selDom;
						},


						_addSelItme : function(obj, evt) {
							if(obj === this) {
								return;
							}
							if (evt) {
								this.itemSelArr.push(evt);
								var self = this;
								var selItem = this._buildSelItem(evt);
								if (!this.itemContainer) {
									this.itemContainer = domConstruct
											.create(
													"div",
													{
														'className' : 'muiTabfilterSecItems'
													}, this.view.containerNode);
								}
								this.itemContainer.appendChild(selItem);
							}
							this._resizeSelection("add");
						},

						_delSelItem : function(obj, evt) {
							if(obj === this) {
								return;
							}
							for (var i = 0; i < this.itemSelArr.length; i++) {
								if (this.itemSelArr[i].value == evt.value) {
									this.itemSelArr.splice(i, 1);
									domConstruct.destroy(dom
											.byId(this.itemPrefix + evt.value));
									break;
								}
							}
							this._resizeSelection("delete");
						},
						
						_resizeSelection : function(method) {
							// 在safari 下总要移动一点点才会显示选择的项，不知道啥原因。。
							var xPos = 0.1;
							if (this.itemContainer) {
								var childCount = this.itemSelArr.length;
								if (childCount > 0) {
									var cW = 0;
									array.forEach(this.itemContainer.children,
											function(item) {
												var mw = domGeometry
														.getMarginBox(item).w;
												cW += mw
											});

									var conW = this.view.domNode.offsetWidth;
									if (cW > conW) {
										xPos = -(cW - conW + 10);
									}
								}
							}
							if (this.view.resize) {
								this.view.resize();
							}
							if (this.view.scrollTo) {
								this.view.scrollTo({
									y : 0,
									x : xPos
								});
							}
							topic.publish("/mui/tabfilter/selChanged" + (method ? ("/" + method) : ""), this,
									this._calcCurSel());
						},
						
						_submitSelect : function() {
							topic.publish("/mui/tabfilter/submit", this, this._calcCurSel());
						},

						_calcCurSel : function() {
							var eCxt = {
								values : "",
								texts : ""
							};
							var self = this;
							if (this.itemSelArr.length > 0) {
								var ids = '';
								var names = '';
								array.forEach(this.itemSelArr,
										function(selItem) {
											ids += self.splitStr + selItem.value;
											names += self.splitStr + selItem.text;
										});
								if (ids != '') {
									ids = ids.substring(1);
									names = names.substring(1);
									eCxt.values = ids;
									eCxt.texts = names;
								}
							}
							return eCxt;
						}
					});
			return selection;
		});