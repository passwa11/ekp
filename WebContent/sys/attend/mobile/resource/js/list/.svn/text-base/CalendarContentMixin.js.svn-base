define(['dojo/_base/declare','dojo/dom-construct','dojo/query','dojo/dom-attr'],
		function(declare,domConstruct,query,domAttr){
	
	return declare('sys.attend.mobile.resource.js.list.CalendarContentMixin',[],{
		
	
		processEvent : function(haveEvent,obj) {
			if( this.getEnclosingCalendarView(this) !== this.getEnclosingCalendarView(obj))
				return;
			if (haveEvent) {
				this.haveEvent = haveEvent;
			}
			query('.journey_status.journey',this.domNode).remove();
			for (var i = 0; i < this.datesNode.length; i++) {
				var key = domAttr
						.get(this.datesNode[i], "date");
				if(dojoConfig.locale == 'en-us' && key){
					key = key.split('-');
					key = key[1] + '/' + key[2] + '/' + key[0];
				}
				if (this.haveEvent && this.haveEvent[key]=='0') {
					domConstruct.create("i", {
						className : "journey_status journey late"
					}, this.datesNode[i].parentNode);
				}
				if (this.haveEvent && this.haveEvent[key]=='1') {
					domConstruct.create("i", {
						className : "journey_status journey normal"
					}, this.datesNode[i].parentNode);
				}
				if (this.haveEvent && this.haveEvent[key]=='2') {
					domConstruct.create("i", {
						className : "journey_status journey outside"
					}, this.datesNode[i].parentNode);
				}
			}
		}
	});
	
});