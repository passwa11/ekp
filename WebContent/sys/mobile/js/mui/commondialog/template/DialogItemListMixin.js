define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/commondialog/template/DialogItemMixin"
	], function(declare, _TemplateItemMixin, DialogItemMixin) {
	
	return declare("mui.commondialog.template.DialogItemListMixin", [_TemplateItemMixin], {
		
		itemRenderer : DialogItemMixin
		
	});
});