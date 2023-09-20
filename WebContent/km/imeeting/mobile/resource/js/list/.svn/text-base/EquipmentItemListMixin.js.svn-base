define([
    "dojo/_base/declare",
    "dojo/query",
    "dojo/date/locale",
    "mui/i18n/i18n!sys-mobile",
    "mui/util",
	"mui/list/_TemplateItemListMixin",
	"km/imeeting/mobile/resource/js/list/item/EquipmentItemMixin"
	], function(declare, query, locale, msg ,util,_TemplateItemMixin, EquipmentItemMixin) {
	
	return declare("km.imeeting.EquipmentItemListMixin", [_TemplateItemMixin], {
		
		itemRenderer : EquipmentItemMixin,
		
		dataUrl : util.formatUrl('/km/imeeting/km_imeeting_equipment/kmImeetingEquipment.do?method=listEquipment&type=free'),
		
		buildRendering : function(){
			var fdHoldDate = query('[name="fdHoldDate"]')[0].value || '',
				fdHoldDurationHour = query('[name="fdHoldDurationHour"]'),
				fdFinishDate = '';
			if(fdHoldDate && fdHoldDurationHour.length > 0){
				var options = {
						selector : 'time',
						timePattern : dojoConfig.DateTime_format
				};
				var _fdHoldDate = locale.parse(fdHoldDate,options);
				fdFinishDate  = _fdHoldDate.getTime() + parseFloat(fdHoldDurationHour[0].value) * 3600 * 1000;
				fdFinishDate = locale.format(new Date(fdFinishDate),options);
			}else{
				if(query('[name="fdFinishDate"]').length > 0){
					fdFinishDate = query('[name="fdFinishDate"]')[0].value;
				}
			}
			this.dataUrl = util.setUrlParameter(this.dataUrl,'fdHoldDate',fdHoldDate);
			this.dataUrl = util.setUrlParameter(this.dataUrl,'fdFinishDate',fdFinishDate);
			this.inherited(arguments);
		},
		
		//往下查看数据
		_cateChange:function(srcObj,evt){
			this.dataUrl = util.setUrlParameter(this.dataUrl,'parentId',evt.fdId);
			this.inherited(arguments);
		},
		
		_setUrlAttr: function(url){
			return ;
		},
		
	});
});