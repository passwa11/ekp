define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/lbpmservice/mobile/workitem/CommonUsageItemMixin"
	], function(declare, _TemplateItemListMixin, CommonUsageItemMixin) {
	
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: CommonUsageItemMixin
	});
});
