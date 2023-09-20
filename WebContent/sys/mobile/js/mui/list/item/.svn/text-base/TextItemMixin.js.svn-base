define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"dojox/mobile/iconUtils", "mui/util", "./_ListLinkItemMixin",
		"mui/list/item/DocStatusMixin", "mui/list/item/TopMixin",
		"mui/list/item/IntroduceMixin"],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				iconUtils, util, _ListLinkItemMixin, DocStatusMixin, TopMixin, IntroduceMixin) {
			var item = declare("mui.list.item.TextItemMixin", [ ItemBase, _ListLinkItemMixin, IntroduceMixin, TopMixin, DocStatusMixin ], {

				// 标题
				label : '',
				// 概括
				summary : '',
				/**
				 *  文档状态状态
				 *	传入的数据源请不要带<div>,<span>之类的标签
				 *  并且格式请统一，如:文档状态传入:00,10,20,30等，DocStatusMixin会进行转换
				 */
				status : '',
				// 标签默认样式
				tagClass : 'muiTextItemTag',
				/**
				 * 是否置顶，传入true/false，
				 */
				isTop : '',
				/**
				 * 是否精华，传入true/false
				 */
				isIntroduce : '',
				// 创建时间
				created : '',
				// 创建者
				creator : '',
				tag : 'li',

				buildRendering : function() {
					this._templated = !!this.templateString;
					if (!this._templated) {
						this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag, {
							className : 'muiTextItem muiListItemCard'
						});
						this.contentNode = domConstruct.create('div', {});
					}
					this.inherited(arguments);

					if (!this._templated)
						this.buildInternalRender();

					if (this.contentNode)
						domConstruct.place(this.contentNode, this.domNode);
				},

				buildLabel : function () {

					// 应该不存在没有标题的情况吧？
					if(this.label) {
						
						this.muiTextItemTitle = domConstruct.create('div', {
							className : 'muiTextItemTitle muiFontSizeM'
						}, this.domNode);

						this.buildTag(this.muiTextItemTitle);
						
						this.muiTextItemTitle.innerHTML = this.muiTextItemTitle.innerHTML + this.label;
					}
					
				},
				
				buildSummary : function () {
					
					if(this.summary)
						domConstruct.create('div', {
							className : 'muiTextItemSummary',
							innerHTML : this.summary
						}, this.domNode);
				},

				buildMessage : function () {
					
					this.messageDomNode = domConstruct.create('ul', {
						className : 'muiTextItemInfo'
					});
					
					this.buildCreator();

					this.buildCreated();
					
					if(this.buildMessage)
						domConstruct.place(this.messageDomNode, this.domNode, 'last');
					
				},
				
				buildCreator : function () {

					if(this.creator){
						
						domConstruct.create('li', {
							innerHTML : this.creator
						}, this.messageDomNode);
						
						this.buildMessage = true;
					}
				},
				
				buildCreated : function () {

					if(this.created){
						
						domConstruct.create('li', {
							innerHTML : this.created
						}, this.messageDomNode);
						
						this.buildMessage = true;
					}
					
				},
				
				buildInternalRender : function() {
					
					if (this.href) {
						this.makeLinkNode(this.domNode);
					}

					this.buildLabel();
					
					this.buildSummary();
					
					this.buildMessage();
					
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