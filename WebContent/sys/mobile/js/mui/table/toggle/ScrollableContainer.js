define( [ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_Contained", "dijit/_Container", "dojo/dom-style","dojo/query"],
		function(declare, WidgetBase, Contained, Container, domStyle, query) {
	
	return declare("mui.table.ScrollableContainer", [WidgetBase, Contained, Container], {
		
		baseClass : "muiScrollableContainer",

		buildRendering : function() {
			this.inherited(arguments);
		},
		startup : function() {
			this.inherited(arguments);
		},
		
		resizeH:function(h){
			if(h>0){
				domStyle.set(this.domNode,{'height': h + 'px'});
			}
		}
	});
});