define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"third/mall/mobile/resource/js/ReuseTemplateItemMixin"
	], function(declare, _TemplateItemListMixin, itemMixin) {
	
	return declare("third.mall.mobile.resource.js.ReuseTemplateItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,

		itemRenderer: itemMixin
		
	});
});