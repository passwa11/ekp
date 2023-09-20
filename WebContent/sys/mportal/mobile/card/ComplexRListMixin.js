define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"./item/ComplexRItemMixin"
	], function(declare, _TemplateItemListMixin, ComplexRItemMixin) {
	
	return declare("sys.mportal.ComplexRListMixin", [_TemplateItemListMixin], {
		
		itemRenderer: ComplexRItemMixin
	});
});