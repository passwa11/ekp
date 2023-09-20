define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/list/item/DocItemMixin"
	], function(declare, _TemplateItemListMixin, DocItemMixin) {
	
	return declare("mui.list.DocItemListMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		itemRenderer: DocItemMixin
	});
});