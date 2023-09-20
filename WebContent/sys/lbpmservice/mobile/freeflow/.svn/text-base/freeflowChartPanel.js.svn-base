define(
		["dojo/_base/declare","dijit/_WidgetBase","dojo/query","dojo/dom-construct","dojo/dom-style","dojo/_base/lang","dojo/topic",
		"mui/dialog/Dialog","mui/i18n/i18n!sys-lbpmservice","dojo/parser",'dojo/dom-attr',"sys/lbpmservice/mobile/freeflow/freeflowNodeAttribute",
		"dojo/_base/window","dojox/mobile/sniff","mui/address/AddressMixin"],
		function(declare,WidgetBase,query,domConstruct,domStyle,lang,topic,Dialog,Msg,parser,domAttr,freeflowNodeAttribute,win,has,Address) {
			var freeflowChartPanel = declare("sys.lbpmservice.mobile.freeflow.freeflowChartPanel",
					[WidgetBase], {
						template : false,
				
						optTmpl : '<div id="freeflowNodeTypeSelect" data-dojo-type="mui/form/RadioGroup" data-dojo-props="showStatus:\'edit\',name:\'_lbpm_address_button_radio\',mul:\'false\',store:{store},orient:\'vertical\'" style="padding-right:0;"></div>',
						
						nodeTmpl : "<div data-dojo-type=\"sys/lbpmservice/mobile/freeflow/freeflowNode\" data-dojo-props=\"nodeId:'!{nodeId}',template:!{template}\"></div>",
						
						addAreaTmpl : "<div data-dojo-type=\"sys/lbpmservice/mobile/freeflow/freeflowSplitArea\" data-dojo-props=\"nodeId:'!{nodeId}',template:!{template}\" class=\"free_flow_addArea\"></div>",
						
						addNodeTmpl : "<div data-dojo-type=\"sys/lbpmservice/mobile/freeflow/freeflowNodeType\" data-dojo-props=\"nodeType:'!{nodeType}'\"></div>",
						
						buildRendering : function() {
							this.inherited(arguments);
							this.cateFieldShow = domConstruct.create("div" ,{className:'free_flow_show'},this.domNode);
							if(this.template){
								this.defer(function(){
									lbpm.flow_chart_load_Frame();
								},0);
							}
							//初始化流程图
							this._init();
						},
						
						_init:function(){
							var self = this;
							var FlowChartObject = this.getFlowChartObject();
							if(FlowChartObject && FlowChartObject.Nodes && FlowChartObject.Nodes.GetNodeById && FlowChartObject.Nodes.GetNodeById("N2")){
								self.updateFreeFlowNodes(self.cateFieldShow,FlowChartObject);
							}else{
								this.defer(function(){
									this._init();
								},100);
							}
						},
						
						__init:function(view){
							if (view.id == "freeflowChartView") {
								this._init();
							}
						},
						
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe("/sys/lbpmservice/freeflow/addNode","_addNode");
							this.subscribe("/sys/lbpmservice/freeflow/deleteNode","_deleteNode");
							this.subscribe("/sys/lbpmservice/freeflow/moveNode","_moveNode");
							this.subscribe("/sys/lbpmservice/freeflow/updateNode","_updateNode");
							this.subscribe("mui/view/currentView","__init");
							this.subscribe("/sys/lbpmservice/freeflow/nodeTypeCancel","_nodeTypeCancel");
							this.subscribe("/sys/lbpmservice/freeflow/nodeTypeSelect","_nodeTypeSelect");
						},
						
						//修改节点
						_updateNode : function(srcObj){
							var FlowChartObject = this.getFlowChartObject();
							this.updateFlowXml(FlowChartObject);
						},
						
						//添加节点
						_addNode : function(srcObj){
							this._selectNodeType(srcObj);
						},
						
						//删除节点
						_deleteNode : function(srcObj){
							this.deleteFreeFlowNode(srcObj.nodeId);
							this.updateFreeFlowNodes(this.cateFieldShow,this.getFlowChartObject());
						},
						
						//移动节点
						_moveNode : function(srcObj,position){
							if(srcObj && position){
								var FlowChartObject = this.getFlowChartObject();
								var getNode = function(nodeId){
									return  FlowChartObject.Nodes.GetNodeById(nodeId);
								}
								if(position=="up"){
									var curNode = getNode(srcObj.nodeId);
									var preNode = curNode.LineIn[0].StartNode;
									if(preNode.Type=="joinNode"){
										preNode = getNode(preNode.Data["relatedNodeIds"]);
									}
									var beforNode = preNode.LineIn[0].StartNode;
									var afterNode = curNode.LineOut[0].EndNode;
									if(curNode.Type=="splitNode"){
										afterNode = getNode(curNode.Data["relatedNodeIds"]).LineOut[0].EndNode;
									}
									var preLine = curNode.LineIn[0];
									var beforLine = preNode.LineIn[0];
									var afterLine = curNode.LineOut[0];
									if(curNode.Type=="splitNode"){
										afterLine = getNode(curNode.Data["relatedNodeIds"]).LineOut[0];
									}
									beforLine.LinkNode(beforNode, curNode, '3', '1');
									if(curNode.Type=="splitNode"){
										preLine.LinkNode(getNode(curNode.Data["relatedNodeIds"]), preNode, '3', '1');
									}else{
										preLine.LinkNode(curNode, preNode, '3', '1');
									}
									if(preNode.Type=="splitNode"){
										afterLine.LinkNode(getNode(preNode.Data["relatedNodeIds"]), afterNode, '3', '1');
									}else{
										afterLine.LinkNode(preNode, afterNode, '3', '1');
									}
									beforLine.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
									preLine.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
									afterLine.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
								}else{
									var curNode = getNode(srcObj.nodeId);
									var nextNode = curNode.LineOut[0].EndNode;
									if(curNode.Type=="splitNode"){
										nextNode = getNode(curNode.Data["relatedNodeIds"]).LineOut[0].EndNode;
									}
									var beforNode = curNode.LineIn[0].StartNode;
									var afterNode = nextNode.LineOut[0].EndNode;
									if(nextNode.Type=="splitNode"){
										afterNode = getNode(nextNode.Data["relatedNodeIds"]).LineOut[0].EndNode;
									}
									var nextLine = nextNode.LineIn[0];
									var beforLine = curNode.LineIn[0];
									var afterLine = nextNode.LineOut[0];
									if(nextNode.Type=="splitNode"){
										afterLine = getNode(nextNode.Data["relatedNodeIds"]).LineOut[0];
									}
									beforLine.LinkNode(beforNode, nextNode, '3', '1');
									if(nextNode.Type=="splitNode"){
										nextLine.LinkNode(getNode(nextNode.Data["relatedNodeIds"]), curNode, '3', '1');
									}else{
										nextLine.LinkNode(nextNode, curNode, '3', '1');
									}
									if(curNode.Type=="splitNode"){
										afterLine.LinkNode(getNode(curNode.Data["relatedNodeIds"]), afterNode, '3', '1');
									}else{
										afterLine.LinkNode(curNode, afterNode, '3', '1');
									}
									beforLine.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
									nextLine.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
									afterLine.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
								}
								//更新流程图
								this.updateFlowXml(FlowChartObject);
								//格式化节点位置
								FlowChartObject.Nodes.FlowChart_formatNodes();
								this.updateFreeFlowNodes(this.cateFieldShow,FlowChartObject);
							}
						},
						
						// 选择节点类型
						_selectNodeType : function(srcObj){
							var self = this;
							if (this.nodeTypeDialog != null){
								return;
							}
							var store = [ {
								text : Msg["mui.freeFlow.lbpm.nodeType.reviewNode"],
								value : "reviewNode"
							}, {
								text : Msg["mui.freeFlow.lbpm.nodeType.signNode"],
								value : "signNode"
							}, {
								text : Msg["mui.freeFlow.lbpm.nodeType.sendNode"],
								value : "sendNode"
							}];
							this.dialogNode = domConstruct.toDom(lang.replace(this.optTmpl,{store: JSON.stringify(store).replace(/\"/g,"\'") }));
							this.nodeTypeDialog = Dialog.element({
								canClose : false,
								showClass : 'muiDialogElementShow muiFormSelect free_flow_selectNodeType',
								element : this.dialogNode,
								position:'bottom',
								'scrollable' : false,
								'parseable' : true,
								onDrawed:function(){
									self.defer(function(){
										self._nodeTypeClick(srcObj);
									},100);
								},
								callback : lang.hitch(this, function() {
									this.nodeTypeDialog = null;
								})
							});
							var splitDom = domConstruct.toDom("<div class='muiDialogElementButton_bottom_split'></div>");
							domConstruct.place(splitDom, query("#freeflowNodeTypeSelect",this.nodeTypeDialog.contentNode)[0],'after');
							var title = domConstruct.toDom("<div class='muiDialogElementButton_bottom'>"+Msg["mui.freeFlow.cancel"]+"</div>");
							domConstruct.place(title, splitDom,'after');
							query(title).on("touchend",function(evt){
								self.nodeTypeDialog.hide();
								self.nodeTypeDialog = null;
							});
						},
						
						//节点类型点击事件
						_nodeTypeClick : function(srcObj){
							var self = this;
							query(".muiRadioItem",this.dialogNode).on("touchend",function(evt){
								var nowTime = new Date().getTime();
							    var clickTime = this.ctime;
							    if(clickTime != 'undefined' && (nowTime - clickTime < 500)){
							       return false;
							    }
							    this.ctime = nowTime;
								var dom = evt.target;
								var nodeSelectType = query("input",dom).val();
								if(!nodeSelectType){
									var labelDom = query(dom).closest(".muiRadioItem")[0];
									nodeSelectType = query("input",labelDom).val();
								}
								self.nodeSelectType = nodeSelectType;
								self.startNodeId = srcObj.nodeId;
								// 弹出属性设置
								self.defer(function(){
									if(!self.nodeTypeDialog){
										self.dialogNode = domConstruct.toDom(self.addNodeTmpl.replace("!{nodeType}", self.nodeSelectType));
										this.nodeTypeDialog = Dialog.element({
											canClose : false,
											showClass : 'muiDialogElementShow muiFormSelect free_flow_NodeType',
											element : self.dialogNode,
											position:'bottom',
											'scrollable' : false,
											'parseable' : true,
											callback : lang.hitch(this, function() {
												self.nodeTypeDialog = null;
											})
										});
									}
								},350);
								// 关闭弹出窗口
								self.nodeTypeDialog.hide();
								self.nodeTypeDialog = null;
							});
						},
						
						_nodeTypeCancel : function(){
							if(this.nodeTypeDialog){
								this.nodeTypeDialog.hide();
								this.nodeTypeDialog = null;
							}
						},
						
						_nodeTypeSelect : function(val){
							if(this.nodeTypeDialog){
								this.nodeTypeDialog.hide();
								this.nodeTypeDialog = null;
								if(val=="3"){
									var newNode= this.addNodeInFreeFlow("","",this.nodeSelectType,this.startNodeId,val);
									if(this.freeflowNodeAttributeDialog==null){
										this.freeflowNodeAttributeDialog = new freeflowNodeAttribute();
									}
									this.freeflowNodeAttributeDialog._selectCate({nodeId:newNode.Data["id"],state:"add",template:this.template});
									var newDom = query(".free_flow_item[data-node-id='"+this.startNodeId+"']");
									if(newDom.length>0){
										var nodeType = domAttr.get(newDom[0],"data-node-type");
										if(nodeType=="splitNode"){
											this.updateFreeFlowNodes(this.cateFieldShow,this.getFlowChartObject());
										}else{
											var newItem = this.buildNodeInfo(null, newNode, newDom);
											parser.parse(newItem).then(function () {
							                    win.doc.dojoClick = !has("ios") || has("ios") > 13;
							                });
											this.defer(function(){
												topic.publish("/sys/lbpmservice/freeflow/refreshNode",this);
											},100);
										}
									}
								}else{
									var self = this;
									var hasDoAction = false
									Address.address(true,"","",this.nodeSelectType=="sendNode"?null:window.ORG_TYPE_POSTORPERSON,function(event){
										if(event && event.curIds && !hasDoAction){
											hasDoAction = true;
											self.addNodeInFreeFlow(event.curIds,event.curNames,self.nodeSelectType,self.startNodeId,val);
											self.updateFreeFlowNodes(self.cateFieldShow,self.getFlowChartObject());
										}
									})
								}
							}
						},
						
						// 构建起草节点
						buildDraftorNode : function (domContainer) {
							var itemDom = domConstruct.create("div", {
								className : 'free_flow_item',
								'data-node-id' : "N2"
							}, domContainer);
							var tmpOrgDom = domConstruct.create("div",{
								className:"free_flow_node"
							},itemDom);
							domConstruct.create("div", {
								className : 'free_flow_left'
							}, tmpOrgDom);
							var centerDom = domConstruct.create("div", {
								className : 'free_flow_center'
							}, tmpOrgDom);
							var spanCenterDom = domConstruct.create("span", {
								className : 'free_flow_center_icon'
							}, centerDom);
							domConstruct.create("i", {
								className : 'fontmuis muis-org-draft'
							}, spanCenterDom);
							domConstruct.create("span", {
								className : 'free_flow_sortName',
								innerHTML : Msg["mui.freeFlow.draftorNode"]
							}, centerDom);
							var rightDom = domConstruct.create("div", {
								className : 'free_flow_right'
							}, tmpOrgDom);
							domConstruct.create("span", {
								className : 'free_flow_drafterTip',
								innerHTML : Msg["mui.freeFlow.draftorNode.default"]
							}, rightDom);
							this.buildAddArea(itemDom,"N2");
						},
						
						//构建节点信息
						buildNodeInfo : function(domContainer, node, afterNode){
							var itemDom = domConstruct.create("div", {
								className : 'free_flow_item'+(node.LineIn[0].StartNode.Type == "splitNode"?" firstChild":"")+(node.LineOut[0].EndNode.Type == "joinNode"?" lastChild":""),
								'data-node-id' : node.Data["id"],
								'data-node-type' : node.Type
							});
							if(domContainer){
								domConstruct.place(itemDom, domContainer, "last");
							}else if(afterNode){
								query(itemDom).insertAfter(query(afterNode));
							}
							var tmpl = this.nodeTmpl.replace("!{nodeId}", node.Data["id"]).replace("!{template}", this.template);
							var nodeDom = domConstruct.toDom(tmpl);
					        domConstruct.place(nodeDom, itemDom, "last");
					        this.buildAddArea(itemDom,node.Data["id"]);
					        return itemDom;
						},

						//构建按钮及分割符区域
						buildAddArea : function(domContainer,nodeId){
							var tmpl = this.addAreaTmpl.replace("!{nodeId}", nodeId).replace("!{template}", this.template);
							var addAreaDom = domConstruct.toDom(tmpl);
					        domConstruct.place(addAreaDom, domContainer, "last");
						},
						
						//添加流程节点
						addNodeInFreeFlow : function(handlerIds,handlerNames,nodeType,beginId,currentNodeType) {
							var FlowChartObject = this.getFlowChartObject();
							if (FlowChartObject) {
								var newNodeObj,beginNode;
								if (beginId) {
									beginNode = FlowChartObject.Nodes.GetNodeById(beginId);
								} else {
									beginNode = FlowChartObject.Nodes.GetNodeById("N3").LineIn[0].StartNode;
								}
								if (lbpm.freeFlow.defOperRefIds.length == 0 || !lbpm.freeFlow.defFlowPopedom) {
									var data = new KMSSData();
									data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowReviewNode");
									data = data.GetHashMapArray();
									for(var i=0,len=data.length;i<len;i++){
										if(data[i].isDefault=="true"){
											lbpm.freeFlow.defOperRefIds["reviewNode"] = data[i].value;
											break;
										}
									}
									data = new KMSSData().AddBeanData("getOperTypesByNodeService&nodeType=freeFlowSignNode");
									data = data.GetHashMapArray();
									for(var i=0,len=data.length;i<len;i++){
										if(data[i].isDefault=="true"){
											lbpm.freeFlow.defOperRefIds["signNode"] = data[i].value;
											break;
										}
									}
									lbpm.freeFlow.defFlowPopedom = Lbpm_SettingInfo["defaultFlowPopedom"];
								}
								if(!this.notifyDfaultValue){
									var data = new KMSSData();
									data.AddBeanData("getNotifyTypeService");
									data = data.GetHashMapArray();
									this.notifyDfaultValue = data[0]["defaultValue"];
								}
								nodeType = nodeType||"reviewNode";
								var nodeList = [];
								if(beginNode.Type == "splitNode"){
									nodeList = FlowChartObject.Nodes.spitNodecreateFreeFlow(beginNode,nodeType,true,{handlerType:currentNodeType,handlerIds:handlerIds,handlerNames:handlerNames})
								}else{
									nodeList = FlowChartObject.Nodes._CreateFreeFlowSpitMore({handlerType:currentNodeType,handlerIds:handlerIds,handlerNames:handlerNames},beginNode,nodeType,true);
								}
								for(var i=0;i<nodeList.length;i++){
									var newNodeObj = nodeList[i];
									lbpm.myAddedNodes.push(newNodeObj.Data.id);
									if(newNodeObj.Type=="reviewNode"||newNodeObj.Type=="signNode"){
										newNodeObj.Data["operations"]["refId"]=lbpm.freeFlow.defOperRefIds[newNodeObj.Type];
										newNodeObj.Data["flowPopedom"]=lbpm.freeFlow.defFlowPopedom;
										newNodeObj.Data["canAddAuditNoteAtt"]=Lbpm_SettingInfo["isCanAddAuditNoteAtt"];
										newNodeObj.Data["canModifyMainDoc"]=Lbpm_SettingInfo["isEditMainDocument"];
										newNodeObj.Data["processType"]="2";
									}
									newNodeObj.Data["notifyType"]=Lbpm_SettingInfo["defaultNotifyType"];
								}
								
								beginNode = newNodeObj;
								this.updateFlowXml(FlowChartObject);
								return newNodeObj;
							}
						},
						
						//开始构建流程图信息
						updateFreeFlowNodes : function(domContainer,FlowChartObject) {
							this.getChildren().forEach(function(wgt,index){
								wgt.destroy();
							})
							domConstruct.empty(domContainer);
							lbpm.freeFlow.nextNodes = [];
							this.buildDraftorNode(domContainer);
							this.appendFreeFlowNode(domContainer,FlowChartObject);
							parser.parse(domContainer).then(function () {
			                    win.doc.dojoClick = !has("ios") || has("ios") > 13;
			                });
						},
						
						//循环添加节点
						appendFreeFlowNode : function(domContainer,FlowChartObject,nodeObj,joinNodeId,parentJoinNodeId){
							if(!nodeObj){
								nodeObj = FlowChartObject.Nodes.GetNodeById("N2");
							}
							if(nodeObj.Type == "splitNode" && !joinNodeId){
								//若为并行分支
								var relatedNodeId = nodeObj.Data["relatedNodeIds"];
								domConstruct.create("div", {
									className : 'free_flow_split_item',
									'data-split-id' : nodeObj.Data["id"],
									'data-relatedNode-id' : relatedNodeId
								},parentJoinNodeId?query(".free_flow_split_item[data-relatedNode-id='"+parentJoinNodeId+"']")[0]:domContainer);
								//循环并行分支
								this.appendFreeFlowNode(domContainer,FlowChartObject,nodeObj,relatedNodeId);
								nodeObj = FlowChartObject.Nodes.GetNodeById(relatedNodeId).LineIn[0].StartNode;
								joinNodeId = parentJoinNodeId || "";
								parentJoinNodeId = "";
								//创建并行分支结束节点
								this.appendFreeFlowNode(domContainer,FlowChartObject,nodeObj,joinNodeId);
								nodeObj = FlowChartObject.Nodes.GetNodeById(relatedNodeId);
							}
							for(var h = 0 ;h<nodeObj.LineOut.length;h++){
								var node = nodeObj.LineOut[h].EndNode;
								if (node.Data["id"] == "N3" || node.Data["id"] == joinNodeId) {
									return;
								}
								var cleanJoinNodeId = false;
								if(node.Type == "splitNode" && joinNodeId){
									parentJoinNodeId = joinNodeId;
									cleanJoinNodeId = true;
								}
								if(joinNodeId){
									var splitDom = query(".free_flow_split_item[data-relatedNode-id='"+joinNodeId+"']");
									if(splitDom.length>0){
										this.buildNodeInfo(splitDom[0], node);
									}else{
										this.buildNodeInfo(domContainer, node);
									}
								}else{
									this.buildNodeInfo(domContainer, node);
								}
								if(node.Type != "joinNode"){
									this.appendFreeFlowNode(domContainer,FlowChartObject,node,cleanJoinNodeId?"":joinNodeId,parentJoinNodeId);
								}
							}
						},
						
						//删除节点
						deleteFreeFlowNode : function (id){
							if(!this.template){
								var node = lbpm.nodes[id];
								if (!node) {
									return;
								}
							}
							this.delNodeInFreeFlow(id);
						},
						
						//删除节点
						delNodeInFreeFlow : function(nodeId) {
							var FlowChartObject = this.getFlowChartObject();
							var delNodeObj = FlowChartObject.Nodes.GetNodeById(nodeId);
							FlowChartObject.Nodes.deleteNodeInFreeFlow(delNodeObj,true);
							this.updateFlowXml(FlowChartObject);
						},
						
						//获得流程图对象
						getFlowChartObject : function(){
							var iframe = document.getElementById('WF_IFrame');
							return iframe.contentWindow.FlowChartObject;
						},
						
						//更新流程图
						updateFlowXml : function(FlowChartObject) {
							var flowXml = FlowChartObject.BuildFlowXML();
							if (!flowXml || this.template){
								return;
							}
							var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
							processXMLObj.value = flowXml;
							lbpm.globals.parseXMLObj();
							lbpm.modifys = {};
							$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
							lbpm.events.mainFrameSynch();
						}
					});
			return freeflowChartPanel;
		});