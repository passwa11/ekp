define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"./item/AuditItemMixin"
	], function(declare, _TemplateItemListMixin, AuditItemMixin) {
	
	return declare("km.imeeting.list.AuditItemListMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		itemRenderer: AuditItemMixin
	});
});