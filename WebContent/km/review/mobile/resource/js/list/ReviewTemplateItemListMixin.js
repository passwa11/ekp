define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"km/review/mobile/resource/js/list/ReviewTemplateItemMixin"
	], function(declare, _TemplateItemListMixin, itemMixin) {
	
	return declare("km.review.mobile.resource.js.list.ReviewTemplateItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,

		itemRenderer: itemMixin
		
	});
});