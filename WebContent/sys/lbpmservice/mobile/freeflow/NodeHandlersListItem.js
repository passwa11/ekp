define( [ "dojo/_base/array", "dojo/_base/declare", "dojo/_base/lang","dojo/dom-class",
		"dojo/dom-construct", "dojo/dom-attr", "dojo/dom-style", "dijit/_WidgetBase","dojo/topic","mui/util","dojo/dom","dojo/query"], function(array,
		declare, lang, domClass, domConstruct, domAttr, domStyle, WidgetBase, topic, util, dom, query) {
	return declare("sys.lbpmservice.mobile.freeflow.NodeHandlersListItem",
			[ WidgetBase ], {
				key : null,
				
				text : '',
				
				value : '',
				
				nodeId : '',
				
				canEdit : false,
				
				iconUrl : '/sys/lbpmservice/mobile/freeflow/image.jsp?orgId=!{orgId}',

				buildRendering : function() {
					this.inherited(arguments);
					// 处理人名称
					this.optionContainerNode = domConstruct.create('label', {
						className : 'muiListItem',
						innerHTML : util.formatText(this.text)
					}, this.domNode);
					// 头像图标
					var icon = util.formatUrl(util.urlResolver(this.iconUrl , {
						orgId : this.value
					}));
					domConstruct.create("div", {
						style:{
							background:'url(' + icon +') center center no-repeat',
							backgroundSize:'cover'
						},
						className : 'muiAddressOrgIcon'
					}, this.optionContainerNode, 'first');
					// 删除按钮
					if (this.canEdit) {
						var delDomWrapper = domConstruct.create("div", {
							style:{
								cssFloat:'right',
								width:'2.2rem',
								textAlign:'center'
							}
						}, this.optionContainerNode, 'last');
						var delDom = domConstruct.create("div", {
							className : 'del mui mui-close'
						}, delDomWrapper, 'last');
						this.connect(delDomWrapper,'click',function(evt) {
							if (evt.stopPropagation)
								evt.stopPropagation();
							if (evt.cancelBubble)
								evt.cancelBubble = true;
							if (evt.preventDefault)
								evt.preventDefault();
							if (evt.returnValue)
								evt.returnValue = false;
							// 只剩一个处理人时删除动作被禁止（自由流节点处理人不能为空）
							if (query('._nodeHandlerListItem').length <= 1) {
								return;
							}
							// 销毁组件
							this.destroy();
						});
					}
				},

				postCreate : function() {
					this.inherited(arguments);
				},

				startup : function() {
					this.inherited(arguments);
				}
		});
});
