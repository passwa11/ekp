define([ "dojo/_base/declare",
         "dojo/_base/lang",
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
         "dojo/dom-style",
         'dojo/topic',
         "dojo/NodeList-traverse",
         ],
         function(declare, lang, domClass, domConstruct, query, ViewController, viewRegistry, registry, util, Dialog, Tip, TabBarButton, Deferred, adapter, domStyle, topic){
	var button = declare("sys.lbpmperson.mobile.operation.FastReviewTodoButton", [TabBarButton], {
		
		icon1:"",
		
		scrollView:"",
		
		processId:"",
		
		modelName:"",
		
		modelId:"",
		
		key: null,
		
		tmp:'<div id="scroll_FastReview" data-dojo-type="mui/list/StoreElementScrollableView">'
			+'<div id="fastReviewTodoHead" data-dojo-type="sys/lbpmperson/mobile/resource/js/FastReviewTodoHead" class="muiFastReviewTodoHead"></div>'
			+'<ul data-dojo-type="mui/list/JsonStoreList" class="muiList fastReviewTodoList"' 
			+'	data-dojo-mixins="sys/lbpmperson/mobile/resource/js/FastReviewTodoList"'
			+'	data-dojo-props="url:\'/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=listNotifyMobileApproval\', lazy:false,rowsize:5, nodataImg:\''+dojoConfig.baseUrl+'sys/mportal/mobile/css/imgs/nodata.png\'">'
			+'</ul>'
		+'</div>',
		
		buildRendering: function() {
			
			if(!this.domNode){
				this.domNode = this.srcNodeRef || this.ownerDocument.createElement("div");
			}
			this.inherited(arguments);
		},
		
		_onClick: function(){
			this.dialogDom=domConstruct.toDom(this.tmp);
			var self = this;
			this.dialog = Dialog.element({
				destroyAfterClose:true,
				canClose : false,
				showClass : 'muiDialogElementShow fastReviewListDialog',
				element:this.dialogDom,
				position:'bottom',
				'scrollable' : false,
				'parseable' : true,
				onDrawed:function(){
					// 弹出框由于有渐入的动画样式，会偶发导致列表滚动高度计算不正确，因此需要在弹出框键入动画效果执行完毕后重置列表滚动高度
					setTimeout(function(){
						registry.byId("scroll_FastReview").resize();
					},500);
				},
				'callback':function(win,obj){
					var sv = registry.byId(self.scrollView);
					sv.currView.getChildren()[0].reload();
				}
			});
		},
		postCreate: function() {
			this.inherited(arguments);
		},
		startup: function() {
			this.inherited(arguments);
		}
	});
	return button;
});
