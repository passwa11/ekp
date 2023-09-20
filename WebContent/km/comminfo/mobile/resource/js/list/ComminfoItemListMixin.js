define([
        "dojo/_base/declare",
    	"mui/list/_TemplateItemListMixin",
    	"km/comminfo/mobile/resource/js/list/item/ComminfoItemMixin"
    	], function(declare, _TemplateItemListMixin, ProcessItemMixin) {
    	
    	return declare("mui.list.ComminfoItemListMixin", [_TemplateItemListMixin], {
    		
    		itemTemplateString : null,

    		itemRenderer: ProcessItemMixin
    		
    	});
});