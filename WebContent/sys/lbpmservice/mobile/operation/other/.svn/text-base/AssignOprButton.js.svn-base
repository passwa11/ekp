define([
        "dojo/_base/declare",
        "dojo/_base/window",
        "dojox/mobile/TransitionEvent", 
        "dojox/mobile/ViewController",
        "dojox/mobile/viewRegistry",
        "mui/util",
        "mui/tabbar/TabBarButton",
        "dojo/topic",
        "dojo/dom-class",
        "dojo/dom-style",
        "dojo/Deferred",
        "mui/history/listener",
        "mui/i18n/i18n!sys-lbpmservice-support"
	], function(declare, win, TransitionEvent, ViewController, viewRegistry, util, TabBarButton, topic, domClass, domStyle, Deferred, listener, Msg) {
	
	return declare("sys.lbpmservice.mobile.operation.other.AssignOprButton", [TabBarButton], {
		
		operationType:0,			//0:回复分发，1:分发
		
		extOptUrl:"/sys/lbpmservice/mobile/operation/other/assignOprViewer.jsp",
		
		processId:"",
		
		modelName:"",
		
		modelId:"",
		
		buildRendering : function() {
			this.inherited(arguments);
			if (window.LBPM_CanAssign) {
				if (this.operationType == 0) {
					domClass.add(this.domNode,"selected");
				}
			} else {
				domClass.add(this.domNode,"selected");
			}
			if (this.operationType == 0) {
				this.label = Msg["mui.lbpmAssign.operation.replyAssign"];
			} else {
				this.label = Msg["mui.lbpmAssign.operation.assign"];
			}
		},
		
		startup : function() {
			if (this._started){
				return;
			}
			this.inherited(arguments);
		},
		
		_setSelectedAttr : function(selected){
			this.inherited(arguments);
			this.defer(function(){
				this.set("selected", false);
			},380);
		},
		
		onClick : function() {
			var vc = ViewController.getInstance();
			var view = null;
			if(this.backTo!=null){
				view = viewRegistry.hash[this.backTo];
			}else{
				view = viewRegistry.getEnclosingView(this.domNode);
				view = viewRegistry.getParentView(view) || view;
				this.backTo = view.id;
			}
			var _extView = viewRegistry.hash['AssignOprView'];
			if(_extView == null){
				var url = util.formatUrl(this.extOptUrl);
				url = util.setUrlParameter(url, "backTo", view.id);
				url = util.setUrlParameter(url, "processId", this.processId);
				url = util.setUrlParameter(url, "modelName", this.modelName);
				url = util.setUrlParameter(url, "modelId", this.modelId);
				var _selfObj = this;
				vc.openExternalView({
					url:url,
					transition: "slide"
				}, view.domNode.parentNode).then(function(){
					var extView = viewRegistry.hash["AssignOprView"];
					extView.set("operationType",_selfObj.operationType);
					if(extView && extView.initExtOpertion){
						extView.initExtOpertion(_selfObj);
					}
				});
			}else{
				_extView.set("operationType",this.operationType);
				view.performTransition(_extView.id, 1, "slide");
				if(_extView.initExtOpertion){
					_extView.initExtOpertion(this);
				}
			}
			var _self=this;
			window.Com_BeforeSubmitDeferred = this.defaultDeferred;
			listener.add({callback:function(){
				new TransitionEvent(win.body(), {moveTo: _self.backTo, transition: "slide", transitionDir: -1}).dispatch();
				window.Com_BeforeSubmitDeferred = null;
			}});
		},
		
		defaultDeferred: function(){
			var defer = new Deferred();
			defer.resolve();
			history.back();
			return defer;
		}
	});
});