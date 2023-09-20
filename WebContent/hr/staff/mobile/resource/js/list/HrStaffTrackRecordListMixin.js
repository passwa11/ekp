define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/HrStaffTrackRecordItemMixin",
	'dojo/_base/lang',
	'dojo/when',
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'mui/util',
	'dojo/topic'
	], function(declare, _TemplateItemListMixin, HrStaffTrackRecordItemMixin,lang,when,JsonStore, JsonpStore,util,topic) {
	
	return declare("hr.staff.mobile.resource.js.list.HrStaffTrackRecordListMixin", [_TemplateItemListMixin], {
		itemRenderer: HrStaffTrackRecordItemMixin,
		startup:function(){
			this.inherited(arguments);
		},
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			props['personInfoId'] = Com_GetUrlParameter(this.url,"personInfoId");
			props['templateString'] = this.itemTemplateString;
			return props;
		}
	});
});