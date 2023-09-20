define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/organization/mobile/js/eco/muiOrgEcoAddressItemMixin",
	"mui/address/AddressChannelMixin",
	"mui/address/AddressItemListMixin"
	], function(declare, _TemplateItemMixin, AddressItemMixin, AddressChannelMixin, AddressItemListMixin) {
	
	return declare("sys.org.eco.AddressItemListMixin", [_TemplateItemMixin, AddressChannelMixin, AddressItemListMixin], {
		
		itemRenderer : AddressItemMixin,
		
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			if(this.isSearch){
				props['isSearch'] = true;
			}
			return props;
		},
	});
});