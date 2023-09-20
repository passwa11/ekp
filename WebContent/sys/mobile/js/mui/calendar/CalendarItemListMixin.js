define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"mui/calendar/item/CalendarItemMixin"
	], function(declare, _TemplateItemListMixin, ItemMixin) {
	
	return declare("mui.calendar.CalendarItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: ItemMixin,
		
		currentDate:new Date(),
		
		buildRendering:function(){
			this.inherited(arguments);
			this.subscribe('/mui/calendar/valueChange','saveCurrentDate');
		},
	
		//选中日期改变时,缓存选中日期
		saveCurrentDate:function(widget,args){
			this.currentDate=args.currentDate;
		}
		
	});
});