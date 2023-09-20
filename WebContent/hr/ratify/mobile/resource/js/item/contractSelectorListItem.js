define([ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_Contained",
	"dijit/_Container", "dojo/window", "dojo/_base/array",
	"dojo/dom-style", "dojo/dom-class", "dojo/dom-construct", "dojo/dom-attr", "dojo/topic", 
	"dojo/on", "dojo/request", "dojo/touch", "dojo/query" ], 
	
	function(declare, WidgetBase, Contained, Container, win, array, domStyle, domClass, domCtr, domAttr, topic, on, request, touch, query) {
	
	return declare("hr.ratify.mobile.js.contractSelectorListItem", [ WidgetBase, Contained, Container ], {

		tagName: 'li',

		TYPE_CERT: 0,
		TYPE_CAT: 1,
		
		isMul: false,
		
		type: '',
		fdId: '',
		text: '',
		operateType: '',
		
		postCreate: function(){
			this.inherited(arguments);
			this.bindEvents();
		},
		
		buildRendering: function(){

			this.domNode = domCtr.create(this.tagName, {
				className: 'muiCateItem'
			});
			domAttr.set(this.domNode, 'data-id', this.fdId);
			domAttr.set(this.domNode, 'data-type', this.type);
			domAttr.set(this.domNode, 'data-operate', this.operateType);
			domAttr.set(this.domNode, 'data-text', this.text);
			domAttr.set(this.domNode, 'data-begin', this.fdBeginDate);
			domAttr.set(this.domNode, 'data-end', this.fdEndDate);
			domAttr.set(this.domNode, 'data-remark', this.remark);
			
			this.renderItem();
			
		},
		
		bindEvents: function(){
			
		},
		
		renderItem: function(){
			var selFdId = "";
			if(this.operateType == 'change'){
				selFdId = query('[name="fdContractId"]')[0].value;
			}else if(this.operateType == 'remove'){
				selFdId = query('[name="fdRemoveContractId"]')[0].value;
			}
			
			var ctx = this;

			var muiCateInfoItem = domCtr.create('div', {
				className: 'muiCateInfoItem'
			}, ctx.domNode);
			
			var muiCateContainer = domCtr.create('div', {
				className: 'muiCateContainer'
			}, muiCateInfoItem);
			
			if(ctx.type == ctx.TYPE_CERT) {
				var innerHTMLDIV = "";
				if(selFdId == this.fdId){
					innerHTMLDIV = '<div style="background-color:#1fbaf3;" class="muiCateSel' + (ctx.isMul ? ' muiCateSelMul' : '') + '"></div>';
				}else{
					innerHTMLDIV = '<div class="muiCateSel' + (ctx.isMul ? ' muiCateSelMul' : '') + '"></div>';
				}
				domCtr.create('div', {
					className: 'muiCateSelArea',
					innerHTML: innerHTMLDIV
				}, muiCateContainer);
			}
			
			domCtr.create('div', {
				className: 'muiCateInfo',
				innerHTML: '<div class="muiCateName">' + ctx.text + '</div>'
			}, muiCateContainer);

			if(ctx.type == ctx.TYPE_CAT) {
				domCtr.create('div', {
					className: 'muiCateMore',
					innerHTML: '<i class="mui mui-forward"></i>'
				}, muiCateContainer);
			}
		}
		
	});
		
})