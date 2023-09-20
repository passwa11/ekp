define([
    "dojo/_base/declare",
    "dojo/topic",
	"km/calendar/mobile/resource/js/list/CalendarItemListMixin"
	], function(declare, topic, _CalendarItemListMixin) {
	
	return declare("km.calendar.mobile.mportal.js.CalendarItemListMixin", [ _CalendarItemListMixin ], {
		
		rowsize : 5,
		
		generateList : function(items) {
			items = items || [];
			if(items.length > this.rowsize){
				var amount = items.length - this.rowsize;
				items.splice(-amount,amount);
			}
			this.inherited(arguments);
		},
		
		buildNoDataItem : function(widget){
			if(this.tempItem){
				if(widget.removeChild)
					widget.removeChild(this.tempItem);
				this.tempItem.destroy();
				this.tempItem = null;
			}
			if(widget.totalSize==0){
				topic.publish('/mui/list/noData',this);
			}
		},
		
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			props['id'] = null;
			return props;
		}
		
	});
});