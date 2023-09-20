define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeItemMixin"
	], function(declare, _TemplateItemMixin, LbpmAuthorizeItemMixin) {
	
	return declare("sys.lbpmext.authorize.mobile.js.authorizescope.LbpmAuthorizeItemListMixin", [_TemplateItemMixin], {
		
		itemRenderer : LbpmAuthorizeItemMixin,
		
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			props['key'] = this.key;
			return props;
		}
	});
});