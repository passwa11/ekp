define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/news/mobile/js/item/NewsItemMixin"
	], function(declare, _TemplateItemListMixin, NewsItemMixin) {

	return declare("sys.news.mobile.js.NewsItemListMixin", [_TemplateItemListMixin], {
		
		itemRenderer: NewsItemMixin
		
	});
	
});