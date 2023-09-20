define(	["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"dojox/mobile/iconUtils", "mui/util", "mui/list/item/_ListLinkItemMixin"],
		function(declare, domConstruct, domClass, 
				domStyle, domAttr, ItemBase,
				iconUtils, util, _ListLinkItemMixin,KmsLearnRangMixin) {
			var item = declare("sys.book.SysBookItemMixin", 
							[ItemBase,_ListLinkItemMixin], {
				
							tag : 'li',
							// 标题
							label : '',
							// 链接
							href : 'javascript:;',
							// 主键
							fdId : '',
					
							buildRendering : function() {
								this._templated = !!this.templateString;
								if (!this._templated) {
									this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag, {className : 'muiTextItem muiListItem' });
									this.contentNode = domConstruct.create('div', null , this.domNode);
								}
								this.inherited(arguments);
								if (!this._templated)
									// 构建内部元素
									this.buildInternalRender();
							},
					
							buildInternalRender : function() {
								this.articleNode = domConstruct.create('a', null, this.contentNode);
								
								if (this.docSubject) {
									
									this.hrefNode = domConstruct.create('span', {
												className : 'muiSubject muiFontSizeM muiFontColorInfo',
												innerHTML : this.docSubject
											}, this.articleNode);
								}
								
								this.timeNode = domConstruct.create("p" , { className : "muiListInfo" } , this.contentNode);
								
								if(this.docCreateTime) {
									domConstruct.create("div" , {innerHTML: this.docCreateTime, className : "muiFollowTime muiFontSizeS muiFontColorMuted"} ,this.timeNode);
								}
								
								if (this.href) {
									this.makeLinkNode(this.articleNode);
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