/**
 * 即将流向选择，复选框组
 */
define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/query",
    "dojo/_base/array",
	"dojo/dom-class",
	"dojo/dom-construct",
	"dojo/dom-style",
	"mui/form/CheckBox",
	"mui/form/CheckBoxGroup",
	"mui/util",
	"dijit/registry",
	"dojo/request"
	], function(declare, lang, query, array, domClass, domConstruct, domStyle, _CheckBox, _CheckBoxGroup, util, registry, request) {

	return declare("sys.lbpmservice.mobile.workitem.FutureNodesChekBoxGroup", [_CheckBoxGroup], {
		
		routeLineInfo:null,
	
		tmpl : '<input type="checkbox" !{disabledText} id="futureNodeId[!{index}]" data-dojo-type="sys/lbpmservice/mobile/workitem/FutureNodesChekBox"'
			+ ' manualBranchNodeId="!{manualBranchNodeId}" key="futureNodeId" index="!{index}"'
			+ 'data-dojo-props="checked:!{checked},showStatus:\'edit\',name:\'!{name}\',text:\'!{text}\',value:\'!{value}\''
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
					}
				});
			}
			//校验一下，并行分支存在默认分支时，默认分支选上了，但是校验时失效的，所以生成列表后再重新校验一次
			if(this.validation){
				this.validation.validateElement(this);
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
			if (lbpm && lbpm.settingInfo){
				if (lbpm.settingInfo.isRemoveNodeIdentifier === "true"){
					isRemoveNodeIdentifier = true;
				}
			}
			this.store = array.map(routeLines, function(lineObj, i) {
				var nodeObj = lineObj.endNode;
				var langLineName = WorkFlow_getLangLabel(lineObj.name,lineObj["langs"]);
				var lineName = langLineName||"";
				var isManualBranch = lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,lineObj.startNode);
				
				//判断并行分支是否启动了自定义启动
				var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
				var defaultStartBranchIds,canSelectDefaultBranch,isDefault = false;
				if(nextNodeObj.nodeDescType=="splitNodeDesc"&&nextNodeObj.splitType&&nextNodeObj.splitType=="custom"){
					isManualBranch=true;
					//获取默认分支和是否可以选择
					var info = _self.getSplitNodeInfo(nextNodeObj.id);
					if(info){
						defaultStartBranchIds = info.defaultStartBranchIds;
						if(defaultStartBranchIds && defaultStartBranchIds.indexOf(nodeObj.id) != -1){
							isDefault = true;
						}
						canSelectDefaultBranch = info.canSelectDefaultBranch;
					}
				}
				
				var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
				var labelText = "";
				if (isRemoveNodeIdentifier){
					labelText = langNodeName;
				}else{
					labelText = nodeObj.id + "." + langNodeName
				}
				return {
					text:util.formatText(lineName + labelText) , 
					checked: isDefault || _self.value == nodeObj.id || (!_self.value && _self.checkedNodeIds && _self.checkedNodeIds.indexOf(nodeObj.id) >= 0), 
					value: nodeObj.id,
					manualBranchNodeId: lineObj.startNode.id,
					index: i,
					rightText: nodeObj.handlerNames == null ? "none" : util.formatText(nodeObj.handlerNames),
					handlerIds: nodeObj.handlerIds == null ? "none" : util.formatText(nodeObj.handlerIds),
					distinct: lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),
					handlerSelectType: nodeObj.handlerSelectType,
					isManualBranch: isManualBranch,
					name: isManualBranch ? 'futureNode' : '__futureNode__',
					disabledText:(isDefault && canSelectDefaultBranch == 'false') ? 'disabled' : ""
				};
			});
			this.inherited(arguments);
			if(dojoConfig.dingXForm === "true"){
			   $("#sys_lbpmservice_mobile_workitem_FutureNodesChekBoxGroup").removeClass("muiFormLeft");
               $("#notifyLevelTD").children().removeClass("muiFormLeft");				
			}
		},
		
		/*
		 * 获取启动并行分支节点的属性(包括默认分支和是否选中）
		 */
		getSplitNodeInfo : function(nodeId){
			var processId = lbpm.modelId;
			var url = "/sys/lbpm/engine/jsonp.jsp?s_bean=lbpmProcessDefinitionDetailService&processId="+processId+"&nodeId="+nodeId;
			url = util.formatUrl(url);
			var nodeInfo;
			request.get(url,
			{handleAs:'json',sync:true}).then(
			function(data){
				//请求成功后的回调
				nodeInfo = WorkFlow_LoadXMLData(data.data)
			});
			return nodeInfo;
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
							.replace('!{handlerIds}', props.handlerIds)
							.replace('!{handlerSelectType}', props.handlerSelectType)
							.replace('!{isManualBranch}', props.isManualBranch)
							.replace('!{name}', props.name)
							.replace('!{rightText}', props.rightText)
							.replace('!{disabledText}', props.disabledText);
			var item = domConstruct.toDom(tmpl);
			return item;
		}
	});
	

});