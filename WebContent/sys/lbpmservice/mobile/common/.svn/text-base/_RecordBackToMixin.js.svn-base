define(["dojo/_base/declare","dojox/mobile/viewRegistry","dijit/_WidgetBase"],function(declare,viewRegistry,WidgetBase){
	return declare("sys.lbpmservice.mobile.common._RecordBackToMixin",[WidgetBase],{

		startup : function() {
			this.recordBackTo();
			this.inherited(arguments);
		},

		recordBackTo:function(){
			var viewId = this.bakcTo;
			if(!viewId){
				var view = viewRegistry.getEnclosingView(this.domNode);
				view = viewRegistry.getParentView(view) || view;
				viewId = view.id;
			}
			window.backToViewId = viewId;
		},
	});
});