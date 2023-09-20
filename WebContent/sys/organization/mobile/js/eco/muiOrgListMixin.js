define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"sys/organization/mobile/js/eco/muiOrgListItemMixin" ],
	function(declare, _TemplateItemListMixin, muiOrgListItemMixin) {

	return declare("sys.org.eco.list.mixin", [ _TemplateItemListMixin ], {

		itemRenderer : muiOrgListItemMixin,

	});
});