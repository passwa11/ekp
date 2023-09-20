define(
		[ "dojo/_base/declare", "mui/form/Category", "mui/address/AddressMixin","dojo/parser",
			"dijit/registry","dojo/query","dojo/dom-construct","dojo/dom-style","dojo/dom-class","dojo/dom-attr","dojo/on", 
			"mui/util", "dojo/dom-attr","dojo/_base/lang", "dojo/_base/array", "dojo/topic", "mui/dialog/Dialog","mui/dialog/Tip", 
			"mui/i18n/i18n!sys-mobile","mui/i18n/i18n!sys-lbpmservice","mui/dialog/Confirm","sys/lbpmservice/mobile/freeflow/freeflowNodeAttributeGw",
			"sys/lbpmservice/mobile/freeflow/freeflowNodeAttribute"],
		function(declare, Category, AddressMixin,parser,registry,query, domConstruct, domStyle ,domClass, domAttr, 
				on, util,domAttr, lang, array, topic, Dialog, Tip, msg, Msg, Confirm, freeflowNodeAttributeGw, freeflowNodeAttribute) {
			var freeflowNodes = declare("sys.lbpmservice.mobile.freeflow.freeflowNodes",
					[ Category, AddressMixin], {
						
						subject : '选人添加节点',
						
						isMul : true,
						
						type : ORG_TYPE_POSTORPERSON,
						
						idField : "_freeflowNodes_",
						
						id : "freeflowNodes",
						
						//是否显示头像
						showHeadImg : true,
						
						_cateDialogPrefix: "__freeflowNodes__",
						
						//新地址本
						isNew : true,
						
						//部门限制
						deptLimit : '',
						
						//例外类别id
						exceptValue:'',
						
						maxPageSize:300,
						
						dataUrl : '/sys/organization/mobile/address.do?method=addressList&parentId=!{parentId}&orgType=!{selType}&deptLimit=!{deptLimit}&maxPageSize=!{maxPageSize}',
						
						iconUrl : '/sys/lbpmservice/mobile/freeflow/image.jsp?orgId=!{orgId}',
						
						searchUrl:"/sys/organization/mobile/address.do?method=searchList&keyword=!{keyword}&orgType=!{orgType}&deptLimit=!{deptLimit}",
						
						nodeTmpl : "<div data-dojo-type=\"sys/lbpmservice/mobile/freeflow/freeflowNodeSplit\" data-dojo-props=\"nodeId:'!{nodeId}'\" style='display:none'></div>",
						
						addNodeTmpl : "<div data-dojo-type=\"sys/lbpmservice/mobile/freeflow/freeflowNodeType\"></div>",
						
						buildRendering : function() {
							this.inherited(arguments);
							domClass.add(this.domNode,'muiAddressForm')
							if(this.showHeadImg){
								domClass.add(this.domNode,'freeFlowShowHeadImg');
							}
							this.maximizeDom = domConstruct.create("div", {
								className : 'MuiFreeflowMaximizeDom'
							});
							domConstruct.place(this.maximizeDom, query("#freeflowRowTitle")[0], 'first');
							this.returnDom = domConstruct.create("div", {
								className : 'MuiFreeflowMaximizeReturn'
							},this.domNode.parentNode);
							domConstruct.create("span", {
								innerHTML : '返回'
							},this.returnDom);
						},
						
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe("mui/form/select/callback", lang.hitch(this,function(evt) {
								// 起草人切换提交身份时对应的起草人图标和起草人名称要跟随变化
								if (evt.id == "rolesSelectObj" && evt.key == "handlerId") {
									this.curDraftorId = evt.value;
									this.curDraftorName = evt.text;
									var nodes = query(this.draftOrgDom).closest(".freeFlowOrg");
									nodes.forEach(function(orgDom) {
										var id = orgDom.getAttribute("data-id");
										if (id == "N2"){
											var orgIconDom = query(orgDom).children(".freeFlowOrgIcon")[0];
											var icon = util.formatUrl(util.urlResolver(this.iconUrl , {
												orgId : this.curDraftorId
											}));
											domStyle.set(orgIconDom, 'background', 'url('+icon+') center center / cover no-repeat');
											var nameDom = query(orgDom).children(".name")[0];
											nameDom.innerText = this.curDraftorName;
										}
									}, this);
								}
							}));
							this.subscribe("/sys/lbpmservice/freeflow/viewTransitionOut",lang.hitch(this,function(view) {
								// 从freeflowChartView离开时刷新当前的freeflowNodes
								if (view.id == "freeflowChartView") {
									this.buildValue(this.contentNode);
								}
							}));
							this.subscribe("/sys/lbpmservice/freeflow/nodeTypeCancel","_nodeTypeCancel");
							this.subscribe("/sys/lbpmservice/freeflow/nodeTypeSelect","_nodeTypeSelect");
							this.subscribe("/sys/lbpmservice/freeflow/updateNode","_updateNode");
							this.subscribe("/sys/lbpmservice/freeflow/_delteNode","_delteNode");
							this.connect(this.maximizeDom,"click","_maximize");
							this.connect(this.returnDom,"click","_cancelMaximize");
							this.subscribe("/sys/lbpmservice/freeflow/___addNode","___addNodeImissive");
							this.subscribe("/sys/lbpmservice/freeflow/imissiveokMsg","_imissiveMsgChange");
							var self = this;
							lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, function(){
								self.buildValue(self.contentNode);
							});
						},
						
						_checkImissiveFlag:function(){
							var isBusinessAuthEnabled = Lbpm_SettingInfo["isBusinessAuthEnabled"];
							if (isBusinessAuthEnabled == "true") {
								return true;
							} else {
								return false;
							}
						},
						
						___addNodeImissive:function(thisVal,nodeVal){
							if(this._checkImissiveFlag()){
								var self = this;
								this.defer(function(){
									var handlerType = nodeVal.handlerType;
									var nodes = nodeVal.nodes;
									var noId = '';
									for ( var i in nodes) {
										var nodeId = nodes[i].Data.id;
										noId += nodeId+";";
									}
									noId = noId.substr(0,noId.length-1);
									//公文
								    if(self.freeflowNodeAttributeGwDialog==null){
										self.freeflowNodeAttributeGwDialog = new freeflowNodeAttributeGw();
									}
								    self.freeflowNodeAttributeGwDialog._selectCate({nodeId:noId,handlerType:handlerType});
								},100);
							}
						},
						
						_imissiveMsgChange:function(thisVal){
							if(this._checkImissiveFlag()){
								var FlowChartObject = this.getFlowChartObject();
								for(var nodeId in thisVal){
									if (FlowChartObject) {
										var endNodeObj = FlowChartObject.Nodes.GetNodeById(nodeId);
										if(!endNodeObj)
											continue;
									}
									var nameDom = query(".freeFlowOrg[data-id='"+nodeId+"']").children(".nameGw")[0];
									var ruleTitle = thisVal[nodeId]['handlerRuleTitle'];
									if(ruleTitle)
										nameDom.innerText = "("+ruleTitle+")";
									
								}

							}
						},
						
						startup:function(){
							this.containerNode = this.contentNode;
							this.inherited(arguments);
						},
						
						_maximize : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if(clickTime != 'undefined' && (nowTime - clickTime < 500)){
						       return false;
						    }
						    this.ctime = nowTime;
						    domClass.add(this.domNode,'muiFreeflowMaximize');
						    domClass.add(this.domNode.parentNode,'muiFreeflowMaximizeParent');
						},
						
						_cancelMaximize : function(){
							var nowTime = new Date().getTime();
						    var clickTime = this.ctime;
						    if(clickTime != 'undefined' && (nowTime - clickTime < 500)){
						       return false;
						    }
						    this.ctime = nowTime;
						    domClass.remove(this.domNode,'muiFreeflowMaximize');
						    domClass.remove(this.domNode.parentNode,'muiFreeflowMaximizeParent');
						},
						
						domNodeClick : function(){
							if(query(".nodeAddIcon").indexOf(arguments[0].target) >= 0){
								return;//中间添加按钮事件不走这边
							}
							this.currentStartNodeId = null;
							this.currentNodeType = null;
							if(this.showHeadImg){
								var evtNode = query(arguments[0].target).closest(".muiCategoryAdd");
								if(evtNode.length <= 0){
									return;
								}
							}
							var evtNode = query(arguments[0].target).closest(".freeFlowOrg");
							if(evtNode.length > 0){
								return;
							}
							this.defer(function(){
								this._selectCate();
							},350);
						},
						
						buildValue : function(domContainer){
							domClass.replace(domContainer,"freeFlowFiledShow",domContainer.className);
							if(this.curIds!=null && this.curIds!=''){
								var ids = this.curIds.split(this.splitStr);
								var names = this.curNames.split(this.splitStr);
								if (ids.length > 0){
									this.addNodeInFreeFlow();
								}
							}

							this.updateFreeFlowNodes(domContainer);
							// 切断父类设置的事件绑定
							this.disconnect(this.orgIconClickHandle);
							// 重新绑定
							if(this.cateFieldShowConnect){
								this.cateFieldShowConnect.remove();
								this.cateFieldShowConnect = null;
							}
							var self = this;
							this.cateFieldShowConnect = this.connect(this.cateFieldShow, on.selector(".freeFlowOrgIcon", "click"), function(evt) {
								if (evt.stopPropagation)
									evt.stopPropagation();
								if (evt.cancelBubble)
									evt.cancelBubble = true;
								if (evt.preventDefault)
									evt.preventDefault();
								if (evt.returnValue)
									evt.returnValue = false;
								var nodes = query(evt.target).closest(".freeFlowOrg");
								nodes.forEach(function(orgDom) {
									var id = orgDom.getAttribute("data-id");
									var node = lbpm.nodes[id];
									if (id == "N2" || node.handlerSelectType!="org") {
										return;
									}
									var canEdit = false;
									if (node.Status == "1" && node.isFixedNode != 'true') {
										if (lbpm.nowNodeFlowPopedom=="2") {
											canEdit = true;
										} else if (lbpm.nowNodeFlowPopedom=="1") {
											if (lbpm.myAddedNodes.contains(node.id)) {
												canEdit = true;
											}
										}
									}
									if(!canEdit){
										return;
									}
									if(!lbpm.globals.canAddOtherNode(id)){
										return;
									}
									if(!lbpm.globals.isCanEdit()){
										return;
									}
									var nowTime = new Date().getTime();
								    var clickTime = self.ctime;
								    if(clickTime != 'undefined' && (nowTime - clickTime < 500)){
								        return false;
								     }
								    self.ctime = nowTime;
								    if(self.freeflowNodeAttributeDialog==null){
								    	self.freeflowNodeAttributeDialog = new freeflowNodeAttribute();
									}
								    self.freeflowNodeAttributeDialog._selectCate({nodeId:id,state:"_update",template:false});
								}, this);
							});
						},
						
						_buildOneOrg : function(domContainer, node){
							var isMulHandlers = false;
							if (node.handlerNames) {
								if (node.handlerNames.split(";").length > 1) {
									isMulHandlers = true;
								}
							}
							
							if (isMulHandlers) {
								var icon = util.formatUrl(util.urlResolver(this.iconUrl , {
									orgId : ""
								}));
								this.buildOrgNode(domContainer, node, icon);
							} else {
								var icon = util.formatUrl(util.urlResolver(this.iconUrl , {
									orgId : node.handlerSelectType=="org" ? node.handlerIds : ""
								}));
								this.buildOrgNode(domContainer, node, icon);
							}
						},
						
						buildOrgNode : function(domContainer, node, icon){
							// 构建节点图标(头像)
							var tmpOrgDom = this.buildNodeIcon(domContainer, node, icon);
							if(node.XMLNODENAME != "endNode"){
								if(node.XMLNODENAME!="splitNode" && node.XMLNODENAME!="joinNode" && node.XMLNODENAME!="robotNode"){
									// 构建节点类型图标
									this.buildNodeTypeIcon(tmpOrgDom, node);
								}
								if(node.XMLNODENAME!="joinNode"){
									// 构建删除按钮图标
									this.buildDeleteIcon(tmpOrgDom, node);
								}
								// 构建箭头
								this.buildArrowIcon(tmpOrgDom, node);
							}
						},
						
						// 构建起草节点
						buildDraftorNode : function (domContainer) {
							var draftorId = $("[name='sysWfBusinessForm.fdDraftorId']")[0].value;
							if (this.curDraftorId) {
								draftorId = this.curDraftorId;
							}
							var draftorName = $("[name='sysWfBusinessForm.fdDraftorName']")[0].value;
							if (this.curDraftorName) {
								draftorName = this.curDraftorName;
							}
							var icon = util.formatUrl(util.urlResolver(this.iconUrl , {
								orgId : draftorId
							}));
							var tmpOrgDom = domConstruct.create("div",{className:"freeFlowOrg", "data-id":"N2"},domContainer);
							domConstruct.create("div", {
								style:{
									background:'url(' + icon +') center center no-repeat',
									backgroundSize:'cover',
									display:'inline-block'
								},
								className : 'freeFlowOrgIcon'
							}, tmpOrgDom);
							domConstruct.create('div',{
								className:'name',
								innerHTML: draftorName 
							},tmpOrgDom);
							//添加节点类型图标
							var iconUrl = util.formatUrl('/sys/lbpmservice/mobile/resource/image/draftNode.png');
							domConstruct.create("div", {
								style:{
									background:'url('+iconUrl+') center center no-repeat',
									backgroundSize:'cover',
									display:'block'
								},
								className : "nodeTypeIcon"
							}, tmpOrgDom);
							if (lbpm.nowNodeId == "N2") {
								this.isPassedRoute = false;
							}
							this.buildArrowIcon(tmpOrgDom, lbpm.nodes["N2"]);
							this.draftOrgDom = tmpOrgDom;
						},
						
						_delteNode : function(srcObj){
							if(srcObj.nodeId && srcObj.state=="_update"){
								this._delOneOrg(null,srcObj.nodeId);
							}
						},
						
						_delOneOrg : function(orgDom, id){
							this.inherited(arguments);
							this.deleteFreeFlowNode(id);
						},

					     _queryImissiveAuthName :function(processId, nodeId) {
							var imissiveTitle = "";
							$.ajax({
								url : Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmNoteImissiveAuth.do?method=queryNodeImissiveAuth",
								type : "GET",
								async : false,    //用同步方式
								data : {
									nodeId : nodeId,
									processId : processId
								},
								success : function(res) {
									res = eval('(' + res + ')');
									if (res.nodeAuthName) {
										imissiveTitle = res.nodeAuthName;
									}
								}
							});
							return imissiveTitle;
						},
						
						//构建节点图标（头像）
						buildNodeIcon : function(domContainer, node, icon){
							if(node.XMLNODENAME == "endNode"){
								icon = util.formatUrl('/sys/lbpmservice/mobile/resource/image/endNode.png');
							}else if(node.XMLNODENAME=="splitNode"){
								icon = util.formatUrl('/sys/lbpmservice/mobile/resource/image/splitNode.png');
							}else if(node.XMLNODENAME=="joinNode"){
								icon = util.formatUrl('/sys/lbpmservice/mobile/resource/image/joinNode.png');
							}else if(node.XMLNODENAME=="robotNode"){
								icon = util.formatUrl('/sys/lbpmservice/mobile/resource/image/robotNode.png');
							}
							var tmpOrgDom = domConstruct.create("div",{className:"freeFlowOrg", "data-id":node.id},domContainer);
							var freeFlowOrgIconNode = domConstruct.create("div", {
								style:{
									background:'url(' + icon +') center center no-repeat',
									backgroundSize:'cover',
									display:'inline-block'
								},
								className : 'freeFlowOrgIcon'
							}, tmpOrgDom);
							if(node.XMLNODENAME=="robotNode"){
								domStyle.set(freeFlowOrgIconNode, 'border', '1px solid');
							}
							if(node.XMLNODENAME != "endNode" && node.XMLNODENAME!="splitNode" && node.XMLNODENAME!="joinNode" && node.XMLNODENAME!="robotNode"){
								var nodeTitle = this._getNodeTitle(node);
								domConstruct.create('div',{
									className:'name',
									innerHTML: nodeTitle 
								},tmpOrgDom);
								if(this._checkImissiveFlag()){
									var ruleSelectTitle = node.ruleSelectTitle;
									if(!ruleSelectTitle){
										ruleSelectTitle = '';
										ruleSelectTitle = this._queryImissiveAuthName(lbpm.modelId, node.id);
										if(ruleSelectTitle)
											ruleSelectTitle = "("+ruleSelectTitle+")";
									}

									domConstruct.create('div',{
										className:'nameGw',
										innerHTML: ruleSelectTitle
									},tmpOrgDom);
								}
							}
							return tmpOrgDom;
						},
						
						//构建节点类型图标
						buildNodeTypeIcon : function(domContainer, node){
							var iconUrl = util.formatUrl('/sys/lbpmservice/mobile/resource/image/'+node.XMLNODENAME+'.png');
							domConstruct.create("div", {
								style:{
									background:'url(' + iconUrl + ') center center no-repeat',
									backgroundSize:'cover',
									display:'block'
								},
								className : "nodeTypeIcon"
							}, domContainer);
						},
						
						//构建删除图标
						buildDeleteIcon : function(domContainer, node){
							var canDelete = false;
							if (node.Status == "1" && node.isFixedNode != 'true') {
								if (lbpm.nowNodeFlowPopedom=="2") {
									canDelete = true;
								} else if (lbpm.nowNodeFlowPopedom=="1") {
									if (lbpm.myAddedNodes.contains(node.id)) {
										canDelete = true;
									}
								}
							}
							//添加删除按钮
							if(canDelete && lbpm.globals.canAddOtherNode(node.id)){
								var delOrgDom = domConstruct.create('div',{ className : 'del mui mui-close' },domContainer);
								this.connect(delOrgDom,'touchend',function(evt) {
									if (evt.stopPropagation)
										evt.stopPropagation();
									if (evt.cancelBubble)
										evt.cancelBubble = true;
									if (evt.preventDefault)
										evt.preventDefault();
									if (evt.returnValue)
										evt.returnValue = false;
									if(!lbpm.globals.isCanEdit()){
										return;
									}
									var nodes = query(evt.target).closest(".freeFlowOrg");
									nodes.forEach(function(orgDom) {
										var id = orgDom.getAttribute("data-id");
										this.defer(function() { // 同时关注时，必须要异步处理
											var node = lbpm.nodes[id];
											if (!node) {
												return;
											}
											if(node.XMLNODENAME=="splitNode"){
												var self = this;
												Confirm(Msg['mui.freeFlow.splitNode.deleteMsg'],null,function(data){
													if(data){
														self._delOneOrg(orgDom, id);
													}
												},false);
											}else{
												this._delOneOrg(orgDom, id);
											}
										}, 300);
									}, this);
								});
							}
						},
						
						// 构建箭头图标
						buildArrowIcon : function(domContainer, node) {
							var arrowUrl = util.formatUrl('/sys/lbpmservice/mobile/resource/image/arrowGoto.png');
							if (this.isPassedRoute){
								arrowUrl = util.formatUrl('/sys/lbpmservice/mobile/resource/image/arrowGone.png');
							}
							this.lastLineIcon = domConstruct.create("div", {
								style:{
									display:'block'
								},
								className : 'nodeAddLine'
							}, domContainer);
							if(node.Status != lbpm.constant.STATUS_PASSED && (lbpm.nowNodeFlowPopedom == "1" || lbpm.nowNodeFlowPopedom == "2") && lbpm.globals.canAddOtherNode(node.id)){
								var className = 'nodeAddIcon mui mui-plus';
								this.lastNodeAddIcon = domConstruct.create("div",{style:{
									display:'block'
								},className: className },domContainer);
								this.connect(this.lastNodeAddIcon,'click',lang.hitch(this,function(evt){
									if(!lbpm.globals.isCanEdit()){
										return;
									}
									var freeFlowOrgElem = query(evt.target).closest(".freeFlowOrg")[0];
									this.currentStartNodeId = domAttr.get(freeFlowOrgElem,"data-id");
									if(!this.nodeTypeDialog){
										this.dialogNode = domConstruct.toDom(this.addNodeTmpl);
										this.nodeTypeDialog = Dialog.element({
											canClose : false,
											showClass : 'muiDialogElementShow muiFormSelect free_flow_NodeType',
											element : this.dialogNode,
											position:'bottom',
											'scrollable' : false,
											'parseable' : true,
											callback : lang.hitch(this, function() {
												this.nodeTypeDialog = null;
											})
										});
									}
								}));
							}
							var nextNodeId = node.endLines[0].endNode.id;
							if (nextNodeId == lbpm.nowNodeId) {
								this.isPassedRoute = false;
								//设置当前页面的添加按钮为none
								query(".nodeAddIcon",query(".freeFlowFiledShow")[0]).style({
									"display":'none'
								})
							}
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
								this.currentNodeType = val;
								this._selectCate();
							}
						},
						
						buildOptIcon:function(optContainer){
							if(!this.showHeadImg)
								this.inherited(arguments);
							this.muiCategoryAddNode = optContainer;
						},
						
						// 构建节点标题（底部的名称）
						_getNodeTitle : function (node) {
							var handlerName = node.handlerNames;
							var dataNextNodeHandler;
							var nextNodeHandlerNames4View="";
							if(node.handlerSelectType=="formula"){
								dataNextNodeHandler = lbpm.globals.formulaNextNodeHandler(node.handlerIds,true,false);
							}else if (node.handlerSelectType=="matrix") {
								dataNextNodeHandler = lbpm.globals.matrixNextNodeHandler(node.handlerIds,true,false);
							}else if (node.handlerSelectType=="rule") {
								dataNextNodeHandler = lbpm.globals.ruleNextNodeHandler(node.id,node.handlerIds,true,false);
							} else {
								dataNextNodeHandler = lbpm.globals.parseNextNodeHandler(node.handlerIds,true,false);
							}
							for(var j=0;j<dataNextNodeHandler.length;j++){
								if(nextNodeHandlerNames4View==""){
									nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
								}else{
									nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
								}
							}
							if(nextNodeHandlerNames4View == "" && node.handlerIds != null) {
								nextNodeHandlerNames4View = lbpm.constant.COMMONNODEHANDLERORGNULL;
							}
							if(nextNodeHandlerNames4View){
								handlerName = nextNodeHandlerNames4View;
							}
							var handlerSize = handlerName.split(";").length;
							var nodeTitle = handlerName;
							if (handlerSize > 1) {
								if (node.XMLNODENAME == "reviewNode") {
									if (node.processType == '0') {
										nodeTitle = lang.replace(Msg['mui.freeFlow.reviewNode.processType_0.nodeTitle'],[handlerSize]);
									} else if (node.processType == '1') {
										nodeTitle = lang.replace(Msg['mui.freeFlow.reviewNode.processType_1.nodeTitle'],[handlerSize]);
									} else if (node.processType == '2') {
										nodeTitle = lang.replace(Msg['mui.freeFlow.reviewNode.processType_2.nodeTitle'],[handlerSize]);
									} 
								} else if (node.XMLNODENAME == "signNode") {
									if (node.processType == '0') {
										nodeTitle = lang.replace(Msg['mui.freeFlow.signNode.processType_0.nodeTitle'],[handlerSize]);
									} else if (node.processType == '1') {
										nodeTitle = lang.replace(Msg['mui.freeFlow.signNode.processType_1.nodeTitle'],[handlerSize]);
									} else if (node.processType == '2') {
										nodeTitle = lang.replace(Msg['mui.freeFlow.signNode.processType_2.nodeTitle'],[handlerSize]);
									} 
								} else if (node.XMLNODENAME == "sendNode") {
									nodeTitle = Msg['mui.freeFlow.sendNode.nodeTitle'];
								}
							}
							return nodeTitle;
						},
						
						_updateNode : function(srcObj){
							if(srcObj.nodeId && srcObj.state=="_update"){
								var FlowChartObject = this.getFlowChartObject();
								this.updateFlowXml(FlowChartObject);
								var node = lbpm.nodes[srcObj.nodeId];
								if(!node){
									return;
								}
								
								var icon = "";
								var isMulHandlers = false;
								if (node.handlerNames) {
									if (node.handlerNames.split(";").length > 1) {
										isMulHandlers = true;
									}
								}
								if (isMulHandlers) {
									var icon = util.formatUrl(util.urlResolver(this.iconUrl , {
										orgId : ""
									}));
								} else {
									var icon = util.formatUrl(util.urlResolver(this.iconUrl , {
										orgId : node.handlerSelectType=="org" ? node.handlerIds : ""
									}));
								}
								var orgIconDom = query(".freeFlowOrg[data-id='"+srcObj.nodeId+"']").children(".freeFlowOrgIcon")[0];
								domStyle.set(orgIconDom, 'background', 'url('+icon+') center center / cover no-repeat');
								var nameDom = query(".freeFlowOrg[data-id='"+srcObj.nodeId+"']").children(".name")[0];
								nameDom.innerText = this._getNodeTitle(lbpm.nodes[srcObj.nodeId]);
							}
						},
						
						returnDialog:function(srcObj , evt){
							if(srcObj.key == this.idField){
								this.inherited(arguments);
								this.curIds = "";
								this.curNames = "";
							}
						},
						
						// 以下是自由流流程图相关操作js
						addNodeInFreeFlow : function() {
							var FlowChartObject = this.getFlowChartObject();
							if (FlowChartObject) {
								var endNodeObj = FlowChartObject.Nodes.GetNodeById("N3");
								var newNodeObj,beginNode;
								beginNode = endNodeObj.LineIn[0].StartNode;
								if(this.currentStartNodeId){
									beginNode = FlowChartObject.Nodes.GetNodeById(this.currentStartNodeId);
								}
								var ids = this.curIds.split(this.splitStr);
								var names = this.curNames.split(this.splitStr);
								if (ids.length>0) {
									if (lbpm.freeFlow.defOperRefIds.length == 0 || !lbpm.freeFlow.defFlowPopedom) {
										var data = new KMSSData();
										data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowReviewNode");
										data = data.GetHashMapArray();
										for(var j=0;j<data.length;j++){
											if(data[j].isDefault=="true"){
												lbpm.freeFlow.defOperRefIds["reviewNode"] = data[j].value;
												break;
											}
										}
										data = new KMSSData().AddBeanData("getOperTypesByNodeService&nodeType=freeFlowSignNode");
										data = data.GetHashMapArray();
										for(var j=0;j<data.length;j++){
											if(data[j].isDefault=="true"){
												lbpm.freeFlow.defOperRefIds["signNode"] = data[j].value;
												break;
											}
										}
										lbpm.freeFlow.defFlowPopedom = (Lbpm_SettingInfo["defaultFlowPopedom"] > lbpm.nodes[lbpm.nowNodeId].flowPopedom)?lbpm.nodes[lbpm.nowNodeId].flowPopedom:Lbpm_SettingInfo["defaultFlowPopedom"];
									}
									var nodeList = [];
									if(beginNode.Type == "splitNode"){
										nodeList = FlowChartObject.Nodes.spitNodecreateFreeFlow(beginNode,"reviewNode",true,{handlerType:this.currentNodeType,handlerIds:this.curIds,handlerNames:this.curNames})
									}else{
										nodeList = FlowChartObject.Nodes._CreateFreeFlowSpitMore({handlerType:this.currentNodeType,handlerIds:this.curIds,handlerNames:this.curNames},beginNode,"reviewNode",true);
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
								}
								this.updateFlowXml(FlowChartObject);
								topic.publish("/sys/lbpmservice/freeflow/___addNode",this,{nodes:nodeList,handlerType:this.currentNodeType});
							}
						},
						
						updateFreeFlowNodes : function(domContainer) {
							this.getChildren().forEach(function(wgt,index){
								wgt.destroy();
							})
							domConstruct.empty(domContainer);
							lbpm.freeFlow.nextNodes = [];
							this.isPassedRoute = true;
							this.buildDraftorNode(domContainer);
							this.appendFreeFlowNode(domContainer,lbpm.nodes["N2"],[]);
							this.validation.validateElement(this);
							var self = this;
							parser.parse(domContainer).then(function(){
								self.formatNodes();
							});
						},

						appendFreeFlowNode : function(domContainer,nodeObj,nodeIds){
							for(var i = 0 ;i<nodeObj.endLines.length;i++){
								var nodeId = nodeObj.endLines[i].endNode.id;
								if ((nodeIds.join(";")+";").indexOf(nodeId+";")>-1) {
									return;
								}
								nodeIds.push(nodeId);
								var node = lbpm.nodes[nodeId];
								this._buildOneOrg(domContainer, node);
								if(!this.edit){
									domConstruct.create("span",{innerHTML:this.splitStr},domContainer);
								}
								if(node.XMLNODENAME=="splitNode" || node.XMLNODENAME=="joinNode"){
									var tmpl = this.nodeTmpl.replace("!{nodeId}", nodeId);
									var addAreaDom = domConstruct.toDom(tmpl);
							        domConstruct.place(addAreaDom, domContainer, "last");
								}
								this.appendFreeFlowNode(domContainer, node, nodeIds);
							}
						},
						
						formatNodes : function(){
							var self = this;
							var FlowChartObject = this.getFlowChartObject();
							if(FlowChartObject && FlowChartObject.Nodes && FlowChartObject.Nodes.GetNodeById && FlowChartObject.Nodes.GetNodeById("N2")){
								if(FlowChartObject.Nodes.all && FlowChartObject.Nodes.all[FlowChartObject.Nodes.all.length-1] && FlowChartObject.Nodes.all[FlowChartObject.Nodes.all.length-1].Level==0){
									FlowChartObject.Nodes.FlowChart_formatNodes();
								}
								var heights = this.getNodeHeight(FlowChartObject.Nodes.all);
								for(var i=0;i<FlowChartObject.Nodes.all.length;i++){
									var node = FlowChartObject.Nodes.all[i];
									var nodeDom = query(".freeFlowOrg[data-id='"+node.Data.id+"']");
									if(nodeDom.length>0){
										var x = node.Data.y-140<=0?0:(node.Data.y-140)/80*95
										domStyle.set(nodeDom[0],{left:x+"px",top:node.Data.x/2-heights.minHeight+"px"});
										domAttr.set(nodeDom[0], 'data-left', x);
										domAttr.set(nodeDom[0], 'data-top', node.Data.x/2-heights.minHeight);
									}
								}
								domStyle.set(this.containerNode,{height:heights.maxHeight+10+'px'});
								topic.publish("/sys/lbpmservice/freeflow/formatNodeFinish",this);
							}else{
								this.defer(function(){
									self.formatNodes();
								},100);
							}
						},
						
						getNodeHeight:function(nodes){
							var maxHeight = 0;
							var minHeight = 999999;
							for(var i=0;i<nodes.length;i++){
								var node = nodes[i];
								if(maxHeight<node.Data.x/2){
									maxHeight = node.Data.x/2;
								}
								if(minHeight>node.Data.x/2){
									minHeight = node.Data.x/2;
								}
							}
							return {maxHeight:maxHeight,minHeight:minHeight};
						},
						
						deleteFreeFlowNode : function (id){
							this.delNodeInFreeFlow(id);
							this.validation.validateElement(this);
						},
						
						delNodeInFreeFlow : function(nodeId) {
							var FlowChartObject = this.getFlowChartObject();
							var delNodeObj = FlowChartObject.Nodes.GetNodeById(nodeId);
							if(delNodeObj){
								FlowChartObject.Nodes.deleteNodeInFreeFlow(delNodeObj,true);
								this.updateFlowXml(FlowChartObject);
								this.updateFreeFlowNodes(this.cateFieldShow);
							}
						},
						
						getFlowChartObject : function(){
							var iframe = document.getElementById('WF_IFrame');
							return iframe.contentWindow.FlowChartObject;
						},
						
						updateFlowXml : function(FlowChartObject) {
							var flowXml = FlowChartObject.BuildFlowXML();
							if (!flowXml){
								return;
							}
							var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
							processXMLObj.value = flowXml;
							lbpm.globals.parseXMLObj();
							lbpm.modifys = {};
							$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
							lbpm.events.mainFrameSynch();
						},
						onTouchEnd: function(e){
							//this.inherited(arguments);
						}
						
					});
			return freeflowNodes;
		});