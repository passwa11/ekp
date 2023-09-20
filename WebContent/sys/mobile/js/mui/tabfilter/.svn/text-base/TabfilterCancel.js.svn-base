define([ "dojo/_base/declare", "dojo/topic" ], function(declare, topic) {
	return declare("mui.tabfilter.muiTabfilterCancel", null, {
		postCreate : function() {
			this.inherited(arguments);
			this.connect(this.domNode, "click", "_onCancel");
		},
		
		
		
		_onCancel : function() {
			topic.publish("/mui/tabfilter/cancel", this);
		}
	});
});
