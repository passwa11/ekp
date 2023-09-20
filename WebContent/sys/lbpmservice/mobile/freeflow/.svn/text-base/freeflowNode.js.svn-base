define(
		["dojo/dom-construct",'dojo/_base/declare',"dojo/dom-style","dojox/mobile/_ScrollableMixin","dijit/_WidgetBase","dojo/topic",
			"dojo/query","dojo/dom-class","sys/lbpmservice/mobile/freeflow/freeflowNodeAttribute","mui/i18n/i18n!sys-lbpmservice"],
		function(domConstruct,declare,domStyle,ScrollableMixin,WidgetBase,topic,query,domClass,freeflowNodeAttribute,Msg) {
			var freeflowNode = declare("sys.lbpmservice.mobile.freeflow.freeflowNode",
					[WidgetBase, ScrollableMixin], {
				
						scrollDir : "h",
						
						buildRendering: function() {
					        this.inherited(arguments);
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
					        this.containerNode = domConstruct.create("div", {
					        	className : 'free_flow_node'
					        }, this.domNode);
					        var node = this.getNode();
					        var sortName = WorkFlow_getLangLabel(node.Data["name"],node.Data["langs"],"nodeName");
					    	var leftDom = domConstruct.create("div", {
								className : 'free_flow_left'
							}, this.containerNode);
					    	if(this.canUpdate){
					    		this.upDom = domConstruct.create("div", {
									className : 'free_flow_move free_flow_move_up'
								}, leftDom);
								this.upNode = domConstruct.create("i", {
									className : 'fontmuis muis-org-moveup'
								}, this.upDom);
								this.downDom = domConstruct.create("div", {
									className : 'free_flow_move free_flow_move_down'
								}, leftDom);
								this.downNode = domConstruct.create("i", {
									className : 'fontmuis muis-org-movedown'
								}, this.downDom);
					    	}
							var centerDom = domConstruct.create("div", {
								className : 'free_flow_center'
							}, this.containerNode);
							var nodeClass = "fontmuis muis-org-approve";
							var spanClass = "free_flow_center_icon_review";
							if (node.Type=="signNode"){
								nodeClass = "fontmuis muis-org-signature"
								spanClass = "free_flow_center_icon_sign";
							} else if (node.Type=="sendNode"){
								nodeClass = "fontmuis muis-org-copy-to";
								spanClass = "free_flow_center_icon_send";
							}
							if(node.Data["isFixedNode"]=="true"){
								nodeClass = "fontmuis muis-org-locked";
							}
							if(node.Type!="splitNode" && node.Type!="joinNode"){
								var spanCenterDom = domConstruct.create("span", {
									className : 'free_flow_center_icon '+spanClass
								}, centerDom);
								this.stateDom = domConstruct.create("i", {
									className : nodeClass
								}, spanCenterDom);
							}
							this.nodeNameDom = domConstruct.create("span", {
								className : 'free_flow_sortName',
								innerHTML : sortName
							}, centerDom);
							var rightDom = domConstruct.create("div", {
								className : 'free_flow_right'
							}, this.containerNode);
							var title = this.formatHandlerNames(this._getHandlerName());
							if(node.Type=="robotNode" || node.Type=="splitNode" || node.Type=="joinNode"){
								title = "";
							}
							this.setDom = domConstruct.create("span", {
								className : 'free_flow_set_tip'
							}, rightDom);
							this.handlerNameDom = domConstruct.create("span", {
								innerHTML : title
							}, this.setDom);
							if(this.canUpdate && node.Data["handlerSelectType"]=="org"){
								domConstruct.create("i", {
									className : 'mui mui-forward'
								}, this.setDom);
							}
						},
						
						startup : function() {
							this.inherited(arguments);
							domStyle.set(this.domNode,{height:''});
						},
						
						postCreate : function() {
							this.inherited(arguments);
							if(this.canUpdate){
								var node = this.getNode();
								var isCanUpMove = this.isCanUpMove();
								var isCanDownMove = this.isCanDownMove();
								if(isCanUpMove){
									this.upMoveConnect = this.connect(this.upNode, 'click', this.upNodeClick);
								}else{
									domStyle.set(this.upNode,{display:'none'});
								}
								if(isCanDownMove){
									this.downMoveConnect = this.connect(this.downNode, 'click', this.downNodeClick);
								}else{
									domStyle.set(this.downNode,{display:'none'});
								}
								if(!isCanUpMove || !isCanDownMove){
									if(isCanUpMove){
										domClass.add(this.upDom,"free_flow_move_up_margin");
									}
									if(isCanDownMove){
										domClass.add(this.downDom,"free_flow_move_down_margin");
									}
								}
								if(node.Data["handlerSelectType"]=="org"){
									this.connect(this.setDom, 'click', this.setNodeClick);
								}
								this.subscribe("/sys/lbpmservice/freeflow/refreshNode","refreshNode");
								this.subscribe("/sys/lbpmservice/freeflow/deleteNode","_deleteNode");
								this.subscribe("/sys/lbpmservice/freeflow/updateNode","_updateNode");
								this.subscribe("/sys/lbpmservice/freeflow/___updateNode","___updateNode");
							}
						},
						
						refreshNode : function(){
							var isCanUpMove = this.isCanUpMove();
							var isCanDownMove = this.isCanDownMove();
							domClass.remove(this.upDom,"free_flow_move_up_margin");
							domClass.remove(this.downDom,"free_flow_move_down_margin");
							if(isCanUpMove){
								if(!this.upMoveConnect){
									this.upMoveConnect = this.connect(this.upNode, 'click', this.upNodeClick);
								}
								domStyle.set(this.upNode,{display:''});
							}else{
								if(this.upMoveConnect){
									this.upMoveConnect.remove();
									this.upMoveConnect = null;
								}
								domStyle.set(this.upNode,{display:'none'});
							}
							if(isCanDownMove){
								if(!this.downMoveConnect){
									this.downMoveConnect = this.connect(this.downNode, 'click', this.downNodeClick);
								}
								domStyle.set(this.downNode,{display:''});
							}else{
								if(this.downMoveConnect){
									this.downMoveConnect.remove();
									this.downMoveConnect = null;
								}
								domStyle.set(this.downNode,{display:'none'});
							}
							if(!isCanUpMove || !isCanDownMove){
								if(isCanUpMove){
									domClass.add(this.upDom,"free_flow_move_up_margin");
								}
								if(isCanDownMove){
									domClass.add(this.downDom,"free_flow_move_down_margin");
								}
							}
						},
						
						isCanUpMove : function(){
							var isCan = false;
							if(!this.template){
								var node = lbpm.nodes[this.nodeId];
								if(node.XMLNODENAME == "joinNode"){
									return false;
								}
								var startNode = node.startLines[0].startNode;
								if(startNode.XMLNODENAME == "splitNode"){
									return false;
								}else if(startNode.XMLNODENAME == "joinNode"){
									startNode = lbpm.nodes[startNode.relatedNodeIds];
								}
								if (startNode.Status == "1") {
									isCan = true;
								}
							}else{
								var node = this.getNode();
								if(node.Type == "joinNode"){
									return false;
								}
								var prevNode = node.LineIn[0].StartNode;
								if(prevNode.Type == "splitNode"){
									return false;
								}else if(prevNode.Type == "joinNode"){
									startNode = this.getNode(prevNode.Data["relatedNodeIds"]);
								}
								if(prevNode && prevNode.Data["id"] != "N2"){
									isCan = true;
								}
							}
							return isCan;
						}, 
						
						isCanDownMove : function(){
							var isCan = false;
							if(!this.template){
								var node = lbpm.nodes[this.nodeId];
								if(node.XMLNODENAME == "joinNode"){
									return isCan;
								}else if(node.XMLNODENAME == "splitNode"){
									node = lbpm.nodes[node.relatedNodeIds];
								}
								var endNode = node.endLines[0].endNode;
								if(endNode.XMLNODENAME == "joinNode"){
									return false;
								}
								if (endNode.id != "N3" && endNode.Status == "1") {
									isCan = true;
								}
							}else{
								var node = this.getNode();
								if(node.Type == "joinNode"){
									return isCan;
								}else if(node.Type == "splitNode"){
									node = this.getNode(node.Data["relatedNodeIds"]);
								}
								var nextNode = node.LineOut[0].EndNode;
								if(nextNode.Type == "joinNode"){
									return false;
								}
								if(nextNode && nextNode.Data["id"] != "N3"){
									isCan = true;
								}
							}
							return isCan;
						}, 
						
						upNodeClick : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if(clickTime != 'undefined' && (nowTime - clickTime < 500)){
						        return false;
						    }
						    this.ctime = nowTime;
							if(!lbpm.globals.isCanEdit()){
								return;
							}
							topic.publish("/sys/lbpmservice/freeflow/moveNode",this,"up");
						},
						
						downNodeClick : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
						        return false;
						     }
						    this.ctime = nowTime;
							if(!lbpm.globals.isCanEdit()){
								return;
							}
							topic.publish("/sys/lbpmservice/freeflow/moveNode",this,"down");
						},
						
						setNodeClick : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
						        return false;
						    }
						    this.ctime = nowTime;
							if(!lbpm.globals.isCanEdit()){
								return;
							}
						    if(this.freeflowNodeAttributeDialog==null){
								this.freeflowNodeAttributeDialog = new freeflowNodeAttribute();
							}
						    this.freeflowNodeAttributeDialog._selectCate({nodeId:this.nodeId,state:"update",template:this.template});
						},
						
						_deleteNode : function(srcObj){
							if(srcObj.nodeId==this.nodeId){
								this.destroy();
							}
						},
						
						_updateNode : function(srcObj){
							if(srcObj.nodeId==this.nodeId){
								var node = this.getNode();
								this.handlerNameDom.innerText= this.formatHandlerNames(this._getHandlerName());
								this.nodeNameDom.innerText = WorkFlow_getLangLabel(node.Data["name"],node.Data["langs"],"nodeName");
								var nodeClass = "fontmuis muis-org-approve";
								if (node.Type=="signNode"){
									nodeClass = "fontmuis muis-org-signature"
								} else if (node.Type=="sendNode"){
									nodeClass = "fontmuis muis-org-copy-to";
								}
								if(node.Data["isFixedNode"]=="true"){
									nodeClass = "fontmuis muis-org-locked";
								}
								domClass.replace(this.stateDom,nodeClass,this.stateDom.className);
							}
						},
						
						___updateNode : function(srcObj,evt){
							if(evt && evt.nodeId==this.nodeId){
								var node = this.getNode();
								this.handlerNameDom.innerText= this.formatHandlerNames(this._getHandlerName());
							}
						},
						
						getNode : function(nodeId){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(nodeId||this.nodeId);
						},
						
						_getHandlerName : function () {
							var node = this.getNode();
							var handlerName = node.Data["handlerNames"];
							if(this.template){
								return handlerName;
							}
							var dataNextNodeHandler;
							var nextNodeHandlerNames4View="";
							if(node.Data["handlerSelectType"]=="formula"){
								dataNextNodeHandler = lbpm.globals.formulaNextNodeHandler(node.Data["handlerIds"],true,false);
							}else if (node.Data["handlerSelectType"]=="matrix") {
								dataNextNodeHandler = lbpm.globals.matrixNextNodeHandler(node.Data["handlerIds"],true,false);
							}else if (node.Data["handlerSelectType"]=="rule") {
								dataNextNodeHandler = lbpm.globals.ruleNextNodeHandler(node.Data["id"],node.Data["handlerIds"],true,false);
							} else {
								dataNextNodeHandler = lbpm.globals.parseNextNodeHandler(node.Data["handlerIds"],true,false);
							}
							for(var j=0;j<dataNextNodeHandler.length;j++){
								if(nextNodeHandlerNames4View==""){
									nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
								}else{
									nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
								}
							}
							if(nextNodeHandlerNames4View == "" && node.Data["handlerIds"] != null) {
								nextNodeHandlerNames4View = lbpm.constant.COMMONNODEHANDLERORGNULL;
							}
							if(nextNodeHandlerNames4View){
								handlerName = nextNodeHandlerNames4View;
							}
							return handlerName;
						},
						
						formatHandlerNames : function(handlerNames){
							var name = Msg["mui.freeFlow.node.set"];
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
						
						onTouchMove: function(e){
							this.inherited(arguments);
							if(this.canUpdate){
								if(this.getPos().x<=-60){
									topic.publish("/sys/lbpmservice/freeflow/leftSlide",this);
								}else{
									topic.publish("/sys/lbpmservice/freeflow/recover",this);
								}
							}
						},
						
						onTouchEnd: function(e){
							this.inherited(arguments);
							if(this.canUpdate){
								if(this.getPos().x<=-60){
									this.slideTo({x:-60, y:0}, 0.3, "ease-out");
								}
							}
						}
					});
			return freeflowNode;
		});