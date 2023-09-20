define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/HrStaffPersonInfoLogItemMixin",
	'dojo/_base/lang',
	'dojo/when',
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'mui/util'
	], function(declare, _TemplateItemListMixin, HrStaffPersonInfoLogItemMixin,lang,when,JsonStore, JsonpStore,util) {
	
	return declare("hr.staff.mobile.resource.js.list.HrStaffPersonInfoLogListMixin", [_TemplateItemListMixin], {
		itemRenderer: HrStaffPersonInfoLogItemMixin
	});
});