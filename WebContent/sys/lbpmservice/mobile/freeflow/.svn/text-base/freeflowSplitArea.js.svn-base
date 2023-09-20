define(
		["dojo/dom-construct",'dojo/_base/declare',"dojo/dom-style","dijit/_WidgetBase","dojo/topic","dojo/query","mui/dialog/Confirm","mui/i18n/i18n!sys-lbpmservice"],
		function(domConstruct,declare,domStyle,WidgetBase,topic,query,Confirm,Msg) {
			var freeflowSplitArea = declare("sys.lbpmservice.mobile.freeflow.freeflowSplitArea",
					[WidgetBase], {
				
						buildRendering: function() {
					        this.inherited(arguments);
							domConstruct.create("div", {
								className : 'free_flow_split'
							}, this.domNode);
							this.canAdd = false;
							if(!this.template){
								var node = lbpm.nodes[this.nodeId];
								if (node.Status != lbpm.constant.STATUS_PASSED) {
									if(lbpm.nowNodeFlowPopedom == "1" || lbpm.nowNodeFlowPopedom == "2"){
										this.canAdd = true;
									}
								}
								if(!lbpm.globals.canAddOtherNode(this.nodeId)){
									this.canAdd = false;
								}
							}else{
								this.canAdd = true;
							}
							if(this.canAdd){
								var className = 'muiCategoryAdd mui mui-plus free_flow_add';
								this.addNode = domConstruct.create("div",{className: className},this.domNode);
							}
							this.canUpdate = false;
							if(!this.template){
								var node = lbpm.nodes[this.nodeId];
								if (node.Status == "1" && node.isFixedNode != 'true') {
									if (lbpm.nowNodeFlowPopedom=="2") {
										this.canUpdate = true;
									} else if (lbpm.nowNodeFlowPopedom=="1") {
										if (lbpm.myAddedNodes.contains(node.id)) {
											this.canUpdate = true;
										}
									}
								}
								if(!lbpm.globals.canAddOtherNode(this.nodeId)){
									this.canUpdate = false;
								}
							}else{
								this.canUpdate = true;
							}
							var nodeObj = this.getNode();
							if(nodeObj.Type=="joinNode"){
								this.canUpdate = false;
							}
							if(this.canUpdate){
								this.delNode = domConstruct.create("div",{className: 'free_flow_delete'},this.domNode);
								domConstruct.create("div",{className: 'fontmuis muis-org-delete'},this.delNode);
							}
						},
						
						startup : function() {
							this.inherited(arguments);
						},
						
						postCreate : function() {
							this.inherited(arguments);
							if(this.canAdd){
								this.connect(this.addNode, 'click', this.addNodeClick);
							}
							if(this.canUpdate){
								this.connect(this.delNode, 'click', this.delNodeClick);
								this.subscribe("/sys/lbpmservice/freeflow/leftSlide","_leftSlide");
								this.subscribe("/sys/lbpmservice/freeflow/recover","_recover");
								this.subscribe("/sys/lbpmservice/freeflow/__deleteNode","__deleteNode");
							}
						},
						
						addNodeClick : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
						        return false;
						    }
							if(!lbpm.globals.isCanEdit()){
								return;
							}
						    this.ctime = nowTime;
						    topic.publish("/sys/lbpmservice/freeflow/addNode",this);
						},
						
						__deleteNode : function(srcObj){
							if(srcObj.nodeId==this.nodeId){
								if(!lbpm.globals.isCanEdit()){
									return;
								}
								topic.publish("/sys/lbpmservice/freeflow/deleteNode",this);
							}
						},
						
						delNodeClick : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
						        return false;
						    }
							this.ctime = nowTime;
							if(!lbpm.globals.isCanEdit()){
								return;
							}
							var node = this.getNode();
						    if(node && (node.Data["handlerIds"] || node.Type=="splitNode")){
						    	var _self = this;
						    	Confirm(Msg['mui.freeFlow.node.deleteMsg'],null,function(data){
									if(data){
										topic.publish("/sys/lbpmservice/freeflow/deleteNode",_self);
									}
								},false);
						    }else{
						    	topic.publish("/sys/lbpmservice/freeflow/deleteNode",this);
						    }
						},
						
						_leftSlide : function(srcObj){
							if(srcObj.nodeId==this.nodeId){
								domStyle.set(this.delNode,{display:'block'});
							}
						},
						
						_recover : function(srcObj){
							if(srcObj.nodeId==this.nodeId){
								domStyle.set(this.delNode,{display:'none'});
							}
						},
						
						getNode : function(){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(this.nodeId);
						}
					});
			return freeflowSplitArea;
		});