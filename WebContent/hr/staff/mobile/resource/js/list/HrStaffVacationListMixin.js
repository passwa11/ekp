define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/HrStaffVacationItemMixin",
	'dojo/_base/lang',
	'dojo/query',
	'dojo/topic',
	'mui/util'
	], function(declare, _TemplateItemListMixin, HrStaffVacationItemMixin,lang,query,topic,util) {
	
	return declare("hr.staff.mobile.resource.js.staffVacationListMixin", [_TemplateItemListMixin], {
		itemRenderer: HrStaffVacationItemMixin,
		startup:function(){
			this.inherited(arguments);
		},
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			try{
				var Index = util.getUrlParameter(this.url,"vaIndex");
				if(Index){
					props['vaIndex'] = Index;
				}
				props["fdName"] = props["fdPerson.fdName"];
			}catch(e){
				console.log(e)
			}
			return props;
		}
	});
});