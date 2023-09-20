define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"./item/TextItemSortMixin"
	], function(declare, _TemplateItemListMixin, TextItemSortMixin) {
	
	return declare("sys.news.TextItemListSortMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		itemRenderer: TextItemSortMixin
	});
});