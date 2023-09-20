define(
		[ "dojo/_base/declare","mui/tabbar/TabBarButton","dojo/topic",'dojo/query',"sys/lbpmservice/mobile/freeflow/freeflowNodeAttrGw"],
		function(declare,TabBarButton,topic,query,freeflowNodeAttrGw) {
			var freeflowNodeOkBtnGw = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeOkBtnGw",
					[TabBarButton], {
						
						rtn : {},
						
						onClick : function() {
							var FlowChartObject = this.getFlowChartObject();
							var handlerType = this.handlerType;
							for(var nodeId in this.rtn){
								var nodeObj = this.getNodes(nodeId);
								if(!nodeObj){
									continue;
								}
								var ruleId = this.rtn[nodeId]['handlerRule'];
								
								//0等于null
								if(ruleId =="0"){
									topic.publish("/sys/lbpmservice/freeflow/imissiveok",this);
									return;
								}
								var ruleSource = this.getRuleSource(ruleId);
								//附加选项
								var rule_s = ruleSource[0][0].ruleVal[0].fdAdditionRule;
								if(rule_s){
									rule_s=JSON.parse(rule_s);
									for(var i in rule_s){
										var imval = new Array();
										if(rule_s[i].key){
											imval["XMLNODENAME"]="variant";
											imval["name"]=rule_s[i].key;
											imval["value"]='true';
											if(!nodeObj.Data["variants"]){
												nodeObj.Data["variants"]=[];
											}
											nodeObj.Data["variants"][i]=imval;
										}
									}
								}
								
								//权限
								var nodeRule_s = ruleSource[0][0].ruleVal[0].fdNodeRule;
								if(nodeRule_s){
									nodeRule_s=JSON.parse(nodeRule_s);
									this.imissiveNodeRule(nodeRule_s,nodeObj);
									
								}
								
								//名称
								nodeObj.Data.opinionType= this.rtn[nodeId]['opinionType'];
								var ruleSelectTitle = this.rtn[nodeId]['handlerRuleTitle'];
								if(ruleSelectTitle){
									if(nodeObj.Data.name.indexOf(ruleSelectTitle) == -1){
										nodeObj.Data.name = "("+ruleSelectTitle+") "+nodeObj.Data.name;
									}
									if(nodeObj.Data["langs"]){
										var langs = JSON.parse(nodeObj.Data["langs"]);
										if(langs["nodeName"] && _userLang){
											for(var z=0;z<langs["nodeName"].length;z++){
												if(langs["nodeName"][z]["lang"]==_userLang){
													langs["nodeName"][z]["value"]=nodeObj.Data.name;
													break;
												}
											}
											nodeObj.Data["langs"] = JSON.stringify(langs);
										}
									}
									nodeObj.Data.ruleSelectTitle= "("+ruleSelectTitle+")";
								}
							}
							this.updateFlowXml(FlowChartObject);
							
							//修复iPhoneX点击穿透问题
							if(!this._CLICK_FLAG) {
								return;
							}
							
							topic.publish("/sys/lbpmservice/freeflow/imissiveokMsg",this.rtn);
							topic.publish("/sys/lbpmservice/freeflow/imissiveok",this);
						},
						
						postCreate : function() {
					        this.subscribe("mui/form/select/callback","_selectChangeGw");
							this.subscribe("/sys/lbpmservice/freeflow/attrValGw","_attrValGw");
							this.subscribe("/sys/lbpmservice/freeflow/imissiveChangeVal","_imissiveChangeVal");
						},
						
						//change Select意见类型
						_imissiveChangeVal : function(attrVal,nodeId){
							var ruleId = attrVal.value;
							var ruleSource = this.getRuleSource(ruleId);
							var nodeRule_s = ruleSource[0][0].ruleVal[0].fdNodeRule;
							var divCount = "";
							if(attrVal.state!="undefined"){
								divCount = attrVal.state;
							}
							var idGw = "#gw_Select_"+divCount;
							if(nodeRule_s){
								nodeRule_s=JSON.parse(nodeRule_s);
								var opinionType = nodeRule_s.opinionType;
								var gw_select = query(idGw)[0];
								if(opinionType){
									query("[name='_freeflowNodeGwType_"+divCount+"']",gw_select).val(opinionType);
									var nodeTypeText = this.ImNodeTypeText(opinionType);
									query('.muiSelInput',gw_select).html(nodeTypeText);
									this.rtn[nodeId]["opinionType"]=opinionType;
								}else{
									query("[name='_freeflowNodeGwType_"+divCount+"']",gw_select).val("");
									query('.muiSelInput',gw_select).html("");
									this.rtn[nodeId]["opinionType"]="";
								}
							}
						},
						
						//意见类型
						ImNodeTypeText : function(nodeTypeText){
							var store = '';
							var lbpmUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=getNodeTypeTextByVal";
							$.ajax({
								url: lbpmUrl,
								data:{'value':nodeTypeText},
								async: false,
								type: "POST",
								dataType: 'json',
								success: function (data) {
									if(data){
										store = data['text'];
									}
								},
								error: function (er) {
									console.log(er);
								}
							});
							return store;
						},
						_attrValGw : function(attrVal){
							this.rtn = attrVal;
						},
						_selectChangeGw : function(srcObj){
							var idFieldRule = "_freeflowNodeGwRule_";
							var idFieldType = "_freeflowNodeGwType_";
							
							if((srcObj.name)&&(srcObj.name.indexOf(idFieldRule) >-1 || srcObj.name.indexOf(idFieldType) >-1)){
								var nodeId = srcObj.nodeId;
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
								if(srcObj.name.indexOf(idFieldRule) >-1){
									var gwHtml = $(srcObj).next();
									this.rtn[nodeId]["handlerRule"]=srcObj.value;
									this.rtn[nodeId]["handlerRuleTitle"]=srcObj.text;
									topic.publish("/sys/lbpmservice/freeflow/imissiveChangeVal",srcObj,nodeId);
								}else if(srcObj.name.indexOf(idFieldType) >-1){
									this.rtn[nodeId]["opinionType"]=srcObj.value;
								}
							}
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
						
						
						getNodes : function(nodeId){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(nodeId);
						},
						
						getNode : function(){
							var iframe = document.getElementById('WF_IFrame');
							var FlowChartObject = iframe.contentWindow.FlowChartObject;
							return FlowChartObject.Nodes.GetNodeById(this.nodeId);
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
						
						//权限节点获取数据初始化
						imissiveNodeRule :function (fdNodeRule,node){
							if(fdNodeRule['opinionConfig']){
								node.Data.opinionConfig=fdNodeRule['opinionConfig'];
							}else{
								node.Data.opinionConfig="flase";
							}
							
							if(fdNodeRule['canAddAuditNoteAtt']){
								node.Data.canAddAuditNoteAtt=fdNodeRule['canAddAuditNoteAtt'];
							}else{
								node.Data.canAddAuditNoteAtt="false";
							}
							if(fdNodeRule['canModifyMainDoc'] == ""){
								node.Data.canModifyMainDoc="false";
							}else{
								node.Data.canModifyMainDoc=fdNodeRule['canModifyMainDoc'];
							}
							node.Data.flowPopedom=fdNodeRule['flowPopedom'];
							node.Data.notifyType=fdNodeRule['notifyType'];
							
							var selected = fdNodeRule['ignoreOnHandlerSame'];
							if(selected=="1"){//相邻处理人重复跳过
								node.Data["ignoreOnHandlerSame"]="true";
								node.Data["adjoinHandlerSame"]="true";
								node.Data["ignoreOnFutureHandlerSame"]="false";
							}else if(selected=="2"){//跨节点处理人重复跳过
								node.Data["ignoreOnHandlerSame"]="true";
								node.Data["adjoinHandlerSame"]="false";
								node.Data["ignoreOnFutureHandlerSame"]="false";
							}else if(selected=="3"){//后续处理人身份重复跳过当前
								node.Data["ignoreOnHandlerSame"]="false";
								node.Data["adjoinHandlerSame"]="false";
								node.Data["ignoreOnFutureHandlerSame"]="true";
							}else{//不跳过
								node.Data["ignoreOnHandlerSame"]="false";
								node.Data["adjoinHandlerSame"]="false";
								node.Data["ignoreOnFutureHandlerSame"]="false";
							}
						},
					});
			return freeflowNodeOkBtnGw;
		});