define(	["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"mui/util", "mui/list/item/_ListLinkItemMixin"],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				 util, _ListLinkItemMixin) {
			var item = declare("sys.zone.mobile.list.item.PersonSearchItemMixin", [ItemBase,
							_ListLinkItemMixin], {

						fdId : "",
						
						fdName : '',
						
						imgUrl : "/sys/person/image.jsp?size=m&personId=",
						

						buildRendering : function() {
							this.inherited(arguments);
							this.domNode = this.containerNode = this.srcNodeRef
									|| domConstruct.create("li", {
										className : "muiPersonSearchItem"
									});
							this.contentNode = domConstruct.create('a', {
								className : 'muiPersonSearchIContent',
							}, this.domNode);

							this.container = domConstruct.create('div', {
								className : "muiPersonSearchItemContariner"
							}, this.contentNode);
							
							var iconNode = domConstruct.create("div", {
								className : "muiPersonSearchIcon"
							},this.container);
							
							var bgnode = domConstruct.create("span", null ,iconNode);
							domStyle.set(bgnode, {
								background : "url('" + util.formatUrl(this.imgUrl + this.fdId) + "')",
								"background-positon" : "center center",
								"background-size" : "cover",
								display: "inline-block"
							});
							
							
							var infoNode = domConstruct.create("div", {
								className : "muiPersonSearchItemInfo"
							},this.container);
							domConstruct.create("div", {
								className : "muiPersonSeachItemName",
								innerHTML : util.formatText(this.fdName)
							}, infoNode);
							if(this.fdDept) {
								domConstruct.create("div", {
									className : "muiPersonSeachItemDep",
									innerHTML : util.formatText(this.fdDept)
								}, infoNode);
							}
						},


						startup : function() {
							if (this._started) {
								return;
							}
							this.inherited(arguments);
						},
						
						_setLabelAttr : function(text) {
							if (text)
								this._set("label", text);
						}
					});
			return item;
		});