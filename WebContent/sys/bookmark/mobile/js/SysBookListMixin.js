define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/bookmark/mobile/js/SysBookItemMixin"
	], function(declare, _TemplateItemListMixin, SysBookItemMixin) {
	
	return declare("sys.list.SysBookListMixin", [_TemplateItemListMixin], {
		
		itemRenderer : SysBookItemMixin
	});
});