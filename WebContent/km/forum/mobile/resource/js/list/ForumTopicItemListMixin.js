define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"km/forum/mobile/resource/js/list/ForumTopicItemMixin"
	], function(declare, _TemplateItemListMixin, ForumTopicItemMixin) {
	//图文混编
	return declare("km.forum.mobile.resource.js.ForumTopicItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: ForumTopicItemMixin
	});
});