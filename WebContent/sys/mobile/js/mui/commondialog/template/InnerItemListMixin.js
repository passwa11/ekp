define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/commondialog/template/InnerItemMixin"
	], function(declare, _TemplateItemMixin, InnerItemMixin) {
	
	return declare("mui.commondialog.template.InnerItemListMixin", [_TemplateItemMixin], {
		
		itemRenderer : InnerItemMixin
		
	});
});