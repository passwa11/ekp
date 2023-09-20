define( [ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_Contained", "dijit/_Container", "dojo/dom-style","dojo/topic"],
		function(declare, WidgetBase, Contained, Container, domStyle,topic) {
	
	return declare("km.immeting.resource.ScrollContainerHMixins", [WidgetBase, Contained, Container], {

		buildRendering : function() {
			this.inherited(arguments);
		
		},
		startup : function() {
			this.inherited(arguments);
			var self = this;
			topic.subscribe("changeTableScrollHeight",function(e){
				self.domNode.style.height = (e+10)+"px";
			});
		
		},
		
	});
});