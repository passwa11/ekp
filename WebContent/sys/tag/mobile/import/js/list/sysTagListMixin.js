define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/tag/mobile/import/js/item/sysTagItemMixin",
	'dojo/_base/lang',
	'dojo/query',
	'dojo/topic',
	'mui/util'
	], function(declare, _TemplateItemListMixin, HrStaffTagItemMixin,lang,query,topic,util) {
	
	return declare("hr.staff.mobile.resource.js.HrStaffTagItemMixin", [_TemplateItemListMixin], {
		itemRenderer: HrStaffTagItemMixin,
		selectValue:[],
		formatDatas : function(datas) {
			var dataed = [];
			for (var i = 0; i < datas.length; i++) {
				var datasi = datas[i];
				dataed[i] = {};
				for (var j = 0; j < datasi.length; j++) {
					dataed[i][datasi[j].col] = datasi[j].value;
					if(datasi[j].col=='index'){
						dataed[i][datasi[j].col] = i;
					}
				}
			}
			return dataed;
		},
		startup:function(){
			this.inherited(arguments);
			var _this =this;
			
			topic.subscribe("hr/staff/tag/list/value",function(data){
				if(data['value']){
					_this.selectValue[data['index']]=data;
				}else{
					_this.selectValue[data['index']]="";
				}
				topic.publish('hr/staff/tag/selectvalue',_this.selectValue);
			})
		}
	});
});