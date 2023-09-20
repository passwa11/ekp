define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"./item/PersonItemMixin" ], function(declare, _TemplateItemListMixin,
		PersonItemMixin) {

	return declare("sys.mportal.PersonListMixin", [ _TemplateItemListMixin ], {

		itemRenderer : PersonItemMixin

	});
});