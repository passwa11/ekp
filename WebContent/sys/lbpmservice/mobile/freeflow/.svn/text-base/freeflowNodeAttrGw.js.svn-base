define(
		["dojo/dom-construct",'dojo/_base/declare',"dijit/_WidgetBase","dojo/parser","dojox/mobile/sniff","dojo/topic"],
		function(domConstruct,declare,WidgetBase,parser,has,topic) {
			var freeflowNodeAttrGw = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeAttrGw",
					[WidgetBase], {
				
						type : ORG_TYPE_POSTORPERSON,
				
						attType : "",
						
						title : "",
												
						isMul : true,
						
						showStatus : "edit",
						
						idField : "__freeflowNodeImissiveAttr__",
						
						_cateDialogPrefix: "__freeflowNodeAttrGw__",
						
						rtn : {},
						
						buildRendering: function() {
					        this.inherited(arguments);
					        this._cateDialogPrefix = this._cateDialogPrefix + this.attType;
					        domConstruct.empty(this.domNode);

					        var title = this.getTitle();
							var self = this;
							this.defer(function(){
								topic.publish("/sys/lbpmservice/freeflow/attrValGw",this.rtn);
							},100);
							if(this.attType=="ruleSelect"){
								parser.parse(this.domNode).then(function () {
			                    win.doc.dojoClick = !has("ios") || has("ios") > 13;
			                	});
							}
						},
						
						buildSelectDom : function() {
							var tmpl = this.selectTemp.replace("!{name}", this.idFieldRule)
										.replace("!{value}", this.value)
										.replace("!{mul}", this.mul)
										.replace("!{store}", this.store)
										.replace("!{showStatus}", this.showStatus)
										.replace("!{state}", this.state)
										.replace("!{nodeId}", this.nodeId);
							var dom = domConstruct.toDom(tmpl);
					        domConstruct.place(dom, this.domNode, "last");
						},
						
						buildSelectType : function() {
							var tmpl = this.selectType.replace("!{name}", this.idFieldType)
										.replace("!{value}", this.value)
										.replace("!{mul}", this.mul)
										.replace("!{storeType}", this.storeType)
										.replace("!{showStatus}", this.showStatus)
										.replace("!{state}", this.state)
										.replace("!{nodeId}", this.nodeId);
							var dom = domConstruct.toDom(tmpl);
					        domConstruct.place(dom, this.domNode, "last");
						},
						
						buildSelectPosen : function() {
							var tmpl = this.showHead;
							var dom = domConstruct.toDom(tmpl);
					        domConstruct.place(dom, this.domNode, "last");
						},
						
						
						getNodes : function(nodeId){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(nodeId);
						},
						getTitle : function(){
							var noId = this.nodeId;
							noId=noId.split(";");
							for(var i in noId){
								var nodeId = noId[i];
								var nodeObj = this.getNodes(nodeId);
								var title = "";
								var lbpmAllSettingInfo = Lbpm_SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
							if(nodeObj && nodeObj.Type == "reviewNode"){
								if(this.attType=="ruleSelect"){
									var handlerType = this.handlerType;
									var _handlerIds = nodeObj.Data['handlerIds'];
									if(handlerType == "0" || handlerType == "1"){
										//name
										this.idFieldRule = "_freeflowNodeGwRule_"+i;
										this.idFieldType = "_freeflowNodeGwType_"+i;
										
										//头像
										this.showHead = "<div class='free_flow_nodeAttribute_item'><div class='free_flow_nodeAttribute_area'><div class='free_flow_nodeAttribute_left'><img src='"+Com_Parameter.ContextPath+"sys/lbpmservice/mobile/freeflow/image.jsp?orgId="+_handlerIds+"' alt='' style='width: 80px;height: 80px;'/></div><div class='free_flow_nodeAttribute_right'>"+nodeObj.Data.name+"</div></div></div>";
										this.buildSelectPosen();
										
										//权限
										this.selectTemp = "<div class='free_flow_nodeAttribute_item'><div class='free_flow_nodeAttribute_area'><div class='free_flow_nodeAttribute_left'>业务权限：</div><div class='free_flow_nodeAttribute_right'><div data-dojo-type='mui/form/Select'  data-dojo-props='name:\"!{name}\",value:\"!{value}\",mul:!{mul},store:!{store},state:\"!{state}\",nodeId:\"!{nodeId}\"'></div></div></div></div>",
										this.ruleNode(handlerType,_handlerIds,nodeObj,i);
										
										//意见类型
										if (lbpmAllSettingInfo && lbpmAllSettingInfo['isOpinionTypeEnabled'] == 'true') {
											this.selectType = "<div class='free_flow_nodeAttribute_item'><div class='free_flow_nodeAttribute_area'><div class='free_flow_nodeAttribute_left'>意见类型：</div><div class='free_flow_nodeAttribute_right'><div data-dojo-type='mui/form/Select' id='gw_Select_"+i+"' data-dojo-props='name:\"!{name}\",value:\"!{value}\",mul:!{mul},store:!{storeType},state:\"!{state}\",nodeId:\"!{nodeId}\"'></div></div></div></div><div class='free_flow_nodeAttribute_split'></div>",
											this.ImNodeType(nodeObj);
										}
										this._selectGwShow(nodeObj);
									}else if(handlerType == "3"){
										//name
										this.idFieldRule = "_freeflowNodeGwRule_";
										this.idFieldType = "_freeflowNodeGwType_";
										
										this.showHead = "<div class='free_flow_nodeAttribute_item'><div class='free_flow_nodeAttribute_area'><div class='free_flow_nodeAttribute_left'>"+nodeObj.Data.name+"</div></div></div>";
										this.buildSelectPosen();
										this.selectTemp = "<div class='free_flow_nodeAttribute_item'><div class='free_flow_nodeAttribute_area'><div class='free_flow_nodeAttribute_left'>业务权限：</div><div class='free_flow_nodeAttribute_right'><div data-dojo-type='mui/form/Select' data-dojo-props='name:\"!{name}\",value:\"!{value}\",mul:!{mul},store:!{store},state:\"!{state}\",nodeId:\"!{nodeId}\"'></div></div></div></div>",
										this.ruleNode(handlerType,_handlerIds,nodeObj);//权限
										//意见类型
										if (lbpmAllSettingInfo && lbpmAllSettingInfo['isOpinionTypeEnabled'] == 'true') {
											this.selectType = "<div class='free_flow_nodeAttribute_item'><div class='free_flow_nodeAttribute_area'><div class='free_flow_nodeAttribute_left'>意见类型：</div><div class='free_flow_nodeAttribute_right'><div data-dojo-type='mui/form/Select' id='gw_Select_' data-dojo-props='name:\"!{name}\",value:\"!{value}\",mul:!{mul},store:!{storeType},state:\"!{state}\",nodeId:\"!{nodeId}\"'></div></div></div></div>",
											this.ImNodeType(nodeObj);
										}
										this._selectGwShow(nodeObj);
									}
								} 
							}
							}
							return title;
						},
						
						//权限
						ruleNode : function(handlerType,_handlerIds,nodeObj,i){
							var modelName = lbpm.modelName;
							var urlPath = Com_Parameter.ContextPath + "km/imissive/km_imissive_rule/kmImissiveRule.do?method=findRuleListMobile";
							var store = '';
							$.ajax({
								url: urlPath,
								async: false,
								data:{'fdImissiveType':modelName,"handlerIds":_handlerIds,"handlerType":handlerType},
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
							if(store[0]){
								var ruleId = store[0].value;
								this.value = ruleId;
								if(ruleId){
									var ruleSource = this.getRuleSource(ruleId);	
									var fdNodeRule = ruleSource[0][0].ruleVal[0].fdNodeRule;
									fdNodeRule = JSON.parse(fdNodeRule);
									this.opinionTypeGw = fdNodeRule['opinionType'];
								}
								
								var processType = nodeObj.Data["processType"];
								this.store = JSON.stringify(store);
								this.mul = false;
								this.state = i;
								this.nodeId = nodeObj.Data.id;
								this.buildSelectDom();
							}
						},
						
						//意见类型
						ImNodeType : function(nodeObj){
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
							var processType = nodeObj.Data["processType"];
							this.storeType = JSON.stringify(store);
							if(this.opinionTypeGw){
								this.value = this.opinionTypeGw;
								this.opinionTypeGw = ''; //使用完初始化
							}
							this.mul = false;
							this.nodeId = nodeObj.Data.id;
							this.buildSelectType();
						},
						
						getNode : function(){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(this.nodeId);
						},
						
						
						getRuleSource:function(ruleId){
							var ruleSource = '';
							var urlPath = Com_Parameter.ContextPath + "km/imissive/km_imissive_rule/kmImissiveRule.do?method=getRuleSource";
							$.ajax({
								url: urlPath,
								data:{'ruleId':ruleId},
								async: false,
								type: "POST",
								dataType: 'json',
								success: function (data) {
									if(data){
										ruleSource = data;
									}
								},
								error: function (er) {
						
								}
							});
							return ruleSource;
						},
						
						_selectGwShow : function(srcObj){
							var nodeId = srcObj.Data.id;
							 var node =  this.getNodes(nodeId);
							if(!this.rtn){
								this.rtn = {};
							}
							if(!this.rtn[nodeId]){
								this.rtn[nodeId] = {};
							}
							if(!node){
								return;
							}
							var ruleStore = this.store;
							if(ruleStore){
									ruleStore = JSON.parse(ruleStore);
								var ruleId = ruleStore[0].value;
								if(ruleId){
									var ruleSource = this.getRuleSource(ruleId);
									var fdNodeRule = ruleSource[0][0].ruleVal[0].fdNodeRule;
									this.rtn[nodeId]["handlerRule"]=ruleId;
									this.rtn[nodeId]["handlerRuleTitle"]=ruleStore[0].text;
									var fdNodeJson = JSON.parse(fdNodeRule);
									this.rtn[nodeId]["opinionType"]=fdNodeJson['opinionType'];
								}
							}
						},
						
					});
			return freeflowNodeAttrGw;
		});