define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/query",
    "dojo/_base/array",
	"dojo/dom-class",
	"dojo/dom-construct",
	"dojo/dom-style",
	"mui/form/RadioGroup",
	"mui/form/Radio",
	"mui/util",
	"dijit/registry"
	], function(declare, lang, query, array, domClass, domConstruct, domStyle, _RadioGroup, _Radio, util, registry) {
	
	var Radio = declare("sys.lbpmservice.mobile.workitem.AdHocRouteRadio", [_Radio], {
		
		rightText: null,
		
		handlerIds: null,
		
		handlerSelectType: null,
		
		buildRendering : function() {
			this.inherited(arguments);
			if (this.rightText != null && this.rightText != 'none') {
				var noSet = this.rightText == '';
				var self = this;
				var action = function(data) {
					var text = self.rightText = '', result = data.GetHashMapArray();
					if (result.length > 0) {
						array.forEach(result,function(info){
							text = text + ";" +  info.name;
						});
						if(text!=''){
							text = text.substring(1);
						}
					}
					self.rightTextNode = domConstruct.create('div', {
						className : noSet ? 'handlerNamesLabel noHandlerNamesLabel' : 'handlerNamesLabel' ,
						innerHTML : noSet ? "(未设置)" : "("+util.formatText(text)+")",
						id: "handlerShowNames[" + self.index + "]"
					}, self.fieldOptions, 'last');
				};
				if(this.handlerSelectType=="formula"){
					lbpm.globals.formulaNextNodeHandler(this.handlerIds,true,this.distinct, action);
				}else if (this.handlerSelectType=="matrix") {
					lbpm.globals.matrixNextNodeHandler(this.handlerIds,true,this.distinct, action);
				}else if (this.handlerSelectType=="rule") {
					lbpm.globals.ruleNextNodeHandler(this.value,this.handlerIds,true,this.distinct, action);
				} else {
					lbpm.globals.parseNextNodeHandler(this.handlerIds,true,this.distinct, action);
				}
			}
		},
		
		_onClick: function() {
			if(!this.__checkFlag){
				this.__checkFlag=true;
				this.inherited(arguments);
				this.__checkFlag=false;
			}
		},
		_setCheckedAttr : function(checked) {
			this.inherited(arguments);
		}
	});
	
	//解析节点处理人详细信息（组织架构配置）
	lbpm.globals.parseNextNodeHandler=function(ids, analysis4View, distinct, action) {
		if (ids == '' || ids == null) {
			return [{name: lbpm.constant.COMMONNODEHANDLERORGEMPTY}];
		}
		ids = encodeURIComponent(ids);
		var other = "&modelId=" + lbpm.globals.getWfBusinessFormModelId();
		var rolesSelectObj = document.getElementsByName('rolesSelectObj');
		if (rolesSelectObj != null && rolesSelectObj.length > 0) {
			other += "&drafterId=" + rolesSelectObj[0].value;
		}
		var url = "lbpmHandlerParseService&handlerIds=" + ids + other+"&analysis4View="+analysis4View;
		if(distinct) {
			url += "&distinct=true";
		}
		var data = new KMSSData(); 
		if(action) {
			data.SendToBean(url, action);
		} else {
			return data.AddBeanData(url).GetHashMapArray();
		}
	};

	// 解析节点处理人详细信息（公式配置）
	lbpm.globals.formulaNextNodeHandler=function(formula, analysis4View, distinct, action) {
		if (formula == '' || formula == null) {
			return [{name: '('+lbpm.constant.COMMONNODEHANDLERORGEMPTY+')'}];
		}
		formula = encodeURIComponent(formula);
		var other = "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&modelName=" + lbpm.globals.getWfBusinessFormModelName();
		var rolesSelectObj = document.getElementsByName('rolesSelectObj');
		if (rolesSelectObj != null && rolesSelectObj.length > 0) {
			other += "&drafterId=" + rolesSelectObj[0].value;
		}
		var url = "lbpmHandlerParseService&formula=" + formula + other+"&analysis4View="+analysis4View;
		if(distinct) {
			url += "&distinct=true";
		}
		var data = new KMSSData();
		if(action) {
			data.SendToBean(url, action);
		} else {
			return data.AddBeanData(url).GetHashMapArray();
		}
	};
	
	// 解析节点处理人详细信息（矩阵组织配置）
	lbpm.globals.matrixNextNodeHandler=function(matrix, analysis4View, distinct, action) {
		if (matrix == '' || matrix == null) {
			return [{name: '('+lbpm.constant.COMMONNODEHANDLERORGEMPTY+')'}];
		}
		matrix = encodeURIComponent(matrix);
		var other = "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&modelName=" + lbpm.globals.getWfBusinessFormModelName();
		var rolesSelectObj = document.getElementsByName('rolesSelectObj');
		if (rolesSelectObj != null && rolesSelectObj.length > 0) {
			other += "&drafterId=" + rolesSelectObj[0].value;
		}
		var url = "lbpmHandlerParseService&matrix=" + matrix + other+"&analysis4View="+analysis4View;
		if(distinct) {
			url += "&distinct=true";
		}
		var data = new KMSSData();
		if(action) {
			data.SendToBean(url, action);
		} else {
			return data.AddBeanData(url).GetHashMapArray();
		}
	};
	
	// 解析节点处理人详细信息（规则配置）
	lbpm.globals.ruleNextNodeHandler=function(nodeId, rule, analysis4View, distinct, action) {
		if (rule == '' || rule == null) {
			return [{name: '('+lbpm.constant.COMMONNODEHANDLERORGEMPTY+')'}];
		}
		rule = encodeURIComponent(rule);
		var other = "&modelId=" + lbpm.globals.getWfBusinessFormModelId() + "&modelName=" + lbpm.globals.getWfBusinessFormModelName();
		var rolesSelectObj = document.getElementsByName('rolesSelectObj');
		if (rolesSelectObj != null && rolesSelectObj.length > 0) {
			other += "&drafterId=" + rolesSelectObj[0].value;
		}
		var url = "lbpmHandlerParseService&nowNodeId="+nodeId+"&rule=" + rule + other+"&analysis4View="+analysis4View;
		if(distinct) {
			url += "&distinct=true";
		}
		var data = new KMSSData();
		if(action) {
			data.SendToBean(url, action);
		} else {
			return data.AddBeanData(url).GetHashMapArray();
		}
	};
	
	return declare("sys.lbpmservice.mobile.workitem.NextAdHocRoutes", [_RadioGroup], {
		
			name : 'nextAdHocRoutes',
		
			tmpl : '<input type="radio" id="nextAdHocRouteId[!{index}]" data-dojo-type="sys.lbpmservice.mobile.workitem.AdHocRouteRadio"'
				+ ' key="nextAdHocRouteId" index="!{index}"'
				+ 'data-dojo-props="checked:!{checked},showStatus:\'edit\',name:\'!{name}\',text:\'!{text}\',value:\'!{value}\''
				+ ',rightText:\'!{rightText}\',index:\'!{index}\',handlerSelectType:\'!{handlerSelectType}\',distinct:!{distinct},handlerIds:\'!{handlerIds}\'">',

			onComplete : function(items) {
				var g = this.generateList(items);
				var self = this;
				if (g && g.then) {
					g.then(function() {
						if (items && items.length == 1) {
							query('.muiFieldText', self.domNode).style({'margin-left': '0', 'padding-left': '5px'});
							query('.muiFormRadio', self.domNode).style('display', 'none');
							query('.muiRadioCircle', self.domNode).style('display', 'none');
						}
					});
				}
			},
			
			startup: function() {
				var _self = this;
				var isRemoveNodeIdentifier = false;
				if (Lbpm_SettingInfo.isRemoveNodeIdentifier === "true"){
					isRemoveNodeIdentifier = true;
				}
				
				var nextAdHocSubRoutes = [];
				// 构建内置子节点的route选择项
				for (var index = 0;index<lbpm.adHocRoutes.length; index++) {
					if (lbpm.adHocRouteId != null && lbpm.adHocRouteId == lbpm.adHocRoutes[index].startNodeId) {
						continue;
					}
					var subNode = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId].adHocSubNodes[lbpm.adHocRoutes[index].startNodeId];
					var nodeObj = subNode.data;
					nextAdHocSubRoutes.push(nodeObj);
				}
				// 构建即席子流程节点流出的下一步节点的route选择项
				nextAdHocSubRoutes.push(lbpm.nodes[lbpm.nowAdHocSubFlowNodeId].endLines[0].endNode);
				
				if (nextAdHocSubRoutes.length == 1) {
					this.set("value",lbpm.nodes[lbpm.nowAdHocSubFlowNodeId].endLines[0].endNode.id);
				}
				
				this.store = array.map(nextAdHocSubRoutes, function(nodeObj, i) {
					var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
					var labelText = "";
					if (isRemoveNodeIdentifier){
						labelText = langNodeName;
					}else{
						labelText = nodeObj.id + "." + langNodeName;
					}
					return {
						text:util.formatText(labelText), 
						checked: _self.value == nodeObj.id, 
						value: nodeObj.id,
						index: i,
						rightText: nodeObj.handlerNames == null ? "none" : util.formatText(nodeObj.handlerNames),
						handlerIds: nodeObj.handlerIds == null ? "none" : util.formatText(nodeObj.handlerIds),
						distinct: false,
						handlerSelectType: nodeObj.handlerSelectType,
						name: 'nextAdHocRouteId'
					};
				});
				this.inherited(arguments);
			},
			
			createListItem : function(props) {
				if (this.isConcentrate(props))
					return null;
				var tmpl = this.tmpl.replace('!{value}', props.value)
								.replace('!{text}', props.text.replace(/&#39;/g,"\\\'").replace(/\$/g, '$$$'))
								.replace(/!{index}/g, props.index)
								.replace('!{checked}', props.checked ? true : false)
								.replace('!{distinct}', props.distinct)
								.replace('!{handlerIds}', props.handlerIds.replace(/\$/g, '$$$'))
								.replace('!{handlerSelectType}', props.handlerSelectType)
								.replace('!{name}', props.name)
								.replace('!{rightText}', props.rightText);
				var item = domConstruct.toDom(tmpl);
				return item;
			}
	});
});