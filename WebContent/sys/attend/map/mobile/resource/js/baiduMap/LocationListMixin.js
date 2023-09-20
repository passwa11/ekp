define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"./LocationPoiItemMixin" ], function(declare,
		_TemplateItemListMixin, LocationPoiItemMixin) {

	return declare("sys.attend.LocationPoiListMixin", [ _TemplateItemListMixin ], {

		itemRenderer : LocationPoiItemMixin
	});
});