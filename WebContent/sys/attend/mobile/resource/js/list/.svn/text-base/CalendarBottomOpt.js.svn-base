define(['dojo/_base/declare','dijit/_WidgetBase','dijit/_Contained','dojo/dom-construct','dojo/dom-style'],
		function(declare,WidgetBase,Contained,domConstruct,domStyle){
	
	return declare('attend.calendar.CalendarBottomOpt',[WidgetBase,Contained],{
		
		startup:function(){
			var parent=this.getParent();
			if(parent){
				//domConstruct.place(this.srcNodeRef, parent.eventNode,"last");
				var opt = parent.eventNode.parentNode;
				domConstruct.empty(opt);
				domConstruct.place(this.srcNodeRef, opt,"last");
			}
		}
		
	});
	
});