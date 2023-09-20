define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/lbpmperson/mobile/resource/js/list/LbpmPersonItemMixin"
	], function(declare, _TemplateItemListMixin, LbpmPersonItemMixin) {
	
	return declare("sys.lbpmperson.mobile.list.LbpmPersonItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,

		itemRenderer: LbpmPersonItemMixin
		
	});
});