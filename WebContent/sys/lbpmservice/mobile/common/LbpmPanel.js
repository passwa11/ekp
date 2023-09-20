define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/_base/array", "dojo/topic", "dojo/ready", "dijit/registry",
        "dojo/query", "dojo/dom-class", "dojo/dom-style", "dojo/parser", "./syslbpmprocess_event" ],
		function(declare, WidgetBase, array, topic, ready, registry, query, domClass, domStyle, parser, lbpmProcessEvent){
	
	return declare("sys/lbpmservice/mobile/common/LbpmPanel",[WidgetBase],{
		stopParser:true,
		
		LBPM_EVENT_INITCOMPLETE : "initComplete",
		
		maxHeight: 0,
		
		startup:function(){
			this.inherited(arguments);
			lbpm.globals.destroyOperations = this.destroyOperations;
			var self = this;
			ready(9999,function(){
				//异步请求资源，进行界面初始化
				setTimeout(function(){
					parser.parse(self.domNode).then(function(){
						var files = [];
						files = files.concat(["sys/lbpmservice/mobile/common/syslbpmprocess"]);
						files = files.concat(lbpm.jsfilelists);
						files = array.map(files,function(fileStr){
							if(fileStr.substr(0, 1) == '/') {
								fileStr = fileStr.substr(1);
							}
							if(fileStr.substr(fileStr.length-3,fileStr.lenghth) == '.js') {
								fileStr = fileStr.substr(0,fileStr.length-3);
							}
							return fileStr;
						});
						require(files,function(){
							array.forEach(arguments,function(mod,idx){
								try{
									if(mod) {
										if(mod.init){
											mod.init();
										}
									}
								}catch(e){
									console.error("流程初始化出错：" + e);
								}
							});
							topic.publish("initComplete", this);
						});
					});
					self.drawReady();
				},10);
			});
		},
		
		
		postCreate: function() {
			this.inherited(arguments);
			this.subscribe("/lbpm/operation/switch","doBackToTop");
		},
		
		destroyOperations:function(){
			query("#operationsTDContent, #nextNodeTD").forEach(function(node) {
				array.forEach(registry.findWidgets(node), function(widget) {
					widget.destroy && !widget._destroyed && widget.destroy();
				});
			});
		},

		// 回到页面顶部
		doBackToTop:function(wgt,ctx){
			if (ctx && ctx.methodSwitch == false) {
				//切换事务时，让lbpmView重新回到顶部，避免因为每个事务对应的内容长度不同，从较长内容时的lbpmView底部离开，
				//再进入较短内容时的lbpmView的时候看到的是一片空白且不可操作的区域的问题
				if (this.touchNode) {
					domStyle.set(this.touchNode, 'transform', 'translate3d(0px, 0px, 0px)');
				}
			}
		},
		
		
		drawReady:function(){
			query('.handingWay').on('click', function(e) {
				var self = this;
				//domClass.add(self, 'selected');
				//setTimeout(function() {domClass.remove(self, 'selected');}, 1000);
			});
			this.autoSaveDraftAction();
			// 发送完成事件
			topic.publish('mui/lbpmservice/drawReady');
		},
		
		autoSaveDraftAction:function() {
			var oldValue = "", timer = null;
			var doSave = function() {
				query("[name='fdUsageContent']").forEach(function(fdUsageContent) {
					if (oldValue != fdUsageContent.value) {
						var defalutUsage = "";
						defalutUsage = lbpm.globals.getOperationDefaultUsage(lbpm.currentOperationType);
						if(defalutUsage != fdUsageContent.value){
							oldValue = fdUsageContent.value;
							lbpm.globals.saveDraftAction();
						}
					}
				});
				timer = null;
			};
			var timeToSave = function() {
				if (timer) {
					clearTimeout(timer);
					timer = null;
				}
				timer = setTimeout(doSave, 10000);
			};
			query("[name='fdUsageContent']").on("input", timeToSave);
			Com_Parameter.event["submit"].push(function() {
				if (timer) {
					clearTimeout(timer);
					timer = null;
				}
				return true;
			});
		}
	});
});