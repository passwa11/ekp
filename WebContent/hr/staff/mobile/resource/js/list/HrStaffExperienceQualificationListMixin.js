define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/HrStaffExperienceQualificationItemMixin",
	'dojo/_base/lang',
	'dojo/when',
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'./hrstaffUtil'
	], function(declare, _TemplateItemListMixin, HrStaffExperienceQualificationItemMixin,lang,when,JsonStore, JsonpStore,util) {
	
	return declare("hr.staff.mobile.resource.js.list.HrStaffExperienceQualificationListMixin", [_TemplateItemListMixin,util], {
		itemRenderer: HrStaffExperienceQualificationItemMixin
	});
});