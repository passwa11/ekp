define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/seachListItemMixin",
	'dojo/_base/lang',
	'dojo/query',
	], function(declare, _TemplateItemListMixin, searchListMixin,lang,query) {
	return declare("hr.staff.mobile.resource.js.searchListMixin", [_TemplateItemListMixin], {
		key:'',
		itemRenderer: searchListMixin,
		startup:function(){
			this.inherited(arguments);
		},
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			props['key'] = this.key;
			return props;
		}
	});
});