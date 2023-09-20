define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"km/imeeting/mobile/resource/js/list/item/MyMeetingCardItemMixin",
	"km/imeeting/mobile/resource/js/list/item/MyMeetingCardYearMonthItemMixin"
	], function(declare, _TemplateItemListMixin, MyMeetingCardItemMixin,MyMeetingCardYearMonthItemMixin) {
	
	return declare("km.imeeting.list.MeetingCardItemListMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		
		itemRenderer: MyMeetingCardItemMixin,
		
		yearMonthRenderer:MyMeetingCardYearMonthItemMixin,
		
		preYearMonth: '',
		
		createListItem:function(item){
			if(this.preYearMonth == item.yearMonth){
				return new this.itemRenderer(this._createItemProperties(item));
			}else{
				this.preYearMonth = item.yearMonth;
				return new this.yearMonthRenderer(this._createItemProperties(item));
			}
		}

	});
});