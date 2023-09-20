define([ "dojo/_base/declare",
         "dojo/_base/array",
         "dojo/_base/lang",
         "dojo/dom-class",
         "dojox/mobile/ViewController",
         "dojox/mobile/viewRegistry",
         "dojo/query",
         "dojo/request",
         "dojo/topic",
         "dojox/mobile/TransitionEvent",
         "dijit/registry",
         "dojo/_base/window",
         "mui/util",
         "mui/dialog/Dialog",
         "mui/dialog/Tip",
         "mui/device/adapter",
         "mui/view/NativeDocView",
         "mui/tabbar/TabBarButton",
         "sys/lbpmservice/mobile/operation/ExtOperationButton",
         "mui/i18n/i18n!sys-lbpmservice"],
         function(declare, array, lang, domClass, ViewController, viewRegistry, query, 
        		 request, topic, TransitionEvent, registry, win, util, Dialog, Tip, adapter, NativeDocView, TabBarButton, ExtOperationButton, msg){
	var button = declare("sys.lbpmservice.mobile.freeflow.freeflowChartView", [NativeDocView], {
		isInit:false,
		
		key:"",
		
		processId:"",
		
		showType:"",
		
		buildRendering: function() {
			this.inherited(arguments);
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},
		
		startup: function() {
			this.inherited(arguments);
			if(query(".freeflowChartBackButton", this.domNode)[0]){
				registry.byNode(query(".freeflowChartBackButton", this.domNode)[0]).on("click", lang.hitch(this, this.hidefreeFlowChartView));
			}
		},
		
		onAfterTransitionIn : function(){
			topic.publish("mui/view/currentView",this);
		},
		
		onAfterTransitionOut : function(){
			topic.publish("/sys/lbpmservice/freeflow/viewTransitionOut",this);
		},
		
		hidefreeFlowChartView:function(){
			var _self = this;
			 new TransitionEvent(win.body(), {
		        moveTo: _self.backTo,
		        transition: "slide",
		        transitionDir: -1
		      }).dispatch();
			 if(this.showType == 'dialog'){
				 topic.publish("/lbpm/operation/showDialog",this);
			 }
		}
	});
	return button;
});
