define([ "dojo/_base/declare",
         "dojo/_base/lang",
         "dojo/topic",
         "dojo/dom-class",
         "dojo/dom-construct",
         "dojo/query",
         "dojox/mobile/ViewController",
         "dojox/mobile/viewRegistry",
         "dijit/registry",
         "mui/util",
         "mui/dialog/Dialog",
         "mui/dialog/Tip",
         "mui/tabbar/TabBarButton",
         "dojo/Deferred",
         "mui/device/adapter",
         "mui/history/listener",
         "mui/i18n/i18n!sys-lbpmservice",
         "dojo/NodeList-traverse"
         ],
         function(declare, lang,topic, domClass, domConstruct, query, ViewController, viewRegistry, registry, util, Dialog, Tip, TabBarButton,Deferred,adapter,listener ,msg){
	var freeflowChartButton = declare("sys.lbpmservice.mobile.freeflow.freeflowChartButton", [TabBarButton], {
		
		icon1:"",
		
		isHandler:false,
		
		handleView:"freeflowChartView",
		
		viewerUrl:"/sys/lbpmservice/mobile/freeflow/freeflowChartViewer.jsp",
		
		processId:"",
		
		modelName:"",
		
		modelId:"",
		
		key: "freeflowChart",
		
		showType:"",
		
		buildRendering: function() {
			this.inherited(arguments);
		},
		
		_onClick: function(){
			if(!lbpm.globals.isCanEdit()){
				return;
			}
			lbpm.globals.saveOrUpdateFreeflowVersion();
			var vc = ViewController.getInstance();
			var view = null;
			if(this.showType == 'dialog'){//流程时弹窗，弹窗时需要返回到前一页
				if(!this.backTo){
					var view = viewRegistry.getEnclosingView(this.domNode);
					view = viewRegistry.getParentView(view);
					this.backTo = view ? view.id : "scrollView";
				}
				view = viewRegistry.hash[this.backTo];
			}else{
				if(this.backTo!=null){
					view = viewRegistry.hash[this.backTo];
				}else{
					view = viewRegistry.getEnclosingView(this.domNode);
					view = viewRegistry.getParentView(view) || view;
					this.backTo = view.id;
				}
			}
			var _extView = viewRegistry.hash[this.key];
			if(_extView==null){
				_extView = viewRegistry.hash[this.key + "View"];
			}
			//若是弹窗模式，隐藏弹窗
			if(this.showType == 'dialog'){
				topic.publish("/lbpm/operation/hideDialog",this);
			}
			if(_extView == null){
				var url = util.formatUrl(this.viewerUrl);
				url = util.setUrlParameter(url, "backTo", view.id);
				url = util.setUrlParameter(url, "processId", this.processId);
				url = util.setUrlParameter(url, "modelName", this.modelName);
				url = util.setUrlParameter(url, "modelId", this.modelId);
				url = util.setUrlParameter(url, "key", this.key);
				url = util.setUrlParameter(url, "showType", this.showType);
				var _selfObj = this;
				vc.openExternalView({
					url:url,
					transition: "slide"
				}, view.domNode.parentNode).then(function(){
					var extView = viewRegistry.hash[_selfObj.key + "View"];
				});
			}else{
				view.performTransition(_extView.id, 1, "slide");
			}
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},
		
		startup: function() {
			this.inherited(arguments);
		}
	});
	return freeflowChartButton;
});
