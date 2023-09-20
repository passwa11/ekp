define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/ftsearch/mobile/list/item/SysFtserachItemMixin"
	], function(declare, _TemplateItemListMixin, SysFtserachItemMixin) {
	
	return declare("sys.ftsearch.SysFtsearchListMixin", [_TemplateItemListMixin], {
		
		itemRenderer: SysFtserachItemMixin
	});
});
