define(
		[ "dojo/_base/declare","mui/form/Category","dojo/topic","dojo/_base/window","dojox/mobile/sniff"],
		function(declare,Category,topic,win,has) {
			var freeflowNodeAttributeGw = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeAttributeGw",
					[Category], {
						
						templURL : "sys/lbpmservice/mobile/freeflow/freeflowNodeAttributeGw.jsp",
						
						_cateDialogPrefix: "__freeflowNodeImissiveAttr__",
						
						idField : "__freeflowNodeImissiveAttr__",
						
						key : "__freeflowNodeImissiveAttr__",
						
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe("/sys/lbpmservice/freeflow/imissiveok","_closeAttrIm");
						},
						
						_closeAttrIm : function(srcObj) {
							this.closeDialog(srcObj);
						},
						
						_selectCate : function(config) {
							this._init(config);
							this.inherited(arguments);
							win.doc.dojoClick = !has("ios") || has("ios") > 13;
						},
						
						_init: function(config) {
							this.nodeId = config.nodeId;
							this.handlerType = config.handlerType;
						}
						
					});
			return freeflowNodeAttributeGw;
		});