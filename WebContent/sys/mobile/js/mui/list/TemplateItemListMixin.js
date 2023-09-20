define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/list/item/TemplateItemMixin"
	], function(declare, _TemplateItemListMixin, TemplateItemMixin) {
	
	return declare("mui.list.TemplateItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,

		itemRenderer: TemplateItemMixin
		
	});
});