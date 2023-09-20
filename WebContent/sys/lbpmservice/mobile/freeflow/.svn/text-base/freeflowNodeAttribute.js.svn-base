define(
		[ "dojo/_base/declare","mui/form/Category","dojo/topic","mui/i18n/i18n!sys-lbpmservice","dijit/registry","dojo/_base/window","dojox/mobile/sniff"],
		function(declare,Category,topic,Msg,registry,win,has) {
			var freeflowNodeAttribute = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeAttribute",
					[Category], {
						templURL : "sys/lbpmservice/mobile/freeflow/freeflowNodeAttribute.jsp",
						
						_cateDialogPrefix: "__freeflowNodeAttribute__",
						
						subject : Msg['mui.freeFlow.lbpm.node.attrSet'],
						
						nodeName : '',
						
						idField : "__freeflowNodeAttribute__",
						
						key : "__freeflowNodeAttribute__",
						
						attrFileds : ["name","langs","handlerIds","handlerNames","processType","notifyType","canModifyMainDoc","canAddAuditNoteAtt","flowPopedom","opinionType","isFixedNode","canJump","ignoreOnHandlerSame","onAdjoinHandlerSame","ignoreOnFutureHandlerSame"],
						
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe("/sys/lbpmservice/freeflow/ok","_saveAttr");
							this.subscribe("/sys/lbpmservice/freeflow/_delteNode","_delteNode");
							this.subscribe("/sys/lbpmservice/freeflow/attrValueChange","_attrValueChange");
							this.subscribe("/mui/form/valueChanged","_nodeNameChange");
						},
						
						_delteNode  : function(srcObj) {
							if(srcObj.nodeId==this.nodeId && srcObj.state==this.state){
								topic.publish("/sys/lbpmservice/freeflow/__deleteNode",this);
								this.closeDialog(srcObj);
							}
						},
						
						_saveAttr : function(srcObj) {
							if(srcObj.nodeId==this.nodeId && srcObj.state==this.state){
								var widget = registry.byId("lbpm_free_validate");
								if(widget.validate()){
									var node = this.getNode();
									if(node){
										for(var i=0;i<this.attrFileds.length;i++){
											var filed = this.attrFileds[i];
											if(this[filed]){
												node.Data[filed] = this[filed];
											}
										}
										topic.publish("/sys/lbpmservice/freeflow/updateNode",this);
										lbpm.globals.saveOrUpdateFreeflowVersion();
									}
									this.closeDialog(srcObj);
								}
							}
						},
						
						_attrValueChange : function(srcObj,data) {
							if(srcObj.nodeId==this.nodeId && srcObj.state==this.state){
								for(var key in data){
									this[key] = data[key];
								}
							}
						},
						
						_nodeNameChange : function(srcObj,data) {
							if(srcObj.name=="nodeName_"+this.nodeId+"_"+this.state){
								var nodeObj = this.getNode();
								if(!nodeObj){
									return;
								}
								if(srcObj.value!=this.name){
									this.name = srcObj.value;
									if(this.langs){
										var langs = JSON.parse(this.langs);
										if(langs["nodeName"] && _userLang){
											for(var i=0;i<langs["nodeName"].length;i++){
												if(langs["nodeName"][i]["lang"]==_userLang){
													langs["nodeName"][i]["value"]=srcObj.value;
													break;
												}
											}
											this.langs = JSON.stringify(langs);
										}
									}
								}
							}
						},
						
						_selectCate : function(config) {
							this._init(config);
							this.inherited(arguments);
							win.doc.dojoClick = !has("ios") || has("ios") > 13;

						},
						
						_init: function(config) {
							this.nodeId = config.nodeId;
							this.state = config.state;
							this.template = config.template;
							var node = this.getNode();
							if(node){
								this.name = node.Data["name"];
								this.langs = node.Data["langs"];
							}
						},
						
						getNode : function(){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(this.nodeId);
						}
					});
			return freeflowNodeAttribute;
		});