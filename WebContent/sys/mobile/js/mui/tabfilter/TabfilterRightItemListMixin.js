define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/tabfilter/TabfilterRightItemMixin"
	], function(declare, _TemplateItemMixin, TabfilterRightItemMixin) {
	
	return declare("mui.tabfilter.TabfilterRightItemListMixin", [_TemplateItemMixin], {
		
		itemRenderer : TabfilterRightItemMixin
		
	});
});