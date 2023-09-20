define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"km/archives/mobile/resource/js/TextItemMixin"
	], function(declare, _TemplateItemListMixin, TextItemMixin) {
	
	
	return declare("km.archives.TextItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,

		itemRenderer : TextItemMixin
	});
});