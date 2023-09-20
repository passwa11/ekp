define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/staffPersonInfoItemMixin",
	'dojo/_base/lang',
	'dojo/query',
	'dojo/topic'
	], function(declare, _TemplateItemListMixin, staffPersonInfoItemMixin,lang,query,topic) {
	
	return declare("hr.staff.mobile.resource.js.staffPersonInfoListMixin", [_TemplateItemListMixin], {
		itemRenderer: staffPersonInfoItemMixin,
		id:'hr-staff-list',
		initUrl:'',
		startup:function(){
			this.set("url",this.initUrl);
			this.inherited(arguments);
			var _this = this;
			topic.subscribe('hr/criteria/value',function(queryString){
				_this.set("url",_this.initUrl+queryString)
				_this.reload();
			})
		}
	});
});