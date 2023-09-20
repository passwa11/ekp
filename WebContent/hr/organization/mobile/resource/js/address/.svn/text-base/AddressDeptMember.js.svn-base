/**
 * 同部门成员组件
 */
define([
	"dojo/_base/declare", 
	"dojo/topic",
	"dijit/_WidgetBase",
	"dojo/dom-construct",
	"dojox/mobile/TransitionEvent",
	"mui/device/adapter",
	"mui/history/listener",
	"mui/util",
	"mui/i18n/i18n!sys-mobile:mui"
	], function(declare, topic, WidgetBase, domConstruct, TransitionEvent, adapter, listener, util, Msg) {
	
	return declare("mui.address.AddressDeptMember", [ WidgetBase ], {
		
		buildRendering: function() {
			var self = this;
			this.inherited(arguments);
			if((this.selType & window.ORG_TYPE_PERSON) != window.ORG_TYPE_PERSON){
				return;
			}
			this.dom = domConstruct.create('div',{ className: 'muiAddressItem muiUsualDeptMember' },this.domNode);
			// 图标
			var iconNode = domConstruct.create('div',{ className: 'muiAddressItemIcon' }, this.dom);
			domConstruct.create('img',{ src: util.formatUrl('/sys/mobile/css/themes/default/images/address-deptmember.png') },iconNode);
			// 名称
			domConstruct.create('div',{ className: 'muiAddressItemName', innerHTML: Msg['mui.mobile.address.deptMember'] },this.dom);
			// 更多
			var moreNode = domConstruct.create('div',{ className: 'muiAddressItemMore'},this.dom);
			domConstruct.create('i',{ className: 'mui mui-forward' }, moreNode);
		},

		startup: function(){
			this.inherited(arguments);
			this.dom && this.connect(this.dom, 'click', 'openView')
		},
		
		openView: function(){
			var previousTitle;
			var self = this;
			// 添加历史记录
			listener.push({
				forwardCallback: function(){
					previousTitle = document.title;
					topic.publish('/mui/address/swapView', self, {
						viewKey: 'deptMember'
					});
					new TransitionEvent(document.body , {
						moveTo: 'deptMemberView_' + self.key
					}).dispatch();
					adapter.setTitle(Msg['mui.mobile.address.deptMember']);
				},
				backCallback: function(){
					topic.publish('/mui/address/swapView', self, {
						viewKey: 'default'
					});
					new TransitionEvent(document.body, {
						moveTo: 'defaultView_' + self.key
					}).dispatch();
					if(previousTitle){
						adapter.setTitle(previousTitle);
						previousTitle = null;
					}
				}
			});
		}
		
	});
});