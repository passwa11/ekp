define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"./DialogItemMixin",
	"./DialogChannelMixin"
	], function(declare, _TemplateItemMixin, DialogItemMixin,DialogChannelMixin) {
	
	return declare("mui.selectdialog.DialogItemListMixin", [_TemplateItemMixin,DialogChannelMixin], {

		itemRenderer : DialogItemMixin,
		
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			props['scope'] = this.scope;
			props['dataUrl'] = this.dataUrl;
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
		
	});
});