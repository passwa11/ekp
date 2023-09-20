define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"./AddressItemMixin",
	"./AddressChannelMixin"
	], function(declare, _TemplateItemMixin, AddressItemMixin, AddressChannelMixin) {
	
	return declare("mui.address.AddressItemListMixin", [_TemplateItemMixin, AddressChannelMixin], {
		
		itemRenderer : AddressItemMixin,
		
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			props['scope'] = this.scope;
			props['dataUrl'] = this.dataUrl;
			props['selType'] = this.selType;
			props['deptLimit'] = this.deptLimit;
			props['channel'] = this.channel;
			return props;
		},
		
		_cateChange : function(srcObj) {
			this.isSameChannel(srcObj) && this.inherited(arguments);
		},

		_cateReback : function(srcObj) {
			this.isSameChannel(srcObj) && this.inherited(arguments);
		},

		_cateSelected : function(srcObj) {
			this.isSameChannel(srcObj) && this.inherited(arguments);
		},

		_scrollToCate : function(srcObj) {
			this.isSameChannel(srcObj) && this.inherited(arguments);
		},

//		_setCurSel : function(srcObj) {
//			this.isSameChannel(srcObj) && this.inherited(arguments);
//		},

		_setNavInfo : function(srcObj) {
			this.isSameChannel(srcObj) && this.inherited(arguments);
		}
		
	});
});