define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/list/item/CardItemDMixin"
	], function(declare, _TemplateItemListMixin, CardItemDMixin) {
	
	return declare("mui.list.CardItemDListMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		itemRenderer: CardItemDMixin
	});
});