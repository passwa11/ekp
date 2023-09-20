define(["dojo/_base/declare",
         "dojo/_base/lang",
         "mui/form/RadioGroup",
         "mui/form/Radio",
         "dojo/_base/array",
         "mui/util",
         "dojo/dom-construct",
         "dojo/dom-attr",
         "dijit/registry",
         "dojo/query"], function(declare, lang, _RadioGroup, _Radio, array, util, domConstruct, domAttr,registry,query) {

	var RadioGroup = declare("sys.lbpmservice.mobile.node.draftnode.RadioGroup", [_RadioGroup], {
		
		checkedId: null,
		
		nodeId: null,
		
		tmpl : '<input type="radio" id="manualFutureNodeId_!{manualBranchNodeId}_!{value}" data-dojo-type="sys/lbpmservice/mobile/node/draftnode/Radio"'
			+ ' manualBranchNodeId="!{manualBranchNodeId}" key="manualFutureNodeId"'
			+ ' data-dojo-props="checked:!{checked},showStatus:\'edit\',name:\'manualFutureNodeId_!{manualBranchNodeId}\',text:\'!{text}\',value:\'!{value}\'">',
			
		postCreate:function(){
			this.subscribe("/sys/lbpmservice/mobile/draftnode/radio/change","_validateAllGroup");
		},
			
		startup: function() {
			var nodeData = lbpm.nodes[this.nodeId];
			if (this.checkedId){
				this.set("value", this.checkedId);
			}
			var _self = this;
			this.store = array.map(nodeData.endLines, function(endLine, i) {
				// 过滤掉产生闭环的节点分支
				if (lbpm.globals.isClosedLoop(nodeData, endLine)) {
					return false;
				}
				var radioName = endLine.name || (endLine.endNode.id + "." + endLine.endNode.name);
				return {
					text:util.formatText(radioName) , 
					checked: _self.checkedId == endLine.endNode.id, 
					value: endLine.endNode.id,
					manualBranchNodeId: nodeData.id
				};
			});
			this.store = array.filter(this.store, function(v) {
				return !(v === false);
			});
			this.inherited(arguments);
			//this.validation.validateElement(this);
		},
		
		createListItem : function(props) {
			if (this.isConcentrate(props))
				return null;
			var tmpl = this.tmpl.replace(/!{value}/g, props.value)
							.replace(/!{text}/g, props.text)
							.replace(/!{manualBranchNodeId}/g, props.manualBranchNodeId)
							.replace('!{checked}', props.checked ? true : false);
			var item = domConstruct.toDom(tmpl);
			return item;
		},
		
		_validateAllGroup:function(){//重新校验，解决人工决策选择后重建导致校验错乱问题
			var _self = this;
			query(".draftworkitemBrancheGroup").forEach(function(domObj){
			   var id = domAttr.get(domObj,"id");
			   if(id){
				   var radioGroup = registry.byId(id);
				   if(radioGroup != _self){
					   radioGroup.validation.validateElement(radioGroup);
				   }
			   }
			})
		}
	});
	
	return RadioGroup;
});
