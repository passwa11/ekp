define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "dojo/topic", "sys/mportal/mobile/OpenProxyMixin" ],
		
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				
				util, topic, OpenProxyMixin) {
			var item = declare("sys.mportal.ProcessItemMixin", [ ItemBase,
					OpenProxyMixin ], {

				baseClass : "muiPortalProcessItem",

				// 流程简要信息
				summary : "",

				// 创建时间
				created : "",

				// 创建者
				creator : "",

				// 状态
				status : "",

				// 链接
				href : "",
				buildRendering : function() {
					this.inherited(arguments);
					
					var linkNode = domConstruct.create('a', { className : 'muiPortalProcessLink' }, this.domNode);
					
					if (this.href) {
						this.proxyClick(linkNode, this.href, '_blank');
					}
					
					// 标题
					var labelNode = domConstruct.create('h2', { className : 'muiPortalProcessLabel muiFontSizeM muiFontColorInfo', innerHTML : this.label }, linkNode);
					if (this.status) {
						var statusNode = domConstruct.create('div', { className : 'muiPortalProcessStaus muiFontSizeS' }, linkNode);
						statusNode.innerHTML = this.status;
					}
					
					// 底部容器DOM
					var footerNode = domConstruct.create('div', {className : 'muiPortalProcessFooter muiFontSizeS muiFontColorMuted'}, linkNode);
					
					// 人员姓名
					if (this.creator) {
						var creatorNode = domConstruct.create('span', {className : 'muiPortalProcessCreator'}, footerNode), 
						textNode = domConstruct.create('span',{innerHTML : this.creator}, creatorNode);
					}
					// 时间
					if (this.created) {
						var createdNode = domConstruct.create('span', {className : 'muiPortalProcessCreated'}, footerNode), 
						textNode = domConstruct.create('span',{innerHTML : this.created}, createdNode);
					}
					// 审批节点信息
					if (this.summary) {
						var summaryNode = domConstruct.create('span', { className : 'muiPortalProcessSummary'}, footerNode), 
						    textNode = domConstruct.create('span',{innerHTML : this.summary }, summaryNode);
					}
				},

				_setLabelAttr : function(label) {
					if (label)
						this._set("label", label);
				}

			});
			return item;
		});