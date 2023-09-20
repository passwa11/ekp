define(
		["dojo/dom-construct",'dojo/_base/declare',"dojo/dom-style","mui/form/Address","dojo/topic","dojo/query",
			'dojo/dom-attr',"dojo/parser","mui/dialog/Confirm","mui/i18n/i18n!sys-lbpmservice","mui/form/validate/Validation","dojo/_base/window","dojox/mobile/sniff"],
		function(domConstruct,declare,domStyle,address,topic,query,domAttr,parser,Confirm,Msg,Validation,win,has) {
			var freeflowNodeAttr = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeAttr",
					[address], {
				
						type : ORG_TYPE_POSTORPERSON,
				
						attType : "",
						
						title : "",
						
						isMul : true,
						
						showStatus : "edit",
						
						_cateDialogPrefix: "__freeflowNodeAttr__",
						
						idField : "__freeflowNodeAttribute__",
						
						selectTemp : "<div data-dojo-type='mui/form/Select' data-dojo-props='name:\"!{name}\",value:\"!{value}\",mul:!{mul},store:!{store},state:\"!{state}\",nodeId:\"!{nodeId}\"'></div>",
						
						switchTemp : "<div data-dojo-type='sys/mobile/js/mui/form/switch/NewSwitch' data-dojo-props='name:\"!{name}\",leftLabel:\"!{leftLabel}\",rightLabel:\"!{rightLabel}\",showStatus:\"!{showStatus}\",mystate:\"!{mystate}\",nodeId:\"!{nodeId}\"' value='!{value}'></div>",
						
						buildRendering: function() {
							if(this.attType!="delBtn"){
								this.idField+=this.attType;
								this.idField+=this.nodeId;
							}
					        this.inherited(arguments);
					        this._cateDialogPrefix = this._cateDialogPrefix + this.attType;
					        domConstruct.empty(this.domNode);
					        var title = this.getTitle();
					        if(this.attType=="handler"||this.attType=="delBtn"){
					        	this.titleNode = domConstruct.create("span", {
						        	innerHTML : title
								}, this.domNode);
						        if(this.attType == "handler"){
						        	domConstruct.create("span", {
										className : 'muiFormRequired muiFormRequiredShow muiFormHandlerRequired',
										innerHTML : "*"
									}, this.domNode);
						        	domConstruct.create("i", {
										className : 'fontmuis muis-to-right free_flow_handler_to_right'	
									}, this.domNode);
						        }
					        }else{
					        	if(this.isNotExitAttr()){
									return;
								}
					        	if(this.attType=="isFixedNode" || this.attType=="canJump"){
					        		this.buildSwitchDom();
					        	}else{
					        		this.buildSelectDom();
					        	}
					        }
						},
						
						buildSwitchDom : function() {
							var tmpl = this.switchTemp.replace("!{name}", this.idField)
											.replace("!{value}", this.value)
											.replace("!{showStatus}", this.showStatus)
											.replace("!{mystate}", this.state)
											.replace("!{nodeId}", this.nodeId)
											.replace("!{leftLabel}", this.leftLabel)
											.replace("!{rightLabel}", this.rightLabel);
							var dom = domConstruct.toDom(tmpl);
					        domConstruct.place(dom, this.domNode, "last");
					        parser.parse(this.domNode).then(function () {
			                    win.doc.dojoClick = !has("ios") || has("ios") > 13;
			                });;
						},
						
						buildSelectDom : function() {
							var tmpl = this.selectTemp.replace("!{name}", this.idField)
										.replace("!{value}", this.value)
										.replace("!{mul}", this.mul)
										.replace("!{store}", this.store)
										.replace("!{showStatus}", this.showStatus)
										.replace("!{state}", this.state)
										.replace("!{nodeId}", this.nodeId);
							var dom = domConstruct.toDom(tmpl);
					        domConstruct.place(dom, this.domNode, "last");
					        parser.parse(this.domNode).then(function () {
			                    win.doc.dojoClick = !has("ios") || has("ios") > 13;
			                });;
						},
						
						startup : function() {
							if(this.isNotExitAttr()){
								var itemNode = query(this.domNode).closest(".free_flow_nodeAttribute_item");
							    this.destroy();
							    itemNode.remove();
								return;
							}
							this.inherited(arguments);
							if(this.attType == "handler"){
								new Validation().hideWarnHint(this);
							}
						},
						
						postCreate : function() {
							if(this.isNotExitAttr()){
								return;
							}
							this.inherited(arguments);
							if(this.attType=="handler"||this.attType=="delBtn"){
								this.connect(this.domNode, 'click', this.domNodeClick);
					        }else{
					        	if(this.isNotExitAttr()){
									return;
								}
					        	if(this.attType=="isFixedNode" || this.attType=="canJump"){
					        		this.subscribe("mui/switch/statChanged","_statChanged");
					        	}else{
					        		this.subscribe("mui/form/select/callback","_selectChange");
					        	}
					        }
						},
						
						domNodeClick : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
						        return false;
						     }
						    this.ctime = nowTime;
							if(this.attType=="handler"){
								this._selectCate();
							} else if(this.attType=="delBtn"){
								var _self = this;
								var node = this.getNode();
								if(node && node.Data["handlerIds"]){
							    	var _self = this;
							    	Confirm(Msg['mui.freeFlow.node.deleteMsg'],null,function(data){
										if(data){
											topic.publish("/sys/lbpmservice/freeflow/_delteNode",_self);
										}
									},false);
							    }else{
							    	topic.publish("/sys/lbpmservice/freeflow/_delteNode",_self);
							    }
								
							} 
						},
						
						buildValue : function(domContainer){
							domConstruct.empty(domContainer);
							if(this.titleNode && this.attType=="handler"){
								domAttr.set(this.titleNode,"innerHTML",this.formatHandlerNames(this.curNames));
								var nodeObj = this.getNode();
								if(this.curIds!=nodeObj.Data["flowPopedom"]){
									topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,{handlerIds:this.curIds,handlerNames:this.curNames});
								}
							}
						},
						
						getTitle : function(){
							var nodeObj = this.getNode();
							var title = "";
							if(nodeObj){
								if(this.attType=="isFixedNode"){
									this.leftLabel = "";
									this.rightLabel = "";
									var isFixedNode = nodeObj.Data["isFixedNode"]||"false";
									if(isFixedNode=="true"){
										this.value = "on";
									}else{
										this.value = "off";
									}
									if(!this.template){
										this.showStatus = "view";
									}
								} else if(this.attType=="handler"){
									var handlerNames = nodeObj.Data["handlerNames"];
									if(handlerNames){
										this.curNames = handlerNames;
									}
									if(nodeObj.Data["handlerIds"]){
										this.curIds = nodeObj.Data["handlerIds"];
									}
									title = this.formatHandlerNames(handlerNames);
									if(this.isSendNode()){
										this.type = ORG_TYPE_ALL;
									}
								} else if(this.attType=="processType"){
									var processType = nodeObj.Data["processType"];
									var store = [ {
										text : Msg['mui.freeFlow.lbpm.node.processType_0'],
										value : "0"
									}, {
										text : Msg['mui.freeFlow.lbpm.node.processType_1'],
										value : "1"
									}, {
										text : Msg['mui.freeFlow.lbpm.node.processType_2.' + nodeObj.Data.XMLNODENAME],
										value : "2"
									}];
									this.store = JSON.stringify(store);
									this.value = processType;
									this.mul = false;
								} else if(this.attType=="handlerSameSelect"){
									var ignoreHandlerSame = nodeObj.Data["ignoreOnHandlerSame"];
									var adjoinHandlerSame = nodeObj.Data["onAdjoinHandlerSame"];
									var ignoreOnFutureHandlerSame = nodeObj.Data["ignoreOnFutureHandlerSame"];
									if(ignoreHandlerSame==null){
										ignoreHandlerSame = "true";
									}
									if(adjoinHandlerSame==null){
										adjoinHandlerSame = "true";
									}
									if(ignoreOnFutureHandlerSame==null){
										ignoreOnFutureHandlerSame = "false";
									}
									var selected = "1";
									if(ignoreHandlerSame == "true"){//兼容老数据配置
										if(adjoinHandlerSame=="true"){
											selected = "1";//相邻跳过
										}else{
											selected = "2";//跨节点跳过
										}
									}else if(ignoreOnFutureHandlerSame=="true"){
										selected = "3";//后续处理人身份重复跳过当前
									}else{
										selected = "0";//不跳过
									}
									var store = [ {
										text : Msg['mui.freeFlow.node.onAdjoinHandlerSame'],
										value : "1"
									}, {
										text : Msg['mui.freeFlow.node.onSkipHandlerSame'],
										value : "2"
									}, {
										text : Msg['mui.freeFlow.node.ignoreOnHandlerSame'],
										value : "0"
									}, {
										text : Msg['mui.freeFlow.node.ignoreOnFutureHandlerSame'],
										value : "3"
									}];
									this.store = JSON.stringify(store);
									this.value = selected;
									this.mul = false;
								} else if(this.attType=="canJump"){
									this.leftLabel = "";
									this.rightLabel = "";
									var canJump = nodeObj.Data["canJump"]||"false";
									if(canJump=="true"){
										this.value = "on";
									}else{
										this.value = "off";
									}
								} else if(this.attType=="notifyType"){
									var data = new KMSSData();
									data.AddBeanData("getNotifyTypeService");
									data = data.GetHashMapArray();
									this.store = data[0]["store"];
									if(nodeObj.Data["notifyType"]){
										this.value = nodeObj.Data["notifyType"];
									}else{
										this.value = "";
									}
									this.mul = true;
								} else if(this.attType=="popedom"){
									var store = [ {
										text : Msg["mui.freeFlow.node.canModifyMainDoc"],
										value : "0"
									}, {
										text : Msg["mui.freeFlow.node.canAddAuditNoteAtt"],
										value : "1"
									}];
									this.store = JSON.stringify(store);
									var value = "";
									if(nodeObj.Data["canModifyMainDoc"]=="true"){
										value +="0;"
									}
									if(nodeObj.Data["canAddAuditNoteAtt"]=="true"){
										value +="1;"
									}
									if(value){
										this.value = value.substring(0,value.length-1);
									}
									this.mul = true;
								} else if(this.attType=="flowPopedom"){
									var store = [ {
										text : Msg["mui.freeFlow.node.flowPopedom_0"],
										value : "0"
									}, {
										text : Msg["mui.freeFlow.node.flowPopedom_1"],
										value : "1"
									}];
									if(this.template || (!this.template&&lbpm.nowNodeFlowPopedom == "2")){
										store.push({
											text : Msg["mui.freeFlow.node.flowPopedom_2"],
											value : "2"
										})
									}
									this.store = JSON.stringify(store);
									this.value = nodeObj.Data["flowPopedom"];
									this.mul = false;
								}else if(this.attType=="opinionType"){
									var store = '';
									var lbpmUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=queryAllAuditNoteTypeByName";
									$.ajax({
										url: lbpmUrl,
										data:{'keyName':"text","valueName":"value"},
										async: false,
										type: "POST",
										dataType: 'json',
										success: function (data) {
											if(data){
												store = data;
											}
										},
										error: function (er) {
								
										}
									});
									this.store = JSON.stringify(store);
									this.mul = false;
									this.value = nodeObj.Data["opinionType"];
								} else if(this.attType=="delBtn"){
									title = Msg["mui.freeFlow.node.delete"];
								}
							}
							return title;
						},
						
						getNode : function(){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(this.nodeId);
						},
						
						isSendNode : function(){
							var nodeObj = this.getNode();
							if(nodeObj){
								return nodeObj.Type=="sendNode";
							}
							return false;
						},
						
						isSignNode : function(){
							var nodeObj = this.getNode();
							if(nodeObj){
								return nodeObj.Type=="signNode";
							}
							return false;
						},
						
						isImissiveModel : function(){
							return lbpm.modelName == "com.landray.kmss.km.imissive.model.KmImissiveSendMain"
								||lbpm.modelName == "com.landray.kmss.km.imissive.model.KmImissiveReceiveMain"
								||lbpm.modelName == "com.landray.kmss.km.imissive.model.KmImissiveSignMain";
						},
						
						isNotExitAttr : function(){
							if(this.isImissiveModel()){
								return this.isSendNodeNoHasAttr() || this.isSignNodeNoHasAttr();
							}else{
								return this.isImissiveModelAttr() || this.isSendNodeNoHasAttr() || this.isSignNodeNoHasAttr();
							}
						},
						
						isImissiveModelAttr : function(){
							return this.attType=="opinionType";
						},
						
						isSignNodeNoHasAttr : function(){
							return this.isSignNode() && (this.attType=='canJump' || this.attType=="opinionType");
						},
						
						isSendNodeNoHasAttr : function(){
							return this.isSendNode() && (this.attType=="processType"||this.attType=="canJump"||this.attType=="popedom"||this.attType=="flowPopedom"||this.attType=="handlerSameSelect"||this.attType=="opinionType");
						},
						
						formatHandlerNames : function(handlerNames){
							var name = Msg["mui.freeFlow.node.select"];
							if(handlerNames){
								var names = handlerNames.split(";");
								if(names.length>2){
									var length = names.length;
									name = names[0]+";"+names[1];
									if(name.length>10){
										name = name.substring(0,10)+"...";
									}
									name += Msg["mui.freeFlow.node.more"]+length+Msg["mui.freeFlow.node.person"];
								}else{
									name = names.join(";");
									if(name.length>10){
										name = name.substring(0,10)+"...";
									}
								}
							}
							return name;
						},
						
						_statChanged : function(srcObj,val){
							if(srcObj.name==this.idField && srcObj.mystate==this.state){
								var nodeObj = this.getNode();
								if(!nodeObj){
									return;
								}
								if(this.attType=="isFixedNode"){
									var value = val?"true":"false";
									if(value!=nodeObj.Data["isFixedNode"]){
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,{isFixedNode:value});
									}
								} else if(this.attType=="canJump"){
									var value = val?"true":"false";
									if(value!=nodeObj.Data["canJump"]){
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,{canJump:value});
									}
								}
							}
						},
						
						_selectChange : function(srcObj){
							if(srcObj.name==this.idField && srcObj.state==this.state){
								var nodeObj = this.getNode();
								if(!nodeObj){
									return;
								}
								if(this.attType=="processType"){
									if(srcObj.value!=nodeObj.Data["processType"]){
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,{processType:srcObj.value});
									}
								} else if(this.attType=="notifyType"){
									if(srcObj.value!=nodeObj.Data["notifyType"]){
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,{notifyType:srcObj.value});
									}
								} else if(this.attType=="popedom"){
									var canModifyMainDoc = srcObj.value.indexOf("0")>-1?"true":"false";
									var canAddAuditNoteAtt = srcObj.value.indexOf("1")>-1?"true":"false";
									if(canModifyMainDoc!=nodeObj.Data["canModifyMainDoc"] || canAddAuditNoteAtt!=nodeObj.Data["canAddAuditNoteAtt"]){
										var data = {canModifyMainDoc:canModifyMainDoc, canAddAuditNoteAtt:canAddAuditNoteAtt}
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,data);
									}
								} else if(this.attType=="flowPopedom"){
									if(srcObj.value!=nodeObj.Data["flowPopedom"]){
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,{flowPopedom:srcObj.value});
									}
								}else if(this.attType=="opinionType"){
									if(srcObj.value!=nodeObj.Data["opinionType"]){
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,{opinionType:srcObj.value});
									}
								} else if(this.attType=="handlerSameSelect"){
									var ignoreHandlerSame = "true";
									var adjoinHandlerSame = "true";
									var ignoreOnFutureHandlerSame = "false";
									if(srcObj.value=="1"){//相邻处理人重复跳过
										ignoreHandlerSame = "true";
										adjoinHandlerSame = "true";
										ignoreOnFutureHandlerSame = "false";
									}else if(srcObj.value=="2"){//跨节点处理人重复跳过
										ignoreHandlerSame = "true";
										adjoinHandlerSame = "false";
										ignoreOnFutureHandlerSame = "false";
									}else if(srcObj.value=="3"){//后续处理人身份重复跳过当前
										ignoreHandlerSame = "false";
										adjoinHandlerSame = "false";
										ignoreOnFutureHandlerSame = "true";
									}else{//不跳过
										ignoreHandlerSame = "false";
										adjoinHandlerSame = "false";
										ignoreOnFutureHandlerSame = "false";
									}
									if(ignoreHandlerSame!=nodeObj.Data["ignoreOnHandlerSame"]
										|| adjoinHandlerSame!=nodeObj.Data["onAdjoinHandlerSame"]
										|| ignoreOnFutureHandlerSame!=nodeObj.Data["ignoreOnFutureHandlerSame"]){
										var data = {ignoreOnHandlerSame:ignoreHandlerSame, onAdjoinHandlerSame:adjoinHandlerSame, ignoreOnFutureHandlerSame:ignoreOnFutureHandlerSame}
										topic.publish("/sys/lbpmservice/freeflow/attrValueChange",this,data);
									}
								}
							}
						}
					});
			return freeflowNodeAttr;
		});