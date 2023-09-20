define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"km/forum/mobile/resource/js/ForumSubCateItemMixin" ], function(declare,
		_TemplateItemListMixin, ForumSubCateItemMixin) {

	return declare("km.forum.ForumSubCateListMixin", [ _TemplateItemListMixin ], {

		itemRenderer : ForumSubCateItemMixin
	});
});