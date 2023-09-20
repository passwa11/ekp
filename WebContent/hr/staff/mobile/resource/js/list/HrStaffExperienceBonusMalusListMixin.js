define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/HrStaffExperienceBonusMalusItemMixin",
	'dojo/_base/lang',
	'dojo/when',
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'./hrstaffUtil'
	], function(declare, _TemplateItemListMixin, HrStaffExperienceBonusMalusItemMixin,lang,when,JsonStore, JsonpStore,util) {
	
	return declare("hr.staff.mobile.resource.js.list.HrStaffExperienceBonusMalusListMixin", [_TemplateItemListMixin,util], {
		itemRenderer: HrStaffExperienceBonusMalusItemMixin
	});
});