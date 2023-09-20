define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"km/review/mobile/resource/js/list/ReviewItemMixin"
	], function(declare, _TemplateItemListMixin, ProcessItemMixin) {
	
	return declare("km.review.list.ReviewItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,

		itemRenderer: ProcessItemMixin
		
	});
});