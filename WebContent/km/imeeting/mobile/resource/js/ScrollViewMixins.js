define( [ "dojo/_base/declare","dojox/mobile/ScrollableView", "dojo/dom-style", "dojo/ready","dojo/topic"],
		function(declare, ScrollableView, domStyle, ready,topic) {
	
	return declare("mui.table.ScrollViewMixins", [ScrollableView], {
		
		scrollDir:'h',
		scrollBar:false,
		
		buildRendering : function() {
			this.inherited(arguments);
			this.resizeHeight();
		},
		resizeHeight:function(){
			var self = this;
			self.domNode.style.background="#fff";
			topic.subscribe("changeTableScrollHeight",function(e){
				self.height = (e+36)+"px";
				self.domNode.style.height = (e+36)+"px";
				
			});
		}
		
	});
});