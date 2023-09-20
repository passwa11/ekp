/**
 * 即将流向选择，复选框
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
	"dijit/registry"
	], function(declare, lang, query, array, domClass, domConstruct, domStyle, _CheckBox, _CheckBoxGroup, util, registry) {
		return declare("sys.lbpmservice.mobile.workitem.FutureNodesChekBox", [_CheckBox], {
		
		rightText: null,
		
		handlerIds: null,
		
		handlerSelectType: null,
		
		isManualBranch: true,
		
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
					domStyle.set(query(".mui-checkbox-base",self.fieldOptions)[0],{"display":"inline"})
				};
				if(this.handlerSelectType=="formula"){
					lbpm.globals.formulaNextNodeHandler(this.handlerIds,true,this.distinct, action);
				} else if (this.handlerSelectType=="matrix") {
					lbpm.globals.matrixNextNodeHandler(this.handlerIds,true,this.distinct, action);
				} else if (this.handlerSelectType=="rule") {
					lbpm.globals.ruleNextNodeHandler(this.value, this.handlerIds,true,this.distinct, action);
				} else {
					lbpm.globals.parseNextNodeHandler(this.handlerIds,true,this.distinct, action);
				}
			}
			if (!this.isManualBranch) {
				this.domNode.removeAttribute('key');
				domStyle.set(this.checkboxIcon,"display","none");
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

});