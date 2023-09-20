define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/HrStaffSalaryAdjustRecordItemMixin",
	'dojo/_base/lang',
	'dojo/when',
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'mui/util'
	], function(declare, _TemplateItemListMixin, HrStaffSalaryAdjustRecordItemMixin,lang,when,JsonStore, JsonpStore,util) {
	
	return declare("hr.staff.mobile.resource.js.list.HrStaffSalaryAdjustRecordListMixin", [_TemplateItemListMixin], {
		itemRenderer: HrStaffSalaryAdjustRecordItemMixin
	});
});