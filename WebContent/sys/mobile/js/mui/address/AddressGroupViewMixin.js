/**
 * 群组视图Mixin
 */
define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"dojox/mobile/TransitionEvent",
	"mui/i18n/i18n!sys-mobile:mui",
	"dojo/request",
	"mui/util"
	], function(declare, domConstruct, TransitionEvent, Msg, request, util) {
	return declare("mui.address.AddressGroupViewMixin", null, {

		buildRendering: function(){
			this.inherited(arguments);
			
			// 生态组织判断是否显示公共组群项
			var self = this;
			request.get(util.formatUrl("/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=getUserType"), {
	            headers: {'Accept': 'application/json'},
	            handleAs: 'json'
			}).then(function(data) {
				if(data)
					// 创建公共群组项
					self.commonGroupNode = self.createItem({
						className: 'muiAddressCommonGroupItem',
						text: Msg['mui.mobile.address.commonGroup'],
						viewKey: 'commonGroupView'
					});
				
				// 创建个人群组项
				self.myGroupNode = self.createItem({
					className: 'muiAddressMyGroupItem',
					text: Msg['mui.mobile.address.myGroup'],
					viewKey: 'myGroupView'
				})
			});
			
		},
		
		createItem: function(item){
			var self = this;
			var dom = domConstruct.create('div',{ className: 'muiAddressItem muiAddressGroupItem ' + item.className },this.domNode);
			domConstruct.create('div',{ className: 'muiAddressItemName', innerHTML: item.text }, dom);
			this.connect(dom, 'click', function(){
				self.openView(item.viewKey);
			});
			// 更多
			var moreNode = domConstruct.create('div',{ className: 'muiAddressItemMore'},dom);
			domConstruct.create('i',{ className: 'mui mui-forward' }, moreNode);
			return dom;
		},
		
		openView: function(viewKey){
			var self = this;
			var opts = {
				moveTo: viewKey + '_' + this.key
			};
			new TransitionEvent(document.body ,  opts ).dispatch();
		}
		
	});
});
