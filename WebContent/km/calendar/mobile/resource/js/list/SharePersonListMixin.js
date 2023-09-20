define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"km/calendar/mobile/resource/js/list/item/SharePersonItemMixin"
	], function(declare, _TemplateItemListMixin, ItemMixin) {
	
	return declare("km.calendar.mobile.resource.js.SharePersonItemListMixin", [_TemplateItemListMixin], {
	
		itemRenderer: ItemMixin
		
	});
});