define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/HrStaffExperienceContractItemMixin",
	'dojo/_base/lang',
	'dojo/when',
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'./hrstaffUtil'
	], function(declare, _TemplateItemListMixin, HrStaffExperienceContractItemMixin,lang,when,JsonStore, JsonpStore,util) {
	
	return declare("hr.staff.mobile.resource.js.list.HrStaffExperienceContractListMixin", [_TemplateItemListMixin,util], {
		itemRenderer: HrStaffExperienceContractItemMixin
	});
});