define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/circulation/mobile/js/CirculationItemNewVersionMixin"
	], function(declare, _TemplateItemListMixin, itemMixin) {
	
	return declare("sys.circulation.CirculationItemListNewVersionMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,

		itemRenderer: itemMixin
		
	});
});