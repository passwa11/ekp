define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"dojox/mobile/iconUtils", "mui/util",
		"mui/list/item/_ListLinkItemMixin" ], function(declare, domConstruct,
		domClass, domStyle, domAttr, ItemBase, iconUtils, util,
		_ListLinkItemMixin, KmsLearnRangMixin) {
	var item = declare("sys.follow.SysFollowItemMixin", [ ItemBase, _ListLinkItemMixin ],
			{
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
						this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag, {
							className : ''
						});
					}
					this.inherited(arguments);
					if (!this._templated)
						// 构建内部元素
						this.buildInternalRender();
				},

				buildInternalRender : function() {
					
					if (this.docSubject) {
						this.hrefNode = domConstruct.create('div', {
							className : 'sysFollowTitle',
							innerHTML : this.docSubject
						}, this.domNode);
					}
					
					var ul = domConstruct.create('ul', {
						className : 'sysFollowInfo clearfix',
					}, this.domNode);
					
					if (this.docCreateTime) {
						domConstruct.create('li', {
							innerHTML : this.docCreateTime
						}, ul);
					}
					
					if (this.from) {
						domConstruct.create('li', {
							innerHTML : '来自：' + this.from
						}, ul);
					}
					
					if (this.href) {
						this.makeLinkNode(this.domNode);
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