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
	"mui/form/Select",
	"mui/util",
	"dijit/registry",
	"dojo/parser",
	"dojo/store/Memory",
	"mui/i18n/i18n!sys-mobile"
	], function(declare, lang, query, array, domClass, domConstruct, domStyle, _RadioGroup, _Radio, _Select, util, registry, parser, Memory, Msg) {
	
	var Radio = declare("sys.lbpmservice.mobile.workitem.Radio", [_Radio], {
		
		rightText: null,
		
		handlerIds: null,
		
		handlerSelectType: null,
		
		isManualBranch: true,

		selectTemp: "<div data-dojo-type='sys.lbpmservice.mobile.workitem.Select' data-dojo-props='name:\"!{name}\",value:\"!{value}\",mul:!{mul},placeholder:\"!{placeholder}\",nodeId:\"!{nodeId}\"'></div>",

		buildRendering : function() {
			this.inherited(arguments);
			if(this.handlerIds){
				this.handlerIds = this.handlerIds.replace(/&#35;/g, "\\");
			}
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
					lbpm.globals.formulaNextNodeHandler(this.handlerIds,true,this.distinct, action,this.value);
				}else if (this.handlerSelectType=="matrix") {
					lbpm.globals.matrixNextNodeHandler(this.handlerIds,true,this.distinct, action,this.value);
				}else if (this.handlerSelectType=="rule") {
					lbpm.globals.ruleNextNodeHandler(this.value,this.handlerIds,true,this.distinct, action);
				} else {
					lbpm.globals.parseNextNodeHandler(this.handlerIds,true,this.distinct, action,this.value);
				}
			}
			if (!this.isManualBranch) {
				this.domNode.removeAttribute('key');
				var radioIconArray = query("span.muis-form-radio.muiRadioCircle",this.radioNode);
				if(radioIconArray && radioIconArray.length>0){
					var radioIcon = radioIconArray[0];
					domStyle.set(radioIcon,"display","none");
					domClass.add(this.radioNode,"only");
				}
			}
			if(this.value){
				var nodeObj = lbpm.nodes[this.value];
				if(nodeObj && nodeObj.nodeDescType=="dynamicSubFlowNodeDesc" && nodeObj.splitType == "custom"){
					lbpm.canHideNextNodeTr = false;
					var tmpl = this.selectTemp.replace("!{name}", "dynamicNextNodeIds_"+this.value)
						.replace("!{value}", "")
						.replace("!{mul}", true)
						.replace("!{nodeId}", nodeObj.id)
						.replace("!{placeholder}", Msg['mui.form.please.select'] + (nodeObj.dynamicGroupShowName?nodeObj.dynamicGroupShowName:nodeObj.name));
					var dom = domConstruct.toDom(tmpl);
					var divDom = domConstruct.create("div",{style:{
						display:'inline-block',
						marginLeft: '20px',
						maxWidth: '60%'
					}});
					domConstruct.place(divDom, this.radioNode, "last");
					domConstruct.place(dom, divDom, "last");
					parser.parse(divDom);
				}
			}
		},
		
		_onClick: function() {
			if(!this.__checkFlag){
				this.__checkFlag=true;
				this.inherited(arguments);
				lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE,null);
				this.__checkFlag=false;
			}
		},
		_setCheckedAttr : function(checked) {
			this.inherited(arguments);
			if(checked){
				this.defer(function(){
					lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE, null);
				},420);
			}
		}
	});

	var Select = declare("sys.lbpmservice.mobile.workitem.Select", [_Select], {
		itemRenderer : '<input type="checkbox" data-dojo-type="mui/form/CheckBox" name="_select_box_!{valueField}" value="!{value}" data-dojo-props="tag:\'li\',mul:!{mul},text:\'!{text}\',checked:!{checked},pop:!{pop}">',

		_onClick : function(evt) {
			lbpm.globals.setDynamicNodeInfo();
			lbpm.globals.parseXMLObj();
			lbpm.selectedNextDynamicSubFlowRoute = true;
			var dynamicNode = lbpm.nodes[this.nodeId];
			var splitNodeId = dynamicNode.splitNodeId;
			var splitNode = lbpm.nodes[splitNodeId];
			if(splitNode){
				var defaultStartBranchIds = splitNode.defaultStartBranchIds;
				if(defaultStartBranchIds && !this.value){
					this.value = defaultStartBranchIds;
				}
				var canSelectDefaultBranch = splitNode.canSelectDefaultBranch;
				var store = [];
				for(var i =0;i<splitNode.endLines.length;i++){
					var endLine = splitNode.endLines[i];
					var lineNodeName = WorkFlow_getLangLabel(endLine.name,endLine["langs"]);
					if(!lineNodeName){
						lineNodeName = WorkFlow_getLangLabel(endLine.endNode.name,endLine.endNode["langs"],"nodeName");
					}
					var _item = {value:endLine.endNode.id,text:lineNodeName};
					if(defaultStartBranchIds && Com_ArrayGetIndex(defaultStartBranchIds.split(";"), endLine.endNode.id) > -1 && canSelectDefaultBranch=="false"){
						_item.disabled = true;
					}
					store.push(_item);
				}
				store = new Memory({
					data : store
				});
				this.setStore(store, this.query, this.queryOptions);
				this.inherited(arguments);
			}
		},

		createListItem : function(props) {
			var itemRenderer = this.itemRenderer;
			var propsText = (typeof(props.text)!="undefined" && props.text!=null) ? util.formatText(props.text.toString().replace("'","&#039;")) : "";
			var propsValue = (typeof(props.value)!="undefined" && props.value!=null) ? util.formatText(props.value.toString()) : "";
			itemRenderer = itemRenderer.replace('!{text}',propsText);
			itemRenderer = itemRenderer.replace('!{checked}', props.selected);
			itemRenderer = itemRenderer.replace('!{value}',propsValue);
			itemRenderer = itemRenderer.replace('!{mul}',this.mul);
			itemRenderer = itemRenderer.replace('!{valueField}',this.valueField);
			itemRenderer = itemRenderer.replace('!{pop}',this.pop);
			var item = domConstruct.toDom(itemRenderer);
			if(props.disabled){
				item.disabled = "true";
			}
			return item;
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

	lbpm.globals.parseNodeHandler4CalcType = function(nodeObj){
		if(!nodeObj || (lbpm.nodeParseHandlers && lbpm.nodeParseHandlers[nodeObj.id])){
			return;//已经存在的不再进行解析
		}
		var handlerArray = null
		var distinct =  lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj);
		if(nodeObj.handlerSelectType=="formula"){
			handlerArray = lbpm.globals.formulaNextNodeHandler(nodeObj.handlerIds,true,distinct);
		}else if (nodeObj.handlerSelectType=="matrix") {
			handlerArray = lbpm.globals.matrixNextNodeHandler(nodeObj.handlerIds,true,distinct);
		}else if (nodeObj.handlerSelectType=="rule") {
			handlerArray = lbpm.globals.ruleNextNodeHandler(nodeObj.id,nodeObj.handlerIds,true,distinct);
		}
		if (handlerArray && handlerArray.length > 0) {
			var ids = '',names='';
			for ( var j = 0; j < handlerArray.length; j++) {
				ids += handlerArray[j].id + ";";
				names += handlerArray[j].name + ";";
			}
			ids = ids ? ids.substring(0,ids.length-1) : ids;
			names = names ? names.substring(0,names.length-1) : names;
			if(!lbpm.nodeParseHandlers){
				lbpm.nodeParseHandlers = {};
			}
			lbpm.nodeParseHandlers[nodeObj.id] = {id:ids,name:names};
		}
	};
	//设置公式定义器/矩阵组织/规则引起，解析后人员的id和name
	lbpm.globals.setNodeParseHandler = function(nodeData){
		lbpm.globals.parseNodeHandler4CalcType(nodeData);
		if(lbpm.nodeParseHandlers && lbpm.nodeParseHandlers[nodeData.id]){
			var nodeParseHandler = lbpm.nodeParseHandlers[nodeData.id];
			lbpm.nodeParseHandlerId = nodeParseHandler.id ? {value:nodeParseHandler.id} : null;
			lbpm.nodeParseHandlerName = nodeParseHandler.name ? {value:nodeParseHandler.name} : null;
		}
	};
	
	return declare("sys.lbpmservice.mobile.workitem.FutureNodes", [_RadioGroup], {
		
			routeLineInfo:null,
		
			tmpl : '<input type="radio" id="futureNodeId[!{index}]" data-dojo-type="sys.lbpmservice.mobile.workitem.Radio"'
				+ ' manualBranchNodeId="!{manualBranchNodeId}" key="futureNodeId" index="!{index}"'
				+ 'data-dojo-props="checked:!{checked},showStatus:\'!{showStatus}\',name:\'!{name}\',text:\'!{text}\',value:\'!{value}\''
				+ ',rightText:\'!{rightText}\',index:\'!{index}\',handlerSelectType:\'!{handlerSelectType}\',distinct:!{distinct},handlerIds:\'!{handlerIds}\',isManualBranch:!{isManualBranch}">',

			onComplete : function(items) {
				var g = this.generateList(items);
				var self = this;
				if (g && g.then) {
					g.then(function() {
						if (items && items.length == 1) {
							query('.muiFieldText', self.domNode).style({'margin-left': '0', 'padding-left': '5px'});
							query('.muiFormRadio', self.domNode).style('display', 'none');
							query('.muiRadioCircle', self.domNode).style('display', 'none');
							domClass.add(query('.muiRadioItem', self.domNode)[0],"only");
						}
					});
				}
			},
			
			startup: function() {
				var routeLines = [];
				if(this.routeLineInfo!="" && this.routeLineInfo!=null){
					var lines= this.routeLineInfo.split(";");
					array.forEach(lines,function(line){
						routeLines.push(lbpm.lines[line]);
					});
				}else{
					var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
					routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(lbpm.globals.getCurrentNodeObj(),nextNodeObj, true);
				}
				if(routeLines.length==1){
					this.set("value",routeLines[0].endNode.id);
				}
				var _self = this;
				var isRemoveNodeIdentifier = false;
				if (Lbpm_SettingInfo && Lbpm_SettingInfo.isHideNodeIdentifier === "true" && (Lbpm_SettingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier" || Lbpm_SettingInfo.hideNodeIdentifierType === "isRemoveNodeIdentifier")){
					isRemoveNodeIdentifier = true;
				}
				this.store = array.map(routeLines, function(lineObj, i) {
					var nodeObj = lineObj.endNode;
					var langLineName = WorkFlow_getLangLabel(lineObj.name,lineObj["langs"]);
					var lineName = langLineName||"";
					var isManualBranch = lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,lineObj.startNode);
					var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
					var labelText = "";
					if (isRemoveNodeIdentifier){
						labelText = langNodeName;
					}else{
						labelText = nodeObj.id + "." + langNodeName;
					}
					var extText = "";
					//下一个节点是自由子流程节点时且显示出子流程节点内的第一个子节点
					if(nodeObj.nodeDescType=='freeSubFlowNodeDesc') {
						if (lbpm.nodes[nodeObj.startNodeId].endLines[0].endNode.nodeDescType != "groupEndNodeDesc") {
							var groupFirstSubNode = lbpm.nodes[nodeObj.startNodeId].endLines[0].endNode;
							extText += "&nbsp;&rarr;&nbsp;[";
							if (Lbpm_SettingInfo.isRemoveNodeIdentifier != "true") {
								extText += groupFirstSubNode.id + ".";
							}
							extText += groupFirstSubNode.name + "(" + groupFirstSubNode.handlerNames + ")]";
						}
					}
					return {
						text:util.formatText(lineName + labelText) + extText, 
						checked: _self.value == nodeObj.id, 
						value: nodeObj.id,
						manualBranchNodeId: lineObj.startNode.id,
						index: i,
						rightText: nodeObj.handlerNames == null ? "none" : util.formatText(nodeObj.handlerNames),
						handlerIds: nodeObj.handlerIds == null ? "none" : util.formatText(nodeObj.handlerIds.replace(/\\/g, "&#35;")),
						distinct: lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),
						handlerSelectType: nodeObj.handlerSelectType,
						isManualBranch: isManualBranch,
						name: isManualBranch ? 'futureNode' : '__futureNode__',
						showStatus:_self.isEditStatus==false ? 'readOnly' : 'edit'
					};
				});
				this.inherited(arguments);
				this._doValidate();//重新校验一次
			},
			
			createListItem : function(props) {
				if (this.isConcentrate(props))
					return null;
				var tmpl = this.tmpl.replace('!{value}', props.value)
								.replace('!{text}', props.text.replace(/&#39;/g,"\\\'").replace(/\$/g, '$$$'))
								.replace('!{manualBranchNodeId}', props.manualBranchNodeId)
								.replace(/!{index}/g, props.index)
								.replace('!{checked}', props.checked ? true : false)
								.replace('!{distinct}', props.distinct)
								.replace('!{handlerIds}', props.handlerIds.replace(/&#39;/g,"\\\'").replace(/\$/g, '$$$'))
								.replace('!{handlerSelectType}', props.handlerSelectType)
								.replace('!{isManualBranch}', props.isManualBranch)
								.replace('!{name}', props.name)
								.replace('!{rightText}', props.rightText.replace(/&#39;/g,"\\\'").replace(/\$/g, '$$$'))
								.replace('!{showStatus}', props.showStatus);
				var item = domConstruct.toDom(tmpl);
				return item;
			},
			
			_doValidate:function(){
				if(!this.validation)
					return;
				this.validation.validateElement(this);
			}
	});
});