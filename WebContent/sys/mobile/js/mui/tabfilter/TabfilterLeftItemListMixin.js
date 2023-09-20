define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/tabfilter/TabfilterLeftItemMixin"
	], function(declare, _TemplateItemMixin, TabfilterLeftItemMixin) {
	
	return declare("mui.tabfilter.TabfilterLeftItemListMixin", [_TemplateItemMixin], {
		
		itemRenderer : TabfilterLeftItemMixin
		
	});
});